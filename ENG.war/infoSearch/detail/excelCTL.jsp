<%@page language="java" contentType="application/json;charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="com.dhitech.framework.crypto.*"%>
<%@page import="com.dhitech.framework.tray.Tray"%>
<%@page import="com.dhitech.framework.tray.RequestTrayFactory"%>
<%@page import="com.dhitech.framework.tray.DhitechRequestTrayFactory"%>
<%@page import="com.dhitech.framework.tray.DhitechMultipartRequestTrayFactory"%>
<%@page import="com.dhitech.spend.infosearchdetail.module.InfoSearchExcelInputModule"%>
<%@include file="/include/aclCheck.jsp"%>
<%
	request.setCharacterEncoding("utf-8"); 
	DhitechMultipartRequestTrayFactory dhrequestFactory  = new DhitechMultipartRequestTrayFactory();
	Tray reqTray = dhrequestFactory.getTray(request);

	Tray userinfo = (Tray)session.getAttribute("user_info");
	reqTray.setString("userid",userinfo.getString("userid"));
	reqTray.setString("username",userinfo.getString("user_name"));
	reqTray.setString("department",userinfo.getString("department"));
		
	InfoSearchExcelInputModule excelInputModule = new InfoSearchExcelInputModule();
	boolean isExecute = excelInputModule.execute(reqTray);

	out.print("{\"status\":\""+(""+isExecute)+"\"}");
%>