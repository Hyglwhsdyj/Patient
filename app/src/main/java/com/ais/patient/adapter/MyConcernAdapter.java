package com.ais.patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.been.MultiItemView;
import com.ais.patient.been.MyConcernResponse;
import com.ais.patient.widget.CircleImageView;
import com.ais.patient.widget.WarpLinearLayout;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */

public class MyConcernAdapter extends BaseMultiItemQuickAdapter<MultiItemView<MyConcernResponse>, BaseViewHolder> {


   private Context context;
    public MyConcernAdapter(Context context,List<MultiItemView<MyConcernResponse>> data) {
        super(data);
        this.context=context;
        addItemType(MultiItemView.BODY, R.layout.item_my_concern_body);
        addItemType(MultiItemView.FOOTER, R.layout.item_my_concern_footer);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemView<MyConcernResponse> item) {

        switch (item.getItemType()) {
            case MultiItemView.BODY:
                CircleImageView iv_doctor_photo=helper.getView(R.id.iv_doctor_photo);
                Glide.with(context).load(item.getBean().getImage()).into(iv_doctor_photo);
                helper.setText(R.id.tv_doctor_name,item.getBean().getName()+"  "+item.getBean().getTitles());
                helper.setText(R.id.tv_doctor_position,"医院机构："+item.getBean().getMedicalInstitutions());
                helper.setText(R.id.tv_doctor_depart,item.getBean().getDepart());
                WarpLinearLayout tv_good=helper.getView(R.id.tv_good);
                tv_good.removeAllViews();
                for(int i=0;i<item.getBean().getDiseaseExpertise().size();i++){
                    View view=View.inflate(context,R.layout.tv_blue_layout,null);
                    TextView tv_blue=view.findViewById(R.id.tv_blue);
                    tv_blue.setText(item.getBean().getDiseaseExpertise().get(i));
                    tv_good.addView(view);
                }
                break;
            case MultiItemView.FOOTER:
                helper.setText(R.id.tv_money,item.getBean().getFee()+"元/次");
                break;
        }
        helper.addOnClickListener(R.id.tv_cancel);
    }
}
