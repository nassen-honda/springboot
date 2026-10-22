package com.nassen.cem.module;

import com.nassen.cem.script.ScriptDb;
import com.nassen.cem.script.ScriptDebug;
import com.nassen.cem.script.ScriptFile;
import com.nassen.cem.script.ScriptFiles;
import com.nassen.cem.script.ScriptLib;
import com.nassen.cem.script.ScriptSession;
import com.nassen.cem.struct.MenuItem;
import java.util.ArrayList;
import java.util.List;

/**
 * サイドメニュー（SideMenu）モジュール（自動生成 / auto-generated）。
 *
 * <p>呼び出し：SideMenu.Call(...)、&amp;変数 = SideMenu.Get(...)（最初の out を返す）。
 * モジュール ファイルのコードから生成されました。手動編集は再生成で失われます。</p>
 */
public class SideMenu {

    // ===== インターフェース（Call の引数） =====

    /** メニュー（out） */
    public List<MenuItem> Menu = new ArrayList<>();

    /** Sub Call：モジュールの処理（インターフェースの Call）（コード 5 行目） */
    public void call() {
        MenuItem group1 = new MenuItem();
        MenuItem group2 = new MenuItem();
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 6)) ScriptDebug.pause(new String[] { "&group1\tMenuItem", "&group2\tMenuItem", }, new Object[] { group1, group2, }, debugVariables_());
        this.Menu.clear();
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 7)) ScriptDebug.pause(new String[] { "&group1\tMenuItem", "&group2\tMenuItem", }, new Object[] { group1, group2, }, debugVariables_());
        this.Menu.add(Screen("CustomerMasterList", "取引先マスタ一覧", "📄"));
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 8)) ScriptDebug.pause(new String[] { "&group1\tMenuItem", "&group2\tMenuItem", }, new Object[] { group1, group2, }, debugVariables_());
        this.Menu.add(Screen("listScreen2", "一覧画面2", "📄"));
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 11)) ScriptDebug.pause(new String[] { "&group1\tMenuItem", "&group2\tMenuItem", }, new Object[] { group1, group2, }, debugVariables_());
        group1 = Group("グループ1", "📁");
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 12)) ScriptDebug.pause(new String[] { "&group1\tMenuItem", "&group2\tMenuItem", }, new Object[] { group1, group2, }, debugVariables_());
        group1.getChildren().add(Screen("listScreen3", "一覧画面3", "📄"));
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 13)) ScriptDebug.pause(new String[] { "&group1\tMenuItem", "&group2\tMenuItem", }, new Object[] { group1, group2, }, debugVariables_());
        group1.getChildren().add(Screen("listScreen4", "一覧画面4", "📄"));
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 16)) ScriptDebug.pause(new String[] { "&group1\tMenuItem", "&group2\tMenuItem", }, new Object[] { group1, group2, }, debugVariables_());
        group2 = Group("グループ2", "📁");
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 17)) ScriptDebug.pause(new String[] { "&group1\tMenuItem", "&group2\tMenuItem", }, new Object[] { group1, group2, }, debugVariables_());
        group2.getChildren().add(Screen("listScreen5", "一覧画面5", "📄"));
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 18)) ScriptDebug.pause(new String[] { "&group1\tMenuItem", "&group2\tMenuItem", }, new Object[] { group1, group2, }, debugVariables_());
        group2.getChildren().add(Screen("listScreen6", "一覧画面6", "📄"));
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 19)) ScriptDebug.pause(new String[] { "&group1\tMenuItem", "&group2\tMenuItem", }, new Object[] { group1, group2, }, debugVariables_());
        group1.getChildren().add(group2);
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 21)) ScriptDebug.pause(new String[] { "&group1\tMenuItem", "&group2\tMenuItem", }, new Object[] { group1, group2, }, debugVariables_());
        this.Menu.add(group1);
    }

    /** Function Screen（コード 25 行目） */
    private MenuItem Screen(String key, String caption, String icon) {
        MenuItem item = new MenuItem();
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 26)) ScriptDebug.pause(new String[] { "&key\tString", "&caption\tString", "&icon\tString", "&item\tMenuItem", }, new Object[] { key, caption, icon, item, }, debugVariables_());
        item.setKey(key);
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 27)) ScriptDebug.pause(new String[] { "&key\tString", "&caption\tString", "&icon\tString", "&item\tMenuItem", }, new Object[] { key, caption, icon, item, }, debugVariables_());
        item.setCaption(caption);
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 28)) ScriptDebug.pause(new String[] { "&key\tString", "&caption\tString", "&icon\tString", "&item\tMenuItem", }, new Object[] { key, caption, icon, item, }, debugVariables_());
        item.setUrl("/" + ScriptLib.str(key));
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 29)) ScriptDebug.pause(new String[] { "&key\tString", "&caption\tString", "&icon\tString", "&item\tMenuItem", }, new Object[] { key, caption, icon, item, }, debugVariables_());
        item.setIcon(icon);
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 30)) ScriptDebug.pause(new String[] { "&key\tString", "&caption\tString", "&icon\tString", "&item\tMenuItem", }, new Object[] { key, caption, icon, item, }, debugVariables_());
        return item;
    }

    /** Function Group（コード 34 行目） */
    private MenuItem Group(String caption, String icon) {
        MenuItem item = new MenuItem();
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 35)) ScriptDebug.pause(new String[] { "&caption\tString", "&icon\tString", "&item\tMenuItem", }, new Object[] { caption, icon, item, }, debugVariables_());
        item.setCaption(caption);
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 36)) ScriptDebug.pause(new String[] { "&caption\tString", "&icon\tString", "&item\tMenuItem", }, new Object[] { caption, icon, item, }, debugVariables_());
        item.setIcon(icon);
        if (ScriptDebug.on && ScriptDebug.stop("SideMenu", 37)) ScriptDebug.pause(new String[] { "&caption\tString", "&icon\tString", "&item\tMenuItem", }, new Object[] { caption, icon, item, }, debugVariables_());
        return item;
    }

    /** ステップ実行：止めたときに IDE に送る変数（{ 名前（名前\tタイプ\t論理名）の配列, 値の配列 }）。 */
    private Object[] debugVariables_() {
        return new Object[] {
            new String[] {
                "&Menu\tList(MenuItem)\tメニュー",
            },
            new Object[] {
                this.Menu,
            },
        };
    }

    /** エラーの場所（ex.Screen・ex.Line）：Java の行とコードの行の組。 */
    static {
        ScriptLib.source(SideMenu.class, "SideMenu", new int[] { 28, 5, 30, 6, 32, 7, 34, 8, 36, 11, 38, 12, 40, 13, 42, 16,
            44, 17, 46, 18, 48, 19, 50, 21, 56, 25, 57, 26, 59, 27, 61, 28,
            63, 29, 65, 30, 71, 34, 72, 35, 74, 36, 76, 37 });
    }
}
