package com.ais.patient.activity.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mWebView)
    WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(url)){
            mWebView.setWebViewClient(new WebViewClient() {
                //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            mWebView.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
            mWebView.loadUrl(url);
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
