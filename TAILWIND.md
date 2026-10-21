# Tailwind CSS 规范（CEM システム / 生成器模板移植版）

本文档是当前项目（Spring Boot + Thymeleaf）前端样式的**唯一规范**，用于把现有实现移植到代码生成器的模板中。
目标：不写任何自定义 CSS，画面全部由 Tailwind CSS v4 的 utility class 构成；公共布局、表单、表格、消息、弹窗统一。

> 生成器客户端的精简执行版：`TAILWIND-CLIENT-SPEC.md`（同目录）。本文档是完整参考版。
>
> **硬约束：生成项目内的文件结构不变、不新增文件（本文档与客户端规范除外）。**
> 不引入 `tailwind.css`、`package.json`、`parts/ui.tpl`、图片等新文件；构建在项目外进行，只更新**既有**的 `BaseStylesheet.css` / `app.css` / `Stylesheet.css`，表单 fragment 放进**既有** `parts/field.tpl`。

## 0. 移植摘要（先看这里）

1. **三个 CSS 的职责**：
   - `BaseStylesheet.css`：**底层 base**（Tailwind 的 theme 变量 + preflight + `body` / `screen-busy`）。生成器模板固定提供。
   - `app.css`：**打包的 utility CSS**（theme 变量 + 画面用到的 utilities）。每个项目构建生成。
   - `Stylesheet.css`：**项目自定义覆盖层**（品牌色、印刷、滚动条等），最后加载。
2. 每个页面按顺序加载：`BaseStylesheet.css` → `app.css` → `Stylesheet.css`。最后加载的自定义层可以覆盖前两者。
3. **同路径复制**以下既有文件到生成器模板（不新增文件）：
   - `parts/field.tpl`（表单 / 页头 / 空状态 fragment）
   - `base/WebPageBaseLayout.tpl`
   - `error.tpl`
   - `parts/layout.tpl`
4. 字段输出改为 `parts/field :: fieldText / fieldSelect / fieldDate / fieldDateTime / fieldTextarea`（第 7、15 节）。
5. 按钮 / 表格 / 分页 / 消息 / 弹窗按第 8–11 节；JS 只改 3 个状态 class（第 13 节）。
6. 构建在项目外：`../cem-tailwind-build` 执行 `npm run css:build`，再回项目 `mvn clean package`。

---

## 1. 技术栈与版本

| 项目 | 版本 / 要求 |
|---|---|
| Tailwind CSS | 4.3.3（`tailwindcss` + `@tailwindcss/cli`） |
| Node.js | 20 以上（开发环境为 24；只用于项目外构建） |
| Java | 21 |
| Spring Boot | 4.1.1 |
| Thymeleaf | Spring Boot 管理版本 |
| 浏览器目标 | 现代浏览器（Tailwind v4 使用 oklch；Safari 16.4+ / Chrome 111+ / Edge 111+ / Firefox 128+） |

Tailwind v4 采用 CSS-first 配置：没有 `tailwind.config.js`，主题与扫描范围写在项目外的 `base-input.css` / `app-input.css` 中。

---

## 2. 文件结构（与生成器现有结构一致）

```text
src/main/resources/static/css/
  BaseStylesheet.css          # 底层：theme + preflight + base（生成器固定提供）
  Stylesheet.css              # プロジェクト固有の上書き（最後に読み込む）
  app.css                     # 打包：theme + utilities（プロジェクトごとに生成）
src/main/resources/templates/base/WebPageBaseLayout.tpl   # 公共布局（Base → app を読み込む）
src/main/resources/templates/parts/field.tpl              # 表单 / 页头 / 空状态 fragment
src/main/resources/templates/parts/layout.tpl             # 既存の簡易レイアウト
src/main/resources/templates/parts/stylesheets.tpl        # /css/app.css + /css/Stylesheet.css を出力
src/main/resources/templates/screen/*.tpl                 # 各画面
src/main/resources/templates/error.tpl                    # 错误页
```

**项目外（リポジトリに含めない）:**

```text
../cem-tailwind-build/
  base-input.css              # theme + preflight + base → BaseStylesheet.css
  app-input.css               # theme + utilities → app.css
  package.json / package-lock.json
  node_modules/
```

**禁止事項：**

- 生成物に `tailwind.css`、`package.json`、`parts/ui.tpl`、`static/img/*`、`favicon.svg` などを追加しない。
- `BaseStylesheet.css` / `Stylesheet.css` のパスを削除しない。
- `parts/field.tpl` / `parts/layout.tpl` は既存ファイルとして保持し、中身だけ置き換える。

---

## 3. 构建与产物

### 3.1 两个构建输入（项目外）

`base-input.css`（→ `BaseStylesheet.css`）:

```css
@import "tailwindcss/theme.css" layer(theme);
@import "tailwindcss/preflight.css" layer(base);

@theme {
    --font-sans: "Hiragino Sans", "Hiragino Kaku Gothic ProN", "Yu Gothic UI", "Yu Gothic", Meiryo,
        ui-sans-serif, system-ui, -apple-system, "Segoe UI", sans-serif;
}

@layer base {
    body {
        @apply min-h-full bg-slate-100 font-sans text-slate-800 antialiased;
    }

    html.screen-busy,
    html.screen-busy * {
        cursor: progress !important;
    }
}
```

`app-input.css`（→ `app.css`）:

```css
/* ビルドディレクトリ内の文書を走査対象から外す（例示 class の混入防止） */
@source not "./TAILWIND.md";
@source not "./docs";

/* @source は import より前に書く（partial import では順序が効く） */
@source "../springboot/src/main/resources/templates/**/*.tpl";
@source "../springboot/src/main/resources/static/js/**/*.js";

@import "tailwindcss/theme.css" layer(theme);
@import "tailwindcss/utilities.css" layer(utilities);

@theme {
    --font-sans: "Hiragino Sans", "Hiragino Kaku Gothic ProN", "Yu Gothic UI", "Yu Gothic", Meiryo,
        ui-sans-serif, system-ui, -apple-system, "Segoe UI", sans-serif;
}
```

`package.json`（项目外）:

```json
{
  "scripts": {
    "css:base": "tailwindcss -i ./base-input.css -o ../springboot/src/main/resources/static/css/BaseStylesheet.css --minify",
    "css:app": "tailwindcss -i ./app-input.css -o ../springboot/src/main/resources/static/css/app.css --minify",
    "css:build": "npm run css:base && npm run css:app",
    "css:dev": "tailwindcss -i ./app-input.css -o ../springboot/src/main/resources/static/css/app.css --watch"
  },
  "devDependencies": {
    "@tailwindcss/cli": "^4.3.3",
    "tailwindcss": "^4.3.3"
  }
}
```

> 目录名 `../springboot` 按实际项目替换；`app-input.css` 的 `@source`、`package.json` 的输出路径要同步。

### 3.2 职责边界

- `BaseStylesheet.css` 提供 **reset / preflight / theme 变量 / body 基础样式 / screen-busy**。生成器模板固定提供，不随画面变化。
- `app.css` 提供 **theme 变量（画面用到的） + utilities**。不包含 preflight / base。
- `Stylesheet.css` 提供 **项目自定义覆盖**（品牌色、印刷、滚动条等），最后加载。
- 因此 **页面必须按 BaseStylesheet → app.css → Stylesheet.css 的顺序加载**。
- `app.css` 不是“独立可运行”的完整 CSS：离开 BaseStylesheet 会缺少 reset / body 基础样式。若要单文件分发，请改用完整构建（theme + preflight + base + utilities），但本项目按“底层 base + 打包 utility + 自定义覆盖”三层交付。

### 3.3 构建顺序（重要）

```bash
cd ../cem-tailwind-build
npm ci                 # 初回のみ
npm run css:build      # BaseStylesheet.css と app.css を生成

cd ../springboot
mvn clean package
```

- **必须先构建 CSS，再打包 Java**。
- **必须 `mvn clean`**：否则 `target/classes` 里旧的 CSS 会残留。
- 验证 jar：

```bash
unzip -l target/cem-0.0.1-SNAPSHOT.jar | grep "static/css"
# 期望：
#  BaseStylesheet.css   (theme + preflight + base, 约 4.4 KB)
#  app.css             (theme + utilities, 约 20 KB)
#  Stylesheet.css      (自定义覆盖层，非空)
```

### 3.4 是否提交 CSS

`BaseStylesheet.css`、`app.css`、`Stylesheet.css` 都提交。只有 JDK/Maven 的环境也能运行；修改 `.tpl` 后，在项目外执行 `npm run css:build` 并提交两个生成产物（Stylesheet.css 按需修改）。

CI：

```bash
cd ../cem-tailwind-build && npm ci && npm run css:build
cd ../springboot && mvn clean package
```

不要在生成项目里增加 `package.json` / `pom.xml` 的 npm 插件；构建保持在项目外。

### 3.5 启动

```bash
cd ../springboot
mvn spring-boot:run
# 或
java -jar target/cem-0.0.1-SNAPSHOT.jar
```

### 3.6 `Stylesheet.css` 自定义覆盖层

`Stylesheet.css` 在 `BaseStylesheet.css` 与 `app.css` **之后**加载，用来做“项目级”的覆盖：

- 品牌色（覆盖 Tailwind 的 `--color-blue-*` 等主题变量）
- 印刷样式（隐藏 header / sidebar / 操作按钮）
- 滚动条、日语约物、第三方组件的微调
- 不适合写进 utility 的横断规则

示例（当前内容）：

```css
:root {
    --color-blue-500: #2563eb;
    --color-blue-600: #1d4ed8;
    --color-blue-700: #1e40af;
}

body {
    font-feature-settings: "palt" 1;
    letter-spacing: 0.01em;
}

.table-scroll {
    scrollbar-gutter: stable;
}

@media print {
    header, aside, .no-print { display: none !important; }
    main { overflow: visible !important; padding: 0 !important; }
    body { background: #fff !important; }
}
```

规则：

- **画面固有の見た目はここに書かない**。`.tpl` の utility で表現する。
- ここに書くのは「全画面に効く上書き」だけ。セレクタは `:root` / `body` / 共通クラス / `@media print` に限定する。
- `!important` は印刷・どうしても勝てない場合だけ。
- Tailwind の utility を再定義しない（`.btn` のような独自コンポーネントを作らない）。

---

## 4. 设计令牌（Design Tokens）

不定义 CSS 变量，全部使用 Tailwind 默认调色板与尺度。

### 4.1 颜色

| 用途 | class |
|---|---|
| 页面背景 | `bg-slate-100` |
| 卡片 / 输入框背景 | `bg-white` |
| 主文字 | `text-slate-800` / `text-slate-900` |
| 标签 | `text-slate-700` |
| 次要文字 / 说明 | `text-slate-500` / `text-slate-400` |
| 边框 | `border-slate-200` / `border-slate-300` / `border-slate-100` |
| 主操作 | `bg-blue-600` / `hover:bg-blue-700` / `text-white` |
| 主操作浅色 | `bg-blue-50` / `text-blue-700` / `border-blue-200` |
| 成功 | `bg-emerald-600` / `hover:bg-emerald-700` |
| 警告 / 复制 | `bg-amber-500` / `hover:bg-amber-600` |
| 危险 / 删除 | `bg-red-600` / `hover:bg-red-700` |
| 错误文字 | `text-red-600` / `text-red-500`（必填星号） |
| 当前页 / 激活菜单 | `bg-blue-50 text-blue-700` |

不要使用 hex（`#409eff` 等）或 `rgb()`；需要新颜色时从 Tailwind 调色板选。
唯一例外是项目自定义覆盖层 `Stylesheet.css`（例如品牌色变量覆写），见第 3.6 节。

### 4.2 字体与字号

| 用途 | class |
|---|---|
| 全局 | `font-sans`（body 默认） |
| 页面标题 | `text-2xl font-bold tracking-tight text-slate-900` |
| 区块标题 | `text-base font-semibold text-slate-900` |
| 卡片内小标题 | `text-sm font-semibold text-slate-700` |
| 标签 | `text-sm font-medium text-slate-700` |
| 正文 / 输入 | `text-sm` |
| 表头 | `text-xs font-semibold tracking-wide text-slate-500` |
| 辅助文字 / 错误 | `text-xs` |
| 大数字 / 强调 | `font-semibold` / `font-bold` |

### 4.3 间距、圆角、阴影、控件高度

| 项目 | 规范 |
|---|---|
| 页面内边距 | `p-4 sm:p-6` |
| 卡片内边距 | `p-6` |
| 区块间距 | `space-y-6` |
| 页头与内容 | `mb-6` |
| 表单行间距 | `gap-x-3 gap-y-1` + `mb-4` |
| 两列表单列间距 | `gap-x-8` |
| 标签列宽 | `grid-cols-[9rem_minmax(0,1fr)]` |
| 输入框高度 | `h-9` |
| 卡片圆角 | `rounded-xl` |
| 控件 / 按钮圆角 | `rounded-md` |
| 小按钮圆角 | `rounded` |
| 卡片阴影 | `shadow-sm` |
| 弹窗阴影 | `shadow-2xl` |
| 输入框最大宽度 | `max-w-sm` |

### 4.4 断点

| 断点 | 宽度 | 用途 |
|---|---|---|
| `sm` | 640px | 移动端表单两列、显示品牌文字 |
| `lg` | 1024px | 侧边栏从抽屉切换为固定 |
| `xl` | 1280px | 列表 / 详情两列表单 |

---

## 5. 页面骨架

### 5.1 根元素

```html
<html lang="ja" xmlns:th="http://www.thymeleaf.org"
      th:replace="~{base/WebPageBaseLayout :: layout(~{::title}, ~{::main})}">
<head>
    <title th:text="'取引先マスタ一覧'">取引先マスタ一覧</title>
</head>
<body>
<main class="min-w-0 flex-1 overflow-auto p-4 sm:p-6">
    ...
</main>
</body>
</html>
```

- 画面侧 `<main>` 必须带 `min-w-0 flex-1 overflow-auto p-4 sm:p-6`（由 `WebPageBaseLayout` 的 flex 容器承载）。
- `<main>` 只有 **一个**，不要嵌套 `<main>`。
- 画面内容统一包在 `mx-auto max-w-[1600px]`（列表 / 详情）或 `mx-auto w-full max-w-[1400px]`（弹窗 iframe 页面）里。

### 5.2 页面头部

```html
<div class="mx-auto mb-6 flex max-w-[1600px] flex-wrap items-end justify-between gap-4">
    <div th:replace="~{parts/field :: pageHeader('取引先マスタ一覧', '取引先の検索・登録・編集を行います。')}"></div>
    <div class="flex flex-wrap items-center gap-2">
        <!-- 主操作・副操作ボタン -->
    </div>
</div>
```

`pageHeader` 片段：

```html
<div th:fragment="pageHeader(title, description)">
    <h1 class="text-2xl font-bold tracking-tight text-slate-900" th:text="${title}">タイトル</h1>
    <p th:if="${description}" class="mt-1 text-sm text-slate-500" th:text="${description}"></p>
</div>
```

### 5.3 卡片

```html
<div class="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
    ...
</div>

<!-- 一覧カード（表を内包するので overflow-hidden） -->
<div class="overflow-hidden rounded-xl border border-slate-200 bg-white shadow-sm">
    ...
</div>
```

---

## 6. 公共布局 `base/WebPageBaseLayout.tpl`

### 6.1 结构

```html
<body class="min-h-screen bg-slate-100 text-slate-800">
    <header class="sticky top-0 z-50 flex h-14 items-center justify-between border-b border-slate-200 bg-white/95 px-4 backdrop-blur sm:px-6">
        <!-- ハンバーガー（lg 未満） + ブランド + ユーザー -->
    </header>

    <div class="flex h-[calc(100vh-3.5rem)]">
        <div id="sidebar-backdrop" class="fixed inset-0 z-30 hidden bg-slate-900/40 backdrop-blur-sm lg:hidden"></div>
        <aside id="sidebar"
               class="fixed inset-y-0 left-0 z-40 w-64 -translate-x-full overflow-y-auto border-r border-slate-200 bg-white px-3 pb-4 pt-20 transition-transform duration-200 lg:static lg:z-auto lg:translate-x-0 lg:pt-4">
            ...
        </aside>
        <th:block th:replace="${content}"/>
    </div>
    ...
</body>
```

要点：

- 顶部栏 `h-14`、`sticky top-0 z-50`。
- 侧栏宽 `w-64`；`lg` 以上 `static`，以下 `fixed` + `-translate-x-full`。
- 移动端侧栏 `pt-20`，避免盖住顶栏；`z-40` 低于 header 的 `z-50`。
- 遮罩 `z-30`，点击后关闭。
- 主内容区高度 `h-[calc(100vh-3.5rem)]`，由 `<main>` 自己滚动。

### 6.2 菜单

- 叶子菜单：`menu-link flex items-center gap-3 rounded-lg px-3 py-2 text-sm transition hover:bg-slate-100`
- 激活：追加 `active bg-blue-50 font-semibold text-blue-700`
- 非激活：`text-slate-600 hover:text-slate-900`
- 分组：`<details class="group">` + `<summary class="... [&::-webkit-details-marker]:hidden">` + 旋转箭头 `group-open:rotate-180`
- 子菜单容器：`mt-1 space-y-1 pl-6`
- `Top` 链接也要带 `menu-link`（移动端点击后关闭抽屉）
- 激活项加 `aria-current="page"`

```html
<a th:if="${item.children.isEmpty()}" th:href="@{${item.url}}"
   class="menu-link flex items-center gap-3 rounded-lg px-3 py-2 text-sm transition hover:bg-slate-100"
   th:attr="aria-current=${item.key == activeScreen ? 'page' : null}"
   th:classappend="${item.key == activeScreen} ? 'active bg-blue-50 font-semibold text-blue-700' : 'text-slate-600 hover:text-slate-900'">
    <span class="w-5 text-center" th:text="${item.icon}" aria-hidden="true"></span>
    <span th:text="${item.caption}">画面</span>
</a>
```

### 6.3 移动端抽屉脚本

```js
(() => {
  const sidebar = document.getElementById('sidebar');
  const backdrop = document.getElementById('sidebar-backdrop');
  const toggle = document.getElementById('sidebar-toggle');
  if (!sidebar || !backdrop || !toggle) return;
  const setOpen = open => {
    sidebar.classList.toggle('-translate-x-full', !open);
    backdrop.classList.toggle('hidden', !open);
    toggle.setAttribute('aria-expanded', String(open));
  };
  toggle.addEventListener('click', () => setOpen(sidebar.classList.contains('-translate-x-full')));
  backdrop.addEventListener('click', () => setOpen(false));
  document.addEventListener('keydown', e => { if (e.key === 'Escape') setOpen(false); });
  sidebar.querySelectorAll('a.menu-link').forEach(a => a.addEventListener('click', () => {
    if (window.matchMedia('(max-width: 1023px)').matches) setOpen(false);
  }));
})();
```

### 6.4 会话与局部更新

`application.properties` 必须保留：

```properties
server.servlet.session.tracking-modes=cookie
```

原因：默认的 URL 重写会在表单 `action` 上生成 `;jsessionid=...`，导致画面局部更新（fetch）比较 action 失败而整页重绘，侧边菜单激活态也会丢失。

---

## 7. 表单组件 `parts/field.tpl`

### 7.1 片段与参数（**所有参数必须传，未使用传 `null`**）

| fragment | 参数（顺序） | 说明 |
|---|---|---|
| `fieldText` | `node, label, field, required, maxlength, readonly, hidden` | 单行文本 |
| `fieldSelect` | `node, label, field, items, required, blank, hidden` | 下拉；`items` 是 value→表示名 的 Map |
| `fieldDate` | `node, label, field, required, readonly, hidden` | `type=date` |
| `fieldDateTime` | `node, label, field, required, readonly, hidden` | `type=datetime-local` |
| `fieldTextarea` | `node, label, field, required, rows, readonly, hidden` | 多行文本 |
| `pageHeader` | `title, description` | 页面标题 + 说明 |
| `emptyRow` | `colspan, title, description` | 列表空状态行 |

Thymeleaf 的 fragment 参数**不能省略**，否则会抛：

```text
Cannot resolve fragment. Signature "fieldText (...)" declares parameter "required", which is not specified...
```

### 7.2 调用示例

```html
<!-- 文本：必填、maxlength=10 -->
<div th:replace="~{parts/field :: fieldText('v:CustomerCD', '取引先コード', 'CustomerCD', true, 10, null, null)}"></div>

<!-- 文本：非必填、maxlength=100 -->
<div th:replace="~{parts/field :: fieldText('v:CustomerNM', '取引先名', 'CustomerNM', null, 100, null, null)}"></div>

<!-- 下拉：blank=true（先頭に空の選択肢） -->
<div th:replace="~{parts/field :: fieldSelect('v:CustomerKbn', '取引先区分', 'CustomerKbn', ${CustomerKbnItems}, null, true, null)}"></div>

<!-- 下拉：blank=null（空の選択肢なし） -->
<div th:replace="~{parts/field :: fieldSelect('v:Customer.MST00004_CustomerKbn', '取引先区分', 'CustomerMST00004_CustomerKbn', ${CustomerMST00004_CustomerKbnItems}, true, null, null)}"></div>

<!-- 日期 / 日期时间 -->
<div th:replace="~{parts/field :: fieldDate('v:Date', '日付', 'Date', null, null, null)}"></div>
<div th:replace="~{parts/field :: fieldDateTime('v:CreateDate', '作成日時', 'CreateDate', null, true, null)}"></div>

<!-- 多行 -->
<div th:replace="~{parts/field :: fieldTextarea('v:Remarks', '備考', 'Remarks', null, 4, null, null)}"></div>

<!-- 隐藏字段（仍然提交；例：削除フラグ） -->
<div th:replace="~{parts/field :: fieldSelect('v:DelFlg', '削除フラグ', 'DelFlg', ${DelFlgItems}, null, true, true)}"></div>
```

### 7.3 生成的 HTML（以 `fieldText` 为例）

```html
<div class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-start gap-x-3 gap-y-1"
     data-node="v:CustomerCD">
    <div class="contents">
        <div class="flex min-h-9 items-center justify-end gap-1">
            <label class="text-sm font-medium text-slate-700" for="CustomerCD">取引先コード</label>
            <span class="text-red-500" aria-hidden="true">*</span>
        </div>
        <div class="flex min-w-0 max-w-sm items-center gap-2">
            <input type="text"
                   class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400"
                   autocomplete="off"
                   id="CustomerCD" name="CustomerCD" value=""/>
        </div>
    </div>
    <span class="col-start-2 text-xs text-red-600"></span>
</div>
```

关键点：

- `grid-cols-[9rem_minmax(0,1fr)]`：标签固定 9rem，输入列可收缩。
- `.contents` 让标签与输入成为 grid 的直接子项，错误信息用 `col-start-2` 对齐输入列。
- `th:field` 自动生成 `id` / `name` / `value`；`label for` 用同一个 `field` 名。
- `required` 只负责显示红色 `*`，不输出 HTML `required`（是否必填仍由服务端校验决定）。
- `readonly`、`hidden` 只影响外观/显示；`hidden` 的字段仍会提交。
- `maxlength` 可传数字（`10`）或字符串（`'10'`）。

### 7.4 旧控件到新部件的映射

| 生成器控件 | 旧 class / 写法 | 新写法 |
|---|---|---|
| 文本框 | `class="ctl w-sm"` | `fieldText(...)` |
| 下拉 | `class="ctl sel w-sm"` | `fieldSelect(...)` |
| 日期 | `class="ctl w-sm ico-date"` | `fieldDate(...)`；和暦专用输入保留自定义 |
| 日期时间 | `class="ctl w-sm ico-date"` | `fieldDateTime(...)` |
| 多行 | `class="ctl ta"` | `fieldTextarea(...)` |
| 隐藏 | `style="display: none"` | `hidden=true` |
| 只读 | `readonly="readonly"` | `readonly=true` |
| 必填星号 | `<span class="required">*</span>` | `required=true` |

### 7.5 和暦输入（例外）

列表画面的「日付」是和暦文本框 + 隐藏 `date` 输入 + 日历按钮，属于业务专用控件，保留自定义标记，但样式仍用 utility：

```html
<div data-node="v:CreateDate" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-start gap-x-3 gap-y-1">
    ...
    <input type="text" id="CreateDate-wareki"
           class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 !pr-10 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 ico-date"
           data-wareki-text="date" .../>
    <svg class="pointer-events-none absolute right-3 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-400" viewBox="0 0 16 16" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
        <rect x="2" y="3" width="12" height="11" rx="1.5"/><path d="M2 6.5h12M5.5 1.6v2.6M10.5 1.6v2.6"/>
    </svg>
    <button type="button" class="wareki-picker hidden" ...>📅</button>
    <input type="date" id="CreateDate" class="wareki-native pointer-events-none absolute bottom-0 left-0 h-px w-px border-0 p-0 opacity-0" .../>
</div>
```

`ico-date` / `ico-search` 是 JS 的 hook，必须保留。图标用内联 `<svg>`（`pointer-events-none absolute right-3 top-1/2 ...`），不新增图片文件。

---

## 8. 按钮

### 8.1 标准按钮

```html
<!-- 主操作 -->
<button type="submit"
        class="inline-flex items-center justify-center gap-1.5 rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white shadow-sm transition hover:bg-blue-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-1">
    保存
</button>

<!-- 副操作 -->
<button type="button"
        class="inline-flex items-center justify-center gap-1.5 rounded-md border border-slate-300 bg-white px-4 py-2 text-sm font-medium text-slate-700 shadow-sm transition hover:bg-slate-50 focus:outline-none focus-visible:ring-2 focus-visible:ring-slate-300 focus-visible:ring-offset-1">
    キャンセル
</button>

<!-- 危险 -->
<button type="button"
        class="inline-flex items-center justify-center gap-1.5 rounded-md bg-red-600 px-4 py-2 text-sm font-medium text-white transition hover:bg-red-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-red-500 focus-visible:ring-offset-1">
    削除
</button>
```

### 8.2 行内小按钮（表格操作列）

```html
<button class="inline-flex items-center justify-center gap-1 rounded px-2.5 py-1 text-xs font-medium transition focus:outline-none focus-visible:ring-2 focus-visible:ring-offset-1 disabled:opacity-60 border border-blue-200 bg-blue-50 text-blue-700 hover:bg-blue-100">詳細</button>
<button class="... bg-blue-600 text-white hover:bg-blue-700">編集</button>
<button class="... bg-amber-500 text-white hover:bg-amber-600">複製</button>
<button class="... bg-red-600 text-white hover:bg-red-700">削除</button>
```

- 操作按钮外层用 `<div class="flex flex-wrap justify-center gap-1">` 包起来，避免窄屏被裁切。
- 不要在 `td` 上加 `whitespace-nowrap` 限制操作列。

### 8.3 搜索 / 清除

```html
<button type="submit" name="_event" value="btnSearch1_Click"
        class="inline-flex items-center gap-1.5 rounded-md bg-blue-600 px-3.5 py-2 text-sm font-medium text-white transition hover:bg-blue-700 ...">
    <svg class="h-4 w-4" ...>...</svg> 検索
</button>
<button type="submit" name="_event" value="btnClear1_Click"
        class="inline-flex items-center gap-1.5 rounded-md border border-slate-300 bg-white px-3.5 py-2 text-sm font-medium text-slate-700 transition hover:bg-slate-50 ...">
    クリア
</button>
```

### 8.4 按钮映射表

| 旧 class | 新样式 |
|---|---|
| `btn btn-primary` | 主操作（`bg-blue-600`） |
| `btn btn-default` | 副操作（白底边框） |
| `btn btn-search` | 主操作 |
| `btn btn-clear` | 副操作 |
| `btn btn-new` | 主操作（列表页头） |
| `btn btn-csv` | 副操作（列表页头，带下载图标） |
| `btn btn-sm btn-view` | 小按钮 · 浅蓝 |
| `btn btn-sm btn-edit` | 小按钮 · 蓝 |
| `btn btn-sm btn-copy` | 小按钮 · 琥珀 |
| `btn btn-sm btn-del` | 小按钮 · 红 |

---

## 9. 列表 / 表格

### 9.1 结构

```html
<div class="overflow-hidden rounded-xl border border-slate-200 bg-white shadow-sm">
    <!-- 工具条 -->
    <div class="flex flex-wrap items-center justify-between gap-3 border-b border-slate-200 px-4 py-3">
        <h2 class="text-sm font-semibold text-slate-700">検索結果</h2>
        <span class="rounded-full bg-slate-100 px-2.5 py-0.5 text-xs font-medium text-slate-600"
              th:text="${#lists.size(form.Customers)} + ' 件'">0 件</span>
    </div>

    <!-- 滚动区域：必须带 table-scroll（JS 把 pager 插到它后面） -->
    <div class="table-scroll max-h-[60vh] overflow-auto">
        <table data-grid="Customers" data-page-sizes="10,20,30,50"
               class="w-full min-w-[1200px] border-collapse text-sm">
            <thead>
                <tr>
                    <th class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500 w-12">No</th>
                    <th class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500">取引先コード</th>
                    ...
                </tr>
            </thead>
            <tbody>
                <tr th:each="row, st : *{Customers}"
                    th:attr="data-row=${st.count}"
                    class="hover:bg-blue-50"
                    th:classappend="${(st.index &gt;= 10 ? 'hidden' : (st.even ? 'bg-slate-50' : ''))}">
                    <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-center w-12 text-slate-400 seq">1</td>
                    <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-left">...</td>
                    ...
                    <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-center">
                        <div class="flex flex-wrap justify-center gap-1">
                            <!-- 行内按钮 -->
                        </div>
                    </td>
                </tr>
                <th:block th:if="${#lists.isEmpty(form.Customers)}">
                    <tr th:replace="~{parts/field :: emptyRow(11, '取引先がありません', '検索条件を変更して、もう一度検索してください。')}"></tr>
                </th:block>
            </tbody>
        </table>
    </div>
</div>
```

### 9.2 表头 / 单元格

| 位置 | class |
|---|---|
| `th` | `sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500` |
| `td` | `border-b border-slate-100 px-3 py-2.5 align-middle` + `text-left` / `text-center` / `text-right` |
| 序号 | `w-12 text-center text-slate-400 seq` |
| 操作列 | `text-center`，内部 `flex flex-wrap justify-center gap-1` |

### 9.3 行状态（JS 依赖）

| 状态 | 写法 | 说明 |
|---|---|---|
| 初始隐藏（第 2 页以后） | `hidden` | 服务端渲染时加；JS 会移除 |
| 斑马纹 | `bg-slate-50` | 服务端偶数行加；JS 重新计算 |
| 悬停 | `hover:bg-blue-50` | 放在 `<tr>` |
| 可点击行（弹窗） | 追加 `cursor-pointer` | |

**旧写法 `alt` / `pg-off` 已废弃**，不要再用；JS 已经改成 `bg-slate-50` / `hidden`。

### 9.4 空状态

- `emptyRow` 渲染为 `<tr class="empty">`。
- `colspan` 必须等于实际列数（当前列表/弹窗都是 11；生成器按列数计算）。
- `table` 的 `min-w-[1200px]` 也按列数调整（当前 11 列）。
- JS 的 `rows()` 会过滤 `.empty`，不要改这个 class。
- 外层用 `<th:block th:if="${#lists.isEmpty(form.Customers)}">` 包住，避免有数据时也显示空行。
- 不要在同一元素上同时写 `th:if` 和 `th:replace`（`th:replace` 优先级更高，`th:if` 可能失效）。

### 9.5 分页器（JS 生成）

- 容器：`pager flex flex-wrap items-center justify-end gap-1.5 px-3 py-3`
- 件数：`total mr-auto text-sm text-slate-600`
- 每页件数：`page-size mr-2 h-8 w-32 rounded-md border border-slate-300 bg-white px-2 text-sm text-slate-700`
- 页码按钮：
  - 普通：`pg inline-flex h-8 min-w-8 items-center justify-center rounded-md border border-slate-300 bg-white px-2 text-sm text-slate-700 transition hover:border-blue-500 hover:text-blue-600`
  - 当前：`pg active inline-flex h-8 min-w-8 ... border-blue-600 bg-blue-600 font-semibold text-white`
  - 禁用：`pg disabled pointer-events-none inline-flex h-8 min-w-8 ... border-slate-200 bg-slate-50 text-slate-300`
  - 省略号：`pg dots inline-flex h-8 min-w-8 cursor-default ... text-slate-400`
- 插入位置：`(table.closest('.table-scroll') || table).after(pager)`。
- **`table-scroll` 必须存在**，否则 pager 会被插进滚动容器里，被 `max-h` 裁掉。

### 9.6 排序 / 列宽 hook

- `th` 加 `sortable` 后 JS 才绑定点击；排序箭头由 JS 插入 `<span class="sort-indicator ml-1 text-[10px] text-blue-600">▲/▼</span>`。
- 列宽拖拽手柄是 `th .col-resizer`（当前模板未生成手柄；如启用，需同时保留 `col-resizing` 的 body class，样式可再补一个 `cursor-col-resize select-none`）。

---

## 10. MessageView

### 10.1 可折叠（`<details>`）

```html
<details class="message-view relative mb-4 rounded-xl border border-slate-200 bg-white px-4 py-3 shadow-sm"
         th:classappend="${messages.empty} ? 'hidden'"
         data-levels="Error,Warning,Info" data-clear-on-event="true"
         th:open="${messages.hasError}">
    <summary class="message-summary cursor-pointer list-none select-none text-sm font-semibold text-slate-600 [&::-webkit-details-marker]:hidden"
             th:text="${messages.summary}">メッセージ</summary>
    <ul class="message-list mt-2 space-y-1">
        <li class="message-item message-error flex items-start gap-2 text-sm text-red-600" ...>
            <span class="shrink-0" aria-hidden="true">✖</span>
            <span class="message-text" ...>...</span>
        </li>
        ...
    </ul>
    <button type="button" class="message-close absolute right-3 top-2.5 text-slate-400 transition hover:text-slate-600" title="閉じる">×</button>
</details>
```

### 10.2 不可折叠（详情 / 弹窗）

```html
<div class="message-view relative mb-4 rounded-xl border border-slate-200 bg-white px-4 py-3 shadow-sm"
     th:classappend="${messages.empty} ? 'hidden'"
     data-levels="Error,Warning,Info" data-clear-on-event="true">
    <ul class="message-list mt-2 space-y-1">...</ul>
    <button type="button" class="message-close absolute right-3 top-2.5 ...">×</button>
</div>
```

规则：

- 空的时候由 `th:classappend="${messages.empty} ? 'hidden'"` 隐藏；JS 的 `refresh()` 也会 `classList.toggle('hidden', items.length === 0)`。
- 关闭按钮的 JS 现在加 `hidden`（**不再使用 `message-closed`**）。
- 图标用显式 `<span>`（JS 动态添加消息时也会插入），不要用 CSS `::before`。
- 层级颜色：Error `text-red-600`、Warning `text-amber-600`、Info `text-blue-600`。
- `message-view` / `message-list` / `message-item` / `message-error` / `message-warning` / `message-info` / `message-summary` / `message-text` / `message-close` 都是 JS hook，必须保留。

---

## 11. 弹窗（Popup）

```html
<div class="popup-overlay fixed inset-0 z-50 flex items-center justify-center bg-slate-900/50 p-4" th:if="${popup}">
    <iframe class="popup-frame max-h-[90vh] w-full max-w-[95vw] rounded-xl border-0 bg-white shadow-2xl"
            th:src="@{${popup.url}}" th:title="${popup.screenId}"
            th:style="|width: min(95vw, ${popup.width}); height: min(90vh, ${popup.height})|"></iframe>
</div>
```

- 背景遮罩：`bg-slate-900/50`（可选 `backdrop-blur-sm`）。
- 宽高是服务端动态值，保留 `th:style`；其余边框/圆角/阴影用 utility。
- 弹窗页面（`UserPopup`）自己也是完整 HTML，使用 `mx-auto w-full max-w-[1400px] p-4 sm:p-6`，不套后台 header/sidebar。

---

## 12. 错误页

`error.tpl` 使用独立布局：

```html
<body class="min-h-screen bg-slate-100 text-slate-800">
<main class="mx-auto flex min-h-screen max-w-xl flex-col justify-center px-6 py-16">
    <div class="rounded-xl border border-slate-200 bg-white p-8 shadow-sm">
        <h1 class="text-xl font-bold text-slate-900">処理できませんでした</h1>
        <p class="text-sm leading-6 text-slate-600" ...></p>
        <dl class="mt-6 grid grid-cols-[auto_1fr] gap-x-6 gap-y-2 rounded-lg bg-slate-50 px-4 py-3 text-sm">...</dl>
        <a class="mt-6 inline-flex items-center gap-2 rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white transition hover:bg-blue-700" ...>トップへ戻る</a>
    </div>
</main>
</body>
```

`/error`（404 等）与业务错误共用这个页面。

---

## 13. JS 契约（必须保留的 class / 属性）

| class / 属性 | 用途 | 删除后的后果 |
|---|---|---|
| `message-view` `message-list` `message-item` `message-error` `message-warning` `message-info` `message-summary` `message-text` `message-close` | 消息显示 / 添加 / 折叠 / 关闭 | 消息无法显示或关闭 |
| `pager` `pg` `total` `page-size` `active` `disabled` `dots` | 分页器 | 分页失效 |
| `table-scroll` | pager 插入位置 | pager 被裁切 |
| `sortable` `sort-asc` `sort-desc` `sort-indicator` `col-resizer` `seq` | 排序 / 列宽 / 行号（当前画面未启用排序/列宽，启用时保留） | 排序或行号异常 |
| `btn-popup` `wareki-picker` `ico-search` `ico-date` | 虫眼鏡 / 日历图标操作 | 点击图标无反应 |
| `empty`（`tr`） | 空状态行 | 分页把空行算作数据 |
| `hidden` | 行 / 消息隐藏 | 分页、关闭消息失效 |
| `bg-slate-50` | 斑马纹 | 斑马纹失效 |
| `screen-busy`（`html`） | 处理中光标 | 无处理中提示 |
| `data-node` | 画面项目定位（生成器/TS） | 代码侧找不到项目 |
| `data-grid` `data-page-sizes` `data-sortable` | 表格行为 | 分页/排序失效 |
| `data-item` | 默认按钮 / 事件 | Enter 键默认按钮失效 |

**动态 class 的硬性要求**：

- Tailwind 只识别**完整字面量**。`'text-' + color + '-600'` 不会生成 CSS。
- 必须写成完整字符串，例如：

```js
level === 'Error' ? 'text-red-600' : level === 'Warning' ? 'text-amber-600' : 'text-blue-600'
```

- JS 生成的分页、消息 class 必须出现在 `.tpl` 或 `static/js` 的源码中（当前已满足）。
- 如果生成器把 JS 放到外部 `.ts` 再编译，确保 `@source` 包含编译后的 `.js` 或 `.ts` 源文件。

**旧 → 新状态 class 对照：**

| 旧 | 新 |
|---|---|
| `alt` | `bg-slate-50` |
| `pg-off` | `hidden` |
| `message-closed` | `hidden` |
| `col-resizing`（CSS 规则） | 已删除；如启用列宽拖拽，用 `cursor-col-resize select-none` 自行补 |

---

## 14. Tailwind 编写规则

1. **只写 utility**，不写 `<style>`、不写 `@layer components`、不写 `@apply`（`body` 与 `screen-busy` 除外）。
2. 不写 hex / `rgb()` / `var(--...)`；颜色从 `slate` / `blue` / `emerald` / `amber` / `red` 选。
3. 任意值语法：
   - 空格用 `_`：`grid-cols-[9rem_minmax(0,1fr)]`、`bg-[right_0.75rem_center]`
   - 斜杠：`max-h-[60vh]`、`w-[calc(100%-1rem)]`
   - 变体里的 `&` **不要写成 `&amp;`**：`[&::-webkit-details-marker]:hidden`（Tailwind 扫描原始文本）
4. 同一元素不要同时写冲突的 `text-sm` 与 `text-xs`；需要小按钮时用独立的一套 class。
5. 响应式：移动优先，`sm:` / `lg:` / `xl:` 递增；不要在桌面端写死 `min-width: 1800px` 之类。
6. 可访问性：
   - 图标 `aria-hidden="true"`；纯图标按钮加 `aria-label`。
   - 必填星号 `aria-hidden="true"`（由 `required` 逻辑或服务端校验承担语义）。
   - 激活菜单 `aria-current="page"`；抽屉按钮 `aria-expanded` / `aria-controls`。
   - 焦点可见：统一 `focus:outline-none focus-visible:ring-2 focus-visible:ring-*`。
   - 表头建议在生成器中追加 `scope="col"`（当前模板可后续补）。
7. 表格：操作列用 `flex flex-wrap`，不要 `whitespace-nowrap` 卡死；避免 4 个按钮被裁切。
8. 长表单：不要用 sticky footer 覆盖字段；操作栏放在卡片正常流末尾，或给内容预留 `pb-*`。

---

## 15. 生成器模板迁移映射

> 生成器模板的实际路径以你们仓库为准，下面用逻辑名称表示。

### 15.1 文件级改动

| 生成器模板 / 资源 | 操作 |
|---|---|
| `spring/src/main/resources/templates/base/WebPageBaseLayout.tpl(.tmpl)` | 用本文第 6 节替换 |
| `spring/src/main/resources/templates/parts/field.tpl`（既存） | 用本文第 7 节 + 附录 A 的内容替换（**不新增文件**） |
| `spring/src/main/resources/templates/parts/layout.tpl`（既存） | 用 Tailwind 版替换（简单布局，未使用也保留路径） |
| `spring/src/main/resources/templates/screen/*.tpl(.tmpl)` | 字段 → fragment；按钮 → utility；表格 → 第 9 节 |
| `spring/src/main/resources/templates/error.tpl(.tmpl)` | 用本文第 12 节替换 |
| `spring/src/main/resources/templates/parts/stylesheets.tpl(.tmpl)` | 依次输出 `app.css` → `Stylesheet.css`（Base 由布局先加载） |
| `spring/src/main/resources/static/css/BaseStylesheet.css` | 生成器固定输出 **base CSS**（theme + preflight + base），页面**先**加载 |
| `spring/src/main/resources/static/css/Stylesheet.css` | 项目自定义覆盖层（最后加载；只放品牌色/印刷/滚动条等横断覆盖） |
| `spring/src/main/resources/static/css/app.css` | 打包 **theme + utilities**（项目外构建，提交产物） |
| `.stylesheets`（生成器元数据） | 内容改为 `app.css` + `Stylesheet.css`（顺序固定） |
| `spring/pom.xml.tmpl` | 不加 CSS / npm 插件（构建在项目外） |
| `../cem-tailwind-build/` | 项目外构建目录（base-input.css / app-input.css / package.json / node_modules） |

### 15.2 生成器概念 → 新组件

| 生成器概念 | 旧输出 | 新输出 |
|---|---|---|
| 页面 | `<h1>` + 表单 | `pageHeader` + 卡片 + 操作栏 |
| Panel | `class="panel"` | `rounded-xl border border-slate-200 bg-white p-6 shadow-sm`（搜索条件可用 `grid ... xl:grid-cols-2`） |
| Field（text） | `ctl w-sm` | `fieldText(...)` |
| Field（select） | `ctl sel w-sm` | `fieldSelect(...)` |
| Field（date/datetime） | `ctl w-sm ico-date` | `fieldDate` / `fieldDateTime` |
| Field（textarea） | `ctl ta` | `fieldTextarea` |
| Button（搜索/清除/新建/导出/确定/取消/返回） | `btn btn-*` | 第 8 节对应 utility |
| Table / Grid | `table.data` | `table-scroll max-h-[60vh] overflow-auto` + 第 9 节表头/单元格 |
| Pager | CSS `.pager` | JS 生成的 utility（第 9.5 节） |
| MessageView | CSS 组件 | 第 10 节 + `hidden` 控制 |
| Popup | CSS 遮罩 | 第 11 节 |
| Error | `app.css` 旧样式 | 第 12 节 |

### 15.3 旧 class → 新 class 速查

| 旧 | 新 |
|---|---|
| `field` / `controls` / `label-cell` / `cell` | fragment 内部（`grid` + `contents` + `flex`） |
| `label-item` | `text-sm font-medium text-slate-700` |
| `error` | `col-start-2 text-xs text-red-600` |
| `required` | fragment 的 `required=true`（`<span class="text-red-500">*</span>`） |
| `ctl` | 第 7.3 节 input class |
| `sel` | 第 7.3 节 select class（`pr-8` 保留原生箭头） |
| `ico-search` / `ico-date` | 保留 hook + 内联 `<svg>` 绝对定位 + `!pr-10`（不新增图片文件） |
| `w-sm` / `w-md` / `w-full` | 输入统一 `w-full`，外层 `max-w-sm` |
| `btn` / `btn-*` | 第 8 节 |
| `a-left` / `a-center` / `a-right` | `text-left` / `text-center` / `text-right` |
| `row-number` | `w-12 text-center text-slate-400` |
| `cell-panel` | `text-center`（内部 `flex flex-wrap`） |
| `data` | `w-full min-w-[1200px] border-collapse text-sm` |
| `card` | `rounded-xl border border-slate-200 bg-white shadow-sm` |
| `table-scroll` | `table-scroll max-h-[60vh] overflow-auto`（hook 必须保留） |
| `pager` / `pg` / `total` / `page-size` | 保留 hook，样式为 utility |
| `rec-end` | 删除（行统一 `border-b`） |
| `alt` | `bg-slate-50` |
| `pg-off` | `hidden` |
| `message-closed` | `hidden` |
| `layout` | `space-y-6` |

### 15.4 迁移步骤

1. 同路径复制 `parts/field.tpl`、`parts/layout.tpl`、`base/WebPageBaseLayout.tpl`、`error.tpl`（**不要**把 `input.css` / `package.json` 复制进生成项目）。
2. 用第 7 节 / 附录 A 的内容替换 `parts/field.tpl`（不是新增文件）。
3. 替换 `base/WebPageBaseLayout.tpl`（第 6 节）。
4. 替换 `error.tpl`（第 12 节）。
5. 把生成器的字段输出改为 fragment 调用（第 7.4 节映射）。
6. 把按钮输出改为第 8 节 class。
7. 把表格 / 分页 / 消息 / 弹窗输出改为第 9–11 节。
8. 同步 JS：
   - `alt` → `bg-slate-50`
   - `pg-off` → `hidden`
   - `message-closed` → `hidden`
   - `pager` 内部 class 按第 9.5 节
   - 保留 `table-scroll`、`empty`、`sort-*`、`btn-popup`、`wareki-picker`、`ico-*`
9. `parts/stylesheets.tpl` 依次输出 `app.css` → `Stylesheet.css`；`BaseStylesheet.css` 输出 base CSS（theme + preflight + base）；页面按 Base → app → Stylesheet 加载。
10. 在项目外构建 CSS，再回项目 `mvn clean package`，最后验收（第 17 节）。

### 15.5 推荐给生成器的输出策略

- **字段**：根据字段类型直接调用 `fieldText` / `fieldSelect` / `fieldDate` / `fieldDateTime` / `fieldTextarea`，不要先输出旧 class 再转换。
- **按钮**：用一个 `button(label, variant)` 的内部函数输出统一 class；行内按钮用 `variant=small-*`。
- **表格**：把列定义集中生成 `th` / `td` class，避免每列手写。
- **分页**：保留当前 JS（或 TS 源），只替换 class 字符串。
- **消息**：保留 `message-*` hook，样式用 utility。
- **空状态**：用 `emptyRow` fragment，`colspan` 由列数计算。

### 15.6 生成器伪代码（字段 / 按钮 / 表格）

```text
// 字段
emitField(field):
  node = "v:" + field.node
  switch field.type:
    case TEXT:
      <div th:replace="~{parts/field :: fieldText('{node}', '{label}', '{property}',
           {required or null}, {maxlength or null}, {readonly or null}, {hidden or null})}"></div>
    case SELECT:
      <div th:replace="~{parts/field :: fieldSelect('{node}', '{label}', '{property}',
           ${'{itemsModelAttribute}'}, {required or null}, {blank or null}, {hidden or null})}"></div>
    case DATE:
      <div th:replace="~{parts/field :: fieldDate('{node}', '{label}', '{property}',
           {required or null}, {readonly or null}, {hidden or null})}"></div>
    case DATETIME:
      <div th:replace="~{parts/field :: fieldDateTime('{node}', '{label}', '{property}',
           {required or null}, {readonly or null}, {hidden or null})}"></div>
    case TEXTAREA:
      <div th:replace="~{parts/field :: fieldTextarea('{node}', '{label}', '{property}',
           {required or null}, {rows or null}, {readonly or null}, {hidden or null})}"></div>

// 按钮（根据 variant 选择 class）
emitButton(label, variant):
  primary   -> "inline-flex items-center justify-center gap-1.5 rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white shadow-sm transition hover:bg-blue-700 ..."
  secondary -> "inline-flex items-center justify-center gap-1.5 rounded-md border border-slate-300 bg-white px-4 py-2 text-sm font-medium text-slate-700 shadow-sm transition hover:bg-slate-50 ..."
  danger    -> "... bg-red-600 text-white hover:bg-red-700 ..."
  small     -> "inline-flex items-center justify-center gap-1 rounded px-2.5 py-1 text-xs font-medium ..."

// 表格
emitTable(columns):
  <div class="table-scroll max-h-[60vh] overflow-auto">
    <table class="w-full min-w-[1200px] border-collapse text-sm" data-grid="...">
      <thead><tr>
        for each column:
          <th class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200
                     bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold
                     tracking-wide text-slate-500">{caption}</th>
      </tr></thead>
      <tbody>
        <tr th:each="..." class="hover:bg-blue-50"
            th:classappend="${(st.index >= 10 ? 'hidden' : (st.even ? 'bg-slate-50' : ''))}">
          ...
        </tr>
        <th:block th:if="${#lists.isEmpty(...)}">
          <tr th:replace="~{parts/field :: emptyRow({columns.size()}, '{emptyTitle}', '{emptyDescription}')}"></tr>
        </th:block>
      </tbody>
    </table>
  </div>
```

### 15.7 移植注意事项（生成器特有）

1. **fragment 调用用位置参数**，顺序固定（见第 7.1 节）；不要用命名参数，避免签名重命名后失配。
2. **参数用逗号分隔**：`label` 里不要出现英文逗号或单引号。日语标签目前没有；如未来出现，改用 `th:with` 先定义变量再传。
3. **`th:field` 的字段名**必须与表单属性名一致；`field` 参数同时用于 `label for`。嵌套属性（如 `customer.name`）需要生成器确认 `id` 生成规则。
4. **分页 / 消息 / 排序脚本**在 `screen/*.tpl` 的内联 `<script>` 里，不是独立 JS 文件；移植时要把这些脚本一起带走。若生成器从 TypeScript 生成 JS，请改 TS 源并保持同样的 class。
5. **`@source` 的模板后缀**：当前是 `*.tpl`。如果生成器输出 `.html`，把 `@source` 改成 `../springboot/src/main/resources/templates/**/*.html`。
6. **生成器模板自身的占位符**（如 `${...}`）可能与 Thymeleaf 表达式冲突，按生成器的转义规则处理，生成结果必须是合法 Thymeleaf。
7. **`min-w`、`colspan`、`max-h`** 都按列数/画面调整；不要照抄 1200px 到列数差异很大的画面。
8. **CSS 三层**：`BaseStylesheet.css` = theme + preflight + base（底层）；`app.css` = theme + utilities（打包）；`Stylesheet.css` = 项目自定义覆盖（最后加载）。不要让旧的 Element Plus 风格 CSS 再生成。
9. **`parts/stylesheets.tpl`** 如仍被生成器使用，只输出 `/css/app.css` 一个 `<link>`；不要重复加载。
10. **错误页 / Popup 页面**不经过 `WebPageBaseLayout`，必须自己包含 `<link ... /css/app.css>`（当前已包含）。
11. **先复制文件再改生成逻辑**：`field.tpl`、`layout.tpl`、`WebPageBaseLayout.tpl`、`error.tpl` 是“成品”，直接同路径复制比按文档重写更不容易漏 class；`input.css` 只放在项目外。
12. **验收以浏览器为准**：Tailwind 是编译期生成，class 拼错不会报错，只会“没样式”；按第 17 节逐项检查。

---

## 16. 构建 / CI 检查

```bash
# 1. 项目外构建 CSS
cd ../cem-tailwind-build
npm ci
npm run css:build

# 2. Java（必须 clean）
cd ../springboot
mvn clean package

# 3. 产物检查：BaseStylesheet.css（base）+ app.css（utilities）+ Stylesheet.css（自定义覆盖）
unzip -l target/cem-0.0.1-SNAPSHOT.jar | grep "static/css"

# 4. 旧 CSS 引用检查（どちらも何も出なければ OK）
if grep -R "<style" src/main/resources/templates; then echo "NG: <style> が残っています"; fi
# 読み込み順（Base → app → Stylesheet）を確認
grep -n "BaseStylesheet.css\|app.css\|Stylesheet.css" src/main/resources/templates/base/WebPageBaseLayout.tpl
```

---

## 17. 验收清单

### 17.1 启动与路由

- `/` → **404**（本次未新增 Java 文件；若需要根重定向，请在既有的 Java 配置里处理，不要新增文件）
- `/CustomerMasterList` → 200
- `/CustomerMasterDetail` → 200
- `/UserPopup` → 200
- `/Developer` → 200
- `/css/app.css` → 200
- `/css/BaseStylesheet.css` → 200（base CSS，约 4.4 KB）
- `/css/Stylesheet.css` → 200（自定义覆盖层）
- `/nonexistent` → 404 且显示新错误页

### 17.2 浏览器行为

- 检索：1000 条、首屏 10 条、分页 100 页；翻页、斑马纹、悬停正常。
- 0 条：显示空状态行、工具条 `0 件`、分页 `件数：0`。
- 详情：带出数据；只读字段不可编辑；必填星号显示。
- 校验：输入 11 位取引先コード → 字段下方显示错误，MessageView 显示错误。
- 消息：关闭按钮隐藏；新消息显示。
- 虫眼鏡：打开用户选择弹窗。
- 移动端（<1024）：汉堡打开抽屉、遮罩点击关闭、Esc 关闭。
- 控制台无 JS 错误。

### 17.3 样式检查

- 页面无 `<style>`；除 popup iframe 动态宽高外无内联 `style`。
- 项目源码中只有 `app.css`；Tailwind 输入 `input.css` 在项目外（`../cem-tailwind-build/`），不进入生成物。
- `app.css` 由 Tailwind 生成，无旧 `:root` 变量 / Element Plus 风格规则。

---

## 18. 常见坑

1. **忘记 `npm run css:build`**：新 class 没有生成，页面看起来“没生效”。
2. **`mvn package` 没加 `clean`**：旧 CSS 留在 `target/classes` 并进 jar。
3. **Fragment 参数少传**：`Cannot resolve fragment...`；所有参数都要写，未用传 `null`。
4. **`th:if` 和 `th:replace` 同一元素**：`th:replace` 先生效，`th:if` 可能失效；用 `<th:block th:if>` 包住。
5. **删掉 JS hook**：`table-scroll`、`empty`、`message-*`、`pager`、`pg`、`btn-popup`、`ico-*`、`seq` 等。
6. **动态拼 class**：Tailwind 不生成。
7. **`&amp;` 写进任意变体**：`[&amp;::-webkit-details-marker]` 不会被识别，必须写 `[&::-webkit-details-marker]`。
8. **操作列 `whitespace-nowrap`**：窄屏 4 个按钮被裁切；改用 `flex flex-wrap`。
9. **`table-scroll` 丢失**：pager 被插进 `max-h` 滚动区，显示不全。
10. **弹窗 iframe 用 class 写死宽高**：宽高是服务端动态值，保留 `th:style`。
11. **`;jsessionid`**：导致 partial update 整页重绘、菜单激活态丢失；保留 `server.servlet.session.tracking-modes=cookie`。
12. **把旧 CSS 模板重新生成回来**：`BaseStylesheet.css` 只放 Tailwind 的 base 层（theme/preflight/base），不要放旧的 Element Plus 风格；`Stylesheet.css` 只放项目覆盖。

---

## 附录 A：`parts/field.tpl` 片段签名（完整实现见源文件 `src/main/resources/templates/parts/field.tpl`）

```html
<div th:fragment="fieldText(node, label, field, required, maxlength, readonly, hidden)">...</div>
<div th:fragment="fieldSelect(node, label, field, items, required, blank, hidden)">...</div>
<div th:fragment="fieldDate(node, label, field, required, readonly, hidden)">...</div>
<div th:fragment="fieldDateTime(node, label, field, required, readonly, hidden)">...</div>
<div th:fragment="fieldTextarea(node, label, field, required, rows, readonly, hidden)">...</div>
<div th:fragment="pageHeader(title, description)">...</div>
<tr th:fragment="emptyRow(colspan, title, description)">...</tr>
```

## 附录 B：列表页最小骨架

```html
<main class="min-w-0 flex-1 overflow-auto p-4 sm:p-6">
    <div class="mx-auto mb-6 flex max-w-[1600px] flex-wrap items-end justify-between gap-4">
        <div th:replace="~{parts/field :: pageHeader('取引先マスタ一覧', '取引先の検索・登録・編集を行います。')}"></div>
        <div class="flex flex-wrap items-center gap-2">
            <button type="button" class="inline-flex items-center gap-1.5 rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white shadow-sm transition hover:bg-blue-700">新規作成</button>
            <button type="button" class="inline-flex items-center gap-1.5 rounded-md border border-slate-300 bg-white px-4 py-2 text-sm font-medium text-slate-700 shadow-sm transition hover:bg-slate-50">CSV 出力</button>
        </div>
    </div>

    <form class="mx-auto max-w-[1600px]" th:action="@{/CustomerMasterList}" th:object="${form}" method="post">
        <div data-node="n:TableMain" class="space-y-6">
            <div data-node="n:panel1" class="grid grid-cols-1 gap-x-8 rounded-xl border border-slate-200 bg-white p-6 shadow-sm xl:grid-cols-2">
                <div class="mb-1 flex items-center justify-between xl:col-span-2">
                    <h2 class="text-sm font-semibold text-slate-700">検索条件</h2>
                </div>
                <div th:replace="~{parts/field :: fieldText('v:CustomerCD', '取引先コード', 'CustomerCD', null, 10, null, null)}"></div>
                ...
                <div class="mt-2 flex flex-wrap items-center gap-3 border-t border-slate-100 pt-5 xl:col-span-2">
                    <button type="submit" name="_event" value="btnSearch1_Click" class="inline-flex items-center gap-1.5 rounded-md bg-blue-600 px-3.5 py-2 text-sm font-medium text-white transition hover:bg-blue-700">検索</button>
                    <button type="submit" name="_event" value="btnClear1_Click" class="inline-flex items-center gap-1.5 rounded-md border border-slate-300 bg-white px-3.5 py-2 text-sm font-medium text-slate-700 transition hover:bg-slate-50">クリア</button>
                </div>
            </div>

            <details class="message-view relative mb-4 rounded-xl border border-slate-200 bg-white px-4 py-3 shadow-sm" ...>...</details>

            <div class="overflow-hidden rounded-xl border border-slate-200 bg-white shadow-sm">
                <div class="flex flex-wrap items-center justify-between gap-3 border-b border-slate-200 px-4 py-3">
                    <h2 class="text-sm font-semibold text-slate-700">検索結果</h2>
                    <span class="rounded-full bg-slate-100 px-2.5 py-0.5 text-xs font-medium text-slate-600" th:text="${#lists.size(form.Customers)} + ' 件'">0 件</span>
                </div>
                <div class="table-scroll max-h-[60vh] overflow-auto">
                    <table data-grid="Customers" data-page-sizes="10,20,30,50" class="w-full min-w-[1200px] border-collapse text-sm">...</table>
                </div>
            </div>
        </div>
    </form>
</main>
```

## 附录 C：详情页骨架

```html
<main class="min-w-0 flex-1 overflow-auto p-4 sm:p-6">
    <div class="mx-auto mb-6 max-w-[1600px]">
        <div th:replace="~{parts/field :: pageHeader('取引先マスタ詳細', '取引先の情報を確認・編集します。')}"></div>
    </div>

    <form class="mx-auto max-w-[1600px]" th:action="@{/CustomerMasterDetail}" th:object="${form}" method="post">
        <div class="message-view ...">...</div>

        <div data-node="n:TableMain" class="space-y-6">
            <div data-node="n:Panel" class="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
                <div class="mb-6 flex flex-wrap items-center justify-between gap-3 border-b border-slate-100 pb-4">
                    <div class="flex items-center gap-3">
                        <button type="submit" name="_event" value="btnReturn_Click" class="inline-flex items-center gap-1 text-sm font-medium text-slate-500 transition hover:text-slate-900">← 一覧へ戻る</button>
                        <span class="h-4 w-px bg-slate-200" aria-hidden="true"></span>
                        <h2 class="text-base font-semibold text-slate-900">取引先情報</h2>
                    </div>
                    <span class="text-xs text-slate-400"><span class="text-red-500">*</span> は必須項目</span>
                </div>

                <fieldset data-node="v:Customer">
                    <div class="grid grid-cols-1 gap-x-8 xl:grid-cols-2">
                        <div th:replace="~{parts/field :: fieldText('v:Customer.MST00004_CustomerCD', '取引先コード', 'CustomerMST00004_CustomerCD', true, 10, null, null)}"></div>
                        ...
                    </div>
                </fieldset>

                <div class="mt-6 flex flex-wrap items-center justify-end gap-3 border-t border-slate-200 pt-5">
                    <button type="submit" name="_event" value="btnOk_Click" class="inline-flex items-center justify-center gap-1.5 rounded-md bg-blue-600 px-5 py-2 text-sm font-medium text-white transition hover:bg-blue-700">確定</button>
                    <button type="button" class="inline-flex items-center justify-center gap-1.5 rounded-md border border-slate-300 bg-white px-4 py-2 text-sm font-medium text-slate-700 transition hover:bg-slate-50">キャンセル</button>
                </div>
            </div>
        </div>
    </form>
</main>
```

## 附录 D：Popup 页面骨架

```html
<body class="bg-white text-slate-800">
<main class="mx-auto w-full max-w-[1400px] p-4 sm:p-6">
    <div class="mb-6">
        <div th:replace="~{parts/field :: pageHeader('ユーザ選択', '一覧からユーザを選択してください。')}"></div>
    </div>

    <form th:action="@{/UserPopup}" th:object="${form}" method="post">
        <div data-node="n:TableMain" class="space-y-6">
            <div data-node="n:panel1" class="grid grid-cols-1 gap-x-8 rounded-xl border border-slate-200 bg-white p-6 shadow-sm sm:grid-cols-2">
                <div th:replace="~{parts/field :: fieldText('v:UserCD', 'ユーザコード', 'UserCD', null, 10, null, null)}"></div>
                <div th:replace="~{parts/field :: fieldText('v:UserNm', 'ユーザ名', 'UserNm', null, 50, null, null)}"></div>
            </div>
            <div class="overflow-hidden rounded-xl border border-slate-200 bg-white shadow-sm">
                <div class="flex flex-wrap items-center justify-between gap-3 border-b border-slate-200 px-4 py-3">
                    <h2 class="text-sm font-semibold text-slate-700">検索結果</h2>
                    <span class="rounded-full bg-slate-100 px-2.5 py-0.5 text-xs font-medium text-slate-600" th:text="${#lists.size(form.Users)} + ' 件'">0 件</span>
                </div>
                <div class="table-scroll max-h-[55vh] overflow-auto">...</div>
            </div>
            <div class="flex flex-wrap items-center justify-end gap-3 border-t border-slate-100 pt-5">...</div>
        </div>
    </form>
</main>
```

## 附录 E：分页器 DOM（JS 生成）

```html
<div class="pager flex flex-wrap items-center justify-end gap-1.5 px-3 py-3">
    <span class="total mr-auto text-sm text-slate-600">件数：<span>1000</span></span>
    <select class="page-size mr-2 h-8 w-32 rounded-md border border-slate-300 bg-white px-2 text-sm text-slate-700">...</select>
    <a class="pg inline-flex h-8 min-w-8 items-center justify-center rounded-md border border-slate-300 bg-white px-2 text-sm text-slate-700 transition hover:border-blue-500 hover:text-blue-600" data-page="1">‹</a>
    <a class="pg active inline-flex h-8 min-w-8 items-center justify-center rounded-md border border-blue-600 bg-blue-600 px-2 text-sm font-semibold text-white" data-page="1">1</a>
    <a class="pg inline-flex h-8 min-w-8 items-center justify-center rounded-md border border-slate-300 bg-white px-2 text-sm text-slate-700 transition hover:border-blue-500 hover:text-blue-600" data-page="2">2</a>
    <a class="pg dots inline-flex h-8 min-w-8 cursor-default items-center justify-center px-2 text-sm text-slate-400">…</a>
    <a class="pg inline-flex h-8 min-w-8 ..." data-page="100">100</a>
    <a class="pg disabled pointer-events-none inline-flex h-8 min-w-8 items-center justify-center rounded-md border border-slate-200 bg-slate-50 px-2 text-sm text-slate-300">›</a>
</div>
```
