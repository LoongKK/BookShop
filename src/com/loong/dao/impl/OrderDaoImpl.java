package com.loong.dao.impl;

import com.loong.dao.OrderDao;
import com.loong.pojo.Order;

import java.sql.Connection;
import java.util.List;

/**
 * @author LoongKK
 * @create 2022/10/10-15:25
 */
public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {
    @Override
    public int saveOrder(Connection conn, Order order) {
        String sql="INSERT INTO t_order (`order_id`,`create_time`,`price`,`status`,`user_id`) "+
                "VALUE(?,?,?,?,?)";
        return update(conn,sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
    }

    @Override
    public List<Order> queryOrders(Connection conn) {
        String sql="SELECT `order_id` orderId,`create_time` createTime,`price`,`status`,`user_id` userId FROM t_order";
        return getBeanList(conn,sql);
    }

    @Override
    public int changeOrderStatus(Connection conn, String orderId, Integer status) {
        String sql="UPDATE t_order SET `status`=? WHERE order_id=?";
        return update(conn,sql,status,orderId);
    }

    @Override
    public Order queryOrderByUserId(Connection conn, Integer userId) {
        String sql="SELECT `order_id` orderId,`create_time` createTime,`price`,`status`,`user_id` userId " +
                "FROM t_order WHERE user_id=?";
        return getBean(conn,sql,userId);
    }
}
