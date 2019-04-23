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
        	SELECT_BOARD_SQL.append("\n	SELECT idx,name,gender,email,etc1,etc2,etc3,etc4,etc5,etc6					");
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
        	        	
        	INSERT_SQL.append("\n	INSERT INTO eng_admin_board (idx, name, gender, email,etc1,etc2,etc3,etc4,etc5,etc6)");
        	INSERT_SQL.append("\n	VALUES (nextval('engseq'), :name , :gender, :email, :etc1 ,:etc2, :etc3, :etc4, :etc5, :etc6 )");
 	
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
	
	public boolean update(Connection conn, Tray reqTray) throws AppException{
		boolean result = true;
        try{
        	int cnt = 0;        	
        	
    	    	
    	    	StringBuffer UPDATE_ADMIN_BOARD_SQL = new StringBuffer();
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n	UPDATE eng_admin_board  SET					");
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n       name        = :name                                                                                  ");                                  
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n       ,gender      = :gender                                                                                ");    
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n       ,email        = :email                                                                                  ");                                  
            	UPDATE_ADMIN_BOARD_SQL.append("\n       ,etc1         = :etc1                                                                                ");                                    
            	UPDATE_ADMIN_BOARD_SQL.append("\n       ,etc2              = :etc2                                                                                         ");                                  
            	UPDATE_ADMIN_BOARD_SQL.append("\n       ,etc3              = :etc3                                                                                         ");                                  
            	UPDATE_ADMIN_BOARD_SQL.append("\n       ,etc4              = :etc4                                                                                         ");                                    
            	UPDATE_ADMIN_BOARD_SQL.append("\n       ,etc5              = :etc5                                                                                         ");                                    
            	UPDATE_ADMIN_BOARD_SQL.append("\n       ,etc6              = :etc6                                                                                         ");                                  
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n	WHERE idx = "+Integer.parseInt(reqTray.getString("idx"))					);
    	    	
    	    	
    	    	QueryRunner runner = new QueryRunner(UPDATE_ADMIN_BOARD_SQL.toString());
    	    	runner.setParams(reqTray);
    	    	Log.debug("", this, "UPDATE_ADMIN_BOARD_SQL	: " + runner.toString());
    	    	cnt = runner.update(conn);
            	if(cnt != 1){                
                    result = false;
            	}
            	return result;
        }catch (Exception ex){
            try{
                conn.rollback();
            }catch (Exception e){}
            Log.error("ERROR", this, "at UPDATE_ADMIN_BOARD_SQL" + ex);
            ex.printStackTrace();
            throw new AppException("UPDATE_ADMIN_BOARD_SQL Exception ", ex);
        }
    }
	
	public boolean delete(Connection conn, Tray reqTray) throws AppException{
		boolean result = true;
        try{
        	int cnt = 0;       	
        	
        	StringBuffer DELETE_ADMIN_BOARD_SQL = new StringBuffer();
        	DELETE_ADMIN_BOARD_SQL.append("\n	DELETE FROM eng_admin_board			");                            
        	DELETE_ADMIN_BOARD_SQL.append("\n	WHERE idx = "+Integer.parseInt(reqTray.getString("idx"))					);
        	
        	QueryRunner runner = new QueryRunner(DELETE_ADMIN_BOARD_SQL.toString());
        	
        	runner.setParams(reqTray);
        	Log.debug("", this, "DELETE_ADMIN_BOARD_SQL	: " + runner.toString());
        	cnt = runner.update(conn);
        	if(cnt != 1){                
                result = false;
        	}
        	return result;
        }catch(Exception ex){
            try{
                conn.rollback();
            }catch (Exception e){}
            Log.error("ERROR", this, "at DELETE_ADMIN_BOARD_SQL.delete()" + ex);
            ex.printStackTrace();
            throw new AppException("DELETE_ADMIN_BOARD_SQL.delete() Exception ", ex);
        }
    }

}
