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
import com.ais.patient.been.ReservationRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/5 0005.
 */

public class ReservationRecordAdapter extends RecyclerView.Adapter<ReservationRecordAdapter.ViewHolder> {

    private final Context context;
    private final List<ReservationRecord> list;
    private onIntemClickLister listener;

    public void setOnIntemClickLister(onIntemClickLister listener){
        this.listener = listener;
    }
    public interface onIntemClickLister{
        void toPay(String id);
        void cancel(String id, int status);
    }

    public ReservationRecordAdapter(Context context, List<ReservationRecord> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvDoctorName.setText("预约医生："+list.get(position).getName());
        holder.tvTime.setText("预约时间："+list.get(position).getTime());
        holder.tvAddress.setText("预约地点："+list.get(position).getAddress());
        String status = list.get(position).getStatus();
        if (status.equals("CANCEL_PAY")){
            holder.tvStatus.setText("订单状态：取消支付");
        }else if (status.equals("CANCEL_ORDER")){
            holder.tvStatus.setText("订单状态：取消订单");
        }else if (status.equals("COMPLETE")){

        }
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
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
