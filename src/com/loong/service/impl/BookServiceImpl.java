package com.loong.service.impl;

import com.loong.dao.BookDao;
import com.loong.dao.impl.BookDaoImpl;
import com.loong.pojo.Book;
import com.loong.pojo.Page;
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
    BookDao bookDao = new BookDaoImpl();

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
            if (bookDao.deleteBookById(conn, id) == 1) {
                //删除成功
            } else {
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
            bookDao.updateBook(conn, book);
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

            return bookDao.queryBookById(conn, id);

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

    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            Page<Book> page = new Page<>();

            //设置每页显示的数量
            page.setPageSize(pageSize);

            //求总记录数
            int pageTotalCount = bookDao.queryForPageTotalCount(conn);
            //设置记录数
            page.setPageTotalCount(pageTotalCount);

            //求总页码。(注意 数量不足一页的也要按一页显示)
            int pageTotal = pageTotalCount / pageSize;
            if (pageTotalCount % pageSize > 0) { //总记录数%每页数量>0，则总页码+1
                pageTotal += 1;
            }
            //设置总页码
            page.setPageTotal(pageTotal);

            //设置当前页码
            page.setPageNo(pageNo);

            //当前页数据的开始索引
            int begin = (page.getPageNo() - 1) * pageSize;
            //当前页数据
            page.setItems(bookDao.queryForPageItems(conn, begin, pageSize));

            return page;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null, null);
        }
        return null;

    }


}
