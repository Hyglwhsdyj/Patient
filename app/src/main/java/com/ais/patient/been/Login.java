package com.ais.patient.been;

/**
 * Created by Administrator on 2018/7/29 0029.
 */

public class Login {

    /**
     * code : 200
     * message : 成功
     * userid : 5af5505d31425014b02cf381
     * token : eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1MjYxMTY1MjUsInN1YiI6IjE3NjAzMDU2NjgxIiwiZXhwIjoxNTI2NzIxMzI1fQ.z_wHhqQFWtRGeSnKUtcg56eWnMXaSLXMmlGTTbynFKQsmgiYXJDAHbB41_MLKXaFasonM3HflBYZmM_20lxQEQ
     */

    private int code;
    private String message;
    private String userid;
    private String token;
    private String appId;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
