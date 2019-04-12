package com.eng.spend.infosearchdetail.module;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.eng.framework.config.Config;
import com.eng.framework.config.ConfigFactory;
import com.eng.framework.tray.Tray;
import com.eng.spend.getduzon.gate.GetDuzonGate;
import com.eng.spend.infosearch.gate.infosearchGate;

public class InfoSearchExcelExportModule {
	/** Base Properties */
	private static Config baseConf = null;
	/** Excel Row */
	private int rowCount = 0 , autoStatementNumber = 0, lineNumber = 0, totalCcardPrice = 0, totalPcardPrice = 0, totalCashPrice = 0;
	private String tmpUserID = "";
	private Map<String, Object> dzInfo = null;

    private Map<String, CellStyle> styles = null;
	
	static {
        try {
        	baseConf = ConfigFactory.getInstance().getConfiguration("base.properties");
        } catch (Exception e) {
            System.err.println("base properties file loading error");
            e.printStackTrace();            
        }
    }
	
	public InfoSearchExcelExportModule(){}
	
	public String exportExcel(Tray reqTray){
		String fileName = "AUTOSLIP_V1.xls";
		Tray rsTray = getDatalist(reqTray);
		this.dzInfo = getDuzonData(reqTray);
		String lastFileName = createFile(fileName);
		getHeapSize();
		exportData(lastFileName, rsTray, reqTray);
		getHeapSize();
		return lastFileName;
	}
	
	private Tray getDatalist(Tray reqTray){	
		infosearchGate gate = new infosearchGate();
		Tray rsTray = gate.findEx(reqTray);
		return rsTray;
	}
	
	private Map<String,Object> getDuzonData(Tray reqTray){
		GetDuzonGate gate = new GetDuzonGate();
		Tray rsTray = gate.find(reqTray);
		Map<String, Object> infoMap = new HashMap<>();
		if (rsTray.getRowCount()>0){
			for (int i=0;i<rsTray.getRowCount();i++){
				String tmpArr[] = {rsTray.getString("dept_cd", i),rsTray.getString("sect_cd", i),rsTray.getString("emp_cd", i),rsTray.getString("tr_cd1", i),rsTray.getString("tr_cd2", i)};
				infoMap.put(rsTray.getString("userid",i),tmpArr);
			}
		}
		return infoMap;
	}
	
	/**
	 * 엘셀 파일명을 결정한다.
	 * @param fileName
	 * @return
	 */
	private String createFile(String fileName){

		Calendar oCal = Calendar.getInstance();
		String file_time = "" + oCal.get(Calendar.YEAR) +"."
							  + Integer.toString((oCal.get(Calendar.MONTH)+1)) +"." 
							  + oCal.get(Calendar.DATE) +"."
							  + oCal.get(Calendar.HOUR_OF_DAY)
							  + oCal.get(Calendar.MINUTE);
		String dir = baseConf.getString("xls.downdir");
		File filedir = new File(dir);
		if(!filedir.isDirectory()){
			filedir.mkdir();
		}
		
		String lastFileName = dir+"/"+FilenameUtils.removeExtension(fileName)+"_" +file_time+"."+FilenameUtils.getExtension(fileName);
		System.out.println("filename : "+lastFileName);
		return lastFileName;
	}
	
	/**
	 * Excel Export
	 * @param lastFileName
	 * @param rsTray
	 */
	private void exportData(String lastFileName, Tray rsTray, Tray reqTray){
		try{
			Workbook wb = new HSSFWorkbook();
			this.styles = createStyles(wb);
			
			Sheet newSheet = null;
			
			//test
			newSheet = wb.createSheet(""+reqTray.getString("year")+"년 "+reqTray.getString("month")+"월 내역");
			createTitleRow(newSheet);
			for (int i=0;i<30;i++){
				newSheet.setColumnWidth(i, 5000);
			}
			for(int i = 0 ; i < rsTray.getRowCount() ; i++){
				createRowData(newSheet, rsTray, i); // 차변
				if (i == rsTray.getRowCount()-1){
					createRowDataTotal(newSheet,rsTray,i);
				}
			}
			
		    FileOutputStream fileOut = new FileOutputStream(lastFileName);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void createTitleRow(Sheet newSheet){
		Row row_code = newSheet.createRow(rowCount++);
	    Row row_tit = newSheet.createRow(rowCount++);
	    Row row_num = newSheet.createRow(rowCount++);
	    int cellCount = 0;
	    try{
	    	String titleStyleName = "cell_content_name";
	    	// row1 code
	    	setCell(row_code.createCell(cellCount++),"IN_DT",titleStyleName);
			setCell(row_code.createCell(cellCount++),"CO_CD",titleStyleName);
			setCell(row_code.createCell(cellCount++),"DIV_CD",titleStyleName);
			setCell(row_code.createCell(cellCount++),"DEPT_CD",titleStyleName);
			setCell(row_code.createCell(cellCount++),"ISU_DT",titleStyleName);
			setCell(row_code.createCell(cellCount++),"IN_SQ",titleStyleName);
			setCell(row_code.createCell(cellCount++),"LN_SQ",titleStyleName);
			setCell(row_code.createCell(cellCount++),"ACCT_CD",titleStyleName);
			setCell(row_code.createCell(cellCount++),"DRCR_FG",titleStyleName);
			setCell(row_code.createCell(cellCount++),"RMK_DC",titleStyleName);
			setCell(row_code.createCell(cellCount++),"ACCT_AM",titleStyleName);
			setCell(row_code.createCell(cellCount++),"TR_CD",titleStyleName);
			setCell(row_code.createCell(cellCount++),"CT_DEPT",titleStyleName);
			setCell(row_code.createCell(cellCount++),"PJT_CD",titleStyleName);
			setCell(row_code.createCell(cellCount++),"CT_NB",titleStyleName);
			setCell(row_code.createCell(cellCount++),"FR_DT",titleStyleName);
			setCell(row_code.createCell(cellCount++),"TO_DT",titleStyleName);
			setCell(row_code.createCell(cellCount++),"CT_QT",titleStyleName);
			setCell(row_code.createCell(cellCount++),"CT_AM",titleStyleName);
			setCell(row_code.createCell(cellCount++),"CT_RT",titleStyleName);
			setCell(row_code.createCell(cellCount++),"CT_DEAL",titleStyleName);
			setCell(row_code.createCell(cellCount++),"CT_USER1",titleStyleName);
			setCell(row_code.createCell(cellCount++),"CT_USER2",titleStyleName);
			setCell(row_code.createCell(cellCount++),"ATTR_CD",titleStyleName);
			setCell(row_code.createCell(cellCount++),"ISU_DOC",titleStyleName);
			setCell(row_code.createCell(cellCount++),"LOGIC_CD",titleStyleName);
			setCell(row_code.createCell(cellCount++),"DUMMY1",titleStyleName);
			setCell(row_code.createCell(cellCount++),"DUMMY2",titleStyleName);
			setCell(row_code.createCell(cellCount++),"JEONJA_YN",titleStyleName);
			setCell(row_code.createCell(cellCount++),"RMK_NB",titleStyleName);
	    	
			// row2 title
			cellCount = 0;
			setCell(row_tit.createCell(cellCount++),"처리일자",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"회사코드",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"사업장코드",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"부서코드",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"결의일자",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"자동전표번호",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"라인번호",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"계정과목",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"차대구분",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"적요",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"금액",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"관리항목",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"사용부서등",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"프로젝트코드",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"수출신고번호",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"발생일",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"만기일",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"사원",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"구분",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"증빙",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"품의내역",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"전표유형",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"환종",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"외화금액",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"전자세금계산서여부",titleStyleName);
			setCell(row_tit.createCell(cellCount++),"적요번호",titleStyleName);
			
			// row3 number
			cellCount = 0;
			setCell(row_num.createCell(cellCount++),"(0)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(1)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(2)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(3)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(4)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(5)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(6)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(7)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(8)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(9)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(10)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(11)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(12)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(13)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(14)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(15)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(16)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(17)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(18)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(19)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(20)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(21)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(22)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(23)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(24)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(25)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(26)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(27)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(28)",titleStyleName);
			setCell(row_num.createCell(cellCount++),"(29)",titleStyleName);
	    } catch (Exception e){
	    	e.printStackTrace();
	    }
	}

	private void createRowData(Sheet sheet, Tray infoTray, int i){
		if (!(tmpUserID).equals(infoTray.getString("userid", i).trim())){
			if (!("").equals(tmpUserID)){
				createRowDataTotal(sheet,infoTray,i-1);
			}
			tmpUserID = infoTray.getString("userid", i).trim();
			autoStatementNumber++;
			lineNumber = 0;
			totalCcardPrice = 0;
			totalPcardPrice = 0;
			totalCashPrice = 0;
		}
		lineNumber++;
		if (("ccard").equals(infoTray.getString("bill_method", i).trim())){
			totalCcardPrice+=infoTray.getInt("price", i);
		}else if (("pcard").equals(infoTray.getString("bill_method", i).trim())){
			totalPcardPrice+=infoTray.getInt("price", i);
		}else if (("cash").equals(infoTray.getString("bill_method", i).trim())){
			totalCashPrice+=infoTray.getInt("price", i);
		}
		
		Row row = sheet.createRow(rowCount++);
		int cellCount = 0;
		
		try{
			String[] tmpArr = (String[]) dzInfo.get(infoTray.getString("userid",i));
			/*
				--차변&대변
				tmpArr[0] : (12) 부서코드
				tmpArr[1] : (22) 부문코드
				tmpArr[2] : (21) 사원코드
				--대변
				tmpArr[3] : (11) 관리항목 - 개인
				tmpArr[4] : (11) 관리항목 - 법인
			*/			
			//처리일자(0) - YYYYMM31 (말일)
			setCell(row.createCell(cellCount++),infoTray.getString("process_day", i).trim());
			//회사코드(1) - 0101 고정
			setCell(row.createCell(cellCount++),infoTray.getString("company_code", i).trim());
			//사업장코드(2) - 1000 고정
			setCell(row.createCell(cellCount++),infoTray.getString("business_code", i).trim());
			//부서코드(3) - 부서마다 상이
			setCell(row.createCell(cellCount++),infoTray.getString("department_code", i).trim());
			//결의일자(4) - 처리일자(0)와 동일
			setCell(row.createCell(cellCount++),infoTray.getString("process_day", i).trim());
			//자동전표번호(5) - 90001~99999
			String rAutoNumber = "0000"+String.valueOf(autoStatementNumber);
			rAutoNumber = "9"+rAutoNumber.substring(rAutoNumber.length()-4, rAutoNumber.length());
			setCell(row.createCell(cellCount++),rAutoNumber);
			//라인번호(6) - 001~999
			String rLineNumber = "00"+String.valueOf(lineNumber);
			rLineNumber = rLineNumber.substring(rLineNumber.length()-3, rLineNumber.length());
			setCell(row.createCell(cellCount++),rLineNumber);
			//계정과목(7) - 81100, 81200, 81300, 81400, 82200, 82400, 82500, 82600, 83000, 83100
			setCell(row.createCell(cellCount++),infoTray.getString("category_code", i).trim());
			//차대구분(8) - 차변 혹은 대변
			setCell(row.createCell(cellCount++),"차변");
			//적요(9) - 사용목적
			if (infoTray.getString("purpose", i).trim().length() > 60){
				setCell(row.createCell(cellCount++),infoTray.getString("purpose", i).trim().substring(0, 60)+"...");
			}else{
				setCell(row.createCell(cellCount++),infoTray.getString("purpose", i).trim());
			}
		//	setCell(row.createCell(cellCount++),infoTray.getString("purpose", i).trim());
			//금액(10) - 사용금액
			setCell(row.createCell(cellCount++),String.valueOf(infoTray.getInt("price", i)));
			//관리항목(11) - 대변에만 표기 > 법인카드코드??
			setCell(row.createCell(cellCount++),"");
			//사용부서등(12) - 부서코드
			setCell(row.createCell(cellCount++),tmpArr[0]);
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),"");
			//발생일(15) - 처리일자(0)과 동일
			setCell(row.createCell(cellCount++),infoTray.getString("process_day", i).trim());
			//만기일(16) - 처리일자 기준 익월 20일
			setCell(row.createCell(cellCount++),infoTray.getString("last_day", i).trim());
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),"");
			//사원(21) - 사원코드
			setCell(row.createCell(cellCount++),tmpArr[2]);
			//구분(22) - 부문코드
			setCell(row.createCell(cellCount++),tmpArr[1]);
			//증빙(23) - 증빙코드 -접대비일 경우 처리
			String evidence = "";
			if ("81300".equals(infoTray.getString("category_code", i).trim())){
				if (("ccard").equals(infoTray.getString("bill_method", i).trim())){ // 법인
					evidence = "8";
				}else if (("pcard").equals(infoTray.getString("bill_method", i).trim())){ // 개인
					evidence = "9";
				}else{ // 현금
					evidence = "3A";
				}
			}
			setCell(row.createCell(cellCount++),evidence);
			//품의내역(24) - XX월 경비 - 홍길동
			setCell(row.createCell(cellCount++),infoTray.getString("details", i).trim());
			//전표유형(25) - 11 고정
			setCell(row.createCell(cellCount++),"11");
			setCell(row.createCell(cellCount++),""); // (26)
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),""); // (29)
		} catch (Exception e){
	    	e.printStackTrace();
	    }
	}
	
	private void createRowDataTotal(Sheet sheet, Tray infoTray, int i){
		Row row = sheet.createRow(rowCount++);
		Row row2 = sheet.createRow(rowCount++);
		//Row row3 = sheet.createRow(rowCount++);
		String rAutoNumber = "";
		String rLineNumber = "";
		String totalCode = "26200"; // 대변 계정 과목 코드
		int cellCount = 0;
		try{
			String[] tmpArr = (String[]) dzInfo.get(infoTray.getString("userid",i));
			/*
				--차변&대변
				tmpArr[0] : (12) 부서코드
				tmpArr[1] : (22) 부문코드
				tmpArr[2] : (21) 사원코드
				--대변
				tmpArr[3] : (11) 관리항목 - 개인
				tmpArr[4] : (11) 관리항목 - 법인
			*/
			
			rAutoNumber = "0000"+String.valueOf(autoStatementNumber);
			rAutoNumber = "9"+rAutoNumber.substring(rAutoNumber.length()-4, rAutoNumber.length());
			
			/* 개인카드 및 현금 START */
			lineNumber++;
			cellCount = 0;
			
			setCell(row.createCell(cellCount++),infoTray.getString("process_day", i).trim());
			setCell(row.createCell(cellCount++),infoTray.getString("company_code", i).trim());
			setCell(row.createCell(cellCount++),infoTray.getString("business_code", i).trim());
			setCell(row.createCell(cellCount++),infoTray.getString("department_code", i).trim());
			setCell(row.createCell(cellCount++),infoTray.getString("process_day", i).trim());
			
			setCell(row.createCell(cellCount++),rAutoNumber);
			rLineNumber = "00"+String.valueOf(lineNumber);
			rLineNumber = rLineNumber.substring(rLineNumber.length()-3, rLineNumber.length());
			setCell(row.createCell(cellCount++),rLineNumber);
			setCell(row.createCell(cellCount++),totalCode);
			setCell(row.createCell(cellCount++),"대변");
			setCell(row.createCell(cellCount++),infoTray.getString("process_day", i).trim().substring(0, 6)+" "+infoTray.getString("details", i).trim().split(" - ")[1]+" 개인카드 및 현금 합계");
			
			setCell(row.createCell(cellCount++),String.valueOf(totalPcardPrice+totalCashPrice));
			setCell(row.createCell(cellCount++),tmpArr[3]);
			setCell(row.createCell(cellCount++),tmpArr[0]);
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),"");
			
			setCell(row.createCell(cellCount++),infoTray.getString("process_day", i).trim());
			setCell(row.createCell(cellCount++),infoTray.getString("last_day", i).trim());
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),"");
			
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),tmpArr[2]);
			setCell(row.createCell(cellCount++),tmpArr[1]);
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),infoTray.getString("details", i).trim());
			
			setCell(row.createCell(cellCount++),"11");
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),"");
			setCell(row.createCell(cellCount++),"");
			/* 개인카드 END */
			
			/* 법인카드 START */
			lineNumber++;
			cellCount = 0;			
			
			setCell(row2.createCell(cellCount++),infoTray.getString("process_day", i).trim());
			setCell(row2.createCell(cellCount++),infoTray.getString("company_code", i).trim());
			setCell(row2.createCell(cellCount++),infoTray.getString("business_code", i).trim());
			setCell(row2.createCell(cellCount++),infoTray.getString("department_code", i).trim());
			setCell(row2.createCell(cellCount++),infoTray.getString("process_day", i).trim());
			
			setCell(row2.createCell(cellCount++),rAutoNumber);
			rLineNumber = "00"+String.valueOf(lineNumber);
			rLineNumber = rLineNumber.substring(rLineNumber.length()-3, rLineNumber.length());
			setCell(row2.createCell(cellCount++),rLineNumber);			
			setCell(row2.createCell(cellCount++),totalCode);
			setCell(row2.createCell(cellCount++),"대변");
			setCell(row2.createCell(cellCount++),infoTray.getString("process_day", i).trim().substring(0, 6)+" "+infoTray.getString("details", i).trim().split(" - ")[1]+" 법인카드 합계");
			
			setCell(row2.createCell(cellCount++),String.valueOf(totalCcardPrice));
			setCell(row2.createCell(cellCount++),tmpArr[4]);
			setCell(row2.createCell(cellCount++),tmpArr[0]);
			setCell(row2.createCell(cellCount++),"");
			setCell(row2.createCell(cellCount++),"");
			
			setCell(row2.createCell(cellCount++),infoTray.getString("process_day", i).trim());
			setCell(row2.createCell(cellCount++),infoTray.getString("last_day", i).trim());
			setCell(row2.createCell(cellCount++),"");
			setCell(row2.createCell(cellCount++),"");
			setCell(row2.createCell(cellCount++),"");
			
			setCell(row2.createCell(cellCount++),"");
			setCell(row2.createCell(cellCount++),tmpArr[2]);
			setCell(row2.createCell(cellCount++),tmpArr[1]);
			setCell(row2.createCell(cellCount++),"");
			setCell(row2.createCell(cellCount++),infoTray.getString("details", i).trim());
			
			setCell(row2.createCell(cellCount++),"11");
			setCell(row2.createCell(cellCount++),"");
			setCell(row2.createCell(cellCount++),"");
			setCell(row2.createCell(cellCount++),"");
			setCell(row2.createCell(cellCount++),"");
			/* 법인카드 END */
			
			/* 현금 START */
			/*
			lineNumber++;
			cellCount = 0;
			
			setCell(row3.createCell(cellCount++),infoTray.getString("process_day", i).trim());
			setCell(row3.createCell(cellCount++),infoTray.getString("company_code", i).trim());
			setCell(row3.createCell(cellCount++),infoTray.getString("business_code", i).trim());
			setCell(row3.createCell(cellCount++),infoTray.getString("department_code", i).trim());
			setCell(row3.createCell(cellCount++),infoTray.getString("process_day", i).trim());
			
			setCell(row3.createCell(cellCount++),rAutoNumber);
			rLineNumber = "00"+String.valueOf(lineNumber);
			rLineNumber = rLineNumber.substring(rLineNumber.length()-3, rLineNumber.length());
			setCell(row3.createCell(cellCount++),rLineNumber);			
			setCell(row3.createCell(cellCount++),"");
			setCell(row3.createCell(cellCount++),"대변");
			setCell(row3.createCell(cellCount++),infoTray.getString("process_day", i).trim().substring(0, 6)+" "+infoTray.getString("details", i).trim().split(" - ")[1]+" 현금 합계");
			
			setCell(row3.createCell(cellCount++),String.valueOf(totalCashPrice));
			setCell(row3.createCell(cellCount++),"OOOOO");
			setCell(row3.createCell(cellCount++),"부서CODE");
			setCell(row3.createCell(cellCount++),"");
			setCell(row3.createCell(cellCount++),"");
			
			setCell(row3.createCell(cellCount++),infoTray.getString("process_day", i).trim());
			setCell(row3.createCell(cellCount++),infoTray.getString("last_day", i).trim());
			setCell(row3.createCell(cellCount++),"");
			setCell(row3.createCell(cellCount++),"");
			setCell(row3.createCell(cellCount++),"");
			
			setCell(row3.createCell(cellCount++),"");
			setCell(row3.createCell(cellCount++),"");
			setCell(row3.createCell(cellCount++),"");
			setCell(row3.createCell(cellCount++),"");
			setCell(row3.createCell(cellCount++),infoTray.getString("details", i).trim());
			
			setCell(row3.createCell(cellCount++),"11");
			setCell(row3.createCell(cellCount++),"");
			setCell(row3.createCell(cellCount++),"");
			setCell(row3.createCell(cellCount++),"");
			setCell(row3.createCell(cellCount++),"");
			*/
			/* 현금 END */
		} catch (Exception e){
	    	e.printStackTrace();
	    }
	}
	
	
	/**
	 * 셀 정보 세팅
	 * @param cell
	 * @param cellvalue
	 * @param styleName
	 */
	private void setCell(Cell cell, String cellvalue, String styleName){
		cell.setCellValue(cellvalue);
		cell.setCellStyle(styles.get(styleName));
	}
	private void setCell(Cell cell, String cellvalue){
		cell.setCellValue(cellvalue);
		cell.setCellStyle(styles.get("cell_content_name"));
	}
	
	/**
	 * 메모리 확인
	 */
    private void getHeapSize(){ 
    	Runtime rt = Runtime.getRuntime();
    	long heapSize = rt.totalMemory();
    	long free = rt.freeMemory();
    	long maxMemory = rt.maxMemory();
    	System.out.println("heapSize \t" + heapSize/1000000);
    	System.out.println("free \t" + free/1000000);
    	System.out.println("maxMemory \t" + maxMemory/1000000);
    	rt.gc();
    }

    /**
     * 
     * @param wb
     * @return
     */
	private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = null;
        Font font1 = wb.createFont();
        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font1);
        styles.put("cell_content_name", style);

        return styles;
    }

    private static CellStyle createBorderedStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
}
