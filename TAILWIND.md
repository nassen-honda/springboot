# Tailwind CSS 使用规范（CEM システム）

生成された画面（`src/main/resources/templates/**/*.tpl`）のスタイルは、Tailwind CSS v4 の utility class だけで構成します。
旧 `BaseStylesheet.css` / `Stylesheet.css` は削除しました。

## ビルド

```bash
npm install            # 初回のみ
npm run css:build      # src/main/frontend/tailwind.css → static/css/app.css（minify）
npm run css:dev        # 監視ビルド（画面を編集しながら開発するとき）
```

- 入力：`src/main/frontend/tailwind.css`（`@import "tailwindcss"` と `@source`）
- 出力：`src/main/resources/static/css/app.css`（Spring Boot が `/css/app.css` で配信）
- 画面の `.tpl` と `static/js` を走査し、使われている utility だけを出力します。
- **`.tpl` を変更したら `npm run css:build` を実行**し、生成された `app.css` もコミットしてください（Java のビルドだけでは CSS は更新されません）。
- `run.sh` は `node_modules` がある場合に自動で CSS をビルドしてから起動します。

## 画面の書き方（規約）

- レイアウトは Tailwind の標準スケールを使う：余白 `p-6` / `gap-4`、角丸 `rounded-xl`、影 `shadow-sm`、文字 `text-sm` / `text-2xl`。
- 色は Tailwind のパレット（`slate` / `blue` / `emerald` / `amber` / `red`）。独自の CSS 変数や hex は原則使いません。
- 1 行の入力項目は次の形（ラベル 9rem + 入力欄）:

```html
<div class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-start gap-x-3 gap-y-1">
    <div class="contents">
        <div class="flex min-h-9 items-center justify-end gap-1">
            <label class="text-sm font-medium text-slate-700" for="code">コード</label>
        </div>
        <div class="flex min-w-0 max-w-sm items-center gap-2">
            <input id="code" class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500">
        </div>
    </div>
    <span class="col-start-2 text-xs text-red-600">エラー</span>
</div>
```

- ボタン：主操作 `bg-blue-600 text-white hover:bg-blue-700`、副操作 `border border-slate-300 bg-white text-slate-700 hover:bg-slate-50`、危険 `bg-red-600`。
- カード：`rounded-xl border border-slate-200 bg-white p-6 shadow-sm`。
- 表：`w-full border-collapse text-sm`。見出し `bg-slate-50 text-xs font-semibold text-slate-500`、セル `border-b border-slate-100 px-3 py-2.5`。
- メッセージ：`rounded-xl border border-slate-200 bg-white` + レベル色（Error `text-red-600` / Warning `text-amber-600` / Info `text-blue-600`）。

## JS が使う class（消さないこと）

見た目ではなく**動作のフック**として残している class です。Tailwind のクラスを足すのは自由ですが、以下は削除しないでください。

| class | 用途 |
|---|---|
| `message-view` / `message-list` / `message-item` / `message-error` `warning` `info` / `message-summary` / `message-text` / `message-close` | MessageView の表示・追加・折りたたみ |
| `pager` / `pg` / `total` / `page-size` | 一覧のページ切り替え（JS が DOM を生成） |
| `sortable` / `sort-asc` / `sort-desc` / `col-resizer` / `seq` | 並べ替え・列幅・行番号 |
| `btn-popup` / `wareki-picker` / `ico-search` / `ico-date` | 虫眼鏡・カレンダーのアイコン操作 |
| `hidden` / `bg-slate-50` / `screen-busy` / `col-resizing` | 行の非表示・縞模様・処理中・列幅ドラッグ |

## 再生成時の注意

`.tpl` と `app.css` はコード生成の対象です。再生成するとこの変更は上書きされるため、恒久的に反映するには
生成器側のテンプレート（`spring/**/*.tmpl` と `BaseStylesheet` 相当）に同じ utility を移植してください。
