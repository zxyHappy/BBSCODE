package com.bluemsun.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bluemsun.util.JWTUtil;
import com.bluemsun.util.RedisUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        Jedis jedis = RedisUtil.getJedis();
        if(uri.equals("/user/add") || uri.equals("/user/login") ){
            RedisUtil.closeJedis(jedis);
            return true;
        }
        if(uri.contains("/posts/show") || uri.contains("/index") || uri.contains("/comment/show") || uri.contains("/block/show") || uri.contains("/user/main/")){
            String msg;
            if(token == null || "".equals(token) || (jedis != null && !jedis.exists("user_token_"+JWTUtil.decode(token).getClaim("id").asInt()))) {
                msg = "游客";
                request.setAttribute("id",0);
            } else {
                msg = "用户";
                DecodedJWT decodedJWT = JWTUtil.decode(token);
                int id = decodedJWT.getClaim("id").asInt();
                if(jedis.ttl("user_token_"+id) <= 24*60*60) jedis.expire("user_token_"+id,7*24*60*60);
                request.setAttribute("decodedJWT",decodedJWT);
                request.setAttribute("id",id);
            }
            request.setAttribute("msg",msg);
            request.setAttribute("token",token);
            RedisUtil.closeJedis(jedis);
            return true;
        }
        if (token == null || token.equals("") || (jedis != null && !jedis.exists("user_token_"+JWTUtil.decode(token).getClaim("id").asInt()))) {
            response.getWriter().println("can not");
            RedisUtil.closeJedis(jedis);
            return false;
        }
        DecodedJWT decodedJWT = JWTUtil.decode(token);
        int id = decodedJWT.getClaim("id").asInt();
        request.setAttribute("decodedJWT",decodedJWT);
        request.setAttribute("id",id);
        if(jedis.ttl("user_token_"+id) <= 24*60*60) jedis.expire("user_token_"+id,7*24*60*60);
        RedisUtil.closeJedis(jedis);
        return true;
    }
}
