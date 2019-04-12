<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="com.eng.framework.crypto.*"%>
<%@page import="com.eng.framework.tray.Tray"%>
<%@page import="com.eng.framework.tray.RequestTrayFactory"%>
<%@page import="com.eng.framework.tray.DhitechRequestTrayFactory"%>
<%@page import="com.eng.spend.infosearch.cmd.infosearchCmd"%>
<%@include file="/include/aclCheck.jsp"%>
<%
	// 이전페이지에서 POST/GET방식으로 전달한 모든 파라미터를 TRAY에 담는다. --> 기본사용
	request.setCharacterEncoding("utf-8");
	RequestTrayFactory requestFactory  = new DhitechRequestTrayFactory();
	Tray reqTray = requestFactory.getTray(request);
	
	Tray userinfo = (Tray)session.getAttribute("user_info");
	
	reqTray.setString("userid", userinfo.getString("userid"));
	reqTray.setString("departmemt", userinfo.getString("department"));
	new infosearchCmd().iCTLCmd(reqTray, request, response);
	
	String result = (String)request.getAttribute("result");
	
	if(!(result==null||("").equals(result))&&result.equals("true")){
		out.print("<script type=\"text/javascript\"> alert('신규 지출결의서가 생성되었습니다.'); location.href='/infoSearch/'; </script>");
	}else{	
		out.print("<script type=\"text/javascript\"> alert('지출결의서 신규 생성에 실패했습니다. 다시 확인해주세요.'); location.href='/infoSearch/'; </script>");
	}
%>