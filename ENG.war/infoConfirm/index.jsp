<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/include/aclCheck.jsp"%>
<%@ page import="com.dhitech.framework.tray.Tray"%>
<%@ page import="com.dhitech.framework.tray.RequestTrayFactory"%>
<%@ page import="com.dhitech.framework.tray.DhitechRequestTrayFactory"%>
<%@ page import="com.dhitech.spend.infosearch.cmd.infosearchCmd"%>
<%@ page import="com.dhitech.framework.util.FormatCalendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
<%
	//이전페이지에서 POST/GET방식으로 전달한 모든 파라미터를 TRAY에 담는다. --> 기본사용
	request.setCharacterEncoding("utf-8");
	RequestTrayFactory requestFactory  = new DhitechRequestTrayFactory();
	Tray reqTray = requestFactory.getTray(request);

	//현재 년도
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	//int month = cal.get(Calendar.MONTH)+1;
	cal.add(Calendar.MONTH,-1);
	int defaultYear = cal.get(Calendar.YEAR);
	int defaultMonth = cal.get(Calendar.MONTH)+1;
	
	//조회 년도 세팅
	if ("all".equals(reqTray.getString("search_year"))){
		reqTray.setString("search_year","");
	}else if ("".equals(reqTray.getString("search_year"))){
		reqTray.setString("search_year",String.valueOf(defaultYear));
	}
	//조회 월 세팅
	if ("all".equals(reqTray.getString("search_month"))){
		reqTray.setString("search_month","");
	}else if ("".equals(reqTray.getString("search_month"))){
		reqTray.setString("search_month", ("0"+defaultMonth).substring(("0"+defaultMonth).length()-2));
	}
	
	//리스트 출력
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
							<h3><i class="fa fa-asterisk"></i> 제출정보 확인</h3>
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
											<select id="confirm_year" name="search_year" class="form-control">
												<option value="all">전체</option>
												<% 
													for (int i = year+1; i > year-2; i--){
														String addText = "";
														if (reqTray.getString("search_year").equals(String.valueOf(i))) addText+="selected";
														out.print("<option value=\""+i+"\" "+addText+">"+i+"</option>");
													}
												%>
											</select>
										</span>
										&nbsp;월
										<span style="display:inline-block">
											<select id="confirm_month" name="search_month" class="form-control">
												<option value="all">전체</option>
												<% 
													for (int i = 1; i < 13; i++){
														String addText = "";
														if (reqTray.getString("search_month").equals(("0"+i).substring(("0"+i).length()-2))) addText+="selected";
														out.print("<option value=\""+ ("0"+i).substring(("0"+i).length()-2) +"\" "+addText+">"+("0"+i).substring(("0"+i).length()-2)+"</option>");
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
									<p class="text-muted font-13 m-b-30 text-right">
										<button type="button" class="btn btn-default" id="btn_dn-closing-excel">더존 마감자료 다운로드</button>
										<button type="button" class="btn btn-default" id="btn_closing-all-spend-doc">마감하기</button>
									</p>
									<table id="tbl_infoconfirm" class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th class="text-center"><input type="checkbox" id="chk_all-closing"></th>
												<th class="text-center">청구연월</th>
												<th class="text-center">조직</th>
												<th class="text-center">이름</th>
												<th class="text-center">법인카드</th>
												<th class="text-center">개인카드</th>
												<th class="text-center">현금</th>
												<th class="text-center">합계</th>
												<th class="text-center">제출일</th>
												<th class="text-center">처리상태</th>
											</tr>
										</thead>
										<tbody>
										<%
				                        	if(rsTray.getRowCount() != 0){
				                          		for(int i = 0; i < rsTray.getRowCount(); i ++){
				                          			String uDate = "";
				                          			String isDisabled = "";
				                          			
				                          			out.print("<tr style='cursor:pointer;' data-uid=\""+rsTray.getString("userid",i)+"\">");
				                          			out.print(" <td class='text-center'><input type=\"checkbox\" value=\""+rsTray.getString("idx",i)+"\""+isDisabled+"></td>");
				                          			out.print(" <td class='text-center'>"+rsTray.getString("year",i)+"-"+rsTray.getString("month",i)+"</td>");
				                					out.print(" <td class='text-center'>"+rsTray.getString("department",i)+"</td>");
				                					out.print(" <td class='text-center'>"+rsTray.getString("username",i)+"</td>");
				                					out.print(" <td class='text-right'>￦ "+String.format("%,d",rsTray.getInt("company_card",i))+"</td>");
				                					out.print(" <td class='text-right'>￦ "+String.format("%,d",rsTray.getInt("credit_card",i))+"</td>");
				                					out.print(" <td class='text-right'>￦ "+String.format("%,d",rsTray.getInt("cash",i))+"</td>");
				                					out.print(" <td class='text-right'>￦ "+String.format("%,d",rsTray.getInt("total",i))+"</td>");
				                					if (("").equals(rsTray.getString("update_date",i))||rsTray.getString("update_date",i)==null){
				                						uDate = "-";
				                					}else{
				                						uDate = rsTray.getString("update_date",i);
				                					}
				                					out.print(" <td class='text-center'>"+uDate+"</td>");
				                					if (("w").equals(rsTray.getString("finish_status",i))){
				                						out.print(" <td class='text-center'><button type='button' class='btn btn-danger btn-xs'>작성중</button></td>");
				                					}else if (("r").equals(rsTray.getString("finish_status",i))){
				                						out.print(" <td class='text-center'><button type='button' class='btn btn-info btn-xs'>제출완료</button></td>");
				                					}else if (("p").equals(rsTray.getString("finish_status",i))){
				                						out.print(" <td class='text-center'><button type='button' class='btn btn-warning btn-xs'>처리중</button></td>");
				                					}else if (("c").equals(rsTray.getString("finish_status",i))){
				                						out.print(" <td class='text-center'><button type='button' class='btn btn-success btn-xs'>마감완료</button></td>");
				                					}
				                					out.print("</tr>");
				                          		}
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
	<script src="/build/js/infoConfirm.jquery.js"></script>
</body>
</html>			