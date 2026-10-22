<!DOCTYPE html>
<!--/* 共通レイアウト部品（想定プロジェクトの既存資産）
       各画面は layout(title, content) を th:replace で呼び出して全体を構成する。 */-->
<html lang="ja" xmlns:th="http://www.thymeleaf.org" th:fragment="layout(title, content)">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title th:replace="${title}">タイトル</title>
    <link rel="stylesheet" th:href="@{/css/app.css}"/>
</head>
<body>
    <header class="app-header">
        <span>サンプルアプリ</span>
    </header>
    <div class="container">
        <div th:replace="${content}">コンテンツ</div>
    </div>
    <footer class="app-footer">
        <small>&copy; Example</small>
    </footer>
</body>
</html>
