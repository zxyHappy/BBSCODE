package com.bluemsun.util;

import java.util.Properties;

public class DataUtil {

    public static Properties properties = new Properties();

    static {
        try {
            //获取配置文件路径注意这里通过类加载器去获取创建在resoures文件下的.properties
            properties.load(DataUtil.class.getResourceAsStream("data.properties"));
        }catch (Exception e){
            System.err.println("appConfig load error");
            e.printStackTrace();
        }

    }

    //获得系统变量的值
    public static String URL = properties.getProperty("url");
//    public static String DRIVER_CLASS_NAME = properties.getProperty("driverClassName");
//    public static String NAME = properties.getProperty("name");
//    public static String PWD = properties.getProperty("pwd");


}
