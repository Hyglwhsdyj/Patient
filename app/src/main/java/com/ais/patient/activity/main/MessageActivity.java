package com.ais.patient.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.chat2.RequstIMChatUtil;
import com.ais.patient.activity.mine.MyAppraiseActivity;
import com.ais.patient.activity.mine.MyConcernActivity;
import com.ais.patient.activity.mine.MyCouponsActivity;
import com.ais.patient.activity.mine.MyOrdonnanceActivity;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.ImInfo;
import com.ais.patient.been.Message;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.UploadPopupwindow;
import com.ais.patient.widget.recycleview.DefineBAGRefreshWithLoadView;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;

public class MessageActivity extends MYBaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mRecycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.define_sliding_bga)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView;
    private List<Message> list = new ArrayList<>();
    private int pageNum = 1;
    private int pageSize = 10;
    private Context context;
    private UploadPopupwindow popupwindow;
    private RecyclerAdapter<Message> adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("消息中心");
        context = this;
        initView();
        setBgaRefreshLayout();
        setData();
    }


    private void initView() {
        //设置刷新和加载监听
        mBGARefreshLayout.setDelegate(this);

        showPopueWindow();
    }

    private void showDialog( final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cancel, null);
        final AlertDialog dialog = builder.create();
        final TextView tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        tvMsg.setText("请确认是否删除？");
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消支付
                String baseUrl = "/api/message/remove/"+id;
                Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).toDeleteSingle(baseUrl);
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                    @Override
                    public void onSuccess(Object o, String info) {
                        ToastUtils.show(context,"删除成功");
                        dialog.dismiss();

                        handler.sendEmptyMessageDelayed(0,0);
                    }

                    @Override
                    public void onFailure(String info) {
                        dialog.dismiss();
                        ToastUtils.show(context,info);
                    }
                });
            }
        });
        dialog.show();
        dialog.getWindow().setContentView((LinearLayout) view);
    }
    /**
     * 设置 BGARefreshLayout刷新和加载
     */
    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(context, true, true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载更多");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        adapter = new RecyclerAdapter<Message>(context, R.layout.message_item, list) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final Message item) {
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_content, item.getContent());
                helper.setText(R.id.tv_time, item.getCreateTime());
                boolean readStatus = item.isReadStatus();
                if (readStatus){
                    helper.setText(R.id.tv_status,"已读");
                    helper.setTextColor(R.id.tv_status,getResources().getColor(R.color.color_red));
                }else {
                    helper.setText(R.id.tv_status,"未读");
                    helper.setTextColor(R.id.tv_status,getResources().getColor(R.color.color_green));
                }
                final int code = item.getCode();
                helper.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        String messageId = item.getMessageId();
                        showDialog(messageId);
                        return true;
                    }
                });

                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String messageId = item.getMessageId();
                        String baseUrl = "/api/message/read/"+messageId;
                        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).toReadSingle(baseUrl);
                        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                            @Override
                            public void onSuccess(Object o, String info) {
                                Intent intent  = new Intent();
                                intent.setAction("com.ais.messageread");
                                sendBroadcast(intent);
                            }

                            @Override
                            public void onFailure(String info) {
                                showToast(info);
                            }
                        });
                        if (code==101){
                            RequstIMChatUtil.request(context,item.getBusinessId(),item.getDoctorId());
                        }else if (code==102){
                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else if (code==103){

                        }else if (code==104){
                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            intent.putExtra("jump",1000);
                            startActivity(intent);
                        }else if (code==105){
                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            intent.putExtra("jump",1000);
                            startActivity(intent);
                        }else if (code==106){
                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            intent.putExtra("jump",1000);
                            startActivity(intent);
                        }else if (code==107){
                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else if (code==201){
                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            intent.putExtra("jump",1000);
                            startActivity(intent);
                        }else if (code==202){
                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            intent.putExtra("jump",1000);
                            startActivity(intent);
                        }else if (code==203){
                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            intent.putExtra("jump",1000);
                            startActivity(intent);
                        }else if (code==204){
                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            intent.putExtra("jump",1000);
                            startActivity(intent);
                        }else if (code==205){
                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            intent.putExtra("jump",1000);
                            startActivity(intent);
                        }else if (code==206){
                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else if (code==301){
                            Intent intent = new Intent(MessageActivity.this, MyOrdonnanceActivity.class);
                            intent.putExtra("type",1);
                            startActivity(intent);
                        }else if (code==302){

                        }else if (code==303){
                            Intent intent = new Intent(MessageActivity.this, MyOrdonnanceActivity.class);
                            intent.putExtra("type",2);
                            startActivity(intent);
                        }else if (code==401){
                            Intent intent = new Intent(MessageActivity.this, MyConcernActivity.class);
                            intent.putExtra("type",1);
                            startActivity(intent);
                        }else if (code==402){
                            Intent intent = new Intent(MessageActivity.this, DoctorInfomationActivity.class);
                            intent.putExtra("id",item.getDoctorId());
                            startActivity(intent);
                        }else if (code==403){
                            Intent intent = new Intent(MessageActivity.this, MyCouponsActivity.class);
                            startActivity(intent);
                        }else if (code==404){
                            Intent intent = new Intent(MessageActivity.this, MyAppraiseActivity.class);
                            startActivity(intent);
                        }

                    }
                });

            }
        };
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 请求网络数据
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    list.clear();
                    adapter.clear();
                    setData();
                    if (mBGARefreshLayout != null) {
                        mBGARefreshLayout.endRefreshing();
                    }
                    break;
                case 1:
                    setData();
                    if (mBGARefreshLayout != null) {
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
     */
    private void setData() {
        final Call<HttpBaseBean<List<Message>>> call = RetrofitFactory.getInstance(context).getMessageList(pageNum, pageSize);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<Message>>() {

            @Override
            public void onSuccess(List<Message> data, String info) {
                if (data!=null && data.size()>0) {
                    list.addAll(data);
                    adapter.addAll(list);
                } else {
                    if (pageNum != 1) {
                        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("没有更多了");
                    }
                }
            }

            @Override
            public void onFailure(String info) {
                ToastUtils.show(context, info);
            }
        });
    }

    /**
     * 刷新
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        pageNum = 1;
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    /**
     * 加载
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pageNum++;
        handler.sendEmptyMessageDelayed(1, 500);
        return true;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_back, R.id.tv_pop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.tv_pop:
                popupwindow.showAtLocation(mToolbar, Gravity.NO_GRAVITY, 0, mToolbar.getHeight() + 25);
                break;
        }
    }


    private void showPopueWindow() {
        popupwindow = new UploadPopupwindow(this, R.layout.popupwindow_msg, R.id.ll_msg, RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupwindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupwindow.setAnimationStyle(R.style.style_pop_animation);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        View view = popupwindow.getContentView();

        final TextView tvToRead = (TextView) view.findViewById(R.id.tv_toRead);
        final TextView tvDelete = (TextView) view.findViewById(R.id.tv_delete);
        tvToRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).toReadAll();
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                    @Override
                    public void onSuccess(Object o, String info) {
                        showToast("操作成功");
                        Intent intent  = new Intent();
                        intent.setAction("com.ais.messageread");
                        sendBroadcast(intent);
                        popupwindow.dismiss();
                        pageNum = 1;
                        handler.sendEmptyMessageDelayed(0, 0);
                    }

                    @Override
                    public void onFailure(String info) {
                        showToast(info);
                    }
                });
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).toClearIsRead();
                new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                    @Override
                    public void onSuccess(Object o, String info) {
                        showToast("操作成功");
                        popupwindow.dismiss();
                        pageNum = 1;
                        handler.sendEmptyMessageDelayed(0, 0);
                    }

                    @Override
                    public void onFailure(String info) {
                        showToast(info);
                    }
                });
            }
        });
    }



}
