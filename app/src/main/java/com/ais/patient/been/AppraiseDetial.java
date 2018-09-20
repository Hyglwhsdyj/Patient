package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/8/13 0013.
 */

public class AppraiseDetial {

    /**
     * headImage : http://www.hk515.com/static/images/doctor.png
     * doctorName : 斩三
     * titles : 主任医师
     * time : 2018年6月10日 12:00-18:00
     * serviceName : 在线问诊服务
     * orderId : 20180522152697363732664231
     * appraises : [{"item":"服务评价","score":3,"labels":["态度一般"]},{"item":"回复速度","score":4,"labels":["回复及时"]}]
     */

    private String headImage;
    private String doctorName;
    private String titles;
    private String time;
    private String serviceName;
    private String orderId;
    private List<AppraisesBean> appraises;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<AppraisesBean> getAppraises() {
        return appraises;
    }

    public void setAppraises(List<AppraisesBean> appraises) {
        this.appraises = appraises;
    }

    public static class AppraisesBean {
        /**
         * item : 服务评价
         * score : 3
         * labels : ["态度一般"]
         */

        private String item;
        private int score;
        private List<String> labels;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public List<String> getLabels() {
            return labels;
        }

        public void setLabels(List<String> labels) {
            this.labels = labels;
        }
    }
}
