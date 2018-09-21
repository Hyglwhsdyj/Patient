package com.ais.patient.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.main.AddPatientActivity;
import com.ais.patient.activity.main.DoctorInfomationActivity;
import com.ais.patient.adapter.ChatOnlineAdapter;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.ChatOnLineList;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.ImInfo;
import com.ais.patient.been.PatientInfo;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.util.UserUtils;
import com.ais.patient.widget.CustomDatePicker;
import com.ais.patient.widget.EmptyView;
import com.ais.patient.widget.MyScrollView;
import com.ais.patient.widget.VpSwipeRefreshLayout;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MyPatientDetailActivity extends MYBaseActivity {

    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_bl)
    TextView tvBl;
    @BindView(R.id.tv_other)
    TextView tvOther;
    @BindView(R.id.tv_remake_patient)
    TextView tvRemake;
    @BindView(R.id.tv_patient_info)
    TextView tvPatientInfo;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.mNestedScrollView)
    MyScrollView mScrollView;

    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.rdb_1)
    RadioButton rdb1;
    @BindView(R.id.mEmptyView)
    EmptyView mEmptyView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.rdb_2)
    RadioButton rdb2;
    @BindView(R.id.rdb_3)
    RadioButton rdb3;
    @BindView(R.id.rdb_4)
    RadioButton rdb4;
    @BindView(R.id.tv_search)
    TextView tvSearch;

    private String id;
    private Context context;
    private int customTime = 0;
    private String startTime;
    private String endTime;
    private ChatOnlineAdapter adapter;
    private CustomDatePicker customDatePicker1;
    private CustomDatePicker customDatePicker2;
    private int pageNum=1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_patient_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        mEmptyView.setVisibility(View.VISIBLE);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setNestedScrollingEnabled(false);

        rdb1.setChecked(true);
        customTime = 0;
        initDatePicker();
        id = getIntent().getStringExtra("id");
        toShowPatient(id);
        setRecyclerCommadapter();
    }

    private void toShowPatient(String id) {
        String baseUrl = "/api/patient/" + id;
        Call<HttpBaseBean<PatientInfo>> call = RetrofitFactory.getInstance(this).getPatientInfo(baseUrl);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<PatientInfo>() {

            @Override
            public void onSuccess(final PatientInfo patientInfo, String info) {
                if (patientInfo != null) {
                    String patientSex = patientInfo.getSex();
                    String name = patientInfo.getName();
                    String patientAge = patientInfo.getAge();
                    tvTitle.setText(name+"的健康档案");

                    tvPatientInfo.setText("患者：" + name + "  " + patientSex + "  " + patientAge);

                    tvHeight.setText(patientInfo.getHeight() + "cm");
                    tvWeight.setText(patientInfo.getWeight() + "Kg");
                    tvPhone.setText(patientInfo.getPhoneNumber());
                    tvAddress.setText(patientInfo.getAddress());
                    String medicalHistory = patientInfo.getMedicalHistory();
                    if (TextUtils.isEmpty(medicalHistory)) {
                        tvBl.setText("无");
                    } else {
                        tvBl.setText(medicalHistory);
                    }
                    String remarks = patientInfo.getRemarks();
                    if (TextUtils.isEmpty(remarks)) {
                        tvOther.setText("无");
                    } else {
                        tvOther.setText(remarks);
                    }
                    tvRemake.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, AddPatientActivity.class);
                            intent.putExtra("patientInfo", patientInfo);
                            startActivityForResult(intent, 4);
                        }
                    });
                    mEmptyView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String info) {
                showToast(info);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 4:
                    toShowPatient(id);
                    break;
            }
        }
    }

    @Override
    protected void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdb_1) {
                    customTime = 0;
                } else if (checkedId == R.id.rdb_2) {
                    customTime = 1;
                } else if (checkedId == R.id.rdb_3) {
                    customTime = 2;
                } else if (checkedId == R.id.rdb_4) {
                    customTime = 3;
                }
            }
        });


        /**
         * 上拉加载
         */
        mScrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                final int myScrollViewHeight = mScrollView.getHeight();
                int height = mScrollView.getChildAt(0).getHeight();

                if (scrollY == height - myScrollViewHeight) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pageNum++;
                            setData();
                        }
                    }, 500);

                }
            }
        });
    }

    List<ChatOnLineList> list = new ArrayList<>();

    @Override
    protected void initData() {

    }

    /**
     * 数据填充
     */
    private void setRecyclerCommadapter() {
        adapter = new ChatOnlineAdapter(context, list);
        mRecycleView.setAdapter(adapter);
        adapter.setOnIntemClickLister(new ChatOnlineAdapter.onIntemClickLister() {
            @Override
            public void toPay(String doctorId, String recordId) {

            }

            @Override
            public void cancel(String id) {

            }

            @Override
            public void onItenClick(final String doctorId, final String recordId, String explainState) {
                if (explainState.equals("2")) {
                    //跳医生主页
                    Intent intent = new Intent(context, DoctorInfomationActivity.class);
                    intent.putExtra("id", doctorId);
                    startActivity(intent);
                } else if (explainState.equals("1")) {
                    /**
                     * 跳问诊聊天详情
                     */
                    UserUtils.saveChatOnLineOrMeeting(context, 100);
                    UserUtils.saveChatOnLineDOCTORID(context, doctorId);
                    UserUtils.saveChatOnLineRECORDID(context, recordId);
                    final Call<HttpBaseBean<ImInfo>> call = RetrofitFactory.getInstance(context).getImInfo(recordId);
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ImInfo>() {

                        @Override
                        public void onSuccess(ImInfo imInfo, String info) {
                            if (imInfo != null) {
                                final String im_doctor_accid = imInfo.getIm_doctor_accid();
                                LoginInfo loginInfo = new LoginInfo(imInfo.getIm_accid(), imInfo.getIm_token());// config...
                                NIMClient.getService(AuthService.class).login(loginInfo).setCallback(new RequestCallback<LoginInfo>() {
                                    @Override
                                    public void onSuccess(LoginInfo param) {
                                        /*NimUIKit.loginSuccess(param.getAccount());
                                        NimUIKit.startP2PSession(context,im_doctor_accid);*/
                                        P2PMessageActivity.start(context, im_doctor_accid, null, null, "医生", recordId, doctorId);
                                    }

                                    @Override
                                    public void onFailed(int code) {
                                        ToastUtils.show(context, "登录失败");
                                    }

                                    @Override
                                    public void onException(Throwable exception) {
                                        ToastUtils.show(context, "登录异常");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(String info) {

                        }
                    });
                }

            }

        });
    }


    @OnClick({R.id.tv_back,R.id.tv_start_time, R.id.tv_end_time, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_start_time:
                customDatePicker1.show(tvStartTime.getText().toString());
                break;
            case R.id.tv_end_time:
                // 日期格式为yyyy-MM-dd HH:mm
                customDatePicker2.show(tvEndTime.getText().toString());
                break;
            case R.id.tv_search:
                list.clear();
                adapter.notifyDataSetChanged();
                setData();
                break;
        }
    }

    private void setData() {
        if (customTime == 0) {
            if (TextUtils.isEmpty(startTime)) {
                showToast("请选择开始时间");
            }else if (TextUtils.isEmpty(endTime)) {
                showToast("请选择结束时间");
            }else {
                Call<HttpBaseBean<List<ChatOnLineList>>> call = RetrofitFactory.getInstance(this).gteHealthRecord(pageNum, 10, id, customTime, startTime, endTime);
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<ChatOnLineList>>() {

                    @Override
                    public void onSuccess(List<ChatOnLineList> chatOnLineLists, String info) {
                        if (chatOnLineLists.size()<1){
                            showToast("没有记录了");
                        }
                        list.addAll(chatOnLineLists);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(String info) {
                        ToastUtils.show(context, info);

                    }
                });
            }
        }else {
            Call<HttpBaseBean<List<ChatOnLineList>>> call = RetrofitFactory.getInstance(this).gteHealthRecord(pageNum, 10, id, customTime, startTime, endTime);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<ChatOnLineList>>() {


                @Override
                public void onSuccess(List<ChatOnLineList> chatOnLineLists, String info) {
                    if (chatOnLineLists.size()>0){
                        list.addAll(chatOnLineLists);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(String info) {
                    ToastUtils.show(context, info);
                }
            });
        }

    }



    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String now = sdf.format(new Date());
        tvStartTime.setText(now);
        tvEndTime.setText(now);

        // 回调接口，获得选中的时间
        // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvStartTime.setText(time);
                startTime= time+":00";
            }
        }, "2018-08-01 00:00:00", "2030-12-31 00:00:00");
        customDatePicker1.showSpecificTime(true); // 不显示时和分
        customDatePicker1.setIsLoop(true); // 不允许循环滚动

        // 回调接口，获得选中的时间
// 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvEndTime.setText(time);
                endTime = time+":";
            }
        }, "2018-08-01 00:00:00", "2030-12-31 00:00");
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }
}
