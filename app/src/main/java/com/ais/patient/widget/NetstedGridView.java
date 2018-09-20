package com.ais.patient.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by YGD on 2018/1/31.
 */

public class NetstedGridView extends GridView {

    public NetstedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NetstedGridView(Context context) {
        super(context);
    }

    public NetstedGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
