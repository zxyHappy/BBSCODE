package com.bluemsun.entity;

public class TwoComment {

    private int id;
    private int oneId;
    private String date;
    private int useridSend;

    private User userSend;
    private User userReply;

    private int useridReply;
    private String body;

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

    public void setComment(int oneId, int useridSend, int useridReply, String body){
        this.oneId = oneId;
        this.useridSend = useridSend;
        this.useridReply = useridReply;
        this.body = body;
    }

    public int getUseridSend() {
        return useridSend;
    }

    public void setUseridSend(int useridSend) {
        this.useridSend = useridSend;
    }

    public int getUseridReply() {
        return useridReply;
    }

    public void setUseridReply(int useridReply) {
        this.useridReply = useridReply;
    }

    public User getUserReply() {
        return userReply;
    }

    public void setUserReply(User userReply) {
        this.userReply = userReply;
    }

    public User getUserSend() {
        return userSend;
    }

    public void setUserSend(User userSend) {
        this.userSend = userSend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOneId() {
        return oneId;
    }

    public void setOneId(int oneId) {
        this.oneId = oneId;
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
