package com.bluemsun.entity.vo;

public class PostsVO {
    private String msg;
    private int postsId;

    public PostsVO() {
    }

    public PostsVO(String msg, int postsId) {
        this.msg = msg;
        this.postsId = postsId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPostsId() {
        return postsId;
    }

    public void setPostsId(int postsId) {
        this.postsId = postsId;
    }
}
