package com.bluemsun.entity.vo;

import com.bluemsun.entity.Inform;
import com.bluemsun.entity.Page;

public class InformVO {

    private String nickName;
    private String idPhoto;
    private Page<Inform> page;

    public InformVO(String nickName, String idPhoto, Page<Inform> page) {
        this.nickName = nickName;
        this.idPhoto = idPhoto;
        this.page = page;
    }
}
