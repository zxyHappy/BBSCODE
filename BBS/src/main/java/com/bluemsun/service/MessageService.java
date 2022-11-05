package com.bluemsun.service;

import com.bluemsun.entity.Message;

import java.util.List;
import java.util.Map;

public interface MessageService {

    String sendMessage(Message message);

    List<Message> getHistoryMessage(int userFrom, int userTo);
}
