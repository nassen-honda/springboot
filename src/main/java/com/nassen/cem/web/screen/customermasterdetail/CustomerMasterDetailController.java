package com.nassen.cem.web.screen.customermasterdetail;

import com.nassen.cem.enums.CustomerKbn;
import com.nassen.cem.enums.DelFlg;
import com.nassen.cem.script.ScreenFlow;
import com.nassen.cem.script.ScriptDb;
import com.nassen.cem.script.ScriptErrorAdvice;
import com.nassen.cem.script.ScriptLib;
import com.nassen.cem.script.ScriptMessages;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 取引先マスタ詳細画面コントローラ（自動生成 / auto-generated）。
 *
 * <p>コードジェネレータにより生成されました。手動編集は再生成で失われます。</p>
 */
@Controller
@RequestMapping("/CustomerMasterDetail")
public class CustomerMasterDetailController {

    /** 画面テンプレート（Thymeleaf）のビュー名。 */
    private static final String VIEW = "screen/CustomerMasterDetail";

    /** 画面 ID（画面の遷移・フォームの保持に使う）。 */
    private static final String SCREEN = "CustomerMasterDetail";

    private final ScreenFlow flow;

    private final SmartValidator validator;

    public CustomerMasterDetailController(ScreenFlow flow, SmartValidator validator) {
        this.flow = flow;
        this.validator = validator;
    }

    /** フォーム（セッションに保持し、送信された値を設定する）。 */
    @ModelAttribute("form")
    public CustomerMasterDetailForm form() {
        return flow.form(SCREEN, CustomerMasterDetailForm::new);
    }

    /** 画面の MessageView に表示するメッセージ（リクエストごとに空から始める）。 */
    @ModelAttribute("messages")
    public ScriptMessages messages() {
        return new ScriptMessages();
    }

    /** 送信された値を設定しない項目（インターフェース用の in の変数、一覧の入力できない列）。 */
    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("piTrnMode", "piTrnMode.*", "piTrnMode[*", "piCustomerCd", "piCustomerCd.*", "piCustomerCd[*");
    }

    /** 初期表示（_return：Call で開いた画面から戻ったとき）。 */
    @GetMapping
    public String index(@RequestParam(name = "_return", required = false) String returned, Model model,
                        HttpServletResponse response) {
        if (returned != null) {
            CustomerMasterDetailForm form = flow.form(SCREEN, CustomerMasterDetailForm::new);
            model.addAttribute("form", form);
            if (flow.applyReturn(SCREEN, returned)) {
                // 戻ったときの処理（Event 画面ID.Return）はない
            }
            return next(form, model, response);
        }

        CustomerMasterDetailForm form = flow.resetForm(SCREEN, new CustomerMasterDetailForm());
        model.addAttribute("form", form);

        // 画面ID.Call(...) で開かれた：in / inout の引数を設定する
        Object[] arguments = flow.arguments(SCREEN);
        if (arguments != null) {
            form.setPiTrnMode((String) arguments[0]);
            form.setPiCustomerCd((String) arguments[1]);
        }

        CustomerMasterDetailEvents loadEvents = new CustomerMasterDetailEvents(form, flow);
        RuntimeException failed = run(model, loadEvents, "Form.Load", loadEvents::Form_Load);
        if (failed != null && !flow.navigating()) {
            // Form.Load のエラー：画面をまだ表示できないため、エラー画面を出す（Form.Error で別の画面に移った場合はその画面）
            return ScriptErrorAdvice.page(model, failed, response);
        }
        return next(form, model, response);
    }

    /** 送信：_event（ボタン・一覧の行・セルのボタン / リンクのイベント。セルは「イベント名:行番号」）がない場合は入力チェックをして登録する。 */
    @PostMapping
    public String submit(@ModelAttribute("form") CustomerMasterDetailForm form,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(name = "_event", required = false) String event,
                         @RequestParam(name = "_row", required = false) Integer row,
                         HttpServletResponse response) {
        // 一覧のセルのボタン・リンク：「イベント名:行番号」
        if (event != null && event.lastIndexOf(':') > 0) {
            int separator = event.lastIndexOf(':');
            try {
                row = Integer.valueOf(event.substring(separator + 1));
            } catch (NumberFormatException ex) {
                row = null;
            }
            event = event.substring(0, separator);
        }

        if (event == null || event.isEmpty()) {
            validator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                // 入力エラー時は同一画面を再表示
                return invalid(form, bindingResult, model);
            }
            // TODO: 業務処理をここに実装
            return "redirect:/CustomerMasterDetail/complete";
        }

        if ("btnReturn_Click".equals(event) || "btnOk_Click".equals(event)) {
            validator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                return invalid(form, bindingResult, model);
            }
        }

        CustomerMasterDetailEvents events = new CustomerMasterDetailEvents(form, flow);
        int eventRow = row == null ? 0 : row;
        String eventName = event;
        run(model, events, eventName, () -> {
            switch (eventName) {
                case "btnReturn_Click" -> {
                    ScriptLib.enter(SCREEN, "btnReturn.Click");
                    events.btnReturn_Click();
                }
                case "btnOk_Click" -> {
                    ScriptLib.enter(SCREEN, "btnOk.Click");
                    events.btnOk_Click();
                }
                default -> { }
            }
        });
        return next(form, model, response);
    }

    /**
     * イベントの後：ダウンロードした場合は応答済み（画面を表示しない）、Call / Close した場合はその画面へ移動し、それ以外は同じ画面を表示する。
     * ポップアップの画面を Call した場合は、同じ画面の上のダイアログで開く。ダイアログの中で Close した場合は、ダイアログを開いた画面を移動する。
     * この画面を表示する場合は、この画面に届いた GlobalEvents のイベント（Event GlobalEvents.…）を表示する前に実行する。
     */
    private String next(CustomerMasterDetailForm form, Model model, HttpServletResponse response) {
        // GlobalEvents：この処理で送ったイベントの届け先を決め、この画面を表示する場合は届いたイベントを受け取る
        java.util.List<ScreenFlow.GlobalEvent> received = flow.eventsFor(SCREEN, !response.isCommitted() && !flow.navigating());
        if (response.isCommitted()) {
            return null;
        }
        String breakOut = flow.takeBreakOut();
        String redirect = flow.takeRedirect();
        if (redirect != null && breakOut != null) {
            try {
                ScreenFlow.breakOut(response, breakOut, redirect);
            } catch (java.io.IOException e) {
                throw new java.io.UncheckedIOException(e);
            }
            return null;
        }
        if (redirect != null) {
            // 画面の部分更新の送信（fetch）は、移動先をヘッダーで返す（ブラウザーが移動先を開く）
            return ScreenFlow.partialRedirect(response, "self", redirect) ? null : "redirect:" + redirect;
        }
        globalEvents(form, model, received);
        ScreenFlow.Popup popup = flow.takePopup();
        if (popup != null) {
            model.addAttribute("popup", popup);
        }
        return view(form, model);
    }

    /**
     * GlobalEvents：この画面に届いたイベント（ほかの画面・この画面が GlobalEvents.名前(…) で送ったもの）を、表示する前に送った順に実行する。
     * その中では Call・Close・GlobalEvents.… は使えない。Event GlobalEvents.… がないイベントはログに出して捨てる。
     */
    private void globalEvents(CustomerMasterDetailForm form, Model model, java.util.List<ScreenFlow.GlobalEvent> received) {
        if (received.isEmpty()) {
            return;
        }
        flow.delivering(true);
        try {
            for (ScreenFlow.GlobalEvent event : received) {
                LOG.info("{} に GlobalEvents の {} が届きましたが、Event GlobalEvents.{} がありません", SCREEN, event.getClass().getSimpleName(), event.getClass().getSimpleName());
            }
        } finally {
            flow.delivering(false);
        }
    }

    /** 入力チェックのエラー：MessageView に Error（項目付き。クリックでその入力欄へ）で表示し、同じ画面を再表示する。 */
    private String invalid(CustomerMasterDetailForm form, BindingResult bindingResult, Model model) {
        ScriptMessages messages = messages(model);
        bindingResult.getAllErrors().forEach(error ->
            messages.add("Error", error.getDefaultMessage(), error instanceof FieldError field ? field.getField() : null));
        return view(form, model);
    }

    /** 画面を表示する：一覧の行ごとの状態を作り直す（Event 一覧.RowLoad を行ごとに実行する）。 */
    private String view(CustomerMasterDetailForm form, Model model) {
        // 行ごとの処理（Event 一覧.RowLoad）はない
        return VIEW;
    }

    /** エラーのログ。 */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(CustomerMasterDetailController.class);

    /**
     * イベントを実行する。Event 1 つが 1 トランザクション：Commit していない DB の更新は取り消す（自動では commit しない）。
     * 捕まえていないエラーは rollback してから Form.Error（ない場合は既定の処理）。Msg のメッセージを画面に表示する。
     * 捕まえていないエラーがあった場合はそのエラーを返す（Form.Load：画面を表示できない場合はエラー画面）。
     */
    private RuntimeException run(Model model, CustomerMasterDetailEvents events, String event, Runnable action) {
        ScriptLib.enter(SCREEN, event);
        try {
            action.run();
            return null;
        } catch (ScriptLib.ScriptException ex) {
            // Throw "メッセージ"：メッセージを MessageView に Error で表示する（検索の件数超過は Warning）
            ScriptDb.end();
            message(model, ex.getLevel(), ex.getMessage());
            return null;
        } catch (RuntimeException ex) {
            // エラーの日時・イベント（ex.Time・ex.Event）を覚えてから rollback
            ScriptLib.caught(ex);
            ScriptDb.end();
            error(model, events, ex);
            return ex;
        } finally {
            ScriptDb.end();
            ScriptLib.leave();
            messages(model).addAll(ScriptLib.takeMessages());
        }
    }

    /** 捕まえていないエラー：ログに出し、Form.Error（ない場合・Throw ex した場合・その中でエラーの場合は標準のメッセージ）。 */
    private void error(Model model, CustomerMasterDetailEvents events, RuntimeException ex) {
        LOG.error("{} のイベントでエラー（{}）", SCREEN, ScriptLib.errCode(ex), ex);
        message(model, "Error", ScriptLib.errDescription(ex) + "（" + ScriptLib.errCode(ex) + "）");
    }

    /** 画面の MessageView に表示するメッセージ（model の messages。ない場合は作る）。 */
    private static ScriptMessages messages(Model model) {
        if (model.getAttribute("messages") instanceof ScriptMessages messages) {
            return messages;
        }
        ScriptMessages messages = new ScriptMessages();
        model.addAttribute("messages", messages);
        return messages;
    }

    /** MessageView にメッセージを表示する（Throw "…"・捕まえていないエラーは Error、検索の件数超過は Warning）。 */
    private static void message(Model model, String level, String message) {
        messages(model).add(level, message, null);
    }

    /** 取引先区分 の選択肢（部品: ComboBox）。 */
    @ModelAttribute("CustomerMST00004_CustomerKbnItems")
    public Map<String, String> CustomerMST00004_CustomerKbnItems() {
        Map<String, String> items = new LinkedHashMap<>();
        items.put("CUSTOMER", "得意先");
        items.put("VENDOR", "仕入先");
        items.put("PARTNER", "業者");
        return items;
    }

    /** 削除フラグ の選択肢（部品: ComboBox）。 */
    @ModelAttribute("CustomerMST00004_DelFlgItems")
    public Map<String, String> CustomerMST00004_DelFlgItems() {
        Map<String, String> items = new LinkedHashMap<>();
        items.put("ACT", "輸入");
        items.put("DEL", "輸出");
        return items;
    }

    // ===== 画面フォーム（部品の集合 / form parts） =====
    /** 取引先マスタ詳細画面フォーム。 */
    public static class CustomerMasterDetailForm implements java.io.Serializable {

        private static final long serialVersionUID = 1L;

        /** 取引先コード */
        @NotBlank
        @Size(max = 10)
        private String CustomerMST00004_CustomerCD;

        /** 取引先請求コード */
        @Size(max = 10)
        private String CustomerMST00004_CustomerInvoiceCD;

        /** 取引先区分 */
        @NotNull
        private CustomerKbn CustomerMST00004_CustomerKbn;

        /** 取引先名 */
        @Size(max = 100)
        private String CustomerMST00004_CustomerNM;

        /** 取引先略称 */
        @Size(max = 100)
        private String CustomerMST00004_CustomerOmission;

        /** 地域コード */
        @Size(max = 20)
        private String CustomerMST00004_AreaCD;

        /** 住所1 */
        @Size(max = 100)
        private String CustomerMST00004_Address1;

        /** 住所2 */
        @Size(max = 100)
        private String CustomerMST00004_Address2;

        /** 住所3 */
        @Size(max = 100)
        private String CustomerMST00004_Address3;

        /** 電話番号 */
        @Size(max = 20)
        private String CustomerMST00004_Tel;

        /** FAX */
        @Size(max = 20)
        private String CustomerMST00004_Fax;

        /** 郵便番号 */
        @Size(max = 200)
        private String CustomerMST00004_Zip;

        /** 発地コード */
        @Size(max = 20)
        private String CustomerMST00004_FromPlaceCD;

        /** 仕向地コード */
        @Size(max = 20)
        private String CustomerMST00004_ToPlaceCD;

        /** 支払条件コード */
        @Size(max = 20)
        private String CustomerMST00004_PaymentTermsCD;

        /** 納入場所 */
        @Size(max = 200)
        private String CustomerMST00004_DeliveryLocation;

        /** 取引条件コード */
        @Size(max = 20)
        private String CustomerMST00004_TradeTermsCD;

        /** 通貨 */
        @Size(max = 20)
        private String CustomerMST00004_Currency;

        /** 部署名 */
        @Size(max = 200)
        private String CustomerMST00004_SectionNM;

        /** 担当者名 */
        @Size(max = 10)
        private String CustomerMST00004_UserCD;

        /** メールアドレス */
        @Size(max = 50)
        private String CustomerMST00004_MailAddress;

        /** 消費税端数処理区分 */
        @Size(max = 2)
        private String CustomerMST00004_TaxRoundKBN;

        /** 税額通知区分 */
        @Size(max = 2)
        private String CustomerMST00004_TaxCalcKBN;

        /** 請求書作成フラグ */
        @Size(max = 1)
        private String CustomerMST00004_InvoiceCreateFlg;

        /** 削除フラグ */
        private DelFlg CustomerMST00004_DelFlg;

        /** 作成者 */
        @Size(max = 10)
        private String CustomerMST00004_CreateUserCD;

        /** 作成日時 */
        private Timestamp CustomerMST00004_CreateDate;

        /** 更新者 */
        @Size(max = 10)
        private String CustomerMST00004_UpdateUserCD;

        /** 更新日時 */
        private Timestamp CustomerMST00004_UpdateDate;

        /** 画面モード（インターフェース用） */
        private String piTrnMode = "";

        /** 取引先コード（インターフェース用） */
        private String piCustomerCd = "";

        /** Res */
        private Boolean Res = false;

        public String getCustomerMST00004_CustomerCD() { return CustomerMST00004_CustomerCD; }
        public void setCustomerMST00004_CustomerCD(String CustomerMST00004_CustomerCD) { this.CustomerMST00004_CustomerCD = CustomerMST00004_CustomerCD; }

        public String getCustomerMST00004_CustomerInvoiceCD() { return CustomerMST00004_CustomerInvoiceCD; }
        public void setCustomerMST00004_CustomerInvoiceCD(String CustomerMST00004_CustomerInvoiceCD) { this.CustomerMST00004_CustomerInvoiceCD = CustomerMST00004_CustomerInvoiceCD; }

        public CustomerKbn getCustomerMST00004_CustomerKbn() { return CustomerMST00004_CustomerKbn; }
        public void setCustomerMST00004_CustomerKbn(CustomerKbn CustomerMST00004_CustomerKbn) { this.CustomerMST00004_CustomerKbn = CustomerMST00004_CustomerKbn; }

        public String getCustomerMST00004_CustomerNM() { return CustomerMST00004_CustomerNM; }
        public void setCustomerMST00004_CustomerNM(String CustomerMST00004_CustomerNM) { this.CustomerMST00004_CustomerNM = CustomerMST00004_CustomerNM; }

        public String getCustomerMST00004_CustomerOmission() { return CustomerMST00004_CustomerOmission; }
        public void setCustomerMST00004_CustomerOmission(String CustomerMST00004_CustomerOmission) { this.CustomerMST00004_CustomerOmission = CustomerMST00004_CustomerOmission; }

        public String getCustomerMST00004_AreaCD() { return CustomerMST00004_AreaCD; }
        public void setCustomerMST00004_AreaCD(String CustomerMST00004_AreaCD) { this.CustomerMST00004_AreaCD = CustomerMST00004_AreaCD; }

        public String getCustomerMST00004_Address1() { return CustomerMST00004_Address1; }
        public void setCustomerMST00004_Address1(String CustomerMST00004_Address1) { this.CustomerMST00004_Address1 = CustomerMST00004_Address1; }

        public String getCustomerMST00004_Address2() { return CustomerMST00004_Address2; }
        public void setCustomerMST00004_Address2(String CustomerMST00004_Address2) { this.CustomerMST00004_Address2 = CustomerMST00004_Address2; }

        public String getCustomerMST00004_Address3() { return CustomerMST00004_Address3; }
        public void setCustomerMST00004_Address3(String CustomerMST00004_Address3) { this.CustomerMST00004_Address3 = CustomerMST00004_Address3; }

        public String getCustomerMST00004_Tel() { return CustomerMST00004_Tel; }
        public void setCustomerMST00004_Tel(String CustomerMST00004_Tel) { this.CustomerMST00004_Tel = CustomerMST00004_Tel; }

        public String getCustomerMST00004_Fax() { return CustomerMST00004_Fax; }
        public void setCustomerMST00004_Fax(String CustomerMST00004_Fax) { this.CustomerMST00004_Fax = CustomerMST00004_Fax; }

        public String getCustomerMST00004_Zip() { return CustomerMST00004_Zip; }
        public void setCustomerMST00004_Zip(String CustomerMST00004_Zip) { this.CustomerMST00004_Zip = CustomerMST00004_Zip; }

        public String getCustomerMST00004_FromPlaceCD() { return CustomerMST00004_FromPlaceCD; }
        public void setCustomerMST00004_FromPlaceCD(String CustomerMST00004_FromPlaceCD) { this.CustomerMST00004_FromPlaceCD = CustomerMST00004_FromPlaceCD; }

        public String getCustomerMST00004_ToPlaceCD() { return CustomerMST00004_ToPlaceCD; }
        public void setCustomerMST00004_ToPlaceCD(String CustomerMST00004_ToPlaceCD) { this.CustomerMST00004_ToPlaceCD = CustomerMST00004_ToPlaceCD; }

        public String getCustomerMST00004_PaymentTermsCD() { return CustomerMST00004_PaymentTermsCD; }
        public void setCustomerMST00004_PaymentTermsCD(String CustomerMST00004_PaymentTermsCD) { this.CustomerMST00004_PaymentTermsCD = CustomerMST00004_PaymentTermsCD; }

        public String getCustomerMST00004_DeliveryLocation() { return CustomerMST00004_DeliveryLocation; }
        public void setCustomerMST00004_DeliveryLocation(String CustomerMST00004_DeliveryLocation) { this.CustomerMST00004_DeliveryLocation = CustomerMST00004_DeliveryLocation; }

        public String getCustomerMST00004_TradeTermsCD() { return CustomerMST00004_TradeTermsCD; }
        public void setCustomerMST00004_TradeTermsCD(String CustomerMST00004_TradeTermsCD) { this.CustomerMST00004_TradeTermsCD = CustomerMST00004_TradeTermsCD; }

        public String getCustomerMST00004_Currency() { return CustomerMST00004_Currency; }
        public void setCustomerMST00004_Currency(String CustomerMST00004_Currency) { this.CustomerMST00004_Currency = CustomerMST00004_Currency; }

        public String getCustomerMST00004_SectionNM() { return CustomerMST00004_SectionNM; }
        public void setCustomerMST00004_SectionNM(String CustomerMST00004_SectionNM) { this.CustomerMST00004_SectionNM = CustomerMST00004_SectionNM; }

        public String getCustomerMST00004_UserCD() { return CustomerMST00004_UserCD; }
        public void setCustomerMST00004_UserCD(String CustomerMST00004_UserCD) { this.CustomerMST00004_UserCD = CustomerMST00004_UserCD; }

        public String getCustomerMST00004_MailAddress() { return CustomerMST00004_MailAddress; }
        public void setCustomerMST00004_MailAddress(String CustomerMST00004_MailAddress) { this.CustomerMST00004_MailAddress = CustomerMST00004_MailAddress; }

        public String getCustomerMST00004_TaxRoundKBN() { return CustomerMST00004_TaxRoundKBN; }
        public void setCustomerMST00004_TaxRoundKBN(String CustomerMST00004_TaxRoundKBN) { this.CustomerMST00004_TaxRoundKBN = CustomerMST00004_TaxRoundKBN; }

        public String getCustomerMST00004_TaxCalcKBN() { return CustomerMST00004_TaxCalcKBN; }
        public void setCustomerMST00004_TaxCalcKBN(String CustomerMST00004_TaxCalcKBN) { this.CustomerMST00004_TaxCalcKBN = CustomerMST00004_TaxCalcKBN; }

        public String getCustomerMST00004_InvoiceCreateFlg() { return CustomerMST00004_InvoiceCreateFlg; }
        public void setCustomerMST00004_InvoiceCreateFlg(String CustomerMST00004_InvoiceCreateFlg) { this.CustomerMST00004_InvoiceCreateFlg = CustomerMST00004_InvoiceCreateFlg; }

        public DelFlg getCustomerMST00004_DelFlg() { return CustomerMST00004_DelFlg; }
        public void setCustomerMST00004_DelFlg(DelFlg CustomerMST00004_DelFlg) { this.CustomerMST00004_DelFlg = CustomerMST00004_DelFlg; }

        public String getCustomerMST00004_CreateUserCD() { return CustomerMST00004_CreateUserCD; }
        public void setCustomerMST00004_CreateUserCD(String CustomerMST00004_CreateUserCD) { this.CustomerMST00004_CreateUserCD = CustomerMST00004_CreateUserCD; }

        public Timestamp getCustomerMST00004_CreateDate() { return CustomerMST00004_CreateDate; }
        public void setCustomerMST00004_CreateDate(Timestamp CustomerMST00004_CreateDate) { this.CustomerMST00004_CreateDate = CustomerMST00004_CreateDate; }

        public String getCustomerMST00004_UpdateUserCD() { return CustomerMST00004_UpdateUserCD; }
        public void setCustomerMST00004_UpdateUserCD(String CustomerMST00004_UpdateUserCD) { this.CustomerMST00004_UpdateUserCD = CustomerMST00004_UpdateUserCD; }

        public Timestamp getCustomerMST00004_UpdateDate() { return CustomerMST00004_UpdateDate; }
        public void setCustomerMST00004_UpdateDate(Timestamp CustomerMST00004_UpdateDate) { this.CustomerMST00004_UpdateDate = CustomerMST00004_UpdateDate; }

        public String getPiTrnMode() { return piTrnMode; }
        public void setPiTrnMode(String piTrnMode) { this.piTrnMode = piTrnMode; }

        public String getPiCustomerCd() { return piCustomerCd; }
        public void setPiCustomerCd(String piCustomerCd) { this.piCustomerCd = piCustomerCd; }

        public Boolean getRes() { return Res; }
        public void setRes(Boolean Res) { this.Res = Res; }
    }
}
