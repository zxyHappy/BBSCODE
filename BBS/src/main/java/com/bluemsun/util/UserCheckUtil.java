package com.bluemsun.util;

import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;

public class UserCheckUtil {

    private static String msg;
    private static String token;
    private static String nickName;
    private static String idPhoto;
    private static DecodedJWT decodedJWT;

    public static String getMsg() {
        return msg;
    }

    public static void setMsg(String msg) {
        UserCheckUtil.msg = msg;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserCheckUtil.token = token;
    }

    public static String getNickName() {
        return nickName;
    }

    public static void setNickName(String nickName) {
        UserCheckUtil.nickName = nickName;
    }

    public static String getIdPhoto() {
        return idPhoto;
    }

    public static void setIdPhoto(String idPhoto) {
        UserCheckUtil.idPhoto = idPhoto;
    }

    public static DecodedJWT getDecodedJWT() {
        return decodedJWT;
    }

    public static void setDecodedJWT(DecodedJWT decodedJWT) {
        UserCheckUtil.decodedJWT = decodedJWT;
    }

    public static void checkUserLogin(HttpServletRequest request){
        msg = (String) request.getAttribute("msg");
        token = (String) request.getAttribute("token");
        if(msg.equals("用户")){
            decodedJWT = JWTUtil.decode(token);
            nickName = decodedJWT.getClaim("nickName").asString();
            idPhoto = decodedJWT.getClaim("idPhoto").asString();
        }else {
            nickName = idPhoto = null;
        }
    }

}
