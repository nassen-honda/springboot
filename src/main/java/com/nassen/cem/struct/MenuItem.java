package com.nassen.cem.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * メニューの項目（MenuItem）の構造体（自動生成 / auto-generated）。
 *
 * <p>構造体ファイル（*.struct.json）から生成されました。手動編集は再生成で失われます。</p>
 */
public class MenuItem {

    /** 画面ID（Key、VARCHAR(255)） */
    private String key = "";

    /** 名前（Caption、VARCHAR(255)） */
    private String caption = "";

    /** URL（Url、VARCHAR(255)） */
    private String url = "";

    /** アイコン（Icon、VARCHAR(255)） */
    private String icon = "";

    /** グループの中の項目（Children、構造体、配列） */
    private List<MenuItem> children;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? "" : key;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption == null ? "" : caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? "" : url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? "" : icon;
    }

    public List<MenuItem> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }
}
