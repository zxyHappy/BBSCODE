package com.bluemsun.dao.mapper;

import com.bluemsun.entity.Posts;
import com.bluemsun.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    User getUserById(int id);

    /**
     *
     * @param user
     * @return 0代表添加失败，1代表添加成功
     */
    int addUser(User user);

    /**
     *
     * @param userName 账号
     * @return 未找到返回null，找到返回user对象
     */
    User getUserByName(String userName);

    /**
     * 手机号查找用户
     * @param telephone
     * @return
     */
    User getUserByTelephone(String telephone);

    /**
     *
     * @param map
     * @return 是否成功
     */
    int addPhoto(Map<String,Object> map);

    /**
     *
     * @param id
     * @return 返回帖子数量
     */
    int getPostsNumber(int id);


    /**
     *
     * @param id 用户id
     * @param startIndex 页码值
     * @return
     */
    List<Posts> getPostsByUser(@Param("id")int id,@Param("startIndex") int startIndex);

    /**
     *
     * @param password 更新的密码
     * @param id 用户id
     * @return 是否成功
     */
    int updatePassword(@Param("password") String password, @Param("id") int id);

    int updateNickName(@Param("nickName") String nickName,@Param("id") int id);

    int updateTelephone(@Param("telephone") String telephone,@Param("id") int id);

    List<User> searchUser(@Param("value") String value);

    int banUser(@Param("userId") int userId);

    int cancelBan(@Param("userId") int userId);

    int insertMaster(@Param("blockId") int blockId,@Param("userId") int userId);

    int cancelMaster(@Param("blockId") int blockId,@Param("userId") int userId);

    List<User> getAllUser(@Param("startIndex") int startIndex);

    int getAllUserNumber();

}
