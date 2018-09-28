package com.ais.patient.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.MainAllInfo;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class DepartmentListActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mNetstedGridView)
    GridView mGridView;
    @BindView(R.id.et_search)
    EditText etSearch;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_not_good_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("按科室找专家");
        mGridView.setNumColumns(4);
        etSearch.setHint("请输入科室名称");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        Call<HttpBaseBean<List<MainAllInfo.DepartsBean>>> call = RetrofitFactory.getInstance(this).getDepartmentList();
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<MainAllInfo.DepartsBean>>() {

            @Override
            public void onSuccess(List<MainAllInfo.DepartsBean> departsBeans, String info) {
                if (departsBeans!=null && departsBeans.size()>0){
                    Adapter<MainAllInfo.DepartsBean> adapter = new Adapter<MainAllInfo.DepartsBean>(DepartmentListActivity.this,R.layout.homeclass_item,departsBeans) {
                        @Override
                        protected void convert(AdapterHelper helper, final MainAllInfo.DepartsBean item) {
                            helper.setVisible(R.id.tv_1,false);
                            helper.setVisible(R.id.tv_2,false);
                            ImageView ivICon = (ImageView) helper.getItemView().findViewById(R.id.iv_icon);
                            helper.setText(R.id.tv_name,item.getDepartName());
                            Picasso.with(context).load(item.getDepartPic()).into(ivICon);
                            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(context, FindDoctorActivity.class);
                                    intent.putExtra("departId",item.getDepartId());
                                    intent.putExtra("name",item.getDepartName());
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    mGridView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(String info) {

            }
        });
    }



    @OnClick({R.id.tv_back, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_ok:
                String search = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(search)){
                    showToast("请输入搜索内容");
                }else {
                    Intent intent = new Intent(this,FindDoctorActivity.class);
                    intent.putExtra("disease",search);
                    intent.putExtra("isDisease",false);
                    startActivity(intent);
                }
                break;
        }
    }
}
