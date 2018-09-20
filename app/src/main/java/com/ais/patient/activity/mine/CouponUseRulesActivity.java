package com.ais.patient.activity.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CouponUseRulesActivity extends MYBaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_use_rules;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("如何使用优惠券");
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

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
