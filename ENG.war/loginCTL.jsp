<%@page contentType="text/html;charset=utf-8"%>
<%@page import="com.eng.framework.crypto.*"%>
<%@page import="com.eng.framework.tray.Tray"%>
<%@page import="com.eng.framework.tray.RequestTrayFactory"%>
<%@page import="com.eng.framework.tray.DhitechRequestTrayFactory"%>
<%@page import="com.eng.login.cmd.LoginCmd"%>
<%@page import="java.util.Calendar"%>
<%
	// 이전페이지에서 POST/GET방식으로 전달한 모든 파라미터를 TRAY에 담는다. --> 기본사용
	request.setCharacterEncoding("utf-8");
	RequestTrayFactory requestFactory  = new DhitechRequestTrayFactory();
	Tray reqTray = requestFactory.getTray(request);
	
	LoginCmd command = new LoginCmd(reqTray, request, response);
	Tray rsTray = (Tray)request.getAttribute("result");
		
	if (("Y").equals(reqTray.getString("chk_cookie"))){
		Cookie cookie = new Cookie("ck_uid", reqTray.getString("user_id"));
		cookie.setMaxAge(7*24*3600); //day*hour*second > 7day
		cookie.setPath("/");
		response.addCookie(cookie);
	}else{
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for(int i=0; i<cookies.length; i++){
				if(("ck_uid").equals(cookies[i].getName())){
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
				}
			}
		}
	}
	
	if( rsTray.getRowCount() > 0 ){
		reqTray.setString("roll", rsTray.getString("roll"));
		reqTray.setString("username", rsTray.getString("username"));
		
		session.setAttribute("user_info", rsTray);
		session.setAttribute("username", rsTray.getString("username"));
		if(rsTray.getString("roll").equals("a")){
			response.sendRedirect("/adminBoard/");
		}else if(rsTray.getString("roll").equals("b")){
			response.sendRedirect("/managerBoard/");
		}
	}else{
		//사용자 정보 없음
%>
		<script type="text/javascript">
			alert("no user");
			location.href = "/";
		</script>
<%
	}
%>
