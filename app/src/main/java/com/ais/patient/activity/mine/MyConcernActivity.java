package com.ais.patient.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.adapter.OrderPagerAdapter;
import com.ais.patient.base.BaseActivity;
import com.ais.patient.base.MYBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyConcernActivity extends MYBaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    private static String[] TITLE_DATA = new String[]{
            "关注的医生", "医生动态"
    };
    private static String[] TITLE_TYPE = new String[]{
            "0", "1"
    };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_concern;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        tvTitle.setText(getResources().getString(R.string.myconcern));
        initViewPager();
        int type = getIntent().getIntExtra("type", -1);
        if (type!=-1){
            mViewPager.setCurrentItem(0);
        }
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

            MyConcernFragment mMyConcernFragment = new MyConcernFragment();
            mMyConcernFragment.setArguments(bundle);
            fragmentList.add(mMyConcernFragment);

        }


        OrderPagerAdapter adapter = new OrderPagerAdapter(getSupportFragmentManager(), list, fragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }
  @OnClick({R.id.tv_back})
  public void setOnClick(View view){
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
        }
  }
}
