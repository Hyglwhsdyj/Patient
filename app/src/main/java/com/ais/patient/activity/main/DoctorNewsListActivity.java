package com.ais.patient.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.mine.DoctorDetailActivity;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.DoctorNewList;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.widget.recycleview.DefineBAGRefreshWithLoadView;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;

public class DoctorNewsListActivity extends MYBaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.mRecycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.define_sliding_bga)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView;

    private String doctorId;
    private int pageNum = 1;
    private int pageSize = 10;
    private Context context;
    List<DoctorNewList> list = new ArrayList<>();
    private String doctorName;
    private RecyclerAdapter<DoctorNewList> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_news_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        Intent intent = getIntent();
        doctorId = intent.getStringExtra("doctorId");
        doctorName = intent.getStringExtra("doctorName");
        initView();
        setBgaRefreshLayout();
    }

    private void initView() {
        tvTitle.setText(doctorName+"动态");
        //设置刷新和加载监听
        mBGARefreshLayout.setDelegate(this);
    }

    /**
     * 设置 BGARefreshLayout刷新和加载
     */
    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(context, true, true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载更多");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        //全部显示
        adapter = new RecyclerAdapter<DoctorNewList>(context, R.layout.doctor_articles, list) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final DoctorNewList item) {
                LinearLayout llPic = helper.getItemView().findViewById(R.id.ll_pic);
                String type = item.getType();
                if (type.equals("5")){
                    llPic.setVisibility(View.VISIBLE);
                }else {
                    llPic.setVisibility(View.GONE);
                }
                if (type.equals("5")){
                    helper.setVisible(R.id.tv_title, false);
                    helper.setVisible(R.id.tv_time, true);
                }else if (type.equals("6")){
                    helper.setVisible(R.id.tv_title, false);
                }else if (type.equals("1")){
                    //全部显示
                    helper.setVisible(R.id.tv_title, true);
                    helper.setVisible(R.id.tv_time, true);
                    helper.setVisible(R.id.tv_context, true);
                }
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_time, item.getTime());
                helper.setText(R.id.tv_context, item.getContent());
                ImageView iv1 = (ImageView) helper.getItemView().findViewById(R.id.iv1);
                ImageView iv2 = (ImageView) helper.getItemView().findViewById(R.id.iv2);
                ImageView iv3 = (ImageView) helper.getItemView().findViewById(R.id.iv3);
                List<String> articlePics = item.getPics();
                if (articlePics.size()<1){
                    llPic.setVisibility(View.GONE);
                }else if (articlePics.size() == 1) {
                    if (articlePics.get(0) != null) {
                        Picasso.with(context).load(articlePics.get(0)).into(iv1);
                    }
                }else if (articlePics.size() == 2) {
                    if (articlePics.get(0) != null) {
                        Picasso.with(context).load(articlePics.get(0)).into(iv1);
                    }
                    if (articlePics.get(1) != null) {
                        Picasso.with(context).load(articlePics.get(1)).into(iv2);
                    }
                }else if (articlePics.size() == 3 || articlePics.size()>3) {
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
                        Intent intent  = new Intent(context, DoctorDetailActivity.class);
                        intent.putExtra("id",item.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 请求网络数据
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    list.clear();
                    adapter.clear();
                    initData();
                    if (mBGARefreshLayout != null) {
                        mBGARefreshLayout.endRefreshing();
                    }
                    break;
                case 1:
                    initData();
                    if (mBGARefreshLayout != null) {
                        mBGARefreshLayout.endLoadingMore();
                    }
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 刷新
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        pageNum = 1;
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    /**
     * 加载
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pageNum++;
        handler.sendEmptyMessageDelayed(1, 500);
        return true;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(doctorId)) {
            String baseUrl = "/api/doctor/list_news/" + doctorId;
            Call<HttpBaseBean<List<DoctorNewList>>> call = RetrofitFactory.getInstance(this).getDoctorNewsList(baseUrl, pageNum, pageSize);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<DoctorNewList>>() {

                @Override
                public void onSuccess(List<DoctorNewList> doctorNewLists, final String info) {
                    if (doctorNewLists != null && doctorNewLists.size() > 0) {
                        list.addAll(doctorNewLists);
                        adapter.clear();
                        adapter.addAll(list);
                        //adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(String info) {

                }
            });
        }
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
