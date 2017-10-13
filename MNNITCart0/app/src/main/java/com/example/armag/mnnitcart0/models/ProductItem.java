package com.example.armag.mnnitcart0.models;

/**
 * Created by armag on 6/10/17.
 */

public class ProductItem {

    private String cid;
    private String iid;
    private String isnew;
    private String msp;
    private String rating;
    private String thumbnail;
    private String title;

    public ProductItem() {
    }

    public ProductItem(String cid, String iid, String isnew, String msp, String rating, String thumbnail, String title) {
        this.cid = cid;
        this.iid = iid;
        this.isnew = isnew;
        this.msp = msp;
        this.rating = rating;
        this.thumbnail = thumbnail;
        this.title = title;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public String getMsp() {
        return msp;
    }

    public void setMsp(String msp) {
        this.msp = msp;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
