package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30 0030.
 */

public class MainDotcor {

    /**
     * code : 200
     * message : 成功
     * page : {"pageIndex":1,"pageSize":10,"pageCount":2,"total":11}
     * data : [{"id":"5af3f407c319b352dc4d40a5","image":null,"name":"赵四","medicalInstitutions":"广东公安边防总队医院宝安分院","titles":"主任医师","depart":"中医妇科","diseaseExpertise":["糖尿病"],"fee":0},{"id":"5af3f420c319b352dc4d40aa","image":null,"name":"刘酶","medicalInstitutions":"朝晖齿科","titles":"住院医师","depart":"中医男科","diseaseExpertise":["风湿性关节炎"],"fee":0},{"id":"5b1390ba22ae053a387730c4","image":null,"name":"qq","medicalInstitutions":"韶关市福康医院","titles":"主任医师","depart":"内分泌科","diseaseExpertise":["糖尿病","内分泌失调"],"fee":0},{"id":"5ae96af8b3a1f130240462d9","image":"http://pic.tunnel.qydev.com//group1/M00/00/A5/wKgByFst6GCEIwY5AAAAAIMVuUA540.jpg","name":"小雨","medicalInstitutions":"宝安人民医院","titles":"主任医师","depart":"内分泌科","diseaseExpertise":["糖尿病","内分泌失调"],"fee":30},{"id":"5af3f410c319b352dc4d40a7","image":"http://www.hk515.com/static/images/doctor.png","name":"李先生","medicalInstitutions":"深圳人民医院","titles":"主任医师","depart":"内分泌科","diseaseExpertise":["糖尿病"],"fee":1},{"id":"5b15edf922ae0545e41a7da0","image":null,"name":"老四","medicalInstitutions":"深圳福华中西医结合医院","titles":"主治医师","depart":"内分泌科","diseaseExpertise":["糖尿病","内分泌失调"],"fee":0},{"id":"5b07a68cd6666f0600bbdfb9","image":null,"name":"阿里郎","medicalInstitutions":"朝晖齿科","titles":"主任医师","depart":"中医男科","diseaseExpertise":["高血压病","支气管炎"],"fee":0},{"id":"5af3f43cc319b352dc4d40b2","image":null,"name":"斩三44","medicalInstitutions":"广东公安边防总队医院宝安分院","titles":"主任医师","depart":"内分泌科","diseaseExpertise":["糖尿病","内分泌失调"],"fee":0},{"id":"5b07a4fed6666f0600bbdfb1","image":null,"name":"刘大海","medicalInstitutions":"广东公安边防总队医院宝安分院","titles":"主任医师","depart":"中医内科","diseaseExpertise":["支气管炎"],"fee":0},{"id":"5b442176d51f085cd412f999","image":"http://pic.tunnel.qydev.com/group1/M00/00/A6/wKgByFtG8gaAcednAABj7Xklusg936.jpg","name":"老七2","medicalInstitutions":"中信健康体检中心","titles":"住院医师","depart":"乳腺科","diseaseExpertise":["上呼吸道感染","流行性感冒","大叶性肺炎"],"fee":75}]
     */

    private int code;
    private String message;
    private PageBean page;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageBean {
        /**
         * pageIndex : 1
         * pageSize : 10
         * pageCount : 2
         * total : 11
         */

        private int pageIndex;
        private int pageSize;
        private int pageCount;
        private int total;

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class DataBean {
        /**
         * id : 5af3f407c319b352dc4d40a5
         * image : null
         * name : 赵四
         * medicalInstitutions : 广东公安边防总队医院宝安分院
         * titles : 主任医师
         * depart : 中医妇科
         * diseaseExpertise : ["糖尿病"]
         * fee : 0
         */

        private String id;
        private String image;
        private String name;
        private String medicalInstitutions;
        private String titles;
        private String depart;
        private int fee;
        private List<String> diseaseExpertise;
        private int caseNum;
        private int clinicalExperience;

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
