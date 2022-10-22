package com.bluemsun.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bluemsun.util.JWTUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        if(uri.equals("/user/add") || uri.equals("/user/login") ){
            return true;
        }
        if(uri.contains("/posts/show") || uri.contains("/index") || uri.contains("/comment/show") || uri.contains("/block/show")){
            String msg;
            if(token == null || "".equals(token)) {
                msg = "游客";
                request.setAttribute("id",0);
            }
            else {
                msg = "用户";
                DecodedJWT decodedJWT = JWTUtil.decode(token);
                int id = decodedJWT.getClaim("id").asInt();
                request.setAttribute("decodedJWT",decodedJWT);
                request.setAttribute("id",id);
            }
            request.setAttribute("msg",msg);
            request.setAttribute("token",token);
            return true;
        }
        if (token == null || token.equals("")) {
            response.getWriter().println("can not");
            return false;
        }
        DecodedJWT decodedJWT = JWTUtil.decode(token);
        int id = decodedJWT.getClaim("id").asInt();
        request.setAttribute("decodedJWT",decodedJWT);
        request.setAttribute("id",id);
        return true;
    }
}
