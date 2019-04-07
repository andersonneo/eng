<%@page contentType="text/html;charset=utf-8"%>
<%@page import="com.dhitech.framework.crypto.*"%>
<%@page import="com.dhitech.framework.tray.Tray"%>
<%@page import="com.dhitech.framework.tray.RequestTrayFactory"%>
<%@page import="com.dhitech.framework.tray.DhitechRequestTrayFactory"%>
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
		reqTray.setString("user_name", rsTray.getString("user_name"));
		
		session.setAttribute("user_info", rsTray);
		if(rsTray.getString("roll").equals("a")){
			response.sendRedirect("/adminBoard/");
		}else if(rsTray.getString("roll").equals("b")){
			response.sendRedirect("/managerBoard/");
		}
	}else{
		//사용자 정보 없음
%>
		<script type="text/javascript">
			alert("사용자가 없거나 패스워드가 틀렸습니다. 확인 후 다시 로그인 하십시오");
			location.href = "/";
		</script>
<%
	}
%>
