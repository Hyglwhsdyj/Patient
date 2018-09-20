package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/5 0005.
 */

public class Reservation {

    /**
     * id : 2018052502152722898743622892
     * name : 李医生
     * time : 2018年5月5日 下午16:00-18:00
     * address : 深圳市宝安区盐田路4号A座533号-001
     * status : 1
     * countdown : 14:11
     */

    private String id;
    private String name;
    private String time;
    private String address;
    private int status;
    private String countdown;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }
}
