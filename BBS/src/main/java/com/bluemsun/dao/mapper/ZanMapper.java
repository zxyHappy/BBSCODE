package com.bluemsun.dao.mapper;

import com.bluemsun.entity.Zan;
import org.apache.ibatis.annotations.Param;

public interface ZanMapper {

    /**
     * 确认该用户是否给该帖子点赞
     * @param userId 用户id
     * @param postsId 帖子id
     * @return 被点赞返回对象，未被点赞返回null
     */
    Zan confirmZanByPosts(@Param("userId") int userId,@Param("postsId") int postsId);

    /**
     * 一级评论点赞状态
     * @param userId
     * @param oneId
     * @return
     */
    Zan confirmZanByOne(@Param("userId") int userId,@Param("oneId") int oneId);

    /**
     * 确认二级评论点赞状态
     * @param userId
     * @param twoId
     * @return
     */
    Zan confirmZanByTwo(@Param("userId") int userId,@Param("twoId") int twoId);

    /**
     * 点赞帖子
     * @param userId
     * @param postsId
     * @return
     */
    int zanPosts(@Param("userId")int userId,@Param("postsId") int postsId);

    /**
     * 点赞一级评论
     * @param userId
     * @param oneId
     * @return
     */
    int zanOne(@Param("userId")int userId,@Param("oneId") int oneId);

    /**
     * 点赞二级评论
     * @param userId
     * @param twoId
     * @return
     */
    int zanTwo(@Param("userId") int userId,@Param("twoId") int twoId);

    int deleteZanPosts(@Param("userId") int userId,@Param("postsId") int postsId);

    int deleteZanOne(@Param("userId")int userId,@Param("oneId") int oneId);

    int deleteZanTwo(@Param("userId") int userId,@Param("twoId") int twoId);

    int zanNumberPosts(@Param("postsId") int postsId);
    int zanNumberOne(@Param("oneId") int oneId);
    int zanNumberTwo(@Param("twoId") int twoId);
}
