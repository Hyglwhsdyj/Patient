package com.netease.nim.uikit.business.session.aszy;

import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderText;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderDefCustom extends MsgViewHolderText {

    public MsgViewHolderDefCustom(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected String getDisplayText() {
        OrderMsgAttachment attachment = (OrderMsgAttachment) message.getAttachment();

        return "type: " + attachment.getType() + ", data: ";//+ attachment.getContent();
    }
}
