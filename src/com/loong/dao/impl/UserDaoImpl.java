package com.loong.dao.impl;

import com.loong.dao.UserDao;
import com.loong.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author LoongKK
 * @create 2022/9/27-1:14
 */
public class UserDaoImpl extends BaseDao<User> implements UserDao {
    @Override
    public User queryUserByUsername(Connection conn, String username) {
        String sql="SELECT `id`,`username`,`password`,`email` FROM t_user WHERE username=?";
        return getBean(conn,sql,username);
    }

    @Override
    public User queryUserByUsernameAndPassword(Connection conn, String username, String password) {
        String sql="SELECT `id`,`username`,`password`,`email` FROM t_user WHERE `username`=? AND `password`=?";
        return getBean(conn,sql,username,password);
    }

    @Override
    public int saveUser(Connection conn, User user) {
        String sql="INSERT INTO t_user(`username`,`password`,`email`) VALUES(?,?,?)";
        return update(conn,sql,user.getUsername(),user.getPassword(),user.getEmail());
        //返回更新的行数
    }


}
