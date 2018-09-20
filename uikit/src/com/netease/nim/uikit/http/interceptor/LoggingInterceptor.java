package com.netease.nim.uikit.http.interceptor;




import com.netease.nim.uikit.http.LogUtils;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * http请求打印
 */
public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        LogUtils.e(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));

        long t1 = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        LogUtils.e(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtils.e(content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}