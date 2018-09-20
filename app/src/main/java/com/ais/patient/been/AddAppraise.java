package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/8/10 0010.
 */

public class AddAppraise {

    /**
     * orderId : 20180522152697363732664231
     * appraises : [{"item":"服务评价","score":5,"labels":["天气好"]}]
     */

    private String orderId;
    private List<AppraisesBean> appraises;

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
         * score : 5
         * labels : ["天气好"]
         */

        private String item;
        private double score;
        private List<String> labels;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
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
