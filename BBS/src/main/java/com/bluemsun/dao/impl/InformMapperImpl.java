package com.bluemsun.dao.impl;

import com.bluemsun.dao.mapper.CommentMapper;
import com.bluemsun.dao.mapper.InformMapper;
import com.bluemsun.entity.Inform;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class InformMapperImpl implements InformMapper {

    private SqlSessionTemplate sqlSession;
    private InformMapper informMapper;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
        informMapper = sqlSession.getMapper(InformMapper.class);
    }

    @Override
    public int addInform(Inform inform) {
        return informMapper.addInform(inform);
    }

    @Override
    public List<Inform> getInform(int userId, String type, int startIndex) {
        return informMapper.getInform(userId,type,startIndex);
    }

    @Override
    public int addInformIndex(int informId, int userId) {
        return informMapper.addInformIndex(informId,userId);
    }

    @Override
    public int deleteInform(int informId) {
        return informMapper.deleteInform(informId);
    }


    @Override
    public int confirmInform(int informId) {
        return informMapper.confirmInform(informId);
    }

    @Override
    public int getInformNumber(int userId, String type) {
        return informMapper.getInformNumber(userId,type);
    }

    @Override
    public Inform checkInform(int userId, int informId) {
        return informMapper.checkInform(userId,informId);
    }
}
