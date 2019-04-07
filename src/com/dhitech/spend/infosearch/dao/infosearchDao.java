package com.dhitech.spend.infosearch.dao;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhitech.framework.dao.BaseDao;
import com.dhitech.framework.exception.AppException;
import com.dhitech.framework.log.Log;
import com.dhitech.framework.sql.QueryRunner;
import com.dhitech.framework.tray.Tray;
import com.dhitech.spend.login.cmd.loginCmd;

public class infosearchDao extends BaseDao{
	public boolean insert(Connection conn, Tray reqTray) throws AppException{
		boolean result = true;
        try{
        	int cnt = 0;
        	Tray rsTray = null;
        	
        	StringBuffer SELECT_SQL = new StringBuffer();
        	StringBuffer INSERT_SQL = new StringBuffer();
        	
        	SELECT_SQL.append("\n	SELECT TO_CHAR(TO_DATE(year||month,'YYYYMM') + INTERVAL '1 month', 'yyyy-mm') AS yyyymm FROM dh_spend_month ");
        	SELECT_SQL.append("\n	WHERE userid = '"+reqTray.getString("userid")+"' ORDER BY year DESC, month DESC LIMIT 1 ");
        	QueryRunner runner = new QueryRunner(SELECT_SQL.toString());        	 
        	Log.debug("", this, "SELECT_SQL : \n" + runner.toString());
        	rsTray = (Tray)runner.query(conn);

        	INSERT_SQL.append("\n	INSERT INTO dh_spend_month (userid, year, month, company_card, credit_card, cash, create_date, update_date, finish_status, department)");
        	INSERT_SQL.append("\n	VALUES ('"+reqTray.getString("userid")+"','"+rsTray.getString("yyyymm").split("-")[0]+"','"+rsTray.getString("yyyymm").split("-")[1]+"','0','0','0',now(),null,'w','"+reqTray.getString("departmemt")+"')");
 	
        	runner = new QueryRunner(INSERT_SQL.toString());        	 
        	Log.debug("", this, "INSERT_SQL : \n" + runner.toString());  
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
	
	public boolean update(Connection conn, Tray reqTray) throws AppException{
		boolean result = true;
        try{      
        	int cnt = 0; 
        	
        	StringBuffer UPDATE_SQL = new StringBuffer();
        	        	
        	UPDATE_SQL.append("\n	UPDATE dh_spend_month SET ");
        	if (!("").equals(reqTray.getString("cCard"))){
        		UPDATE_SQL.append("\n		company_card = '"+reqTray.getString("cCard")+"', ");
        	}
        	if (!("").equals(reqTray.getString("pCard"))){
        		UPDATE_SQL.append("\n		credit_card = '"+reqTray.getString("pCard")+"', ");
        	}
        	if (!("").equals(reqTray.getString("cash"))){
        		UPDATE_SQL.append("\n		cash = '"+reqTray.getString("cash")+"', ");
        	}
        	UPDATE_SQL.append("\n		update_date = now(), finish_status = '"+reqTray.getString("status")+"' ");
        	UPDATE_SQL.append("\n	WHERE userid = '"+reqTray.getString("userid")+"' AND year ='"+reqTray.getString("pYear")+"' AND month = '"+reqTray.getString("pMonth")+"' ");
 	
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
	
	public boolean updateEx(Connection conn, Tray reqTray) throws AppException{
		boolean result = true;
        try{      
        	int cnt = 0; 
        	
        	StringBuffer UPDATE_SQL = new StringBuffer();
        	        	
        	UPDATE_SQL.append("\n	UPDATE dh_spend_month SET ");
        	UPDATE_SQL.append("\n		update_date = now(), finish_status = 'c' ");
        	UPDATE_SQL.append("\n	WHERE idx in ( "+reqTray.getString("closingIdx")+" ) ");
 	
        	QueryRunner runner = new QueryRunner(UPDATE_SQL.toString());
        	Log.debug("", this, "UPDATE_SQL : \n" + runner.toString());  
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
		
	public Tray find(Connection conn, Tray reqTray) throws AppException{		
		try{
			Tray rsTray = null;
        	
        	StringBuffer SELECT_LIST_SQL = new StringBuffer();
    		SELECT_LIST_SQL.append("\n 	SELECT idx, a.userid, b.username, year, month, company_card, credit_card, cash, create_date, ");
    		SELECT_LIST_SQL.append("\n 		update_date, finish_status, department, company_card+credit_card+cash as total ");
    		SELECT_LIST_SQL.append("\n 	FROM dh_spend_month a");
    		SELECT_LIST_SQL.append("\n 	INNER JOIN dh_user b on a.userid = b.userid ");
    		SELECT_LIST_SQL.append("\n 	WHERE 1=1 ");
    		if (!("").equals(reqTray.getString("userid"))){
    			SELECT_LIST_SQL.append("\n	AND a.userid = '"+reqTray.getString("userid")+"' ");
    		}else{
    			SELECT_LIST_SQL.append("\n	AND finish_status <> 'w' ");
    		}
    		if (!("").equals(reqTray.getString("search_year"))){
    			SELECT_LIST_SQL.append("\n 	AND year = '"+reqTray.getString("search_year")+"' ");
    		}
    		if (!("").equals(reqTray.getString("search_month"))){
    			SELECT_LIST_SQL.append("\n 	AND month = '"+reqTray.getString("search_month")+"' ");
    		}
    		SELECT_LIST_SQL.append("\n 	ORDER BY year DESC, month DESC ");

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
    		SELECT_LIST_SQL.append("\n	SELECT * FROM ( ");
    		SELECT_LIST_SQL.append("\n		SELECT A.userid, to_char( (to_date(B.year||B.month, 'yyyymm')+ INTERVAL '1 MONTH - 1 day') , 'yyyymmdd') AS process_day, '0101' AS company_code, ");
    		SELECT_LIST_SQL.append("\n			'1000' AS business_code, '2000' AS department_code, '81200' AS category_code, use_purpose AS purpose, price, B.month||'월 경비 - '||username AS details, ");
    		SELECT_LIST_SQL.append("\n			to_char( (to_date(B.year||B.month, 'yyyymm')+ INTERVAL '1 MONTH + 19 day') , 'yyyymmdd') AS last_day, to_char(start_date,'yyyymmdd') AS use_date, 'pcard' AS bill_method ");
    		SELECT_LIST_SQL.append("\n		FROM dh_traffic_item A ");
    		SELECT_LIST_SQL.append("\n		INNER JOIN dh_spend_month B ON A.spend_month_idx = B.idx ");
    		SELECT_LIST_SQL.append("\n		WHERE B.year = :year and B.month = :month and B.userid in ("+reqTray.getString("uids")+") ");
    		SELECT_LIST_SQL.append("\n		UNION ALL ");
    		SELECT_LIST_SQL.append("\n		SELECT A.userid, to_char( (to_date(B.year||B.month, 'yyyymm')+ INTERVAL '1 MONTH - 1 day') , 'yyyymmdd') AS process_day, '0101' AS company_code, ");
    		SELECT_LIST_SQL.append("\n			'1000' AS business_code, '2000' AS department_code, C.real_code as category_code, use_purpose AS purpose, price, B.month||'월 경비 - '||username AS details, ");
    		SELECT_LIST_SQL.append("\n			to_char( (to_date(B.year||B.month, 'yyyymm')+ INTERVAL '1 MONTH + 19 day') , 'yyyymmdd') AS last_day, to_char(use_date,'yyyymmdd') AS use_date, bill_method ");
    		SELECT_LIST_SQL.append("\n		FROM dh_spend_item A ");
    		SELECT_LIST_SQL.append("\n		INNER JOIN dh_spend_month B ON A.spend_month_idx = B.idx ");
    		SELECT_LIST_SQL.append("\n		INNER JOIN dh_category C ON A.category_code = C.category_code ");
    		SELECT_LIST_SQL.append("\n		WHERE B.year = :year and B.month = :month and B.userid in ("+reqTray.getString("uids")+") ");
    		SELECT_LIST_SQL.append("\n	) TBL ");
    		SELECT_LIST_SQL.append("\n	ORDER BY userid ASC, use_date ASC ");
       	        	        	
        	QueryRunner runner = new QueryRunner(SELECT_LIST_SQL.toString());
        	runner.setParam("year", reqTray.getString("year"));
        	runner.setParam("month", reqTray.getString("month"));
        	
        	Log.debug("", this, "SELECT_LIST_SQL : \n" + runner.toString());
        	
            rsTray = (Tray)runner.query(conn);
        	return rsTray;	
		}catch (Exception ex){
			String methodName = "."+new Object(){}.getClass().getEnclosingMethod().getName()+"()";
            Log.error("ERROR", this, "at "+this.getClass().getName()+methodName+ex);
            ex.printStackTrace();
            throw new AppException(this.getClass().getSimpleName()+methodName+" Exception ", ex);
		}
	}
}
