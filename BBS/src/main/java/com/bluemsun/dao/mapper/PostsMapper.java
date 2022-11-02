package com.bluemsun.dao.mapper;

import com.bluemsun.entity.Comment;
import com.bluemsun.entity.Posts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostsMapper {

    /**
     * 发布帖子
     * @param posts 要发布的帖子信息
     * @return 发帖是否成功
     */
    int addPosts(Posts posts);

    /**
     *
     * @param id 帖子id
     * @return 帖子信息
     */
    Posts showPosts(int id);

    /**
     *
     * @param postsId 帖子id
     * @return 评论列表
     */
    List<Comment> getComments(@Param("postsId") int postsId);

    int getOneCommentNumber(@Param("postsId") int postsId);

    /**
     *
     * @param id 帖子id
     * @return 是否成功
     */
    int addScan(@Param("id") int id);

    /**
     * 得到热门贴
     * @return
     */
    List<Posts> getTopPosts();


    /**
     * 板块内热门贴
     * @param blockId 板块id
     * @return
     */
    List<Posts> getHotPosts(@Param("blockId") int blockId);

    int getNumberByBlock(@Param("blockId") int blockId);

    void addLike(@Param("id") int id);

    void deleteLike(@Param("id") int id);

    void addComment(@Param("id") int id);

    void deleteComment(@Param("id") int id);

    int setTop(@Param("id") int id);

    int deleteTop(@Param("id") int id);

    /**
     * 板块详情页获得置顶帖
     * @param blockId
     * @return
     */
    List<Posts> getTop(@Param("blockId") int blockId);

    /**
     * 板块详情页获得普通帖子
     * @param blockId
     * @param startIndex
     * @return
     */
    List<Posts> getPosts(@Param("blockId") int blockId,@Param("startIndex") int startIndex);

    int updatePosts(@Param("head") String head,@Param("body") String body,@Param("id") int id);

    int deletePosts(@Param("id") int id);


    int setScanNumber(@Param("scanNumber") int scanNumber,@Param("postsId") int postsId);

    int getScanNumber(@Param("id") int id);

    List<Posts> searchPosts(@Param("value") String value);

    List<Posts> showDrafts(@Param("userId") int userId);

    int addDraft(Posts posts);

    int setPostsStatus(@Param("postsId") int postsId);

    int confirmPosts(@Param("postsId") int postsId);

    int rejectPosts(@Param("postsId") int postsId);
}
