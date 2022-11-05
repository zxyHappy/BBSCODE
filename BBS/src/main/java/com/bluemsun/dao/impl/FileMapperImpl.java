package com.bluemsun.dao.impl;

import com.bluemsun.dao.mapper.FileMapper;
import com.bluemsun.entity.File;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class FileMapperImpl implements FileMapper {

    private SqlSessionTemplate sqlSession;
    private FileMapper fileMapper;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
        fileMapper = sqlSession.getMapper(FileMapper.class);
    }

    @Override
    public int addFile(File file) {
//        FileMapper fileMapper = sqlSession.getMapper(FileMapper.class);
        return fileMapper.addFile(file);
    }

    @Override
    public List<File> getFile(int id) {
//        FileMapper fileMapper = sqlSession.getMapper(FileMapper.class);
        return fileMapper.getFile(id);
    }

    @Override
    public File getFileById(int id) {
//        FileMapper fileMapper = sqlSession.getMapper(FileMapper.class);
        return fileMapper.getFileById(id);
    }

    @Override
    public int deleteFile(int id) {
        return fileMapper.deleteFile(id);
    }

    @Override
    public int setFilePosts(int postsId, int id) {
        return fileMapper.setFilePosts(postsId,id);
    }
}
