package com.ais.patient.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ais.patient.been.Login;
import com.ais.patient.constant.UserConstant;
import com.ais.patient.wxapi.WXPayEntryActivity;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class UserUtils {

    /**
     * 退出登陆，清除登陆信息
     * @param context
     */
    public static void clearLoginUserInfo(Context context){
        SharedPreferences.Editor editor = SPUtils.getSp(context).edit();
        editor.putString(UserConstant.Userid,"");
        editor.putString(UserConstant.USER_TOKEN,"");
        editor.putString(UserConstant.PASSWORD,"");
        editor.apply();
    }

    /**
     * 保存登录信息
     * @param context
     */
    public static void saveLoginUserInfo(Context context, Login login) {
        SharedPreferences.Editor editor = SPUtils.getSp(context).edit();
        editor.putString(UserConstant.Userid,login.getUserid());
        editor.putString(UserConstant.USER_TOKEN,login.getToken());
        editor.putString(UserConstant.APP_ID,login.getAppId());
        editor.apply();
    }


    /**
     * 保存IM登录信息
     * @param context
     * @param
     */
    public static void saveIMLoginInfo(Context context, LoginInfo loginInfo) {
        SharedPreferences.Editor editor = SPUtils.getSp(context).edit();
        editor.putString(UserConstant.IM_ACCOUT,loginInfo.getAccount());
        editor.putString(UserConstant.IM_TOKEN,loginInfo.getToken());
        editor.apply();
    }

    /**
     * 获取IM 账号
     * @param context
     * @return
     */
    public static String getIMAccount(Context context) {
        return SPUtils.getString(context, UserConstant.IM_ACCOUT, "");
    }

    /**
     * 获取 Appid
     * @param context
     * @return
     */
    public static String getAppId(Context context) {
        return SPUtils.getString(context, UserConstant.APP_ID, "");
    }

    /**
     * 获取IM Toke
     * @param context
     * @return
     */
    public static String getIMToken(Context context) {
        return SPUtils.getString(context, UserConstant.IM_TOKEN, "");
    }



    /**
     * 获取登录用户token
     */
    public static String getUserToken(Context context) {
        return SPUtils.getString(context, UserConstant.USER_TOKEN, "");
    }

    /**
     * 获取登录用户UserId
     */
    public static String getUserId(Context context) {
        return SPUtils.getString(context, UserConstant.Userid, "");
    }

    /**
     * 保存用户手机号密码
     * @param context
     * @param phone
     * @param password
     */
    public static void savePhonePassword(Context context, String phone, String password) {
        SPUtils.putString(context,UserConstant.PHONE,phone);
        SPUtils.putString(context,UserConstant.PASSWORD,password);
    }

    public static String getPhone(Context context) {
        return SPUtils.getString(context, UserConstant.PHONE, "");
    }

    public static String getPassword(Context context) {
        return SPUtils.getString(context, UserConstant.PASSWORD, "");
    }

    /**
     * 保存是否第一次登陆
     * @param context
     * @param isFirst
     */
    public static void saveIsFirstLogin(Context context,boolean isFirst) {
        SPUtils.putBoolean(context,UserConstant.ISFIRST,isFirst);
    }

    public static boolean getIsFirstLogin(Context context) {
        return SPUtils.getBoolean(context, UserConstant.ISFIRST, true);
    }

    /**
     * 判断是购买在线还是面诊
     * @param context
     * @param flag
     */
    public static void saveChatOnLineOrMeeting(Context context,int flag) {
        SPUtils.putInt(context,UserConstant.OnLineOrMeeting,flag);
    }

    public static void saveChatOnLineDOCTORID(Context context,String doctorId) {
        SPUtils.putString(context,UserConstant.DOCTORID,doctorId);
    }

    public static void saveChatOnLineRECORDID(Context context,String recordId) {
        SPUtils.putString(context,UserConstant.RECORDID,recordId);
    }

    public static int getChatOnLineOrMeeting(Context context) {
        return SPUtils.getInt(context, UserConstant.OnLineOrMeeting,0);
    }

    public static String getChatOnLineDoctorId(Context context) {
        return SPUtils.getString(context, UserConstant.DOCTORID);
    }

    public static String getChatOnLineRecordId(Context context) {
        return SPUtils.getString(context, UserConstant.RECORDID);
    }


    /**
     * 判断患者男女
     * @param context
     * @param
     */
    public static void saveSex(Context context,String sex) {
        SPUtils.putString(context,UserConstant.SEX,sex);
    }

    public static String getSex(Context context) {
        return SPUtils.getString(context, UserConstant.SEX,"女");
    }


}
