package com.nassen.cem.module;

import com.nassen.cem.entity.Mst00002;
import com.nassen.cem.entity.Mst00005;
import com.nassen.cem.enums.DelFlg;
import com.nassen.cem.script.ScriptDb;
import com.nassen.cem.script.ScriptDebug;
import com.nassen.cem.script.ScriptFile;
import com.nassen.cem.script.ScriptFiles;
import com.nassen.cem.script.ScriptLib;
import com.nassen.cem.script.ScriptSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 取引先マスタモジュール（getUsers）モジュール（自動生成 / auto-generated）。
 *
 * <p>呼び出し：getUsers.Call(...)、&amp;変数 = getUsers.Get(...)（最初の out を返す）。
 * モジュール ファイルのコードから生成されました。手動編集は再生成で失われます。</p>
 */
public class GetUsers {

    // ===== インターフェース（Call の引数） =====

    /** ユーザコード（in） */
    public String UserCD = "";

    /** ユーザ名（in） */
    public String UserNm = "";

    /** 担当者マスタ（out） */
    public List<Mst00002> Users = new ArrayList<>();

    /** Sub Call：モジュールの処理（インターフェースの Call）（コード 2 行目） */
    public void call() {
        if (ScriptDebug.on && ScriptDebug.stop("getUsers", 4)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        ScriptLib.log("getUsers");
        if (ScriptDebug.on && ScriptDebug.stop("getUsers", 5)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        java.util.Map<String, Object> p_1 = new java.util.HashMap<>();
        java.util.List<String> w_1 = new java.util.ArrayList<>();
        if (!ScriptLib.isEmpty(this.UserCD)) {
            p_1.put("p1", ScriptLib.str("%" + ScriptLib.str(this.UserCD)) + "%");
            w_1.add("(t1.mst00002UserCD LIKE :p1)");
        }
        if (!ScriptLib.isEmpty(this.UserNm)) {
            p_1.put("p2", ScriptLib.str("%" + ScriptLib.str(this.UserNm)) + "%");
            w_1.add("(t1.mst00002UserNM LIKE :p2)");
        }
        p_1.put("p3", DelFlg.ACT);
        w_1.add("(t1.mst00002DelFlg = :p3)");
        ScriptDb.Rows rows_2 = ScriptDb.query("t1, t2", "Mst00002 t1 LEFT JOIN Mst00005 t2 ON ((t2.mst00005SectionCD = t1.mst00002SectionCD))", w_1, "", "", p_1, 1000);
        this.Users.clear();
        try {
            for (Object[] row_3 : rows_2.list()) {
                Mst00002 mst00002_4 = (Mst00002) row_3[0];
                Mst00005 mst00005_5 = (Mst00005) row_3[1];
                ScriptDb.attach(mst00002_4, "部署", mst00005_5);
                this.Users.add(mst00002_4);
            }
        } finally {
            ScriptDb.detach(this.Users);
        }
    }

    /** ステップ実行：止めたときに IDE に送る変数（{ 名前（名前\tタイプ\t論理名）の配列, 値の配列 }）。 */
    private Object[] debugVariables_() {
        return new Object[] {
            new String[] {
                "&UserCD\tString\tユーザコード",
                "&UserNm\tString\tユーザ名",
                "&Users\tList(MST00002)\t担当者マスタ",
            },
            new Object[] {
                this.UserCD,
                this.UserNm,
                this.Users,
            },
        };
    }

    /** エラーの場所（ex.Screen・ex.Line）：Java の行とコードの行の組。 */
    static {
        ScriptLib.source(GetUsers.class, "getUsers", new int[] { 36, 4, 38, 5, 41, 6, 45, 7, 49, 8, 51, 5 });
    }
}
