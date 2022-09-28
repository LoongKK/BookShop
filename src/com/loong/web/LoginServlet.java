package com.loong.web;

import com.loong.pojo.User;
import com.loong.service.UserService;
import com.loong.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LoongKK
 * @create 2022/9/28-23:42
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //登录业务
        UserService userService=new UserServiceImpl();
        User loginUser = userService.login(new User(null, username, password, null));

        //根据登录结果进行跳转
        if (loginUser != null){
            System.out.println("登录成功");
            req.getRequestDispatcher("/pages/user/login_success.html").forward(req,resp);
        }else{
            System.out.println("登录失败");
            req.getRequestDispatcher("/pages/user/login.html").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
