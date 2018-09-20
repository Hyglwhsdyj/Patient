package com.ais.patient.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.adapter.LogisticsAdapter;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.OrdonnanceDetailRespone;
import com.ais.patient.been.WetChat;
import com.ais.patient.http.API;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.DisplayUtil;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.NetstedListView;
import com.ais.patient.widget.UploadPopupwindow;
import com.ais.patient.widget.WarpLinearLayout;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
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

public class OrdonnanceDetailActivity extends MYBaseActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
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

    @BindView(R.id.tv_isFried)
    TextView tvIsFried;
    @BindView(R.id.mNetstedListView)
    NetstedListView mNetstedListView;
    @BindView(R.id.ll_logist)
    LinearLayout llLogist;
    @BindView(R.id.layout_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.tv_bottom1)
    TextView tvBottom1;
    @BindView(R.id.tv_bottom2)
    TextView tvBottom2;


    private WindowManager.LayoutParams params;
    private UploadPopupwindow popupwindow;
    private String orderId;
    private String addressId;
    private String couponId;
    private double totalFee;
    private String url_str;
    private String isFried;
    private int orderStatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ordonnance_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        context = OrdonnanceDetailActivity.this;
        Intent intent = getIntent();

        orderId = intent.getStringExtra("orderId");
        orderStatus = intent.getIntExtra("orderStatus", 0);
        Log.i("orderId", orderId + "  orderStatus: " + orderStatus);
        switch (orderStatus) {
            case 1://待支付
                tvBottom2.setVisibility(View.VISIBLE);
                url_str = API.ORDONNANCE_DETAIL_WAIT_PAY_URL + orderId;
                break;
            case 2://已支付--待收货
                llLogist.setVisibility(View.VISIBLE);
                rlBottom.setVisibility(View.GONE);
                url_str = API.ORDONTRANING_DETAIL_URL + orderId;
                break;
            case 3://订单已完成
                llLogist.setVisibility(View.VISIBLE);
                rlBottom.setVisibility(View.GONE);
                url_str = API.ORDONNANCE_DETAIL_URL + orderId;
                break;
        }

        tvTitle.setText(getResources().getString(R.string.prescription_detail));

        getData();
    }

    @OnClick({R.id.tv_back, R.id.tv_bottom1, R.id.tv_bottom2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_bottom1:
                //评价医生 或者取消订单
                if (orderStatus==1){
                    Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).getOrdonnanceCancel(API.ORDONNANCE_DETAIL_CANCEL_URL+orderId);
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
                /*else if (orderStatus==3){
                    Intent intent = new Intent(context,AppraiseDetialActivity.class);
                    intent.putExtra("orderId",orderId);
                    startActivity(intent);
                }*/
                break;
            case R.id.tv_bottom2:
                //支付
                requestWechat();
                break;
        }
    }

    /**
     * 微信支付
     */
    private void requestWechat() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("payType", "weixinpay_app");
        ajaxParams.put("orderId", orderId);
        ajaxParams.put("fromType", "ANDROID");
        ajaxParams.put("addressId", addressId);
        if (isFried.equals("不代煎")){
            ajaxParams.put("friedStatus",false );
        }else if (isFried.equals("代煎")){
            ajaxParams.put("friedStatus",true );
        }

        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Call<HttpBaseBean<WetChat>> call = RetrofitFactory.getInstance(this).requestWechat2(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<WetChat>() {

            @Override
            public void onSuccess(WetChat wxPay, String info) {
                IWXAPI api = WXAPIFactory.createWXAPI(context, wxPay.getAppid());
                api.registerApp(wxPay.getAppid());
                Log.e("Appid", "run: " + wxPay.getAppid());
                PayReq payReq = new PayReq();
                payReq.appId = wxPay.getAppid();
                payReq.partnerId = wxPay.getPartnerid();
                Log.e("partnerId", "run: " + wxPay.getPartnerid());
                payReq.prepayId = wxPay.getPrepayid();
                Log.e("prepayId", "run: " + wxPay.getPrepayid());
                payReq.packageValue = wxPay.getPackageX();
                Log.e("packageValue", "run: " + wxPay.getPackageX());
                payReq.nonceStr = wxPay.getNoncestr();
                Log.e("nonceStr", "run: " + wxPay.getNoncestr());
                payReq.timeStamp = wxPay.getTimestamp();
                Log.e("timeStamp", "run: " + wxPay.getTimestamp());
                payReq.sign = wxPay.getSign();
                Log.e("sign", "run: " + wxPay.getSign());
                api.sendReq(payReq);
            }

            @Override
            public void onFailure(String info) {
                showToast(info);
            }
        });
    }


    /**
     * 获取详情数据
     */
    private void getData() {
        if (TextUtils.isEmpty(url_str)) {
            return;
        }
        Call<HttpBaseBean<OrdonnanceDetailRespone>> call = RetrofitFactory.getInstance(this).getBuyMedicineDetial(url_str);
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
                    isFried = drugs.get(0).getIsFried();
                    tvIsFried.setText(isFried);
                    Adapter<OrdonnanceDetailRespone.DrugsBean> adapter = new Adapter<OrdonnanceDetailRespone.DrugsBean>(context, R.layout.ordonnance_detail_medicinal_info, drugs) {
                        @Override
                        protected void convert(AdapterHelper helper, OrdonnanceDetailRespone.DrugsBean item) {
                            helper.setText(R.id.tv_yaocai, item.getDrugType());
                            helper.setText(R.id.tv_medicinal_nums, item.getDrugNum() + "");
                            helper.setText(R.id.tv_medicinal_weight, item.getWeight() + "g");
                            helper.setText(R.id.tv_supplier, item.getSupplier());
                            helper.setText(R.id.tv_do_medicinal_nums, item.getPlaster() + "");

                            NetstedListView mNetstedListView = helper.getItemView().findViewById(R.id.mNetstedListView);
                            if (!TextUtils.isEmpty(item.getUserMethod()) && !TextUtils.isEmpty(item.getTakingTime())) {
                                helper.setVisible(R.id.tv_user_method, true);
                                helper.setVisible(R.id.mNetstedListView, false);
                                helper.setText(R.id.tv_user_method, "用法用量：" + item.getUserMethod() + "，" + item.getTakingTime());
                            } else {
                                helper.setVisible(R.id.tv_user_method, false);
                                helper.setVisible(R.id.mNetstedListView, true);
                                List<String> specialUsemethod = item.getSpecialUsemethod();
                                specialUsemethod.add(0,"用法用量：");
                                Adapter<String> adapter1 = new Adapter<String>(context, R.layout.special_item, specialUsemethod) {
                                    @Override
                                    protected void convert(AdapterHelper helper, String item) {
                                        helper.setText(R.id.tv_user_method, item);
                                    }
                                };
                                mNetstedListView.setAdapter(adapter1);
                                specialUsemethod.remove(0);
                            }
                            WarpLinearLayout tvMedicinalDetail = helper.getItemView().findViewById(R.id.tv_medicinal_detail);
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

                    tvPersonName.setText(reservations.getConsignee());
                    tvPersonPhone.setText(reservations.getConsigneeTel());
                    tvAddress.setText(reservations.getAddress());

                    orderId = reservations.getOrderId();
                    addressId = reservations.getAddressId();

                    /**
                     * 物流
                     */
                    List<OrdonnanceDetailRespone.LogisticsBean.DataBean> list = new ArrayList<>();
                    List<OrdonnanceDetailRespone.LogisticsBean> logistics = reservations.getLogistics();
                    if (logistics!=null && logistics.size()>0){
                        for (int i = 0; i < logistics.size(); i++) {
                            String logisticsName = logistics.get(i).getLogisticsName();
                            String logisticsNo = logistics.get(i).getLogisticsNo();
                            List<OrdonnanceDetailRespone.LogisticsBean.DataBean> data = logistics.get(i).getData();
                            OrdonnanceDetailRespone.LogisticsBean.DataBean bean = new OrdonnanceDetailRespone.LogisticsBean.DataBean();
                            bean.setTime(logisticsName);
                            bean.setContext(logisticsNo);
                            bean.setType(2);
                            data.add(0, bean);
                            data.get(1).setType(1);
                            list.addAll(data);
                        }
                        LogisticsAdapter adapterLogist = new LogisticsAdapter(context, list);
                        mRecyclerView.setAdapter(adapterLogist);
                    }
                }
            }

            @Override
            public void onFailure(String info) {
                showToast(info);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
