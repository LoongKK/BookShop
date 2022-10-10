package com.loong.web;

import com.loong.pojo.Cart;
import com.loong.pojo.User;
import com.loong.service.OrderService;
import com.loong.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LoongKK
 * @create 2022/10/10-19:44
 */
public class OrderServlet extends BaseServlet{
    OrderService orderService = new OrderServiceImpl();

    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        User loginUser = (User) req.getSession().getAttribute("user");
        if(loginUser==null){
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            return;
        }

        String orderId = orderService.createOrder(cart, loginUser.getId());

        req.getSession().setAttribute("orderId",orderId);
        //防止表单重复提交，要用重定向。所以前面也要用session保存orderId，不能用req.setAttribute
        resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
    }
}
