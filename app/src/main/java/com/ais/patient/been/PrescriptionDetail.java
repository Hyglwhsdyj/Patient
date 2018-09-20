package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/4 0004.
 */

public class PrescriptionDetail {

    /**
     * title : 秘方标题
     * function : 功能主治
     * introduce : 秘方介绍
     * doctor_introduce  : 医生介绍
     *  cases  : 康复病例
     *  doctorId  : xxxxxxxx
     */

    private String title;
    private String function;
    private String introduce;
    private String doctor_introduce;
    private String cases;
    private String doctorId;

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

    public String getDoctor_introduce() {
        return doctor_introduce;
    }

    public void setDoctor_introduce(String doctor_introduce) {
        this.doctor_introduce = doctor_introduce;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
