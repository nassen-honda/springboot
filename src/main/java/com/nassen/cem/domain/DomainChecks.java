package com.nassen.cem.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

/**
 * ドメインのクラスが使う、値がデータ型に合っているかの検査（自動生成 / auto-generated）。
 *
 * <p>各メソッドは、合っていない場合はエラーメッセージを、合っている場合（値が null の場合を含む）は null を返す。
 * 文字列の長さは文字数で数える。</p>
 *
 * <p>型定義（Data/project/type.json）から生成されました。手動編集は再生成で失われます。</p>
 */
public final class DomainChecks {

    private DomainChecks() {
    }

    /** 文字列（CHAR / VARCHAR）：length 文字以内。 */
    public static String checkString(String name, Object value, Integer length) {
        if (value == null) {
            return null;
        }
        String text = value.toString();
        int count = text.codePointCount(0, text.length());
        if (length != null && count > length) {
            return name + "：" + length + " 文字以内で入力してください（" + count + " 文字）。";
        }
        return null;
    }

    /** 数値（DECIMAL(length, scale)）：整数部 length - scale 桁以内、小数部 scale 桁以内。 */
    public static String checkDecimal(String name, Object value, Integer length, Integer scale) {
        if (value == null) {
            return null;
        }
        BigDecimal number = toBigDecimal(value);
        if (number == null) {
            return name + "：数値で入力してください。";
        }
        number = number.stripTrailingZeros();
        int fractionDigits = Math.max(number.scale(), 0);
        int integerDigits = Math.max(number.precision() - number.scale(), 0);
        int maxFraction = scale == null ? 0 : scale;
        if (fractionDigits > maxFraction) {
            return name + "：小数は " + maxFraction + " 桁以内で入力してください。";
        }
        if (length != null && integerDigits > length - maxFraction) {
            return name + "：整数部は " + (length - maxFraction) + " 桁以内で入力してください。";
        }
        return null;
    }

    /** 整数（INTEGER / BIGINT など）。 */
    public static String checkInteger(String name, Object value) {
        if (value == null) {
            return null;
        }
        BigDecimal number = toBigDecimal(value);
        if (number == null || number.stripTrailingZeros().scale() > 0) {
            return name + "：整数で入力してください。";
        }
        return null;
    }

    /** 日付（DATE）：LocalDate、または yyyy-MM-dd。 */
    public static String checkDate(String name, Object value) {
        if (value == null || value instanceof LocalDate) {
            return null;
        }
        try {
            LocalDate.parse(value.toString());
            return null;
        } catch (DateTimeParseException e) {
            return name + "：日付（yyyy-MM-dd）で入力してください。";
        }
    }

    /** 日時（TIMESTAMP）：LocalDateTime、または yyyy-MM-ddTHH:mm:ss。 */
    public static String checkTimestamp(String name, Object value) {
        if (value == null || value instanceof LocalDateTime) {
            return null;
        }
        try {
            LocalDateTime.parse(value.toString());
            return null;
        } catch (DateTimeParseException e) {
            return name + "：日時（yyyy-MM-ddTHH:mm:ss）で入力してください。";
        }
    }

    /** 列挙：enum の定数、または enum の値（value）。 */
    public static String checkEnum(String name, Object value, String enumName, Class<?> enumClass, Predicate<String> isEnumValue) {
        if (value == null || enumClass.isInstance(value) || isEnumValue.test(value.toString())) {
            return null;
        }
        return name + "：「" + value + "」は " + enumName + " の値ではありません。";
    }

    private static BigDecimal toBigDecimal(Object value) {
        if (value instanceof BigDecimal number) {
            return number;
        }
        if (value instanceof Number number) {
            return new BigDecimal(number.toString());
        }
        try {
            return new BigDecimal(value.toString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}