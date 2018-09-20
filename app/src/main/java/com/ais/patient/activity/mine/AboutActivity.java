package com.ais.patient.activity.mine;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.About;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.HtmlImageGetter;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class AboutActivity extends MYBaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_html)
    TextView tvHtml;
    /*@BindView(R.id.tv_weAre)
    TextView tvWeAre;
    @BindView(R.id.tv_our_serive)
    TextView tvOurSerive;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;*/

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("关于我们");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        Call<HttpBaseBean<About>> call = RetrofitFactory.getInstance(this).getAboutMsg();
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<About>() {

            @Override
            public void onSuccess(About about, String info) {
                String content = about.getContent();
                if (!TextUtils.isEmpty(content)){
                    HtmlImageGetter htmlImageGetter = new HtmlImageGetter(AboutActivity.this,tvHtml);
                    tvHtml.setText(Html.fromHtml(content,htmlImageGetter,null));
                }
            }

            @Override
            public void onFailure(String info) {

            }
        });
    }


    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }

   /* private void requestCommit() {
        AjaxParams ajaxParams = new AjaxParams();
        if (!TextUtils.isEmpty(address)) {
            ajaxParams.put("address", address);
        }
        ajaxParams.put("phoneNumber", phone);
        if (!TextUtils.isEmpty(email)) {
            ajaxParams.put("email", email);
        }
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).requestCommit(urlParams);
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
    }*/

}
