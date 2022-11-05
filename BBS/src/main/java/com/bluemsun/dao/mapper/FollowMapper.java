package com.bluemsun.dao.mapper;

import com.bluemsun.entity.Block;
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

    int addFollowPeople(Follow follow);

    Follow checkFollowPeople(Follow follow);

    int deleteFollowPeople(Follow follow);

    int addFollowBlock(Follow follow);

    List<Follow> getFollowBlock(@Param("userId") int userId);

    Follow checkFollowBlock(Follow follow);

    int deleteFollowBlock(@Param("userId") int userId,@Param("blockId") int blockId);

    List<Integer> getPersonByBlock(@Param("blockId") int blockId);

    List<Integer> getPersonByPerson(@Param("userId") int userId);

    int getFollowerNumber(@Param("userId") int userId);

    int getFansNumber(@Param("userId") int userId);
}
