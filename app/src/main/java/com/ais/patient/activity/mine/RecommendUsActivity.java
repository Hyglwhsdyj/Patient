package com.ais.patient.activity.mine;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.ShareMsg;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.widget.UploadPopupwindow;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import retrofit2.Call;

public class RecommendUsActivity extends MYBaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    private WindowManager.LayoutParams params;
    private UploadPopupwindow popupwindow;
    private OnekeyShare oks;
    private String shareTitle;
    private String imagesUrl;
    private String shareImage;
    private String url;
    private String shareContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recommend_us;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Call<HttpBaseBean<ShareMsg>> shareMsg = RetrofitFactory.getInstance(this).getShareMsg();
        new BaseCallback(shareMsg).handleResponse(new BaseCallback.ResponseListener<ShareMsg>() {

            @Override
            public void onSuccess(ShareMsg shareMsg, String info) {
                if (shareMsg != null) {
                    imagesUrl = shareMsg.getImagesUrl();
                    if (!TextUtils.isEmpty(imagesUrl)){
                        Glide.with(RecommendUsActivity.this).load(imagesUrl).into(ivImage);
                    }
                    shareImage = shareMsg.getShareImage();
                    url = shareMsg.getUrl();
                    shareContent = shareMsg.getShareContent();
                    shareTitle = shareMsg.getShareTitle();
                }
            }

            @Override
            public void onFailure(String info) {

            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_back, R.id.tv_share})
    public void setOnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_share:
                showPopWindow();
                break;
        }
    }

    private void showPopWindow() {
        oks = new OnekeyShare();
        popupwindow = new UploadPopupwindow(this, R.layout.pop_share, R.id.ll_photographs, 1000);
        popupwindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupwindow.showAtLocation(findViewById(R.id.register_root_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        popupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        TextView tvQQ = popupwindow.getContentView().findViewById(R.id.tv_qq);
        TextView tvWechat = popupwindow.getContentView().findViewById(R.id.tv_wechat);
        TextView tvFriend = popupwindow.getContentView().findViewById(R.id.tv_friend);
        TextView tvCancel = popupwindow.getContentView().findViewById(R.id.tv_cancel);
        tvQQ.setOnClickListener(this);
        tvWechat.setOnClickListener(this);
        tvFriend.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_qq:
                oks.setTitleUrl(url);
                oks.setImageUrl(shareImage);
                //oks.setImageData(((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_logo)).getBitmap());
                oks.setTitle(shareTitle);
                oks.setText(shareContent);
                oks.setPlatform(QQ.NAME);
                oks.show(this);
                popupwindow.dismiss();
                break;
            case R.id.tv_wechat:
                oks.setUrl(url);
                //oks.setImagePath(shareImage);
                oks.setImageUrl(shareImage);
                oks.setTitle(shareTitle);
                oks.setText(shareContent);
                oks.setPlatform(Wechat.NAME);
                oks.show(this);
                popupwindow.dismiss();
                break;
            case R.id.tv_friend:
                OnekeyShare oks = new OnekeyShare();
                oks.setUrl(url);
                //oks.setImagePath(shareImage);
                oks.setImageUrl(shareImage);
                oks.setTitle(shareTitle);
                oks.setText(shareContent);
                oks.setPlatform(WechatMoments.NAME);
                oks.show(this);
                popupwindow.dismiss();
                break;
            case R.id.tv_cancel:
                popupwindow.dismiss();
                break;
        }
    }


}
