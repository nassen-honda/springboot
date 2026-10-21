package com.nassen.cem.repository;

import com.nassen.cem.entity.Mst00005;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 部署マスタ（MST00005）の Repository（自動生成 / auto-generated）。
 *
 * <p>表定義（Data/tables.json）から生成されました。検索画面の検索（検索条件の項目）は JpaSpecificationExecutor を使います。手動編集は再生成で失われます。</p>
 */
@Repository
public interface Mst00005Repository extends JpaRepository<Mst00005, String>, JpaSpecificationExecutor<Mst00005> {
}
