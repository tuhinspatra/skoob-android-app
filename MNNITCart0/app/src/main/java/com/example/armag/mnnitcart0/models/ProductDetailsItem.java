package com.example.armag.mnnitcart0.models;

import java.util.List;

/**
 * Created by armag on 12/10/17.
 */

public class ProductDetailsItem {

    private String copiesSold;
    private String description;
    private String iid;
    private String img1;
    private String img2;
    private String img3;
    private String noOfPages;
    private String publisher;
    private String sellerUid;


    public ProductDetailsItem(String copiesSold, String description, String iid, String img1, String img2, String img3, String noOfPages, String publisher, String sellerUid) {
        this.copiesSold = copiesSold;
        this.description = description;
        this.iid = iid;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.noOfPages = noOfPages;
        this.publisher = publisher;
        this.sellerUid = sellerUid;
    }

    public String getCopiesSold() {
        return copiesSold;

    }

    public void setCopiesSold(String copiesSold) {
        this.copiesSold = copiesSold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(String noOfPages) {
        this.noOfPages = noOfPages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSellerUid() {
        return sellerUid;
    }

    public void setSellerUid(String sellerUid) {
        this.sellerUid = sellerUid;
    }

    public ProductDetailsItem() {

    }
}
