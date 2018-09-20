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

public class WriteFromSecondActivity extends MYBaseActivity {

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
    private Context context;

    private String stool;
    List<String> shape = new ArrayList<>();
    private String colour;
    List<String> other = new ArrayList<>();
    List<String> urine = new ArrayList<>();
    private String supplement;
    private String recordId;
    private String doctorId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_from_second;
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
        list1.add("大便一天一次");
        list1.add("大便一天几次");
        list1.add("大便几天一次");
        list1.add("长期便秘");
        /**
         * 第二个
         */
        list2.add("成形、不干不稀（香蕉便）");
        list2.add("成形、偏干");
        list2.add("成形、偏软（细条状）");
        list2.add("干燥成球状");
        list2.add("不成形、偏烂");
        list2.add("黏马桶");
        list2.add("腹泻");
        /**
         * 第三个
         */
        list3.add("大便黄");
        list3.add("大便黑（吃了黑色食物不算）");
        list3.add("大便白");
        list3.add("大便青");
        list3.add("便血（吃了红色食物不算）");
        /**
         * 第四个
         */
        list4.add("放屁多");
        list4.add("放屁臭");
        list4.add("大便臭");
        list4.add("大便时灼热感");
        list4.add("想拉拉不出");
        list4.add("无以上情况");
        /**
         * 第五个
         */
        list5.add("小便次数偏多（多于8次）");
        list5.add("小便次数偏少（少于5次）");
        list5.add("夜尿超过一次（睡前喝多了水不算）");
        list5.add("遗尿");
        list5.add("尿不尽");
        list5.add("小便时涩痛感");
        list5.add("尿热烫");
        list5.add("尿血");
        list5.add("小便味道大");
        list5.add("小便泡沫多");
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
                    stool = null;
                }else {
                    for (Integer position : selectPosSet){
                        stool = list1.get(position);
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
                shape.clear();
                for (Integer position : selectPosSet){
                    shape.add(list2.get(position));
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
                    colour = null;
                }else {
                    for (Integer position : selectPosSet){
                        colour=list3.get(position);
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
                other.clear();
                for (Integer position : selectPosSet){
                    other.add(list4.get(position)) ;
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
                urine.clear();
                for (Integer position : selectPosSet){
                    urine.add(list5.get(position));
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
                Intent intent = new Intent(this, WriteFromFirstActivity.class);
                intent.putExtra("recordId",recordId);
                intent.putExtra("doctorId",doctorId);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.tv_next:
                supplement = etOther.getText().toString().trim();
                requestSecond();
                break;
        }
    }

    class Excrement{
        String stool;
        List<String> shape;
        String colour;
        List<String> other;
        List<String> urine;
        String supplement;

        public String getStool() {
            return stool;
        }

        public void setStool(String stool) {
            this.stool = stool;
        }

        public List<String> getShape() {
            return shape;
        }

        public void setShape(List<String> shape) {
            this.shape = shape;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public List<String> getOther() {
            return other;
        }

        public void setOther(List<String> other) {
            this.other = other;
        }

        public List<String> getUrine() {
            return urine;
        }

        public void setUrine(List<String> urine) {
            this.urine = urine;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }
    }
    private void requestSecond() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("recordId",recordId);
        ajaxParams.put("templateType","excrement");
        Excrement excrement = new Excrement();
        if (!TextUtils.isEmpty(stool)){
            //ajaxParams.put("stool",stool);
            excrement.setStool(stool);
        }
        if (shape.size()>0){
            //ajaxParams.put("shape",shape);
            excrement.setShape(shape);
        }

        if (!TextUtils.isEmpty(colour)){
            //ajaxParams.put("colour",colour);
            excrement.setColour(colour);
        }

        if (other.size()>0){
            //ajaxParams.put("other",other);
            excrement.setOther(other);
        }

        if (urine.size()>0){
            //ajaxParams.put("urine",urine);
            excrement.setUrine(urine);
        }

        if (!TextUtils.isEmpty(supplement)){
            //ajaxParams.put("supplement",supplement);
            excrement.setSupplement(supplement);
        }
        ajaxParams.put("excrement",excrement);
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).reQuestUpdateSecond(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                showToast("提交成功");
                Intent intent = new Intent(context, WriteFromThirdActivity.class);
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
                    ChatOnLinePaper.ExcrementBean excrement = chatOnLinePaper.getExcrement();
                    if (excrement!=null){
                        stool = excrement.getStool();
                        for (int i = 0; i < list1.size(); i++) {
                            if (stool.equals(list1.get(i))){
                                mAdapter1.setSelectedList(i);
                            }
                        }

                        shape = excrement.getShape();
                        if (shape!=null){
                            for (int i = 0; i < list2.size(); i++) {
                                for (int j = 0; j < shape.size(); j++) {
                                    if (shape.get(j).equals(list2.get(i))){
                                        mAdapter2.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            shape = new ArrayList<>();
                        }

                        colour = excrement.getColour();
                        for (int i = 0; i < list3.size(); i++) {
                            if (colour.equals(list3.get(i))){
                                mAdapter3.setSelectedList(i);
                            }
                        }

                        other = excrement.getOther();
                        if (other!=null){
                            for (int i = 0; i < list4.size(); i++) {
                                for (int j = 0; j < other.size(); j++) {
                                    if (other.get(j).equals(list4.get(i))){
                                        mAdapter4.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            other = new ArrayList<>();
                        }

                        urine = excrement.getUrine();
                        if (urine!=null){
                            for (int i = 0; i < list5.size(); i++) {
                                for (int j = 0; j < urine.size(); j++) {
                                    if (urine.get(j).equals(list5.get(i))){
                                        mAdapter5.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            urine = new ArrayList<>();
                        }

                        if (excrement.getSupplement()!=null){
                            supplement = excrement.getSupplement();
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
