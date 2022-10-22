package com.bluemsun.dao.impl;

import com.bluemsun.dao.mapper.FileMapper;
import com.bluemsun.entity.File;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class FileMapperImpl implements FileMapper {

    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public int addFile(File file) {
        FileMapper fileMapper = sqlSession.getMapper(FileMapper.class);
        return fileMapper.addFile(file);
    }

    @Override
    public List<File> getFile(int id) {
        FileMapper fileMapper = sqlSession.getMapper(FileMapper.class);
        return fileMapper.getFile(id);
    }

    @Override
    public File getFileById(int id) {
        FileMapper fileMapper = sqlSession.getMapper(FileMapper.class);
        return fileMapper.getFileById(id);
    }
}
