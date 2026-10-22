package com.nassen.cem.script;

import java.util.ArrayList;
import java.util.List;

/**
 * 処理（Event・モジュールの Sub Call）の中で使う File を作り、処理の終わりでまとめて閉じる（自動生成 / auto-generated）。
 *
 * <p>保存していない Excel の変更は破棄される（保存は Save / SaveAs）。手動編集は再生成で失われます。</p>
 */
public class ScriptFiles {

    private final List<ScriptFile> files = new ArrayList<>();

    /** File の変数を作る。 */
    public ScriptFile create() {
        ScriptFile file = new ScriptFile();
        files.add(file);
        return file;
    }

    /** 画面に配置した File の変数を作る（item：画面の項目名。アップロードしたファイルを受け取る）。 */
    public ScriptFile create(String item) {
        ScriptFile file = create();
        file.item = item;
        return file;
    }

    /**
     * 画面に配置した File の配列を作る（item：画面の項目名）。送られたファイル（複数選択のアップロードの部品）ごとに 1 つ。
     * 送られていない場合（画面の表示など）は空。
     */
    public List<ScriptFile> uploads(String item) {
        List<ScriptFile> list = new ArrayList<>();
        int count = ScriptFile.uploadedFiles(item).size();
        for (int i = 0; i < count; i++) {
            ScriptFile file = create(item);
            file.index = i;
            list.add(file);
        }
        return list;
    }

    /** すべての File を閉じる。 */
    public void closeAll() {
        for (ScriptFile file : files) {
            try {
                file.close();
            } catch (RuntimeException e) {
                // 処理の終わりの後始末：閉じられないファイルは無視する
            }
        }
    }
}
