package com.ais.patient.activity.mine;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends MYBaseActivity {


    @BindView(R.id.mWebView)
    WebView mWebView;

    String agreetmentUrl = "http://aszy-p.test.globalyun.com/pages/H5/user_agreement.html";
    String ticketruleUrl = "http://aszy-p.test.globalyun.com/pages/H5/ticket_rule.html";
    String problemUrl = "http://aszy-p.test.globalyun.com/pages/H5/user_problem.html";

    String webUrl;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view2;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        int type = getIntent().getIntExtra("type", 0);
        switch (type){
            case 1:
                webUrl = agreetmentUrl;
                break;
            case 2:
                webUrl = ticketruleUrl;
                break;
            case 3:
                webUrl = problemUrl;
                break;
        }
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.loadUrl(webUrl);
    }

    @Override
    protected void initEvent() {

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
}
