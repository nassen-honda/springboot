package com.nassen.cem.domain;

/**
 * 画面モード（VARCHAR(50)）のドメイン（自動生成 / auto-generated）。
 *
 * <p>データ型を定数で持ち、{@link #check(Object)} / {@link #isValid(Object)} で値がデータ型に合っているかを検査する。
 * 例: {@code TrnMode.check(value)}</p>
 * <p>列挙の型：値は {@link com.nassen.cem.enums.TrnMode} の値に限る。</p>
 *
 * <p>型定義（Data/project/type.json）から生成されました。手動編集は再生成で失われます。</p>
 */
public final class TrnMode {

    /** 物理名。 */
    public static final String PHYSICAL_NAME = "TrnMode";

    /** 論理名。 */
    public static final String LOGICAL_NAME = "画面モード";

    /** データ型。 */
    public static final String DATA_TYPE = "VARCHAR(50)";

    /** 型名。 */
    public static final String TYPE = "VARCHAR";

    /** 長さ（DECIMAL は全体の桁数）。指定がない型は null。 */
    public static final Integer LENGTH = 50;

    /** 小数桁。指定がない型は null。 */
    public static final Integer SCALE = null;

    /** 列挙の型の enum クラス。 */
    public static final Class<com.nassen.cem.enums.TrnMode> ENUM_CLASS = com.nassen.cem.enums.TrnMode.class;

    private TrnMode() {
    }

    /**
     * 値がデータ型に合っているかを検査する。
     *
     * @param value 検査する値（null は検査しない。必須チェックは別途行う）
     * @return 合っていない場合はエラーメッセージ、合っている場合は null
     */
    public static String check(Object value) {
        return DomainChecks.checkEnum(LOGICAL_NAME, value, "TrnMode", ENUM_CLASS, v -> com.nassen.cem.enums.TrnMode.fromValue(v) != null);
    }

    /** 値がデータ型に合っているか。 */
    public static boolean isValid(Object value) {
        return check(value) == null;
    }
}
