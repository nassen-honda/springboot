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
<body class="min-h-screen bg-slate-100 text-slate-800">
    <header class="border-b border-slate-200 bg-white px-6 py-4">
        <span class="text-lg font-bold text-slate-900">サンプルアプリ</span>
    </header>
    <div class="mx-auto max-w-5xl p-6">
        <div th:replace="${content}">コンテンツ</div>
    </div>
    <footer class="px-6 py-4 text-xs text-slate-400">
        <small>&copy; Example</small>
    </footer>
</body>
</html>
