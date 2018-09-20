package com.ais.patient.been;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/7 0007.
 */

public class PatientInfo implements Serializable{

    /**
     * phoneNumber : 17603056681
     * address : 广东省,广州市,天河区
     * sex : 男
     * name : 张三
     * weight : 75.0
     * id : 5af5548631425014b02cf382
     * medicalHistory : 高血压，高血脂
     * age : 30岁
     * remarks : 病例
     * height : 1.73
     */

    private String birthday;
    private String phoneNumber;
    private String address;
    private String sex;
    private String name;
    private double weight;
    private String id;
    private String medicalHistory;
    private String age;
    private String remarks;
    private double height;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
