package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3 0003.
 */

public class AppraiseList {


    /**
     * name : 15******454
     * time : 2018-08-18
     * score : 42.67
     * items : [{"item":"服务态度","score":1},{"item":"回复速度","score":2},{"item":"处方疗效","score":4}]
     */

    private String name;
    private String time;
    private double score;
    private List<ItemsBean> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * item : 服务态度
         * score : 1
         */

        private String item;
        private int score;

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
    }
}
