package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/10 0010.
 */

public class Appraise {

    /**
     * headImage : http://pic.5iyzy.com/group1/M00/00/A4/wKgByFsDesiEMltKAAAAANoOews287.jpg
     * doctorName : 斩三
     * titles : 主任医师
     * time : 2018年5月22 08:00-11:00
     * serviceName : 在线问诊服务
     * appraiseStatus : 0
     * orderId : 20180522152697363732664231
     */

    private String headImage;
    private String doctorName;
    private String titles;
    private String time;
    private String serviceName;
    private int appraiseStatus;
    private String orderId;

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getAppraiseStatus() {
        return appraiseStatus;
    }

    public void setAppraiseStatus(int appraiseStatus) {
        this.appraiseStatus = appraiseStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
