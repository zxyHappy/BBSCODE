package com.bluemsun.service;

import com.bluemsun.entity.Block;
import com.bluemsun.entity.Posts;

import java.util.List;
import java.util.Map;

public interface BlockService {

    /**
     * 创建板块
     * @param blockName
     * @return
     */
    String addBlock(String blockName,String describe);

    /**
     * 板块页面展示所有板块
     * @return
     */
    List<Block> showBlock();

    /**
     * 获取板块详情页
     * @param blockId
     * @return
     */
    Map<String,Object> showBlockMessage(int blockId);

    /**
     * 获取板块界面某也的帖子
     * @param blockId
     * @param index
     * @return
     */
    List<Posts> showPostsPage(int blockId,int index);
}
