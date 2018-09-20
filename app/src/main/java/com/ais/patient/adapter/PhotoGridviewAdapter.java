package com.ais.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YGD on 2017/12/26.
 */

public class PhotoGridviewAdapter extends BaseAdapter {

    private List<String> adats;
    private Context context;
    public SetItmeOnClickListener listener;

     MyViewHolder myViewHolder;
     MyViewHolder2 myViewHolder2;
    MyViewHolder3 myViewHolder3;
    public PhotoGridviewAdapter(Context context) {
        this.context = context;
        this.adats=new ArrayList<>();
    }

    public PhotoGridviewAdapter SetOnCLickItme(SetItmeOnClickListener listener){
        this.listener=listener;
        return PhotoGridviewAdapter.this;
    }

    public void SetData( List<String> adats){
        this.adats=adats;
    }


    @Override
    public int getCount() {
        return adats.size();
    }

    @Override
    public Object getItem(int i) {
        return adats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view!=null){
            if (adats.get(i).equals("")){
                myViewHolder2= (MyViewHolder2) view.getTag();
            }else if (adats.get(i).equals("1")){
                myViewHolder3= (MyViewHolder3) view.getTag();
            }else {
                myViewHolder= (MyViewHolder) view.getTag();
            }
        }else {
            myViewHolder=new MyViewHolder();
            myViewHolder2=new MyViewHolder2();
            myViewHolder3=new MyViewHolder3();
            if (adats.get(i).equals("")){
                view= LayoutInflater.from(context).inflate(R.layout.gridview_itme1,null,false);
                myViewHolder2.img_photo=view.findViewById(R.id.img_photo);
                myViewHolder2.delete=view.findViewById(R.id.delete);
                view.setTag(myViewHolder2);
            }else if (adats.get(i).equals("1")){
                view= LayoutInflater.from(context).inflate(R.layout.gridview_itme2,null,false);
                myViewHolder3.llLook = view.findViewById(R.id.ll_look);
                view.setTag(myViewHolder2);
            }else {
                view = LayoutInflater.from(context).inflate(R.layout.gridview_itme, null);
                myViewHolder.img_photo = view.findViewById(R.id.img_photo);
                myViewHolder.delete = view.findViewById(R.id.delete);
                view.setTag(myViewHolder);
            }

        }

        if (!adats.get(i).equals("") && !adats.get(i).equals("1")){
            Glide.with(context).load(adats.get(i)).into(myViewHolder.img_photo);
        }
        if (adats.get(i).equals("")){
            myViewHolder2.delete.setVisibility(View.GONE);
            myViewHolder2.img_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.ItmeonClickListener(i);
                }
            });
        }else if (adats.get(i).equals("1")){
            myViewHolder3.llLook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.showDialog();
                }
            });
        }else {
            myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.ItmeonDelete(i);
                }
            });
        }

        return view;
    }

    class MyViewHolder{
        ImageView img_photo,delete;
    }

    class MyViewHolder2{
        ImageView delete;
        TextView img_photo;
    }

    class MyViewHolder3{
        LinearLayout llLook;
    }

    public interface SetItmeOnClickListener{
        void ItmeonClickListener(int i);
        void ItmeonDelete(int i);
        void showDialog();
    }

}
