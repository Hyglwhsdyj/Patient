package com.ais.patient.been;


import java.util.List;

public class InterrogationItemBean {

    private String labelName;
    private List<ItemChildBean> itemChildBeans;

    public InterrogationItemBean() {
    }

    public InterrogationItemBean(String labelName, List<ItemChildBean> itemChildBeans) {
        this.labelName = labelName;
        this.itemChildBeans = itemChildBeans;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public List<ItemChildBean> getItemChildBeans() {
        return itemChildBeans;
    }

    public void setItemChildBeans(List<ItemChildBean> itemChildBeans) {
        this.itemChildBeans = itemChildBeans;
    }
}
