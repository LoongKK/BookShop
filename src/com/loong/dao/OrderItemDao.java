package com.loong.dao;

import com.loong.pojo.Order;
import com.loong.pojo.OrderItem;

import java.sql.Connection;

/**
 * @author LoongKK
 * @create 2022/10/10-17:36
 */
public interface OrderItemDao {
    /**
     *保存订单项
     */
    int saveOrderItem(Connection conn, OrderItem orderItem);
    /**
     *根据订单号查询订单明细
     */
    OrderItem queryOrderItemByOrderId(Connection conn, String orderId);
}
