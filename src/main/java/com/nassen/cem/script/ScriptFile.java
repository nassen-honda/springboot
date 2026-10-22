package com.nassen.cem.script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ContentDisposition;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * コード（画面・モジュールの「コード」タブ）の File 型（自動生成 / auto-generated）。
 *
 * <ul>
 * <li>プロパティ：Encoding（UTF-8 / Shift_JIS）、Extension（txt / csv / xlsx / xlsm。空はパスの拡張子）、Separator（comma / tab）、DateFormat（日付を書くときの書式）、EOF、RowCount</li>
 * <li>テキスト・CSV：Open(パス, input / output / append)、ReadLine、Read（CSV の 1 行）、WriteLine、Write（CSV の 1 行）、Close</li>
 * <li>Excel：Open(パス)、Sheet、Cell、SetCell、Read / Write（行単位）、Save、SaveAs、Close（保存していない変更は破棄）</li>
 * <li>アップロード・ダウンロード・圧縮：Upload、Download、Zip、Unzip、ZipDownload</li>
 * </ul>
 * <p>読むとき：数値・Boolean・日付はその型、それ以外は文字列。書くとき：数値・Boolean はその型、日付は DateFormat の文字列、それ以外は文字列。
 * 行・列は 1 から。手動編集は再生成で失われます。</p>
 */
public class ScriptFile {

    private String encoding = "UTF-8";
    private String extension = "";
    private String separator = "comma";
    private String dateFormat = "yyyy/MM/dd";

    /** 画面の項目名（画面に配置した File のみ。アップロードの部品 name="_file_項目名"）。 */
    String item;

    /** File の配列の要素（画面に配置した File の配列のみ）：送られたファイルの何番目か（0 から）。-1 は配列でない。 */
    int index = -1;

    private Path path;
    private BufferedReader reader;
    private String next;
    private BufferedWriter writer;
    private Workbook book;
    private Sheet sheet;
    // Excel：Read で次に読む行（0 から）
    private int readRow;

    private static ScriptLib.ScriptException error(String message) {
        return new ScriptLib.ScriptException(message);
    }

    // ===== プロパティ =====

    public String getEncoding() { return encoding; }
    public void setEncoding(String encoding) { this.encoding = encoding == null || encoding.isBlank() ? "UTF-8" : encoding.trim(); }

    public String getExtension() { return extension; }
    public void setExtension(String extension) { this.extension = extension == null ? "" : extension.trim().toLowerCase().replace(".", ""); }

    public String getSeparator() { return separator; }
    public void setSeparator(String separator) { this.separator = separator == null ? "comma" : separator.trim().toLowerCase(); }

    public String getDateFormat() { return dateFormat; }
    public void setDateFormat(String dateFormat) { this.dateFormat = dateFormat == null || dateFormat.isBlank() ? "yyyy/MM/dd" : dateFormat; }

    /** 読むものがない（テキスト・CSV はファイルの終わり、Excel はシートの最後の行の後）。 */
    public boolean isEof() {
        if (book != null) {
            return readRow > sheet.getLastRowNum() || sheet.getPhysicalNumberOfRows() == 0;
        }
        return reader == null || next == null;
    }

    /** Excel のシートの最後の行の番号（行がない場合は 0）。テキスト・CSV は 0。 */
    public int getRowCount() {
        return book == null || sheet.getPhysicalNumberOfRows() == 0 ? 0 : sheet.getLastRowNum() + 1;
    }

    // ===== 開く・閉じる =====

    private String kind() {
        String ext = extension;
        if (ext.isEmpty() && path != null) {
            String name = path.getFileName().toString();
            int dot = name.lastIndexOf('.');
            ext = dot < 0 ? "txt" : name.substring(dot + 1).toLowerCase();
        }
        return ext.isEmpty() ? "txt" : ext;
    }

    private boolean isExcel() {
        return kind().equals("xlsx") || kind().equals("xlsm") || kind().equals("xls");
    }

    private Charset charset() {
        return Charset.forName(encoding);
    }

    /** Open(パス, モード)：モードは input（読む）/ output（書く。上書き）/ append（追加）。Excel はファイルがなければ新しく作る。 */
    public void open(String path, String mode) {
        close();
        this.path = Paths.get(path);
        try {
            if (isExcel()) {
                if (Files.exists(this.path)) {
                    try (InputStream in = Files.newInputStream(this.path)) {
                        book = WorkbookFactory.create(in);
                    }
                } else {
                    book = new XSSFWorkbook();
                }
                sheet = book.getNumberOfSheets() > 0 ? book.getSheetAt(0) : book.createSheet("Sheet1");
                readRow = 0;
                return;
            }
            switch (mode == null ? "input" : mode.toLowerCase()) {
                case "output" -> writer = Files.newBufferedWriter(this.path, charset());
                case "append" -> writer = Files.newBufferedWriter(this.path, charset(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                default -> {
                    reader = Files.newBufferedReader(this.path, charset());
                    next = reader.readLine();
                    // UTF-8 の BOM は読み飛ばす
                    if (next != null && next.startsWith("﻿")) {
                        next = next.substring(1);
                    }
                }
            }
        } catch (IOException e) {
            throw error("ファイルを開けません：" + path + "（" + e.getMessage() + "）");
        }
    }

    /** 閉じる（Excel の保存していない変更は破棄）。 */
    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (book != null) {
                book.close();
            }
        } catch (IOException e) {
            throw error("ファイルを閉じられません：" + path + "（" + e.getMessage() + "）");
        } finally {
            reader = null;
            writer = null;
            next = null;
            book = null;
            sheet = null;
        }
    }

    /** 保存する（Excel は開いたファイルに書く。テキスト・CSV は書いた内容をファイルに出す）。 */
    public void save() {
        if (path == null) {
            throw error("ファイルを開いていません（Open してください）。");
        }
        saveAs(path.toString());
    }

    /** 別のファイルに保存する。 */
    public void saveAs(String path) {
        Path target = Paths.get(path);
        try {
            if (book != null) {
                try (OutputStream out = Files.newOutputStream(target)) {
                    book.write(out);
                }
            } else if (writer != null) {
                writer.flush();
                if (!target.equals(this.path)) {
                    Files.copy(this.path, target, StandardCopyOption.REPLACE_EXISTING);
                }
            } else if (this.path != null && !target.equals(this.path)) {
                Files.copy(this.path, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw error("ファイルを保存できません：" + path + "（" + e.getMessage() + "）");
        }
    }

    // ===== 読む =====

    private String nextLine() {
        if (reader == null) {
            throw error("ファイルを読むモード（Open(パス) / Open(パス, Input)）で開いていません。");
        }
        if (next == null) {
            throw error("ファイルの終わりを超えて読もうとしました（EOF で確認してください）。");
        }
        String line = next;
        try {
            next = reader.readLine();
        } catch (IOException e) {
            throw error("ファイルを読めません：" + path + "（" + e.getMessage() + "）");
        }
        return line;
    }

    /** ReadLine：1 行（テキスト・CSV）。 */
    public String readLine() {
        if (book != null) {
            return ScriptLib.str(ScriptLib.item(read(), 0));
        }
        return nextLine();
    }

    /** Read：1 行の値（CSV は区切り文字で分ける。Excel は次の行のセル。テキストは 1 行）。 */
    public List<Object> read() {
        if (book != null) {
            List<Object> values = new ArrayList<>();
            Row row = sheet.getRow(readRow++);
            if (row != null) {
                for (int c = 0; c < row.getLastCellNum(); c++) {
                    values.add(value(row.getCell(c)));
                }
            }
            return values;
        }
        String line = nextLine();
        if (!kind().equals("csv")) {
            return new ArrayList<>(List.of(line));
        }
        char sep = separator.equals("tab") ? '\t' : ',';
        List<Object> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        boolean quoted = false;
        while (true) {
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                if (quoted) {
                    if (ch == '"' && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        value.append('"');
                        i++;
                    } else if (ch == '"') {
                        quoted = false;
                    } else {
                        value.append(ch);
                    }
                } else if (ch == '"') {
                    quoted = true;
                } else if (ch == sep) {
                    values.add(value.toString());
                    value.setLength(0);
                } else {
                    value.append(ch);
                }
            }
            // "…" の中の改行：次の行に続く
            if (quoted && next != null) {
                value.append('\n');
                line = nextLine();
                continue;
            }
            break;
        }
        values.add(value.toString());
        return values;
    }

    // セルの値：数値は BigDecimal、Boolean、日付は LocalDateTime、それ以外は文字列（空のセルは null）
    private static Object value(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType type = cell.getCellType() == CellType.FORMULA ? cell.getCachedFormulaResultType() : cell.getCellType();
        return switch (type) {
            case NUMERIC -> DateUtil.isCellDateFormatted(cell)
                    ? cell.getLocalDateTimeCellValue()
                    : new BigDecimal(String.valueOf(cell.getNumericCellValue())).stripTrailingZeros();
            case BOOLEAN -> cell.getBooleanCellValue();
            case STRING -> cell.getStringCellValue();
            case BLANK -> null;
            default -> cell.toString();
        };
    }

    // ===== 書く =====

    private String text(Object value) {
        if (value instanceof java.sql.Timestamp t) {
            value = t.toLocalDateTime();
        }
        if (value instanceof LocalDateTime dt) {
            return DateTimeFormatter.ofPattern(dateFormat + " HH:mm:ss").format(dt);
        }
        if (value instanceof Temporal t) {
            return DateTimeFormatter.ofPattern(dateFormat).format(t);
        }
        return ScriptLib.str(value);
    }

    private BufferedWriter out() {
        if (writer == null) {
            throw error("ファイルを書くモード（Open(パス, Output) / Open(パス, Append)）で開いていません。");
        }
        return writer;
    }

    /** WriteLine：1 行書く（Excel は次の行の最初のセル）。 */
    public void writeLine(Object value) {
        if (book != null) {
            write(new Object[] { value });
            return;
        }
        try {
            out().write(text(value));
            out().newLine();
        } catch (IOException e) {
            throw error("ファイルに書けません：" + path + "（" + e.getMessage() + "）");
        }
    }

    /** Write：1 行の値（CSV は数値・Boolean 以外を "…" で囲み、区切り文字でつなぐ。Excel は最後の行の次の行に書く）。 */
    public void write(Object[] values) {
        if (book != null) {
            int row = getRowCount() + 1;
            for (int i = 0; i < values.length; i++) {
                setCell(row, i + 1, values[i]);
            }
            return;
        }
        String sep = separator.equals("tab") ? "\t" : ",";
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                line.append(sep);
            }
            // 数値・Boolean はそのまま、それ以外（文字列・日付・null）は "…" で囲む（値の中の " は ""。null は ""）
            Object v = values[i];
            if (v instanceof Number || v instanceof Boolean) {
                line.append(text(v));
            } else {
                line.append('"').append(text(v).replace("\"", "\"\"")).append('"');
            }
        }
        writeLine(line.toString());
    }

    // ===== Excel =====

    private Sheet excel() {
        if (book == null) {
            throw error("Excel ファイル（xlsx / xlsm）を開いていません。");
        }
        return sheet;
    }

    /** Sheet("シート名")：シートを選ぶ（ない場合は作る）。 */
    public void sheet(String name) {
        excel();
        Sheet found = book.getSheet(name);
        sheet = found != null ? found : book.createSheet(name);
        readRow = 0;
    }

    private Cell cellAt(int row, int column, boolean create) {
        if (row < 1 || column < 1) {
            throw error("行・列は 1 から指定してください（行 " + row + "、列 " + column + "）。");
        }
        Row r = excel().getRow(row - 1);
        if (r == null) {
            if (!create) {
                return null;
            }
            r = sheet.createRow(row - 1);
        }
        Cell c = r.getCell(column - 1);
        return c == null && create ? r.createCell(column - 1) : c;
    }

    /** Cell(行, 列)：セルの値（数値・Boolean・日付はその型、それ以外は文字列。空のセルは null）。 */
    public Object cell(int row, int column) {
        return value(cellAt(row, column, false));
    }

    /** SetCell(行, 列, 値)：数値・Boolean はその型、日付は DateFormat の文字列、それ以外は文字列。 */
    public void setCell(int row, int column, Object value) {
        Cell c = cellAt(row, column, true);
        if (value == null) {
            c.setBlank();
        } else if (value instanceof Number n) {
            c.setCellValue(n instanceof BigDecimal d ? d.doubleValue() : n.doubleValue());
        } else if (value instanceof Boolean b) {
            c.setCellValue(b);
        } else {
            c.setCellValue(text(value));
        }
    }

    // ===== アップロード・ダウンロード・圧縮 =====

    /** Upload(フォルダー)：画面に配置した File（アップロードの部品）で送られたファイルをフォルダーに保存し、保存したパスを返す（ファイル名は送られたファイルの名前）。 */
    public String upload(String folder) {
        if (item == null) {
            throw new ScriptLib.ScriptException("Upload(フォルダー) は画面に配置した File で使います（配置していない File は Upload(項目名, フォルダー) と書きます）。");
        }
        return upload(item, folder, null);
    }

    /**
     * 画面に配置した File：Upload(フォルダー, ファイル名)（送られたファイルを名前を指定して保存する）。
     * 配置していない File：Upload(項目名, フォルダー)（以前の書き方）。
     */
    public String upload(String first, String second) {
        return item != null ? upload(item, first, second) : upload(first, second, null);
    }

    /** 画面のアップロード部品（name="_file_項目名"）で送られたファイル（空のものを除く。複数選択の場合は選んだ順）。 */
    static List<MultipartFile> uploadedFiles(String item) {
        if (!(currentRequest() instanceof MultipartHttpServletRequest multipart)) {
            return List.of();
        }
        return multipart.getFiles("_file_" + item).stream().filter(f -> f != null && !f.isEmpty()).toList();
    }

    // この File で受け取るファイル（File の配列の要素は index 番目、それ以外は最初のもの）。送られていない場合は null
    private MultipartFile uploaded(String item) {
        List<MultipartFile> files = uploadedFiles(item);
        int i = index >= 0 && item.equals(this.item) ? index : 0;
        return i < files.size() ? files.get(i) : null;
    }

    /** FileName：画面に配置した File（アップロードの部品）で送られたファイルの名前（送られていない場合は空文字）。 */
    public String getFileName() {
        MultipartFile file = item == null ? null : uploaded(item);
        return file == null ? "" : Paths.get(String.valueOf(file.getOriginalFilename())).getFileName().toString();
    }

    /**
     * Upload(項目名, フォルダー[, ファイル名])：画面のアップロード部品（name="_file_項目名"）で送られたファイルをフォルダーに保存し、保存したパスを返す。
     * ファイル名を省略した場合は送られたファイルの名前。ファイルが送られていない場合は空文字。
     */
    public String upload(String item, String folder, String fileName) {
        MultipartFile file = uploaded(item);
        if (file == null) {
            return "";
        }
        String name = fileName == null || fileName.isEmpty() ? Paths.get(String.valueOf(file.getOriginalFilename())).getFileName().toString() : fileName;
        try {
            Path dir = Paths.get(folder);
            Files.createDirectories(dir);
            Path target = dir.resolve(name).normalize();
            if (!target.startsWith(dir.normalize())) {
                throw new ScriptLib.ScriptException("Upload：ファイル名が正しくありません：" + name);
            }
            try (InputStream in = file.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }
            return target.toString();
        } catch (IOException ex) {
            throw new ScriptLib.ScriptException("ファイルを保存できません：" + name + "（" + ex.getMessage() + "）");
        }
    }

    /** Download(パス[, ファイル名])：ファイルをブラウザーにダウンロードさせる（ファイル名を省略した場合はパスのファイル名）。 */
    public void download(String path, String fileName) {
        Path source = Paths.get(path);
        if (!Files.isRegularFile(source)) {
            throw new ScriptLib.ScriptException("ダウンロードするファイルがありません：" + path);
        }
        String name = fileName == null || fileName.isEmpty() ? source.getFileName().toString() : fileName;
        HttpServletResponse response = startDownload(name, contentType(name));
        try (OutputStream out = response.getOutputStream()) {
            response.setContentLengthLong(Files.size(source));
            Files.copy(source, out);
        } catch (IOException ex) {
            throw new ScriptLib.ScriptException("ダウンロードできません：" + path + "（" + ex.getMessage() + "）");
        }
    }

    /** Zip(圧縮ファイルのパス, パス, ...)：ファイル・フォルダー（中のファイルすべて）を zip に圧縮する。 */
    public void zip(String zipPath, String... paths) {
        try {
            Path target = Paths.get(zipPath);
            if (target.getParent() != null) {
                Files.createDirectories(target.getParent());
            }
            try (OutputStream out = Files.newOutputStream(target)) {
                writeZip(out, paths);
            }
        } catch (IOException ex) {
            throw new ScriptLib.ScriptException("圧縮できません：" + zipPath + "（" + ex.getMessage() + "）");
        }
    }

    /** Unzip(圧縮ファイルのパス, フォルダー)：zip をフォルダーに展開する。 */
    public void unzip(String zipPath, String folder) {
        Path dir = Paths.get(folder).toAbsolutePath().normalize();
        try (ZipInputStream in = new ZipInputStream(Files.newInputStream(Paths.get(zipPath)), StandardCharsets.UTF_8)) {
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                Path target = dir.resolve(entry.getName()).normalize();
                if (!target.startsWith(dir)) {
                    throw new ScriptLib.ScriptException("Unzip：正しくないパスがあります：" + entry.getName());
                }
                if (entry.isDirectory()) {
                    Files.createDirectories(target);
                } else {
                    Files.createDirectories(target.getParent());
                    Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException ex) {
            throw new ScriptLib.ScriptException("展開できません：" + zipPath + "（" + ex.getMessage() + "）");
        }
    }

    /** ZipDownload(ファイル名, パス, ...)：ファイル・フォルダーを zip に圧縮してブラウザーにダウンロードさせる（ファイルは作らない）。 */
    public void zipDownload(String fileName, String... paths) {
        String name = fileName.toLowerCase().endsWith(".zip") ? fileName : fileName + ".zip";
        HttpServletResponse response = startDownload(name, "application/zip");
        try (OutputStream out = response.getOutputStream()) {
            writeZip(out, paths);
        } catch (IOException ex) {
            throw new ScriptLib.ScriptException("ダウンロードできません：" + name + "（" + ex.getMessage() + "）");
        }
    }

    // パス（ファイル・フォルダー）を zip に書く。フォルダーはそのフォルダー名から下の相対パスで入れる
    private static void writeZip(OutputStream out, String... paths) throws IOException {
        try (ZipOutputStream zip = new ZipOutputStream(out, StandardCharsets.UTF_8)) {
            for (String p : paths) {
                Path source = Paths.get(p);
                if (!Files.exists(source)) {
                    throw new ScriptLib.ScriptException("圧縮するファイルがありません：" + p);
                }
                Path base = source.toAbsolutePath().getParent();
                List<Path> files;
                try (var walk = Files.walk(source)) {
                    files = walk.filter(Files::isRegularFile).toList();
                }
                for (Path file : files) {
                    String entry = base == null ? file.getFileName().toString() : base.relativize(file.toAbsolutePath()).toString().replace('\\', '/');
                    zip.putNextEntry(new ZipEntry(entry));
                    Files.copy(file, zip);
                    zip.closeEntry();
                }
            }
        }
    }

    // ダウンロードの応答を始める（画面は Controller が応答済みとして扱い、画面を表示しない）
    private static HttpServletResponse startDownload(String fileName, String contentType) {
        if (!(RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) || attributes.getResponse() == null) {
            throw new ScriptLib.ScriptException("ダウンロードは画面の処理の中でだけ使えます。");
        }
        HttpServletResponse response = attributes.getResponse();
        response.reset();
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", ContentDisposition.attachment().filename(fileName, StandardCharsets.UTF_8).build().toString());
        return response;
    }

    private static HttpServletRequest currentRequest() {
        return RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes ? attributes.getRequest() : null;
    }

    private static String contentType(String fileName) {
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".csv")) {
            return "text/csv";
        }
        if (lower.endsWith(".txt")) {
            return "text/plain";
        }
        if (lower.endsWith(".xlsx") || lower.endsWith(".xlsm")) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        if (lower.endsWith(".zip")) {
            return "application/zip";
        }
        if (lower.endsWith(".pdf")) {
            return "application/pdf";
        }
        return "application/octet-stream";
    }
}
