<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.eng.framework.tray.Tray"%>
<%@ page import="com.eng.framework.tray.MultipartRequestTray"%>
<%@ page import="com.eng.framework.tray.DhitechMultipartRequestTrayFactory"%>
<%@page import="com.eng.adminBoard.cmd.AdminBoardCmd"%>

<%

request.setCharacterEncoding("utf-8"); 
//파일업로드용
DhitechMultipartRequestTrayFactory dhrequestFactory  = new DhitechMultipartRequestTrayFactory(); 
Tray reqTray = dhrequestFactory.getTray(request);
	




reqTray.setString("gubun",request.getParameter("gubun"));
reqTray.setString("idx",request.getParameter("idx")); 

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
		alert("success");
		opener.parent.location='index.jsp';
		window.close();
	</script>
<%}else{%>
	<script type="text/javascript">
		alert("fail");
		location.href = "./register.jsp";
		window.close();
	</script>
<%}%>

</body>
</html>

