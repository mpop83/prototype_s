package com.test.prototype_s.model;

/**
 * Model for a Product's attributes
 */
public class Product extends Item {

    String price;
    String currency;

    public Product(String title, String price, String currency, String imgUrl) {
        super(title, imgUrl);
        this.currency = currency;
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPrice() {
        return price;
    }

}
