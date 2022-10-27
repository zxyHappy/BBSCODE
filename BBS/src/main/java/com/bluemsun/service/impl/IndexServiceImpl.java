package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.*;
import com.bluemsun.entity.Block;
import com.bluemsun.entity.Posts;
import com.bluemsun.service.IndexService;
import com.bluemsun.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexServiceImpl implements IndexService {

    @Autowired BlockMapper blockMapper;
    @Autowired PostsMapper postsMapper;
    @Autowired LikeMapper likeMapper;
    @Autowired CommentMapper commentMapper;
    @Autowired UserMapper userMapper;

    @Override
    public Map<String, Object> getIndex(int userId) {
        List<Block> blockList = blockMapper.getBlocks();
        List<Posts> postsList = postsMapper.getTopPosts();
        Jedis jedis = RedisUtil.getJedis();
        for(Block block:blockList){
            int blockId = block.getId();
            block.setTopList(postsMapper.getHotPosts(blockId));
            for(Posts posts:block.getTopList()){
                if(!jedis.hexists("user_id_"+userId,"posts_id_"+posts.getId()) || "0".equals(jedis.hget("user_id_"+userId,"posts_id_"+posts.getId()))){
                    posts.setlikeStatus(0);
                }else posts.setlikeStatus(1);
                if(!jedis.hexists("posts_id_"+posts.getId(),"like_number")) posts.setLikeNumber(0);
                else {
                    int likeNumber = Integer.parseInt(jedis.hget("posts_id_"+posts.getId(),"like_number"));
                    posts.setLikeNumber(likeNumber);
                }
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("blockList",blockList);
        map.put("postsList",postsList);
        return map;
    }

    @Override
    public Map<String, Object> indexSearch(String value) {
        return null;
    }
}
