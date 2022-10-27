package com.bluemsun.dao.mapper;

import com.bluemsun.entity.ChildComment;
import com.bluemsun.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {

    /**
     * 添加一级评论
     * @param comment
     * @return
     */
    int addOneComment(Comment comment);

    /**
     * 获取某一页的一级回复
     * @param postsId 帖子id
     * @return
     */
    List<Comment> getOneComment(@Param("postsId") int postsId);

    /**
     *
     * @param oneId 所属一级评论的id
     * @return
     */
    List<ChildComment> getTwoComment(@Param("oneId") int oneId);

    int addTwoComment(ChildComment childComment);

    /**
     * 获取某个帖子下一级评论总数
     * @param postsId
     * @return
     */
    int getOneCommentNumber(@Param("postsId") int postsId);

    /**
     * 获取某个一级评论下二级评论总数
     * @param oneId
     * @return
     */
    int getTwoCommentNumber(@Param("oneId") int oneId);

    int deleteOneComment(@Param("id") int id);

    int deleteTwoComment(@Param("id") int id);

    Comment getOneCommentById(@Param("id") int id);

    ChildComment getTwoCommentById(@Param("id") int id);
}
