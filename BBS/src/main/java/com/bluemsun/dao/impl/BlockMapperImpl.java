package com.bluemsun.dao.impl;

import com.bluemsun.dao.mapper.BlockMapper;
import com.bluemsun.dao.mapper.PostsMapper;
import com.bluemsun.entity.Block;
import com.bluemsun.entity.BlockMaster;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class BlockMapperImpl implements BlockMapper {

    private SqlSessionTemplate sqlSession;
    private BlockMapper blockMapper;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
        blockMapper = sqlSession.getMapper(BlockMapper.class);
    }

    @Override
    public String getBlockName(int id) {
//        BlockMapper blockMapper = sqlSession.getMapper(BlockMapper.class);
        return blockMapper.getBlockName(id);
    }

    @Override
    public List<Block> getBlocks() {
//        BlockMapper blockMapper = sqlSession.getMapper(BlockMapper.class);
        return blockMapper.getBlocks();
    }

    @Override
    public int addBlock(String blockName,String describe) {
//        BlockMapper blockMapper = sqlSession.getMapper(BlockMapper.class);
        return blockMapper.addBlock(blockName,describe);
    }

    @Override
    public List<Block> showBlocks() {
//        BlockMapper blockMapper = sqlSession.getMapper(BlockMapper.class);
        return blockMapper.showBlocks();
    }

    @Override
    public void addScan(int id) {
//        BlockMapper blockMapper = sqlSession.getMapper(BlockMapper.class);
        blockMapper.addScan(id);
    }

    @Override
    public Block showBlockMessage(int id) {
//        BlockMapper blockMapper = sqlSession.getMapper(BlockMapper.class);
        return blockMapper.showBlockMessage(id);
    }

    @Override
    public List<BlockMaster> getBlockMaster(int blockId) {
//        BlockMapper blockMapper = sqlSession.getMapper(BlockMapper.class);
        return blockMapper.getBlockMaster(blockId);
    }
    @Override
    public int addPostsNumber(int id) {
//        BlockMapper blockMapper = sqlSession.getMapper(BlockMapper.class);
        return blockMapper.addPostsNumber(id);
    }

    @Override
    public int deletePostsNumber(int id) {
//        BlockMapper blockMapper = sqlSession.getMapper(BlockMapper.class);
        return blockMapper.deletePostsNumber(id);
    }

    @Override
    public List<Block> searchBlock(String value) {
        return blockMapper.searchBlock(value);
    }

    @Override
    public BlockMaster checkMaster(int blockId, int userId) {
        return blockMapper.checkMaster(blockId,userId);
    }

    @Override
    public int deleteBlock(int blockId) {
        return blockMapper.deleteBlock(blockId);
    }

    @Override
    public List<BlockMaster> getBlockByMaster(int userId) {
        return blockMapper.getBlockByMaster(userId);
    }

    @Override
    public Block getBlockByName(String blockName) {
        return blockMapper.getBlockByName(blockName);
    }
}
