package com.ais.patient.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.mine.WebViewActivity;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.Login;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.LogUtils;
import com.ais.patient.util.MD5;
import com.ais.patient.util.MobileUtil;
import com.ais.patient.widget.MyCountDownTimer;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends MYBaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.CheckBox_aggare)
    CheckBox CheckBoxAggare;

    private MyCountDownTimer myCountDownTimer;
    private String phoneNumber;
    private String inputCaptcha;
    private String passWord;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("注册");
        myCountDownTimer = new MyCountDownTimer(tvGetcode,120000,1000);
        /**
         * 禁止输入空格和特殊字符
         */
        MobileUtil.setEditTextInhibitInputSpace(etPhone);
        MobileUtil.setEditTextInhibitInputSpeChat(etPhone);

        MobileUtil.setEditTextInhibitInputSpace(etCode);
        MobileUtil.setEditTextInhibitInputSpeChat(etCode);

        MobileUtil.setEditTextInhibitInputSpace(etPassword);
        MobileUtil.setEditTextInhibitInputSpeChat(etPassword);



        /**
         * 设置密码隐藏
         */
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_back, R.id.tv_getcode, R.id.tv_mand, R.id.tv_register, R.id.tv_tologin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_getcode:
                phoneNumber = etPhone.getText().toString().trim();
                if(TextUtils.isEmpty(phoneNumber)){
                    showToast("请输入手机号");
                }else {
                    if (MobileUtil.isMobile(phoneNumber)){
                        requestGetCode();
                    }else {
                        showToast("手机号不合法");
                    }
                }
                break;
            case R.id.tv_mand:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.tv_register:
                phoneNumber = etPhone.getText().toString().trim();
                inputCaptcha = etCode.getText().toString().trim();
                passWord = etPassword.getText().toString().trim();
                if(TextUtils.isEmpty(phoneNumber)){
                    showToast("请输入手机号");
                }else if (TextUtils.isEmpty(inputCaptcha)){
                    showToast("请输入验证码");
                } else if (TextUtils.isEmpty(passWord)){
                    showToast("请输入密码");
                }else if (passWord.length()<6){
                    showToast("设置6位以上密码");
                }else{
                    if (MobileUtil.isMobile(phoneNumber)){
                        requestRegister();
                    }else {
                        showToast("手机号不合法");
                    }
                }
                break;
            case R.id.tv_tologin:
                finish();
                break;
        }
    }

    private void requestRegister() {
        //Call<Login> call = RetrofitFactory.getInstance(this).register(phoneNumber, inputCaptcha, MD5.getMD5(passWord));
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("phoneNumber",phoneNumber);
        ajaxParams.put("inputCaptcha",inputCaptcha);
        ajaxParams.put("passWord",passWord);
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Call<Login> call = RetrofitFactory.getInstance(this).register(urlParams);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = response.body();
                if (login.getCode()==200){
                    showToast("注册成功");
                    finish();
                }else showToast(login.getMessage());
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                showToast("网络异常，请稍后再试");
            }
        });
    }

    private void requestGetCode() {
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).getCode(phoneNumber,"register");
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                showToast("发送成功");
                myCountDownTimer.start();
            }

            @Override
            public void onFailure(String info) {
                showToast(info);
            }
        });
    }
}
