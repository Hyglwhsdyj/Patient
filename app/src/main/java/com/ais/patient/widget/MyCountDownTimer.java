package com.ais.patient.widget;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class MyCountDownTimer extends CountDownTimer{

    private final TextView textView;

    /**
     * @param getCode
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
 *                          {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(TextView getCode, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        textView = getCode;
    }

    //计时过程
    @Override
    public void onTick(long l) {
        //防止计时过程中重复点击
        textView.setClickable(false);
        textView.setText(l/1000+"s");
    }

    //计时完毕的方法
    @Override
    public void onFinish() {
        //重新给textView设置文字
        textView.setText("重新获取");
        //设置可点击
        textView.setClickable(true);
    }
}
