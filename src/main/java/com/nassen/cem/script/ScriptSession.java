package com.nassen.cem.script;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * コード（画面・モジュールの「コード」タブ）の Session 型（自動生成 / auto-generated）：HttpSession に文字列の値を保持する。
 *
 * <p>Set(キー, 値)、Get(キー)（ない場合は ""）、Remove(キー)。手動編集は再生成で失われます。</p>
 */
public class ScriptSession {

    private HttpSession session() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest().getSession(true);
    }

    /** Set(キー, 値)。 */
    public void set(String key, String value) {
        session().setAttribute(key, value);
    }

    /** Get(キー)：値（ない場合は ""）。 */
    public String get(String key) {
        Object value = session().getAttribute(key);
        return value == null ? "" : value.toString();
    }

    /** Remove(キー)。 */
    public void remove(String key) {
        session().removeAttribute(key);
    }
}
