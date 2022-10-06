package com.loong.test;

import com.loong.pojo.Book;
import com.loong.pojo.Page;
import com.loong.service.BookService;
import com.loong.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author LoongKK
 * @create 2022/10/3-17:34
 */
public class BookServiceTest {
    private BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
        Book book = new Book(null, "java编程思想(弹幕版)", "海岸线", BigDecimal.valueOf(9.9), 2, 30, null);
        bookService.addBook(book);
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(22);
    }

    @Test
    public void updateBook() {
        int id=2;
        Book book = bookService.queryBookById(id);
        book.setSales(book.getSales()+1);
        book.setStock(book.getStock()-1);

        bookService.updateBook(book);
    }

    @Test
    public void queryBookById() {
        System.out.println(bookService.queryBookById(2));
    }

    @Test
    public void queryBooks() {
        List<Book> bookList = bookService.queryBooks();
        for (Book book : bookList) {
            System.out.println(book);
        }
    }

    @Test
    public void page() {
        Page<Book> page = bookService.page(2, 4);
        System.out.println(page);
    }

    @Test
    public void pageByPrice() {
        Page<Book> page = bookService.pageByPrice(2, 4,0,500);
        System.out.println(page);
    }
}