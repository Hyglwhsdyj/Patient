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
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.ImInfo;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.LogUtils;
import com.ais.patient.util.UserUtils;
import com.ais.patient.widget.FlowGroupView;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
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

public class WriteFifthActivity extends MYBaseActivity {
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
    @BindView(R.id.id_flowlayout9)
    TagFlowLayout tagFlowlayout9;
    @BindView(R.id.id_flowlayout10)
    TagFlowLayout tagFlowlayout10;
    @BindView(R.id.id_flowlayout11)
    TagFlowLayout tagFlowlayout11;
    @BindView(R.id.id_flowlayout12)
    TagFlowLayout tagFlowlayout12;
    @BindView(R.id.id_flowlayout13)
    TagFlowLayout tagFlowlayout13;

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
    List<String> list9 = new ArrayList<>();
    List<String> list10 = new ArrayList<>();
    List<String> list11 = new ArrayList<>();
    List<String> list12 = new ArrayList<>();
    List<String> list13 = new ArrayList<>();
    private Context context;
    private String recordId;
    private String specialStage;
    private String fertility;
    private String abortion;
    private String menopause;
    private String cycle;
    private String period;
    private String color;
    private String quantity;
    private String clot;
    List<String> dysmenorrhoea = new ArrayList<>();
    private String feel;
    private String leucorrheaCapacity;
    private String leucorrhea;

    private String supplement;
    private String doctorId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_fifth;
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
        list1.add("备孕中");
        list1.add("孕期");
        list1.add("哺乳期");
        list1.add("更年期");
        list1.add("无");


        /**
         * 第二个
         */
        list2.add("无");
        list2.add("生育1次");
        list2.add("生育2次及以上");

        /**
         * 第三个
         */
        list3.add("无");
        list3.add("流产1次");
        list3.add("流产2次及以上");

        /**
         * 第四个
         */
        list4.add("未绝经");
        list4.add("已绝经");

        /**
         * 第五个
         */
        list5.add("经期很准（28-30天来一次）");
        list5.add("经期经常提前（21-28天来一次）");
        list5.add("经期经常提前（不到21天来一次）");
        list5.add("经期推后（30-35天来一次）");
        list5.add("经期推后（超过35天来一次）");
        list5.add("经期大幅度推后（超过3个月以上没来）");
        list5.add("经期时而提前，时而推后（推后差距在7天之内）");

        /**
         * 第六个
         */
        list6.add("行经期3天以下");
        list6.add("行经期4-5天");
        list6.add("行经期6-7天");
        list6.add("行经期8-10天");
        list6.add("行经期10天以上");

        /**
         * 第七个
         */
        list7.add("月经暗黑");
        list7.add("月经褐色");
        list7.add("月经鲜红");
        list7.add("月经暗红");
        list7.add("月经淡红,质地稀");
        list7.add("月经淡红,质地粘绸");

        /**
         * 第八个
         */
        list8.add("量少（整个经期不足10片卫生巾）");
        list8.add("中等（整个经期10-20片卫生巾）");
        list8.add("量多（整个经期超过20片卫生巾）");
        list8.add("持续出血，淋漓不断");

        /**
         * 第九个
         */
        list9.add("有血块（少量）");
        list9.add("有血块（多）");
        list9.add("无血块");

        /**
         * 第十个
         */
        list10.add("无经痛");
        list10.add("月经前有痛");
        list10.add("月经后期或月经后又小腹隐痛空痛");
        list10.add("小腹冷痛（用热水袋可稍微缓解）");
        list10.add("小腹刺痛（疼痛剧烈，像针扎）");
        list10.add("小腹隐痛（疼痛不太厉害，按住疼痛减轻）");
        list10.add("小腹灼热疼痛");
        list10.add("小腹有坠胀感");
        list10.add("腰痛");

        /**
         * 第十一个
         */
        list11.add("经前乳涨痛");
        list11.add("经前腰酸");
        list11.add("经前小腹坠涨");
        list11.add("经前头痛");
        list11.add("无以上情况");

        /**
         * 第十二个
         */
        list12.add("白带多");
        list12.add("白带少");

        /**
         * 第十三个
         */
        list13.add("无色或白色");
        list13.add("黄色");
        list13.add("血性");
        list13.add("脓性");
        list13.add("豆腐渣样");
        list13.add("质地粘稠臭秽");
        list13.add("质地清稀（如鼻涕）");

        initFlow1();
        initFlow2();
        initFlow3();
        initFlow4();
        initFlow5();
        initFlow6();
        initFlow7();
        initFlow8();
        initFlow9();
        initFlow10();
        initFlow11();
        initFlow12();
        initFlow13();
    }


    private void initFlow1() {
        TagAdapter mAdapter;
        tagFlowlayout1.setAdapter(mAdapter=new TagAdapter<String>(list1)
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
                for (Integer position : selectPosSet){
                    specialStage= list1.get(position);
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    private void initFlow2() {
        TagAdapter mAdapter;
        tagFlowlayout2.setAdapter(mAdapter=new TagAdapter<String>(list2)
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
                for (Integer position : selectPosSet){
                    fertility = list2.get(position);
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    private void initFlow3() {
        TagAdapter mAdapter;
        tagFlowlayout3.setAdapter(mAdapter=new TagAdapter<String>(list3)
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

                for (Integer position : selectPosSet){
                    abortion = list3.get(position);
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    private void initFlow4() {
        TagAdapter mAdapter;
        tagFlowlayout4.setAdapter(mAdapter=new TagAdapter<String>(list4)
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
                for (Integer position : selectPosSet){
                    menopause = list4.get(position);
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    private void initFlow5() {
        TagAdapter mAdapter;
        tagFlowlayout5.setAdapter(mAdapter=new TagAdapter<String>(list5)
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
                for (Integer position : selectPosSet){
                    cycle = list5.get(position);
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    private void initFlow6() {
        TagAdapter mAdapter;
        tagFlowlayout6.setAdapter(mAdapter=new TagAdapter<String>(list6)
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

                for (Integer position : selectPosSet){
                    period = list6.get(position);
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    private void initFlow7() {
        TagAdapter mAdapter;
        tagFlowlayout7.setAdapter(mAdapter=new TagAdapter<String>(list7)
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
                for (Integer position : selectPosSet){
                    color = list7.get(position);
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    private void initFlow8() {
        TagAdapter mAdapter;
        tagFlowlayout8.setAdapter(mAdapter=new TagAdapter<String>(list8)
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
                for (Integer position : selectPosSet){
                    quantity = list8.get(position);
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }


    private void initFlow9() {
        TagAdapter mAdapter;
        tagFlowlayout9.setAdapter(mAdapter=new TagAdapter<String>(list9)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout9, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout9.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer position : selectPosSet){
                    clot = list9.get(position);
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    private void initFlow10() {
        TagAdapter mAdapter;
        tagFlowlayout10.setAdapter(mAdapter=new TagAdapter<String>(list10)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout10, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout10.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                dysmenorrhoea.clear();
                for (Integer position : selectPosSet){
                    dysmenorrhoea.add(list10.get(position));
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    private void initFlow11() {
        TagAdapter mAdapter;
        tagFlowlayout11.setAdapter(mAdapter=new TagAdapter<String>(list11)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout11, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout11.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer position : selectPosSet){
                    feel = list11.get(position);
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    private void initFlow12() {
        TagAdapter mAdapter;
        tagFlowlayout12.setAdapter(mAdapter=new TagAdapter<String>(list12)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout12, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout12.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer position : selectPosSet){
                    leucorrheaCapacity = list12.get(position);
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    private void initFlow13() {
        TagAdapter mAdapter;
        tagFlowlayout13.setAdapter(mAdapter=new TagAdapter<String>(list13)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout13, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout13.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer position : selectPosSet){
                    leucorrhea = list13.get(position);
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
                Intent intent = new Intent(this, WriteFromFourActivity.class);
                intent.putExtra("recordId",recordId);
                intent.putExtra("doctorId",doctorId);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.tv_next:
                supplement = etOther.getText().toString().trim();
                requestFifth();
                break;
        }
    }
    class Gynaecology {
        String specialStage;
        String fertility;
        String abortion;
        String menopause;
        String cycle;
        String period;
        String color;
        String quantity;
        String clot;
        List<String> dysmenorrhoea;
        String feel;
        String leucorrheaCapacity;
        String leucorrhea;
        String supplement;

        public String getSpecialStage() {
            return specialStage;
        }

        public void setSpecialStage(String specialStage) {
            this.specialStage = specialStage;
        }

        public String getFertility() {
            return fertility;
        }

        public void setFertility(String fertility) {
            this.fertility = fertility;
        }

        public String getAbortion() {
            return abortion;
        }

        public void setAbortion(String abortion) {
            this.abortion = abortion;
        }

        public String getMenopause() {
            return menopause;
        }

        public void setMenopause(String menopause) {
            this.menopause = menopause;
        }

        public String getCycle() {
            return cycle;
        }

        public void setCycle(String cycle) {
            this.cycle = cycle;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getClot() {
            return clot;
        }

        public void setClot(String clot) {
            this.clot = clot;
        }

        public List<String> getDysmenorrhoea() {
            return dysmenorrhoea;
        }

        public void setDysmenorrhoea(List<String> dysmenorrhoea) {
            this.dysmenorrhoea = dysmenorrhoea;
        }

        public String getFeel() {
            return feel;
        }

        public void setFeel(String feel) {
            this.feel = feel;
        }

        public String getLeucorrheaCapacity() {
            return leucorrheaCapacity;
        }

        public void setLeucorrheaCapacity(String leucorrheaCapacity) {
            this.leucorrheaCapacity = leucorrheaCapacity;
        }

        public String getLeucorrhea() {
            return leucorrhea;
        }

        public void setLeucorrhea(String leucorrhea) {
            this.leucorrhea = leucorrhea;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }
    }
    private void requestFifth() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("recordId",recordId);
        ajaxParams.put("templateType","gynaecology");

        Gynaecology gynaecology = new Gynaecology();

        if (!TextUtils.isEmpty(specialStage)){
            //ajaxParams.put("specialStage",specialStage);
            gynaecology.setSpecialStage(specialStage);
        }
        if (!TextUtils.isEmpty(fertility)){
            //ajaxParams.put("fertility",fertility);
            gynaecology.setFertility(fertility);
        }
        if (!TextUtils.isEmpty(abortion)){
            //ajaxParams.put("abortion",abortion);
            gynaecology.setAbortion(abortion);
        }
        if (!TextUtils.isEmpty(menopause)){
            //ajaxParams.put("menopause",menopause);
            gynaecology.setMenopause(menopause);
        }
        if (!TextUtils.isEmpty(cycle)){
            //ajaxParams.put("cycle",cycle);
            gynaecology.setCycle(cycle);
        }
        if (!TextUtils.isEmpty(period)){
            //ajaxParams.put("period",period);
            gynaecology.setPeriod(period);
        }
        if (!TextUtils.isEmpty(color)){
            //ajaxParams.put("color",color);
            gynaecology.setColor(color);
        }
        if (!TextUtils.isEmpty(quantity)){
            //ajaxParams.put("quantity",quantity);
            gynaecology.setQuantity(quantity);
        }
        if (!TextUtils.isEmpty(clot)){
            //ajaxParams.put("clot",clot);
            gynaecology.setClot(clot);
        }
        if (dysmenorrhoea.size()>0){
            //ajaxParams.put("dysmenorrhoea",dysmenorrhoea);
            gynaecology.setDysmenorrhoea(dysmenorrhoea);
        }
        if (!TextUtils.isEmpty(feel)){
            //ajaxParams.put("feel",feel);
            gynaecology.setFeel(feel);
        }
        if (!TextUtils.isEmpty(leucorrheaCapacity)){
            //ajaxParams.put("leucorrheaCapacity",leucorrheaCapacity);
            gynaecology.setLeucorrheaCapacity(leucorrheaCapacity);
        }
        if (!TextUtils.isEmpty(leucorrhea)){
            //ajaxParams.put("leucorrhea",leucorrhea);
            gynaecology.setLeucorrhea(leucorrhea);
        }

        if (!TextUtils.isEmpty(supplement)){
            //ajaxParams.put("supplement",supplement);
            gynaecology.setSupplement(supplement);
        }

        ajaxParams.put("gynaecology",gynaecology);

        /**
         * 已完成发送给医生
         */
        ajaxParams.put("complete",true);

        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).reQuestUpdateSecond(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                Call<HttpBaseBean<ImInfo>> call = RetrofitFactory.getInstance(context).getImInfo(recordId);
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ImInfo>() {

                    @Override
                    public void onSuccess(ImInfo imInfo, String info) {
                        if (imInfo!=null){
                            final String im_doctor_accid = imInfo.getIm_doctor_accid();
                            LoginInfo loginInfo = new LoginInfo(imInfo.getIm_accid(), imInfo.getIm_token());// config...
                            NIMClient.getService(AuthService.class).login(loginInfo).setCallback(new RequestCallback<LoginInfo>() {
                                @Override
                                public void onSuccess(LoginInfo param) {
                                    /*NimUIKit.loginSuccess(param.getAccount());
                                    NimUIKit.startP2PSession(context,im_doctor_accid);*/
                                    P2PMessageActivity.start(context,im_doctor_accid,null,null,"医生",recordId,doctorId);
                                }

                                @Override
                                public void onFailed(int code) {
                                    showToast("登录失败");
                                }

                                @Override
                                public void onException(Throwable exception) {
                                    showToast("登录异常");
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(String info) {

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

    }
}
