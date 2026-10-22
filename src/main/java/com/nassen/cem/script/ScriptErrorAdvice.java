package com.nassen.cem.script;

import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * エラー画面（自動生成 / auto-generated）。
 *
 * <p>画面のイベントのエラーは各画面の Form.Error（ない場合は画面にメッセージ）で処理する。ここでは次の場合にエラー画面（templates/error.tpl）を表示する：</p>
 * <ul>
 *   <li>イベント以外で捕まえていないエラー（@ControllerAdvice）：ログに出し、Commit していない DB の更新は取り消す</li>
 *   <li>Form.Load のエラー：画面をまだ表示できないため（Form.Error で別の画面に移った場合はその画面）</li>
 * </ul>
 * <p>エラー画面の見た目は templates/error.tpl を変更する（再生成で上書きしない）。手動編集は再生成で失われます。</p>
 */
@ControllerAdvice
public class ScriptErrorAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ScriptErrorAdvice.class);

    /** 捕まえていないエラー：ログ（404 など HTTP の状態のエラーは状態だけ）＋ rollback ＋ エラー画面。 */
    @ExceptionHandler(Exception.class)
    public String handle(Exception ex, Model model, HttpServletResponse response) {
        int status = ex instanceof ErrorResponse error ? error.getStatusCode().value() : HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        RuntimeException error = ex instanceof RuntimeException runtime ? runtime : new IllegalStateException(ex.getMessage(), ex);
        if (status >= 500) {
            ScriptLib.caught(error);
            LOG.error("捕まえていないエラー（{}）", ScriptLib.errCode(error), ex);
        }
        ScriptDb.end();
        return page(model, error, response, status);
    }

    /** エラー画面を表示する（ex の種類・説明・画面・イベント・日時）。 */
    public static String page(Model model, RuntimeException ex, HttpServletResponse response) {
        return page(model, ex, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    private static String page(Model model, RuntimeException ex, HttpServletResponse response, int status) {
        if (!response.isCommitted()) {
            response.setStatus(status);
        }
        model.addAttribute("status", status);
        model.addAttribute("errorCode", status == 404 ? "ページがありません" : ScriptLib.errCode(ex));
        model.addAttribute("errorDescription", status == 404 ? "指定したページはありません。" : ScriptLib.errDescription(ex));
        model.addAttribute("errorScreen", ScriptLib.errScreen(ex));
        model.addAttribute("errorEvent", ScriptLib.errEvent(ex));
        model.addAttribute("errorTime", ScriptLib.errTime(ex));
        return "error";
    }
}
