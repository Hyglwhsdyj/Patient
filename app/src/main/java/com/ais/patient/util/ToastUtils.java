/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.ais.patient.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private ToastUtils() {}

    public static void show(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }
    public static void show(Context context, int resId, int duration) {
        show(context, context.getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId) {
        show(context, context.getText(resId));
    }
}
