package com.ais.patient.activity.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.BaseFragment;
import com.ais.patient.been.MainAllInfo;
import com.ais.patient.been.MainDotcor;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.BannerImageLoader;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.CircleTransform;
import com.ais.patient.widget.FlowGroupView;
import com.ais.patient.widget.MyScrollView;
import com.ais.patient.widget.VpSwipeRefreshLayout;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/7/29 0029.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.rl_right)
    RelativeLayout rlRight;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.mBanner)
    Banner mBanner;
    @BindView(R.id.iv_robot_long)
    ImageView ivRobotLong;
    @BindView(R.id.iv_h)
    ImageView ivH;
    @BindView(R.id.mNestedScrollView)
    MyScrollView mScrollView;

    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.mSwipeRefreshLayout)
    VpSwipeRefreshLayout mSwipeRefreshView;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    private Context context;

    private int pageNum = 1;
    private int pageSize = 10;
    private RecyclerAdapter<MainDotcor.DataBean> adapter;
    List<MainDotcor.DataBean> list = new ArrayList<>();
    List<String> imgList = new ArrayList<>();
    private FlowGroupView mFlowGroupView;

    private MyReceiver myReceiver;
    private Animation animation;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View view) {
        context = getBaseActivity();
        tvTitle.setText("爱尚中医");
        tvVip.setText("VIP\n服务中心");
        tvShop.setText("健康\n特色商城");
        tvBack.setVisibility(View.GONE);
        rlRight.setVisibility(View.VISIBLE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setNestedScrollingEnabled(false);
        mSwipeRefreshView.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ais.messageread");
        context.registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void initEvent() {
        ivRobotLong.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent arg1) {
                int action=arg1.getAction();
                if(action==MotionEvent.ACTION_DOWN){//按下时，缩小
                    ScaleAnimation sa=new  ScaleAnimation(1.0f, 0.85f,1.0f,0.85f,Animation.RELATIVE_TO_SELF
                            ,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    sa.setDuration(100);//时间
                    sa.setFillAfter(true);//此句尤其注意,不写的话,你按下,他动画过后马上恢复原样。这句代码就是阻止它恢复
                    ivRobotLong.startAnimation(sa);
                    ivH.startAnimation(sa);
                }else if(action==MotionEvent.ACTION_UP){//松开，放大恢复
                    ScaleAnimation   sa=new  ScaleAnimation(0.85f, 1.0f,0.85f,1.0f,Animation.RELATIVE_TO_SELF
                            ,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    sa.setDuration(100);//时间
                    sa.setFillAfter(true);
                    ivRobotLong.startAnimation(sa);
                    ivH.startAnimation(sa);
                    startActivity(new Intent(context, RobotFindDoctorActivity.class));
                }
                return false;
            }
        });
        /**
         * 下拉刷新
         */
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                list.clear();
                mSwipeRefreshView.setRefreshing(true);
                initData();
            }
        });

        /**
         * 上拉加载
         */
        mScrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                final int myScrollViewHeight = mScrollView.getHeight();
                int height = mScrollView.getChildAt(0).getHeight();

                if (scrollY == height - myScrollViewHeight) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pageNum++;
                            loadMianListDate();
                        }
                    }, 1500);

                }
            }
        });
    }


    @Override
    protected void initData() {
        Call<MainAllInfo> call = RetrofitFactory.getInstance(context).getMainAllInfo();
        call.enqueue(new Callback<MainAllInfo>() {
            @Override
            public void onResponse(Call<MainAllInfo> call, Response<MainAllInfo> response) {
                MainAllInfo body = response.body();
                if (body.getCode() == 200) {
                    /**
                     * 消息
                     */
                    int unread_num = body.getUnread_num();
                    if (unread_num > 0) {
                        tvMsg.setVisibility(View.VISIBLE);
                        tvMsg.setText(unread_num + "");
                    } else {
                        tvMsg.setVisibility(View.GONE);
                    }
                    /**
                     * 首页广告
                     */
                    final List<MainAllInfo.AdvertBean> advert = body.getAdvert();
                    mBanner.setImageLoader(new BannerImageLoader());
                    mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
                    mBanner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
                    imgList.clear();
                    for (int i = 0; i < advert.size(); i++) {
                        String imagesUrl = advert.get(i).getImagesUrl();
                        imgList.add(imagesUrl);
                    }
                    mBanner.setImages(imgList);//设置图片源
                    //banner.setBannerTitles(null);//设置标题源
                    mBanner.start();
                    mBanner.setOnBannerClickListener(new OnBannerClickListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            String url = advert.get(position - 1).getUrl();
                            String title = advert.get(position - 1).getTitle();
                            Intent intent = new Intent(context, WebViewActivity.class);
                            intent.putExtra("url", url);
                            intent.putExtra("title", title);
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<MainAllInfo> call, Throwable t) {
                ToastUtils.show(context, "网络异常，请稍后再试");
            }
        });


        loadMianListDate();
    }

    /**
     * 推荐医生列表
     */
    public void loadMianListDate() {
        llMore.setVisibility(View.VISIBLE);
        Call<MainDotcor> call = RetrofitFactory.getInstance(context).getMainDoctorList(pageNum, pageSize);
        call.enqueue(new Callback<MainDotcor>() {
            @Override
            public void onResponse(Call<MainDotcor> call, Response<MainDotcor> response) {
                MainDotcor mainDotcor = response.body();
                List<MainDotcor.DataBean> data = mainDotcor.getData();
                if (data != null && data.size() > 0) {
                    list.addAll(data);
                    mProgressBar.setVisibility(View.VISIBLE);
                    tvMore.setVisibility(View.VISIBLE);
                    tvMore.setText("加载中...");
                    adapter = new RecyclerAdapter<MainDotcor.DataBean>(context, R.layout.main_doctor_item, list) {
                        @Override
                        protected void convert(RecyclerAdapterHelper helper, final MainDotcor.DataBean item) {
                            ImageView ivIcon = (ImageView) helper.getItemView().findViewById(R.id.iv_icon);
                            Picasso.with(context).load(item.getImage()).transform(new CircleTransform()).into(ivIcon);
                            helper.setText(R.id.tv_name, item.getName());
                            helper.setText(R.id.tv_title, item.getTitles());
                            helper.setText(R.id.tv_depart, item.getDepart());
                            helper.setText(R.id.tv_medicalInstitutions, "医院机构：" + item.getMedicalInstitutions());
                            helper.setText(R.id.tv_fee, item.getFee() + "元/次");

                            helper.setText(R.id.tv_num,"诊疗案例"+item.getCaseNum()+"个");
                            helper.setText(R.id.tv_year,"临床经验"+item.getClinicalExperience()+"年");

                            final List<String> diseaseExpertise = item.getDiseaseExpertise();
                            mFlowGroupView = (FlowGroupView) helper.getItemView().findViewById(R.id.mFlowGroupView);
                            if (diseaseExpertise != null && diseaseExpertise.size() > 0) {
                                for (int i = 0; i < diseaseExpertise.size(); i++) {
                                    addTextView(diseaseExpertise.get(i));
                                }
                            }
                            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String id = item.getId();
                                    Intent intent = new Intent(context, DoctorInfomationActivity.class);
                                    intent.putExtra("id", id);
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                } else {
                    mProgressBar.setVisibility(View.GONE);
                    tvMore.setVisibility(View.VISIBLE);
                    tvMore.setText("没有更多了");
                }
                mRecyclerView.setAdapter(adapter);
                if (mSwipeRefreshView.isRefreshing()) {
                    mSwipeRefreshView.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<MainDotcor> call, Throwable t) {
                if (mSwipeRefreshView != null) {
                    mSwipeRefreshView.setRefreshing(false);
                }
            }
        });
    }

    private void addTextView(String str) {
        TextView child = new TextView(context);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        child.setLayoutParams(params);
        child.setBackgroundResource(R.drawable.shape_diseaseexpertise);
        child.setText(str);
        child.setTextSize(11);
        child.setTextColor(context.getResources().getColor(R.color.diseaseexpertise_bg));
        mFlowGroupView.addView(child);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(myReceiver);
    }

    @OnClick({R.id.tv_back, R.id.rl_right, R.id.iv_robot_long, R.id.iv_roobot, R.id.rl_more_doctor, R.id.tv_disease, R.id.tv_depart,R.id.tv_vip, R.id.tv_shop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_right:
                startActivityForResult(new Intent(context, MessageActivity.class), 0);
                break;
            case R.id.iv_robot_long:

                break;
            case R.id.iv_roobot:
                startActivity(new Intent(context, RobotFindDoctorActivity.class));
                break;
            case R.id.rl_more_doctor:
                Intent intent = new Intent(context, MoreDoctorActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_disease:
                startActivity(new Intent(context, NotGoodListActivity.class));
                break;
            case R.id.tv_depart:
                startActivity(new Intent(context, DepartmentListActivity.class));
                break;

            case R.id.tv_vip:
                break;
            case R.id.tv_shop:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            reFreshMessageCOunt();
        }
    }


    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            reFreshMessageCOunt();
        }
    }

    private void reFreshMessageCOunt() {
        Call<MainAllInfo> call = RetrofitFactory.getInstance(context).getMainAllInfo();
        call.enqueue(new Callback<MainAllInfo>() {
            @Override
            public void onResponse(Call<MainAllInfo> call, Response<MainAllInfo> response) {
                MainAllInfo body = response.body();
                if (body.getCode() == 200) {
                    /**
                     * 消息
                     */
                    int unread_num = body.getUnread_num();
                    if (unread_num > 0) {
                        tvMsg.setVisibility(View.VISIBLE);
                        tvMsg.setText(unread_num + "");
                    } else {
                        tvMsg.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<MainAllInfo> call, Throwable t) {

            }
        });
    }
}
