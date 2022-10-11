package com.loong.service.impl;

import com.loong.dao.BookDao;
import com.loong.dao.OrderDao;
import com.loong.dao.OrderItemDao;
import com.loong.dao.impl.BookDaoImpl;
import com.loong.dao.impl.OrderDaoImpl;
import com.loong.dao.impl.OrderItemDaoImpl;
import com.loong.pojo.*;
import com.loong.service.OrderService;
import com.loong.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author LoongKK
 * @create 2022/10/10-18:21
 */
public class OrderServiceImpl implements OrderService {
    OrderDao orderDao=new OrderDaoImpl();
    OrderItemDao orderItemDao=new OrderItemDaoImpl();
    BookDao bookDao=new BookDaoImpl();
    @Override
    public String createOrder(Cart cart, int userId) {
        //生成订单包括：(1)保存订单 (2)保存订单项 (3)更新每种书的库存和销量（4）清空购物车
        Connection conn=null;
        try {
            conn = JDBCUtils.getConnection();
            //关闭自动提交
            conn.setAutoCommit(false);

            //(1)保存订单
            // 订单号===唯一性
            String orderId = System.currentTimeMillis()+""+userId;
            // 创建一个订单对象
            Order order = new Order(orderId, LocalDateTime.now(),cart.getTotalPrice(), 0,userId);
            // 保存订单
            orderDao.saveOrder(conn,order);

            //(2)保存订单项
            // 遍历购物车中每一个商品项转换成为订单项保存到数据库
            for (Map.Entry<Integer, CartItem>entry : cart.getItems().entrySet()){
                // 获取每一个购物车中的商品项
                CartItem cartItem = entry.getValue();
                // 转换为每一个订单项
                OrderItem orderItem = new
                        OrderItem(null,cartItem.getName(),cartItem.getCount(),cartItem.getPrice(),cartItem.getTotalPrice(),
                        orderId);
                // 保存订单项到数据库
                orderItemDao.saveOrderItem(conn,orderItem);

                //int c=1/0;//手动产生一个异常，以供测试事务

                // (3)更新库存和销量
                Book book = bookDao.queryBookById(conn,cartItem.getId());
                book.setSales( book.getSales() + cartItem.getCount() );
                book.setStock( book.getStock() - cartItem.getCount() );
                bookDao.updateBook(conn,book);
            }

            //(4)清空购物车
            cart.clear();

            //手动提交事务
            conn.commit();

            return orderId;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                //有异常 回滚事务
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally{
            try {
                //释放连接之前恢复自动提交
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResource(conn,null,null);
        }
        return null;
    }
}
