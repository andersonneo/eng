<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dhitech.framework.tray.Tray"%>
<%@ page import="com.dhitech.framework.tray.RequestTrayFactory"%>
<%@ page import="com.dhitech.framework.tray.DhitechRequestTrayFactory"%>    
<%@ page import="com.dhitech.framework.log.Log"%>
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