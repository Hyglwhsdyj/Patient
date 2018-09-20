package com.ais.patient.been;

/**
 * Created by Administrator on 2018/9/18 0018.
 */

public class MyPatient {

    /**
     * patientId : 5b8b5ef02f396e7ea357b5fb
     * sex : 女
     * name : QQ
     * age : 29岁
     */

    private String patientId;
    private String sex;
    private String name;
    private String age;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
