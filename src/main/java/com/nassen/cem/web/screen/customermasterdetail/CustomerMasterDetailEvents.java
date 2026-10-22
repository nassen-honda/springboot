package com.nassen.cem.web.screen.customermasterdetail;

import com.nassen.cem.entity.Mst00004;
import com.nassen.cem.enums.CustomerKbn;
import com.nassen.cem.enums.DelFlg;
import com.nassen.cem.enums.TrnMode;
import com.nassen.cem.script.ScreenFlow;
import com.nassen.cem.script.ScriptDb;
import com.nassen.cem.script.ScriptDebug;
import com.nassen.cem.script.ScriptFile;
import com.nassen.cem.script.ScriptFiles;
import com.nassen.cem.script.ScriptLib;
import com.nassen.cem.script.ScriptSession;
import java.sql.Timestamp;

/**
 * 取引先マスタ詳細画面のイベント処理（自動生成 / auto-generated）。
 *
 * <p>画面定義の「コード」タブ（サーバー側の Event と、そこから呼び出す Sub / Function）から生成されました。手動編集は再生成で失われます。</p>
 */
public class CustomerMasterDetailEvents {

    /** 画面のフォーム（変数）。 */
    private final CustomerMasterDetailController.CustomerMasterDetailForm form;

    /** 画面の遷移（画面ID.Call / Close）。 */
    private final ScreenFlow flow;

    public CustomerMasterDetailEvents(CustomerMasterDetailController.CustomerMasterDetailForm form, ScreenFlow flow) {
        this.form = form;
        this.flow = flow;
    }

    /** Event Form.Load：画面：画面の初期表示（サーバー）（コード 3 行目） */
    public void Form_Load() {
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterDetail", 5)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        if (ScriptLib.eq(ScriptLib.nz(form.getPiTrnMode()), ScriptLib.enumText(TrnMode.NEW, TrnMode::getValue))) {
            if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterDetail", 6)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            form.setCustomerMST00004_CustomerCD("");
            form.setCustomerMST00004_CustomerInvoiceCD("");
            form.setCustomerMST00004_CustomerKbn(null);
            form.setCustomerMST00004_CustomerNM("");
            form.setCustomerMST00004_CustomerOmission("");
            form.setCustomerMST00004_AreaCD("");
            form.setCustomerMST00004_Address1("");
            form.setCustomerMST00004_Address2("");
            form.setCustomerMST00004_Address3("");
            form.setCustomerMST00004_Tel("");
            form.setCustomerMST00004_Fax("");
            form.setCustomerMST00004_Zip("");
            form.setCustomerMST00004_FromPlaceCD("");
            form.setCustomerMST00004_ToPlaceCD("");
            form.setCustomerMST00004_PaymentTermsCD("");
            form.setCustomerMST00004_DeliveryLocation("");
            form.setCustomerMST00004_TradeTermsCD("");
            form.setCustomerMST00004_Currency("");
            form.setCustomerMST00004_SectionNM("");
            form.setCustomerMST00004_UserCD("");
            form.setCustomerMST00004_MailAddress("");
            form.setCustomerMST00004_TaxRoundKBN("");
            form.setCustomerMST00004_TaxCalcKBN("");
            form.setCustomerMST00004_InvoiceCreateFlg("");
            form.setCustomerMST00004_DelFlg(null);
            form.setCustomerMST00004_CreateUserCD("");
            form.setCustomerMST00004_CreateDate(null);
            form.setCustomerMST00004_UpdateUserCD("");
            form.setCustomerMST00004_UpdateDate(null);
        } else {
            if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterDetail", 8)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            if (!ScriptDb.load(Mst00004.class, ScriptLib.nz(form.getPiCustomerCd()), null, (e_, x_) -> { form.setCustomerMST00004_CustomerCD(ScriptLib.nzChar(e_.getMst00004CustomerCD())); form.setCustomerMST00004_CustomerInvoiceCD(ScriptLib.nzChar(e_.getMst00004CustomerInvoiceCD())); form.setCustomerMST00004_CustomerKbn(e_.getMst00004CustomerKbn()); form.setCustomerMST00004_CustomerNM(ScriptLib.nz(e_.getMst00004CustomerNM())); form.setCustomerMST00004_CustomerOmission(ScriptLib.nz(e_.getMst00004CustomerOmission())); form.setCustomerMST00004_AreaCD(ScriptLib.nz(e_.getMst00004AreaCD())); form.setCustomerMST00004_Address1(ScriptLib.nz(e_.getMst00004Address1())); form.setCustomerMST00004_Address2(ScriptLib.nz(e_.getMst00004Address2())); form.setCustomerMST00004_Address3(ScriptLib.nz(e_.getMst00004Address3())); form.setCustomerMST00004_Tel(ScriptLib.nz(e_.getMst00004Tel())); form.setCustomerMST00004_Fax(ScriptLib.nz(e_.getMst00004Fax())); form.setCustomerMST00004_Zip(ScriptLib.nz(e_.getMst00004Zip())); form.setCustomerMST00004_FromPlaceCD(ScriptLib.nz(e_.getMst00004FromPlaceCD())); form.setCustomerMST00004_ToPlaceCD(ScriptLib.nz(e_.getMst00004ToPlaceCD())); form.setCustomerMST00004_PaymentTermsCD(ScriptLib.nz(e_.getMst00004PaymentTermsCD())); form.setCustomerMST00004_DeliveryLocation(ScriptLib.nz(e_.getMst00004DeliveryLocation())); form.setCustomerMST00004_TradeTermsCD(ScriptLib.nz(e_.getMst00004TradeTermsCD())); form.setCustomerMST00004_Currency(ScriptLib.nz(e_.getMst00004Currency())); form.setCustomerMST00004_SectionNM(ScriptLib.nz(e_.getMst00004SectionNM())); form.setCustomerMST00004_UserCD(ScriptLib.nzChar(e_.getMst00004UserCD())); form.setCustomerMST00004_MailAddress(ScriptLib.nz(e_.getMst00004MailAddress())); form.setCustomerMST00004_TaxRoundKBN(ScriptLib.nzChar(e_.getMst00004TaxRoundKBN())); form.setCustomerMST00004_TaxCalcKBN(ScriptLib.nzChar(e_.getMst00004TaxCalcKBN())); form.setCustomerMST00004_InvoiceCreateFlg(ScriptLib.nzChar(e_.getMst00004InvoiceCreateFlg())); form.setCustomerMST00004_DelFlg(e_.getMst00004DelFlg()); form.setCustomerMST00004_CreateUserCD(ScriptLib.nzChar(e_.getMst00004CreateUserCD())); form.setCustomerMST00004_CreateDate(e_.getMst00004CreateDate()); form.setCustomerMST00004_UpdateUserCD(ScriptLib.nzChar(e_.getMst00004UpdateUserCD())); form.setCustomerMST00004_UpdateDate(e_.getMst00004UpdateDate()); }, false)) { ScriptLib.msg("Error", "取引先が見つかりません。他の人が削除した可能性があります。", null); }
        }
    }

    /** Event btnReturn.Click：戻る：クリック（サーバー。Front Event にするとブラウザー）（コード 14 行目） */
    public void btnReturn_Click() {
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterDetail", 16)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        flow.close("CustomerMasterDetail", new Object[] { ScriptLib.nz(form.getPiTrnMode()), ScriptLib.nz(form.getPiCustomerCd()) });
    }

    /** Event btnOk.Click：確定：クリック（サーバー。Front Event にするとブラウザー）（コード 19 行目） */
    public void btnOk_Click() {
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterDetail", 20)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        if (ScriptLib.eq(ScriptLib.nz(form.getPiTrnMode()), ScriptLib.enumText(TrnMode.NEW, TrnMode::getValue))) {
            if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterDetail", 21)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            ScriptDb.insert(Mst00004.class, null, x_ -> ScriptLib.nz(form.getCustomerMST00004_CustomerCD()), (e_, x_) -> { e_.setMst00004CustomerCD(ScriptLib.nz(form.getCustomerMST00004_CustomerCD())); e_.setMst00004CustomerInvoiceCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_CustomerInvoiceCD()))); e_.setMst00004CustomerKbn(form.getCustomerMST00004_CustomerKbn()); e_.setMst00004CustomerNM(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_CustomerNM()))); e_.setMst00004CustomerOmission(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_CustomerOmission()))); e_.setMst00004AreaCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_AreaCD()))); e_.setMst00004Address1(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Address1()))); e_.setMst00004Address2(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Address2()))); e_.setMst00004Address3(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Address3()))); e_.setMst00004Tel(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Tel()))); e_.setMst00004Fax(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Fax()))); e_.setMst00004Zip(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Zip()))); e_.setMst00004FromPlaceCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_FromPlaceCD()))); e_.setMst00004ToPlaceCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_ToPlaceCD()))); e_.setMst00004PaymentTermsCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_PaymentTermsCD()))); e_.setMst00004DeliveryLocation(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_DeliveryLocation()))); e_.setMst00004TradeTermsCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_TradeTermsCD()))); e_.setMst00004Currency(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Currency()))); e_.setMst00004SectionNM(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_SectionNM()))); e_.setMst00004UserCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_UserCD()))); e_.setMst00004MailAddress(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_MailAddress()))); e_.setMst00004TaxRoundKBN(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_TaxRoundKBN()))); e_.setMst00004TaxCalcKBN(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_TaxCalcKBN()))); e_.setMst00004InvoiceCreateFlg(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_InvoiceCreateFlg()))); e_.setMst00004DelFlg(form.getCustomerMST00004_DelFlg()); e_.setMst00004CreateUserCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_CreateUserCD()))); e_.setMst00004CreateDate(form.getCustomerMST00004_CreateDate()); e_.setMst00004UpdateUserCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_UpdateUserCD()))); e_.setMst00004UpdateDate(form.getCustomerMST00004_UpdateDate()); }, true);
        } else {
            if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterDetail", 23)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
            ScriptDb.update(Mst00004.class, null, x_ -> ScriptLib.nz(form.getCustomerMST00004_CustomerCD()), (e_, x_) -> { e_.setMst00004CustomerCD(ScriptLib.nz(form.getCustomerMST00004_CustomerCD())); e_.setMst00004CustomerInvoiceCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_CustomerInvoiceCD()))); e_.setMst00004CustomerKbn(form.getCustomerMST00004_CustomerKbn()); e_.setMst00004CustomerNM(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_CustomerNM()))); e_.setMst00004CustomerOmission(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_CustomerOmission()))); e_.setMst00004AreaCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_AreaCD()))); e_.setMst00004Address1(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Address1()))); e_.setMst00004Address2(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Address2()))); e_.setMst00004Address3(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Address3()))); e_.setMst00004Tel(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Tel()))); e_.setMst00004Fax(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Fax()))); e_.setMst00004Zip(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Zip()))); e_.setMst00004FromPlaceCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_FromPlaceCD()))); e_.setMst00004ToPlaceCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_ToPlaceCD()))); e_.setMst00004PaymentTermsCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_PaymentTermsCD()))); e_.setMst00004DeliveryLocation(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_DeliveryLocation()))); e_.setMst00004TradeTermsCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_TradeTermsCD()))); e_.setMst00004Currency(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_Currency()))); e_.setMst00004SectionNM(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_SectionNM()))); e_.setMst00004UserCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_UserCD()))); e_.setMst00004MailAddress(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_MailAddress()))); e_.setMst00004TaxRoundKBN(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_TaxRoundKBN()))); e_.setMst00004TaxCalcKBN(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_TaxCalcKBN()))); e_.setMst00004InvoiceCreateFlg(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_InvoiceCreateFlg()))); e_.setMst00004DelFlg(form.getCustomerMST00004_DelFlg()); e_.setMst00004CreateUserCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_CreateUserCD()))); e_.setMst00004CreateDate(form.getCustomerMST00004_CreateDate()); e_.setMst00004UpdateUserCD(ScriptLib.emptyToNull(ScriptLib.nz(form.getCustomerMST00004_UpdateUserCD()))); e_.setMst00004UpdateDate(form.getCustomerMST00004_UpdateDate()); }, true);
        }
        if (ScriptDebug.on && ScriptDebug.stop("CustomerMasterDetail", 25)) ScriptDebug.pause(new String[] { }, new Object[] { }, debugVariables_());
        ScriptDb.commit();
    }

    /** ステップ実行：止めたときに IDE に送る変数（{ 名前（名前\tタイプ\t論理名）の配列, 値の配列 }）。 */
    private Object[] debugVariables_() {
        return new Object[] {
            new String[] {
                "&piTrnMode\tString\t画面モード",
                "&piCustomerCd\tString\t取引先コード",
                "&Res\tBoolean\tRes",
            },
            new Object[] {
                ScriptLib.nz(form.getPiTrnMode()),
                ScriptLib.nz(form.getPiCustomerCd()),
                ScriptLib.nz(form.getRes()),
            },
        };
    }

    /** エラーの場所（ex.Screen・ex.Line）：Java の行とコードの行の組。 */
    static {
        ScriptLib.source(CustomerMasterDetailEvents.class, "CustomerMasterDetail", new int[] { 36, 5, 38, 6, 69, 8, 76, 16, 82, 20, 84, 21, 87, 23, 90, 25 });
    }
}
