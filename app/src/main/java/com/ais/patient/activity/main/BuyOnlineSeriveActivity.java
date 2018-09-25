package com.ais.patient.activity.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.mine.MyOrdonnanceActivity;
import com.ais.patient.activity.mine.MyReservationActivity;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.ChatOnlineMsg;
import com.ais.patient.been.CheckPay;
import com.ais.patient.been.DoctorMsg;
import com.ais.patient.been.DoctorTime;
import com.ais.patient.been.FactFee;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.WetChat;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.UserUtils;
import com.ais.patient.widget.CircleTransform;
import com.ais.patient.widget.FlowGroupView;
import com.ais.patient.widget.UploadPopupwindow;
import com.ais.patient.wxapi.WXPayEntryActivity;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class BuyOnlineSeriveActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_type)
    TextView tvTpe;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_depart)
    TextView tvDepart;
    @BindView(R.id.tv_medicalInstitutions)
    TextView tvMedicalInstitutions;
    @BindView(R.id.mFlowGroupView)
    FlowGroupView mFlowGroupView;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_quota)
    TextView tvQuota;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    @BindView(R.id.tv_introl)
    TextView tvIntrol;
    @BindView(R.id.tv_cardnum)
    TextView tvCardnum;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_express)
    TextView tvExpress;

    boolean isExpress=false;

    private String doctorId;
    private Context context;
    private double fee;
    private String couponId;
    private WindowManager.LayoutParams params;
    private UploadPopupwindow popupwindow;
    private String settingId;
    private double money;
    private double minMoney;
    private String recordId;
    private MyReceiver myReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_online_serive;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        tvTitle.setText("购买问诊服务");
        Intent intent = getIntent();
        doctorId = intent.getStringExtra("doctorId");
        String image = intent.getStringExtra("image");
        String name = intent.getStringExtra("name");
        String titles = intent.getStringExtra("titles");
        String depart = intent.getStringExtra("depart");
        String medicalInstitutions = intent.getStringExtra("medicalInstitutions");
        List<String> diseaseExpertise = (List<String>) intent.getSerializableExtra("diseaseExpertise");
        if (image!=null){
            Picasso.with(this)
                    .load(image)
                    .transform(new CircleTransform())
                    .error(R.mipmap.ic_doctor_icon_bg)
                    .into(ivIcon);
        }

        if (!TextUtils.isEmpty(name)) {
            tvName.setText(name);
        }

        if (!TextUtils.isEmpty(titles)) {
            tvTpe.setText(titles);
        }

        if (!TextUtils.isEmpty(depart)) {
            tvDepart.setText(depart);
        }

        if (!TextUtils.isEmpty(medicalInstitutions)) {
            tvMedicalInstitutions.setText("医院机构：" + medicalInstitutions);
        }
        if (diseaseExpertise.size()>0){
            for (int i = 0; i < diseaseExpertise.size(); i++) {
                addTextView(diseaseExpertise.get(i));
            }
        }
    }

    private void addTextView(String str) {
        TextView child = new TextView(context);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        child.setLayoutParams(params);
        child.setBackgroundResource(R.drawable.shape_diseaseexpertise);
        child.setText(str);
        child.setTextSize(11);
        child.setTextColor(context.getResources().getColor(R.color.diseaseexpertise_bg));
        mFlowGroupView.addView(child);
    }
    @Override
    protected void initEvent() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ais.pay");
        registerReceiver(myReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void initData() {
        /**
         * 基本信息
         */
        if (doctorId!=null && !doctorId.equals("")){
            Call<HttpBaseBean<ChatOnlineMsg>> call = RetrofitFactory.getInstance(this).getChatOnlineMsg(doctorId);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ChatOnlineMsg>() {

                @Override
                public void onSuccess(ChatOnlineMsg chatOnlineMsg, String info) {
                    if (chatOnlineMsg!=null){
                        settingId = chatOnlineMsg.getSettingId();
                        String pageTime = chatOnlineMsg.getPageTime();
                        if (!TextUtils.isEmpty(pageTime)){
                            tvTime.setText(pageTime+"    剩余");
                        }
                        int quota = chatOnlineMsg.getQuota();
                        tvQuota.setText(quota+"");
                        fee = chatOnlineMsg.getFee();
                        tvFee.setText("￥"+ fee +"/次");
                        tvTotalPrice.setText(fee+"");
                        if (fee>0){
                            /**
                             * 优惠券可用数量
                             */
                            Call<HttpBaseBean<Integer>> dr_inquiry = RetrofitFactory.getInstance(context).getQuantity("dr_inquiry", fee);
                            new BaseCallback(dr_inquiry).handleResponse(new BaseCallback.ResponseListener<Integer>() {

                                @Override
                                public void onSuccess(Integer integer, String info) {
                                    if (integer!=null){
                                        tvCardnum.setText(integer+"");
                                    }
                                }

                                @Override
                                public void onFailure(String info) {
                                    showToast(info);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(String info) {
                    showToast(info);
                }
            });
        }

    }

    @OnClick({R.id.tv_back, R.id.tl_time,R.id.tv_express, R.id.tl_card, R.id.tv_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tl_time:
                showPopWindow();
                break;
            case R.id.tv_express:
                isExpress = !isExpress;

                if (isExpress){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_cancel, null, false);
                    builder.setView(inflate);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    TextView tvCancel = (TextView) inflate.findViewById(R.id.tv_cancel);
                    TextView tvOk = (TextView) inflate.findViewById(R.id.tv_ok);
                    tvCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    tvOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvExpress.setText("取消加急");
                            fee = fee*2;
                            tvFee.setText("￥"+fee+"/次");
                            tvTotalPrice.setText(fee+"");
                            alertDialog.dismiss();
                        }
                    });
                }else {
                    tvExpress.setText("我要加急");
                    fee = fee/2;
                    tvFee.setText("￥"+fee+"/次");
                    tvTotalPrice.setText(fee+"");
                }

                break;
            case R.id.tl_card:
                Intent intent = new Intent(this,CouponsActivity.class);
                intent.putExtra("businessType","dr_inquiry");
                intent.putExtra("fee",fee);
                startActivityForResult(intent,0);
                break;
            case R.id.tv_wechat:
                if (isExpress){
                    requestWechatExpress();
                }else {
                    requestWechat();
                }
                break;
        }
    }

    /**
     * 微信支付
     */
    private void requestWechat() {
        UserUtils.saveChatOnLineOrMeeting(this,100);
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("payType","weixinpay_app");
        ajaxParams.put("settingsId",settingId);
        ajaxParams.put("fromType","ANDROID");
        if (couponId!=null && !TextUtils.isEmpty(couponId)){
            ajaxParams.put("couponId",couponId);
        }
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Log.e("BuyOnlineSeriveActivity", "requestWechat: "+urlParams.toString());
        Call<HttpBaseBean<WetChat>> call = RetrofitFactory.getInstance(this).requestWechat(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<WetChat>() {

            @Override
            public void onSuccess(WetChat wxPay, String info) {
                IWXAPI api = WXAPIFactory.createWXAPI(context, wxPay.getAppid());
                api.registerApp(wxPay.getAppid());

                recordId = wxPay.getRecordId();
                UserUtils.saveChatOnLineRECORDID(context,recordId);
                UserUtils.saveChatOnLineDOCTORID(context,doctorId);


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
                showToast(info);
            }
        });
    }

    private void requestWechatExpress() {
        UserUtils.saveChatOnLineOrMeeting(this,100);
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("payType","weixinpay_app");
        ajaxParams.put("fromType","ANDROID");
        ajaxParams.put("doctorId",doctorId);
        if (couponId!=null && !TextUtils.isEmpty(couponId)){
            ajaxParams.put("couponId",couponId);
        }
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Log.e("BuyOnlineSeriveActivity", "requestWechat: "+urlParams.toString());
        Call<HttpBaseBean<WetChat>> call = RetrofitFactory.getInstance(this).requestExpressWechat(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<WetChat>() {

            @Override
            public void onSuccess(WetChat wxPay, String info) {
                IWXAPI api = WXAPIFactory.createWXAPI(context, wxPay.getAppid());
                api.registerApp(wxPay.getAppid());

                recordId = wxPay.getRecordId();
                UserUtils.saveChatOnLineRECORDID(context,recordId);
                UserUtils.saveChatOnLineDOCTORID(context,doctorId);


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
                showToast(info);
            }
        });
    }

    /**
     * 支付成功广播接收器
     */
    public class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(final Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", -1);
            if (isExpress){
                if (errCode==0){
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put("recordId",recordId);
                    ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
                    Call<HttpBaseBean<CheckPay>> call = RetrofitFactory.getInstance(context).makeSureExpress(urlParams);
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<CheckPay>() {

                        @Override
                        public void onSuccess(CheckPay checkPay, String info) {
                            if (checkPay!=null){
                                if (checkPay.getStatus()==1){
                                    /**
                                     * 1 支付成功
                                     2 支付待确认，需要再请求接口
                                     3 支付异常，订单定时取消
                                     */
                                    Intent intent1 = new Intent(context, MakeSurePayActivity.class);
                                    intent1.putExtra("AdvertUrl",checkPay.getAdvertUrl());
                                    startActivity(intent1);
                                }
                            }
                        }

                        @Override
                        public void onFailure(String info) {

                        }
                    });
                }
            }else {
                if (errCode==0){
                    showProgressDialog();
                    Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(BuyOnlineSeriveActivity.this).makePaySure(recordId,"dr_inquiry");
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                        @Override
                        public void onSuccess(Object o, String info) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //购买在线问诊
                                    Intent intent  = new Intent(BuyOnlineSeriveActivity.this, PatientInfomationActivity.class);
                                    intent.putExtra("doctorId",doctorId);
                                    intent.putExtra("recordId",recordId);
                                    if (isShowingProgressDialog()){
                                        dismissProgressDialog();
                                    }
                                    startActivity(intent);
                                    finish();
                                }
                            },3000);
                        }

                        @Override
                        public void onFailure(String info) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //购买在线问诊
                                    Intent intent  = new Intent(BuyOnlineSeriveActivity.this, PatientInfomationActivity.class);
                                    intent.putExtra("doctorId",doctorId);
                                    intent.putExtra("recordId",recordId);
                                    if (isShowingProgressDialog()){
                                        dismissProgressDialog();
                                    }
                                    startActivity(intent);
                                    finish();
                                }
                            },3000);
                        }
                    });
                }else {
                    //购买在线问诊
                    Intent intent2  = new Intent(BuyOnlineSeriveActivity.this, MainActivity.class);
                    startActivity(intent2);
                }
            }
        }
    }


    /**
     * 时间选择弹窗
     */
    private void showPopWindow() {
        popupwindow = new UploadPopupwindow(this, R.layout.serive_time, R.id.ll_photographs, 1000);
        popupwindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupwindow.showAtLocation(findViewById(R.id.register_root_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        popupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        final ListView listView = (ListView) popupwindow.getContentView().findViewById(R.id.mListView);

        Call<HttpBaseBean<List<DoctorTime>>> call = RetrofitFactory.getInstance(this).getDoctorTimeList(doctorId);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<DoctorTime>>() {

            @Override
            public void onSuccess(List<DoctorTime> doctorTimes, String info) {
                if (doctorTimes!=null && doctorTimes.size()>0){
                    Adapter<DoctorTime> adapter = new Adapter<DoctorTime>(context,R.layout.doctor_time_item,doctorTimes) {
                        @Override
                        protected void convert(AdapterHelper helper, final DoctorTime item) {
                            helper.setText(R.id.tv_time,item.getListTime());
                            helper.setText(R.id.tv_quota,item.getQuota()+"");
                            helper.setText(R.id.tv_fee,item.getFee()+"");

                            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tvTime.setText(item.getPageTime()+"    剩余");
                                    tvQuota.setText(item.getQuota()+"");
                                    fee = item.getFee();
                                    tvFee.setText("￥"+ item.getFee() +"/次");
                                    settingId=item.getSettingId();
                                    popupwindow.dismiss();
                                }
                            });
                        }
                    };
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(String info) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case 0:
                    if (data!=null){
                        couponId = data.getStringExtra("couponId");
                        if (!TextUtils.isEmpty(couponId) && !couponId.equals("")){
                            Call<HttpBaseBean<FactFee>> call = RetrofitFactory.getInstance(this).getFactMoney(couponId, fee);
                            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<FactFee>() {

                                @Override
                                public void onSuccess(FactFee factFee, String info) {
                                    if (factFee!=null){
                                        double factMoney = factFee.getFactFee();
                                        tvTotalPrice.setText(factMoney+"");
                                    }
                                }

                                @Override
                                public void onFailure(String info) {

                                }
                            });
                        }else {
                            tvTotalPrice.setText(fee+"");
                        }

                    }
                    break;
            }
        }
    }
}
