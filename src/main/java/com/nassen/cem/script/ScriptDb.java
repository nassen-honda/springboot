package com.nassen.cem.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * コードの DB の処理（自動生成 / auto-generated）：テーブルの変数の Load / Insert / Update / Delete / Save と Commit / Rollback。
 *
 * <p>画面のサーバーのイベント 1 つが 1 トランザクション。最初の DB の処理で始め、Commit / Rollback で終わる（その後の DB の処理は新しいトランザクション）。
 * 自動では commit しない：Controller がイベントの後で {@link #end()} を呼び、Commit していない更新は rollback する。
 * トランザクションはスレッド（リクエスト）ごと。画面から呼び出したモジュールも同じトランザクションを使う。</p>
 *
 * <p>戻り値を使う呼び出し（call = false）は失敗を false、文（Call）で呼んだ場合（call = true）は失敗を {@link DbException} にする。</p>
 *
 * <p>主キーの重複は登録の前に主キーで確かめる（SQL のエラーにしないため、PostgreSQL でも重複の後で処理を続けて Commit できる）。
 * 排他のチェックはコードで行う（LoadForUpdate で行ロックを取って読み、読み込んだときの値と比べる）。</p>
 *
 * <p>application.properties：script.db.lock-timeout（LoadForUpdate の待ち時間・秒。表ごとは script.db.lock-timeout.表名）、
 * script.sql.log（実行した検索・件数・時間をログに出す）。手動編集は再生成で失われます。</p>
 */
@Component
public class ScriptDb implements ApplicationContextAware {

    private static ApplicationContext context;

    private static EntityManager entityManager;

    private static final ThreadLocal<TransactionStatus> CURRENT = new ThreadLocal<>();

    private static final Logger SQL_LOG = LoggerFactory.getLogger("script.sql");

    /** DB の処理の失敗（code：重複エラー・排他エラー・データなし）。 */
    public static class DbException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        private final String code;

        public DbException(String code, String message) {
            super(message);
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        entityManager = null;   // アプリを起動し直した（開発時の再起動など）：新しい EntityManagerFactory を使う
    }

    private static PlatformTransactionManager transactions() {
        return context.getBean(PlatformTransactionManager.class);
    }

    private static String property(String key, String defaultValue) {
        return context == null ? defaultValue : context.getEnvironment().getProperty(key, defaultValue);
    }

    /** トランザクションの中の EntityManager（トランザクションがなければ始める）。 */
    private static EntityManager em() {
        if (CURRENT.get() == null) {
            CURRENT.set(transactions().getTransaction(new DefaultTransactionDefinition()));
        }
        if (entityManager == null) {
            entityManager = SharedEntityManagerCreator.createSharedEntityManager(context.getBean(EntityManagerFactory.class));
        }
        return entityManager;
    }

    /** Commit：それまでの更新を確定する（トランザクションがなければ何もしない）。 */
    public static void commit() {
        TransactionStatus status = CURRENT.get();
        if (status != null) {
            CURRENT.remove();
            transactions().commit(status);
        }
    }

    /** Rollback：それまでの更新を取り消す（トランザクションがなければ何もしない）。 */
    public static void rollback() {
        TransactionStatus status = CURRENT.get();
        if (status != null) {
            CURRENT.remove();
            if (!status.isCompleted()) {
                transactions().rollback(status);
            }
        }
    }

    /** イベントの終わり：Commit していない更新は取り消す（自動では commit しない）。 */
    public static void end() {
        rollback();
    }

    /** トランザクションの途中か（Commit / Rollback していない DB の処理がある）。 */
    public static boolean active() {
        return CURRENT.get() != null;
    }

    private static boolean fail(boolean call, String code, String message) {
        if (call) {
            throw new DbException(code, message);
        }
        return false;
    }

    private static <T> T newEntity(Class<T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException(type.getName() + " を作れません", ex);
        }
    }

    /** Load：主キーで 1 件読み込み、変数（target）に写す。見つからない場合は false（call は データなし）。 */
    public static <T> boolean load(Class<T> type, Object id, Object target, BiConsumer<T, Object> toTarget, boolean call) {
        T found = id == null ? null : em().find(type, id);
        if (found == null) {
            return fail(call, "データなし", "データがありません。");
        }
        toTarget.accept(found, target);
        return true;
    }

    /**
     * For Each 表 Into &変数：変数に入れた行を DB（EntityManager）から切り離す。
     * 変数の値を変えても、Update / Save しない限り DB は変わらない（Load で読み込んだ変数と同じ）。
     */
    public static void detach(java.util.Collection<?> rows) {
        EntityManager em = em();
        java.util.Set<Object> done = java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());
        for (Object row : rows) {
            detach(em, row, done);
        }
    }

    // 行と、行に付けた参照の表の行（参照の参照も。同じ行は 1 回）を切り離す
    private static void detach(EntityManager em, Object row, java.util.Set<Object> done) {
        if (row == null || !done.add(row)) {
            return;
        }
        if (em.contains(row)) {
            em.detach(row);
        }
        java.util.Map<String, Object> refs = refsOf(row);
        if (refs != null) {
            for (Object ref : refs.values()) {
                detach(em, ref, done);
            }
        }
    }

    /**
     * For Each 表 Into &変数：行（最初の表）に参照の表（結合したほかの表）の行を付ける（name：別名、ない場合は表名）。
     * 外部結合で相手の行がない場合（ref が null）は付けない（&行.参照 は空の行）。
     */
    public static void attach(Object row, String name, Object ref) {
        java.util.Map<String, Object> refs = refsOf(row);
        if (refs == null) {
            return;
        }
        if (ref == null) {
            refs.remove(name);
        } else {
            refs.put(name, ref);
        }
    }

    /** &行.参照：行に付けた参照の表の行（付けていない場合・外部結合で相手の行がない場合は空の行。読み込みだけ）。 */
    public static <T> T ref(Object row, String name, Class<T> type) {
        java.util.Map<String, Object> refs = refsOf(row);
        Object value = refs == null ? null : refs.get(name);
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    // Entity の参照の表の行（getScriptRefs。Entity でない場合は null）
    @SuppressWarnings("unchecked")
    private static java.util.Map<String, Object> refsOf(Object row) {
        if (row == null) {
            return null;
        }
        try {
            Object refs = row.getClass().getMethod("getScriptRefs").invoke(row);
            return refs instanceof java.util.Map<?, ?> map ? (java.util.Map<String, Object>) map : null;
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    /** Exists：主キーの行があるか（変数には読み込まない）。 */
    public static <T> boolean exists(Class<T> type, Object id) {
        return id != null && em().find(type, id) != null;
    }

    /**
     * LoadForUpdate：行ロックを取って 1 件読み込み（Commit・Rollback まで他の人は更新できない）、変数（target）に写す。
     * 見つからない場合は false（call は データなし）。待ち時間（script.db.lock-timeout 秒）を過ぎたら例外（タイムアウト）。
     */
    public static <T> boolean loadForUpdate(Class<T> type, Object id, Object target, BiConsumer<T, Object> toTarget, boolean call) {
        EntityManager em = em();
        T found = id == null ? null : em.find(type, id);
        if (found == null) {
            return fail(call, "データなし", "データがありません。");
        }
        em.refresh(found, LockModeType.PESSIMISTIC_WRITE, lockHints(type));
        toTarget.accept(found, target);
        return true;
    }

    // 行ロックの待ち時間（ミリ秒。表ごとの設定があればそれ）
    private static Map<String, Object> lockHints(Class<?> type) {
        String seconds = property("script.db.lock-timeout." + type.getSimpleName(), property("script.db.lock-timeout", "10"));
        return Map.of("jakarta.persistence.lock.timeout", (long) (Double.parseDouble(seconds) * 1000));
    }

    /** Insert：変数（target）から 1 行を登録する。主キーが重複する場合は false（call は 重複エラー）。 */
    public static <T> boolean insert(Class<T> type, Object target, Function<Object, Object> keyOf, BiConsumer<T, Object> fromTarget, boolean call) {
        T entity = newEntity(type);
        fromTarget.accept(entity, target);
        return insertNew(type, entity, keyOf.apply(target), call);
    }

    /**
     * New 表 ～ End New・Insert：1 行を登録する。主キーが重複する場合は false（call は 重複エラー）。
     * 重複は登録の前に主キーで確かめる（SQL のエラーにしないため、重複の後もトランザクションを続けて Commit できる）。
     */
    public static <T> boolean insertNew(Class<T> type, T entity, Object id, boolean call) {
        EntityManager em = em();
        if (id != null && em.find(type, id) != null) {
            return fail(call, "重複エラー", "同じデータが既にあります。");
        }
        em.persist(entity);
        em.flush();
        return true;
    }

    /** Update：主キーの行を変数（target）の値で更新する。行がない場合は false（call は 排他エラー）。 */
    public static <T> boolean update(Class<T> type, Object target, Function<Object, Object> keyOf, BiConsumer<T, Object> fromTarget, boolean call) {
        Object id = keyOf.apply(target);
        EntityManager em = em();
        T found = id == null ? null : em.find(type, id);
        if (found == null) {
            return fail(call, "排他エラー", "他の人が更新または削除しました。");
        }
        fromTarget.accept(found, target);
        em.flush();
        return true;
    }

    /** Delete：主キーの行を削除する。行がない場合は false（call は 排他エラー）。 */
    public static <T> boolean delete(Class<T> type, Object target, Function<Object, Object> keyOf, boolean call) {
        Object id = keyOf.apply(target);
        EntityManager em = em();
        T found = id == null ? null : em.find(type, id);
        if (found == null) {
            return fail(call, "排他エラー", "他の人が更新または削除しました。");
        }
        em.remove(found);
        em.flush();
        return true;
    }

    /** Save：主キーの行があれば更新、なければ登録する。 */
    public static <T> boolean save(Class<T> type, Object target, Function<Object, Object> keyOf, BiConsumer<T, Object> fromTarget, boolean call) {
        Object id = keyOf.apply(target);
        T found = id == null ? null : em().find(type, id);
        return found != null ? update(type, target, keyOf, fromTarget, call) : insert(type, target, keyOf, fromTarget, call);
    }

    // ===== OptimisticCheck・OptimisticOf（楽観排他） =====

    /**
     * OptimisticCheck(&amp;情報[, ロック])：構造体 Optimistic の表名（TableName）・主キーの列と値（KeyColumn1～10・KeyValue1～10）で行を読み、
     * 行があり、更新日時の列（UpdateColumn）の今の値が読んだときの更新日時（ReadDate）と同じなら true（保存してよい）。
     * 更新日時はミリ秒まで比べる（列が DateTime(6) などでも同じ。画面の日時の入力はミリ秒までのため。CurrentDate・OptimisticOf の ReadDate もミリ秒まで）。
     * 行がない・更新日時が違う場合は false。情報の CurrentDate に今の更新日時（行がない場合は null）、更新者の列（UpdateUserColumn）があれば
     * CurrentUser に今の更新者を入れる。表・列は Entity の @Table・@Column にあるものだけ使える（SQL に書く名前はそこから取る）。主キーの値は列の型に変換する。
     * lock：行ロックを取って読む（LoadForUpdate と同じ。Commit・Rollback まで他の人は更新できない。待ち時間は script.db.lock-timeout。主キーの列はすべて指定する）。
     */
    public static boolean optimisticCheck(Object info, boolean lock) {
        if (info == null) {
            throw new DbException("その他のエラー", "OptimisticCheck の情報（Optimistic）が空です。");
        }
        String table = infoText(info, "TableName");
        Class<?> entity = null;
        for (var type : em().getMetamodel().getEntities()) {
            jakarta.persistence.Table annotation = type.getJavaType().getAnnotation(jakarta.persistence.Table.class);
            if (annotation != null && annotation.name().equalsIgnoreCase(table)) {
                entity = type.getJavaType();
            }
        }
        if (entity == null) {
            throw new DbException("その他のエラー", "OptimisticCheck：表「" + table + "」がありません（TableName は表の物理名）。");
        }

        Map<String, java.lang.reflect.Field> columns = entityColumns(entity);
        String update = optimisticColumn(columns, infoText(info, "UpdateColumn"), table, "UpdateColumn");
        String userText = infoText(info, "UpdateUserColumn");
        String user = userText.isEmpty() ? null : optimisticColumn(columns, userText, table, "UpdateUserColumn");

        // 主キーの列（@Column の名前）→ 値（列の型）
        Map<String, Object> keys = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            String key = infoText(info, "KeyColumn" + i);
            if (key.isEmpty()) {
                continue;
            }
            String name = optimisticColumn(columns, key, table, "KeyColumn" + i);
            keys.put(name, keyValue(infoText(info, "KeyValue" + i), columns.get(upper(name)).getType()));
        }
        if (keys.isEmpty()) {
            throw new DbException("その他のエラー", "OptimisticCheck：主キーの列（KeyColumn1～10）がありません。");
        }

        java.sql.Timestamp current = null;
        String currentUser = "";
        if (lock) {
            Object found = findForUpdate(entity, columns, keys, table);
            if (found != null) {
                current = millisOf(fieldValue(columns.get(upper(update)), found));
                if (user != null) {
                    currentUser = ScriptLib.nzChar(ScriptLib.str(fieldValue(columns.get(upper(user)), found)));
                }
            }
        } else {
            StringBuilder sql = new StringBuilder("SELECT ").append(update);
            if (user != null) {
                sql.append(", ").append(user);
            }
            sql.append(" FROM ").append(entity.getAnnotation(jakarta.persistence.Table.class).name()).append(" WHERE ");
            List<Object> params = new ArrayList<>();
            for (var key : keys.entrySet()) {
                sql.append(params.isEmpty() ? "" : " AND ").append(key.getKey()).append(" = ?").append(params.size() + 1);
                params.add(key.getValue());
            }
            Query query = em().createNativeQuery(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i + 1, params.get(i));
            }
            long start = System.nanoTime();
            List<?> rows = query.getResultList();
            if (Boolean.parseBoolean(property("script.sql.log", "false"))) {
                SQL_LOG.info("{} {} → {} 件（{} ms）", sql, params, rows.size(), (System.nanoTime() - start) / 1_000_000);
            }
            if (!rows.isEmpty()) {
                Object row = rows.get(0);
                current = millisOf(user == null ? row : ((Object[]) row)[0]);
                if (user != null) {
                    currentUser = ScriptLib.nzChar(ScriptLib.str(((Object[]) row)[1]));
                }
            }
        }
        infoSet(info, "CurrentDate", current);
        infoSet(info, "CurrentUser", currentUser);
        java.sql.Timestamp read = millisOf(infoValue(info, "ReadDate"));
        return current != null && current.equals(read);
    }

    /**
     * OptimisticOf(&amp;表の変数)：楽観排他の情報（info：構造体 Optimistic の新しいもの）に、表名・主キーの列と値・更新日時の列・更新者の列・
     * 読んだときの更新日時（ReadDate）を入れて返す。列はコンパイル時に表定義（列の用途）から決めたもの、値は変数の今の値（keyValues・readDate）。
     */
    public static <T> T optimisticOf(T info, String table, String[] keys, Object[] keyValues, String update, String user, Object readDate) {
        infoSet(info, "TableName", table);
        for (int i = 0; i < keys.length; i++) {
            infoSet(info, "KeyColumn" + (i + 1), keys[i]);
            infoSet(info, "KeyValue" + (i + 1), ScriptLib.nzChar(ScriptLib.str(keyValues[i])));
        }
        infoSet(info, "UpdateColumn", update);
        infoSet(info, "UpdateUserColumn", user == null ? "" : user);
        infoSet(info, "ReadDate", millisOf(readDate));
        return info;
    }

    // 主キーで 1 行を行ロックを取って読む（LoadForUpdate と同じ）。ない場合は null。主キーの列はすべて指定する
    private static Object findForUpdate(Class<?> entity, Map<String, java.lang.reflect.Field> columns, Map<String, Object> keys, String table) {
        List<java.lang.reflect.Field> ids = new ArrayList<>();
        for (java.lang.reflect.Field field : columns.values()) {
            if (field.isAnnotationPresent(jakarta.persistence.Id.class)) {
                ids.add(field);
            }
        }
        for (java.lang.reflect.Field field : ids) {
            String name = field.getAnnotation(jakarta.persistence.Column.class).name();
            if (!keys.containsKey(name)) {
                throw new DbException("その他のエラー", "OptimisticCheck：行ロックを取る場合は表「" + table + "」の主キーの列をすべて指定してください（"
                        + name + " がありません。ロックしない場合は OptimisticCheck(&情報, False)）。");
            }
        }
        if (keys.size() != ids.size()) {
            throw new DbException("その他のエラー", "OptimisticCheck：行ロックを取る場合は KeyColumn1～10 に表「" + table + "」の主キーの列だけを指定してください。");
        }

        Object id;
        jakarta.persistence.IdClass idClass = entity.getAnnotation(jakarta.persistence.IdClass.class);
        try {
            if (idClass == null) {
                id = keys.get(ids.get(0).getAnnotation(jakarta.persistence.Column.class).name());
            } else {
                id = idClass.value().getDeclaredConstructor().newInstance();
                for (java.lang.reflect.Field field : ids) {
                    java.lang.reflect.Field key = idClass.value().getDeclaredField(field.getName());
                    key.setAccessible(true);
                    key.set(id, keys.get(field.getAnnotation(jakarta.persistence.Column.class).name()));
                }
            }
        } catch (ReflectiveOperationException | IllegalArgumentException e) {
            throw new DbException("その他のエラー", "OptimisticCheck：表「" + table + "」の主キーを作れません（" + e + "）。");
        }

        EntityManager em = em();
        Object found = em.find(entity, id);
        if (found == null) {
            return null;
        }
        em.refresh(found, LockModeType.PESSIMISTIC_WRITE, lockHints(entity));
        return found;
    }

    // 列の名前（大文字）→ フィールド（@Column）
    private static Map<String, java.lang.reflect.Field> entityColumns(Class<?> entity) {
        Map<String, java.lang.reflect.Field> columns = new LinkedHashMap<>();
        for (Class<?> c = entity; c != null && c != Object.class; c = c.getSuperclass()) {
            for (java.lang.reflect.Field field : c.getDeclaredFields()) {
                jakarta.persistence.Column column = field.getAnnotation(jakarta.persistence.Column.class);
                if (column != null) {
                    columns.putIfAbsent(upper(column.name()), field);
                }
            }
        }
        return columns;
    }

    private static String upper(String name) {
        return name.toUpperCase(java.util.Locale.ROOT);
    }

    // Entity のフィールドの値
    private static Object fieldValue(java.lang.reflect.Field field, Object row) {
        try {
            field.setAccessible(true);
            return field.get(row);
        } catch (ReflectiveOperationException | RuntimeException e) {
            throw new DbException("その他のエラー", "楽観排他：列「" + field.getName() + "」の値を読めません（" + e + "）。");
        }
    }

    // 構造体の項目の値（getXxx()。ない場合は null）
    private static Object infoValue(Object info, String name) {
        try {
            return info.getClass().getMethod("get" + name).invoke(info);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    // 構造体の項目の値（getXxx()。文字にする。ない場合は ""）
    private static String infoText(Object info, String name) {
        try {
            return ScriptLib.str(info.getClass().getMethod("get" + name).invoke(info)).trim();
        } catch (ReflectiveOperationException e) {
            return "";
        }
    }

    // 構造体の項目に入れる（setXxx(値)。ない場合は何もしない）
    private static void infoSet(Object info, String name, Object value) {
        for (var method : info.getClass().getMethods()) {
            if (method.getName().equals("set" + name) && method.getParameterCount() == 1) {
                try {
                    method.invoke(info, value);
                } catch (ReflectiveOperationException | IllegalArgumentException e) {
                    // 型が合わない：入れない
                }
                return;
            }
        }
    }

    // Entity にある列の名前（@Column の名前のとおり）。ない場合は例外
    private static String optimisticColumn(Map<String, java.lang.reflect.Field> columns, String name, String table, String item) {
        java.lang.reflect.Field field = name.isEmpty() ? null : columns.get(upper(name));
        if (field == null) {
            throw new DbException("その他のエラー", "OptimisticCheck：表「" + table + "」に列「" + name + "」がありません（" + item + "）。");
        }
        return field.getAnnotation(jakarta.persistence.Column.class).name();
    }

    // 主キーの値（文字）を列の型にする
    private static Object keyValue(String value, Class<?> type) {
        if (type == Integer.class || type == int.class) {
            return ScriptLib.toInteger(value);
        }
        if (type == Long.class || type == long.class) {
            return ScriptLib.toLong(value);
        }
        if (type == java.math.BigDecimal.class) {
            return ScriptLib.dec(value);
        }
        if (type == Double.class || type == double.class) {
            return ScriptLib.toDouble(value);
        }
        if (type == java.time.LocalDate.class) {
            return ScriptLib.toDate(value);
        }
        if (type == java.time.LocalDateTime.class) {
            return ScriptLib.toDateTime(value);
        }
        if (type == java.sql.Timestamp.class) {
            return ScriptLib.toTimestamp(value);
        }
        return value;
    }

    // 楽観排他の更新日時：ミリ秒までにする（列が DateTime(6) などでも、画面の日時の入力はミリ秒までのため。構造体 Optimistic は DateTime(3)）
    private static java.sql.Timestamp millisOf(Object value) {
        return ScriptLib.truncateTime(timestampOf(value), 3);
    }

    // DB の日時（JDBC の型はドライバーによって違う）→ Timestamp
    private static java.sql.Timestamp timestampOf(Object value) {
        if (value instanceof java.time.OffsetDateTime o) {
            return java.sql.Timestamp.valueOf(o.toLocalDateTime());
        }
        if (value instanceof java.util.Date d && !(value instanceof java.sql.Timestamp)) {
            return new java.sql.Timestamp(d.getTime());
        }
        return ScriptLib.toTimestamp(value);
    }

    // ===== For Each 表・DataProvider の検索 =====

    /** 検索の結果：MaxRows までの行（表ごとの Entity、または Group By の値の並び）と、MaxRows を超える行があったか。 */
    public static final class Rows {
        private final List<Object[]> list;
        private final boolean over;

        Rows(List<Object[]> list, boolean over) {
            this.list = list;
            this.over = over;
        }

        /** 行（MaxRows まで）。 */
        public List<Object[]> list() {
            return list;
        }

        /** 1 件もない（When None）。 */
        public boolean none() {
            return list.isEmpty();
        }

        /** MaxRows を超える行があった（When Over）。 */
        public boolean over() {
            return over;
        }
    }

    /**
     * JPQL で検索する：SELECT select FROM from WHERE where（AND でつなぐ）GROUP BY groupBy ORDER BY orderBy。
     * 値はすべてパラメーター（params）で渡す。maxRows を超える行があれば over（maxRows が 0 以下は上限なし）。
     */
    public static Rows query(String select, String from, List<String> where, String groupBy, String orderBy, Map<String, Object> params, int maxRows) {
        StringBuilder jpql = new StringBuilder("SELECT ").append(select).append(" FROM ").append(from);
        if (!where.isEmpty()) {
            jpql.append(" WHERE ").append(String.join(" AND ", where));
        }
        if (groupBy != null && !groupBy.isEmpty()) {
            jpql.append(" GROUP BY ").append(groupBy);
        }
        if (orderBy != null && !orderBy.isEmpty()) {
            jpql.append(" ORDER BY ").append(orderBy);
        }
        long start = System.nanoTime();
        Query query = em().createQuery(jpql.toString());
        params.forEach(query::setParameter);
        if (maxRows > 0) {
            query.setMaxResults(maxRows + 1);
        }
        List<Object[]> rows = new ArrayList<>();
        for (Object row : query.getResultList()) {
            rows.add(row instanceof Object[] values ? values : new Object[] { row });
        }
        log(jpql.toString(), params, rows.size(), start);
        boolean over = maxRows > 0 && rows.size() > maxRows;
        return new Rows(over ? new ArrayList<>(rows.subList(0, maxRows)) : rows, over);
    }

    // 実行した検索・件数・時間（script.sql.log が true の場合）
    private static void log(String jpql, Map<String, Object> params, int count, long start) {
        if (Boolean.parseBoolean(property("script.sql.log", "false"))) {
            SQL_LOG.info("{} {} → {} 件（{} ms）", jpql, params, count, (System.nanoTime() - start) / 1_000_000);
        }
    }

    /**
     * For Each の一括の更新・削除（中が列への代入だけ・Delete だけの場合）：UPDATE / DELETE の 1 文で実行し、件数を返す。
     * set は「別名.属性 = 値」の並び。
     */
    public static int bulk(String statement, List<String> where, Map<String, Object> params) {
        StringBuilder jpql = new StringBuilder(statement);
        if (!where.isEmpty()) {
            jpql.append(" WHERE ").append(String.join(" AND ", where));
        }
        long start = System.nanoTime();
        EntityManager em = em();
        em.flush();
        Query query = em.createQuery(jpql.toString());
        params.forEach(query::setParameter);
        int count = query.executeUpdate();
        // 一括の更新は管理中の Entity に反映されないため、読み込み済みの Entity は読み直す
        em.clear();
        log(jpql.toString(), params, count, start);
        return count;
    }

    /** In &amp;配列 の条件：配列が空の場合は該当なし（Not In は全件。IN () は SQL のエラーになるため）。 */
    public static String in(String column, String name, Collection<?> values, Map<String, Object> params, boolean not) {
        if (values == null || values.isEmpty()) {
            return not ? "1 = 1" : "1 = 0";
        }
        params.put(name, values);
        return column + (not ? " NOT IN (:" : " IN (:") + name + ")";
    }

    /** 入れ子の検索の結果を親の行のキーごとに分ける（子の行の keyIndex 番目の値がキー）。 */
    public static Map<Object, List<Object[]>> groupRows(Rows rows, int keyIndex) {
        Map<Object, List<Object[]>> result = new LinkedHashMap<>();
        for (Object[] row : rows.list()) {
            result.computeIfAbsent(row[keyIndex], k -> new ArrayList<>()).add(row);
        }
        return result;
    }

    /** For Each の中の Delete：今の行を削除する。 */
    public static void remove(Object entity) {
        EntityManager em = em();
        em.remove(entity);
        em.flush();
    }

    /** For Each の中の更新（今の行の列への代入）を DB に書き込む。 */
    public static void flush() {
        em().flush();
    }
}
