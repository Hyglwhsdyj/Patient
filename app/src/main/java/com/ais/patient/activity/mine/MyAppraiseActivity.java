package com.ais.patient.activity.mine;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.adapter.OrderPagerAdapter;
import com.ais.patient.base.MYBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAppraiseActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_appraise;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("我的评价");
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
        List<String> list = new ArrayList<>();;
        list.add("全部评价");
        list.add("在线问诊");
        list.add("线下面诊");
        list.add("中药调理");
        mTabLayout.addTab(mTabLayout.newTab().setText("全部评价"));
        mTabLayout.addTab(mTabLayout.newTab().setText("在线问诊"));
        mTabLayout.addTab(mTabLayout.newTab().setText("线下面诊"));
        mTabLayout.addTab(mTabLayout.newTab().setText("中药调理"));

        fragmentList.add(AppraiseFragment.newInstance(0));
        fragmentList.add(AppraiseFragment.newInstance(1));
        fragmentList.add(AppraiseFragment.newInstance(2));
        fragmentList.add(AppraiseFragment.newInstance(3));
        OrderPagerAdapter adapter = new OrderPagerAdapter(getSupportFragmentManager(),list,fragmentList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }


}
