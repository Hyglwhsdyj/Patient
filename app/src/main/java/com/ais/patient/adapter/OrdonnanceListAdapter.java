package com.ais.patient.adapter;

import android.text.TextUtils;

import com.ais.patient.R;
import com.ais.patient.been.MultiItemView;
import com.ais.patient.been.OrdonnanceListRespone;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by lyf on 2018/8/6.
 */

public class OrdonnanceListAdapter extends BaseMultiItemQuickAdapter<MultiItemView<OrdonnanceListRespone>, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public OrdonnanceListAdapter(List<MultiItemView<OrdonnanceListRespone>> data) {
        super(data);
        addItemType(MultiItemView.TITLE, R.layout.item_ordonnance_list_title);
        addItemType(MultiItemView.BODY, R.layout.item_ordonnance_list_body);
        addItemType(MultiItemView.FOOTER, R.layout.item_ordonnance_list_footer);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemView<OrdonnanceListRespone> item) {
        if(item.getBean()==null){
            return;
        }
        switch (item.getItemType()) {
            case MultiItemView.TITLE:
                helper.setText(R.id.tv_order_num,"订单号："+item.getBean().getOrderId()+"");
                helper.setText(R.id.tv_order_time,item.getBean().getOrderTime());
                break;
            case MultiItemView.BODY:
                helper.setText(R.id.tv_name,"医生："+item.getBean().getDoctorName()+"/"+"患者："+item.getBean().getPatientName());
                String decoct = item.getBean().getDecoct();
                if(decoct!=null){
                    helper.setText(R.id.tv_dococt,"开放剂型："+decoct);
                }else {
                    helper.setText(R.id.tv_dococt,"开放剂型：");
                }
                helper.setText(R.id.tv_invalidTime,"失效时间："+item.getBean().getInvalidTime());
                switch (item.getBean().getStatus()){
                    case "01":
                        helper.setText(R.id.tv_status,"待支付");
                        break;
                    case "02":
                        helper.setText(R.id.tv_status,"已支付");
                        break;
                    case "03":
                        helper.setText(R.id.tv_status,"订单已完成");
                        break;
                    case "04":
                        helper.setText(R.id.tv_status,"订单未支付过期");
                        break;
                    case "05":
                        helper.setText(R.id.tv_status,"订单取消");
                        break;
                }
                break;
            case MultiItemView.FOOTER:
                helper.setText(R.id.tv_order_price,"￥"+item.getBean().getFee());
                switch (item.getBean().getStatus()) {
                    case "01":
                        helper.setText(R.id.tv_1,"去支付");
                        helper.setText(R.id.tv_2,"取消订单");
                        helper.setVisible(R.id.tv_1,true);
                        helper.setVisible(R.id.tv_2,true);
                        helper.setTag(R.id.tv_2,"取消订单");
                        break;
                    case "02":
                        helper.setText(R.id.tv_2,"查看物流");
                        helper.setVisible(R.id.tv_1,false);
                        helper.setVisible(R.id.tv_2,true);
                        helper.setTag(R.id.tv_2,"查看物流");
                        break;
                    case "03":
                        helper.setText(R.id.tv_2,"查看详情");
                        helper.setVisible(R.id.tv_1,false);
                        helper.setVisible(R.id.tv_2,true);
                        helper.setTag(R.id.tv_2,"查看详情");
                        break;
                    case "04":
                        helper.setText(R.id.tv_2,"取消订单");
                        helper.setVisible(R.id.tv_1,false);
                        helper.setVisible(R.id.tv_2,false);

                        helper.setTag(R.id.tv_2,"取消订单");
                        break;
                    case "05":
                        helper.setText(R.id.tv_2,"取消订单");
                        helper.setVisible(R.id.tv_1,false);
                        helper.setVisible(R.id.tv_2,false);

                        helper.setTag(R.id.tv_2,"取消订单");
                        break;
                }
                break;
        }
        helper.addOnClickListener(R.id.tv_1);
        helper.addOnClickListener(R.id.tv_2);
    }
}
