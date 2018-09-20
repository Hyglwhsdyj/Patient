package com.ais.patient.been;

import java.io.Serializable;

/**
 * Created by lyf on 2018/8/9.
 */

public class MyAdressResponse implements Serializable{

    /**
     * addressId : 5b0a87fd059401617cffabe3
     * name : 柳柳州
     * phoneNumber : 13560181591
     * address : 广西壮族自治区柳州市鱼峰区柳石路XX号
     * preferred : Y
     */

    private String addressId;
    private String name;
    private String phoneNumber;
    private String address;
    private String preferred;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPreferred() {
        return preferred;
    }

    public void setPreferred(String preferred) {
        this.preferred = preferred;
    }
}
