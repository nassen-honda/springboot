package com.nassen.cem.web.screen.developer;

import com.nassen.cem.script.ScreenFlow;
import com.nassen.cem.script.ScriptDb;
import com.nassen.cem.script.ScriptFile;
import com.nassen.cem.script.ScriptFiles;
import com.nassen.cem.script.ScriptLib;
import com.nassen.cem.script.ScriptSession;

/**
 * 開発メニュー画面のイベント処理（自動生成 / auto-generated）。
 *
 * <p>画面定義の「コード」タブ（サーバー側の Event と、そこから呼び出す Sub / Function）から生成されました。手動編集は再生成で失われます。</p>
 */
public class DeveloperEvents {

    /** 画面のフォーム（変数）。 */
    private final DeveloperController.DeveloperForm form;

    /** 画面の遷移（画面ID.Call / Close）。 */
    private final ScreenFlow flow;

    public DeveloperEvents(DeveloperController.DeveloperForm form, ScreenFlow flow) {
        this.form = form;
        this.flow = flow;
    }

    /** ステップ実行：止めたときに IDE に送る変数（{ 名前（名前\tタイプ\t論理名）の配列, 値の配列 }）。 */
    private Object[] debugVariables_() {
        return new Object[] {
            new String[] { },
            new Object[] { },
        };
    }

    /** エラーの場所（ex.Screen・ex.Line）：Java の行とコードの行の組。 */
    static {
        ScriptLib.source(DeveloperEvents.class, "Developer", new int[] { });
    }
}
