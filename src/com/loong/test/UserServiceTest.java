package com.loong.test;

import com.loong.pojo.User;
import com.loong.service.UserService;
import com.loong.service.impl.UserServiceImpl;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author LoongKK
 * @create 2022/9/28-15:45
 */
public class UserServiceTest {
    UserService userService = new UserServiceImpl();

    @Test
    public void registUser() {
        userService.registUser(new User(null, "user02", "123456", "user02@loong.com"));
    }

    @Test
    public void login() {
        User user02 = userService.login(new User(null, "user02", "123456", null));
        System.out.println(user02);
    }

    @Test
    public void existsUsername() {
        if (!userService.existsUsername("user03")) {
            System.out.println("用户名可用");
        } else {
            System.out.println("用户名已存在");
        }
    }
}