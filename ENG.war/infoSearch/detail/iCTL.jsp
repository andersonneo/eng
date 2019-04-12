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
	
	if ((reqTray.getString("userid") == "") || (reqTray.getString("username") == "")){
		Tray userinfo = (Tray)session.getAttribute("user_info");
		
		reqTray.setString("userid", userinfo.getString("userid"));
		reqTray.setString("username", userinfo.getString("user_name"));
	}	
	new infosearchDetailCmd().iCTLCmd(reqTray, request, response);
	
	Tray rsTray = (Tray)request.getAttribute("result");
	
	if (rsTray != null){
		if(rsTray.getRowCount() != 0){
			out.print("{\"result\": \"success\", \"gidx\": \""+rsTray.getInt("gidx")+"\"}");
		}else{
			out.print("{\"result\": \"fail\", \"gidx\": 0}");
		}
	}else{
		out.print("{\"result\": \"fail\", \"gidx\": 0}");
	}
%>