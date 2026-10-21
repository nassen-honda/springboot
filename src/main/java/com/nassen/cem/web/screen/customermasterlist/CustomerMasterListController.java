package com.nassen.cem.web.screen.customermasterlist;

import com.nassen.cem.entity.Mst00004;
import com.nassen.cem.script.ScreenFlow;
import com.nassen.cem.script.ScriptDb;
import com.nassen.cem.script.ScriptErrorAdvice;
import com.nassen.cem.script.ScriptLib;
import com.nassen.cem.script.ScriptMessages;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
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
 * 取引先マスタ一覧画面コントローラ（自動生成 / auto-generated）。
 *
 * <p>コードジェネレータにより生成されました。手動編集は再生成で失われます。</p>
 */
@Controller
@RequestMapping("/CustomerMasterList")
public class CustomerMasterListController {

    /** 画面テンプレート（Thymeleaf）のビュー名。 */
    private static final String VIEW = "screen/CustomerMasterList";

    /** 画面 ID（画面の遷移・フォームの保持に使う）。 */
    private static final String SCREEN = "CustomerMasterList";

    private final ScreenFlow flow;

    private final SmartValidator validator;

    public CustomerMasterListController(ScreenFlow flow, SmartValidator validator) {
        this.flow = flow;
        this.validator = validator;
    }

    /** フォーム（セッションに保持し、送信された値を設定する）。 */
    @ModelAttribute("form")
    public CustomerMasterListForm form() {
        return flow.form(SCREEN, CustomerMasterListForm::new);
    }

    /** 画面の MessageView に表示するメッセージ（リクエストごとに空から始める）。 */
    @ModelAttribute("messages")
    public ScriptMessages messages() {
        return new ScriptMessages();
    }

    /** 送信された値を設定しない項目（インターフェース用の in の変数、一覧の入力できない列）。 */
    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("Customers[*].mst00004CustomerCD", "Customers[*].mst00004CustomerCD.*", "Customers[*].mst00004CustomerCD[*", "Customers[*].mst00004CustomerInvoiceCD", "Customers[*].mst00004CustomerInvoiceCD.*", "Customers[*].mst00004CustomerInvoiceCD[*", "Customers[*].mst00004CustomerKbn", "Customers[*].mst00004CustomerKbn.*", "Customers[*].mst00004CustomerKbn[*", "Customers[*].mst00004CustomerNM", "Customers[*].mst00004CustomerNM.*", "Customers[*].mst00004CustomerNM[*", "Customers[*].mst00004CustomerOmission", "Customers[*].mst00004CustomerOmission.*", "Customers[*].mst00004CustomerOmission[*", "Customers[*].mst00004AreaCD", "Customers[*].mst00004AreaCD.*", "Customers[*].mst00004AreaCD[*", "Customers[*].mst00004Address1", "Customers[*].mst00004Address1.*", "Customers[*].mst00004Address1[*", "Customers[*].mst00004Address2", "Customers[*].mst00004Address2.*", "Customers[*].mst00004Address2[*", "Customers[*].mst00004Address3", "Customers[*].mst00004Address3.*", "Customers[*].mst00004Address3[*", "Customers[*].mst00004Tel", "Customers[*].mst00004Tel.*", "Customers[*].mst00004Tel[*", "Customers[*].mst00004Fax", "Customers[*].mst00004Fax.*", "Customers[*].mst00004Fax[*", "Customers[*].mst00004Zip", "Customers[*].mst00004Zip.*", "Customers[*].mst00004Zip[*", "Customers[*].mst00004FromPlaceCD", "Customers[*].mst00004FromPlaceCD.*", "Customers[*].mst00004FromPlaceCD[*", "Customers[*].mst00004ToPlaceCD", "Customers[*].mst00004ToPlaceCD.*", "Customers[*].mst00004ToPlaceCD[*", "Customers[*].mst00004PaymentTermsCD", "Customers[*].mst00004PaymentTermsCD.*", "Customers[*].mst00004PaymentTermsCD[*", "Customers[*].mst00004DeliveryLocation", "Customers[*].mst00004DeliveryLocation.*", "Customers[*].mst00004DeliveryLocation[*", "Customers[*].mst00004TradeTermsCD", "Customers[*].mst00004TradeTermsCD.*", "Customers[*].mst00004TradeTermsCD[*", "Customers[*].mst00004Currency", "Customers[*].mst00004Currency.*", "Customers[*].mst00004Currency[*", "Customers[*].mst00004SectionNM", "Customers[*].mst00004SectionNM.*", "Customers[*].mst00004SectionNM[*", "Customers[*].mst00004UserCD", "Customers[*].mst00004UserCD.*", "Customers[*].mst00004UserCD[*", "Customers[*].mst00004MailAddress", "Customers[*].mst00004MailAddress.*", "Customers[*].mst00004MailAddress[*", "Customers[*].mst00004TaxRoundKBN", "Customers[*].mst00004TaxRoundKBN.*", "Customers[*].mst00004TaxRoundKBN[*", "Customers[*].mst00004TaxCalcKBN", "Customers[*].mst00004TaxCalcKBN.*", "Customers[*].mst00004TaxCalcKBN[*", "Customers[*].mst00004InvoiceCreateFlg", "Customers[*].mst00004InvoiceCreateFlg.*", "Customers[*].mst00004InvoiceCreateFlg[*", "Customers[*].mst00004DelFlg", "Customers[*].mst00004DelFlg.*", "Customers[*].mst00004DelFlg[*", "Customers[*].mst00004CreateUserCD", "Customers[*].mst00004CreateUserCD.*", "Customers[*].mst00004CreateUserCD[*", "Customers[*].mst00004CreateDate", "Customers[*].mst00004CreateDate.*", "Customers[*].mst00004CreateDate[*", "Customers[*].mst00004UpdateUserCD", "Customers[*].mst00004UpdateUserCD.*", "Customers[*].mst00004UpdateUserCD[*", "Customers[*].mst00004UpdateDate", "Customers[*].mst00004UpdateDate.*", "Customers[*].mst00004UpdateDate[*");
    }

    /** 初期表示（_return：Call で開いた画面から戻ったとき）。 */
    @GetMapping
    public String index(@RequestParam(name = "_return", required = false) String returned, Model model,
                        HttpServletResponse response) {
        if (returned != null) {
            CustomerMasterListForm form = flow.form(SCREEN, CustomerMasterListForm::new);
            model.addAttribute("form", form);
            if (flow.applyReturn(SCREEN, returned)) {
                // 戻ったときの処理（Event 画面ID.Return）はない
            }
            return next(form, model, response);
        }

        CustomerMasterListForm form = flow.resetForm(SCREEN, new CustomerMasterListForm());
        model.addAttribute("form", form);
        return next(form, model, response);
    }

    /** 送信：_event（ボタン・一覧の行・セルのボタン / リンクのイベント。セルは「イベント名:行番号」）がない場合は入力チェックをして登録する。 */
    @PostMapping
    public String submit(@ModelAttribute("form") CustomerMasterListForm form,
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
            return "redirect:/CustomerMasterList/complete";
        }

        if ("btnSearch1_Click".equals(event) || "btnClear1_Click".equals(event)) {
            validator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                return invalid(form, bindingResult, model);
            }
        }

        CustomerMasterListEvents events = new CustomerMasterListEvents(form, flow);
        int eventRow = row == null ? 0 : row;
        String eventName = event;
        run(model, events, eventName, () -> {
            switch (eventName) {
                case "btnSearch1_Click" -> {
                    ScriptLib.enter(SCREEN, "btnSearch1.Click");
                    events.btnSearch1_Click();
                }
                case "btnClear1_Click" -> {
                    ScriptLib.enter(SCREEN, "btnClear1.Click");
                    events.btnClear1_Click();
                }
                case "Customers_btnDetail_Click" -> {
                    ScriptLib.enter(SCREEN, "Customers.btnDetail.Click");
                    events.Customers_btnDetail_Click(eventRow);
                }
                case "Customers_btnEdit_Click" -> {
                    ScriptLib.enter(SCREEN, "Customers.btnEdit.Click");
                    events.Customers_btnEdit_Click(eventRow);
                }
                case "Customers_btnCopy_Click" -> {
                    ScriptLib.enter(SCREEN, "Customers.btnCopy.Click");
                    events.Customers_btnCopy_Click(eventRow);
                }
                case "Customers_btnDelete_Click" -> {
                    ScriptLib.enter(SCREEN, "Customers.btnDelete.Click");
                    events.Customers_btnDelete_Click(eventRow);
                }
                case "UserCD_Popup" -> {
                    ScriptLib.enter(SCREEN, "UserCD.Popup");
                    events.UserCD_Popup();
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
    private String next(CustomerMasterListForm form, Model model, HttpServletResponse response) {
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
    private void globalEvents(CustomerMasterListForm form, Model model, java.util.List<ScreenFlow.GlobalEvent> received) {
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
    private String invalid(CustomerMasterListForm form, BindingResult bindingResult, Model model) {
        ScriptMessages messages = messages(model);
        bindingResult.getAllErrors().forEach(error ->
            messages.add("Error", error.getDefaultMessage(), error instanceof FieldError field ? field.getField() : null));
        return view(form, model);
    }

    /** 画面を表示する：一覧の行ごとの状態を作り直す（Event 一覧.RowLoad を行ごとに実行する）。 */
    private String view(CustomerMasterListForm form, Model model) {
        // 行ごとの処理（Event 一覧.RowLoad）はない
        return VIEW;
    }

    /** エラーのログ。 */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(CustomerMasterListController.class);

    /**
     * イベントを実行する。Event 1 つが 1 トランザクション：Commit していない DB の更新は取り消す（自動では commit しない）。
     * 捕まえていないエラーは rollback してから Form.Error（ない場合は既定の処理）。Msg のメッセージを画面に表示する。
     * 捕まえていないエラーがあった場合はそのエラーを返す（Form.Load：画面を表示できない場合はエラー画面）。
     */
    private RuntimeException run(Model model, CustomerMasterListEvents events, String event, Runnable action) {
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
    private void error(Model model, CustomerMasterListEvents events, RuntimeException ex) {
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
    @ModelAttribute("CustomerKbnItems")
    public Map<String, String> CustomerKbnItems() {
        Map<String, String> items = new LinkedHashMap<>();
        items.put("01", "01:得意先");
        items.put("02", "02:仕入先");
        items.put("03", "03:業者");
        return items;
    }

    // ===== 画面フォーム（部品の集合 / form parts） =====
    /** 取引先マスタ一覧画面フォーム。 */
    public static class CustomerMasterListForm implements java.io.Serializable {

        private static final long serialVersionUID = 1L;

        /** 取引先コード */
        @Size(max = 10)
        private String CustomerCD;

        /** 取引先名 */
        @Size(max = 100)
        private String CustomerNM;

        /** 取引先略称 */
        @Size(max = 100)
        private String CustomerOmission;

        /** ユーザコード */
        @Size(max = 10)
        private String UserCD;

        /** ユーザ名 */
        @Size(max = 50)
        private String UserName;

        /** 日付 */
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate CreateDate;

        /** 取引先区分 */
        private String CustomerKbn;

        /** 取引先s（一覧） */
        private List<Mst00004> Customers = new ArrayList<>();

        public String getCustomerCD() { return CustomerCD; }
        public void setCustomerCD(String CustomerCD) { this.CustomerCD = CustomerCD; }

        public String getCustomerNM() { return CustomerNM; }
        public void setCustomerNM(String CustomerNM) { this.CustomerNM = CustomerNM; }

        public String getCustomerOmission() { return CustomerOmission; }
        public void setCustomerOmission(String CustomerOmission) { this.CustomerOmission = CustomerOmission; }

        public String getUserCD() { return UserCD; }
        public void setUserCD(String UserCD) { this.UserCD = UserCD; }

        public String getUserName() { return UserName; }
        public void setUserName(String UserName) { this.UserName = UserName; }

        public LocalDate getCreateDate() { return CreateDate; }
        public void setCreateDate(LocalDate CreateDate) { this.CreateDate = CreateDate; }

        public String getCustomerKbn() { return CustomerKbn; }
        public void setCustomerKbn(String CustomerKbn) { this.CustomerKbn = CustomerKbn; }

        public List<Mst00004> getCustomers() { return Customers; }
        public void setCustomers(List<Mst00004> Customers) { this.Customers = Customers; }

        /** 画面項目の状態（コードで設定したプロパティ）。 */
        private final ScriptLib.ViewState viewState = new ScriptLib.ViewState();

        public ScriptLib.ViewState getViewState() { return viewState; }

        /** 取引先s（一覧）の行ごとの状態（Event Customers.RowLoad で設定したプロパティ）。 */
        private final ScriptLib.GridState CustomersRowState = new ScriptLib.GridState();

        public ScriptLib.GridState getCustomersRowState() { return CustomersRowState; }
    }
}
