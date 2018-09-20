package com.ais.patient.http.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * token拦截判断
 */
public class TokenInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private Context context;

    public TokenInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response originalResponse = chain.proceed(request);

        /**
         * 通过如下的办法曲线取到请求完成的数据
         */

        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        String bodyString = buffer.clone().readString(charset);

        Gson gson = new Gson();
        try {
            HttpBaseBean mBaseBean = gson.fromJson(bodyString, HttpBaseBean.class);

            if (TextUtils.equals(mBaseBean.code+"", "1000")) { //登录失效，请重新登录

                /*SUCCESS(true, "0000", "请求成功"), FAILURE(false, "9999", "网络错误,请稍后重试"),
                        LOGIN_INVALID(false, "1000", "登录已失效，请重新登录"),
                        TOKEN_BLANK(false, "1001", "Token为空"),
                        ACCOUNT_DISABLED(false, "1002", "账号已禁用");*/

                LogUtils.e("登录失效: 重新登录");

                /*UserUtils.clearUserInfo(context);
                UserUtils.clearUserPassWord(context);
                LoginActivity.startActivity(context, true);  //跳转到登录页面*/

                originalResponse.body().close();
            }
            if (TextUtils.equals(mBaseBean.code+"", "1001")) { //Token为空

                /*SUCCESS(true, "0000", "请求成功"), FAILURE(false, "9999", "网络错误,请稍后重试"),
                        LOGIN_INVALID(false, "1000", "登录已失效，请重新登录"),
                        TOKEN_BLANK(false, "1001", "Token为空"),
                        ACCOUNT_DISABLED(false, "1002", "账号已禁用");*/

                /*LogUtils.e("登录失效: 重新登录");

                UserUtils.clearUserInfo(context);
                LoginActivity.startActivity(context, true);  //跳转到登录页面*/

                originalResponse.body().close();
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return originalResponse;
    }
}