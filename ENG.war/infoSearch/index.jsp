<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/include/aclCheck.jsp"%>
<%@ page import="com.dhitech.framework.tray.Tray"%>
<%@ page import="com.dhitech.framework.tray.RequestTrayFactory"%>
<%@ page import="com.dhitech.framework.tray.DhitechRequestTrayFactory"%>
<%@ page import="com.dhitech.spend.infosearch.cmd.infosearchCmd"%>
<%@ page import="com.dhitech.framework.util.FormatCalendar"%>
<%@ page import="java.util.Calendar"%>
<%
	//이전페이지에서 POST/GET방식으로 전달한 모든 파라미터를 TRAY에 담는다. --> 기본사용
	request.setCharacterEncoding("utf-8");
	RequestTrayFactory requestFactory  = new DhitechRequestTrayFactory();
	Tray reqTray = requestFactory.getTray(request);
	
	//현재 연도
	int year = Calendar.getInstance().get(Calendar.YEAR);
	
	//조회 년도 세팅
	if ("all".equals(reqTray.getString("search_year"))){
		reqTray.setString("search_year","");
	}else if ("".equals(reqTray.getString("search_year"))){
		reqTray.setString("search_year",String.valueOf(year));
	}
	
	Tray userinfo = (Tray)session.getAttribute("user_info");
	reqTray.setString("userid", userinfo.getString("userid"));

	//월별 리스트 출력 tbl - dh_spend_month
	new infosearchCmd().searchCmd(reqTray, request, response);
	Tray rsTray = (Tray)request.getAttribute("result");	
%>
<%@include file="/include/header.jsp"%>
<body class="nav-md footer_fixed">
	<div class="container body">
		<div class="main_container">
			<%@include file="/include/left.jsp"%>
			<%@include file="/include/nav_top.jsp"%>
			<div class="right_col" role="main" id="cont_frame">

				<div class="">
					<div class="page-title">
						<div class="title_left">
							<h3><i class="fa fa-asterisk"></i> 제출정보 조회</h3>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="x_panel">
								<div class="x_title div-nowYear">
									<form action="./" method="POST">
									<h2>
										조회 연도 
										<span style="display:inline-block">
											<select id="search_year" name="search_year" class="form-control">
												<option value="all">전체</option>
												<% 
													for (int i = year+1; i > year-5; i--){
														String addText = "";
														if (reqTray.getString("search_year").equals(String.valueOf(i))) addText+="selected";
														out.print("<option value=\""+i+"\" "+addText+">"+i+"</option>");
													}
												%>
											</select>
										</span>
										<small>조회할 연도를 선택해주세요.</small>
									</h2>
									</form>
									<div class="clearfix"></div>
								</div>
								<div class="x_content" id="mInfo-table">
									<p class="text-muted font-13 m-b-30 text-right"><button id="btn_regist-mInfo" type="button" class="btn btn-default">신규 등록</button></p>
									<table id="tbl_infosearch" class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th class="text-center">대상월</th>
												<th class="text-center">법인카드</th>
												<th class="text-center">개인카드</th>
												<th class="text-center">현금</th>
												<th class="text-center">합계</th>
												<th class="text-center">작성일</th>
												<th class="text-center">수정일</th>
												<th class="text-center">작성자</th>
												<th class="text-center">구분</th>
											</tr>
										</thead>
										<tbody>
										<%
				                        	if(rsTray.getRowCount() != 0){
				                          		for(int i = 0; i < rsTray.getRowCount(); i ++){
				                          			String uDate = "";
				                          			out.print("<tr style='cursor:pointer;'>");
				                					out.print(" <td class='text-center'>"+rsTray.getString("year",i)+"-"+rsTray.getString("month",i)+"</td>");
				                					out.print(" <td class='text-right'>￦ "+String.format("%,d",rsTray.getInt("company_card",i))+"</td>");
				                					out.print(" <td class='text-right'>￦ "+String.format("%,d",rsTray.getInt("credit_card",i))+"</td>");
				                					out.print(" <td class='text-right'>￦ "+String.format("%,d",rsTray.getInt("cash",i))+"</td>");
				                					out.print(" <td class='text-right'>￦ "+String.format("%,d",rsTray.getInt("total",i))+"</td>");
				                					out.print(" <td class='text-center'>"+rsTray.getString("create_date",i)+" </td>");
				                					if (("").equals(rsTray.getString("update_date",i))||rsTray.getString("update_date",i)==null){
				                						uDate = "-";
				                					}else{
				                						uDate = rsTray.getString("update_date",i);
				                					}
				                					out.print(" <td class='text-center'>"+uDate+" </td>");
				                					out.print(" <td class='text-center'>"+rsTray.getString("username",i)+"</td>");
				                					if (("w").equals(rsTray.getString("finish_status",i))){
				                						out.print(" <td class='text-center'><button type='button' class='btn btn-danger btn-xs'>작성중</button></td>");
				                					}else if (("p").equals(rsTray.getString("finish_status",i))){
				                						out.print(" <td class='text-center'><button type='button' class='btn btn-warning btn-xs'>처리중</button></td>");
				                					}else if (("c").equals(rsTray.getString("finish_status",i))){
				                						out.print(" <td class='text-center'><button type='button' class='btn btn-success btn-xs'>마감완료</button></td>");
				                					}
				                					out.print("</tr>");
				                          		}
				                        	}else{
				                        		out.print("<tr class='nodata text-center'><td colspan='8'>작성된 데이터가 없습니다.</td></tr>");
				                        	}
				                        %>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>

			</div>
			<%@include file="/include/nav_bottom.jsp"%>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
	<script src="/build/js/infoSearch.jquery.js"></script>
</body>
</html>