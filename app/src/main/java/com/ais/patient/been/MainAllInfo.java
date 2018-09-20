package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/8/1 0001.
 */

public class MainAllInfo {

    /**
     * code : 200
     * message : 成功
     * unread_num : 2
     * diseaes : [{"diseaseId":"5b06a1737aaf24be3a926198","diseaseName":"糖尿病","diseaseUrl":"http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg"},{"diseaseId":"5b2e165b6610dfc30e72bf9e","diseaseName":"口腔溃疡","diseaseUrl":"http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg"},{"diseaseId":"5b2e16346610dfc30e72bef6","diseaseName":"腰痛","diseasePic":"http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg"},{"diseaseId":"5b2e16286610dfc30e72bec7","diseaseName":"多发性肌炎","diseasePic":"http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg"},{"diseaseId":"5b2e16116610dfc30e72be53","diseaseName":"虚劳","diseasePic":"http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg"},{"diseaseId":"5b2e15ff6610dfc30e72be03","diseaseName":"抑郁症","diseasePic":"http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg"}]
     * advert : [{"title":"患者端首页广告","content":"广告内容-急诊-找大夫","imagesUrl":"https://www.baidu.com/img/bd_logo1.png","url":"https://www.baidu.com/"},{"title":"患者端首页广告","content":"广告内容-急诊-找大夫","imagesUrl":"https://b.bdstatic.com/boxlib/20180530/20180530164702784791028.jpg","url":"https://www.baidu.com/"},{"title":"患者端首页广告","content":"广告内容-急诊-找大夫","imagesUrl":"https://t10.baidu.com/it/u=2144410068,1040956479&fm=173&app=25&f=JPEG?w=218&h=146&s=B9914F91448A66E644B0D9040300F0B1","url":"https://www.baidu.com/"}]
     * departs : [{"departId":"5aeab4ecada3ae76569a95d7","departName":"内科","departPic":"http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg"},{"departId":"5aeab4cfada3ae76569a95d2","departName":"内分泌科","departPic":"http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg"},{"departId":"5b2e0c476610dfc30e72973b","departName":"神经内科","departPic":"http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg"},{"departId":"5b2e0b5a6610dfc30e7293c6","departName":"中医内科","departUrl":"http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg"},{"departId":"5b2e0e466610dfc30e729edf","departName":"乳腺科","departPic":"http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg"},{"departId":"5b2e0e356610dfc30e729ea0","departName":"治末病科","departPic":"http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg"}]
     */

    private int code;
    private String message;
    private int unread_num;
    private List<DiseaesBean> diseaes;
    private List<AdvertBean> advert;
    private List<DepartsBean> departs;

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

    public int getUnread_num() {
        return unread_num;
    }

    public void setUnread_num(int unread_num) {
        this.unread_num = unread_num;
    }

    public List<DiseaesBean> getDiseaes() {
        return diseaes;
    }

    public void setDiseaes(List<DiseaesBean> diseaes) {
        this.diseaes = diseaes;
    }

    public List<AdvertBean> getAdvert() {
        return advert;
    }

    public void setAdvert(List<AdvertBean> advert) {
        this.advert = advert;
    }

    public List<DepartsBean> getDeparts() {
        return departs;
    }

    public void setDeparts(List<DepartsBean> departs) {
        this.departs = departs;
    }

    public static class DiseaesBean {
        /**
         * diseaseId : 5b06a1737aaf24be3a926198
         * diseaseName : 糖尿病
         * diseaseUrl : http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg
         * diseasePic : http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg
         */

        private String diseaseId;
        private String diseaseName;
        private String diseasePic;

        public String getDiseaseId() {
            return diseaseId;
        }

        public void setDiseaseId(String diseaseId) {
            this.diseaseId = diseaseId;
        }

        public String getDiseaseName() {
            return diseaseName;
        }

        public void setDiseaseName(String diseaseName) {
            this.diseaseName = diseaseName;
        }


        public String getDiseasePic() {
            return diseasePic;
        }

        public void setDiseasePic(String diseasePic) {
            this.diseasePic = diseasePic;
        }
    }

    public static class AdvertBean {
        /**
         * title : 患者端首页广告
         * content : 广告内容-急诊-找大夫
         * imagesUrl : https://www.baidu.com/img/bd_logo1.png
         * url : https://www.baidu.com/
         */

        private String title;
        private String content;
        private String imagesUrl;
        private String url;

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

        public String getImagesUrl() {
            return imagesUrl;
        }

        public void setImagesUrl(String imagesUrl) {
            this.imagesUrl = imagesUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class DepartsBean {
        /**
         * departId : 5aeab4ecada3ae76569a95d7
         * departName : 内科
         * departPic : http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg
         * departUrl : http://pic.tunnel.qydev.com/group1/M00/00/A5/wKgByFsuEnmEPdufAAAAAP8E3mY194.jpg
         */

        private String departId;
        private String departName;
        private String departPic;
        private String departUrl;

        public String getDepartId() {
            return departId;
        }

        public void setDepartId(String departId) {
            this.departId = departId;
        }

        public String getDepartName() {
            return departName;
        }

        public void setDepartName(String departName) {
            this.departName = departName;
        }

        public String getDepartPic() {
            return departPic;
        }

        public void setDepartPic(String departPic) {
            this.departPic = departPic;
        }

        public String getDepartUrl() {
            return departUrl;
        }

        public void setDepartUrl(String departUrl) {
            this.departUrl = departUrl;
        }
    }
}
