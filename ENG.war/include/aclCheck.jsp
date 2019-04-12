<%@page import="com.eng.framework.tray.Tray"%>
<%@page import="com.eng.framework.tray.RequestTrayFactory"%>
<%@page import="com.eng.framework.tray.DhitechRequestTrayFactory"%>    
<%
	request.setCharacterEncoding("utf-8");
	Tray sessionTray = (Tray)session.getAttribute("user_info");
	/*
		reqTray.getString("dept_id") : 부서 아이디 > 54
		reqTray.getString("dept_cd") : 부서 코드 > BB20
		reqTray.getString("department") : 부문명
		reqTray.getString("team") : 팀명
		reqTray.getString("userid") : ID
		reqTray.getString("user_pw") : PW
		reqTray.getString("user_name") : 이름
		reqTray.getString("user_grade") : 직급
		reqTray.getString("user_duty") : 팀내 역할
	*/
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