package com.loong.dao.impl;

import com.loong.dao.BookDao;
import com.loong.dao.UserDao;
import com.loong.pojo.Book;
import com.loong.pojo.User;

import java.sql.Connection;
import java.util.List;

/**
 * @author LoongKK
 * @create 2022/10/3-16:13
 */
public class BookDaoImpl extends BaseDao<Book> implements BookDao{


    @Override
    public int addBook(Connection conn, Book book) {
        String sql="INSERT INTO t_book(`id`,`name`,`author`,`price`,`sales`,`stock`,`img_path`) " +
                "VALUES(?,?,?,?,?,?,?);";
        return update(conn,sql,book.getId(),book.getName(),book.getAuthor(),
                book.getPrice(), book.getSales(),book.getStock(),book.getImgPath());
    }

    @Override
    public int deleteBookById(Connection conn, Integer id) {
        String sql="DELETE FROM t_book WHERE `id`=?";
        return update(conn,sql,id);
    }

    @Override
    public int updateBook(Connection conn, Book book) {
        String sql="UPDATE t_book SET `name`=?,`author`=?,`price`=?,`sales`=?,`stock`=?,`img_path`=? WHERE `id`=?";
        return update(conn,sql,book.getName(),book.getAuthor(),
                book.getPrice(), book.getSales(),book.getStock(),book.getImgPath(),book.getId());
    }

    @Override
    public Book queryBookById(Connection conn, Integer id) {
        String sql="SELECT `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` imgPath From t_book WHERE id=?";//imgPath是别名
        return getBean(conn,sql,id);
    }

    @Override
    public List<Book> queryBooks(Connection conn) {
        String sql="SELECT `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` imgPath From t_book";//imgPath是别名
        return getBeanList(conn,sql);
    }

    @Override
    public Integer queryForPageTotalCount(Connection conn) {
        String sql="SELECT COUNT(*) FROM t_book";
        //Integer pageTotalCount = (Integer) getValue(conn, sql, null);
        // 直接强转为Integer报错java.lang.Long cannot be cast to java.lang.Integer
        //返回的count(*)是Object型，运行时类型是Long型的
        //java.lang.Number是Integer,Long的父类。而Long和Integer不具有继承关系
        //解决：
        Number count = (Number) getValue(conn, sql, null);
        //Number对象调用inValue()方法转换为int类型
        return count.intValue();
    }

    @Override
    public List<Book> queryForPageItems(Connection conn, int begin, int pageSize) {
        String sql = "SELECT `id`,`name`,`author`,`price`,`sales`,`stock`,`img_path` imgPath " +
                "FROM t_book LIMIT ?,?";
        return getBeanList(conn,sql,begin,pageSize);
    }


}
