package com.ais.patient.been;

import java.util.List;

/**
 * Created by Administrator on 2018/8/15 0015.
 */

public class DoctorNewList {

    /**
     * id : 5b0eae31aa54e33020d5ccec
     * type : 1
     * title : 我的文章
     * content : 悲催别的国家法人股大数据福建发货人
     * time : 2018-05-28
     * pics : ["http://pic.5iyzy.com/123","http://pic.5iyzy.com/123"]
     */

    private String id;
    private String type;
    private String title;
    private String content;
    private String time;
    private List<String> pics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
