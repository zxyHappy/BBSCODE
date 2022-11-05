package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.MessageMapper;
import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.Message;
import com.bluemsun.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class MessageServiceImpl implements MessageService {

    @Autowired MessageMapper messageMapper;
    @Autowired UserMapper userMapper;

    @Override
    public String sendMessage(Message message) {
        int i = messageMapper.sendMessage(message);
        if(i != 0) return "发送成功";
        return "发送失败";
    }

    @Override
    public List<Message> getHistoryMessage(int userFrom, int userTo) {
        List<Message> list =  messageMapper.getHistoryMessage(userFrom,userTo);
        for(Message message:list){
            message.setUser(userMapper.getUserById(message.getUserFrom()));
        }
        return list;
    }
}
