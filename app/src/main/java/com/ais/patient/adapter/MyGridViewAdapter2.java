package com.ais.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.been.MainAllInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/8/29 0029.
 */

public class MyGridViewAdapter2 extends BaseAdapter{
    private final List<MainAllInfo.DiseaesBean> list1;
    private final Context context;
    private final int type;
    private final List<MainAllInfo.DepartsBean> list2;
    private onItemclickListener listener;
    private int mPosition=-1;

    public void setOnItemclickListener(onItemclickListener listener){
        this.listener = listener;
    }

    public void setPosition(int position) {
        this.mPosition = position;
        notifyDataSetChanged();
    }

    public interface onItemclickListener{
        void onIntenClick();
    }
    public MyGridViewAdapter2(Context context, List<MainAllInfo.DiseaesBean> diseaseList, List<MainAllInfo.DepartsBean> departsList, int type) {
        this.list1 = diseaseList;
        this.type = type;
        this.list2 = departsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (type==1){
            return list1.size();
        }else if (type==2){
            return list2.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (type==1){
            return list1.get(position);
        }else if (type==2){
            return list2.get(position);
        }
        return null;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.choose_item,null,false);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_seleted);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (type==1){
            holder.tvName.setText(list1.get(position).getDiseaseName());
        }else if (type==2){
            holder.tvName.setText(list2.get(position).getDepartName());
        }
        if (mPosition==position){
            holder.tvName.setBackgroundResource(R.drawable.shape_choose);
            holder.tvName.setTextColor(context.getResources().getColor(R.color.color_yellow));
            holder.imageView.setVisibility(View.VISIBLE);
        }else {
            holder.tvName.setBackgroundResource(R.drawable.shape_choose_item);
            holder.tvName.setTextColor(context.getResources().getColor(R.color.grey6));
            holder.imageView.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvName;
        ImageView imageView;
    }
}
