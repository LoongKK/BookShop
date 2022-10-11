package com.loong.test;

import com.loong.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author LoongKK
 * @create 2022/9/27-0:02
 */
public class Utilstest {
    @Test
    public void testJDBCUtils() throws Exception{
        Connection conn = JDBCUtils.getConnection();
        System.out.println(conn);
        JDBCUtils.closeConnection();
    }
}
