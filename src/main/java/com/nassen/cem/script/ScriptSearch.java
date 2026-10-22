package com.nassen.cem.script;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ContentDisposition;

/**
 * 検索画面の検索の部品（自動生成 / auto-generated）。Controller の検索（JPA の Specification）から使う。
 *
 * <p>手動編集は再生成で失われます。</p>
 */
public final class ScriptSearch {

    private ScriptSearch() {
    }

    /**
     * コードで設定した並び順（一覧.DefaultSort = "列 [ASC|DESC], …"）：列は表の列名・項目名（columns：名前 → 項目名）。
     * 設定していない（null）場合・表にない列だけの場合は画面定義の並び順。
     */
    public static org.springframework.data.domain.Sort sort(String text, java.util.Map<String, String> columns, org.springframework.data.domain.Sort defaultSort) {
        if (text == null) {
            return defaultSort;
        }
        List<org.springframework.data.domain.Sort.Order> orders = new ArrayList<>();
        for (String part : text.split(",")) {
            String[] words = part.trim().split("\\s+");
            String field = words[0].isEmpty() ? null : columns.get(words[0].toUpperCase());
            if (field == null || words.length > 2) {
                continue;
            }
            boolean desc = words.length == 2 && words[1].equalsIgnoreCase("DESC");
            orders.add(desc ? org.springframework.data.domain.Sort.Order.desc(field) : org.springframework.data.domain.Sort.Order.asc(field));
        }
        return orders.isEmpty() ? (text.isBlank() ? org.springframework.data.domain.Sort.unsorted() : defaultSort) : org.springframework.data.domain.Sort.by(orders);
    }

    /** 条件の値がある（null・空白だけの文字・空の配列でない）。 */
    public static boolean has(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof String s) {
            return !s.isBlank();
        }
        if (value instanceof Collection<?> c) {
            return c.stream().anyMatch(ScriptSearch::has);
        }
        return true;
    }

    /** 比べる文字：前後の空白を除き、normalize の場合は全角・半角をそろえる（NFKC）、lower の場合は小文字にする。 */
    public static String text(Object value, boolean normalize, boolean lower) {
        String s = value == null ? "" : String.valueOf(value).trim();
        if (normalize) {
            s = Normalizer.normalize(s, Normalizer.Form.NFKC);
        }
        return lower ? s.toLowerCase() : s;
    }

    /** LIKE の文字：kind は start（前方一致）/ contains（部分一致）/ end（後方一致）。% _ \ は文字として扱う（エスケープは \）。 */
    public static String like(Object value, String kind, boolean normalize, boolean lower) {
        String s = text(value, normalize, lower).replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
        return switch (kind) {
            case "start" -> s + "%";
            case "end" -> "%" + s;
            default -> "%" + s + "%";
        };
    }

    /** 配列の条件の値（空の値を除く）。 */
    public static List<Object> values(Collection<?> values) {
        List<Object> list = new ArrayList<>();
        if (values != null) {
            for (Object v : values) {
                if (has(v)) {
                    list.add(v);
                }
            }
        }
        return list;
    }

    /** 列挙の値：定数名、または CD（getValue()）が同じ定数。ない場合は null。 */
    public static <E extends Enum<E>> E enumOf(Class<E> type, Object value) {
        if (value == null) {
            return null;
        }
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        String s = String.valueOf(value).trim();
        for (E e : type.getEnumConstants()) {
            if (e.name().equals(s) || s.equals(code(e))) {
                return e;
            }
        }
        return null;
    }

    /** 列挙の値の一覧（配列の条件）。 */
    public static <E extends Enum<E>> List<E> enumsOf(Class<E> type, Collection<?> values) {
        List<E> list = new ArrayList<>();
        for (Object v : values(values)) {
            E e = enumOf(type, v);
            if (e != null) {
                list.add(e);
            }
        }
        return list;
    }

    private static String code(Enum<?> e) {
        try {
            Object v = e.getClass().getMethod("getValue").invoke(e);
            return v == null ? null : String.valueOf(v);
        } catch (ReflectiveOperationException ex) {
            return null;
        }
    }

    /**
     * 検索結果を CSV でダウンロードさせる（UTF-8、BOM 付き。Excel で開ける）。値は文字・日付をダブルクォーテーションで囲み、
     * 日付は yyyy/MM/dd、日時は yyyy/MM/dd HH:mm:ss、列挙は表示名（getLabel()）。
     */
    public static void csv(HttpServletResponse response, String fileName, List<String> headers, List<List<Object>> rows) {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", ContentDisposition.attachment().filename(fileName, StandardCharsets.UTF_8).build().toString());
        try {
            Writer w = new java.io.OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
            w.write('﻿');
            w.write(line(new ArrayList<>(headers)));
            for (List<Object> row : rows) {
                w.write(line(row));
            }
            w.flush();
        } catch (IOException ex) {
            throw new ScriptLib.ScriptException("CSV を出力できません（" + ex.getMessage() + "）");
        }
    }

    private static String line(List<?> values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(cell(values.get(i)));
        }
        return sb.append("\r\n").toString();
    }

    private static String cell(Object v) {
        if (v == null) {
            return "";
        }
        if (v instanceof Number || v instanceof Boolean) {
            return String.valueOf(v);
        }
        String s;
        if (v instanceof LocalDate d) {
            s = d.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        } else if (v instanceof LocalDateTime t) {
            s = t.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        } else if (v instanceof Enum<?> e) {
            s = label(e);
        } else {
            s = String.valueOf(v);
        }
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }

    private static String label(Enum<?> e) {
        try {
            Object v = e.getClass().getMethod("getLabel").invoke(e);
            return v == null ? e.name() : String.valueOf(v);
        } catch (ReflectiveOperationException ex) {
            return e.name();
        }
    }
}
