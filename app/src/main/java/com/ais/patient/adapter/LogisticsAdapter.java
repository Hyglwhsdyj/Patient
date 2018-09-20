package com.ais.patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.been.OrdonnanceDetailRespone;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/20 0020.
 */

public class LogisticsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<OrdonnanceDetailRespone.LogisticsBean.DataBean> list;


    public LogisticsAdapter(Context context, List<OrdonnanceDetailRespone.LogisticsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType()==2) {
            return 0;
        } else if (list.get(position).getType()==1) {
            return 1;
        } else {
            return 2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.logistics_item1, parent, false);
            return new ViewHolder1(view);
        } else if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.logistics_item2, parent, false);
            return new ViewHolder2(view);
        } else if (viewType == 2) {
            View view = LayoutInflater.from(context).inflate(R.layout.logistics_item3, parent, false);
            return new ViewHolder3(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder1){
            ViewHolder1 holder1 = (ViewHolder1) holder;
            holder1.tvLogisticsName.setText("物流公司："+list.get(position).getTime());
            holder1.tvLogisticsNo.setText("物流单号："+list.get(position).getContext());
        }else if (holder instanceof ViewHolder2){
            ViewHolder2 holder2 = (ViewHolder2) holder;
            holder2.tvTime.setText(list.get(position).getTime());
            holder2.tvContext.setText(list.get(position).getContext());
        }else if (holder instanceof ViewHolder3){
            ViewHolder3 holder3 = (ViewHolder3) holder;
            holder3.tvTime.setText(list.get(position).getTime());
            holder3.tvContext.setText(list.get(position).getContext());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_logisticsNo)
        TextView tvLogisticsNo;
        @BindView(R.id.tv_logisticsName)
        TextView tvLogisticsName;

        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_context)
        TextView tvContext;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolder3 extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_context)
        TextView tvContext;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder3(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
