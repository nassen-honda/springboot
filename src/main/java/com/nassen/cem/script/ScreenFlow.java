package com.nassen.cem.script;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 画面の遷移（自動生成 / auto-generated）：画面ID.Call(...) で別の画面を開き、開いた画面が Close したら呼び出し元に戻って out / inout を書き戻す。
 * 各画面のフォーム（変数）もここ（セッション）に保持し、リクエストをまたいで変数の値を残す。
 *
 * <p>手動編集は再生成で失われます。</p>
 */
@Component
@SessionScope
public class ScreenFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 画面の呼び出し：呼び出し元、呼び出し先、引数、戻ったときの書き戻し、Close で返された値。 */
    public static class Frame implements Serializable {
        private static final long serialVersionUID = 1L;
        final String caller;
        final String callee;
        final Object[] arguments;
        final transient Consumer<Object[]> onReturn;
        final boolean popup;
        Object[] results;

        Frame(String caller, String callee, Object[] arguments, Consumer<Object[]> onReturn, boolean popup) {
            this.caller = caller;
            this.callee = callee;
            this.arguments = arguments;
            this.onReturn = onReturn;
            this.popup = popup;
        }
    }

    /** ポップアップの画面（画面の Popup が True）：画面 ID、ダイアログの幅・高さ。画面のテンプレートが ${popup.url} などで読む。 */
    public static final class Popup implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String screenId;
        private final String width;
        private final String height;

        Popup(String screenId, String width, String height) {
            this.screenId = screenId;
            this.width = width;
            this.height = height;
        }

        public String getScreenId() {
            return screenId;
        }

        public String getUrl() {
            return "/" + screenId;
        }

        public String getWidth() {
            return width;
        }

        public String getHeight() {
            return height;
        }
    }

    /** ポップアップの画面（画面 ID → ダイアログの大きさ）。Popup の画面のコントローラーが登録する。 */
    private static final Map<String, Popup> POPUPS = new ConcurrentHashMap<>();

    /** ポップアップの画面を登録する（Popup の画面のコントローラーの static の初期化）。 */
    public static void registerPopup(String screenId, String width, String height) {
        POPUPS.put(screenId, new Popup(screenId, width, height));
    }

    private final Deque<Frame> frames = new ArrayDeque<>();

    private final Map<String, Object> forms = new HashMap<>();

    private String redirect;

    /** 処理の後にダイアログで開くポップアップの画面（Popup の画面を Call した場合）。 */
    private Popup popup;

    /** 処理の後の移動をダイアログの外で行う（"parent"：ダイアログを開いた画面、"top"：ブラウザーのページ全体）。 */
    private String breakOut;

    // ===== GlobalEvents：画面の間でイベントを送る =====

    /** GlobalEvents のイベント（GlobalEvents.java の record。引数をセッションに保持するためシリアライズできる）。 */
    public interface GlobalEvent extends Serializable {
    }

    /** 届け先が決まったイベント：届ける画面、イベント、届かずに表示した画面の数（2 回目で捨てる）。 */
    private static final class PendingEvent implements Serializable {
        private static final long serialVersionUID = 1L;
        final String screenId;
        final GlobalEvent event;
        int age;

        PendingEvent(String screenId, GlobalEvent event) {
            this.screenId = screenId;
            this.event = event;
        }
    }

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(ScreenFlow.class);

    /** この処理で送ったイベント（処理の後で届け先を決める）。 */
    private final List<GlobalEvent> raised = new ArrayList<>();

    /** 届け先が決まったイベント（届け先の画面を表示する前に実行する）。 */
    private final List<PendingEvent> pending = new ArrayList<>();

    /** この処理の後に表示する画面（Call は開く画面、Close は呼び出し元、呼び出し元がない Close は ""。null はこの画面）。 */
    private String nextScreen;

    /** Event GlobalEvents.… を実行している（その中では Call・Close・GlobalEvents.… は使えない）。 */
    private boolean delivering;

    /** GlobalEvents.名前(...)：イベントを送る（この処理の後に表示する画面が、表示する前に Event GlobalEvents.名前 を実行する）。 */
    public synchronized void raise(GlobalEvent event) {
        if (delivering) {
            throw new IllegalStateException("Event GlobalEvents.… の中では GlobalEvents のイベントを送れません。");
        }
        raised.add(event);
    }

    /**
     * 処理の後：送ったイベントの届け先（この処理の後に表示する画面）を決め、displaying（この画面を表示する）の場合は、
     * この画面に届いたイベントを送った順に返す（返したものは消える）。届け先が表示されずにほかの画面を 2 回表示したイベントは捨てる。
     */
    public synchronized List<GlobalEvent> eventsFor(String screenId, boolean displaying) {
        String target = nextScreen != null ? nextScreen : screenId;
        nextScreen = null;
        for (GlobalEvent event : raised) {
            if (target.isEmpty()) {
                LOG.info("GlobalEvents の {} を捨てました（{} が Close したが、戻る画面がありません）", event.getClass().getSimpleName(), screenId);
            } else {
                pending.add(new PendingEvent(target, event));
            }
        }
        raised.clear();
        List<GlobalEvent> result = new ArrayList<>();
        if (!displaying) {
            return result;
        }
        for (Iterator<PendingEvent> i = pending.iterator(); i.hasNext(); ) {
            PendingEvent p = i.next();
            if (p.screenId.equals(screenId)) {
                result.add(p.event);
                i.remove();
            } else if (++p.age > 1) {
                LOG.info("GlobalEvents の {} を捨てました（届け先の {} が表示されませんでした）", p.event.getClass().getSimpleName(), p.screenId);
                i.remove();
            }
        }
        return result;
    }

    /** Event GlobalEvents.… の実行の始まり・終わり（その間は Call・Close・GlobalEvents.… を使えない）。 */
    public synchronized void delivering(boolean value) {
        delivering = value;
    }

    private void checkNotDelivering(String what) {
        if (delivering) {
            throw new IllegalStateException("Event GlobalEvents.… の中では " + what + " は使えません（画面を表示する前に実行するため）。");
        }
    }

    /** 画面のフォーム（なければ作る）。 */
    @SuppressWarnings("unchecked")
    public <T> T form(String screenId, Supplier<T> create) {
        return (T) forms.computeIfAbsent(screenId, k -> create.get());
    }

    /** 画面のフォームを新しくする（画面を開き直したとき）。 */
    public <T> T resetForm(String screenId, T form) {
        forms.put(screenId, form);
        return form;
    }

    /**
     * 画面ID.Call(...)：処理の後で callee を開く。callee が Close したら onReturn で out / inout を書き戻す。
     * callee がポップアップの画面の場合は、呼び出し元の画面をそのまま表示し、その上のダイアログで開く。
     * ダイアログの中の画面から普通の画面を開く場合は、ページ全体をその画面にする。
     */
    public void call(String caller, String callee, Object[] arguments, Consumer<Object[]> onReturn) {
        checkNotDelivering(callee + ".Call");
        nextScreen = callee;
        boolean fromPopup = inPopup(caller);
        Popup target = POPUPS.get(callee);
        frames.push(new Frame(caller, callee, arguments, onReturn, target != null));
        if (target != null) {
            popup = target;
        } else {
            redirect = "/" + callee;
            if (fromPopup) {
                breakOut = "top";
            }
        }
    }

    /** 画面がダイアログの中に表示されているか（ポップアップの画面として Call で開かれ、まだ Close していない）。 */
    public boolean inPopup(String screenId) {
        Frame frame = frames.peek();
        return frame != null && frame.popup && frame.callee.equals(screenId) && frame.results == null;
    }

    /** Call で開かれた画面の引数（Call で開かれていない場合は null）。 */
    public Object[] arguments(String screenId) {
        Frame frame = frames.peek();
        return frame != null && frame.callee.equals(screenId) && frame.results == null ? frame.arguments : null;
    }

    /** Close：インターフェースの引数の値を返し、呼び出し元に戻る（Call で開かれていない場合はトップへ）。 */
    public void close(String screenId, Object[] results) {
        checkNotDelivering("Close");
        Frame frame = frames.peek();
        nextScreen = frame != null && frame.callee.equals(screenId) ? frame.caller : "";
        if (frame != null && frame.callee.equals(screenId)) {
            frame.results = results;
            redirect = "/" + frame.caller + "?_return=" + screenId;
            if (frame.popup) {
                // ダイアログを閉じる：ダイアログを開いた画面（呼び出し元）を移動する
                breakOut = "parent";
            }
        } else {
            redirect = "/";
        }
        forms.remove(screenId);
    }

    /** 呼び出し元に戻ったとき：out / inout を書き戻す。書き戻した場合は true（その後 Event 画面ID.Return を実行する）。 */
    public boolean applyReturn(String screenId, String returned) {
        Frame frame = frames.peek();
        if (frame == null || !frame.caller.equals(screenId) || !frame.callee.equals(returned) || frame.results == null) {
            return false;
        }
        frames.pop();
        if (frame.onReturn != null) {
            frame.onReturn.accept(frame.results);
        }
        return true;
    }

    /** 処理の後に別の画面に移る（Call / Close した）。 */
    public boolean navigating() {
        return redirect != null;
    }

    /** 処理の後に移動する画面（Call / Close した場合）。取り出すと消える。 */
    public String takeRedirect() {
        String result = redirect;
        redirect = null;
        return result;
    }

    /** 移動をダイアログの外で行う場合の移動先のウィンドウ（"parent" / "top"。ダイアログの中でない場合は null）。取り出すと消える。 */
    public String takeBreakOut() {
        String result = breakOut;
        breakOut = null;
        return result;
    }

    /** 処理の後にダイアログで開くポップアップの画面（ない場合は null）。取り出すと消える。 */
    public Popup takePopup() {
        Popup result = popup;
        popup = null;
        return result;
    }

    /**
     * ダイアログの中の画面から、ダイアログの外の画面を移動する（url はアプリの中のパス）。
     * ダイアログ（iframe）の中には何も表示せず、移動先のウィンドウの location を置き換えるスクリプトを返す。
     */
    /**
     * 画面の部分更新の送信（ヘッダー X-Screen-Update。画面の補助スクリプトが fetch で送る）の場合：画面の移動は 302 ではなく
     * ヘッダー X-Screen-Location（と X-Screen-Target：top / parent / self）で返す（ブラウザーが移動先を 1 回だけ開く）。部分更新でない場合は false。
     */
    public static boolean partialRedirect(HttpServletResponse response, String target, String url) {
        org.springframework.web.context.request.RequestAttributes attributes =
            org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
        if (!(attributes instanceof org.springframework.web.context.request.ServletRequestAttributes servlet)
            || !"1".equals(servlet.getRequest().getHeader("X-Screen-Update"))) {
            return false;
        }
        String href = ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath();
        response.setHeader("X-Screen-Location", (href == null ? "" : href) + url);
        response.setHeader("X-Screen-Target", target);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        try {
            // 応答済みにする（コントローラーは null を返す。画面を表示しない）
            response.flushBuffer();
        } catch (IOException e) {
            throw new java.io.UncheckedIOException(e);
        }
        return true;
    }

    public static void breakOut(HttpServletResponse response, String target, String url) throws IOException {
        if (partialRedirect(response, target, url)) {
            return;
        }
        String href = ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath();
        href = (href == null ? "" : href) + url;
        String quoted = "'" + href.replace("\\", "\\\\").replace("'", "\\'").replace("<", "\\u003c") + "'";
        String window = "top".equals(target) ? "window.top" : "window.parent";
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"></head><body><script>"
            + window + ".location.replace(" + quoted + ");</script></body></html>");
        response.flushBuffer();
    }
}
