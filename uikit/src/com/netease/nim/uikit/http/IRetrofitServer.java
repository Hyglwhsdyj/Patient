package com.netease.nim.uikit.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/9/9 0009.
 */

public interface IRetrofitServer {
    /**
     * 问诊单填写第几步
     * @param url
     * @param recordId
     * @return
     */
    @GET
    Call<HttpBaseBean<WichStep>> getWhichStep(@Url String url, @Query("recordId")String recordId);
}
