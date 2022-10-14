package com.bluemsun.entity;

public class User {
    private int id;
    private String nickName;
    private String userName;
    private String password;
    private int banStatus;
    private String telephone;
    private int masterStatus;
    private String idPhoto;



    public User() {
    }

    public User(int id, String nickName, String userName, String password, int banStatus, String telephone, int masterStatus) {
        this.id = id;
        this.nickName = nickName;
        this.userName = userName;
        this.password = password;
        this.banStatus = banStatus;
        this.telephone = telephone;
        this.masterStatus = masterStatus;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBanStatus() {
        return banStatus;
    }

    public void setBanStatus(int banStatus) {
        this.banStatus = banStatus;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getMasterStatus() {
        return masterStatus;
    }

    public void setMasterStatus(int masterStatus) {
        this.masterStatus = masterStatus;
    }
}
