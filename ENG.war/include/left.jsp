<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="com.eng.framework.tray.Tray"%>
<%@page import="com.eng.framework.tray.RequestTrayFactory"%>
<%@page import="com.eng.framework.tray.DhitechRequestTrayFactory"%>
<%
	request.setCharacterEncoding("utf-8");
	Tray leftTray = (Tray)session.getAttribute("user_info");
	String path = request.getRequestURI().toString();
%>
<!-- LEFT MENU -->
<div class="col-md-3 left_col menu_fixed">
	<div class="left_col scroll-view">
		<div class="navbar nav_title" style="border: 0;">
			<a href="/Spending/" class="site_title"><i class="fa fa-calculator"></i>
				<span>동훈아이텍</span></a>
		</div>
		<div class="clearfix"></div>
		<br />
		<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
			<div class="menu_section">
				<h3>Menu</h3>
				<ul class="nav side-menu">
					<li><a href="/infoSearch/"><i class="fa fa-search"></i> 제출정보 조회 </a></li>
					<%if(("/infoSearch/detail/").equals(path)){ %>
					<li><a href="/infoSearch/detail/" class="disabled">상세 내역관리</a></li>
					<%} %>
					
					<%if (("jijeong").equals(leftTray.getString("userid")) || ("hblee").equals(leftTray.getString("userid")) || ("isjang").equals(leftTray.getString("userid")) || ("yjchoi").equals(leftTray.getString("userid")) || ("hjkim").equals(leftTray.getString("userid"))){ %>
					
					<li><a href="/infoConfirm/"><i class="fa fa-money"></i> 제출정보 확인</a></li>
					<%if(("/infoConfirm/detail/").equals(path)){ %>
					<li><a href="/infoConfirm/detail/" class="disabled">마감 관리</a></li>
					<%} %>
					
					<%} %>
				</ul>
			</div>
		</div>
	</div>
</div>