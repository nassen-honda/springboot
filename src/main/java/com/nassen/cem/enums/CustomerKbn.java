package com.nassen.cem.enums;

/**
 * 取引先区分（自動生成 / auto-generated）。
 *
 * <p>データ型: VARCHAR(2)</p>
 *
 * <p>型定義（Data/enums.json）から生成されました。手動編集は再生成で失われます。</p>
 */
public enum CustomerKbn {

    /** 得意先 */
    CUSTOMER("01", "得意先"),
    /** 仕入先 */
    VENDOR("02", "仕入先"),
    /** 業者 */
    PARTNER("03", "業者");

    /** 値（画面・DB で使う値）。 */
    private final String value;

    /** 表示名。 */
    private final String label;

    CustomerKbn(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() { return value; }

    public String getLabel() { return label; }

    /** 値と表示名（例: 01:得意先）。プルダウン等の表示用。 */
    public String getShowLabel() { return value + ":" + label; }

    /** 値から定数を取得する（該当なしの場合は null）。 */
    public static CustomerKbn fromValue(String value) {
        for (CustomerKbn v : values()) {
            if (v.value.equals(value)) {
                return v;
            }
        }
        return null;
    }

    /** JPA 用のコンバータ（DB の値 ⇔ 定数）。Entity では @Convert(converter = CustomerKbn.JpaConverter.class) で使う。 */
    @jakarta.persistence.Converter
    public static class JpaConverter implements jakarta.persistence.AttributeConverter<CustomerKbn, String> {

        @Override
        public String convertToDatabaseColumn(CustomerKbn attribute) {
            return attribute == null ? null : attribute.value;
        }

        @Override
        public CustomerKbn convertToEntityAttribute(String dbData) {
            return dbData == null ? null : fromValue(dbData);
        }
    }
}
