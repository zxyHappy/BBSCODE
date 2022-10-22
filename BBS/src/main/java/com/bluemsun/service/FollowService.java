package com.bluemsun.service;

import com.bluemsun.dao.mapper.FollowMapper;
import com.bluemsun.entity.Follow;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FollowService {

    /**
     * 获取关注列表
     * @param userId 用户id
     * @return
     */
     List<Follow> getPeople(int userId,String type);

    /**
     * 更新关注，已关注就取关，未关注就关注
     * @param userId 关注人
     * @param userFollowed 被关注人
     * @return
     */
    String updateFollowPeople(int userId,int userFollowed);

}
