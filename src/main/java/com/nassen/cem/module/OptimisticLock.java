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
 * 楽観排他の確認（OptimisticLock）モジュール（自動生成 / auto-generated）。
 *
 * <p>呼び出し：OptimisticLock.Call(...)、&amp;変数 = OptimisticLock.Get(...)（最初の out を返す）。
 * モジュール ファイルのコードから生成されました。手動編集は再生成で失われます。</p>
 */
public class OptimisticLock {

    // ===== インターフェース（Call の引数） =====

    /** 楽観排他の情報（in） */
    public Optimistic piOptimistic = new Optimistic();

    /** 結果（True：保存してよい）（out） */
    public Boolean poResult = false;

    /** メッセージ（False のとき）（out） */
    public String poMessage = "";

    // ===== 変数 =====

    /** 今日の 0 時（計算用） */
    private Timestamp Midnight = null;

    /** Sub Call：モジュールの処理（インターフェースの Call）（コード 7 行目） */
    public void call() {
        if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 8)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        this.poResult = false;
        if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 9)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        this.poMessage = "";
        if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 12)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        ScriptDb.optimisticCheck(this.piOptimistic);
        if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 14)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        if (ScriptLib.isEmpty(this.piOptimistic.getCurrentDate())) {
            if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 15)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            this.poMessage = "他の人が削除しました。画面を開き直してください。";
            if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 16)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            return;
        }
        if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 20)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        if (!ScriptLib.eq(this.piOptimistic.getCurrentDate(), this.piOptimistic.getReadDate())) {
            if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 21)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            if (ScriptLib.isEmpty(this.piOptimistic.getCurrentUser())) {
                if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 22)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
                this.poMessage = ScriptLib.formatText("%1 に他の人が先に更新しました。画面を開き直してから、もう一度入力してください。", (Object) ScriptLib.format(this.piOptimistic.getCurrentDate(), "yyyy/MM/dd HH:mm:ss"));
            } else {
                if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 24)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
                this.poMessage = ScriptLib.formatText("%1 に %2 さんが先に更新しました。画面を開き直してから、もう一度入力してください。", (Object) ScriptLib.format(this.piOptimistic.getCurrentDate(), "yyyy/MM/dd HH:mm:ss"), (Object) this.piOptimistic.getCurrentUser());
            }
            if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 27)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            ScriptLib.logAt("Info", ScriptLib.vars(null, (Object) "排他エラー", "&piOptimistic", (Object) this.piOptimistic));
            if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 28)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            return;
        }
        if (ScriptDebug.on && ScriptDebug.stop("OptimisticLock", 31)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        this.poResult = true;
    }

    /** ステップ実行：止めたときに IDE に送る変数（{ 名前（名前\tタイプ\t論理名）の配列, 値の配列 }）。 */
    private Object[] debugVariables_() {
        return new Object[] {
            new String[] {
                "&piOptimistic\tOptimistic\t楽観排他の情報",
                "&poResult\tBoolean\t結果（True：保存してよい）",
                "&poMessage\tString\tメッセージ（False のとき）",
                "&Midnight\tTimestamp\t今日の 0 時（計算用）",
            },
            new Object[] {
                this.piOptimistic,
                this.poResult,
                this.poMessage,
                this.Midnight,
            },
        };
    }

    /** エラーの場所（ex.Screen・ex.Line）：Java の行とコードの行の組。 */
    static {
        ScriptLib.source(OptimisticLock.class, "OptimisticLock", new int[] { 38, 8, 40, 9, 42, 12, 44, 14, 46, 15, 48, 16, 51, 20, 53, 21,
            55, 22, 58, 24, 61, 27, 63, 28, 66, 31 });
    }
}
