package com.dhitech.spend.getduzon.dao;

import java.sql.Connection;

import com.dhitech.framework.dao.BaseDao;
import com.dhitech.framework.exception.AppException;
import com.dhitech.framework.log.Log;
import com.dhitech.framework.sql.QueryRunner;
import com.dhitech.framework.tray.Tray;

public class GetDuzonDao extends BaseDao{
	public Tray find(Connection conn) throws AppException{		
		try{
			Tray rsTray = null;
        	
        	StringBuffer SELECT_LIST_SQL = new StringBuffer();
    		SELECT_LIST_SQL.append("\n 	SELECT * FROM dz_user_info ");
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
