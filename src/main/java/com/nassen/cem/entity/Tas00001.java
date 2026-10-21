package com.nassen.cem.entity;

import com.nassen.cem.enums.DelFlg;
import com.nassen.cem.enums.TransactionKbn;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;
import org.hibernate.annotations.DynamicInsert;

/**
 * 見積テーブル（TAS00001）の Entity（自動生成 / auto-generated）。
 *
 * <p>表定義（Data/tables.json）から生成されました。登録では値を設定していない列（null）を INSERT に含めない（DB の既定値にする）。
 * 手動編集は再生成で失われます。</p>
 */
@Entity
@DynamicInsert
@Table(name = "TAS00001")
@IdClass(Tas00001.PK.class)
public class Tas00001 {

    /** 見積番号（主キー 1） */
    @Id
    @Column(name = "TAS00001_EstimateCD", nullable = false, length = 10)
    private String tas00001EstimateCD;

    /** 取引区分 */
    @Convert(converter = TransactionKbn.JpaConverter.class)
    @Column(name = "TAS00001_TransactionKbn", length = 2)
    private TransactionKbn tas00001TransactionKbn;

    /** 見積日 */
    @Column(name = "TAS00001_EstimateDate")
    private LocalDate tas00001EstimateDate;

    /** 担当者コード */
    @Column(name = "TAS00001_UserCD", length = 10)
    private String tas00001UserCD;

    /** 得意先コード */
    @Column(name = "TAS00001_CustomerCD", length = 10)
    private String tas00001CustomerCD;

    /** 取引条件コード */
    @Column(name = "TAS00001_TradeTermsCD", length = 20)
    private String tas00001TradeTermsCD;

    /** 支払条件コード */
    @Column(name = "TAS00001_PaymentTermsCD", length = 20)
    private String tas00001PaymentTermsCD;

    /** メーカーコード */
    @Column(name = "TAS00001_MakerCD", length = 10)
    private String tas00001MakerCD;

    /** 仕向地コード */
    @Column(name = "TAS00001_ToPlaceCD", length = 20)
    private String tas00001ToPlaceCD;

    /** 納入場所 */
    @Column(name = "TAS00001_DeliveryLocation", length = 200)
    private String tas00001DeliveryLocation;

    /** 原産地域コード */
    @Column(name = "TAS00001_RegionOfOriginCD", length = 20)
    private String tas00001RegionOfOriginCD;

    /** 見積有効期限 */
    @Column(name = "TAS00001_ExpirationDate")
    private LocalDate tas00001ExpirationDate;

    /** 使用時期 */
    @Column(name = "TAS00001_UsagePeriod", length = 20)
    private String tas00001UsagePeriod;

    /** 保険コード */
    @Column(name = "TAS00001_InsuranceCD", length = 10)
    private String tas00001InsuranceCD;

    /** 通貨 */
    @Column(name = "TAS00001_Currency", length = 20)
    private String tas00001Currency;

    /** レート */
    @Column(name = "TAS00001_Rate", precision = 5, scale = 2)
    private BigDecimal tas00001Rate;

    /** 取引銀行コード */
    @Column(name = "TAS00001_OwnBankCode", length = 10)
    private String tas00001OwnBankCode;

    /** 消費税端数処理区分（主キー 2） */
    @Id
    @Column(name = "TAS00001_TaxRound", nullable = false, length = 2)
    private String tas00001TaxRound;

    /** 消費税税率 */
    @Column(name = "TAS00001_TaxRate", precision = 5, scale = 2)
    private BigDecimal tas00001TaxRate;

    /** 金額 */
    @Column(name = "TAS00001_Amount", precision = 18, scale = 3)
    private BigDecimal tas00001Amount;

    /** 消費税金額 */
    @Column(name = "TAS00001_TaxAmount", precision = 18, scale = 3)
    private BigDecimal tas00001TaxAmount;

    /** 円貨換算金額 */
    @Column(name = "TAS00001_YenCurrencyAmount", precision = 18, scale = 3)
    private BigDecimal tas00001YenCurrencyAmount;

    /** 円貨換算消費税金額 */
    @Column(name = "TAS00001_YenCurrencyTaxAmount", precision = 18, scale = 3)
    private BigDecimal tas00001YenCurrencyTaxAmount;

    /** 備考 */
    @Column(name = "TAS00001_Remarks", length = 200)
    private String tas00001Remarks;

    /** 見積ステータス */
    @Column(name = "TAS00001_EstimateStatus", length = 2)
    private String tas00001EstimateStatus;

    /** 承認不要フラグ */
    @Column(name = "TAS00001_AgreementNoneedFlg", length = 1)
    private String tas00001AgreementNoneedFlg;

    /** 差戻し日 */
    @Column(name = "TAS00001_FbDate")
    private LocalDate tas00001FbDate;

    /** 差戻しコメント */
    @Column(name = "TAS00001_FbComment", length = 200)
    private String tas00001FbComment;

    /** 申請日 */
    @Column(name = "TAS00001_RequestDate")
    private LocalDate tas00001RequestDate;

    /** 承認日 */
    @Column(name = "TAS00001_AgreementDate")
    private LocalDate tas00001AgreementDate;

    /** 承認者 */
    @Column(name = "TAS00001_Authorizer", length = 10)
    private String tas00001Authorizer;

    /** 最終承認日 */
    @Column(name = "TAS00001_FinalAgreementDate")
    private LocalDate tas00001FinalAgreementDate;

    /** 最終承認者 */
    @Column(name = "TAS00001_FinalAuthorizer", length = 10)
    private String tas00001FinalAuthorizer;

    /** 削除フラグ。既定値: 0 */
    @Convert(converter = DelFlg.JpaConverter.class)
    @Column(name = "TAS00001_DelFlg", length = 1)
    private DelFlg tas00001DelFlg;

    /** 作成者 */
    @Column(name = "TAS00001_CreateUserCD", length = 10)
    private String tas00001CreateUserCD;

    /** 作成日時。既定値: Sysdate */
    @Column(name = "TAS00001_CreateDate")
    private Timestamp tas00001CreateDate;

    /** 更新者 */
    @Column(name = "TAS00001_UpdateUserCD", length = 10)
    private String tas00001UpdateUserCD;

    /** 更新日時。既定値: Sysdate */
    @Column(name = "TAS00001_UpdateDate")
    private Timestamp tas00001UpdateDate;

    /** For Each 表 Into で付けた参照の表の行（別名 → 行）。DB には保存しない。 */
    @Transient
    private java.util.Map<String, Object> scriptRefs = new java.util.HashMap<>();

    public String getTas00001EstimateCD() { return tas00001EstimateCD; }

    public void setTas00001EstimateCD(String tas00001EstimateCD) { this.tas00001EstimateCD = tas00001EstimateCD; }

    public TransactionKbn getTas00001TransactionKbn() { return tas00001TransactionKbn; }

    public void setTas00001TransactionKbn(TransactionKbn tas00001TransactionKbn) { this.tas00001TransactionKbn = tas00001TransactionKbn; }

    public LocalDate getTas00001EstimateDate() { return tas00001EstimateDate; }

    public void setTas00001EstimateDate(LocalDate tas00001EstimateDate) { this.tas00001EstimateDate = tas00001EstimateDate; }

    public String getTas00001UserCD() { return tas00001UserCD; }

    public void setTas00001UserCD(String tas00001UserCD) { this.tas00001UserCD = tas00001UserCD; }

    public String getTas00001CustomerCD() { return tas00001CustomerCD; }

    public void setTas00001CustomerCD(String tas00001CustomerCD) { this.tas00001CustomerCD = tas00001CustomerCD; }

    public String getTas00001TradeTermsCD() { return tas00001TradeTermsCD; }

    public void setTas00001TradeTermsCD(String tas00001TradeTermsCD) { this.tas00001TradeTermsCD = tas00001TradeTermsCD; }

    public String getTas00001PaymentTermsCD() { return tas00001PaymentTermsCD; }

    public void setTas00001PaymentTermsCD(String tas00001PaymentTermsCD) { this.tas00001PaymentTermsCD = tas00001PaymentTermsCD; }

    public String getTas00001MakerCD() { return tas00001MakerCD; }

    public void setTas00001MakerCD(String tas00001MakerCD) { this.tas00001MakerCD = tas00001MakerCD; }

    public String getTas00001ToPlaceCD() { return tas00001ToPlaceCD; }

    public void setTas00001ToPlaceCD(String tas00001ToPlaceCD) { this.tas00001ToPlaceCD = tas00001ToPlaceCD; }

    public String getTas00001DeliveryLocation() { return tas00001DeliveryLocation; }

    public void setTas00001DeliveryLocation(String tas00001DeliveryLocation) { this.tas00001DeliveryLocation = tas00001DeliveryLocation; }

    public String getTas00001RegionOfOriginCD() { return tas00001RegionOfOriginCD; }

    public void setTas00001RegionOfOriginCD(String tas00001RegionOfOriginCD) { this.tas00001RegionOfOriginCD = tas00001RegionOfOriginCD; }

    public LocalDate getTas00001ExpirationDate() { return tas00001ExpirationDate; }

    public void setTas00001ExpirationDate(LocalDate tas00001ExpirationDate) { this.tas00001ExpirationDate = tas00001ExpirationDate; }

    public String getTas00001UsagePeriod() { return tas00001UsagePeriod; }

    public void setTas00001UsagePeriod(String tas00001UsagePeriod) { this.tas00001UsagePeriod = tas00001UsagePeriod; }

    public String getTas00001InsuranceCD() { return tas00001InsuranceCD; }

    public void setTas00001InsuranceCD(String tas00001InsuranceCD) { this.tas00001InsuranceCD = tas00001InsuranceCD; }

    public String getTas00001Currency() { return tas00001Currency; }

    public void setTas00001Currency(String tas00001Currency) { this.tas00001Currency = tas00001Currency; }

    public BigDecimal getTas00001Rate() { return tas00001Rate; }

    public void setTas00001Rate(BigDecimal tas00001Rate) { this.tas00001Rate = tas00001Rate; }

    public String getTas00001OwnBankCode() { return tas00001OwnBankCode; }

    public void setTas00001OwnBankCode(String tas00001OwnBankCode) { this.tas00001OwnBankCode = tas00001OwnBankCode; }

    public String getTas00001TaxRound() { return tas00001TaxRound; }

    public void setTas00001TaxRound(String tas00001TaxRound) { this.tas00001TaxRound = tas00001TaxRound; }

    public BigDecimal getTas00001TaxRate() { return tas00001TaxRate; }

    public void setTas00001TaxRate(BigDecimal tas00001TaxRate) { this.tas00001TaxRate = tas00001TaxRate; }

    public BigDecimal getTas00001Amount() { return tas00001Amount; }

    public void setTas00001Amount(BigDecimal tas00001Amount) { this.tas00001Amount = tas00001Amount; }

    public BigDecimal getTas00001TaxAmount() { return tas00001TaxAmount; }

    public void setTas00001TaxAmount(BigDecimal tas00001TaxAmount) { this.tas00001TaxAmount = tas00001TaxAmount; }

    public BigDecimal getTas00001YenCurrencyAmount() { return tas00001YenCurrencyAmount; }

    public void setTas00001YenCurrencyAmount(BigDecimal tas00001YenCurrencyAmount) { this.tas00001YenCurrencyAmount = tas00001YenCurrencyAmount; }

    public BigDecimal getTas00001YenCurrencyTaxAmount() { return tas00001YenCurrencyTaxAmount; }

    public void setTas00001YenCurrencyTaxAmount(BigDecimal tas00001YenCurrencyTaxAmount) { this.tas00001YenCurrencyTaxAmount = tas00001YenCurrencyTaxAmount; }

    public String getTas00001Remarks() { return tas00001Remarks; }

    public void setTas00001Remarks(String tas00001Remarks) { this.tas00001Remarks = tas00001Remarks; }

    public String getTas00001EstimateStatus() { return tas00001EstimateStatus; }

    public void setTas00001EstimateStatus(String tas00001EstimateStatus) { this.tas00001EstimateStatus = tas00001EstimateStatus; }

    public String getTas00001AgreementNoneedFlg() { return tas00001AgreementNoneedFlg; }

    public void setTas00001AgreementNoneedFlg(String tas00001AgreementNoneedFlg) { this.tas00001AgreementNoneedFlg = tas00001AgreementNoneedFlg; }

    public LocalDate getTas00001FbDate() { return tas00001FbDate; }

    public void setTas00001FbDate(LocalDate tas00001FbDate) { this.tas00001FbDate = tas00001FbDate; }

    public String getTas00001FbComment() { return tas00001FbComment; }

    public void setTas00001FbComment(String tas00001FbComment) { this.tas00001FbComment = tas00001FbComment; }

    public LocalDate getTas00001RequestDate() { return tas00001RequestDate; }

    public void setTas00001RequestDate(LocalDate tas00001RequestDate) { this.tas00001RequestDate = tas00001RequestDate; }

    public LocalDate getTas00001AgreementDate() { return tas00001AgreementDate; }

    public void setTas00001AgreementDate(LocalDate tas00001AgreementDate) { this.tas00001AgreementDate = tas00001AgreementDate; }

    public String getTas00001Authorizer() { return tas00001Authorizer; }

    public void setTas00001Authorizer(String tas00001Authorizer) { this.tas00001Authorizer = tas00001Authorizer; }

    public LocalDate getTas00001FinalAgreementDate() { return tas00001FinalAgreementDate; }

    public void setTas00001FinalAgreementDate(LocalDate tas00001FinalAgreementDate) { this.tas00001FinalAgreementDate = tas00001FinalAgreementDate; }

    public String getTas00001FinalAuthorizer() { return tas00001FinalAuthorizer; }

    public void setTas00001FinalAuthorizer(String tas00001FinalAuthorizer) { this.tas00001FinalAuthorizer = tas00001FinalAuthorizer; }

    public DelFlg getTas00001DelFlg() { return tas00001DelFlg; }

    public void setTas00001DelFlg(DelFlg tas00001DelFlg) { this.tas00001DelFlg = tas00001DelFlg; }

    public String getTas00001CreateUserCD() { return tas00001CreateUserCD; }

    public void setTas00001CreateUserCD(String tas00001CreateUserCD) { this.tas00001CreateUserCD = tas00001CreateUserCD; }

    public Timestamp getTas00001CreateDate() { return tas00001CreateDate; }

    public void setTas00001CreateDate(Timestamp tas00001CreateDate) { this.tas00001CreateDate = tas00001CreateDate; }

    public String getTas00001UpdateUserCD() { return tas00001UpdateUserCD; }

    public void setTas00001UpdateUserCD(String tas00001UpdateUserCD) { this.tas00001UpdateUserCD = tas00001UpdateUserCD; }

    public Timestamp getTas00001UpdateDate() { return tas00001UpdateDate; }

    public void setTas00001UpdateDate(Timestamp tas00001UpdateDate) { this.tas00001UpdateDate = tas00001UpdateDate; }

    public java.util.Map<String, Object> getScriptRefs() { return scriptRefs; }

    /** 複合主キー（見積番号・消費税端数処理区分）。 */
    public static class PK implements Serializable {

        private static final long serialVersionUID = 1L;

        private String tas00001EstimateCD;

        private String tas00001TaxRound;

        public PK() {
        }

        public PK(String tas00001EstimateCD, String tas00001TaxRound) {
            this.tas00001EstimateCD = tas00001EstimateCD;
            this.tas00001TaxRound = tas00001TaxRound;
        }

        public String getTas00001EstimateCD() { return tas00001EstimateCD; }

        public void setTas00001EstimateCD(String tas00001EstimateCD) { this.tas00001EstimateCD = tas00001EstimateCD; }

        public String getTas00001TaxRound() { return tas00001TaxRound; }

        public void setTas00001TaxRound(String tas00001TaxRound) { this.tas00001TaxRound = tas00001TaxRound; }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof PK other)) {
                return false;
            }
            return Objects.equals(tas00001EstimateCD, other.tas00001EstimateCD)
                && Objects.equals(tas00001TaxRound, other.tas00001TaxRound);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tas00001EstimateCD, tas00001TaxRound);
        }
    }
}
