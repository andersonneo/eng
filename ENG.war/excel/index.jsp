<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="com.eng.framework.tray.Tray"%>
<%@ page import="com.eng.framework.tray.RequestTrayFactory"%>
<%@ page import="com.eng.framework.tray.DhitechRequestTrayFactory"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date" %>
<%@ include file="/include/aclCheck.jsp"%>
<%
	Tray userinfo = (Tray)session.getAttribute("user_info");
	Date date = new Date();
	String toDay= new SimpleDateFormat("yyyy-MM-dd").format(date);
	
	if ((Tray)session.getAttribute("s_user_info")!=null){
		userinfo = (Tray)session.getAttribute("s_user_info");
	}
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>DHITech - 지출결의서</title>
    <link href="../build/css/dhitech.css" rel="stylesheet">
  </head>
<body>
	<div class="notice-print">
		<p>지출결의서 인쇄 <a href="#" id="btn_print-spend">[Click]</a> <span style="float:right;display:none;" id="anchor-view"><a href="#btn_print-traffic">시내교통비 이용 내역 보기</a></span></p>
	</div>
	<div class="container" id="doc_spend-excel">
		<div class="doc-tit">
			<h1>지 출 결 의 서</h1>
		</div>
		<div class="doc-sign">
			<table>
				<thead>
					<tr>
						<th class="col-10p-sm">담당</th>
						<th class="col-10p-sm">검토</th>
						<th class="col-10p-sm">검토</th>
						<th class="col-10p-sm">심사</th>
						<th class="col-10p-sm">결정</th>
						<th class="table-remove-tb-border"></th>
						<th class="col-25p-sm">사업부명</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="col-10p-md"></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td class="table-remove-tb-border"></td>
						<td class="txt-center"><%=userinfo.getString("department") %></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="doc-department">
			<table>
				<thead>
					<tr>
						<th class="col-11p-sm bg-cell">부서</th>
						<th class="col-22p-sm txt-center"><%=userinfo.getString("team") %></th>
						<th class="col-11p-sm bg-cell">직급</th>
						<th class="col-22p-sm txt-center"><%=userinfo.getString("user_grade") %></th>
						<th class="col-11p-sm bg-cell">성명</th>
						<th class="col-22p-sm txt-center"><%=userinfo.getString("user_name") %></th>
					</tr>
					<tr>
						<th class="col-11p-sm table-remove-top-border bg-cell">사용기간</th>
						<th class="table-remove-top-border pop_date" colspan="3"></th>
						<th class="table-remove-top-border bg-cell">작성일</th>
						<th class="table-remove-top-border"><%=toDay %></th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="doc-total_price">
			<table>
				<thead>
					<tr>
						<th class="col-14p-sm bg-cell">복리후생비</th>
						<th class="col-14p-sm bg-cell">접대비</th>
						<th class="col-14p-sm bg-cell">여비교통비</th>
						<th class="col-14p-sm bg-cell">통신비</th>
						<th class="col-14p-sm bg-cell table-add-right-border">소모품비</th>
						<th class="col-14p-sm bg-cell">결제수단</th>
						<th class="col-14p-sm bg-cell">금액</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="col-14p-sm txt-right"></td>
						<td class="txt-right"></td>
						<td class="txt-right"></td>
						<td class="txt-right"></td>
						<td class="table-add-right-border txt-right"></td>
						<td class="txt-center">개인카드</td>
						<td class="txt-right"></td>
					</tr>
				</tbody>
				<thead>
					<tr>
						<th class="col-14p-sm bg-cell">지급수수료</th>
						<th class="col-14p-sm bg-cell">도서인쇄비</th>
						<th class="col-14p-sm bg-cell">차량유지비</th>
						<th class="col-14p-sm bg-cell">운반비</th>
						<th class="col-14p-sm bg-cell table-add-right-border">교육훈련비</th>
						<td class="col-14p-sm txt-center">법인카드</td>
						<td class="col-14p-sm txt-right"></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="col-14p-sm txt-right"></td>
						<td class="txt-right"></td>
						<td class="txt-right"></td>
						<td class="txt-right"></td>
						<td class="table-add-right-border txt-right"></td>
						<td class="txt-center">현금</td>
						<td class="txt-right"></td>
					</tr>
				</tbody>
				<thead>
					<tr>
						<th colspan="5" class="col-14p-sm table-add-right-border"></th>
						<td class="txt-center">총 계</td>
						<td class="txt-right"></td>
					</tr>
				</thead>
			</table>
		</div>
		
		<div class="doc-unit_price">
			<table>
				<thead>
					<tr>
						<th class="bg-cell col--sm" colspan="7">지 출 내 역</th>
					</tr>
					<tr>
						<th class="bg-cell col--sm">사용일</th>
						<th class="bg-cell">계정과목</th>
						<th class="bg-cell">거래처</th>
						<th class="bg-cell">사용목적</th>
						<th class="bg-cell">인원</th>
						<th class="bg-cell">금액</th>
						<th class="bg-cell">결제수단</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	
	<div class="notice-print">
		<p>시내교통비 이용 내역 인쇄 <a href="#" id="btn_print-traffic">[Click]</a></p>
	</div>
	
	<div class="container" id="doc_traffic-excel">
		<div class="doc-tit">
			<h1>시내교통비 이용 내역</h1>
		</div>
		<div class="doc-sign">
			<table>
				<thead>
					<tr>
						<th class="table-remove-tl-border" colspan="7"></th>
						<th class="col-7p-sm">담당</th>
						<th class="col-7p-sm">검토</th>
						<th class="col-7p-sm">검토</th>
						<th class="col-7p-sm">심사</th>
						<th class="col-7p-sm table-add-right-border">결정</th>
					</tr>
					<tr>
						<th class="col-7p-sm bg-cell">부서</th>
						<td class="txt-center"><%=userinfo.getString("team") %></td>
						<th class="col-7p-sm bg-cell">직급</th>
						<td class="txt-center"><%=userinfo.getString("user_grade") %></td>
						<th class="col-7p-sm bg-cell">성명</th>
						<td class="txt-center"><%=userinfo.getString("user_name") %></td>
						<td class="col-2p-sm table-remove-top-border" rowspan="2"></td>
						<td class="table-add-bottom-border" rowspan="2"></td>
						<td class="table-add-bottom-border" rowspan="2"></td>
						<td class="table-add-bottom-border" rowspan="2"></td>
						<td class="table-add-bottom-border" rowspan="2"></td>
						<td class="table-add-bottom-border table-add-right-border" rowspan="2"></td>
					</tr>
					<tr>
						<th class="col-7p-sm table-add-bottom-border bg-cell">사용기간</th>
						<td class="table-add-bottom-border txt-center pop_date col-35p-sm" colspan="3"></td>
						<th class="table-add-bottom-border bg-cell">작성일</th>
						<td class="table-add-bottom-border txt-center"><%=toDay %></td>
					</tr>
				</thead>
			</table>
		</div>
		<div class="doc-readme">
			<p>아래와 같이 업무관련 교통비를 청구하고자 하오니, 검토 후 재가하여 주시기 바랍니다.</p>
		</div>
		<div class="doc-unit_price">
			<table>
				<thead>
					<tr>
						<th class="bg-cell col--sm table-add-right-border" colspan="3">출발</th>
						<th class="bg-cell table-add-right-border" colspan="3">도착</th>
						<th class="bg-cell table-add-right-border table-add-bottom-border" rowspan="2">업무내용</th>
						<th class="bg-cell table-add-bottom-border table-add-right-border" colspan="9">이동수단별 금액</th>
					</tr>
					<tr>
						<th class="bg-cell col--md">출발지</th>
						<th class="bg-cell">일자</th>
						<th class="bg-cell table-add-right-border">시간</th>
						<th class="bg-cell">목적지</th>
						<th class="bg-cell">일자</th>
						<th class="bg-cell table-add-right-border">시간</th>
						<th class="bg-cell">버스</th>
						<th class="bg-cell">지하철</th>
						<th class="bg-cell table-add-right-border">택시</th>
						<th class="bg-cell">이용거리<br>(Km)</th>
						<th class="bg-cell">지정연비<br>(Km/l)</th>
						<th class="bg-cell">단가<br>(l)</th>
						<th class="bg-cell table-add-right-border">자차 계</th>
						<th class="bg-cell table-add-right-border">통행료</th>
						<th class="bg-cell table-add-right-border">합계</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	<script src="../vendors/jquery/dist/jquery.min.js"></script>
	<script src="../vendors/jquery-print/jQuery.print.js"></script>
	<script src="../vendors/numeral/numeral.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			var isTraffic = false;
			var calcTotTraffic = 0;
			var tempTrafficDate = "";
			
			function getWeekName(strDate){
				var d = new Date(strDate);
				var days = ["(일)", "(월)", "(화)", "(수)", "(목)", "(금)", "(토)"];
				return strDate + days[d.getDay()];
			}
			function date2YYYY_MM_DD(date) {
				var d = new Date(date),
				month = '' + (d.getMonth() + 1),
				day = '' + d.getDate(),
				year = d.getFullYear();
				if (month.length < 2) month = '0' + month;
				if (day.length < 2) day = '0' + day;
				return [year, month, day].join('-');
			}
			
			function getSpendlist(){
				var limitRow = 30;
				var $opener_detaillist = $("#tbl_spend-detail-list tbody tr", opener.document);
				var tmpTR = "";
				var remainRow = limitRow - $opener_detaillist.length;
				if ($opener_detaillist.length > 0 && !$opener_detaillist.eq(0).hasClass("dataTables_empty")){
					$opener_detaillist.each(function(){
						tmpTR+="<tr class=\"bot-dot\">";
						tmpTR+="	<td class=\"col--sm txt-center\">"+getWeekName($(this).children("td").eq(1).text().substring(0,10))+"</td>";
						tmpTR+="	<td class=\"txt-center\">"+$(this).children("td").eq(2).text()+"</td>";
						tmpTR+="	<td class=\"txt-center\">"+$(this).children("td").eq(3).text()+"</td>";
						tmpTR+="	<td>"+$(this).children("td").eq(4).text()+"</td>";
						tmpTR+="	<td class=\"txt-center\">"+$(this).children("td").eq(5).text()+"</td>";
						tmpTR+="	<td class=\"txt-right\">"+$(this).children("td").eq(6).text()+"</td>";
						tmpTR+="	<td class=\"txt-center\">"+$(this).children("td").eq(7).text()+"</td>";
						tmpTR+="</tr>";
						$("#doc_spend-excel > .doc-unit_price > table > tbody").prepend(tmpTR);
						tmpTR = "";
					});
				}
				tmpTR = "<tr class=\"bot-dot\">";
				tmpTR+="	<td class=\"col--sm\"></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td></td>";
				tmpTR+="</tr>";
				/* padding */
				for (var i=0;i<remainRow;i++){
					$("#doc_spend-excel > .doc-unit_price > table > tbody").append(tmpTR);
				}
				/* padding end */
			}
			function getTrafficlist(){
				var limitRow = 30;
				var $opener_detaillist = $("#tbl_spend-traffic-list tbody tr", opener.document);
				var tmpTR = "";
				var remainRow = limitRow - $opener_detaillist.length;
				var ttl_billBus = 0;
				var ttl_billTrain = 0;
				var ttl_billTaxi = 0;
				var ttl_billCar = 0;
				var ttl_billToll = 0;
				var ttlBill = 0;
				
				if ($opener_detaillist.length > 0 && !$opener_detaillist.eq(0).hasClass("dataTables_empty")){
					$opener_detaillist.each(function(){
						isTraffic = true;
						var bill_car = "", bill_bus = "", bill_train = "", bill_taxi = "";
						var trafficMethod = $(this).children("td").eq(6).text();
						var trafficBill = $(this).children("td").eq(7).text().replace(" ￦","").replace(/,/,"");
						var carDistance = "", carUnitPrice = "", carTollpay = "", carEff = "";

						if (trafficMethod == "버스"){
							bill_bus = trafficBill;
							ttl_billBus+=parseInt(bill_bus);
						}else if (trafficMethod == "지하철"){
							bill_train = trafficBill;
							ttl_billTrain+=parseInt(bill_train);
						}else if (trafficMethod == "택시"){
							bill_taxi = trafficBill;
							ttl_billTaxi+=parseInt(bill_taxi);
						}else{
							carDistance = $(this).children("td").eq(9).attr("data-distance");
							carUnitPrice = $(this).children("td").eq(9).attr("data-unitprice");
							carTollpay = $(this).children("td").eq(9).attr("data-tollpay");
							carEff = "9";
							bill_car = (parseInt(trafficBill)-parseInt(carTollpay))+"";
							ttl_billCar+=parseInt(bill_car);
							ttl_billToll+=parseInt(carTollpay);
						}
						tmpTR+="<tr>";
						tmpTR+="	<td class=\"col--sm txt-center\">"+$(this).children("td").eq(1).text()+"</td>";
						tmpTR+="	<td class=\"txt-center\">"+$(this).children("td").eq(2).text().split(" ")[0]+"</td>";
						tmpTR+="	<td class=\"table-add-right-border txt-center\">"+$(this).children("td").eq(2).text().split(" ")[1].substring(0,5)+"</td>";
						tmpTR+="	<td class=\"txt-center\">"+$(this).children("td").eq(3).text()+"</td>";
						tmpTR+="	<td class=\"txt-center\">"+$(this).children("td").eq(4).text().split(" ")[0]+"</td>";
						tmpTR+="	<td class=\"table-add-right-border txt-center\">"+$(this).children("td").eq(4).text().split(" ")[1].substring(0,5)+"</td>";
						tmpTR+="	<td class=\"table-add-right-border txt-center\">"+$(this).children("td").eq(5).text()+"</td>";
						tmpTR+="	<td class=\"txt-right\">"+numeral(bill_bus).format('0,0')+"</td>";
						tmpTR+="	<td class=\"txt-right\">"+numeral(bill_train).format('0,0')+"</td>";
						tmpTR+="	<td class=\"table-add-right-border txt-right\">"+numeral(bill_taxi).format('0,0')+"</td>";
						tmpTR+="	<td class=\"txt-right\">"+numeral(carDistance).format('0,0.0')+"</td>";
						tmpTR+="	<td class=\"txt-right\">"+numeral(carEff).format('0,0.0')+"</td>";
						tmpTR+="	<td class=\"txt-right\">"+numeral(carUnitPrice).format('0,0')+"</td>";
						tmpTR+="	<td class=\"table-add-right-border txt-right\">"+numeral(bill_car).format('0,0')+"</td>";
						tmpTR+="	<td class=\"table-add-right-border txt-right\">"+numeral(carTollpay).format('0,0')+"</td>";
						tmpTR+="	<td class=\"table-add-right-border txt-right\">"+numeral(trafficBill).format('0,0')+"</td>";
						tmpTR+="</tr>";
						$("#doc_traffic-excel > .doc-unit_price > table > tbody").prepend(tmpTR);
						tmpTR = "";
						tempTrafficDate = $(this).children("td").eq(2).text().split(" ")[0];
					});
				}
				/* padding */
				tmpTR = "<tr>";
				tmpTR+="	<td class=\"col--sm\"></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td class=\"table-add-right-border\"></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td class=\"table-add-right-border\"></td>";
				tmpTR+="	<td class=\"table-add-right-border\"></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td class=\"table-add-right-border\"></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td></td>";
				tmpTR+="	<td class=\"table-add-right-border\"></td>";
				tmpTR+="	<td class=\"table-add-right-border\"></td>";
				tmpTR+="	<td class=\"table-add-right-border\"></td>";
				tmpTR+="</tr>";
				for (var i=0;i<remainRow;i++){
					$("#doc_traffic-excel > .doc-unit_price > table > tbody").append(tmpTR);
				};
				/* total */
				ttlBill = ttl_billBus + ttl_billTrain + ttl_billTaxi + ttl_billCar + ttl_billToll;
				tmpTR = "<tr>";
				tmpTR+="	<td class=\"col--sm table-add-right-border txt-center bg-cell\" colspan=\"6\">합계</td>";
				tmpTR+="	<td class=\"table-add-right-border bg-cell\"></td>";
				tmpTR+="	<td class=\"txt-right bg-cell\">"+numeral(ttl_billBus).format('0,0')+"</td>";
				tmpTR+="	<td class=\"txt-right bg-cell\">"+numeral(ttl_billTrain).format('0,0')+"</td>";
				tmpTR+="	<td class=\"table-add-right-border txt-right bg-cell\">"+numeral(ttl_billTaxi).format('0,0')+"</td>";
				tmpTR+="	<td class=\"bg-cell\"></td>";
				tmpTR+="	<td class=\"bg-cell\"></td>";
				tmpTR+="	<td class=\"bg-cell\"></td>";
				tmpTR+="	<td class=\"table-add-right-border txt-right bg-cell\">"+numeral(ttl_billCar).format('0,0')+"</td>";
				tmpTR+="	<td class=\"table-add-right-border txt-right bg-cell\">"+numeral(ttl_billToll).format('0,0')+"</td>";
				tmpTR+="	<td class=\"table-add-right-border txt-right bg-cell\">"+numeral(ttlBill).format('0,0')+"</td>";
				tmpTR+="</tr>";
				$("#doc_traffic-excel > .doc-unit_price > table > tbody").append(tmpTR);
				calcTotTraffic = ttlBill;
			}
			function setTraffic2Spendlist(){
				var tmpTR = "";
				var useDate = date2YYYY_MM_DD(new Date(tempTrafficDate.split("-")[0],tempTrafficDate.split("-")[1],0)) 
				tmpTR+="<tr class=\"bot-dot\">";
				tmpTR+="	<td class=\"col--sm txt-center\">"+getWeekName(useDate)+"</td>";
				tmpTR+="	<td class=\"txt-center\">여비교통비</td>";
				tmpTR+="	<td class=\"txt-center\">대중교통</td>";
				tmpTR+="	<td>"+tempTrafficDate.split("-")[1]+"월 개인 교통비 (시내교통비 이용 내역 확인)</td>";
				tmpTR+="	<td class=\"txt-center\">1</td>";
				tmpTR+="	<td class=\"txt-right\">"+numeral(calcTotTraffic).format('0,0')+" ￦</td>";
				tmpTR+="	<td class=\"txt-center\">개인카드</td>";
				tmpTR+="</tr>";
				$("#doc_spend-excel > .doc-unit_price > table > tbody").prepend(tmpTR);
			}
			function getOpenerDATA(){
				if ($("#tbl_spend-detail-list tbody tr", opener.document).eq(0).hasClass("dataTables_empty") && $("#tbl_spend-traffic-list tbody tr", opener.document).eq(0).hasClass("dataTables_empty")){
					alert("지출항목이 존재하지 않습니다.");
					window.close();
				}				
				//부서정보
				var periodDate = $("#info_detail-date > small", opener.document).text().substring(1,24);
				if (periodDate.indexOf(", ")>0){
					periodDate = $("#info_detail-date > small", opener.document).text().substring(6,29);
				}
				var sdate = periodDate.split(" ~ ")[0];
				var edate = periodDate.split(" ~ ")[1];
				var timeDiff = Math.abs((new Date(sdate)).getTime() - (new Date(edate)).getTime());
				var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24))+1; 
				var periodText = sdate.substring(0,4)+"년 "+sdate.substring(5,7)+"월 "+sdate.substring(8,10)+"일 부터 ";
					periodText+= edate.substring(0,4)+"년 "+edate.substring(5,7)+"월 "+edate.substring(8,10)+"일 까지 "+"("+diffDays+"일)";
				$(".pop_date").text(periodText);
				
				//총 금액
				var $opener_priceinfo = $("#tbl_infosearch-detail tbody tr", opener.document);
				var $priceinfo = $(".doc-total_price table");
				//복리후생~>교육훈련
				for (var i=0; i<5;i++){
					$priceinfo.children("tbody").eq(0).find("td").eq(i).text($opener_priceinfo.eq(1).children("td").eq(i).text());
					$priceinfo.children("tbody").eq(1).find("td").eq(i).text($opener_priceinfo.eq(3).children("td").eq(i).text());
				}
				//개인카드 합계
				$priceinfo.children("tbody").eq(0).find("td").eq(6).text($opener_priceinfo.eq(0).children("td").eq(6).text());
				//법인카드 합계
				$priceinfo.children("thead").eq(1).find("td").eq(1).text($opener_priceinfo.eq(1).children("td").eq(6).text());
				//현금 합계
				$priceinfo.children("tbody").eq(1).find("td").eq(6).text($opener_priceinfo.eq(2).children("td").eq(6).text());
				//총 계
				$priceinfo.children("thead").eq(2).find("td").eq(1).text($opener_priceinfo.eq(3).children("td").eq(6).text());
				
				//건별 내역
				getTrafficlist(); // $("#doc_traffic-excel").css("display","block");
				if (isTraffic){
					alert("시내교통비 이용 내역이 존재합니다.\n지출결의서 인쇄후 아래 시내교통비 이용 내역도 인쇄해주세요.");
					$("#anchor-view").css("display","block");
					setTraffic2Spendlist();
				}
				getSpendlist(); // $("#doc_spend-excel").css("display","block");
			}
			getOpenerDATA();
			$(document).on("click","#btn_print-spend",function(){
				$("#doc_spend-excel").print();
			});
			$(document).on("click","#btn_print-traffic",function(){
				$("#doc_traffic-excel").print();
			});
			opener.location.href="javascript:registSpendDocFunc();";
		});
	</script>
</body>
</html>