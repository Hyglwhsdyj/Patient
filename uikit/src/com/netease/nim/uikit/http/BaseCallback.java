package com.netease.nim.uikit.http;



import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseCallback<T> {

    private Call<HttpBaseBean<T>> mCall;

    public BaseCallback(Call call) {
        mCall = call;
    }

    public void handleResponse(final ResponseListener listener) {
        mCall.enqueue(new Callback<HttpBaseBean<T>>() {
            @Override
            public void onResponse(Call<HttpBaseBean<T>> call, Response<HttpBaseBean<T>> response) {
                Log.i("datas",response.isSuccessful()+"Body  "+response.errorBody());
                if (response.isSuccessful() && response.errorBody() == null) {
                    if ("200".equals(response.body().code+"")) {
                        listener.onSuccess(response.body().getData(),response.body().getMessage());
                    } else {
                        listener.onFailure(response.body().getMessage());
                    }
                } else {
                    listener.onFailure("网络异常，请稍后再试");
                }
            }

            @Override
            public void onFailure(Call<HttpBaseBean<T>> call, Throwable t) {
                listener.onFailure("网络异常，请稍后再试");
            }
        });
    }

    public interface ResponseListener<T> {
        void onSuccess(T t, String info);

        void onFailure(String info);
    }
}