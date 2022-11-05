package com.bluemsun.dao.mapper;

import com.bluemsun.entity.File;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper {



    int addFile(File file);


    /**
     *
     * @param id  帖子id
     * @return
     */
    List<File> getFile(@Param("id") int id);

    /**
     *
     * @param id 文件id
     * @return
     */
    File getFileById(@Param("id") int id);

    int deleteFile(@Param("id") int id);

    int setFilePosts(@Param("postsId") int postsId,@Param("id") int id);
}
