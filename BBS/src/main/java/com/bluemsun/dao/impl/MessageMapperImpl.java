package com.bluemsun.dao.impl;

import com.bluemsun.dao.mapper.MessageMapper;
import com.bluemsun.entity.Message;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class MessageMapperImpl implements MessageMapper {

    private SqlSessionTemplate sqlSession;
    private MessageMapper messageMapper;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
        messageMapper = sqlSession.getMapper(MessageMapper.class);
    }

    @Override
    public List<Message> getHistoryMessage(int userFrom, int userTo) {
        return messageMapper.getHistoryMessage(userFrom,userTo);
    }

    @Override
    public int sendMessage(Message message) {
        return messageMapper.sendMessage(message);
    }
}
