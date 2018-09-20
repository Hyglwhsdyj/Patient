package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9 0009.
 */

public class SymptomReback {

    private List<String> recessiveDiseases;
    private List<RecessiveDoctorsBean> recessiveDoctors;
    private List<String> dominanceDiseases;
    private List<DominanceDoctorsBean> dominanceDoctors;
    private List<String> diseases;
    private List<DoctorsBean> doctors;

    public List<String> getRecessiveDiseases() {
        return recessiveDiseases;
    }

    public void setRecessiveDiseases(List<String> recessiveDiseases) {
        this.recessiveDiseases = recessiveDiseases;
    }

    public List<RecessiveDoctorsBean> getRecessiveDoctors() {
        return recessiveDoctors;
    }

    public void setRecessiveDoctors(List<RecessiveDoctorsBean> recessiveDoctors) {
        this.recessiveDoctors = recessiveDoctors;
    }

    public List<String> getDominanceDiseases() {
        return dominanceDiseases;
    }

    public void setDominanceDiseases(List<String> dominanceDiseases) {
        this.dominanceDiseases = dominanceDiseases;
    }

    public List<DominanceDoctorsBean> getDominanceDoctors() {
        return dominanceDoctors;
    }

    public void setDominanceDoctors(List<DominanceDoctorsBean> dominanceDoctors) {
        this.dominanceDoctors = dominanceDoctors;
    }

    public List<String> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<String> diseases) {
        this.diseases = diseases;
    }

    public List<DoctorsBean> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorsBean> doctors) {
        this.doctors = doctors;
    }

    public static class RecessiveDoctorsBean {
        /**
         * doctorId : 5b8a02412f396e534f8637c7
         * image : http://pic.5aszy.com/group1/M00/00/0F/rBImA1uKB76ALicEAAToOltBfMk685.jpg
         * name : Test黄
         * medicalInstitutions : 广东公安边防总队医院宝安分院
         * titles : 主治医师
         * depart : 心血管科
         * diseaseExpertise : ["支气管炎","感冒","上呼吸道感染"]
         * fee : 70
         */

        private String doctorId;
        private String image;
        private String name;
        private String medicalInstitutions;
        private String titles;
        private String depart;
        private int fee;
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

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public List<String> getDiseaseExpertise() {
            return diseaseExpertise;
        }

        public void setDiseaseExpertise(List<String> diseaseExpertise) {
            this.diseaseExpertise = diseaseExpertise;
        }
    }

    public static class DominanceDoctorsBean {
        /**
         * doctorId : 5b8a02412f396e534f8637c7
         * image : http://pic.5aszy.com/group1/M00/00/0F/rBImA1uKB76ALicEAAToOltBfMk685.jpg
         * name : Test黄
         * medicalInstitutions : 广东公安边防总队医院宝安分院
         * titles : 主治医师
         * depart : 心血管科
         * diseaseExpertise : ["支气管炎","感冒","上呼吸道感染"]
         * fee : 70
         */

        private String doctorId;
        private String image;
        private String name;
        private String medicalInstitutions;
        private String titles;
        private String depart;
        private int fee;
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

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public List<String> getDiseaseExpertise() {
            return diseaseExpertise;
        }

        public void setDiseaseExpertise(List<String> diseaseExpertise) {
            this.diseaseExpertise = diseaseExpertise;
        }
    }

    public static class DoctorsBean {
        /**
         * doctorId : 5b8a02412f396e534f8637c7
         * image : http://pic.5aszy.com/group1/M00/00/0F/rBImA1uKB76ALicEAAToOltBfMk685.jpg
         * name : Test黄
         * medicalInstitutions : 广东公安边防总队医院宝安分院
         * titles : 主治医师
         * depart : 心血管科
         * diseaseExpertise : ["支气管炎","感冒","上呼吸道感染"]
         * fee : 70
         */

        private String doctorId;
        private String image;
        private String name;
        private String medicalInstitutions;
        private String titles;
        private String depart;
        private int fee;
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

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public List<String> getDiseaseExpertise() {
            return diseaseExpertise;
        }

        public void setDiseaseExpertise(List<String> diseaseExpertise) {
            this.diseaseExpertise = diseaseExpertise;
        }
    }
}
