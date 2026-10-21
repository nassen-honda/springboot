package com.nassen.cem.script;

import java.util.ArrayList;
import java.util.List;

/**
 * 画面の MessageView に表示するメッセージ（自動生成 / auto-generated）：model の messages。リクエストごとに空から始める。
 * Msg（Info）、Throw "…"・捕まえていないエラー（Error）、検索の件数超過（Warning）、入力チェック（Error。項目付き）。
 * 画面のテンプレートはレベルごとの一覧（error / warning / info）を表示する。
 *
 * <p>手動編集は再生成で失われます。</p>
 */
public class ScriptMessages {

    private final List<ScriptMessage> all = new ArrayList<>();

    /** メッセージを追加する（空のメッセージは追加しない）。 */
    public void add(ScriptMessage message) {
        if (message != null && message.getDescription() != null && !message.getDescription().isEmpty()) {
            all.add(message);
        }
    }

    public void add(String level, String description, String item) {
        add(ScriptMessage.of(level, description, item));
    }

    public void addAll(List<ScriptMessage> messages) {
        if (messages != null) {
            messages.forEach(this::add);
        }
    }

    /** すべてのメッセージ（書いた順）。 */
    public List<ScriptMessage> getAll() {
        return all;
    }

    /** Error のメッセージ。 */
    public List<ScriptMessage> getError() {
        return of("Error");
    }

    /** Warning のメッセージ。 */
    public List<ScriptMessage> getWarning() {
        return of("Warning");
    }

    /** Info のメッセージ。 */
    public List<ScriptMessage> getInfo() {
        return of("Info");
    }

    public boolean isEmpty() {
        return all.isEmpty();
    }

    /** Error のメッセージがある（折りたたむ MessageView も開いて表示する）。 */
    public boolean isHasError() {
        return all.stream().anyMatch(m -> "Error".equals(m.getLevel()));
    }

    /** 件数（例：エラー 2 件・警告 1 件）。折りたたんだ MessageView の 1 行。 */
    public String getSummary() {
        List<String> parts = new ArrayList<>();
        for (String[] level : new String[][] { { "Error", "エラー" }, { "Warning", "警告" }, { "Info", "お知らせ" } }) {
            int count = of(level[0]).size();
            if (count > 0) {
                parts.add(level[1] + " " + count + " 件");
            }
        }
        return String.join("・", parts);
    }

    private List<ScriptMessage> of(String level) {
        return all.stream().filter(m -> level.equals(m.getLevel())).toList();
    }
}
