package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/9/14 0014.
 */

public class AppraiseDetail {

    /**
     * code : 200
     * message : 成功
     * page : {"pageIndex":1,"pageSize":10,"pageCount":1,"total":3}
     * data : [{"name":"","time":"2018-08-07","score":99,"items":[{"item":"服务态度","score":5},{"item":"回复速度","score":5},{"item":"处方疗效","score":5}]},{"name":"17******668","time":"2018-06-14","score":79,"items":[{"item":"服务态度","score":3},{"item":"回复速度","score":4},{"item":"处方疗效","score":5}]},{"name":"17******668","time":"2018-06-14","score":74,"items":[{"item":"服务态度","score":3},{"item":"回复速度","score":4},{"item":"处方疗效","score":5}]}]
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
         * pageCount : 1
         * total : 3
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
         * name :
         * time : 2018-08-07
         * score : 99.0
         * items : [{"item":"服务态度","score":5},{"item":"回复速度","score":5},{"item":"处方疗效","score":5}]
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
             * score : 5
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
}
