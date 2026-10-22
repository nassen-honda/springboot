# 生成器客户端用 Tailwind CSS 规范

> 本次回归问题与修复要求：`TAILWIND-GENERATOR-REGRESSION-REPORT.md`（同目录）。

本文件是给**代码生成器客户端**的落地规范：生成页面时按这里的结构、class 和 fragment 输出。
详细移植说明见同目录 `TAILWIND.md`；本文件是精简执行版。

## 1. 硬约束

- **不新增文件（本文档除外）**，不改变生成项目的文件结构。
- 不新增 `tailwind.css`、`package.json`、`parts/ui.tpl`、图片、favicon 文件。
- CSS 只使用既有的：
  - `static/css/BaseStylesheet.css`（底层 base）
  - `static/css/app.css`（打包 utilities）
  - `static/css/Stylesheet.css`（项目自定义覆盖）
- 表单组件放在既有的 `templates/parts/field.tpl`。
- 构建在项目外完成（`../cem-tailwind-build`）。

## 2. CSS 三层与加载顺序

| 文件 | 内容 | 谁维护 |
|---|---|---|
| `BaseStylesheet.css` | Tailwind theme 变量 + preflight + `body` / `screen-busy` | 生成器模板固定 |
| `app.css` | theme 变量 + 画面用到的 utilities | 项目构建生成 |
| `Stylesheet.css` | 项目级自定义覆盖（品牌色、印刷、滚动条等） | 项目按需修改 |

每个页面必须按此顺序加载：

```html
<link rel="stylesheet" th:href="@{/css/BaseStylesheet.css}"/>
<link rel="stylesheet" th:href="@{/css/app.css}"/>
<link rel="stylesheet" th:href="@{/css/Stylesheet.css}"/>
```

> `app.css` 不包含 preflight / base；缺少 `BaseStylesheet.css` 会出现 reset / body 样式缺失。

## 3. 页面骨架

```html
<html lang="ja" xmlns:th="http://www.thymeleaf.org"
      th:replace="~{base/WebPageBaseLayout :: layout(~{::title}, ~{::main})}">
<head>
    <title th:text="'画面名'">画面名</title>
</head>
<body>
<main class="min-w-0 flex-1 overflow-auto p-4 sm:p-6">
    <div class="mx-auto mb-6 flex max-w-[1600px] flex-wrap items-end justify-between gap-4">
        <div th:replace="~{parts/field :: pageHeader('画面名', '画面の説明')}"></div>
        <div class="flex flex-wrap items-center gap-2">
            <!-- 画面操作ボタン -->
        </div>
    </div>

    <form class="mx-auto max-w-[1600px]" th:action="@{/screen}" th:object="${form}" method="post">
        <div data-node="n:TableMain" class="space-y-6">
            <!-- 検索カード / メッセージ / 一覧カード -->
        </div>
    </form>
</main>
</body>
</html>
```

- 画面の `<main>` は 1 つだけ。クラスは `min-w-0 flex-1 overflow-auto p-4 sm:p-6`。
- 内容の最大幅は `max-w-[1600px]`（ポップアップは `max-w-[1400px]`）。
- `<h1>` は `pageHeader` に任せる。

## 4. 卡片与页头

```html
<!-- 页头 -->
<div th:replace="~{parts/field :: pageHeader('取引先マスタ一覧', '取引先の検索・登録・編集を行います。')}"></div>

<!-- 卡片 -->
<div class="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">...</div>

<!-- 列表卡片（表を内包） -->
<div class="overflow-hidden rounded-xl border border-slate-200 bg-white shadow-sm">...</div>
```

## 5. 表单组件（`parts/field.tpl`）

### 5.1 fragment 与参数（**全参数必须传，未使用传 `null`**）

| fragment | 参数顺序 |
|---|---|
| `fieldText` | `node, label, field, required, maxlength, readonly, hidden` |
| `fieldSelect` | `node, label, field, items, required, blank, hidden` |
| `fieldDate` | `node, label, field, required, readonly, hidden` |
| `fieldDateTime` | `node, label, field, required, readonly, hidden` |
| `fieldTextarea` | `node, label, field, required, rows, readonly, hidden` |
| `pageHeader` | `title, description` |
| `emptyRow` | `colspan, title, description` |

### 5.2 调用示例

```html
<div th:replace="~{parts/field :: fieldText('v:CustomerCD', '取引先コード', 'CustomerCD', true, 10, null, null)}"></div>
<div th:replace="~{parts/field :: fieldText('v:CustomerNM', '取引先名', 'CustomerNM', null, 100, null, null)}"></div>
<div th:replace="~{parts/field :: fieldSelect('v:CustomerKbn', '取引先区分', 'CustomerKbn', ${CustomerKbnItems}, null, true, null)}"></div>
<div th:replace="~{parts/field :: fieldDateTime('v:CreateDate', '作成日時', 'CreateDate', null, true, null)}"></div>
<div th:replace="~{parts/field :: fieldTextarea('v:Remarks', '備考', 'Remarks', null, 4, null, null)}"></div>
```

- `field` はフォームのプロパティ名。`th:field` が `id` / `name` を生成する。
- `required=true` は赤い `*` のみ（HTML `required` は付けない）。
- `hidden=true` は `hidden` クラス（値は送信される）。
- 日付専用の和暦入力は業務専用のため、カスタムマークアップのまま utility を使う。

## 6. 按钮

```html
<!-- 主操作 -->
<button class="inline-flex items-center justify-center gap-1.5 rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white shadow-sm transition hover:bg-blue-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-1">保存</button>

<!-- 副操作 -->
<button class="inline-flex items-center justify-center gap-1.5 rounded-md border border-slate-300 bg-white px-4 py-2 text-sm font-medium text-slate-700 shadow-sm transition hover:bg-slate-50 focus:outline-none focus-visible:ring-2 focus-visible:ring-slate-300 focus-visible:ring-offset-1">キャンセル</button>

<!-- 行内小按钮 -->
<button class="inline-flex items-center justify-center gap-1 rounded px-2.5 py-1 text-xs font-medium transition focus:outline-none focus-visible:ring-2 focus-visible:ring-offset-1 disabled:opacity-60 border border-blue-200 bg-blue-50 text-blue-700 hover:bg-blue-100">詳細</button>
```

- 危険操作：`bg-red-600 text-white hover:bg-red-700`。
- 行内按钮放在 `<div class="flex flex-wrap justify-center gap-1">` 里，防止窄屏被裁切。
- 按钮类型明确：submit / button。

## 7. 列表、空状态、分页

```html
<div class="overflow-hidden rounded-xl border border-slate-200 bg-white shadow-sm">
    <div class="flex flex-wrap items-center justify-between gap-3 border-b border-slate-200 px-4 py-3">
        <h2 class="text-sm font-semibold text-slate-700">検索結果</h2>
        <span class="rounded-full bg-slate-100 px-2.5 py-0.5 text-xs font-medium text-slate-600"
              th:text="${#lists.size(form.Items)} + ' 件'">0 件</span>
    </div>

    <div class="table-scroll max-h-[60vh] overflow-auto">
        <table data-grid="Items" data-page-sizes="10,20,30,50"
               class="w-full min-w-[1200px] border-collapse text-sm">
            <thead>
                <tr>
                    <th class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500 w-12">No</th>
                    <th class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500">コード</th>
                </tr>
            </thead>
            <tbody>
                <tr th:each="row, st : *{Items}" class="hover:bg-blue-50"
                    th:classappend="${(st.index &gt;= 10 ? 'hidden' : (st.even ? 'bg-slate-50' : ''))}">
                    <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-center w-12 text-slate-400 seq" th:text="${st.count}">1</td>
                    <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-left">...</td>
                </tr>
                <th:block th:if="${#lists.isEmpty(form.Items)}">
                    <tr th:replace="~{parts/field :: emptyRow(2, 'データがありません', '条件を変更して、もう一度検索してください。')}"></tr>
                </th:block>
            </tbody>
        </table>
    </div>
</div>
```

- `table-scroll` は**必須**（JS が pager をその後ろに挿す）。
- `empty` 行は JS がデータ行から除外する。
- 行の非表示は `hidden`、斑馬紋は `bg-slate-50`（旧 `pg-off` / `alt` は使わない）。
- 分页器は JS 生成。ページャーの class は既存 JS のまま（`pager` / `pg` / `total` / `page-size`）。
- `min-w-[1200px]` と `colspan` は列数に合わせる。

## 8. MessageView

```html
<details class="message-view relative mb-4 rounded-xl border border-slate-200 bg-white px-4 py-3 shadow-sm"
         th:classappend="${messages.empty} ? 'hidden'"
         data-levels="Error,Warning,Info" data-clear-on-event="true" th:open="${messages.hasError}">
    <summary class="message-summary cursor-pointer list-none select-none text-sm font-semibold text-slate-600 [&::-webkit-details-marker]:hidden"
             th:text="${messages.summary}">メッセージ</summary>
    <ul class="message-list mt-2 space-y-1">
        <li class="message-item message-error flex items-start gap-2 text-sm text-red-600" ...>
            <span class="shrink-0" aria-hidden="true">✖</span>
            <span class="message-text" ...>...</span>
        </li>
    </ul>
    <button type="button" class="message-close absolute right-3 top-2.5 text-slate-400 transition hover:text-slate-600" title="閉じる">×</button>
</details>
```

- 层级颜色：Error `text-red-600`、Warning `text-amber-600`、Info `text-blue-600`。
- 关闭由 JS 加 `hidden`（旧 `message-closed` 已废弃）。
- `message-*` 是 JS hook，不能删。

## 9. 弹窗

```html
<div class="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/50 p-4" th:if="${popup}">
    <iframe class="max-h-[90vh] w-full max-w-[95vw] rounded-xl border-0 bg-white shadow-2xl"
            th:src="@{${popup.url}}" th:title="${popup.screenId}"
            th:style="|width: min(95vw, ${popup.width}); height: min(90vh, ${popup.height})|"></iframe>
</div>
```

## 10. `Stylesheet.css` 的写法

只放“全画面に効く上書き”。画面固有の見た目は `.tpl` の utility で書く。

```css
/* 品牌色：Tailwind の theme 変数を上書き */
:root {
    --color-blue-500: #2563eb;
    --color-blue-600: #1d4ed8;
    --color-blue-700: #1e40af;
}

/* 日本語組版 */
body {
    font-feature-settings: "palt" 1;
    letter-spacing: 0.01em;
}

/* スクロールバー分のガタつき防止 */
.table-scroll {
    scrollbar-gutter: stable;
}

/* 印刷 */
@media print {
    header, aside, .no-print { display: none !important; }
    main { overflow: visible !important; padding: 0 !important; }
    body { background: #fff !important; }
}
```

禁止：

- 在 `Stylesheet.css` 里新建 `.btn` / `.card` 之类的组件类。
- 用 `Stylesheet.css` 写画面专用布局。
- 用 `!important` 解决普通优先级问题（打印等例外）。

## 11. JS 必须保留的 hook

| class / 属性 | 用途 |
|---|---|
| `message-view` `message-list` `message-item` `message-error` `message-warning` `message-info` `message-summary` `message-text` `message-close` | 消息 |
| `pager` `pg` `total` `page-size` `active` `disabled` `dots` | 分页 |
| `table-scroll` | pager 插入位置 |
| `empty` | 空状态行 |
| `hidden` / `bg-slate-50` | 行隐藏 / 斑马纹 |
| `sortable` `sort-asc` `sort-desc` `sort-indicator` `col-resizer` `seq` | 排序 / 列宽 / 行号 |
| `btn-popup` `wareki-picker` `ico-search` `ico-date` | 虫眼鏡 / 日历 |
| `screen-busy` | 处理中 |
| `data-node` / `data-grid` / `data-item` | 生成器 / TS 定位 |

## 12. Tailwind 写法规则

- 只写 utility；不写 `<style>`、`@layer components`、`@apply`（Base 的 body 除外）。
- 颜色用 Tailwind 调色板；自定义色只放 `Stylesheet.css`。
- 任意值：空格用 `_`（`grid-cols-[9rem_minmax(0,1fr)]`），`&` 不要写成 `&amp;`。
- class 必须是完整字面量；不要 `'text-' + color`。
- 图片不要新增文件：图标用内联 `<svg>`，favicon 用 data URI。
- 可访问性：图标 `aria-hidden`、纯图标按钮 `aria-label`、激活菜单 `aria-current`、抽屉 `aria-expanded`。
- 表头建议加 `scope="col"`。

## 13. 生成器输出检查清单

- [ ] 页面 head 按 Base → app → Stylesheet 顺序加载。
- [ ] 字段使用 `parts/field` fragment，参数全部传。
- [ ] 按钮使用第 6 节 class，行内按钮 `flex flex-wrap`。
- [ ] 表格有 `table-scroll`、`emptyRow`、`data-grid`、`data-page-sizes`。
- [ ] 行状态使用 `hidden` / `bg-slate-50`。
- [ ] 消息使用 `message-*` hook。
- [ ] 弹窗 iframe 宽高用 `th:style`。
- [ ] 没有新增图片 / CSS / JS 文件。
- [ ] `Stylesheet.css` 只放项目级覆盖。

### 13.1 本次回归实际发现的问题（必须避免）

生成器重新生成后，出现过以下问题，客户端需要逐项确认：

1. **CSS 被旧模板覆盖**
   - `BaseStylesheet.css` 变成约 37 KB 的旧 Element Plus 风格（应为约 4.4 KB 的 Tailwind base）。
   - `app.css` 变成约 1.3 KB 的旧公共样式（应为约 20 KB 的 theme + utilities）。
   - `Stylesheet.css` 被清空（应为项目自定义覆盖）。
   - 页面只加载 `BaseStylesheet.css` + `Stylesheet.css`，**没有加载 `app.css`**，Tailwind utility 全部失效。
2. **模板文件名大小写与 Controller 的 `VIEW` 不一致**
   - 生成器输出 `screen/developer.tpl`，Controller 却是 `screen/Developer`。
   - macOS 默认大小写不敏感，可能“看起来正常”；Linux / CI 会直接 500。
   - 生成器必须保证 **模板文件名 = Controller 的 VIEW**（本仓库已把 Controller 改为 `screen/developer`）。
3. **生成代码编译失败**
   - `OptimisticLock.java` 调用 `ScriptDb.optimisticCheck(Optimistic)`，但当前库签名是 `optimisticCheck(Object, boolean)`。
   - 生成器模板必须按当前 `ScriptDb` 的签名输出。
4. **Form.Load 无数据时 500**
   - 详情页直接打开（没有 `piCustomerCd`）时，`ScriptDb.load(..., true)` 会抛“データがありません”。
   - 应使用 `load(..., false)` 并处理返回 false（显示「取引先が見つかりません」），而不是让页面 500。
5. **会话设置被覆盖**
   - `server.servlet.session.tracking-modes=cookie` 被删除后，部分更新会整页重绘、侧边菜单激活态丢失。
6. **文档被生成器删除**
   - `TAILWIND.md` / `TAILWIND-CLIENT-SPEC.md` 不在生成器的输出清理范围内，不能被覆盖或删除。

> 生成后先执行：`grep -n "stylesheet" base/WebPageBaseLayout.tpl`、`wc -c static/css/*.css`、`mvn clean package`，再打开 4 个画面确认。

## 14. 构建 / 验收

```bash
cd ../cem-tailwind-build
npm run css:build

cd ../springboot
mvn clean package
```

验收：

- `/css/BaseStylesheet.css` 200、非空
- `/css/app.css` 200、非空
- `/css/Stylesheet.css` 200、非空（自定义覆盖）
- 4 个画面 200；搜索 / 分页 / 详情 / 校验 / 弹窗 / 移动端正常
- 浏览器无 JS 错误
- jar 内只有上述 3 个 CSS
