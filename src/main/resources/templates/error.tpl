<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="ja">
<!--/*
  エラー画面（Form.Load のエラー・画面のイベント以外の捕まえていないエラー・存在しないページ）。
  ScriptErrorAdvice が errorCode（ex.Code）・errorDescription（ex.Description）・errorScreen・errorEvent・errorTime を設定する。
  Spring Boot の /error（404 など）の場合は status・error・path。見た目は自由に変更できる（再生成で上書きしない）。
*/-->
<head>
    <meta charset="UTF-8">
    <title>エラー</title>
    <link rel="stylesheet" th:href="@{/css/app.css}">
</head>
<body>
<main class="screen" style="max-width: 640px; margin: 40px auto;">
    <h1>処理できませんでした</h1>
    <p th:text="${errorDescription} ?: (${status} == 404 ? '指定したページはありません。' : 'エラーが起きました。しばらくしてからもう一度操作してください。')">エラーが起きました。</p>
    <p th:if="${message}" th:text="${message}" style="white-space: pre-line;"></p>
    <dl style="color: #666; font-size: 90%;">
        <dt>種類</dt>
        <dd th:text="${errorCode} ?: (${error} ?: ${status})">その他のエラー</dd>
        <th:block th:if="${errorScreen}">
            <dt>画面・イベント</dt>
            <dd th:text="${errorScreen} + (${errorEvent} != null and ${errorEvent} != '' ? '（' + ${errorEvent} + '）' : '')"></dd>
        </th:block>
        <th:block th:if="${errorTime}">
            <dt>日時</dt>
            <dd th:text="${#temporals.format(errorTime, 'yyyy/MM/dd HH:mm:ss')}"></dd>
        </th:block>
    </dl>
    <p><a th:href="@{/}">トップへ戻る</a></p>
</main>
</body>
</html>
