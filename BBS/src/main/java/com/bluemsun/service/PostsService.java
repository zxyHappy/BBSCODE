package com.bluemsun.service;

import com.bluemsun.entity.Posts;

import java.util.Map;

public interface PostsService {

    String addPosts(Posts posts);

    /**
     *
     * @param id 帖子id
     * @return
     */
    Map<String,Object> showPosts(int id,int userId);


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

}
