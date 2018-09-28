package com.ais.patient.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.MyPatient;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.widget.recycleview.DefineBAGRefreshWithLoadView;
import com.bumptech.glide.Glide;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;

public class MyPatientListActivity extends MYBaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mRecycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.define_sliding_bga)
    BGARefreshLayout mBGARefreshLayout;
    private Context context;
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView;
    private int pageNum = 1;
    private int pageSize = 100;
    List<MyPatient> list = new ArrayList<>();
    private RecyclerAdapter<MyPatient> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_patient_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initView();
        setBgaRefreshLayout();
        setRecyCleViewAdapter();
    }

    private void initView() {
        tvTitle.setText("我的患者");
        context = this;
        //设置刷新和加载监听
        mBGARefreshLayout.setDelegate(this);
    }
    /**
     * 设置 BGARefreshLayout刷新和加载
     *
     */
    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(context , true , true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载更多");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        pageNum = 1;
        handler.sendEmptyMessageDelayed(0 , 500);
    }

    private void setRecyCleViewAdapter() {
        adapter = new RecyclerAdapter<MyPatient>(context, R.layout.my_patient_item,list) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final MyPatient item) {
                ImageView ivHead = (ImageView) helper.getItemView().findViewById(R.id.iv_img);
                Glide.with(context).load(item.getImage()).into(ivHead);
                helper.setText(R.id.tv_name,item.getName());
                helper.setText(R.id.tv_msg,item.getSex()+"      "+item.getAge());
                helper.setOnClickListener(R.id.tv_see_detail, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,MyPatientDetailActivity.class);
                        intent.putExtra("id",item.getPatientId());
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
                    setData();
                    adapter.clear();
                    if (mBGARefreshLayout!=null){
                        mBGARefreshLayout.endRefreshing();
                    }
                    break;
                case 1:
                    setData();
                    if (mBGARefreshLayout!=null){
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

    private void setData() {
        Call<HttpBaseBean<List<MyPatient>>> call = RetrofitFactory.getInstance(this).gteMyPatientList(pageNum, pageSize);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<MyPatient>>() {

            @Override
            public void onSuccess(List<MyPatient> myPatients, String info) {
                if (myPatients!=null){
                    list.addAll(myPatients);
                    adapter.addAll(myPatients);
                }
            }

            @Override
            public void onFailure(String info) {
                showToast(info);
            }
        });
    }


    /** 刷新 */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        pageNum = 1;
        handler.sendEmptyMessageDelayed(0 , 500);
    }
    /** 加载 */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pageNum++;
        handler.sendEmptyMessageDelayed(1 , 500);
        return true;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }

}
