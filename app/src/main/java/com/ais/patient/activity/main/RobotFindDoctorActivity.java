package com.ais.patient.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.SymptomReback;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.widget.CircleTransform;
import com.ais.patient.widget.FlowGroupView;
import com.ais.patient.widget.NetstedListView;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class RobotFindDoctorActivity extends MYBaseActivity implements TextWatcher {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_find_more)
    TextView tvFindMore;
    @BindView(R.id.tv_myDisease)
    TextView tvMyDisease;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.et_zhusu)
    EditText etZhusu;
    @BindView(R.id.mListView)
    NetstedListView mListView;
    @BindView(R.id.mMabDisease)
    NetstedListView mMabDisease;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_msg)
    LinearLayout llMsg;
    @BindView(R.id.ll_disease)
    LinearLayout llDisease;
    @BindView(R.id.tv_normal)
    TextView tvNormal;
    @BindView(R.id.tv_yin)
    TextView tvYin;
    private String searchWord;
    private Context context;
    private String symptom = "";
    private FlowGroupView mFlowGroupView;
    private Adapter<String> adapter;
    private List<String> dominanceDiseases;
    private List<SymptomReback.DominanceDoctorsBean> dominanceDoctors;
    private List<String> recessiveDiseases;
    private List<SymptomReback.RecessiveDoctorsBean> recessiveDoctors;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_robot_find_doctor;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        tvTitle.setText("智能导医");
        etZhusu.addTextChangedListener(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_back, R.id.tv_clear, R.id.tv_isWhat, R.id.tv_commit, R.id.tv_normal, R.id.tv_yin, R.id.tv_find_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_isWhat:
                llDisease.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_clear:
                tvClear.setVisibility(View.GONE);
                tvCommit.setVisibility(View.GONE);
                tvMyDisease.setText("我的症状：");
                symptom = "";
                etZhusu.setText("");
                adapter.clear();
                break;
            case R.id.tv_commit:
                Call<HttpBaseBean<SymptomReback>> call = RetrofitFactory.getInstance(this).getSymptomReback(symptom);
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<SymptomReback>() {

                    @Override
                    public void onSuccess(SymptomReback symptomReback, String info) {
                        dominanceDiseases = symptomReback.getDominanceDiseases();
                        dominanceDoctors = symptomReback.getDominanceDoctors();

                        recessiveDiseases = symptomReback.getRecessiveDiseases();
                        recessiveDoctors = symptomReback.getRecessiveDoctors();

                        etZhusu.setVisibility(View.GONE);
                        mListView.setVisibility(View.GONE);

                        llMsg.setVisibility(View.VISIBLE);

                        if (dominanceDiseases.size() < 4) {
                            tvFindMore.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(String info) {

                    }
                });
                break;
            case R.id.tv_normal:
                tvNormal.setBackgroundResource(R.drawable.shape_disease_bg2);
                tvNormal.setTextColor(getResources().getColor(R.color.white));

                tvYin.setBackgroundResource(R.drawable.shape_disease_bg);
                tvYin.setTextColor(getResources().getColor(R.color.blue_5));

                if (dominanceDiseases!=null) {
                    Adapter<String> adapter = new Adapter<String>(context, R.layout.diseasr_item2, dominanceDiseases) {
                        @Override
                        protected void convert(AdapterHelper helper, String item) {
                            helper.setText(R.id.tv_text, item);
                        }
                    };
                    mMabDisease.setAdapter(adapter);
                }

                if (dominanceDoctors.size() < 4) {
                    tvFindMore.setVisibility(View.GONE);
                }
                if (dominanceDoctors!=null) {
                    RecyclerAdapter<SymptomReback.DominanceDoctorsBean> adapter1 = new RecyclerAdapter<SymptomReback.DominanceDoctorsBean>(context, R.layout.main_doctor_item, dominanceDoctors) {
                        @Override
                        protected void convert(RecyclerAdapterHelper helper, final SymptomReback.DominanceDoctorsBean item) {
                            ImageView ivIcon = (ImageView) helper.getItemView().findViewById(R.id.iv_icon);
                            Picasso.with(context).load(item.getImage()).transform(new CircleTransform()).into(ivIcon);
                            helper.setText(R.id.tv_name, item.getName());
                            helper.setText(R.id.tv_title, item.getTitles());
                            helper.setText(R.id.tv_depart, item.getDepart());
                            helper.setText(R.id.tv_medicalInstitutions, item.getMedicalInstitutions());
                            helper.setText(R.id.tv_fee, item.getFee() + "元/次");
                            final List<String> diseaseExpertise = item.getDiseaseExpertise();
                            mFlowGroupView = (FlowGroupView) helper.getItemView().findViewById(R.id.mFlowGroupView);
                            if (diseaseExpertise != null && diseaseExpertise.size() > 0) {
                                for (int i = 0; i < diseaseExpertise.size(); i++) {
                                    addTextView(diseaseExpertise.get(i));
                                }
                            }
                            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String id = item.getDoctorId();
                                    Intent intent = new Intent(context, DoctorInfomationActivity.class);
                                    intent.putExtra("id", id);
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    mRecyclerView.setAdapter(adapter1);
                }

                break;
            case R.id.tv_yin:
                tvYin.setBackgroundResource(R.drawable.shape_disease_bg2);
                tvYin.setTextColor(getResources().getColor(R.color.white));

                tvNormal.setBackgroundResource(R.drawable.shape_disease_bg);
                tvNormal.setTextColor(getResources().getColor(R.color.blue_5));
                if (recessiveDiseases!=null) {
                    Adapter<String> adapter2 = new Adapter<String>(context, R.layout.diseasr_item2, recessiveDiseases) {
                        @Override
                        protected void convert(AdapterHelper helper, String item) {
                            helper.setText(R.id.tv_text, item);
                        }
                    };
                    mMabDisease.setAdapter(adapter2);
                }

                if (recessiveDoctors!=null) {
                    RecyclerAdapter<SymptomReback.RecessiveDoctorsBean> adapter3 = new RecyclerAdapter<SymptomReback.RecessiveDoctorsBean>(context, R.layout.main_doctor_item, recessiveDoctors) {
                        @Override
                        protected void convert(RecyclerAdapterHelper helper, final SymptomReback.RecessiveDoctorsBean item) {
                            ImageView ivIcon = (ImageView) helper.getItemView().findViewById(R.id.iv_icon);
                            Picasso.with(context).load(item.getImage()).transform(new CircleTransform()).into(ivIcon);
                            helper.setText(R.id.tv_name, item.getName());
                            helper.setText(R.id.tv_title, item.getTitles());
                            helper.setText(R.id.tv_depart, item.getDepart());
                            helper.setText(R.id.tv_medicalInstitutions, item.getMedicalInstitutions());
                            helper.setText(R.id.tv_fee, item.getFee() + "元/次");
                            final List<String> diseaseExpertise = item.getDiseaseExpertise();
                            mFlowGroupView = (FlowGroupView) helper.getItemView().findViewById(R.id.mFlowGroupView);
                            if (diseaseExpertise != null && diseaseExpertise.size() > 0) {
                                for (int i = 0; i < diseaseExpertise.size(); i++) {
                                    addTextView(diseaseExpertise.get(i));
                                }
                            }
                            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String id = item.getDoctorId();
                                    Intent intent = new Intent(context, DoctorInfomationActivity.class);
                                    intent.putExtra("id", id);
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    mRecyclerView.setAdapter(adapter3);
                }

                break;
            case R.id.tv_find_more:
                String disease = dominanceDiseases.get(0);
                Intent intent = new Intent(this, FindDoctorActivity.class);
                intent.putExtra("disease", disease);
                startActivity(intent);
                break;
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchWord = s.toString().trim();
        Call<HttpBaseBean<List<String>>> call = RetrofitFactory.getInstance(this).getSymptom(searchWord);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<String>>() {

            @Override
            public void onSuccess(List<String> strings, String info) {
                if (strings != null && strings.size() > 0) {
                    adapter = new Adapter<String>(context, R.layout.diseasr_item, strings) {
                        @Override
                        protected void convert(AdapterHelper helper, final String item) {
                            helper.setText(R.id.tv_name, item);
                            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tvCommit.setVisibility(View.VISIBLE);
                                    tvClear.setVisibility(View.VISIBLE);
                                    if (symptom.length() < 1) {
                                        symptom = symptom + "+" + item;
                                    } else {
                                        symptom = "+" + symptom + "+" + item;
                                    }
                                    symptom = symptom.substring(1, symptom.length());
                                    tvMyDisease.setText("我的症状：" + symptom);
                                }
                            });
                        }
                    };
                    mListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(String info) {

            }
        });
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
