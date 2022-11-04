package com.bluemsun.entity;


import com.bluemsun.entity.vo.UserVO;
import lombok.Data;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Data
public class Result {


    private Boolean success;


    private Integer code;


    private String message;


    private Map data;

    private Result(){}

    public static Result ok(){
        Result r = new Result();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    public static Result error(){
        Result r = new Result();
        r.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return r;
    }

    public static Result setResult(ResultCodeEnum resultCodeEnum){
        Result r = new Result();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value){
        Map<String,Object> map = new HashMap<>();
        map.put(key,value);
        this.data = map;
        return this;
    }

    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

    public Result data(String msg){
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        this.setData(map);
        return this;
    }

    @SneakyThrows
    public Result data(Object object){
        Map<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(object));
        }
        this.setData(map);
        return this;
    }



}
