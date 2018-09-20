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
import android.widget.EditText;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
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

public class WriteFifthManActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.id_flowlayout1)
    TagFlowLayout tagFlowlayout1;
    @BindView(R.id.id_flowlayout2)
    TagFlowLayout tagFlowlayout2;
    @BindView(R.id.et_other)
    EditText etOther;
    @BindView(R.id.tv_text_num)
    TextView tvTextNum;
    private Context context;
    private String recordId;

    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    private String sexualFunction;
    private String andrology;
    private String supplement;
    private String doctorId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_fifth_man;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("填写问诊单");
        context = this;
        recordId = getIntent().getStringExtra("recordId");
        doctorId = getIntent().getStringExtra("doctorId");
        list1.add("早泄");
        list1.add("阳痿");
        list1.add("勃起不坚");
        list1.add("晨勃消失");
        list1.add("频繁遗精");
        list1.add("频繁滑精");
        list1.add("无以上情况");

        list2.add("不育");
        list2.add("前列腺炎");
        list2.add("附睾炎");
        list2.add("睾丸炎");
        list2.add("尿道炎");
        list2.add("无以上情况");
        initFlow1();
        initFlow2();
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
                    sexualFunction = list1.get(position);
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
                    andrology = list2.get(position);
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
                requestFifthMan();
                break;
        }
    }

    class Andrology{
        String sexualFunction;
        String andrology;
        String supplement;

        public String getSexualFunction() {
            return sexualFunction;
        }

        public void setSexualFunction(String sexualFunction) {
            this.sexualFunction = sexualFunction;
        }

        public String getAndrology() {
            return andrology;
        }

        public void setAndrology(String andrology) {
            this.andrology = andrology;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }
    }

    private void requestFifthMan() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("recordId",recordId);
        ajaxParams.put("templateType","andrology");
        Andrology andrology1 = new Andrology();

        if (!TextUtils.isEmpty(sexualFunction)){
            //ajaxParams.put("sexualFunction",sexualFunction);
            andrology1.setSexualFunction(sexualFunction);
        }
        if (!TextUtils.isEmpty(andrology)){
            //ajaxParams.put("\"andrology\"",andrology);
            andrology1.setAndrology(andrology);
        }
        if (!TextUtils.isEmpty(supplement)){
            //ajaxParams.put("supplement",supplement);
            andrology1.setSupplement(supplement);
        }
        ajaxParams.put("andrology",andrology1);
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
