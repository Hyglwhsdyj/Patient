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

public class WriteChildFromSecondActivity extends MYBaseActivity {

    private static final String TAG = "WriteChildFromSecondActivity";
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

    private Context context;
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();
    List<String> list4 = new ArrayList<>();
    List<String> list5 = new ArrayList<>();
    private String recordId;
    private String diet;
    private String drink;
    private String spirit;
    private String sleep;
    private String sweat;
    private String supplement;
    private String doctorId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_child_from_second;
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
        list1.add("进食较少");
        list1.add("进食较多");
        list1.add("正常");

        /**
         * 第二个
         */
        list2.add("喝水较少");
        list2.add("喝水较多");
        list2.add("正常");
        /**
         * 第三个
         */
        list3.add("精神好");
        list3.add("精神一般");
        list3.add("精神差");

        /**
         * 第四个
         */
        list4.add("睡眠好");
        list4.add("睡眠一般");
        list4.add("睡眠差");

        list5.add("出汗较少");
        list5.add("出汗较多");
        list5.add("正常");

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
                    diet=null;
                }else {
                    for (Integer position : selectPosSet){
                        diet = list1.get(position);
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
                    drink = null;
                }else {
                    for (Integer position : selectPosSet){
                        drink = list2.get(position);
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
                    spirit = null;
                }else {
                    for (Integer position : selectPosSet){
                        spirit = list3.get(position);
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
                if (selectPosSet.size()<1){
                    sleep=null;
                }else {
                    for (Integer position : selectPosSet){
                        sleep = list4.get(position);
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
                if (selectPosSet.size()<1){
                    sweat=null;
                }else {
                    for (Integer position : selectPosSet){
                        sweat = list5.get(position);
                    }
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
                Intent intent = new Intent(this, WriteChildFromFirstActivity.class);
                intent.putExtra("recordId",recordId);
                intent.putExtra("doctorId",doctorId);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.tv_next:
                supplement = etOther.getText().toString().trim();
                requestFirst();
                break;
        }
    }

    class BasicSituation {
        String diet;
        String drink;
        String spirit;
        String sleep;
        String sweat;
        String supplement;

        public String getDiet() {
            return diet;
        }

        public void setDiet(String diet) {
            this.diet = diet;
        }

        public String getDrink() {
            return drink;
        }

        public void setDrink(String drink) {
            this.drink = drink;
        }

        public String getSpirit() {
            return spirit;
        }

        public void setSpirit(String spirit) {
            this.spirit = spirit;
        }

        public String getSleep() {
            return sleep;
        }

        public void setSleep(String sleep) {
            this.sleep = sleep;
        }

        public String getSweat() {
            return sweat;
        }

        public void setSweat(String sweat) {
            this.sweat = sweat;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }
    }
    private void requestFirst() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("recordId",recordId);
        ajaxParams.put("templateType","basicSituation");
        BasicSituation basicSituation = new BasicSituation();

        if (!TextUtils.isEmpty(diet)){
            //ajaxParams.put("\"diet\"",diet);
            basicSituation.setDiet(diet);
        }
        if (!TextUtils.isEmpty(drink)){
            //ajaxParams.put("drink",drink);
            basicSituation.setDrink(drink);
        }
        if (!TextUtils.isEmpty(spirit)){
            //ajaxParams.put("spirit",spirit);
            basicSituation.setSpirit(spirit);
        }
        if (!TextUtils.isEmpty(sleep)){
            //ajaxParams.put("sleep",sleep);
            basicSituation.setSleep(sleep);
        }
        if (!TextUtils.isEmpty(sweat)){
            //ajaxParams.put("sweat",sweat);
            basicSituation.setSweat(sweat);
        }
        if (!TextUtils.isEmpty(supplement)){
            //ajaxParams.put("supplement",supplement);
            basicSituation.setSupplement(supplement);
        }
        ajaxParams.put("basicSituation",basicSituation);
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Log.e("requestFirst", "requestFirst: "+urlParams.toString());
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).reQuestUpdateSecond(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                showToast("提交成功");
                Intent intent = new Intent(context, WriteChildFromThirdActivity.class);
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
        final String baseUrl = "/api/interrogation/" + recordId;
        Call<HttpBaseBean<ChatOnLinePaper>> call = RetrofitFactory.getInstance(context).getChatOnLinePaper(baseUrl);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ChatOnLinePaper>() {

            @Override
            public void onSuccess(ChatOnLinePaper chatOnLinePaper, String info) {
                if (chatOnLinePaper!=null){
                    ChatOnLinePaper.BasicSituationBean basicSituation = chatOnLinePaper.getBasicSituation();
                    if (basicSituation!=null){
                        diet = basicSituation.getDiet();
                        for (int i = 0; i < list1.size(); i++) {
                            if (diet.equals(list1.get(i))){
                                mAdapter1.setSelectedList(i);
                            }
                        }
                        drink = basicSituation.getDrink();
                        for (int i = 0; i < list2.size(); i++) {
                            if (drink.equals(list2.get(i))){
                                mAdapter2.setSelectedList(i);
                            }
                        }

                        spirit = basicSituation.getSpirit();
                        for (int i = 0; i < list3.size(); i++) {
                            if (spirit.equals(list3.get(i))){
                                mAdapter3.setSelectedList(i);
                            }
                        }

                        sleep = basicSituation.getSleep();
                        for (int i = 0; i < list4.size(); i++) {
                            if (sleep.equals(list4.get(i))){
                                mAdapter4.setSelectedList(i);
                            }
                        }

                        sweat = basicSituation.getSweat();
                        for (int i = 0; i < list5.size(); i++) {
                            if (sweat.equals(list5.get(i))){
                                mAdapter5.setSelectedList(i);
                            }
                        }

                        if (basicSituation.getSupplement()!=null){
                            supplement = basicSituation.getSupplement();
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
