package com.loong.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
/**
 * @author LoongKK
 * @create 2022/9/26-23:49
 */
public class JDBCUtils {
    private static DataSource source;
    static {
        try {
            Properties pros=new Properties();
            pros.load(JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            source = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception{
        Connection conn = source.getConnection();
        return conn;
    }

    public static void closeResource(Connection conn, Statement ps, ResultSet rs){
        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(rs);
    }
}
