package com.ais.patient.util;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.ais.patient.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/4 0004.
 */

public class CallPhoneUtil {

    static List<String> mPermissionList = new ArrayList<>();
    static private int PERMISSION_STATE = 200;

    static String[] permissions = new String[]{
            Manifest.permission.CALL_PHONE,
    };

    public static void showPermission(final Context context,final String phone) {
        /**
         * 判断哪些权限未授予
         */
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }

        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            //用intent启动拨打电话
            if (MobileUtil.isMobile(phone)){
                new android.support.v7.app.AlertDialog.Builder(context)
                        .setTitle("提示")
                        .setMessage("确定拨打"+phone +"?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                                context.startActivity(intent);
                                dialog.dismiss();
                            }
                        })
                        .show();

            }else {
                ToastUtils.show(context,"手机号不合法");
            }
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions((BaseActivity)context, permissions, PERMISSION_STATE);
        }
    }


    /*//Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_STATE)
        {
            mPermissionList.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            *//**
             * 判断是否为空
             *//*
            if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
                //用intent启动拨打电话
                if (MobileUtil.isMobile(phone)){
                    new android.support.v7.app.AlertDialog.Builder(context)
                            .setTitle("提示")
                            .setMessage("确定拨打"+phone +"?")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                                    context.startActivity(intent);
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }else {
                    ToastUtils.show(context,"手机号不合法");
                }
            } else {//请求权限方法
                // 没有获取到权限，做特殊处理
                ToastUtils.show(context, "请开启权限，拒绝将无法拨打电话");
            }
        }else {
            // 没有获取到权限，做特殊处理
            ToastUtils.show(context, "请开启权限");
            //引导用户到设置中去进行设置
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
            startActivity(intent);
        }
    }*/
}
