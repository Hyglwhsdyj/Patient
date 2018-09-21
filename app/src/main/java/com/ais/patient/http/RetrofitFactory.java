package com.ais.patient.http;

import android.content.Context;

import com.ais.patient.http.interceptor.HeaderInterceptor;
import com.ais.patient.http.interceptor.LoggingInterceptor;
import com.ais.patient.http.interceptor.TokenInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private static final String BASE_URL_PATH = ".5aszy.com";

    private static final int urlSwitch = 1;

    private static Retrofit.Builder builder;
    private static IRetrofitServer retrofitServer;

    private RetrofitFactory() {

    }

    private static String getBaseUrl() {
        String baseParent = "";
        switch (urlSwitch) {
            case 0:
                baseParent = "https://dev.patient"+ BASE_URL_PATH;   // 内网测试
                break;
            case 1:
                baseParent = "https://patient" + BASE_URL_PATH;    // 外网测试
                break;
        }
        return baseParent;
    }

    public static IRetrofitServer getInstance(Context context) {
        if (retrofitServer == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofitServer == null) {
                    builder = new Retrofit.Builder();
                    builder.baseUrl(getBaseUrl());
                    builder.addConverterFactory(GsonConverterFactory.create());
                }
            }
        }
        builder.client(getClientInstance(context));
        retrofitServer = builder.build().create(IRetrofitServer.class);
        return retrofitServer;
    }

    private static OkHttpClient getClientInstance(Context context) {
        //OkHttpClient不进行单例化处理， user_token 可能登录过时后的需要重新从本地获取后重新登录请求刷新
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor(context))
                .addInterceptor(new TokenInterceptor(context))
                .addInterceptor(new LoggingInterceptor())
                //.addInterceptor(new CookiesAddInterceptor(context))
                //.addInterceptor(new CookiesSaveInterceptor(context))
                .build();
    }
}
