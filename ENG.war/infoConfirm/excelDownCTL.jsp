<%@page language="java" contentType="application/vnd.ms-excel;charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="com.dhitech.framework.file.DownLoad"%>
<%@page import="java.io.File"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.net.URLDecoder"%>
<%@page import="com.dhitech.framework.tray.Tray"%>
<%@page import="com.dhitech.framework.tray.RequestTrayFactory"%>
<%@page import="com.dhitech.framework.tray.DhitechRequestTrayFactory"%>
<%@page import="com.dhitech.spend.infosearchdetail.module.InfoSearchExcelExportModule"%>
<%@include file="/include/aclCheck.jsp"%>
<%
	request.setCharacterEncoding("utf-8");
	RequestTrayFactory requestFactory  = new DhitechRequestTrayFactory();
	Tray reqTray = requestFactory.getTray(request);
		
	String fileName = null;
	
	InfoSearchExcelExportModule module = new InfoSearchExcelExportModule();
	fileName = module.exportExcel(reqTray);

	out.clear();
	out = pageContext.pushBody();
	File file = new File(URLDecoder.decode(fileName,"UTF-8"));
	DownLoad.download(request, response, file);
%>