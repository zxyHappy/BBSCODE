package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.FollowMapper;
import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.Follow;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.Posts;
import com.bluemsun.entity.User;
import com.bluemsun.service.UserService;
import com.bluemsun.util.JWTUtil;
import com.bluemsun.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    @Autowired UserMapper userMapper;
    @Autowired Page<Posts> postsPage;
    @Autowired FollowMapper followMapper;

    @Override
    public String addUser(User user) {
        if(userMapper.getUserByTelephone(user.getTelephone())!=null) return "注册失败，该账号已存在";
        String uid = "";
        for(int i = 0; i <= 5; i++){
            String s = String.valueOf(Math.round(Math.random()*5));
            uid = uid.concat(s);
        }
        while(userMapper.getUserByName(uid) != null){
            for(int i = 0; i <= 5; i++){
                String s = String.valueOf(Math.round(Math.random()*5));
                uid = uid.concat(s);
            }
        }
        user.setUserName(uid);
        int i = userMapper.addUser(user);
        if(i == 1) return "注册成功";
        return "注册失败";
    }

    @Override
    public String loginUser(String idNumber, String password) {
        if(idNumber==null || "".equals(idNumber) || password == null || "".equals(password)){
            return "输入不能为空";
        }
        String msg = "登录失败，请检查输入是否有误";
        User user1 = userMapper.getUserByName(idNumber);
        User user2 = userMapper.getUserByTelephone(idNumber);
        Jedis jedis = RedisUtil.getJedis();
        if(user1!=null){
            if(user1.getPassword().equals(password)){
                if(user1.getBanStatus()==1) msg = "账号已被封禁，无法登录";
                else {
                    msg = JWTUtil.getToken(user1);
                    if(jedis != null){
                        jedis.set("user_token_"+user1.getId(),msg);
                        jedis.expire("user_token_"+user1.getId(),7*24*60*60);
                    }
                }
            }
        }
        if(user2!=null){
            if(user2.getPassword().equals(password)){
                if(user2.getBanStatus()==1) msg = "账号已被封禁，无法登录";
                else {
                    msg = JWTUtil.getToken(user2);
                    if(jedis != null){
                        jedis.set("user_token_"+user2.getId(),msg);
                        jedis.expire("user_token_"+user2.getId(),7*24*60*60);
                    }
                }
            }
        }
        RedisUtil.closeJedis(jedis);
        return msg;
    }

    @Override
    public String addUserPhoto(String idPhoto, int id) {
        Map<String,Object> map = new HashMap<>();
        map.put("idPhoto",idPhoto);
        map.put("id",id);
        if(userMapper.addPhoto(map)==1) {
            return "添加成功";
        }
        return "添加失败";
    }

    @Override
    public Map<String, Object> getUserMessage(int id, int index) {
        Map<String,Object> map = new HashMap<>();
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
        int postsNumber = userMapper.getPostsNumber(id);
        postsPage.setPage(index,4,postsNumber);
        postsPage.setList(userMapper.getPostsByUser(id,postsPage.getStartIndex()));
        return postsPage;
    }

    @Override
    public String updatePassword(String password, int id) {
        int i = userMapper.updatePassword(password,id);
        if(i != 0) {
            return "密码修改成功";
        }
        return "密码修改失败";
    }

    @Override
    public String updateNickName(String nickName, int id) {
        int i = userMapper.updateNickName(nickName,id);
        if(i != 0) {
            return "昵称修改成功";
        }
        return "昵称修改失败";
    }

    @Override
    public String updateTelephone(String telephone, int id) {
        int i = userMapper.updateTelephone(telephone,id);
        if(i!=0) {
            return "电话修改成功";
        }
        return "电话修改失败";
    }

    @Override
    public User getUserById(int id) {
        User user = userMapper.getUserById(id);
        if(user == null){
            user = new User();
            user.setNickName(null);
            user.setIdPhoto(null);
        }
        return user;
    }

    @Override
    public Map<String, Object> getUserMessage(int userId, int index, int id) {
        Follow follow = new Follow(id,userId);
        Map<String,Object> map = new HashMap<>();
        User user = userMapper.getUserById(id);
        if(followMapper.checkFollowPeople(follow) == null) user.setFollowStatus(0);
        else user.setFollowStatus(1);
        Page<Posts> postsPage = getPostsByUser(id,index);
        map.put("user",user);
        map.put("postsNumber",postsPage.getTotalRecord());
        map.put("totalPage",postsPage.getTotalPage());
        map.put("postsList",postsPage.getList());
        return map;
    }
}
