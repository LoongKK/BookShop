package com.loong.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
/**工具类V5.0 使用ThreadLocal的方式进行事务管理
 * 依赖Druid、DbUtils
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

//---------------------------使用ThreadLocal--------------------------------
    private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
    /**
     * 使用ThreadLocal 的 getConnection方法
     * 返回一个连接。返回null则获取连接失败
     */
    public static Connection getConnection() {
        Connection conn = tl.get();
        if (conn == null) {
            try {
                conn = source.getConnection();
                tl.set(conn); // 保存到 ThreadLocal 对象中，供后面的 jdbc 操作使用
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     *开始事务
     */
    public static void beginTransaction(){
        Connection conn=getConnection();//这里不用tl.get()好处是 可以判断null 并获取连接，可以在Filter前使用
        if (conn != null) {
            try {
                conn.setAutoCommit(false);//禁用自动提交
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commit(){
        Connection conn = tl.get();
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     *  回滚事务
     */
    public static void rollback(){
        System.out.println("回滚");
        Connection conn = tl.get();
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     *关闭连接
     */
    public static void closeConnection(){
        Connection conn=tl.get();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);//关闭前恢复自动提交
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //DbUtils.closeQuietly(conn); //DbUtils工具类中的关闭方法可以 非null判断、异常处理
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 一定要执行 remove 操作，否则就会出错。（因为 Tomcat 服务器底层使用了线程池技术）
        tl.remove();
    }

}
