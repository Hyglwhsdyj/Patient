package com.ais.patient.activity.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.ChatOnLinePaper;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.LogUtils;
import com.ais.patient.widget.FlowGroupView;
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

public class WriteFromThirdActivity extends MYBaseActivity {

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
    @BindView(R.id.id_flowlayout5)
    TagFlowLayout tagFlowlayout5;
    @BindView(R.id.et_other)
    EditText etOther;
    @BindView(R.id.tv_text_num)
    TextView tvTextNum;

    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();
    List<String> list4 = new ArrayList<>();
    List<String> list5 = new ArrayList<>();
    private String recordId;
    private Context context;

    private String fallAsleep;
    private String quality;
    private String dream;
    private List<String> wakeUp = new ArrayList<>();
    private List<String> other = new ArrayList<>();
    private String supplement;
    private String doctorId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_from_third;
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
        list1.add("很难睡着");
        list1.add("正常入睡");

        /**
         * 第二个
         */
        list2.add("睡眠深");
        list2.add("睡眠浅、易醒");
        list2.add("易失眠");
        /**
         * 第三个
         */
        list3.add("经常夜醒");
        list3.add("很少夜醒");

        /**
         * 第四个
         */
        list4.add("经常做梦，但睡醒就不记得");
        list4.add("经常做噩梦");
        list4.add("偶尔做梦");
        list4.add("无");

        /**
         * 第五个
         */
        list5.add("易怒");
        list5.add("易悲");
        list5.add("易燥");
        list5.add("易抑郁");
        list5.add("健忘");
        list5.add("易焦虑");
        list5.add("易受惊吓");
        list5.add("喜欢热闹");
        list5.add("喜欢安静");
        list5.add("爱叹气");
        list5.add("思虑较多");
        list5.add("平时压力大");
        list5.add("无以上情况");

        initFlow1();
        initFlow2();
        initFlow3();
        initFlow4();
        initFlow5();

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
                    fallAsleep=null;
                }else {
                    for (Integer position : selectPosSet){
                        fallAsleep = list1.get(position);
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
                    quality=null;
                }else {
                    for (Integer position : selectPosSet){
                        quality = list2.get(position);
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
                wakeUp.clear();
                for (Integer position : selectPosSet){
                    wakeUp.add(list3.get(position));
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
                if (selectPosSet.size()<1){
                    dream = null;
                }else {
                    for (Integer position : selectPosSet){
                        dream=list4.get(position);
                    }
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    TagAdapter mAdapter5;
    private void initFlow5() {
        tagFlowlayout5.setAdapter(mAdapter5=new TagAdapter<String>(list5)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout5, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout5.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                other.clear();
                for (Integer position : selectPosSet){
                    other.add(list5.get(position));
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
                Intent intent = new Intent(this, WriteFromSecondActivity.class);
                intent.putExtra("recordId",recordId);
                intent.putExtra("doctorId",doctorId);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.tv_next:
                supplement = etOther.getText().toString().trim();
                requestThird();
                break;
        }
    }

    class SleepMood{
        String fallAsleep;
        String quality;
        List<String> wakeUp;
        String dream;
        List<String> other;
        String supplement;

        public String getFallAsleep() {
            return fallAsleep;
        }

        public void setFallAsleep(String fallAsleep) {
            this.fallAsleep = fallAsleep;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public List<String> getWakeUp() {
            return wakeUp;
        }

        public void setWakeUp(List<String> wakeUp) {
            this.wakeUp = wakeUp;
        }

        public String getDream() {
            return dream;
        }

        public void setDream(String dream) {
            this.dream = dream;
        }

        public List<String> getOther() {
            return other;
        }

        public void setOther(List<String> other) {
            this.other = other;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }
    }
    private void requestThird() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("recordId",recordId);
        ajaxParams.put("templateType","sleepMood");

        SleepMood sleepMood = new SleepMood();

        if (!TextUtils.isEmpty(fallAsleep)){
            //ajaxParams.put("fallAsleep",fallAsleep);
            sleepMood.setFallAsleep(fallAsleep);
        }

        if (!TextUtils.isEmpty(quality)){
            //ajaxParams.put("quality",quality);
            sleepMood.setQuality(quality);
        }

        if (wakeUp.size()>0){
            //ajaxParams.put("wakeUp",wakeUp);
            sleepMood.setWakeUp(wakeUp);
        }

        if (!TextUtils.isEmpty(dream)){
            //ajaxParams.put("dream",dream);
            sleepMood.setDream(dream);
        }

        if (other.size()>0){
            //ajaxParams.put("other",other);
            sleepMood.setOther(other);
        }

        if (!TextUtils.isEmpty(supplement)){
            //ajaxParams.put("supplement",supplement);
            sleepMood.setSupplement(supplement);
        }
        ajaxParams.put("sleepMood",sleepMood);
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).reQuestUpdateSecond(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                showToast("提交成功");
                Intent intent = new Intent(context, WriteFromFourActivity.class);
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
                    ChatOnLinePaper.SleepMoodBean sleepMood = chatOnLinePaper.getSleepMood();

                    if (sleepMood!=null){
                        fallAsleep = sleepMood.getFallAsleep();
                        for (int i = 0; i < list1.size(); i++) {
                            if (fallAsleep.equals(list1.get(i))){
                                mAdapter1.setSelectedList(i);
                            }
                        }

                        quality = sleepMood.getQuality();
                        for (int i = 0; i < list2.size(); i++) {
                            if (quality.equals(list2.get(i))){
                                mAdapter2.setSelectedList(i);
                            }
                        }

                        wakeUp = sleepMood.getWakeUp();
                        if (wakeUp!=null){
                            for (int i = 0; i < list3.size(); i++) {
                                for (int j = 0; j < wakeUp.size(); j++) {
                                    if (wakeUp.get(j).equals(list3.get(i))){
                                        mAdapter3.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            wakeUp = new ArrayList<>();
                        }

                        dream = sleepMood.getDream();
                        for (int i = 0; i < list4.size(); i++) {
                            if (dream.equals(list4.get(i))){
                                mAdapter4.setSelectedList(i);
                            }
                        }

                        other = sleepMood.getOther();
                        if (other!=null){
                            for (int i = 0; i < list5.size(); i++) {
                                for (int j = 0; j < other.size(); j++) {
                                    if (other.get(j).equals(list5.get(i))){
                                        mAdapter5.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            other = new ArrayList<>();
                        }

                        if (sleepMood.getSupplement()!=null){
                            supplement = sleepMood.getSupplement();
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
