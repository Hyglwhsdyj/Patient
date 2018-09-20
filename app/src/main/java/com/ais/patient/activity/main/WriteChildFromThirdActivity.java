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
import com.ais.patient.been.ImInfo;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.LogUtils;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.util.NIMUtil;
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

import static com.netease.nim.uikit.impl.NimUIKitImpl.getAccount;

public class WriteChildFromThirdActivity extends MYBaseActivity {

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
    private String stoolTime;
    List<String>shape = new ArrayList<>();
    private String colour;
    private String urineTime;
    private String urineColour;
    private String supplement;
    private String TAG = "WriteChildFromThirdActivity";
    private String doctorId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_child_from_third;
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


        /**
         * 第二个
         */
        list2.add("成形、不干不稀（香蕉便）");
        list2.add("成形、偏干");
        list2.add("成形、偏软（细条状）");
        list2.add("干燥成球状");
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
        list4.add("小便次数偏多（多于8次）");
        list4.add("小便次数偏少（少于5次）");
        list4.add("正常");

        /**
         * 第五个
         */
        list5.add("小便深黄");
        list5.add("小便淡黄");
        list5.add("小便红");
        list5.add("小便无色");

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
                    stoolTime=null;
                }else {
                    for (Integer position : selectPosSet){
                        stoolTime = list1.get(position);
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
                    colour=null;
                }else {
                    for (Integer position : selectPosSet){
                        colour = list3.get(position);
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
                    urineTime = null;
                }else {
                    for (Integer position : selectPosSet){
                        urineTime = list4.get(position);
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
                    urineColour = null;
                }else {
                    for (Integer position : selectPosSet){
                        urineColour = list5.get(position);
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
                Intent intent = new Intent(this, WriteChildFromSecondActivity.class);
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

    class ChildExcrement {
        String stoolTime;
        List<String> shape;
        String colour;
        String urineTime;
        String urineColour;
        String supplement;

        public String getStoolTime() {
            return stoolTime;
        }

        public void setStoolTime(String stoolTime) {
            this.stoolTime = stoolTime;
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

        public String getUrineTime() {
            return urineTime;
        }

        public void setUrineTime(String urineTime) {
            this.urineTime = urineTime;
        }

        public String getUrineColour() {
            return urineColour;
        }

        public void setUrineColour(String urineColour) {
            this.urineColour = urineColour;
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
        ajaxParams.put("templateType","childExcrement");
        ChildExcrement childExcrement = new ChildExcrement();
        if (!TextUtils.isEmpty(stoolTime)){
            //ajaxParams.put("stoolTime",stoolTime);
            childExcrement.setStoolTime(stoolTime);
        }
        if (shape.size()>0){
            //ajaxParams.put("shape",shape);
            childExcrement.setShape(shape);
        }
        if (!TextUtils.isEmpty(colour)){
            //ajaxParams.put("colour",colour);
            childExcrement.setColour(colour);
        }
        if (!TextUtils.isEmpty(urineTime)){
            //ajaxParams.put("urineTime",urineTime);
            childExcrement.setUrineTime(urineTime);
        }
        if (!TextUtils.isEmpty(urineColour)){
            //ajaxParams.put("urineColour",urineColour);
            childExcrement.setUrineColour(urineColour);
        }
        if (!TextUtils.isEmpty(supplement)){
            //ajaxParams.put("supplement",supplement);
            childExcrement.setSupplement(supplement);
        }
        ajaxParams.put("childExcrement",childExcrement);
        /**
         * 已完成发送给医生
         */
        ajaxParams.put("complete",true);

        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Log.e(TAG, "requestFirst: "+urlParams.toString());
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).reQuestUpdateSecond(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                showToast("提交成功");
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
        final String baseUrl = "/api/interrogation/" + recordId;
        Call<HttpBaseBean<ChatOnLinePaper>> call = RetrofitFactory.getInstance(context).getChatOnLinePaper(baseUrl);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ChatOnLinePaper>() {

            @Override
            public void onSuccess(ChatOnLinePaper chatOnLinePaper, String info) {
                if (chatOnLinePaper!=null){
                    ChatOnLinePaper.ChildExcrementBean childExcrement = chatOnLinePaper.getChildExcrement();
                    if (childExcrement!=null){
                        stoolTime = childExcrement.getStoolTime();
                        for (int i = 0; i < list1.size(); i++) {
                            if (stoolTime.equals(list1.get(i))){
                                mAdapter1.setSelectedList(i);
                            }
                        }

                        shape = childExcrement.getShape();
                        if (shape!=null){
                            for (int i = 0; i < list2.size(); i++) {
                                for (int j = 0; j < shape.size(); j++) {
                                    if (shape.get(j).equals(list2.get(i))){
                                        mAdapter4.setSelectedList(i);
                                    }
                                }
                            }
                        }else {
                            shape = new ArrayList<>();
                        }

                        colour = childExcrement.getColour();
                        for (int i = 0; i < list3.size(); i++) {
                            if (colour.equals(list3.get(i))){
                                mAdapter3.setSelectedList(i);
                            }
                        }

                        urineTime = childExcrement.getUrineTime();
                        for (int i = 0; i < list4.size(); i++) {
                            if (urineTime.equals(list4.get(i))){
                                mAdapter4.setSelectedList(i);
                            }
                        }

                        urineColour = childExcrement.getUrineColour();
                        for (int i = 0; i < list5.size(); i++) {
                            if (urineColour.equals(list5.get(i))){
                                mAdapter5.setSelectedList(i);
                            }
                        }

                        if (childExcrement.getSupplement()!=null){
                            supplement = childExcrement.getSupplement();
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
