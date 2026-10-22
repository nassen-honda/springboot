package com.nassen.cem.script;

import java.util.Locale;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.BasicTypeReference;
import org.hibernate.type.StandardBasicTypes;

/**
 * 登録関数（自動生成 / auto-generated）：プロジェクトの「SQL文関数定義」（function.json）の関数を Hibernate に登録する。
 *
 * <p>For Each 表・データプロバイダーの条件などで使う。DB の方言（Oracle・SQL Server・PostgreSQL・MySQL・H2）ごとの SQL のひな形を使い、
 * その DB のひな形がない場合は共通のひな形を使う。META-INF/services/org.hibernate.boot.model.FunctionContributor で読み込む。
 * 手動編集は再生成で失われます。</p>
 */
public class ScriptFunctions implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functions) {
        String db = functions.getDialect().getClass().getSimpleName().toLowerCase(Locale.ROOT);
    }

    // DB の種類（方言の名前に含まれる文字）ごとのひな形から選んで登録する（ない場合は共通のひな形。どちらもない場合は登録しない）
    private static void register(FunctionContributions functions, String db, String name, BasicTypeReference<?> type, String standard, String... byDb) {
        String pattern = standard;
        for (int i = 0; i + 1 < byDb.length; i += 2) {
            if (db.contains(byDb[i])) {
                pattern = byDb[i + 1];
            }
        }
        if (pattern != null) {
            functions.getFunctionRegistry().registerPattern(name, pattern,
                    functions.getTypeConfiguration().getBasicTypeRegistry().resolve(type));
        }
    }
}
