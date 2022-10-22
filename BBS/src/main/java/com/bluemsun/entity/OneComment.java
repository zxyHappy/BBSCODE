package com.bluemsun.entity;

public class OneComment {
    private int id;
    private int userId;
    private int postsId;
    private String date;
    private String body;
    private User user;
    private int zanNumber;

    public int getZanNumber() {
        return zanNumber;
    }

    public void setZanNumber(int zanNumber) {
        this.zanNumber = zanNumber;
    }

    private int zanStatus;

    public int getZanStatus() {
        return zanStatus;
    }

    public void setZanStatus(int zanStatus) {
        this.zanStatus = zanStatus;
    }

    public void setComment(int userId, int postsId, String body){
        this.body = body;
        this.userId = userId;
        this.postsId = postsId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostsId() {
        return postsId;
    }

    public void setPostsId(int postsId) {
        this.postsId = postsId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
