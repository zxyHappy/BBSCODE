package com.bluemsun.service;

import com.bluemsun.entity.Posts;

import java.util.List;
import java.util.Map;

public interface PostsService {

    String addPosts(Posts posts);

    /**
     *
     * @param id 帖子id
     * @return
     */
    Map<String,Object> showPosts(int id,int userId);


    /**
     * 更新帖子点赞状态
     * @param postsId
     * @param userId
     * @return
     */
    String updateLike(int postsId,int userId);

    /**
     * 更新帖子信息
     * @param map
     * @return
     */
    String updatePosts(Map<String,Object> map,int userId);

    /**
     * 删除帖子
     * @param userId
     * @param id
     * @return
     */
    String deletePosts(int userId,int id);


    /**
     * 展示草稿箱
     * @param userId
     * @return
     */
    List<Posts> showDrafts(int userId);

    /**
     * 发帖时加入草稿箱
     * @param posts
     * @return
     */
    String addDraft(Posts posts);

    /**
     * 从草稿箱中移出（发帖）
     * @param postsId
     * @return
     */
    String setPostsStatus(int postsId);

    /**
     * 审核帖子
     * @param postsId
     * @param msg accept/unaccept
     * @param userId 当前登录用户id，用来判断该用户是否有权限执行该操作
     * @return
     */
    String confirmPosts(int postsId,String msg,int userId);

    String setTopPosts(int postsId,int userId);

    String cancelTop(int postsId,int userId);
}
