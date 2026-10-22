<!--/* プロジェクトのスタイルシート（Tailwind の app.css + カスタム上書き Stylesheet.css）を読み込む部品（自動生成 / auto-generated）。
       基本テンプレートの head で <link th:replace="~{parts/stylesheets :: links}"/> と書いて使う。手動編集は再生成で失われます。 */-->
<th:block xmlns:th="http://www.thymeleaf.org" th:fragment="links">
    <link rel="stylesheet" th:href="@{/css/app.css}"/>
    <link rel="stylesheet" th:href="@{/css/Stylesheet.css}"/>
</th:block>
