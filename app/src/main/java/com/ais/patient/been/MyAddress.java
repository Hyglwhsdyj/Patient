package com.ais.patient.been;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/10 0010.
 */

public class MyAddress implements Serializable {

    /**
     * id : 5b1cae00159ca91fd009bcd3
     * userId : 5affcb20159ca937b81a39ff
     * name : 醉美人
     * phoneNumber : 13560181591
     * provinceName : 广西壮族自治区
     * cityName : 柳州市
     * countyName : 鱼峰区
     * townsName : 柳石街道
     * address : 柳石路XX号
     * zipcode : 545005
     * createTime : 2018-06-10T04:50:08.774+0000
     * updateTime : 2018-06-10T04:50:08.774+0000
     */

    private String id;
    private String userId;
    private String name;
    private String phoneNumber;
    private String provinceName;
    private String cityName;
    private String countyName;
    private String townsName;
    private String address;
    private String zipcode;
    private String createTime;
    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getTownsName() {
        return townsName;
    }

    public void setTownsName(String townsName) {
        this.townsName = townsName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
