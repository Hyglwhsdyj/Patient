package com.ais.patient.been;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/8/2 0002.
 */

public class DoctorMsg implements Serializable{


    /**
     * id : 5b442176d51f085cd412f999
     * image : http://pic.tunnel.qydev.com/group1/M00/00/A6/wKgByFtG8gaAcednAABj7Xklusg936.jpg
     * name : 老七2
     * medicalInstitutions : 中信健康体检中心
     * titles : 住院医师
     * depart : 乳腺科
     * number : 0
     * revisit : 0
     * diseaseExpertise : ["上呼吸道感染","流行性感冒","大叶性肺炎"]
     * introduce : 80890890890
     * jobPhotos : ["http://pic.tunnel.qydev.com/group1/M00/00/A6/wKgByFtHEtWAet3SAAGagTvQ98I801.png"]
     * isInquiry : true
     * isAppointment : false
     * fee : 75
     * announcementContent : 456456
     * announcementTime : 2018-07-20
     * articles : [{"articleId":"5b51e96bd51f08088de29055","articleType":"5","articleContent":"8888","articleTime":"2018-07-20","articlePics":["http://pic.tunnel.qydev.com/group1/M00/00/AD/wKgByFtR6WuALxzIAAGagTvQ98I178.png","http://pic.tunnel.qydev.com/group1/M00/00/AD/wKgByFtR6XSAY2e0AAAq1LfSfrI494.jpg"]},{"articleId":"5b51e95fd51f08088de29054","articleType":"1","articleTitle":"77","articleContent":"66","articleTime":"2018-07-20","articlePics":[]}]
     * isFollow : false
     * isRecommend : true
     */

    private String id;
    private String image;
    private String name;
    private String medicalInstitutions;
    private String titles;
    private String depart;
    private int number;
    private String revisit;
    private String introduce;
    private boolean isInquiry;
    private boolean isAppointment;
    private double fee;
    private String announcementContent;
    private String announcementTime;
    private boolean isFollow;
    private boolean isRecommend;
    private List<String> diseaseExpertise;
    private List<String> jobPhotos;
    private List<String> certificatePics;
    private List<ArticlesBean> articles;

    private int caseNum;
    private int clinicalExperience;

    public int getClinicalExperience() {
        return clinicalExperience;
    }

    public void setClinicalExperience(int clinicalExperience) {
        this.clinicalExperience = clinicalExperience;
    }

    public int getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(int caseNum) {
        this.caseNum = caseNum;
    }

    public List<String> getCertificatePics() {
        return certificatePics;
    }

    public void setCertificatePics(List<String> certificatePics) {
        this.certificatePics = certificatePics;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getRevisit() {
        return revisit;
    }

    public void setRevisit(String revisit) {
        this.revisit = revisit;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public boolean isRecommend() {
        return isRecommend;
    }

    public void setRecommend(boolean recommend) {
        isRecommend = recommend;
    }

    public String getAnnouncementContent() {
        return announcementContent;
    }

    public void setAnnouncementContent(String announcementContent) {
        this.announcementContent = announcementContent;
    }

    public String getAnnouncementTime() {
        return announcementTime;
    }

    public void setAnnouncementTime(String announcementTime) {
        this.announcementTime = announcementTime;
    }

    public boolean isIsFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }

    public boolean isIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public List<String> getDiseaseExpertise() {
        return diseaseExpertise;
    }

    public void setDiseaseExpertise(List<String> diseaseExpertise) {
        this.diseaseExpertise = diseaseExpertise;
    }

    public List<String> getJobPhotos() {
        return jobPhotos;
    }

    public void setJobPhotos(List<String> jobPhotos) {
        this.jobPhotos = jobPhotos;
    }

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        /**
         * articleId : 5b51e96bd51f08088de29055
         * articleType : 5
         * articleContent : 8888
         * articleTime : 2018-07-20
         * articlePics : ["http://pic.tunnel.qydev.com/group1/M00/00/AD/wKgByFtR6WuALxzIAAGagTvQ98I178.png","http://pic.tunnel.qydev.com/group1/M00/00/AD/wKgByFtR6XSAY2e0AAAq1LfSfrI494.jpg"]
         * articleTitle : 77
         */

        private String articleId;
        private String articleType;
        private String articleContent;
        private String articleTime;
        private String articleTitle;
        private List<String> articlePics;

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        public String getArticleType() {
            return articleType;
        }

        public void setArticleType(String articleType) {
            this.articleType = articleType;
        }

        public String getArticleContent() {
            return articleContent;
        }

        public void setArticleContent(String articleContent) {
            this.articleContent = articleContent;
        }

        public String getArticleTime() {
            return articleTime;
        }

        public void setArticleTime(String articleTime) {
            this.articleTime = articleTime;
        }

        public String getArticleTitle() {
            return articleTitle;
        }

        public void setArticleTitle(String articleTitle) {
            this.articleTitle = articleTitle;
        }

        public List<String> getArticlePics() {
            return articlePics;
        }

        public void setArticlePics(List<String> articlePics) {
            this.articlePics = articlePics;
        }
    }
}
