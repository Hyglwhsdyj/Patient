package com.ais.patient.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.Coupons;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.widget.NetstedListView;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class CouponsActivity extends MYBaseActivity {

    List<Coupons> list = new ArrayList<>();
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mNetstedListView)
    NetstedListView mNetstedListView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupons;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("我的优惠券");
        Intent intent = getIntent();
        String businessType = intent.getStringExtra("businessType");
        double fee = intent.getDoubleExtra("fee", 0);
        String businessId = intent.getStringExtra("businessId");
        if (businessId!=null && !businessId.equals("")){
            Call<HttpBaseBean<List<Coupons>>> call = RetrofitFactory.getInstance(this).getCouponsList2(businessType,fee,businessId);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<Coupons>>() {

                @Override
                public void onSuccess(List<Coupons> coupons, String info) {
                    if (coupons != null) {
                        list.addAll(coupons);
                        Adapter<Coupons> adapter = new Adapter<Coupons>(CouponsActivity.this,R.layout.coupons_item,list) {
                            @Override
                            protected void convert(AdapterHelper helper, final Coupons item) {
                                helper.setText(R.id.tv_money,item.getMoney()+"");
                                helper.setText(R.id.tv_minMoney,"满"+item.getMinMoney()+"使用");
                                helper.setText(R.id.tv_title,item.getName());
                                helper.setText(R.id.tv_type,"限"+item.getType()+"使用");
                                helper.setText(R.id.tv_time,item.getStartTime()+"至"+item.getEndTime());
                                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String couponId = item.getCouponId();
                                        Intent intent1 = new Intent();
                                        intent1.putExtra("couponId",couponId);
                                        intent1.putExtra("money",item.getMoney());
                                        intent1.putExtra("minMoney",item.getMinMoney());
                                        setResult(RESULT_OK,intent1);
                                        finish();
                                    }
                                });
                            }
                        };
                        mNetstedListView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(String info) {
                    showToast(info);
                }
            });
        }else {
            Call<HttpBaseBean<List<Coupons>>> call = RetrofitFactory.getInstance(this).getCouponsList(businessType, fee);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<Coupons>>() {

                @Override
                public void onSuccess(List<Coupons> coupons, String info) {
                    if (coupons != null) {
                        list.addAll(coupons);
                        Adapter<Coupons> adapter = new Adapter<Coupons>(CouponsActivity.this,R.layout.coupons_item,list) {
                            @Override
                            protected void convert(AdapterHelper helper, final Coupons item) {
                                helper.setText(R.id.tv_money,item.getMoney()+"");
                                helper.setText(R.id.tv_minMoney,"满"+item.getMinMoney()+"使用");
                                helper.setText(R.id.tv_title,item.getName());
                                helper.setText(R.id.tv_type,"限"+item.getType()+"使用");
                                helper.setText(R.id.tv_time,item.getStartTime()+"至"+item.getEndTime());
                                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String couponId = item.getCouponId();
                                        Intent intent1 = new Intent();
                                        intent1.putExtra("couponId",couponId);
                                        intent1.putExtra("money",item.getMoney());
                                        intent1.putExtra("minMoney",item.getMinMoney());
                                        setResult(RESULT_OK,intent1);
                                        finish();
                                    }
                                });
                            }
                        };
                        mNetstedListView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(String info) {
                    showToast(info);
                }
            });
        }

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_back, R.id.ll_not_use})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_not_use:
                Intent intent1 = new Intent();
                intent1.putExtra("couponId","");
                setResult(RESULT_OK,intent1);
                finish();
                break;
        }
    }
}
