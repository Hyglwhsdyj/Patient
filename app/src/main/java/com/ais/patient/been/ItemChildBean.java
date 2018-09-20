package com.ais.patient.been;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemChildBean {
    private String title;
    private List<String> flowList;

    public ItemChildBean() {
    }

    public ItemChildBean(String title, List<String> flowList) {
        this.title = title;
        this.flowList = flowList;
    }

    public ItemChildBean(String title, String flowItem) {
        this.title = title;

        List<String> list = new ArrayList<>();
        if (TextUtils.isEmpty(flowItem)) {
            flowItem = "（空）";
        }
        list.add(flowItem);
        this.flowList = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getFlowList() {
        return flowList;
    }

    public void setFlowList(List<String> flowList) {
        this.flowList = flowList;
    }
}
