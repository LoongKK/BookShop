<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员注册页面</title>
	<!--静态包含-->
	<%@ include file="/pages/common/head.jsp" %>
	<script type="text/javascript">

		//页面加载完成之后
		$(function(){

			//”注册“（提交commit）绑定单击事件。（也可以对每个输入框用失去焦点事件  $("iniput[name='username']").bind('mouseover',function(){})）
			$("#sub_btn").click(function (){
				// 验证用户名：必须由字母，数字下划线组成，并且长度为 5 到 12 位
				//1 获取用户名输入框里的内容
				var usernameText = $("#username").val();
				//2 创建正则表达式对象
				var usernamePatt=/^\w{5,12}$/;
				//3 使用test方法验证。
				if (!usernamePatt.test(usernameText)){//取反 不合法的情况
				//4 提示用户结果
				//可以看到“注册尚硅谷会员”后面有个class为errorMsg的span，是用来错误信息的地方
					$("span.errorMsg").text("用户名必须由字母，数字下划线组成，并且长度为5到12位");
					//取消默认动作。防止跳转
					return false;
				}

				// 验证密码：必须由字母，数字下划线组成，并且长度为 5 到 12 位
				var passwordText = $("#password").val();
				var passwordPatt=/^\w{5,12}$/;
				if (!passwordPatt.test(passwordText)){
					$("span.errorMsg").text("密码必须由字母，数字下划线组成，并且长度为5到12位");

					return false;
				}

				// 验证确认密码：和密码相同
				//1 获取密码内容
				var repwdText = $("#repwd").val();
				//2 和密码比较
				if (repwdText != passwordText){
					$("span.errorMsg").text("两次输入的密码不一致");

					return false;
				}
				// 邮箱验证：xxxxx@xxx.com
				//1 获取输入的邮箱地址
				var emailText = $("#email").val();
				//2 创建正则表达式对象
				var emailPatt = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
				//3 使用test验证
				if (!emailPatt.test(emailText)) {
					$("span.errorMsg").text("邮箱地址不正确！");
					return false;
				}

				// 验证码：现在只需要验证用户已输入。因为还没讲到服务器 验证码生成。
				var codeText = $("#code").val();
				codeText=$.trim(codeText);//去掉前后空格
				if(codeText==null || codeText==""){
					$("span.errorMsg").text("验证码不能为空")
					return false;
				}

				//都合法 把错误信息清空
				//因为如果是一个访问很慢的网址 注册页面就会暂时留下错误信息。(可以修改form的action来验证)
				$("span.errorMsg").text("")
			});

		});
	</script>
<style type="text/css">
	.login_form{
		height:420px;
		margin-top: 25px;
	}
	
</style>
</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>
		
			<div class="login_banner">
			
				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>
				
				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册尚硅谷会员</h1>
								<span class="errorMsg">
									<!--回显错误信息-->
									<%=request.getAttribute("msg")==null?"":request.getAttribute("msg")%>
								</span>
							</div>
							<div class="form">
								<form action="registServlet" method="post">
									<label>用户名称：</label>
									<!--设置value属性，表单项回显信息-->
									<input class="itxt" type="text" placeholder="请输入用户名" autocomplete="off" tabindex="1" name="username" id="username"
										   value="<%=request.getAttribute("username")==null?"":request.getAttribute("username")%>"
									/>
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码" autocomplete="off" tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>电子邮件：</label>
									<!--设置value属性，表单项回显信息-->
									<input class="itxt" type="text" placeholder="请输入邮箱地址" autocomplete="off" tabindex="1" name="email" id="email"
										   value="<%=request.getAttribute("email")==null?"":request.getAttribute("email")%>"
									/>
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" type="text" style="width: 150px;" name="code" id="code"/>
									<img alt="" src="static/img/code.bmp" style="float: right; margin-right: 40px">
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" />
									
								</form>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>