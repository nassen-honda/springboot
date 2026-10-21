<!--/* caption: 基本テンプレート（Tailwind CSS） */-->
<!DOCTYPE html>
<!--/* 基本テンプレート WebPageBaseLayout（BaseObject/WebPageBaseTemplates/WebPageBaseLayout.tpl）
       テンプレート（BaseObjectCustomized/WebPageTemplates）の土台。画面は layout(title, content) を th:replace で呼び出す。
       スタイルシート：Tailwind CSS のビルド結果（/css/app.css。src/main/frontend/tailwind.css から生成）。
       画面側の <main> に「min-w-0 flex-1 overflow-auto p-6」を付けると、本文だけがスクロールする。
       サイドメニューは lg 未満で引き出し（ハンバーガーで開閉）。 */-->
<html lang="ja" xmlns:th="http://www.thymeleaf.org" th:fragment="layout(title, content)">
<head th:fragment="head(title)">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title th:replace="${title}">タイトル</title>
    <link rel="icon" type="image/svg+xml" th:href="@{/favicon.svg}"/>
    <link rel="stylesheet" th:href="@{/css/app.css}"/>
</head>
<body class="min-h-screen bg-slate-100 text-slate-800">
    <!-- ヘッダー -->
    <header th:fragment="header" class="sticky top-0 z-50 flex h-14 items-center justify-between border-b border-slate-200 bg-white/95 px-4 backdrop-blur sm:px-6">
        <div class="flex items-center gap-2 sm:gap-3">
            <button type="button" id="sidebar-toggle" aria-controls="sidebar" aria-expanded="false" aria-label="メニューを開く"
                    class="grid h-9 w-9 place-items-center rounded-lg text-slate-600 transition hover:bg-slate-100 lg:hidden">
                <svg class="h-5 w-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" aria-hidden="true">
                    <path d="M4 6h16M4 12h16M4 18h16"/>
                </svg>
            </button>
            <span class="grid h-8 w-8 place-items-center rounded-lg bg-blue-600 text-sm font-bold text-white">C</span>
            <span class="hidden text-lg font-bold tracking-tight text-slate-900 sm:inline">CEMシステム</span>
        </div>
        <div class="flex items-center gap-3 sm:gap-4">
            <span class="hidden text-sm text-slate-500 sm:inline">田中 太郎</span>
            <a th:href="@{/login}" class="rounded-md border border-slate-300 bg-white px-3 py-1.5 text-sm font-medium text-slate-700 transition hover:bg-slate-50">ログアウト</a>
        </div>
    </header>

    <div class="flex h-[calc(100vh-3.5rem)]">
        <!-- モバイル：メニューの外側を押したら閉じる -->
        <div id="sidebar-backdrop" class="fixed inset-0 z-30 hidden bg-slate-900/40 backdrop-blur-sm lg:hidden"></div>

        <!-- サイドメニュー -->
        <aside th:fragment="sidebar" id="sidebar"
               class="fixed inset-y-0 left-0 z-40 w-64 -translate-x-full overflow-y-auto border-r border-slate-200 bg-white px-3 pb-4 pt-20 transition-transform duration-200 lg:static lg:z-auto lg:translate-x-0 lg:pt-4">
            <nav class="space-y-1" aria-label="メインメニュー">
                <a th:href="@{/home}" aria-label="トップ"
                   class="menu-link mb-1 flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium transition hover:bg-slate-100"
                   th:classappend="${activeScreen == 'home'} ? 'active bg-blue-50 text-blue-700' : 'text-slate-700'">
                    <span class="w-5 text-center" aria-hidden="true">🏠</span>
                    <span>Top</span>
                </a>
                <!--/* メニュー：モジュール SideMenu（menu/SideMenu.module.json）の Sub Call で作った項目（画面・グループ）。画面を増やすときはモジュールのコードに 1 行足す。
                       sideMenu・activeScreen（今の画面の ID）は ScriptMenuAdvice がすべての画面の model に入れる */-->
                <th:block th:replace="~{base/WebPageBaseLayout :: menuItems(${sideMenu}, 0)}"></th:block>
            </nav>
            <div class="mt-8 text-center text-xs text-slate-400">Version: 1.3.1</div>
            <!--/* グループは最初は閉じていて、クリックで開閉する（details）。今の画面を含むグループは開いておく */-->
            <script>
                document.querySelectorAll('aside .menu-link.active').forEach(function (link) {
                    for (var group = link.closest('details'); group; group = group.parentElement.closest('details')) group.open = true;
                });
            </script>
        </aside>

        <!--/* メニューの項目（menuNodes：MenuItem の List、depth：入れ子の深さ）：子項目（Children）がある項目はグループ（details）、ない項目は画面へのリンク。
               グループの中は同じフレームで入れ子に表示する。menuNodes がない場合（このテンプレートを表示するとき）は何も出さない */-->
        <th:block th:fragment="menuItems(menuNodes, depth)" th:if="${menuNodes != null}">
            <th:block th:each="item : ${menuNodes}">
                <details th:if="${!item.children.isEmpty()}" class="group">
                    <summary class="flex cursor-pointer list-none items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-slate-700 transition hover:bg-slate-100 [&::-webkit-details-marker]:hidden">
                        <span class="w-5 text-center" th:text="${item.icon}" aria-hidden="true"></span>
                        <span class="flex-1" th:text="${item.caption}">グループ</span>
                        <svg class="h-4 w-4 shrink-0 text-slate-400 transition-transform group-open:rotate-180" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                            <path d="m6 9 6 6 6-6"/>
                        </svg>
                    </summary>
                    <div class="mt-1 space-y-1 pl-6">
                        <th:block th:replace="~{base/WebPageBaseLayout :: menuItems(${item.children}, ${depth + 1})}"></th:block>
                    </div>
                </details>
                <a th:if="${item.children.isEmpty()}" th:href="@{${item.url}}"
                   class="menu-link flex items-center gap-3 rounded-lg px-3 py-2 text-sm transition hover:bg-slate-100"
                   th:attr="aria-current=${item.key == activeScreen ? 'page' : null}"
                   th:classappend="${item.key == activeScreen} ? 'active bg-blue-50 font-semibold text-blue-700' : 'text-slate-600 hover:text-slate-900'">
                    <span class="w-5 text-center" th:text="${item.icon}" aria-hidden="true"></span>
                    <span th:text="${item.caption}">画面</span>
                </a>
            </th:block>
        </th:block>

        <th:block th:replace="${content}"/>
    </div>

    <script>
        // モバイルのサイドメニュー（lg 未満）：ハンバーガーで開閉、外側クリック・Esc・メニュー選択で閉じる
        (() => {
            const sidebar = document.getElementById('sidebar');
            const backdrop = document.getElementById('sidebar-backdrop');
            const toggle = document.getElementById('sidebar-toggle');
            if (!sidebar || !backdrop || !toggle) return;
            const setOpen = open => {
                sidebar.classList.toggle('-translate-x-full', !open);
                backdrop.classList.toggle('hidden', !open);
                toggle.setAttribute('aria-expanded', String(open));
            };
            toggle.addEventListener('click', () => setOpen(sidebar.classList.contains('-translate-x-full')));
            backdrop.addEventListener('click', () => setOpen(false));
            document.addEventListener('keydown', event => { if (event.key === 'Escape') setOpen(false); });
            sidebar.querySelectorAll('a.menu-link').forEach(link => link.addEventListener('click', () => {
                if (window.matchMedia('(max-width: 1023px)').matches) setOpen(false);
            }));
        })();
    </script>
</body>
</html>
