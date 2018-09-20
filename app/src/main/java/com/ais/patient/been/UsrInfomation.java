package com.ais.patient.been;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/10 0010.
 */

public class UsrInfomation implements Serializable{

    /**
     * headImage : http://www.baidu.com
     * name : 用户
     * phoneNumber : 1760xxxxxxx
     * sex : 男
     * age : 0
     */

    private String headImage;
    private String name;
    private String phoneNumber;
    private String sex;
    private int age;

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
