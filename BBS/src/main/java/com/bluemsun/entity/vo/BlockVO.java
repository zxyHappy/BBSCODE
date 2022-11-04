package com.bluemsun.entity.vo;

import com.bluemsun.entity.Block;

import java.util.List;

public class BlockVO {

    private List<Block> list;
    private String nickName;
    private String idPhoto;

    public BlockVO(List<Block> list, String nickName, String idPhoto) {
        this.list = list;
        this.nickName = nickName;
        this.idPhoto = idPhoto;
    }
}
