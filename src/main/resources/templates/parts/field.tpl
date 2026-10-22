<!DOCTYPE html>
<!--/* 共通 UI 部品（Tailwind CSS）: 画面の入力項目・見出しなどを 1 か所にまとめる。
       画面は th:replace="~{parts/field :: fieldText(...)}" の形で使う。 */-->
<html xmlns:th="http://www.thymeleaf.org">
<body>

<!--/* ===== 入力項目（1 行：ラベル 9rem + 入力欄） =====
       引数：node（data-node）・label・field（th:field の名前）・required・maxlength・readonly・hidden
       エラーは入力欄の下（2 列目）に表示する。 */-->
<div th:fragment="fieldText(node, label, field, required, maxlength, readonly, hidden)"
     th:attr="data-node=${node}"
     th:classappend="${hidden} ? 'hidden' : ''"
     class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-start gap-x-3 gap-y-1">
    <div class="contents">
        <div class="flex min-h-9 items-center justify-end gap-1">
            <label class="text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
            <span th:if="${required}" class="text-red-500" aria-hidden="true">*</span>
        </div>
        <div class="flex min-w-0 max-w-sm items-center gap-2">
            <input type="text" th:field="*{__${field}__}" th:readonly="${readonly}" th:maxlength="${maxlength}"
                   autocomplete="off"
                   class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400"/>
        </div>
    </div>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!--/* プルダウン：items は value→表示名の Map。blank=true で先頭に空の選択肢を出す。 */-->
<div th:fragment="fieldSelect(node, label, field, items, required, blank, hidden)"
     th:attr="data-node=${node}"
     th:classappend="${hidden} ? 'hidden' : ''"
     class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-start gap-x-3 gap-y-1">
    <div class="contents">
        <div class="flex min-h-9 items-center justify-end gap-1">
            <label class="text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
            <span th:if="${required}" class="text-red-500" aria-hidden="true">*</span>
        </div>
        <div class="flex min-w-0 max-w-sm items-center gap-2">
            <select th:field="*{__${field}__}"
                    class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 pr-8 text-sm text-slate-800 shadow-sm transition focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 disabled:bg-slate-50 disabled:text-slate-400">
                <option th:if="${blank}" value=""></option>
                <option th:each="item : ${items}" th:value="${item.key}" th:text="${item.value}">項目</option>
            </select>
        </div>
    </div>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!--/* 日付（type=date） */-->
<div th:fragment="fieldDate(node, label, field, required, readonly, hidden)"
     th:attr="data-node=${node}"
     th:classappend="${hidden} ? 'hidden' : ''"
     class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-start gap-x-3 gap-y-1">
    <div class="contents">
        <div class="flex min-h-9 items-center justify-end gap-1">
            <label class="text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
            <span th:if="${required}" class="text-red-500" aria-hidden="true">*</span>
        </div>
        <div class="flex min-w-0 max-w-sm items-center gap-2">
            <input type="date" th:field="*{__${field}__}" th:readonly="${readonly}"
                   class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400"/>
        </div>
    </div>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!--/* 日時（type=datetime-local） */-->
<div th:fragment="fieldDateTime(node, label, field, required, readonly, hidden)"
     th:attr="data-node=${node}"
     th:classappend="${hidden} ? 'hidden' : ''"
     class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-start gap-x-3 gap-y-1">
    <div class="contents">
        <div class="flex min-h-9 items-center justify-end gap-1">
            <label class="text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
            <span th:if="${required}" class="text-red-500" aria-hidden="true">*</span>
        </div>
        <div class="flex min-w-0 max-w-sm items-center gap-2">
            <input type="datetime-local" th:field="*{__${field}__}" th:readonly="${readonly}"
                   class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400"/>
        </div>
    </div>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!--/* 複数行テキスト */-->
<div th:fragment="fieldTextarea(node, label, field, required, rows, readonly, hidden)"
     th:attr="data-node=${node}"
     th:classappend="${hidden} ? 'hidden' : ''"
     class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-start gap-x-3 gap-y-1">
    <div class="contents">
        <div class="flex min-h-9 items-center justify-end gap-1">
            <label class="text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
            <span th:if="${required}" class="text-red-500" aria-hidden="true">*</span>
        </div>
        <div class="flex min-w-0 max-w-sm items-center gap-2">
            <textarea th:field="*{__${field}__}" th:readonly="${readonly}" th:rows="${rows}"
                      class="min-h-24 w-full rounded-md border border-slate-300 bg-white px-3 py-2 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400"></textarea>
        </div>
    </div>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>


<!--/* ===== ページ見出し（タイトル・説明）。操作ボタンは画面側で横に並べる ===== */-->
<div th:fragment="pageHeader(title, description)">
    <h1 class="text-2xl font-bold tracking-tight text-slate-900" th:text="${title}">タイトル</h1>
    <p th:if="${description}" class="mt-1 text-sm text-slate-500" th:text="${description}"></p>
</div>

<!--/* ===== 一覧が空のときの行（tbody の中に置く） ===== */-->
<tr th:fragment="emptyRow(colspan, title, description)" class="empty">
    <td th:colspan="${colspan}" class="px-6 py-16 text-center">
        <div class="mx-auto flex max-w-sm flex-col items-center gap-3">
            <span class="grid h-12 w-12 place-items-center rounded-full bg-slate-100 text-slate-400" aria-hidden="true">
                <svg class="h-6 w-6" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round">
                    <circle cx="11" cy="11" r="6.5"/><path d="m16 16 4.5 4.5"/>
                </svg>
            </span>
            <div>
                <p class="text-sm font-semibold text-slate-700" th:text="${title}">データがありません</p>
                <p class="mt-1 text-xs text-slate-400" th:text="${description}">条件を変更して、もう一度検索してください。</p>
            </div>
        </div>
    </td>
</tr>

</body>
</html>
