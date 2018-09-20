package com.ais.patient.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.adapter.OrderPagerAdapter;
import com.ais.patient.base.MYBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCouponsActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coupons;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("我的优惠券");
        tvRight.setText("使用规则");
        initViewPager();
    }

    /**
     * 初始化viewPager
     */
    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("未使用优惠券");
        list.add("已过期优惠券");
        mTabLayout.addTab(mTabLayout.newTab().setText("未使用优惠券"));
        mTabLayout.addTab(mTabLayout.newTab().setText("已过期优惠券"));

        fragmentList.add(CouponsFragment.newInstance("INIT"));
        fragmentList.add(CouponsFragment.newInstance("EXPIRE"));

        OrderPagerAdapter adapter = new OrderPagerAdapter(getSupportFragmentManager(), list, fragmentList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
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


    @OnClick({R.id.tv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
                break;
        }
    }
}
