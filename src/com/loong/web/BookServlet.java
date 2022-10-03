package com.loong.web;

import com.loong.pojo.Book;
import com.loong.service.BookService;
import com.loong.service.impl.BookServiceImpl;
import com.loong.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author LoongKK
 * @create 2022/10/3-17:44
 */
public class BookServlet extends BaseServlet{
    BookService bookService = new BookServiceImpl();

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.查询全部图书
        List<Book> books = bookService.queryBooks();
        //2.报存到Request域中
        request.setAttribute("books",books);
        //3.请求转发到/pages/manager/book_manager.jsp页面
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);
    }

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求的参数，封装为Book对象（使用BeanUtils工具类）
        Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());
        //2.调用bookService.addBook() 保存图书
        bookService.addBook(book);
        //3.跳到"/BookShop/pages/manager/bookServlet?action=list"（注意：要用重定向）
        response.sendRedirect(request.getContextPath()+"/manager/bookServlet?action=list");
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取请求的参数id,图书
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        //2、调用bookService.deleteBookById()删除图书
        bookService.deleteBookById(id);
        //3、重定向回图书列表管理页面
        response.sendRedirect(request.getContextPath()+"/manager/bookServlet?action=list");
    }

    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取待修改的图书编号
        int id=WebUtils.parseInt(request.getParameter("id"),0);
        //2、获取待修改的图书对象（通过调用bookService.queryBookById(id)）
        Book book = bookService.queryBookById(id);
        //3、把图书对象保存到Request域中
        request.setAttribute("book",book);
        //4、请求转发到"编辑图书"页面 "/pages/manager/book_edit.jsp"
        request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request,response);
    }
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求的参数并封装为Book对象
        Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());
        //2.修改图书。
        bookService.updateBook(book);
        //3.重定向回图书管理页面
        response.sendRedirect(request.getContextPath()+ "/manager/bookServlet?action=list");
    }
}
