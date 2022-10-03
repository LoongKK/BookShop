package com.loong.service.impl;

import com.loong.dao.BookDao;
import com.loong.dao.impl.BookDaoImpl;
import com.loong.pojo.Book;
import com.loong.service.BookService;
import com.loong.utils.JDBCUtils;
import jdk.nashorn.internal.scripts.JD;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author LoongKK
 * @create 2022/10/3-17:11
 */
public class BookServiceImpl implements BookService {
    BookDao bookDao=new BookDaoImpl();

    @Override
    public void addBook(Book book) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            if (bookDao.addBook(conn, book) == 1) {
                //添加成功
            } else {
                //添加失败
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null, null);
        }
    }

    @Override
    public void deleteBookById(Integer id) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            if(bookDao.deleteBookById(conn,id)==1){
                //删除成功
            }else{
                //删除失败
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null, null);
        }
    }

    @Override
    public void updateBook(Book book) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            bookDao.updateBook(conn,book);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null, null);
        }
    }

    @Override
    public Book queryBookById(Integer id) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            return bookDao.queryBookById(conn,id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null, null);
        }
        return null;
    }

    @Override
    public List<Book> queryBooks() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            return bookDao.queryBooks(conn);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null, null);
        }
        return null;
    }
}
