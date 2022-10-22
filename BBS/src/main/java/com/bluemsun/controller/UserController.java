package com.bluemsun.controller;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bluemsun.dao.mapper.FollowMapper;
import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.Posts;
import com.bluemsun.entity.User;
import com.bluemsun.service.FollowService;
import com.bluemsun.service.UserService;
import com.bluemsun.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String addUser(@RequestBody User user) throws JsonProcessingException {
        String msg = userService.addUser(user);
        int status;
        if(msg.equals("注册成功")) status = 1;
        else status = 0;
        map.clear();
        map.put("status",status);
        map.put("msg",msg);
        return JsonUtil.toJson(map);
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody Map<String,String> m) throws IOException {
        String msg = userService.loginUser(m.get("idNumber"),m.get("password"));
        int status = 1;
        if(msg.equals("登录失败，请检查输入是否有误") || msg.equals("账号已被封禁，无法登录") || msg.equals("输入不能为空")) status = 0;
        map.clear();
        map.put("status",status);
        map.put("msg",msg);
        return JsonUtil.toJson(map);
    }

    @PostMapping (value = "/success/update")
    public String addUserPhoto(@RequestParam("photo") MultipartFile photo, HttpSession session,HttpServletRequest request) throws IOException {
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
        String url = "http://43.140.247.80:8080/photo/"+fileName;
        String msg = userService.addUserPhoto(url,id.asInt());
        map.clear();
        map.put("msg",msg);
        map.put("url",url);
        return JsonUtil.toJson(map);
    }

    @PostMapping(value = "/success/main")
    public String showUserMessage(HttpServletRequest request) throws JsonProcessingException {
        int id = (int) request.getAttribute("id");
        Map<String,Object> map = userService.getUserMessage(id,1);
        return JsonUtil.toJson(map);
    }

    @GetMapping(value = "/success/posts/{index}")
    public String getPostsByUser(@PathVariable int index,HttpServletRequest request) throws JsonProcessingException {
        int id = (int) request.getAttribute("id");
        Page<Posts> postsPage = userService.getPostsByUser(id,index);
        return JsonUtil.toJson(postsPage);
    }

    @PostMapping(value = "/password/update")
    public String updatePassword(HttpServletRequest request,@RequestBody Map<String,String> m) throws JsonProcessingException {
        String password = m.get("password");
        int id = (int) request.getAttribute("id");
        String msg = userService.updatePassword(password,id);
        int status;
        if(!msg.equals("密码修改失败")) status = 1;
        else status = 0;
        map.clear();
        map.put("status",status);
        map.put("msg",msg);
        return JsonUtil.toJson(map);
    }

    @PostMapping(value = "/nickName/update")
    public String updateNickName(HttpServletRequest request,@RequestBody Map<String,String> m) throws JsonProcessingException {
        String nickName = m.get("nickName");
        int id = (int) request.getAttribute("id");
        String msg = userService.updateNickName(nickName,id);
        int status;
        if(!msg.equals("昵称修改失败")) status = 1;
        else status = 0;
        map.clear();
        map.put("msg",msg);
        map.put("status",status);
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/telephone/update")
    public String updateTelephone(HttpServletRequest request,@RequestBody Map<String,Object> m) throws JsonProcessingException {
        String telephone =(String) m.get("telephone");
        int id = (int) request.getAttribute("id");
        String msg = userService.updateTelephone(telephone,id);
        int status;
        if(!msg.equals("电话修改失败")) status = 1;
        else status = 0;
        map.clear();
        map.put("status",status);
        map.put("msg",msg);
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/show/{type}")
    public String showPeople(HttpServletRequest request,@PathVariable String type) throws JsonProcessingException {
        int userId = (int) request.getAttribute("id");
        return JsonUtil.toJson(followService.getPeople(userId,type));
    }

    @RequestMapping(value = "/update/follow/people/{userFollowed}")
    public String updateFollowPeople(HttpServletRequest request,@PathVariable int userFollowed) throws JsonProcessingException {
        int userId = (int)request.getAttribute("id");
        String msg = followService.updateFollowPeople(userId,userFollowed);
        map.clear();
        map.put("msg",msg);
        return JsonUtil.toJson(map);
    }
}
