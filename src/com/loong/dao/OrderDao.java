package com.loong.dao;

import com.loong.pojo.Order;

import java.sql.Connection;
import java.util.List;

/**
 * @author LoongKK
 * @create 2022/10/10-12:16
 */
public interface OrderDao {
    /**
     * 保存订单
     */
    int saveOrder(Connection conn, Order order);
    /**
     *查询全部订单
     */
    List<Order> queryOrders(Connection conn);
    /**
     *修改订单状态
     */
    int changeOrderStatus(Connection conn,String orderId,Integer status);
    /**
     * 根据用户编号查询订单信息
     */
    Order queryOrderByUserId(Connection conn,Integer userId);
}
