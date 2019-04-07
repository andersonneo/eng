package com.eng.login.dao;

import java.sql.Connection;
import com.dhitech.framework.dao.BaseDao;
import com.dhitech.framework.exception.AppException;
import com.dhitech.framework.log.Log;
import com.dhitech.framework.sql.QueryRunner;
import com.dhitech.framework.tray.Tray;

public class loginDao extends BaseDao {

	/**
	 * 로그인 처리를 한다.
	 * @param conn
	 * @param reqTray
	 * @return
	 * @throws AppException
	 */
	public Tray find(Connection conn, Tray reqTray) throws AppException{
        try{
        	Tray rsTray = null;

        	StringBuffer userQuery = new StringBuffer();

        	userQuery.append("\n	select * from where user_id= :user_id AND  passwd = :user_pw ( ");
        	
        	QueryRunner runner = new QueryRunner(userQuery.toString());
        	runner.setParams(reqTray);
        	Log.debug("", this, "SELECT_USER_SQL : " + runner.toString());
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
