package com.bluemsun.entity;

public class Like {

    private int id;
    private int userId;
    private int oneId;
    private int postsId;
    private int twoId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOneId() {
        return oneId;
    }

    public void setOneId(int oneId) {
        this.oneId = oneId;
    }

    public int getPostsId() {
        return postsId;
    }

    public void setPostsId(int postsId) {
        this.postsId = postsId;
    }

    public int getTwoId() {
        return twoId;
    }

    public void setTwoId(int twoId) {
        this.twoId = twoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
