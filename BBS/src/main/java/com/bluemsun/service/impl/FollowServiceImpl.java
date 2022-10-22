package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.FollowMapper;
import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.Follow;
import com.bluemsun.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class FollowServiceImpl implements FollowService {

    @Autowired FollowMapper followMapper;
    @Autowired UserMapper userMapper;


    @Override
    public List<Follow> getPeople(int userId,String type) {
        if(type.equals("follower")){
            List<Follow> list = followMapper.getFollower(userId);
            for(Follow follow:list){
                follow.setUser(userMapper.getUserById(follow.getUserFollowed()));
            }
            return list;
        }else if(type.equals("fans")) {
            List<Follow> list =  followMapper.getFans(userId);
            for(Follow follow:list){
                follow.setUser(userMapper.getUserById(follow.getUserId()));
            }
            return list;
        }
        else return null;
    }


    @Override
    public String updateFollowPeople(int userId, int userFollowed) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Follow follow = (Follow) context.getBean("Follow");
        follow.setUserId(userId);
        follow.setUserFollowed(userFollowed);
        if(followMapper.checkFollowPeople(follow) == null){
            int i = followMapper.addFollowPeople(follow);
            if(i != 0) return "关注成功";
            return "关注失败";
        }else {
            int i = followMapper.deleteFollowPeople(follow);
            if(i != 0) return "已取消关注";
            return "取消失败";
        }
    }
}
