package com.ais.patient.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.LastCode;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.MD5;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class RemakePhoneFirstActivity extends MYBaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remake_phone_first;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("更换手机号码");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_back, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_next:
                String password = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(password)){
                    showToast("请输入登录密码");
                }else {
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put("passWord", MD5.getMD5(password));
                    ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
                    Call<HttpBaseBean<LastCode>> call = RetrofitFactory.getInstance(this).retPassWord(urlParams);
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<LastCode>() {

                        @Override
                        public void onSuccess(LastCode lastCode, String info) {
                            showToast("验证成功");
                            Intent intent = new Intent(RemakePhoneFirstActivity.this,RemakePhoneActivity.class);
                            intent.putExtra("lastStepCode",lastCode.getLastStepCode());
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(String info) {
                            showToast(info);
                        }
                    });
                }
                break;
        }
    }
}
