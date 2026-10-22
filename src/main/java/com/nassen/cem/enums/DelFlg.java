package com.nassen.cem.enums;

/**
 * 削除フラグ（自動生成 / auto-generated）。
 *
 * <p>データ型: VARCHAR(1)</p>
 *
 * <p>型定義（Data/enums.json）から生成されました。手動編集は再生成で失われます。</p>
 */
public enum DelFlg {

    /** 輸入 */
    ACT("0", "輸入"),
    /** 輸出 */
    DEL("1", "輸出");

    /** 値（画面・DB で使う値）。 */
    private final String value;

    /** 表示名。 */
    private final String label;

    DelFlg(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() { return value; }

    public String getLabel() { return label; }

    /** 値と表示名（例: 01:得意先）。プルダウン等の表示用。 */
    public String getShowLabel() { return value + ":" + label; }

    /** 値から定数を取得する（該当なしの場合は null）。 */
    public static DelFlg fromValue(String value) {
        for (DelFlg v : values()) {
            if (v.value.equals(value)) {
                return v;
            }
        }
        return null;
    }

    /** JPA 用のコンバータ（DB の値 ⇔ 定数）。Entity では @Convert(converter = DelFlg.JpaConverter.class) で使う。 */
    @jakarta.persistence.Converter
    public static class JpaConverter implements jakarta.persistence.AttributeConverter<DelFlg, String> {

        @Override
        public String convertToDatabaseColumn(DelFlg attribute) {
            return attribute == null ? null : attribute.value;
        }

        @Override
        public DelFlg convertToEntityAttribute(String dbData) {
            return dbData == null ? null : fromValue(dbData);
        }
    }
}
