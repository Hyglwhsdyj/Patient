package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/9/27 0027.
 */

public class PatientDoctor {

    /**
     * id : 5b6e8fc92f396e54a1ff03f2
     * image : http://dev.pic.5aszy.com/group1/M00/00/02/rBImA1tuomOAG9-zAABdaL4QQ-g829.jpg
     * name : 赖医生
     * medicalInstitutions : 中国工商银行广州市分行医务室
     * titles : 住院医师
     * depart : 中医美容
     * diseaseExpertise : ["内分泌失调","高血压病","脑出血"]
     * fee : 46
     * caseNum : 0
     * clinicalExperience : 0
     */

    private String id;
    private String image;
    private String name;
    private String medicalInstitutions;
    private String titles;
    private String depart;
    private double fee;
    private int caseNum;
    private int clinicalExperience;
    private List<String> diseaseExpertise;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(int caseNum) {
        this.caseNum = caseNum;
    }

    public int getClinicalExperience() {
        return clinicalExperience;
    }

    public void setClinicalExperience(int clinicalExperience) {
        this.clinicalExperience = clinicalExperience;
    }

    public List<String> getDiseaseExpertise() {
        return diseaseExpertise;
    }

    public void setDiseaseExpertise(List<String> diseaseExpertise) {
        this.diseaseExpertise = diseaseExpertise;
    }
}
