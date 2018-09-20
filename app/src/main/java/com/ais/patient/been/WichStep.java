package com.ais.patient.been;

/**
 * Created by Administrator on 2018/8/19 0019.
 */

public class WichStep {

    /**
     * sex : M
     * template : 成年
     * step : 2
     * two_step : 4
     */

    private String sex;
    private String template;
    private int step;
    private int two_step;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getTwo_step() {
        return two_step;
    }

    public void setTwo_step(int two_step) {
        this.two_step = two_step;
    }
}
