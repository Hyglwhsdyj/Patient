package com.ais.patient.activity.mine;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.main.BuyMeetingSeriveActivity;
import com.ais.patient.adapter.ReservationAdapter;
import com.ais.patient.base.BaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.Refund;
import com.ais.patient.been.Reservation;
import com.ais.patient.been.WetChat;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.MyProgressDialog;
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

public class NewReservationFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate{

    @BindView(R.id.mRecycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.define_sliding_bga)
    BGARefreshLayout mBGARefreshLayout;
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView;
    private List<Reservation> list = new ArrayList<>();
    private int pageNum=1;
    private int pageSize=10;
    private Context context;
    private Unbinder bind;
    private ReservationAdapter adapter;
    private MyReceiver myReceiver;
    MyProgressDialog progressDialog;
    private String recordId;

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

    /**
     * 支付成功广播接收器
     */
    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", -1);
            if (errCode==0){}
            Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).makePaySure(recordId,"dr_appointment");
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                @Override
                public void onSuccess(Object o, String info) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //购买线下面诊
                            pageNum = 1;
                            handler.sendEmptyMessageDelayed(0 , 0);
                            progressDialog.dismissProgress();
                        }
                    },3000);
                }

                @Override
                public void onFailure(String info) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pageNum = 1;
                            handler.sendEmptyMessageDelayed(0 , 0);
                            progressDialog.dismissProgress();
                        }
                    },3000);
                }
            });
        }
    }

    private void initView() {
        //设置刷新和加载监听
        mBGARefreshLayout.setDelegate(this);
        progressDialog = new MyProgressDialog(context);
        progressDialog.setCancelable(false); //设置等待按钮不能手动取消
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ais.pay");
        context.registerReceiver(myReceiver,intentFilter);
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
        Call<HttpBaseBean<List<Reservation>>> call = RetrofitFactory.getInstance(context).getReservationNewList(pageNum, pageSize);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<Reservation>>() {

            @Override
            public void onSuccess(List<Reservation> reservations, String info) {
                list.addAll(reservations);
                adapter.notifyDataSetChanged();
                if (reservations.size()>0){
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
        adapter = new ReservationAdapter(context,list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnIntemClickLister(new ReservationAdapter.onIntemClickLister() {
            @Override
            public void toPay(String id) {
                recordId = id;
                Call<HttpBaseBean<WetChat>> call = RetrofitFactory.getInstance(context).toPay(id, "dr_appointment");
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<WetChat>() {

                    @Override
                    public void onSuccess(WetChat wxPay, String info) {
                        IWXAPI api = WXAPIFactory.createWXAPI(context, wxPay.getAppid());
                        api.registerApp(wxPay.getAppid());
                        Log.e("Appid", "run: "+wxPay.getAppid());
                        PayReq payReq = new PayReq();
                        payReq.appId = wxPay.getAppid();
                        payReq.partnerId = wxPay.getPartnerid();
                        Log.e("partnerId", "run: "+wxPay.getPartnerid());
                        payReq.prepayId = wxPay.getPrepayid();
                        Log.e("prepayId", "run: "+wxPay.getPrepayid());
                        payReq.packageValue = wxPay.getPackageX();
                        Log.e("packageValue", "run: "+wxPay.getPackageX());
                        payReq.nonceStr = wxPay.getNoncestr();
                        Log.e("nonceStr", "run: "+wxPay.getNoncestr());
                        payReq.timeStamp = wxPay.getTimestamp();
                        Log.e("timeStamp", "run: "+wxPay.getTimestamp());
                        payReq.sign = wxPay.getSign();
                        Log.e("sign", "run: "+wxPay.getSign());
                        api.sendReq(payReq);
                    }

                    @Override
                    public void onFailure(String info) {
                        ToastUtils.show(context,info);
                    }
                });
            }

            @Override
            public void cancel(String id, int status) {
                int ss = status;
                if (status==0){
                    showDialog(status,id);
                }else if(status==1){
                    showDialog(status,id);
                }
            }
        });
    }


    private void showDialog(final int status, final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cancel, null);
        final AlertDialog dialog = builder.create();
        final TextView tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        if (status==0){
            String baseUrl = "/api/order/appointment/cancel_prompt/"+id;
            Call<HttpBaseBean<Refund>> call = RetrofitFactory.getInstance(context).isBackMoneyReservation(baseUrl);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<Refund>() {

                @Override
                public void onSuccess(Refund refund, String info) {
                    if (refund.isRefund()){
                        tvMsg.setText("请确认是否取消此次预约？");
                    }else {
                        tvMsg.setText("请确认是否取消此次预约？现在取消，已支付的费用将不退还，确认是否取消？");
                    }
                }

                @Override
                public void onFailure(String info) {

                }
            });
        }else if (status==1){
            tvMsg.setText("请确认是否取消支付？");
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status==0){
                    //取消预约
                    String baseUrl = "/api/order/appointment/cancel_order/"+id+".json";
                    Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).cancalOrder(baseUrl);
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                        @Override
                        public void onSuccess(Object o, String info) {
                            ToastUtils.show(context,"取消成功");
                            dialog.dismiss();

                            handler.sendEmptyMessageAtTime(0,500);
                        }

                        @Override
                        public void onFailure(String info) {
                            dialog.dismiss();
                            ToastUtils.show(context,info);
                        }
                    });
                }else if (status==1){
                    //取消支付
                    String baseUrl = "/api/order/appointment/cancel_pay/"+id+".json";
                    Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).cancalPay(baseUrl);
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                        @Override
                        public void onSuccess(Object o, String info) {
                            ToastUtils.show(context,"取消成功");
                            dialog.dismiss();
                            handler.sendEmptyMessageAtTime(0,500);
                        }

                        @Override
                        public void onFailure(String info) {
                            dialog.dismiss();
                            ToastUtils.show(context,info);
                        }
                    });
                }
            }
        });
        dialog.show();
        dialog.getWindow().setContentView((LinearLayout) view);
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
        context.unregisterReceiver(myReceiver);
    }
}
