package com.example.armag.mnnitcart0.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by armag on 30/9/17.
 */
@IgnoreExtraProperties
public class CategoryItem {
    private String cid;

    private String title;
    private String url;

    public CategoryItem() {
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CategoryItem(String cid, String title, String url) {

        this.cid = cid;
        this.title = title;
        this.url = url;
    }
}
