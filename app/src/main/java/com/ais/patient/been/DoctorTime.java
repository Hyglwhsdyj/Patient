package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/3 0003.
 */

public class DoctorTime {

    /**
     * settingId : 5b23be1526c14c6cb070fe74
     * quota : 3
     * fee : 4.0
     * listTime : 2018-06-25 13:10-15:20
     * pageTime : 06-25（周一） 13:10-15:20
     */

    private String settingId;
    private int quota;
    private double fee;
    private String listTime;
    private String pageTime;

    public String getSettingId() {
        return settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
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
}
