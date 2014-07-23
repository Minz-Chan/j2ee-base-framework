<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理页面</title>
<script type="text/javascript">
function selectAction(){
	var mainForm = document.getElementById("mainForm");
	var userAction = document.getElementsByName("userAction")[0].value;
	var action = "";
	
	
	if(userAction == "getCustomerList"){
		action = "../admin/showCustomerList.do";
	}
	if(userAction == "exceptionTest"){
		action = "../admin/exceptionTest.do";
	}
	if(userAction == "exceptionTest1"){
		action = "../admin/exceptionTest1.do";
	}
	
	mainForm.action = action;
	mainForm.submit();
}
</script>
</head>
<body>
<h3>Welcome, <span style="color: red">${userName }</span></h3>
<s:form id="mainForm" method="POST">
	<input type="hidden"" name="userName" value="${userName }" /> 
	<input type="hidden"" name="userAction" />
	<input type="button" value="获取客户列表" onclick="userAction.value='getCustomerList';selectAction();" />
	<input type="button" value="Service层异常测试" onclick="userAction.value='exceptionTest';selectAction();" />
	<input type="button" value="Action层异常测试" onclick="userAction.value='exceptionTest1';selectAction();" />
</s:form>
<br/>
<c:set var="list" value="${customerList}" />
<c:if test="${list != null}">

<table width="100%" cellspacing="0" cellpadding="0" border="1">
	<tr>
		<td align="center">客户名称</td>
		<td align="center">公司</td>

	</tr>
	<c:forEach items="${customerList}" begin="0" var="c">
		<tr>
			<td align="center"><c:out value="${c.customerName}" /></td>
			<td align="center"><c:out value="${c.company}" /></td>
		</tr>
	</c:forEach>
</table>
</c:if>
</body>
</html>