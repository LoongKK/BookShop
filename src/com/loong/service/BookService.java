package com.loong.service;

import com.loong.pojo.Book;
import com.loong.pojo.Page;

import java.util.List;

/**
 * @author LoongKK
 * @create 2022/10/3-17:07
 */
public interface BookService {
    public void addBook(Book book);
    public void deleteBookById(Integer id);
    public void updateBook(Book book);
    public Book queryBookById(Integer id);
    public List<Book> queryBooks();

    /**
     * 返回一个包含数据后的Page对象
     * @param pageNo
     * @param pageSize
     * @return 包含数据的Page对象
     */
    Page<Book> page(int pageNo, int pageSize);

    /**
     * 返回包含价格区间内Book信息的Page对象
     * @param pageNo
     * @param pageSize
     * @param min
     * @param max
     * @return
     */
    Page<Book> pageByPrice(int pageNo, int pageSize,int min,int max);
}
