<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="ja">
<!--/*
  エラー画面（Form.Load のエラー・画面のイベント以外の捕まえていないエラー・存在しないページ）。
  ScriptErrorAdvice が errorCode（ex.Code）・errorDescription（ex.Description）・errorScreen・errorEvent・errorTime を設定する。
  Spring Boot の /error（404 など）の場合は status・error・path。見た目は自由に変更できる（再生成で上書きしない）。
*/-->
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>エラー</title>
    <link rel="icon" type="image/svg+xml" th:href="@{/favicon.svg}">
    <link rel="stylesheet" th:href="@{/css/app.css}">
</head>
<body class="min-h-screen bg-slate-100 text-slate-800">
<main class="mx-auto flex min-h-screen max-w-xl flex-col justify-center px-6 py-16">
    <div class="rounded-xl border border-slate-200 bg-white p-8 shadow-sm">
        <div class="mb-4 flex items-center gap-3">
            <span class="grid h-10 w-10 place-items-center rounded-full bg-red-50 text-lg text-red-600">!</span>
            <h1 class="text-xl font-bold text-slate-900">処理できませんでした</h1>
        </div>
        <p class="text-sm leading-6 text-slate-600" th:text="${errorDescription} ?: (${status} == 404 ? '指定したページはありません。' : 'エラーが起きました。しばらくしてからもう一度操作してください。')">エラーが起きました。</p>
        <p th:if="${message}" th:text="${message}" class="mt-2 whitespace-pre-line text-sm text-red-600"></p>
        <dl class="mt-6 grid grid-cols-[auto_1fr] gap-x-6 gap-y-2 rounded-lg bg-slate-50 px-4 py-3 text-sm">
            <dt class="text-slate-500">種類</dt>
            <dd class="text-slate-800" th:text="${errorCode} ?: (${error} ?: ${status})">その他のエラー</dd>
            <th:block th:if="${errorScreen}">
                <dt class="text-slate-500">画面・イベント</dt>
                <dd class="text-slate-800" th:text="${errorScreen} + (${errorEvent} != null and ${errorEvent} != '' ? '（' + ${errorEvent} + '）' : '')"></dd>
            </th:block>
            <th:block th:if="${errorTime}">
                <dt class="text-slate-500">日時</dt>
                <dd class="text-slate-800" th:text="${#temporals.format(errorTime, 'yyyy/MM/dd HH:mm:ss')}"></dd>
            </th:block>
        </dl>
        <a th:href="@{/}" class="mt-6 inline-flex items-center gap-2 rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white transition hover:bg-blue-700">トップへ戻る</a>
    </div>
</main>
</body>
</html>
