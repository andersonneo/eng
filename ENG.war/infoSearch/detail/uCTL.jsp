<%@page language="java" contentType="application/json;charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="com.eng.framework.crypto.*"%>
<%@page import="com.eng.framework.tray.Tray"%>
<%@page import="com.eng.framework.tray.RequestTrayFactory"%>
<%@page import="com.eng.framework.tray.DhitechRequestTrayFactory"%>
<%@page import="com.eng.spend.infosearchdetail.cmd.infosearchDetailCmd"%>
<%@include file="/include/aclCheck.jsp"%>
<%
	// 이전페이지에서 POST/GET방식으로 전달한 모든 파라미터를 TRAY에 담는다. --> 기본사용
	request.setCharacterEncoding("utf-8");
	RequestTrayFactory requestFactory  = new DhitechRequestTrayFactory();
	Tray reqTray = requestFactory.getTray(request);
		
	if (reqTray.getString("userid") == ""){
		Tray userinfo = (Tray)session.getAttribute("user_info");
		reqTray.setString("userid", userinfo.getString("userid"));
	}
	new infosearchDetailCmd().uCTLCmd(reqTray, request, response);
	
	String result = (String)request.getAttribute("result");
	
	if(!(result==null||("").equals(result))&&result.equals("true")){
		out.print("{\"result\": \"success\"}");
	}else{	
		out.print("{\"result\": \"fail\"}");
	}
%>