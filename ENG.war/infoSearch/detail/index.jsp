<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/include/aclCheck.jsp"%>
<%@ page import="com.dhitech.framework.tray.Tray"%>
<%@ page import="com.dhitech.framework.tray.RequestTrayFactory"%>
<%@ page import="com.dhitech.framework.tray.DhitechRequestTrayFactory"%>
<%@ page import="com.dhitech.spend.infosearch.cmd.infosearchCmd"%>
<%@ page import="com.dhitech.spend.infosearchdetail.cmd.infosearchDetailCmd"%>
<%@ page import="com.dhitech.spend.accountsubject.cmd.accountSubjectCmd"%>
<%@ page import="com.dhitech.spend.getdept.cmd.GetDeptCmd"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%
//이전페이지에서 POST/GET방식으로 전달한 모든 파라미터를 TRAY에 담는다. --> 기본사용
	request.setCharacterEncoding("utf-8");
	RequestTrayFactory requestFactory  = new DhitechRequestTrayFactory();
	Tray reqTray = requestFactory.getTray(request);
	
	//로그인 사용자 정보 취득
	Tray userinfo = (Tray)session.getAttribute("user_info");
	reqTray.setString("userid", userinfo.getString("userid"));
	
	//혹시 존재할 관리자용 확인용 세션 제거
	session.removeAttribute("s_user_info");
	
	//요청 년/월
	String pYear = reqTray.getString("pYear");
	String pMonth = reqTray.getString("pMonth");
	String pDate = pYear + "-" + pMonth;
	
	//월별내역 리스트
	reqTray.setString("search_year", pYear);
	reqTray.setString("search_month", pMonth);
	new infosearchCmd().searchCmd(reqTray, request, response);
	Tray rsMonthListTray = (Tray)request.getAttribute("result");
	
	String mIdx = rsMonthListTray.getString("idx");
	String docStatus = rsMonthListTray.getString("finish_status");
	
	//부문 리스트 레코드
	new GetDeptCmd().searchCmd(null, request, response);
	Tray rsDeptTray = (Tray)request.getAttribute("result");

	//계정과목 리스트 레코드
	new accountSubjectCmd().searchCmd(null, request, response);
	Tray rsASListTray = (Tray)request.getAttribute("result");
	
	//지출항목 상세 레코드
	new infosearchDetailCmd().searchCmd(reqTray, request, response);
	Tray rsGTray = (Tray)request.getAttribute("result");
	
	//교통비 상세 레코드
	new infosearchDetailCmd().searchTCmd(reqTray, request, response);
	Tray rsTTray = (Tray)request.getAttribute("result");
	
	//사용 시작일, 종료일 생성
	String startUsingDate = "";
	String endUsingDate = "";
	String periodOfDate = "";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	if(rsGTray.getRowCount() != 0){
		for(int i = 0; i < rsGTray.getRowCount(); i ++){
			if (i == 0){
				startUsingDate = rsGTray.getString("usedate",i).substring(0,10);
				endUsingDate = rsGTray.getString("usedate",i).substring(0,10);
			}else{
				if (sdf.parse(startUsingDate).compareTo(sdf.parse(rsGTray.getString("usedate",i).substring(0,10))) > 0){
					startUsingDate = rsGTray.getString("usedate",i).substring(0,10);
				}
				if (sdf.parse(endUsingDate).compareTo(sdf.parse(rsGTray.getString("usedate",i).substring(0,10))) <= 0){
					endUsingDate = rsGTray.getString("usedate",i).substring(0,10);
				}
			}
		}
	}
	if(rsTTray.getRowCount() != 0){
		for(int i = 0; i < rsTTray.getRowCount(); i ++){
			if (startUsingDate == "" && endUsingDate==""){
				startUsingDate = rsTTray.getString("sdate",i).substring(0,10);
				endUsingDate = rsTTray.getString("sdate",i).substring(0,10);
			}else{
				if (sdf.parse(startUsingDate).compareTo(sdf.parse(rsTTray.getString("sdate",i).substring(0,10))) > 0){
					startUsingDate = rsTTray.getString("sdate",i).substring(0,10);
				}
				if (sdf.parse(endUsingDate).compareTo(sdf.parse(rsTTray.getString("sdate",i).substring(0,10))) <= 0){
					endUsingDate = rsTTray.getString("sdate",i).substring(0,10);
				}
			}
		}
	}
	if (startUsingDate!="" && endUsingDate!=""){
		periodOfDate = "("+startUsingDate+" ~ "+endUsingDate+")";
	}
	
	//전달 날짜 생성
	sdf = new SimpleDateFormat("yyyyMM");
	Date d = sdf.parse(pYear+pMonth);
	Calendar c = Calendar.getInstance();
	c.setTime(d);
	c.add(Calendar.MONTH, -1);
	String previousYear = String.valueOf(c.get(Calendar.YEAR));
	String previousMonth = String.valueOf(c.get(Calendar.MONTH)+1);
	if (previousMonth.length() == 1){
		previousMonth="0"+previousMonth;
	}
	String previousDate = previousYear + "-" + previousMonth;
	
	//결제수단
	String[] billArr = {"pcard","ccard","cash"};
	String[] billClassArr = {"personal_card","corpration_card","cash"};
	String[] billMethodArr = {"개인카드","법인카드","현금"};
	
	//교통수단
	String[] transportArr = {"car","bus","train","taxi"};
	String[] transportMethodArr = {"자차","버스","지하철","택시"};
	
	//계정과목
	/*String[] categoryArr = {"TV","BE","HO","CO","CM","TR","PR","EX","FE"};
	String[] categoryClassArr = {"travel","benefits","hosting","communication","car_maintenance","transportation","print","expandables","fees"};*/
	String[] categoryArr = {"TV","BE","HO","CO","CM","TR","PR","EX","FE","EP"};
	String[] categoryClassArr = {"travel","benefits","hosting","communication","car_maintenance","transportation","print","expandables","fees","education"};
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
							<h3><i class="fa fa-asterisk"></i> 제출정보 조회 <i class="fa fa-chevron-right"></i> 상세 내역관리</h3>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="x_panel">
								<div class="x_title">
									<h1 style="display:inline-block" id="info_detail-date" data-midx="<%=mIdx %>" data-doctype="<%=docStatus %>" data-cdate="<%=pDate %>" data-pdate="<%=previousDate %>"><%=pYear %>년 <%=pMonth %>월 내역 <small><%=periodOfDate %></small></h1>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<table id="tbl_infosearch-detail" class="table table-bordered">
										<tbody>
											<tr class="text-center">
												<td style="color:white;background:#a27272">복리후생비</td>
												<td style="color:white;background:#a27272">접대비</td>
												<td style="color:white;background:#a27272">여비교통비</td>
												<td style="color:white;background:#a27272">통신비</td>
												<td style="color:white;background:#a27272">소모품비</td>
												<td style="color:white;background:#425568;">개인카드</td>
												<td id="total-personal_card">-</td>
											</tr>
											<tr class="text-center">
												<td id="total-benefits">-</td>
												<td id="total-hosting">-</td>
												<td id="total-travel">-</td>
												<td id="total-communication">-</td>
												<td id="total-expandables">-</td>
												<td style="color:white;background:#425568;">법인카드</td>
												<td id="total-corpration_card">-</td>
											</tr>
											<tr class="text-center">
												<td style="color:white;background:#a27272">지급수수료</td>
												<td style="color:white;background:#a27272">도서인쇄비</td>
												<td style="color:white;background:#a27272">차량유지비</td>
												<td style="color:white;background:#a27272">운반비</td>
												<td style="color:white;background:#a27272">교육훈련비</td>
												<td style="color:white;background:#425568;">현금</td>
												<td id="total-cash">-</td>
											</tr>
											<tr class="text-center">
												<td id="total-fees">-</td>
												<td id="total-print">-</td>
												<td id="total-car_maintenance">-</td>
												<td id="total-transportation">-</td>
												<td id="total-education">-</td>
												<td style="color:white;background:#425568;">합계</td>
												<td id="total-all_price">-</td>
											</tr>
										</tbody>
									</table>
									<div class="clearfix"></div>
									<div class="row text-right">
									<% if(!("c").equals(docStatus)){  %>
										<button type="button" class="btn btn-lg btn-info" id="btn_copyPreData">전월 데이터 복사</button> <button type="button" class="btn btn-success btn-lg" id="btn_regist-spend-doc" data-rdate="<%=pDate %>">제출하기</button>
										<p style="padding:10px 5px 0 0;font-weight:bold">항목 추가, 삭제, 수정시 반드시 제출하기를 눌러 내역을 반영해 주세요.</p>
									<% }else{  %>
										<button type="button" class="btn btn-info btn-lg" id="btn_print-spend-doc" data-rdate="<%=pDate %>">인쇄하기</button>
										<p style="padding:10px 5px 0 0;font-weight:bold">마감된 지출결의서는 수정이 불가능합니다. (마감되었을 경우 경영지원팀 문의)</p>
									<% } %>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
						
						<div class="col-md-12 col-sm-12 col-xs-12 hide" id="panel-excel_upload">
							<div class="x_panel">
								<div class="x_title">
									<h3 style="display:inline-block">법인카드 내역 업로드 <small>엑셀 파일을 업로드합니다.</small></h3>
								</div>
								<div class="x_content">
									<p>사용방법 안내를 위한 도움말을 보시려면 우측 버튼을 클릭해주세요. <button type="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target=".modal-card-manual">도움말</button></p>
									<div class="modal fade modal-card-manual" tabindex="-1" role="dialog" aria-hidden="true">
										<div class="modal-dialog modal-lg">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span></button>
													<h4 class="modal-title" id="myModalLabel">법인카드 내역 업로드 도움말</h4>
												</div>
												<div class="modal-body">
													<h4>1. 법인카드 내역 다운로드</h4>
													<p>- <a href="https://mycompany.hyundaicard.com" target="_blank">https://mycompany.hyundaicard.com</a> 에 로그인 후 상단 메뉴 <b>My Account > 카드이용내역</b> 버튼 클릭</p>
													<p><img src="/build/images/manual/card_manual_01.jpg" style="width:100%"></p>
													<p>- 조회기간에서 <b>직접입력 클릭 후 해당 월의 1일부터 말일까지 선택</b> 후 조회 클릭</p>
													<p><img src="/build/images/manual/card_manual_02.jpg" style="width:100%"></p>
													<p>- 이용내역  우측 하단에 있는 <b>엑셀다운로드 버튼</b>을 클릭하여 파일 다운로드</p>
													<p><img src="/build/images/manual/card_manual_03.jpg" style="width:100%"></p>
													
													<h4>2. 엑셀파일 추가정보 입력</h4>
													<p>- 다운로드 받은 엑셀 파일을 열어 <b>Z, AA, AB 열</b>을 편집하며 다른 정보(가맹점업종코드, 가맹점업종명, 유종 등)가 입력되어 있을 경우 덮어 쓴다.</p>
													<p><img src="/build/images/manual/card_manual_04.jpg" style="width:100%"></p>
													<p>- 승인시간(Y열) 기준 우측으로 각각 <b>계정과목, 목적, 인원을 각 행에 모두</b> 입력</p>
													<p style="padding-left:10px">a. 계정과목 : 반드시 다음 중 
														<select>
															<option>클릭하여 입력 가능한 계정과목 확인</option>
															<%
															if(rsASListTray.getRowCount() != 0){
								                          		for(int i = 0; i < rsASListTray.getRowCount(); i ++){
								                          			out.print("<option>"+rsASListTray.getString("categoryname",i)+"</option>");
								                          		}
															}
															%>
														</select> <b>지출결의 양식에 맞는 것</b>을 입력									
													</p>
													<p style="padding-left:10px">b. 목적 : <b>지출 목적</b>을 간략히 작성</p>
													<p style="padding-left:10px">c. 인원 : 지출간 이용한 <b>인원 수를 숫자</b>로 작성</p>
													<p>- 다음은 올바르게 입력된 예시</p>
													<p><img src="/build/images/manual/card_manual_05.jpg" style="width:100%"></p>
													<p>- 저장하여 법인카드 내역을 업로드</p>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
												</div>
											</div>
										</div>
									</div>
									
									<p>아래 박스에 업로드 파일을 드래그 하거나 박스를 클릭하여 업로드 파일을 선택해주세요.</p>
				                    <form action="/infoSearch/detail/excelCTL.jsp" class="dropzone" id="frm_excelUpload">
				                    	<input type="hidden" name="attachDate" value="<%=pYear+pMonth%>">
				                    	<input type="hidden" name="attachMIdx" value="<%=mIdx%>">
				                    </form>
				                    <br>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
				
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="x_panel">
								<div class="x_title">
									<h3 style="display:inline-block">상세 내역 <small>지출 항목의 상세내역을 확인할 수 있습니다.</small></h3>
									<%if (!("c").equals(docStatus)){ %>
									<span class="grp-add-n-del_item" style="display:inline-block;float:right">
										<button type="button" class="btn btn-default" id="btn_regist-ccard-excel">법인카드 내역 엑셀 등록</button>
										<button type="button" class="btn btn-default" id="btn_add-ditem">항목추가</button>
										<button type="button" class="btn btn-default" id="btn_del-ditem" data-tab="G">항목삭제</button>
									</span>
									<%} %>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<div class="" role="tabpanel" data-example-id="togglable-tabs">
										<ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
											<li role="presentation" class="active"><a href="#tab_general" id="btn_general-tab" role="tab" data-toggle="tab" aria-expanded="true"> 지출 항목</a></li>
											<li role="presentation" class=""><a href="#tab_traffic" role="tab" id="btn_traffic-tab" data-toggle="tab" aria-expanded="false"> 교통비</a></li>
										</ul>
										<div class="tab-content">
											<div role="tabpanel" class="tab-pane fade active in" id="tab_general" aria-labelledby="btn_general-tab">
												<form id="gform" data-parsley-validate>
												<input type="hidden" id="gidx" value="">
												<table class="table table-bordered table-striped jambo_table tbl-input-general hide" style="margin-bottom:10px">
													<thead>
														<tr>
															<th colspan="4" class="Jleft">
																지출 내역 입력
																<span class="grp-input-add hide">(<span class="glyphicon glyphicon-star" aria-hidden="true"></span> 신규 입력)</span>
																<span class="grp-input-mod hide">(<span class="glyphicon glyphicon-star" aria-hidden="true"></span> 내용 수정)</span>
															</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td class=" " style="width:10%">부문</td>
															<td class="Jleft " colspan="3">
																<select id="gform-department" class="form-control" style="width:20%" required>
																	<option value="">선택</option>
																	<%
																	if (rsDeptTray.getRowCount() != 0){
																		for (int i=0; i < rsDeptTray.getRowCount(); i++){
																			String addText = "";
																			if ((userinfo.getString("team")).equals(rsDeptTray.getString("dept_nm_kr", i))){
																				addText = " selected";
																			}																			
																			out.print("<option value='"+rsDeptTray.getString("dept_nm_kr", i)+"'"+addText+">"+rsDeptTray.getString("dept_nm_kr", i)+"</option>");
																		}
																	}
																	%>
																</select>
															</td>
														</tr>
														<tr>
															<td class=" ">사용일</td>
															<td class="Jleft " style="width:20%">
																<input type="text" id="gform-date" class="form-control" placeholder="" style="width:80%;" required readonly>
															</td>
															<td class=" " style="width:10%">계정과목</td>
															<td class="Jleft ">
																<select class="form-control" id="gform-aslist" style="width:25%" required>
																	<option value="">선택</option>
																	<%
																	if(rsASListTray.getRowCount() != 0){
										                          		for(int i = 0; i < rsASListTray.getRowCount(); i ++){
										                          			out.print("<option value=\""+rsASListTray.getString("categorycode",i)+"\">"+rsASListTray.getString("categoryname",i)+"</option>");
										                          		}
																	}
																	%>
																</select>
															</td>
														</tr>
														<tr>
															<td class=" ">거래처</td>
															<td class="Jleft ">
																<input type="text" id="gform-store" class="form-control" placeholder="" style="width:80%;" required>
															</td>
															<td class=" ">결제수단</td>
															<td class="Jleft ">
																<select id="gform-billmethod" class="form-control" style="width:25%" required>
																	<option value="">선택</option>
																	<option value="ccard">법인카드</option>
																	<option value="pcard">개인카드</option>
																	<option value="cash">현금</option>
																</select>
															</td>
														</tr>
														<tr>
															<td class=" ">금액</td>
															<td class="Jleft ">
																<input type="text" id="gform-price" class="form-control" placeholder="" style="width:80%;" required data-parsley-type="digits">
															</td>
															<td class=" ">사용인원</td>
															<td class="Jleft ">
																<input type="text" id="gform-membercnt" class="form-control" placeholder="" style="width:25%;" required data-parsley-type="digits">
															</td>
														</tr>
														<tr>
															<td class=" ">사용목적</td>
															<td class="Jleft " colspan="3">
																<textarea id="gform-purpose" class="form-control" rows="3" placeholder="" required></textarea>
															</td>
														</tr>
													</tbody>
												</table>
												</form>
												<span class="hide grp-input-add" style="padding:15px">[CtrlKey + 입력 데이터 클릭] 시 내용이 복사됩니다.</span>
												<span class="hide grp-input-add" style="margin-bottom:20px;float:right;">
													<button type="button" class="btn btn-default" id="btn_add-gform">추가</button>
													<button type="button" class="btn btn-default btn_close-form">닫기</button>
												</span>
												<span class="hide grp-input-mod" style="margin-bottom:20px;float:right;">
													<button type="button" class="btn btn-default" id="btn_confirm-gform">확인</button>
													<button type="button" class="btn btn-default" id="btn_del-gform">삭제</button>
													<button type="button" class="btn btn-default btn_close-form">닫기</button>
												</span>
				
												<div class="clearfix"></div>
												<table class="table table-bordered table-striped jambo_table" id="tbl_spend-detail-list">
													<thead>
														<tr class="headings">
															<th><input type="checkbox" class="flat chk-all"></th>
															<th class="column-title">사용일</th>
															<th class="column-title">계정과목</th>
															<th class="column-title">거래처</th>
															<th style="width:40%" class="column-title">사용목적</th>
															<th class="column-title">인원</th>
															<th class="column-title">금액</th>
															<th class="column-title">결제수단</th>
															<th class="column-title last">사업부</th>
														</tr>
													</thead>
													<tbody>
														<%
							                        	if(rsGTray.getRowCount() != 0){
							                          		for(int i = 0; i < rsGTray.getRowCount(); i ++){
							                          			String trClass="";
							                          			String billMethodName = "";
				
							                          			for (int j=0;j<billArr.length;j++){
							                          				if ((billArr[j]).equals(rsGTray.getString("billmethod",i))){
								                          				trClass+=" piece-"+billClassArr[j];
								                          				billMethodName = billMethodArr[j];
								                          				break;
								                          			}
							                          			}
							                          			for (int j=0;j<categoryArr.length;j++){
							                          				if ((categoryArr[j]).equals(rsGTray.getString("categorycode",i))){
								                          				trClass+=" piece-"+categoryClassArr[j];
								                          				break;
								                          			}
							                          			}
							                          			out.print("<tr class=\"pointer"+trClass+"\" style=\"cursor:pointer;\">");
							                          			out.print("	<td class=\"a-center \"><input type=\"checkbox\" class=\"flat\" value=\""+rsGTray.getInt("didx",i)+"\"></td>");
							                          			out.print("	<td class=\" \">"+rsGTray.getString("usedate",i)+"</td>");
							                          			out.print("	<td class=\" \">"+rsGTray.getString("category",i)+"</td>");
							                          			out.print("	<td class=\" \">"+rsGTray.getString("storename",i)+"</td>");
							                          			out.print("	<td class=\" \">"+rsGTray.getString("purpose",i)+"</td>");
							                          			out.print("	<td class=\" \">"+rsGTray.getInt("membercnt",i)+"</td>");
							                          			out.print("	<td class=\" use-price\">"+String.format("%,d",rsGTray.getInt("price",i))+" ￦</td>");
							                          			out.print("	<td class=\" \">"+billMethodName+"</td>");
							                          			out.print("	<td class=\" \">"+rsGTray.getString("department",i)+"</td>");
							                          			out.print("</tr>");
							                          		}
							                        	}else{
							                        		out.print("<tr class=\"pointer text-center dataTables_empty\">");
							                        		out.print("<td colspan=\"9\">데이터가 없습니다</td>");
							                        		out.print("</tr>");
							                        	}
								                        %>
													</tbody>
												</table>
											</div>
											<div role="tabpanel" class="tab-pane fade" id="tab_traffic" aria-labelledby="btn_traffic-tab">
												<form id="tform">
												<input type="hidden" id="tidx" value="">
												<table class="table table-bordered table-striped jambo_table tbl-input-traffic hide" style="margin-bottom:10px">
													<thead>
														<tr>
															<th colspan="4" class="Jleft">
																교통비 입력
																<span class="grp-input-add hide">(<span class="glyphicon glyphicon-star" aria-hidden="true"></span> 신규 입력)</span>
																<span class="grp-input-mod hide">(<span class="glyphicon glyphicon-star" aria-hidden="true"></span> 내용 수정)</span>
															</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td class=" " style="width:10%">부문</td>
															<td class="Jleft " style="width:20%">
																<select id="tform-department" class="form-control" style="width:80%" required>
																	<option value="">선택</option>
																	<%
																	if (rsDeptTray.getRowCount() != 0){
																		for (int i=0; i < rsDeptTray.getRowCount(); i++){
																			String addText = "";
																			if ((userinfo.getString("team")).equals(rsDeptTray.getString("dept_nm_kr", i))){
																				addText = " selected";
																			}																			
																			out.print("<option value='"+rsDeptTray.getString("dept_nm_kr", i)+"'"+addText+">"+rsDeptTray.getString("dept_nm_kr", i)+"</option>");
																		}
																	}
																	%>
																</select>
															</td>
															<td class=" " style="width:10%">계정과목</td>
															<td class="Jleft ">
																<select class="form-control" id="tform-aslist" style="width:25%" required>
																	<option value="TV">여비교통비</option>
																</select>
															</td>
														</tr>
														<tr>
															<td class=" ">출발지</td>
															<td class="Jleft ">
																<input type="text" id="tform-spoint" class="form-control" placeholder="" style="width:80%;" required>
															</td>
															<td class=" ">목적지</td>
															<td class="Jleft ">
																<input type="text" id="tform-dpoint" class="form-control" placeholder="" style="width:25%;" required>
															</td>
														</tr>
														<tr>
															<td class=" ">이동시간</td>
															<td class="Jleft " colspan="3">
																<input type="text" id="tform-date" class="form-control" placeholder="" style="width:340px;" required readonly>
															</td>
														</tr>
														<tr>
															<td class=" ">금액</td>
															<td class="Jleft ">
																<input type="text" id="tform-price" class="form-control" placeholder="" style="width:80%;" required data-parsley-type="digits">
															</td>
															<td class=" " style="width:10%">이동수단</td>
															<td class="Jleft ">
																<select class="form-control" id="tform-transportation" style="width:25%" required>
																	<option value="">선택</option>
																	<option value="car">자차</option>
																	<option value="bus">버스</option>
																	<option value="train">지하철</option>
																	<option value="taxi">택시</option>
																</select>
															</td>
														</tr>
														<tr class="transportation-car hide">
															<td class=" ">이동거리</td>
															<td class="Jleft ">
																<input type="text" id="tform-distance" class="form-control" placeholder="Km" style="width:80%;" data-parsley-type="digits">
															</td>
															<td class=" ">통행료</td>
															<td class="Jleft ">
																<input type="text" id="tform-toll" class="form-control" placeholder="￦" style="width:25%;" data-parsley-type="digits">
															</td>
														</tr>
														<tr class="transportation-car hide">
															<td class=" ">단가</td>
															<td class="Jleft ">
																<input type="text" id="tform-unitprice" class="form-control" placeholder="/l" style="width:80%;" data-parsley-type="digits">
															</td>
															<td class=" ">금액 계산</td>
															<td class="Jleft ">
																<b>연비는 9.0Km/l 로 계산함</b> - <span id="val_km">0</span>(이동거리)/9 * <span id="val_unitprice">0</span>(단가) + <span id="val_toll">0</span>(통행료) = <span id="val_total">0</span> ￦  
															</td>
														</tr>
														<tr>
															<td class=" ">사용목적</td>
															<td class="Jleft " colspan="3">
																<textarea id="tform-purpose" class="form-control" rows="3" placeholder="" required></textarea>
															</td>
														</tr>
													</tbody>
												</table>
												</form>
												
												<span class="hide grp-input-add" style="padding:15px">[CtrlKey + 입력 데이터 클릭] 시 내용이 복사됩니다.</span>
												<span class="hide grp-input-add" style="margin-bottom:20px;float:right;">
													<button type="button" class="btn btn-default" id="btn_add-tform">추가</button>
													<button type="button" class="btn btn-default btn_close-form">닫기</button>
												</span>
												<span class="hide grp-input-mod" style="margin-bottom:20px;float:right;">
													<button type="button" class="btn btn-default" id="btn_confirm-tform">확인</button>
													<button type="button" class="btn btn-default" id="btn_del-tform">삭제</button>
													<button type="button" class="btn btn-default btn_close-form">닫기</button>
												</span>
												<div class="clearfix"></div>
				
												<table class="table table-bordered table-striped jambo_table" id="tbl_spend-traffic-list">
													<thead>
														<tr class="headings">
															<th><input type="checkbox" class="flat chk-all"></th>
															<th class="column-title">출발지</th>
															<th class="column-title">출발일시</th>
															<th class="column-title">목적지</th>
															<th class="column-title">도착일시</th>
															<th class="column-title">사용목적</th>
															<th class="column-title">수단</th>
															<th class="column-title">금액</th>
															<th class="column-title">사업부</th>
															<th class="column-title last">비고</th>
														</tr>
													</thead>
													<tbody>
														<%
							                        	if(rsTTray.getRowCount() != 0){
							                          		for(int i = 0; i < rsTTray.getRowCount(); i ++){
							                          			String trClass=" piece-personal_card piece-travel";
							                          			String transportMethodName = "";
							                          			String etcStr = "";
							                          			
							                          			for (int j=0;j<transportArr.length;j++){
							                          				if ((transportArr[j]).equals(rsTTray.getString("transportation",i))){
								                          				transportMethodName = transportMethodArr[j];
								                          				break;
								                          			}
							                          			}
							                          			if (("car").equals(rsTTray.getString("transportation",i))){
							                          				etcStr += "거리 : "+rsTTray.getString("distance",i)+" Km<br>";
							                          				etcStr += "단가 : "+rsTTray.getString("unit_price",i)+" /l<br>";
							                          				etcStr += "통행료 : "+rsTTray.getString("toll_pay",i)+" ￦";
							                          			}
							                          			out.print("<tr class=\"pointer"+trClass+"\" style=\"cursor:pointer;\">");
							                          			out.print("	<td class=\"a-center \"><input type=\"checkbox\" class=\"flat\" value=\""+rsTTray.getInt("didx",i)+"\"></td>");
							                          			out.print("	<td class=\" \">"+rsTTray.getString("spoint",i)+"</td>");
							                          			out.print("	<td class=\" \">"+rsTTray.getString("sdate",i)+"</td>");
							                          			out.print("	<td class=\" \">"+rsTTray.getString("dpoint",i)+"</td>");
							                          			out.print("	<td class=\" \">"+rsTTray.getString("edate",i)+"</td>");
							                          			out.print("	<td class=\" \">"+rsTTray.getString("purpose",i)+"</td>");
							                          			out.print("	<td class=\" \">"+transportMethodName+"</td>");
							                          			out.print("	<td class=\" use-price\">"+String.format("%,d",rsTTray.getInt("price",i))+" ￦</td>");
							                          			out.print("	<td class=\" \">"+rsTTray.getString("department",i)+"</td>");
							                          			out.print("	<td class=\" \" data-distance=\""+rsTTray.getInt("distance",i)+"\" data-tollpay=\""+rsTTray.getInt("toll_pay",i)+"\" data-unitprice=\""+rsTTray.getInt("unit_price",i)+"\">"+etcStr+"</td>");
							                          			out.print("</tr>");
							                          		}
							                        	}else{
							                        		out.print("<tr class=\"pointer text-center dataTables_empty\">");
							                        		out.print(" <td colspan=\"10\">데이터가 없습니다</td>");
							                        		out.print("</tr>");
							                        	}
								                        %>
													</tbody>
												</table>
											</div>
										</div>
									</div>
				
								</div>
							</div>
						</div>
					</div>
					<div style="position:fixed;bottom:60px;right:30px">
						<a href="#" onclick="location.href='/infoSearch/';" class="btn-backward btn-danger btn"><i class="fa fa-reply"></i> 뒤로</a>
					</div>
					<div class="clearfix" style="padding-top:60px"></div>
				</div>
				
			</div>
			<%@include file="/include/nav_bottom.jsp"%>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
	<script src="/build/js/infoSearch.jquery.js"></script>
</body>
</html>
