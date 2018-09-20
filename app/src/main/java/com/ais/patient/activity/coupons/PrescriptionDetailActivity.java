package com.ais.patient.activity.coupons;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.main.DoctorInfomationActivity;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.PrescriptionDetail;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.HtmlImageGetter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class PrescriptionDetailActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_main)
    TextView tvTitleMain;
    @BindView(R.id.tv_function)
    TextView tvFunction;
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R.id.tv_doctor_introduce)
    TextView tvDoctorIntroduce;
    @BindView(R.id.tv_cases)
    TextView tvCases;
    @BindView(R.id.tv_cou1)
    TextView tvCou1;
    @BindView(R.id.tv_cou2)
    TextView tvCou2;
    @BindView(R.id.tv_cou3)
    TextView tvCou3;
    @BindView(R.id.tv_cou4)
    TextView tvCou4;
    private String secret_id;
    private String baseUrl;
    private String doctorId;
    private Context context;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_prescription_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        tvTitle.setText("秘方详情");

        //得到AssetManager
        AssetManager mgr = context.getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/JDFYUANF.TTF");
        //设置字体
        tvCou1.setTypeface(tf);
        tvCou2.setTypeface(tf);
        tvCou3.setTypeface(tf);
        tvCou4.setTypeface(tf);

        secret_id = getIntent().getStringExtra("Secret_id");
        if (secret_id != null && !TextUtils.isEmpty(secret_id)) {
            baseUrl = "/api/secret_recipe/" + secret_id;
        }

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        if (baseUrl != null && !TextUtils.isEmpty(baseUrl)) {
            Call<HttpBaseBean<PrescriptionDetail>> call = RetrofitFactory.getInstance(this).getPrescriptionDetail(baseUrl);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<PrescriptionDetail>() {

                @Override
                public void onSuccess(PrescriptionDetail prescriptionDetail, String info) {
                    if (prescriptionDetail != null) {
                        String function = prescriptionDetail.getFunction();
                        if (function != null) {
                            tvFunction.setText(function);
                        }
                        final String introduce = prescriptionDetail.getIntroduce();
                        if (introduce != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //tvIntroduce.setText(Html.fromHtml(introduce,imgGetter,null));
                                    HtmlImageGetter htmlImageGetter = new HtmlImageGetter(context, tvIntroduce);
                                    tvIntroduce.setText(Html.fromHtml(introduce, htmlImageGetter, null));
                                    /*tvIntroduce.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效
                                    URLImageGetter ReviewImgGetter = new URLImageGetter(PrescriptionDetailActivity.this, tvIntroduce);//实例化URLImageGetter类
                                    tvIntroduce.setText(Html.fromHtml(introduce,ReviewImgGetter,null));*/
                                }
                            });
                        }
                        final String doctor_introduce = prescriptionDetail.getDoctor_introduce();
                        if (doctor_introduce != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    HtmlImageGetter htmlImageGetter = new HtmlImageGetter(context, tvDoctorIntroduce);
                                    tvDoctorIntroduce.setText(Html.fromHtml(doctor_introduce, htmlImageGetter, null));
                                    /*tvDoctorIntroduce.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效
                                    URLImageGetter ReviewImgGetter = new URLImageGetter(PrescriptionDetailActivity.this, tvDoctorIntroduce);//实例化URLImageGetter类
                                    tvDoctorIntroduce.setText(Html.fromHtml(doctor_introduce,ReviewImgGetter,null));*/
                                }
                            });
                        }

                        String title = prescriptionDetail.getTitle();
                        if (title != null) {
                            tvTitleMain.setText(title);
                        }

                        final String cases = prescriptionDetail.getCases();
                        if (cases != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //tvCases.setText(Html.fromHtml(cases,imgGetter,null));
                                    HtmlImageGetter htmlImageGetter = new HtmlImageGetter(context, tvCases);
                                    tvCases.setText(Html.fromHtml(cases, htmlImageGetter, null));
                                    /*tvCases.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效
                                    URLImageGetter ReviewImgGetter = new URLImageGetter(PrescriptionDetailActivity.this, tvCases);//实例化URLImageGetter类
                                    tvCases.setText(Html.fromHtml(cases,ReviewImgGetter,null));*/
                                }
                            });
                        }

                        doctorId = prescriptionDetail.getDoctorId();
                    }
                }

                @Override
                public void onFailure(String info) {

                }
            });
        }
    }


    @OnClick({R.id.tv_back, R.id.tv_chat_online})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_chat_online:
                if (doctorId != null && !TextUtils.isEmpty(doctorId)) {
                    Intent intent = new Intent(this, DoctorInfomationActivity.class);
                    intent.putExtra("id", doctorId);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
