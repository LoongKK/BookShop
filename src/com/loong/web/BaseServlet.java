package com.loong.web;

import com.loong.pojo.User;
import com.loong.service.UserService;
import com.loong.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 抽象类BaseServlet
 * @author LoongKK
 * @create 2022/10/2-22:44
 */

public abstract class BaseServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");

        //使用反射优化大量 else if 代码
        try {
            // 获取 action 业务鉴别字符串，获取相应的业务 方法反射对象。比如根据action=“login"找方法名login的方法
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            //System.out.println(method);
            // 调用目标业务 方法
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            //要把 BaseServlet 中的异常往外抛给 Filter 过滤器。以便于Filter捕获到异常，触发事务回滚
            throw new RuntimeException(e);// 把异常抛给 Filter 过滤器
        }

            /*未使用反射优化
        if("login".equals(action)){
            //登录
            System.out.println("login");
            login(request, response);
        }else if ("regist".equals(action)){
            //注册
            System.out.println("regist");
            regist(request,response);
        }
        */
    }
}
