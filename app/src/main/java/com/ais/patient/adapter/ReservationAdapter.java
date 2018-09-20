package com.ais.patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.been.Reservation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/5 0005.
 */

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private final Context context;
    private final List<Reservation> list;
    private onIntemClickLister listener;

    public void setOnIntemClickLister(onIntemClickLister listener){
        this.listener = listener;
    }
    public interface onIntemClickLister{
        void toPay(String id);
        void cancel(String id,int status);
    }

    public ReservationAdapter(Context context, List<Reservation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvDoctorName.setText("预约医生："+list.get(position).getName());
        holder.tvTime.setText("预约时间："+list.get(position).getTime());
        holder.tvAddress.setText("预约地点："+list.get(position).getAddress());
        holder.tvCountdown.setText(list.get(position).getCountdown());
        int status = list.get(position).getStatus();
        if (status==0){
            holder.tvCancal.setText("取消预约");
            holder.tvStatus.setText("订单状态：已支付");
            holder.tvCountdown.setVisibility(View.GONE);
        }else if (status==1){
            holder.tvToPay.setVisibility(View.VISIBLE);
            holder.tvCancal.setText("取消支付");
            holder.tvStatus.setText("订单状态：未支付");
            holder.tvCountdown.setVisibility(View.VISIBLE);
        }

        holder.tvToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    String id = list.get(position).getId();
                    listener.toPay(id);
                }

            }
        });

        holder.tvCancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    String id = list.get(position).getId();
                    int status1 = list.get(position).getStatus();
                    listener.cancel(id,status1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_doctor_name)
        TextView tvDoctorName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_countdown)
        TextView tvCountdown;
        @BindView(R.id.tv_msg)
        TextView tvMsg;
        @BindView(R.id.tv_toPay)
        TextView tvToPay;
        @BindView(R.id.tv_cancal)
        TextView tvCancal;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
