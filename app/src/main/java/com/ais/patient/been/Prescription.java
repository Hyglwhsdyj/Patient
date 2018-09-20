package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/4 0004.
 */

/**
 * 秘方
 */

public class Prescription {

    /**
     * title : 秘方标题
     * function : 功能主治
     * introduce : 秘方介绍
     *  secret_id  : xxxxxxxxxxxx
     */

    private String title;
    private String function;
    private String introduce;
    private String secret_id;
    private String coverImage;

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getSecret_id() {
        return secret_id;
    }

    public void setSecret_id(String secret_id) {
        this.secret_id = secret_id;
    }
}
