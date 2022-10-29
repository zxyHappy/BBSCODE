package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.BlockMapper;
import com.bluemsun.dao.mapper.PostsMapper;
import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.Block;
import com.bluemsun.entity.BlockMaster;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.Posts;
import com.bluemsun.service.BlockService;
import com.bluemsun.util.RedisUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BlockServiceImpl implements BlockService {

    @Autowired BlockMapper blockMapper;
    @Autowired PostsMapper postsMapper;
    @Autowired UserMapper userMapper;
    @Autowired Page<Posts> postsPage;

    @Override
    public String addBlock(String blockName,String describe) {
        int i = blockMapper.addBlock(blockName,describe);
        if(i != 0) return "添加成功";
        return "添加失败";
    }

    @Override
    public List<Block> showBlock(int userId) {
        List<Block> list = blockMapper.showBlocks();
        for(Block block:list){
            block.setPostsNumber(postsMapper.getNumberByBlock(block.getId()));
            block.setTopList(postsMapper.getHotPosts(block.getId()));
            setPosts(block.getTopList(),userId);
        }
        return list;
    }

    @Override
    public Map<String, Object> showBlockMessage(int blockId,int userId) {
//        blockMapper.addScan(blockId);
        Jedis jedis = RedisUtil.getJedis();
        if(jedis != null){
            if(!jedis.exists("block_id_"+blockId)) jedis.set("block_id_"+blockId,"0");
            jedis.incr("block_id_"+blockId);
        }
        Block block = blockMapper.showBlockMessage(blockId);
        block.setTopList(postsMapper.getTop(blockId));
        List<BlockMaster> blockMasters = blockMapper.getBlockMaster(blockId);
        for(BlockMaster blockMaster:blockMasters){
            blockMaster.setNickName(userMapper.getUserById(blockMaster.getUserId()).getNickName());
        }
        List<Posts> postsList = postsMapper.getPosts(blockId,0);
        setPosts(postsList,userId);
        Map<String,Object> map = new HashMap<>();
        map.put("block",block);
        map.put("blockMasters",blockMasters);
        map.put("postsList",postsList);
        return map;
    }

    @Override
    public List<Posts> showPostsPage(int blockId, int index) {
        int number = postsMapper.getNumberByBlock(blockId);
        postsPage.setPage(index,10,number);
        return postsMapper.getPosts(blockId,postsPage.getStartIndex());
    }

    public void setPosts(List<Posts> postsList,int userId){
        Jedis jedis = RedisUtil.getJedis();
        if(jedis != null){
            for(Posts posts:postsList){
                if (!jedis.hexists("user_id_" + userId, "posts_id_" + posts.getId()) || "0".equals(jedis.hget("user_id_" + userId, "posts_id_" + posts.getId()))) {
                    posts.setlikeStatus(0);
                } else posts.setlikeStatus(1);
                if (!jedis.hexists("posts_id_" + posts.getId(), "like_number")) posts.setLikeNumber(0);
                else {
                    int likeNumber = Integer.parseInt(jedis.hget("posts_id_" + posts.getId(), "like_number"));
                    posts.setLikeNumber(likeNumber);
                }
            }
        }
    }
}
