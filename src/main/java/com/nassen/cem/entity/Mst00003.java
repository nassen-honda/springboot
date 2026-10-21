package com.nassen.cem.entity;

import com.nassen.cem.enums.DelFlg;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Timestamp;
import org.hibernate.annotations.DynamicInsert;

/**
 * 商品マスタ（MST00003）の Entity（自動生成 / auto-generated）。
 *
 * <p>表定義（Data/tables.json）から生成されました。登録では値を設定していない列（null）を INSERT に含めない（DB の既定値にする）。
 * 手動編集は再生成で失われます。</p>
 */
@Entity
@DynamicInsert
@Table(name = "MST00003")
public class Mst00003 {

    /** 商品コード（主キー 1） */
    @Id
    @Column(name = "MST00003_ItemCD", nullable = false, length = 10)
    private String mst00003ItemCD;

    /** 商品名 */
    @Column(name = "MST00003_ItemNM", length = 200)
    private String mst00003ItemNM;

    /** メーカーコード */
    @Column(name = "MST00003_MakerCD", length = 10)
    private String mst00003MakerCD;

    /** 品名 */
    @Column(name = "MST00003_Item", length = 200)
    private String mst00003Item;

    /** 品番 */
    @Column(name = "MST00003_ItemType", length = 50)
    private String mst00003ItemType;

    /** 重量 */
    @Column(name = "MST00003_NetWeight", precision = 17, scale = 2)
    private BigDecimal mst00003NetWeight;

    /** 在庫管理単位 */
    @Column(name = "MST00003_StockUnit", length = 20)
    private String mst00003StockUnit;

    /** 備考 */
    @Column(name = "MST00003_Remarks", length = 200)
    private String mst00003Remarks;

    /** 削除フラグ。既定値: 0 */
    @Convert(converter = DelFlg.JpaConverter.class)
    @Column(name = "MST00003_DelFlg", length = 1)
    private DelFlg mst00003DelFlg;

    /** 作成者 */
    @Column(name = "MST00003_CreateUserCD", length = 10)
    private String mst00003CreateUserCD;

    /** 作成日時。既定値: Sysdate */
    @Column(name = "MST00003_CreateDate")
    private Timestamp mst00003CreateDate;

    /** 更新者 */
    @Column(name = "MST00003_UpdateUserCD", length = 10)
    private String mst00003UpdateUserCD;

    /** 更新日時。既定値: Sysdate */
    @Column(name = "MST00003_UpdateDate")
    private Timestamp mst00003UpdateDate;

    /** For Each 表 Into で付けた参照の表の行（別名 → 行）。DB には保存しない。 */
    @Transient
    private java.util.Map<String, Object> scriptRefs = new java.util.HashMap<>();

    public String getMst00003ItemCD() { return mst00003ItemCD; }

    public void setMst00003ItemCD(String mst00003ItemCD) { this.mst00003ItemCD = mst00003ItemCD; }

    public String getMst00003ItemNM() { return mst00003ItemNM; }

    public void setMst00003ItemNM(String mst00003ItemNM) { this.mst00003ItemNM = mst00003ItemNM; }

    public String getMst00003MakerCD() { return mst00003MakerCD; }

    public void setMst00003MakerCD(String mst00003MakerCD) { this.mst00003MakerCD = mst00003MakerCD; }

    public String getMst00003Item() { return mst00003Item; }

    public void setMst00003Item(String mst00003Item) { this.mst00003Item = mst00003Item; }

    public String getMst00003ItemType() { return mst00003ItemType; }

    public void setMst00003ItemType(String mst00003ItemType) { this.mst00003ItemType = mst00003ItemType; }

    public BigDecimal getMst00003NetWeight() { return mst00003NetWeight; }

    public void setMst00003NetWeight(BigDecimal mst00003NetWeight) { this.mst00003NetWeight = mst00003NetWeight; }

    public String getMst00003StockUnit() { return mst00003StockUnit; }

    public void setMst00003StockUnit(String mst00003StockUnit) { this.mst00003StockUnit = mst00003StockUnit; }

    public String getMst00003Remarks() { return mst00003Remarks; }

    public void setMst00003Remarks(String mst00003Remarks) { this.mst00003Remarks = mst00003Remarks; }

    public DelFlg getMst00003DelFlg() { return mst00003DelFlg; }

    public void setMst00003DelFlg(DelFlg mst00003DelFlg) { this.mst00003DelFlg = mst00003DelFlg; }

    public String getMst00003CreateUserCD() { return mst00003CreateUserCD; }

    public void setMst00003CreateUserCD(String mst00003CreateUserCD) { this.mst00003CreateUserCD = mst00003CreateUserCD; }

    public Timestamp getMst00003CreateDate() { return mst00003CreateDate; }

    public void setMst00003CreateDate(Timestamp mst00003CreateDate) { this.mst00003CreateDate = mst00003CreateDate; }

    public String getMst00003UpdateUserCD() { return mst00003UpdateUserCD; }

    public void setMst00003UpdateUserCD(String mst00003UpdateUserCD) { this.mst00003UpdateUserCD = mst00003UpdateUserCD; }

    public Timestamp getMst00003UpdateDate() { return mst00003UpdateDate; }

    public void setMst00003UpdateDate(Timestamp mst00003UpdateDate) { this.mst00003UpdateDate = mst00003UpdateDate; }

    public java.util.Map<String, Object> getScriptRefs() { return scriptRefs; }
}
