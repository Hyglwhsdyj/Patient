package com.ais.patient.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.CheckStatus;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MakeSurePayActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String recordId;
    private String advertUrl,doctorId,doctorName;
    private Context context;
    private int type;

    int count = 0;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_make_sure_pay;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        tvTitle.setText("支付成功");
        advertUrl = getIntent().getStringExtra("AdvertUrl");
        recordId = getIntent().getStringExtra("recordId");
        doctorId = getIntent().getStringExtra("doctorId");
        doctorName = getIntent().getStringExtra("doctorName");
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                count++;
                handler.sendEmptyMessage(0x123);
            }
        };
        timer.schedule(timerTask,0,10000);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x123:
                    refreshData();
                    break;
            }
        }
    };

    private void refreshData() {
        Call<HttpBaseBean<CheckStatus>> call = RetrofitFactory.getInstance(this).CheckExpressStatus(recordId);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<CheckStatus>() {

            @Override
            public void onSuccess(CheckStatus checkStatus, String info) {
                /**
             *      1 医生接诊
                     2 医生拒绝接诊
                     3 医生未确认接诊，继续轮询请求接口
                     4 到达指定等待时间，医生未确认接诊，弹出选择弹窗
                     5 保持选择弹窗，用户选择时间
                     6 弹窗选择时间到了，弹窗关闭，页面刷新
                     7 用户选择继续等待选项，时间未到，继续等待
                     8 用户选择继续等待选项，时间已到，医生未接诊，订单取消
                 */
                int status = checkStatus.getStatus();
                if (status==3){
                    //refreshData();
                }else if (status==1){
                    showToast("医生已接诊");
                    Intent intent  = new Intent(MakeSurePayActivity.this, PatientInfomationActivity.class);
                    intent.putExtra("doctorId",doctorId);
                    intent.putExtra("recordId",recordId);
                    if (isShowingProgressDialog()){
                        dismissProgressDialog();
                    }
                    startActivity(intent);
                    finish();
                }else if (status==2){
                    showToast("医生拒绝接诊");
                    finish();
                }else if (status==4){
                    timer.cancel();
                    timer = null;
                    timerTask.cancel();
                    timerTask = null;
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_time_out, null, false);
                    TextView tvMsg = (TextView) inflate.findViewById(R.id.tv_msg);
                    builder.setView(inflate);
                    final AlertDialog dialog = builder.create();

                    TextView tvOK = (TextView) inflate.findViewById(R.id.tv_ok);
                    RadioGroup mRadioGroup = (RadioGroup) inflate.findViewById(R.id.mRadioGroup);
                    RadioButton btn3 = (RadioButton) inflate.findViewById(R.id.btn3);
                    tvMsg.setText(doctorName+"暂未接诊，请选择");
                    btn3.setChecked(true);
                    mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if (checkedId==R.id.btn1){
                                type = 1;
                            }else if (checkedId==R.id.btn2){
                                type=2;
                            }else if (checkedId==R.id.btn3){
                                type=3;
                            }
                        }
                    });
                    tvOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (type==1){
                                timer = new Timer();
                                timerTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        count++;
                                        handler.sendEmptyMessage(0x123);
                                    }
                                };
                                timer.schedule(timerTask,0,10000);
                                dialog.dismiss();
                            }else if (type==2){
                                dialog.dismiss();
                                showToast("加急问诊取消");
                                finish();
                            }else if (type==3){
                                dialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_choose_other, null, false);
                                builder.setView(inflate);
                                final AlertDialog dialog1 = builder.create();
                                TextView tv_yes = (TextView) inflate.findViewById(R.id.tv_yes);
                                TextView tv_no = (TextView) inflate.findViewById(R.id.tv_no);
                                tv_no.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog1.dismiss();
                                    }
                                });
                                tv_yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context,OtherDoctorActivity.class);
                                        intent.putExtra("doctorId",doctorId);
                                        startActivity(intent);
                                        dialog1.dismiss();
                                    }
                                });
                                dialog1.show();
                            }
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }else if (status==7){

                }else if (status==8){
                    showToast("订单取消,已支付费用将原路返回您的支付账户");
                    finish();
                }

            }

            @Override
            public void onFailure(String info) {
                showToast(info);
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
