package com.ais.patient.activity.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.login.LoginActivity;
import com.ais.patient.activity.main.MessageActivity;
import com.ais.patient.base.BaseFragment;
import com.ais.patient.been.Customer;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.UsrInfomation;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.util.UserUtils;
import com.ais.patient.widget.CircleTransform;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/7/29 0029.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.iv_head_icon)
    ImageView ivHeadIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    Unbinder unbinder;
    @BindView(R.id.rl_right)
    RelativeLayout rlRight;
    Unbinder unbinder1;
    Unbinder unbinder2;
    private Context context;
    private UsrInfomation useInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews(View view) {
        tvTitle.setText("我的");
        tvBack.setVisibility(View.GONE);
        rlRight.setVisibility(View.GONE);
        context = getBaseActivity();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        loadInfo();
        loadMessageCount();
    }

    private void loadMessageCount() {
        Call<HttpBaseBean<Integer>> call = RetrofitFactory.getInstance(context).getMessageCount();
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<Integer>() {

            @Override
            public void onSuccess(Integer integer, String info) {
                if (integer != null) {
                    int count = integer;
                    tvMsg.setText(count + "");
                }
            }

            @Override
            public void onFailure(String info) {
                ToastUtils.show(context, info);
            }
        });
    }

    private void loadInfo() {
        final Call<HttpBaseBean<UsrInfomation>> userInfo = RetrofitFactory.getInstance(context).getUserInfo();
        new BaseCallback(userInfo).handleResponse(new BaseCallback.ResponseListener<UsrInfomation>() {
            @Override
            public void onSuccess(UsrInfomation usrInfomation, String info) {
                if (usrInfomation != null) {
                    String headImage = usrInfomation.getHeadImage();
                    if (!TextUtils.isEmpty(headImage)) {
                        Picasso.with(context).load(headImage).transform(new CircleTransform()).into(ivHeadIcon);
                    }
                    String name = usrInfomation.getName();
                    if (!TextUtils.isEmpty(name)) {
                        tvName.setText(name);
                    }

                    String phoneNumber = usrInfomation.getPhoneNumber();
                    if (!TextUtils.isEmpty(phoneNumber)) {
                        tvPhone.setText(phoneNumber);
                    }
                }
                useInfo = usrInfomation;
            }

            @Override
            public void onFailure(String info) {

            }
        });
    }


    @OnClick({R.id.rl_right, R.id.iv_head_icon, R.id.tv_vip,R.id.tv_edit, R.id.rl_0, R.id.rl_1, R.id.rl_2, R.id.rl_3, R.id.rl_4, R.id.rl_5, R.id.rl_6, R.id.rl_7,R.id.rl_11, R.id.rl_8, R.id.rl_9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_right:
                startActivity(new Intent(context, MessageActivity.class));
                break;
            case R.id.iv_head_icon:
                break;
            case R.id.tv_vip:
                break;
            case R.id.tv_edit:
                Intent intent1 = new Intent(context, RemakeUserInfoActivity.class);
                if (useInfo != null) {
                    intent1.putExtra("useInfo", useInfo);
                }
                startActivityForResult(intent1, 0);
                break;
            case R.id.rl_0:
                startActivity(new Intent(new Intent(context, MyAccountActivity.class)));
                break;
            case R.id.rl_1://我的处方单
                if (TextUtils.isEmpty(UserUtils.getUserToken(context))) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                startActivity(new Intent(context, MyOrdonnanceActivity.class));
                break;
            case R.id.rl_2:
                if (TextUtils.isEmpty(UserUtils.getUserToken(context))) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                startActivity(new Intent(context, MyAdressActivity.class));
                break;
            case R.id.rl_3://我的关注
                if (TextUtils.isEmpty(UserUtils.getUserToken(context))) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                startActivity(new Intent(context, MyConcernActivity.class));
                break;
            case R.id.rl_4:
                if (TextUtils.isEmpty(UserUtils.getUserToken(context))) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                startActivity(new Intent(context, MyReservationActivity.class));
                break;
            case R.id.rl_5:
                startActivity(new Intent(context, MyCouponsActivity.class));
                break;
            case R.id.rl_6:
                startActivity(new Intent(context, MyAppraiseActivity.class));
                break;
            case R.id.rl_7://设置
                if (TextUtils.isEmpty(UserUtils.getUserToken(context))) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                startActivity(new Intent(context, SetActivity.class));
                break;

            case R.id.rl_11:
                startActivity(new Intent(context,MyPatientListActivity.class));
                break;
            case R.id.rl_8:
                Call<HttpBaseBean<Customer>> call = RetrofitFactory.getInstance(context).getServiceId();
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<Customer>() {


                    @Override
                    public void onSuccess(Customer customer, String info) {
                        if (customer!=null){

                            final String Im_customer_accid = customer.getIm_customer_accid();
                            LoginInfo loginInfo = new LoginInfo(customer.getIm_accid(), customer.getIm_token());// config...
                            NIMClient.getService(AuthService.class).login(loginInfo).setCallback(new RequestCallback<LoginInfo>() {
                                @Override
                                public void onSuccess(LoginInfo param) {
                                        /*NimUIKit.loginSuccess(param.getAccount());
                                        NimUIKit.startP2PSession(context,Im_customer_accid);*/
                                    P2PMessageActivity.start(context,Im_customer_accid,null,null,"医生","","");
                                }

                                @Override
                                public void onFailed(int code) {
                                    ToastUtils.show(context,"登录失败");
                                }

                                @Override
                                public void onException(Throwable exception) {
                                    ToastUtils.show(context,"登录异常");
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(String info) {

                    }
                });
                break;
            case R.id.rl_9:
                startActivity(new Intent(context,RecommendUsActivity.class));
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 0:
                    loadInfo();
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder2 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder2.unbind();
    }

}
