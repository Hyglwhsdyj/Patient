package com.ais.patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.been.ChatOnLineList;
import com.ais.patient.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/5 0005.
 */

public class ChatOnlineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_OFIRST = 0;
    private static final int TYPE_SECOND = 1;
    private final Context context;
    private final List<ChatOnLineList> list;

    private onIntemClickLister listener;

    public ChatOnlineAdapter(Context context, List<ChatOnLineList> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnIntemClickLister(onIntemClickLister listener) {
        this.listener = listener;
    }

    public interface onIntemClickLister {
        void toPay(String doctorId, String recordId);

        void cancel(String id);


        void onItenClick(String doctorId, String recordId, String explainState);
    }


    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getDepart() != null) {
            return TYPE_OFIRST;
        } else {
            return TYPE_SECOND;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_OFIRST) {
            View view = LayoutInflater.from(context).inflate(R.layout.chatonline_item, parent, false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_SECOND) {
            View view = LayoutInflater.from(context).inflate(R.layout.chatonline_record_item, parent, false);
            return new ViewHolderRecord(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1,final int position) {
        if (holder1 instanceof ViewHolder){
            ViewHolder holder = (ViewHolder) holder1;
            holder.tvOrderNum.setText("订单号：" + list.get(position).getRecordId());
            holder.tvTime.setText(list.get(position).getTime());
            Picasso.with(context)
                    .load(list.get(position).getImage())
                    .transform(new CircleTransform())
                    .into(holder.ivIcon);
            holder.tvName.setText(list.get(position).getName());
            if (list.get(position).getTitles()!=null){
                holder.tvTitle.setText(list.get(position).getTitles());
            }
            if (list.get(position).getDepart()!=null){
                holder.tvDepart.setText(list.get(position).getDepart());
            }
            String explainState = list.get(position).getExplainState();
            if (explainState.equals("1")) {
                holder.tvExplainState.setText("24小时内图文、语音、视频咨询问诊服务");
            } else if (explainState.equals("2")) {
                holder.tvExplainState.setText("填写问诊单");
            } else if (explainState.equals("3")) {
                holder.tvExplainState.setText("问诊单已填写");
                holder.tvInfo.setVisibility(View.VISIBLE);
                holder.tvInfo.setText("预约服务时间："+list.get(position).getServiceTime());
            } else if (explainState.equals("4")) {
                holder.tvExplainState.setText("已支付问诊进行中订单");
                holder.tvInfo.setVisibility(View.VISIBLE);
                if (list.get(position).getLastReply()!=null){
                    holder.tvInfo.setText(list.get(position).getLastReply());
                }
            }
            double fee = list.get(position).getFee();
            holder.tvFee.setText(fee + "元/次");

            String status = list.get(position).getStatus();
            if (status.equals("WAIT_BUYER_PAY")) {
                //待支付
                holder.llPay.setVisibility(View.VISIBLE);
            } else {
                holder.llPay.setVisibility(View.GONE);
            }

            holder.tvToPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        String recordId = list.get(position).getRecordId();
                        String doctorId = list.get(position).getDoctorId();
                        listener.toPay(doctorId,recordId);
                    }
                }
            });

            holder.tvCancal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.cancel(list.get(position).getRecordId());
                    }
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        String recordId = list.get(position).getRecordId();
                        String doctorId = list.get(position).getDoctorId();
                        String explainState = list.get(position).getExplainState();
                        listener.onItenClick(doctorId,recordId,explainState);
                    }
                }
            });
        }else {
            ViewHolderRecord holder = (ViewHolderRecord) holder1;
            holder.tvOrderNum.setText("订单号：" + list.get(position).getRecordId());
            holder.tvTime.setText(list.get(position).getTime());
            if(!TextUtils.isEmpty(list.get(position).getImage())){
                Picasso.with(context)
                        .load(list.get(position).getImage())
                        .transform(new CircleTransform())
                        .into(holder.ivIcon);
            }

            holder.tvName.setText(list.get(position).getName());
            String explainState = list.get(position).getExplainState();
            if (explainState.equals("1")){
                holder.tvMsg.setText("在线问诊已结束,查看问诊详情 >>");
            }if (explainState.equals("2")){
                holder.tvMsg.setText("在线问诊已取消，再次前往问诊 >>");
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        String recordId = list.get(position).getRecordId();
                        String doctorId = list.get(position).getDoctorId();
                        String explainState = list.get(position).getExplainState();
                        listener.onItenClick(doctorId,recordId,explainState);
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_order_num)
        TextView tvOrderNum;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_depart)
        TextView tvDepart;
        @BindView(R.id.tv_explainState)
        TextView tvExplainState;
        @BindView(R.id.tv_info)
        TextView tvInfo;
        @BindView(R.id.tv_fee)
        TextView tvFee;
        @BindView(R.id.tv_toPay)
        TextView tvToPay;
        @BindView(R.id.tv_cancal)
        TextView tvCancal;
        @BindView(R.id.ll_pay)
        LinearLayout llPay;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class ViewHolderRecord extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_order_num)
        TextView tvOrderNum;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_msg)
        TextView tvMsg;

        public ViewHolderRecord(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
