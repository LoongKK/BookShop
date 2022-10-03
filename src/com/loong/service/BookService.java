package com.loong.service;

import com.loong.pojo.Book;

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
}
