package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/8/15 0015.
 */

public class DoctorNewDetial {
    /**
     * id : 5b76ddec2f396e03b373e958
     * type : 1
     * title : 周四额度拉拉队a
     * content : 下课哦饿死地理课德克士凯撒刷卡校门口都多大了打开扣扣jjhhhh
     * time : 2018-08-17
     * pics : []
     * doctor : {"doctorId":"5ae96af8b3a1f130240462d9","image":"http://pic.5aszy.com/group1/M00/00/09/rBImA1t7dG2Af_l7AACLPjW3lg8956.jpg","name":"黄医生","medicalInstitutions":"南昌市新华医院","titles":"主任医师","depart":"内分泌科","diseaseExpertise":["糖尿病","内分泌失调"],"fee":21,"isInquiry":true,"isAppointment":true}
     */

    private String id;
    private String type;
    private String title;
    private String content;
    private String time;
    private DoctorBean doctor;
    private List<String> pics;
    private String shareTitle;
    private String shareContent;
    private String shareImage;
    private String shareUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DoctorBean getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorBean doctor) {
        this.doctor = doctor;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public static class DoctorBean {
        /**
         * doctorId : 5ae96af8b3a1f130240462d9
         * image : http://pic.5aszy.com/group1/M00/00/09/rBImA1t7dG2Af_l7AACLPjW3lg8956.jpg
         * name : 黄医生
         * medicalInstitutions : 南昌市新华医院
         * titles : 主任医师
         * depart : 内分泌科
         * diseaseExpertise : ["糖尿病","内分泌失调"]
         * fee : 21
         * isInquiry : true
         * isAppointment : true
         */

        private String doctorId;
        private String image;
        private String name;
        private String medicalInstitutions;
        private String titles;
        private String depart;
        private double fee;
        private boolean isInquiry;
        private boolean isAppointment;
        private List<String> diseaseExpertise;

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMedicalInstitutions() {
            return medicalInstitutions;
        }

        public void setMedicalInstitutions(String medicalInstitutions) {
            this.medicalInstitutions = medicalInstitutions;
        }

        public String getTitles() {
            return titles;
        }

        public void setTitles(String titles) {
            this.titles = titles;
        }

        public String getDepart() {
            return depart;
        }

        public void setDepart(String depart) {
            this.depart = depart;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public boolean isInquiry() {
            return isInquiry;
        }

        public void setInquiry(boolean inquiry) {
            isInquiry = inquiry;
        }

        public boolean isAppointment() {
            return isAppointment;
        }

        public void setAppointment(boolean appointment) {
            isAppointment = appointment;
        }

        public boolean isIsInquiry() {
            return isInquiry;
        }

        public void setIsInquiry(boolean isInquiry) {
            this.isInquiry = isInquiry;
        }

        public boolean isIsAppointment() {
            return isAppointment;
        }

        public void setIsAppointment(boolean isAppointment) {
            this.isAppointment = isAppointment;
        }

        public List<String> getDiseaseExpertise() {
            return diseaseExpertise;
        }

        public void setDiseaseExpertise(List<String> diseaseExpertise) {
            this.diseaseExpertise = diseaseExpertise;
        }
    }

}
