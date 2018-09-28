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
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class NotGoodListActivity extends MYBaseActivity {

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
        tvTitle.setText("按疾病找专家");
        mGridView.setNumColumns(3);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        Call<HttpBaseBean<List<MainAllInfo.DiseaesBean>>> call = RetrofitFactory.getInstance(this).getNotGoodList();
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<MainAllInfo.DiseaesBean>>() {

            @Override
            public void onSuccess(List<MainAllInfo.DiseaesBean> diseaesBeans, String info) {
                if (diseaesBeans != null && diseaesBeans.size() > 0) {
                    Adapter<MainAllInfo.DiseaesBean> adapter = new Adapter<MainAllInfo.DiseaesBean>(NotGoodListActivity.this, R.layout.homeclass_item, diseaesBeans) {
                        @Override
                        protected void convert(AdapterHelper helper, final MainAllInfo.DiseaesBean item) {
                            ImageView ivICon = (ImageView) helper.getItemView().findViewById(R.id.iv_icon);
                            helper.setText(R.id.tv_name, item.getDiseaseName());
                            Picasso.with(context).load(item.getDiseasePic()).into(ivICon);

                            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(context, FindDoctorActivity.class);
                                    intent.putExtra("diseaseId", item.getDiseaseId());
                                    intent.putExtra("name", item.getDiseaseName());
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
                showToast(info);
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
                    intent.putExtra("isDisease",true);
                    startActivity(intent);
                }
                break;
        }
    }
}
