# 生成器客户端 Tailwind 回归问题报告

| 项目 | 内容 |
|---|---|
| 对象 | 代码生成器客户端（生成 Spring Boot + Thymeleaf 画面） |
| 当前项目 | `springboot`（CEM システム） |
| 报告日期 | 2026-10-22 |
| 参考提交 | 生成器输出 `c8b71ca` / 本地修复 `a9d072f` |
| 相关规范 | `TAILWIND.md`、`TAILWIND-CLIENT-SPEC.md` |
| 结论 | **规范本身没有问题；生成器模板仍按旧结构输出，覆盖了 Tailwind 层，并引入了若干生成代码缺陷。** |

---

## 1. 结论摘要

把《生成器客户端执行版》交给生成器后，生成结果出现了 3 类问题：

1. **样式层被整体覆盖**
   - `BaseStylesheet.css` 被写成约 37 KB 的旧 Element Plus 风格；
   - `app.css` 被写成约 1.3 KB 的旧公共样式；
   - `Stylesheet.css` 被清空；
   - 页面只加载 `BaseStylesheet.css` + `Stylesheet.css`，**没有加载 `app.css`**；
   - 模板里仍是 `layout` / `panel` / `field` / `controls` / `ctl` 等旧 class，没有 `parts/field` fragment，也没有 Tailwind utility。

2. **生成代码本身不能编译**
   - `OptimisticLock.java` 调用 `ScriptDb.optimisticCheck(Optimistic)`，而当前库签名是 `optimisticCheck(Object, boolean)`；
   - `mvn clean package` 直接失败，新的页面无法启动。

3. **部分页面运行异常**
   - 生成器输出 `screen/developer.tpl`，Controller 的 `VIEW` 却是 `screen/Developer`；macOS 可能“碰巧能开”，Linux / CI 会 500。
   - 详情页 `Form.Load` 使用 `ScriptDb.load(..., true)`，无数据时抛「データがありません」，直接打开详情 URL 会 500。
   - 生成器删除了 `server.servlet.session.tracking-modes=cookie`，画面局部更新会整页重绘、侧边菜单激活态丢失。
   - `TAILWIND.md` / `TAILWIND-CLIENT-SPEC.md` 被生成器清理掉。

> **一句话：文档是规范，不会自动改变生成器模板。生成器必须按规范修改它自己的模板，否则每次生成都会把 Tailwind 实现覆盖回旧版本。**

---

## 2. 背景与预期

按规范，生成项目应保持以下结构：

```text
src/main/resources/static/css/
  BaseStylesheet.css     # 底层：Tailwind theme + preflight + base
  app.css                # 打包：theme + utilities
  Stylesheet.css         # 项目自定义覆盖（最后加载）

src/main/resources/templates/
  base/WebPageBaseLayout.tpl
  parts/field.tpl        # fieldText / fieldSelect / pageHeader / emptyRow ...
  screen/*.tpl
  error.tpl
```

页面加载顺序必须是：

```html
<link rel="stylesheet" href="/css/BaseStylesheet.css"/>
<link rel="stylesheet" href="/css/app.css"/>
<link rel="stylesheet" href="/css/Stylesheet.css"/>
```

预期大小：

| 文件 | 预期 |
|---|---|
| `BaseStylesheet.css` | 约 4–5 KB（theme + preflight + body） |
| `app.css` | 约 20 KB（theme + utilities） |
| `Stylesheet.css` | 约 1–2 KB（项目覆盖） |

---

## 3. 实际生成结果（问题证据）

### 3.1 CSS 大小与内容

| 文件 | 生成结果 | 应有结果 |
|---|---|---|
| `BaseStylesheet.css` | 38,218 B，旧 Element Plus 风格（`--brand-blue`、`.ctl`、`.btn-*`、`.message-view` 等） | 约 4.4 KB Tailwind base |
| `app.css` | 1,381 B，旧 `.app-header` / `.form-row` / `table.grid` 等 | 约 20.4 KB theme + utilities |
| `Stylesheet.css` | 0 B | 约 1.3 KB 自定义覆盖 |

### 3.2 页面 head 只加载旧 CSS

生成结果：

```html
<link rel="stylesheet" th:href="@{/css/BaseStylesheet.css}"/>
<link th:replace="~{parts/stylesheets :: links}"/>
```

而 `parts/stylesheets.tpl` 只输出：

```html
<link rel="stylesheet" th:href="@{/css/Stylesheet.css}"/>
```

结果：**`app.css` 从未被加载**，所有 Tailwind utility 都不会生效。

### 3.3 模板仍是旧 class

生成的列表页片段：

```html
<div data-node="n:TableMain" class="layout" style="width: 2400px">
  <div class="panel no-caption font-set" ...>
    <div class="field">
      <div class="controls">
        <div class="label-cell" ...>
          <label class="label-item">取引先コード</label>
        ...
        <input class="ctl w-sm">
```

规范要求的是：

```html
<div th:replace="~{parts/field :: fieldText('v:CustomerCD', '取引先コード', 'CustomerCD', null, 10, null, null)}"></div>
```

### 3.4 `parts/field.tpl` 仍是旧 fragment

生成器输出的是：

```html
<div th:fragment="text(label, field)" class="form-row">
<div th:fragment="ctlText(label, field, readOnly, rightText)" class="form-row">
...
```

没有 `fieldText` / `fieldSelect` / `fieldDate` / `fieldDateTime` / `fieldTextarea` / `pageHeader` / `emptyRow`。
因此画面即使想调用新 fragment 也会失败。

### 3.5 文档被删除

生成器清理后：

- `TAILWIND.md` 不存在
- `TAILWIND-CLIENT-SPEC.md` 不存在

---

## 4. 运行/构建问题

### 4.1 编译失败：`optimisticCheck` 参数不匹配

生成器输出 `OptimisticLock.java`：

```java
ScriptDb.optimisticCheck(this.piOptimistic);
```

当前 `ScriptDb` 的签名：

```java
public static boolean optimisticCheck(Object info, boolean lock)
```

编译错误：

```text
OptimisticLock.java:[43,17] method optimisticCheck in class ScriptDb cannot be applied to given types;
  required: java.lang.Object,boolean
  found:    com.nassen.cem.struct.Optimistic
```

同时项目里还有 `OptimisticLockingCheck.java`，它已经是正确调用：

```java
ScriptDb.optimisticCheck(this.piOptimistic, true);
```

说明生成器同时输出了新旧两个版本，`OptimisticLock` 是过期模板。

### 4.2 Developer 画面大小写不一致

生成器输出：

```text
src/main/resources/templates/screen/developer.tpl
src/main/resources/static/js/developer.js
```

Controller：

```java
private static final String VIEW = "screen/Developer";
```

macOS 默认大小写不敏感，可能看不出问题；Linux / CI 会找不到模板，页面 500。
本仓库已把 Controller 改为：

```java
private static final String VIEW = "screen/developer";
```

生成器必须统一约定：**模板文件名 = Controller 的 VIEW**。

### 4.3 详情页直接打开 500

生成的 `CustomerMasterDetailEvents.Form_Load`：

```java
ScriptDb.load(Mst00004.class, ..., true);
```

`ScriptDb.load` 的最后一个参数 `call=true` 时，无数据会抛异常：

```text
com.nassen.cem.script.ScriptDb$DbException: データがありません。
```

表现：从列表点「詳細」可以打开；直接访问 `/CustomerMasterDetail` 会 500。
应改为：

```java
if (!ScriptDb.load(Mst00004.class, ..., false)) {
    ScriptLib.msg("Error", "取引先が見つかりません。他の人が削除した可能性があります。", null);
}
```

### 4.4 会话设置被删除

生成器覆盖 `application.properties` 后，以下设置消失：

```properties
server.servlet.session.tracking-modes=cookie
```

影响：带 `;jsessionid` 的 URL 会让画面局部更新的 `action` 比较失败，导致整页重绘、侧边菜单激活态丢失。

### 4.5 详情页 500 的实际日志

```text
ERROR ... CustomerMasterDetailController : CustomerMasterDetail のイベントでエラー（データなし）
com.nassen.cem.script.ScriptDb$DbException: データがありません。
```

---

## 5. 页面影响矩阵

| 页面 | 生成后的表现 | 原因 |
|---|---|---|
| `/CustomerMasterList` | 仍能显示，但是旧 Element Plus 风格，不是 Tailwind | 旧 `BaseStylesheet.css` 覆盖；`app.css` 未加载 |
| `/CustomerMasterDetail` | 从列表进入能显示旧样式；直接打开 500 | `load(..., true)` 无数据抛异常 |
| `/UserPopup` | 旧样式，弹窗内容不是 Tailwind | 旧 CSS / 旧 class |
| `/Developer` | macOS 可能 200，Linux / CI 500 | `developer.tpl` vs `screen/Developer` |
| 所有画面 | Tailwind utility 不生效 | `app.css` 没有被加载 |
| 构建 | `mvn clean package` 失败 | `optimisticCheck` 参数数量不匹配 |

---

## 6. 生成器模板必须修改的内容

### 6.1 公共布局 head

`base/WebPageBaseLayout.tpl`：

```html
<link rel="stylesheet" th:href="@{/css/BaseStylesheet.css}"/>
<link th:replace="~{parts/stylesheets :: links}"/>
```

`parts/stylesheets.tpl`：

```html
<th:block xmlns:th="http://www.thymeleaf.org" th:fragment="links">
    <link rel="stylesheet" th:href="@{/css/app.css}"/>
    <link rel="stylesheet" th:href="@{/css/Stylesheet.css}"/>
</th:block>
```

错误页 / 独立 Popup 页面也必须按 **Base → app → Stylesheet** 输出三个 link。

### 6.2 CSS 输出

生成器不要再输出旧的 Element Plus 风格 CSS。三个文件职责：

| 文件 | 生成内容 |
|---|---|
| `BaseStylesheet.css` | Tailwind `theme` + `preflight` + `body` / `screen-busy`（约 4.4 KB） |
| `app.css` | Tailwind `theme` + `utilities`，扫描 `.tpl` / `static/js`（约 20 KB） |
| `Stylesheet.css` | 项目自定义覆盖（品牌色、印刷、滚动条等，约 1.3 KB） |

CSS 构建在项目外完成，生成器只提交构建产物；不要在生成项目里新增 `package.json` / `tailwind.css`。

### 6.3 字段输出

字段由生成器直接输出 fragment 调用，不再输出 `field` / `controls` / `ctl`：

```html
<div th:replace="~{parts/field :: fieldText('v:CustomerCD', '取引先コード', 'CustomerCD', true, 10, null, null)}"></div>
<div th:replace="~{parts/field :: fieldSelect('v:CustomerKbn', '取引先区分', 'CustomerKbn', ${CustomerKbnItems}, null, true, null)}"></div>
<div th:replace="~{parts/field :: fieldDateTime('v:CreateDate', '作成日時', 'CreateDate', null, true, null)}"></div>
```

参数顺序固定，**所有参数都要传，未使用传 `null`**：

| fragment | 参数 |
|---|---|
| `fieldText` | `node, label, field, required, maxlength, readonly, hidden` |
| `fieldSelect` | `node, label, field, items, required, blank, hidden` |
| `fieldDate` | `node, label, field, required, readonly, hidden` |
| `fieldDateTime` | `node, label, field, required, readonly, hidden` |
| `fieldTextarea` | `node, label, field, required, rows, readonly, hidden` |

### 6.4 按钮 / 表格

- 按钮：主操作 `bg-blue-600 text-white hover:bg-blue-700`，副操作 `border border-slate-300 bg-white`，危险 `bg-red-600`。
- 行内按钮：`flex flex-wrap justify-center gap-1`，不要 `whitespace-nowrap`。
- 表格：`table-scroll` + 固定表头 + `emptyRow` + `data-grid` + `data-page-sizes`。
- 行状态：初始隐藏 `hidden`，斑马纹 `bg-slate-50`（不要再用 `pg-off` / `alt`）。

### 6.5 文件名与 Controller 一致

选择一种约定并全局统一，例如：

- 模板：`screen/developer.tpl`
- `DeveloperController.VIEW = "screen/developer"`

或反过来都用 `Developer`。**不能一边 `developer.tpl`、一边 `screen/Developer`。**

### 6.6 生成 Java 的签名

- `ScriptDb.optimisticCheck(Object info, boolean lock)`：生成器必须传第二个参数。
- 只保留一个乐观排他模块（建议 `OptimisticLockingCheck`），不要同时生成签名不一致的 `OptimisticLock`。
- `Form.Load` 用 `ScriptDb.load(..., false)` 并处理返回值；不要用 `true` 后忽略结果。

### 6.7 `application.properties`

保留：

```properties
server.servlet.session.tracking-modes=cookie
```

### 6.8 文档与清理范围

生成器的 clean / 覆盖范围不要包含：

- `TAILWIND.md`
- `TAILWIND-CLIENT-SPEC.md`
- `TAILWIND-GENERATOR-REGRESSION-REPORT.md`（本文件）

### 6.9 构建与提交

```bash
cd ../cem-tailwind-build
npm run css:build

cd ../springboot
mvn clean package
```

必须提交 `BaseStylesheet.css`、`app.css`、`Stylesheet.css` 三个产物；只跑 Java 构建不会更新 CSS。

---

## 7. 验收清单

### 7.1 静态检查

```bash
# CSS 大小（期望约 4.4K / 20K / 1.3K）
wc -c src/main/resources/static/css/*.css

# 页面加载顺序必须是 Base → app → Stylesheet
grep -n "stylesheet" src/main/resources/templates/base/WebPageBaseLayout.tpl

# 旧 class / 旧 fragment 不应再出现
grep -R "class=\"ctl\|class=\"layout\|class=\"panel\|label-item\|parts/ui" src/main/resources/templates && echo NG

# 新 fragment 应存在
grep -R "parts/field :: fieldText" src/main/resources/templates | head

# 不应有内联 style 标签（除 iframe 动态宽高）
grep -R "<style" src/main/resources/templates && echo NG
```

### 7.2 构建

```bash
mvn clean package
```

期望：BUILD SUCCESS。

### 7.3 路由

```bash
for u in /CustomerMasterList /CustomerMasterDetail /UserPopup /Developer; do
  curl -s -o /dev/null -w "$u %{http_code}\n" "http://127.0.0.1:8080$u"
done
```

期望全部 200。

### 7.4 浏览器

- 列表：1000 条检索、10 条/页、分页、斑马纹、悬停正常。
- 详情：从列表进入带出数据；直接打开显示「取引先が見つかりません」，不 500。
- 用户选择弹窗：虫眼鏡打开、列表正常。
- 移动端：汉堡菜单可开闭。
- 控制台无 JS 错误。

---

## 8. 本地已修复内容（供对照）

本仓库已在提交 `a9d072f` 中恢复/修复：

- 从 `e2eca35` 恢复 Tailwind 三层 CSS、公共布局、`parts/field` fragment、4 个画面、两份文档
- 保留生成器新增的 `Newmodule` / `ScriptSearch` / 新 Events
- 修复 `OptimisticLock` 的 `optimisticCheck` 参数
- 详情页 `load(..., false)` + 未找到消息
- `DeveloperController.VIEW = "screen/developer"`，与生成器 lowercase 文件名一致
- 恢复 `server.servlet.session.tracking-modes=cookie`
- 在 `TAILWIND-CLIENT-SPEC.md` 增加本报告的检查清单

修复后：

| 检查 | 结果 |
|---|---|
| `BaseStylesheet.css` | 4,440 B |
| `app.css` | 20,434 B |
| `Stylesheet.css` | 1,336 B |
| 4 个画面 | 200 |
| `mvn clean package` | 成功 |
| 浏览器 JS 错误 | 0 |

> 这些修复目前只在本仓库工作区/提交中。**如果生成器模板不修改，下一次生成仍会覆盖回去。**

---

## 9. 附录：关键代码对照

### 9.1 公共布局 head

错误：

```html
<link rel="stylesheet" href="/css/BaseStylesheet.css"/>
<link rel="stylesheet" href="/css/Stylesheet.css"/>
```

正确：

```html
<link rel="stylesheet" href="/css/BaseStylesheet.css"/>
<link rel="stylesheet" href="/css/app.css"/>
<link rel="stylesheet" href="/css/Stylesheet.css"/>
```

### 9.2 字段

错误：

```html
<div class="field">
  <div class="controls">
    <div class="label-cell"><label class="label-item">取引先コード</label></div>
    <div class="cell"><input class="ctl w-sm"></div>
  </div>
</div>
```

正确：

```html
<div th:replace="~{parts/field :: fieldText('v:CustomerCD', '取引先コード', 'CustomerCD', null, 10, null, null)}"></div>
```

### 9.3 乐观排他

错误：

```java
ScriptDb.optimisticCheck(this.piOptimistic);
```

正确（保留的模块 `OptimisticLockingCheck`，需要行锁）：

```java
ScriptDb.optimisticCheck(this.piOptimistic, true);
```

本仓库的旧 `OptimisticLock` 以无锁方式修复为 `false`，仅用于让遗留模块编译通过。
生成器的正确做法是**只生成一个模块**（建议 `OptimisticLockingCheck`），并统一传 `true`。

### 9.4 详情页 Form.Load

错误：

```java
ScriptDb.load(Mst00004.class, ..., true);
```

正确：

```java
if (!ScriptDb.load(Mst00004.class, ..., false)) {
    ScriptLib.msg("Error", "取引先が見つかりません。他の人が削除した可能性があります。", null);
}
```

---

## 10. 参考文档

- `TAILWIND.md`：完整规范（三层 CSS、构建、组件、迁移、验收）
- `TAILWIND-CLIENT-SPEC.md`：生成器客户端执行版（精简）
- 本文件：本次回归问题报告与修复要求

建议客户端在修改生成器模板后，把本文件的第 7 节验收清单直接加入生成后的检查流程。
