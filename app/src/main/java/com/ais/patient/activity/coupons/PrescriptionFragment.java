package com.ais.patient.activity.coupons;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.BaseFragment;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.Prescription;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.BannerImageLoader;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.NetstedListView;
import com.ais.patient.widget.VpSwipeRefreshLayout;
import com.bumptech.glide.Glide;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/7/29 0029.
 */

public class PrescriptionFragment extends BaseFragment {
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mBanner)
    Banner mBanner;
    @BindView(R.id.mSwipeRefreshLayout)
    VpSwipeRefreshLayout mSwipeRefreshView;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.et_search)
    EditText etSearch;
    private Context context;

    List<Integer> imgList = new ArrayList<>();
    private String searchWord;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_prescription;
    }

    @Override
    protected void initViews(View view) {
        context = getBaseActivity();
        tvBack.setVisibility(View.GONE);
        tvTitle.setText("传承秘方");
        mSwipeRefreshView.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setNestedScrollingEnabled(false);
        /**
         * banner轮播
         */
        imgList.add(R.mipmap.ic_pre_banner);
        mBanner.setImageLoader(new BannerImageLoader());
        mBanner.setImages(imgList);//设置图片源
        mBanner.start();
    }

    @Override
    protected void initEvent() {
        /**
         * 下拉刷新
         */
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchWord=null;
                etSearch.getText().clear();
                mSwipeRefreshView.setRefreshing(true);
                initData();
            }
        });
    }

    @Override
    protected void initData() {
        Call<HttpBaseBean<List<Prescription>>> call = RetrofitFactory.getInstance(context).getPrescriptionList(searchWord);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<Prescription>>() {

            @Override
            public void onSuccess(List<Prescription> prescriptions, String info) {
                    RecyclerAdapter<Prescription> adapter = new RecyclerAdapter<Prescription>(context, R.layout.prescription_item, prescriptions) {
                        @Override
                        protected void convert(RecyclerAdapterHelper helper, final Prescription item) {
                            ImageView ivCoverImg = helper.getItemView().findViewById(R.id.iv_coverImage);

                            TextView tv_title = helper.getItemView().findViewById(R.id.tv_title);
                            TextView tv1 = helper.getItemView().findViewById(R.id.tv_1);
                            TextView tv2 = helper.getItemView().findViewById(R.id.tv_2);
                            //得到AssetManager
                            AssetManager mgr=context.getAssets();
                            //根据路径得到Typeface
                            Typeface tf=Typeface.createFromAsset(mgr, "fonts/JDFYUANF.TTF");
                            //设置字体
                            tv_title.setTypeface(tf);
                            tv1.setTypeface(tf);
                            tv2.setTypeface(tf);

                            Glide.with(context).load(item.getCoverImage()).into(ivCoverImg);
                            helper.setText(R.id.tv_title, item.getTitle());
                            helper.setText(R.id.tv_function, item.getFunction());
                            helper.setText(R.id.tv_introduce, item.getIntroduce());
                            helper.setOnClickListener(R.id.tv_toDatial, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(context, PrescriptionDetailActivity.class);
                                    intent.putExtra("Secret_id", item.getSecret_id());
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    mRecyclerView.setAdapter(adapter);
                if (mSwipeRefreshView.isRefreshing()) {
                    mSwipeRefreshView.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(String info) {
                ToastUtils.show(context,info);
                if (mSwipeRefreshView.isRefreshing()) {
                    mSwipeRefreshView.setRefreshing(false);
                }
            }
        });
    }


    @OnClick(R.id.tv_ok)
    public void onViewClicked() {
        searchWord = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(searchWord)){
            ToastUtils.show(context,"请输入搜索内容");
        }else {
            initData();
        }
    }
}
