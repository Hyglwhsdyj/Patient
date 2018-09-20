package com.ais.patient.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ais.patient.R;
import com.ais.patient.activity.main.MainActivity;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.util.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    List<View> list = new ArrayList<>();
    private ViewPager mViewPager;
    private LinearLayout llClick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_welcome);
        initViews();
    }

    protected void initViews() {
        if (UserUtils.getIsFirstLogin(this)){
            mViewPager = (ViewPager)findViewById(R.id.mViewPager);
            llClick = (LinearLayout) findViewById(R.id.ll_click);
            ImageView imageView1 = new ImageView(this);
            imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView1.setImageResource(R.mipmap.vp_1);
            list.add(imageView1);
            ImageView imageView2 = new ImageView(this);
            imageView2.setImageResource(R.mipmap.vp_2);
            imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
            list.add(imageView2);
            ImageView imageView3 = new ImageView(this);
            imageView3.setImageResource(R.mipmap.vp_3);
            imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
            list.add(imageView3);
            ImageView imageView4 = new ImageView(this);
            imageView4.setImageResource(R.mipmap.vp_4);
            imageView4.setScaleType(ImageView.ScaleType.FIT_XY);
            list.add(imageView4);
            WecomeAdaper adaper = new WecomeAdaper();
            mViewPager.setAdapter(adaper);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position==3){
                        llClick.setVisibility(View.VISIBLE);
                    }else {
                        llClick.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            llClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                    startActivity(intent);
                    UserUtils.saveIsFirstLogin(WelcomeActivity.this,false);
                    finish();
                }
            });

        }else {
            if (UserUtils.getUserToken(WelcomeActivity.this)!=null && !TextUtils.isEmpty(UserUtils.getUserToken(WelcomeActivity.this))){
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public class WecomeAdaper extends PagerAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return (view==object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(list.get(position));
        }
    }

}
