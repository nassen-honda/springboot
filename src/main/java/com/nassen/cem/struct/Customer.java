package com.nassen.cem.struct;

/**
 * 取引先構造体（Customer）の構造体（自動生成 / auto-generated）。
 *
 * <p>構造体ファイル（*.struct.json）から生成されました。手動編集は再生成で失われます。</p>
 */
public class Customer {

    /** 取引先コード（CustomerCD、VARCHAR(10)） */
    private String customerCD = "";

    /** 取引先名（CustomerNM、VARCHAR(100)） */
    private String customerNM = "";

    /** 取引先略称（CustomerOmission、VARCHAR(100)） */
    private String customerOmission = "";

    public String getCustomerCD() {
        return customerCD;
    }

    public void setCustomerCD(String customerCD) {
        this.customerCD = customerCD == null ? "" : customerCD;
    }

    public String getCustomerNM() {
        return customerNM;
    }

    public void setCustomerNM(String customerNM) {
        this.customerNM = customerNM == null ? "" : customerNM;
    }

    public String getCustomerOmission() {
        return customerOmission;
    }

    public void setCustomerOmission(String customerOmission) {
        this.customerOmission = customerOmission == null ? "" : customerOmission;
    }
}
