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
}
