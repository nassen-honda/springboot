<!DOCTYPE html>
<!--/* 入力部品ライブラリ（想定プロジェクトの既存資産）
       生成された各画面テンプレートは、ここに定義された th:fragment を
       th:replace で組み込むことで「部品形式」の画面を構成する。 */-->
<html xmlns:th="http://www.thymeleaf.org">
<body>

<!-- テキスト入力部品 -->
<div th:fragment="text(label, field)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <input type="text" th:id="${field}" th:field="*{__${field}__}"/>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- テキスト入力部品（readOnly：読み取り専用、rightText：右側の文字。th:field は type 属性で処理が決まるため、type は固定で書く） -->
<div th:fragment="ctlText(label, field, readOnly, rightText)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <span class="control">
        <input type="text" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}"/>
        <span class="right-text" th:text="${rightText}"></span>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 数値入力部品（readOnly：読み取り専用、rightText：右側の文字。th:field は type 属性で処理が決まるため、type は固定で書く） -->
<div th:fragment="ctlNumber(label, field, readOnly, rightText)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <span class="control">
        <input type="number" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}"/>
        <span class="right-text" th:text="${rightText}"></span>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 日付入力部品（readOnly：読み取り専用、rightText：右側の文字。th:field は type 属性で処理が決まるため、type は固定で書く） -->
<div th:fragment="ctlDate(label, field, readOnly, rightText)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <span class="control">
        <input type="date" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}"/>
        <span class="right-text" th:text="${rightText}"></span>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 日時入力部品（readOnly：読み取り専用、rightText：右側の文字。th:field は type 属性で処理が決まるため、type は固定で書く） -->
<div th:fragment="ctlDateTime(label, field, readOnly, rightText)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <span class="control">
        <input type="datetime-local" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}"/>
        <span class="right-text" th:text="${rightText}"></span>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- パスワード入力部品（readOnly：読み取り専用、rightText：右側の文字。th:field は type 属性で処理が決まるため、type は固定で書く） -->
<div th:fragment="ctlPassword(label, field, readOnly, rightText)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <span class="control">
        <input type="password" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}"/>
        <span class="right-text" th:text="${rightText}"></span>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- メールアドレス入力部品（readOnly：読み取り専用、rightText：右側の文字。th:field は type 属性で処理が決まるため、type は固定で書く） -->
<div th:fragment="ctlEmail(label, field, readOnly, rightText)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <span class="control">
        <input type="email" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}"/>
        <span class="right-text" th:text="${rightText}"></span>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- ファイルのアップロード部品（フォームは multipart/form-data。サーバーはコードの &file.Upload("項目名", フォルダー) で保存する） -->
<div th:fragment="upload(label, field)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <input type="file" th:id="${field}" th:name="|_file_${field}|"/>
</div>

<!-- メールアドレス入力部品 -->
<div th:fragment="email(label, field)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <input type="email" th:id="${field}" th:field="*{__${field}__}"/>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- パスワード入力部品 -->
<div th:fragment="password(label, field)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <input type="password" th:id="${field}" th:field="*{__${field}__}"/>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 数値入力部品 -->
<div th:fragment="number(label, field)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <input type="number" th:id="${field}" th:field="*{__${field}__}"/>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 日付入力部品 -->
<div th:fragment="date(label, field)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <input type="date" th:id="${field}" th:field="*{__${field}__}"/>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 複数行テキスト（テキストエリア）部品 -->
<div th:fragment="textarea(label, field)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <textarea th:id="${field}" th:field="*{__${field}__}" rows="4"></textarea>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- プルダウン選択部品（部品の種類 ComboBox。items は value→表示名の Map） -->
<div th:fragment="select(label, field, items)" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <select th:id="${field}" th:field="*{__${field}__}">
        <option value="">選択してください</option>
        <option th:each="item : ${items}" th:value="${item.key}" th:text="${item.value}">項目</option>
    </select>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- チェックボックス部品（Boolean の項目のみ。以前の画面用。新しい画面は ctlCheckbox を使う） -->
<div th:fragment="checkbox(label, field)" class="form-row">
    <label>
        <input type="checkbox" th:field="*{__${field}__}"/>
        <span th:text="${label}">ラベル</span>
    </label>
</div>

<!-- チェックボックス部品（値は true。Boolean 以外（String など）の項目にも使えるように value を書く：チェックすると "true"） -->
<div th:fragment="ctlCheckbox(label, field)" class="form-row">
    <label>
        <input type="checkbox" value="true" th:field="*{__${field}__}"/>
        <span th:text="${label}">ラベル</span>
    </label>
</div>

<!-- ラジオ ボタン部品（items は value→表示名の Map。選択肢から 1 つ選ぶ） -->
<div th:fragment="ctlRadio(label, field, items)" class="form-row">
    <label th:text="${label}">ラベル</label>
    <span class="radios">
        <label th:each="item : ${items}" class="radio">
            <input type="radio" th:field="*{__${field}__}" th:value="${item.key}"/>
            <span th:text="${item.value}">項目</span>
        </label>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!--/* ===== 入力部品（画面項目のプロパティ対応）=====
       引数は名前付きで渡す（例：inputText(label='名前', field='userName', placeholder='山田 太郎')）。渡さない引数は null になり、その属性は出力しない。
       共通：label・field・readOnly・disabled・tooltip（title 属性）・tabindex・autofocus・cssStyle（幅・文字の位置など）
       入力欄：rightText・placeholder・inputmode・autocomplete・pattern・min・max・step・maxlength
       複数行：rows　選択：items・blank（先頭の空の選択肢の表示）・noBlank（空の選択肢なし）・radioStyle（並べ方）　アップロード：accept・maxsize（MB） */-->

<!-- テキスト入力部品 -->
<div th:fragment="inputText" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <span class="control">
        <input type="text" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}" th:placeholder="${placeholder}"
               th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:inputmode="${inputmode}"
               th:autocomplete="${autocomplete}" th:pattern="${pattern}" th:maxlength="${maxlength}"/>
        <span class="right-text" th:text="${rightText}"></span>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- パスワード入力部品 -->
<div th:fragment="inputPassword" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <span class="control">
        <input type="password" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}" th:placeholder="${placeholder}"
               th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:autocomplete="${autocomplete}"
               th:pattern="${pattern}" th:maxlength="${maxlength}"/>
        <span class="right-text" th:text="${rightText}"></span>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- メールアドレス入力部品 -->
<div th:fragment="inputEmail" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <span class="control">
        <input type="email" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}" th:placeholder="${placeholder}"
               th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:autocomplete="${autocomplete}"
               th:pattern="${pattern}" th:maxlength="${maxlength}"/>
        <span class="right-text" th:text="${rightText}"></span>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 数値入力部品 -->
<div th:fragment="inputNumber" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <span class="control">
        <input type="number" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}" th:placeholder="${placeholder}"
               th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:inputmode="${inputmode}"
               th:min="${min}" th:max="${max}" th:step="${step}"/>
        <span class="right-text" th:text="${rightText}"></span>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 日付入力部品 -->
<div th:fragment="inputDate" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <span class="control">
        <input type="date" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}"
               th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:min="${min}" th:max="${max}"/>
        <span class="right-text" th:text="${rightText}"></span>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 日時入力部品 -->
<div th:fragment="inputDateTime" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <span class="control">
        <input type="datetime-local" th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}"
               th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:min="${min}" th:max="${max}"/>
        <span class="right-text" th:text="${rightText}"></span>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- 複数行テキスト部品 -->
<div th:fragment="inputTextarea" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <textarea th:id="${field}" th:field="*{__${field}__}" th:readonly="${readOnly}" th:disabled="${disabled}" th:placeholder="${placeholder}"
              th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}" th:style="${cssStyle}" th:rows="${rows}" th:maxlength="${maxlength}"></textarea>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- プルダウン選択部品（items は value→表示名の Map） -->
<div th:fragment="inputSelect" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <select th:id="${field}" th:field="*{__${field}__}" th:disabled="${disabled}" th:title="${tooltip}" th:tabindex="${tabindex}"
            th:autofocus="${autofocus}" th:style="${cssStyle}">
        <option value="" th:unless="${noBlank}" th:text="${blank}">選択してください</option>
        <option th:each="item : ${items}" th:value="${item.key}" th:text="${item.value}">項目</option>
    </select>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- ラジオ ボタン部品（items は value→表示名の Map。radioStyle：並べ方） -->
<div th:fragment="inputRadio" class="form-row">
    <label th:text="${label}">ラベル</label>
    <span class="radios" th:style="${radioStyle}">
        <label th:each="item : ${items}" class="radio">
            <input type="radio" th:field="*{__${field}__}" th:value="${item.key}" th:disabled="${disabled}" th:title="${tooltip}" th:tabindex="${tabindex}"/>
            <span th:text="${item.value}">項目</span>
        </label>
    </span>
    <span class="error" th:if="${#fields.hasErrors('__${field}__')}" th:errors="*{__${field}__}"></span>
</div>

<!-- チェック ボックス部品（値は true） -->
<div th:fragment="inputCheckbox" class="form-row">
    <label>
        <input type="checkbox" value="true" th:field="*{__${field}__}" th:disabled="${disabled}" th:title="${tooltip}" th:tabindex="${tabindex}" th:autofocus="${autofocus}"/>
        <span th:text="${label}">ラベル</span>
    </label>
</div>

<!-- ファイルのアップロード部品（accept：選べるファイル、maxsize：最大サイズ（MB。画面の補助スクリプトが確認する）） -->
<div th:fragment="inputUpload" class="form-row">
    <label th:for="${field}" th:text="${label}">ラベル</label>
    <input type="file" th:id="${field}" th:name="|_file_${field}|" th:accept="${accept}" th:data-max-size="${maxsize}" th:disabled="${disabled}"
           th:title="${tooltip}" th:tabindex="${tabindex}"/>
</div>

<!-- 送信ボタン部品 -->
<div th:fragment="submit(label)" class="form-actions">
    <button type="submit" th:text="${label}">送信</button>
</div>

</body>
</html>
