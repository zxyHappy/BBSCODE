package com.bluemsun.entity.vo;

public class UserVO {
    private String msg;
    private int status;
    private String url;

    public UserVO() {
    }

    public UserVO(String msg, int status, String url) {
        this.msg = msg;
        this.status = status;
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
