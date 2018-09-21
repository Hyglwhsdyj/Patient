package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/9/20 0020.
 */

public class HealthTempDetail {

    /**
     * name : 李二
     * sex : 男
     * age : 15
     * disease : 妇科炎症
     * beforeInfo : 妇科炎症白带异常
     * beforeImage : []
     * afterInfo : 好了，吃饭也香了1111
     * afterImage : []
     * doctorId : 5ae96af8b3a1f130240462d9
     */

    private String name;
    private String sex;
    private String age;
    private String disease;
    private String beforeInfo;
    private String afterInfo;
    private String doctorId;
    private List<String> beforeImage;
    private List<String> afterImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getBeforeInfo() {
        return beforeInfo;
    }

    public void setBeforeInfo(String beforeInfo) {
        this.beforeInfo = beforeInfo;
    }

    public String getAfterInfo() {
        return afterInfo;
    }

    public void setAfterInfo(String afterInfo) {
        this.afterInfo = afterInfo;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public List<String> getBeforeImage() {
        return beforeImage;
    }

    public void setBeforeImage(List<String> beforeImage) {
        this.beforeImage = beforeImage;
    }

    public List<String> getAfterImage() {
        return afterImage;
    }

    public void setAfterImage(List<String> afterImage) {
        this.afterImage = afterImage;
    }
}
