package com.cqcye.simpledemo.bean;

import java.io.Serializable;

/**
 * Created by xupingwei on 2016/12/10.
 * 个人数据描述
 */

public class DescBean implements Serializable {
    private String title;
    private String description;
    private String imageHref;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }
}
