package com.nassen.cem.web.screen.customermasterlist;

import com.nassen.cem.entity.Mst00004;
import com.nassen.cem.enums.TrnMode;
import com.nassen.cem.script.ScreenFlow;
import com.nassen.cem.script.ScriptDb;
import com.nassen.cem.script.ScriptDebug;
import com.nassen.cem.script.ScriptFile;
import com.nassen.cem.script.ScriptFiles;
import com.nassen.cem.script.ScriptLib;
import com.nassen.cem.script.ScriptSession;
import java.util.List;

/**
 * 取引先マスタ一覧画面のイベント処理（自動生成 / auto-generated）。
 *
 * <p>画面定義の「コード」タブ（サーバー側の Event と、そこから呼び出す Sub / Function）から生成されました。手動編集は再生成で失われます。</p>
 */
public class CustomerMasterListEvents {

    /** 画面のフォーム（変数）。 */
    private final CustomerMasterListController.CustomerMasterListForm form;

    /** 画面の遷移（画面ID.Call / Close）。 */
    private final ScreenFlow flow;

    public CustomerMasterListEvents(CustomerMasterListController.CustomerMasterListForm form, ScreenFlow flow) {
        this.form = form;
        this.flow = flow;
    }

    /** Sub Search（コード 3 行目） */
    private void Search() {
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterList", 4)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        java.util.Map<String, Object> p_1 = new java.util.HashMap<>();
        java.util.List<String> w_1 = new java.util.ArrayList<>();
        if (!ScriptLib.isEmpty(ScriptLib.nz(form.getCustomerCD()))) {
            p_1.put("p1", ScriptLib.str("%" + ScriptLib.str(ScriptLib.nz(form.getCustomerCD()))) + "%");
            w_1.add("(t1.mst00004CustomerCD LIKE :p1)");
        }
        if (!ScriptLib.isEmpty(ScriptLib.nz(form.getCustomerNM()))) {
            p_1.put("p2", ScriptLib.str("%" + ScriptLib.str(ScriptLib.nz(form.getCustomerNM()))) + "%");
            w_1.add("(t1.mst00004CustomerNM LIKE :p2)");
        }
        ScriptDb.Rows rows_2 = ScriptDb.query("t1", "Mst00004 t1", w_1, "", "t1.mst00004CustomerCD DESC", p_1, 1000);
        form.getCustomers().clear();
        try {
            for (Object[] row_3 : rows_2.list()) {
                Mst00004 mst00004_4 = (Mst00004) row_3[0];
                form.getCustomers().add(mst00004_4);
            }
        } finally {
            ScriptDb.detach(form.getCustomers());
        }
    }

    /** Event btnSearch1.Click：検索：クリック（サーバー。Front Event にするとブラウザー）（コード 11 行目） */
    public void btnSearch1_Click() {
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterList", 12)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        Search();
    }

    /** Event btnClear1.Click：クリア：クリック（サーバー。Front Event にするとブラウザー）（コード 15 行目） */
    public void btnClear1_Click() {
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterList", 16)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        form.setCustomerCD("");
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterList", 17)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        form.setCustomerNM("");
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterList", 18)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        form.getCustomers().clear();
    }

    /** Event Customers.btnDetail.Click：取引先sの詳細：セルのボタン・リンクのクリック（サーバー。Front Event にするとブラウザー。引数 row：行番号（1 から））（コード 21 行目） */
    public void Customers_btnDetail_Click(Integer row) {
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterList", 22)) ScriptDebug.pause(new String[] { "row\tInteger", }, new Object[] { row, }, debugVariables_());
        flow.call("CustomerMasterList", "CustomerMasterDetail", new Object[] { ScriptLib.enumText(TrnMode.VIEW, TrnMode::getValue), ScriptLib.nzChar(form.getCustomers().get(ScriptLib.intv(row) - 1).getMst00004CustomerCD()) }, results -> {
        });
    }

    /** Event Customers.btnEdit.Click：取引先sの編集：セルのボタン・リンクのクリック（サーバー。Front Event にするとブラウザー。引数 row：行番号（1 から））（コード 25 行目） */
    public void Customers_btnEdit_Click(Integer row) {
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterList", 26)) ScriptDebug.pause(new String[] { "row\tInteger", }, new Object[] { row, }, debugVariables_());
        flow.call("CustomerMasterList", "CustomerMasterDetail", new Object[] { ScriptLib.enumText(TrnMode.EDIT, TrnMode::getValue), ScriptLib.nzChar(form.getCustomers().get(ScriptLib.intv(row) - 1).getMst00004CustomerCD()) }, results -> {
        });
    }

    /** Event Customers.btnCopy.Click：取引先sの複製：セルのボタン・リンクのクリック（サーバー。Front Event にするとブラウザー。引数 row：行番号（1 から））（コード 29 行目） */
    public void Customers_btnCopy_Click(Integer row) {
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterList", 30)) ScriptDebug.pause(new String[] { "row\tInteger", }, new Object[] { row, }, debugVariables_());
        flow.call("CustomerMasterList", "CustomerMasterDetail", new Object[] { ScriptLib.enumText(TrnMode.COPY, TrnMode::getValue), ScriptLib.nzChar(form.getCustomers().get(ScriptLib.intv(row) - 1).getMst00004CustomerCD()) }, results -> {
        });
    }

    /** Event Customers.btnDelete.Click：取引先sの削除：セルのボタン・リンクのクリック（サーバー。Front Event にするとブラウザー。引数 row：行番号（1 から））（コード 33 行目） */
    public void Customers_btnDelete_Click(Integer row) {
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterList", 34)) ScriptDebug.pause(new String[] { "row\tInteger", }, new Object[] { row, }, debugVariables_());
        flow.call("CustomerMasterList", "CustomerMasterDetail", new Object[] { ScriptLib.enumText(TrnMode.DEL, TrnMode::getValue), ScriptLib.nzChar(form.getCustomers().get(ScriptLib.intv(row) - 1).getMst00004CustomerCD()) }, results -> {
        });
    }

    /** Event UserCD.Popup：ユーザコード：🔍 のボタン：ポップアップ画面を開く（サーバー。書かない場合は 画面ID.Call(引数の変数, ...)。ポップアップ画面を付けた入力項目だけ）（コード 37 行目） */
    public void UserCD_Popup() {
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterList", 38)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        flow.call("CustomerMasterList", "UserPopup", new Object[] { ScriptLib.nz(form.getUserCD()), null }, results -> {
            form.setUserCD((String) results[0]);
            form.setUserName((String) results[1]);
        });
    }

    /** ステップ実行：止めたときに IDE に送る変数（{ 名前（名前\tタイプ\t論理名）の配列, 値の配列 }）。 */
    private Object[] debugVariables_() {
        return new Object[] {
            new String[] {
                "&UserCD\tString\tユーザコード",
                "&UserName\tString\tユーザ名",
                "&CustomerKbn\tString\t取引先区分",
                "&CustomerNM\tString\t取引先名",
                "&CustomerCD\tString\t取引先コード",
                "&CustomerOmission\tString\t取引先略称",
                "&CreateDate\tDate\t日付",
                "&Customers\tList(MST00004)\t取引先s",
            },
            new Object[] {
                ScriptLib.nz(form.getUserCD()),
                ScriptLib.nz(form.getUserName()),
                ScriptLib.nz(form.getCustomerKbn()),
                ScriptLib.nz(form.getCustomerNM()),
                ScriptLib.nz(form.getCustomerCD()),
                ScriptLib.nz(form.getCustomerOmission()),
                form.getCreateDate(),
                form.getCustomers(),
            },
        };
    }

    /** エラーの場所（ex.Screen・ex.Line）：Java の行とコードの行の組。 */
    static {
        ScriptLib.source(CustomerMasterListEvents.class, "CustomerMasterList", new int[] { 34, 4, 37, 5, 41, 6, 45, 4, 59, 12, 65, 16, 67, 17, 69, 18,
            75, 22, 82, 26, 89, 30, 96, 34, 103, 38 });
    }
}
