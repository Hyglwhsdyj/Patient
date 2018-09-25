package com.ais.patient.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.MainDotcor;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.widget.CircleTransform;
import com.ais.patient.widget.FlowGroupView;
import com.ais.patient.widget.MyScrollView;
import com.ais.patient.widget.VpSwipeRefreshLayout;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreDoctorActivity extends MYBaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.mScrollView)
    MyScrollView mScrollView;
    @BindView(R.id.mVpSwipeRefreshLayout)
    VpSwipeRefreshLayout mVpSwipeRefreshLayout;
    private Context context;
    private int pageNum=1;
    private int pageSize=10;
    private RecyclerAdapter<MainDotcor.DataBean> adapter;
    List<MainDotcor.DataBean> list = new ArrayList<>();
    private FlowGroupView mFlowGroupView;
    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_doctor;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        tvTitle.setText("名医推荐");
        type = getIntent().getIntExtra("type", -1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setNestedScrollingEnabled(false);
        mVpSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
    }

    /**
     * 推荐医生列表
     */
    public void loadMianListDate(){
        llMore.setVisibility(View.VISIBLE);
        Call<MainDotcor> call = RetrofitFactory.getInstance(context).getMainDoctorList(pageNum, pageSize);
        call.enqueue(new Callback<MainDotcor>() {
            @Override
            public void onResponse(Call<MainDotcor> call, Response<MainDotcor> response) {
                MainDotcor mainDotcor = response.body();
                List<MainDotcor.DataBean> data = mainDotcor.getData();
                if (data.size()>0){
                    list.addAll(data);
                    mProgressBar.setVisibility(View.VISIBLE);
                    tvMore.setVisibility(View.VISIBLE);
                    tvMore.setText("加载中...");
                    adapter = new RecyclerAdapter<MainDotcor.DataBean>(context, R.layout.main_doctor_item,list) {
                        @Override
                        protected void convert(RecyclerAdapterHelper helper, final MainDotcor.DataBean item) {
                            ImageView ivIcon = (ImageView) helper.getItemView().findViewById(R.id.iv_icon);
                            Picasso.with(context).load(item.getImage()).transform(new CircleTransform()).into(ivIcon);
                            helper.setText(R.id.tv_name,item.getName());
                            helper.setText(R.id.tv_title,item.getTitles());
                            helper.setText(R.id.tv_depart,item.getDepart());
                            helper.setText(R.id.tv_medicalInstitutions,"医院机构："+item.getMedicalInstitutions());
                            helper.setText(R.id.tv_fee,item.getFee()+"元/次");
                            helper.setText(R.id.tv_num,"诊疗案例"+item.getCaseNum()+"个");
                            helper.setText(R.id.tv_year,"临床经验"+item.getClinicalExperience()+"年");

                            final List<String> diseaseExpertise = item.getDiseaseExpertise();
                            mFlowGroupView = (FlowGroupView) helper.getItemView().findViewById(R.id.mFlowGroupView);
                            if (diseaseExpertise!=null && diseaseExpertise.size()>0){
                                for (int i = 0; i < diseaseExpertise.size(); i++) {
                                    addTextView(diseaseExpertise.get(i));
                                }
                            }
                            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String id = item.getId();
                                    Intent intent = new Intent(context,DoctorInfomationActivity.class);
                                    intent.putExtra("id",id);
                                    startActivity(intent);
                                }
                            });
                            if (type==1){
                                helper.setVisible(R.id.tv_choose_other,true);
                            }else {
                                helper.setVisible(R.id.tv_choose_other,false);
                            }

                        }
                    };
                }else {
                    mProgressBar.setVisibility(View.GONE);
                    tvMore.setVisibility(View.VISIBLE);
                    tvMore.setText("没有更多了");
                }
                mRecyclerView.setAdapter(adapter);
                if (mVpSwipeRefreshLayout.isRefreshing()){
                    mVpSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<MainDotcor> call, Throwable t) {
                if (mVpSwipeRefreshLayout!=null){
                    mVpSwipeRefreshLayout.setRefreshing(false);
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
    protected void initEvent() {
        /**
         * 下拉刷新
         */
        mVpSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                mVpSwipeRefreshLayout.setRefreshing(true);
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
                    },1000);

                }
            }
        });
    }

    @Override
    protected void initData() {
        loadMianListDate();
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
