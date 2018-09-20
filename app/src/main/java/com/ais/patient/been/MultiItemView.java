package com.ais.patient.been;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by lyf on 2018/8/6.
 */

public class MultiItemView<T> implements MultiItemEntity {

    public static final int TITLE = 1;
    public static final int BODY = 2;
    public static final int FOOTER= 3;
    private int itemType;
    private T bean;

    public MultiItemView(int itemType) {
        this.itemType = itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}

