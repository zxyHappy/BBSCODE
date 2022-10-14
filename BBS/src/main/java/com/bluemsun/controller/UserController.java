package com.bluemsun.controller;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.User;
import com.bluemsun.service.UserService;
import com.bluemsun.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
public class UserController {


    @RequestMapping(value = "/add")
    public String addUser(@RequestBody User user) throws JsonProcessingException {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService userService = (UserService) context.getBean("UserService");
        String msg = userService.addUser(user);
        int status;
        if(msg.equals("注册成功")) status = 1;
        else status = 0;
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        map.put("msg",msg);
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/login")
    public String login(@RequestBody Map<String,String> m) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService userService = (UserService) context.getBean("UserService");
        String msg = userService.loginUser(m.get("idNumber"),m.get("password"));
        int status = 1;
        if(msg.equals("登录失败，请检查输入是否有误") || msg.equals("账号已被封禁，无法登录")) status = 0;
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        map.put("msg",msg);
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/success/addphoto")
    public String addUserPhoto(MultipartFile photo, HttpSession session,HttpServletRequest request) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
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
        UserService userService = (UserService) context.getBean("UserService");
        DecodedJWT decodedJWT =(DecodedJWT) request.getAttribute("decodedJWT");
        Claim id = decodedJWT.getClaim("id");
        String msg = userService.addUserPhoto(finalPath,id.asInt());
        Map<String,String> map = new HashMap<>();
        map.put("msg",msg);
        map.put("url",finalPath);
        return JsonUtil.toJson(map);
    }

    @RequestMapping(value = "/success/main")
    public String showUserMessage(HttpServletRequest request) throws JsonProcessingException {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");
        Claim id = decodedJWT.getClaim("id");
        UserService userService = (UserService) context.getBean("UserService");
        Map<String,Object> map = userService.getUserMessage(id.asInt(),1);
        return JsonUtil.toJson(map);
    }
}
