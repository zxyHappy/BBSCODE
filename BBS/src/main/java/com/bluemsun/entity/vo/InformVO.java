package com.bluemsun.entity.vo;

import com.bluemsun.entity.Inform;
import com.bluemsun.entity.Page;

public class InformVO {

    private String nickName;
    private String idPhoto;
    private Page<Inform> page;

    public InformVO() {
    }

    public InformVO(String nickName, String idPhoto, Page<Inform> page) {
        this.nickName = nickName;
        this.idPhoto = idPhoto;
        this.page = page;
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

    public Page<Inform> getPage() {
        return page;
    }

    public void setPage(Page<Inform> page) {
        this.page = page;
    }
}
