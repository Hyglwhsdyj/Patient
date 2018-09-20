package com.ais.patient.been;

/**
 * Created by Administrator on 2018/7/2.
 */

public class VersionBean {

    /**
     * versionCode : 1
     * versionName : 1.0.1
     * versionInfo : APP版本测试
     * forceUpdate : false
     * downloadUrl : http://XXXXXX
     */

    private int versionCode;
    private String versionName;
    private String versionInfo;
    private boolean forceUpdate;
    private String downloadUrl;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
