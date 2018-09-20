package com.ais.patient.activity.seedoctor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.adapter.OrderPagerAdapter;
import com.ais.patient.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/7/29 0029.
 */

public class SeeDoctorFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.tv_back)
    TextView tvBack;
    Unbinder unbinder;
    private MyReceiver myReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_seedotor;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getBaseActivity().unregisterReceiver(myReceiver);
    }

    @Override
    protected void initViews(View view) {
        tvTitle.setText("我的问诊");
        tvBack.setVisibility(View.GONE);
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ais.jump");
        getBaseActivity().registerReceiver(myReceiver,intentFilter);
        initViewPager();
    }

    /**
     * 初始化viewPager
     */
    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        ;
        list.add("最新问诊");
        list.add("问诊记录");
        mTabLayout.addTab(mTabLayout.newTab().setText("最新问诊"));
        mTabLayout.addTab(mTabLayout.newTab().setText("问诊记录"));
        /**
         * 3.16 我的问诊列表-问诊记录
         3.16.1描述
         */
        fragmentList.add(new NewChatOnLineFragment());
        fragmentList.add(new RecordChatOnLineFragment());

        OrderPagerAdapter adapter = new OrderPagerAdapter(getFragmentManager(), list, fragmentList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            mViewPager.setCurrentItem(1);
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
