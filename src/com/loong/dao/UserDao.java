package com.loong.dao;

import com.loong.pojo.User;

import java.sql.Connection;

/**
 * @author LoongKK
 * @create 2022/9/27-0:54
 */
public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return User对象。如果没有此用户，则返回null
     */
    public User queryUserByUsername(Connection conn,String username);

    /**
     * 根据用户名和密码查询用户信息
     * @param username 用户名
     * @param password 密码
     * @return User对象。如果没有此用户（可能用户名或密码错误），则返回null
     */
    public User queryUserByUsernameAndPassword(Connection conn,String username,String password);
    /**
     * 保存用户信息到数据库中
     * @param user
     * @return 更新的行数
     */
    public int saveUser(Connection conn,User user);


}
