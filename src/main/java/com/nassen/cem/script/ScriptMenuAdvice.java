package com.nassen.cem.script;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * サイドメニュー（自動生成 / auto-generated）。
 *
 * <p>すべての画面の model に次を入れる。基本テンプレート（templates/base）のサイドメニューが使う：</p>
 * <ul>
 *   <li>sideMenu：モジュール SideMenu（Sub Call で out の変数 Menu に MenuItem を追加する）の結果。モジュールがない・エラーの場合は空</li>
 *   <li>activeScreen：今の画面の ID（URL の最初の部分。例：/orderList → orderList）</li>
 * </ul>
 * <p>メニューの項目はモジュール SideMenu のコードで変更する。手動編集は再生成で失われます。</p>
 */
@ControllerAdvice
public class ScriptMenuAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ScriptMenuAdvice.class);

    /** サイドメニューのモジュール（ない場合は null）。 */
    private static final Class<?> MODULE = find("com.nassen.cem.module.SideMenu");

    private static Class<?> find(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    /** サイドメニュー：モジュール SideMenu を呼び、out の変数 Menu を返す。 */
    @ModelAttribute("sideMenu")
    public List<?> sideMenu() {
        if (MODULE == null) {
            return List.of();
        }
        try {
            Object module = MODULE.getDeclaredConstructor().newInstance();
            MODULE.getMethod("call").invoke(module);
            return MODULE.getField("Menu").get(module) instanceof List<?> menu ? menu : List.of();
        } catch (ReflectiveOperationException | RuntimeException ex) {
            LOG.error("サイドメニュー（モジュール SideMenu）を作れません", ex);
            return List.of();
        }
    }

    /** 今の画面の ID：URL（アプリのパスの後）の最初の部分。 */
    @ModelAttribute("activeScreen")
    public String activeScreen(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = path.split("/");
        return parts.length > 1 ? parts[1] : "";
    }
}
