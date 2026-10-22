package com.nassen.cem.module;

import com.nassen.cem.entity.Mst00002;
import com.nassen.cem.script.ScriptDb;
import com.nassen.cem.script.ScriptFile;
import com.nassen.cem.script.ScriptFiles;
import com.nassen.cem.script.ScriptLib;
import com.nassen.cem.script.ScriptSession;
import java.util.ArrayList;
import java.util.List;

/**
 * モジュール（newmodule）モジュール（自動生成 / auto-generated）。
 *
 * <p>呼び出し：newmodule.Call(...)、&amp;変数 = newmodule.Get(...)（最初の out を返す）。
 * モジュール ファイルのコードから生成されました。手動編集は再生成で失われます。</p>
 */
public class Newmodule {

    // ===== インターフェース（Call の引数） =====

    /** ユーザコード（in） */
    public String UserCD = "";

    /** ユーザ名（in） */
    public String UserNm = "";

    /** 担当者マスタ（out） */
    public List<Mst00002> Users = new ArrayList<>();

    /** Sub Call：モジュールの処理（インターフェースの Call）（コード 2 行目） */
    public void call() {
    }

    /** エラーの場所（ex.Screen・ex.Line）：Java の行とコードの行の組。 */
    static {
        ScriptLib.source(Newmodule.class, "newmodule", new int[] { });
    }
}
