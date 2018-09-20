package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */

public class DoctorDynamicRespone {

    /**
     * articleId : 5b0eae31aa54e33020d5ccec
     * image : http://pic.5iyzy.com/group1/M00/00/A4/wKgByFsDesiEMltKAAAAANoOews287.jpg
     * name : 斩三
     * type : 1
     * title : 我的文章
     * content : 文章内容
     * time : 2018年05月28日 17:16:24
     * pics : ["http://pic.5iyzy.com/123","http://pic.5iyzy.com/123"]
     */

    private String articleId;
    private String image;
    private String name;
    private String type;
    private String title;
    private String content;
    private String time;
    private List<String> pics;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }
}
