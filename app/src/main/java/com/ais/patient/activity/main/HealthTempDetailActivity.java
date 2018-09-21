package com.ais.patient.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HealthTempDetail;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.widget.NetstedListView;
import com.bumptech.glide.Glide;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class HealthTempDetailActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tv_disease)
    TextView tvDisease;
    @BindView(R.id.tv_disease_before)
    TextView tvDiseaseBefore;
    @BindView(R.id.tv_afterInfo)
    TextView tvAfterInfo;
    @BindView(R.id.mListView1)
    NetstedListView mListView1;
    @BindView(R.id.mListView2)
    NetstedListView mListView2;
    private String id;
    private Context context;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_health_temp_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        showProgressDialog();
        id = getIntent().getStringExtra("id");
        tvTitle.setText("康复案例");

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        Call<HttpBaseBean<HealthTempDetail>> call = RetrofitFactory.getInstance(this).gteHealthTempDetail(id);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<HealthTempDetail>() {

            @Override
            public void onSuccess(HealthTempDetail healthTempDetail, String info) {
                if (healthTempDetail != null) {
                    tvMsg.setText(healthTempDetail.getName()+"："+healthTempDetail.getSex()+","+healthTempDetail.getAge()+"岁");
                    tvDisease.setText(healthTempDetail.getDisease());
                    tvAfterInfo.setText(healthTempDetail.getBeforeInfo());

                    List<String> beforeImage = healthTempDetail.getBeforeImage();
                    if (beforeImage!=null && beforeImage.size()>0){
                        Adapter<String> adapter = new Adapter<String>(context,R.layout.health_temp_img,beforeImage) {
                            @Override
                            protected void convert(AdapterHelper helper, String item) {
                                ImageView ivImg = helper.getItemView().findViewById(R.id.iv_img);
                                Glide.with(context).load(item).into(ivImg);
                            }
                        };
                        mListView1.setAdapter(adapter);
                    }

                    List<String> afterImage = healthTempDetail.getAfterImage();
                    if (afterImage!=null && afterImage.size()>0){
                        Adapter<String> adapter = new Adapter<String>(context,R.layout.health_temp_img,afterImage) {
                            @Override
                            protected void convert(AdapterHelper helper, String item) {
                                ImageView ivImg = helper.getItemView().findViewById(R.id.iv_img);
                                Glide.with(context).load(item).into(ivImg);
                            }
                        };
                        mListView1.setAdapter(adapter);
                    }
                    if (isShowingProgressDialog()){
                        dismissProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(String info) {
                if (isShowingProgressDialog()){
                    dismissProgressDialog();
                }
                showToast(info);
            }
        });
    }


    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
