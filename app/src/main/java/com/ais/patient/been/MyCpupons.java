package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/13 0013.
 */

public class MyCpupons {

    /**
     * id : 7
     * couponId : 5b03d618689b921a5860e093
     * status : 1
     * type : 图文问诊
     * name : 优惠券1
     * money : 2000.0
     * minMoney : 8000.0
     * startTime : 2018-05-23 03:03:03
     * endTime : 2018-05-31 22:04:04
     */

    private int id;
    private String couponId;
    private String status;
    private String type;
    private String name;
    private double money;
    private double minMoney;
    private String startTime;
    private String endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
