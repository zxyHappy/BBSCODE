package com.bluemsun.service;

import com.bluemsun.entity.Comment;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.ChildComment;

import java.util.List;

public interface CommentService {

    /**
     * 添加一级评论
     * @param userId 用户id
     * @param postsId 所属帖子id
     * @param body 评论内容
     * @return 是否成功
     */
    String addOneComment(int userId,int postsId,String body);

    /**
     *
     * @param postsId 帖子id
     * @return
     */
    List<Comment> getOneComment(int postsId, int userId);

    /**
     *
     * @param oneId  所属的一级评论id
     * @return
     */
    List<ChildComment> getTwoComment(int oneId,int userId);

    /**
     *
     * @return
     */
    String addTwoComment(ChildComment childComment);

    /**
     * 更新一级评论点赞状态
     * @param oneId
     * @param userId
     * @return
     */
    String updateLikeOne(int oneId,int userId);

    /**
     * 更新二级评论点赞状态
     * @param twoId
     * @param userId
     * @return
     */
    String updateLikeTwo(int twoId,int userId);

    /**
     * 设置是否已被点赞
     * @param userId
     */
    void setLikeStatusOne(int userId,List<Comment> commentList);

    void setLikeStatusTwo(int userId,List<ChildComment> commentList);

    /**
     * 删除一级评论
     * @param userId 用户id
     * @param id 一级评论id
     * @return
     */
    String deleteOneComment(int userId,int id);

    /**
     * 删除二级评论
     * @param userId 用户id
     * @param id 二级评论id
     * @return
     */
    String deleteTwoComment(int userId,int id);
}
