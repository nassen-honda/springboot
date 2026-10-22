package com.nassen.cem.entity;

import com.nassen.cem.enums.DelFlg;
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
import java.util.Objects;
import org.hibernate.annotations.DynamicInsert;

/**
 * 見積明細テーブル（TAS00002）の Entity（自動生成 / auto-generated）。
 *
 * <p>表定義（Data/tables.json）から生成されました。登録では値を設定していない列（null）を INSERT に含めない（DB の既定値にする）。
 * 手動編集は再生成で失われます。</p>
 */
@Entity
@DynamicInsert
@Table(name = "TAS00002")
@IdClass(Tas00002.PK.class)
public class Tas00002 {

    /** 見積番号（主キー 1） */
    @Id
    @Column(name = "TAS00002_EstimateCD", nullable = false, length = 10)
    private String tas00002EstimateCD;

    /** 商品コード（主キー 2） */
    @Id
    @Column(name = "TAS00002_ItemCD", nullable = false, length = 10)
    private String tas00002ItemCD;

    /** 見積数量 */
    @Column(name = "TAS00002_EstimateQuantity", precision = 12, scale = 2)
    private BigDecimal tas00002EstimateQuantity;

    /** 単価②単位数量 */
    @Column(name = "TAS00002_ConversionUnitQuantity", precision = 12, scale = 2)
    private BigDecimal tas00002ConversionUnitQuantity;

    /** 見積単価 */
    @Column(name = "TAS00002_EstimateUnitPrice", precision = 12, scale = 2)
    private BigDecimal tas00002EstimateUnitPrice;

    /** 単位 */
    @Column(name = "TAS00002_Unit", length = 20)
    private String tas00002Unit;

    /** 金額 */
    @Column(name = "TAS00002_Amount", precision = 18, scale = 3)
    private BigDecimal tas00002Amount;

    /** 消費税金額 */
    @Column(name = "TAS00002_TaxAmount", precision = 18, scale = 3)
    private BigDecimal tas00002TaxAmount;

    /** 円貨換算金額 */
    @Column(name = "TAS00002_YenCurrencyAmount", precision = 18, scale = 3)
    private BigDecimal tas00002YenCurrencyAmount;

    /** 円貨換算消費税金額 */
    @Column(name = "TAS00002_YenCurrencyTaxAmount", precision = 18, scale = 3)
    private BigDecimal tas00002YenCurrencyTaxAmount;

    /** 印字用数量 */
    @Column(name = "TAS00002_ShowUnitQuantity", precision = 12, scale = 2)
    private BigDecimal tas00002ShowUnitQuantity;

    /** 印字用単価 */
    @Column(name = "TAS00002_ShowUnitPrice", precision = 12, scale = 2)
    private BigDecimal tas00002ShowUnitPrice;

    /** 印字用単位 */
    @Column(name = "TAS00002_ShowUnit", length = 20)
    private String tas00002ShowUnit;

    /** 備考 */
    @Column(name = "TAS00002_Remarks", length = 200)
    private String tas00002Remarks;

    /** 削除フラグ。既定値: 0 */
    @Convert(converter = DelFlg.JpaConverter.class)
    @Column(name = "TAS00002_DelFlg", length = 1)
    private DelFlg tas00002DelFlg;

    /** 作成者 */
    @Column(name = "TAS00002_CreateUserCD", length = 10)
    private String tas00002CreateUserCD;

    /** 作成日時。既定値: Sysdate */
    @Column(name = "TAS00002_CreateDate")
    private Timestamp tas00002CreateDate;

    /** 更新者 */
    @Column(name = "TAS00002_UpdateUserCD", length = 10)
    private String tas00002UpdateUserCD;

    /** 更新日時。既定値: Sysdate */
    @Column(name = "TAS00002_UpdateDate")
    private Timestamp tas00002UpdateDate;

    /** For Each 表 Into で付けた参照の表の行（別名 → 行）。DB には保存しない。 */
    @Transient
    private java.util.Map<String, Object> scriptRefs = new java.util.HashMap<>();

    public String getTas00002EstimateCD() { return tas00002EstimateCD; }

    public void setTas00002EstimateCD(String tas00002EstimateCD) { this.tas00002EstimateCD = tas00002EstimateCD; }

    public String getTas00002ItemCD() { return tas00002ItemCD; }

    public void setTas00002ItemCD(String tas00002ItemCD) { this.tas00002ItemCD = tas00002ItemCD; }

    public BigDecimal getTas00002EstimateQuantity() { return tas00002EstimateQuantity; }

    public void setTas00002EstimateQuantity(BigDecimal tas00002EstimateQuantity) { this.tas00002EstimateQuantity = tas00002EstimateQuantity; }

    public BigDecimal getTas00002ConversionUnitQuantity() { return tas00002ConversionUnitQuantity; }

    public void setTas00002ConversionUnitQuantity(BigDecimal tas00002ConversionUnitQuantity) { this.tas00002ConversionUnitQuantity = tas00002ConversionUnitQuantity; }

    public BigDecimal getTas00002EstimateUnitPrice() { return tas00002EstimateUnitPrice; }

    public void setTas00002EstimateUnitPrice(BigDecimal tas00002EstimateUnitPrice) { this.tas00002EstimateUnitPrice = tas00002EstimateUnitPrice; }

    public String getTas00002Unit() { return tas00002Unit; }

    public void setTas00002Unit(String tas00002Unit) { this.tas00002Unit = tas00002Unit; }

    public BigDecimal getTas00002Amount() { return tas00002Amount; }

    public void setTas00002Amount(BigDecimal tas00002Amount) { this.tas00002Amount = tas00002Amount; }

    public BigDecimal getTas00002TaxAmount() { return tas00002TaxAmount; }

    public void setTas00002TaxAmount(BigDecimal tas00002TaxAmount) { this.tas00002TaxAmount = tas00002TaxAmount; }

    public BigDecimal getTas00002YenCurrencyAmount() { return tas00002YenCurrencyAmount; }

    public void setTas00002YenCurrencyAmount(BigDecimal tas00002YenCurrencyAmount) { this.tas00002YenCurrencyAmount = tas00002YenCurrencyAmount; }

    public BigDecimal getTas00002YenCurrencyTaxAmount() { return tas00002YenCurrencyTaxAmount; }

    public void setTas00002YenCurrencyTaxAmount(BigDecimal tas00002YenCurrencyTaxAmount) { this.tas00002YenCurrencyTaxAmount = tas00002YenCurrencyTaxAmount; }

    public BigDecimal getTas00002ShowUnitQuantity() { return tas00002ShowUnitQuantity; }

    public void setTas00002ShowUnitQuantity(BigDecimal tas00002ShowUnitQuantity) { this.tas00002ShowUnitQuantity = tas00002ShowUnitQuantity; }

    public BigDecimal getTas00002ShowUnitPrice() { return tas00002ShowUnitPrice; }

    public void setTas00002ShowUnitPrice(BigDecimal tas00002ShowUnitPrice) { this.tas00002ShowUnitPrice = tas00002ShowUnitPrice; }

    public String getTas00002ShowUnit() { return tas00002ShowUnit; }

    public void setTas00002ShowUnit(String tas00002ShowUnit) { this.tas00002ShowUnit = tas00002ShowUnit; }

    public String getTas00002Remarks() { return tas00002Remarks; }

    public void setTas00002Remarks(String tas00002Remarks) { this.tas00002Remarks = tas00002Remarks; }

    public DelFlg getTas00002DelFlg() { return tas00002DelFlg; }

    public void setTas00002DelFlg(DelFlg tas00002DelFlg) { this.tas00002DelFlg = tas00002DelFlg; }

    public String getTas00002CreateUserCD() { return tas00002CreateUserCD; }

    public void setTas00002CreateUserCD(String tas00002CreateUserCD) { this.tas00002CreateUserCD = tas00002CreateUserCD; }

    public Timestamp getTas00002CreateDate() { return tas00002CreateDate; }

    public void setTas00002CreateDate(Timestamp tas00002CreateDate) { this.tas00002CreateDate = tas00002CreateDate; }

    public String getTas00002UpdateUserCD() { return tas00002UpdateUserCD; }

    public void setTas00002UpdateUserCD(String tas00002UpdateUserCD) { this.tas00002UpdateUserCD = tas00002UpdateUserCD; }

    public Timestamp getTas00002UpdateDate() { return tas00002UpdateDate; }

    public void setTas00002UpdateDate(Timestamp tas00002UpdateDate) { this.tas00002UpdateDate = tas00002UpdateDate; }

    public java.util.Map<String, Object> getScriptRefs() { return scriptRefs; }

    /** 複合主キー（見積番号・商品コード）。 */
    public static class PK implements Serializable {

        private static final long serialVersionUID = 1L;

        private String tas00002EstimateCD;

        private String tas00002ItemCD;

        public PK() {
        }

        public PK(String tas00002EstimateCD, String tas00002ItemCD) {
            this.tas00002EstimateCD = tas00002EstimateCD;
            this.tas00002ItemCD = tas00002ItemCD;
        }

        public String getTas00002EstimateCD() { return tas00002EstimateCD; }

        public void setTas00002EstimateCD(String tas00002EstimateCD) { this.tas00002EstimateCD = tas00002EstimateCD; }

        public String getTas00002ItemCD() { return tas00002ItemCD; }

        public void setTas00002ItemCD(String tas00002ItemCD) { this.tas00002ItemCD = tas00002ItemCD; }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof PK other)) {
                return false;
            }
            return Objects.equals(tas00002EstimateCD, other.tas00002EstimateCD)
                && Objects.equals(tas00002ItemCD, other.tas00002ItemCD);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tas00002EstimateCD, tas00002ItemCD);
        }
    }
}
