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

/**
 * 我的处方单
 */

public class MyOrdonnanceActivity extends MYBaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    private static String[] TITLE_DATA = new String[]{
            "全部", "待支付", "待收货", "已完成", "已取消"
    };
    private static String[] TITLE_TYPE = new String[]{
            "", "wait_pay", "wait_receipt", "complete", "cancel"
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_ordonnance;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("我的处方单");
        initViewPager();
        int type = getIntent().getIntExtra("type", -1);
        if (type!=-1){
            mViewPager.setCurrentItem(type);
        }
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

        for (int i = 0; i < TITLE_DATA.length; i++) {
            list.add(TITLE_DATA[i]);
            mTabLayout.addTab(mTabLayout.newTab().setText(TITLE_DATA[i]));

            Bundle bundle = new Bundle();
            bundle.putString("type", TITLE_TYPE[i]);

            OrdonnanceListFragment ordonnanceListFragment = new OrdonnanceListFragment();
            ordonnanceListFragment.setArguments(bundle);
            fragmentList.add(ordonnanceListFragment);

        }


        OrderPagerAdapter adapter = new OrderPagerAdapter(getSupportFragmentManager(), list, fragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_back, R.id.tv_buyBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_buyBack:
                startActivity(new Intent(this,AfterSalesActivity.class));
                break;
        }
    }
}
