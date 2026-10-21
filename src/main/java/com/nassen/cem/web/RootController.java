package com.nassen.cem.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ルート（/）の入口。
 *
 * <p>生成された画面には / のマッピングがないため、最初に開く画面（取引先マスタ一覧）へ移動する。
 * コード生成の対象外の追加クラス（手動で追加）。</p>
 */
@Controller
public class RootController {

    /** / を取引先マスタ一覧へ移動する（エラー画面の「トップへ戻る」もここに来る）。 */
    @GetMapping("/")
    public String index() {
        return "redirect:/CustomerMasterList";
    }
}
