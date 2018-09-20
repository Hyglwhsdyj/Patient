package com.ais.patient.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.main.MainActivity;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.ImInfo;
import com.ais.patient.been.Login;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.LogUtils;
import com.ais.patient.util.MD5;
import com.ais.patient.util.MobileUtil;
import com.ais.patient.util.UserUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends MYBaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.CheckBox_isRemenber)
    CheckBox CheckBoxIsRemenber;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("登录");
        /**
         * 禁止输入空格和特殊字符
         */
        MobileUtil.setEditTextInhibitInputSpace(etPhone);
        MobileUtil.setEditTextInhibitInputSpeChat(etPhone);

        MobileUtil.setEditTextInhibitInputSpace(etPassword);
        MobileUtil.setEditTextInhibitInputSpeChat(etPassword);

        /**
         * 设置密码隐藏
         */
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        String phone = UserUtils.getPhone(this);
        String password = UserUtils.getPassword(this);
        if (!TextUtils.isEmpty(phone)){
            etPhone.setText(phone);
            etPhone.setSelection(phone.length());
        }
        if (!TextUtils.isEmpty(password)){
            etPassword.setText(password);
            etPassword.setSelection(password.length());
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_back, R.id.tv_forgetPassword, R.id.tv_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                break;
            case R.id.tv_forgetPassword:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.tv_login:
                String phone = etPhone.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    showToast("请输入手机号");
                }else if (TextUtils.isEmpty(password)){
                    showToast("请输入密码");
                }else if (!MobileUtil.isMobile(phone)){
                    showToast("手机号不合法");
                }else {
                    requestLogin(phone,password);
                }
                break;
            case R.id.tv_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    /**
     * 登录
     * @param phone
     * @param password
     */
    private void requestLogin(final String phone, final String password) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("loginName",phone);
        ajaxParams.put("passWord",password);
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        LogUtils.e(urlParams.toString());
        Call<Login> call = RetrofitFactory.getInstance(this).login(urlParams);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = response.body();
                if (login.getCode()==200){
                    showToast("登录成功");
                    //保存登录用户信息
                    UserUtils.saveLoginUserInfo(LoginActivity.this,login);
                    if (CheckBoxIsRemenber.isChecked()){
                        UserUtils.savePhonePassword(LoginActivity.this,phone,password);
                    }else {
                        UserUtils.savePhonePassword(LoginActivity.this,"","");
                    }
                    /**
                     * IM登录
                     */
                    //doLogin();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else {
                    showToast(login.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                showToast("网络异常，请稍后再试");
            }
        });
    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
