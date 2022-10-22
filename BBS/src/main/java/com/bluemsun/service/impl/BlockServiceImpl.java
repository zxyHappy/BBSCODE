package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.BlockMapper;
import com.bluemsun.dao.mapper.PostsMapper;
import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.Block;
import com.bluemsun.entity.BlockMaster;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.Posts;
import com.bluemsun.service.BlockService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    public List<Block> showBlock() {
        List<Block> list = blockMapper.showBlocks();
        for(Block block:list){
            block.setPostsNumber(postsMapper.getNumberByBlock(block.getId()));
            block.setTopList(postsMapper.getHotPosts(block.getId()));
        }
        return list;
    }

    @Override
    public Map<String, Object> showBlockMessage(int blockId) {
        blockMapper.addScan(blockId);
        Block block = blockMapper.showBlockMessage(blockId);
        block.setTopList(postsMapper.getTop(blockId));
        List<BlockMaster> blockMasters = blockMapper.getBlockMaster(blockId);
        for(BlockMaster blockMaster:blockMasters){
            blockMaster.setNickName(userMapper.getUserById(blockMaster.getUserId()).getNickName());
        }
        List<Posts> postsList = postsMapper.getPosts(blockId,0);
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
}
