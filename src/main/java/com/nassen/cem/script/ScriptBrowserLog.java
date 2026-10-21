package com.nassen.cem.script;

import java.io.IOException;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ブラウザー側の Log をサーバーのログに出す（開発の実行環境だけ。自動生成 / auto-generated）。手動編集は再生成で失われます。
 *
 * <p>application.properties の script.log.browser=true の場合だけ使う：画面に Cookie の script-log=1 を送り、
 * ブラウザー（scriptlib.ts）は Log の内容を /script/log に送る。ログは「【画面 ID 行 行目】（ブラウザー）内容」。</p>
 */
@Component
@ConditionalOnProperty(name = "script.log.browser", havingValue = "true")
public class ScriptBrowserLog extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        boolean has = false;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                has |= "script-log".equals(cookie.getName());
            }
        }
        if (!has) {
            Cookie cookie = new Cookie("script-log", "1");
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        chain.doFilter(request, response);
    }

    /** ブラウザーの Log を受け取る。 */
    @RestController
    @ConditionalOnProperty(name = "script.log.browser", havingValue = "true")
    public static class Endpoint {

        @PostMapping("/script/log")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void log(@RequestBody Map<String, Object> body) {
            Object line = body.get("line");
            ScriptLib.logBrowser(String.valueOf(body.getOrDefault("level", "Info")), String.valueOf(body.getOrDefault("screen", "")),
                line instanceof Number n ? n.intValue() : 0, String.valueOf(body.getOrDefault("text", "")));
        }
    }
}
