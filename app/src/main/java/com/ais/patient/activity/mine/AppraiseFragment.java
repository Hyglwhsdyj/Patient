package com.ais.patient.activity.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.adapter.ReservationAdapter;
import com.ais.patient.base.BaseActivity;
import com.ais.patient.been.Appraise;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.Reservation;
import com.ais.patient.been.WetChat;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.CircleTransform;
import com.ais.patient.widget.recycleview.DefineBAGRefreshWithLoadView;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.squareup.picasso.Picasso;
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

public class AppraiseFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate{

    @BindView(R.id.mRecycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.define_sliding_bga)
    BGARefreshLayout mBGARefreshLayout;
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView;
    private List<Appraise> list = new ArrayList<>();
    private int pageNum=1;
    private int pageSize=10;
    private Context context;
    private Unbinder bind;
    private String businessType;
    private Call<HttpBaseBean<List<Appraise>>> call;

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


    public static AppraiseFragment newInstance(int flag) {
        Bundle bundle = new Bundle();
        bundle.putInt("flag", flag);
        AppraiseFragment fragment = new AppraiseFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_newreservation, null, false);
        bind = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        int flag = bundle.getInt("flag");
        if (flag==0){
            businessType = "";
        }else if (flag==1){
            businessType = "dr_inquiry";
        }else if (flag==2){
            businessType = "dr_appointment";
        }else if (flag==3){
            businessType = "dr_prescription";
        }
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


    /**
     * 加载数据
     *
     */
    private void setData() {
        if (TextUtils.isEmpty(businessType)){
            call = RetrofitFactory.getInstance(context).getAppraiseListALL(pageNum, pageSize);
        }else {
            call = RetrofitFactory.getInstance(context).getAppraiseList(pageNum, pageSize,businessType);
        }
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<Appraise>>() {

            @Override
            public void onSuccess(List<Appraise> appraises, final String info) {
                /*Appraise appraise = new Appraise();
                appraise.setAppraiseStatus(0);
                appraise.setDoctorName("张三");
                appraises.add(appraise);
                appraise = new Appraise();
                appraise.setAppraiseStatus(1);
                appraise.setDoctorName("李四");
                appraises.add(appraise);*/
                if (appraises.size()>0){
                    list.addAll(appraises);
                    RecyclerAdapter<Appraise> adapter = new RecyclerAdapter<Appraise>(context,R.layout.appraise_my_item,list) {
                        @Override
                        protected void convert(RecyclerAdapterHelper helper, final Appraise item) {
                            ImageView ivIcon = (ImageView) helper.getItemView().findViewById(R.id.iv_icon);
                            Picasso.with(context).load(item.getHeadImage()).transform(new CircleTransform()).into(ivIcon);
                            helper.setText(R.id.tv_name,item.getDoctorName());
                            helper.setText(R.id.tv_serviceName,item.getTime()+"  "+item.getServiceName());
                            int appraiseStatus = item.getAppraiseStatus();
                            if (appraiseStatus==0){
                                helper.setVisible(R.id.tv_toApprise,true);
                                helper.setVisible(R.id.ll_isApprise,false);
                                helper.setOnClickListener(R.id.tv_toApprise, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String orderId = item.getOrderId();
                                        Intent intent = new Intent(context,AppraiseDetialActivity.class);
                                        intent.putExtra("orderId",orderId);
                                        intent.putExtra("name",item.getDoctorName());
                                        intent.putExtra("serviceName",item.getServiceName());
                                        startActivityForResult(intent,0);
                                    }
                                });
                            }else {
                                helper.setVisible(R.id.tv_toApprise,false);
                                helper.setVisible(R.id.ll_isApprise,true);
                                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String orderId = item.getOrderId();
                                        Intent intent = new Intent(context,AppraiseDetialActivity.class);
                                        intent.putExtra("orderId",orderId);
                                        startActivityForResult(intent,0);
                                    }
                                });
                            }

                        }
                    };
                    adapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(adapter);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==Activity.RESULT_OK){
            pageNum = 1;
            handler.sendEmptyMessageDelayed(0 , 500);
        }
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
