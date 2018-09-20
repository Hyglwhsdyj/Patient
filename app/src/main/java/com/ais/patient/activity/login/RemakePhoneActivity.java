package com.ais.patient.activity.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.MobileUtil;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class RemakePhoneActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    private String phone;
    private String lastStepCode;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_remake_phone;
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("更换手机号码");
        lastStepCode = getIntent().getStringExtra("lastStepCode");
    }

    @Override
    protected void initEvent() {

    }


    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_back, R.id.tv_getcode,R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_getcode:
                phone = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    showToast("请输入手机号");
                }else if (!MobileUtil.isMobile(phone)){
                    showToast("手机号不合法");
                }else {
                    Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).getCode(phone, "reset_phone");
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                        @Override
                        public void onSuccess(Object o, String info) {
                            showToast("获取成功");
                        }

                        @Override
                        public void onFailure(String info) {
                            showToast(info);
                        }
                    });
                }
                break;
            case R.id.tv_next:
                String code = etCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)){
                    showToast("请输入验证码");
                }else {
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put("phoneNumber",phone);
                    ajaxParams.put("inputCaptcha",code);
                    ajaxParams.put("lastStepCode",lastStepCode);
                    ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
                    Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).retPassWord2(urlParams);
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                        @Override
                        public void onSuccess(Object o, String info) {
                            showToast("修改成功");
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
