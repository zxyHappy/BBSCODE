package com.bluemsun.dao.impl;

import com.bluemsun.dao.mapper.UserMapper;
import com.bluemsun.entity.Posts;
import com.bluemsun.entity.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapperImpl implements UserMapper {

    private SqlSessionTemplate sqlSession;
    private UserMapper userMapper;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Override
    public User getUserById(int id) {
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        return userMapper.getUserById(id);
    }

    @Override
    public int addUser(User user) {
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int i = userMapper.addUser(user);
        if(i != 0) return 1;
        return 0;
    }

    @Override
    public User getUserByName(String userName) {
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserByName(userName);
        return user;
    }

    @Override
    public User getUserByTelephone(String telephone) {
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserByTelephone(telephone);
        return user;
    }

    @Override
    public int addPhoto(Map<String,Object> map) {
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int i = userMapper.addPhoto(map);
        if(i!=0) return 1;
        return 0;
    }

    @Override
    public int getPostsNumber(int id) {
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        return userMapper.getPostsNumber(id);
    }


    public List<Posts> getPostsByUser(int id, int startIndex) {
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        return userMapper.getPostsByUser(id,startIndex);
    }

    @Override
    public int updatePassword(String password, int id) {
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        return userMapper.updatePassword(password,id);
    }

    @Override
    public int updateNickName(String nickName, int id) {
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        return userMapper.updateNickName(nickName,id);
    }

    @Override
    public int updateTelephone(String telephone, int id) {
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        return userMapper.updateTelephone(telephone,id);
    }

    @Override
    public List<User> searchUser(String value) {
        return userMapper.searchUser(value);
    }

    @Override
    public int banUser(int userId) {
        return userMapper.banUser(userId);
    }

    @Override
    public int cancelBan(int userId) {
        return userMapper.cancelBan(userId);
    }

    @Override
    public int insertMaster(int blockId, int userId) {
        return userMapper.insertMaster(blockId,userId);
    }

    @Override
    public int cancelMaster(int blockId, int userId) {
        return userMapper.cancelMaster(blockId,userId);
    }
}
