package com.bluemsun.service.impl;

import com.bluemsun.dao.mapper.FileMapper;
import com.bluemsun.entity.File;
import com.bluemsun.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FileServiceImpl implements FileService {

    @Autowired FileMapper fileMapper;

    @Override
    public String addFile(File file) {
        int i = fileMapper.addFile(file);
        if(i != 0) return "文件上传成功";
        return "文件上传失败";
    }

    @Override
    public File getFile(int id) {
        return fileMapper.getFileById(id);
    }
}
