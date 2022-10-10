package com.loong.dao.impl;

import com.loong.dao.OrderItemDao;
import com.loong.pojo.OrderItem;

import java.sql.Connection;

/**
 * @author LoongKK
 * @create 2022/10/10-17:42
 */
public class OrderItemDaoImpl extends BaseDao<OrderItem> implements OrderItemDao {
    @Override
    public int saveOrderItem(Connection conn, OrderItem orderItem) {
        String sql="INSERT INTO t_order_item (`id`,`name`,`count`,`price`,`total_price`,`order_id`) " +
                "VALUE(?,?,?,?,?,?)";
        return update(conn,sql,orderItem.getId(),orderItem.getName(),orderItem.getCount(),orderItem.getPrice(),
                orderItem.getTotalPrice(),orderItem.getOrderId());
    }

    @Override
    public OrderItem queryOrderItemByOrderId(Connection conn, String orderId) {
        String sql="SELECT `id`,`name`,`count`,`price`,`total_price` totalPrice,`order_id` orderId FROM t_order_item WHERE order_id=?";
        return getBean(conn,sql,orderId);
    }
}
