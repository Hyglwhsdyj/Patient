package com.ais.patient.activity.chat2;

import com.alibaba.fastjson.JSONObject;

/**
 * 自定义消息类型 - 问诊单
 *
 场景1：问诊单填写后，在IM上面发出消息，自定义消息
 参数：1、消息类型 iyzy_msg_type:"01"
 2、标题    iyzy_title:"我的问诊单填写完了，请您查看"
 3、动作描述 iyzy_action:"立即查看"
 4、问诊单ID iyzy_business_id:"123456"
 5、跳转到对应问诊单详情


 场景2：医生问诊完毕，需要开处方，开完处方，发送信息通知用户
 参数：1、消息类型 iyzy_msg_type:"02"
 2、标题    iyzy_title:"xx医生已经给您开出了处方，请您查看"
 3、动作描述 iyzy_action:"立即查看"
 4、处方单ID iyzy_business_id:"123456"
 5、跳转到对应中药调理服务详情

 场景3：在非APP端，患者购买在线问诊服务，进入IM聊天时，医生自动给患者发消息，提醒用户视频问诊功能只有APP上面有，用户点击下载，跳转到下载APP页面（医生点击，则跳转到医生端APP下载页面）
 参数：1、消息类型 iyzy_msg_type:"03"
 2、标题    iyzy_title:"你好，你当前环境是微信小程序，暂无视频问诊功能，如发起视频问诊，无法进行服务，请前往爱尚中医APP进行视频问诊服务。"
 3、动作描述 iyzy_action:"下载APP"
 4、患者端APP下载地址 iyzy_patient_app_url:"http://xxx.com"
 5、医生端APP下载地址 iyzy_doctor_app_url:"http://xxx.com"

 */
public class OrderMsgAttachment extends CustomAttachment {

    public static final String MSG_TYPE_INTERROGATION = "01";     // 问诊单详情
    public static final String MSG_TYPE_MEDICINE = "02";          // 中药调理服务详情
    public static final String MSG_TYPE_DOWN_APP = "03";          // 下载App

    private String iyzy_msg_type;
    private String iyzy_business_id;
    private String iyzy_title;
    private String iyzy_action;
    private String iyzy_doctor_app_url;

    public OrderMsgAttachment() {
        super(1000102);
    }

    @Override
    protected void parseData(JSONObject data) {

        iyzy_msg_type = data.getString("iyzy_msg_type");             //    消息类型
        iyzy_business_id = data.getString("iyzy_business_id");       //    处方单ID / 问诊单ID
        iyzy_title = data.getString("iyzy_title");                   //    标题
        iyzy_action = data.getString("iyzy_action");                 //    动作描述
        iyzy_doctor_app_url = data.getString("iyzy_doctor_app_url"); //    动作描述 "iyzy_doctor_app_url" -> "http://www.baidu.com"


    }

    @Override
    protected JSONObject packData() {
        JSONObject data = null;
//        try {
//            data = JSONObject.parseObject(content);
//        } catch (Exception e) {
//
//        }
        return data;
    }

    public String getIyzy_msg_type() {
        return iyzy_msg_type;
    }

    public void setIyzy_msg_type(String iyzy_msg_type) {
        this.iyzy_msg_type = iyzy_msg_type;
    }

    public String getIyzy_business_id() {
        return iyzy_business_id;
    }

    public void setIyzy_business_id(String iyzy_business_id) {
        this.iyzy_business_id = iyzy_business_id;
    }

    public String getIyzy_title() {
        return iyzy_title;
    }

    public void setIyzy_title(String iyzy_title) {
        this.iyzy_title = iyzy_title;
    }

    public String getIyzy_action() {
        return iyzy_action;
    }

    public void setIyzy_action(String iyzy_action) {
        this.iyzy_action = iyzy_action;
    }

    public String getIyzy_doctor_app_url() {
        return iyzy_doctor_app_url;
    }

    public void setIyzy_doctor_app_url(String iyzy_doctor_app_url) {
        this.iyzy_doctor_app_url = iyzy_doctor_app_url;
    }
}
