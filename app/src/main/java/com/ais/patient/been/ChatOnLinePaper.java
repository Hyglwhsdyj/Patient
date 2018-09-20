package com.ais.patient.been;

import java.util.List;

public class ChatOnLinePaper {

    /**
     * body : {"headPharynx":null,"body":["身体觉得重"],"cough":["咳嗽","无痰"],"feel":["怕冷","怕热"],"supplement":"无解","chest":["心慌","胸闷"],"livingHabit":["喜食油腻"],"sweat":["易出汗","基本不出汗"],"handAndFoot":"手机偏冷"}
     * createTime : 2018-05-21 16:30:51
     * gynaecology : {"specialStage":"备孕中","feel":"经前腰酸","cycle":"经期很准（28~30天来一次）","clot":"无血块","dysmenorrhoea":["月经前有痛","月经期有痛经"],"menopause":"未绝经","fertility":"生育1次","period":"行经期3天以下","color":"月经暗黑","leucorrheaCapacity":"白带多","supplement":"补充说明","abortion":"流产1次","quantity":"量少（整个经期不足10块卫生纸）","leucorrhea":"黄色"}
     * diet : {"water":"喝水多","waterDegree":"喜欢喝冷水","appetite":"胃口好","other":["口气","恶心"],"texture":["口酸","口苦"],"supplement":"补充说明"}
     * andrology : {"andrology":"不育、前列腺炎","sexualFunction":"早泄、阳痿","supplement":"一般般啦，都差不多"}
     * excrement : {"colour":"大便黄","stool":"大便一天一次","other":["放屁多","放屁臭"],"shape":["成形，偏干","腹泻"],"urine":["遗尿","尿不尽"],"supplement":"补充说明"}
     * otherImages : ["http://pic.5aszy.com/group1/M00/00/A3/wKgByFr_npGEN68QAAAAAKe9iJQ802.jpg","http://pic.5aszy.com/group1/M00/00/A3/wKgByFr_npGEN68QAAAAAKe9iJQ802.jpg"]
     * basicSituation : {"spirit":"精神好","sleep":"睡眠好","diet":"正常","sweat":"出汗较少","supplement":"补充说明","drink":"喝水较少"}
     * id : 5afffc4c27fe6f24681d87fd
     * childExcrement : {"colour":"大便黄","urineColour":"小便深黄","urineTime":"正常","other":null,"shape":["成形，偏干","干燥成球状"],"supplement":"补充说明","stoolTime":"大便一天一次"}
     * patientId : 5af64f05314250399da4ff8b
     * templateType : 成年
     * patient : {"sex":"女","medicalHistory":"高血压，高血脂","weight":75,"height":1.73,"phoneNumber":"17603056681","address":"广东省深圳市","age":"50岁","name":"李四","remarks":"病例"}
     * tongueImages : ["http://pic.5aszy.com/group1/M00/00/A3/wKgByFr_npGEN68QAAAAAKe9iJQ802.jpg","http://pic.5aszy.com/group1/M00/00/A3/wKgByFr_npGEN68QAAAAAKe9iJQ802.jpg"]
     * doctorId : 5ae96af8b3a1f130240462d9
     * userId : 5af5505d31425014b02cf381
     * appeal : 天天向上
     * choseTime : null
     * special : {"cough":"有咳嗽","sputum":["黄痰","黄白痰"],"foot":"手脚发冷","supplement":"补充说明","temperature":"发冷"}
     * sleepMood : {"quality":"睡眠深","wakeUp":["很少夜醒","经常夜醒"],"fallAsleep":"很难睡着","other":["易怒","易悲"],"dream":"经常做噩梦","supplement":"补充说明"}
     * orderId : null
     * symptom : 挑食
     */

    private BodyBean body;
    private String createTime;
    private GynaecologyBean gynaecology;
    private DietBean diet;
    private AndrologyBean andrology;
    private ExcrementBean excrement;
    private BasicSituationBean basicSituation;
    private String id;
    private ChildExcrementBean childExcrement;
    private String patientId;
    private String templateType;
    private PatientBean patient;
    private String doctorId;
    private String userId;
    private String appeal;
    private String choseTime;
    private SpecialBean special;
    private SleepMoodBean sleepMood;
    private String orderId;
    private String symptom;
    private List<String> otherImages;
    private List<String> tongueImages;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public GynaecologyBean getGynaecology() {
        return gynaecology;
    }

    public void setGynaecology(GynaecologyBean gynaecology) {
        this.gynaecology = gynaecology;
    }

    public DietBean getDiet() {
        return diet;
    }

    public void setDiet(DietBean diet) {
        this.diet = diet;
    }

    public AndrologyBean getAndrology() {
        return andrology;
    }

    public void setAndrology(AndrologyBean andrology) {
        this.andrology = andrology;
    }

    public ExcrementBean getExcrement() {
        return excrement;
    }

    public void setExcrement(ExcrementBean excrement) {
        this.excrement = excrement;
    }

    public BasicSituationBean getBasicSituation() {
        return basicSituation;
    }

    public void setBasicSituation(BasicSituationBean basicSituation) {
        this.basicSituation = basicSituation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChildExcrementBean getChildExcrement() {
        return childExcrement;
    }

    public void setChildExcrement(ChildExcrementBean childExcrement) {
        this.childExcrement = childExcrement;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public PatientBean getPatient() {
        return patient;
    }

    public void setPatient(PatientBean patient) {
        this.patient = patient;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppeal() {
        return appeal;
    }

    public void setAppeal(String appeal) {
        this.appeal = appeal;
    }

    public SpecialBean getSpecial() {
        return special;
    }

    public void setSpecial(SpecialBean special) {
        this.special = special;
    }

    public SleepMoodBean getSleepMood() {
        return sleepMood;
    }

    public void setSleepMood(SleepMoodBean sleepMood) {
        this.sleepMood = sleepMood;
    }

    public String getChoseTime() {
        return choseTime;
    }

    public void setChoseTime(String choseTime) {
        this.choseTime = choseTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public List<String> getOtherImages() {
        return otherImages;
    }

    public void setOtherImages(List<String> otherImages) {
        this.otherImages = otherImages;
    }

    public List<String> getTongueImages() {
        return tongueImages;
    }

    public void setTongueImages(List<String> tongueImages) {
        this.tongueImages = tongueImages;
    }

    public static class BodyBean {
        /**
         * headPharynx : null
         * body : ["身体觉得重"]
         * cough : ["咳嗽","无痰"]
         * feel : ["怕冷","怕热"]
         * supplement : 无解
         * chest : ["心慌","胸闷"]
         * livingHabit : ["喜食油腻"]
         * sweat : ["易出汗","基本不出汗"]
         * handAndFoot : 手机偏冷
         */

        private List<String> headPharynx;
        private String supplement;
        private String handAndFoot;
        private List<String> body;
        private List<String> cough;
        private List<String> feel;
        private List<String> chest;
        private List<String> livingHabit;
        private List<String> sweat;

        public List<String> getHeadPharynx() {
            return headPharynx;
        }

        public void setHeadPharynx(List<String> headPharynx) {
            this.headPharynx = headPharynx;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }

        public String getHandAndFoot() {
            return handAndFoot;
        }

        public void setHandAndFoot(String handAndFoot) {
            this.handAndFoot = handAndFoot;
        }

        public List<String> getBody() {
            return body;
        }

        public void setBody(List<String> body) {
            this.body = body;
        }

        public List<String> getCough() {
            return cough;
        }

        public void setCough(List<String> cough) {
            this.cough = cough;
        }

        public List<String> getFeel() {
            return feel;
        }

        public void setFeel(List<String> feel) {
            this.feel = feel;
        }

        public List<String> getChest() {
            return chest;
        }

        public void setChest(List<String> chest) {
            this.chest = chest;
        }

        public List<String> getLivingHabit() {
            return livingHabit;
        }

        public void setLivingHabit(List<String> livingHabit) {
            this.livingHabit = livingHabit;
        }

        public List<String> getSweat() {
            return sweat;
        }

        public void setSweat(List<String> sweat) {
            this.sweat = sweat;
        }
    }

    public static class GynaecologyBean {
        /**
         * specialStage : 备孕中
         * feel : 经前腰酸
         * cycle : 经期很准（28~30天来一次）
         * clot : 无血块
         * dysmenorrhoea : ["月经前有痛","月经期有痛经"]
         * menopause : 未绝经
         * fertility : 生育1次
         * period : 行经期3天以下
         * color : 月经暗黑
         * leucorrheaCapacity : 白带多
         * supplement : 补充说明
         * abortion : 流产1次
         * quantity : 量少（整个经期不足10块卫生纸）
         * leucorrhea : 黄色
         */

        private String specialStage;
        private String feel;
        private String cycle;
        private String clot;
        private String menopause;
        private String fertility;
        private String period;
        private String color;
        private String leucorrheaCapacity;
        private String supplement;
        private String abortion;
        private String quantity;
        private String leucorrhea;
        private List<String> dysmenorrhoea;

        public String getSpecialStage() {
            return specialStage;
        }

        public void setSpecialStage(String specialStage) {
            this.specialStage = specialStage;
        }

        public String getFeel() {
            return feel;
        }

        public void setFeel(String feel) {
            this.feel = feel;
        }

        public String getCycle() {
            return cycle;
        }

        public void setCycle(String cycle) {
            this.cycle = cycle;
        }

        public String getClot() {
            return clot;
        }

        public void setClot(String clot) {
            this.clot = clot;
        }

        public String getMenopause() {
            return menopause;
        }

        public void setMenopause(String menopause) {
            this.menopause = menopause;
        }

        public String getFertility() {
            return fertility;
        }

        public void setFertility(String fertility) {
            this.fertility = fertility;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getLeucorrheaCapacity() {
            return leucorrheaCapacity;
        }

        public void setLeucorrheaCapacity(String leucorrheaCapacity) {
            this.leucorrheaCapacity = leucorrheaCapacity;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }

        public String getAbortion() {
            return abortion;
        }

        public void setAbortion(String abortion) {
            this.abortion = abortion;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getLeucorrhea() {
            return leucorrhea;
        }

        public void setLeucorrhea(String leucorrhea) {
            this.leucorrhea = leucorrhea;
        }

        public List<String> getDysmenorrhoea() {
            return dysmenorrhoea;
        }

        public void setDysmenorrhoea(List<String> dysmenorrhoea) {
            this.dysmenorrhoea = dysmenorrhoea;
        }
    }

    public static class DietBean {
        /**
         * water : 喝水多
         * waterDegree : 喜欢喝冷水
         * appetite : 胃口好
         * other : ["口气","恶心"]
         * texture : ["口酸","口苦"]
         * supplement : 补充说明
         */

        private String water;
        private String waterDegree;
        private String appetite;
        private String supplement;
        private List<String> other;
        private List<String> texture;

        public String getWater() {
            return water;
        }

        public void setWater(String water) {
            this.water = water;
        }

        public String getWaterDegree() {
            return waterDegree;
        }

        public void setWaterDegree(String waterDegree) {
            this.waterDegree = waterDegree;
        }

        public String getAppetite() {
            return appetite;
        }

        public void setAppetite(String appetite) {
            this.appetite = appetite;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }

        public List<String> getOther() {
            return other;
        }

        public void setOther(List<String> other) {
            this.other = other;
        }

        public List<String> getTexture() {
            return texture;
        }

        public void setTexture(List<String> texture) {
            this.texture = texture;
        }
    }

    public static class AndrologyBean {
        /**
         * andrology : 不育、前列腺炎
         * sexualFunction : 早泄、阳痿
         * supplement : 一般般啦，都差不多
         */

        private String andrology;
        private String sexualFunction;
        private String supplement;

        public String getAndrology() {
            return andrology;
        }

        public void setAndrology(String andrology) {
            this.andrology = andrology;
        }

        public String getSexualFunction() {
            return sexualFunction;
        }

        public void setSexualFunction(String sexualFunction) {
            this.sexualFunction = sexualFunction;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }
    }

    public static class ExcrementBean {
        /**
         * colour : 大便黄
         * stool : 大便一天一次
         * other : ["放屁多","放屁臭"]
         * shape : ["成形，偏干","腹泻"]
         * urine : ["遗尿","尿不尽"]
         * supplement : 补充说明
         */

        private String colour;
        private String stool;
        private String supplement;
        private List<String> other;
        private List<String> shape;
        private List<String> urine;

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public String getStool() {
            return stool;
        }

        public void setStool(String stool) {
            this.stool = stool;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }

        public List<String> getOther() {
            return other;
        }

        public void setOther(List<String> other) {
            this.other = other;
        }

        public List<String> getShape() {
            return shape;
        }

        public void setShape(List<String> shape) {
            this.shape = shape;
        }

        public List<String> getUrine() {
            return urine;
        }

        public void setUrine(List<String> urine) {
            this.urine = urine;
        }
    }

    public static class BasicSituationBean {
        /**
         * spirit : 精神好
         * sleep : 睡眠好
         * diet : 正常
         * sweat : 出汗较少
         * supplement : 补充说明
         * drink : 喝水较少
         */

        private String spirit;
        private String sleep;
        private String diet;
        private String sweat;
        private String supplement;
        private String drink;

        public String getSpirit() {
            return spirit;
        }

        public void setSpirit(String spirit) {
            this.spirit = spirit;
        }

        public String getSleep() {
            return sleep;
        }

        public void setSleep(String sleep) {
            this.sleep = sleep;
        }

        public String getDiet() {
            return diet;
        }

        public void setDiet(String diet) {
            this.diet = diet;
        }

        public String getSweat() {
            return sweat;
        }

        public void setSweat(String sweat) {
            this.sweat = sweat;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }

        public String getDrink() {
            return drink;
        }

        public void setDrink(String drink) {
            this.drink = drink;
        }
    }

    public static class ChildExcrementBean {
        /**
         * colour : 大便黄
         * urineColour : 小便深黄
         * urineTime : 正常
         * other : null
         * shape : ["成形，偏干","干燥成球状"]
         * supplement : 补充说明
         * stoolTime : 大便一天一次
         */

        private String colour;
        private String urineColour;
        private String urineTime;
        private String other;
        private String supplement;
        private String stoolTime;
        private List<String> shape;

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public String getUrineColour() {
            return urineColour;
        }

        public void setUrineColour(String urineColour) {
            this.urineColour = urineColour;
        }

        public String getUrineTime() {
            return urineTime;
        }

        public void setUrineTime(String urineTime) {
            this.urineTime = urineTime;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }

        public String getStoolTime() {
            return stoolTime;
        }

        public void setStoolTime(String stoolTime) {
            this.stoolTime = stoolTime;
        }

        public List<String> getShape() {
            return shape;
        }

        public void setShape(List<String> shape) {
            this.shape = shape;
        }
    }

    public static class PatientBean {
        /**
         * sex : 女
         * medicalHistory : 高血压，高血脂
         * weight : 75
         * height : 1.73
         * phoneNumber : 17603056681
         * address : 广东省深圳市
         * age : 50岁
         * name : 李四
         * remarks : 病例
         */

        private String sex;
        private String medicalHistory;
        private int weight;
        private double height;
        private String phoneNumber;
        private String address;
        private String age;
        private String name;
        private String remarks;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMedicalHistory() {
            return medicalHistory;
        }

        public void setMedicalHistory(String medicalHistory) {
            this.medicalHistory = medicalHistory;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }

    public static class SpecialBean {
        /**
         * cough : 有咳嗽
         * sputum : ["黄痰","黄白痰"]
         * foot : 手脚发冷
         * supplement : 补充说明
         * temperature : 发冷
         */

        private String cough;
        private String foot;
        private String supplement;
        private String temperature;
        private List<String> sputum;

        public String getCough() {
            return cough;
        }

        public void setCough(String cough) {
            this.cough = cough;
        }

        public String getFoot() {
            return foot;
        }

        public void setFoot(String foot) {
            this.foot = foot;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public List<String> getSputum() {
            return sputum;
        }

        public void setSputum(List<String> sputum) {
            this.sputum = sputum;
        }
    }

    public static class SleepMoodBean {
        /**
         * quality : 睡眠深
         * wakeUp : ["很少夜醒","经常夜醒"]
         * fallAsleep : 很难睡着
         * other : ["易怒","易悲"]
         * dream : 经常做噩梦
         * supplement : 补充说明
         */

        private String quality;
        private String fallAsleep;
        private String dream;
        private String supplement;
        private List<String> wakeUp;
        private List<String> other;

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getFallAsleep() {
            return fallAsleep;
        }

        public void setFallAsleep(String fallAsleep) {
            this.fallAsleep = fallAsleep;
        }

        public String getDream() {
            return dream;
        }

        public void setDream(String dream) {
            this.dream = dream;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }

        public List<String> getWakeUp() {
            return wakeUp;
        }

        public void setWakeUp(List<String> wakeUp) {
            this.wakeUp = wakeUp;
        }

        public List<String> getOther() {
            return other;
        }

        public void setOther(List<String> other) {
            this.other = other;
        }
    }
}
