package com.bluemsun.entity.vo;

import com.bluemsun.entity.Block;

import java.util.List;

public class BlockVO {

    private List<Block> list;
    private String nickName;
    private String idPhoto;

    public BlockVO() {
    }

    public BlockVO(List<Block> list, String nickName, String idPhoto) {
        this.list = list;
        this.nickName = nickName;
        this.idPhoto = idPhoto;
    }

    public List<Block> getList() {
        return list;
    }

    public void setList(List<Block> list) {
        this.list = list;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }
}
