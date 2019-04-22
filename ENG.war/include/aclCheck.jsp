<%@page import="com.eng.framework.tray.Tray"%>
<%@page import="com.eng.framework.tray.RequestTrayFactory"%>
<%@page import="com.eng.framework.tray.DhitechRequestTrayFactory"%>    
<%
	request.setCharacterEncoding("utf-8");
	Tray sessionTray = (Tray)session.getAttribute("user_info");
	
	if(sessionTray == null || sessionTray.getRowCount() <= 0){
%>
	<script type="text/javascript">
		function SelfClose(){
			window.open('about:blank', '_self').close();
		}
		alert('SESSION TIME OVER.');
		location.href = '/login.jsp';
	</script>
<%
		return;
	}
%>