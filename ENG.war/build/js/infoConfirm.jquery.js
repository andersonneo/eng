$(document).ready(function(){
	// infoConfirm/
	// INIT() >>
	if ($("#tbl_infoconfirm").length > 0){
		$("#tbl_infoconfirm").DataTable({
			dom: 'T<"clear">lfrtip',
			columnDefs:[{targets: 0, orderable: false},{type:'currency', targets: [4,5,6,7]}],
			order: [[ 8, 'desc']],
			language: dataTableLanguageSet
		});
	}	
	// INIT() <<
	
	// SELECTBOX - 조회년도
	$(document).on("change","#confirm_year",function(){
		$("#confirm_month").val("all");
		$("form").submit();
	});
	
	// SELECTBOX - 조회월
	$(document).on("change","#confirm_month",function(){
		if ($("#confirm_year").val() == "all"){
			alert("조회 연도를 먼저 선택해주세요.");
			$("#confirm_month").val("all");
			$("#confirm_year").focus();
			return;
		}
		$("form").submit();
	});
	
	// BTN - 더존 마감자료 다운로드
	$(document).on("click","#btn_dn-closing-excel",function(e){
		var yyyy = $("#confirm_year").val();
		var mm = $("#confirm_month").val();
		var uids = "";
		var isChecked = false;
		var isClosing = true;
		$("#tbl_infoconfirm tbody tr").each(function(){
			if ($(this).find("input:checkbox").is(":checked")){
				if (uids == ""){
					uids += "'"+$(this).attr("data-uid")+"'";
				}else{
					uids += ","+"'"+$(this).attr("data-uid")+"'";
				}
				isChecked = true;
				if ($(this).find("button").text() != "마감완료") isClosing = false;
			}
		});
		if (yyyy == "all" || mm == "all"){
			alert("다운로드할 마감자료의 년도/월을 선택해주세요.");
			return;
		}
		if (!isChecked){
			alert("다운로드할 마감자료를 선택하지 않았습니다.");
			return;
		}
		if (!isClosing){
			alert("마감완료된 자료만 다운로드 할 수 있습니다.");
			return;
		}
		if (confirm(yyyy+"년 "+mm+"월 마감자료를 다운로드하시겠습니까?")){
			$.fileDownload("/infoConfirm/excelDownCTL.jsp",{data:{year:yyyy, month:mm, uids:uids}, successCallback:function(url){}})
				.done(function(){});
		}
		e.preventDefault();
	});
	
	// BTN - 마감하기
	$(document).on("click","#btn_closing-all-spend-doc",function(){
		var closingIdx = "";
		var $selectedTbody = $("#tbl_infoconfirm > tbody > tr");
		var chkLen = $selectedTbody.find("input[type=checkbox]:checked").length;
		if (chkLen == 0){
			alert("마감할 데이터를 선택해주세요.");
			return;
		}
		$selectedTbody.find("input[type=checkbox]:checked").each(function(idx){
			closingIdx+=$(this).val();
			if (idx < chkLen-1){
				closingIdx+=",";
			}
		});
		if (confirm("선택한 항목을 마감 처리하시겠습니까?")){
			$.ajax({
		        type:"POST",  
		        url:"/infoConfirm/uCTL.jsp",
		        data:{
		        	closingIdx : closingIdx
		        },		        
		        cache:false,
		        dataType:"JSON",
		        success:function(data){
					if (data.result == "success"){
						alert("선택하신 데이터가 마감 완료되었습니다.")
						$("form").submit();
					}
		        },
		        error:function(e){
		            console.log(e.responseText);  
		        }
		    });
		}
	});
	
	// CHECKBOX - 테이블 전체 체크
	$(document).on("click","#chk_all-closing",function(){
		if ($(this).is(":checked")){
			$("#tbl_infoconfirm > tbody > tr").each(function(){
				if(!$(this).find("input:checkbox").attr("disabled")) $(this).find("input:checkbox").prop("checked",true);
			});
		}else{
			$("#tbl_infoconfirm > tbody > tr").each(function(){
				$(this).find("input:checkbox").prop("checked",false);
			});
		}
	});
	
	// CLICK - 제출정보 ROW
	$(document).on("click","table > tbody > tr > td > input:checkbox",function(e){
		e.stopPropagation();
	});
	$(document).on("click","#tbl_infoconfirm > tbody > tr",function(){
		if ($(this).children("td").hasClass("dataTables_empty")){
			return;
		}
		var yyyyMM = $(this).children("td").eq(1).text();
		var writer = $(this).children("td").eq(3).text();
		if (confirm("["+writer+"]님이 입력한 ["+yyyyMM+"]일자 데이터를 확인 하시겠습니까?")){
			var action = "/infoConfirm/detail/";
			var inputs = "<input type='hidden' name='pYear' value='"+yyyyMM.split("-")[0]+"'>" +
						"<input type='hidden' name='pMonth' value='"+yyyyMM.split("-")[1]+"'>" +
						"<input type='hidden' name='selectedId' value='"+$(this).attr("data-uid")+"'>" +
						"<input type='hidden' name='selectedName' value='"+writer+"'>";
			tmpFormSubmit(action, inputs);
		}
	});
	
	// infoConfirm/detail/
	// INIT() >>
	if ($("#info_detail-date").length>0) setTotalPriceInSearchDetail();
	if ($("#tbl_spend-detail-list").length>0){
		initCheckbox();
		initDetailCommon();
		$("#gform-date").daterangepicker(datepickerSingleOption);
		$("#tform-date").daterangepicker(datepickerDualOption);
		$("#gform").parsley();
		$("#tform").parsley();
	}
	// INIT() <<
	
	//BTN - 제출하기, 인쇄하기
	$(document).on("click","#btn_print-spend-doc",function(){
		printSpendDocFunc();
	});
	
	//BTN - 마감하기, 반려하기, 마감취소하기
	$(document).on("click","#btn_closing-spend-doc, #btn_cancel-closing-spend-doc, #btn_refusal-spend-doc",function(){
		var yyyy = $(this).attr("data-rdate").split("-")[0];
		var mm = $(this).attr("data-rdate").split("-")[1];
		var uid = $(this).attr("data-userid");
		var stat = "", confirmStr = "";
		if ($(this).attr("id")=="btn_refusal-spend-doc"){
			confirmStr = "지출결의서를 반려하여 작성자에게 되돌리겠습니까?";
			stat = "w";
		}else if ($(this).attr("id")=="btn_closing-spend-doc"){
			confirmStr = "지출결의서를 마감 처리하시겠습니까?";
			stat = "c";
		}else if ($(this).attr("id")=="btn_cancel-closing-spend-doc"){
			confirmStr = "지출결의서를 마감취소 처리하시겠습니까?";
			stat = "p";
		}
		
		if (confirm(confirmStr)){
			$.ajax({
		        type:"POST",
		        url:"/infoSearch/uCTL.jsp",      
		        data:{
		        	pCard:numeral($("#total-personal_card").text()).value(),
		        	cCard:numeral($("#total-corpration_card").text()).value(),
		        	cash:numeral($("#total-cash").text()).value(),
		        	pYear:yyyy,
		        	pMonth:mm,
		        	userid:uid,
		        	status:stat
		        },
		        cache:false,
		        dataType:"JSON",
		        success:function(data){
					if (data.result == "success"){
						alert("처리되었습니다.");
						if (stat=="w"){
							location.href = "/infoConfirm/";
						}else{
							location.reload();
						}
					}
		        },
		        error:function(e){
		        	console.log(e.responseText);
		        }
		    });
		}
	});
	
	//BTN - 항목추가
	$(document).on("click","#btn_add-ditem",function(){
		// util.js > initDetailCommon();
	});
		
	//BTN - 항목삭제
	$(document).on("click","#btn_del-ditem",function(){
		var userid = $("#btn_closing-spend-doc").attr("data-userid");
		var delIdx = "";
		var status = $(this).attr("data-tab");
		var chkLen = 0;
		var $selectedTbody = null;

		if (status == "G"){
			$selectedTbody = $("#tbl_spend-detail-list > tbody");
		}else{
			$selectedTbody = $("#tbl_spend-traffic-list > tbody");
		}
		chkLen = $selectedTbody.find("input[type=checkbox]:checked").length;
		
		if (chkLen == 0){
			alert("선택된 항목이 없습니다.");
			return;
		}
		$selectedTbody.find("input[type=checkbox]:checked").each(function(idx){
			delIdx+=$(this).val();
			if (idx < chkLen-1){
				delIdx+=",";
			}
		});		
		if (confirm("선택한 항목을 삭제하시겠습니까?")){
			$.ajax({      
		        type:"POST",  
		        url:"/infoSearch/detail/dCTL.jsp",
		        data:{
		        	delItem : delIdx,
		        	status : status,
		        	userid : userid
		        },
		        cache:false,
		        dataType:"JSON",
		        success:function(data){
		        	if (data.result == "success"){
						alert("선택하신 항목이 삭제 되었습니다.");
						location.reload();
					}else{
						alert("삭제에 실패했습니다. 다시 시도해주세요.");
						location.reload();
					}
		        },
		        error:function(e){
		            console.log(e.responseText);
		        }
		    });
		}
	});
	
	//TAB BTN - 지출항목, 교통비
	$(document).on("click","#myTab > li", function(){
		// util.js > initDetailCommon();
	});
	
	//SELECTBOX - 교통비 입력간 이동수단 선택시
	$(document).on("change","#tform-transportation",function(){
		// util.js > initDetailCommon();
	});
	
	//BTN - 항목추가 > 추가(btn_add-gform, btn_add-tform), 내역수정 > 확인(btn_confirm-gform, btn_confirm-tform)
	$(document).on("click","#btn_add-gform, #btn_confirm-gform, #btn_add-tform, #btn_confirm-tform",function(){
		var ajaxURL = "", ajaxData = "", confirmStr = "", idx = "", usedate = "", categoryname = "",
			categorycode = "", storename = "", purpose = "", membercnt = "", price = "", billmethod = "", billmethodname = "",
			department = "", status = "";
		var userid = $("#btn_closing-spend-doc").attr("data-userid");
		var username = $("#btn_closing-spend-doc").attr("data-username");
		var monthIdx = $("#info_detail-date").attr("data-midx");
		
		if($(this).attr("id") == "btn_add-gform" || $(this).attr("id") == "btn_confirm-gform"){
			if (!$("#gform").parsley().validate()){
				return;
			}
			idx = $("#gidx").val();
			department = $("#gform-department").val();
			usedate = $("#gform-date").val();
			categoryname = $("#gform-aslist option:selected").text();
			categorycode = $("#gform-aslist").val();
			storename = $("#gform-store").val();
			purpose = $("#gform-purpose").val();
			membercnt = $("#gform-membercnt").val();
			price = $("#gform-price").val();
			billmethod = $("#gform-billmethod").val();
			billmethodname = $("#gform-billmethod option:selected").text();
			status = "G";
			ajaxData = {
				mIdx : monthIdx,
				idx : idx,
				userid : userid,
				username : username,
	        	usedate : usedate,
	        	categorycode : categorycode,
	        	storename : storename,
	        	membercnt : membercnt,
	        	price : price,
	        	billmethod : billmethod,
	        	purpose : purpose,
	        	department : department,
	        	status : status
			}
		}else{
			if (!$("#tform").parsley().validate()){
				return;
			}
			idx = $("#tidx").val();
			department = $("#tform-department").val();
			categoryname = $("#tform-aslist option:selected").text();
			categorycode = $("#tform-aslist").val();
			usedate = $("#tform-date").val().split(" ~ ")[0].split(" ")[0];
			spoint = $("#tform-spoint").val();
			dpoint = $("#tform-dpoint").val();
			sdate = $("#tform-date").val().split(" ~ ")[0];
			edate = $("#tform-date").val().split(" ~ ")[1];
			price = $("#tform-price").val();
			unitprice = $("#tform-unitprice").val();
			transportationName = $("#tform-transportation option:selected").text();
			transportationCode = $("#tform-transportation").val();
			distance = $("#tform-distance").val();
			toll = $("#tform-toll").val();
			status = "T";
			if (transportationCode != "car"){
				distance = "0";
				toll = "0";
				unitprice = "0";
			}
			purpose = $("#tform-purpose").val();
			ajaxData = {
				mIdx : monthIdx,
				idx : idx,
				userid : userid,
				username : username,
				spoint : spoint,
				dpoint : dpoint,
				sdate : sdate,
				edate : edate,
				usedate : usedate,
	        	categorycode : categorycode,
	        	storename : storename,
	        	price : price,
	        	unitprice : unitprice,
	        	tcode : transportationCode,
	        	distance : distance,
	        	toll : toll,
	        	purpose : purpose,
	        	department : department,
	        	status : status
			}
		}
		//추가시
		if (($(this).attr("id") == "btn_add-gform" || $(this).attr("id") == "btn_add-tform") && idx != ""){
			return;
		}else if (($(this).attr("id") == "btn_add-gform" || $(this).attr("id") == "btn_add-tform") && idx == ""){
			ajaxURL = "/infoSearch/detail/iCTL.jsp";
			confirmStr = "추가";
		}
		//수정시
		if (($(this).attr("id") == "btn_confirm-gform" || $(this).attr("id") == "btn_confirm-tform") && idx == ""){
			return;
		}else if (($(this).attr("id") == "btn_confirm-gform" || $(this).attr("id") == "btn_confirm-tform") && idx != ""){
			ajaxURL = "/infoSearch/detail/uCTL.jsp";
			confirmStr = "수정";
		}
		if (confirm("입력한 정보로 "+confirmStr+"하시겠습니까?")){
			$.ajax({      
		        type:"POST",  
		        url:ajaxURL,
		        data:ajaxData,
		        dataTypa:"JSON",
		        success:function(dat){
		        	if (dat.result == "success"){
		        		alert(confirmStr+" 완료하였습니다.");
		        		location.reload();	
		        	}else{
		        		alert(confirmStr+"에 실패했습니다. 다시 시도해주세요.");
		        	}
		        }
			});
		}
	});
	
	//BTN - 내역확인 > 삭제
	$(document).on("click","#btn_del-gform, #btn_del-tform",function(){
		// util.js > initDetailCommon();
	});
	
	//BTN - 항목추가 > 닫기
	$(document).on("click",".btn_close-form",function(){
		// util.js > initDetailCommon();
	});
	
	//CLICK - 상세내역 ROW
	$(document).on("click","#tbl_spend-detail-list > tbody > tr, #tbl_spend-traffic-list > tbody > tr",function(e){
		// util.js > initDetailCommon();
	});
});