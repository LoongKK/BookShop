package com.loong.test;

import com.loong.dao.UserDao;
import com.loong.dao.impl.UserDaoImpl;
import com.loong.pojo.User;
import com.loong.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author LoongKK
 * @create 2022/9/27-21:39
 */
public class UserDaoTest {
    @Test
    public void queryUserByUsername() throws Exception{
        Connection conn = JDBCUtils.getConnection();
        UserDao userDao = new UserDaoImpl();
        //System.out.println(userDao.queryUserByUsername(conn, "admin"));
        if(userDao.queryUserByUsername(conn, "admin")==null){
            System.out.println("用户名可用");
        }else{
            System.out.println("用户名已存在");
        }
        JDBCUtils.closeConnection();
    }

    @Test
    public void queryUserByUsernameAndPassword()throws Exception{
        Connection conn = JDBCUtils.getConnection();
        UserDao userDao = new UserDaoImpl();
        if(userDao.queryUserByUsernameAndPassword(conn, "admin","admin")==null){
            System.out.println("登陆失败！用户名或密码错误");
        }else{
            System.out.println("登录成功！");
        }
        JDBCUtils.closeConnection();
    }
    @Test
    public void saveUser()throws Exception{
        Connection conn = JDBCUtils.getConnection();
        UserDao userDao = new UserDaoImpl();

        //User user = new User(null,"admin","123456","test@loong.com");
        User user = new User(null,"user01","123456","user01@loong.com");
        if(userDao.queryUserByUsername(conn,user.getUsername())!=null){
            System.out.println("用户名已存在");
        }else{
            userDao.saveUser(conn, user);
        }

        JDBCUtils.closeConnection();
    }
}
