$(document).ready(function(){
	// infoSearch/
	// INIT() >>
	if ($("#tbl_infosearch").length > 0){
		$("#tbl_infosearch").DataTable({
			dom: 'T<"clear">lfrtip',
			order: [[ 0, 'desc']],
			columnDefs: [{type:'currency', targets: [1,2,3,4] }],
			language: dataTableLanguageSet
		});
	}	
	// INIT() <<
	
	$(document).on("change","#search_year",function(){
		$("form").submit();
	});
	
	$(document).on("click","#btn_regist-mInfo",function(){
		if (!confirm("신규 지출결의 데이터를 생성하시겠습니까?")){
			return;
		}
		tmpFormSubmit("./iCTL.jsp","");
	});
	
	$(document).on("click","#tbl_infosearch > tbody > tr",function(){
		if ($(this).hasClass("nodata")) return;
		var yyyyMM = $(this).children("td").eq(0).text(); 
		var action = "/infoSearch/detail/";
		var inputs = "<input type='hidden' name='pYear' value='"+yyyyMM.split("-")[0]+"'>" +
				"<input type='hidden' name='pMonth' value='"+yyyyMM.split("-")[1]+"'>";
		tmpFormSubmit(action, inputs);
	});
	
	// infoSearch/detail/
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
	
	//BTN - 전원 데이터 복사
	$(document).on("click","#btn_copyPreData",function(){
		var $dInfo = $("#info_detail-date");
		if (confirm("["+$dInfo.attr("data-pdate")+"] 데이터를 복사 하시겠습니까?")){
			$.ajax({      
		        type:"POST",
		        url:"/infoSearch/detail/copyCTL.jsp",      
		        data:{
		        	midx : $dInfo.attr("data-midx"),
					preYear : $dInfo.attr("data-pdate").split("-")[0],
					preMonth : $dInfo.attr("data-pdate").split("-")[1]
		        },		        
		        cache:false,
		        dataType:"JSON",
		        success:function(data){
					if (data.result == "success"){
						alert("복사가 완료되었습니다.");
						location.reload();
					}else{
						alert("전월 데이터가 존재하지 않습니다.");
					}
		        },
		        error:function(e){
		            console.log(e.responseText);  
		        }
		    });
		}
	});
	
	//BTN - 제출하기, 인쇄하기
	$(document).on("click","#btn_regist-spend-doc, #btn_print-spend-doc",function(){
		//데이터 검증 플로 추가
		
		
		if ($(this).attr("id")=="btn_regist-spend-doc"){
			if(confirm("작성한 내용으로 지출결의서를 제출하시겠습니까?")){
				printSpendDocFunc();
			}
		}else{
			printSpendDocFunc();
		}
	});
	
	//BTN - 법인카드 내역 엑셀 등록
	$(document).on("click","#btn_regist-ccard-excel",function(){
		if ($("#panel-excel_upload").hasClass("hide")){
			alert("현대 법인카드 내역의 잦은 변경으로 법인카드 내역 업로드시 반드시 도움말 참조(2.엑셀파일 추가정보 입력)를 부탁드립니다.\n\n엑셀파일 추가정보 입력(계정과목, 목적, 인원)은 다운받은 엑셀 내용과는 관계 없이 순서대로 Z, AA, AB 열에 입력하여야 합니다.");
			$("#panel-excel_upload").removeClass("hide");
		}else{
			$("#panel-excel_upload").addClass("hide");
		}
	});
	
	//BTN - 항목추가
	$(document).on("click","#btn_add-ditem",function(){
		// util.js > initDetailCommon();
	});
	
	//BTN - 항목삭제
	$(document).on("click","#btn_del-ditem",function(){
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
		        	status : status
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