package com.bluemsun.dao.impl;

import com.bluemsun.dao.mapper.LikeMapper;
import com.bluemsun.entity.Like;
import org.mybatis.spring.SqlSessionTemplate;


public class LikeMapperImpl implements LikeMapper {
    private SqlSessionTemplate sqlSession;
    private LikeMapper likeMapper;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
        likeMapper = sqlSession.getMapper(LikeMapper.class);
    }

    @Override
    public Like confirmLikeByPosts(int userId, int postsId) {
//        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        return likeMapper.confirmLikeByPosts(userId,postsId);
    }

    @Override
    public Like confirmLikeByOne(int userId, int oneId) {
//        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        return likeMapper.confirmLikeByOne(userId,oneId);
    }

    @Override
    public Like confirmLikeByTwo(int userId, int twoId) {
//        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        return likeMapper.confirmLikeByTwo(userId,twoId);
    }

    @Override
    public int likePosts(int userId, int postsId) {
//        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        return likeMapper.likePosts(userId,postsId);
    }

    @Override
    public int likeOne(int userId, int oneId) {
//        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        return likeMapper.likeOne(userId,oneId);
    }

    @Override
    public int likeTwo(int userId, int twoId) {
//        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        return likeMapper.likeTwo(userId,twoId);
    }

    @Override
    public int deleteLikePosts(int userId, int postsId) {
//        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        return likeMapper.deleteLikePosts(userId,postsId);
    }

    @Override
    public int deleteLikeOne(int userId, int oneId) {
//        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        return likeMapper.deleteLikeOne(userId,oneId);
    }

    @Override
    public int deleteLikeTwo(int userId, int twoId) {
//        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        return likeMapper.deleteLikeTwo(userId,twoId);
    }

    @Override
    public int likeNumberPosts(int postsId) {
//        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        return likeMapper.likeNumberPosts(postsId);
    }

    @Override
    public int likeNumberOne(int oneId) {
//        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        return likeMapper.likeNumberOne(oneId);
    }

    @Override
    public int likeNumberTwo(int twoId) {
//        LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
        return likeMapper.likeNumberTwo(twoId);
    }
}
