package com.ais.patient.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.ais.patient.util.ActivityManage;
import com.ais.patient.widget.MyProgressDialog;

import butterknife.ButterKnife;

/**
 * Created by ewu on 2016/2/18.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener{

    MyProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        progressDialog = new MyProgressDialog(this);
        progressDialog.setCancelable(false); //设置等待按钮不能手动取消
        initViews(savedInstanceState);
        initEvent();
        initData();
    }

    protected abstract int getLayoutId();
    protected abstract void initViews(Bundle savedInstanceState);
    protected abstract void initEvent();
    protected abstract void initData();


    @Override
    public void onClick(View view) {

    }

    public boolean isShowingProgressDialog() {
        return progressDialog.isShowing();
    }

    public void showProgressDialog()
    {
        progressDialog.showProgress();
    }

    public void dismissProgressDialog()
    {
        progressDialog.dismissProgress();
    }

}
