//파슬리 언어세팅
Parsley.setLocale('ko');

//DROPZONE 세팅
Dropzone.options.frmExcelUpload = {
	maxFilesize:20,
	paramName:"attach",
	init: function() {
		this.on("success", function(file, json) {
			if (json.status == "true"){
				alert("등록이 완료되었습니다.");
				location.reload();
			}else{
				new PNotify({
		            title: '엑셀파일 등록실패',
		            text: '잘못된 파일을 업로드했습니다. 다시 시도해주세요.',
		            type: 'error',
		            styling: 'bootstrap3'
		        });
			}
		});
	}
};

//달력 싱글 옵션세팅
var datepickerSingleOption = {
	"timePicker": true,
	"timePicker24Hour": true,
	"timePickerIncrement": 10,
	locale: {
		"format": "YYYY-MM-DD HH:mm:00",
		"applyLabel": "확인",
		"cancelLabel": "취소",
	    "daysOfWeek": ["일","월","화","수","목","금","토"],
	    "monthNames": ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
	    "firstDay": 0
    },
    "startDate": moment(),
    singleDatePicker: true,
    singleClasses: "picker_2"
};

//달력 듀얼 옵션세팅
var datepickerDualOption = {
	"timePicker": true,
	"timePicker24Hour": true,
	"timePickerIncrement": 10,
	"locale": {
		"format": "YYYY-MM-DD HH:mm:00",
	    "separator": " ~ ",
		"applyLabel": "확인",
		"cancelLabel": "취소",
		"daysOfWeek": ["일","월","화","수","목","금","토"],
		"monthNames": ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
		"firstDay": 0
	},
	"startDate": moment(),
	"endDate": moment(),
	singleClasses: "picker_2"	
}

//데이터 테이블 언어세팅
var dataTableLanguageSet = {
   "sEmptyTable":     "데이터가 없습니다",
   "sInfo":           "_START_ - _END_ / 전체 _TOTAL_",
   "sInfoEmpty":      "0 - 0 / 전체 0",
   "sInfoFiltered":   "(총 _MAX_ 개)",
   "sInfoPostFix":    "",
   "sInfoThousands":  ",",
   "sLengthMenu":     "페이지당 줄수 _MENU_ ",
   "sLoadingRecords": "읽는중...",
   "sProcessing":     "처리중...",
   "sSearch":         "검색 :",
   "sZeroRecords":    "검색 결과가 없습니다",
   "oPaginate": {
       "sFirst":    "처음",
       "sLast":     "마지막",
       "sNext":     "다음",
       "sPrevious": "이전"
   },
   "oAria": {
       "sSortAscending":  ": 오름차순 정렬",
       "sSortDescending": ": 내림차순 정렬"
   }
};

//임시폼 생성및 서밋 함수
function tmpFormSubmit(action, inputs){
	var $form = $("<form></form>");
	$form.attr("action", action);
	$form.attr("method", "POST");
	if (arguments.length==3){
		$form.attr("target", arguments[2]);
	}
	$form.appendTo("body");
	$form.append(inputs);
	$form.submit();
}

//금액 합계 계산 함수
function sumPriceInSearchDetail(str){
	var tmpSUM = 0;
	var pieceStr = ".piece-"+str;
	var totalStr = "#total-"+str;
	var $generalTbl = $("#tab_general > table > tbody");
	var $trafficTbl = $("#tab_traffic > table > tbody");
	$generalTbl.find(pieceStr).each(function(idx){
		tmpSUM+=numeral($(this).children(".use-price").text()).value();
	});
	$trafficTbl.find(pieceStr).each(function(idx){
		tmpSUM+=numeral($(this).children(".use-price").text()).value();
	});
	$(totalStr).text(numeral(tmpSUM).format('0,0 $'));
	console.log("	Proc >> ["+str+" - "+numeral(tmpSUM).format('0,0 $')+"] 원");
	return tmpSUM;
}

//전체 금액 내역 수집 함수
function setTotalPriceInSearchDetail(){
	console.log("Proc >> 비용 계산 진행 START");
	//SUM 복리후생비
	sumPriceInSearchDetail("benefits");
	//SUM 접대비
	sumPriceInSearchDetail("hosting");
	//SUM 통신비
	sumPriceInSearchDetail("communication");
	//SUM 소모품비
	sumPriceInSearchDetail("expandables");
	//SUM 지급수수료
	sumPriceInSearchDetail("fees");
	//SUM 도서인쇄비
	sumPriceInSearchDetail("print");
	//SUM 운반비
	sumPriceInSearchDetail("transportation");	
	//SUM 여비교통비
	sumPriceInSearchDetail("travel");
	//SUM 차량유지비
	sumPriceInSearchDetail("car_maintenance");
	//SUM 교육훈련비
	sumPriceInSearchDetail("education");
	
	//SUM 개인카드
	var pcard_price = sumPriceInSearchDetail("personal_card");
	//SUM 법인카드
	var ccard_price = sumPriceInSearchDetail("corpration_card");
	//SUM 현금
	var cash_price = sumPriceInSearchDetail("cash");

	//SUM 총계
	$("#total-all_price").text(numeral(pcard_price+ccard_price+cash_price).format('0,0 $'));
}

//인쇄하기 함수
function printSpendDocFunc(){
	var rdate = $(this).attr("data-rdate");
	var left = (screen.width / 2) - (950 / 2);
    return window.open("/excel/", "print", "scrollbars=1,menubar=no,resizable=no,width=950,height=700,left="+left);
}

//체크박스 INIT함수
function initCheckbox(){
	if ($("table").length==0) return;
	console.log("DHITECH - InitCheckbox");
	$(document).on("ifChecked",".chk-all",function(){
		$(this).closest("table").children("tbody").find("input[type=checkbox]").iCheck("check");
	}).on("ifUnchecked",".chk-all",function(){
		$(this).closest("table").children("tbody").find("input[type=checkbox]").iCheck("uncheck");
	});
}

//지출항목 상세ROW 의 데이터 얻는 함수
function getSpendDetailRow(idx,isCtrl){
	gformReset();
	var $standard = $("#tbl_spend-detail-list > tbody").find("input[type=checkbox][value="+idx+"]").parent("div").parent("td").parent("tr");
	if (!isCtrl) $("#gidx").val(idx);
	$("#gform-date").val($standard.children("td").eq(1).text());
	if ($standard.children("td").eq(2).text() != ""){
		$("#gform-aslist option:contains('"+$standard.children("td").eq(2).text()+"')").prop("selected",true);
	}else{
		$("#gform-aslist option:eq(0)").prop("selected",true);
	}		
	$("#gform-store").val($standard.children("td").eq(3).text());
	$("#gform-purpose").val($standard.children("td").eq(4).text());
	$("#gform-membercnt").val($standard.children("td").eq(5).text());
	$("#gform-price").val(numeral($standard.children("td").eq(6).text()).value());
	$("#gform-billmethod option:contains('"+$standard.children("td").eq(7).text()+"')").prop("selected",true);
	$("#gform-department option:contains('"+$standard.children("td").eq(8).text()+"')").prop("selected",true);
}

// 교통비 상세ROW 의 데이터 얻는 함수
function getSpendTrafficRow(idx,isCtrl){
	tformReset();
	var $standard = $("#tbl_spend-traffic-list > tbody").find("input[type=checkbox][value="+idx+"]").parent("div").parent("td").parent("tr");
	if (!isCtrl) $("#tidx").val(idx);
	$("#tform-department option:contains('"+$standard.children("td").eq(8).text()+"')").prop("selected",true);
	$("#tform-aslist").val("TV");
	$("#tform-spoint").val($standard.children("td").eq(1).text());
	$("#tform-dpoint").val($standard.children("td").eq(3).text());
	$("#tform-date").val($standard.children("td").eq(2).text()+" ~ "+$standard.children("td").eq(4).text());		
	$("#tform-price").val(numeral($standard.children("td").eq(7).text()).value());
	$("#val_total").text(numeral($standard.children("td").eq(7).text()).value());
	$("#tform-transportation option:contains('"+$standard.children("td").eq(6).text()+"')").prop("selected",true);
	if ($standard.children("td").eq(6).text() == "자차"){
		$(".transportation-car").removeClass("hide");
		$("#tform-distance").attr("required","required");
		$("#tform-toll").attr("required","required");
		$("#tform-unitprice").attr("required","required");
		$("#tform-price").attr("readonly","readonly");
	}
	$("#tform-purpose").val($standard.children("td").eq(5).text());
	$("#tform-distance").val($standard.children("td").eq(9).attr("data-distance"));
	$("#val_km").text($standard.children("td").eq(9).attr("data-distance"));
	$("#tform-toll").val($standard.children("td").eq(9).attr("data-tollpay"));
	$("#val_toll").text($standard.children("td").eq(9).attr("data-tollpay"));
	$("#tform-unitprice").val($standard.children("td").eq(9).attr("data-unitprice"));
	$("#val_unitprice").text($standard.children("td").eq(9).attr("data-unitprice"));
}

//자차 금액 계산 함수
function calcCarPrice(){
	var km = parseInt($("#val_km").text());
	var uprice = parseInt($("#val_unitprice").text())/9;
	var toll = parseInt($("#val_toll").text());
	var resultVal = Math.round(km*uprice)+toll;
	if (isNaN(resultVal)){
		resultVal = 0;
	}
	$("#val_total").text(resultVal);
	$("#tform-price").val(resultVal);
}

//지출항목 폼초기화 함수
function gformReset(){
	$("#gform")[0].reset();
	$("#gidx").val("");
	$("#gform").parsley().reset();
	$("#chk_all-general").iCheck("uncheck");
}

//교통비 폼초기화 함수
function tformReset(){
	$("#tform")[0].reset();
	$("#tidx").val("");
	$("#tform").parsley().reset();
	$("#tform-distance").removeAttr("required");
	$("#tform-toll").removeAttr("required");
	$("#tform-unitprice").removeAttr("required");
	$("#tform-price").removeAttr("readonly");
	$("#chk_all-traffic").iCheck("uncheck");
	$("#val_km, #val_unitprice, #val_toll, #val_total").text("0");
	$(".transportation-car").addClass("hide");
}

//제출시 
function registSpendDocFunc(){
	if ($("#btn_regist-spend-doc").length == 0){
		return;
	}
	var yyyy = $("#btn_regist-spend-doc").attr("data-rdate").split("-")[0];
	var mm = $("#btn_regist-spend-doc").attr("data-rdate").split("-")[1];
	$.ajax({      
        type:"POST",  
        url:"/infoSearch/uCTL.jsp",      
        data:{
        	pCard:numeral($("#total-personal_card").text()).value(),
        	cCard:numeral($("#total-corpration_card").text()).value(),
        	cash:numeral($("#total-cash").text()).value(),
        	pYear:yyyy,
        	pMonth:mm,
        	status:"p"
        },		        
        cache:false,
        dataType:"JSON",
        success:function(data){
			if (data.result == "success"){
				alert("제출이 완료되었습니다. 처리중인 데이터는 마감완료 전까지 데이터 수정이 가능합니다. 데이터 수정이 필요할 경우 데이터 수정 후에 다시 제출하기 버튼을 클릭해주세요.");
				location.href="/infoSearch/";
			}
        },
        error:function(e){
            console.log(e.responseText);  
        }
    });
}

function initDetailCommon(){
	console.log("DHITECH - initDetailCommon");
	//BTN - 항목추가
	$(document).on("click","#btn_add-ditem",function(){
		gformReset();
		tformReset();
		$(".grp-add-n-del_item").css("display","none");
		$(".tbl-input-general, .tbl-input-traffic, .grp-input-add").removeClass("hide");
	});
	
	//TAB BTN - 지출항목, 교통비
	$(document).on("click","#myTab > li", function(){
		if ($(this).children("a").attr("id") == "btn_general-tab"){
			$("#btn_del-ditem").attr("data-tab","G");
		}else{
			$("#btn_del-ditem").attr("data-tab","T");
		}
		$(".btn_close-form").click();
	});
	
	//SELECTBOX - 교통비 입력간 이동수단 선택시
	$(document).on("change","#tform-transportation",function(){
		if ($(this).val() == "car"){
			$(".transportation-car").removeClass("hide");
			$("#tform-distance").attr("required","required");
			$("#tform-toll").attr("required","required");
			$("#tform-unitprice").attr("required","required");
			$("#tform-price").attr("readonly","readonly");
		}else{
			if (!$(".transportation-car").hasClass("hide"))	$(".transportation-car").addClass("hide");
			$("#tform-distance").removeAttr("required");
			$("#tform-toll").removeAttr("required");
			$("#tform-unitprice").removeAttr("required");
			$("#tform-distance").val("");
			$("#tform-toll").val("");
			$("#tform-price").removeAttr("readonly");
		}
	});
	$(document).on("keyup focusout","#tform-distance",function(){
		var tmp = $(this).val();
		if (tmp == "") tmp = 0;
		$("#val_km").text(tmp);
		calcCarPrice();
	});
	$(document).on("keyup focusout","#tform-unitprice",function(){
		var tmp = $(this).val();
		if (tmp == "") tmp = 0;
		$("#val_unitprice").text(tmp);
		calcCarPrice();
	});
	$(document).on("keyup focusout","#tform-toll",function(){
		var tmp = $(this).val();
		if (tmp == "") tmp = 0;
		$("#val_toll").text(tmp);
		calcCarPrice();
	});
	
	//BTN - 내역확인 > 삭제
	$(document).on("click","#btn_del-gform, #btn_del-tform",function(){
		if ($(this).attr("id") == "btn_del-gform"){
			$("#tbl_spend-detail-list > tbody").find("input[type=checkbox]").iCheck("uncheck");
			$("#tbl_spend-detail-list > tbody").find("input[type=checkbox][value="+$("#gidx").val()+"]").iCheck("check");
		}else if ($(this).attr("id") == "btn_del-tform"){
			$("#tbl_spend-traffic-list > tbody").find("input[type=checkbox]").iCheck("uncheck");
			$("#tbl_spend-traffic-list > tbody").find("input[type=checkbox][value="+$("#tidx").val()+"]").iCheck("check");
		}
		$("#btn_del-ditem").click();
	});
	
	//BTN - 항목추가 > 닫기
	$(document).on("click",".btn_close-form",function(){
		$(".tbl-input-general, .tbl-input-traffic, .grp-input-add, .grp-input-mod").addClass("hide");
		$(".grp-add-n-del_item").css("display","inline-block");
	});
	
	//CLICK - 상세내역 ROW
	$(document).on("click","#tbl_spend-detail-list > tbody > tr, #tbl_spend-traffic-list > tbody > tr",function(e){
		if($(this).hasClass("dataTables_empty")) return;
		if ($("#info_detail-date").attr("data-doctype") == "c"){
			new PNotify({
	            title: '데이터수정 실패',
	            text: '마감된 문서는 데이터수정을 할 수 없습니다.',
	            type: 'error',
	            styling: 'bootstrap3'
	        });
			return;
		}
		var isCtrl = false;
		var idx = $(this).find("input[type=checkbox]").val();
		var confirmStr = "";
		var tblID = $(this).closest("table").attr("id");
		var isCallFunc = false;
		if (e.ctrlKey) isCtrl = true;
		
		if (isCtrl){
			$(".grp-input-add, .tbl-input-general, .tbl-input-traffic").removeClass("hide");
			$(".grp-add-n-del_item").css("display","none");
			if (!$(".grp-input-mod").hasClass("hide")) $(".grp-input-mod").addClass("hide");
			isCallFunc = true;
		}else{
			if(confirm("항목을 확인(또는 수정)하시겠습니까?")){
				$(".grp-input-mod, .tbl-input-general, .tbl-input-traffic").removeClass("hide");
				$(".grp-add-n-del_item").css("display","none");
				if (!$(".grp-input-add").hasClass("hide")) $(".grp-input-add").addClass("hide");
				isCallFunc = true;
			}
		}
		if (tblID == "tbl_spend-detail-list" && isCallFunc){
			getSpendDetailRow(idx,isCtrl);
		}else if (tblID == "tbl_spend-traffic-list" && isCallFunc){
			getSpendTrafficRow(idx,isCtrl);
		}
	});
}