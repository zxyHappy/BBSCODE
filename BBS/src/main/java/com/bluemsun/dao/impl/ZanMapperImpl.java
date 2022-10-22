package com.bluemsun.dao.impl;

import com.bluemsun.dao.mapper.ZanMapper;
import com.bluemsun.entity.Zan;
import org.mybatis.spring.SqlSessionTemplate;


public class ZanMapperImpl implements ZanMapper {
    private SqlSessionTemplate sqlSession;
    private ZanMapper zanMapper;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
        zanMapper = sqlSession.getMapper(ZanMapper.class);
    }

    @Override
    public Zan confirmZanByPosts(int userId, int postsId) {
//        ZanMapper zanMapper = sqlSession.getMapper(ZanMapper.class);
        return zanMapper.confirmZanByPosts(userId,postsId);
    }

    @Override
    public Zan confirmZanByOne(int userId, int oneId) {
//        ZanMapper zanMapper = sqlSession.getMapper(ZanMapper.class);
        return zanMapper.confirmZanByOne(userId,oneId);
    }

    @Override
    public Zan confirmZanByTwo(int userId, int twoId) {
//        ZanMapper zanMapper = sqlSession.getMapper(ZanMapper.class);
        return zanMapper.confirmZanByTwo(userId,twoId);
    }

    @Override
    public int zanPosts(int userId, int postsId) {
//        ZanMapper zanMapper = sqlSession.getMapper(ZanMapper.class);
        return zanMapper.zanPosts(userId,postsId);
    }

    @Override
    public int zanOne(int userId, int oneId) {
//        ZanMapper zanMapper = sqlSession.getMapper(ZanMapper.class);
        return zanMapper.zanOne(userId,oneId);
    }

    @Override
    public int zanTwo(int userId, int twoId) {
//        ZanMapper zanMapper = sqlSession.getMapper(ZanMapper.class);
        return zanMapper.zanTwo(userId,twoId);
    }

    @Override
    public int deleteZanPosts(int userId, int postsId) {
//        ZanMapper zanMapper = sqlSession.getMapper(ZanMapper.class);
        return zanMapper.deleteZanPosts(userId,postsId);
    }

    @Override
    public int deleteZanOne(int userId, int oneId) {
//        ZanMapper zanMapper = sqlSession.getMapper(ZanMapper.class);
        return zanMapper.deleteZanOne(userId,oneId);
    }

    @Override
    public int deleteZanTwo(int userId, int twoId) {
//        ZanMapper zanMapper = sqlSession.getMapper(ZanMapper.class);
        return zanMapper.deleteZanTwo(userId,twoId);
    }

    @Override
    public int zanNumberPosts(int postsId) {
//        ZanMapper zanMapper = sqlSession.getMapper(ZanMapper.class);
        return zanMapper.zanNumberPosts(postsId);
    }

    @Override
    public int zanNumberOne(int oneId) {
//        ZanMapper zanMapper = sqlSession.getMapper(ZanMapper.class);
        return zanMapper.zanNumberOne(oneId);
    }

    @Override
    public int zanNumberTwo(int twoId) {
//        ZanMapper zanMapper = sqlSession.getMapper(ZanMapper.class);
        return zanMapper.zanNumberTwo(twoId);
    }
}
