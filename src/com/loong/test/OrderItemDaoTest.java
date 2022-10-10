package com.loong.test;

import com.loong.dao.OrderItemDao;
import com.loong.dao.impl.OrderItemDaoImpl;
import com.loong.pojo.OrderItem;
import com.loong.utils.JDBCUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * @author LoongKK
 * @create 2022/10/10-17:56
 */
public class OrderItemDaoTest {
    OrderItemDao orderItemDao = new OrderItemDaoImpl();
    @Test
    public void saveOrderItem() {
        try {
            Connection conn = JDBCUtils.getConnection();
            OrderItem orderItem = new OrderItem(null, "java编程", 2, BigDecimal.valueOf(25),
                    BigDecimal.valueOf(50),"1234567548");
            orderItemDao.saveOrderItem(conn,orderItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryOrderItemByOrderId() {
        try {
            Connection conn = JDBCUtils.getConnection();
            OrderItem orderItem = orderItemDao.queryOrderItemByOrderId(conn, "1234567548");
            System.out.println(orderItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}