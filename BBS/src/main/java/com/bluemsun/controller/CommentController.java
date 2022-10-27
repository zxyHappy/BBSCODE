package com.bluemsun.controller;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bluemsun.entity.ChildComment;
import com.bluemsun.entity.Comment;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.Result;
import com.bluemsun.service.CommentService;
import com.bluemsun.util.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/comment")
@Scope("session")
public class CommentController {

    private ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    private CommentService commentService = (CommentService) context.getBean("CommentService");
    private Map<String,Object> map = new HashMap<>();


    @RequestMapping(value = "/add/one")
    public Result addOneComment(HttpServletRequest request, @RequestBody Map<String,Object> m) throws JsonProcessingException {
        int userId = (int)request.getAttribute("id");
        int postsId =(int) m.get("postsId");
        String body = (String) m.get("body");
        map.clear();
        map.put("msg",commentService.addOneComment(userId,postsId,body));
        return Result.ok().data(map);
    }

    @RequestMapping(value = "/add/two")
    public Result addTwoComment(HttpServletRequest request,@RequestBody ChildComment comment) throws JsonProcessingException {
        comment.setUseridSend((int)request.getAttribute("id"));
        return Result.ok().data("msg",commentService.addTwoComment(comment));
    }

    @GetMapping(value = "/show/one/{postsId}")
    public Result showOneComment(@PathVariable int postsId,HttpServletRequest request) throws JsonProcessingException {
        int userId =(int) request.getAttribute("id");
        List<Comment> commentList = commentService.getOneComment(postsId,userId);
        String msg = (String) request.getAttribute("msg");
        if(msg.equals("用户")){
            commentService.setLikeStatusOne(userId,commentList);
        }
        return Result.ok().data("commentPage",commentList);
    }

    @GetMapping(value = "/show/two/{oneId}")
    public Result showTwoComment(@PathVariable int oneId,HttpServletRequest request) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        List<ChildComment> commentList = commentService.getTwoComment(oneId,userId);
        String msg = (String) request.getAttribute("msg");
        if(msg.equals("用户")){
            commentService.setLikeStatusTwo(userId,commentList);
        }
        return Result.ok().data("commentPage",commentList);
    }

    @RequestMapping(value = "/update/like/one/{oneId}")
    public Result updateOnelike(HttpServletRequest request,@PathVariable int oneId) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        return Result.ok().data("msg",commentService.updateLikeOne(oneId,userId));
    }

    @RequestMapping(value = "/update/like/two/{twoId}")
    public Result updateTwolike(HttpServletRequest request,@PathVariable int twoId) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        return Result.ok().data("msg",commentService.updateLikeTwo(twoId,userId));
    }

    @RequestMapping(value = "/delete/{type}/{id}")
    public Result deleteComment(HttpServletRequest request,@PathVariable int id,@PathVariable String type) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        String msg = null;
        if(type.equals("one")) msg = commentService.deleteOneComment(userId,id);
        else if(type.equals("two")) msg = commentService.deleteTwoComment(userId,id);
        else msg = "url有误";
        map.clear();
        map.put("msg",msg);
        return Result.ok().data(map);
    }
}
