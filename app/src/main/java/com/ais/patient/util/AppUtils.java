package com.ais.patient.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

public class AppUtils {

    private AppUtils() {
    }

    /**
     * 获取当前应用的版本号
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 1;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0); // 获取包信息
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取当前应用的版本名称
     */
    public static String getAppVersionName(Context context) {
        String versionName = "1.0";
        try {
            PackageManager pmanager = context.getPackageManager();
            PackageInfo packageInfo = pmanager.getPackageInfo(
                    context.getPackageName(), 0); // 获取包信息
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * 取得当前的进程名
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                    .getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        }
        return null;
    }

    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     * 验证邮箱格式
     */
//    public static boolean isEmail(String email) {
//        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
//        Pattern p = Pattern.compile(str);
//        Matcher m = p.matcher(email);
//        return m.matches();
//    }

    /**
     * 验证手机号格式
     */
    public static boolean isMobile(String mobiles) {
//		  移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
//		  联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通） 网络：17
//		  第一位必定为1，第二位必定为3或5或7或8，其他位置的可以为0-9

        String telRegex = "[129][3456789]\\d{9}";// "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    /**
     * 隐藏手机号中间四位数字
     */
    public static String hidePhoneNum(String tel) {
        if (tel.length() == 11) {
            return tel.substring(0, 3) + "****"
                    + tel.substring(7, tel.length());
        } else {
            return tel;
        }
    }
}
