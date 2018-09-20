package com.ais.patient.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.ChatOnLinePaper;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.InterrogationDetailsBean;
import com.ais.patient.been.InterrogationItemBean;
import com.ais.patient.been.ItemChildBean;
import com.ais.patient.been.Patient;
import com.ais.patient.been.PatientInfo;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ConvertUtil;
import com.ais.patient.widget.NetstedGridView;
import com.ais.patient.widget.NetstedListView;
import com.bumptech.glide.Glide;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ChatOnLinePaperDetailActivity extends MYBaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_patient_name)
    TextView tvPatientName;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_bl)
    TextView tvBl;
    @BindView(R.id.tv_other)
    TextView tvOther;
    @BindView(R.id.tv_appeal)
    TextView tvAppeal;
    @BindView(R.id.tv_symptom)
    TextView tvSymptom;
    @BindView(R.id.mGridViewS)
    NetstedGridView mGridViewS;
    @BindView(R.id.mGridViewOther)
    NetstedGridView mGridViewOther;
    @BindView(R.id.mListViewMian)
    NetstedListView mListView;
    private Context context;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_on_line_paper_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("问诊单详情");
        context = this;
        String recordId = getIntent().getStringExtra("recordId");
        if (!TextUtils.isEmpty(recordId)) {
            showProgressDialog();
            String baseUrl = "/api/interrogation/" + recordId;
            Call<HttpBaseBean<ChatOnLinePaper>> call = RetrofitFactory.getInstance(context).getChatOnLinePaper(baseUrl);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ChatOnLinePaper>() {

                @Override
                public void onSuccess(ChatOnLinePaper chatOnLinePaper, final String info) {
                    if (chatOnLinePaper!=null){
                        final String patientId = chatOnLinePaper.getPatientId();

                        if (!TextUtils.isEmpty(patientId)){
                            String baseUrl = "/api/patient/"+patientId;
                            Call<HttpBaseBean<PatientInfo>> patientInfo = RetrofitFactory.getInstance(context).getPatientInfo(baseUrl);
                            new BaseCallback(patientInfo).handleResponse(new BaseCallback.ResponseListener<PatientInfo>() {

                                @Override
                                public void onSuccess(final PatientInfo patientInfo, String info) {
                                    if (patientInfo!=null){
                                        tvPatientName.setText("患者："+patientInfo.getName()+" "+patientInfo.getSex()+" "+patientInfo.getAge());


                                        tvHeight.setText(patientInfo.getHeight()+"cm");
                                        tvWeight.setText(patientInfo.getWeight()+"Kg");
                                        tvPhone.setText(patientInfo.getPhoneNumber());
                                        tvAddress.setText(patientInfo.getAddress());
                                        String medicalHistory = patientInfo.getMedicalHistory();
                                        if (TextUtils.isEmpty(medicalHistory)){
                                            tvBl.setText("无");
                                        }else {
                                            tvBl.setText(medicalHistory);
                                        }
                                        String remarks = patientInfo.getRemarks();
                                        if (TextUtils.isEmpty(remarks)){
                                            tvOther.setText("无");
                                        }else {
                                            tvBl.setText(remarks);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(String info) {
                                    showToast(info);
                                }
                            });
                        }
                        String appeal = chatOnLinePaper.getAppeal();
                        if (!TextUtils.isEmpty(appeal)){
                            tvAppeal.setText(appeal);
                        }
                        String symptom = chatOnLinePaper.getSymptom();
                        if (!TextUtils.isEmpty(symptom)){
                            tvSymptom.setText(symptom);
                        }
                        //舌苔照
                        List<String> tongueImages = chatOnLinePaper.getTongueImages();
                        Adapter<String> adapter = new Adapter<String>(context,R.layout.image_item,tongueImages) {
                            @Override
                            protected void convert(AdapterHelper helper, final String item) {
                                ImageView imageView = (ImageView) helper.getItemView().findViewById(R.id.iv_icon);
                                Glide.with(context).load(item).into(imageView);
                                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context,BigImageViewActivity.class);
                                        intent.putExtra("imgUrl",item);
                                        startActivity(intent);
                                    }
                                });
                            }
                        };
                        mGridViewS.setAdapter(adapter);
                        //其他照片
                        List<String> otherImages = chatOnLinePaper.getOtherImages();
                        Adapter<String> adapterOther = new Adapter<String>(context,R.layout.image_item,otherImages) {
                            @Override
                            protected void convert(AdapterHelper helper, final String item) {
                                ImageView imageView = (ImageView) helper.getItemView().findViewById(R.id.iv_icon);
                                Glide.with(context).load(item).into(imageView);
                                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context,BigImageViewActivity.class);
                                        intent.putExtra("imgUrl",item);
                                        startActivity(intent);
                                    }
                                });
                            }
                        };
                        mGridViewOther.setAdapter(adapterOther);

                        final List<InterrogationItemBean> dataList = new ArrayList<>();

                        ChatOnLinePaper.BasicSituationBean basicSituation = chatOnLinePaper.getBasicSituation();
                        if (basicSituation != null) {
                            InterrogationItemBean itemBean = new InterrogationItemBean();
                            itemBean.setLabelName("基本情况");
                            List<ItemChildBean> childBeanList = new ArrayList<>();
                            childBeanList.add(new ItemChildBean("饮食", basicSituation.getDiet()));
                            childBeanList.add(new ItemChildBean("饮水", basicSituation.getDrink()));
                            childBeanList.add(new ItemChildBean("精神", basicSituation.getSpirit()));
                            childBeanList.add(new ItemChildBean("睡眠", basicSituation.getSleep()));
                            childBeanList.add(new ItemChildBean("出汗", basicSituation.getSweat()));
                            childBeanList.add(new ItemChildBean("补充说明", basicSituation.getSupplement()));
                            itemBean.setItemChildBeans(childBeanList);

                            dataList.add(itemBean);
                        }



                        ChatOnLinePaper.DietBean diet = chatOnLinePaper.getDiet();
                        if (diet != null) {
                            InterrogationItemBean itemBean = new InterrogationItemBean();
                            itemBean.setLabelName("饮食情况");
                            List<ItemChildBean> childBeanList = new ArrayList<>();
                            childBeanList.add(new ItemChildBean("喝水", diet.getWater()));
                            childBeanList.add(new ItemChildBean("喜欢喝", diet.getWaterDegree()));
                            childBeanList.add(new ItemChildBean("口中感觉", diet.getTexture()));
                            childBeanList.add(new ItemChildBean("胃口", diet.getAppetite()));
                            childBeanList.add(new ItemChildBean("其他情况", diet.getOther()));
                            childBeanList.add(new ItemChildBean("补充说明", diet.getSupplement()));
                            itemBean.setItemChildBeans(childBeanList);

                            dataList.add(itemBean);
                        }

                        ChatOnLinePaper.SleepMoodBean sleepMood = chatOnLinePaper.getSleepMood();
                        if (sleepMood != null) {
                            InterrogationItemBean itemBean = new InterrogationItemBean();
                            itemBean.setLabelName("睡眠与情绪");
                            List<ItemChildBean> childBeanList = new ArrayList<>();
                            childBeanList.add(new ItemChildBean("入睡情况", sleepMood.getFallAsleep()));
                            childBeanList.add(new ItemChildBean("睡眠质量", sleepMood.getQuality()));
                            childBeanList.add(new ItemChildBean("夜醒情况", sleepMood.getWakeUp()));
                            childBeanList.add(new ItemChildBean("做梦情况", sleepMood.getDream()));
                            childBeanList.add(new ItemChildBean("其他情况", sleepMood.getOther()));
                            childBeanList.add(new ItemChildBean("补充说明", sleepMood.getSupplement()));
                            itemBean.setItemChildBeans(childBeanList);
                            dataList.add(itemBean);
                        }

                        ChatOnLinePaper.BodyBean body = chatOnLinePaper.getBody();
                        if (body != null) {
                            InterrogationItemBean itemBean = new InterrogationItemBean();
                            itemBean.setLabelName("身体感觉");
                            List<ItemChildBean> childBeanList = new ArrayList<>();
                            childBeanList.add(new ItemChildBean("身体感觉", body.getFeel()));
                            childBeanList.add(new ItemChildBean("手脚", body.getHandAndFoot()));
                            childBeanList.add(new ItemChildBean("出汗", body.getSweat()));
                            childBeanList.add(new ItemChildBean("头咽部", body.getHeadPharynx()));
                            childBeanList.add(new ItemChildBean("咳嗽和痰", body.getCough()));
                            childBeanList.add(new ItemChildBean("胸腹部", body.getChest()));
                            childBeanList.add(new ItemChildBean("身体", body.getBody()));
                            childBeanList.add(new ItemChildBean("生活习惯", body.getLivingHabit()));
                            childBeanList.add(new ItemChildBean("补充说明", body.getSupplement()));
                            itemBean.setItemChildBeans(childBeanList);
                            dataList.add(itemBean);
                        }

                        ChatOnLinePaper.ExcrementBean excrement = chatOnLinePaper.getExcrement();
                        if (excrement != null) {
                            InterrogationItemBean itemBean = new InterrogationItemBean();
                            itemBean.setLabelName("二便");
                            List<ItemChildBean> childBeanList = new ArrayList<>();
                            childBeanList.add(new ItemChildBean("大便", excrement.getStool()));
                            childBeanList.add(new ItemChildBean("小便", excrement.getUrine()));
                            childBeanList.add(new ItemChildBean("形状质地", excrement.getShape()));
                            childBeanList.add(new ItemChildBean("颜色", excrement.getColour()));
                            childBeanList.add(new ItemChildBean("其他情况", excrement.getOther()));
                            childBeanList.add(new ItemChildBean("补充说明", excrement.getSupplement()));
                            itemBean.setItemChildBeans(childBeanList);
                            dataList.add(itemBean);
                        }

                        ChatOnLinePaper.ChildExcrementBean childExcrement = chatOnLinePaper.getChildExcrement();
                        if (childExcrement != null) {
                            InterrogationItemBean itemBean = new InterrogationItemBean();
                            itemBean.setLabelName("二便情况");
                            List<ItemChildBean> childBeanList = new ArrayList<>();
                            childBeanList.add(new ItemChildBean("大便次数", childExcrement.getStoolTime()));
                            childBeanList.add(new ItemChildBean("形状质地", childExcrement.getShape()));
                            childBeanList.add(new ItemChildBean("大便颜色", childExcrement.getColour()));
                            childBeanList.add(new ItemChildBean("小便次数", childExcrement.getUrineTime()));
                            childBeanList.add(new ItemChildBean("小便颜色", childExcrement.getUrineColour()));
                            childBeanList.add(new ItemChildBean("补充说明", childExcrement.getSupplement()));
                            itemBean.setItemChildBeans(childBeanList);
                            dataList.add(itemBean);
                        }

                        ChatOnLinePaper.AndrologyBean andrology = chatOnLinePaper.getAndrology();
                        if (andrology != null) {
                            InterrogationItemBean itemBean = new InterrogationItemBean();
                            itemBean.setLabelName("男科情况");
                            List<ItemChildBean> childBeanList = new ArrayList<>();
                            childBeanList.add(new ItemChildBean("性功能问题", andrology.getSexualFunction()));
                            childBeanList.add(new ItemChildBean("男科问题", andrology.getAndrology()));
                            childBeanList.add(new ItemChildBean("补充说明", andrology.getSupplement()));
                            itemBean.setItemChildBeans(childBeanList);
                            dataList.add(itemBean);
                        }

                        ChatOnLinePaper.GynaecologyBean gynaecology = chatOnLinePaper.getGynaecology();
                        if (gynaecology != null) {
                            InterrogationItemBean itemBean = new InterrogationItemBean();
                            itemBean.setLabelName("妇科");
                            List<ItemChildBean> childBeanList = new ArrayList<>();
                            childBeanList.add(new ItemChildBean("备孕情况", gynaecology.getSpecialStage()));
                            childBeanList.add(new ItemChildBean("生育情况", gynaecology.getFertility()));
                            childBeanList.add(new ItemChildBean("流产情况", gynaecology.getAbortion()));
                            childBeanList.add(new ItemChildBean("是否绝经", gynaecology.getMenopause()));
                            childBeanList.add(new ItemChildBean("月经情况", gynaecology.getCycle()));
                            childBeanList.add(new ItemChildBean("行经期", gynaecology.getPeriod()));
                            childBeanList.add(new ItemChildBean("月经颜色", gynaecology.getColor()));
                            childBeanList.add(new ItemChildBean("月经量", gynaecology.getQuantity()));
                            childBeanList.add(new ItemChildBean("血块", gynaecology.getClot()));
                            childBeanList.add(new ItemChildBean("痛经", gynaecology.getDysmenorrhoea()));
                            childBeanList.add(new ItemChildBean("经前感觉", gynaecology.getFeel()));
                            childBeanList.add(new ItemChildBean("白带量", gynaecology.getLeucorrheaCapacity()));
                            childBeanList.add(new ItemChildBean("白带颜色", gynaecology.getLeucorrhea()));
                            childBeanList.add(new ItemChildBean("补充说明", gynaecology.getSupplement()));
                            itemBean.setItemChildBeans(childBeanList);
                            dataList.add(itemBean);
                        }

                        ChatOnLinePaper.SpecialBean special = chatOnLinePaper.getSpecial();
                        if (special != null) {
                            InterrogationItemBean itemBean = new InterrogationItemBean();
                            itemBean.setLabelName("特殊情况");
                            List<ItemChildBean> childBeanList = new ArrayList<>();
                            childBeanList.add(new ItemChildBean("体温", special.getTemperature()));
                            childBeanList.add(new ItemChildBean("手脚", special.getFoot()));
                            childBeanList.add(new ItemChildBean("咳嗽", special.getCough()));
                            childBeanList.add(new ItemChildBean("痰", special.getSputum()));
                            childBeanList.add(new ItemChildBean("补充说明", special.getSupplement()));
                            itemBean.setItemChildBeans(childBeanList);
                            dataList.add(itemBean);
                        }
                        Adapter<InterrogationItemBean> adapter1 = new Adapter<InterrogationItemBean>(context,R.layout.item_interrogation_item,dataList) {
                            @Override
                            protected void convert(AdapterHelper holder, InterrogationItemBean item) {
                                holder.setText(R.id.tv_labName,item.getLabelName());
                                NetstedListView mNetstedListView = (NetstedListView)holder.getItemView().findViewById(R.id.mNetstedListView);
                                List<ItemChildBean> itemChildBeans = item.getItemChildBeans();
                                Adapter<ItemChildBean> adapter2 = new Adapter<ItemChildBean>(context,R.layout.item_interrogation_flowlayout_layout,itemChildBeans) {

                                    @Override
                                    protected void convert(AdapterHelper helper, ItemChildBean item) {
                                        helper.setText(R.id.tv_label_title,item.getTitle());
                                        final TagFlowLayout flowLayout = (TagFlowLayout)helper.getView(R.id.flowLayout);
                                        final List<String> flowList = item.getFlowList();
                                        if (flowList.size()>0){
                                            TagAdapter<String> adapter = new TagAdapter<String>(flowList) {
                                                @Override
                                                public View getView(FlowLayout parent, int position, String o) {
                                                    TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_flow_interrogation_layout, flowLayout, false);
                                                    tv.setText(flowList.get(position));
                                                    return tv;
                                                }
                                            };
                                            flowLayout.setAdapter(adapter);
                                        }
                                    }
                                };
                                mNetstedListView.setAdapter(adapter2);
                            }
                        };

                        mListView.setAdapter(adapter1);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isShowingProgressDialog()){
                                dismissProgressDialog();
                            }
                        }
                    },1000);
                }

                @Override
                public void onFailure(String info) {
                    showToast(info);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isShowingProgressDialog()){
                                dismissProgressDialog();
                            }
                        }
                    },1000);
                }
            });
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
