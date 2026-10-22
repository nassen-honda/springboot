package com.nassen.cem.repository;

import com.nassen.cem.entity.Tas00002;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 見積明細テーブル（TAS00002）の Repository（自動生成 / auto-generated）。
 *
 * <p>表定義（Data/tables.json）から生成されました。検索画面の検索（検索条件の項目）は JpaSpecificationExecutor を使います。手動編集は再生成で失われます。</p>
 */
@Repository
public interface Tas00002Repository extends JpaRepository<Tas00002, Tas00002.PK>, JpaSpecificationExecutor<Tas00002> {
}
