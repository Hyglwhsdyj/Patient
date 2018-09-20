package com.ais.patient.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.util.DisplayUtil;
import com.ais.patient.util.SystemUtils;
import com.ais.patient.widget.loadingdrawable.LoadingDrawable;
import com.ais.patient.widget.loadingdrawable.MaterialLoadingRenderer;


/**
 * @author chenwenwen
 */

public class EmptyView extends LinearLayout
{

	private LoadingDrawable mMaterialDrawable;

	private ImageView mImage;
	
	private TextView mText;
	
	private TextView mBtn;
	
	public static final int MODE_LOADING = 0;
	
	public static final int MODE_NODATA = 1;
	
	public static final int MODE_ERROR = 2;

	private int currentMode = -1;
	
	@SuppressLint("NewApi")
	public EmptyView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		initView(context);
	}
	
	public EmptyView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView(context);
	}
	
	public EmptyView(Context context)
	{
		super(context);
		initView(context);
	}
	
	@SuppressLint("NewApi")
	private void initView(Context context)
	{
		mMaterialDrawable = new LoadingDrawable(new MaterialLoadingRenderer(context));

//		this.setId(R.id.emptyview);
		this.setGravity(Gravity.CENTER);
		this.setOrientation(LinearLayout.VERTICAL);
		mImage = new ImageView(getContext());
		mImage.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		this.addView(mImage);

		mText = new TextView(getContext());
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
		        LayoutParams.WRAP_CONTENT);
		params.setMargins(0, DisplayUtil.dip2px(getContext(), 10), 0, DisplayUtil.dip2px(getContext(), 10));
		mText.setLayoutParams(params);
		mText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		this.addView(mText);
		
		mBtn = new TextView(getContext());
		mBtn.setLayoutParams(params);
		mBtn.setGravity(Gravity.CENTER);
		
		if (SystemUtils.getSDKVersion() < 16)
		{
//			mBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_buynow));
		} else
		{
//			mBtn.setBackground(getResources().getDrawable(R.drawable.selector_buynow));
		}
		
		this.addView(mBtn);
		mBtn.setVisibility(View.GONE);
		
		if (SystemUtils.getSDKVersion() < 16)
		{
			// mMin.setBackgroundDrawable(ViewUtils.getStateDrawable(getContext(),
			// normal, active, disable));
		} else
		{
			// mMin.setBackground(ViewUtils.getStateDrawable(getContext(),
			// normal, active, disable));
		}
		
		this.setMode(MODE_LOADING);
		
		this.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public void setBtnTextAndListener(String text, Drawable background, OnClickListener listener)
	{
		mBtn.setVisibility(View.VISIBLE);
		mBtn.setText(text);
		mBtn.setTextColor(Color.parseColor("#ffffff"));
		mBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		if (Build.VERSION.SDK_INT < 16)
		{
			mBtn.setBackgroundDrawable(background);
		} else
		{
			mBtn.setBackground(background);
		}

		mBtn.setOnClickListener(listener);
	}
	
	public void setImageResource(int rId)
	{
		if (getVisibility() == View.GONE)
		{
			this.setVisibility(View.VISIBLE);
		}

		if (mMaterialDrawable.isRunning())
		{
			mMaterialDrawable.stop();
		}

		mImage.setImageResource(rId);
	}
	
	public void setText(String text)
	{
		if (getVisibility() == View.GONE)
		{
			this.setVisibility(View.VISIBLE);
		}
		mText.setText(text);
	}
	
	public void setMode(int mode)
	{
		if (getVisibility() == View.GONE)
		{
			this.setVisibility(View.VISIBLE);
		}

		currentMode = mode;
		
		switch (mode)
		{
		case MODE_LOADING:
			this.setText("正在加载...");
			mImage.setImageDrawable(mMaterialDrawable);
			mMaterialDrawable.start();
			break;
		case MODE_NODATA:
			if (mMaterialDrawable.isRunning())
			{
				mMaterialDrawable.stop();
			}
			mImage.setImageResource(R.mipmap.src_nodata);
			this.setText("未找到数据");
			break;
		case MODE_ERROR:
			if (mMaterialDrawable.isRunning())
			{
				mMaterialDrawable.stop();
			}
			mImage.setImageResource(R.mipmap.src_error);
			this.setText("获取数据失败，请点击重试");
			break;
		
		default:
			break;
		}
	}

	public int getCurrentMode() {
		return currentMode;
	}

	public void setCurrentMode(int mode)
	{
		currentMode = mode;
	}

	public void setNoDataView(int rId, String text)
	{
		if (getVisibility() == View.GONE)
		{
			this.setVisibility(View.VISIBLE);
		}
		this.setImageResource(rId);
		this.setText(text);
	}

	public ImageView getmImage()
	{
		return mImage;
	}
	
	public void setmImage(ImageView mImage)
	{
		this.mImage = mImage;
	}
	
	public TextView getmText()
	{
		return mText;
	}
	
	public void setmText(TextView mText)
	{
		this.mText = mText;
	}
	
	public TextView getmBtn()
	{
		return mBtn;
	}
	
	public void setmBtn(TextView mBtn)
	{
		this.mBtn = mBtn;
	}
	
}
