<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="com.eng.framework.tray.Tray"%>
<%@ page import="com.eng.framework.tray.RequestTrayFactory"%>
<%@ page import="com.eng.framework.tray.DhitechRequestTrayFactory"%>
<%@ page import="com.eng.framework.config.*"%>
<%
	//이전페이지에서 POST/GET방식으로 전달한 모든 파라미터를 TRAY에 담는다. --> 기본사용
	request.setCharacterEncoding("utf-8"); 
	RequestTrayFactory requestFactory  = new DhitechRequestTrayFactory();
	Tray reqTray = requestFactory.getTray(request);
	
	System.out.println("세션초기화");
	session.setAttribute("user_info",null);
	session.invalidate();
	
	String userid_cookie = "";
	
	Cookie[] cookies = request.getCookies();
	if(cookies!=null){
		for(int i=0; i<cookies.length; i++){
			if(("ck_uid").equals(cookies[i].getName())){
				userid_cookie=cookies[i].getValue();
			}
		}
	}
%>
<%@include file="/include/header.jsp"%>
	<body class="login">
	<div>
		<a class="hiddenanchor" id="signup"></a> <a class="hiddenanchor"
			id="signin"></a>
		<div class="login_wrapper">
			<div class="animate form login_form">
				<section class="login_content">
					<form name="frm_login" action="loginCTL.jsp" method="POST">
						<h1>We'll English Academy</h1>
						<div>
							<input type="text" id="user_id" name="user_id" class="form-control" placeholder="ID" required="" value="<%=userid_cookie %>"/>
						</div>
						<div>
							<input type="password" id="user_pw" name="user_pw" class="form-control" placeholder="PASSWORD" required="" />
						</div>
						<div>
							<div class="checkbox" style="float:left;display:inline-block">
								<label>
									<input type="checkbox" id="chk_cookie" name="chk_cookie" <% if (("").equals(userid_cookie)){ out.print("value=\"N\"");}else{out.print("value=\"Y\" checked=\"checked\"");} %>  class="flat"> save
								</label>
							</div>
							<a class="btn btn-default submit" href="#" style="float:right">Log in</a>
						</div>
						<div class="clearfix"></div>
						<div class="separator">
							<div class="clearfix"></div>
							<br />
							<div>
								<h2>
									<i class="fa fa-calculator"></i> well-english-academy.com
								</h2>
								<p>©2019 All Rights Reserved. well-english-academy.com</p>
							</div>
						</div>
					</form>
				</section>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
	<script type="text/javascript">
		$(document).ready(function(){
			$(document).on("click",".submit",function(){
				if ($("#chk_cookie").is(":checked")){
					$("#chk_cookie").val("Y");
				}else{
					$("#chk_cookie").val("N");
				}
				document.frm_login.submit();
			});
			$(document).on("keyup","#user_id",function(e){
				if (e.keyCode == "13") $("#user_pw").focus();
			});
			$(document).on("keyup","#user_pw",function(e){
				if (e.keyCode == "13") $(".submit").click();
			});
			if ($("#user_id").val()==""){
				$("#user_id").focus();
			}else{
				$("#user_pw").focus();
			}
		});		
	</script>
	</body>
</html>
