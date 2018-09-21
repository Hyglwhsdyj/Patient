package com.ais.patient.activity.seedoctor;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.main.DoctorInfomationActivity;
import com.ais.patient.adapter.ChatOnlineAdapter;
import com.ais.patient.base.BaseActivity;
import com.ais.patient.been.ChatOnLineList;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.ImInfo;
import com.ais.patient.been.WetChat;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.util.UserUtils;
import com.ais.patient.widget.recycleview.DefineBAGRefreshWithLoadView;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
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

public class RecordChatOnLineFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate{

    @BindView(R.id.mRecycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.define_sliding_bga)
    BGARefreshLayout mBGARefreshLayout;
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView;
    private List<ChatOnLineList> list = new ArrayList<>();
    private int pageNum=1;
    private int pageSize=10;
    private Context context;
    private Unbinder bind;
    private ChatOnlineAdapter adapter;

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
        Call<HttpBaseBean<List<ChatOnLineList>>> call = RetrofitFactory.getInstance(context).getChatOnlineRecordList(pageNum, pageSize);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<ChatOnLineList>>() {


            @Override
            public void onSuccess(List<ChatOnLineList> chatOnLineLists, String info) {
                if (chatOnLineLists.size()>0){
                    list.addAll(chatOnLineLists);
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
        adapter = new ChatOnlineAdapter(context,list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnIntemClickLister(new ChatOnlineAdapter.onIntemClickLister() {
            @Override
            public void toPay(String doctorId, String recordId) {
                Call<HttpBaseBean<WetChat>> call = RetrofitFactory.getInstance(context).toPayChatOnline(recordId, "dr_inquiry","APP");
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
            public void cancel(String id) {
                showDialog(id);
            }

            @Override
            public void onItenClick(final String doctorId, final String recordId, String explainState) {
                if (explainState.equals("2")){
                    //跳医生主页
                    Intent intent = new Intent(context, DoctorInfomationActivity.class);
                    intent.putExtra("id",doctorId);
                    startActivity(intent);
                }else if (explainState.equals("1")){
                    /**
                     * 跳问诊聊天详情
                     */
                    UserUtils.saveChatOnLineOrMeeting(context,100);
                    UserUtils.saveChatOnLineDOCTORID(context,doctorId);
                    UserUtils.saveChatOnLineRECORDID(context,recordId);
                    final Call<HttpBaseBean<ImInfo>> call = RetrofitFactory.getInstance(context).getImInfo(recordId);
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ImInfo>() {

                        @Override
                        public void onSuccess(ImInfo imInfo, String info) {
                            if (imInfo!=null){
                                final String im_doctor_accid = imInfo.getIm_doctor_accid();
                                LoginInfo loginInfo = new LoginInfo(imInfo.getIm_accid(), imInfo.getIm_token());// config...
                                NIMClient.getService(AuthService.class).login(loginInfo).setCallback(new RequestCallback<LoginInfo>() {
                                    @Override
                                    public void onSuccess(LoginInfo param) {
                                        /*NimUIKit.loginSuccess(param.getAccount());
                                        NimUIKit.startP2PSession(context,im_doctor_accid);*/
                                        P2PMessageActivity.start(context,im_doctor_accid,null,null,"医生",recordId,doctorId);
                                    }

                                    @Override
                                    public void onFailed(int code) {
                                        ToastUtils.show(context,"登录失败");
                                    }

                                    @Override
                                    public void onException(Throwable exception) {
                                        ToastUtils.show(context,"登录异常");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(String info) {

                        }
                    });
                }

            }

        });
    }


    private void showDialog( final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cancel, null);
        final AlertDialog dialog = builder.create();
        final TextView tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        tvMsg.setText("请确认是否取消支付？");
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消支付
                String baseUrl = "/api/order/inquiry/cancel_pay/"+id+".json";
                Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).cancalOrder(baseUrl);
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                    @Override
                    public void onSuccess(Object o, String info) {
                        ToastUtils.show(context,"取消成功");
                        dialog.dismiss();

                        handler.sendEmptyMessageDelayed(0,500);
                    }

                    @Override
                    public void onFailure(String info) {
                        dialog.dismiss();
                        ToastUtils.show(context,info);
                    }
                });
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
    }

}
