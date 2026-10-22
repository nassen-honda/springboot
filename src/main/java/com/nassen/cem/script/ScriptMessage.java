package com.nassen.cem.script;

import java.io.Serializable;

/**
 * メッセージ 1 件（自動生成 / auto-generated）：コードの Message 型（Level・Description・Item）。
 * Msg(&m) で画面の MessageView に表示する。GlobalEvents で画面の間で受け渡すためシリアライズできる。
 *
 * <p>手動編集は再生成で失われます。</p>
 */
public class ScriptMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /** レベル：Error / Warning / Info（MsgLevel.Error など）。 */
    private String level = "Info";

    /** メッセージ。 */
    private String description = "";

    /** 項目の名前（MessageView でクリックするとその入力欄へ移動する。ない場合は空）。 */
    private String item = "";

    public ScriptMessage() {
    }

    /** NewMessage(レベル, 文字列[, 項目])。 */
    public static ScriptMessage of(String level, String description, String item) {
        ScriptMessage message = new ScriptMessage();
        message.setLevel(level);
        message.setDescription(description);
        message.setItem(item);
        return message;
    }

    /** レベルの決まった書き方（Error / Warning / Info。それ以外は Info）。 */
    public static String normalizeLevel(String level) {
        if ("error".equalsIgnoreCase(level)) {
            return "Error";
        }
        if ("warning".equalsIgnoreCase(level)) {
            return "Warning";
        }
        return "Info";
    }

    /** 同じ内容のコピー（表示するメッセージを、あとで変数を変更しても変わらないようにする）。 */
    public ScriptMessage copy() {
        return of(level, description, item);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = normalizeLevel(level);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item == null ? "" : item;
    }

    @Override
    public String toString() {
        return level + ": " + description;
    }
}
