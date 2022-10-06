package com.loong.web;

import com.loong.pojo.Book;
import com.loong.pojo.Page;
import com.loong.service.BookService;
import com.loong.service.impl.BookServiceImpl;
import com.loong.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LoongKK
 * @create 2022/10/5-23:21
 */
public class ClientBookServlet extends BaseServlet{
    BookService bookService = new BookServiceImpl();
    //和BookServlet中的page方法几乎一样，只是请求地址改为/pages/client/index.jsp
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1、获取请求的参数pageNo和pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //2、调用bookService.page(pageNo,pageSize)获取Page对象
        Page<Book> page=bookService.page(pageNo,pageSize);
        //设置 url 的分页请求地址（为”分页条的抽取“）
        page.setUrl("client/bookServlet?action=page");
        //3、Page对象保存到Request域中
        req.setAttribute("page",page);
        //4、请求转发到/pages/client/index.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }

    //处理价格区间的分页
    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1、获取请求的参数pageNo，pagesize，min，max
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.parseInt(req.getParameter("min"), 0);
        int max = WebUtils.parseInt(req.getParameter("max"), Integer.MAX_VALUE);
        //2、获取Page对象
        Page<Book> page = bookService.pageByPrice(pageNo, pageSize, min, max);

        //解决分页条跳转页面没有价格回显 要在设置分页的url时，把min、max带上
        StringBuffer sb = new StringBuffer("client/bookServlet?action=pageByPrice");
        //如果输入框填写了最小价格的话，就带上min——注意这里是网页传来的参数min 不是进行类型转换后的。
        if(req.getParameter("min")!=null){
            sb.append("&min=").append(req.getParameter("min"));
        }
        //如果输入框填写了最小价格的话，就带上max——注意这里是网页传来的参数max 不是进行类型转换后的。
        if(req.getParameter("max")!=null){
            sb.append("&max=").append(req.getParameter("max"));
        }
        //设置 url 的分页请求地址（为”分页条的抽取“）
        page.setUrl(sb.toString());

        //3、Page对象保存到Request域中
        req.setAttribute("page",page);
        //4、请求转发到/pages/client/index.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }
}
