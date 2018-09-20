package com.ais.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.activity.main.DoctorInfomationActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/29 0029.
 */

public class MyGridViewAdapter extends BaseAdapter{
    private final List<String> list;
    private final Context context;
    private onItemclickListener listener;

    public void setOnItemclickListener(onItemclickListener listener){
        this.listener = listener;
    }
    public interface onItemclickListener{
        void onIntenClick();
    }
    public MyGridViewAdapter(Context context, List<String> diseaseList) {
        this.list = diseaseList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item,null,false);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (list.size()>5 && position==list.size()-1){
            holder.tvName.setBackgroundResource(R.drawable.shape_diseaseexpertise3);
            holder.tvName.setTextColor(context.getResources().getColor(R.color.color_yellow));
        }else {
            holder.tvName.setBackgroundResource(R.drawable.shape_diseaseexpertise2);
            holder.tvName.setTextColor(context.getResources().getColor(R.color.color_main));
        }
        holder.tvName.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tvName;
    }
}
