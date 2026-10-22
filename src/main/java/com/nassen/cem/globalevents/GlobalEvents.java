package com.nassen.cem.globalevents;

import com.nassen.cem.script.ScreenFlow;
import com.nassen.cem.script.ScriptMessage;
import java.util.List;

/**
 * 画面の間で送るイベント（自動生成 / auto-generated）：BaseObjectCustomized/GlobalEvents の定義ファイルのイベントごとに record を 1 つ。
 * 送る側のコードは GlobalEvents.名前(…)、受け取る画面のコードは Event GlobalEvents.名前(&引数, …)。
 * 送ったイベントはセッション（ScreenFlow）に保持し、次に表示する画面が表示する前に実行する。
 *
 * <p>手動編集は再生成で失われます。</p>
 */
public final class GlobalEvents {

    private GlobalEvents() {
    }

    /** メッセージを渡す（例：ポップアップの入力チェックのエラーを親画面の MessageView に出す）（Messages.events.json）。 */
    public record PopupMsg(List<ScriptMessage> errorMsgs) implements ScreenFlow.GlobalEvent {
    }
}
