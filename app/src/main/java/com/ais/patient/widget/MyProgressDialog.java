
package com.ais.patient.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.widget.loadingdrawable.LoadingDrawable;
import com.ais.patient.widget.loadingdrawable.MaterialLoadingRenderer;


/**
 * @author cate 2014-12-3 下午6:09:06
 */
public class MyProgressDialog extends Dialog
{
	private LoadingDrawable mMaterialDrawable;
	private ImageView mLoadingView;
	private TextView mMessage;
	private String message="";
	
	/**
	 * 创建自定义style的progressdialog
	 * 
	 * @param context
	 * @param style
	 */
	public MyProgressDialog(Context context, int style)
	{
		super(context, style);
		initView(context);
	}
	
	/**
	 * 创建自定义的progressdialog 默认style (style 为无边框 透明 无标题)
	 * 
	 * @param context
	 */
	public MyProgressDialog(Context context)
	{
		this(context, R.style.CustomDialog);
		initView(context);
	}
	
	/**
	 * 创建自定义的progressdialog 默认style (style 为无边框 透明 无标题)
	 * 
	 * @param context
	 *            上下文
	 * @param message
	 *            提示消息
	 */
	public MyProgressDialog(Context context, String message)
	{
		this(context, R.style.CustomDialog, message);
	}
	
	/**
	 * 创建自定义style的的progressdialog
	 * 
	 * @param context
	 *            上下文
	 * @param message
	 *            提示消息
	 */
	public MyProgressDialog(Context context, int style, String message)
	{
		this(context, style);
		this.message = message;
		initView(context);
	}
	
	private void initView(Context context)
	{
		mMaterialDrawable = new LoadingDrawable(new MaterialLoadingRenderer(context));
		this.setContentView(R.layout.view_loading_progress);
		mMessage = (TextView) findViewById(R.id.current_action);
		mLoadingView = (ImageView)  findViewById(R.id.data_loading_img);
		mLoadingView.setImageDrawable(mMaterialDrawable);
		setShowMessage(message);
	}
	
	public MyProgressDialog setShowMessage(String msg)
	{
		if (!TextUtils.isEmpty(msg))
		{
			mMessage.setText(msg);
		}
		else
		{
			mMessage.setText("加载中...");
		}
		
		return this;
	}

	public MyProgressDialog showProgress()
	{
		this.show();
		mMaterialDrawable.start();
		return this;
	}

	public MyProgressDialog dismissProgress()
	{
		if (mMaterialDrawable.isRunning()) {
			mMaterialDrawable.stop();
		}
		this.dismiss();
		return this;
	}

}
