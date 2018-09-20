package com.ais.patient.activity.main;

import android.content.Context;
import android.content.Intent;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class WriteFromFirstActivity extends MYBaseActivity {

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

    private String water;
    private String waterDegree;
    private List<String> texture = new ArrayList<>();
    private List<String> other = new ArrayList<>();
    private String appetite;
    private String recordId;
    private String supplement;
    private String doctorId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_from_first;
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
        list1.add("喝水多");
        list1.add("喝水少");
        list1.add("口渴想喝大量的水");
        list1.add("口渴很不喜欢喝水");
        list1.add("正常");
        /**
         * 第二个
         */
        list2.add("喜欢喝冷水");
        list2.add("喜欢喝温热水");
        list2.add("无偏好");
        /**
         * 第三个
         */
        list3.add("口酸");
        list3.add("口苦");
        list3.add("口甜");
        list3.add("口中辛辣");
        list3.add("口咸");
        list3.add("口淡");
        list3.add("口热");
        list3.add("口麻");
        list3.add("口粘");
        list3.add("晨起口苦");
        list3.add("嘴唇干燥");
        list3.add("无以上情况");
        /**
         * 第四个
         */
        list4.add("胃口好");
        list4.add("胃口一般");
        list4.add("胃口较差");
        list4.add("吃得多单容易饿");
        list4.add("想吃但吃不下");
        /**
         * 第五个
         */
        list5.add("嗳气");
        list5.add("呃逆（打嗝）");
        list5.add("反酸");
        list5.add("胃胀气");
        list5.add("口气");
        list5.add("恶心");
        list5.add("胃有烧心感");
        list5.add("食道有灼烧感");
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
                    water=null;
                }else {
                    for (Integer position : selectPosSet){
                        water = list1.get(position);
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
                    waterDegree = null;
                }else {
                    for (Integer position : selectPosSet){
                        waterDegree = list2.get(position);
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
                texture.clear();
                for (Integer position : selectPosSet){
                    texture.add(list3.get(position));
                }
                LogUtils.e("choose2:" + texture.toString());
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
                    appetite = null;
                }else {
                    for (Integer position : selectPosSet){
                        appetite = list4.get(position);
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

    class Diet{
        String water;
        String waterDegree;
        List<String> texture;
        String appetite;
        List<String>other;
        String supplement;

        public String getWater() {
            return water;
        }

        public void setWater(String water) {
            this.water = water;
        }

        public String getWaterDegree() {
            return waterDegree;
        }

        public void setWaterDegree(String waterDegree) {
            this.waterDegree = waterDegree;
        }

        public List<String> getTexture() {
            return texture;
        }

        public void setTexture(List<String> texture) {
            this.texture = texture;
        }

        public String getAppetite() {
            return appetite;
        }

        public void setAppetite(String appetite) {
            this.appetite = appetite;
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
    private void requestFirst() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("recordId",recordId);
        ajaxParams.put("templateType","diet");
        Diet diet = new Diet();
        if (!TextUtils.isEmpty(water)){
            //ajaxParams.put("water",water);
            diet.setWater(water);
        }
        if (!TextUtils.isEmpty(waterDegree)){
            //ajaxParams.put("waterDegree",waterDegree);
            diet.setWaterDegree(waterDegree);
        }
        if (texture.size()>0){
            //ajaxParams.put("texture",texture);
            diet.setTexture(texture);
        }

        if (!TextUtils.isEmpty(appetite)){
            //ajaxParams.put("appetite",appetite);
            diet.setAppetite(appetite);
        }

        if (other.size()>0){
            //ajaxParams.put("other",other);
            diet.setOther(other);
        }

        if (!TextUtils.isEmpty(supplement)){
            //ajaxParams.put("supplement",supplement);
            diet.setSupplement(supplement);
        }
        ajaxParams.put("diet",diet);
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).reQuestUpdateSecond(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                showToast("提交成功");
                Intent intent = new Intent(context, WriteFromSecondActivity.class);
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
    protected void initData() {
        String baseUrl = "/api/interrogation/" + recordId;
        Call<HttpBaseBean<ChatOnLinePaper>> call = RetrofitFactory.getInstance(context).getChatOnLinePaper(baseUrl);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ChatOnLinePaper>() {

            @Override
            public void onSuccess(ChatOnLinePaper chatOnLinePaper, String info) {
                if (chatOnLinePaper!=null){
                    ChatOnLinePaper.DietBean diet = chatOnLinePaper.getDiet();
                    if (diet!=null){
                        water = diet.getWater();
                        for (int i = 0; i < list1.size(); i++) {
                            if (water.equals(list1.get(i))){
                                mAdapter1.setSelectedList(i);
                            }
                        }

                        waterDegree = diet.getWaterDegree();
                        for (int i = 0; i < list2.size(); i++) {
                            if (waterDegree.equals(list2.get(i))){
                                mAdapter2.setSelectedList(i);
                            }
                        }

                        texture = diet.getTexture();
                        if (texture!=null){
                            for (int i = 0; i < list3.size(); i++) {
                                for (int j = 0; j < texture.size(); j++) {
                                    if (texture.get(j).equals(list3.get(i))){
                                        mAdapter3.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            texture = new ArrayList<>();
                        }

                        appetite = diet.getAppetite();
                        for (int i = 0; i < list4.size(); i++) {
                            if (appetite.equals(list4.get(i))){
                                mAdapter4.setSelectedList(i);
                            }
                        }

                        other = diet.getOther();
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

                        if (diet.getSupplement()!=null){
                            supplement = diet.getSupplement();
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
