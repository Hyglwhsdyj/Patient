package com.ais.patient.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.nanchen.compresshelper.CompressHelper;

import java.io.File;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class ImageCompress {
    //压缩图片
    public static File CompressPic(File file, Context context){
        File newFile = new CompressHelper.Builder(context)
                .setMaxWidth(750)  // 默认最大宽度为720 750
                .setMaxHeight(608) // 默认最大高度为960 608
                .setQuality(80)    // 默认压缩质量为80
                .setFileName(file.getName()) // 设置你需要修改的文件名
                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .build()
                .compressToFile(file);
        return newFile;
    }
}
