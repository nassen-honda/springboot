package com.nassen.cem.module;

import com.nassen.cem.script.ScriptDb;
import com.nassen.cem.script.ScriptDebug;
import com.nassen.cem.script.ScriptFile;
import com.nassen.cem.script.ScriptFiles;
import com.nassen.cem.script.ScriptLib;
import com.nassen.cem.script.ScriptSession;
import com.nassen.cem.struct.Optimistic;
import java.sql.Timestamp;

/**
 * 楽観排他の確認（OptimisticLockingCheck）モジュール（自動生成 / auto-generated）。
 *
 * <p>呼び出し：OptimisticLockingCheck.Call(...)、&amp;変数 = OptimisticLockingCheck.Get(...)（最初の out を返す）。
 * モジュール ファイルのコードから生成されました。手動編集は再生成で失われます。</p>
 */
public class OptimisticLockingCheck {

    // ===== インターフェース（Call の引数） =====

    /** 楽観排他の情報（in） */
    public Optimistic piOptimistic = new Optimistic();

    /** 結果（True：保存してよい）（out） */
    public Boolean poResult = false;

    /** メッセージ（False のとき）（out） */
    public String poMessage = "";

    /** Sub Call：モジュールの処理（インターフェースの Call）（コード 6 行目） */
    public void call() {
        if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 7)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        this.poResult = false;
        if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 8)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        this.poMessage = "";
        if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 11)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        ScriptDb.optimisticCheck(this.piOptimistic, true);
        if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 13)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        if (ScriptLib.isEmpty(this.piOptimistic.getCurrentDate())) {
            if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 14)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            this.poMessage = "他の人が削除しました。画面を開き直してください。";
            if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 15)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            return;
        }
        if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 19)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        if (!ScriptLib.eq(this.piOptimistic.getCurrentDate(), this.piOptimistic.getReadDate())) {
            if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 20)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            if (ScriptLib.isEmpty(this.piOptimistic.getCurrentUser())) {
                if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 21)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
                this.poMessage = ScriptLib.formatText("%1 に他の人が先に更新しました。画面を開き直してから、もう一度入力してください。", (Object) ScriptLib.format(this.piOptimistic.getCurrentDate(), "yyyy/MM/dd HH:mm:ss"));
            } else {
                if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 23)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
                this.poMessage = ScriptLib.formatText("%1 に %2 さんが先に更新しました。画面を開き直してから、もう一度入力してください。", (Object) ScriptLib.format(this.piOptimistic.getCurrentDate(), "yyyy/MM/dd HH:mm:ss"), (Object) this.piOptimistic.getCurrentUser());
            }
            if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 26)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            ScriptLib.logAt("Info", ScriptLib.vars(null, (Object) "排他エラー", "&piOptimistic", (Object) this.piOptimistic));
            if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 27)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            return;
        }
        if (ScriptDebug.on && ScriptDebug.stop("OptimisticLockingCheck", 30)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        this.poResult = true;
    }

    /** ステップ実行：止めたときに IDE に送る変数（{ 名前（名前\tタイプ\t論理名）の配列, 値の配列 }）。 */
    private Object[] debugVariables_() {
        return new Object[] {
            new String[] {
                "&piOptimistic\tOptimistic\t楽観排他の情報",
                "&poResult\tBoolean\t結果（True：保存してよい）",
                "&poMessage\tString\tメッセージ（False のとき）",
            },
            new Object[] {
                this.piOptimistic,
                this.poResult,
                this.poMessage,
            },
        };
    }

    /** エラーの場所（ex.Screen・ex.Line）：Java の行とコードの行の組。 */
    static {
        ScriptLib.source(OptimisticLockingCheck.class, "OptimisticLockingCheck", new int[] { 33, 7, 35, 8, 37, 11, 39, 13, 41, 14, 43, 15, 46, 19, 48, 20,
            50, 21, 53, 23, 56, 26, 58, 27, 61, 30 });
    }
}
