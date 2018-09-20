package com.ais.patient.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ais.patient.R;

import java.text.SimpleDateFormat;

public class PullToRefreshListView extends ListView implements OnScrollListener {
	private static final String TAG = "PullToRefreshListView";

	private int firstVisibleItemPosition;
	private int downY;
	private int headerViewHeight;
	private View headerView;

	private final int DOWN_PULL_REFRESH = 0;
	private final int RELEASE_REFRESH = 1;
	private final int REFRESHING = 2;
	private int currentState = DOWN_PULL_REFRESH;

	private Animation upAnimation;
	private Animation downAnimation;

	private ImageView ivArrow;
	private ProgressBar mProgressBar;
	private TextView tvState;
	private TextView tvLastUpdateTime;

	private OnRefreshListener mOnRefershListener;
	private boolean isScrollToBottom;
	private View footerView;
	private int footerViewHeight;
	private boolean isLoadingMore = false;

	private boolean isLoadMore = true;


	private final static float RATIO = 2.5f;

	private static final int MAX_Y_OVERSCROLL_DISTANCE = 100;  
	private int mMaxYOverscrollDistance;  

	public PullToRefreshListView(Context context, AttributeSet attrs,
                                 int defStyle) {
		super(context, attrs, defStyle);
		init(context);

	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PullToRefreshListView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context){
		initHeaderView(context);
		initFooterView(context);
		initBounceListView(context);
		this.setOnScrollListener(this);
	}

//	/**
//	 * 计算高度为测量高度，解决与scrollview的滑动冲突
//	 * @param widthMeasureSpec
//	 * @param heightMeasureSpec
//	 */
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//		super.onMeasure(widthMeasureSpec, expandSpec);
//	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN :
			downY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE :
//			int moveY = (int) ev.getY();
//			int diff = (int) (((float)moveY - (float)downY) / RATIO);
//			int paddingTop = -headerViewHeight + diff;
//			if (firstVisibleItemPosition == 0 && diff>0 && currentState != REFRESHING && !isLoadingMore) {
//				if (paddingTop > 0 && currentState == DOWN_PULL_REFRESH) {
//					currentState = RELEASE_REFRESH;
//					refreshHeaderView();
//				} else if (paddingTop < 0 && currentState == RELEASE_REFRESH) {
//					currentState = DOWN_PULL_REFRESH;
//					refreshHeaderView();
//				}
//				headerView.setPadding(0, paddingTop/2, 0, 0);
//
//			}
			if (currentState == REFRESHING) {
				//如果当前处在滑动状态，则不做处理
				break;
			}
			//手指滑动偏移量
			int deltaY = (int) (((float)ev.getY() - (float)downY) / RATIO);

			//获取新的padding值
			int paddingTop = -headerViewHeight + deltaY;
			if (paddingTop > -headerViewHeight && getFirstVisiblePosition() == 0) {
				//向下滑，且处于顶部，设置padding值，该方法实现了顶布局慢慢滑动显现
				headerView.setPadding(0, paddingTop, 0, 0);

				if (paddingTop >= 0 && currentState == DOWN_PULL_REFRESH) {
					//从下拉刷新进入松开刷新状态
					currentState = RELEASE_REFRESH;
					//刷新头布局
					refreshHeaderView();
				} else if (paddingTop < 0 && currentState == RELEASE_REFRESH) {
					//进入下拉刷新状态
					currentState = DOWN_PULL_REFRESH;
					refreshHeaderView();
				}


			//	return true;//拦截TouchMove，不让listview处理该次move事件,会造成listview无法滑动
			}
			break;
		case MotionEvent.ACTION_UP :
			// 判断当前的状态是松开刷新还是下拉刷新
			if (currentState == RELEASE_REFRESH) {
				headerView.setPadding(0, 0, 0, 0);
				currentState = REFRESHING;
				refreshHeaderView();

				if (mOnRefershListener != null) {
					mOnRefershListener.onDownPullRefresh();// 调用使用者的监听方法

				}

			} else if (currentState == DOWN_PULL_REFRESH) {
				headerView.setPadding(0, -headerViewHeight, 0, 0);
			}
			break;
		default :
			break;
		}
		return super.onTouchEvent(ev);
	}



	private void refreshHeaderView() {
		switch (currentState) {
		case DOWN_PULL_REFRESH :
			tvState.setText("下拉刷新");
			//ivArrow.startAnimation(downAnimation);
			ivArrow.setVisibility(VISIBLE);
			break;
		case RELEASE_REFRESH :
			tvState.setText("松开刷新");
			//ivArrow.startAnimation(upAnimation);
			ivArrow.setVisibility(VISIBLE);
			break;
		case REFRESHING :
			ivArrow.clearAnimation();
			ivArrow.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);
			tvState.setText("正在刷新中...");
			break;
		default :
			break;
		}
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {


		if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {

			if (isScrollToBottom && !isLoadingMore && isLoadMore() && currentState != REFRESHING) {
				isLoadingMore = true;
				footerView.setPadding(0, 0, 0, 0);//显示出footerView
				setSelection(getCount());//让listview最后一条显示出来，在页面完全显示出底布局
				if(mOnRefershListener!=null){
					mOnRefershListener.onLoadingMore();
				 }
			}
		}

	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
		firstVisibleItemPosition = firstVisibleItem;

		if (getLastVisiblePosition() == (totalItemCount - 1)) {
			isScrollToBottom = true;
		} else {
			isScrollToBottom = false;
		}

	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
			int scrollRangeX, int scrollRangeY, int maxOverScrollX, 
			int maxOverScrollY, boolean isTouchEvent){   
		//This is where the magic happens, we have replaced the incoming maxOverScrollY 
		//with our own custom variable mMaxYOverscrollDistance;

		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
				scrollRangeX, scrollRangeY, maxOverScrollX,
				mMaxYOverscrollDistance, isTouchEvent);

	}

	private void initBounceListView(Context context){
		//get the density of the screen and do some maths with it on the max overscroll distance  
		//variable so that you get similar behaviors no matter what the screen size  
		final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		final float density = metrics.density; 
		mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);  
	}  


	private void initFooterView(Context context) {
		footerView = LinearLayout.inflate(context, R.layout.footer_bga_dodo,null);
		footerView.measure(0, 0);
		footerViewHeight = footerView.getMeasuredHeight();
		footerView.setPadding(0, -footerViewHeight, 0, 0);
		this.addFooterView(footerView);
	}


	private void initHeaderView(Context context) {
		headerView = LinearLayout.inflate(context, R.layout.header_bga_dodo,null);
		ivArrow = (ImageView) headerView
				.findViewById(R.id.iv_normal_refresh_header_arrow);
		mProgressBar = (ProgressBar) headerView
				.findViewById(R.id.mProgressBar);
		tvState = (TextView) headerView
				.findViewById(R.id.tv_normal_refresh_header_status);
//		tvLastUpdateTime = (TextView) headerView
//				.findViewById(R.id.tv_listview_header_last_update_time);
//
//		tvLastUpdateTime.setText("最后刷新时间: " + getLastUpdateTime());
		
		headerView.measure(0, 0);
		headerViewHeight = headerView.getMeasuredHeight();
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		this.addHeaderView(headerView);
//		initAnimation();
	}


	public void onRefreshComplete(){

		if (isLoadingMore) {
			hideFooterView();
		}else if (currentState == REFRESHING) {
			hideHeaderView();
		}
	}


	private void hideHeaderView() {
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		ivArrow.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
		tvState.setText("下拉刷新");
//		tvLastUpdateTime.setText("最后刷新时间: " + getLastUpdateTime());
		currentState = DOWN_PULL_REFRESH;
	}


	private void hideFooterView() {
		footerView.setPadding(0, -footerViewHeight, 0, 0);
		isLoadingMore = false;
	}



	private String getLastUpdateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(System.currentTimeMillis());
	}


	private void initAnimation() {

		upAnimation = new RotateAnimation(0f, -180f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		upAnimation.setInterpolator(new LinearInterpolator());
		upAnimation.setDuration(500);
		upAnimation.setFillAfter(true);

		downAnimation = new RotateAnimation(-180f, -360f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		downAnimation.setInterpolator(new LinearInterpolator());
		downAnimation.setDuration(500);
		downAnimation.setFillAfter(true);
	}


	public void setOnRefreshListener(OnRefreshListener listener) {
		mOnRefershListener = listener;
	}

	public interface OnRefreshListener {

		void onDownPullRefresh();


		void onLoadingMore();
	}

	public interface onDownPullRefresh {
		public void onRefresh();
	}


	public boolean isLoadMore() {
		return isLoadMore;
	}


	public void setLoadMore(boolean isLoadMore) {
		this.isLoadMore = isLoadMore;
	}


}
