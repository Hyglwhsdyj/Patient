package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/8 0008.
 */

public class ImInfo {

    /**
     * business_status : 0
     * im_accid : 2eeae5c421cb41baa3b6dc5d0cb14f96
     * im_doctor_id : 5af3f410c319b352dc4d40a7
     * im_doctor_accid : 3e828be72aee4e9ab759b4fcacbc3cd3
     * im_token : 180ff4b4b2470b2b2ec860e3f520a1d6
     */

    private int business_status;
    private String im_accid;
    private String im_doctor_id;
    private String im_doctor_accid;
    private String im_token;

    public int getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(int business_status) {
        this.business_status = business_status;
    }

    public String getIm_accid() {
        return im_accid;
    }

    public void setIm_accid(String im_accid) {
        this.im_accid = im_accid;
    }

    public String getIm_doctor_id() {
        return im_doctor_id;
    }

    public void setIm_doctor_id(String im_doctor_id) {
        this.im_doctor_id = im_doctor_id;
    }

    public String getIm_doctor_accid() {
        return im_doctor_accid;
    }

    public void setIm_doctor_accid(String im_doctor_accid) {
        this.im_doctor_accid = im_doctor_accid;
    }

    public String getIm_token() {
        return im_token;
    }

    public void setIm_token(String im_token) {
        this.im_token = im_token;
    }
}
