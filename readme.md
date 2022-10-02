# BookShop
尚硅谷_书城项目
## 第一次提交  
空项目 静态资源  
(熟悉git使用)

## 第一阶段 表单验证的实现
### 需求
验证用户名：必须由字母，数字下划线组成，并且长度为 5 到 12 位  
验证密码：必须由字母，数字下划线组成，并且长度为 5 到 12 位  
验证确认密码：和密码相同  
邮箱验证：xxxxx@xxx.com  
验证码：现在只需要验证用户已输入。因为还没讲到服务器 验证码生成。
### 操作：  
①首先引入jQuery
文件放置在static/script/jquery-1.7.2.js  
在regist.html中引入jQuery。见pages/user/regist.html

②在regist中实现需求. 见pages/user/regist.html

## 第二阶段 用户登录和注册
### 一、需求
项目阶段二：用户注册和登陆的实现。
#### 需求 1：用户注册
1）访问注册页面  
2）填写注册信息，提交给服务器  
3）服务器应该保存用户  
4）当用户已经存在----提示用户注册 失败，用户名已存在  
5）当用户不存在-----注册成功  
#### 需求二：用户登陆  
1）访问登陆页面  
2）填写用户名密码后提交  
3）服务器判断用户是否存在  
4）如果登陆失败 --->>>> 返回用户名或者密码错误信息  
5）如果登录成功 --->>>> 返回登陆成功 信息  
### 二、搭建书城项目开发环境(准备工作)：  
①首先新建java模块BookShop，添加web支持，将第一阶段的静态资源复制到web目录下，可删掉默认的index.jsp。  
②配置"运行/调试配置”，本地Tomcat服务器 "BookShop_Tomcat 8.0.53"并添加工件（注意修改应用上下文为“/BookShop”）  
③创建包
```
└── com
    └── loong
        ├── dao Dao接口包
        │   └── impl  Dao接口实现类包
        ├── pojo  javabean类
        ├── service Service接口包
        │   └── impl Service接口实现类包
        ├── test 存放测试类
        ├── utils 存放工具类
        └── web web层
```

### 三、创建数据库和表
```mysql
drop database if exists book;
create database book;
use book;
create table t_user(
`id` int primary key auto_increment,
`username` varchar(20) not null unique,
`password` varchar(32) not null,
`email` varchar(200)
);
insert into t_user(`username`,`password`,`email`) values('admin','admin','admin@loong.com');
select * from t_user;
```

### 四、编写数据库对应的javaBean对象
com.loong.pojo 下 User.java 对应t_user表

### 五、编写工具类 JDBCUtils
（1）web/WEB-INF/lib文件夹导入jar包：  
  mysql-connector-java-8.0.26.jar、  
  druid-1.2.11.jar、  
  commons-dbutils-1.7.jar。    
（2）“库”将jar包添加为库BookShop_lib,“模块”给模块BookShop使用(添加依赖)，再在”工件“处修复（将模块需要的该库加入到工件）。  
（3）编写jdbc.properties  I（src下）  
（4）编写工具类JDBCUtils.java 包com.loong.utils  
（5）*测试工具类Utilstest.java 包com.loong.test  
### 六、Dao持久层
（1）BaseDao.java 基本Dao （com.loong.dao.impl下）  
（2）UserDao.java 接口 （com.loong.dao下）  
（3）UserDaoImpl.java 实现类 （com.loong.dao.impl下）  
(4)* UserDaoTest.java 测试类 （com.loong.test下）    
   在接口UserDao中右击 生成 测试... 全选方法
### 七、Service业务层
（1）UserService.java 接口  com.loong.service包  
（2）UserServiceImpl.java 实现类 com.loong.service.impl包下  
（3）UserServiceTest.java 测试类 com.loong.test包下  
### 八、Web层（视图展现层）
（先将servlet-api.jar加入到库BookShop_lib中）  
（servlet不管多复杂 永远都是那四步1.获取参数 2.调用Service 3.将数据共享到域 4.路径跳转）
#### 实现用户注册功能
当在注册页面用户输入完成并点击提交，将参数发送给服务器来注册保存。  
1.RegistServlet程序：
RegistServlet.java（com.loong.web 包下）  
逻辑：
```
获取请求参数;
if(验证码正确){
    if(用户名可用){
        调用Service保存到数据库;
        跳转到注册成功页面regist_success.html;
    }else{
        跳回注册页面regist.html;
        }
}else{
    跳回注册页面regist.html;
}
```
并配置web.xml。  
2.修改页面regist.html和regist_success.html  
[regist.html](web/pages/user/regist.jsp)  
[regist_success.html](web/pages/user/regist_success.jsp)  
主要改动：  
（1）两个html中都增加`<base>` 标签（永远固定相对路径跳转的结果），并修改受其影响的相对路径。  
实际项目中，base的harf属性值一般写到工程路径。  
`<base href="http://localhost:8080/BookShop/">`  
然后修改受其影响的相对路径（技巧：浏览器预览页面，F12看控制台报红的找不到的js、css、图片等资源）  
“base写到工程路径”相当于到了web目录下，再相对web目录来写相对路径，如  
`<link type="text/css" rel="stylesheet" href="static/css/style.css" >`  
（实际文件路径BookShop项目/web/static/css/style.css）  
（2）修改regist.html中表单提交的地址为Servlet程序,请求方式为post。  
`<form action="registServlet" method="post">`
#### 实现用户登录功能
当用户输入完毕，点击”登录“（提交）后，将参数发送给服务器。  
1.LoginServlet程序  
LoginServlet.java（com.loong.web 包下）  
逻辑:
```
获取请求的参数；
调用XxxService.xxx()处理业务。如userService。login()登录;
if(根据Login（）方法返回结果判断,登录成功?){
    跳转到成功页面login_success.html;
 }else{
    跳回登录页面；
 }
```

并配置web.xml。
2.修改login.html页面和login_success.html页面(参考前面的，不多说)  
（1）添加 base 标签，并修改相对路径  
（2）修改login.html中表单提交的地址为Servlet程序,请求方式为post  


# 书城项目第三阶段：代码优化

## 页面jsp动态化  
1、在 html 页面顶行添加 page 指令。  
 即`<%@ page contentType="text/html;charset=UTF-8" language="java" %>`  
```shell
#我用的linux子系统（ubuntu） 在pages文件夹下
$ sed -i '1i <%@ page contentType="text/html;charset=UTF-8" language="java" %>' */*.html
```
2、修改文件后缀名为：.jsp(重命名快捷键shift+F6)  
```shell
#我用的linux子系统（ubuntu） 在pages文件夹下
$ rename 's/\.html/\.jsp/' */*.html
```
3、使用 IDEA 搜索替换.html 为.jsp(快捷键：Ctrl+Shift+R)
```shell
#我用的linux子系统（ubuntu） 在pages文件夹下
$ sed -n 's/\.html/\.jsp/p' */*.jsp #预览
$ sed -i 's/\.html/\.jsp/g' */*.jsp #直接对文件操作
```
(除了pages下的页面，别忘了还有index.html需要做这些修改。)
(Servlet程序里的请求转发也要改成".jsp"。)
## 抽取页面中相同的内容  
将不同页面的相同内容抽取为一个公共页面，通过包含或页面转发使用。  
web/pages下新建目录common存放公共页面

（1）head 中 css、jquery、base 标签  
head.jsp
```html
<body>
<%
//    "http://localhost:8080/BookShop/"
    String basePath = request.getScheme()
            + "://"
            + request.getServerName()
            + ":"
            + request.getServerPort()
            + request.getContextPath()
            + "/";
%>

<%--动态的获取base的href属性值<%=basePath%>--%>
<base href="<%=basePath%>">
<link type="text/css" rel="stylesheet" href="static/css/style.css" >
<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>
</body>
```

静态包含:`<%@ include file="/pages/common/head.jsp" %>`  
然后每个页面不对的相对路径也要改。  
（2）每个页面的页脚
footer.jsp
```html
<body>
<div id="bottom">
		<span>
			尚硅谷书城.Copyright &copy;2015
		</span>
</div>
</body>
```

静态包含`<%@ include file="/pages/common/footer.jsp" %>`  
（3）登录成功后的菜单   
login_success_menu.jsp
```html
<body>
<div>
    <span>欢迎<span class="um_span">韩总</span>光临尚硅谷书城</span>
    <a href="pages/order/order.jsp">我的订单</a>
    <a href="index.jsp">注销</a>&nbsp;&nbsp;
    <a href="index.jsp">返回</a>
</div>
</body>
```
然后所有有相同内容的页面，采用 静态包含 此页面，替换相同内容  
```<%@ include file="/pages/common/login_success_menu.jsp" %>```   
（4）manager 模块的菜单  
manager_menu.jsp
```html
<body>
<div>
    <a href="pages/manager/book_manager.jsp">图书管理</a>
    <a href="pages/manager/order_manager.jsp">订单管理</a>
    <a href="index.jsp">返回商城</a>
</div>
</body>
```
静态包含`<%@ include file="/pages/common/manager_menu.jsp" %>`
## 登录，注册错误提示，及表单回显  
当用户名或密码错误时，跳回原来的页面 并 回显错误信息 和 表单项信息。  
比如登录失败，返回登录页面并提示“用户名或密码错误”，然后自动填充刚才输入的用户名到用户名输入框。   
以登录回显为示例：
Servlet 程序端需要添加回显信息到 Request 域中
修改LoginServlet.java
```java
//System.out.println("登录失败");
            //把错误信息、回显的表单项信息，保存到Request域中
            req.setAttribute("msg","用户名或密码错误");
            req.setAttribute("username",username);
            //跳转登录页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
```
jsp 页面，需要输出回显信息。修改login.jsp
```html
<span class="errorMsg">
<!--动态显示回显的错误信息-->
<%=request.getAttribute("msg")==null?"请输入用户名和密码":request.getAttribute("msg")%>
</span>
						
。。。。。。
<label>用户名称：</label>
<!--设置value属性 动态的表单项回显-->
<input class="itxt" type="text" placeholder="请输入用户名" autocomplete="off" tabindex="1" name="username"
value="<%=request.getAttribute("username")==null?"":request.getAttribute("username")%>"
/>
```

注册回显:  
回显"验证码"和”用户名已存在“错误信息，并回显表单项username和email即可。见RegistServlet.java和regist.jsp


# 书城项目第四阶段：代码优化
## 合并LoginServlet和RegistServlet程序为UserServlet程序
在实际的项目开发中，一个模块，一般只使用一个 Servlet 程序。  
LoginServlet和RegistServlet程序都属于用户模块。合并成一个UserServlet程序。
(1)login.jsp和regist.jsp页面都添加隐藏域action，和修改表单请求地址为userServlet
```html
<!--修改表单请求地址-->
<form action="userServlet" method="post">
    <!--添加隐藏域 action，值为login-->
    <input type="hidden" name="action" value="login">
```
```html
<!--修改表单请求地址-->
<form action="userServlet" method="post">
    <!--添加隐藏域 action，值为regist-->
    <input type="hidden" name="action" value="regist">
```
(2)UserServlet.java  
基本逻辑：
```java
public class UserServlet extends HttpServlet {

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //原来LoginServlet中的代码。。。
    }

    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //原来RegistServlet中的代码。。。
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if("login".equals(action)){
            //登录
            login(request, response);
        }else if ("regist".equals(action)){
            //注册
            regist(request,response);
        }
    }
}
```
## 使用反射优化大量 else if 代码
除了登录和注册，还有很多和用户模块有关的功能，比如：添加用户、修改用户信息、修改邮箱、修改密码、绑定手机号、绑定邮箱、注销用户等。  
若都在UserServlet的doPost方法中采用else if判断action,会有大量代码。—— 使用反射优化  
```java
   String action = request.getParameter("action");

   //使用反射优化大量 else if 代码
   try {
       //获取 action 业务鉴别字符串，获取相应的业务 方法反射对象
        //比如根据action=“login"找方法名login的方法 
       Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
     
       // 调用目标业务 方法
       method.invoke(this, request, response);
   } catch (Exception e) {
       e.printStackTrace();
   }
```
## 抽取BaseServlet
一些代码（比如获取action参数值、通过反射获取action对应的业务方法、通过反射调用业务方法）等不仅是用户模块(UserServlet程序)需要的，其它模块比如图书模块BookServlet也需要这些。  
抽取一个BaseServlet类，UserServlet和BookServlet等只要继承BaseServlet就可以。  
BaseServlet.java(com.loong.web包下)
①是抽象类 ②继承HttpServlet ③相同的代码写在里面 ④不需要配置在web.xml中
```java
public abstract class BaseServlet extends HttpServlet{
    //这里是以前UserServlet中doPost方法代码，复制过来。。。
}
```

⑤UserServlet或其它功能模块需要继承BaseServlet
```java
public class UserServlet extends BaseServlet {
    //doPost抽取到BaseServlet中了，只需继承BaseServlet即可(也就继承了其中的doPost方法)

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //。。。
    }

    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //...
        }
    }
}
```
## 数据的封装和抽取 BeanUtils 的使用
以前获取参数、封装成javabean，如果参数过多会很麻烦。
```java
        //获取参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
                ...
                //封装成bean
                userService.registUser(new User(null,username,password,email));
```
BeanUtils 工具类的一个重要作用是，它可以一次性的把所有请求的参数注入到 JavaBean 中。  
BeanUtils 工具类，经常用于把 Map 中的值注入到 JavaBean 中，或者是对象属性值的拷贝操作。  
1、导入需要的 jar 包：  
commons-beanutils-1.8.0.jar、 commons-logging-1.1.1.jar  
（最新版commons-beanutils-1.9.4.jar、commons-logging-1.2.jar，注意：新版的还依赖commons-collections-3.2.2.jar别用最新的4.4！！  ）  
下载仓库：
[官网](https://commons.apache.org/proper/) [阿里](https://mirrors.aliyun.com/apache/commons/)  
（放入web/WEB-INF/lib下，添加到Book_lib库）  
2、编写 WebUtils 工具类使用：  
WebUtils.java（）：  
```java
package com.loong.utils;

import org.apache.commons.beanutils.BeanUtils;
import java.util.Map;

public class WebUtils {
    /**
     * 把 Map 中的值注入到对应的 JavaBean 属性中。并返回新的bean
     * @param properties
     * @param bean
     */
    public static <T> T copyParamToBean(Map properties , T bean ){
        try {
            //把所有请求的参数都注入到 bean 对象中
//static void populate(Object bean, Map<String,? extends Object> properties)
            //根据指定的name-value对填充指定bean的属性。
            // 底层依赖于javabean中的setter
            BeanUtils.populate(bean, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
```
以注册方法为例演示如何使用：
```java
 protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //使用BeanUtils优化：
        //获取获取含所有参数(name-value对)的Map,并封装到javabean的属性，返回封装了数据的user
        User user = WebUtils.copyParamToBean(req.getParameterMap(), new User());
        //下面用参数的时候，直接通过bean的getter调用。如user.getUsername()相当于req.getParameter("username")
        //甚至”保存到数据库“时直接使用user对象
        
        if("abcde".equals(req.getParameter("code"))){
        UserService userService=new UserServiceImpl();
        if(!userService.existsUsername(user.getUsername())){//通过getter使用参数
        userService.registUser(user);//直接存user
        req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,resp);
        }else
        。。。
        }
```
## 使用EL表达式修改表单回显
在第三阶段 使用jsp做回显，可使用EL表达式进行优化。  
修改login.jsp和regist.jsp  
如：
```html
<span class="errorMsg">
<!--动态显示回显的错误信息-->
<!--<%=request.getAttribute("msg")==null?"请输入用户名和密码":request.getAttribute("msg")%>-->
    	${empty requestScope.msg?"请输入用户名和密码":requestScope.msg}
</span>
						
。。。。。。
<label>用户名称：</label>
<!--设置value属性 动态的表单项回显-->
<input class="itxt" type="text" placeholder="请输入用户名" autocomplete="off" tabindex="1" name="username"
<!--value="<%=request.getAttribute("username")==null?"":request.getAttribute("username")%>"-->
value="${requestScope.username}"
<!--el表达式如果是null自然就返回空字符串""-->
/>
```
bug修改：  
①login.jsp的el表达式写错了，导致表单项回显用户名那里写的是错误信息”用户名或密码错误“而不是用户名  
②注册提交后报错(少了一个依赖包)   
debug发现`java.lang.ClassNotFoundException: org.apache.commons.collections.FastHashMap`  
[官网查看依赖](https://commons.apache.org/proper/commons-beanutils/dependencies.html)
发现新版commons-beanutils.jar包 不仅依赖commons-logging.jar还依赖	commons-collections.jar  
添加commons-collections-3.2.2.jar后可以了（不要用新版本4.4的）
参考https://blog.csdn.net/weixin_45454773/article/details/122262709
https://www.jianshu.com/p/8d870e807aaf
