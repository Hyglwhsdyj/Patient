package com.ais.patient.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.AddAppraise;
import com.ais.patient.been.AppraiseDetial;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.UpdateStatus;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class AppraiseDetialActivity extends MYBaseActivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_serviceName)
    TextView tvServiceName;
    @BindView(R.id.popup_ratingbar)
    AppCompatRatingBar popupRatingbar;
    @BindView(R.id.rd_attitude1)
    RadioButton rdAttitude1;
    @BindView(R.id.rd_attitude2)
    RadioButton rdAttitude2;
    @BindView(R.id.rd_attitude3)
    RadioButton rdAttitude3;
    @BindView(R.id.mRadioGroup1)
    RadioGroup mRadioGroup1;
    @BindView(R.id.popup_ratingbar2)
    AppCompatRatingBar popupRatingbar2;
    @BindView(R.id.rd_speed1)
    RadioButton rdSpeed1;
    @BindView(R.id.rd_speed2)
    RadioButton rdSpeed2;
    @BindView(R.id.mRadioGroup2)
    RadioGroup mRadioGroup2;
    @BindView(R.id.popup_ratingbar3)
    AppCompatRatingBar popupRatingbar3;
    @BindView(R.id.rd_medical1)
    RadioButton rdMedical1;
    @BindView(R.id.rd_medical2)
    RadioButton rdMedical2;
    @BindView(R.id.rd_medical3)
    RadioButton rdMedical3;
    @BindView(R.id.mRadioGroup3)
    RadioGroup mRadioGroup3;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private String orderId;
    private List<String> attitudeLabs = new ArrayList<>();
    private List<String> speedLabs = new ArrayList<>();
    private List<String> medicalLabs = new ArrayList<>();
    private String attitude;
    private String speed;
    private String medical;
    private boolean isPower;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appraise_detial;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("评价服务");
        Intent intent = getIntent();
        //15344848563702018081725901
        orderId = intent.getStringExtra("orderId");
        String name = intent.getStringExtra("name");
        String serviceName = intent.getStringExtra("serviceName");

        if (!TextUtils.isEmpty(name)){
            //说明是来评价的
            tvServiceName.setText(name+serviceName);
        }else {
            //说明是来看详情的
            tvCommit.setEnabled(false);
            popupRatingbar.setIsIndicator(true);
            popupRatingbar2.setIsIndicator(true);
            popupRatingbar3.setIsIndicator(true);

            for (int i = 0; i < mRadioGroup1.getChildCount(); i++) {
                mRadioGroup1.getChildAt(i).setEnabled(false);
            }
            for (int i = 0; i < mRadioGroup2.getChildCount(); i++) {
                mRadioGroup2.getChildAt(i).setEnabled(false);
            }
            for (int i = 0; i < mRadioGroup3.getChildCount(); i++) {
                mRadioGroup3.getChildAt(i).setEnabled(false);
            }

            if (!TextUtils.isEmpty(orderId)){
                String baseUrl = "/api/appraise/"+orderId;
                Call<HttpBaseBean<AppraiseDetial>> call = RetrofitFactory.getInstance(this).seeAppraiseDetail(baseUrl);
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<AppraiseDetial>() {

                    @Override
                    public void onSuccess(AppraiseDetial appraiseDetial, String info) {
                        if (appraiseDetial!=null){
                            tvServiceName.setText(appraiseDetial.getDoctorName()+appraiseDetial.getServiceName());
                            List<AppraiseDetial.AppraisesBean> list = appraiseDetial.getAppraises();

                            int score = list.get(0).getScore();
                            String labels = list.get(0).getLabels().get(0);
                            popupRatingbar.setRating(score);
                            if (labels.equals("态度好")){
                                rdAttitude1.setChecked(true);
                            }else if (labels.equals("态度一般")){
                                rdAttitude2.setChecked(true);
                            }else if (labels.equals("态度不佳")){
                                rdAttitude3.setChecked(true);
                            }

                            int score2 = list.get(1).getScore();
                            popupRatingbar2.setRating(score2);
                            String label2 = list.get(1).getLabels().get(0);
                            if (label2.equals("回复及时")){
                                rdSpeed1.setChecked(true);
                            }else if (label2.equals("回复时间较长")){
                                rdSpeed2.setChecked(true);
                            }

                            int score3 = list.get(2).getScore();
                            String labels3 = list.get(2).getLabels().get(0);
                            popupRatingbar3.setRating(score3);
                            if (labels3.equals("效果很好，见效快")){
                                rdMedical1.setChecked(true);
                            }else if (labels3.equals("效果一般")){
                                rdMedical2.setChecked(true);
                            }else if (labels3.equals("没什么效果")){
                                rdMedical3.setChecked(true);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String info) {

                    }
                });
            }

        }
    }

    @Override
    protected void initEvent() {
        mRadioGroup1.setOnCheckedChangeListener(this);
        mRadioGroup2.setOnCheckedChangeListener(this);
        mRadioGroup3.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_back, R.id.reMake, R.id.reMake2, R.id.reMake3,R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.reMake:
                if (isPower){
                    popupRatingbar.setIsIndicator(true);
                    for (int i = 0; i < mRadioGroup1.getChildCount(); i++) {
                        mRadioGroup1.getChildAt(i).setEnabled(true);
                    }
                    tvCommit.setEnabled(true);
                    showToast("请重新评价");
                }else {
                    showToast("不允许重新评价");
                }

                break;
            case R.id.reMake2:
                Call<HttpBaseBean<UpdateStatus>> call = RetrofitFactory.getInstance(this).isUpdateAppraise(orderId);
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<UpdateStatus>() {

                    @Override
                    public void onSuccess(UpdateStatus updateStatus, String info) {
                        if (updateStatus!=null){
                            isPower = updateStatus.isIsPower();
                            if (isPower){
                                popupRatingbar2.setIsIndicator(true);
                                for (int i = 0; i < mRadioGroup2.getChildCount(); i++) {
                                    mRadioGroup2.getChildAt(i).setEnabled(true);
                                }
                                tvCommit.setEnabled(true);
                                showToast("请重新评价");
                            }else {
                                showToast("不允许重新评价");
                            }
                        }
                    }

                    @Override
                    public void onFailure(String info) {
                        showToast(info);
                    }
                });
                break;
            case R.id.reMake3:
                if (isPower){
                    popupRatingbar3.setIsIndicator(true);
                    for (int i = 0; i < mRadioGroup3.getChildCount(); i++) {
                        mRadioGroup3.getChildAt(i).setEnabled(true);
                    }
                    tvCommit.setEnabled(true);
                    showToast("请重新评价");
                }else {
                    showToast("不允许重新评价");
                }
                break;
            case R.id.tv_commit:
                if (attitudeLabs.size()<1){
                    showToast("请评价服务态度");
                }else if (speedLabs.size()<1){
                    showToast("请评价回复速度");
                }if (medicalLabs.size()<1){
                    showToast("请评价处方疗效");
                }else {

                AddAppraise.AppraisesBean bean = new AddAppraise.AppraisesBean();
                bean.setItem("服务态度");
                bean.setScore(popupRatingbar.getRating());
                bean.setLabels(attitudeLabs);

                AddAppraise.AppraisesBean bean2 = new AddAppraise.AppraisesBean();
                bean2.setItem("回复速度");
                bean2.setScore(popupRatingbar2.getRating());
                bean2.setLabels(speedLabs);

                AddAppraise.AppraisesBean bean3 = new AddAppraise.AppraisesBean();
                bean3.setItem("处方疗效");
                bean3.setScore(popupRatingbar3.getRating());
                bean3.setLabels(medicalLabs);

                List<AddAppraise.AppraisesBean> list = new ArrayList<>();
                list.add(bean);
                list.add(bean2);
                list.add(bean3);


                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put("orderId",orderId);

                ajaxParams.put("appraises",list);
                ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
                Log.e("onViewClicked", "onViewClicked: "+urlParams.toString());
                Call<HttpBaseBean<Object>> call2 = RetrofitFactory.getInstance(this).commitAppraise(urlParams);
                new BaseCallback(call2).handleResponse(new BaseCallback.ResponseListener() {
                    @Override
                    public void onSuccess(Object o, String info) {
                        showToast("提交成功");
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(String info) {
                        showToast(info);
                    }
                });
            }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()){
            case R.id.mRadioGroup1:
                attitudeLabs.clear();
                if (checkedId==R.id.rd_attitude1){
                    attitude = "态度好";
                }else if (checkedId==R.id.rd_attitude2){
                    attitude = "态度一般";
                }else if (checkedId==R.id.rd_attitude3){
                    attitude = "态度不佳";
                }
                attitudeLabs.add(attitude);
                break;
            case R.id.mRadioGroup2:
                speedLabs.clear();
                if (checkedId==R.id.rd_speed1){
                    speed = "回复及时";
                }else if (checkedId==R.id.rd_speed2){
                    speed = "回复时间较长";
                }
                speedLabs.add(speed);
                break;
            case R.id.mRadioGroup3:
                medicalLabs.clear();
                if (checkedId==R.id.rd_medical1){
                    medical = "效果很好，见效快";
                }else if (checkedId==R.id.rd_medical2){
                    medical = "效果一般";
                }else if (checkedId==R.id.rd_medical3){
                    medical = "没什么效果";
                }
                medicalLabs.add(medical);
                break;
        }
    }
}
