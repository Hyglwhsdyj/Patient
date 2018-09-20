package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/8/15 0015.
 */

public class BuyMedicine {

    /**
     * name : 李四
     * sex : 男
     * age : 18
     * phoneNumber : 13533480326
     * applyTime : 2018-06-21 13:48:46
     * limitTime : 2018-06-22 13:48:46
     * diseaseDescribe : 失眠多梦50年,病症简述，失眠
     * adviceDiacrisis : 诊断建议
     * drugs : [{"drugType":"中药饮片","plaster":"5贴","userMethod":"200ml/包，一天2次","takingTime":null,"isFried":"代煎","drugTemps":[{"name":"当归","num":60,"unit":"g"},{"name":"人参片","num":900,"unit":"g"},{"name":"小蓟炭","num":60,"unit":"g"},{"name":"盐小茴香","num":60,"unit":"g"}],"drugNum":4,"weight":"中药饮片"},{"drugType":"中药颗粒","plaster":"5贴","userMethod":"200ml/包，一天2次","takingTime":null,"drugTemps":[{"name":"人参片","num":60,"unit":"g"},{"name":"当归","num":60,"unit":"g"}],"drugNum":2,"weight":"中药颗粒"}]
     * diagnosisFee : 10.0
     * drugFee : 4961.25
     * makeFee : 0.0
     * expressFee : 0.0
     * consignee : 张三
     * consigneeTel : 13560181591
     * address : 广西壮族自治区柳州市鱼峰区飞蛾路XX号XX栋XX室
     * orderId : 5b1626d3a1b8db124013eb57
     * totalFee : 4971.25
     * addressId : 5b0a8649059401617cffabe1
     */

    private String name;
    private String sex;
    private int age;
    private String phoneNumber;
    private String applyTime;
    private String limitTime;
    private String diseaseDescribe;
    private String adviceDiacrisis;
    private double diagnosisFee;
    private double drugFee;
    private double makeFee;
    private double expressFee;
    private String consignee;
    private String consigneeTel;
    private String address;
    private String orderId;
    private double totalFee;
    private String addressId;
    private List<DrugsBean> drugs;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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

    public List<DrugsBean> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<DrugsBean> drugs) {
        this.drugs = drugs;
    }

    public static class DrugsBean {
        /**
         * drugType : 中药饮片
         * plaster : 5贴
         * userMethod : 200ml/包，一天2次
         * takingTime : null
         * isFried : 代煎
         * drugTemps : [{"name":"当归","num":60,"unit":"g"},{"name":"人参片","num":900,"unit":"g"},{"name":"小蓟炭","num":60,"unit":"g"},{"name":"盐小茴香","num":60,"unit":"g"}]
         * drugNum : 4
         * weight : 中药饮片
         */

        private String drugType;
        private String plaster;
        private String userMethod;
        private Object takingTime;
        private String isFried;
        private int drugNum;
        private String weight;
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

        public Object getTakingTime() {
            return takingTime;
        }

        public void setTakingTime(Object takingTime) {
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

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public List<DrugTempsBean> getDrugTemps() {
            return drugTemps;
        }

        public void setDrugTemps(List<DrugTempsBean> drugTemps) {
            this.drugTemps = drugTemps;
        }

        public static class DrugTempsBean {
            /**
             * name : 当归
             * num : 60.0
             * unit : g
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
}
