<!DOCTYPE html>
<!--/* 取引先マスタ一覧 画面テンプレート（自動生成 / auto-generated）
       画面の部品のテンプレート（テンプレートのフォルダーの screen\*.tpl）を展開して画面を構成します。 */-->
<html lang="ja" xmlns:th="http://www.thymeleaf.org"
      th:replace="~{base/WebPageBaseLayout :: layout(~{::title}, ~{::main})}">
<head>
    <title th:text="'取引先マスタ一覧'">取引先マスタ一覧</title>
</head>
<body>
<main class="min-w-0 flex-1 overflow-auto p-4 sm:p-6">
    <div class="mx-auto mb-6 flex max-w-[1600px] flex-wrap items-end justify-between gap-4">
        <div th:replace="~{parts/field :: pageHeader('取引先マスタ一覧', '取引先の検索・登録・編集を行います。')}"></div>
        <div class="flex flex-wrap items-center gap-2">
            <button type="button" data-item="btnNew1" data-node="n:btnNew1"
                    class="inline-flex items-center gap-1.5 rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white shadow-sm transition hover:bg-blue-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-1">
                <svg class="h-4 w-4" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" aria-hidden="true"><path d="M12 5v14M5 12h14"/></svg>
                新規作成
            </button>
            <button type="button" data-item="btnExport1" data-node="n:btnExport1"
                    class="inline-flex items-center gap-1.5 rounded-md border border-slate-300 bg-white px-4 py-2 text-sm font-medium text-slate-700 shadow-sm transition hover:bg-slate-50 focus:outline-none focus-visible:ring-2 focus-visible:ring-slate-300 focus-visible:ring-offset-1">
                <svg class="h-4 w-4" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M12 3v12m0 0 4-4m-4 4-4-4M4 17v3h16v-3"/></svg>
                CSV 出力
            </button>
        </div>
    </div>
    <form class="mx-auto max-w-[1600px]" th:action="@{/CustomerMasterList}" th:object="${form}" method="post">

        <!--/* 画面項目（画面定義の「画面」タブの順・入れ子） */-->
        <div data-node="n:TableMain" class="space-y-6">
            <div data-node="n:panel1" class="grid grid-cols-1 gap-x-8 rounded-xl border border-slate-200 bg-white p-6 shadow-sm xl:grid-cols-2">
                <div class="mb-1 flex items-center justify-between xl:col-span-2">
                    <h2 class="text-sm font-semibold text-slate-700">検索条件</h2>
                </div>
                <div th:replace="~{parts/field :: fieldText('v:CustomerCD', '取引先コード', 'CustomerCD', null, 10, null, null)}"></div>
                <div th:replace="~{parts/field :: fieldText('v:CustomerNM', '取引先名', 'CustomerNM', null, 100, null, null)}"></div>
                <div th:replace="~{parts/field :: fieldText('v:CustomerOmission', '取引先略称', 'CustomerOmission', null, 100, null, null)}"></div>
                <div data-node="n:table1" class="mt-2 grid grid-cols-1 gap-x-8 xl:col-span-2 xl:grid-cols-2">
                    <div data-node="v:UserCD" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-start gap-x-3 gap-y-1">
                        <div class="contents">
                            <div class="flex min-h-9 items-center justify-end gap-1">
                                <label class="text-sm font-medium text-slate-700" for="UserCD">ユーザコード</label>
                            </div>
                            <div class="flex min-w-0 max-w-sm items-center gap-2">
                                <div class="relative w-full">
                                    <input type="text" id="UserCD" class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400 !pr-10 ico-search" th:field="*{UserCD}" autocomplete="off" maxlength="10"/>
                                    <svg class="pointer-events-none absolute right-3 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-400" viewBox="0 0 16 16" fill="none" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" aria-hidden="true"><circle cx="7" cy="7" r="4.2"/><path d="M10.4 10.4L14 14"/></svg>
                                </div>
                                <button type="submit" name="_event" value="UserCD_Popup" class="btn-popup hidden" formnovalidate="formnovalidate" title="ユーザ選択画面">🔍</button>
                            </div>
                        </div>
                        <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('UserCD')}" th:errors="*{UserCD}"></span>
                    </div>
                    <div th:replace="~{parts/field :: fieldText('v:UserName', 'ユーザ名', 'UserName', null, 50, null, null)}"></div>
                    <div data-node="v:CreateDate" class="mb-4 grid grid-cols-[9rem_minmax(0,1fr)] items-start gap-x-3 gap-y-1">
                        <div class="contents">
                            <div class="flex min-h-9 items-center justify-end gap-1">
                                <label class="text-sm font-medium text-slate-700" for="CreateDate-wareki">日付</label>
                            </div>
                            <div class="relative flex min-w-0 max-w-sm items-center gap-2">
                                <input type="text" id="CreateDate-wareki" class="h-9 w-full rounded-md border border-slate-300 bg-white px-3 text-sm text-slate-800 shadow-sm transition placeholder:text-slate-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-100 readonly:bg-slate-50 readonly:text-slate-500 disabled:bg-slate-50 disabled:text-slate-400 !pr-10 ico-date" data-wareki-text="date" th:value="${form.viewState.wareki(form.CreateDate)}" placeholder="例: R7.10.17" autocomplete="off"/>
                                <svg class="pointer-events-none absolute right-3 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-400" viewBox="0 0 16 16" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><rect x="2" y="3" width="12" height="11" rx="1.5"/><path d="M2 6.5h12M5.5 1.6v2.6M10.5 1.6v2.6"/></svg>
                                <button type="button" class="wareki-picker" data-wareki-picker="CreateDate" title="カレンダーから選ぶ" tabindex="-1" style="display: none">📅</button>
                                <input type="date" id="CreateDate" th:field="*{CreateDate}" tabindex="-1"
                                       class="wareki-native pointer-events-none absolute bottom-0 left-0 h-px w-px border-0 p-0 opacity-0"/>
                            </div>
                        </div>
                        <span class="col-start-2 text-xs text-red-600" th:if="${#fields.hasErrors('CreateDate')}" th:errors="*{CreateDate}"></span>
                    </div>
                    <div th:replace="~{parts/field :: fieldSelect('v:CustomerKbn', '取引先区分', 'CustomerKbn', ${CustomerKbnItems}, null, true, null)}"></div>
                </div>
                <div data-node="n:buttonGroup2" class="mt-2 flex flex-wrap items-center gap-3 border-t border-slate-100 pt-5 xl:col-span-2">
                    <button type="submit" name="_event" value="btnSearch1_Click" data-item="btnSearch1" data-node="n:btnSearch1" class="inline-flex items-center justify-center gap-1.5 rounded-md px-3.5 py-2 text-sm font-medium transition focus:outline-none focus-visible:ring-2 focus-visible:ring-offset-1 disabled:opacity-60 bg-blue-600 text-white hover:bg-blue-700 focus-visible:ring-blue-500"><span class='icon'><svg class="h-4 w-4" viewBox="0 0 16 16" fill="none" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d='M11.2 7A4.2 4.2 0 1 1 2.8 7a4.2 4.2 0 1 1 8.4 0z M10.4 10.4L14 14'/></svg></span>検索</button>
                    <button type="submit" name="_event" value="btnClear1_Click" data-item="btnClear1" data-node="n:btnClear1" class="inline-flex items-center justify-center gap-1.5 rounded-md px-3.5 py-2 text-sm font-medium transition focus:outline-none focus-visible:ring-2 focus-visible:ring-offset-1 disabled:opacity-60 border border-slate-300 bg-white text-slate-700 hover:bg-slate-50 focus-visible:ring-slate-300"><span class='icon'><svg class="h-4 w-4" viewBox="0 0 16 16" fill="none" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d='M13 8a5 5 0 1 1-1.5-3.5 M13 2.5v3h-3'/></svg></span>クリア</button>
                </div>
            </div>
            <details data-node="n:messageView" class="message-view relative mb-4 rounded-xl border border-slate-200 bg-white px-4 py-3 shadow-sm" th:classappend="${messages.empty} ? 'hidden'" data-levels="Error,Warning,Info" data-clear-on-event="true" th:open="${messages.hasError}">
                <summary class="message-summary cursor-pointer list-none select-none text-sm font-semibold text-slate-600 [&::-webkit-details-marker]:hidden" th:text="${messages.summary}">メッセージ</summary>
                <ul class="message-list mt-2 space-y-1">
                    <li class="message-item message-error flex items-start gap-2 text-sm text-red-600" th:each="m : ${messages.error}" th:attr="data-item=${m.item}"><span class="shrink-0" aria-hidden="true">✖</span><span class="message-text" th:text="${m.description}">エラーのメッセージ</span></li>
                    <li class="message-item message-warning flex items-start gap-2 text-sm text-amber-600" th:each="m : ${messages.warning}" th:attr="data-item=${m.item}"><span class="shrink-0" aria-hidden="true">⚠</span><span class="message-text" th:text="${m.description}">警告のメッセージ</span></li>
                    <li class="message-item message-info flex items-start gap-2 text-sm text-blue-600" th:each="m : ${messages.info}" th:attr="data-item=${m.item}"><span class="shrink-0" aria-hidden="true">ℹ</span><span class="message-text" th:text="${m.description}">お知らせのメッセージ</span></li>
                </ul>
                <button type="button" class="message-close absolute right-3 top-2.5 text-slate-400 transition hover:text-slate-600" title="閉じる">×</button>
            </details>
            <div class="overflow-hidden rounded-xl border border-slate-200 bg-white shadow-sm">
                <div class="flex flex-wrap items-center justify-between gap-3 border-b border-slate-200 px-4 py-3">
                    <h2 class="text-sm font-semibold text-slate-700">検索結果</h2>
                    <span class="rounded-full bg-slate-100 px-2.5 py-0.5 text-xs font-medium text-slate-600" th:text="${#lists.size(form.Customers)} + ' 件'">0 件</span>
                </div>
                <div class="table-scroll max-h-[60vh] overflow-auto">
                    <table data-node="v:Customers" class="w-full min-w-[1200px] border-collapse text-sm" data-grid="Customers" data-page-sizes="10,20,30,50">
                        <thead>
                            <tr>
                                <th data-node="n:SEQ" class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500 w-12" data-nosort="true">No</th>
                                <th data-node="v:Customers.MST00004_CustomerCD" class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500">取引先コード</th>
                                <th data-node="v:Customers.MST00004_CustomerInvoiceCD" class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500">取引先請求コード</th>
                                <th data-node="v:Customers.MST00004_CustomerKbn" class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500">取引先区分</th>
                                <th data-node="v:Customers.MST00004_CustomerNM" class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500">取引先名</th>
                                <th data-node="v:Customers.MST00004_CustomerOmission" class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500">取引先略称</th>
                                <th data-node="v:Customers.MST00004_AreaCD" class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500">地域コード</th>
                                <th data-node="v:Customers.MST00004_Address1" class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500">住所1</th>
                                <th data-node="v:Customers.MST00004_Address2" class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500">住所2</th>
                                <th data-node="v:Customers.MST00004_Address3" class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500">住所3</th>
                                <th data-node="n:btnPanel1" class="sticky top-0 z-10 whitespace-nowrap border-b border-slate-200 bg-slate-50 px-3 py-2.5 text-center text-xs font-semibold tracking-wide text-slate-500" data-nosort="true">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr th:each="row, st : *{Customers}" th:attr="data-row=${st.count}" class="hover:bg-blue-50" th:classappend="${(st.index &gt;= 10 ? 'hidden' : (st.even ? 'bg-slate-50' : ''))}">
                                <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-center w-12 text-slate-400 seq" data-node="n:SEQ" th:text="${st.count}">1</td>
                                <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-left" data-node="v:Customers.MST00004_CustomerCD" th:text="${row.mst00004CustomerCD}">－</td>
                                <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-left" data-node="v:Customers.MST00004_CustomerInvoiceCD" th:text="${row.mst00004CustomerInvoiceCD}">－</td>
                                <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-left" data-node="v:Customers.MST00004_CustomerKbn" th:text="${row.mst00004CustomerKbn?.label}">－</td>
                                <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-left" data-node="v:Customers.MST00004_CustomerNM" th:text="${row.mst00004CustomerNM}">－</td>
                                <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-left" data-node="v:Customers.MST00004_CustomerOmission" th:text="${row.mst00004CustomerOmission}">－</td>
                                <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-left" data-node="v:Customers.MST00004_AreaCD" th:text="${row.mst00004AreaCD}">－</td>
                                <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-left" data-node="v:Customers.MST00004_Address1" th:text="${row.mst00004Address1}">－</td>
                                <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-left" data-node="v:Customers.MST00004_Address2" th:text="${row.mst00004Address2}">－</td>
                                <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-left" data-node="v:Customers.MST00004_Address3" th:text="${row.mst00004Address3}">－</td>
                                <td class="border-b border-slate-100 px-3 py-2.5 align-middle text-center" data-node="n:btnPanel1"><div class="flex flex-wrap justify-center gap-1">
                                    <button type="submit" name="_event" th:value="|Customers_btnDetail_Click:${st.count}|" data-node="n:btnDetail" class="inline-flex items-center justify-center gap-1 rounded px-2.5 py-1 text-xs font-medium transition focus:outline-none focus-visible:ring-2 focus-visible:ring-offset-1 disabled:opacity-60 border border-blue-200 bg-blue-50 text-blue-700 hover:bg-blue-100" formnovalidate="formnovalidate">詳細</button>
                                    <button type="submit" name="_event" th:value="|Customers_btnEdit_Click:${st.count}|" data-node="n:btnEdit" class="inline-flex items-center justify-center gap-1 rounded px-2.5 py-1 text-xs font-medium transition focus:outline-none focus-visible:ring-2 focus-visible:ring-offset-1 disabled:opacity-60 bg-blue-600 text-white hover:bg-blue-700" formnovalidate="formnovalidate">編集</button>
                                    <button type="submit" name="_event" th:value="|Customers_btnCopy_Click:${st.count}|" data-node="n:btnCopy" class="inline-flex items-center justify-center gap-1 rounded px-2.5 py-1 text-xs font-medium transition focus:outline-none focus-visible:ring-2 focus-visible:ring-offset-1 disabled:opacity-60 bg-amber-500 text-white hover:bg-amber-600" formnovalidate="formnovalidate">複製</button>
                                    <button type="submit" name="_event" th:value="|Customers_btnDelete_Click:${st.count}|" data-node="n:btnDelete" class="inline-flex items-center justify-center gap-1 rounded px-2.5 py-1 text-xs font-medium transition focus:outline-none focus-visible:ring-2 focus-visible:ring-offset-1 disabled:opacity-60 bg-red-600 text-white hover:bg-red-700" data-confirm="削除してもよろしいですか？" formnovalidate="formnovalidate">削除</button>
                                </div></td>
                            </tr>
                            <th:block th:if="${#lists.isEmpty(form.Customers)}">
                                <tr th:replace="~{parts/field :: emptyRow(11, '取引先がありません', '検索条件を変更して、もう一度検索してください。')}"></tr>
                            </th:block>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </form>

    <!--/* ポップアップの画面（Popup の画面を Call で開いたとき）：Close で閉じる */-->
    <div class="popup-overlay fixed inset-0 z-50 flex items-center justify-center bg-slate-900/50 p-4" th:if="${popup}">
        <iframe class="popup-frame max-h-[90vh] w-full max-w-[95vw] rounded-xl border-0 bg-white shadow-2xl" th:src="@{${popup.url}}" th:title="${popup.screenId}"
                th:style="|width: min(95vw, ${popup.width}); height: min(90vh, ${popup.height})|"></iframe>
    </div>

    <!--/* 画面の部分更新（自動生成）：送信を fetch で送り、返ってきた画面で main の中身を入れ替える（ちらつかない） */-->
    <script th:inline="none">
    //<![CDATA[
    (() => {
      const inits = [];
      const form = document.querySelector('main form');
      let busy = false;
      // 送信中に来た送信（前の送信の後に順に送る）。同じイベント（_event）が続く場合は最後の 1 つ（入力中の Change など）
      const queue = [];
      // 送信したときのフォーカスのある入力欄の値（送信中に入力を続けた場合は、入れ替えた後も入力した値を残す）
      let sent = null;
      // 日本語の変換中（IME）：変換を確定してから画面を入れ替える（変換中に入力欄を入れ替えると変換が消える）
      let composing = false;
      document.addEventListener('compositionstart', () => composing = true, true);
      document.addEventListener('compositionend', () => composing = false, true);
      const composed = () => composing ? new Promise(r => document.addEventListener('compositionend', () => setTimeout(r, 0), { once: true, capture: true })) : null;
      // フォーカスのある項目を入れ替えた後に探す目印（id、なければ name。ボタンは value も）
      const keyOf = e => !e || e === document.body ? null : e.id ? '#' + CSS.escape(e.id)
        : e.name ? '[name="' + CSS.escape(e.name) + '"]' + (e.value && e.type === 'submit' ? '[value="' + CSS.escape(e.value) + '"]' : '') : null;
      // 普通の送信（部分更新できない場合）
      const nativeSubmit = (submitter, extra) => {
        Object.entries(extra || {}).forEach(([name, value]) => {
          const input = document.createElement('input');
          input.type = 'hidden';
          input.name = name;
          input.value = value;
          form.appendChild(input);
        });
        if (submitter && submitter.name) {
          const input = document.createElement('input');
          input.type = 'hidden';
          input.name = submitter.name;
          input.value = submitter.value;
          form.appendChild(input);
        }
        HTMLFormElement.prototype.submit.call(form);
      };
      // 送信の後：コードの送信で付けた一時的な値（data-transient）を消し、画面の処理に知らせる（送信中の印を消す）
      const done = () => {
        form.querySelectorAll('input[data-transient]').forEach(i => i.remove());
        busy = false;
        document.documentElement.classList.remove('screen-busy');
        document.dispatchEvent(new Event('screen-updated'));
        if (queue.length) {
          const next = queue.shift();
          send(next.submitter, next.extra);
        }
      };
      // 応答の画面（HTML）で main の中身を入れ替える。同じ画面でない場合は false
      const swap = html => {
        const doc = new DOMParser().parseFromString(html, 'text/html');
        const main = form.closest('main'), next = doc.querySelector('main');
        const nextForm = next && next.querySelector(':scope > form');
        if (!main || !nextForm || nextForm.getAttribute('action') !== form.getAttribute('action')) return false;
        // フォーカスのある項目（入れ替えた後に戻す）
        const active = document.activeElement;
        const key = keyOf(active);
        // 文字の入力欄：入力中の値・カーソルの位置
        const text = active && (active.tagName === 'TEXTAREA' || (active.tagName === 'INPUT' && /^(text|search|tel|url|email|password)$/.test(active.type)));
        const typed = text ? { value: active.value, start: active.selectionStart, end: active.selectionEnd } : null;
        // form は残して（form の処理を残す）属性と中身を入れ替える。form の前後の項目（見出し・ポップアップの画面など）は入れ替える。スクリプトは残す
        [...form.attributes].forEach(a => form.removeAttribute(a.name));
        [...nextForm.attributes].forEach(a => form.setAttribute(a.name, a.value));
        form.replaceChildren(...[...nextForm.childNodes].map(n => document.importNode(n, true)));
        [...main.children].filter(c => c !== form && c.tagName !== 'SCRIPT').forEach(c => c.remove());
        let before = true;
        const firstScript = [...main.children].find(c => c.tagName === 'SCRIPT');
        [...next.children].forEach(c => {
          if (c === nextForm) { before = false; return; }
          if (c.tagName === 'SCRIPT') return;
          const node = document.importNode(c, true);
          if (before) main.insertBefore(node, form); else main.insertBefore(node, firstScript || null);
        });
        if (doc.title) document.title = doc.title;
        // 補助スクリプト・画面の処理を登録し直す（画面を描く前に同じ処理の中で行う。ちらつかない）
        inits.forEach(f => { try { f(form); } catch (e) { console.error(e); } });
        const target = key && form.querySelector(key);
        if (target) {
          // 送信の後に入力を続けた場合は、入力した値を残す（サーバーの値で消さない）。カーソルの位置も戻す
          if (typed && sent && sent.key === key && typed.value !== sent.value && 'value' in target) target.value = typed.value;
          target.focus({ preventScroll: true });
          if (typed && typeof target.setSelectionRange === 'function') {
            try { target.setSelectionRange(Math.min(typed.start, target.value.length), Math.min(typed.end, target.value.length)); } catch (e) { /* 位置を戻せない種類 */ }
          }
        }
        return true;
      };
      // ダウンロード（Content-Disposition のファイル名）
      const download = async response => {
        const blob = await response.blob();
        const header = response.headers.get('Content-Disposition') || '';
        const star = /filename\*=UTF-8''([^;]+)/i.exec(header), plain = /filename="?([^";]+)"?/i.exec(header);
        const a = document.createElement('a');
        a.href = URL.createObjectURL(blob);
        a.download = star ? decodeURIComponent(star[1]) : plain ? plain[1] : 'download';
        document.body.appendChild(a);
        a.click();
        a.remove();
        setTimeout(() => URL.revokeObjectURL(a.href), 60000);
      };
      const send = async (submitter, extra) => {
        if (busy) {
          const last = queue[queue.length - 1];
          if (!submitter && last && !last.submitter && last.extra && extra && last.extra._event === extra._event) queue[queue.length - 1] = { submitter, extra };
          else queue.push({ submitter, extra });
          return;
        }
        busy = true;
        const active = document.activeElement;
        sent = keyOf(active) ? { key: keyOf(active), value: active.value } : null;
        document.documentElement.classList.add('screen-busy');
        let data;
        try { data = new FormData(form, submitter || undefined); } catch (e) { data = new FormData(form); if (submitter && submitter.name) data.append(submitter.name, submitter.value); }
        Object.entries(extra || {}).forEach(([name, value]) => data.append(name, value));
        const multipart = (form.getAttribute('enctype') || '').toLowerCase() === 'multipart/form-data';
        let response;
        try {
          response = await fetch(form.action, {
            method: 'POST', credentials: 'same-origin', headers: { 'X-Screen-Update': '1' },
            body: multipart ? data : new URLSearchParams([...data].map(([k, v]) => [k, String(v)])),
          });
        } catch (e) {
          // 送れない場合は普通に送信する
          busy = false;
          nativeSubmit(submitter, extra);
          return;
        }
        // 画面の移動（Call・Close など）：移動先を開く（ポップアップの画面の中から閉じる場合は親・一番上の画面）
        const location = response.headers.get('X-Screen-Location');
        if (location) {
          const target = response.headers.get('X-Screen-Target');
          (target === 'top' ? window.top : target === 'parent' ? window.parent : window).location.replace(location);
          return;
        }
        if (response.redirected) {
          window.location.assign(response.url);
          return;
        }
        const type = response.headers.get('Content-Type') || '';
        if (!type.includes('text/html')) {
          await download(response);
          done();
          return;
        }
        const html = await response.text();
        await composed();
        if (!swap(html)) {
          // 同じ画面でない応答（エラーの画面など）：ページを入れ替える
          document.open();
          document.write(html);
          document.close();
          return;
        }
        done();
      };
      window.screenUpdate = {
        /** 画面の部分更新の後に実行する関数（入れ替えた form を渡す）を登録する。 */
        onUpdate: f => inits.push(f),
        /** 画面を送信する（extra：ボタンの代わりに送る値 _event など）。 */
        submit: extra => form && (window.fetch ? send(null, extra) : nativeSubmit(null, extra)),
      };
      if (!form || !window.fetch || !window.DOMParser) return;
      form.addEventListener('submit', e => {
        e.preventDefault();
        send(e.submitter, null);
      });
    })();
    //]]>
    </script>

    <!--/* 画面の補助スクリプト（自動生成）：一覧のページ切り替え・並べ替え、ボタンの確認、Enter キーで次の項目へ、離れるときの確認、ファイルのサイズの確認 */-->
    <script th:inline="none">
    //<![CDATA[
        (() => {
          // ボタンの確認（data-confirm）：キャンセルした場合は処理しない
          document.addEventListener('click', e => {
            const b = e.target.closest('[data-confirm]');
            if (b && !window.confirm(b.dataset.confirm)) { e.preventDefault(); e.stopImmediatePropagation(); }
          }, true);
          // Enter で押すボタン（data-default-button：Panel / Frame の中の入力欄で Enter を押すと、そのボタンを押す）
          document.addEventListener('keydown', e => {
            if (e.key !== 'Enter' || e.target.tagName === 'TEXTAREA' || e.target.tagName === 'BUTTON') return;
            const area = e.target.closest('[data-default-button]');
            const b = area && document.querySelector(`[data-item="${area.dataset.defaultButton}"]`);
            if (b) { e.preventDefault(); e.stopImmediatePropagation(); b.click(); }
          }, true);
          const form = document.querySelector('form[data-enter-key], form[data-confirm-leave]');
          // Enter キーで次の項目へ（data-enter-key）
          if (form && form.dataset.enterKey) {
            form.addEventListener('keydown', e => {
              if (e.key !== 'Enter' || e.target.tagName === 'TEXTAREA' || e.target.tagName === 'BUTTON' || e.target.type === 'submit') return;
              const items = [...form.querySelectorAll('input:not([type=hidden]):not([disabled]),select:not([disabled]),textarea:not([disabled]),button:not([disabled])')];
              const next = items[items.indexOf(e.target) + 1];
              if (next) { e.preventDefault(); next.focus(); }
            });
          }
          // 入力の途中で離れるときの確認（data-confirm-leave）
          if (form && form.dataset.confirmLeave) {
            let dirty = false;
            form.addEventListener('input', () => dirty = true);
            form.addEventListener('submit', () => dirty = false);
            window.addEventListener('beforeunload', e => { if (dirty) { e.preventDefault(); e.returnValue = ''; } });
          }
          // 画面の中身（最初と、画面の部分更新で入れ替えた後）
          const init = root => {
          // コードで設定した必須・最小長（data-required / data-minlength）：中の入力欄に設定する
          root.querySelectorAll('[data-required]').forEach(d => d.querySelectorAll('input:not([type=hidden]),select,textarea').forEach(i => i.required = true));
          root.querySelectorAll('[data-minlength]').forEach(d => d.querySelectorAll('input:not([type=hidden]),textarea').forEach(i => i.minLength = Number(d.dataset.minlength)));
          // ファイルのサイズ（data-max-size：MB）
          root.querySelectorAll('input[type=file][data-max-size]').forEach(input => input.addEventListener('change', () => {
            const max = Number(input.dataset.maxSize) * 1024 * 1024;
            if ([...input.files].some(f => f.size > max)) { window.alert(`ファイルは ${input.dataset.maxSize} MB までです。`); input.value = ''; }
          }));
          // ポップアップ画面の 🔍 のボタン（.btn-popup）・和暦の 📅 のボタン（.wareki-picker）：入力欄の中にアイコン（Tailwind の ico-search・ico-date）がある場合はボタンを隠し、
          // アイコン（入力欄の右端）のクリックでボタンを押す（🔍 は送信して Event 項目.Popup、📅 はカレンダー）。アイコンの上はマウスの形を指にする
          root.querySelectorAll('.btn-popup, .wareki-picker').forEach(button => {
            const input = button.parentElement && button.parentElement.querySelector(button.classList.contains('btn-popup') ? 'input.ico-search' : 'input.ico-date[data-wareki-text]');
            if (!input) return;
            button.style.display = 'none';
            const onIcon = e => {
              const r = input.getBoundingClientRect();
              return e.clientX >= r.right - (parseFloat(getComputedStyle(input).paddingRight) || 26);
            };
            input.addEventListener('mousemove', e => input.style.cursor = onIcon(e) && !button.disabled ? 'pointer' : '');
            input.addEventListener('click', e => { if (onIcon(e) && !button.disabled) button.click(); });
          });
          // 一覧（Tailwind の table.data）：縞模様（表示中の行に 1 行おきに bg-slate-50）、ページの切り替え（data-page-size。div.pager：件数・表示件数・ページ番号）、
          // 並べ替え（th.sortable。sort-asc / sort-desc）、列の幅の変更（.col-resizer）、表示中の行をまとめて選ぶ（data-check-all）
          root.querySelectorAll('table[data-grid]').forEach(table => {
            const body = table.tBodies[0];
            const rows = () => [...body.rows].filter(r => !r.classList.contains('empty'));
            // div.pager に出すもの（data-pager-*="false" は出さない）：件数、1 ページの行数の選択、ページ番号。ページ番号を出さない場合はページを分けず、件数だけを出す
            const showTotal = table.dataset.pagerTotal !== 'false';
            const showPages = table.dataset.pagerPages !== 'false';
            // 1 ページの行数：選択肢（data-page-sizes）の最初の値（コードで設定した行数 data-page-size があればその値）。ページ番号を出さない場合はページを分けない（1 ページの行数の選択も出さない）
            const sizes = (table.dataset.pageSizes || '10,20,30,50').split(',').map(Number).filter(n => n > 0);
            if (!sizes.length) sizes.push(10);
            let size = showPages ? Number(table.dataset.pageSize || 0) || sizes[0] : 0;
            const showSize = showPages && table.dataset.pagerPageSize !== 'false';
            let page = 0;
            let pager = null;
            const stripe = () => rows().filter(r => r.style.display !== 'none').forEach((r, i) => r.classList.toggle('bg-slate-50', i % 2 === 1));
            const link = (text, to, css) => {
              const a = document.createElement('a');
              a.className = 'pg ' + (css ? css + ' ' : '')
                + (css === 'active' ? 'inline-flex h-8 min-w-8 items-center justify-center rounded-md border border-blue-600 bg-blue-600 px-2 text-sm font-semibold text-white'
                  : css === 'disabled' ? 'pointer-events-none inline-flex h-8 min-w-8 items-center justify-center rounded-md border border-slate-200 bg-slate-50 px-2 text-sm text-slate-300'
                  : css === 'dots' ? 'inline-flex h-8 min-w-8 cursor-default items-center justify-center px-2 text-sm text-slate-400'
                  : 'inline-flex h-8 min-w-8 items-center justify-center rounded-md border border-slate-300 bg-white px-2 text-sm text-slate-700 transition hover:border-blue-500 hover:text-blue-600');
              a.textContent = text;
              a.dataset.page = to;
              return a;
            };
            const show = () => {
              const all = rows();
              // 最初のページ以外の行（サーバーで隠した行 hidden）は、ここで表示・非表示を決める
              all.forEach(r => r.classList.remove('hidden'));
              // SEQ（td.seq）：並べ替えた後も上から 1, 2, …（data-row の行番号は変えない）
              all.forEach((r, i) => { const seq = r.querySelector('td.seq'); if (seq) seq.textContent = i + 1; });
              if (pager && showTotal) pager.querySelector('.total span').textContent = all.length;
              if (size) {
                const pages = Math.max(1, Math.ceil(all.length / size));
                page = Math.max(0, Math.min(page, pages - 1));
                all.forEach((r, i) => r.style.display = Math.floor(i / size) === page ? '' : 'none');
                // ページ番号：今のページの前後（最大 6 個）と最初・最後。間は …
                pager.querySelectorAll('a.pg').forEach(a => a.remove());
                const current = page + 1;
                const start = Math.max(1, Math.min(current - 2, pages - 5));
                const end = Math.min(pages, start + 5);
                const links = [link('‹', current - 1, current === 1 ? 'disabled' : '')];
                if (start > 1) links.push(link('1', 1));
                if (start > 2) links.push(link('…', 0, 'dots'));
                for (let p = start; p <= end; p++) links.push(link(String(p), p, p === current ? 'active' : ''));
                if (end < pages - 1) links.push(link('…', 0, 'dots'));
                if (end < pages) links.push(link(String(pages), pages));
                links.push(link('›', current + 1, current === pages ? 'disabled' : ''));
                pager.append(...links);
              }
              stripe();
            };
            if (showTotal || size) {
              pager = document.createElement('div');
              pager.className = 'pager flex flex-wrap items-center justify-end gap-1.5 px-3 py-3';
              // 件数がない場合も、表示件数の選択・ページ番号は右に寄せる（.total の margin-right: auto の代わり）
              pager.innerHTML = showTotal ? '<span class="total mr-auto text-sm text-slate-600">件数：<span></span></span>' : '<span class="total mr-auto"></span>';
              if (showSize) {
                if (!sizes.includes(size)) sizes.unshift(size);
                pager.insertAdjacentHTML('beforeend', '<select class="page-size mr-2 h-8 w-32 rounded-md border border-slate-300 bg-white px-2 text-sm text-slate-700">'
                  + sizes.map(s => '<option value="' + s + '"' + (s === size ? ' selected' : '') + '>' + s + '件/ページ</option>').join('') + '</select>');
                pager.querySelector('select').addEventListener('change', e => { size = Number(e.target.value); page = 0; show(); });
              }
              pager.addEventListener('click', e => {
                const a = e.target.closest('a.pg');
                if (!a || a.classList.contains('disabled') || a.classList.contains('dots') || a.classList.contains('active')) return;
                page = Number(a.dataset.page) - 1;
                show();
              });
              (table.closest('.table-scroll') || table).after(pager);
            }
            show();
            if (table.dataset.sortable) {
              const heads = [...table.tHead.rows[0].cells];
              heads.forEach((th, index) => {
                if (!th.classList.contains('sortable')) return;
                th.addEventListener('click', e => {
                  // 幅を変えた直後のクリック・取っ手のクリックは並べ替えない
                  if (e.target.closest('.col-resizer') || table.dataset.resized) return;
                  const asc = !th.classList.contains('sort-asc');
                  heads.forEach(h => {
                    h.classList.remove('sort-asc', 'sort-desc');
                    h.querySelector('.sort-indicator')?.remove();
                  });
                  th.classList.add(asc ? 'sort-asc' : 'sort-desc');
                  th.insertAdjacentHTML('beforeend', '<span class="sort-indicator ml-1 text-[10px] text-blue-600" aria-hidden="true">' + (asc ? '▲' : '▼') + '</span>');
                  const sorted = rows().sort((a, b) => {
                    const x = a.cells[index].textContent.trim(), y = b.cells[index].textContent.trim();
                    const nx = Number(x.replace(/,/g, '')), ny = Number(y.replace(/,/g, ''));
                    const c = !isNaN(nx) && !isNaN(ny) && x !== '' && y !== '' ? nx - ny : x.localeCompare(y, 'ja');
                    return asc ? c : -c;
                  });
                  sorted.forEach(r => body.appendChild(r));
                  const empty = body.querySelector('tr.empty');
                  if (empty) body.appendChild(empty);
                  page = 0;
                  show();
                });
              });
              // 列の幅：見出しの右端（.col-resizer）をつかんで左右に動かす
              table.querySelectorAll('th .col-resizer').forEach(handle => handle.addEventListener('mousedown', e => {
                e.preventDefault();
                const th = handle.parentElement, x = e.clientX, width = th.offsetWidth;
                document.body.classList.add('col-resizing');
                const move = m => { th.style.width = th.style.minWidth = Math.max(30, width + m.clientX - x) + 'px'; table.dataset.resized = '1'; };
                const up = () => {
                  document.body.classList.remove('col-resizing');
                  document.removeEventListener('mousemove', move);
                  document.removeEventListener('mouseup', up);
                  setTimeout(() => delete table.dataset.resized, 0);
                };
                document.addEventListener('mousemove', move);
                document.addEventListener('mouseup', up);
              }));
            }
            // 見出しのチェック ボックス：表示中の行の選択をまとめて切り替える
            const checkAll = table.querySelector('thead input[data-check-all]');
            if (checkAll) {
              checkAll.addEventListener('change', () => rows().filter(r => r.style.display !== 'none')
                .forEach(r => r.querySelectorAll('input[data-check]').forEach(c => { c.checked = checkAll.checked; c.dispatchEvent(new Event('change', { bubbles: true })); })));
            }
          });
          };
          init(document);
          if (window.screenUpdate) window.screenUpdate.onUpdate(init);
        })();
    //]]>
    </script>

    <!--/* MessageView の補助スクリプト（自動生成）：折りたたんだときの件数、× で閉じる、項目付きのメッセージのクリックでその入力欄へ、Info を消す、ブラウザー側の Msg */-->
    <script th:inline="none">
    //<![CDATA[
        (() => {
          const LEVELS = ['Error', 'Warning', 'Info'];
          const NAMES = { Error: 'エラー', Warning: '警告', Info: 'お知らせ' };
          const views = () => [...document.querySelectorAll('.message-view')];
          const levelOf = li => LEVELS.find(l => li.classList.contains('message-' + l.toLowerCase()));
          const accepts = (view, level) => (view.dataset.levels || LEVELS.join(',')).split(',').includes(level);
          // 件数（折りたたんだときの 1 行）。Error があるときは開く
          function refresh(view) {
            const items = [...view.querySelectorAll('.message-item')];
            view.classList.toggle('hidden', items.length === 0);
            const summary = view.querySelector('.message-summary');
            if (summary) summary.textContent = LEVELS.map(l => [l, items.filter(li => levelOf(li) === l).length]).filter(x => x[1] > 0).map(x => NAMES[x[0]] + ' ' + x[1] + ' 件').join('・');
            if (view.tagName === 'DETAILS' && items.some(li => levelOf(li) === 'Error')) view.open = true;
          }
          // Info を N 秒後に消す（AutoHideInfo）
          function autoHide(view, li) {
            const seconds = Number(view.dataset.autoHideInfo || 0);
            if (seconds > 0 && levelOf(li) === 'Info') setTimeout(() => { li.remove(); refresh(view); }, seconds * 1000);
          }
          // メッセージを書く（そのレベルを表示する最初の MessageView。Error・Warning・Info の順に並べる）。表示できない場合は false
          function add(level, text, item) {
            const view = views().find(v => accepts(v, level));
            if (!view) return false;
            const li = document.createElement('li');
            li.className = 'message-item message-' + level.toLowerCase() + ' flex items-start gap-2 py-0.5 text-sm '
              + (level === 'Error' ? 'text-red-600' : level === 'Warning' ? 'text-amber-600' : 'text-blue-600');
            const icon = document.createElement('span');
            icon.className = 'shrink-0';
            icon.textContent = level === 'Error' ? '✖' : level === 'Warning' ? '⚠' : 'ℹ';
            li.appendChild(icon);
            if (item) li.dataset.item = item;
            const span = document.createElement('span');
            span.className = 'message-text';
            span.textContent = text;
            li.appendChild(span);
            const list = view.querySelector('.message-list');
            const next = [...list.children].find(c => LEVELS.indexOf(levelOf(c)) > LEVELS.indexOf(level));
            list.insertBefore(li, next || null);
            view.classList.remove('hidden');
            refresh(view);
            autoHide(view, li);
            return true;
          }
          // 空にする（onEventOnly：ClearOnEvent の MessageView だけ）
          function clear(onEventOnly) {
            views().filter(v => !onEventOnly || v.dataset.clearOnEvent !== 'false').forEach(v => {
              v.querySelectorAll('.message-item').forEach(li => li.remove());
              refresh(v);
            });
          }
          function count(level) {
            return views().reduce((n, v) => n + [...v.querySelectorAll('.message-item')].filter(li => !level || levelOf(li) === level).length, 0);
          }
          window.screenMessages = { add, clear, count };
          document.addEventListener('click', e => {
            // × で閉じる（次のメッセージで再び表示する）
            const close = e.target.closest('.message-close');
            if (close) {
              close.closest('.message-view').classList.add('hidden');
              return;
            }
            // 項目付きのメッセージ：その入力欄へ移動する
            const li = e.target.closest('.message-item[data-item]');
            if (li && li.dataset.item) {
              const name = li.dataset.item;
              const target = document.querySelector(`[name="${name}"], [data-item="${name}"]`) || document.getElementById(name);
              if (target) {
                target.scrollIntoView({ block: 'center' });
                target.focus();
              }
            }
          });
          const init = () => views().forEach(v => {
            refresh(v);
            v.querySelectorAll('.message-item').forEach(li => autoHide(v, li));
          });
          init();
          // 画面の部分更新で入れ替えた後
          if (window.screenUpdate) window.screenUpdate.onUpdate(init);
        })();
    //]]>
    </script>

    <!--/* 和暦の補助スクリプト（自動生成）：和暦の入力（R7.10.17・令和7年10月17日 など）を日付にして送信する入力欄に入れる、📅 でカレンダーから選ぶ */-->
    <script th:inline="none">
    //<![CDATA[
    (() => {
      // 元号（新しい順）と始まりの日。明治はサーバー（java.time.chrono.JapaneseDate）と同じく明治6年（1873年）から
      const ERAS = [
        { name: '令和', abbr: 'R', start: [2019, 5, 1] },
        { name: '平成', abbr: 'H', start: [1989, 1, 8] },
        { name: '昭和', abbr: 'S', start: [1926, 12, 25] },
        { name: '大正', abbr: 'T', start: [1912, 7, 30] },
        { name: '明治', abbr: 'M', start: [1873, 1, 1] },
      ];
      const key = (y, m, d) => y * 10000 + m * 100 + d;
      const eraOf = (y, m, d) => ERAS.find(e => key(y, m, d) >= key(...e.start));
      const two = n => String(n).padStart(2, '0');
      // yyyy-MM-dd（日時は yyyy-MM-ddTHH:mm）→ 令和7年10月17日（日時は 令和7年10月17日 14:30）。明治6年より前は西暦のまま
      function format(iso, withTime) {
        const m = /^(\d{4})-(\d{2})-(\d{2})(?:T(\d{2}):(\d{2}))?/.exec(iso || '');
        if (!m) return '';
        const [y, mo, d] = [+m[1], +m[2], +m[3]];
        const era = eraOf(y, mo, d);
        const time = withTime && m[4] ? ' ' + m[4] + ':' + m[5] : '';
        if (!era) return y + '年' + mo + '月' + d + '日' + time;
        const year = y - era.start[0] + 1;
        return era.name + (year === 1 ? '元' : year) + '年' + mo + '月' + d + '日' + time;
      }
      // 和暦・西暦の文字 → yyyy-MM-dd（日時は yyyy-MM-ddTHH:mm。時刻がない場合は今の値の時刻、ない場合は 00:00）。空は ''、読めない場合は null
      function parse(text, withTime, current) {
        let s = (text || '').replace(/[！-～]/g, c => String.fromCharCode(c.charCodeAt(0) - 0xFEE0)).replace(/　/g, ' ').trim();
        if (!s) return '';
        let hh = null, mi = null;
        const t = /\s*(\d{1,2})\s*[:時]\s*(\d{1,2})\s*分?$/.exec(s);
        if (t) {
          hh = +t[1]; mi = +t[2];
          s = s.slice(0, t.index).trim();
          if (!withTime || hh > 23 || mi > 59) return null;
        }
        let y, mo, d, era = null, m;
        if ((m = /^(令和|平成|昭和|大正|明治|[令平昭大明RHSTMrhstm])\s*(元|\d{1,2})\s*[年.\/\-\s]\s*(\d{1,2})\s*[月.\/\-\s]\s*(\d{1,2})\s*日?$/.exec(s))
          || (m = /^([RHSTMrhstm])(\d{2})(\d{2})(\d{2})$/.exec(s))) {
          era = ERAS.find(e => e.name === m[1] || e.name[0] === m[1] || e.abbr === m[1].toUpperCase());
          y = era.start[0] + (m[2] === '元' ? 1 : +m[2]) - 1; mo = +m[3]; d = +m[4];
        } else if ((m = /^(\d{4})\s*[年.\/\-]\s*(\d{1,2})\s*[月.\/\-]\s*(\d{1,2})\s*日?$/.exec(s)) || (m = /^(\d{4})(\d{2})(\d{2})$/.exec(s))) {
          y = +m[1]; mo = +m[2]; d = +m[3];
        } else {
          return null;
        }
        const date = new Date(Date.UTC(y, mo - 1, d));
        if (date.getUTCFullYear() !== y || date.getUTCMonth() !== mo - 1 || date.getUTCDate() !== d) return null;
        if (era && eraOf(y, mo, d) !== era) return null;   // 元号の期間の外（例: 平成31年5月1日）
        const iso = y + '-' + two(mo) + '-' + two(d);
        if (!withTime) return iso;
        const old = /T(\d{2}:\d{2})/.exec(current || '');
        return iso + 'T' + (hh !== null ? two(hh) + ':' + two(mi) : old ? old[1] : '00:00');
      }
      function init() {
        document.querySelectorAll('.wareki').forEach(box => {
          const text = box.querySelector('[data-wareki-text]');
          const native = box.querySelector('.wareki-native');
          const picker = box.querySelector('.wareki-picker');
          if (!text || !native || box.dataset.warekiReady) return;
          box.dataset.warekiReady = '1';
          const withTime = native.type === 'datetime-local';
          const message = '和暦の' + (withTime ? '日時' : '日付') + 'として読めません（例: ' + (withTime ? 'R7.10.17 14:30' : 'R7.10.17、令和7年10月17日') + '）';
          const show = () => {
            text.value = format(native.value, withTime);
            text.setCustomValidity('');
            native.setCustomValidity('');
            text.disabled = native.disabled;
            if (picker) picker.disabled = native.disabled || native.readOnly;
          };
          show();
          text.addEventListener('input', () => { text.setCustomValidity(''); native.setCustomValidity(''); });
          text.addEventListener('change', () => {
            const value = parse(text.value, withTime, native.value);
            if (value === null) {
              text.setCustomValidity(message);
              native.setCustomValidity(message);
              text.reportValidity();
              return;
            }
            native.value = value;
            show();
            native.dispatchEvent(new Event('input', { bubbles: true }));
            native.dispatchEvent(new Event('change', { bubbles: true }));
          });
          native.addEventListener('change', show);
          native.addEventListener('wareki-sync', show);
          // 送信のときの入力チェック（必須・範囲）：隠した入力欄の代わりに和暦の入力欄にメッセージを出す
          native.addEventListener('invalid', e => {
            e.preventDefault();
            text.setCustomValidity(native.validationMessage);
            text.reportValidity();
          });
          // 隠した入力欄にフォーカスが来たとき（メッセージの項目へ移動など）は和暦の入力欄へ
          native.addEventListener('focus', () => text.focus());
          if (picker) picker.addEventListener('click', () => {
            if (native.disabled || native.readOnly) return;
            try { native.showPicker(); } catch (e) { /* 日付の選択がないブラウザーは和暦の入力だけ */ }
          });
        });
      }
      window.warekiInit = init;
      if (document.readyState === 'loading') document.addEventListener('DOMContentLoaded', init); else init();
      // 画面の部分更新で入れ替えた後
      if (window.screenUpdate) window.screenUpdate.onUpdate(() => init());
    })();
    //]]>
    </script>
</main>
</body>
</html>
