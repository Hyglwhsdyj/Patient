package com.ais.patient.been;

/**
 * Created by lyf on 2018/8/6.
 */

public class OrdonnanceListRespone {


    /**
     * id : 5b6ab3e82f396e4d407b8482
     * orderTime : 2018-08-08 17:12:08
     * doctorName : 小雨
     * patientName : 李大爷
     * invalidTime : 2018-08-09 17:12:08
     * fee : 31.49
     * status : 04
     * appraise : false
     * isOrder : false
     * orderId : 58631194350592000
     */

    private String id;
    private String decoct;
    private String orderTime;
    private String doctorName;
    private String patientName;
    private String invalidTime;
    private double fee;
    private String status;
    private boolean appraise;
    private boolean isOrder;
    private String orderId;

    public String getDecoct() {
        return decoct;
    }

    public void setDecoct(String decoct) {
        this.decoct = decoct;
    }

    public boolean isOrder() {
        return isOrder;
    }

    public void setOrder(boolean order) {
        isOrder = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(String invalidTime) {
        this.invalidTime = invalidTime;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAppraise() {
        return appraise;
    }

    public void setAppraise(boolean appraise) {
        this.appraise = appraise;
    }

    public boolean isIsOrder() {
        return isOrder;
    }

    public void setIsOrder(boolean isOrder) {
        this.isOrder = isOrder;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
