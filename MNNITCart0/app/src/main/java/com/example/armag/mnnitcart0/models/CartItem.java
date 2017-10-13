package com.example.armag.mnnitcart0.models;

/**
 * Created by armag on 12/10/17.
 */

public class CartItem {
    private String iid;
    private String quantity;

    public CartItem(String iid, String quantity) {
        this.iid = iid;
        this.quantity = quantity;
    }

    public String getIid() {

        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public CartItem() {

    }
}
