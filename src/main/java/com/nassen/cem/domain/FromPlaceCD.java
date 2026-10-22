package com.nassen.cem.domain;

/**
 * 発地コード（VARCHAR(20)）のドメイン（自動生成 / auto-generated）。
 *
 * <p>データ型を定数で持ち、{@link #check(Object)} / {@link #isValid(Object)} で値がデータ型に合っているかを検査する。
 * 例: {@code FromPlaceCD.check(value)}</p>
 *
 * <p>型定義（Data/project/type.json）から生成されました。手動編集は再生成で失われます。</p>
 */
public final class FromPlaceCD {

    /** 物理名。 */
    public static final String PHYSICAL_NAME = "FromPlaceCD";

    /** 論理名。 */
    public static final String LOGICAL_NAME = "発地コード";

    /** データ型。 */
    public static final String DATA_TYPE = "VARCHAR(20)";

    /** 型名。 */
    public static final String TYPE = "VARCHAR";

    /** 長さ（DECIMAL は全体の桁数）。指定がない型は null。 */
    public static final Integer LENGTH = 20;

    /** 小数桁。指定がない型は null。 */
    public static final Integer SCALE = null;

    private FromPlaceCD() {
    }

    /**
     * 値がデータ型に合っているかを検査する。
     *
     * @param value 検査する値（null は検査しない。必須チェックは別途行う）
     * @return 合っていない場合はエラーメッセージ、合っている場合は null
     */
    public static String check(Object value) {
        return DomainChecks.checkString(LOGICAL_NAME, value, LENGTH);
    }

    /** 値がデータ型に合っているか。 */
    public static boolean isValid(Object value) {
        return check(value) == null;
    }
}
