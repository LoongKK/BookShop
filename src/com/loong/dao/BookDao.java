package com.loong.dao;

import com.loong.pojo.Book;

import java.sql.Connection;
import java.util.List;

/**
 * @author LoongKK
 * @create 2022/10/3-16:07
 */
public interface BookDao {
    //添加图书
    public int addBook(Connection conn,Book book);

    //根据id删除图书
    public int deleteBookById(Connection conn, Integer id);

    //修改图书信息
    public int updateBook(Connection conn,Book book);

    //根据id查询图书
    public Book queryBookById(Connection conn,Integer id);

    //查询图书，返回一个list
    public List<Book> queryBooks(Connection conn);

    /**
     * 查询总记录数
     * @param conn
     * @return 总记录数PageTotalCount
     */
    public Integer queryForPageTotalCount(Connection conn);

    /**
     * 查询当前页的Book数据
     * @param conn
     * @param begin
     * @param pageSize
     * @return 当前页的size个Book对象元素的List集合
     */
    public List<Book> queryForPageItems(Connection conn, int begin, int pageSize);
}