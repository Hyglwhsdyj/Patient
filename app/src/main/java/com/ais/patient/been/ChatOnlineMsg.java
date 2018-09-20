package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/3 0003.
 */

public class ChatOnlineMsg {

    /**
     * settingId : 5b531bfdd51f0806badfbecc
     * quota : 2288
     * fee : 32.0
     * pageTime : 07-26（周四） 12:00-16:45
     */

    private String settingId;
    private int quota;
    private double fee;
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

    public String getPageTime() {
        return pageTime;
    }

    public void setPageTime(String pageTime) {
        this.pageTime = pageTime;
    }

}
