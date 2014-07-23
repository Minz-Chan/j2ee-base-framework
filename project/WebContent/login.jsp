<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>Login</title>
    <script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.7.min.js"></script>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
  </head>
  <body>
  <p>&nbsp;</p>
<p>&nbsp;</p>
<center>
<p class="title"><B><font color="navy">Logining.....</font></B></p>
	<s:form action="/security/login.do" name="form1" id="login" >
	<table><tr><td>
	User：<input type="text" name="userName" id="userName"></td></tr><tr><td>
	Password：<input type="password" name="password" id="password"></td></tr><tr><td>
	<input type="button" onclick="loginValidate(this.form)" value="Login">
	</td></tr></table>
	</s:form>
</center>
  </body>
  <script type="text/javascript">
  function loginValidate(f){
	  if($("#userName").val()==""){
		  alert("Username can not be null！");
		  return;
	  }
	  if($("#password").val()==""){
		  alert("Password can not be null！");
		  return;
	  }
	  if($("#password").val().length>20){
		  alert("Password length should be between 1 and 20！");
		  return;
	  }
	  f.submit();
  }
</script>
</html>
