package com.bluemsun.dao.mapper;

import com.bluemsun.entity.OneComment;
import com.bluemsun.entity.TwoComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {

    /**
     * 添加一级评论
     * @param oneComment
     * @return
     */
    int addOneComment(OneComment oneComment);

    /**
     * 获取某一页的一级回复
     * @param postsId 帖子id
     * @param startIndex 当前页第一个评论的下标
     * @return
     */
    List<OneComment> getOneComment(@Param("postsId") int postsId,@Param("startIndex") int startIndex);

    /**
     *
     * @param oneId 所属一级评论的id
     * @param startIndex 当前页第一个二级评论的下标
     * @return
     */
    List<TwoComment> getTwoComment(@Param("oneId") int oneId,@Param("startIndex") int startIndex);

    int addTwoComment(TwoComment twoComment);

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

    OneComment getOneCommentById(@Param("id") int id);

    TwoComment getTwoCommentById(@Param("id") int id);
}
