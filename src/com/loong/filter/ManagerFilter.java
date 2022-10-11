package com.loong.filter;

import com.loong.pojo.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author LoongKK
 * @create 2022/10/11-0:46
 */
public class ManagerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //类型转换为httpServlet （这样才有getSession方法
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //通过session获取user属性
        User loginUser = (User) httpServletRequest.getSession().getAttribute("user");

        if(loginUser==null){
            //请求转发到登录页面
            httpServletRequest.getRequestDispatcher("/pages/user/login.jsp").forward(servletRequest,servletResponse);
        }else if(loginUser.getUsername().equals("admin")){
            //管理员 （暂时先这样判断）（正常方式：可以给数据库加个字段判断是否是管理员，调用user对象看字段值是多少判断是否是管理员）
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            //非管理员
            servletResponse.setContentType("text/html;charset=utf-8");
            servletResponse.getWriter().print("<script>alert('你不是管理员!');" +
                    "location.href='" +httpServletRequest.getContextPath()+"/index.jsp'</script>");
//            servletResponse.getWriter().print("<script>alert('你不是管理员!')</script> " +
//                    "<a href="+httpServletRequest.getContextPath()+"/index.jsp>返回首页</a>");
        }

    }

    @Override
    public void destroy() { }

}
