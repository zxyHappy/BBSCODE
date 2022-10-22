package com.bluemsun.entity;

public class Follow {

    private int id;
    private int userId;
    private int userFollowed;
    private int blockId;


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

    public int getUserFollowed() {
        return userFollowed;
    }

    public void setUserFollowed(int userFollowed) {
        this.userFollowed = userFollowed;
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }
}
