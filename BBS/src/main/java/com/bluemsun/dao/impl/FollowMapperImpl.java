package com.bluemsun.dao.impl;

import com.bluemsun.dao.mapper.FollowMapper;
import com.bluemsun.entity.Follow;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class FollowMapperImpl implements FollowMapper {

    private SqlSessionTemplate sqlSession;
    private FollowMapper followMapper;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
        followMapper = sqlSession.getMapper(FollowMapper.class);
    }

    @Override
    public List<Follow> getFollower(int userId) {
        return followMapper.getFollower(userId);
    }

    @Override
    public List<Follow> getFans(int userId) {
        return followMapper.getFans(userId);
    }

    @Override
    public int addFollowPeople(Follow follow) {
        int i = followMapper.addFollowPeople(follow);
        if(i != 0) return 1;
        return 0;
    }

    @Override
    public Follow checkFollowPeople(Follow follow) {
        return followMapper.checkFollowPeople(follow);
    }

    @Override
    public int deleteFollowPeople(Follow follow) {
        int i = followMapper.deleteFollowPeople(follow);
        if(i != 0) return 1;
        return 0;
    }

    @Override
    public int addFollowBlock(Follow follow) {
        int i = followMapper.addFollowBlock(follow);
        if(i != 0) return 1;
        return 0;
    }

    @Override
    public List<Follow> getFollowBlock(int userId) {
        return followMapper.getFollowBlock(userId);
    }

    @Override
    public Follow checkFollowBlock(Follow follow) {
        return followMapper.checkFollowBlock(follow);
    }
}
