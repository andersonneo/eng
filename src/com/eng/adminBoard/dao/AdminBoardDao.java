package com.eng.adminBoard.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.eng.framework.exception.AppException;
import com.eng.framework.log.Log;
import com.eng.framework.sql.QueryRunner;
import com.eng.framework.tray.Tray;

public class AdminBoardDao {
	
	
	public Collection findAll(Connection conn, Tray reqTray) throws AppException{
        try{
        	Log.debug("", this, "reqTray.toString : \n" + reqTray.toString());
        	ArrayList arrayList = new ArrayList();

        	int count = 0;
        	Tray rsTray = null;

        	StringBuffer SELECT_COUNT_SERVER_SQL = new StringBuffer();

        	SELECT_COUNT_SERVER_SQL.append("\n		select COUNT(a.CHECK_DATE_FORM) as cnt                                                                                                        ");        	
        	SELECT_COUNT_SERVER_SQL.append("\n		            from                                                                                                                              ");

            Log.debug("", this, "SELECT_COUNT_SERVER_SQL : \n" + SELECT_COUNT_SERVER_SQL.toString());
            QueryRunner runner = new QueryRunner(SELECT_COUNT_SERVER_SQL.toString());
            rsTray = (Tray)runner.query(conn);

        	arrayList.add(rsTray);
        	
        	
        	//OS 쿼리
        	StringBuffer SELECT_CORP_SQL = new StringBuffer();
        	SELECT_CORP_SQL.append("\n	SELECT *					");
        	SELECT_CORP_SQL.append("\n	FROM pc_os				");
        	SELECT_CORP_SQL.append("\n	ORDER BY os_key	");
        	runner = new QueryRunner(SELECT_CORP_SQL.toString());
        	Log.debug("SELECT_CORP_SQL : " + runner.toString());
        	arrayList.add((Tray)runner.query(conn));    

        	return arrayList;
        }catch (Exception e){
            Log.error("ERROR", this, "at FullReportDao.findAll()" + e);
            e.printStackTrace();
            throw new AppException("FullReportDao.findAll() Exception ", e);
        }
    } 

}
