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
 * @create 2022/9/28-16:51
 */
public class RegistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //idea输入doGet快速生成的会首行调用父类HttpServlet的doGet或doPost，要注视掉，否则报错。
        // super.doGet(req, resp);//浏览器报错Cannot call sendError() after the response has…

        //获取参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");//注意在html里为验证码添加属性name="code"

        //验证码是否正确。（目前没学如何生成验证码，是写死的"abcde"）
        if("abcde".equals(code)){
            UserService userService=new UserServiceImpl();
            //用户名是否可用
            if(!userService.existsUsername(username)){
                //保存到数据库
                userService.registUser(new User(null,username,password,email));
                //跳转到注册成功页面
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,resp);
                System.out.println("注册成功");
            }else{
                //System.out.println("用户名已存在");
                //回显的错误信息
                req.setAttribute("msg","用户名已存在！");
                //回显的表单项值信息
                req.setAttribute("username",username);
                req.setAttribute("email",email);

                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
            }
        }else{
            //System.out.println("验证码错误");
            //回显的错误信息
            req.setAttribute("msg","验证码错误！");
            //回显的表单项值信息
            req.setAttribute("username",username);
            req.setAttribute("email",email);

            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        this.doGet(req,resp);
    }
}
