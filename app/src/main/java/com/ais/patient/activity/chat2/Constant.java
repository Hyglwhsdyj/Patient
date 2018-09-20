package com.ais.patient.activity.chat2;

/**
 * Created by beryl on 2017/11/6.
 */

public class Constant {

    // 视频通话（声网https://dashboard.agora.io/projects） AppKey  登录接口返回
    public static String VIDEO_KEY_APP_KEY = "";

    public static final String ACTION_VIDEO_CALL_BACK = "0x03";
    public static int CALL_IN = 0x01;
    public static int CALL_OUT = 0x02;

    private static long timeLast;

    public static boolean isFastlyClick() {
        if (System.currentTimeMillis() - timeLast < 1500) {
            timeLast = System.currentTimeMillis();
            return true;
        } else {
            timeLast = System.currentTimeMillis();
            return false;
        }
    }
}
