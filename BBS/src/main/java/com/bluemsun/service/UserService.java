package com.bluemsun.service;

import com.bluemsun.entity.Page;
import com.bluemsun.entity.Posts;
import com.bluemsun.entity.User;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

public interface UserService {


    /**
     * 注册用户
     * @param user
     * @return
     */
    String addUser(User user);

    /**
     *
     * @param idNumber
     * @param password
     * @return 返回要么是登录不成功，要么是token
     */
    String loginUser(String idNumber, String password);

    /**
     *
     * @param url 图片url
     * @param id 用户id
     * @return 添加成功、添加失败
     */
    String addUserPhoto(String url, int id);

    /**
     *
     * @param id 用户id
     * @param index 第几页
     * @return
     */
    Map<String,Object> getUserMessage(int id,int index,int userId);

    Map<String, Object> getUserMessage(int id, int index);

    /**
     * 获得某一页用户帖子列表
     * @param id
     * @param index
     * @return
     */
    Page<Posts> getPostsByUser(int id,int index);

    /**
     *
     * @param password 新的密码
     * @param id 用户id
     * @return
     */
    String updatePassword(String password,int id);

    String updateNickName(String nickName,int id);

    String updateTelephone(String telephone,int id);

    User getUserById(int id);


    /**
     * 用户封禁/解封
     * @param userId 要被操作的用户id
     * @param id 当前登录的用户id
     * @return
     */
    String updateUserStatus(int userId,int id);

    String updateMaster(int userId,int blockId,int id);

    Page<User> getAllUser(int index,int userId);
}
