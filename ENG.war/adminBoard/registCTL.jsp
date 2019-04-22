<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.eng.framework.tray.Tray"%>
<%@page import="com.eng.framework.tray.RequestTrayFactory"%>
<%@page import="com.eng.framework.tray.DhitechRequestTrayFactory"%>
<%@page import="com.eng.adminBoard.cmd.AdminBoardCmd"%>

<%

request.setCharacterEncoding("utf-8"); 
RequestTrayFactory requestFactory  = new DhitechRequestTrayFactory();
Tray reqTray = requestFactory.getTray(request);
	
	
new AdminBoardCmd().iCTLCmd(reqTray, request, response);
String result = (String)request.getAttribute("result");	

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Pragma" content="No-Cache">
  <meta http-equiv="Cache-Control" content="No-Cache">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
	</script>
</head>
<body>


<%if(!(result==null||result.equals(""))&&result.equals("true")){%>
	<script type="text/javascript">
		alert("정상");
		location.href = "./register.jsp";
	</script>
<%}else{%>
	<script type="text/javascript">
		alert("문제가 발생했습니다.");
		location.href = "./register.jsp";
	</script>
<%}%>

</body>
</html>

