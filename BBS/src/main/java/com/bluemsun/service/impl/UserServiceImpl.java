package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.Posts;
import com.bluemsun.entity.User;
import com.bluemsun.service.UserService;
import com.bluemsun.util.JWTUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {



    @Override
    public String addUser(User user) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserMapper userMapper =(UserMapper) context.getBean("UserMapper");
        if(userMapper.getUserByTelephone(user.getTelephone())!=null) return "注册失败，该账号已存在";
        String uid = "";
        for(int i = 0; i <= 9; i++){
            String s = String.valueOf(Math.round(Math.random()*9));
            uid = uid.concat(s);
        }
        user.setUserName(uid);
        int i = userMapper.addUser(user);
        if(i == 1) return "注册成功";
        return "注册失败";
    }

    @Override
    public String loginUser(String idNumber, String password) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserMapper userMapper = (UserMapper) context.getBean("UserMapper");
        String msg = "登录失败，请检查输入是否有误";
        User user1 = userMapper.getUserByName(idNumber);
        User user2 = userMapper.getUserByTelephone(idNumber);
        if(user1!=null){
            if(user1.getPassword().equals(password)){
                if(user1.getBanStatus()==1) msg = "账号已被封禁，无法登录";
                else msg = JWTUtil.getToken(user1);
            }
        }
        if(user2!=null){
            if(user2.getPassword().equals(password)){
                if(user2.getBanStatus()==1) msg = "账号已被封禁，无法登录";
                else msg = JWTUtil.getToken(user2);
            }
        }
        return msg;
    }

    @Override
    public String addUserPhoto(String idPhoto, int id) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserMapper userMapper = (UserMapper) context.getBean("UserMapper");
        Map<String,Object> map = new HashMap<>();
        map.put("idPhoto",idPhoto);
        map.put("id",id);
        if(userMapper.addPhoto(map)==1) return "添加成功";
        return "添加失败";
    }

    @Override
    public Map<String, Object> getUserMessage(int id, int index) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Map<String,Object> map = new HashMap<>();
        UserMapper userMapper = (UserMapper) context.getBean("UserMapper");
        User user = userMapper.getUserById(id);
        Page<Posts> postsPage = getPostsByUser(id,index);
        map.put("user",user);
        map.put("postsNumber",postsPage.getTotalRecord());
        map.put("totalPage",postsPage.getTotalPage());
        map.put("postsList",postsPage.getList());
        return map;
    }

    @Override
    public Page<Posts> getPostsByUser(int id, int index) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserMapper userMapper = (UserMapper) context.getBean("UserMapper");
        int postsNumber = userMapper.getPostsNumber(id);
        Page<Posts> postsPage = (Page<Posts>) context.getBean("myPostsPage");
        postsPage.setPage(index,4,postsNumber);
        postsPage.setList(userMapper.getPostsByUser(id,postsPage.getStartIndex()));
        return postsPage;
    }
}
