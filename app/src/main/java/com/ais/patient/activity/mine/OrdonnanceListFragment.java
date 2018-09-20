package com.ais.patient.activity.mine;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.main.BuyMedicineActivity;
import com.ais.patient.adapter.OrdonnanceListAdapter;
import com.ais.patient.base.BaseFragment;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.Jump;
import com.ais.patient.been.MultiItemView;
import com.ais.patient.been.OrdonnanceListRespone;
import com.ais.patient.been.Reservation;
import com.ais.patient.been.WetChat;
import com.ais.patient.http.API;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.MyProgressDialog;
import com.ais.patient.widget.recycleview.DefineBAGRefreshWithLoadView;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
 * Created by lyf on 2018/8/6.
 */

public class OrdonnanceListFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.define_sliding_bga)
    BGARefreshLayout defineSlidingBga;

    private String type;//订单类型
    private int pageNum = 1;
    private int pageSize = 10;
    private Context context;

    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView;


    private OrdonnanceListAdapter adapter;
    private List<MultiItemView<OrdonnanceListRespone>> datas = new ArrayList<>();
    public MyReceiver myReceiver;
    MyProgressDialog progressDialog;
    private String recordId;

    @Override
    protected int getLayoutId() {
        return R.layout.ordonnannce_list_fragment;
    }

    @Override
    protected void initViews(View view) {
        context = getActivity();
        progressDialog = new MyProgressDialog(context);
        progressDialog.setCancelable(false); //设置等待按钮不能手动取消
    }

    /**
     * 支付成功广播接收器
     */
    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", -1);
                progressDialog.showProgress();
                Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).makePaySure(recordId,"dr_prescription");
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                    @Override
                    public void onSuccess(Object o, String info) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pageNum = 1;
                                handler.sendEmptyMessage(0);
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
                                handler.sendEmptyMessage(0);
                                progressDialog.dismissProgress();
                            }
                        },3000);
                    }
                });
            }
    }
    @Override
    protected void initEvent() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ais.pay");
        context.registerReceiver(myReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       context.unregisterReceiver(myReceiver);
    }



    @Override
    protected void initData() {
        type = getArguments().getString("type");
        adapter = new OrdonnanceListAdapter(datas);
        mRecycleView.setAdapter(adapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(context));
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);

        //设置刷新和加载监听
        defineSlidingBga.setDelegate(this);

        datas.clear();
        getData();
        setBgaRefreshLayout();
    }

    /**
     * 设置 BGARefreshLayout刷新和加载
     */
    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(context, true, true);
        //设置刷新样式
        defineSlidingBga.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载更多");
        mRecycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    /**
     * 获取列表数据
     */
    private void getData() {
        Call<HttpBaseBean<List<OrdonnanceListRespone>>> call = RetrofitFactory.getInstance(context).getOrdonnanceList(pageNum, pageSize, type);
        Log.i("datas", call.toString() + "");

        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<OrdonnanceListRespone>>() {

            @Override
            public void onSuccess(List<OrdonnanceListRespone> reservations, String info) {

                Log.i("datas", reservations.size() + "");
                for (OrdonnanceListRespone respone : reservations) {
                    MultiItemView<OrdonnanceListRespone> multiItemViewTitle = new MultiItemView<>(MultiItemView.TITLE);
                    OrdonnanceListRespone responeTitle = new OrdonnanceListRespone();
                    responeTitle.setOrderId(respone.getOrderId());
                    responeTitle.setOrderTime(respone.getOrderTime());
                    multiItemViewTitle.setBean(responeTitle);
                    datas.add(multiItemViewTitle);

                    MultiItemView<OrdonnanceListRespone> multiItemViewBody = new MultiItemView<>(MultiItemView.BODY);
                    OrdonnanceListRespone responeBody = new OrdonnanceListRespone();
                    responeBody.setPatientName(respone.getPatientName());
//                    responeBody.setDecoct(respone.getDecoct());
                    responeBody.setInvalidTime(respone.getInvalidTime());
                    responeBody.setDecoct(respone.getDecoct());
                    responeBody.setId(respone.getId());
                    responeBody.setStatus(respone.getStatus());
                    responeBody.setDoctorName(respone.getDoctorName());
                    multiItemViewBody.setBean(responeBody);
                    datas.add(multiItemViewBody);

                    MultiItemView<OrdonnanceListRespone> multiItemViewFooter = new MultiItemView<>(MultiItemView.FOOTER);
                    OrdonnanceListRespone responeFooter = new OrdonnanceListRespone();
                    responeFooter.setFee(respone.getFee());
                    responeFooter.setId(respone.getId());
                    responeFooter.setOrderId(respone.getOrderId());
                    responeFooter.setStatus(respone.getStatus());
                    responeFooter.setAppraise(respone.isAppraise());
                    responeFooter.setIsOrder(respone.isIsOrder());
                    responeFooter.setDoctorName(respone.getDoctorName());
                    multiItemViewFooter.setBean(responeFooter);
                    datas.add(multiItemViewFooter);

                }
                adapter.notifyDataSetChanged();
                if (pageNum != 1) {
                    mDefineBAGRefreshWithLoadView.updateLoadingMoreText("没有更多了");
                }
            }

            @Override
            public void onFailure(String info) {
                ToastUtils.show(context, info);
            }
        });
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
                    datas.clear();
                    getData();
                    if (defineSlidingBga != null) {
                        defineSlidingBga.endRefreshing();
                    }
                    break;
                case 1:
                    getData();
                    if (defineSlidingBga != null) {
                        defineSlidingBga.endLoadingMore();
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
        handler.sendEmptyMessage(0);
    }

    /**
     * 加载
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pageNum++;
        handler.sendEmptyMessage(1);
        return true;
    }

    /**
     * 列表点击事件
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (datas.get(position).getItemType()){
            case MultiItemView.BODY:

                break;
        }
    }

    /**
     * 列表子元素点击事件
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Log.i("取消订单", "position: "+position);
        switch (view.getId()){
            case R.id.tv_1:
                String status = datas.get(position).getBean().getStatus();
                final String id = datas.get(position).getBean().getId();
                String baseUrl = "/api/order/my/jump/"+id;
                Call<HttpBaseBean<Jump>> call = RetrofitFactory.getInstance(context).getJumpType(baseUrl);
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<Jump>() {

                    @Override
                    public void onSuccess(Jump jump, String info) {
                        if (jump!=null){
                            int type = jump.getJump();
                            if (type==0){
                                Intent intent = new Intent(context, BuyMedicineActivity.class);
                                intent.putExtra("recordId",id);
                                startActivity(intent);
                            }else if (type==1 || type==2 || type==3){
                                Intent intent=new Intent(context,OrdonnanceDetailActivity.class);
                                intent.putExtra("orderId",id);
                                intent.putExtra("orderStatus",type);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String info) {
                        ToastUtils.show(context,info);
                    }
                });
                break;
            case R.id.tv_2:
               String tag= (String) view.getTag();
               switch (tag){
                   case "取消订单":
                       getCancelOrder(datas.get(position).getBean().getId());
                       break;
                   case "查看物流":
                       String id2 = datas.get(position).getBean().getId();
                       //5b7599cc2f396e48e48382a4
                       Intent intent=new Intent(context,OrdonnanceDetailActivity.class);
                       intent.putExtra("orderId",id2);
                       intent.putExtra("orderStatus",2);
                       startActivity(intent);
                       break;
                   case "查看详情":
                       String id3 = datas.get(position).getBean().getId();
                       //5b7599cc2f396e48e48382a4
                       Intent intent2=new Intent(context,OrdonnanceDetailActivity.class);
                       intent2.putExtra("orderId",id3);
                       intent2.putExtra("orderStatus",3);
                       startActivity(intent2);
                       break;
               }
                break;
        }
    }
    /**
     * 取消订单
     */
    private void getCancelOrder(String id) {
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).getOrdonnanceCancel(API.ORDONNANCE_DETAIL_CANCEL_URL+id);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                ToastUtils.show(context,"取消成功");

            }

            @Override
            public void onFailure(String info) {
                ToastUtils.show(context,info);
            }
        });
    }
}
