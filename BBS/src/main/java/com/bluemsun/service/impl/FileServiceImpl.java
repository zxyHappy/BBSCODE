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
        if(i != 0) return "文件上传成功，文件名："+fileMapper.getFileById(file.getId()).getFileName();
        return "文件上传失败";
    }

    @Override
    public File getFile(int id) {
        return fileMapper.getFileById(id);
    }


    @Override
    public String deleteFile(int id,int userId) {
        if(fileMapper.getFileById(id).getUserId() != userId) return "无操作权限";
        int i = fileMapper.deleteFile(id);
        if(i != 0) return "删除成功";
        return "删除失败";
    }
}
