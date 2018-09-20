package com.netease.nim.uikit.http;

import android.content.Context;

import com.netease.nim.uikit.SPUtils;

/**
 * Created by Administrator on 2018/9/9 0009.
 */

public class UserUtils {
    /**
     * 获取登录用户token
     */
    public static String getUserToken(Context context) {
        return SPUtils.getString(context, "user_token", "");
    }
}
