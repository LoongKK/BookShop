package com.loong.filter;

import com.loong.utils.JDBCUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author LoongKK
 * @create 2022/10/11-19:39
 */
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            JDBCUtils.beginTransaction();
            filterChain.doFilter(servletRequest, servletResponse);
            JDBCUtils.commit();
        } catch (Exception e) {
            e.printStackTrace();
            JDBCUtils.rollback();
            throw new RuntimeException(e);//抛给Tomcat，让 Tomcat 展示友好的错误信息页面
        } finally {
            JDBCUtils.closeConnection();
        }
    }

    @Override
    public void destroy() { }
}
