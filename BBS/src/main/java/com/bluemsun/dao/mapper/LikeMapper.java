package com.bluemsun.dao.mapper;

import com.bluemsun.entity.Like;
import org.apache.ibatis.annotations.Param;

public interface LikeMapper {

    /**
     * 确认该用户是否给该帖子点赞
     * @param userId 用户id
     * @param postsId 帖子id
     * @return 被点赞返回对象，未被点赞返回null
     */
    Like confirmLikeByPosts(@Param("userId") int userId, @Param("postsId") int postsId);

    /**
     * 一级评论点赞状态
     * @param userId
     * @param oneId
     * @return
     */
    Like confirmLikeByOne(@Param("userId") int userId, @Param("oneId") int oneId);

    /**
     * 确认二级评论点赞状态
     * @param userId
     * @param twoId
     * @return
     */
    Like confirmLikeByTwo(@Param("userId") int userId, @Param("twoId") int twoId);

    /**
     * 点赞帖子
     * @param userId
     * @param postsId
     * @return
     */
    int likePosts(@Param("userId")int userId,@Param("postsId") int postsId);

    /**
     * 点赞一级评论
     * @param userId
     * @param oneId
     * @return
     */
    int likeOne(@Param("userId")int userId,@Param("oneId") int oneId);

    /**
     * 点赞二级评论
     * @param userId
     * @param twoId
     * @return
     */
    int likeTwo(@Param("userId") int userId,@Param("twoId") int twoId);

    int deleteLikePosts(@Param("userId") int userId,@Param("postsId") int postsId);

    int deleteLikeOne(@Param("userId")int userId,@Param("oneId") int oneId);

    int deleteLikeTwo(@Param("userId") int userId,@Param("twoId") int twoId);

    int likeNumberPosts(@Param("postsId") int postsId);
    int likeNumberOne(@Param("oneId") int oneId);
    int likeNumberTwo(@Param("twoId") int twoId);
}
