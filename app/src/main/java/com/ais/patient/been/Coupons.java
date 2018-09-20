package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/3 0003.
 */

public class Coupons {

    /**
     * couponId : 5b03d5f5689b921a5860e092
     * type : 图文问诊
     * name : 优惠券标题
     * money : 100
     * minMoney : 500
     * startTime : 2018-05-22 00:00:00
     * endTime : 2018-06-07 23:59:59
     */

    private String couponId;
    private String type;
    private String name;
    private double money;
    private double minMoney;
    private String startTime;
    private String endTime;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(double minMoney) {
        this.minMoney = minMoney;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
