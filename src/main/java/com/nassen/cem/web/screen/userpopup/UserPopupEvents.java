package com.nassen.cem.web.screen.userpopup;

import com.nassen.cem.entity.Mst00002;
import com.nassen.cem.module.GetUsers;
import com.nassen.cem.script.ScreenFlow;
import com.nassen.cem.script.ScriptDb;
import com.nassen.cem.script.ScriptDebug;
import com.nassen.cem.script.ScriptFile;
import com.nassen.cem.script.ScriptFiles;
import com.nassen.cem.script.ScriptLib;
import com.nassen.cem.script.ScriptSession;
import java.util.ArrayList;
import java.util.List;

/**
 * ユーザ選択画面画面のイベント処理（自動生成 / auto-generated）。
 *
 * <p>画面定義の「コード」タブ（サーバー側の Event と、そこから呼び出す Sub / Function）から生成されました。手動編集は再生成で失われます。</p>
 */
public class UserPopupEvents {

    /** 画面のフォーム（変数）。 */
    private final UserPopupController.UserPopupForm form;

    /** 画面の遷移（画面ID.Call / Close）。 */
    private final ScreenFlow flow;

    public UserPopupEvents(UserPopupController.UserPopupForm form, ScreenFlow flow) {
        this.form = form;
        this.flow = flow;
    }

    /** Event Form.Load：画面：画面の初期表示（サーバー）（コード 2 行目） */
    public void Form_Load() {
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 3)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        getData();
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 4)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        ScriptLib.log("Form.Load");
    }

    /** Event Users.RowClick：担当者マスタ：行をクリック（サーバー。引数 row：行番号（1 から））（コード 7 行目） */
    public void Users_RowClick(Integer row) {
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 8)) ScriptDebug.pause(new String[] { "row\tInteger", }, new Object[] { row, }, debugVariables_());
        form.setUser(form.getUsers().get(ScriptLib.intv(row) - 1));
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 9)) ScriptDebug.pause(new String[] { "row\tInteger", }, new Object[] { row, }, debugVariables_());
        form.setUserCD(ScriptLib.nzChar(form.getUser().getMst00002UserCD()));
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 10)) ScriptDebug.pause(new String[] { "row\tInteger", }, new Object[] { row, }, debugVariables_());
        form.setUserNm(ScriptLib.nz(form.getUser().getMst00002UserNM()));
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 12)) ScriptDebug.pause(new String[] { "row\tInteger", }, new Object[] { row, }, debugVariables_());
        flow.close("UserPopup", new Object[] { ScriptLib.nz(form.getUserCD()), ScriptLib.nz(form.getUserNm()) });
    }

    /** Event btnOk.Click：確定：クリック（サーバー。Front Event にするとブラウザー）（コード 16 行目） */
    public void btnOk_Click() {
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 18)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        ScriptLib.log("btnOk.Click");
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 19)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        flow.close("UserPopup", new Object[] { ScriptLib.nz(form.getUserCD()), ScriptLib.nz(form.getUserNm()) });
    }

    /** Event btnCancel.Click：キャンセル：クリック（サーバー。Front Event にするとブラウザー）（コード 22 行目） */
    public void btnCancel_Click() {
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 23)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        flow.close("UserPopup", new Object[] { ScriptLib.nz(form.getUserCD()), ScriptLib.nz(form.getUserNm()) });
    }

    /** Sub getData（コード 26 行目） */
    private void getData() {
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 27)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        {
            var call_1 = new GetUsers();
            call_1.UserCD = ScriptLib.nz(form.getUserCD());
            call_1.UserNm = ScriptLib.nz(form.getUserNm());
            call_1.call();
            form.setUsers(call_1.Users);
        }
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 28)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        ScriptLib.log("getData");
    }

    /** Event UserCD.Change：ユーザコード：値が変わったとき：入力中は入力が少し（0.4 秒）止まったとき、選択・チェックはすぐ（サーバー。画面を送信する（ページは読み込み直さない）。DB で検索して表示するときなど）（コード 31 行目） */
    public void UserCD_Change() {
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 32)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        getData();
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 33)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        ScriptLib.log("UserCD.Change");
    }

    /** Event UserNm.Change：ユーザ名：値が変わったとき：入力中は入力が少し（0.4 秒）止まったとき、選択・チェックはすぐ（サーバー。画面を送信する（ページは読み込み直さない）。DB で検索して表示するときなど）（コード 37 行目） */
    public void UserNm_Change() {
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 38)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        getData();
        if (ScriptDebug.on && ScriptDebug.stop("UserPopup", 39)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        ScriptLib.log("UserNm.Change");
    }

    /** ステップ実行：止めたときに IDE に送る変数（{ 名前（名前\tタイプ\t論理名）の配列, 値の配列 }）。 */
    private Object[] debugVariables_() {
        return new Object[] {
            new String[] {
                "&UserCD\tString\tユーザコード",
                "&UserNm\tString\tユーザ名",
                "&Users\tList(MST00002)\t担当者マスタ",
                "&User\tMST00002\t担当者マスタ",
            },
            new Object[] {
                ScriptLib.nz(form.getUserCD()),
                ScriptLib.nz(form.getUserNm()),
                form.getUsers(),
                form.getUser(),
            },
        };
    }

    /** エラーの場所（ex.Screen・ex.Line）：Java の行とコードの行の組。 */
    static {
        ScriptLib.source(UserPopupEvents.class, "UserPopup", new int[] { 35, 3, 37, 4, 43, 8, 45, 9, 47, 10, 49, 12, 55, 18, 57, 19,
            63, 23, 69, 27, 77, 28, 83, 32, 85, 33, 91, 38, 93, 39 });
    }
}
