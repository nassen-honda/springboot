<!DOCTYPE html>
<!--/* 入力部品ライブラリ（想定プロジェクトの既存資産）
       生成された各画面テンプレートは、ここに定義された th:fragment を
       th:replace で組み込むことで「部品形式」の画面を構成する。 */-->
<html xmlns:th="http://www.thymeleaf.org">
<body>

<!-- テキスト入力部品 -->
<div th:fragment="text(label, field)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="text" th:id="${field}" th:field="*{__${field}__}"/>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- テキスト入力部品（readOnly：読み取り専用、rightText：右側の文字。th:field は type 属性で処理が決まるため、type は固定で書く） -->
<div th:fragment="ctlText(label, field, readOnly, rightText)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <span class="flex min-w-0 items-center gap-2">
        <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="text" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}"/>
        <span class="text-sm text-slate-500" th:text="${rightText}"></span>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 数値入力部品（readOnly：読み取り専用、rightText：右側の文字。th:field は type 属性で処理が決まるため、type は固定で書く） -->
<div th:fragment="ctlNumber(label, field, readOnly, rightText)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <span class="flex min-w-0 items-center gap-2">
        <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="number" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}"/>
        <span class="text-sm text-slate-500" th:text="${rightText}"></span>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 日付入力部品（readOnly：読み取り専用、rightText：右側の文字。th:field は type 属性で処理が決まるため、type は固定で書く） -->
<div th:fragment="ctlDate(label, field, readOnly, rightText)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <span class="flex min-w-0 items-center gap-2">
        <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="date" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}"/>
        <span class="text-sm text-slate-500" th:text="${rightText}"></span>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 日時入力部品（readOnly：読み取り専用、rightText：右側の文字。th:field は type 属性で処理が決まるため、type は固定で書く） -->
<div th:fragment="ctlDateTime(label, field, readOnly, rightText)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <span class="flex min-w-0 items-center gap-2">
        <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="datetime-local" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}"/>
        <span class="text-sm text-slate-500" th:text="${rightText}"></span>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- パスワード入力部品（readOnly：読み取り専用、rightText：右側の文字。th:field は type 属性で処理が決まるため、type は固定で書く） -->
<div th:fragment="ctlPassword(label, field, readOnly, rightText)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <span class="flex min-w-0 items-center gap-2">
        <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="password" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}"/>
        <span class="text-sm text-slate-500" th:text="${rightText}"></span>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- メールアドレス入力部品（readOnly：読み取り専用、rightText：右側の文字。th:field は type 属性で処理が決まるため、type は固定で書く） -->
<div th:fragment="ctlEmail(label, field, readOnly, rightText)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <span class="flex min-w-0 items-center gap-2">
        <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="email" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}"/>
        <span class="text-sm text-slate-500" th:text="${rightText}"></span>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- ファイルのアップロード部品（フォームは multipart/form-data。サーバーはコードの &file.Upload("項目名", フォルダー) で保存する） -->
<div th:fragment="upload(label, field)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <input class="block w-full text-sm text-slate-600 file:mr-3 file:rounded-md file:border-0 file:bg-slate-100 file:px-3 file:py-1.5 file:text-sm file:font-medium file:text-slate-700 hover:file:bg-slate-200" type="file" th:id="${field}" th:name="|_file_${field}|"/>
</div>

<!-- メールアドレス入力部品 -->
<div th:fragment="email(label, field)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="email" th:id="${field}" th:field="*{__${field}__}"/>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- パスワード入力部品 -->
<div th:fragment="password(label, field)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="password" th:id="${field}" th:field="*{__${field}__}"/>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 数値入力部品 -->
<div th:fragment="number(label, field)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="number" th:id="${field}" th:field="*{__${field}__}"/>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 日付入力部品 -->
<div th:fragment="date(label, field)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="date" th:id="${field}" th:field="*{__${field}__}"/>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 複数行テキスト（テキストエリア）部品 -->
<div th:fragment="textarea(label, field)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <textarea class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400 min-h-24 py-2" th:id="${field}" th:field="*{__${field}__}" rows="4"></textarea>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- プルダウン選択部品（部品の種類 ComboBox。items は value→表示名の Map） -->
<div th:fragment="select(label, field, items)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <select class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400 pr-8" th:id="${field}" th:field="*{__${field}__}">
        <option value="">選択してください</option>
        <option th:each="item : ${items}" th:value="${item.key}" th:text="${item.value}">項目</option>
    </select>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- チェックボックス部品（Boolean の項目のみ。以前の画面用。新しい画面は ctlCheckbox を使う） -->
<div th:fragment="checkbox(label, field)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label>
        <input class="h-4 w-4 rounded border-slate-300 text-blue-600 focus:ring-blue-500" type="checkbox" th:field="*{__${field}__}"/>
        <span th:text="${label}">ラベル</span>
    </label>
</div>

<!-- チェックボックス部品（値は true。Boolean 以外（String など）の項目にも使えるように value を書く：チェックすると "true"） -->
<div th:fragment="ctlCheckbox(label, field)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label>
        <input class="h-4 w-4 rounded border-slate-300 text-blue-600 focus:ring-blue-500" type="checkbox" value="true" th:field="*{__${field}__}"/>
        <span th:text="${label}">ラベル</span>
    </label>
</div>

<!-- ラジオ ボタン部品（items は value→表示名の Map。選択肢から 1 つ選ぶ） -->
<div th:fragment="ctlRadio(label, field, items)" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:text="${label}">ラベル</label>
    <span class="flex flex-wrap items-center gap-4">
        <label th:each="item : ${items}" class="inline-flex items-center gap-1.5 text-sm text-slate-700">
            <input class="h-4 w-4 rounded border-slate-300 text-blue-600 focus:ring-blue-500" type="radio" th:field="*{__${field}__}" th:value="${item.key}"/>
            <span th:text="${item.value}">項目</span>
        </label>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!--/* ===== 入力部品（画面項目のプロパティ対応）=====
       引数は名前付きで渡す（例：inputText(label='名前', field='userName', placeholder='山田 太郎')）。渡さない引数は null になり、その属性は出力しない。
       共通：label・field・readOnly・disabled・tooltip（title 属性）・tabindex・autofocus・cssStyle（幅・文字の位置など）
       入力欄：rightText・placeholder・inputmode・autocomplete・pattern・min・max・step・maxlength
       複数行：rows　選択：items・blank（先頭の空の選択肢の表示）・noBlank（空の選択肢なし）・radioStyle（並べ方）　アップロード：accept・maxsize（MB） */-->

<!-- テキスト入力部品 -->
<div th:fragment="inputText" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <span class="flex min-w-0 items-center gap-2">
        <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="text" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}" th:placeholder="${placeholder}"
               th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:inputmode="${inputmode}"
               th:autocomplete="${autocomplete}" th:pattern="${pattern}" th:maxlength="${maxlength}"/>
        <span class="text-sm text-slate-500" th:text="${rightText}"></span>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- パスワード入力部品 -->
<div th:fragment="inputPassword" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <span class="flex min-w-0 items-center gap-2">
        <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="password" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}" th:placeholder="${placeholder}"
               th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:autocomplete="${autocomplete}"
               th:pattern="${pattern}" th:maxlength="${maxlength}"/>
        <span class="text-sm text-slate-500" th:text="${rightText}"></span>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- メールアドレス入力部品 -->
<div th:fragment="inputEmail" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <span class="flex min-w-0 items-center gap-2">
        <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="email" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}" th:placeholder="${placeholder}"
               th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:autocomplete="${autocomplete}"
               th:pattern="${pattern}" th:maxlength="${maxlength}"/>
        <span class="text-sm text-slate-500" th:text="${rightText}"></span>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 数値入力部品 -->
<div th:fragment="inputNumber" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <span class="flex min-w-0 items-center gap-2">
        <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="number" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}" th:placeholder="${placeholder}"
               th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:inputmode="${inputmode}"
               th:min="${min}" th:max="${max}" th:step="${step}"/>
        <span class="text-sm text-slate-500" th:text="${rightText}"></span>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 日付入力部品 -->
<div th:fragment="inputDate" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <span class="flex min-w-0 items-center gap-2">
        <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="date" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}"
               th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:min="${min}" th:max="${max}"/>
        <span class="text-sm text-slate-500" th:text="${rightText}"></span>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 日時入力部品 -->
<div th:fragment="inputDateTime" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <span class="flex min-w-0 items-center gap-2">
        <input class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400" type="datetime-local" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}"
               th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:min="${min}" th:max="${max}"/>
        <span class="text-sm text-slate-500" th:text="${rightText}"></span>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 複数行テキスト部品 -->
<div th:fragment="inputTextarea" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <textarea class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400 min-h-24 py-2" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}" th:placeholder="${placeholder}"
              th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:rows="${rows}" th:maxlength="${maxlength}"></textarea>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- プルダウン選択部品（items は value→表示名の Map） -->
<div th:fragment="inputSelect" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <select class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400 pr-8" th:id="${field}" th:field="*{__${field}__}" th:disabled="${disabled}" th:title="${tooltip}" th:tabindex="${tabindex}"
            th:autofocus="${autofocus}" th:style="${cssStyle}">
        <option value="" th:unless="${noBlank}" th:text="${blank}">選択してください</option>
        <option th:each="item : ${items}" th:value="${item.key}" th:text="${item.value}">項目</option>
    </select>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- ラジオ ボタン部品（items は value→表示名の Map。radioStyle：並べ方） -->
<div th:fragment="inputRadio" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:text="${label}">ラベル</label>
    <span class="flex flex-wrap items-center gap-4" th:style="${radioStyle}">
        <label th:each="item : ${items}" class="inline-flex items-center gap-1.5 text-sm text-slate-700">
            <input class="h-4 w-4 rounded border-slate-300 text-blue-600 focus:ring-blue-500" type="radio" th:field="*{__${field}__}" th:value="${item.key}" th:disabled="${disabled}" th:title="${tooltip}" th:tabindex="${tabindex}"/>
            <span th:text="${item.value}">項目</span>
        </label>
    </span>
    <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- チェック ボックス部品（値は true） -->
<div th:fragment="inputCheckbox" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label>
        <input class="h-4 w-4 rounded border-slate-300 text-blue-600 focus:ring-blue-500" type="checkbox" value="true" th:field="*{__${field}__}" th:disabled="${disabled}" th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}"/>
        <span th:text="${label}">ラベル</span>
    </label>
</div>

<!-- ファイルのアップロード部品（accept：選べるファイル、maxsize：最大サイズ（MB。画面の補助スクリプトが確認する）） -->
<div th:fragment="inputUpload" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-center gap-x-3 gap-y-1">
    <label class="text-right text-sm font-medium text-slate-700" th:for="${field}" th:text="${label}">ラベル</label>
    <input class="block w-full text-sm text-slate-600 file:mr-3 file:rounded-md file:border-0 file:bg-slate-100 file:px-3 file:py-1.5 file:text-sm file:font-medium file:text-slate-700 hover:file:bg-slate-200" type="file" th:id="${field}" th:name="|_file_${field}|" th:accept="${accept}" th:data-max-size="${maxsize}" th:disabled="${disabled}"
           th:title="${tooltip}" th:tabindex="${tabindex}"/>
</div>

<!-- 送信ボタン部品 -->
<div th:fragment="submit(label)" class="mt-6 flex items-center gap-3">
    <button type="submit" class="inline-flex items-center justify-center gap-1.5 rounded-md bg-blue-600 px-5 py-2 text-sm font-medium text-white transition hover:bg-blue-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-1" th:text="${label}">送信</button>
</div>

</body>
</html>
