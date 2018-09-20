package com.ais.patient.activity.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class CallBackActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_call_back;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("意见反馈");
    }

    @Override
    protected void initEvent() {
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>0){
                    tvCommit.setBackgroundResource(R.drawable.shape_back2);
                }
                int length = etContent.getText().length();
                tvNum.setText(length + "/" + 300);
                if (etContent.getText().length() > 300) {
                    String newShopTitle = etContent.getText().toString().substring(0, 300);
                    etContent.setText(newShopTitle);
                    etContent.setSelection(etContent.length());
                    showToast("字数300个以内");
                } else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    @OnClick({R.id.tv_back, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_commit:
                String content = etContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    showToast("请输入您的宝贵意见");
                }else {
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put("content",content);
                    ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
                    Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).feedBack(urlParams);
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                        @Override
                        public void onSuccess(Object o, String info) {
                            showToast("提交成功");
                            finish();
                        }

                        @Override
                        public void onFailure(String info) {
                            showToast(info);
                        }
                    });
                }
                break;
        }
    }
}
