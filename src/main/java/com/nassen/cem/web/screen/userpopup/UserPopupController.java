package com.nassen.cem.web.screen.userpopup;

import com.nassen.cem.entity.Mst00002;
import com.nassen.cem.script.ScreenFlow;
import com.nassen.cem.script.ScriptDb;
import com.nassen.cem.script.ScriptErrorAdvice;
import com.nassen.cem.script.ScriptLib;
import com.nassen.cem.script.ScriptMessages;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
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
 * ユーザ選択画面画面コントローラ（自動生成 / auto-generated）。
 *
 * <p>コードジェネレータにより生成されました。手動編集は再生成で失われます。</p>
 */
@Controller
@RequestMapping("/UserPopup")
public class UserPopupController {

    /** 画面テンプレート（Thymeleaf）のビュー名。 */
    private static final String VIEW = "screen/UserPopup";

    /** 画面 ID（画面の遷移・フォームの保持に使う）。 */
    private static final String SCREEN = "UserPopup";

    // ポップアップの画面：Call で開いたとき、呼び出し元の画面の上のダイアログ（幅・高さ）に表示する
    static {
        ScreenFlow.registerPopup(SCREEN, "800px", "600px");
    }

    private final ScreenFlow flow;

    private final SmartValidator validator;

    public UserPopupController(ScreenFlow flow, SmartValidator validator) {
        this.flow = flow;
        this.validator = validator;
    }

    /** フォーム（セッションに保持し、送信された値を設定する）。 */
    @ModelAttribute("form")
    public UserPopupForm form() {
        return flow.form(SCREEN, UserPopupForm::new);
    }

    /** 画面の MessageView に表示するメッセージ（リクエストごとに空から始める）。 */
    @ModelAttribute("messages")
    public ScriptMessages messages() {
        return new ScriptMessages();
    }

    /** 送信された値を設定しない項目（インターフェース用の in の変数、一覧の入力できない列）。 */
    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("Users[*].mst00002UserCD", "Users[*].mst00002UserCD.*", "Users[*].mst00002UserCD[*", "Users[*].mst00002UserNM", "Users[*].mst00002UserNM.*", "Users[*].mst00002UserNM[*", "Users[*].mst00002LoginUsr", "Users[*].mst00002LoginUsr.*", "Users[*].mst00002LoginUsr[*", "Users[*].mst00002LoginPwd", "Users[*].mst00002LoginPwd.*", "Users[*].mst00002LoginPwd[*", "Users[*].mst00002MailAddress", "Users[*].mst00002MailAddress.*", "Users[*].mst00002MailAddress[*", "Users[*].mst00002UserRole", "Users[*].mst00002UserRole.*", "Users[*].mst00002UserRole[*", "Users[*].mst00002SectionCD", "Users[*].mst00002SectionCD.*", "Users[*].mst00002SectionCD[*", "Users[*].mst00002DelFlg", "Users[*].mst00002DelFlg.*", "Users[*].mst00002DelFlg[*", "Users[*].mst00002CreateUserCD", "Users[*].mst00002CreateUserCD.*", "Users[*].mst00002CreateUserCD[*", "Users[*].mst00002CreateDate", "Users[*].mst00002CreateDate.*", "Users[*].mst00002CreateDate[*", "Users[*].mst00002UpdateUserCD", "Users[*].mst00002UpdateUserCD.*", "Users[*].mst00002UpdateUserCD[*", "Users[*].mst00002UpdateDate", "Users[*].mst00002UpdateDate.*", "Users[*].mst00002UpdateDate[*", "Users[*].scriptRefs", "Users[*].scriptRefs.*", "Users[*].scriptRefs[*");
    }

    /** 初期表示（_return：Call で開いた画面から戻ったとき）。 */
    @GetMapping
    public String index(@RequestParam(name = "_return", required = false) String returned, Model model,
                        HttpServletResponse response) {
        if (returned != null) {
            UserPopupForm form = flow.form(SCREEN, UserPopupForm::new);
            model.addAttribute("form", form);
            if (flow.applyReturn(SCREEN, returned)) {
                // 戻ったときの処理（Event 画面ID.Return）はない
            }
            return next(form, model, response);
        }

        UserPopupForm form = flow.resetForm(SCREEN, new UserPopupForm());
        model.addAttribute("form", form);

        // 画面ID.Call(...) で開かれた：in / inout の引数を設定する
        Object[] arguments = flow.arguments(SCREEN);
        if (arguments != null) {
            form.setUserCD((String) arguments[0]);
        }

        UserPopupEvents loadEvents = new UserPopupEvents(form, flow);
        RuntimeException failed = run(model, loadEvents, "Form.Load", loadEvents::Form_Load);
        if (failed != null && !flow.navigating()) {
            // Form.Load のエラー：画面をまだ表示できないため、エラー画面を出す（Form.Error で別の画面に移った場合はその画面）
            return ScriptErrorAdvice.page(model, failed, response);
        }
        return next(form, model, response);
    }

    /** 送信：_event（ボタン・一覧の行・セルのボタン / リンクのイベント。セルは「イベント名:行番号」）がない場合は入力チェックをして登録する。 */
    @PostMapping
    public String submit(@ModelAttribute("form") UserPopupForm form,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(name = "_event", required = false) String event,
                         @RequestParam(name = "_row", required = false) Integer row,
                         @RequestParam(name = "_change", required = false) String change,
                         @RequestParam(name = "_changeRow", required = false) Integer changeRow,
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

        // 値を変えてすぐボタンを押した（_change）：先に Change / LostFocus を実行する（エラーの場合・画面を移る場合はボタンのイベントを実行しない）
        if (change != null && !change.isEmpty() && !change.equals(event)) {
            UserPopupEvents changeEvents = new UserPopupEvents(form, flow);
            int changeRowNo = changeRow == null ? 0 : changeRow;
            String changeName = change;
            RuntimeException failed = run(model, changeEvents, changeName, () -> {
                switch (changeName) {
                    case "UserCD_Change" -> {
                        ScriptLib.enter(SCREEN, "UserCD.Change");
                        changeEvents.UserCD_Change();
                    }
                    case "UserNm_Change" -> {
                        ScriptLib.enter(SCREEN, "UserNm.Change");
                        changeEvents.UserNm_Change();
                    }
                    default -> { }
                }
            });
            if (failed != null || flow.navigating() || !messages(model).getError().isEmpty()) {
                return next(form, model, response);
            }
        }

        if (event == null || event.isEmpty()) {
            validator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                // 入力エラー時は同一画面を再表示
                return invalid(form, bindingResult, model);
            }
            // TODO: 業務処理をここに実装
            return "redirect:/UserPopup/complete";
        }

        if ("btnOk_Click".equals(event) || "btnCancel_Click".equals(event)) {
            validator.validate(form, bindingResult);
            if (bindingResult.hasErrors()) {
                return invalid(form, bindingResult, model);
            }
        }

        UserPopupEvents events = new UserPopupEvents(form, flow);
        int eventRow = row == null ? 0 : row;
        String eventName = event;
        run(model, events, eventName, () -> {
            switch (eventName) {
                case "Users_RowClick" -> {
                    ScriptLib.enter(SCREEN, "Users.RowClick");
                    events.Users_RowClick(eventRow);
                }
                case "btnOk_Click" -> {
                    ScriptLib.enter(SCREEN, "btnOk.Click");
                    events.btnOk_Click();
                }
                case "btnCancel_Click" -> {
                    ScriptLib.enter(SCREEN, "btnCancel.Click");
                    events.btnCancel_Click();
                }
                case "UserCD_Change" -> {
                    ScriptLib.enter(SCREEN, "UserCD.Change");
                    events.UserCD_Change();
                }
                case "UserNm_Change" -> {
                    ScriptLib.enter(SCREEN, "UserNm.Change");
                    events.UserNm_Change();
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
    private String next(UserPopupForm form, Model model, HttpServletResponse response) {
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
    private void globalEvents(UserPopupForm form, Model model, java.util.List<ScreenFlow.GlobalEvent> received) {
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
    private String invalid(UserPopupForm form, BindingResult bindingResult, Model model) {
        ScriptMessages messages = messages(model);
        bindingResult.getAllErrors().forEach(error ->
            messages.add("Error", error.getDefaultMessage(), error instanceof FieldError field ? field.getField() : null));
        return view(form, model);
    }

    /** 画面を表示する：一覧の行ごとの状態を作り直す（Event 一覧.RowLoad を行ごとに実行する）。 */
    private String view(UserPopupForm form, Model model) {
        // 行ごとの処理（Event 一覧.RowLoad）はない
        return VIEW;
    }

    /** エラーのログ。 */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(UserPopupController.class);

    /**
     * イベントを実行する。Event 1 つが 1 トランザクション：Commit していない DB の更新は取り消す（自動では commit しない）。
     * 捕まえていないエラーは rollback してから Form.Error（ない場合は既定の処理）。Msg のメッセージを画面に表示する。
     * 捕まえていないエラーがあった場合はそのエラーを返す（Form.Load：画面を表示できない場合はエラー画面）。
     */
    private RuntimeException run(Model model, UserPopupEvents events, String event, Runnable action) {
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
    private void error(Model model, UserPopupEvents events, RuntimeException ex) {
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

    // ===== 画面フォーム（部品の集合 / form parts） =====
    /** ユーザ選択画面画面フォーム。 */
    public static class UserPopupForm implements java.io.Serializable {

        private static final long serialVersionUID = 1L;

        /** ユーザコード */
        @Size(max = 10)
        private String UserCD;

        /** ユーザ名 */
        @Size(max = 50)
        private String UserNm;

        /** 担当者マスタ（一覧） */
        private List<Mst00002> Users = new ArrayList<>();

        /** 担当者マスタ */
        private Mst00002 User = new Mst00002();

        public String getUserCD() { return UserCD; }
        public void setUserCD(String UserCD) { this.UserCD = UserCD; }

        public String getUserNm() { return UserNm; }
        public void setUserNm(String UserNm) { this.UserNm = UserNm; }

        public List<Mst00002> getUsers() { return Users; }
        public void setUsers(List<Mst00002> Users) { this.Users = Users; }

        public Mst00002 getUser() { return User; }
        public void setUser(Mst00002 User) { this.User = User; }

        /** 画面項目の状態（コードで設定したプロパティ）。 */
        private final ScriptLib.ViewState viewState = new ScriptLib.ViewState();

        public ScriptLib.ViewState getViewState() { return viewState; }

        /** 担当者マスタ（一覧）の行ごとの状態（Event Users.RowLoad で設定したプロパティ）。 */
        private final ScriptLib.GridState UsersRowState = new ScriptLib.GridState();

        public ScriptLib.GridState getUsersRowState() { return UsersRowState; }
    }
}
