package com.bluemsun.dao.mapper;

import com.bluemsun.entity.Follow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowMapper {

    List<Follow> getFollower(@Param("userId") int userId);

    List<Follow> getFans(@Param("userId") int userId);


}
