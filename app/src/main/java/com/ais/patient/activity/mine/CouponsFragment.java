package com.ais.patient.activity.mine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ais.patient.R;
import com.ais.patient.base.BaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.MyCpupons;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.widget.recycleview.DefineBAGRefreshWithLoadView;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/8/13 0013.
 */

public class CouponsFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.mRecycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.define_sliding_bga)
    BGARefreshLayout mBGARefreshLayout;
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView;
    private int pageNum=1;
    private int pageSize=10;
    private Context context;
    private Unbinder bind;
    private String type;
    List<MyCpupons> list = new ArrayList<>();
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context = (BaseActivity)context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible() && mRecyclerView.getVisibility() == View.VISIBLE) {
            pageNum=1;
            list.clear();
            setData();
            Log.e("setUserVisibleHint",  "[TakeFragment]setUserVisibleHint");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getUserVisibleHint() && mRecyclerView.getVisibility() == View.VISIBLE) {
            handler.sendEmptyMessageDelayed(0 , 0);
        }
        Log.e("setUserVisibleHint",  "[TakeFragment]onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    public static CouponsFragment newInstance(String flag) {
        Bundle bundle = new Bundle();
        bundle.putString("type", flag);
        CouponsFragment fragment = new CouponsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_newreservation, null, false);
        bind = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        type = bundle.getString("type");

        initView();
        setBgaRefreshLayout();
        return view;
    }

    private void initView() {
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
        Call<HttpBaseBean<List<MyCpupons>>> call = RetrofitFactory.getInstance(context).getMyCouponsList(pageNum, pageSize, type);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<MyCpupons>>() {

            @Override
            public void onSuccess(List<MyCpupons> myCpupons, String info) {
                if (myCpupons.size()>0){
                    list.addAll(myCpupons);
                    if (type.equals("INIT")){
                        RecyclerAdapter<MyCpupons> adapter = new RecyclerAdapter<MyCpupons>(context,R.layout.coupons_item,list) {
                            @Override
                            protected void convert(RecyclerAdapterHelper helper, MyCpupons item) {
                                helper.setVisible(R.id.mCheck,false);
                                helper.setText(R.id.tv_money,item.getMoney()+"");
                                helper.setText(R.id.tv_minMoney,"满"+item.getMinMoney()+"使用");
                                helper.setText(R.id.tv_title,item.getName());
                                helper.setText(R.id.tv_type,item.getType());
                                helper.setText(R.id.tv_time,item.getStartTime()+"至"+item.getEndTime());
                            }
                        };
                        mRecyclerView.setAdapter(adapter);
                    }else {
                        RecyclerAdapter<MyCpupons> adapter = new RecyclerAdapter<MyCpupons>(context,R.layout.coupons_item2,list) {
                            @Override
                            protected void convert(RecyclerAdapterHelper helper, MyCpupons item) {
                                helper.setText(R.id.tv_money,item.getMoney()+"");
                                helper.setText(R.id.tv_minMoney,"满"+item.getMinMoney()+"使用");
                                helper.setText(R.id.tv_title,item.getName());
                                helper.setText(R.id.tv_type,item.getType());
                                helper.setText(R.id.tv_time,item.getStartTime()+"至"+item.getEndTime());
                            }
                        };
                        mRecyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(String info) {

            }
        });
    }


    /** 刷新 */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        pageNum = 1;
        handler.sendEmptyMessageDelayed(0 , 1000);
    }
    /** 加载 */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pageNum++;
        handler.sendEmptyMessageDelayed(1 , 500);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
