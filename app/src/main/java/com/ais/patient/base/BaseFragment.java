package com.ais.patient.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment
{

	protected BaseActivity mActivity;

	protected ProgressDialog progressDialog;
	private Unbinder bind;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutId(), container, false);
		bind = ButterKnife.bind(this,view);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		initViews(view);
		initEvent();
		initData();
	}

	public BaseActivity getBaseActivity(){
		return mActivity;
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		mActivity = (BaseActivity) activity;
		progressDialog = new ProgressDialog(mActivity);
		progressDialog.setMessage("正在加载数据，请稍等......");// 正在加载数据，请稍等......
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		bind.unbind();
	}

	protected abstract int getLayoutId();
	protected abstract void initViews(View view);
	protected abstract void initEvent();
	protected abstract void initData();

}
