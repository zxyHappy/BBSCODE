package com.bluemsun.service;

import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.Inform;
import com.bluemsun.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface InformService {

    /**
     * type:comment
     * @param value 回复的内容
     * @param userIdSend 发送回复的人
     * @param userIdReply 被回复的人,放在index索引表里
     * @param postsId 所属的帖子
     * @return
     */
    String addCommentInform(String value,int userIdSend,int userIdReply,int postsId);

    /**
     * type:follow
     * @param userId userId指向的那个用户关注了你
     * @param userIdFollowed 自己的id，放在index表里
     * @return
     */
    String addFollowInform(int userId,int userIdFollowed);

    /**
     * 您关注的板块更新了 type:system
     * @param postsId 新的帖子的id
     * @param userId 放进index里的
     * @return
     */
    String addPostsInformByBlock(int postsId,int userId);

    /**
     * 您关注的用户更新了 type:system
     * @param postsId 新的帖子id
     * @param userIdFollowed 发帖人
     * @return
     */
    String addPostsInformByPerson(int postsId,int userIdFollowed);

    /**
     * 您的帖子已通过审核/未通过审核 type：system
     * @param postsId 自己的要审核的帖子id
     * @param userId 用户id，放在index里
     * @return
     */
    String confirmPostsInform(int postsId,int userId,int status);

    /**
     * 通知版主干活确认帖子 type：system
     * @param postsId 需要确认的帖子id
     * @return
     */
    String MasterConfirmInform(int postsId);

    /**
     * 您的帖子***正在送审，请耐心等待
     * @param userId 放进index里
     * @param postsId 被送审的帖子
     * @return
     */
    String confirmPostsForUser(int userId,int postsId);

    /**
     *
     * @param informId 要删除的通知id
     * @param userId 当前登录用户id
     * @return
     */
    String deleteInform(int informId,int userId);

    /**
     *
     * @param userId 通知所属的用户
     * @param index 页码值
     * @param type 通知类型
     * @return
     */
    Page<Inform> showInform(int userId, int index, String type);
}
