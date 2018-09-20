package com.ais.patient.activity.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.mine.MyReservationActivity;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.ChatOnlineMsg;
import com.ais.patient.been.DoctorMeetingTime;
import com.ais.patient.been.DoctorTime;
import com.ais.patient.been.FactFee;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.MeetingMsg;
import com.ais.patient.been.Patient;
import com.ais.patient.been.WetChat;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ConvertUtil;
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

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class BuyMeetingSeriveActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_type)
    TextView tvType;
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
    @BindView(R.id.tv_t)
    TextView tvT;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_patient)
    TextView tvPatient;
    private String doctorId;
    private Context context;
    private String fee;
    private String couponId;
    private WindowManager.LayoutParams params;
    private UploadPopupwindow popupwindow;
    private String settingId;
    private ListView mmListView;
    private UploadPopupwindow popupwindowPatient;
    private String patientId;
    private String recordId;
    private MyReceiver myReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_meeting_serive;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        tvTitle.setText("购买线下面诊服务");
        Intent intent = getIntent();
        doctorId = intent.getStringExtra("doctorId");
        String image = intent.getStringExtra("image");
        String name = intent.getStringExtra("name");
        String titles = intent.getStringExtra("titles");
        String depart = intent.getStringExtra("depart");
        String medicalInstitutions = intent.getStringExtra("medicalInstitutions");
        List<String> diseaseExpertise = (List<String>) intent.getSerializableExtra("diseaseExpertise");
        if (image != null) {
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
            tvType.setText(titles);
        }

        if (!TextUtils.isEmpty(depart)) {
            tvDepart.setText(depart);
        }

        if (!TextUtils.isEmpty(medicalInstitutions)) {
            tvMedicalInstitutions.setText("医院机构：" + medicalInstitutions);
        }
        if (diseaseExpertise.size() > 0) {
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
            Call<HttpBaseBean<List<MeetingMsg>>> call = RetrofitFactory.getInstance(this).getMeetingMsg(doctorId);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<MeetingMsg>>() {

                @Override
                public void onSuccess(List<MeetingMsg> meetingMsgs, String info) {
                    if (meetingMsgs.size()>0){
                        MeetingMsg meetingMsg = meetingMsgs.get(0);
                        if (meetingMsg!=null){
                            settingId = meetingMsg.getId();
                            String pageTime = meetingMsg.getPageTime();
                            if (!TextUtils.isEmpty(pageTime)){
                                tvTime.setText(pageTime+"    剩余");
                            }
                            int quota = meetingMsg.getQuota();
                            tvQuota.setText(quota+"");
                            fee = meetingMsg.getFee();
                            tvFee.setText("￥"+ fee +"/次");
                            tvTotalPrice.setText(fee+"");
                            String address = meetingMsg.getAddress();
                            if (!TextUtils.isEmpty(address)){
                                tvAddress.setText(address);
                            }
                            if (ConvertUtil.convertToInt(fee,0)>0){
                                /**
                                 * 优惠券可用数量
                                 */
                                Call<HttpBaseBean<Integer>> dr_inquiry = RetrofitFactory.getInstance(context).getQuantity("dr_appointment", ConvertUtil.convertToDouble(fee,0));
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
                            }else {
                                tvCardnum.setText(0+"");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(String info) {
                    showToast(info);
                }
            });
        }
        /**
         * 患者选择
         */
        showPopueWindow();
    }


    @OnClick({R.id.tv_back, R.id.tl_time, R.id.tl_card, R.id.rl_patient,R.id.tv_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tl_time:
                showPopWindow();
                break;
            case R.id.tl_card:
                Intent intent = new Intent(this,CouponsActivity.class);
                intent.putExtra("businessType","dr_appointment");
                intent.putExtra("fee",fee);
                startActivityForResult(intent,0);
                break;
            case R.id.rl_patient:
                popupwindowPatient.showAtLocation(findViewById(R.id.register_root_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_wechat:
                if (TextUtils.isEmpty(patientId)){
                    showToast("请选择患者");
                }else {
                    requestWechat();
                }
                break;
        }
    }

    /**
     * 患者选择
     */
    private void showPopueWindow() {
        popupwindowPatient = new UploadPopupwindow(this, R.layout.popupwindow_choose_patient,R.id.ll_type, RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupwindowPatient.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupwindowPatient.setAnimationStyle(R.style.style_pop_animation);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        View view = popupwindowPatient.getContentView();
        mmListView = (ListView) view.findViewById(R.id.mListView);
        final TextView tvAddPatient = (TextView) view.findViewById(R.id.tv_add_patient);
        tvAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddPatientActivity.class);
                startActivityForResult(intent,1);
            }
        });

        loadPatientList();
    }

    private void loadPatientList() {
        Call<HttpBaseBean<List<Patient>>> call = RetrofitFactory.getInstance(this).getPatientList();
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<Patient>>() {

            @Override
            public void onSuccess(List<Patient> patients, String info) {
                if (patients!=null && patients.size()>0){
                    Adapter<Patient> adapter = new Adapter<Patient>(context,R.layout.patient_item,patients) {
                        @Override
                        protected void convert(AdapterHelper helper, final Patient item) {
                            helper.setText(R.id.tv_patient_info,item.getName()+item.getSex()+item.getAge());
                            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tvPatient.setText(item.getName());
                                    patientId = item.getId();
                                    popupwindowPatient.dismiss();
                                }
                            });
                        }
                    };
                    mmListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(String info) {

            }
        });
    }

    /**
     * 微信支付
     */
    private void requestWechat() {
        UserUtils.saveChatOnLineOrMeeting(this,200);
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("payType","weixinpay_app");
        ajaxParams.put("settingsId",settingId);
        ajaxParams.put("fromType","ANDROID");
        ajaxParams.put("patientId",patientId);
        if (couponId!=null && !TextUtils.isEmpty(couponId)){
            ajaxParams.put("couponId",couponId);
        }
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Call<HttpBaseBean<WetChat>> call = RetrofitFactory.getInstance(this).requestWechat2(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<WetChat>() {

            @Override
            public void onSuccess(WetChat wxPay, String info) {
                IWXAPI api = WXAPIFactory.createWXAPI(context, wxPay.getAppid());
                api.registerApp(wxPay.getAppid());
                Log.e("Appid", "run: "+wxPay.getAppid());
                PayReq payReq = new PayReq();
                recordId = wxPay.getRecordId();
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
    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", -1);
            if (errCode==0){}
            showProgressDialog();
            Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(BuyMeetingSeriveActivity.this).makePaySure(recordId,"dr_appointment");
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                @Override
                public void onSuccess(Object o, String info) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //购买线下面诊
                            Intent intent  = new Intent(BuyMeetingSeriveActivity.this, MyReservationActivity.class);
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
                            //购买线下面诊
                            Intent intent  = new Intent(BuyMeetingSeriveActivity.this, MyReservationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },3000);
                }
            });
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

        Call<HttpBaseBean<List<DoctorMeetingTime>>> call = RetrofitFactory.getInstance(this).getDoctorMeetingTimeList(doctorId);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<DoctorMeetingTime>>() {
            @Override
            public void onSuccess(List<DoctorMeetingTime> doctorMeetingTimes, String info) {
                if (doctorMeetingTimes!=null && doctorMeetingTimes.size()>0){
                    Adapter<DoctorMeetingTime> adapter = new Adapter<DoctorMeetingTime>(context,R.layout.doctor_time_item,doctorMeetingTimes) {
                        @Override
                        protected void convert(AdapterHelper helper, final DoctorMeetingTime item) {
                            helper.setText(R.id.tv_time,item.getListTime());
                            helper.setText(R.id.tv_quota,item.getQuota()+"");
                            helper.setText(R.id.tv_fee,item.getFee()+"");
                            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tvTime.setText(item.getPageTime()+"    剩余");
                                    tvQuota.setText(item.getQuota()+"");
                                    fee = item.getFee();
                                    tvFee.setText("￥"+ fee +"/次");
                                    settingId=item.getId();
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
                        String money = data.getStringExtra("money");
                        if (couponId.equals("")){
                            tvTotalPrice.setText(fee+"");
                        }else {
                            Call<HttpBaseBean<FactFee>> call = RetrofitFactory.getInstance(context).getRealyPrice(couponId, ConvertUtil.convertToDouble(fee,0));
                            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<FactFee>() {

                                @Override
                                public void onSuccess(FactFee factFee, String info) {
                                    if (factFee!=null){
                                        double realyFee = factFee.getFactFee();
                                        tvTotalPrice.setText(realyFee+"");
                                    }
                                }

                                @Override
                                public void onFailure(String info) {

                                }
                            });
                        }
                    }
                    break;
                case 1:
                    loadPatientList();
                    break;
            }
        }
    }

}
