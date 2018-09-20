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
import com.ais.patient.been.PatientType;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.LogUtils;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.util.UserUtils;
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

public class WriteFromFourActivity extends MYBaseActivity {

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
    @BindView(R.id.id_flowlayout6)
    TagFlowLayout tagFlowlayout6;
    @BindView(R.id.id_flowlayout7)
    TagFlowLayout tagFlowlayout7;
    @BindView(R.id.id_flowlayout8)
    TagFlowLayout tagFlowlayout8;
    @BindView(R.id.et_other)
    EditText etOther;
    @BindView(R.id.tv_text_num)
    TextView tvTextNum;

    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();
    List<String> list4 = new ArrayList<>();
    List<String> list5 = new ArrayList<>();
    List<String> list6 = new ArrayList<>();
    List<String> list7 = new ArrayList<>();
    List<String> list8 = new ArrayList<>();

    private String recordId;
    private Context context;

    List<String> feel = new ArrayList<>();
    List<String> sweat = new ArrayList<>();
    List<String> headPharynx = new ArrayList<>();
    List<String> cough = new ArrayList<>();
    List<String> chest = new ArrayList<>();
    List<String> body = new ArrayList<>();
    List<String> livingHabit = new ArrayList<>();
    String handAndFoot;
    String supplement;
    private String doctorId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_from_four;
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
        list1.add("怕冷（夏天怕空调，冬天比别人冷）");
        list1.add("怕热（冬天都怕热）");
        list1.add("怕风（风吹觉得很冷）");
        list1.add("忽冷忽热");
        list1.add("下午有潮热");
        list1.add("无以上情况");

        /**
         * 第二个
         */
        list2.add("手脚偏冷");
        list2.add("手脚偏热");
        list2.add("手脚头冷");
        list2.add("手脚心热烫");
        list2.add("无以上情况");
        /**
         * 第三个
         */
        list3.add("易出汗");
        list3.add("稍活动大汗淋漓，容易乏力感");
        list3.add("基本不出汗");
        list3.add("手足心出汗");
        list3.add("半身出汗（左右半身或上下半身）");
        list3.add("仅头部出汗");
        list3.add("睡觉时容易盗汗");
        list3.add("出汗有尿臭味");
        list3.add("出汗有腥臭味");
        list3.add("汗液呈深黄色");
        list3.add("汗液呈红色");
        list3.add("汗液呈绿色");
        list3.add("汗液呈乳白色");
        list3.add("无以上情况");

        /**
         * 第四个
         */
        list4.add("头痛");
        list4.add("头昏");
        list4.add("有眩晕感");
        list4.add("咽干");
        list4.add("咽痒");
        list4.add("咽痛");
        list4.add("咽部有异物感");
        list4.add("鼻塞");
        list4.add("流清涕，质地稀水状");
        list4.add("流黄或白涕（浊涕），质地粘稠");
        list4.add("呼吸发热发烫");
        list4.add("容易流鼻血");
        list4.add("眼睛视物模糊");
        list4.add("眼睛干涩");
        list4.add("耳聋");
        list4.add("听力下降");
        list4.add("无以上情况");

        /**
         * 第五个
         */
        list5.add("咳嗽");
        list5.add("喘");
        list5.add("无痰");
        list5.add("极少");
        list5.add("痰多");
        list5.add("黄痰");
        list5.add("白痰粘稠");
        list5.add("白痰清稀");
        list5.add("黄白痰");
        list5.add("清稀泡沫痰");
        list5.add("多涎沫");
        list5.add("痰中带血丝");
        list5.add("无以上情况");

        /**
         * 第六个
         */
        list6.add("心慌");
        list6.add("心悸动");
        list6.add("胸闷");
        list6.add("气短");
        list6.add("胸部胀满");
        list6.add("胸骨正中处按痛");
        list6.add("胃痛");
        list6.add("胃胀满");
        list6.add("嗳气");
        list6.add("泛酸");
        list6.add("腹胀腹痛");
        list6.add("有气从腹部上冲");
        list6.add("下腹部按着僵硬");
        list6.add("两肋按着胀痛");
        list6.add("下腹部隐隐作痛");
        list6.add("强项后背部拘紧");
        list6.add("无以上情况");

        /**
         * 第七个
         */
        list7.add("全身乏力懒动");
        list7.add("身体觉得重");
        list7.add("身痒");
        list7.add("四肢烦疼");
        list7.add("四肢拘紧屈伸不力");
        list7.add("睡觉时四肢不自觉抽动");
        list7.add("身体转侧不利索");
        list7.add("腰痛");
        list7.add("脚后跟痛");
        list7.add("无以上情况");

        /**
         * 第八个
         */
        list8.add("贪食辛辣");
        list8.add("喜食肥腻");
        list8.add("喜食甜食");
        list8.add("喜凉/生冷");
        list8.add("上班久坐");
        list8.add("房事过频");
        list8.add("频繁手淫");
        list8.add("长期熬夜");
        list8.add("长期嗜酒");
        list8.add("抽烟");
        list8.add("三餐不律");
        list8.add("无以上情况");

        initFlow1();
        initFlow2();
        initFlow3();
        initFlow4();
        initFlow5();
        initFlow6();
        initFlow7();
        initFlow8();
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
                feel.clear();
                for (Integer position : selectPosSet){
                    feel.add(list1.get(position));
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
                    handAndFoot=null;
                }else {
                    for (Integer position : selectPosSet){
                        handAndFoot = list2.get(position);
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
                sweat.clear();
                for (Integer position : selectPosSet){
                    sweat.add(list3.get(position));
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
                headPharynx.clear();
                for (Integer position : selectPosSet){
                    headPharynx.add(list4.get(position));
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
                cough.clear();
                for (Integer position : selectPosSet){
                    cough.add(list5.get(position));
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    TagAdapter mAdapter6;
    private void initFlow6() {
        tagFlowlayout6.setAdapter(mAdapter6=new TagAdapter<String>(list6)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout6, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout6.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                chest.clear();
                for (Integer position : selectPosSet){
                    chest.add(list6.get(position));
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    TagAdapter mAdapter7;
    private void initFlow7() {
        tagFlowlayout7.setAdapter(mAdapter7=new TagAdapter<String>(list7)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout7, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout7.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                body.clear();
                for (Integer position : selectPosSet){
                    body.add(list7.get(position));
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    TagAdapter mAdapter8;
    private void initFlow8() {
        tagFlowlayout8.setAdapter(mAdapter8=new TagAdapter<String>(list8)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout8, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout8.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                livingHabit.clear();
                for (Integer position : selectPosSet){
                    livingHabit.add(list8.get(position));
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
                Intent intent = new Intent(this, WriteFromThirdActivity.class);
                intent.putExtra("recordId",recordId);
                intent.putExtra("doctorId",doctorId);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.tv_next:
                supplement = etOther.getText().toString().trim();
                requestFour();
                break;
        }
    }

    class Body{
        String handAndFoot;
        List<String> feel;
        List<String> sweat;
        List<String> headPharynx;
        List<String> cough;
        List<String> chest;
        List<String> body;
        List<String> livingHabit;
        String supplement;

        public String getHandAndFoot() {
            return handAndFoot;
        }

        public void setHandAndFoot(String handAndFoot) {
            this.handAndFoot = handAndFoot;
        }

        public List<String> getFeel() {
            return feel;
        }

        public void setFeel(List<String> feel) {
            this.feel = feel;
        }

        public List<String> getSweat() {
            return sweat;
        }

        public void setSweat(List<String> sweat) {
            this.sweat = sweat;
        }

        public List<String> getHeadPharynx() {
            return headPharynx;
        }

        public void setHeadPharynx(List<String> headPharynx) {
            this.headPharynx = headPharynx;
        }

        public List<String> getCough() {
            return cough;
        }

        public void setCough(List<String> cough) {
            this.cough = cough;
        }

        public List<String> getChest() {
            return chest;
        }

        public void setChest(List<String> chest) {
            this.chest = chest;
        }

        public List<String> getBody() {
            return body;
        }

        public void setBody(List<String> body) {
            this.body = body;
        }

        public List<String> getLivingHabit() {
            return livingHabit;
        }

        public void setLivingHabit(List<String> livingHabit) {
            this.livingHabit = livingHabit;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }
    }
    private void requestFour() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("recordId",recordId);
        ajaxParams.put("templateType","body");
        Body body1 = new Body();
        if (!TextUtils.isEmpty(handAndFoot)){
            //ajaxParams.put("handAndFoot",handAndFoot);
            body1.setHandAndFoot(handAndFoot);
        }

        if (feel.size()>0){
            //ajaxParams.put("feel",feel);
            body1.setFeel(feel);
        }

        if (sweat.size()>0){
            //ajaxParams.put("sweat",sweat);
            body1.setSweat(sweat);
        }

        if (headPharynx.size()>0){
            //ajaxParams.put("headPharynx",headPharynx);
            body1.setHeadPharynx(headPharynx);
        }

        if (cough.size()>0){
            //ajaxParams.put("cough",cough);
            body1.setCough(cough);
        }

        if (chest.size()>0){
            //ajaxParams.put("chest",chest);
            body1.setChest(chest);
        }

        if (body.size()>0){
            //ajaxParams.put("\"body\"",body);
            body1.setBody(body);
        }

        if (livingHabit.size()>0){
            //ajaxParams.put("livingHabit",livingHabit);
            body1.setLivingHabit(livingHabit);
        }

        if (!TextUtils.isEmpty(supplement)){
            //ajaxParams.put("supplement",supplement);
            body1.setSupplement(supplement);
        }
        ajaxParams.put("body",body1);

        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).reQuestUpdateSecond(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                showToast("提交成功");

                Call<HttpBaseBean<PatientType>> patient = RetrofitFactory.getInstance(context).judgePatient(recordId);
                new BaseCallback(patient).handleResponse(new BaseCallback.ResponseListener<PatientType>() {

                    @Override
                    public void onSuccess(PatientType patientType, String info) {
                        if (patientType!=null){
                            String sex = patientType.getSex();
                            if (sex.equals("女")){
                                Intent intent = new Intent(context, WriteFifthActivity.class);
                                intent.putExtra("recordId",recordId);
                                intent.putExtra("doctorId",doctorId);
                                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(context, WriteFifthManActivity.class);
                                intent.putExtra("recordId",recordId);
                                intent.putExtra("doctorId",doctorId);
                                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String info) {
                        ToastUtils.show(context,info);
                    }
                });
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
                    ChatOnLinePaper.BodyBean body1 = chatOnLinePaper.getBody();
                    if (body1!=null){

                        if (body1.getFeel()!=null && body1.getFeel().size()>0){
                            feel = body1.getFeel();
                            for (int i = 0; i < list1.size(); i++) {
                                for (int j = 0; j < feel.size(); j++) {
                                    if (feel.get(j).equals(list1.get(i))){
                                        mAdapter1.setSelectedList(i);
                                    }
                                }
                            }
                        }


                        handAndFoot = body1.getHandAndFoot();
                        for (int i = 0; i < list2.size(); i++) {
                            if (handAndFoot.equals(list2.get(i))){
                                mAdapter2.setSelectedList(i);
                            }
                        }

                        if (body1.getSweat()!=null && body1.getSweat().size()>0){
                            sweat = body1.getSweat();
                            for (int i = 0; i < list3.size(); i++) {
                                for (int j = 0; j < sweat.size(); j++) {
                                    if (sweat.get(j).equals(list3.get(i))){
                                        mAdapter3.setSelectedList(i);
                                    }
                                }
                            }
                        }

                        headPharynx = body1.getHeadPharynx();
                        if (headPharynx!=null){
                            for (int i = 0; i < list4.size(); i++) {
                                for (int j = 0; j < headPharynx.size(); j++) {
                                    if (headPharynx.get(j).equals(list4.get(i))){
                                        mAdapter4.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            headPharynx = new ArrayList<>();
                        }

                        cough = body1.getCough();
                        if (cough!=null){
                            for (int i = 0; i < list5.size(); i++) {
                                for (int j = 0; j < cough.size(); j++) {
                                    if (cough.get(j).equals(list5.get(i))){
                                        mAdapter5.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            cough = new ArrayList<>();
                        }

                        chest = body1.getChest();
                        if (chest!=null){
                            for (int i = 0; i < list6.size(); i++) {
                                for (int j = 0; j < chest.size(); j++) {
                                    if (chest.get(j).equals(list6.get(i))){
                                        mAdapter6.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            chest = new ArrayList<>();
                        }

                        body = body1.getBody();
                        if (body!=null){
                            for (int i = 0; i < list7.size(); i++) {
                                for (int j = 0; j < body.size(); j++) {
                                    if (body.get(j).equals(list7.get(i))){
                                        mAdapter7.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            body = new ArrayList<>();
                        }

                        livingHabit = body1.getLivingHabit();
                        if (livingHabit!=null){
                            for (int i = 0; i < list8.size(); i++) {
                                for (int j = 0; j < livingHabit.size(); j++) {
                                    if (livingHabit.get(j).equals(list8.get(i))){
                                        mAdapter8.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            livingHabit = new ArrayList<>();
                        }

                        if (body1.getSupplement()!=null){
                            supplement = body1.getSupplement();
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
