package com.loong.test;

import com.loong.dao.OrderDao;
import com.loong.dao.impl.OrderDaoImpl;
import com.loong.pojo.Order;
import com.loong.utils.JDBCUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author LoongKK
 * @create 2022/10/10-15:50
 */
public class OrderDaoTest {
    OrderDao orderDao=new OrderDaoImpl();
    @Test
    public void saveOrder() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            orderDao.saveOrder(conn,new Order("1234567548", LocalDateTime.now(),BigDecimal.valueOf(100),0,1));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void queryOrders() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            List<Order> orders = orderDao.queryOrders(conn);
            for (Order order : orders) {
                System.out.println(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void changeOrderStatus() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            orderDao.changeOrderStatus(conn,"123456758",1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryOrderByUserId() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Order order = orderDao.queryOrderByUserId(conn, 1);
            System.out.println(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}