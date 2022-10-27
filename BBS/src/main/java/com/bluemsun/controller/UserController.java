package com.bluemsun.controller;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bluemsun.entity.Result;
import com.bluemsun.entity.User;
import com.bluemsun.service.FollowService;
import com.bluemsun.service.UserService;
import com.bluemsun.util.DataUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
@CrossOrigin
@RequestMapping("/user")
@ResponseBody
@Scope("session")
public class UserController {

    private ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    private UserService userService = (UserService) context.getBean("UserService");
    private Map<String,Object> map = new HashMap<>();
    private FollowService followService = (FollowService) context.getBean("FollowService");


    @PostMapping (value = "/add")
    public Result addUser(@RequestBody User user) throws JsonProcessingException {
        String msg = userService.addUser(user);
        int status;
        if(msg.equals("注册成功")) status = 1;
        else status = 0;
        map.clear();
        map.put("status",status);
        map.put("msg",msg);
        return Result.ok().data(map);
    }

    @PostMapping(value = "/login")
    public Result login(@RequestBody Map<String,String> m) throws IOException {
        String msg = userService.loginUser(m.get("idNumber"),m.get("password"));
        int status = 1;
        if(msg.equals("登录失败，请检查输入是否有误") || msg.equals("账号已被封禁，无法登录") || msg.equals("输入不能为空")) status = 0;
        map.clear();
        map.put("status",status);
        map.put("msg",msg);
        return Result.ok().data(map);
    }

    @PostMapping (value = "/success/update")
    public Result addUserPhoto(@RequestParam("photo") MultipartFile photo, HttpSession session,HttpServletRequest request) throws IOException {
        String fileName = photo.getOriginalFilename();
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString() + hzName;
        ServletContext servletContext = session.getServletContext();
        String photoPath = servletContext.getRealPath("photo");
        File file = new File(photoPath);
        if(!file.exists()){
            file.mkdir();
        }
        String finalPath = photoPath + File.separator + fileName;
        photo.transferTo(new File(finalPath));
        DecodedJWT decodedJWT =(DecodedJWT) request.getAttribute("decodedJWT");
        Claim id = decodedJWT.getClaim("id");
        String url = DataUtil.URL+ "/photo/"+fileName;
        String msg = userService.addUserPhoto(url,id.asInt());
        map.clear();
        map.put("msg",msg);
        map.put("url",url);
        return Result.ok().data(map);
    }

    @PostMapping(value = "/success/main")
    public Result showUserMessage(HttpServletRequest request) throws JsonProcessingException {
        int id = (int) request.getAttribute("id");
        return Result.ok().data(userService.getUserMessage(id,1));
    }

    @GetMapping(value = "/success/posts/{index}")
    public Result getPostsByUser(@PathVariable int index,HttpServletRequest request) throws JsonProcessingException {
        int id = (int) request.getAttribute("id");
        return Result.ok().data("postsPage",userService.getPostsByUser(id,index));
    }

    @PostMapping(value = "/password/update")
    public Result updatePassword(HttpServletRequest request,@RequestBody User user) throws JsonProcessingException {
        int id = (int) request.getAttribute("id");
        String msg = userService.updatePassword(user.getPassword(),id);
        int status;
        if(!msg.equals("密码修改失败")) status = 1;
        else status = 0;
        map.clear();
        map.put("status",status);
        map.put("msg",msg);
        return Result.ok().data(map);
    }

    @PostMapping(value = "/nickName/update")
    public Result updateNickName(HttpServletRequest request,@RequestBody User user) throws JsonProcessingException {
        int id = (int) request.getAttribute("id");
        String msg = userService.updateNickName(user.getNickName(),id);
        int status;
        if(!msg.equals("昵称修改失败")) status = 1;
        else status = 0;
        map.clear();
        map.put("msg",msg);
        map.put("status",status);
        return Result.ok().data(map);
    }

    @RequestMapping(value = "/telephone/update")
    public Result updateTelephone(HttpServletRequest request,@RequestBody User user) throws JsonProcessingException {
        int id = (int) request.getAttribute("id");
        String msg = userService.updateTelephone(user.getTelephone(),id);
        return Result.ok().data("msg",msg);
    }

    @RequestMapping(value = "/show/{type}")
    public Result showPeople(HttpServletRequest request,@PathVariable String type) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        return Result.ok().data("personMessage",followService.getPeople(userId,type));
    }

    @RequestMapping(value = "/update/follow/people/{userFollowed}")
    public Result updateFollowPeople(HttpServletRequest request,@PathVariable int userFollowed) throws JsonProcessingException {
        int userId = (int)request.getAttribute("id");
        return Result.ok().data("msg",followService.updateFollowPeople(userId,userFollowed));
    }
}
