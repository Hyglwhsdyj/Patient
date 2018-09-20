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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.mine.MyAdressActivity;
import com.ais.patient.activity.mine.MyOrdonnanceActivity;
import com.ais.patient.activity.mine.MyReservationActivity;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.DoctorNewList;
import com.ais.patient.been.FactFee;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.MyAdressResponse;
import com.ais.patient.been.OrdonnanceDetailRespone;
import com.ais.patient.been.WetChat;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.DisplayUtil;
import com.ais.patient.util.UserUtils;
import com.ais.patient.widget.NetstedListView;
import com.ais.patient.widget.UploadPopupwindow;
import com.ais.patient.widget.WarpLinearLayout;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class BuyMedicineActivity extends MYBaseActivity {

    private String recordId;
    private Context context;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_patient_name)
    TextView tvPatientName;
    @BindView(R.id.tv_patient_sex)
    TextView tvPatientSex;
    @BindView(R.id.tv_patient_age)
    TextView tvPatientAge;
    @BindView(R.id.tv_patient_phone)
    TextView tvPatientPhone;
    @BindView(R.id.tv_medical_history)
    TextView tvMedicalHistory;
    @BindView(R.id.tv_medical_describe)
    TextView tvMedicalDescribe;
    @BindView(R.id.tv_visit_time)
    TextView tvVisitTime;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_iless_resume)
    TextView tvIlessResume;
    @BindView(R.id.tv_diagnose_suggest)
    TextView tvDiagnoseSuggest;


    @BindView(R.id.tv_momeny_unit1)
    TextView tvMomenyUnit1;
    @BindView(R.id.tv_diagnosis_fee)
    TextView tvDiagnosisFee;
    @BindView(R.id.tv_medicine_price)
    TextView tvMedicinePrice;
    @BindView(R.id.tv_make_price)
    TextView tvMakePrice;
    @BindView(R.id.tv_transport_price)
    TextView tvTransportPrice;
    @BindView(R.id.tv_person_name)
    TextView tvPersonName;
    @BindView(R.id.tv_person_phone)
    TextView tvPersonPhone;
    @BindView(R.id.tv_address5)
    TextView tvAddress;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_moren)
    TextView tvMoren;

    @BindView(R.id.tv_isFried)
    TextView tvIsFried;
    @BindView(R.id.tv_cardnum)
    TextView tvCardNum;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.mNetstedListView)
    NetstedListView mNetstedListView;
    private WindowManager.LayoutParams params;
    boolean isFried;
    private UploadPopupwindow popupwindow;
    private String orderId;
    private String addressId;
    private String couponId;
    private double totalFee;
    private MyReceiver myReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_medicine;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("购买中药调理服务 ");
        tvRight.setText("查看我的问诊 ");
        context = this;
        Intent intent = getIntent();
        recordId = intent.getStringExtra("recordId");
        if (!TextUtils.isEmpty(recordId)) {
            String baseUrl = "/api/order/my/to_create_order/" + recordId;
            Call<HttpBaseBean<OrdonnanceDetailRespone>> call = RetrofitFactory.getInstance(this).getBuyMedicineDetial(baseUrl);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<OrdonnanceDetailRespone>() {

                @Override
                public void onSuccess(OrdonnanceDetailRespone reservations, String info) {
                    if (reservations != null) {
                        String patientName = reservations.getName();
                        tvPatientName.setText(reservations.getName());
                        tvPatientSex.setText(reservations.getSex());
                        tvPatientAge.setText(reservations.getAge() + "");
                        tvPatientPhone.setText(reservations.getPhoneNumber());
                        tvMedicalHistory.setText(reservations.getMedicalHistory());
                        tvMedicalDescribe.setText(reservations.getRemarks());
                        tvVisitTime.setText(reservations.getApplyTime());
                        tvPayTime.setText(reservations.getLimitTime());
                        tvIlessResume.setText(reservations.getDiseaseDescribe());
                        tvDiagnoseSuggest.setText(reservations.getAdviceDiacrisis());

                        List<OrdonnanceDetailRespone.DrugsBean> drugs = reservations.getDrugs();
                        Adapter<OrdonnanceDetailRespone.DrugsBean> adapter = new Adapter<OrdonnanceDetailRespone.DrugsBean>(context,R.layout.ordonnance_detail_medicinal_info,drugs) {
                            @Override
                            protected void convert(AdapterHelper helper, OrdonnanceDetailRespone.DrugsBean item) {
                                helper.setText(R.id.tv_yaocai,item.getDrugType());
                                helper.setText(R.id.tv_medicinal_nums,item.getDrugNum()+"");
                                helper.setText(R.id.tv_medicinal_weight,item.getWeight()+"g");
                                helper.setText(R.id.tv_supplier,item.getSupplier());
                                helper.setText(R.id.tv_do_medicinal_nums,item.getPlaster()+"");
                                helper.setText(R.id.tv_user_method,item.getUserMethod()+"，"+item.getTakingTime());
                                WarpLinearLayout tvMedicinalDetail =helper.getItemView().findViewById(R.id.tv_medicinal_detail);
                                List<OrdonnanceDetailRespone.DrugsBean.DrugTempsBean> drugTemps = item.getDrugTemps();
                                tvMedicinalDetail.removeAllViews();
                                for (int i = 0; i < drugTemps.size(); i++) {
                                    View view1 = View.inflate(context, R.layout.item, null);
                                    TextView tv_content = view1.findViewById(R.id.tv_content);
                                    tv_content.setText(drugTemps.get(i).getName() + "：" + drugTemps.get(i).getNum() + drugTemps.get(i).getUnit());
                                    ViewGroup.LayoutParams params = tv_content.getLayoutParams();
                                    params.width = DisplayUtil.getScreenWidth(context) / 2 - DisplayUtil.dip2px(context, 30);
                                    tv_content.setLayoutParams(params);
                                    tvMedicinalDetail.addView(view1);
                                }

                            }
                        };
                        mNetstedListView.setAdapter(adapter);

                        tvDiagnosisFee.setText(reservations.getDiagnosisFee() + "");
                        tvMedicinePrice.setText(reservations.getDrugFee() + "");
                        tvMakePrice.setText(reservations.getMakeFee() + "");
                        tvTransportPrice.setText(reservations.getExpressFee() + "");
                        totalFee = reservations.getTotalFee();
                        tvTotalPrice.setText(totalFee +"");

                        tvPersonName.setText(reservations.getConsignee());
                        tvPersonPhone.setText(reservations.getConsigneeTel());
                        tvAddress.setText(reservations.getAddress());

                        orderId = reservations.getOrderId();
                        addressId = reservations.getAddressId();

                        Call<HttpBaseBean<Integer>> quantity = RetrofitFactory.getInstance(context).getQuantity2("dr_prescription", totalFee,recordId);
                        new BaseCallback(quantity).handleResponse(new BaseCallback.ResponseListener<Integer>() {

                            @Override
                            public void onSuccess(Integer integer, String info) {
                                tvCardNum.setText(integer+"");
                            }

                            @Override
                            public void onFailure(String info) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(String info) {
                    showToast(info);
                }
            });
        }

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

    }


    @OnClick({R.id.tv_back, R.id.tv_right,R.id.rl_isFried,R.id.tv_addAddress,R.id.logistics_tracking_layout,R.id.tv_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_right:
                finish();
                break;
            case R.id.rl_isFried:
                showPopWindow();
                break;
            case R.id.tv_addAddress:
                startActivityForResult(new Intent(context, MyAdressActivity.class),0);
                break;
            case R.id.logistics_tracking_layout:
                Intent intent = new Intent(context, CouponsActivity.class);
                intent.putExtra("businessType","dr_prescription");
                intent.putExtra("businessId",recordId);
                intent.putExtra("fee",totalFee);
                startActivityForResult(intent,1);
                break;
            case R.id.tv_wechat:
                //微信支付
                requestWechatPay();
                break;
        }
    }

    /**
     * 微信支付
     */
    private void requestWechatPay() {
        UserUtils.saveChatOnLineOrMeeting(this,300);
        UserUtils.saveChatOnLineRECORDID(this,recordId);
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("payType","weixinpay_app");
        if (!TextUtils.isEmpty(couponId)){
            ajaxParams.put("couponId",couponId);
        }
        ajaxParams.put("orderId",orderId);
        ajaxParams.put("fromType","ANDROID");
        ajaxParams.put("addressId",addressId);
        ajaxParams.put("friedStatus",isFried);
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Call<HttpBaseBean<WetChat>> call = RetrofitFactory.getInstance(this).requestWechatDrug(urlParams);
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
            if (errCode==0){
                showProgressDialog();
                Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(BuyMedicineActivity.this).makePaySure(recordId,"dr_prescription");
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                    @Override
                    public void onSuccess(Object o, String info) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //购买处方单
                                Intent intent  = new Intent(BuyMedicineActivity.this, MyOrdonnanceActivity.class);
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
                                //购买处方单
                                Intent intent  = new Intent(BuyMedicineActivity.this, MyOrdonnanceActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },3000);
                    }
                });
            }else {
                //购买线下面诊
                Intent intent2  = new Intent(BuyMedicineActivity.this, MyOrdonnanceActivity.class);
                startActivity(intent2);
                finish();
            }

        }
    }

    private void showPopWindow() {
        popupwindow = new UploadPopupwindow(this, R.layout.layout_isfried, R.id.ll_photographs, 1000);
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
        TextView tvIsFried = (TextView) popupwindow.getContentView().findViewById(R.id.tv_isFried);
        TextView tvNotFried = (TextView) popupwindow.getContentView().findViewById(R.id.tv_notFried);
        tvIsFried.setOnClickListener(this);
        tvNotFried.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_isFried:
                isFried = true;
                tvIsFried.setText("代煎");
                popupwindow.dismiss();
                break;
            case R.id.tv_notFried:
                isFried = false;
                tvIsFried.setText("不代煎");
                popupwindow.dismiss();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case 0:
                    MyAdressResponse address = (MyAdressResponse) data.getSerializableExtra("address");
                    tvPersonName.setText(address.getName());
                    tvPersonPhone.setText(address.getPhoneNumber());
                    tvAddress.setText(address.getAddress());
                    addressId = address.getAddressId();
                    tvMoren.setVisibility(View.GONE);
                    break;
                case 1:
                    if(data!=null){
                        couponId = data.getStringExtra("couponId");
                        if (couponId.equals("")){
                            tvTotalPrice.setText(totalFee+"");
                        }else {
                            Call<HttpBaseBean<FactFee>> call = RetrofitFactory.getInstance(context).getRealyPrice(couponId, totalFee);
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
            }
        }
    }
}
