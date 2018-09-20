package com.ais.patient.activity.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ais.patient.R;
import com.ais.patient.activity.chat2.CallActivity;
import com.ais.patient.activity.chat2.Constant;
import com.ais.patient.activity.coupons.PrescriptionFragment;
import com.ais.patient.activity.login.LoginActivity;
import com.ais.patient.activity.mine.MineFragment;
import com.ais.patient.activity.seedoctor.SeeDoctorFragment;
import com.ais.patient.app.MyApplication;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.util.UserUtils;

import butterknife.BindView;
import io.agora.AgoraAPI;
import io.agora.AgoraAPIOnlySignal;
import io.agora.IAgoraAPI;
import io.agora.rtc.RtcEngine;

public class MainActivity extends MYBaseActivity {

    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.radioMain)
    RadioButton radioMain;
    @BindView(R.id.radioSeedoctor)
    RadioButton radioSeedoctor;
    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;
    private Fragment[] fragments = new Fragment[4];
    private String TAG = "MainActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragments[0] == null && fragment instanceof HomeFragment) {
            fragments[0] = (HomeFragment)fragment;
        }else if(fragments[1] == null && fragment instanceof SeeDoctorFragment) {
            fragments[1] = (SeeDoctorFragment)fragment;
        }else if(fragments[2] == null && fragment instanceof PrescriptionFragment) {
            fragments[2] = (PrescriptionFragment)fragment;
        }else if(fragments[3] == null && fragment instanceof MineFragment) {
            fragments[3] = (MineFragment)fragment;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        addFragment(1);
        radioSeedoctor.setChecked(true);
        if (intent.getIntExtra("jump",0)==1000){
            Intent intent1 = new Intent();
            intent1.setAction("com.ais.jump");
            sendBroadcast(intent1);
        }
    }

    private void loginVideo() {
        String appId = UserUtils.getAppId(this);
        String userId = UserUtils.getUserId(this);
        Log.e(TAG, "loginVideo--APPID"+appId );
        Log.e(TAG, "loginVideo--USERID"+userId );

        Constant.VIDEO_KEY_APP_KEY = appId;
        MyApplication.getInstance().getmAgoraAPI().login2(appId,userId , "_no_need_token", 0, "", 5, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public AgoraAPIOnlySignal getAgoraAPI() {
        return MyApplication.getInstance().getmAgoraAPI();
    }

    private void addCallback() {
       final AgoraAPIOnlySignal mAgoraAPI = getAgoraAPI();
        if (mAgoraAPI == null) {
            return;
        }

        mAgoraAPI.callbackSet(new AgoraAPI.CallBack() {

            @Override
            public void onLoginSuccess(int i, int i1) {
                Log.e(TAG, "onLoginSuccess: "+"视频问诊 onLoginSuccess " + i + "  " + i1 );
            }

            @Override
            public void onLogout(final int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (i == IAgoraAPI.ECODE_LOGOUT_E_KICKED) { // other login the account
                            showToast("账号已经在别的设备登录");
//                            ToastUtil.showToast(getActivity(), "Other login account, you are logout.");

                        } else if (i == IAgoraAPI.ECODE_LOGOUT_E_NET) { // net
                            showToast("网络不通畅");
//                            ToastUtil.showToast(getActivity(), "Logout for Network can not be.");
                        }
//                        finish();
                    }
                });

            }

            @Override
            public void onLoginFailed(final int i) {
               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (i == IAgoraAPI.ECODE_LOGIN_E_NET) { // 网络不通畅
                            showToast("网络不通畅-视频SDK");
                        }
                    }
                });
            }

            @Override
            public void onInviteReceived(final String channelID, final String account, int uid, String s2) { //call out other remote receiver
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, CallActivity.class);
//                        intent.putExtra("account", mMyAccount);
                        intent.putExtra("channelName", channelID);
                        intent.putExtra("subscriber", account);
                        intent.putExtra("type", Constant.CALL_IN);
                        startActivity(intent);
//                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });
            }

            @Override
            public void onInviteReceivedByPeer(final String channelID, final String account, int uid) {//call out other local receiver
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, CallActivity.class);
//                        intent.putExtra("account", mMyAccount);
                        intent.putExtra("channelName", channelID);
                        intent.putExtra("subscriber", account);
                        intent.putExtra("type", Constant.CALL_OUT);
                        startActivity(intent);
//                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });

            }

            @Override
            public void onInviteFailed(String channelID, String account, int uid, int i1, String s2) {
            }

            @Override
            public void onError(final String s, int i, final String s1) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (s.equals("query_user_status")) {
                            Toast.makeText(MainActivity.this, s1, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onQueryUserStatusResult(final String name, final String status) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (status.equals("1")) {
//                            channelName = mMyAccount + mSubscriber;
                        } else if (status.equals("0")) {
                            showToast("对方不在线");
                        }
                    }
                });
            }

        });
    }



    // 医生未读消息数需要经常刷新
    private BroadcastReceiver mRefreshDocInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            // 重新注册视频问诊回调的Action
            if (Constant.ACTION_VIDEO_CALL_BACK.equals(action)) {
                addCallback();
                return;
            }
        }
    };

    @Override
    protected void initViews(Bundle savedInstanceState) {
        MyApplication.addDestoryActivity(this,"MainActivity");
        radioMain.setChecked(true);
        addFragment(0);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_VIDEO_CALL_BACK);
        LocalBroadcastManager.getInstance(this).registerReceiver(mRefreshDocInfoReceiver, intentFilter);
        loginVideo();
        addCallback();
    }


    @Override
    protected void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                int index = Integer.parseInt(radioGroup.findViewById(checkedId).getTag().toString());
                addFragment(index);
            }
        });
    }

    int lastIndex = -1;

    private void addFragment(int index) {
        if (lastIndex == index) {
            return;
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        if (lastIndex > -1) {
            transaction.hide(fragments[lastIndex]);
        }
        if (fragments[index] == null) {
            switch (index) {
                case 0:
                    fragments[index] = new HomeFragment();
                    break;
                case 1:
                    fragments[index] = new SeeDoctorFragment();
                    break;
                case 2:
                    fragments[index] = new PrescriptionFragment();
                    break;
                case 3:
                    fragments[index] = new MineFragment();
                    break;
            }
            transaction.add(R.id.framelayout, fragments[index]);
        } else {
            transaction.show(fragments[index]);
        }
        transaction.commit();
        lastIndex = index;
    }

    @Override
    protected void initData() {

    }


    // 第一次按下返回键的事件
    private long firstPressedTime;

    // System.currentTimeMillis() 当前系统的时间
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstPressedTime < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            firstPressedTime = System.currentTimeMillis();
        }
    }

}
