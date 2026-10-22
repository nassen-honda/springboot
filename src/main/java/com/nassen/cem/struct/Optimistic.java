package com.nassen.cem.struct;

import java.sql.Timestamp;

/**
 * 楽観排他の情報（OptimisticCheck）（Optimistic）の構造体（自動生成 / auto-generated）。
 *
 * <p>構造体ファイル（*.struct.json）から生成されました。手動編集は再生成で失われます。</p>
 */
public class Optimistic {

    /** 表の物理名（TableName、VARCHAR(30)） */
    private String tableName = "";

    /** 主キーの列 1（KeyColumn1、VARCHAR(50)） */
    private String keyColumn1 = "";

    /** 主キーの値 1（KeyValue1、VARCHAR(255)） */
    private String keyValue1 = "";

    /** 主キーの列 2（KeyColumn2、VARCHAR(50)） */
    private String keyColumn2 = "";

    /** 主キーの値 2（KeyValue2、VARCHAR(255)） */
    private String keyValue2 = "";

    /** 主キーの列 3（KeyColumn3、VARCHAR(50)） */
    private String keyColumn3 = "";

    /** 主キーの値 3（KeyValue3、VARCHAR(255)） */
    private String keyValue3 = "";

    /** 主キーの列 4（KeyColumn4、VARCHAR(50)） */
    private String keyColumn4 = "";

    /** 主キーの値 4（KeyValue4、VARCHAR(255)） */
    private String keyValue4 = "";

    /** 主キーの列 5（KeyColumn5、VARCHAR(50)） */
    private String keyColumn5 = "";

    /** 主キーの値 5（KeyValue5、VARCHAR(255)） */
    private String keyValue5 = "";

    /** 主キーの列 6（KeyColumn6、VARCHAR(50)） */
    private String keyColumn6 = "";

    /** 主キーの値 6（KeyValue6、VARCHAR(255)） */
    private String keyValue6 = "";

    /** 主キーの列 7（KeyColumn7、VARCHAR(50)） */
    private String keyColumn7 = "";

    /** 主キーの値 7（KeyValue7、VARCHAR(255)） */
    private String keyValue7 = "";

    /** 主キーの列 8（KeyColumn8、VARCHAR(50)） */
    private String keyColumn8 = "";

    /** 主キーの値 8（KeyValue8、VARCHAR(255)） */
    private String keyValue8 = "";

    /** 主キーの列 9（KeyColumn9、VARCHAR(50)） */
    private String keyColumn9 = "";

    /** 主キーの値 9（KeyValue9、VARCHAR(255)） */
    private String keyValue9 = "";

    /** 主キーの列 10（KeyColumn10、VARCHAR(50)） */
    private String keyColumn10 = "";

    /** 主キーの値 10（KeyValue10、VARCHAR(255)） */
    private String keyValue10 = "";

    /** 更新日時の列（UpdateColumn、VARCHAR(50)） */
    private String updateColumn = "";

    /** 更新者の列（任意）（UpdateUserColumn、VARCHAR(50)） */
    private String updateUserColumn = "";

    /** 読んだときの更新日時（ReadDate、DATETIME(3)） */
    private Timestamp readDate;

    /** 今の更新日時（OptimisticCheck が入れる）（CurrentDate、DATETIME(3)） */
    private Timestamp currentDate;

    /** 今の更新者（OptimisticCheck が入れる）（CurrentUser、VARCHAR(50)） */
    private String currentUser = "";

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? "" : tableName;
    }

    public String getKeyColumn1() {
        return keyColumn1;
    }

    public void setKeyColumn1(String keyColumn1) {
        this.keyColumn1 = keyColumn1 == null ? "" : keyColumn1;
    }

    public String getKeyValue1() {
        return keyValue1;
    }

    public void setKeyValue1(String keyValue1) {
        this.keyValue1 = keyValue1 == null ? "" : keyValue1;
    }

    public String getKeyColumn2() {
        return keyColumn2;
    }

    public void setKeyColumn2(String keyColumn2) {
        this.keyColumn2 = keyColumn2 == null ? "" : keyColumn2;
    }

    public String getKeyValue2() {
        return keyValue2;
    }

    public void setKeyValue2(String keyValue2) {
        this.keyValue2 = keyValue2 == null ? "" : keyValue2;
    }

    public String getKeyColumn3() {
        return keyColumn3;
    }

    public void setKeyColumn3(String keyColumn3) {
        this.keyColumn3 = keyColumn3 == null ? "" : keyColumn3;
    }

    public String getKeyValue3() {
        return keyValue3;
    }

    public void setKeyValue3(String keyValue3) {
        this.keyValue3 = keyValue3 == null ? "" : keyValue3;
    }

    public String getKeyColumn4() {
        return keyColumn4;
    }

    public void setKeyColumn4(String keyColumn4) {
        this.keyColumn4 = keyColumn4 == null ? "" : keyColumn4;
    }

    public String getKeyValue4() {
        return keyValue4;
    }

    public void setKeyValue4(String keyValue4) {
        this.keyValue4 = keyValue4 == null ? "" : keyValue4;
    }

    public String getKeyColumn5() {
        return keyColumn5;
    }

    public void setKeyColumn5(String keyColumn5) {
        this.keyColumn5 = keyColumn5 == null ? "" : keyColumn5;
    }

    public String getKeyValue5() {
        return keyValue5;
    }

    public void setKeyValue5(String keyValue5) {
        this.keyValue5 = keyValue5 == null ? "" : keyValue5;
    }

    public String getKeyColumn6() {
        return keyColumn6;
    }

    public void setKeyColumn6(String keyColumn6) {
        this.keyColumn6 = keyColumn6 == null ? "" : keyColumn6;
    }

    public String getKeyValue6() {
        return keyValue6;
    }

    public void setKeyValue6(String keyValue6) {
        this.keyValue6 = keyValue6 == null ? "" : keyValue6;
    }

    public String getKeyColumn7() {
        return keyColumn7;
    }

    public void setKeyColumn7(String keyColumn7) {
        this.keyColumn7 = keyColumn7 == null ? "" : keyColumn7;
    }

    public String getKeyValue7() {
        return keyValue7;
    }

    public void setKeyValue7(String keyValue7) {
        this.keyValue7 = keyValue7 == null ? "" : keyValue7;
    }

    public String getKeyColumn8() {
        return keyColumn8;
    }

    public void setKeyColumn8(String keyColumn8) {
        this.keyColumn8 = keyColumn8 == null ? "" : keyColumn8;
    }

    public String getKeyValue8() {
        return keyValue8;
    }

    public void setKeyValue8(String keyValue8) {
        this.keyValue8 = keyValue8 == null ? "" : keyValue8;
    }

    public String getKeyColumn9() {
        return keyColumn9;
    }

    public void setKeyColumn9(String keyColumn9) {
        this.keyColumn9 = keyColumn9 == null ? "" : keyColumn9;
    }

    public String getKeyValue9() {
        return keyValue9;
    }

    public void setKeyValue9(String keyValue9) {
        this.keyValue9 = keyValue9 == null ? "" : keyValue9;
    }

    public String getKeyColumn10() {
        return keyColumn10;
    }

    public void setKeyColumn10(String keyColumn10) {
        this.keyColumn10 = keyColumn10 == null ? "" : keyColumn10;
    }

    public String getKeyValue10() {
        return keyValue10;
    }

    public void setKeyValue10(String keyValue10) {
        this.keyValue10 = keyValue10 == null ? "" : keyValue10;
    }

    public String getUpdateColumn() {
        return updateColumn;
    }

    public void setUpdateColumn(String updateColumn) {
        this.updateColumn = updateColumn == null ? "" : updateColumn;
    }

    public String getUpdateUserColumn() {
        return updateUserColumn;
    }

    public void setUpdateUserColumn(String updateUserColumn) {
        this.updateUserColumn = updateUserColumn == null ? "" : updateUserColumn;
    }

    public Timestamp getReadDate() {
        return readDate;
    }

    public void setReadDate(Timestamp readDate) {
        this.readDate = readDate;
    }

    public Timestamp getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Timestamp currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser == null ? "" : currentUser;
    }
}
