package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/9 0009.
 */

public class Message {

    /**
     * title : 在线问诊服务购买成功
     * content : 您已经成功购买了2018年08月29日上午08:00-11:45 黄医生医生的在线问诊服务，黄医生医生将会按时与您在线沟通，请提前安排相应时间。
     * readStatus : false
     * createTime : 2018-08-28 23:10
     * code : 102
     * doctorId :
     * doctorName :
     * messageId : 5b8565e72f396e0c4b3b647a
     * businessId : 2018082801153546901149254378
     */

    private String title;
    private String content;
    private boolean readStatus;
    private String createTime;
    private int code;
    private String doctorId;
    private String doctorName;
    private String messageId;
    private String businessId;

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

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}
