package com.bluemsun.entity.vo;

public class FileVO {

    private String msg;
    private String url;
    private int fileId;

    public FileVO() {
    }

    public FileVO(String msg, String url, int fileId) {
        this.msg = msg;
        this.url = url;
        this.fileId = fileId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
