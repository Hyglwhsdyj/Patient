package com.ais.patient.activity.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;

public class MakeSurePayActivity extends MYBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_make_sure_pay;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        String advertUrl = getIntent().getStringExtra("AdvertUrl");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }
}
