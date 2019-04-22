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

        	StringBuffer SELECT_COUNT_BOARD_SQL = new StringBuffer();

        	//
        	SELECT_COUNT_BOARD_SQL.append("\n		select count(*) as cnt from eng_admin_board                                                                                                        ");        	

            Log.debug("", this, "SELECT_COUNT_SERVER_SQL : \n" + SELECT_COUNT_BOARD_SQL.toString());
            QueryRunner runner = new QueryRunner(SELECT_COUNT_BOARD_SQL.toString());
            rsTray = (Tray)runner.query(conn);

        	Log.debug("SELECT_COUNT_BOARD_SQL : " + runner.toString());
        	arrayList.add(rsTray);
        	
        	
        	//
        	StringBuffer SELECT_BOARD_SQL = new StringBuffer();
        	SELECT_BOARD_SQL.append("\n	SELECT name,gender,email					");
        	SELECT_BOARD_SQL.append("\n	from eng_admin_board				");
        	Log.debug("SELECT_BOARD_SQL : " + runner.toString());
            runner = new QueryRunner(SELECT_BOARD_SQL.toString());
            
            
            rsTray = (Tray)runner.query(conn);
            
            
        	arrayList.add(rsTray);    

        	return arrayList;
        }catch (Exception e){
            Log.error("ERROR", this, "at FullReportDao.findAll()" + e);
            e.printStackTrace();
            throw new AppException("FullReportDao.findAll() Exception ", e);
        }
    } 
	
	//등록
	public boolean insert(Connection conn, Tray reqTray) throws AppException{
		boolean result = true;
        try{
        	int cnt = 0;
        	Tray rsTray = null; 
        	
        	StringBuffer INSERT_SQL = new StringBuffer();
        	        	
        	INSERT_SQL.append("\n	INSERT INTO eng_admin_board (name, gender, email)");
        	INSERT_SQL.append("\n	VALUES (:name , :gender, :email )");
 	
        	QueryRunner runner = new QueryRunner(INSERT_SQL.toString());        	 
        	Log.debug("", this, "INSERT_SQL : \n" + runner.toString());  
        	runner.setParams(reqTray);
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

}
