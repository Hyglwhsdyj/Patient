package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/5 0005.
 */

public class MeetingMsg {

    /**
     * id : 5b2f66b7d4b0ad5f78b36fc2
     * pageTime : 06-24（周日）08:00-12:00
     * quota : 5
     * fee : 100
     * address : 深圳市宝安区盐田路4号A座533号-001
     */

    private String id;
    private String pageTime;
    private int quota;
    private String fee;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPageTime() {
        return pageTime;
    }

    public void setPageTime(String pageTime) {
        this.pageTime = pageTime;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
