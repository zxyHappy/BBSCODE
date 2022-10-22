package com.bluemsun.service;

import com.bluemsun.entity.OneComment;
import com.bluemsun.entity.Page;
import com.bluemsun.entity.TwoComment;

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
     * @param index 页码值
     * @return
     */
    Page<OneComment> getOneComment(int postsId, int index);

    /**
     *
     * @param oneId  所属的一级评论id
     * @param index 页码值
     * @return
     */
    Page<TwoComment> getTwoComment(int oneId,int index);

    /**
     *
     * @param oneId 所属的一级评论的id
     * @param useridSend 发送二级评论的用户id，token里取出
     * @param useridReply 前端发送的被回复人的id
     * @param body 评论内容
     * @return
     */
    String addTwoComment(int oneId,int useridSend,int useridReply,String body);

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
     * @param commentPage
     */
    void setLikeStatusOne(int userId,Page<OneComment> commentPage);

    void setLikeStatusTwo(int userId,Page<TwoComment> commentPage);

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
