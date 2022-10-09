package com.loong.web; /**
 * @author LoongKK
 * @create 2022/10/2-21:54
 */

import com.loong.pojo.User;
import com.loong.service.UserService;
import com.loong.service.impl.UserServiceImpl;
import com.loong.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.Method;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;


public class UserServlet extends BaseServlet {
    //doPost抽取到BaseServlet中了，只需继承BaseServlet即可(也就继承了其中的doPost方法)

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //原来LoginServlet中的代码
        //获取参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //登录业务
        UserService userService = new UserServiceImpl();
        User loginUser = userService.login(new User(null, username, password, null));

        //根据登录结果进行跳转
        if (loginUser != null) {
            System.out.println("登录成功");
            //保存用户登录信息到Session域中
            req.getSession().setAttribute("user", loginUser);

            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        } else {
            //System.out.println("登录失败");
            //把错误信息、回显的表单项信息，保存到Request域中
            req.setAttribute("msg", "用户名或密码错误");
            req.setAttribute("username", username);
            //跳转登录页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        }
    }

    /**
     * 注销
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1、销毁 Session 中用户登录的信息（或者销毁 Session）
        req.getSession().invalidate();
        //2、重定向到首页（或登录页面）。
        resp.sendRedirect(req.getContextPath());
    }

    //使用BeanUtils(封装在WebUtils工具类中)的regist方法：
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //原来RegistServlet中的代码

        //使用BeanUtils优化：
        //获取获取含所有参数(name-value对)的Map,并封装到javabean的属性，返回封装了数据的user
        User user = WebUtils.copyParamToBean(req.getParameterMap(), new User());
        //下面用参数的时候，直接通过bean的getter调用。如user.getUsername()相当于req.getParameter("username")
        //甚至”保存到数据库“时直接使用user对象

        // 获取 Session 中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        //验证码是否正确。（目前没学如何生成验证码，是写死的"abcde"）
        if (token != null && token.equalsIgnoreCase(req.getParameter("code"))) {
            UserService userService = new UserServiceImpl();
            //用户名是否可用
            if (!userService.existsUsername(user.getUsername())) {
                //保存到数据库
                userService.registUser(user);
//                //跳转到注册成功页面
//                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
                //因为要解决刷新 表单重复提交问题，使用重定向
                resp.sendRedirect(req.getContextPath()+"/pages/user/regist_success.jsp");
                System.out.println("注册成功");
            } else {
                //回显的错误信息
                req.setAttribute("msg", "用户名已存在！");
                //回显的表单项值信息
                req.setAttribute("username", user.getUsername());
                req.setAttribute("email", user.getUsername());

                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            }
        } else {
            //回显的错误信息
            req.setAttribute("msg", "验证码错误！");
            //回显的表单项值信息
            req.setAttribute("username", user.getUsername());
            req.setAttribute("email", user.getEmail());

            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }
    }

/*//未使用BeanUtils(封装在WebUtils工具类中)的regist方法：
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //原来RegistServlet中的代码
        //获取参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");//注意在html里为验证码添加属性name="code"

        //验证码是否正确。（目前没学如何生成验证码，是写死的"abcde"）
        if("abcde".equals(code)){
            UserService userService=new UserServiceImpl();
            //用户名是否可用
            if(!userService.existsUsername(username)){
                //保存到数据库
                userService.registUser(new User(null,username,password,email));
                //跳转到注册成功页面
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,resp);
                System.out.println("注册成功");
            }else{
                //System.out.println("用户名已存在");
                //回显的错误信息
                req.setAttribute("msg","用户名已存在！");
                //回显的表单项值信息
                req.setAttribute("username",username);
                req.setAttribute("email",email);

                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
            }
        }else{
            //System.out.println("验证码错误");
            //回显的错误信息
            req.setAttribute("msg","验证码错误！");
            //回显的表单项值信息
            req.setAttribute("username",username);
            req.setAttribute("email",email);

            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
        }
    }
*/
}
