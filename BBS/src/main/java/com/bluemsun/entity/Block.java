package com.bluemsun.entity;

import java.util.List;

public class Block {

    private int id;
    private String blockName;
    private int postsNumber;
    private int scanNumber;
    List<Posts> topList;

    private String blockDescribe;

    private int followStatus;

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }

    public String getBlockDescribe() {
        return blockDescribe;
    }

    public void setBlockDescribe(String blockDescribe) {
        this.blockDescribe = blockDescribe;
    }

    public List<Posts> getTopList() {
        return topList;
    }

    public void setTopList(List<Posts> topList) {
        this.topList = topList;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public int getPostsNumber() {
        return postsNumber;
    }

    public void setPostsNumber(int postsNumber) {
        this.postsNumber = postsNumber;
    }

    public int getScanNumber() {
        return scanNumber;
    }

    public void setScanNumber(int scanNumber) {
        this.scanNumber = scanNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
