package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/7 0007.
 */

public class ChatOnLineList {

    /**
     * image : http://www.hk515.com/static/images/doctor.png
     * name : 斩三
     * fee : 1.0
     * time : 2018-06-16 11:54:11
     * explainState : 1
     * serviceTime : 6月17日 12:00-18:00
     * lastReply : 医生最后回复内容
     * status : WAIT_BUYER_PAY
     */
    private String recordId;
    private String doctorId;
    private String image;
    private String name;
    private double fee;
    private String time;
    private String explainState;
    private String serviceTime;
    private String lastReply;
    private String status;
    private String titles;
    private String depart;


    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExplainState() {
        return explainState;
    }

    public void setExplainState(String explainState) {
        this.explainState = explainState;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getLastReply() {
        return lastReply;
    }

    public void setLastReply(String lastReply) {
        this.lastReply = lastReply;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }
}
