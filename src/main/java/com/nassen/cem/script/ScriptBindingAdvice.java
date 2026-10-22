package com.nassen.cem.script;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 送信の値の変換（自動生成 / auto-generated）。手動編集は再生成で失われます。
 *
 * <p>java.sql.Timestamp（秒の小数部のある日時の列）：送信の値（datetime-local の 2026-10-18T20:25:46・2026-10-18T20:25、
 * 日付だけの 2026-10-18 は 0 時）を Timestamp に、画面の表示は datetime-local の書き方（2026-10-18T20:25:46、ミリ秒がある場合は .123）にする。</p>
 */
@ControllerAdvice
public class ScriptBindingAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Timestamp.class, new TimestampEditor());
    }

    /** Timestamp と datetime-local の文字列の変換。 */
    static final class TimestampEditor extends PropertyEditorSupport {

        private static final DateTimeFormatter SECONDS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        private static final DateTimeFormatter MILLIS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        @Override
        public void setAsText(String text) {
            if (text == null || text.isBlank()) {
                setValue(null);
                return;
            }
            String value = text.trim().replace(' ', 'T');
            try {
                LocalDateTime dateTime = value.contains("T")
                    ? LocalDateTime.parse(value)
                    : LocalDate.parse(value).atStartOfDay();
                setValue(Timestamp.valueOf(dateTime));
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("日時として読めません：" + text, ex);
            }
        }

        @Override
        public String getAsText() {
            if (!(getValue() instanceof Timestamp timestamp)) {
                return "";
            }
            LocalDateTime dateTime = timestamp.toLocalDateTime().truncatedTo(ChronoUnit.MILLIS);
            return dateTime.getNano() == 0 ? SECONDS.format(dateTime) : MILLIS.format(dateTime);
        }
    }
}
