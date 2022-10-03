package com.loong.test;


import com.loong.dao.BookDao;
import com.loong.dao.impl.BookDaoImpl;
import com.loong.pojo.Book;
import com.loong.utils.JDBCUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * @author LoongKK
 * @create 2022/10/3-16:39
 */
public class BookDaoTest {

    @Test
    public void addBook() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        BookDao bookDao = new BookDaoImpl();

        Book book = new Book(null, "java编程思想(弹幕版)", "海岸线", BigDecimal.valueOf(9.9), 2, 30, null);
        int count = bookDao.addBook(conn, book);
        System.out.println(count);

        JDBCUtils.closeResource(conn,null,null);
    }

    @Test
    public void deleteBookById() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        BookDao bookDao = new BookDaoImpl();

        System.out.println(bookDao.deleteBookById(conn, 21));

        JDBCUtils.closeResource(conn,null,null);
    }

    @Test
    public void updateBook() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        BookDao bookDao = new BookDaoImpl();

        int id=2;
        Book book = bookDao.queryBookById(conn,id);
        System.out.println(book);
        book.setSales(book.getSales()+1);
        book.setStock(book.getStock()-1);

        bookDao.updateBook(conn,book);

        book = bookDao.queryBookById(conn,id);
        System.out.println(book);

        JDBCUtils.closeResource(conn,null,null);
    }

    @Test
    public void queryBookById() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        BookDao bookDao = new BookDaoImpl();

        Book book = bookDao.queryBookById(conn, 2);
        System.out.println(book);

        JDBCUtils.closeResource(conn,null,null);
    }

    @Test
    public void queryBooks() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        BookDao bookDao = new BookDaoImpl();

        List<Book> bookList = bookDao.queryBooks(conn);
        for (Book book : bookList) {
            System.out.println(book);
        }

        JDBCUtils.closeResource(conn,null,null);

    }
}