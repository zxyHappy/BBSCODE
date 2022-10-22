package com.bluemsun.controller;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bluemsun.entity.OneComment;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.TwoComment;
import com.bluemsun.service.CommentService;
import com.bluemsun.util.JWTUtil;
import com.bluemsun.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    public String addOneComment(HttpServletRequest request, @RequestBody Map<String,Object> m) throws JsonProcessingException {
        int userId = (int)request.getAttribute("id");
        int postsId =(int) m.get("postsId");
        String body = (String) m.get("body");
        map.clear();
        map.put("msg",commentService.addOneComment(userId,postsId,body));
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/add/two")
    public String addTwoComment(HttpServletRequest request,@RequestBody Map<String,Object> m) throws JsonProcessingException {
        int useridSend = (int)request.getAttribute("id");
        int oneId = (int)m.get("oneId");
        int useridReply =(int) m.get("useridReply");
        String body = (String) m.get("body");
        map.clear();
        map.put("msg",commentService.addTwoComment(oneId,useridSend,useridReply,body));
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/show/one/{postsId}/{index}")
    public String showOneComment(@PathVariable int postsId,@PathVariable int index,HttpServletRequest request) throws JsonProcessingException {
        Page<OneComment> commentPage = commentService.getOneComment(postsId,index);
        String msg = (String) request.getAttribute("msg");
        if(msg.equals("用户")){
            String token =(String) request.getAttribute("token");
            DecodedJWT decodedJWT = JWTUtil.decode(token);
            Claim userId = decodedJWT.getClaim("id");
            commentService.setLikeStatusOne(userId.asInt(),commentPage);
        }
        return JsonUtil.toJson(commentPage);
    }

    @RequestMapping(value = "/show/two/{oneId}/{index}")
    public String showTwoComment(@PathVariable int oneId,@PathVariable int index,HttpServletRequest request) throws JsonProcessingException {
        Page<TwoComment> commentPage = commentService.getTwoComment(oneId,index);
        String msg = (String) request.getAttribute("msg");
        if(msg.equals("用户")){
            String token =(String) request.getAttribute("token");
            DecodedJWT decodedJWT = JWTUtil.decode(token);
            Claim userId = decodedJWT.getClaim("id");
            commentService.setLikeStatusTwo(userId.asInt(),commentPage);
        }
        return JsonUtil.toJson(commentPage);
    }

    @RequestMapping(value = "/update/zan/one/{oneId}")
    public String updateOneZan(HttpServletRequest request,@PathVariable int oneId) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        map.clear();
        map.put("msg",commentService.updateLikeOne(oneId,userId));
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/update/zan/two/{twoId}")
    public String updateTwoZan(HttpServletRequest request,@PathVariable int twoId) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        map.clear();
        map.put("msg",commentService.updateLikeTwo(twoId,userId));
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/delete/{type}/{id}")
    public String deleteComment(HttpServletRequest request,@PathVariable int id,@PathVariable String type) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        String msg = null;
        if(type.equals("one")) msg = commentService.deleteOneComment(userId,id);
        else if(type.equals("two")) msg = commentService.deleteTwoComment(userId,id);
        else msg = "url有误";
        map.clear();
        map.put("msg",msg);
        return JsonUtil.toJson(map);
    }
}
