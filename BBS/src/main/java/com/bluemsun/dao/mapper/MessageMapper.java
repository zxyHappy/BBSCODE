package com.bluemsun.dao.mapper;

import com.bluemsun.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {

    List<Message> getHistoryMessage(@Param("userFrom") int userFrom,@Param("userTo") int userTo);

    int sendMessage(Message message);
}
