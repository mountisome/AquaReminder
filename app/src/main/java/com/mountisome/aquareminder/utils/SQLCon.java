package com.mountisome.aquareminder.utils;

public class SQLCon {

    // 数据库名
    public static String DB_NAME = "AquaReminder";

    // 数据库版本
    public static int DB_VERSION = 1;

    // 建表语句
    public static final String CREATE_TABLE = "CREATE TABLE user (\n" +
            "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "name VARCHAR(30) NOT NULL,\n" +
            "pwd VARCHAR(30) NOT NULL,\n" +
            "mailbox VARCHAR(30) NOT NULL,\n" +
            "water INT,\n" +
            "energy INT,\n" +
            "planted VARCHAR(100),\n" +
            "day INT,\n" +
            "average_water INT,\n" +
            "average_time INT,\n" +
            "total_water INT,\n" +
            "total_time INT\n" +
            ");";

    // 插入数据
    public static final String INSERT = "INSERT INTO user(id,name,pwd,mailbox,water,energy,planted,"
            + "day,average_water,average_time,total_water,total_time) VALUES" +
            "(1,'user1','123','123@qq.com',0,100,'0000',0,0,0,0,0)," +
            "(2,'user2','1234','1234@qq.com',0,5,'0000',0,0,0,0,0)," +
            "(3,'user3','12345','12345@qq.com',0,0,'0000',0,0,0,0,0);";
}
