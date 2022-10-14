package com.bluemsun.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bluemsun.util.JWTUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserCheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin," +
                        "Access-Control-Request-Headers," +
                        "Access-Control-Allow-Headers," +
                        "Content-Type," +
                        "Keep-Alive," +
                        "User-Agent," +
                        "Cache-Control," +
                        "Cookie," +
                        "token," +

                        "DNT," +
                        "X-Requested-With," +
                        "X-Mx-ReqToken," +
                        "X-Requested-With," +
                        "If-Modified-Since," +
                        "Accept," +
                        "Connection," +
                        "X-XSRF-TOKEN," +
                        "X-CSRF-TOKEN," +
                        "Authorization");

        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");

        response.setHeader("Access-Control-Max-Age", "86400");

        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Access-Control-Expose-Headers", "token");
//        if(request.getAttribute("Authorization") == null ||"".equals(request.getAttribute("Authorization"))) response.sendError(404);
        String token = request.getHeader("Authorization");
        DecodedJWT decodedJWT = JWTUtil.decode(token);
        request.setAttribute("decodedJWT",decodedJWT);
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
