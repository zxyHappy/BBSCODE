package com.bluemsun.entity;

public class Follow {

    private int id;
    private int userId;
    private int userFollowed;
    private int blockId;

    private User user;

    private Block block;

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
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

    public Follow(int userId, int userFollowed) {
        this.userId = userId;
        this.userFollowed = userFollowed;
    }
}
