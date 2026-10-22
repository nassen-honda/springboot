package com.nassen.cem.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ステップ実行（自動生成 / auto-generated）。手動編集は再生成で失われます。
 *
 * <p>設定 → コード生成 → Java の「ステップ実行」を使う場合、生成したコードは文ごとに {@link #on} と {@link #stop} を調べ、
 * 止める場所なら {@link #pause} で変数の値を IDE に送って続きの指示を待つ。</p>
 * <p>application.properties の script.debug.port（開発の実行環境だけ出力する）がない場合は IDE につながず、止めない。</p>
 * <p>止めるのは 1 つずつ（ほかの処理が止める場所に来た場合は、前の処理が続きを始めるまで待つ）。</p>
 */
@Component
public class ScriptDebug implements DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(ScriptDebug.class);

    /** IDE とつながっている（生成したコードは文ごとにこれを見る。つながっていない場合は何もしない）。 */
    public static volatile boolean on;

    /** ブレークポイント（画面 ID・モジュール名:行）。 */
    private static volatile Set<String> breakpoints = Set.of();

    // ステップ：0 なし、1 次の文（over：同じか浅い呼び出しの深さ）、2 中へ（into：次の文）、3 出る（out：浅い深さ）
    private static volatile int stepMode;
    private static volatile int stepDepth;
    private static volatile Thread stepThread;

    // 1 つずつ止める
    private static final Object PAUSE = new Object();
    // 続きの指示（WAIT で守る）
    private static final Object WAIT = new Object();
    private static String command;
    private static volatile Writer out;

    // アプリの終了・再起動（devtools）：つなぎ直さない
    private static volatile boolean closed;
    private static volatile Socket current;

    // 最初のブレークポイントを受け取った（起動の直後に動く処理（バッチ）も止められるように、起動時に少し待つ）
    private static final java.util.concurrent.CountDownLatch READY = new java.util.concurrent.CountDownLatch(1);

    public ScriptDebug(@Value("${script.debug.port:0}") int port) {
        if (port > 0) {
            Thread thread = new Thread(() -> connect(port), "script-debug");
            thread.setDaemon(true);
            thread.start();
            try {
                READY.await(1500, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /** アプリの終了・再起動：IDE との接続を切る（止めている処理は続ける）。 */
    @Override
    public void destroy() {
        closed = true;
        Socket socket = current;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                // 閉じられない場合は何もしない
            }
        }
    }

    // IDE につなぐ（切れたらつなぎ直す）
    private static void connect(int port) {
        boolean reported = false;
        while (!closed) {
            try (Socket socket = new Socket()) {
                current = socket;
                socket.connect(new InetSocketAddress("127.0.0.1", port), 2000);
                socket.setTcpNoDelay(true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
                send("{\"type\":\"hello\"}");
                on = true;
                LOG.info("ステップ実行：IDE（ポート {}）につなぎました", port);
                reported = false;
                String line;
                while ((line = in.readLine()) != null) {
                    receive(line);
                }
            } catch (IOException e) {
                READY.countDown();
                if (!reported) {
                    LOG.debug("ステップ実行：IDE（ポート {}）につなげません：{}", port, e.getMessage());
                    reported = true;
                }
            } finally {
                boolean was = on;
                on = false;
                out = null;
                breakpoints = Set.of();
                stepMode = 0;
                stepThread = null;
                resume("continue");
                if (was) {
                    LOG.info("ステップ実行：IDE との接続が切れました");
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private static void receive(String line) {
        String[] parts = line.split("\t", -1);
        switch (parts[0]) {
            case "B" -> {
                Set<String> set = new HashSet<>();
                for (int i = 1; i < parts.length; i++) {
                    if (!parts[i].isEmpty()) {
                        set.add(parts[i].toLowerCase(java.util.Locale.ROOT));
                    }
                }
                breakpoints = set;
                READY.countDown();
            }
            case "R" -> resume(parts.length > 1 ? parts[1] : "continue");
            default -> {
            }
        }
    }

    private static void resume(String value) {
        synchronized (WAIT) {
            command = value;
            WAIT.notifyAll();
        }
    }

    private static synchronized void send(String json) throws IOException {
        Writer writer = out;
        if (writer != null) {
            writer.write(json);
            writer.write('\n');
            writer.flush();
        }
    }

    /** この文で止めるか（ブレークポイント、またはステップの次の文）。 */
    public static boolean stop(String unit, int line) {
        int mode = stepMode;
        if (mode != 0 && stepThread == Thread.currentThread()) {
            if (mode == 2) {
                return true;
            }
            int depth = depth();
            if ((mode == 1 && depth <= stepDepth) || (mode == 3 && depth < stepDepth)) {
                return true;
            }
        }
        Set<String> set = breakpoints;
        return !set.isEmpty() && set.contains((unit + ":" + line).toLowerCase(java.util.Locale.ROOT));
    }

    /** イベントの終わり（ScriptLib.leave）：このスレッドのステップを終える（次のイベントでは止めない）。 */
    public static void ended() {
        if (stepThread == Thread.currentThread()) {
            stepMode = 0;
            stepThread = null;
        }
    }

    /**
     * 止める：変数（名前は「名前\tタイプ[\t論理名]」）の値を IDE に送り、続きの指示を待つ。
     * names・values はローカル変数（引数・Event の引数・Catch の変数）、variables は画面・モジュールの変数（debugVariables_()：{ 名前の配列, 値の配列 }）。
     */
    public static void pause(String[] names, Object[] values, Object[] variables) {
        synchronized (PAUSE) {
            if (!on) {
                return;
            }
            Thread thread = Thread.currentThread();
            StringBuilder json = new StringBuilder("{\"type\":\"paused\"");
            List<Object[]> stack = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(frames -> frames
                .map(f -> {
                    Object[] place = ScriptLib.sourceLine(f.getClassName(), f.getLineNumber());
                    // runBatch・debugVariables_ はコードにない（生成したメソッド）
                    return place == null || f.getMethodName().equals("runBatch") || f.getMethodName().endsWith("_")
                        ? null : new Object[] { place[0], place[1], f.getMethodName() };
                })
                .filter(java.util.Objects::nonNull)
                .toList());
            if (!stack.isEmpty()) {
                json.append(",\"unit\":").append(quote(String.valueOf(stack.get(0)[0]))).append(",\"line\":").append(stack.get(0)[1]);
            }
            json.append(",\"thread\":").append(quote(thread.getName()));
            json.append(",\"stack\":[");
            for (int i = 0; i < stack.size(); i++) {
                Object[] frame = stack.get(i);
                json.append(i == 0 ? "" : ",").append("{\"unit\":").append(quote(String.valueOf(frame[0]))).append(",\"line\":").append(frame[1])
                    .append(",\"method\":").append(quote(String.valueOf(frame[2]))).append('}');
            }
            json.append("],\"vars\":[");
            // ローカル変数、画面・モジュールの変数の順（同じ名前はローカル変数）
            String[] screenNames = variables != null && variables.length > 1 ? (String[]) variables[0] : new String[0];
            Object[] screenValues = variables != null && variables.length > 1 ? (Object[]) variables[1] : new Object[0];
            List<String> allNames = new java.util.ArrayList<>(java.util.Arrays.asList(names));
            List<Object> allValues = new java.util.ArrayList<>(java.util.Arrays.asList(values));
            Set<String> seen = new HashSet<>();
            for (String name : names) {
                seen.add(name.split("\t", 2)[0].toLowerCase(java.util.Locale.ROOT));
            }
            for (int i = 0; i < screenNames.length && i < screenValues.length; i++) {
                if (!seen.contains(screenNames[i].split("\t", 2)[0].toLowerCase(java.util.Locale.ROOT))) {
                    allNames.add(screenNames[i]);
                    allValues.add(screenValues[i]);
                }
            }
            int count = 0;
            for (int i = 0; i < allNames.size() && i < allValues.size(); i++) {
                String[] nameType = allNames.get(i).split("\t", 3);
                String value;
                try {
                    value = ScriptLib.display(allValues.get(i));
                } catch (RuntimeException e) {
                    value = "（値を読めません：" + e.getMessage() + "）";
                }
                if (value.length() > 4000) {
                    value = value.substring(0, 4000) + "…";
                }
                json.append(count++ == 0 ? "" : ",").append("{\"name\":").append(quote(nameType[0]))
                    .append(",\"type\":").append(quote(nameType.length > 1 ? nameType[1] : ""))
                    .append(",\"caption\":").append(quote(nameType.length > 2 ? nameType[2] : ""))
                    .append(",\"value\":").append(quote(value)).append('}');
            }
            json.append("]}");

            String next;
            synchronized (WAIT) {
                command = null;
            }
            try {
                send(json.toString());
                synchronized (WAIT) {
                    while (command == null && on) {
                        WAIT.wait();
                    }
                    next = command == null ? "continue" : command;
                    command = null;
                }
            } catch (IOException e) {
                next = "continue";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                next = "continue";
            }

            switch (next) {
                case "over", "into", "out" -> {
                    stepMode = next.equals("over") ? 1 : next.equals("into") ? 2 : 3;
                    stepDepth = depth();
                    stepThread = thread;
                }
                default -> {
                    stepMode = 0;
                    stepThread = null;
                }
            }
        }
    }

    // 呼び出しの深さ（生成したクラスのメソッドの数）
    private static int depth() {
        return StackWalker.getInstance().walk(frames -> frames
            .filter(f -> ScriptLib.sourceLine(f.getClassName(), f.getLineNumber()) != null)
            .count()).intValue();
    }

    private static String quote(String s) {
        StringBuilder sb = new StringBuilder("\"");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.append('"').toString();
    }
}
