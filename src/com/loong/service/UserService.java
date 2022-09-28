package com.loong.service;

import com.loong.pojo.User;

/**
 * @author LoongKK
 * @create 2022/9/27-23:48
 */
public interface UserService {

    /**
     * 注册用户
     * @param user
     */
    public void registUser(User user);

    /**
     * 登录
     * @param user
     * @return 返回一个User对象。返回null为登录失败
     */
    public User login(User user);

    /**
     * 检查用户名是否可用(是否已存在)
     * @param username
     * @return true-用户名已存在，false-用户名可用
     */
    public Boolean existsUsername(String username);
}
