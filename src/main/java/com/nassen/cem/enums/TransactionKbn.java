package com.nassen.cem.enums;

/**
 * 取引区分（自動生成 / auto-generated）。
 *
 * <p>データ型: VARCHAR(2)</p>
 *
 * <p>型定義（Data/enums.json）から生成されました。手動編集は再生成で失われます。</p>
 */
public enum TransactionKbn {

    /** 輸入 */
    IMPORT("01", "輸入"),
    /** 輸出 */
    EXPORT("02", "輸出");

    /** 値（画面・DB で使う値）。 */
    private final String value;

    /** 表示名。 */
    private final String label;

    TransactionKbn(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() { return value; }

    public String getLabel() { return label; }

    /** 値と表示名（例: 01:得意先）。プルダウン等の表示用。 */
    public String getShowLabel() { return value + ":" + label; }

    /** 値から定数を取得する（該当なしの場合は null）。 */
    public static TransactionKbn fromValue(String value) {
        for (TransactionKbn v : values()) {
            if (v.value.equals(value)) {
                return v;
            }
        }
        return null;
    }

    /** JPA 用のコンバータ（DB の値 ⇔ 定数）。Entity では @Convert(converter = TransactionKbn.JpaConverter.class) で使う。 */
    @jakarta.persistence.Converter
    public static class JpaConverter implements jakarta.persistence.AttributeConverter<TransactionKbn, String> {

        @Override
        public String convertToDatabaseColumn(TransactionKbn attribute) {
            return attribute == null ? null : attribute.value;
        }

        @Override
        public TransactionKbn convertToEntityAttribute(String dbData) {
            return dbData == null ? null : fromValue(dbData);
        }
    }
}
