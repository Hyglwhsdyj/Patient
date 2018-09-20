package com.ais.patient.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.main.BigImageViewActivity;
import com.ais.patient.activity.main.BuyMeetingSeriveActivity;
import com.ais.patient.activity.main.BuyOnlineSeriveActivity;
import com.ais.patient.activity.main.DoctorInfomationActivity;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.DoctorNewDetial;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.HtmlImageGetter;
import com.ais.patient.widget.CircleTransform;
import com.ais.patient.widget.FlowGroupView;
import com.ais.patient.widget.NetstedGridView;
import com.ais.patient.widget.UploadPopupwindow;
import com.bumptech.glide.Glide;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import retrofit2.Call;

public class DoctorDetailActivity extends MYBaseActivity {


    @BindView(R.id.mNetstedGridView)
    NetstedGridView mNetstedGridView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_dtitle)
    TextView tvDtitle;
    @BindView(R.id.tv_doctortitle)
    TextView tvDoctortitle;
    @BindView(R.id.tv_depart)
    TextView tvDepart;
    @BindView(R.id.tv_medicalInstitutions)
    TextView tvMedicalInstitutions;
    @BindView(R.id.mFlowGroupView)
    FlowGroupView mFlowGroupView;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    private Context context;
    private String doctorId;
    DoctorNewDetial.DoctorBean doctor2 = new DoctorNewDetial.DoctorBean();
    private String shareTitle;
    private String shareContent;
    private String shareImage;
    private String shareUrl;
    private WindowManager.LayoutParams params;
    private UploadPopupwindow popupwindow;
    private OnekeyShare oks;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_dynamic;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        tvTitle.setText("动态详情");
        String id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            String baseUrl = "/api/doctor/news/" + id;
            Call<HttpBaseBean<DoctorNewDetial>> call = RetrofitFactory.getInstance(this).getDoctorNewsDetial(baseUrl);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<DoctorNewDetial>() {

                @Override
                public void onSuccess(DoctorNewDetial doctorNew, final String info) {
                    if (doctorNew != null) {
                        shareTitle = doctorNew.getShareTitle();
                        shareContent = doctorNew.getShareContent();
                        shareImage = doctorNew.getShareImage();
                        shareUrl = doctorNew.getShareUrl();

                        String type = doctorNew.getType();
                        if (type.equals("5")){
                            mNetstedGridView.setVisibility(View.VISIBLE);
                        }else {
                            mNetstedGridView.setVisibility(View.GONE);
                        }
                        if (type.equals("5")){
                            tvDtitle.setVisibility(View.GONE);
                            tvTime.setVisibility(View.GONE);
                        }else if (type.equals("6")){
                            tvDtitle.setVisibility(View.GONE);
                        }else if (type.equals("1")){
                            //全部显示
                        }
                        tvDtitle.setText(doctorNew.getTitle());
                        tvTime.setText(doctorNew.getTime());

                        String content = doctorNew.getContent();
                        HtmlImageGetter htmlImageGetter = new HtmlImageGetter(context,tvContent);
                        tvContent.setText(Html.fromHtml(content,htmlImageGetter,null));

                        List<String> pics = doctorNew.getPics();
                        Adapter<String> adapter = new Adapter<String>(context,R.layout.image_item,pics) {
                            @Override
                            protected void convert(AdapterHelper helper, final String item) {
                                ImageView ivIcon = helper.getItemView().findViewById(R.id.iv_icon);
                                Glide.with(context).load(item).into(ivIcon);
                                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, BigImageViewActivity.class);
                                        intent.putExtra("imgUrl",item);
                                        startActivity(intent);
                                    }
                                });
                            }
                        };
                        mNetstedGridView.setAdapter(adapter);

                        DoctorNewDetial.DoctorBean doctor = doctorNew.getDoctor();
                        doctor2 = doctor;
                        doctorId = doctor.getDoctorId();
                        Picasso.with(context).load(doctor.getImage()).transform(new CircleTransform()).into(ivIcon);
                        tvName.setText(doctor.getName());
                        tvDoctortitle.setText(doctor.getTitles());
                        tvDepart.setText(doctor.getDepart());
                        tvMedicalInstitutions.setText(doctor.getMedicalInstitutions());
                        tvFee.setText(doctor.getFee()+"元/次");

                        final List<String> diseaseExpertise = doctor.getDiseaseExpertise();
                        if (diseaseExpertise!=null && diseaseExpertise.size()>0){
                            for (int i = 0; i < diseaseExpertise.size(); i++) {
                                addTextView(diseaseExpertise.get(i));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(String info) {

                }
            });
        }
    }

    private void addTextView(String str) {
        TextView child = new TextView(this);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        child.setLayoutParams(params);
        child.setBackgroundResource(R.drawable.shape_diseaseexpertise);
        child.setText(str);
        child.setTextSize(11);
        child.setTextColor(this.getResources().getColor(R.color.diseaseexpertise_bg));
        mFlowGroupView.addView(child);
    }
    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_back, R.id.tv_right, R.id.tv_toChatonLine,R.id.tv_toMeeting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_right:
                showPopWindow();
                break;
            case R.id.tv_toChatonLine:
                if (doctor2!=null){
                    Intent intent = new Intent(context,BuyOnlineSeriveActivity.class);
                    intent.putExtra("doctorId",doctorId);
                    intent.putExtra("image",doctor2.getImage());
                    intent.putExtra("name",doctor2.getName());
                    intent.putExtra("depart",doctor2.getDepart());
                    intent.putExtra("titles",doctor2.getTitles());
                    intent.putExtra("medicalInstitutions",doctor2.getMedicalInstitutions());
                    intent.putExtra("diseaseExpertise",(Serializable) doctor2.getDiseaseExpertise());
                    startActivity(intent);
                }
                break;
            case R.id.tv_toMeeting:
                if (doctor2!=null){
                    //否则去到购买服务页面
                    Intent intent = new Intent(context, BuyMeetingSeriveActivity.class);
                    intent.putExtra("doctorId",doctorId);
                    intent.putExtra("image",doctor2.getImage());
                    intent.putExtra("name",doctor2.getName());
                    intent.putExtra("depart",doctor2.getDepart());
                    intent.putExtra("titles",doctor2.getTitles());
                    intent.putExtra("medicalInstitutions",doctor2.getMedicalInstitutions());
                    intent.putExtra("diseaseExpertise",(Serializable) doctor2.getDiseaseExpertise());
                    startActivity(intent);
                }
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
                oks.setTitleUrl(shareUrl);
                oks.setImageUrl(shareImage);
                //oks.setImageData(((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_logo)).getBitmap());
                oks.setTitle(shareTitle);
                oks.setText(shareContent);
                oks.setPlatform(QQ.NAME);
                oks.show(this);
                popupwindow.dismiss();
                break;
            case R.id.tv_wechat:
                oks.setUrl(shareUrl);
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
                oks.setUrl(shareUrl);
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
