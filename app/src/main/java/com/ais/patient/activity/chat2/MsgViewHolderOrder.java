package com.ais.patient.activity.chat2;

import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;


import com.ais.patient.R;
import com.ais.patient.activity.main.BuyMedicineActivity;
import com.ais.patient.activity.main.ChatOnLinePaperDetailActivity;
import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderBase;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;

/**
 * 问诊单 、 订单
 */
public class MsgViewHolderOrder extends MsgViewHolderBase {

    protected TextView bodyTextView;
    protected TextView tv_action_name;

    public MsgViewHolderOrder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_order;
    }

    @Override
    protected void inflateContentView() {
        bodyTextView = findViewById(R.id.tv_content);
        tv_action_name = findViewById(R.id.tv_action_name);
    }

    @Override
    protected void bindContentView() {

        OrderMsgAttachment attachment = (OrderMsgAttachment) message.getAttachment();
        String action = attachment.getIyzy_action();
        String title = attachment.getIyzy_title();
        bodyTextView.setText(title);        // +title+title
        tv_action_name.setText(action);


        layoutDirection();
//        bodyTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClick();
//            }
//        });
//
//        MoonUtil.identifyFaceExpression(NimUIKit.getContext(), bodyTextView, getDisplayText(), ImageSpan.ALIGN_BOTTOM);
//        bodyTextView.setMovementMethod(LinkMovementMethod.getInstance());
//        bodyTextView.setOnLongClickListener(longClickListener);
    }

    private void layoutDirection() {
        bodyTextView.setMaxWidth((int) (0.5 * ScreenUtil.screenWidth));
        if (isReceivedMessage()) {
//            bodyTextView.setBackgroundResource(NimUIKitImpl.getOptions().messageLeftBackground);
//            bodyTextView.setTextColor(Color.BLACK);
//            bodyTextView.setPadding(ScreenUtil.dip2px(15), ScreenUtil.dip2px(8), ScreenUtil.dip2px(10), ScreenUtil.dip2px(8));
        } else {
//            bodyTextView.setBackgroundResource(NimUIKitImpl.getOptions().messageRightBackground);
//            bodyTextView.setTextColor(Color.WHITE);
//            bodyTextView.setPadding(ScreenUtil.dip2px(10), ScreenUtil.dip2px(8), ScreenUtil.dip2px(15), ScreenUtil.dip2px(8));
        }
    }

    @Override
    protected int leftBackground() {
        return R.mipmap.icon_order_msg_bg_left_small;
    }

    @Override
    protected int rightBackground() {
        return R.mipmap.icon_order_msg_bg_right_small;
    }

    @Override
    protected void onItemClick() {
        OrderMsgAttachment attachment = (OrderMsgAttachment) message.getAttachment();
        String msgType = attachment.getIyzy_msg_type();
        // 中药调理服务详情
        if (OrderMsgAttachment.MSG_TYPE_MEDICINE.equals(msgType)) {

            Intent intent = new Intent(context, BuyMedicineActivity.class);
            intent.putExtra("recordId", attachment.getIyzy_business_id());
            context.startActivity(intent);
        }


        // 问诊单详情
        if (OrderMsgAttachment.MSG_TYPE_INTERROGATION.equals(msgType)) {

            Intent intent = new Intent(context, ChatOnLinePaperDetailActivity.class);
            intent.putExtra("recordId", attachment.getIyzy_business_id());
            context.startActivity(intent);

        }

        // 下载App
        if (OrderMsgAttachment.MSG_TYPE_DOWN_APP.equals(msgType)) {
            Intent intent= new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri url = Uri.parse(attachment.getIyzy_doctor_app_url());
            intent.setData(url);
            context.startActivity(intent);
        }

        /*// 处方单详情
        if (OrderMsgAttachment.MSG_TYPE_MEDICINE.equals(msgType)) {

            Intent intent = new Intent(context, MedicineOrderDetailsActivity.class);
            intent.putExtra(XConfig.KEY_INTENT_ORDER_ID, attachment.getIyzy_business_id());
            context.startActivity(intent);

        }*/
    }

    protected String getDisplayText() {
        return message.getContent();
    }
}
