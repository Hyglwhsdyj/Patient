package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/5 0005.
 */

public class DoctorMeetingTime {

    /**
     * id : 5b10e300a1b8db26c88e73ae
     * listTime : 2018/05/10 08:00-12:00
     * pageTime : 05-10（周四）08:00-12:00
     * quota : 4
     * fee : 100
     * address : 深圳市宝安区盐田路4号A座533号-001
     */

    private String id;
    private String listTime;
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

    public String getListTime() {
        return listTime;
    }

    public void setListTime(String listTime) {
        this.listTime = listTime;
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
