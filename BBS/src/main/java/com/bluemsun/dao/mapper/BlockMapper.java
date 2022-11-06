package com.bluemsun.dao.mapper;

import com.bluemsun.entity.Block;
import com.bluemsun.entity.BlockMaster;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public interface BlockMapper {

    /**
     *
     * @param id 板块id
     * @return 板块名
     */
    String getBlockName(@Param("id") int id);

    /**
     * 获取首页热门板块
     * @return
     */
    List<Block> getBlocks();

    /**
     * 发布板块
     * @param blockName 板块名
     * @param describe 描述
     * @return
     */
    int addBlock(@Param("blockName") String blockName,@Param("blockDescribe") String describe);

    /**
     * 展示板块
     * @return
     */
    List<Block> showBlocks();

    /**
     * 浏览量+1
     * @param id
     */
    void addScan(@Param("id") int id);

    Block showBlockMessage(@Param("id") int id);

    List<BlockMaster> getBlockMaster(@Param("blockId") int blockId);

    int addPostsNumber(@Param("id") int id);

    int deletePostsNumber(@Param("id") int id);

    List<Block> searchBlock(@Param("value") String value);

    BlockMaster checkMaster(@Param("blockId") int blockId,@Param("userId") int userId);

    int deleteBlock(@Param("blockId") int blockId);

    List<BlockMaster> getBlockByMaster(@Param("userId") int userId);

    Block getBlockByName(@Param("blockName") String blockName);
}
