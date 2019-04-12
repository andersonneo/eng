package com.eng.spend.infosearchdetail.module;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.eng.framework.common.ExcelControl;
import com.eng.framework.common.ResultMap;
import com.eng.framework.config.Config;
import com.eng.framework.config.ConfigFactory;
import com.eng.framework.connection.DBManager;
import com.eng.framework.log.Log;
import com.eng.framework.sql.QueryRunner;
import com.eng.framework.tray.Tray;
import com.eng.spend.infosearchdetail.dto.InfoSearchDTO;

public class InfoSearchExcelInputModule {
	/**
	 * Connection 취득
	 * @return
	 */
	public Connection getConnection() { 
		Connection conn = null;
		try{
			conn = DBManager.getInstance().getConnection();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return conn;
	}
    
    /**
     * Excel 처리를 한다.
     * @param reqTray
     */
	public boolean execute(Tray reqTray){
		try{
			String excelFile = getExcelFile(reqTray);
			ExcelControl excelControl = new ExcelControl(excelFile, 50);
			ResultMap excelData = excelControl.getResult();
			ArrayList colName = excelData.getcolumnNames();
			if (!("카드번호").equals(colName.get(0))){
				return false;
			}
			ArrayList<InfoSearchDTO> productList = getDtoItemFromExcelData(excelData);
			insertData(productList, reqTray);
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 엑셀 데이타 가공을해 제품(입력) 데이타를 만든다.
	 * @param resultMap
	 * @return
	*/
	private ArrayList<InfoSearchDTO> getDtoItemFromExcelData(ResultMap resultMap) {
		ArrayList<InfoSearchDTO> pdDetailList = new ArrayList<InfoSearchDTO>();
		// TODO Auto-generated method stub
		InfoSearchDTO dto = null;
		int col = 0;
		for(int i = 0 ; i < resultMap.getRowCount()-1 ; i++){
			dto = new InfoSearchDTO();
			System.out.println("1 : "+resultMap.getString(i, col));
			dto.setMasked_cardnumber(resultMap.getString(i, col++));
			System.out.println("2 : "+resultMap.getString(i, col));
			dto.setMasked_name(resultMap.getString(i, col++));
			System.out.println("3 : "+resultMap.getString(i, col));
			dto.setUse_date(resultMap.getString(i, col++).replaceAll("[^0-9]", ""));
			System.out.println("4 : "+resultMap.getString(i, col));
			dto.setUse_store(resultMap.getString(i, col++));
			
			System.out.println("5 : "+resultMap.getString(i, col));
			dto.setSupply_price(resultMap.getString(i, col++).replaceAll("[^0-9]", ""));
			System.out.println("6 : "+resultMap.getString(i, col));
			dto.setTax_price(resultMap.getString(i, col++).replaceAll("[^0-9]", ""));
			System.out.println("7 : "+resultMap.getString(i, col));
			dto.setService_price(resultMap.getString(i, col++).replaceAll("[^0-9]", ""));
			System.out.println("8 : "+resultMap.getString(i, col));
			dto.setUsed_price(resultMap.getString(i, col++).replaceAll("[^0-9]", ""));
			System.out.println("9 : "+resultMap.getString(i, col));
			dto.setDiscount_price(resultMap.getString(i, col++).replaceAll("[^0-9]", ""));
			System.out.println("10 : "+resultMap.getString(i, col));
			dto.setLocal_price(resultMap.getString(i, col++));
			
			System.out.println("11 : "+resultMap.getString(i, col));
			dto.setCurrency_code(resultMap.getString(i, col++));
			System.out.println("12 : "+resultMap.getString(i, col));
			dto.setExrate(resultMap.getString(i, col++));
			System.out.println("13 : "+resultMap.getString(i, col));
			dto.setUse_place(resultMap.getString(i, col++));
			System.out.println("14 : "+resultMap.getString(i, col));
			dto.setStore_ceo(resultMap.getString(i, col++));
			System.out.println("15 : "+resultMap.getString(i, col));
			dto.setStore_address(resultMap.getString(i, col++));
			System.out.println("17 : "+resultMap.getString(i, col));
			dto.setLicense_no(resultMap.getString(i, col++));
			System.out.println("18 : "+resultMap.getString(i, col));
			dto.setApproval_no(resultMap.getString(i, col++));
			
			System.out.println("19 : "+resultMap.getString(i, col));
			dto.setTax_type(resultMap.getString(i, col++));
			System.out.println("20 : "+resultMap.getString(i, col));
			dto.setStatement_classification(resultMap.getString(i, col++));
			System.out.println("21 : "+resultMap.getString(i, col));
			dto.setPartial_cancel(resultMap.getString(i, col++));
			System.out.println("22 : "+resultMap.getString(i, col));
			dto.setUSD_price(resultMap.getString(i, col++));
			System.out.println("23 : "+resultMap.getString(i, col));
			dto.setUSD_exrate(resultMap.getString(i, col++));
			System.out.println("24 : "+resultMap.getString(i, col));
			dto.setReception_date(resultMap.getString(i, col++).replaceAll("[^0-9]", ""));
			System.out.println("25 : "+resultMap.getString(i, col));
			dto.setPayment_date(resultMap.getString(i, col++).replaceAll("[^0-9]", ""));
			System.out.println("26 : "+resultMap.getString(i, col));
			dto.setApproval_time(resultMap.getString(i, col++));
			System.out.println("27 : "+resultMap.getString(i, col));
			String category_code = resultMap.getString(i, col++);
			if (category_code.indexOf("여비") > -1 || category_code.indexOf("교통") > -1){
				dto.setCategory_code("TV");
			}else if (category_code.indexOf("복리") > -1){
				dto.setCategory_code("BE");
			}else if (category_code.indexOf("접대") > -1){
				dto.setCategory_code("HO");
			}else if (category_code.indexOf("통신") > -1){
				dto.setCategory_code("CO");
			}else if (category_code.indexOf("차량") > -1){
				dto.setCategory_code("CM");
			}else if (category_code.indexOf("운반") > -1){
				dto.setCategory_code("TR");
			}else if (category_code.indexOf("도서") > -1 || category_code.indexOf("인쇄") > -1){
				dto.setCategory_code("PR");
			}else if (category_code.indexOf("소모품") > -1){
				dto.setCategory_code("EX");
			}else if (category_code.indexOf("수수료") > -1){
				dto.setCategory_code("FE");
			}else{
				dto.setCategory_code("");
			}
			System.out.println("28 : "+resultMap.getString(i, col));
			dto.setPurpose(resultMap.getString(i, col++));
			System.out.println("29 : "+resultMap.getString(i, col));
			int memCnt = 0;
			if (("").equals(resultMap.getString(i, col))){
				memCnt = 1; 
			}else{
				try{
					memCnt = Integer.parseInt(resultMap.getString(i, col));
				}catch(NumberFormatException e) { 
					memCnt = 1;
			    } catch(NullPointerException e) {
			    	memCnt = 1;
			    }
			}
			dto.setMember_cnt(memCnt);
			
			col = 0; 
			pdDetailList.add(dto);
		}
		return pdDetailList;
	}

	/**
	 * 업로드된 파일을 취득한다.
	 * @param reqTray
	 * @return
	 */
	public String getExcelFile(Tray reqTray){
		String filename = null;
		try{
			Config conf = ConfigFactory.getInstance().getConfiguration("base.properties");
			String downName = reqTray.getString("attach");
			String reqfileName = downName.substring(downName.lastIndexOf("\\") + 1, downName.length());
			filename = conf.getString("file.upload.path") + "/" + reqfileName;
		} catch(Exception e){
			e.printStackTrace();
		}
		return filename;
	}

	/**
	 * 제품 등록을 한다.
	 * @param productList
	 */
	public void insertData(ArrayList<InfoSearchDTO> productList, Tray reqTray){
		Connection conn = null;
		try{
			conn = this.getConnection();
			QueryRunner runner = new QueryRunner(getQuery());
			if(productList != null && productList.size() > 0){
				for( InfoSearchDTO dto : productList ){
					setParam(runner, dto);
					runner.setParam("userid", reqTray.getString("userid"));
					runner.setParam("username", reqTray.getString("username"));
					runner.setParam("rdate", reqTray.getString("attachDate"));
					runner.setParam("midx", reqTray.getInt("attachMIdx"));
					runner.setParam("department", reqTray.getString("department"));
					Log.debug("", this, "INSERT QUERY [" + runner.toString());
					int cnt = runner.update(conn);
					if(cnt < 1){
						Log.debug("Excel Insert Error CNT[" + cnt + "]");
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 상세 상품 정보 등록용 Query를 취득한다.
	 * @return
	 */
	private String getQuery() {
		// TODO Auto-generated method stub
    	StringBuffer query = new StringBuffer();
    	query.append("\n	INSERT INTO dh_card_details(userid, masked_cardnumber, masked_name, use_date, use_store, supply_price, tax_price, service_price, used_price, ");
    	query.append("\n		discount_price, local_price, currency_code, exrate, use_place, store_ceo, store_address, license_no, approval_no, tax_type, ");
    	query.append("\n		statement_classification, partial_cancel, USD_price, USD_exrate, reception_date, payment_date, approval_time, rdate, rtime) ");
    	query.append("\n	VALUES (:userid, :masked_cardnumber, :masked_name, :use_date, :use_store, :supply_price, :tax_price, :service_price, :used_price, ");
    	query.append("\n		:discount_price, :local_price, :currency_code, :exrate, :use_place, :store_ceo, :store_address, :license_no, :approval_no, :tax_type, ");
    	query.append("\n		:statement_classification, :partial_cancel, :USD_price, :USD_exrate, :reception_date, :payment_date, :approval_time, :rdate, now() ); ");
    	
    	query.append("\n	INSERT INTO dh_spend_item( spend_month_idx, userid, username, use_date, category_code, ");
    	query.append("\n		store_name, use_member_cnt, price, bill_method, use_purpose, ");
    	query.append("\n		department_code, regdate) ");
    	query.append("\n	VALUES (:midx, :userid, :username, to_timestamp(:use_date_addbar,'YYYY-MM-DD HH24:MI:SS'), :categorycode, ");
    	query.append("\n		:use_store, :member_cnt, :price, 'ccard', :purpose, ");
    	query.append("\n		:department, now() ); ");
    	
    	return query.toString();
	}
	
	/**
	 * 파람메터 셋팅
	 * @param runner
	 * @param dto
	 */
	private void setParam(QueryRunner runner, InfoSearchDTO dto) {
		// TODO Auto-generated method stub
    	runner.setParam("masked_cardnumber", dto.getMasked_cardnumber());
    	runner.setParam("masked_name", dto.getMasked_name());
    	runner.setParam("use_date", dto.getUse_date());
    	runner.setParam("use_store", dto.getUse_store());
    	runner.setParam("supply_price", dto.getSupply_price());
    	runner.setParam("tax_price", dto.getTax_price());
    	runner.setParam("service_price", dto.getService_price());
    	runner.setParam("used_price", dto.getUsed_price());
    	runner.setParam("discount_price", dto.getDiscount_price());
    	runner.setParam("local_price", dto.getLocal_price());
    	runner.setParam("currency_code", dto.getCurrency_code());
    	runner.setParam("exrate", dto.getExrate());
    	runner.setParam("use_place", dto.getUse_place());
    	runner.setParam("store_ceo", dto.getStore_ceo());
    	runner.setParam("store_address", dto.getStore_address());
    	runner.setParam("license_no", dto.getLicense_no());
    	runner.setParam("approval_no", dto.getApproval_no());
    	runner.setParam("tax_type", dto.getTax_type());
    	runner.setParam("statement_classification", dto.getStatement_classification());
    	runner.setParam("partial_cancel", dto.getPartial_cancel());
    	runner.setParam("USD_price", dto.getUSD_price());
    	runner.setParam("USD_exrate", dto.getUSD_exrate());
    	runner.setParam("reception_date", dto.getReception_date());
    	runner.setParam("payment_date", dto.getPayment_date());
    	runner.setParam("approval_time", dto.getApproval_time());
    	runner.setParam("categorycode", dto.getCategory_code());
    	runner.setParam("purpose", dto.getPurpose());
    	runner.setParam("member_cnt", dto.getMember_cnt());
    	runner.setParam("use_date_addbar", dto.getUse_date().substring(0, 4)+"-"+dto.getUse_date().substring(4, 6)+"-"+dto.getUse_date().substring(6, 8)+" "+dto.getApproval_time());
    	runner.setParam("price", Integer.parseInt(dto.getUsed_price()));
	}
}
