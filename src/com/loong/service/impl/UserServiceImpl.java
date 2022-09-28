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

    private UserDao userDao=new UserDaoImpl();


    @Override
    public void registUser(User user) {
        Connection conn=null;
        try {
            conn=JDBCUtils.getConnection();
            if(!existsUsername(user.getUsername())){
                //用户名可用
                if(userDao.saveUser(conn,user)==1){
                    //成功
                };
            }else{
                //用户名已存在
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null,null);
        }

    }

    @Override
    public User login(User user) {
        Connection conn=null;
        try {
            conn=JDBCUtils.getConnection();
            return userDao.queryUserByUsernameAndPassword(conn,user.getUsername(),user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null,null);
        }
        return null;
    }

    @Override
    public Boolean existsUsername(String username) {
        Connection conn = null;
        try {
            conn=JDBCUtils.getConnection();
            return userDao.queryUserByUsername(conn,username)!=null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null,null);
        }
        return null;
    }
}
