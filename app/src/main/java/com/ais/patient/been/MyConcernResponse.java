package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */

public class MyConcernResponse {


    /**
     * id : 5b15edf922ae0545e41a7da0
     * image : null
     * name : 老四
     * titles : 主治医师
     * medicalInstitutions : 深圳福华中西医结合医院
     * depart : 内分泌科
     * diseaseExpertise : ["糖尿病","内分泌失调"]
     * fee : 0
     */

    private String id;
    private String image;
    private String name;
    private String titles;
    private String medicalInstitutions;
    private String depart;
    private double fee;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getMedicalInstitutions() {
        return medicalInstitutions;
    }

    public void setMedicalInstitutions(String medicalInstitutions) {
        this.medicalInstitutions = medicalInstitutions;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public List<String> getDiseaseExpertise() {
        return diseaseExpertise;
    }

    public void setDiseaseExpertise(List<String> diseaseExpertise) {
        this.diseaseExpertise = diseaseExpertise;
    }
}
