<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="com.dhitech.framework.tray.Tray"%>
<%@page import="com.dhitech.framework.tray.RequestTrayFactory"%>
<%@page import="com.dhitech.framework.tray.DhitechRequestTrayFactory"%>
<%
	request.setCharacterEncoding("utf-8");
	Tray topTray = (Tray)session.getAttribute("user_info");
%>
<!-- top navigation -->
<div class="top_nav">
	<div class="nav_menu">
		<nav>
			<div class="nav toggle">
				<a id="menu_toggle"><i class="fa fa-bars"></i></a>
			</div>

			<ul class="nav navbar-nav navbar-right">
				<li class=""><a href="javascript:;"
					class="user-profile dropdown-toggle" data-toggle="dropdown"
					aria-expanded="false">안녕하세요. <span id="login_username"><%=topTray.getString("user_name") %> <%=topTray.getString("user_grade") %></span>님 <span class=" fa fa-angle-down"></span>
				</a>
					<ul class="dropdown-menu dropdown-usermenu pull-right">
						<!--  
						<li><a href="javascript:;"> Profile</a></li>
						<li><a href="javascript:;"> <span
								class="badge bg-red pull-right">50%</span> <span>Settings</span>
						</a></li>
						<li><a href="javascript:;">Help</a></li>
						-->
						<li><a href="/logOut.jsp"><i
								class="fa fa-sign-out pull-right"></i> Log Out</a></li>
					</ul></li>
			</ul>
		</nav>
	</div>
</div>
<!-- /top navigation -->