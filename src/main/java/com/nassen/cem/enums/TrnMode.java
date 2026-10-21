package com.nassen.cem.enums;

/**
 * 画面モード（自動生成 / auto-generated）。
 *
 * <p>データ型: VARCHAR(50)</p>
 *
 * <p>型定義（Data/enums.json）から生成されました。手動編集は再生成で失われます。</p>
 */
public enum TrnMode {

    /** 新規 */
    NEW("NEW", "新規"),
    /** 詳細 */
    VIEW("VIEW", "詳細"),
    /** 編集 */
    EDIT("EDIT", "編集"),
    /** 複製 */
    COPY("COPY", "複製"),
    /** 削除 */
    DEL("DEL", "削除"),
    /** 承認 */
    APPROVE("APPROVE", "承認");

    /** 値（画面・DB で使う値）。 */
    private final String value;

    /** 表示名。 */
    private final String label;

    TrnMode(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() { return value; }

    public String getLabel() { return label; }

    /** 値と表示名（例: 01:得意先）。プルダウン等の表示用。 */
    public String getShowLabel() { return value + ":" + label; }

    /** 値から定数を取得する（該当なしの場合は null）。 */
    public static TrnMode fromValue(String value) {
        for (TrnMode v : values()) {
            if (v.value.equals(value)) {
                return v;
            }
        }
        return null;
    }

    /** JPA 用のコンバータ（DB の値 ⇔ 定数）。Entity では @Convert(converter = TrnMode.JpaConverter.class) で使う。 */
    @jakarta.persistence.Converter
    public static class JpaConverter implements jakarta.persistence.AttributeConverter<TrnMode, String> {

        @Override
        public String convertToDatabaseColumn(TrnMode attribute) {
            return attribute == null ? null : attribute.value;
        }

        @Override
        public TrnMode convertToEntityAttribute(String dbData) {
            return dbData == null ? null : fromValue(dbData);
        }
    }
}
