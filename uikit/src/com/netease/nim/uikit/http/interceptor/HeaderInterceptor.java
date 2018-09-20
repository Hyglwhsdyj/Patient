package com.netease.nim.uikit.http.interceptor;

import android.content.Context;

import com.netease.nim.uikit.http.UserUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求头添加
 */
public class HeaderInterceptor implements Interceptor {

    private Context context;

    public HeaderInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .addHeader("token", UserUtils.getUserToken(context))
                .build();
        return chain.proceed(request);
    }
}
