<!DOCTYPE html>
<!--/* 取引先マスタ詳細 画面テンプレート（自動生成 / auto-generated）
       画面の部品のテンプレート（テンプレートのフォルダーの screen\*.tpl）を展開して画面を構成します。 */-->
<html lang="ja" xmlns:th="http://www.thymeleaf.org"
      th:replace="~{base/WebPageBaseLayout :: layout(~{::title}, ~{::main})}">
<head>
    <title th:text="'取引先マスタ詳細'">取引先マスタ詳細</title>
</head>
<body>
<main class="min-w-0 flex-1 overflow-auto p-4 sm:p-6">
    <div class="mx-auto mb-6 max-w-[1600px]">
        <div th:replace="~{parts/ui :: pageHeader('取引先マスタ詳細', '取引先の情報を確認・編集します。')}"></div>
    </div>
    <form class="mx-auto max-w-[1600px]" th:action="@{/CustomerMasterDetail}" th:object="${form}" method="post">

        <!--/* メッセージ（MessageView を置いていない画面の既定の MessageView：Msg・Throw・エラー・入力チェック） */-->
        <div class="message-view relative mb-4 rounded-xl border border-slate-200 bg-white px-4 py-3 shadow-sm" th:classappend="${messages.empty} ? 'hidden'" data-levels="Error,Warning,Info" data-clear-on-event="true">
            <ul class="message-list mt-2 space-y-1">
                <li class="message-item message-error flex items-start gap-2 text-sm text-red-600" th:each="m : ${messages.error}" th:attr="data-item=${m.item}"><span class="shrink-0" aria-hidden="true">✖</span><span class="message-text" th:text="${m.description}">エラーのメッセージ</span></li>
                <li class="message-item message-warning flex items-start gap-2 text-sm text-amber-600" th:each="m : ${messages.warning}" th:attr="data-item=${m.item}"><span class="shrink-0" aria-hidden="true">⚠</span><span class="message-text" th:text="${m.description}">警告のメッセージ</span></li>
                <li class="message-item message-info flex items-start gap-2 text-sm text-blue-600" th:each="m : ${messages.info}" th:attr="data-item=${m.item}"><span class="shrink-0" aria-hidden="true">ℹ</span><span class="message-text" th:text="${m.description}">お知らせのメッセージ</span></li>
            </ul>
            <button type="button" class="message-close absolute right-3 top-2.5 text-slate-400 transition hover:text-slate-600" title="閉じる">×</button>
        </div>

        <!--/* 画面項目（画面定義の「画面」タブの順・入れ子） */-->
        <div data-node="n:TableMain" class="space-y-6">
            <div data-node="n:Panel" class="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
                <div class="mb-6 flex flex-wrap items-center justify-between gap-3 border-b border-slate-100 pb-4">
                    <div class="flex items-center gap-3">
                        <button type="submit" name="_event" value="btnReturn_Click" data-item="btnReturn" data-node="n:btnReturn"
                                class="inline-flex items-center gap-1 text-sm font-medium text-slate-500 transition hover:text-slate-900 focus:outline-none focus-visible:ring-2 focus-visible:ring-slate-300 focus-visible:ring-offset-1">
                            <svg class="h-4 w-4" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="m15 18-6-6 6-6"/></svg>
                            一覧へ戻る
                        </button>
                        <span class="h-4 w-px bg-slate-200" aria-hidden="true"></span>
                        <h2 class="text-base font-semibold text-slate-900">取引先情報</h2>
                    </div>
                    <span class="text-xs text-slate-400"><span class="text-red-500">*</span> は必須項目</span>
                </div>
                <fieldset data-node="v:Customer">
                    <div class="grid grid-cols-1 gap-x-8 xl:grid-cols-2">
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_CustomerCD', '取引先コード', 'CustomerMST00004_CustomerCD', true, 10, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_CustomerInvoiceCD', '取引先請求コード', 'CustomerMST00004_CustomerInvoiceCD', null, 10, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldSelect('v:Customer.MST00004_CustomerKbn', '取引先区分', 'CustomerMST00004_CustomerKbn', ${CustomerMST00004_CustomerKbnItems}, true, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_CustomerNM', '取引先名', 'CustomerMST00004_CustomerNM', null, 100, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_CustomerOmission', '取引先略称', 'CustomerMST00004_CustomerOmission', null, 100, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_AreaCD', '地域コード', 'CustomerMST00004_AreaCD', null, 20, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_Address1', '住所1', 'CustomerMST00004_Address1', null, 100, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_Address2', '住所2', 'CustomerMST00004_Address2', null, 100, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_Address3', '住所3', 'CustomerMST00004_Address3', null, 100, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_Tel', '電話番号', 'CustomerMST00004_Tel', null, 20, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_Fax', 'FAX', 'CustomerMST00004_Fax', null, 20, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_Zip', '郵便番号', 'CustomerMST00004_Zip', null, 200, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_FromPlaceCD', '発地コード', 'CustomerMST00004_FromPlaceCD', null, 20, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_ToPlaceCD', '仕向地コード', 'CustomerMST00004_ToPlaceCD', null, 20, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_PaymentTermsCD', '支払条件コード', 'CustomerMST00004_PaymentTermsCD', true, 20, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_DeliveryLocation', '納入場所', 'CustomerMST00004_DeliveryLocation', null, 200, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_TradeTermsCD', '取引条件コード', 'CustomerMST00004_TradeTermsCD', null, 20, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_Currency', '通貨', 'CustomerMST00004_Currency', null, 20, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_SectionNM', '部署名', 'CustomerMST00004_SectionNM', null, 200, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_UserCD', '担当者名', 'CustomerMST00004_UserCD', null, 10, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_MailAddress', 'メールアドレス', 'CustomerMST00004_MailAddress', null, 50, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_TaxRoundKBN', '消費税端数処理区分', 'CustomerMST00004_TaxRoundKBN', null, 2, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_TaxCalcKBN', '税額通知区分', 'CustomerMST00004_TaxCalcKBN', null, 2, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_InvoiceCreateFlg', '請求書作成フラグ', 'CustomerMST00004_InvoiceCreateFlg', null, 1, null, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldSelect('v:Customer.MST00004_DelFlg', '削除フラグ', 'CustomerMST00004_DelFlg', ${CustomerMST00004_DelFlgItems}, null, true, true)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_CreateUserCD', '作成者', 'CustomerMST00004_CreateUserCD', null, 10, true, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldDateTime('v:Customer.MST00004_CreateDate', '作成日時', 'CustomerMST00004_CreateDate', null, true, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldText('v:Customer.MST00004_UpdateUserCD', '更新者', 'CustomerMST00004_UpdateUserCD', null, 10, true, null)}"></div>
                        <div th:replace="~{parts/ui :: fieldDateTime('v:Customer.MST00004_UpdateDate', '更新日時', 'CustomerMST00004_UpdateDate', null, true, null)}"></div>
                    </div>
                </fieldset>
                <div data-node="n:buttonGroup1" class="mt-6 flex flex-wrap items-center justify-end gap-3 border-t border-slate-200 pt-5">
                    <button type="submit" name="_event" value="btnOk_Click" data-item="btnOk" data-node="n:btnOk" class="inline-flex items-center justify-center gap-1.5 rounded-md bg-blue-600 px-5 py-2 text-sm font-medium text-white transition hover:bg-blue-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-1">確定</button>
                    <button type="button" data-item="btnCancel" data-node="n:btnCancel" class="inline-flex items-center justify-center gap-1.5 rounded-md px-3.5 py-2 text-sm font-medium transition focus:outline-none focus-visible:ring-2 focus-visible:ring-offset-1 disabled:opacity-60 border border-slate-300 bg-white text-slate-700 hover:bg-slate-50 focus-visible:ring-slate-300">キャンセル</button>
                </div>
            </div>
        </div>

        <!--/* 画面に配置していない変数（ブラウザー側の処理とサーバーの間で値を受け渡す） */-->
        <input type="hidden" th:field="*{piTrnMode}"/>
        <input type="hidden" th:field="*{piCustomerCd}"/>
        <input type="hidden" th:field="*{Res}"/>
        <input type="hidden" th:field="*{Message}"/>
        <input type="hidden" th:field="*{NewDate}"/>
        <input type="hidden" th:field="*{UserCd}"/>
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
</main>
</body>
</html>
