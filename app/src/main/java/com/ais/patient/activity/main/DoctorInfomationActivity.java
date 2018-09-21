package com.ais.patient.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.mine.DoctorDetailActivity;
import com.ais.patient.adapter.MyGridViewAdapter;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.AppraiseDetail;
import com.ais.patient.been.DoctorMsg;
import com.ais.patient.been.HaveMeet;
import com.ais.patient.been.HealthTemp;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.ImInfo;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.IRetrofitServer;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.BannerImageLoader;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.CircleTransform;
import com.ais.patient.widget.MyScrollView;
import com.ais.patient.widget.NetstedGridView;
import com.ais.patient.widget.NetstedListView;
import com.bumptech.glide.Glide;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DoctorInfomationActivity extends MYBaseActivity {

    private static Retrofit.Builder builder;
    private static IRetrofitServer retrofitServer;
    @BindView(R.id.checkbox_save)
    CheckBox checkboxSave;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_depart)
    TextView tvDepart;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_istuijian)
    TextView tvIstuijian;
    @BindView(R.id.tv_medicalInstitutions)
    TextView tvMedicalInstitutions;
    @BindView(R.id.tv_back_number)
    TextView tvBackNumber;
    @BindView(R.id.tv_revisit)
    TextView tvRevisit;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    @BindView(R.id.tv_chat_type)
    TextView tvChatType;
    @BindView(R.id.tv_chatonline)
    TextView tvChatonline;
    @BindView(R.id.tv_announcementContent)
    TextView tvAnnouncementContent;
    @BindView(R.id.tvannouncementTime)
    TextView tvannouncementTime;
    @BindView(R.id.ll_announcement)
    LinearLayout llAnnouncement;
    @BindView(R.id.mNetstedGridView)
    NetstedGridView mNetstedGridView;
    @BindView(R.id.mBanner)
    Banner mBanner;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_make_meet)
    RelativeLayout rlMakeMeet;
    @BindView(R.id.checkbox_announcement)
    CheckBox checkboxAnnouncement;
    @BindView(R.id.tv_introl)
    TextView tvIntrol;
    @BindView(R.id.ck_more1)
    CheckBox cbMore1;
    @BindView(R.id.tv_more2)
    TextView tvMore2;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.mListView)
    NetstedListView mListView;
    @BindView(R.id.mListView2)
    NetstedListView mListView2;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.mScrollView)
    MyScrollView mScrollView;
    @BindView(R.id.tv_more3)
    TextView tvMore3;
    private String baseUrl;
    private Context context;
    private boolean appointment;
    private List<DoctorMsg.ArticlesBean> articles = new ArrayList<>();
    private List<DoctorMsg.ArticlesBean> articlesBeans = new ArrayList<>();

    List<HealthTemp> healthList = new ArrayList<>();
    List<HealthTemp> healthListAll = new ArrayList<>();

    private RecyclerAdapter<DoctorMsg.ArticlesBean> adapter;
    private String doctorId;
    private String appraiseUrl;

    private int pageNum = 1;
    private int pageSize = 10;

    private int pageNum2 = 1;
    private int pageSize2 = 100;


    List<AppraiseDetail.DataBean> list = new ArrayList<>();
    private boolean inquiry;
    public DoctorMsg doctorMsg2;
    private String name;
    private List<String> diseaseExpertise;
    private List<String> certificatePics;
    private AlertDialog alertDialog;
    private List<String> diseaseList = new ArrayList<>();
    private MyGridViewAdapter myGridViewAdapter;
    private Adapter<HealthTemp> healthTempAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_infomation;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        initFocus();
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        doctorId = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(doctorId)) {
            baseUrl = "/api/doctor/index/" + doctorId;
            appraiseUrl = "api/doctor/appraise/" + doctorId;
        }

        myGridViewAdapter = new MyGridViewAdapter(this, diseaseList);
        mNetstedGridView.setAdapter(myGridViewAdapter);
        mNetstedGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (diseaseList.size() > 4) {
                    if (position == diseaseList.size() - 1) {
                        if (diseaseList.get(position).equals("查看更多")) {
                            diseaseList.clear();
                            diseaseList.addAll(diseaseExpertise);
                            diseaseList.add("收起更多");
                            myGridViewAdapter.notifyDataSetChanged();
                        } else {
                            diseaseList.clear();
                            for (int i = 0; i < 5; i++) {
                                diseaseList.add(diseaseExpertise.get(i));
                            }
                            diseaseList.add("查看更多");
                            myGridViewAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        healthTempAdapter = new Adapter<HealthTemp>(context, R.layout.health_temp_item, healthList) {
            @Override
            protected void convert(AdapterHelper helper, final HealthTemp item) {
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_time, item.getTime());
                helper.setText(R.id.tv_content, item.getContent());
                TextView tvLook = helper.getItemView().findViewById(R.id.tv_look);
                tvLook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cureId = item.getCureId();
                        Intent intent = new Intent(context, HealthTempDetailActivity.class);
                        intent.putExtra("id", cureId);
                        startActivity(intent);
                    }
                });
            }
        };
        mListView2.setAdapter(healthTempAdapter);
    }

    /**
     * 让banner子控件获取焦点
     * 这样scrollview不会初始化时候下滑到下面
     */
    private void initFocus() {
        ivBack.setFocusable(true);
        ivBack.setFocusableInTouchMode(true);
        ivBack.requestFocus();
    }

    @Override
    protected void initEvent() {

        cbMore1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvIntrol.setMaxLines(Integer.MAX_VALUE);//把TextView行数显示取消掉
                    cbMore1.setText("收起更多");
                } else {
                    tvIntrol.setMaxLines(3);//超过10行就设置只能显示10行
                    cbMore1.setText("查看更多");
                }
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
                            loadAppraise();
                        }
                    }, 1000);

                }
            }
        });

        checkboxAnnouncement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llAnnouncement.setVisibility(View.VISIBLE);
                } else {
                    llAnnouncement.setVisibility(View.GONE);
                }
            }
        });

        checkboxSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).saveDoctor(doctorId);
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                        @Override
                        public void onSuccess(Object o, String info) {
                            checkboxSave.setText("已关注");
                            showToast("关注成功");
                        }

                        @Override
                        public void onFailure(String info) {
                            showToast(info);
                        }
                    });
                } else {
                    Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).unSaveDoctor(doctorId);
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                        @Override
                        public void onSuccess(Object o, String info) {
                            checkboxSave.setText("关注");
                            showToast("取消关注");
                        }

                        @Override
                        public void onFailure(String info) {
                            showToast(info);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void initData() {
        /**
         * 获取医生个人信息
         */
        if (!TextUtils.isEmpty(baseUrl)) {
            Call<HttpBaseBean<DoctorMsg>> call = RetrofitFactory.getInstance(this).getDoctorImformation(baseUrl);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<DoctorMsg>() {

                @Override
                public void onSuccess(DoctorMsg doctorMsg, final String info) {
                    doctorMsg2 = doctorMsg;
                    if (doctorMsg != null) {
                        certificatePics = doctorMsg.getCertificatePics();
                        showDoctorDialog();
                        if (doctorMsg.isFollow()) {
                            checkboxSave.setChecked(true);
                            checkboxSave.setText("已关注");
                        } else {
                            checkboxSave.setChecked(false);
                            checkboxSave.setText("关注");
                        }

                        Picasso.with(context)
                                .load(doctorMsg
                                        .getImage())
                                .transform(new CircleTransform())
                                .error(R.mipmap.ic_doctor_icon_bg)
                                .into(ivIcon);
                        name = doctorMsg.getName();
                        if (!TextUtils.isEmpty(name)) {
                            tvName.setText(name);
                        }
                        String titles = doctorMsg.getTitles();
                        if (!TextUtils.isEmpty(titles)) {
                            tvTitle.setText(titles);
                        }
                        if (doctorMsg.isRecommend()) {
                            tvIstuijian.setVisibility(View.VISIBLE);
                        } else {
                            tvIstuijian.setVisibility(View.GONE);
                        }

                        String depart = doctorMsg.getDepart();
                        if (!TextUtils.isEmpty(depart)) {
                            tvDepart.setText(depart);
                        }

                        String medicalInstitutions = doctorMsg.getMedicalInstitutions();
                        if (!TextUtils.isEmpty(medicalInstitutions)) {
                            tvMedicalInstitutions.setText("医院机构：" + medicalInstitutions);
                        }

                        int caseNum = doctorMsg.getCaseNum();
                        tvBackNumber.setText("诊疗案例" + caseNum + "个");

                        int experience = doctorMsg.getClinicalExperience();
                        tvRevisit.setText("临床经验" + experience + "年");

                        double fee = doctorMsg.getFee();
                        tvFee.setText(fee + "元/次");
                        inquiry = doctorMsg.isInquiry();
                        if (!inquiry) {
                            tvChatonline.setBackgroundResource(R.drawable.shape_online_no);
                        } else {
                            tvChatonline.setBackgroundResource(R.drawable.shape_online);
                        }

                        appointment = doctorMsg.isAppointment();

                        String announcementContent = doctorMsg.getAnnouncementContent();
                        if (!TextUtils.isEmpty(announcementContent)) {
                            tvAnnouncementContent.setText(announcementContent);
                        }

                        String announcementTime = doctorMsg.getAnnouncementTime();
                        if (!TextUtils.isEmpty(announcementTime)) {
                            tvannouncementTime.setText(announcementTime);
                        }

                        diseaseExpertise = doctorMsg.getDiseaseExpertise();
                        if (diseaseExpertise.size() > 5) {
                            diseaseList.clear();
                            for (int i = 0; i < 5; i++) {
                                diseaseList.add(diseaseExpertise.get(i));
                            }
                            diseaseList.add("查看更多");
                            myGridViewAdapter.notifyDataSetChanged();
                        } else {
                            diseaseList.clear();
                            diseaseList.addAll(diseaseExpertise);
                            myGridViewAdapter.notifyDataSetChanged();
                        }


                        List<String> jobPhotos = doctorMsg.getJobPhotos();
                        if (jobPhotos != null && jobPhotos.size() > 0) {
                            mBanner.setImageLoader(new BannerImageLoader());
                            //mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
                            //mBanner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
                            mBanner.setImages(jobPhotos);//设置图片源
                            //banner.setBannerTitles(null);//设置标题源
                            mBanner.start();
                        } else {
                            mBanner.setVisibility(View.GONE);
                        }


                        tvIntrol.setMaxLines(3);
                        String introduce = doctorMsg.getIntroduce();
                        if (!TextUtils.isEmpty(introduce)) {
                            tvIntrol.setText(introduce);
                        }


                        articles = doctorMsg.getArticles();
                        if (articles.size() > 2) {
                            articlesBeans = articles.subList(0, 2);
                            adapter = new RecyclerAdapter<DoctorMsg.ArticlesBean>(context, R.layout.doctor_articles, articlesBeans) {
                                @Override
                                protected void convert(RecyclerAdapterHelper helper, final DoctorMsg.ArticlesBean item) {
                                    LinearLayout llPic = helper.getItemView().findViewById(R.id.ll_pic);
                                    String type = item.getArticleType();
                                    if (type.equals("5")) {
                                        llPic.setVisibility(View.VISIBLE);
                                    } else {
                                        llPic.setVisibility(View.GONE);
                                    }
                                    if (type.equals("5")) {
                                        helper.setVisible(R.id.tv_title, false);
                                        helper.setVisible(R.id.tv_time, true);
                                    } else if (type.equals("6")) {
                                        helper.setVisible(R.id.tv_title, false);
                                    } else if (type.equals("1")) {
                                        //全部显示
                                        helper.setVisible(R.id.tv_title, true);
                                        helper.setVisible(R.id.tv_time, true);
                                        helper.setVisible(R.id.tv_context, true);
                                    }
                                    helper.setText(R.id.tv_title, item.getArticleTitle());
                                    helper.setText(R.id.tv_time, item.getArticleTime());
                                    helper.setText(R.id.tv_context, item.getArticleContent());
                                    ImageView iv1 = (ImageView) helper.getItemView().findViewById(R.id.iv1);
                                    ImageView iv2 = (ImageView) helper.getItemView().findViewById(R.id.iv2);
                                    ImageView iv3 = (ImageView) helper.getItemView().findViewById(R.id.iv3);
                                    List<String> articlePics = item.getArticlePics();
                                    if (articlePics.size() == 1) {
                                        if (articlePics.get(0) != null) {
                                            Picasso.with(context).load(articlePics.get(0)).into(iv1);
                                        }
                                    }
                                    if (articlePics.size() == 2) {
                                        if (articlePics.get(0) != null) {
                                            Picasso.with(context).load(articlePics.get(0)).into(iv1);
                                        }
                                        if (articlePics.get(1) != null) {
                                            Picasso.with(context).load(articlePics.get(1)).into(iv2);
                                        }
                                    }
                                    if (articlePics.size() == 3) {
                                        if (articlePics.get(0) != null) {
                                            Picasso.with(context).load(articlePics.get(0)).into(iv1);
                                        }
                                        if (articlePics.get(1) != null) {
                                            Picasso.with(context).load(articlePics.get(1)).into(iv2);
                                        }
                                        if (articlePics.get(2) != null) {
                                            Picasso.with(context).load(articlePics.get(2)).into(iv3);
                                        }
                                    }
                                    helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(context, DoctorDetailActivity.class);
                                            intent.putExtra("id", item.getArticleId());
                                            startActivity(intent);
                                        }
                                    });
                                }
                            };
                        } else {
                            adapter = new RecyclerAdapter<DoctorMsg.ArticlesBean>(context, R.layout.doctor_articles, articles) {
                                @Override
                                protected void convert(RecyclerAdapterHelper helper, final DoctorMsg.ArticlesBean item) {
                                    LinearLayout llPic = helper.getItemView().findViewById(R.id.ll_pic);
                                    String type = item.getArticleType();
                                    if (type.equals("5")) {
                                        llPic.setVisibility(View.VISIBLE);
                                    } else {
                                        llPic.setVisibility(View.GONE);
                                    }
                                    if (type.equals("5")) {
                                        helper.setVisible(R.id.tv_title, false);
                                        helper.setVisible(R.id.tv_time, true);
                                    } else if (type.equals("6")) {
                                        helper.setVisible(R.id.tv_title, false);
                                    } else if (type.equals("1")) {
                                        //全部显示
                                        helper.setVisible(R.id.tv_title, true);
                                        helper.setVisible(R.id.tv_time, true);
                                        helper.setVisible(R.id.tv_context, true);
                                    }
                                    helper.setText(R.id.tv_title, item.getArticleTitle());
                                    helper.setText(R.id.tv_time, item.getArticleTime());
                                    helper.setText(R.id.tv_context, item.getArticleContent());
                                    ImageView iv1 = (ImageView) helper.getItemView().findViewById(R.id.iv1);
                                    ImageView iv2 = (ImageView) helper.getItemView().findViewById(R.id.iv2);
                                    ImageView iv3 = (ImageView) helper.getItemView().findViewById(R.id.iv3);
                                    List<String> articlePics = item.getArticlePics();
                                    if (articlePics.size() < 1) {
                                        llPic.setVisibility(View.GONE);
                                    } else if (articlePics.size() == 1) {
                                        if (articlePics.get(0) != null) {
                                            Picasso.with(context).load(articlePics.get(0)).into(iv1);
                                        }
                                    } else if (articlePics.size() == 2) {
                                        if (articlePics.get(0) != null) {
                                            Picasso.with(context).load(articlePics.get(0)).into(iv1);
                                        }
                                        if (articlePics.get(1) != null) {
                                            Picasso.with(context).load(articlePics.get(1)).into(iv2);
                                        }
                                    } else if (articlePics.size() == 3 || articlePics.size() > 3) {
                                        if (articlePics.get(0) != null) {
                                            Picasso.with(context).load(articlePics.get(0)).into(iv1);
                                        }
                                        if (articlePics.get(1) != null) {
                                            Picasso.with(context).load(articlePics.get(1)).into(iv2);
                                        }
                                        if (articlePics.get(2) != null) {
                                            Picasso.with(context).load(articlePics.get(2)).into(iv3);
                                        }
                                    }
                                    helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(context, DoctorDetailActivity.class);
                                            intent.putExtra("id", item.getArticleId());
                                            startActivity(intent);
                                        }
                                    });
                                }
                            };
                            mRecyclerView.setAdapter(adapter);
                        }
                    }
                }

                @Override
                public void onFailure(String info) {
                    showToast(info);
                }
            });
        }

        /**
         * 获取康复案例
         */
        loadHeadTemp();


        /**
         * 获取用户评价
         */
        loadAppraise();
    }

    private void loadHeadTemp() {
        Call<HttpBaseBean<List<HealthTemp>>> call = RetrofitFactory.getInstance(this).gteHealthTempList(doctorId, pageNum2, pageSize2);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<HealthTemp>>() {

            @Override
            public void onSuccess(List<HealthTemp> healthTemps, String info) {

                if (healthTemps != null) {
                    healthListAll.addAll(healthTemps);


                    if (healthTemps.size()>3){
                        tvMore3.setVisibility(View.VISIBLE);
                        for (int i = 0; i < 3; i++) {
                            healthList.add(healthTemps.get(i));
                        }
                        healthTempAdapter.addAll(healthList);
                    }else {
                        healthList.addAll(healthTemps);
                        healthTempAdapter.addAll(healthList);
                        tvMore3.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(String info) {

            }
        });
    }


    /**
     * 获取用户评价
     */

    private void loadAppraise() {
        llMore.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(appraiseUrl)) {
            Call<AppraiseDetail> call = RetrofitFactory.getInstance(context).getDoctorAppraiseList(appraiseUrl, pageNum, pageSize);
            call.enqueue(new Callback<AppraiseDetail>() {
                @Override
                public void onResponse(Call<AppraiseDetail> call, Response<AppraiseDetail> response) {
                    AppraiseDetail body = response.body();

                    int code = body.getCode();
                    if (code == 200) {

                        AppraiseDetail.PageBean page = body.getPage();
                        int total = page.getTotal();
                        tvTotal.setText("总评价（" + total + "条）");

                        List<AppraiseDetail.DataBean> data = body.getData();
                        if (data.size() > 0) {
                            list.addAll(data);
                            mProgressBar.setVisibility(View.VISIBLE);
                            tvMore.setVisibility(View.VISIBLE);
                            tvMore.setText("加载中...");
                            Adapter<AppraiseDetail.DataBean> adapter = new Adapter<AppraiseDetail.DataBean>(context, R.layout.appraiselist_item, list) {
                                @Override
                                protected void convert(AdapterHelper helper, AppraiseDetail.DataBean item) {
                                    helper.setText(R.id.tv_name, item.getName());
                                    helper.setText(R.id.tv_time, item.getTime());

                                    List<AppraiseDetail.DataBean.ItemsBean> items = item.getItems();
                                    NetstedListView mNetstedListView = (NetstedListView) helper.getItemView().findViewById(R.id.mNetstedListView);
                                    Adapter<AppraiseDetail.DataBean.ItemsBean> adapter1 = new Adapter<AppraiseDetail.DataBean.ItemsBean>(context, R.layout.appraise_item, items) {
                                        @Override
                                        protected void convert(AdapterHelper helper, AppraiseDetail.DataBean.ItemsBean item) {
                                            helper.setText(R.id.tv_item_name, item.getItem());
                                            AppCompatRatingBar ratingBar = (AppCompatRatingBar) helper.getItemView().findViewById(R.id.popup_ratingbar);
                                            ratingBar.setRating((float) item.getScore());
                                        }
                                    };
                                    mNetstedListView.setAdapter(adapter1);
                                }

                            };
                            mListView.setAdapter(adapter);
                        } else {
                            mProgressBar.setVisibility(View.GONE);
                            tvMore.setVisibility(View.VISIBLE);
                            tvMore.setText("没有更多了");
                        }
                    }
                }

                @Override
                public void onFailure(Call<AppraiseDetail> call, Throwable t) {

                }
            });
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_chatonline, R.id.rl_make_meet, R.id.rl_announcement, R.id.tv_more2, R.id.tv_look_post,R.id.tv_more3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_chatonline:
                if (inquiry) {
                    Call<HttpBaseBean<HaveMeet>> call = RetrofitFactory.getInstance(context).isHaveMeeting(doctorId);
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<HaveMeet>() {
                        @Override
                        public void onSuccess(final HaveMeet haveMeet, String info) {

                            if (haveMeet != null && !TextUtils.isEmpty(haveMeet.getRecordId())) {
                                if (haveMeet.getStatus().equals("PAY_SUCCESS")) {
                                    final Call<HttpBaseBean<ImInfo>> call = RetrofitFactory.getInstance(context).getImInfo(haveMeet.getRecordId());
                                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ImInfo>() {

                                        @Override
                                        public void onSuccess(ImInfo imInfo, String info) {
                                            if (imInfo != null) {
                                                final String im_doctor_accid = imInfo.getIm_doctor_accid();
                                                LoginInfo loginInfo = new LoginInfo(imInfo.getIm_accid(), imInfo.getIm_token());// config...
                                                NIMClient.getService(AuthService.class).login(loginInfo).setCallback(new RequestCallback<LoginInfo>() {
                                                    @Override
                                                    public void onSuccess(LoginInfo param) {
                                                    /*NimUIKit.loginSuccess(param.getAccount());
                                                    NimUIKit.startP2PSession(context,im_doctor_accid);*/
                                                        P2PMessageActivity.start(context, im_doctor_accid, null, null, "0", haveMeet.getRecordId(), doctorId);
                                                    }

                                                    @Override
                                                    public void onFailed(int code) {
                                                        ToastUtils.show(context, "登录失败");
                                                    }

                                                    @Override
                                                    public void onException(Throwable exception) {
                                                        ToastUtils.show(context, "登录异常");
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onFailure(String info) {

                                        }
                                    });
                                } else {
                                    startActivity(new Intent(context, MainActivity.class));
                                }

                            } else {
                                Intent intent = new Intent(context, BuyOnlineSeriveActivity.class);
                                intent.putExtra("doctorId", doctorId);
                                intent.putExtra("image", doctorMsg2.getImage());
                                intent.putExtra("name", doctorMsg2.getName());
                                intent.putExtra("depart", doctorMsg2.getDepart());
                                intent.putExtra("titles", doctorMsg2.getTitles());
                                intent.putExtra("medicalInstitutions", doctorMsg2.getMedicalInstitutions());
                                intent.putExtra("diseaseExpertise", (Serializable) doctorMsg2.getDiseaseExpertise());
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onFailure(String info) {
                            showToast(info);
                        }
                    });
                } else {
                    showToast("该医生没有开通在线问诊");
                }
                break;
            case R.id.rl_make_meet:
                if (appointment) {
                    Intent intent = new Intent(context, BuyMeetingSeriveActivity.class);
                    intent.putExtra("doctorId", doctorId);
                    intent.putExtra("image", doctorMsg2.getImage());
                    intent.putExtra("name", doctorMsg2.getName());
                    intent.putExtra("depart", doctorMsg2.getDepart());
                    intent.putExtra("titles", doctorMsg2.getTitles());
                    intent.putExtra("medicalInstitutions", doctorMsg2.getMedicalInstitutions());
                    intent.putExtra("diseaseExpertise", (Serializable) doctorMsg2.getDiseaseExpertise());
                    startActivity(intent);
                } else {
                    showToast("该医生没有开通线下面诊");
                }
                break;
            case R.id.rl_announcement:
                break;
            case R.id.tv_more2:
                Intent intent = new Intent(context, DoctorNewsListActivity.class);
                intent.putExtra("doctorId", doctorId);
                intent.putExtra("doctorName", name);
                startActivity(intent);
                break;
            case R.id.tv_look_post:
                alertDialog.show();
                break;
            case R.id.tv_more3:
                if (tvMore3.getText().toString().equals("查看更多案例")){
                    healthTempAdapter.clear();
                    healthTempAdapter.addAll(healthListAll);
                    tvMore3.setText("收起更多");
                }else {
                    healthTempAdapter.clear();
                    healthTempAdapter.addAll(healthList);
                    tvMore3.setText("查看更多案例");
                }
                break;
        }
    }

    private void showDoctorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_doctor_pic, null, false);
        ListView mListView = view.findViewById(R.id.mListView);
        Adapter<String> adapter = new Adapter<String>(this, R.layout.dialog_doctor_pic_item, certificatePics) {
            @Override
            protected void convert(AdapterHelper helper, String item) {
                ImageView ivPic = helper.getItemView().findViewById(R.id.iv_pic);
                Glide.with(context).load(item).into(ivPic);
            }
        };
        mListView.setAdapter(adapter);
        builder.setView(view);
        alertDialog = builder.create();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
