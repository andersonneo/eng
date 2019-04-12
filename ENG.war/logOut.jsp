<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.eng.framework.tray.Tray"%>
<%@ page import="com.eng.framework.tray.RequestTrayFactory"%>
<%@ page import="com.eng.framework.tray.DhitechRequestTrayFactory"%>    
<%@ page import="com.eng.framework.log.Log"%>
<%//@ include file="/include/aclCheck.jsp" %>
<%
	//Default
	request.setCharacterEncoding("utf-8"); 
	RequestTrayFactory requestFactory  = new DhitechRequestTrayFactory();
	Tray reqTray = requestFactory.getTray(request);
	
	Log.info("LOGOUT - 세션초기화");
	session.setAttribute("user_info",null);
	session.invalidate();
	
	response.sendRedirect("/login.jsp");
%>