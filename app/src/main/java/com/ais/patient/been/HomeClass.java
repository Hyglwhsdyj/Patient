package com.ais.patient.been;

/**
 * Created by Administrator on 2018/7/30 0030.
 */

public class HomeClass {
    String name;
    int icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public HomeClass(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }
}
