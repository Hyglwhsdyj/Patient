package com.ais.patient.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.ais.patient.R;
import com.ais.patient.been.MyAdressResponse;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by lyf on 2018/8/9.
 */

public class MyAdressAdapter extends BaseQuickAdapter<MyAdressResponse,BaseViewHolder> {

    private Context context;
    public MyAdressAdapter(Context context,int layoutResId, @Nullable List<MyAdressResponse> data) {
        super(layoutResId, data);
        this.context=context;
    }


    @Override
    protected void convert(BaseViewHolder helper, MyAdressResponse item) {

        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_phone,item.getPhoneNumber());
        helper.setText(R.id.tv_adress,item.getAddress());
        if ("Y".equals(item.getPreferred())) {
            helper.setImageResource(R.id.iv_default,R.drawable.select_yellow);
        }else {
            helper.setImageResource(R.id.iv_default,R.drawable.unselect_gray);
        }

    }
}
