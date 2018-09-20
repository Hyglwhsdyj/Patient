package com.ais.patient.activity.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.ChatOnLinePaper;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.LogUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class WriteChildFromFirstActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.id_flowlayout1)
    TagFlowLayout tagFlowlayout1;
    @BindView(R.id.id_flowlayout2)
    TagFlowLayout tagFlowlayout2;
    @BindView(R.id.id_flowlayout3)
    TagFlowLayout tagFlowlayout3;
    @BindView(R.id.id_flowlayout4)
    TagFlowLayout tagFlowlayout4;


    @BindView(R.id.et_other)
    EditText etOther;
    @BindView(R.id.tv_text_num)
    TextView tvTextNum;

    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();
    List<String> list4 = new ArrayList<>();
    private Context context;
    private String recordId;
    private String supplement;
    private String temperature;
    private String foot;
    private String cough;
    private List<String> sputum = new ArrayList<>();
    private String TAG = "WriteChildFromFirstActivity";
    private String doctorId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_child_from_first;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("填写问诊单");
        context = this;
        recordId = getIntent().getStringExtra("recordId");
        doctorId = getIntent().getStringExtra("doctorId");
        /**
         * 第一个
         */
        list1.add("发冷");
        list1.add("发热（39℃以下）");
        list1.add("高热（39℃以上）");
        list1.add("正常");

        /**
         * 第二个
         */
        list2.add("手脚发冷");
        list2.add("手脚发热");
        list2.add("正常");
        /**
         * 第三个
         */
        list3.add("有咳嗽");
        list3.add("无");

        /**
         * 第四个
         */
        list4.add("黄痰");
        list4.add("黄白痰");
        list4.add("白痰粘稠");
        list4.add("白痰清稀");
        list4.add("清稀泡沫痰");
        list4.add("痰中带血丝");
        list4.add("无痰");

        initFlow1();
        initFlow2();
        initFlow3();
        initFlow4();

        initData();
    }

    TagAdapter mAdapter1;
    private void initFlow1() {
        tagFlowlayout1.setAdapter(mAdapter1=new TagAdapter<String>(list1)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout1, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout1.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet.size()<1){
                    temperature=null;
                }else {
                    for (Integer position : selectPosSet){
                        temperature = list1.get(position);
                    }
                }

                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    TagAdapter mAdapter2;
    private void initFlow2() {
        tagFlowlayout2.setAdapter(mAdapter2=new TagAdapter<String>(list2)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout2, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout2.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet.size()<1){
                    foot=null;
                }else {
                    for (Integer position : selectPosSet){
                        foot = list2.get(position);
                    }
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    TagAdapter mAdapter3;
    private void initFlow3() {
        tagFlowlayout3.setAdapter(mAdapter3=new TagAdapter<String>(list3)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout3, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout3.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet.size()<1){
                    cough=null;
                }else {
                    for (Integer position : selectPosSet){
                        cough = list3.get(position);
                    }
                }

                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    TagAdapter mAdapter4;
    private void initFlow4() {
        tagFlowlayout4.setAdapter(mAdapter4=new TagAdapter<String>(list4)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout4, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout4.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                sputum.clear();
                for (Integer position : selectPosSet){
                    sputum.add(list4.get(position));
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }


    @OnClick({R.id.tv_back, R.id.tv_last, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_last:
                break;
            case R.id.tv_next:
                supplement = etOther.getText().toString().trim();
                requestFirst();
                break;
        }
    }

    class special{
        String temperature;
        String foot;
        String cough;
        String supplement;
        List<String> sputum;

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getFoot() {
            return foot;
        }

        public void setFoot(String foot) {
            this.foot = foot;
        }

        public String getCough() {
            return cough;
        }

        public void setCough(String cough) {
            this.cough = cough;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }

        public List<String> getSputum() {
            return sputum;
        }

        public void setSputum(List<String> sputum) {
            this.sputum = sputum;
        }
    }

    private void requestFirst() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("recordId",recordId);
        ajaxParams.put("templateType","special");
        special special = new special();
        if (!TextUtils.isEmpty(temperature)){
            //ajaxParams.put("temperature",temperature);
            special.setTemperature(temperature);
        }
        if (!TextUtils.isEmpty(foot)){
            //ajaxParams.put("foot",foot);
            special.setFoot(foot);
        }
        if (!TextUtils.isEmpty(cough)){
            //ajaxParams.put("cough",cough);
            special.setCough(cough);
        }
        if (sputum.size()>0){
            //ajaxParams.put("sputum",sputum);
            special.setSputum(sputum);
        }
        if (!TextUtils.isEmpty(supplement)){
           // ajaxParams.put("supplement",supplement);
            special.setSupplement(supplement);
        }
        ajaxParams.put("special",special);
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Log.e(TAG, "requestFirst: "+urlParams.toString());
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).reQuestUpdateSecond(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                showToast("提交成功");
                Intent intent = new Intent(context, WriteChildFromSecondActivity.class);
                intent.putExtra("recordId",recordId);
                intent.putExtra("doctorId",doctorId);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }

            @Override
            public void onFailure(String info) {
                showToast(info);
            }
        });
    }

    @Override
    protected void initEvent() {
        etOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = etOther.getText().length();
                tvTextNum.setText(length + "/" + 250);
                if (etOther.getText().length() > 250) {
                    String newShopTitle = etOther.getText().toString().substring(0, 250);
                    etOther.setText(newShopTitle);
                    etOther.setSelection(etOther.length());
                    showToast("字数250个以内");
                } else {

                }
            }
        });
    }

    @Override
    protected void initData() {
        String baseUrl = "/api/interrogation/" + recordId;
        Call<HttpBaseBean<ChatOnLinePaper>> call = RetrofitFactory.getInstance(context).getChatOnLinePaper(baseUrl);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ChatOnLinePaper>() {

            @Override
            public void onSuccess(ChatOnLinePaper chatOnLinePaper, String info) {
                if (chatOnLinePaper!=null){
                    ChatOnLinePaper.SpecialBean special = chatOnLinePaper.getSpecial();
                    if (special!=null){
                        temperature = special.getTemperature();
                        for (int i = 0; i < list1.size(); i++) {
                            if (temperature.equals(list1.get(i))){
                                mAdapter1.setSelectedList(i);
                            }
                        }

                        foot = special.getFoot();
                        for (int i = 0; i < list2.size(); i++) {
                            if (foot.equals(list2.get(i))){
                                mAdapter2.setSelectedList(i);
                            }
                        }

                        cough = special.getCough();
                        for (int i = 0; i < list3.size(); i++) {
                            if (cough.equals(list3.get(i))){
                                mAdapter3.setSelectedList(i);
                            }
                        }

                        sputum = special.getSputum();
                        if (sputum!=null){
                            for (int i = 0; i < list4.size(); i++) {
                                for (int j = 0; j < sputum.size(); j++) {
                                    if (sputum.get(j).equals(list4.get(i))){
                                        mAdapter4.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            sputum = new ArrayList<>();
                        }


                        if (special.getSupplement()!=null){
                            supplement = special.getSupplement();
                            etOther.setText(supplement);
                        }
                    }
                }
            }

            @Override
            public void onFailure(String info) {

            }
        });
    }
}
