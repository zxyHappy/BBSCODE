package com.bluemsun.controller;

import com.bluemsun.entity.Posts;
import com.bluemsun.entity.Result;
import com.bluemsun.service.PostsService;
import com.bluemsun.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

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
    private UserService userService = (UserService) context.getBean("UserService");

    @RequestMapping(value = "/add")
    public Result addPosts(@RequestBody Posts posts, HttpServletRequest request) throws JsonProcessingException {
        int id = (int)request.getAttribute("id");
        posts.setUserId(id);
        map.clear();
        map.put("msg",postsService.addPosts(posts));
        map.put("postsId",posts.getId());
        return Result.ok().data(map);
    }

    @GetMapping (value = "/show/{id}")
    public Result showPosts(@PathVariable int id,HttpServletRequest request) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        Map<String,Object> map = postsService.showPosts(id,userId);
        map.put("nickName",userService.getUserById(userId).getNickName());
        map.put("idPhoto",userService.getUserById(userId).getIdPhoto());
        return Result.ok().data(map);
    }

    @RequestMapping(value = "/like/update/{postsId}")
    public Result updateLike(HttpServletRequest request,@PathVariable int postsId) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        return Result.ok().data("msg",postsService.updateLike(postsId,userId));
    }

    @RequestMapping(value = "/update")
    public Result updatePosts(HttpServletRequest request,@RequestBody Map<String,Object> m) throws JsonProcessingException {
        int userId =(int) request.getAttribute("id");
        return Result.ok().data("msg",postsService.updatePosts(m,userId));
    }

    @RequestMapping(value = "/delete/{id}")
    public Result deletePosts(HttpServletRequest request,@PathVariable int id) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        return Result.ok().data("msg",postsService.deletePosts(userId,id));
    }

    @GetMapping(value = "/show/drafts")
    public Result showDrafts(HttpServletRequest request){
        int userId = (int) request.getAttribute("id");
        return Result.ok().data(postsService.showDrafts(userId));
    }

    @PostMapping(value = "/draft/add")
    public Result addDraft(@RequestBody Posts posts,HttpServletRequest request){
        int userId = (int) request.getAttribute("id");
        posts.setUserId(userId);
        return Result.ok().data(postsService.addDraft(posts));
    }

    @GetMapping(value = "/draft/send/{postsId}")
    public Result sendPosts(@PathVariable int postsId){
        return Result.ok().data(postsService.setPostsStatus(postsId));
    }

    @GetMapping(value = "/confirm/{postsId}/{msg}")
    public Result confirmPosts(@PathVariable int postsId,@PathVariable String msg,HttpServletRequest request){
        int userId = (int) request.getAttribute("id");
        return Result.ok().data(postsService.confirmPosts(postsId,msg,userId));
    }
}
