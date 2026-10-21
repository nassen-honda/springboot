package com.nassen.cem.columntype;

import com.nassen.cem.domain.DomainChecks;

/**
 * 列 仕向地コード（VARCHAR(20)）の型（自動生成 / auto-generated）。
 *
 * <p>データ型を定数で持ち、{@link #check(Object)} / {@link #isValid(Object)} で値がデータ型に合っているかを検査する。
 * 例: {@code TAS00001_ToPlaceCD.check(value)}</p>
 *
 * <p>表定義（Data/tables.json、Data/ColumnTypes.json）から生成されました。手動編集は再生成で失われます。</p>
 */
public final class TAS00001_ToPlaceCD {

    /** 物理名。 */
    public static final String PHYSICAL_NAME = "TAS00001_ToPlaceCD";

    /** 論理名。 */
    public static final String LOGICAL_NAME = "仕向地コード";

    /** データ型。 */
    public static final String DATA_TYPE = "VARCHAR(20)";

    /** 型名。 */
    public static final String TYPE = "VARCHAR";

    /** 長さ（DECIMAL は全体の桁数）。指定がない型は null。 */
    public static final Integer LENGTH = 20;

    /** 小数桁。指定がない型は null。 */
    public static final Integer SCALE = null;

    /** 列の型（タイプの型の物理名。ColumnTypes.json の type）。指定がない場合は null。 */
    public static final String BASE_TYPE = "ToPlaceCD";

    private TAS00001_ToPlaceCD() {
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
