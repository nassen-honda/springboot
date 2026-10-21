package com.nassen.cem.entity;

import com.nassen.cem.enums.CustomerKbn;
import com.nassen.cem.enums.DelFlg;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.sql.Timestamp;
import org.hibernate.annotations.DynamicInsert;

/**
 * 取引先マスタ（MST00004）の Entity（自動生成 / auto-generated）。
 *
 * <p>表定義（Data/tables.json）から生成されました。登録では値を設定していない列（null）を INSERT に含めない（DB の既定値にする）。
 * 手動編集は再生成で失われます。</p>
 */
@Entity
@DynamicInsert
@Table(name = "MST00004")
public class Mst00004 {

    /** 取引先コード（主キー 1） */
    @Id
    @Column(name = "MST00004_CustomerCD", nullable = false, length = 10)
    private String mst00004CustomerCD;

    /** 取引先請求コード */
    @Column(name = "MST00004_CustomerInvoiceCD", length = 10)
    private String mst00004CustomerInvoiceCD;

    /** 取引先区分 */
    @Convert(converter = CustomerKbn.JpaConverter.class)
    @Column(name = "MST00004_CustomerKbn", length = 2)
    private CustomerKbn mst00004CustomerKbn;

    /** 取引先名 */
    @Column(name = "MST00004_CustomerNM", length = 100)
    private String mst00004CustomerNM;

    /** 取引先略称 */
    @Column(name = "MST00004_CustomerOmission", length = 100)
    private String mst00004CustomerOmission;

    /** 地域コード */
    @Column(name = "MST00004_AreaCD", length = 20)
    private String mst00004AreaCD;

    /** 住所1 */
    @Column(name = "MST00004_Address1", length = 100)
    private String mst00004Address1;

    /** 住所2 */
    @Column(name = "MST00004_Address2", length = 100)
    private String mst00004Address2;

    /** 住所3 */
    @Column(name = "MST00004_Address3", length = 100)
    private String mst00004Address3;

    /** 電話番号 */
    @Column(name = "MST00004_Tel", length = 20)
    private String mst00004Tel;

    /** FAX */
    @Column(name = "MST00004_Fax", length = 20)
    private String mst00004Fax;

    /** 郵便番号 */
    @Column(name = "MST00004_Zip", length = 200)
    private String mst00004Zip;

    /** 発地コード */
    @Column(name = "MST00004_FromPlaceCD", length = 20)
    private String mst00004FromPlaceCD;

    /** 仕向地コード */
    @Column(name = "MST00004_ToPlaceCD", length = 20)
    private String mst00004ToPlaceCD;

    /** 支払条件コード */
    @Column(name = "MST00004_PaymentTermsCD", length = 20)
    private String mst00004PaymentTermsCD;

    /** 納入場所 */
    @Column(name = "MST00004_DeliveryLocation", length = 200)
    private String mst00004DeliveryLocation;

    /** 取引条件コード */
    @Column(name = "MST00004_TradeTermsCD", length = 20)
    private String mst00004TradeTermsCD;

    /** 通貨 */
    @Column(name = "MST00004_Currency", length = 20)
    private String mst00004Currency;

    /** 部署名 */
    @Column(name = "MST00004_SectionNM", length = 200)
    private String mst00004SectionNM;

    /** 担当者名 */
    @Column(name = "MST00004_UserCD", length = 10)
    private String mst00004UserCD;

    /** メールアドレス */
    @Column(name = "MST00004_MailAddress", length = 50)
    private String mst00004MailAddress;

    /** 消費税端数処理区分。01:切り捨て,02:切り上げ,03:四捨五入。既定値: 01 */
    @Column(name = "MST00004_TaxRoundKBN", length = 2)
    private String mst00004TaxRoundKBN;

    /** 税額通知区分。01:通知、02:非通知。既定値: 01 */
    @Column(name = "MST00004_TaxCalcKBN", length = 2)
    private String mst00004TaxCalcKBN;

    /** 請求書作成フラグ。0:なし、1:作成。既定値: 0 */
    @Column(name = "MST00004_InvoiceCreateFlg", length = 1)
    private String mst00004InvoiceCreateFlg;

    /** 削除フラグ。既定値: 0 */
    @Convert(converter = DelFlg.JpaConverter.class)
    @Column(name = "MST00004_DelFlg", length = 1)
    private DelFlg mst00004DelFlg;

    /** 作成者 */
    @Column(name = "MST00004_CreateUserCD", length = 10)
    private String mst00004CreateUserCD;

    /** 作成日時。既定値: Sysdate */
    @Column(name = "MST00004_CreateDate")
    private Timestamp mst00004CreateDate;

    /** 更新者 */
    @Column(name = "MST00004_UpdateUserCD", length = 10)
    private String mst00004UpdateUserCD;

    /** 更新日時。既定値: Sysdate */
    @Column(name = "MST00004_UpdateDate")
    private Timestamp mst00004UpdateDate;

    /** For Each 表 Into で付けた参照の表の行（別名 → 行）。DB には保存しない。 */
    @Transient
    private java.util.Map<String, Object> scriptRefs = new java.util.HashMap<>();

    public String getMst00004CustomerCD() { return mst00004CustomerCD; }

    public void setMst00004CustomerCD(String mst00004CustomerCD) { this.mst00004CustomerCD = mst00004CustomerCD; }

    public String getMst00004CustomerInvoiceCD() { return mst00004CustomerInvoiceCD; }

    public void setMst00004CustomerInvoiceCD(String mst00004CustomerInvoiceCD) { this.mst00004CustomerInvoiceCD = mst00004CustomerInvoiceCD; }

    public CustomerKbn getMst00004CustomerKbn() { return mst00004CustomerKbn; }

    public void setMst00004CustomerKbn(CustomerKbn mst00004CustomerKbn) { this.mst00004CustomerKbn = mst00004CustomerKbn; }

    public String getMst00004CustomerNM() { return mst00004CustomerNM; }

    public void setMst00004CustomerNM(String mst00004CustomerNM) { this.mst00004CustomerNM = mst00004CustomerNM; }

    public String getMst00004CustomerOmission() { return mst00004CustomerOmission; }

    public void setMst00004CustomerOmission(String mst00004CustomerOmission) { this.mst00004CustomerOmission = mst00004CustomerOmission; }

    public String getMst00004AreaCD() { return mst00004AreaCD; }

    public void setMst00004AreaCD(String mst00004AreaCD) { this.mst00004AreaCD = mst00004AreaCD; }

    public String getMst00004Address1() { return mst00004Address1; }

    public void setMst00004Address1(String mst00004Address1) { this.mst00004Address1 = mst00004Address1; }

    public String getMst00004Address2() { return mst00004Address2; }

    public void setMst00004Address2(String mst00004Address2) { this.mst00004Address2 = mst00004Address2; }

    public String getMst00004Address3() { return mst00004Address3; }

    public void setMst00004Address3(String mst00004Address3) { this.mst00004Address3 = mst00004Address3; }

    public String getMst00004Tel() { return mst00004Tel; }

    public void setMst00004Tel(String mst00004Tel) { this.mst00004Tel = mst00004Tel; }

    public String getMst00004Fax() { return mst00004Fax; }

    public void setMst00004Fax(String mst00004Fax) { this.mst00004Fax = mst00004Fax; }

    public String getMst00004Zip() { return mst00004Zip; }

    public void setMst00004Zip(String mst00004Zip) { this.mst00004Zip = mst00004Zip; }

    public String getMst00004FromPlaceCD() { return mst00004FromPlaceCD; }

    public void setMst00004FromPlaceCD(String mst00004FromPlaceCD) { this.mst00004FromPlaceCD = mst00004FromPlaceCD; }

    public String getMst00004ToPlaceCD() { return mst00004ToPlaceCD; }

    public void setMst00004ToPlaceCD(String mst00004ToPlaceCD) { this.mst00004ToPlaceCD = mst00004ToPlaceCD; }

    public String getMst00004PaymentTermsCD() { return mst00004PaymentTermsCD; }

    public void setMst00004PaymentTermsCD(String mst00004PaymentTermsCD) { this.mst00004PaymentTermsCD = mst00004PaymentTermsCD; }

    public String getMst00004DeliveryLocation() { return mst00004DeliveryLocation; }

    public void setMst00004DeliveryLocation(String mst00004DeliveryLocation) { this.mst00004DeliveryLocation = mst00004DeliveryLocation; }

    public String getMst00004TradeTermsCD() { return mst00004TradeTermsCD; }

    public void setMst00004TradeTermsCD(String mst00004TradeTermsCD) { this.mst00004TradeTermsCD = mst00004TradeTermsCD; }

    public String getMst00004Currency() { return mst00004Currency; }

    public void setMst00004Currency(String mst00004Currency) { this.mst00004Currency = mst00004Currency; }

    public String getMst00004SectionNM() { return mst00004SectionNM; }

    public void setMst00004SectionNM(String mst00004SectionNM) { this.mst00004SectionNM = mst00004SectionNM; }

    public String getMst00004UserCD() { return mst00004UserCD; }

    public void setMst00004UserCD(String mst00004UserCD) { this.mst00004UserCD = mst00004UserCD; }

    public String getMst00004MailAddress() { return mst00004MailAddress; }

    public void setMst00004MailAddress(String mst00004MailAddress) { this.mst00004MailAddress = mst00004MailAddress; }

    public String getMst00004TaxRoundKBN() { return mst00004TaxRoundKBN; }

    public void setMst00004TaxRoundKBN(String mst00004TaxRoundKBN) { this.mst00004TaxRoundKBN = mst00004TaxRoundKBN; }

    public String getMst00004TaxCalcKBN() { return mst00004TaxCalcKBN; }

    public void setMst00004TaxCalcKBN(String mst00004TaxCalcKBN) { this.mst00004TaxCalcKBN = mst00004TaxCalcKBN; }

    public String getMst00004InvoiceCreateFlg() { return mst00004InvoiceCreateFlg; }

    public void setMst00004InvoiceCreateFlg(String mst00004InvoiceCreateFlg) { this.mst00004InvoiceCreateFlg = mst00004InvoiceCreateFlg; }

    public DelFlg getMst00004DelFlg() { return mst00004DelFlg; }

    public void setMst00004DelFlg(DelFlg mst00004DelFlg) { this.mst00004DelFlg = mst00004DelFlg; }

    public String getMst00004CreateUserCD() { return mst00004CreateUserCD; }

    public void setMst00004CreateUserCD(String mst00004CreateUserCD) { this.mst00004CreateUserCD = mst00004CreateUserCD; }

    public Timestamp getMst00004CreateDate() { return mst00004CreateDate; }

    public void setMst00004CreateDate(Timestamp mst00004CreateDate) { this.mst00004CreateDate = mst00004CreateDate; }

    public String getMst00004UpdateUserCD() { return mst00004UpdateUserCD; }

    public void setMst00004UpdateUserCD(String mst00004UpdateUserCD) { this.mst00004UpdateUserCD = mst00004UpdateUserCD; }

    public Timestamp getMst00004UpdateDate() { return mst00004UpdateDate; }

    public void setMst00004UpdateDate(Timestamp mst00004UpdateDate) { this.mst00004UpdateDate = mst00004UpdateDate; }

    public java.util.Map<String, Object> getScriptRefs() { return scriptRefs; }
}
