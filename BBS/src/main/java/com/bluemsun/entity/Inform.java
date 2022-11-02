package com.bluemsun.entity;

public class Inform {

    private int id;
    private int userId;
    private int postsId;
    private int confirmStatus;
    private String body;
    private String type;
    private String date;


    public Inform() {
    }

    public Inform(int userId, int postsId, String body, String type) {
        this.userId = userId;
        this.postsId = postsId;
        this.body = body;
        this.type = type;
    }

    public Inform(int userId, String body, String type) {
        this.userId = userId;
        this.body = body;
        this.type = type;
    }

    public Inform(String body, String type) {
        this.body = body;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(int confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
