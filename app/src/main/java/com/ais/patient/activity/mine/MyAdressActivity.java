package com.ais.patient.activity.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.adapter.MyAdressAdapter;
import com.ais.patient.base.BaseActivity;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.MultiItemView;
import com.ais.patient.been.MyAdressResponse;
import com.ais.patient.been.MyConcernResponse;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.recycleview.DefineBAGRefreshWithLoadView;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;

public class MyAdressActivity extends MYBaseActivity {



    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;

    private Context context;


    private List<MyAdressResponse> datas=new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_adress;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("管理收货人地址");
        context=MyAdressActivity.this;

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        getDatas();
    }

    /**
     * 获取地址列表
     */
    private void getDatas() {
        Call<HttpBaseBean<List<MyAdressResponse>>> call = RetrofitFactory.getInstance(context).getMyAdressList();
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<MyAdressResponse>>() {

            @Override
            public void onSuccess(List<MyAdressResponse> reservations, final String info) {
                datas.clear();
                for(MyAdressResponse response:reservations){
                    datas.add(response);
                }
                RecyclerAdapter<MyAdressResponse> adapter = new RecyclerAdapter<MyAdressResponse>(context,R.layout.item_my_adress_layout,datas) {
                    @Override
                    protected void convert(RecyclerAdapterHelper helper, final MyAdressResponse item) {
                        helper.setText(R.id.tv_name,item.getName());
                        helper.setText(R.id.tv_phone,item.getPhoneNumber());
                        helper.setText(R.id.tv_adress,item.getAddress());
                        if ("Y".equals(item.getPreferred())) {
                            helper.setImageResource(R.id.iv_default,R.drawable.select_yellow);
                        }else {
                            helper.setImageResource(R.id.iv_default,R.drawable.unselect_gray);
                        }
                        helper.setOnClickListener(R.id.iv_default, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String addressId = item.getAddressId();
                                Call<HttpBaseBean<Object>> call1 = RetrofitFactory.getInstance(MyAdressActivity.this).setMyAdressMand(addressId);
                                new BaseCallback(call1).handleResponse(new BaseCallback.ResponseListener() {
                                    @Override
                                    public void onSuccess(Object o, String info) {
                                        showToast("设置成功");
                                        getDatas();
                                    }

                                    @Override
                                    public void onFailure(String info) {

                                    }
                                });
                            }
                        });
                        helper.setOnClickListener(R.id.tv_edit, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MyAdressActivity.this,AddAddressActivity.class);
                                intent.putExtra("id",item.getAddressId());
                                startActivityForResult(intent,1);
                            }
                        });
                        helper.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyAdressActivity.this);
                                builder.setTitle("提示")
                                        .setMessage("确定删除该地址")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialog, int which) {
                                                Call<HttpBaseBean<Object>> address = RetrofitFactory.getInstance(MyAdressActivity.this).deleteAddress(item.getAddressId());
                                                new BaseCallback(address).handleResponse(new BaseCallback.ResponseListener() {
                                                    @Override
                                                    public void onSuccess(Object o, String info) {
                                                        showToast("删除成功");
                                                        getDatas();
                                                    }

                                                    @Override
                                                    public void onFailure(String info) {
                                                        showToast(info);
                                                    }
                                                });
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.create().show();
                            }
                        });
                        helper.getItemView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.putExtra("address",item);
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        });
                    }
                };
                mRecycleView.setAdapter(adapter);


            }

            @Override
            public void onFailure(String info) {
                ToastUtils.show(context, info);
            }
        });
    }

    @OnClick({R.id.tv_back,R.id.tv_add})
    public void setOnClick(View view){
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_add:
                startActivityForResult(new Intent(this,AddAddressActivity.class),0);
                break;
        }
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            getDatas();
        }
    }

}
