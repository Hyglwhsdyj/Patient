package com.ais.patient.activity.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.MainDotcor;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.widget.CircleTransform;
import com.ais.patient.widget.FlowGroupView;
import com.ais.patient.widget.MyScrollView;
import com.ais.patient.widget.VpSwipeRefreshLayout;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherDoctorActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private Context context;

    private RecyclerAdapter<MainDotcor.DataBean> adapter;
    List<MainDotcor.DataBean> list = new ArrayList<>();
    private FlowGroupView mFlowGroupView;
    private String doctorId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_doctor;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        tvTitle.setText("医生列表");
        doctorId = getIntent().getStringExtra("doctorId");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
            Call<MainDotcor> call = RetrofitFactory.getInstance(context).getOtherDoctorList(doctorId);
            call.enqueue(new Callback<MainDotcor>() {
                @Override
                public void onResponse(Call<MainDotcor> call, Response<MainDotcor> response) {
                    MainDotcor mainDotcor = response.body();
                    List<MainDotcor.DataBean> data = mainDotcor.getData();
                    if (data.size()>0){
                        list.addAll(data);
                        adapter = new RecyclerAdapter<MainDotcor.DataBean>(context, R.layout.main_doctor_item,list) {
                            @Override
                            protected void convert(RecyclerAdapterHelper helper, final MainDotcor.DataBean item) {
                                helper.setVisible(R.id.tv_choose_other,true);
                                ImageView ivIcon = (ImageView) helper.getItemView().findViewById(R.id.iv_icon);
                                Picasso.with(context).load(item.getImage()).transform(new CircleTransform()).into(ivIcon);
                                helper.setText(R.id.tv_name,item.getName());
                                helper.setText(R.id.tv_title,item.getTitles());
                                helper.setText(R.id.tv_depart,item.getDepart());
                                helper.setText(R.id.tv_medicalInstitutions,"医院机构："+item.getMedicalInstitutions());
                                helper.setText(R.id.tv_fee,item.getFee()+"元/次");
                                helper.setText(R.id.tv_num,"诊疗案例"+item.getCaseNum()+"个");
                                helper.setText(R.id.tv_year,"临床经验"+item.getClinicalExperience()+"年");

                                final List<String> diseaseExpertise = item.getDiseaseExpertise();
                                mFlowGroupView = (FlowGroupView) helper.getItemView().findViewById(R.id.mFlowGroupView);
                                if (diseaseExpertise!=null && diseaseExpertise.size()>0){
                                    for (int i = 0; i < diseaseExpertise.size(); i++) {
                                        addTextView(diseaseExpertise.get(i));
                                    }
                                }

                                helper.setOnClickListener(R.id.tv_choose_other, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String id = item.getId();
                                        Intent intent = new Intent(context,DoctorInfomationActivity.class);
                                        intent.putExtra("id",id);
                                        startActivity(intent);
                                    }
                                });
                                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String id = item.getId();
                                        Intent intent = new Intent(context,DoctorInfomationActivity.class);
                                        intent.putExtra("id",id);
                                        startActivity(intent);
                                    }
                                });
                            }
                        };
                    }
                    mRecyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<MainDotcor> call, Throwable t) {

                }
            });
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


}
