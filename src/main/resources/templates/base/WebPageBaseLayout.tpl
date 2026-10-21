<!--/* caption: 基本テンプレート */-->
<!DOCTYPE html>
<!--/* 基本テンプレート WebPageBaseLayout（BaseObject/WebPageBaseTemplates/WebPageBaseLayout.tpl）
       テンプレート（BaseObjectCustomized/WebPageTemplates）の土台。画面は layout(title, content) を th:replace で呼び出す。
       テンプレートの項目（ヘッダーなど）と画面の内容は content に入る。
       スタイルシート：基本の BaseStylesheet.css と、BaseObjectCustomized/Stylesheet の *.css（parts/stylesheets.tpl。自動生成）。 */-->
<html lang="ja" xmlns:th="http://www.thymeleaf.org" th:fragment="layout(title, content)">
<head th:fragment="head(title)">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title th:replace="${title}">タイトル</title>
    <link rel="stylesheet" th:href="@{/css/BaseStylesheet.css}"/>
    <link th:replace="~{parts/stylesheets :: links}"/>
</head>
<body>
    <!-- ヘッダー -->
    <header th:fragment="header" class="header">
        <div class="logo">${title}タイトル</div>
        <div class=".open">⇔</div>
       <div class="right">
            <span class="user">田中 太郎</span>
            <a class="logout" th:href="@{/login}">ログアウト</a>
        </div>
    </header>
    <!-- サイドメニュー -->
 <div style="display: flex;"> 
 <aside th:fragment="sidebar" class="aside">
        <a th:href="@{/home}" class="menu-link" th:classappend="${activeScreen == 'home'} ? 'active'">
            <span class="ico">🏠</span>
            <span>Top</span>
        </a>
        <!--/* メニュー：モジュール SideMenu（menu/SideMenu.module.json）の Sub Call で作った項目（画面・グループ）。画面を増やすときはモジュールのコードに 1 行足す。
               sideMenu・activeScreen（今の画面の ID）は ScriptMenuAdvice がすべての画面の model に入れる */-->
        <th:block th:replace="~{base/WebPageBaseLayout :: menuItems(${sideMenu}, 0)}"></th:block>
        <div class="version">Version: 1.3.1</div>
        <!--/* グループは最初は閉じていて、クリックで開閉する（details）。今の画面を含むグループは開いておく */-->
        <script>
            document.querySelectorAll('.aside .menu-link.active').forEach(function (link) {
                for (var group = link.closest('details.group'); group; group = group.parentElement.closest('details.group')) group.open = true;
            });
        </script>
    </aside>
    <!--/* メニューの項目（menuNodes：MenuItem の List、depth：入れ子の深さ）：子項目（Children）がある項目はグループ（details）、ない項目は画面へのリンク。
           グループの中は同じフラグメントで入れ子に表示する。menuNodes がない場合（このテンプレートを表示するとき）は何も出さない */-->
    <th:block th:fragment="menuItems(menuNodes, depth)" th:if="${menuNodes != null}">
        <th:block th:each="item : ${menuNodes}">
            <details th:if="${!item.children.isEmpty()}" class="group">
                <summary class="group-title" th:style="${depth > 0} ? |padding-left: ${20 + depth * 34}px|">
                    <span class="ico" th:text="${item.icon}"></span>
                    <span class="label" th:text="${item.caption}">グループ</span>
                    <span class="caret"></span>
                </summary>
                <div class="submenu">
                    <th:block th:replace="~{base/WebPageBaseLayout :: menuItems(${item.children}, ${depth + 1})}"></th:block>
                </div>
            </details>
            <a th:if="${item.children.isEmpty()}" th:href="@{${item.url}}" class="menu-link"
               th:classappend="${item.key == activeScreen} ? 'active'"
               th:style="${depth > 0} ? |padding-left: ${(item.key == activeScreen ? 16 : 20) + depth * 34}px|">
                <span class="ico" th:text="${item.icon}"></span>
                <span th:text="${item.caption}">画面</span>
            </a>
        </th:block>
    </th:block>
    <div class="app" th:replace="${content}">コンテンツ</div> 
</div> 
</body>
</html>
