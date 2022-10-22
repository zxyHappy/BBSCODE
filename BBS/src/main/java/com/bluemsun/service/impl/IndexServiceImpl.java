package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.*;
import com.bluemsun.entity.Block;
import com.bluemsun.entity.Posts;
import com.bluemsun.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexServiceImpl implements IndexService {

    @Autowired BlockMapper blockMapper;
    @Autowired PostsMapper postsMapper;
    @Autowired ZanMapper zanMapper;
    @Autowired CommentMapper commentMapper;

    @Override
    public Map<String, Object> getIndex() {
        List<Block> blockList = blockMapper.getBlocks();
        List<Posts> postsList = postsMapper.getTopPosts();
//        for(Posts posts:postsList){
//            posts.setLikeNumber(zanMapper.zanNumberPosts(posts.getId()));
//            posts.setReplyNumber(commentMapper.getOneCommentNumber(posts.getId()));
//        }
        for(Block block:blockList){
            int blockId = block.getId();
            block.setTopList(postsMapper.getHotPosts(blockId));
//            for(Posts posts:block.getTopList()){
//                posts.setLikeNumber(zanMapper.zanNumberPosts(posts.getId()));
//                posts.setReplyNumber(commentMapper.getOneCommentNumber(posts.getId()));
//            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("blockList",blockList);
        map.put("postsList",postsList);
        return map;
    }
}
