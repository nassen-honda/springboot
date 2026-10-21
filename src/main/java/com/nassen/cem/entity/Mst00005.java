package com.nassen.cem.entity;

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
 * 部署マスタ（MST00005）の Entity（自動生成 / auto-generated）。
 *
 * <p>表定義（Data/tables.json）から生成されました。登録では値を設定していない列（null）を INSERT に含めない（DB の既定値にする）。
 * 手動編集は再生成で失われます。</p>
 */
@Entity
@DynamicInsert
@Table(name = "MST00005")
public class Mst00005 {

    /** 部署コード（主キー 1） */
    @Id
    @Column(name = "MST00005_SectionCD", nullable = false, length = 10)
    private String mst00005SectionCD;

    /** 部署名 */
    @Column(name = "MST00005_SectionNM", length = 200)
    private String mst00005SectionNM;

    /** 削除フラグ。既定値: 0 */
    @Convert(converter = DelFlg.JpaConverter.class)
    @Column(name = "MST00005_DelFlg", length = 1)
    private DelFlg mst00005DelFlg;

    /** 作成者 */
    @Column(name = "MST00005_CreateUserCD", length = 10)
    private String mst00005CreateUserCD;

    /** 作成日時。既定値: Sysdate */
    @Column(name = "MST00005_CreateDate")
    private Timestamp mst00005CreateDate;

    /** 更新者 */
    @Column(name = "MST00005_UpdateUserCD", length = 10)
    private String mst00005UpdateUserCD;

    /** 更新日時。既定値: Sysdate */
    @Column(name = "MST00005_UpdateDate")
    private Timestamp mst00005UpdateDate;

    /** For Each 表 Into で付けた参照の表の行（別名 → 行）。DB には保存しない。 */
    @Transient
    private java.util.Map<String, Object> scriptRefs = new java.util.HashMap<>();

    public String getMst00005SectionCD() { return mst00005SectionCD; }

    public void setMst00005SectionCD(String mst00005SectionCD) { this.mst00005SectionCD = mst00005SectionCD; }

    public String getMst00005SectionNM() { return mst00005SectionNM; }

    public void setMst00005SectionNM(String mst00005SectionNM) { this.mst00005SectionNM = mst00005SectionNM; }

    public DelFlg getMst00005DelFlg() { return mst00005DelFlg; }

    public void setMst00005DelFlg(DelFlg mst00005DelFlg) { this.mst00005DelFlg = mst00005DelFlg; }

    public String getMst00005CreateUserCD() { return mst00005CreateUserCD; }

    public void setMst00005CreateUserCD(String mst00005CreateUserCD) { this.mst00005CreateUserCD = mst00005CreateUserCD; }

    public Timestamp getMst00005CreateDate() { return mst00005CreateDate; }

    public void setMst00005CreateDate(Timestamp mst00005CreateDate) { this.mst00005CreateDate = mst00005CreateDate; }

    public String getMst00005UpdateUserCD() { return mst00005UpdateUserCD; }

    public void setMst00005UpdateUserCD(String mst00005UpdateUserCD) { this.mst00005UpdateUserCD = mst00005UpdateUserCD; }

    public Timestamp getMst00005UpdateDate() { return mst00005UpdateDate; }

    public void setMst00005UpdateDate(Timestamp mst00005UpdateDate) { this.mst00005UpdateDate = mst00005UpdateDate; }

    public java.util.Map<String, Object> getScriptRefs() { return scriptRefs; }
}
