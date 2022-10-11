package com.loong.service.impl;

import com.loong.dao.UserDao;
import com.loong.dao.impl.UserDaoImpl;
import com.loong.pojo.User;
import com.loong.service.UserService;
import com.loong.utils.JDBCUtils;

import java.sql.Connection;

/**
 * @author LoongKK
 * @create 2022/9/28-0:31
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();


    @Override
    public void registUser(User user) {
        Connection conn = JDBCUtils.getConnection();
        userDao.saveUser(conn, user);
    }

    @Override
    public User login(User user) {
        Connection conn = JDBCUtils.getConnection();
        return userDao.queryUserByUsernameAndPassword(conn, user.getUsername(), user.getPassword());

    }

    @Override
    public Boolean existsUsername(String username) {
        Connection conn = JDBCUtils.getConnection();
        return userDao.queryUserByUsername(conn, username) != null;
    }
}
