package com.ais.patient.activity.chat2;

import android.content.Context;

import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.ImInfo;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import retrofit2.Call;

/**
 * Created by Administrator on 2018/8/29 0029.
 */

public class RequstIMChatUtil {

    public static void request(final Context context, final String businessId, final String doctorId) {
        //跳到问诊详情
        Call<HttpBaseBean<ImInfo>> call = RetrofitFactory.getInstance(context).getImInfo(businessId);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ImInfo>() {

            @Override
            public void onSuccess(ImInfo imInfo, String info) {
                if (imInfo != null) {
                    final String im_doctor_accid = imInfo.getIm_doctor_accid();
                    LoginInfo loginInfo = new LoginInfo(imInfo.getIm_accid(), imInfo.getIm_token());// config...
                    NIMClient.getService(AuthService.class).login(loginInfo).setCallback(new RequestCallback<LoginInfo>() {
                        @Override
                        public void onSuccess(LoginInfo param) {
                            P2PMessageActivity.start(context, im_doctor_accid, null, null, "医生", businessId, doctorId);
                        }

                        @Override
                        public void onFailed(int code) {
                        }

                        @Override
                        public void onException(Throwable exception) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(String info) {

            }

        });
    }
}
