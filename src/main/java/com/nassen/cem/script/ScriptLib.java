package com.nassen.cem.script;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

/**
 * コード（画面・モジュールの「コード」タブ）から生成した Java が使う組み込み関数・型の変換・比較（自動生成 / auto-generated）。
 *
 * <p>null の数値は 0、文字列は "" として扱う（VB と同じ）。手動編集は再生成で失われます。</p>
 */
public final class ScriptLib {

    private ScriptLib() {
    }

    /** Throw で投げる例外（メッセージは画面に表示する）。 */
    public static class ScriptException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        /** MessageView に表示するレベル（Throw "…" は Error、検索の件数超過は Warning）。 */
        private final String level;

        public ScriptException(String message) {
            this(message, "Error");
        }

        public ScriptException(String message, String level) {
            super(message);
            this.level = level;
        }

        public String getLevel() {
            return level;
        }
    }

    // ===== 型の変換 =====

    public static String str(Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof BigDecimal d) {
            return d.toPlainString();
        }
        if (value instanceof Double d && d == Math.rint(d) && !Double.isInfinite(d)) {
            return String.valueOf(d.longValue());
        }
        return value.toString();
    }

    /** 列挙の値の Value・Label・Cd（text：getValue など）。空（null）の場合は ""。 */
    public static <E> String enumText(E value, java.util.function.Function<E, String> text) {
        if (value == null) {
            return "";
        }
        String result = text.apply(value);
        return result == null ? "" : result;
    }

    public static boolean bool(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean b) {
            return b;
        }
        if (value instanceof Number n) {
            return n.doubleValue() != 0;
        }
        String s = value.toString().trim();
        return s.equalsIgnoreCase("true") || s.equals("1") || s.equalsIgnoreCase("on");
    }

    public static BigDecimal dec(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal d) {
            return d;
        }
        if (value instanceof Integer || value instanceof Long || value instanceof Short || value instanceof Byte) {
            return BigDecimal.valueOf(((Number) value).longValue());
        }
        if (value instanceof Number n) {
            return BigDecimal.valueOf(n.doubleValue());
        }
        if (value instanceof Boolean b) {
            return b ? BigDecimal.ONE.negate() : BigDecimal.ZERO;
        }
        String s = value.toString().trim().replace(",", "");
        return s.isEmpty() ? BigDecimal.ZERO : new BigDecimal(s);
    }

    public static int intv(Object value) {
        return value instanceof Integer i ? i : dec(value).setScale(0, RoundingMode.HALF_EVEN).intValue();
    }

    public static long lng(Object value) {
        return value instanceof Long l ? l : value instanceof Integer i ? i : dec(value).setScale(0, RoundingMode.HALF_EVEN).longValue();
    }

    public static double dbl(Object value) {
        return value instanceof Number n && !(value instanceof BigDecimal) ? n.doubleValue() : dec(value).doubleValue();
    }

    public static Integer toInteger(Object value) {
        return intv(value);
    }

    public static Long toLong(Object value) {
        return lng(value);
    }

    public static Double toDouble(Object value) {
        return dbl(value);
    }

    public static LocalDate toDate(Object value) {
        if (value == null || value instanceof LocalDate) {
            return (LocalDate) value;
        }
        if (value instanceof Timestamp t) {
            return t.toLocalDateTime().toLocalDate();
        }
        if (value instanceof LocalDateTime dt) {
            return dt.toLocalDate();
        }
        String s = value.toString().trim().replace('/', '-');
        return s.isEmpty() ? null : LocalDate.parse(s.length() > 10 ? s.substring(0, 10) : s);
    }

    public static LocalDateTime toDateTime(Object value) {
        if (value == null || value instanceof LocalDateTime) {
            return (LocalDateTime) value;
        }
        if (value instanceof Timestamp t) {
            return t.toLocalDateTime();
        }
        if (value instanceof LocalDate d) {
            return d.atStartOfDay();
        }
        String s = value.toString().trim().replace('/', '-').replace(' ', 'T');
        return s.isEmpty() ? null : s.length() <= 10 ? LocalDate.parse(s).atStartOfDay() : LocalDateTime.parse(s);
    }

    /** DateTime(1～9)（java.sql.Timestamp）にする。 */
    public static Timestamp toTimestamp(Object value) {
        if (value == null || value instanceof Timestamp) {
            return (Timestamp) value;
        }
        LocalDateTime dt = toDateTime(value);
        return dt == null ? null : Timestamp.valueOf(dt);
    }

    /** DateTime(n) への代入：秒の小数部を digits 桁に切り捨てる（新しい Timestamp。元の値は変えない）。 */
    public static Timestamp truncateTime(Timestamp value, int digits) {
        if (value == null) {
            return null;
        }
        int unit = (int) Math.pow(10, 9 - Math.max(0, Math.min(9, digits)));
        int nanos = value.getNanos();
        if (nanos % unit == 0) {
            return value;
        }
        Timestamp result = new Timestamp(value.getTime());
        result.setNanos(nanos - nanos % unit);
        return result;
    }

    public static boolean isNumeric(Object value) {
        if (value instanceof Number) {
            return true;
        }
        if (value == null) {
            return false;
        }
        try {
            new BigDecimal(value.toString().trim().replace(",", ""));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** List の要素（位置は 0 から。範囲外は null。File.Read の 1 行の値を変数に入れるときに使う）。 */
    public static Object item(java.util.List<?> list, int index) {
        return list != null && index >= 0 && index < list.size() ? list.get(index) : null;
    }

    /** List の値をすべて文字列にする（File.Read で 1 行の値を String の List に入れるときに使う）。 */
    public static java.util.List<String> strings(java.util.List<?> list) {
        java.util.List<String> result = new java.util.ArrayList<>();
        if (list != null) {
            for (Object value : list) {
                result.add(str(value));
            }
        }
        return result;
    }

    public static String errMessage(Throwable ex) {
        return ex == null ? "" : ex.getMessage() != null ? ex.getMessage() : ex.toString();
    }

    /** ex.Code：エラーの種類（重複エラー／排他エラー／データなし／参照エラー／タイムアウト／DBエラー／その他のエラー）。 */
    public static String errCode(Throwable ex) {
        if (ex instanceof ScriptDb.DbException db) {
            return db.getCode();
        }
        boolean database = false;
        for (Throwable t = ex; t != null; t = t.getCause() == t ? null : t.getCause()) {
            String name = t.getClass().getSimpleName();
            if (name.contains("OptimisticLock") || name.contains("StaleObjectState") || name.contains("StaleState")) {
                return "排他エラー";
            }
            if (name.contains("PessimisticLock") || name.contains("LockTimeout") || name.contains("QueryTimeout") || name.contains("CannotAcquireLock")) {
                return "タイムアウト";
            }
            if (t instanceof java.sql.SQLException sql) {
                String state = sql.getSQLState() == null ? "" : sql.getSQLState();
                int code = sql.getErrorCode();
                // 一意制約：SQLState 23505（PostgreSQL・H2）、ORA-00001。外部キー：23503・23506、ORA-02291・ORA-02292
                if (state.equals("23505") || code == 1) {
                    return "重複エラー";
                }
                if (state.equals("23503") || state.equals("23506") || code == 2291 || code == 2292) {
                    return "参照エラー";
                }
                if (state.equals("57014") || state.equals("HYT00") || code == 1013 || code == 30006) {
                    return "タイムアウト";
                }
                database = true;
            }
            String pkg = t.getClass().getName();
            if (pkg.startsWith("org.springframework.dao") || pkg.startsWith("org.springframework.orm") || pkg.startsWith("org.springframework.transaction")
                || pkg.startsWith("jakarta.persistence") || pkg.startsWith("org.hibernate")) {
                database = true;
            }
        }
        return database ? "DBエラー" : "その他のエラー";
    }

    /** ex.Description：利用者に見せてよいメッセージ（SQL・内部の情報を含まない）。Throw・DB の処理の失敗はそのメッセージ。 */
    public static String errDescription(Throwable ex) {
        if (ex instanceof ScriptException || ex instanceof ScriptDb.DbException) {
            return errMessage(ex);
        }
        return switch (errCode(ex)) {
            case "重複エラー" -> "同じデータが既にあります。";
            case "排他エラー" -> "他の人が更新しました。開き直してください。";
            case "データなし" -> "データがありません。";
            case "参照エラー" -> "関連するデータがあるため処理できません。";
            case "タイムアウト" -> "混み合っています。しばらくしてから操作してください。";
            case "DBエラー" -> "データベースのエラーのため処理できませんでした。";
            default -> "処理できませんでした。";
        };
    }

    /** ex.Detail：詳細（エラーの種類とメッセージを原因までつなげたもの）。ログ用。画面には出さない。 */
    public static String errDetail(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        for (Throwable t = ex; t != null; t = t.getCause() == t ? null : t.getCause()) {
            if (sb.length() > 0) {
                sb.append(" / ");
            }
            sb.append(t.getClass().getName()).append(": ").append(t.getMessage());
        }
        return sb.toString();
    }

    // ===== エラーの場所・日時（ex.Event / ex.Screen / ex.Line / ex.Time） =====

    /** 実行中のイベント（画面 ID・イベント名。Controller がイベントの前に設定する）。 */
    private static final ThreadLocal<String[]> EVENT = new ThreadLocal<>();

    /** 捕まえたエラーの日時とイベント（エラーごと。エラーが不要になると消える）。 */
    private static final java.util.Map<Throwable, Object[]> CAUGHT = java.util.Collections.synchronizedMap(new java.util.WeakHashMap<>());

    /** 生成したクラス（画面のイベント・モジュール）の名前と「Java の行 → コードの行」の対応（Java の行の順の組）。 */
    private static final java.util.Map<String, Object[]> SOURCES = new java.util.concurrent.ConcurrentHashMap<>();

    /** イベントの始まり（ex.Event・ex.Screen の値）。 */
    public static void enter(String screen, String event) {
        EVENT.set(new String[] { screen, event });
    }

    /** イベントの終わり。 */
    public static void leave() {
        EVENT.remove();
        if (ScriptDebug.on) {
            ScriptDebug.ended();
        }
    }

    /** 生成したクラスを登録する（クラスの static の初期化で呼ぶ）。lines は Java の行とコードの行の組を Java の行の順に並べたもの。 */
    public static void source(Class<?> type, String name, int[] lines) {
        SOURCES.put(type.getName(), new Object[] { name, lines });
    }

    /** エラーを捕まえた（Catch・Form.Error の前）：日時とイベントを覚える。 */
    public static <T extends Throwable> T caught(T ex) {
        if (ex != null) {
            String[] event = EVENT.get();
            CAUGHT.putIfAbsent(ex, new Object[] { LocalDateTime.now(), event == null ? "" : event[1] });
        }
        return ex;
    }

    /** ex.Event：エラーが起きたイベント（例：btnSave.Click）。 */
    public static String errEvent(Throwable ex) {
        return ex == null ? "" : (String) CAUGHT.get(caught(ex))[1];
    }

    /** ex.Time：エラーが起きた日時。 */
    public static LocalDateTime errTime(Throwable ex) {
        return ex == null ? null : (LocalDateTime) CAUGHT.get(caught(ex))[0];
    }

    /** ex.Screen：エラーが起きた画面 ID・モジュール名。 */
    public static String errScreen(Throwable ex) {
        Object[] place = place(ex);
        if (place != null) {
            return (String) place[0];
        }
        String[] event = EVENT.get();
        return event == null ? "" : event[0];
    }

    /** ex.Line：エラーが起きたコードの行（わからない場合は 0）。 */
    public static int errLine(Throwable ex) {
        Object[] place = place(ex);
        return place == null ? 0 : (Integer) place[1];
    }

    // エラーが起きた場所：スタックトレース（原因を含む）で最初に見つかった生成したクラスの行（名前、コードの行）
    private static Object[] place(Throwable ex) {
        for (Throwable t = ex; t != null; t = t.getCause() == t ? null : t.getCause()) {
            for (StackTraceElement frame : t.getStackTrace()) {
                String type = frame.getClassName();
                int inner = type.indexOf('$');
                Object[] source = SOURCES.get(inner < 0 ? type : type.substring(0, inner));
                if (source == null || frame.getLineNumber() <= 0) {
                    continue;
                }
                int[] lines = (int[]) source[1];
                int line = 0;
                for (int i = 0; i + 1 < lines.length && lines[i] <= frame.getLineNumber(); i += 2) {
                    line = lines[i + 1];
                }
                return new Object[] { source[0], line };
            }
        }
        return null;
    }

    // ===== Log（ログに出す） =====

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger("script");

    /** Log(値)：ログに出す。エラー（ex）は Error、それ以外は Info。 */
    public static void log(Object value) {
        logAt(value instanceof Throwable ? "Error" : "Info", value);
    }

    /**
     * Log(レベル, 値)：レベル（LogLevel.Error / Warning / Info / Debug）を指定してログに出す。
     * エラー（ex）は内容（種類・説明・場所・日時・詳細）とスタックトレースをすべて出す。
     */
    public static void logAt(String level, Object value) {
        org.slf4j.event.Level slf4j = switch (level == null ? "" : level.toLowerCase(java.util.Locale.ROOT)) {
            case "error" -> org.slf4j.event.Level.ERROR;
            case "warning" -> org.slf4j.event.Level.WARN;
            case "debug" -> org.slf4j.event.Level.DEBUG;
            default -> org.slf4j.event.Level.INFO;
        };
        if (!LOG.isEnabledForLevel(slf4j)) {
            return;
        }
        // ログの場所（【画面 ID 行 行目】。出力ウィンドウでダブルクリックするとその行へ移動する）
        Object[] here = here();
        String where = here == null ? "" : "【" + here[0] + " " + here[1] + " 行目】";
        if (value instanceof Throwable ex) {
            LOG.atLevel(slf4j).setCause(ex).log("{}{}（{}）画面 / モジュール：{}　イベント：{}　行：{}　日時：{}　詳細：{}", where, errDescription(ex), errCode(ex), errScreen(ex), errEvent(ex),
                errLine(ex), errTime(ex), errDetail(ex));
            return;
        }
        LOG.atLevel(slf4j).log(where + str(value));
    }

    /** ブラウザー側の Log（開発の実行環境でサーバーに送ったもの）：【画面 ID 行 行目】（ブラウザー）内容。 */
    public static void logBrowser(String level, String screen, int line, String text) {
        org.slf4j.event.Level slf4j = switch (level == null ? "" : level.toLowerCase(java.util.Locale.ROOT)) {
            case "error" -> org.slf4j.event.Level.ERROR;
            case "warning" -> org.slf4j.event.Level.WARN;
            case "debug" -> org.slf4j.event.Level.DEBUG;
            default -> org.slf4j.event.Level.INFO;
        };
        if (LOG.isEnabledForLevel(slf4j)) {
            String where = screen == null || screen.isEmpty() ? "" : "【" + screen + (line > 0 ? " " + line + " 行目" : "") + "】";
            LOG.atLevel(slf4j).log("{}（ブラウザー）{}", where, text);
        }
    }

    /** 今の場所：呼び出し元の生成したクラス（画面のイベント・モジュール）の名前とコードの行。わからない場合は null。 */
    public static Object[] here() {
        return StackWalker.getInstance().walk(frames -> frames
            .map(f -> sourceLine(f.getClassName(), f.getLineNumber()))
            .filter(java.util.Objects::nonNull)
            .findFirst().orElse(null));
    }

    /** 生成したクラスの Java の行 → 名前とコードの行（生成したクラスでない場合は null）。 */
    public static Object[] sourceLine(String className, int javaLine) {
        int inner = className.indexOf('$');
        Object[] source = SOURCES.get(inner < 0 ? className : className.substring(0, inner));
        if (source == null || javaLine <= 0) {
            return null;
        }
        int[] lines = (int[]) source[1];
        int line = 0;
        for (int i = 0; i + 1 < lines.length && lines[i] <= javaLine; i += 2) {
            line = lines[i + 1];
        }
        return new Object[] { source[0], line };
    }

    /**
     * Log(&amp;a, &amp;b, "文字列", …)：名前と値の組を「&amp;a = 1, &amp;b = "x", 文字列」の形にする（名前が null の値は文字列としてそのまま）。
     * 文字列は "" で囲み、空（null）は Nothing、一覧・構造体は中身を並べる。
     */
    public static String vars(Object... pairs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i + 1 < pairs.length; i += 2) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            if (pairs[i] == null) {
                sb.append(str(pairs[i + 1]));
            } else {
                sb.append(pairs[i]).append(" = ").append(display(pairs[i + 1]));
            }
        }
        return sb.toString();
    }

    /** 値を確認用の文字にする（ログ・ステップ実行の変数の値）。 */
    public static String display(Object value) {
        StringBuilder sb = new StringBuilder();
        display(sb, value, 0, new java.util.IdentityHashMap<>());
        return sb.toString();
    }

    private static void display(StringBuilder sb, Object value, int depth, java.util.IdentityHashMap<Object, Boolean> seen) {
        if (value == null) {
            sb.append("Nothing");
            return;
        }
        if (value instanceof CharSequence s) {
            sb.append('"').append(s.length() > 500 ? s.subSequence(0, 500) + "…" : s).append('"');
            return;
        }
        if (value instanceof Throwable ex) {
            sb.append(ex.getClass().getSimpleName()).append(": ").append(errDescription(ex));
            return;
        }
        if (value instanceof Number || value instanceof Boolean || value instanceof Character || value instanceof Temporal
            || value instanceof java.util.Date || value instanceof Enum<?> || value.getClass().getName().startsWith("java.")) {
            if (!(value instanceof java.util.Collection<?>) && !(value instanceof java.util.Map<?, ?>)) {
                sb.append(str(value));
                return;
            }
        }
        if (seen.containsKey(value) || depth >= 4) {
            sb.append("…");
            return;
        }
        seen.put(value, Boolean.TRUE);
        try {
            if (value instanceof java.util.Collection<?> list) {
                sb.append('[');
                int n = 0;
                for (Object item : list) {
                    if (n > 0) {
                        sb.append(", ");
                    }
                    if (n++ == 20) {
                        sb.append("…（全 ").append(list.size()).append(" 件）");
                        break;
                    }
                    display(sb, item, depth + 1, seen);
                }
                sb.append(']');
                return;
            }
            if (value instanceof java.util.Map<?, ?> map) {
                sb.append('{');
                int n = 0;
                for (var entry : map.entrySet()) {
                    if (n > 0) {
                        sb.append(", ");
                    }
                    if (n++ == 20) {
                        sb.append("…（全 ").append(map.size()).append(" 件）");
                        break;
                    }
                    display(sb, entry.getKey(), depth + 1, seen);
                    sb.append(": ");
                    display(sb, entry.getValue(), depth + 1, seen);
                }
                sb.append('}');
                return;
            }
            // 構造体・Entity：フィールドを並べる
            sb.append('{');
            int n = 0;
            for (Class<?> type = value.getClass(); type != null && type != Object.class; type = type.getSuperclass()) {
                for (java.lang.reflect.Field field : type.getDeclaredFields()) {
                    if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                        continue;
                    }
                    if (n++ > 0) {
                        sb.append(", ");
                    }
                    sb.append(field.getName()).append(": ");
                    try {
                        field.setAccessible(true);
                        display(sb, field.get(value), depth + 1, seen);
                    } catch (ReflectiveOperationException | RuntimeException e) {
                        sb.append('?');
                    }
                }
            }
            sb.append('}');
        } finally {
            seen.remove(value);
        }
    }

    // ===== List =====

    /** AddAll(&一覧2)：一覧 2 の要素をすべて最後に追加する（自分自身も可）。 */
    public static <E> void addAll(java.util.List<E> list, java.util.List<? extends E> values) {
        if (values != null) {
            list.addAll(new java.util.ArrayList<>(values));
        }
    }

    /**
     * Clone()：構造体・List・Map のコピー（中の構造体・List・Map もコピーする。文字列・数値・日付などはそのまま）。
     * コピーを変更しても元は変わらない。同じものを 2 か所から指している場合は、コピーでも同じ 1 つを指す。
     */
    @SuppressWarnings("unchecked")
    public static <T> T copyOf(T value) {
        return (T) copyOf(value, new java.util.IdentityHashMap<>());
    }

    private static Object copyOf(Object value, java.util.IdentityHashMap<Object, Object> copied) {
        if (value == null || value instanceof CharSequence || value instanceof Number || value instanceof Boolean || value instanceof Character
            || value instanceof Enum<?> || value instanceof java.time.temporal.Temporal || value instanceof java.util.Date || value.getClass().isRecord()) {
            return value;
        }
        Object done = copied.get(value);
        if (done != null) {
            return done;
        }
        if (value instanceof java.util.List<?> list) {
            java.util.List<Object> copy = new java.util.ArrayList<>(list.size());
            copied.put(value, copy);
            for (Object element : list) {
                copy.add(copyOf(element, copied));
            }
            return copy;
        }
        if (value instanceof java.util.Map<?, ?> map) {
            java.util.Map<Object, Object> copy = new java.util.LinkedHashMap<>();
            copied.put(value, copy);
            for (java.util.Map.Entry<?, ?> entry : map.entrySet()) {
                copy.put(entry.getKey(), copyOf(entry.getValue(), copied));
            }
            return copy;
        }
        // 構造体（表の変数を含む）：同じクラスを作り、フィールドをコピーする（static・final は除く）
        try {
            var constructor = value.getClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            Object copy = constructor.newInstance();
            copied.put(value, copy);
            for (Class<?> type = value.getClass(); type != null && type != Object.class; type = type.getSuperclass()) {
                for (java.lang.reflect.Field field : type.getDeclaredFields()) {
                    int modifiers = field.getModifiers();
                    if (java.lang.reflect.Modifier.isStatic(modifiers) || java.lang.reflect.Modifier.isFinal(modifiers) || field.isSynthetic()) {
                        continue;
                    }
                    field.setAccessible(true);
                    field.set(copy, copyOf(field.get(value), copied));
                }
            }
            return copy;
        } catch (ReflectiveOperationException | RuntimeException ex) {
            throw new IllegalStateException(value.getClass().getSimpleName() + " をコピーできません（Clone）：" + ex.getMessage(), ex);
        }
    }

    /** Sort([Desc])：並べ替える（Null は昇順で先頭）。 */
    public static void sort(java.util.List<?> list, boolean desc) {
        list.sort((a, b) -> {
            int r = a == null ? (b == null ? 0 : -1) : b == null ? 1 : compare(a, b);
            return desc ? -r : r;
        });
    }

    // ===== IsEmpty =====

    /** 値が空：空の値（null）・""・0・要素が 0 件の配列 / Map・項目がすべて初期値の構造体（表の変数を含む。SetEmpty の後と同じ）。 */
    public static boolean isEmpty(Object value) {
        return isEmpty(value, false, new java.util.IdentityHashMap<>());
    }

    // item：構造体の項目（False も初期値なので空）。seen：見た構造体（互いに参照し合う構造体で同じものを見ない）
    private static boolean isEmpty(Object value, boolean item, java.util.Map<Object, Boolean> seen) {
        if (value == null) {
            return true;
        }
        if (value instanceof CharSequence s) {
            return s.length() == 0;
        }
        if (value instanceof BigDecimal d) {
            return d.signum() == 0;
        }
        if (value instanceof Number n) {
            return n.doubleValue() == 0;
        }
        if (value instanceof Boolean b) {
            return item && !b;
        }
        if (value instanceof java.util.Collection<?> c) {
            return c.isEmpty();
        }
        if (value instanceof java.util.Map<?, ?> m) {
            return m.isEmpty();
        }
        if (value.getClass().isArray()) {
            return java.lang.reflect.Array.getLength(value) == 0;
        }
        if (value instanceof Enum<?> || value.getClass().getName().startsWith("java.")) {
            return false;
        }
        // 構造体・表の変数：項目をすべて調べる（getter は使わない。使うときに作る項目を作らないため）
        if (seen.put(value, Boolean.TRUE) != null) {
            return true;
        }
        for (Class<?> type = value.getClass(); type != null && type != Object.class; type = type.getSuperclass()) {
            for (java.lang.reflect.Field field : type.getDeclaredFields()) {
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    if (!isEmpty(field.get(value), true, seen)) {
                        return false;
                    }
                } catch (ReflectiveOperationException | RuntimeException e) {
                    return false;
                }
            }
        }
        return true;
    }

    // ===== 空の値（DB の NULL・画面の空の入力をコードでは空の値にする） =====

    public static String nz(String value) {
        return value == null ? "" : value;
    }

    public static Integer nz(Integer value) {
        return value == null ? 0 : value;
    }

    public static Long nz(Long value) {
        return value == null ? 0L : value;
    }

    public static BigDecimal nz(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    public static Double nz(Double value) {
        return value == null ? 0.0 : value;
    }

    public static Boolean nz(Boolean value) {
        return value != null && value;
    }

    /** CHAR の列：右の空白を削る（DB の NULL は ""）。 */
    public static String nzChar(String value) {
        return value == null ? "" : value.stripTrailing();
    }

    /** NULL を許す文字の列に保存する値："" は NULL（Oracle と同じ。DB によって違わないように）。 */
    public static String emptyToNull(String value) {
        return value == null || value.isEmpty() ? null : value;
    }

    /** 「空（0）は NULL で保存」の数値の列に保存する値：0 は NULL。 */
    public static <T extends Number> T zeroToNull(T value) {
        return value == null || isEmpty(value) ? null : value;
    }

    /** Map の値（構造体）：キーがない場合はエラー（作って返すと、書いた値が Map に入らないため）。 */
    public static <K, V> V item(java.util.Map<K, V> map, K key) {
        V value = map.get(key);
        if (value == null && !map.containsKey(key)) {
            throw new ScriptException("キー「" + key + "」がありません。");
        }
        return value;
    }

    // ===== Msg（サーバー側）：処理の後で画面の MessageView に表示する =====

    private static final ThreadLocal<java.util.List<ScriptMessage>> MESSAGES = ThreadLocal.withInitial(java.util.ArrayList::new);

    /** Msg(&m)：メッセージを MessageView に書く（処理の後で表示する）。 */
    public static void msg(ScriptMessage message) {
        if (message != null) {
            MESSAGES.get().add(message.copy());
        }
    }

    /** Msg(&一覧)：List(Message) をすべて書く。 */
    public static void msgAll(java.util.List<ScriptMessage> messages) {
        if (messages != null) {
            messages.forEach(ScriptLib::msg);
        }
    }

    /** Msg(レベル, 文字列[, 項目])。 */
    public static void msg(String level, String description, String item) {
        MESSAGES.get().add(ScriptMessage.of(level, description, item));
    }

    /** MsgClear：この処理で書いたメッセージを消す。 */
    public static void msgClear() {
        MESSAGES.get().clear();
    }

    /** MsgCount([レベル])：この処理で書いたメッセージの件数（レベルが null の場合はすべて）。 */
    public static int msgCount(String level) {
        return (int) MESSAGES.get().stream().filter(m -> level == null || level.equalsIgnoreCase(m.getLevel())).count();
    }

    /** 処理で書いたメッセージ（Msg）を取り出す（ない場合は空）。 */
    public static java.util.List<ScriptMessage> takeMessages() {
        java.util.List<ScriptMessage> list = MESSAGES.get();
        MESSAGES.remove();
        return list;
    }

    // ===== 比較 =====

    /** =（数値どうしは値で比較。null は "" / 0 と同じ）。 */
    public static boolean eq(Object a, Object b) {
        return compare(a, b) == 0;
    }

    /** 大小の比較（負：a &lt; b、0：等しい、正：a &gt; b）。 */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static int compare(Object a, Object b) {
        if (a == null && b == null) {
            return 0;
        }
        // Timestamp は LocalDateTime として比較する
        if (a instanceof Timestamp t) {
            a = t.toLocalDateTime();
        }
        if (b instanceof Timestamp t) {
            b = t.toLocalDateTime();
        }
        if (a instanceof Number || b instanceof Number) {
            return dec(a).compareTo(dec(b));
        }
        if (a instanceof String || b instanceof String) {
            return str(a).compareTo(str(b));
        }
        if (a == null || b == null) {
            return a == null ? -1 : 1;
        }
        if (a instanceof LocalDate && b instanceof LocalDateTime) {
            return ((LocalDate) a).atStartOfDay().compareTo((LocalDateTime) b);
        }
        if (a instanceof LocalDateTime && b instanceof LocalDate) {
            return ((LocalDateTime) a).compareTo(((LocalDate) b).atStartOfDay());
        }
        if (a instanceof Comparable ca) {
            return ca.compareTo(b);
        }
        return a.equals(b) ? 0 : 1;
    }

    /** For の継続条件（Step が負の場合は i &gt;= end）。 */
    public static boolean forCondition(Object i, Object end, Object step) {
        return dec(step).signum() < 0 ? compare(i, end) >= 0 : compare(i, end) <= 0;
    }

    // ===== 計算 =====

    public static BigDecimal div(BigDecimal a, BigDecimal b) {
        return a.divide(b, MathContext.DECIMAL128).stripTrailingZeros();
    }

    public static long idiv(Object a, Object b) {
        return dec(a).divideToIntegralValue(dec(b)).longValue();
    }

    public static BigDecimal round(Object value, int digits) {
        return dec(value).setScale(digits, RoundingMode.HALF_UP);
    }

    /** Truncate(数値, 桁数)：小数点以下 桁数 桁で切り捨て（0 の方へ。-1.25 → -1.2）。 */
    public static BigDecimal truncate(Object value, int digits) {
        return dec(value).setScale(digits, RoundingMode.DOWN);
    }

    /** Floor(数値, 桁数)：小数点以下 桁数 桁の、小さい方の値（-1.25 → -1.3）。 */
    public static BigDecimal floor(Object value, int digits) {
        return dec(value).setScale(digits, RoundingMode.FLOOR);
    }

    /** Ceiling(数値, 桁数)：小数点以下 桁数 桁の、大きい方の値（切り上げ。1.21 → 1.3）。 */
    public static BigDecimal ceiling(Object value, int digits) {
        return dec(value).setScale(digits, RoundingMode.CEILING);
    }

    // ===== 文字列 =====

    public static int len(String s) {
        return s == null ? 0 : s.length();
    }

    public static String left(String s, int n) {
        s = str(s);
        return s.substring(0, Math.max(0, Math.min(n, s.length())));
    }

    public static String right(String s, int n) {
        s = str(s);
        return s.substring(Math.max(0, s.length() - Math.max(0, n)));
    }

    /** LPad(文字列, 文字数, 文字)：左に文字を埋めて 文字数 にする（長い場合は先頭から 文字数。文字が空の場合は空白）。 */
    public static String lpad(String s, int n, String c) {
        return pad(s, n, c, true);
    }

    /** RGB(赤, 緑, 青)：色の番号（#RRGGBB）。0 ～ 255 の外は 0・255 にする。 */
    public static String rgb(int r, int g, int b) {
        return String.format("#%02X%02X%02X", Math.max(0, Math.min(255, r)), Math.max(0, Math.min(255, g)), Math.max(0, Math.min(255, b)));
    }

    /** RPad(文字列, 文字数, 文字)：右に文字を埋めて 文字数 にする。 */
    public static String rpad(String s, int n, String c) {
        return pad(s, n, c, false);
    }

    private static String pad(String s, int n, String c, boolean left) {
        s = str(s);
        n = Math.max(0, n);
        if (s.length() >= n) {
            return s.substring(0, n);
        }
        String padding = (str(c).isEmpty() ? " " : str(c).substring(0, 1)).repeat(n - s.length());
        return left ? padding + s : s + padding;
    }

    public static String mid(String s, int start) {
        s = str(s);
        return start - 1 >= s.length() ? "" : s.substring(Math.max(0, start - 1));
    }

    public static String mid(String s, int start, int length) {
        String rest = mid(s, start);
        return rest.substring(0, Math.max(0, Math.min(length, rest.length())));
    }

    public static String trim(String s) {
        return str(s).strip();
    }

    public static String ltrim(String s) {
        return str(s).stripLeading();
    }

    public static String rtrim(String s) {
        return str(s).stripTrailing();
    }

    public static String ucase(String s) {
        return str(s).toUpperCase();
    }

    public static String lcase(String s) {
        return str(s).toLowerCase();
    }

    public static int instr(String s, String find) {
        return instr(1, s, find);
    }

    public static int instr(int start, String s, String find) {
        return str(s).indexOf(str(find), Math.max(0, start - 1)) + 1;
    }

    public static String replace(String s, String find, String replacement) {
        return str(find).isEmpty() ? str(s) : str(s).replace(str(find), str(replacement));
    }

    // ===== 日付 =====

    private static ChronoUnit unit(String interval) {
        return switch (str(interval).toLowerCase()) {
            case "yyyy", "y" -> ChronoUnit.YEARS;
            case "m" -> ChronoUnit.MONTHS;
            case "ww", "w" -> ChronoUnit.WEEKS;
            case "h" -> ChronoUnit.HOURS;
            case "n" -> ChronoUnit.MINUTES;
            case "s" -> ChronoUnit.SECONDS;
            default -> ChronoUnit.DAYS;
        };
    }

    public static LocalDate dateAdd(String interval, int amount, LocalDate date) {
        return date == null ? null : toDate(date.atStartOfDay().plus(amount, unit(interval)));
    }

    public static LocalDateTime dateAdd(String interval, int amount, LocalDateTime date) {
        return date == null ? null : date.plus(amount, unit(interval));
    }

    public static Timestamp dateAdd(String interval, int amount, Timestamp date) {
        return date == null ? null : Timestamp.valueOf(date.toLocalDateTime().plus(amount, unit(interval)));
    }

    public static long dateDiff(String interval, Object from, Object to) {
        LocalDateTime a = toDateTime(from);
        LocalDateTime b = toDateTime(to);
        return a == null || b == null ? 0 : unit(interval).between(a, b);
    }

    public static int year(Object date) {
        LocalDate d = toDate(date);
        return d == null ? 0 : d.getYear();
    }

    public static int month(Object date) {
        LocalDate d = toDate(date);
        return d == null ? 0 : d.getMonthValue();
    }

    public static int day(Object date) {
        LocalDate d = toDate(date);
        return d == null ? 0 : d.getDayOfMonth();
    }

    /** LastDay(日付)：その月の末日（日時は日付にする）。 */
    public static LocalDate lastDay(Object date) {
        LocalDate d = toDate(date);
        return d == null ? null : d.withDayOfMonth(d.lengthOfMonth());
    }

    /** Format(値, 書式)：数値は #,##0.00 など（DecimalFormat）、日付は yyyy/MM/dd など。文字列は %1 を 2 つ目の引数にする（formatText）。 */
    public static String format(Object value, String pattern) {
        if (value == null) {
            return "";
        }
        if (value instanceof String text) {
            return formatText(text, pattern);
        }
        if (value instanceof Timestamp t) {
            value = t.toLocalDateTime();
        }
        if (value instanceof Number) {
            return new DecimalFormat(pattern).format(dec(value));
        }
        if (value instanceof Temporal t) {
            return DateTimeFormatter.ofPattern(pattern).format(t);
        }
        return str(value);
    }

    private static final java.util.regex.Pattern FORMAT_PLACEHOLDER = java.util.regex.Pattern.compile("%(%|\\d+)");

    /** Format(文字列, 値1, 値2, …)：%1・%2 … を値の文字列にする（%% は %。値がない %n はそのまま）。 */
    public static String formatText(String template, Object... values) {
        java.util.regex.Matcher m = FORMAT_PLACEHOLDER.matcher(str(template));
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            String g = m.group(1);
            String text = m.group();
            if (g.equals("%")) {
                text = "%";
            } else if (g.length() < 10) {
                int n = Integer.parseInt(g);
                if (n >= 1 && values != null && n <= values.length) {
                    text = str(values[n - 1]);
                }
            }
            m.appendReplacement(sb, java.util.regex.Matcher.quoteReplacement(text));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    // ===== 画面項目の状態（コードの「項目.プロパティ = 式」） =====

    /**
     * 画面項目の状態：コードで設定したプロパティ（例: lines.Visible、txtName.ForeColor）。設定していないプロパティは画面定義の値（既定値）を使う。
     * フォームと一緒にセッションに保持する（setter がないため、送信された値は設定されない）。
     */
    public static class ViewState implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private final java.util.Map<String, Object> values = new java.util.HashMap<>();

        public void set(String key, Object value) {
            values.put(key, value);
            // ClassName を設定したら、それまでの AddClass / RemoveClass は使わない
            if (key.endsWith(".ClassName")) {
                String item = key.substring(0, key.length() - ".ClassName".length());
                values.remove(item + ".+Class");
                values.remove(item + ".-Class");
            }
        }

        public boolean has(String key) {
            return values.containsKey(key);
        }

        public Object get(String key, Object defaultValue) {
            return values.containsKey(key) ? values.get(key) : defaultValue;
        }

        /** Boolean のプロパティ（Visible / Enabled / ReadOnly など）。 */
        public boolean flag(String key, Object defaultValue) {
            return Boolean.TRUE.equals(get(key, defaultValue));
        }

        /** 文字のプロパティ（Caption / EmptyText など）。 */
        public String text(String key, String defaultValue) {
            return values.containsKey(key) ? str(values.get(key)) : defaultValue;
        }

        /** 属性の値（placeholder・min・max など）：値がない（Null・""）場合は null（属性を出さない）。 */
        public String attr(String key, Object defaultValue) {
            Object value = get(key, defaultValue);
            if (value instanceof java.time.LocalDateTime t) {
                return t.withSecond(0).withNano(0).toString().substring(0, 16);
            }
            String text = value instanceof BigDecimal d ? d.stripTrailingZeros().toPlainString() : value == null ? "" : value.toString();
            return text.isEmpty() ? null : text;
        }

        /** 正の整数の属性（maxlength・PageSize など）：0 以下・Null は null（属性を出さない）。 */
        public Integer positive(String key, Object defaultValue) {
            Object value = get(key, defaultValue);
            return value instanceof Number n && n.intValue() > 0 ? n.intValue() : null;
        }

        /** 整数のプロパティ（MaxRows など）。 */
        public int number(String key, int defaultValue) {
            Object value = get(key, defaultValue);
            return value instanceof Number n ? n.intValue() : defaultValue;
        }

        /** CSS のクラスを追加する（項目.AddClass）。 */
        public void addClass(String item, String name) {
            for (String c : classNames(name)) {
                classSet(item + ".+Class").add(c);
                classSet(item + ".-Class").remove(c);
            }
        }

        /** CSS のクラスを削除する（項目.RemoveClass）。 */
        public void removeClass(String item, String name) {
            for (String c : classNames(name)) {
                classSet(item + ".+Class").remove(c);
                classSet(item + ".-Class").add(c);
            }
        }

        @SuppressWarnings("unchecked")
        private java.util.Set<String> classSet(String key) {
            return (java.util.Set<String>) values.computeIfAbsent(key, k -> new java.util.LinkedHashSet<String>());
        }

        /** CSS のクラス（ClassName を設定した場合はその値、それ以外は画面定義のクラス。AddClass / RemoveClass を反映する）。 */
        @SuppressWarnings("unchecked")
        public String classes(String item, String defaultClasses) {
            java.util.Set<String> result = new java.util.LinkedHashSet<>(classNames(text(item + ".ClassName", defaultClasses)));
            if (values.get(item + ".+Class") instanceof java.util.Set<?> added) {
                result.addAll((java.util.Set<String>) added);
            }
            if (values.get(item + ".-Class") instanceof java.util.Set<?> removed) {
                result.removeAll(removed);
            }
            return String.join(" ", result);
        }

        /**
         * 見た目の CSS（コードで設定した ForeColor・BackColor・Bold・Italic・Underline・Strikeout・FontSize・Width・Height）。
         * input：入力欄の項目（背景色は入力欄の背景 --item-back、幅は入力欄で指定する）。underline・strikeout：画面定義の値。
         */
        public String css(String item, boolean input, boolean underline, boolean strikeout) {
            return look(values, item, input, underline, strikeout);
        }

        /** 入力欄の CSS（画面定義の幅・文字の位置など ＋ コードで設定した Width）。 */
        public String inputCss(String item, String defaultCss) {
            String width = cssValue(values.get(item + ".Width"));
            String css = defaultCss == null ? "" : defaultCss;
            return width.isEmpty() ? (css.isEmpty() ? null : css) : (css.isEmpty() ? "" : css + "; ") + "width: " + width;
        }

        /** 数値の表示書式（一覧.列.Format。例: #,##0.00）。数値でない値・書式が空の場合はそのままの文字。 */
        public String format(String key, String defaultFormat, Object value) {
            if (value == null) {
                return "";
            }
            String pattern = text(key, defaultFormat);
            if (pattern == null || pattern.isEmpty() || !(value instanceof Number)) {
                return str(value);
            }
            try {
                return new java.text.DecimalFormat(pattern).format(value);
            } catch (IllegalArgumentException e) {
                return str(value);
            }
        }

        /** 和暦の表示（画面項目の Wareki）：日付は 令和7年10月17日、日時は 令和7年10月17日 14:30。Null は空、明治6年より前・日付でない値はそのままの文字。 */
        public String wareki(Object value) {
            return warekiText(value);
        }

        /** Label の表示の種類（LabelStyle）の CSS。 */
        public String labelCss(String item, String defaultStyle) {
            return switch (text(item + ".LabelStyle", defaultStyle)) {
                case "Bold" -> "font-weight: bold";
                case "Info" -> "color: #0B5CAD";
                case "Warning" -> "color: #B25E00; font-weight: bold";
                case "Error" -> "color: #C62828; font-weight: bold";
                default -> "";
            };
        }

        /** ボタンの見た目（ButtonStyle）の CSS。 */
        public String buttonCss(String item, String defaultStyle) {
            return buttonCss(text(item + ".ButtonStyle", defaultStyle));
        }

        /** ボタンの見た目（Normal / Primary / Danger / Link）の CSS。 */
        public String buttonCss(String style) {
            return switch (style == null ? "" : style) {
                case "Primary" -> "background: #2B579A; color: #fff; border-color: #2B579A";
                case "Danger" -> "background: #C62828; color: #fff; border-color: #C62828";
                default -> "";
            };
        }

        /** 選択肢（Options を設定した場合はその Map。値・表示は文字列）。 */
        public java.util.Map<String, String> options(String key, java.util.Map<String, String> defaultOptions) {
            if (!(values.get(key) instanceof java.util.Map<?, ?> map)) {
                return defaultOptions;
            }
            java.util.Map<String, String> result = new java.util.LinkedHashMap<>();
            map.forEach((k, v) -> result.put(str(k), str(v)));
            return result;
        }

        /** 選択肢にある値か（Options を設定していない場合・空の値は true。配列は全部）。 */
        public boolean inOptions(String key, Object value) {
            if (!(values.get(key) instanceof java.util.Map<?, ?> map) || isBlank(value)) {
                return true;
            }
            java.util.Set<String> keys = new java.util.HashSet<>();
            map.keySet().forEach(k -> keys.add(str(k)));
            if (value instanceof java.util.Collection<?> list) {
                return list.stream().allMatch(v -> keys.contains(str(v)));
            }
            return keys.contains(value instanceof Enum<?> e ? e.name() : str(value));
        }
    }

    /** 和暦の文字（令和7年10月17日。日時は 令和7年10月17日 14:30。1 年は元年）。Null は空、明治6年より前・日付でない値はそのままの文字。 */
    public static String warekiText(Object value) {
        if (value == null || (value instanceof String s && s.isBlank())) {
            return "";
        }
        try {
            boolean withTime = value instanceof java.time.LocalDateTime || value instanceof Timestamp
                || (value instanceof String s && s.trim().length() > 10);
            java.time.LocalDateTime dt = toDateTime(value);
            java.time.chrono.JapaneseDate jd = java.time.chrono.JapaneseDate.from(dt.toLocalDate());
            int year = jd.get(java.time.temporal.ChronoField.YEAR_OF_ERA);
            String text = jd.getEra().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.JAPAN)
                + (year == 1 ? "元" : String.valueOf(year)) + "年" + dt.getMonthValue() + "月" + dt.getDayOfMonth() + "日";
            return withTime ? text + String.format(" %02d:%02d", dt.getHour(), dt.getMinute()) : text;
        } catch (RuntimeException e) {
            return str(value);
        }
    }

    /** 入力がない（Null・空白だけの文字・空の配列）。 */
    public static boolean isBlank(Object value) {
        return value == null || (value instanceof String s && s.isBlank()) || (value instanceof java.util.Collection<?> c && c.isEmpty());
    }

    /** 入力の範囲（コードの Min / Max・MinDate / MaxDate）：値と限度の比較（数値・日付・日時。比べられない場合は 0）。 */
    public static int compareLimit(Object value, Object limit) {
        if (value == null || limit == null) {
            return 0;
        }
        if (value instanceof Number && limit instanceof Number) {
            return new BigDecimal(value.toString()).compareTo(new BigDecimal(limit.toString()));
        }
        if (value instanceof java.time.LocalDateTime v && limit instanceof java.time.LocalDate l) {
            return v.compareTo(l.atStartOfDay());
        }
        if (value instanceof java.time.LocalDate v && limit instanceof java.time.LocalDateTime l) {
            return v.atStartOfDay().compareTo(l);
        }
        if (value instanceof java.time.LocalDate v && limit instanceof java.time.LocalDate l) {
            return v.compareTo(l);
        }
        if (value instanceof java.time.LocalDateTime v && limit instanceof java.time.LocalDateTime l) {
            return v.compareTo(l);
        }
        return 0;
    }

    // CSS のクラスの名前（英字・数字・_・- だけ）
    private static java.util.List<String> classNames(String text) {
        java.util.List<String> names = new java.util.ArrayList<>();
        for (String name : (text == null ? "" : text).split("\\s+")) {
            if (name.matches("[A-Za-z0-9_-]+")) {
                names.add(name);
            }
        }
        return names;
    }

    // CSS の値（属性を壊す文字・別の宣言を書ける文字を除く）
    private static String cssValue(Object value) {
        return value == null ? "" : str(value).replaceAll("[;{}<>\"'\\\\\\r\\n]", "").trim();
    }

    // 見た目の CSS（ViewState・GridState の行）
    private static String look(java.util.Map<String, Object> values, String item, boolean input, boolean underline, boolean strikeout) {
        java.util.List<String> css = new java.util.ArrayList<>();
        String fore = cssValue(values.get(item + ".ForeColor"));
        if (!fore.isEmpty()) {
            css.add("color: " + fore);
        }
        String back = cssValue(values.get(item + ".BackColor"));
        if (!back.isEmpty()) {
            css.add((input ? "--item-back: " : "background-color: ") + back);
        }
        if (values.containsKey(item + ".Bold")) {
            css.add("font-weight: " + (Boolean.TRUE.equals(values.get(item + ".Bold")) ? "bold" : "normal"));
        }
        if (values.containsKey(item + ".Italic")) {
            css.add("font-style: " + (Boolean.TRUE.equals(values.get(item + ".Italic")) ? "italic" : "normal"));
        }
        if (values.containsKey(item + ".Underline") || values.containsKey(item + ".Strikeout")) {
            boolean u = values.containsKey(item + ".Underline") ? Boolean.TRUE.equals(values.get(item + ".Underline")) : underline;
            boolean s = values.containsKey(item + ".Strikeout") ? Boolean.TRUE.equals(values.get(item + ".Strikeout")) : strikeout;
            css.add("text-decoration: " + (u && s ? "underline line-through" : u ? "underline" : s ? "line-through" : "none"));
        }
        String size = cssValue(values.get(item + ".FontSize"));
        if (!size.isEmpty()) {
            css.add("font-size: " + size);
        }
        String width = cssValue(values.get(item + ".Width"));
        if (!input && !width.isEmpty()) {
            css.add("width: " + width);
        }
        String height = cssValue(values.get(item + ".Height"));
        if (!height.isEmpty()) {
            css.add("height: " + height);
        }
        return String.join("; ", css);
    }

    /**
     * 一覧の行ごとの状態：Event 一覧.RowLoad で設定したプロパティ（例: lines.btnDelete.Visible）。行番号は 1 から。
     * 画面を表示する前に作り直す（RowLoad を行ごとに実行する）。
     */
    public static class GridState implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private final java.util.List<java.util.Map<String, Object>> rows = new java.util.ArrayList<>();

        public void clear() {
            rows.clear();
        }

        public void set(int row, String key, Object value) {
            if (row < 1) {
                return;
            }
            while (rows.size() < row) {
                rows.add(new java.util.HashMap<>());
            }
            rows.get(row - 1).put(key, value);
        }

        private Object get(int row, String key, Object defaultValue) {
            if (row < 1 || row > rows.size() || !rows.get(row - 1).containsKey(key)) {
                return defaultValue;
            }
            return rows.get(row - 1).get(key);
        }

        /** Boolean のプロパティ（Visible / Enabled / ReadOnly / Required）。 */
        public boolean flag(int row, String key, Object defaultValue) {
            return Boolean.TRUE.equals(get(row, key, defaultValue));
        }

        /** 文字のプロパティ（Caption など）。 */
        public String text(int row, String key, String defaultValue) {
            return str(get(row, key, defaultValue));
        }

        /** 属性の値（Confirm・Url など）：値がない（Null・""）場合は null（属性を出さない）。 */
        public String attr(int row, String key, String defaultValue) {
            String text = str(get(row, key, defaultValue));
            return text.isEmpty() ? null : text;
        }

        /** 値のプロパティ（変数の列の Value。設定していない行は defaultValue）。 */
        public Object value(int row, String key, Object defaultValue) {
            return get(row, key, defaultValue);
        }

        /** この行の見た目の CSS（ForeColor・BackColor・Bold など。input：入力できるセル）。 */
        public String css(int row, String item, boolean input) {
            return row < 1 || row > rows.size() ? "" : look(rows.get(row - 1), item, input, false, false);
        }

        /** この行に追加する CSS のクラス（ClassName。ボタンの ButtonStyle のクラスを含む）。 */
        public String classes(int row, String item, String defaultClasses) {
            return String.join(" ", classNames(str(get(row, item + ".ClassName", defaultClasses))));
        }
    }
}
