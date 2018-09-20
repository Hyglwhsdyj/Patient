package com.ais.patient.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.adapter.OrderPagerAdapter;
import com.ais.patient.base.MYBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyReservationActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_reservation;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("我的预约");
        initViewPager();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化viewPager
     */
    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("最新预约");
        list.add("预约记录");
        mTabLayout.addTab(mTabLayout.newTab().setText("最新预约"));
        mTabLayout.addTab(mTabLayout.newTab().setText("预约记录"));

        fragmentList.add(new NewReservationFragment());
        fragmentList.add(new RecordReservationFragment());

        OrderPagerAdapter adapter = new OrderPagerAdapter(getSupportFragmentManager(),list,fragmentList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
