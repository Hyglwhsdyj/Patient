package com.ais.patient.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ais.patient.R;
import com.ais.patient.been.DoctorDynamicRespone;
import com.ais.patient.been.MultiItemView;
import com.ais.patient.util.DisplayUtil;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.CircleImageView;
import com.ais.patient.widget.NetstedGridView;
import com.ais.patient.widget.WarpLinearLayout;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */

public class DoctorDynamicAdapter extends BaseMultiItemQuickAdapter<MultiItemView<DoctorDynamicRespone>, BaseViewHolder> {

    private Context context;

    public DoctorDynamicAdapter(Context context, List<MultiItemView<DoctorDynamicRespone>> data) {
        super(data);
        this.context = context;
        addItemType(MultiItemView.BODY, R.layout.item_doctor_dynamic_body);
        addItemType(MultiItemView.FOOTER, R.layout.item_doctor_dynamic_footer);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemView<DoctorDynamicRespone> item) {
        switch (item.getItemType()) {
            case MultiItemView.BODY:
                CircleImageView iv_doctor_photo = helper.getView(R.id.iv_doctor_photo);
                Glide.with(context).load(item.getBean().getImage()).into(iv_doctor_photo);
                if (item.getBean().getType().equals("1")){
                    helper.setText(R.id.tv_doctor_name, item.getBean().getName()+"发布了健康科普");
                    helper.setText(R.id.tv_dynamic_title, item.getBean().getTitle());
                }else if (item.getBean().getType().equals("5")){
                    helper.setText(R.id.tv_doctor_name, item.getBean().getName()+"发布了新动态");
                    helper.setText(R.id.tv_dynamic_title, item.getBean().getContent());
                }else if (item.getBean().getType().equals("6")){
                    helper.setText(R.id.tv_doctor_name, item.getBean().getName()+"发布了新公告");
                    helper.setText(R.id.tv_dynamic_title, item.getBean().getContent());
                }
                List<String> pics = item.getBean().getPics();
                NetstedGridView mGrideView = helper.getView(R.id.mNetstedGridView);
                if (item.getBean().getType().equals("5") && pics.size()>0){
                    mGrideView.setVisibility(View.VISIBLE);
                }else {
                    mGrideView.setVisibility(View.GONE);
                }
                Adapter<String> adapter = new Adapter<String>(context,R.layout.image_item2,pics) {
                    @Override
                    protected void convert(AdapterHelper helper, String item) {
                        ImageView imageView = (ImageView) helper.getItemView().findViewById(R.id.iv_icon);
                        Glide.with(context).load(item).into(imageView);
                    }
                };
                mGrideView.setAdapter(adapter);
                break;
        }
        helper.addOnClickListener(R.id.tv_look_detail);
    }
}
