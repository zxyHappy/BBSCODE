package com.bluemsun.dao.mapper;

import com.bluemsun.entity.Follow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowMapper {

    /**
     * 获取关注
     * @param userId
     * @return
     */
    List<Follow> getFollower(@Param("userId") int userId);

    /**
     * 获取粉丝
     * @param userId
     * @return
     */
    List<Follow> getFans(@Param("userId") int userId);


}
