package com.bluemsun.service;

import com.bluemsun.entity.File;

public interface FileService {


    String addFile(File file);

    /**
     * 通过文件id获得文件
     * @param id 文件id
     * @return
     */
    File getFile(int id);
}
