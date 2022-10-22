package com.bluemsun.controller;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bluemsun.entity.Posts;
import com.bluemsun.entity.User;
import com.bluemsun.service.PostsService;
import com.bluemsun.util.JWTUtil;
import com.bluemsun.util.JsonUtil;
import com.bluemsun.util.UserCheckUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/posts")
@Scope("session")
public class PostsController {

    private ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    private PostsService postsService =(PostsService) context.getBean("PostsService");
    private Map<String,Object> map = new HashMap<>();

    @RequestMapping(value = "/add")
    public String addPosts(@RequestBody Posts posts, HttpServletRequest request) throws JsonProcessingException {
        int id = (int)request.getAttribute("id");
        posts.setUserId(id);
        String msg =  postsService.addPosts(posts);
        map.clear();
        map.put("msg",msg);
        return JsonUtil.toJson(map);
    }

    @GetMapping (value = "/show/{id}")
    public String showPosts(@PathVariable int id,HttpServletRequest request) throws JsonProcessingException {
        UserCheckUtil.checkUserLogin(request);
        Map<String,Object> map = postsService.showPosts(id);
        map.put("nickName",UserCheckUtil.getNickName());
        map.put("idPhoto",UserCheckUtil.getIdPhoto());
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/zan/update/{postsId}")
    public String updateZan(HttpServletRequest request,@PathVariable int postsId) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        map.put("msg",postsService.updateZan(postsId,userId));
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/update")
    public String updatePosts(HttpServletRequest request,@RequestBody Map<String,Object> m) throws JsonProcessingException {
        int userId =(int) request.getAttribute("id");
        map.clear();
        map.put("msg",postsService.updatePosts(m,userId));
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/delete/{id}")
    public String deletePosts(HttpServletRequest request,@PathVariable int id) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        map.clear();
        map.put("msg",postsService.deletePosts(userId,id));
        return JsonUtil.toJson(map);
    }
    
}
