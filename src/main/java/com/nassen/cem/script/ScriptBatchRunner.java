package com.nassen.cem.script;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 単独で実行するモジュール（バッチ）の起動（自動生成 / auto-generated）。
 *
 * <p>java -jar アプリ.jar --batch=モジュール名 [--引数=値 …]：モジュールの runBatch() を実行し、終了コード（0：正常、1：エラー）でアプリを終える。
 * in の引数は同じ名前のオプションの値（文字・数値・Boolean・日付 yyyy-MM-dd・日時 yyyy-MM-ddTHH:mm:ss）。
 * Web サーバーを起動しない場合は --spring.main.web-application-type=none を付ける。--batch がない場合は何もしない（画面のアプリとして動く）。
 * 手動編集は再生成で失われます。</p>
 */
@Component
public class ScriptBatchRunner implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ScriptBatchRunner.class);

    private final ConfigurableApplicationContext context;

    public ScriptBatchRunner(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> names = args.getOptionValues("batch");
        if (names == null || names.isEmpty()) {
            return;
        }
        String name = names.get(0);
        String className = "com.nassen.cem.module." + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        int code;
        try {
            Object module = Class.forName(className).getDeclaredConstructor().newInstance();
            for (String option : args.getOptionNames()) {
                if (!option.equals("batch") && !option.startsWith("spring.") && !option.startsWith("server.")) {
                    set(module, option, args.getOptionValues(option));
                }
            }
            LOG.info("バッチ {} を開始します", name);
            code = (int) module.getClass().getMethod("runBatch").invoke(module);
            LOG.info("バッチ {} を終了しました（終了コード {}）", name, code);
        } catch (ReflectiveOperationException | IllegalArgumentException ex) {
            LOG.error("バッチ {} を実行できません（単独で実行するモジュールではない、または引数の値が正しくない）", name, ex);
            code = 2;
        }
        int exitCode = code;
        System.exit(SpringApplication.exit(context, () -> exitCode));
    }

    // in の引数（public フィールド）に値を設定する（型に合わせて変換。List は , 区切り・複数指定）
    private static void set(Object module, String name, List<String> values) throws ReflectiveOperationException {
        Field field;
        try {
            field = module.getClass().getField(name);
        } catch (NoSuchFieldException ex) {
            throw new IllegalArgumentException("引数 " + name + " はありません", ex);
        }
        String value = values == null || values.isEmpty() ? "" : values.get(values.size() - 1);
        Class<?> type = field.getType();
        Object converted;
        if (List.class.isAssignableFrom(type)) {
            converted = new java.util.ArrayList<>(values == null ? List.of() : values.stream()
                    .flatMap(v -> java.util.Arrays.stream(v.split(","))).map(String::trim).toList());
        } else if (type == String.class) {
            converted = value;
        } else if (type == Integer.class) {
            converted = Integer.valueOf(value);
        } else if (type == Long.class) {
            converted = Long.valueOf(value);
        } else if (type == BigDecimal.class) {
            converted = new BigDecimal(value);
        } else if (type == Double.class) {
            converted = Double.valueOf(value);
        } else if (type == Boolean.class) {
            converted = Boolean.valueOf(value);
        } else if (type == LocalDate.class) {
            converted = LocalDate.parse(value);
        } else if (type == LocalDateTime.class) {
            converted = LocalDateTime.parse(value);
        } else {
            throw new IllegalArgumentException("引数 " + name + " の型（" + type.getSimpleName() + "）は起動の引数で指定できません");
        }
        field.set(module, converted);
    }
}
