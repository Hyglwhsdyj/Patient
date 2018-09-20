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
import com.ais.patient.adapter.ReservationAdapter;
import com.ais.patient.adapter.ReservationRecordAdapter;
import com.ais.patient.base.BaseActivity;
import com.ais.patient.base.BaseFragment;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.Reservation;
import com.ais.patient.been.ReservationRecord;
import com.ais.patient.been.WetChat;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.recycleview.DefineBAGRefreshWithLoadView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/7/29 0029.
 */

public class RecordReservationFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.mRecycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.define_sliding_bga)
    BGARefreshLayout mBGARefreshLayout;
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView;
    private List<ReservationRecord> list = new ArrayList<>();
    private int pageNum=1;
    private int pageSize=10;
    private Context context;
    private Unbinder bind;
    private ReservationRecordAdapter adapter;



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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_newreservation, null, false);
        bind = ButterKnife.bind(this, view);
        initView();
        setBgaRefreshLayout();
        setRecyclerCommadapter();
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

    /**
     * 加载数据
     *
     */
    private void setData() {
        Call<HttpBaseBean<List<ReservationRecord>>> call = RetrofitFactory.getInstance(context).getReservationRecordList(pageNum, pageSize);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<ReservationRecord>>() {


            @Override
            public void onSuccess(List<ReservationRecord> reservationRecords, String info) {
                if (reservationRecords.size()>0){
                    list.addAll(reservationRecords);
                    adapter.notifyDataSetChanged();
                }else {
                    if (pageNum!=1){
                        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("没有更多了");
                    }
                }
            }

            @Override
            public void onFailure(String info) {
                ToastUtils.show(context,info);
            }
        });
    }

    /** 数据填充 */
    private void setRecyclerCommadapter() {
        adapter = new ReservationRecordAdapter(context,list);
        mRecyclerView.setAdapter(adapter);
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
}
