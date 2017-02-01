package com.test.prototype_s.model;

/**
 * Model for a fashion magazine Post's attributes
 */
public class Post extends Item {

//    String descr;

    public Post(String title, /*String descr,*/ String imgUrl) {
        super(title, imgUrl);
//        this.descr = descr;
    }

//    public String getDescr() {
//        return descr;
//    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }
}
