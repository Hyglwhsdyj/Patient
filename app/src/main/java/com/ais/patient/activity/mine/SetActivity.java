package com.ais.patient.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.login.LoginActivity;
import com.ais.patient.activity.login.RemakePhoneActivity;
import com.ais.patient.activity.login.RemakePhoneFirstActivity;
import com.ais.patient.activity.main.MainActivity;
import com.ais.patient.app.MyApplication;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.util.ActivityManage;
import com.ais.patient.util.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class SetActivity extends MYBaseActivity {


    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_logout)
    TextView tvLogout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("设置");
        String phone = UserUtils.getPhone(this);
        if (!TextUtils.isEmpty(phone) && phone.length() > 4) {
            tvPhone.setText(phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length()));
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_back, R.id.tv_logout,R.id.rl_1, R.id.rl_2, R.id.rl_3, R.id.rl_4, R.id.rl_5, R.id.rl_6, R.id.rl_7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_logout:
                UserUtils.clearLoginUserInfo(SetActivity.this);
                MyApplication.destoryActivity("MainActivity");
                Intent intent = new Intent(SetActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.rl_1:
                startActivity(new Intent(this,RemakePhoneFirstActivity.class));
                break;
            case R.id.rl_2:
                break;
            case R.id.rl_3:
                startActivity(new Intent(this,CallBackActivity.class));
                break;
            case R.id.rl_4:
                break;
            case R.id.rl_5:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.rl_6:
                Intent intent1 = new Intent(SetActivity.this, RecommendUsActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_7:
                Intent intent2 = new Intent(this, WebViewActivity.class);
                intent2.putExtra("type",3);
                startActivity(intent2);
                break;
        }
    }
}
