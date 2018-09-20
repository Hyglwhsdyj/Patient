package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/8/8.
 */

public class OrdonnanceDetailRespone {


    /**
     * name : 小孩子
     * sex : 男
     * age : 1岁
     * phoneNumber : 13530531749
     * applyTime : 2018-08-16 23:35:40
     * limitTime : 2018-08-17 23:36:27
     * diseaseDescribe : 哦哦哦
     * adviceDiacrisis : 肺气虚
     * medicalHistory : 高血压
     * remarks :
     * drugs : [{"drugType":"中药饮片","plaster":"5","userMethod":"200ml/包，一天2次","takingTime":"饭后半小时服用","isFried":"不代煎","drugTemps":[{"name":"黄芩","num":2,"unit":"包"},{"name":"人参","num":2,"unit":"包"}],"drugNum":2,"weight":0,"supplier":"康美药业"}]
     * diagnosisFee : 0.0
     * drugFee : 18.64
     * makeFee : 0.0
     * expressFee : 0.0
     * consignee : 李
     * consigneeTel : 13530531789
     * address : 广东省深圳市南山区西乡
     * orderId : 5b7599cc2f396e48e48382a4
     * logistics : [{"logisticsName":"申通快递","logisticsNo":"3367283341734","data":[{"time":"2018-07-09 12:41:21","context":"【广东宝安坪州营业厅】已签收,签收人是阿里巴巴代收"},{"time":"2018-07-09 09:25:58","context":"【广东宝安坪州营业厅】 的派件员 廖平洪:广东宝安坪州营业厅,13510017035 正在派件"},{"time":"2018-07-09 08:38:42","context":"快件已到达 【广东宝安坪州营业厅】"},{"time":"2018-07-09 06:42:42","context":"由【广东深圳中转部】 发往 【广东宝安坪州营业厅】"},{"time":"2018-07-09 03:49:46","context":"由【广东深圳中转部】 发往 【广东深圳公司】"},{"time":"2018-07-08 20:52:48","context":"由【广东佛山中转部】 发往 【广东深圳中转部】"},{"time":"2018-07-08 20:52:48","context":"【广东佛山中转部】-已进行装车扫描"},{"time":"2018-07-08 20:48:54","context":"【广东佛山公司】 的收件员 已收件"},{"time":"2018-07-08 14:06:31","context":"【广东佛山金沙分部】 的收件员 黄献泽已收件"}],"drugs":["人参片","当归"]},{"logisticsName":"申通快递","logisticsNo":"3367283341734","data":[{"time":"2018-07-09 12:41:21","context":"【广东宝安坪州营业厅】已签收,签收人是阿里巴巴代收"},{"time":"2018-07-09 09:25:58","context":"【广东宝安坪州营业厅】 的派件员 廖平洪:广东宝安坪州营业厅,13510017035 正在派件"},{"time":"2018-07-09 08:38:42","context":"快件已到达 【广东宝安坪州营业厅】"},{"time":"2018-07-09 06:42:42","context":"由【广东深圳中转部】 发往 【广东宝安坪州营业厅】"},{"time":"2018-07-09 03:49:46","context":"由【广东深圳中转部】 发往 【广东深圳公司】"},{"time":"2018-07-08 20:52:48","context":"由【广东佛山中转部】 发往 【广东深圳中转部】"},{"time":"2018-07-08 20:52:48","context":"【广东佛山中转部】-已进行装车扫描"},{"time":"2018-07-08 20:48:54","context":"【广东佛山公司】 的收件员 已收件"},{"time":"2018-07-08 14:06:31","context":"【广东佛山金沙分部】 的收件员 黄献泽已收件"}],"drugs":["人参片","当归"]}]
     */

    private String name;
    private String sex;
    private String age;
    private String phoneNumber;
    private String applyTime;
    private String limitTime;
    private String diseaseDescribe;
    private String adviceDiacrisis;
    private String medicalHistory;
    private String remarks;
    private double totalFee;
    private String addressId;
    private double diagnosisFee;
    private double drugFee;
    private double makeFee;
    private double expressFee;
    private String consignee;
    private String consigneeTel;
    private String address;
    private String orderId;
    private List<DrugsBean> drugs;
    private List<LogisticsBean> logistics;

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }

    public String getDiseaseDescribe() {
        return diseaseDescribe;
    }

    public void setDiseaseDescribe(String diseaseDescribe) {
        this.diseaseDescribe = diseaseDescribe;
    }

    public String getAdviceDiacrisis() {
        return adviceDiacrisis;
    }

    public void setAdviceDiacrisis(String adviceDiacrisis) {
        this.adviceDiacrisis = adviceDiacrisis;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getDiagnosisFee() {
        return diagnosisFee;
    }

    public void setDiagnosisFee(double diagnosisFee) {
        this.diagnosisFee = diagnosisFee;
    }

    public double getDrugFee() {
        return drugFee;
    }

    public void setDrugFee(double drugFee) {
        this.drugFee = drugFee;
    }

    public double getMakeFee() {
        return makeFee;
    }

    public void setMakeFee(double makeFee) {
        this.makeFee = makeFee;
    }

    public double getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(double expressFee) {
        this.expressFee = expressFee;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<DrugsBean> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<DrugsBean> drugs) {
        this.drugs = drugs;
    }

    public List<LogisticsBean> getLogistics() {
        return logistics;
    }

    public void setLogistics(List<LogisticsBean> logistics) {
        this.logistics = logistics;
    }

    public static class DrugsBean {
        /**
         * drugType : 中药饮片
         * plaster : 5
         * userMethod : 200ml/包，一天2次
         * takingTime : 饭后半小时服用
         * isFried : 不代煎
         * drugTemps : [{"name":"黄芩","num":2,"unit":"包"},{"name":"人参","num":2,"unit":"包"}]
         * drugNum : 2
         * weight : 0.0
         * supplier : 康美药业
         */

        private String drugType;
        private String plaster;
        private String userMethod;
        private String takingTime;
        private String isFried;
        private int drugNum;
        private double weight;
        private String supplier;
        private List<String> specialUsemethod;

        public List<String> getSpecialUsemethod() {
            return specialUsemethod;
        }

        public void setSpecialUsemethod(List<String> specialUsemethod) {
            this.specialUsemethod = specialUsemethod;
        }

        private List<DrugTempsBean> drugTemps;

        public String getDrugType() {
            return drugType;
        }

        public void setDrugType(String drugType) {
            this.drugType = drugType;
        }

        public String getPlaster() {
            return plaster;
        }

        public void setPlaster(String plaster) {
            this.plaster = plaster;
        }

        public String getUserMethod() {
            return userMethod;
        }

        public void setUserMethod(String userMethod) {
            this.userMethod = userMethod;
        }

        public String getTakingTime() {
            return takingTime;
        }

        public void setTakingTime(String takingTime) {
            this.takingTime = takingTime;
        }

        public String getIsFried() {
            return isFried;
        }

        public void setIsFried(String isFried) {
            this.isFried = isFried;
        }

        public int getDrugNum() {
            return drugNum;
        }

        public void setDrugNum(int drugNum) {
            this.drugNum = drugNum;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public String getSupplier() {
            return supplier;
        }

        public void setSupplier(String supplier) {
            this.supplier = supplier;
        }

        public List<DrugTempsBean> getDrugTemps() {
            return drugTemps;
        }

        public void setDrugTemps(List<DrugTempsBean> drugTemps) {
            this.drugTemps = drugTemps;
        }

        public static class DrugTempsBean {
            /**
             * name : 黄芩
             * num : 2.0
             * unit : 包
             */

            private String name;
            private double num;
            private String unit;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getNum() {
                return num;
            }

            public void setNum(double num) {
                this.num = num;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }
        }
    }

    public static class LogisticsBean {
        /**
         * logisticsName : 申通快递
         * logisticsNo : 3367283341734
         * data : [{"time":"2018-07-09 12:41:21","context":"【广东宝安坪州营业厅】已签收,签收人是阿里巴巴代收"},{"time":"2018-07-09 09:25:58","context":"【广东宝安坪州营业厅】 的派件员 廖平洪:广东宝安坪州营业厅,13510017035 正在派件"},{"time":"2018-07-09 08:38:42","context":"快件已到达 【广东宝安坪州营业厅】"},{"time":"2018-07-09 06:42:42","context":"由【广东深圳中转部】 发往 【广东宝安坪州营业厅】"},{"time":"2018-07-09 03:49:46","context":"由【广东深圳中转部】 发往 【广东深圳公司】"},{"time":"2018-07-08 20:52:48","context":"由【广东佛山中转部】 发往 【广东深圳中转部】"},{"time":"2018-07-08 20:52:48","context":"【广东佛山中转部】-已进行装车扫描"},{"time":"2018-07-08 20:48:54","context":"【广东佛山公司】 的收件员 已收件"},{"time":"2018-07-08 14:06:31","context":"【广东佛山金沙分部】 的收件员 黄献泽已收件"}]
         * drugs : ["人参片","当归"]
         */

        private String logisticsName;
        private String logisticsNo;
        private List<DataBean> data;
        private List<String> drugs;

        public String getLogisticsName() {
            return logisticsName;
        }

        public void setLogisticsName(String logisticsName) {
            this.logisticsName = logisticsName;
        }

        public String getLogisticsNo() {
            return logisticsNo;
        }

        public void setLogisticsNo(String logisticsNo) {
            this.logisticsNo = logisticsNo;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public List<String> getDrugs() {
            return drugs;
        }

        public void setDrugs(List<String> drugs) {
            this.drugs = drugs;
        }

        public static class DataBean {
            /**
             * time : 2018-07-09 12:41:21
             * context : 【广东宝安坪州营业厅】已签收,签收人是阿里巴巴代收
             */

            private String time;
            private String context;
            private int type;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getContext() {
                return context;
            }

            public void setContext(String context) {
                this.context = context;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
