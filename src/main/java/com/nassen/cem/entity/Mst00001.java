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
import java.sql.Timestamp;
import java.util.Objects;
import org.hibernate.annotations.DynamicInsert;

/**
 * 汎用区分マスタ（MST00001）の Entity（自動生成 / auto-generated）。
 *
 * <p>表定義（Data/tables.json）から生成されました。登録では値を設定していない列（null）を INSERT に含めない（DB の既定値にする）。
 * 手動編集は再生成で失われます。</p>
 */
@Entity
@DynamicInsert
@Table(name = "MST00001")
@IdClass(Mst00001.PK.class)
public class Mst00001 {

    /** 分類（主キー 1） */
    @Id
    @Column(name = "MST00001_CategoryCD", nullable = false, length = 3)
    private String mst00001CategoryCD;

    /** 区分（主キー 2） */
    @Id
    @Column(name = "MST00001_CategoryKBN", nullable = false, length = 2)
    private String mst00001CategoryKBN;

    /** 分類名 */
    @Column(name = "MST00001_CategoryNM", nullable = false, length = 50)
    private String mst00001CategoryNM;

    /** 区分名 */
    @Column(name = "MST00001_CategoryKBNNM", nullable = false, length = 100)
    private String mst00001CategoryKBNNM;

    /** 削除フラグ。既定値: 0 */
    @Convert(converter = DelFlg.JpaConverter.class)
    @Column(name = "MST00001_DelFlg", length = 1)
    private DelFlg mst00001DelFlg;

    /** 作成者 */
    @Column(name = "MST00001_CreateUserCD", length = 10)
    private String mst00001CreateUserCD;

    /** 作成日時。既定値: Sysdate */
    @Column(name = "MST00001_CreateDate")
    private Timestamp mst00001CreateDate;

    /** 更新者 */
    @Column(name = "MST00001_UpdateUserCD", length = 10)
    private String mst00001UpdateUserCD;

    /** 更新日時。既定値: Sysdate */
    @Column(name = "MST00001_UpdateDate")
    private Timestamp mst00001UpdateDate;

    /** For Each 表 Into で付けた参照の表の行（別名 → 行）。DB には保存しない。 */
    @Transient
    private java.util.Map<String, Object> scriptRefs = new java.util.HashMap<>();

    public String getMst00001CategoryCD() { return mst00001CategoryCD; }

    public void setMst00001CategoryCD(String mst00001CategoryCD) { this.mst00001CategoryCD = mst00001CategoryCD; }

    public String getMst00001CategoryKBN() { return mst00001CategoryKBN; }

    public void setMst00001CategoryKBN(String mst00001CategoryKBN) { this.mst00001CategoryKBN = mst00001CategoryKBN; }

    public String getMst00001CategoryNM() { return mst00001CategoryNM; }

    public void setMst00001CategoryNM(String mst00001CategoryNM) { this.mst00001CategoryNM = mst00001CategoryNM; }

    public String getMst00001CategoryKBNNM() { return mst00001CategoryKBNNM; }

    public void setMst00001CategoryKBNNM(String mst00001CategoryKBNNM) { this.mst00001CategoryKBNNM = mst00001CategoryKBNNM; }

    public DelFlg getMst00001DelFlg() { return mst00001DelFlg; }

    public void setMst00001DelFlg(DelFlg mst00001DelFlg) { this.mst00001DelFlg = mst00001DelFlg; }

    public String getMst00001CreateUserCD() { return mst00001CreateUserCD; }

    public void setMst00001CreateUserCD(String mst00001CreateUserCD) { this.mst00001CreateUserCD = mst00001CreateUserCD; }

    public Timestamp getMst00001CreateDate() { return mst00001CreateDate; }

    public void setMst00001CreateDate(Timestamp mst00001CreateDate) { this.mst00001CreateDate = mst00001CreateDate; }

    public String getMst00001UpdateUserCD() { return mst00001UpdateUserCD; }

    public void setMst00001UpdateUserCD(String mst00001UpdateUserCD) { this.mst00001UpdateUserCD = mst00001UpdateUserCD; }

    public Timestamp getMst00001UpdateDate() { return mst00001UpdateDate; }

    public void setMst00001UpdateDate(Timestamp mst00001UpdateDate) { this.mst00001UpdateDate = mst00001UpdateDate; }

    public java.util.Map<String, Object> getScriptRefs() { return scriptRefs; }

    /** 複合主キー（分類・区分）。 */
    public static class PK implements Serializable {

        private static final long serialVersionUID = 1L;

        private String mst00001CategoryCD;

        private String mst00001CategoryKBN;

        public PK() {
        }

        public PK(String mst00001CategoryCD, String mst00001CategoryKBN) {
            this.mst00001CategoryCD = mst00001CategoryCD;
            this.mst00001CategoryKBN = mst00001CategoryKBN;
        }

        public String getMst00001CategoryCD() { return mst00001CategoryCD; }

        public void setMst00001CategoryCD(String mst00001CategoryCD) { this.mst00001CategoryCD = mst00001CategoryCD; }

        public String getMst00001CategoryKBN() { return mst00001CategoryKBN; }

        public void setMst00001CategoryKBN(String mst00001CategoryKBN) { this.mst00001CategoryKBN = mst00001CategoryKBN; }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof PK other)) {
                return false;
            }
            return Objects.equals(mst00001CategoryCD, other.mst00001CategoryCD)
                && Objects.equals(mst00001CategoryKBN, other.mst00001CategoryKBN);
        }

        @Override
        public int hashCode() {
            return Objects.hash(mst00001CategoryCD, mst00001CategoryKBN);
        }
    }
}
