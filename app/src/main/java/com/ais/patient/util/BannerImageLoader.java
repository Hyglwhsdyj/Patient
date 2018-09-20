package com.ais.patient.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * 轮播图图片加载器
 *
 * @author GreenTurtle <p>email:912967642@qq.com <p>date:2018-03-06 16:15 <p>company:kemean
 * @version 1.0
 */

public class BannerImageLoader extends ImageLoader {

  @Override
  public void displayImage(Context context, Object path, ImageView imageView) {
    Glide.with(context).load(path).into(imageView);
  }
}
