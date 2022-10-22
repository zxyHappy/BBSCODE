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
}
