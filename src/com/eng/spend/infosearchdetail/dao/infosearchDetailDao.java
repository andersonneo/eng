package com.eng.spend.infosearchdetail.dao;

import java.sql.Connection;

import com.eng.framework.dao.BaseDao;
import com.eng.framework.exception.AppException;
import com.eng.framework.log.Log;
import com.eng.framework.sql.QueryRunner;
import com.eng.framework.tray.Tray;

public class infosearchDetailDao extends BaseDao{
	public boolean insert(Connection conn, Tray reqTray) throws AppException{
		boolean result = true;
        try{      
        	int spendCnt = 0;
        	int trafficCnt = 0;
        	Tray tmpTray = null;
        	
        	StringBuffer SELECT_SQL = new StringBuffer();
        	SELECT_SQL.append("\n	SELECT idx FROM dh_spend_month WHERE userid = '"+reqTray.getString("userid")+"' AND year = '"+reqTray.getString("preYear")+"' AND month = '"+reqTray.getString("preMonth")+"' ");
        	QueryRunner runner = new QueryRunner(SELECT_SQL.toString());
        	Log.debug("", this, "SELECT_SQL : \n" + runner.toString());  
        	tmpTray = (Tray)runner.query(conn);
        	
        	if (tmpTray.getRowCount() == 1){
	        	StringBuffer INSERT_SQL = new StringBuffer();
	        	//dh_spend_item
	        	INSERT_SQL.append("\n	INSERT INTO dh_spend_item( spend_month_idx, userid, username, use_date, ");
	        	INSERT_SQL.append("\n		category_code, store_name, use_member_cnt, price, bill_method, use_purpose, department_code, regdate) ");
	        	INSERT_SQL.append("\n	SELECT '"+reqTray.getString("midx")+"' AS idx, userid, username, use_date + INTERVAL '1 month' AS use_date, ");
	        	INSERT_SQL.append("\n		category_code, store_name, use_member_cnt, price, bill_method, use_purpose, department_code, now() AS regdate FROM dh_spend_item ");
	        	INSERT_SQL.append("\n	WHERE userid = '"+reqTray.getString("userid")+"'  and spend_month_idx = '"+tmpTray.getInt("idx")+"'; ");
	        	
	        	runner = new QueryRunner(INSERT_SQL.toString());        	 
	        	Log.debug("", this, "UPDATE_SQL : \n" + runner.toString());  
	        	spendCnt = runner.update(conn);
	        	
	        	//dh_traffic_item
	        	INSERT_SQL.setLength(0);
	        	INSERT_SQL.append("\n	INSERT INTO dh_traffic_item( spend_month_idx, category_code, userid, username, department_code, ");
				INSERT_SQL.append("\n		start_date, end_date, ");
				INSERT_SQL.append("\n		price, method, distance, toll_pay, unit_price, use_purpose, regdate, starting_point, destination_point) ");
				INSERT_SQL.append("\n	SELECT '"+reqTray.getString("midx")+"' AS idx, category_code, userid, username, department_code, ");
				INSERT_SQL.append("\n		start_date + INTERVAL '1 month' AS start_date, end_date + INTERVAL '1 month' AS end_date, ");
				INSERT_SQL.append("\n		price, method, distance, toll_pay, unit_price, use_purpose, now() as regdate, starting_point, destination_point FROM dh_traffic_item ");
				INSERT_SQL.append("\n	where userid = '"+reqTray.getString("userid")+"' and spend_month_idx = '"+tmpTray.getInt("idx")+"'; ");
				
				runner = new QueryRunner(INSERT_SQL.toString());        	 
	        	Log.debug("", this, "UPDATE_SQL : \n" + runner.toString());  
	        	trafficCnt = runner.update(conn);
        	}

        	if(spendCnt < 1 && trafficCnt < 1){
                result = false;
        	}
        	return result;
        }catch (Exception ex){
            try{
                conn.rollback();
            }catch (Exception e){}
            String methodName = "."+new Object(){}.getClass().getEnclosingMethod().getName()+"()";
            Log.error("ERROR", this, "at "+this.getClass().getName()+methodName+ex);
            ex.printStackTrace();
            throw new AppException(this.getClass().getSimpleName()+methodName+" Exception ", ex);
        }
    }
	
	public Tray find(Connection conn, Tray reqTray) throws AppException{		
		try{
			Tray rsTray = null;
        	
        	StringBuffer SELECT_LIST_SQL = new StringBuffer();
        	SELECT_LIST_SQL.append("\n 	SELECT b.idx AS didx, to_char(b.use_date,'YYYY-MM-DD HH24:MI:SS') AS usedate, c.category_name AS category, b.category_code as categorycode, b.use_purpose AS purpose, b.store_name as storename, ");
        	SELECT_LIST_SQL.append("\n 		b.use_member_cnt AS membercnt, b.price, b.bill_method AS billmethod, b.department_code AS department FROM dh_spend_month a ");
        	SELECT_LIST_SQL.append("\n 	 	INNER JOIN dh_spend_item b ON a.idx = b.spend_month_idx AND a.userid = b.userid ");
        	SELECT_LIST_SQL.append("\n 		LEFT JOIN dh_category c ON b.category_code = c.category_code ");
        	SELECT_LIST_SQL.append("\n 	WHERE a.userid = '"+reqTray.getString("userid")+"' AND a.year = '"+reqTray.getString("pYear")+"' AND a.month = '"+reqTray.getString("pMonth")+"'");
        	SELECT_LIST_SQL.append("\n 	ORDER BY b.use_date ASC");

    		Log.debug("", this, "SELECT_LIST_SQL : \n" + SELECT_LIST_SQL.toString());
        	        	        	
        	QueryRunner runner = new QueryRunner(SELECT_LIST_SQL.toString());
        	
            rsTray = (Tray)runner.query(conn);
        	return rsTray;	
		}catch (Exception ex){
			String methodName = "."+new Object(){}.getClass().getEnclosingMethod().getName()+"()";
            Log.error("ERROR", this, "at "+this.getClass().getName()+methodName+ex);
            ex.printStackTrace();
            throw new AppException(this.getClass().getSimpleName()+methodName+" Exception ", ex);
		}
	}
	
	public Tray findEx(Connection conn, Tray reqTray) throws AppException{		
		try{
			Tray rsTray = null;
        	
        	StringBuffer SELECT_LIST_SQL = new StringBuffer();
        	SELECT_LIST_SQL.append("\n 	SELECT b.idx AS didx, b.starting_point AS spoint, to_char(b.start_date,'YYYY-MM-DD HH24:MI:SS') AS sdate, b.destination_point AS dpoint, to_char(b.end_date,'YYYY-MM-DD HH24:MI:SS') AS edate, ");
        	SELECT_LIST_SQL.append("\n 		b.use_purpose AS purpose, b.method as transportation, b.price, b.department_code AS department, b.distance, b.toll_pay, b.unit_price FROM dh_spend_month a ");
        	SELECT_LIST_SQL.append("\n 	INNER JOIN dh_traffic_item b ON a.idx = b.spend_month_idx AND a.userid = b.userid ");
        	SELECT_LIST_SQL.append("\n 	LEFT JOIN dh_category c ON b.category_code = c.category_code ");
        	SELECT_LIST_SQL.append("\n 	WHERE a.userid = '"+reqTray.getString("userid")+"' AND a.year = '"+reqTray.getString("pYear")+"' AND a.month = '"+reqTray.getString("pMonth")+"'");
        	SELECT_LIST_SQL.append("\n 	ORDER BY b.start_date ASC");

    		Log.debug("", this, "SELECT_LIST_SQL : \n" + SELECT_LIST_SQL.toString());
        	        	        	
        	QueryRunner runner = new QueryRunner(SELECT_LIST_SQL.toString());
        	
            rsTray = (Tray)runner.query(conn);
        	return rsTray;	
		}catch (Exception ex){
			String methodName = "."+new Object(){}.getClass().getEnclosingMethod().getName()+"()";
            Log.error("ERROR", this, "at "+this.getClass().getName()+methodName+ex);
            ex.printStackTrace();
            throw new AppException(this.getClass().getSimpleName()+methodName+" Exception ", ex);
		}
	}
	
	//INSERT 이후 IDX SELECT
	public Tray findStatic(Connection conn, Tray reqTray) throws AppException{		
		try{
			Tray rsTray = null;
        	int cnt = 0;
			
        	StringBuffer INSERT_SQL = new StringBuffer();
			StringBuffer SELECT_LIST_SQL = new StringBuffer();
        	
        	if (("G").equals(reqTray.getString("status"))){
        		INSERT_SQL.append("\n	INSERT INTO dh_spend_item( spend_month_idx, userid, username, use_date, category_code, ");
    			INSERT_SQL.append("\n		store_name, use_member_cnt, price, bill_method, use_purpose, ");
    			INSERT_SQL.append("\n		department_code, regdate) ");
    			INSERT_SQL.append("\n	VALUES ('"+reqTray.getString("mIdx")+"', '"+reqTray.getString("userid")+"', '"+reqTray.getString("username")+"', '"+reqTray.getString("usedate")+"', '"+reqTray.getString("categorycode")+"', ");
    			INSERT_SQL.append("\n		'"+reqTray.getString("storename")+"', '"+reqTray.getString("membercnt")+"', '"+reqTray.getString("price")+"', '"+reqTray.getString("billmethod")+"', '"+reqTray.getString("purpose")+"', ");
    			INSERT_SQL.append("\n		'"+reqTray.getString("department")+"', now() ); ");
        	}else if (("T").equals(reqTray.getString("status"))){
        		INSERT_SQL.append("\n	INSERT INTO dh_traffic_item( spend_month_idx, category_code, userid, username, department_code, ");
    			INSERT_SQL.append("\n		start_date, end_date, price, method, distance, ");
    			INSERT_SQL.append("\n		toll_pay, unit_price, use_purpose, regdate, starting_point, destination_point ) ");
    			INSERT_SQL.append("\n	VALUES ('"+reqTray.getString("mIdx")+"', '"+reqTray.getString("categorycode")+"', '"+reqTray.getString("userid")+"', '"+reqTray.getString("username")+"', '"+reqTray.getString("department")+"', ");
    			INSERT_SQL.append("\n		'"+reqTray.getString("sdate")+"', '"+reqTray.getString("edate")+"', '"+reqTray.getString("price")+"', '"+reqTray.getString("tcode")+"', '"+reqTray.getString("distance")+"', ");
    			INSERT_SQL.append("\n		'"+reqTray.getString("toll")+"', '"+reqTray.getString("unitprice")+"', '"+reqTray.getString("purpose")+"', now(), '"+reqTray.getString("spoint")+"', '"+reqTray.getString("dpoint")+"' ); ");
        	}
        				
        	QueryRunner runner = new QueryRunner(INSERT_SQL.toString());
        	Log.debug("", this, "INSERT_SQL : \n" + runner.toString());  
        	cnt = runner.update(conn);
        	
        	if(cnt == 1){                
        		if (("G").equals(reqTray.getString("status"))){
        			SELECT_LIST_SQL.append("\n 	SELECT MAX(idx) AS gidx FROM dh_spend_item WHERE userid = '"+reqTray.getString("userid")+"' ");
        		}else if (("T").equals(reqTray.getString("status"))){
        			SELECT_LIST_SQL.append("\n 	SELECT MAX(idx) AS gidx FROM dh_traffic_item WHERE userid = '"+reqTray.getString("userid")+"' ");
        		}
	        	Log.debug("", this, "SELECT_LIST_SQL : \n" + SELECT_LIST_SQL.toString());
	        	runner = new QueryRunner(SELECT_LIST_SQL.toString());
	        	rsTray = (Tray)runner.query(conn);
	        	rsTray.setString("result", "true");
        	}            
        	return rsTray;	
		}catch (Exception ex){
			String methodName = "."+new Object(){}.getClass().getEnclosingMethod().getName()+"()";
            Log.error("ERROR", this, "at "+this.getClass().getName()+methodName+ex);
            ex.printStackTrace();
            throw new AppException(this.getClass().getSimpleName()+methodName+" Exception ", ex);
		}
	}
	
	public boolean update(Connection conn, Tray reqTray) throws AppException{
		boolean result = true;
        try{      
        	int cnt = 0; 
        	
        	StringBuffer UPDATE_SQL = new StringBuffer();
        	if (("G").equals(reqTray.getString("status"))){
	        	UPDATE_SQL.append("\n	UPDATE dh_spend_item SET use_date = '"+reqTray.getString("usedate")+"', category_code = '"+reqTray.getString("categorycode")+"', ");
	        	UPDATE_SQL.append("\n		store_name = '"+reqTray.getString("storename")+"', use_member_cnt = '"+reqTray.getString("membercnt")+"', price = '"+reqTray.getString("price")+"', ");
	        	UPDATE_SQL.append("\n		bill_method = '"+reqTray.getString("billmethod")+"', use_purpose = '"+reqTray.getString("purpose")+"', department_code = '"+reqTray.getString("department")+"' ");
	        	UPDATE_SQL.append("\n	WHERE userid = '"+reqTray.getString("userid")+"' AND idx = '"+reqTray.getString("idx")+"' ");
        	}else if (("T").equals(reqTray.getString("status"))){
        		UPDATE_SQL.append("\n	UPDATE dh_traffic_item SET category_code = '"+reqTray.getString("categorycode")+"', department_code = '"+reqTray.getString("department")+"', ");
	        	UPDATE_SQL.append("\n		start_date = '"+reqTray.getString("sdate")+"', end_date = '"+reqTray.getString("edate")+"', price = '"+reqTray.getString("price")+"', ");
	        	UPDATE_SQL.append("\n		method = '"+reqTray.getString("tcode")+"', distance = '"+reqTray.getString("distance")+"', toll_pay = '"+reqTray.getString("toll")+"', unit_price = '"+reqTray.getString("unitprice")+"', ");
	        	UPDATE_SQL.append("\n		use_purpose = '"+reqTray.getString("purpose")+"', starting_point = '"+reqTray.getString("spoint")+"', destination_point = '"+reqTray.getString("dpoint")+"' ");
	        	UPDATE_SQL.append("\n	WHERE userid = '"+reqTray.getString("userid")+"' AND idx = '"+reqTray.getString("idx")+"' ");
        	}

        	QueryRunner runner = new QueryRunner(UPDATE_SQL.toString());        	 
        	Log.debug("", this, "UPDATE_SQL : \n" + runner.toString());  
        	cnt = runner.update(conn);
        	if(cnt != 1){                
                result = false;
        	}
        	return result;
        }catch (Exception ex){
            try{
                conn.rollback();
            }catch (Exception e){}
            String methodName = "."+new Object(){}.getClass().getEnclosingMethod().getName()+"()";
            Log.error("ERROR", this, "at "+this.getClass().getName()+methodName+ex);
            ex.printStackTrace();
            throw new AppException(this.getClass().getSimpleName()+methodName+" Exception ", ex);
        }
    }
	
	public boolean delete(Connection conn, Tray reqTray) throws AppException{
		boolean result = true;
        try{      
        	int cnt = 0; 
        	
        	StringBuffer DELETE_SQL = new StringBuffer();
        	if (("G").equals(reqTray.getString("status"))){
        		DELETE_SQL.append("\n	DELETE FROM dh_spend_item WHERE userid = '"+reqTray.getString("userid")+"' and idx in ("+reqTray.getString("delItem")+") ");
        	}else if (("T").equals(reqTray.getString("status"))){
        		DELETE_SQL.append("\n	DELETE FROM dh_traffic_item WHERE userid = '"+reqTray.getString("userid")+"' and idx in ("+reqTray.getString("delItem")+") ");
        	} 	
        	QueryRunner runner = new QueryRunner(DELETE_SQL.toString());        	 
        	Log.debug("", this, "DELETE_SQL : \n" + runner.toString());  
        	cnt = runner.update(conn);

        	if(cnt < 1){                
                result = false;
        	}
        	return result;
        }catch (Exception ex){
            try{
                conn.rollback();
            }catch (Exception e){}
            String methodName = "."+new Object(){}.getClass().getEnclosingMethod().getName()+"()";
            Log.error("ERROR", this, "at "+this.getClass().getName()+methodName+ex);
            ex.printStackTrace();
            throw new AppException(this.getClass().getSimpleName()+methodName+" Exception ", ex);
        }
    }
}