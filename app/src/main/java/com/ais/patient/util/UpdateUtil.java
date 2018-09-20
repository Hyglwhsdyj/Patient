package com.ais.patient.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.VersionBean;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;

import retrofit2.Call;


public class UpdateUtil {
    private static Activity mContext;
//    private static boolean mNeedCheckUpgrade = true; // 是否需要检测升级
    // 是否需要显示对话框，在登录和主页检测更新时不需要提示，再设置页面点击检测更新时显示提示
    private static boolean mIsShowToast = false;

//    public static boolean isNeedCheckUpgrade() {
//        return mNeedCheckUpgrade;
//    }

    /*public static void checkAppUpdate(Activity context, boolean isShowToast) {
        mContext = context;
        mIsShowToast = isShowToast;

//        //开始检测了升级之后，设置标志位为不再检测升级
//        if (mNeedCheckUpgrade) {
//            mNeedCheckUpgrade = false;
//        } else {
//            return;
//        }
        final int versionCode = Integer.valueOf(getVersionCode(context));
        Call<HttpBaseBean<VersionBean>> call = RetrofitFactory.getInstance(context).getVersionInfo(1201);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<VersionBean>() {
            @Override
            public void onSuccess(VersionBean version, String info) {
                if (version != null) {
                    int vCode = version.getVersionCode();
                    if (vCode > versionCode)// 表示有更新
                    {
                        boolean isForce = version.isForceUpdate();
                        String downloadUrl = version.getDownloadUrl();
                        String versionName = version.getVersionName();
                        String versionInfo = version.getVersionInfo();

                        if (isForce) {// 表示强制更新
                            showUpdateDialog(true, downloadUrl, versionName, versionInfo);
                        } else {// 表示不强制更新
                            showUpdateDialog(false, downloadUrl, versionName, versionInfo);
                        }
                    }else {// 表示未更新
                        if (mIsShowToast) {
                            Toast.makeText(mContext, "已经是最新版本!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (mIsShowToast) {
                        Toast.makeText(mContext, "已经是最新版本!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(String msg) {
            }
        });
    }*/

    /**
     * 获取当前软件版本号
     *
     * @param ctx
     * @return
     */
    public static String getVersionCode(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(ctx.getPackageName(), 0);

            return String.valueOf(pi.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 弹出更新的dialog
     *
     * @param isForceUpdate 是否强制更新 true是，false否
     * @param downloadUrl   下载链接
     * @param versionName   最新版本
     * @param versionInfo   更新内容
     * @description
     */
    private static void showUpdateDialog(final boolean isForceUpdate, final String downloadUrl, String versionName, String versionInfo) {

        /*AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_update_dialog,null);
        TextView tvTitle = view.findViewById(R.id.tv_update_title);
        TextView tvVersionInfo = view.findViewById(R.id.tv_versionInfo);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        TextView tvConfirm = view.findViewById(R.id.tv_confirm);
        tvTitle.setText("是否更新到"+versionName+"版本");
        tvVersionInfo.setText(versionInfo);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 稍后更新（强制更新）:点击稍后更新也要更新
                if (isForceUpdate) {
                    dialog.dismiss();
                    mContext.finish();
                    System.exit(0);
                } else {// 稍后更新（非强制更新）:点击稍后更新消失对话框
                    dialog.dismiss();
                }
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                UpdateService updateService = new UpdateService(mContext, downloadUrl, "daiyan", isForceUpdate);
                updateService.downFile();
            }
        });
        dialog.setCancelable(false);
        dialog.show();*/
        /*Window window = dialog.getWindow();
        //设置背景透明
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = dpToPxInt(300);
        window.setAttributes(layoutParams);
        window.setContentView(view);*/
    }

    public static int dpToPxInt(float dp) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics));
    }
}
