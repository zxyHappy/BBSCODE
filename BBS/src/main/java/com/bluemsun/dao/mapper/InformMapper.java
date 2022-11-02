package com.bluemsun.dao.mapper;

import com.bluemsun.entity.Inform;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InformMapper {

    int addInform(Inform inform);

    List<Inform> getInform(@Param("userId") int userId,@Param("type") String type,@Param("startIndex") int startIndex);

    int addInformIndex(@Param("informId") int informId,@Param("userId") int userId);

    int deleteInform(@Param("informId") int informId);


    int confirmInform(@Param("informId") int informId);

    int getInformNumber(@Param("userId") int userId,@Param("type") String type);

    Inform checkInform(@Param("userId") int userId,@Param("informId") int informId);
}
