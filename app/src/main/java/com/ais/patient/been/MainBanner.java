package com.ais.patient.been;

/**
 * Created by Administrator on 2018/7/31 0031.
 */

public class MainBanner {

    /**
     * title : 患者端首页广告
     * content : 广告内容-急诊-找大夫
     * imagesUrl : https://www.baidu.com/img/bd_logo1.png
     * url : https://www.baidu.com/
     */

    private String title;
    private String content;
    private String imagesUrl;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
