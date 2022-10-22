package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.FollowMapper;
import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.Follow;
import com.bluemsun.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;

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


}
