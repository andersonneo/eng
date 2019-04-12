package com.eng.spend.accountsubject.dao;

import java.sql.Connection;

import com.eng.framework.dao.BaseDao;
import com.eng.framework.exception.AppException;
import com.eng.framework.log.Log;
import com.eng.framework.sql.QueryRunner;
import com.eng.framework.tray.Tray;

public class accountSubjectDao extends BaseDao{
	public Tray find(Connection conn, Tray reqTray) throws AppException{		
		try{
			Tray rsTray = null;
        	
        	StringBuffer SELECT_LIST_SQL = new StringBuffer();
    		SELECT_LIST_SQL.append("\n 	SELECT category_code as categorycode, category_name as categoryname ");
    		SELECT_LIST_SQL.append("\n 	FROM public.dh_category ");
    		SELECT_LIST_SQL.append("\n 	ORDER BY real_code ASC ");

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
}
