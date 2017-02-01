package com.test.prototype_s.model;

/**
 * base class for items like Product and Post
 */
public abstract class Item {

    String title;
    String imgUrl;

    protected Item(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }
}
