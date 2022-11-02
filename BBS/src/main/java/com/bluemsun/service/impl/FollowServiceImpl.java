package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.BlockMapper;
import com.bluemsun.dao.mapper.FollowMapper;
import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.Follow;
import com.bluemsun.service.FollowService;
import com.bluemsun.service.InformService;
import com.bluemsun.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.jar.JarEntry;

public class FollowServiceImpl implements FollowService {

    @Autowired FollowMapper followMapper;
    @Autowired UserMapper userMapper;
    @Autowired BlockMapper blockMapper;
    @Autowired InformService informService;


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
            informService.addFollowInform(userId,userFollowed);
            if(i != 0) return "关注成功";
            return "关注失败";
        }else {
            int i = followMapper.deleteFollowPeople(follow);
            if(i != 0) return "已取消关注";
            return "取消失败";
        }
    }

    @Override
    public List<Follow> getFollowBlock(int userId) {
        List<Follow> followList =  followMapper.getFollowBlock(userId);
        for(Follow follow:followList){
            follow.setBlock(blockMapper.showBlockMessage(follow.getBlockId()));
        }
        return followList;
    }

    @Override
    public String updateFollowBlock(int userId, int blockId) {
        Follow follow = new Follow();
        follow.setBlockId(blockId);
        follow.setUserId(userId);
        Jedis jedis = RedisUtil.getJedis();
        if(followMapper.checkFollowBlock(follow) == null){
            int i = followMapper.addFollowBlock(follow);
            if(jedis != null){
                jedis.hset("user_id_"+userId,"block_id_"+blockId,"1");
            }
            RedisUtil.closeJedis(jedis);
            if(i != 0) return "关注成功";
            return "关注失败";
        }else {
            int i = followMapper.deleteFollowBlock(userId,blockId);
            if(jedis != null){
                if(jedis.hexists("user_id_"+userId,"block_id_"+blockId)){
                    jedis.hset("user_id_"+userId,"block_id_"+blockId,"0");
                }
            }
            RedisUtil.closeJedis(jedis);
            if(i != 0) return "取消成功";
            return "取消失败";
        }
    }
}
