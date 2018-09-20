package com.ais.patient.http;

import android.text.TextUtils;


import com.ais.patient.util.JsonParser;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Endoon on 2016/4/12.
 */
public class AjaxParams {

  protected ConcurrentHashMap<String, Object> urlParams;

  public AjaxParams() {
    urlParams = new ConcurrentHashMap<>();
  }

  public void put(String key, Object value) {
    if (!TextUtils.isEmpty(key) && value != null) {
      urlParams.put(key, value);
    }
  }

  public void remove(String key) {
    urlParams.remove(key);
  }

  public ConcurrentHashMap<String, Object> getUrlParams() {
    return urlParams;
  }


  @Override
  public String toString() {
    return JsonParser.toJson(urlParams);
  }
}