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
 * 担当者マスタ（MST00002）の Entity（自動生成 / auto-generated）。
 *
 * <p>表定義（Data/tables.json）から生成されました。登録では値を設定していない列（null）を INSERT に含めない（DB の既定値にする）。
 * 手動編集は再生成で失われます。</p>
 */
@Entity
@DynamicInsert
@Table(name = "MST00002")
public class Mst00002 {

    /** 担当者コード（主キー 1） */
    @Id
    @Column(name = "MST00002_UserCD", nullable = false, length = 10)
    private String mst00002UserCD;

    /** 担当者名 */
    @Column(name = "MST00002_UserNM", nullable = false, length = 50)
    private String mst00002UserNM;

    /** ログインID */
    @Column(name = "MST00002_LoginUsr", nullable = false, length = 20)
    private String mst00002LoginUsr;

    /** パスワード */
    @Column(name = "MST00002_LoginPwd", nullable = false, length = 100)
    private String mst00002LoginPwd;

    /** メールアドレス */
    @Column(name = "MST00002_MailAddress", nullable = false, length = 50)
    private String mst00002MailAddress;

    /** ロール */
    @Column(name = "MST00002_UserRole", nullable = false, length = 10)
    private String mst00002UserRole;

    /** 部署コード */
    @Column(name = "MST00002_SectionCD", nullable = false, length = 10)
    private String mst00002SectionCD;

    /** 削除フラグ。既定値: 0 */
    @Convert(converter = DelFlg.JpaConverter.class)
    @Column(name = "MST00002_DelFlg", length = 1)
    private DelFlg mst00002DelFlg;

    /** 作成者 */
    @Column(name = "MST00002_CreateUserCD", length = 10)
    private String mst00002CreateUserCD;

    /** 作成日時。既定値: Sysdate */
    @Column(name = "MST00002_CreateDate")
    private Timestamp mst00002CreateDate;

    /** 更新者 */
    @Column(name = "MST00002_UpdateUserCD", length = 10)
    private String mst00002UpdateUserCD;

    /** 更新日時。既定値: Sysdate */
    @Column(name = "MST00002_UpdateDate")
    private Timestamp mst00002UpdateDate;

    /** For Each 表 Into で付けた参照の表の行（別名 → 行）。DB には保存しない。 */
    @Transient
    private java.util.Map<String, Object> scriptRefs = new java.util.HashMap<>();

    public String getMst00002UserCD() { return mst00002UserCD; }

    public void setMst00002UserCD(String mst00002UserCD) { this.mst00002UserCD = mst00002UserCD; }

    public String getMst00002UserNM() { return mst00002UserNM; }

    public void setMst00002UserNM(String mst00002UserNM) { this.mst00002UserNM = mst00002UserNM; }

    public String getMst00002LoginUsr() { return mst00002LoginUsr; }

    public void setMst00002LoginUsr(String mst00002LoginUsr) { this.mst00002LoginUsr = mst00002LoginUsr; }

    public String getMst00002LoginPwd() { return mst00002LoginPwd; }

    public void setMst00002LoginPwd(String mst00002LoginPwd) { this.mst00002LoginPwd = mst00002LoginPwd; }

    public String getMst00002MailAddress() { return mst00002MailAddress; }

    public void setMst00002MailAddress(String mst00002MailAddress) { this.mst00002MailAddress = mst00002MailAddress; }

    public String getMst00002UserRole() { return mst00002UserRole; }

    public void setMst00002UserRole(String mst00002UserRole) { this.mst00002UserRole = mst00002UserRole; }

    public String getMst00002SectionCD() { return mst00002SectionCD; }

    public void setMst00002SectionCD(String mst00002SectionCD) { this.mst00002SectionCD = mst00002SectionCD; }

    public DelFlg getMst00002DelFlg() { return mst00002DelFlg; }

    public void setMst00002DelFlg(DelFlg mst00002DelFlg) { this.mst00002DelFlg = mst00002DelFlg; }

    public String getMst00002CreateUserCD() { return mst00002CreateUserCD; }

    public void setMst00002CreateUserCD(String mst00002CreateUserCD) { this.mst00002CreateUserCD = mst00002CreateUserCD; }

    public Timestamp getMst00002CreateDate() { return mst00002CreateDate; }

    public void setMst00002CreateDate(Timestamp mst00002CreateDate) { this.mst00002CreateDate = mst00002CreateDate; }

    public String getMst00002UpdateUserCD() { return mst00002UpdateUserCD; }

    public void setMst00002UpdateUserCD(String mst00002UpdateUserCD) { this.mst00002UpdateUserCD = mst00002UpdateUserCD; }

    public Timestamp getMst00002UpdateDate() { return mst00002UpdateDate; }

    public void setMst00002UpdateDate(Timestamp mst00002UpdateDate) { this.mst00002UpdateDate = mst00002UpdateDate; }

    public java.util.Map<String, Object> getScriptRefs() { return scriptRefs; }
}
