package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.*;
import com.bluemsun.entity.Block;
import com.bluemsun.entity.Posts;
import com.bluemsun.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexServiceImpl implements IndexService {

    @Autowired BlockMapper blockMapper;
    @Autowired PostsMapper postsMapper;
    @Autowired
    LikeMapper likeMapper;
    @Autowired CommentMapper commentMapper;
    @Autowired UserMapper userMapper;

    @Override
    public Map<String, Object> getIndex() {
        List<Block> blockList = blockMapper.getBlocks();
        List<Posts> postsList = postsMapper.getTopPosts();
        for(Block block:blockList){
            int blockId = block.getId();
            block.setTopList(postsMapper.getHotPosts(blockId));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("blockList",blockList);
        map.put("postsList",postsList);
        return map;
    }
}
