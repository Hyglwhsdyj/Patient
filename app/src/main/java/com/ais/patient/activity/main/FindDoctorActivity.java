package com.ais.patient.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.adapter.MyGridViewAdapter2;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.FindDoctor;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.MainAllInfo;
import com.ais.patient.been.Reservation;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.CircleTransform;
import com.ais.patient.widget.FlowGroupView;
import com.ais.patient.widget.UploadPopupwindow;
import com.ais.patient.widget.recycleview.DefineBAGRefreshWithLoadView;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;

public class FindDoctorActivity extends MYBaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_complex)
    TextView tvComplex;
    @BindView(R.id.tv_appraise)
    TextView tvAppraise;
    @BindView(R.id.tv_choose)
    TextView tvChoose;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.mRecycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.define_sliding_bga)
    BGARefreshLayout mBGARefreshLayout;
    private String diseaseId;   //疾病id
    private String departId;   // 科室id
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView;
    private int pageNum=1;
    private int pageSize=10;
    List<FindDoctor> list = new ArrayList<>();
    private Call<HttpBaseBean<List<FindDoctor>>> call;
    private FlowGroupView mFlowGroupView;
    private String sortType="complex";
    private String searchWord="";
    private UploadPopupwindow popupwindow;
    private MyGridViewAdapter2 adapter2;
    private String departID;
    private String diseaseID;
    private String name;
    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_doctor;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        tvTitle.setText("找专家");
        searchWord = intent.getStringExtra("disease");
        diseaseId = intent.getStringExtra("diseaseId");
        departId = intent.getStringExtra("departId");
        name = intent.getStringExtra("name");
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(diseaseId)){
            tvName.setText("以下医生擅长治疗["+ name +"]");
        }else {
            tvName.setVisibility(View.GONE);
        }
        initView();
        setBgaRefreshLayout();
        setData();

        showPopueWindow();
    }

    private void initView() {
        //设置刷新和加载监听
        mBGARefreshLayout.setDelegate(this);
    }
    /**
     * 设置 BGARefreshLayout刷新和加载
     *
     */
    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(this , true , true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载更多");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }



    /**
     * 请求网络数据
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    list.clear();
                    setData();
                    if (mBGARefreshLayout!=null){
                        mBGARefreshLayout.endRefreshing();
                    }
                    break;
                case 1:
                    setData();
                    if (mBGARefreshLayout!=null){
                        mBGARefreshLayout.endLoadingMore();
                    }
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 加载数据
     *
     */
    private void setData() {
        if (!TextUtils.isEmpty(diseaseId)){
            type = 1;
            call = RetrofitFactory.getInstance(this).getDiseaseDoctorList2(pageNum, pageSize,sortType,diseaseId,searchWord);
        }else if (!TextUtils.isEmpty(departId)){
            call = RetrofitFactory.getInstance(this).getDepartDoctorList2(pageNum, pageSize,sortType,departId,searchWord);
            type = 2;
        }else {
            call = RetrofitFactory.getInstance(this).getDiseaseDoctorList(pageNum, pageSize,sortType,searchWord);
        }
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<FindDoctor>>() {

            @Override
            public void onSuccess(List<FindDoctor> findDoctors, String info) {
                if (findDoctors!=null){
                    list.addAll(findDoctors);

                    RecyclerAdapter<FindDoctor> adapter = new RecyclerAdapter<FindDoctor>(FindDoctorActivity.this,R.layout.find_coctor_item,list) {
                        @Override
                        protected void convert(RecyclerAdapterHelper helper, final FindDoctor item) {
                            ImageView ivIcon = (ImageView) helper.getItemView().findViewById(R.id.iv_icon);
                            Picasso.with(context).load(item.getImage()).transform(new CircleTransform()).into(ivIcon);
                            helper.setText(R.id.tv_name,item.getName());
                            helper.setText(R.id.tv_title,item.getTitles());
                            helper.setText(R.id.tv_depart,item.getDepart());
                            helper.setText(R.id.tv_medicalInstitutions,"医院机构："+item.getMedicalInstitutions());
                            helper.setText(R.id.tv_fee,item.getFee()+"元/次");
                            helper.setText(R.id.tv_payNum,item.getPayNum()+"人付款");
                            helper.setText(R.id.tv_appraiseNum,item.getAppraiseNum()+"人评价");
                            final List<String> diseaseExpertise = item.getDiseaseExpertise();
                            mFlowGroupView = (FlowGroupView) helper.getItemView().findViewById(R.id.mFlowGroupView);
                            if (diseaseExpertise!=null && diseaseExpertise.size()>0){
                                for (int i = 0; i < diseaseExpertise.size(); i++) {
                                    addTextView(diseaseExpertise.get(i));
                                }
                            }

                            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String id = item.getId();
                                    Intent intent = new Intent(context,DoctorInfomationActivity.class);
                                    intent.putExtra("id",id);
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    mRecyclerView.setAdapter(adapter);
                }else {
                    if (pageNum!=1){
                        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("没有更多了");
                    }
                }
                if (isShowingProgressDialog()){
                    dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(String info) {
                showToast(info);
                if (isShowingProgressDialog()){
                    dismissProgressDialog();
                }
            }
        });
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


    /** 刷新 */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        pageNum = 1;
        handler.sendEmptyMessageDelayed(0 , 500);
    }
    /** 加载 */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pageNum++;
        handler.sendEmptyMessageDelayed(1 , 500);
        return true;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_back, R.id.tv_search,R.id.tv_complex, R.id.tv_appraise, R.id.tv_choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_complex:
                showProgressDialog();
                sortType="complex";
                pageNum = 1;
                handler.sendEmptyMessageDelayed(0 , 1000);
                break;
            case R.id.tv_search:
                searchWord = etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(searchWord)){
                    pageNum = 1;
                    showProgressDialog();
                    handler.sendEmptyMessageDelayed(0 , 0);
                }
                break;
            case R.id.tv_appraise:
                showProgressDialog();
                sortType="appraise";
                pageNum = 1;
                handler.sendEmptyMessageDelayed(0 , 1000);
                break;
            case R.id.tv_choose:
                popupwindow.showAsDropDown(tvChoose);
                break;
        }
    }

    List<MainAllInfo.DiseaesBean> diseaesList = new ArrayList<>();
    List<MainAllInfo.DepartsBean> departsList = new ArrayList<>();
    private void showPopueWindow() {
        popupwindow = new UploadPopupwindow(this, R.layout.popupwindow_choose_doctor, R.id.ll_type, RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupwindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupwindow.setAnimationStyle(R.style.style_pop_animation);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        View view = popupwindow.getContentView();
        final GridView mGridView = (GridView) view.findViewById(R.id.mGridView);
        final TextView tvOK = (TextView) view.findViewById(R.id.tv_ok);
        final TextView tvReset = (TextView) view.findViewById(R.id.tv_reset);
            adapter2 = new MyGridViewAdapter2(this,diseaesList,departsList,type);
            mGridView.setAdapter(adapter2);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    adapter2.setPosition(position);
                    if (type==1){
                        diseaseID = diseaesList.get(position).getDiseaseId();
                        name = diseaesList.get(position).getDiseaseName();
                    }else if (type==2){
                        departID= departsList.get(position).getDepartId();
                        //name = diseaesList.get(position).getDiseaseName();
                    }
                }
            });
            tvOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type==1){
                        diseaseId =diseaseID;
                        tvName.setText("以下医生擅长治疗["+name+"]");
                        pageNum = 1;
                        handler.sendEmptyMessageDelayed(0 , 500);
                        popupwindow.dismiss();
                    }else if (type==2){
                        departId = departID;
                        pageNum = 1;
                        handler.sendEmptyMessageDelayed(0 , 500);
                        popupwindow.dismiss();
                    }
                }
            });
            tvReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter2.setPosition(-1);
                }
            });

        /**
         * 加载数据
         */
        if (!TextUtils.isEmpty(diseaseId)){
            Call<HttpBaseBean<List<MainAllInfo.DiseaesBean>>> call = RetrofitFactory.getInstance(this).getNotGoodList();
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<MainAllInfo.DiseaesBean>>() {

                @Override
                public void onSuccess(List<MainAllInfo.DiseaesBean> diseaesBeans, String info) {
                    if (diseaesBeans!=null && diseaesBeans.size()>0){
                        diseaesList.addAll(diseaesBeans);
                        adapter2.notifyDataSetChanged();
                        /*final Adapter<MainAllInfo.DiseaesBean> adapter = new Adapter<MainAllInfo.DiseaesBean>(FindDoctorActivity.this,R.layout.choose_item, diseaesBeans) {
                            @Override
                            protected void convert(final AdapterHelper helper, final MainAllInfo.DiseaesBean item) {
                                helper.setText(R.id.tv_name,item.getDiseaseName());
                                if (!TextUtils.isEmpty(item.getDiseaseName()) && !TextUtils.isEmpty(item.getDiseaseId())){
                                    tvName.setText("以下医生擅长治疗["+item.getDiseaseName()+"]");
                                }else {
                                    tvName.setVisibility(View.GONE);
                                }
                                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        diseaseId = item.getDiseaseId();
                                        tvName.setText("以下医生擅长治疗["+item.getDiseaseName()+"]");
                                        pageNum = 1;
                                        handler.sendEmptyMessageDelayed(0 , 500);
                                        popupwindow.dismiss();
                                    }
                                });
                            }

                        };
                        mGridView.setAdapter(adapter);*/
                    }
                }

                @Override
                public void onFailure(String info) {
                    showToast(info);
                }
            });
        }else if (!TextUtils.isEmpty(departId)){
            Call<HttpBaseBean<List<MainAllInfo.DepartsBean>>> call = RetrofitFactory.getInstance(this).getDepartmentList();
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<MainAllInfo.DepartsBean>>() {

                @Override
                public void onSuccess(List<MainAllInfo.DepartsBean> departsBeans, String info) {
                    if (departsBeans!=null && departsBeans.size()>0){
                        departsList.addAll(departsBeans);
                        adapter2.notifyDataSetChanged();
                        /*Adapter<MainAllInfo.DepartsBean> adapter = new Adapter<MainAllInfo.DepartsBean>(FindDoctorActivity.this,R.layout.choose_item,departsBeans) {
                            @Override
                            protected void convert(AdapterHelper helper, final MainAllInfo.DepartsBean item) {
                                helper.setText(R.id.tv_name,item.getDepartName());
                                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        departId = item.getDepartId();
                                        pageNum = 1;
                                        handler.sendEmptyMessageDelayed(0 , 500);
                                        popupwindow.dismiss();
                                    }
                                });
                            }
                        };
                        mGridView.setAdapter(adapter);*/
                    }
                }

                @Override
                public void onFailure(String info) {

                }
            });
        }
    }
}
