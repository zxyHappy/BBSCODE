package com.bluemsun.service;

import java.util.Map;

public interface IndexService {

    /**
     * 获取主页信息
     * @return
     */
    Map<String,Object> getIndex(int userId);

    Map<String,Object> indexSearch(String value,int userId);


}
