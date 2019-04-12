package com.eng.login.dao;

import java.sql.Connection;

import com.eng.framework.dao.BaseDao;
import com.eng.framework.exception.AppException;
import com.eng.framework.log.Log;
import com.eng.framework.sql.QueryRunner;
import com.eng.framework.tray.Tray;

public class LoginDao extends BaseDao {

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

        	userQuery.append("\n	select * from eng_user where userid= :user_id AND  passwd = :user_pw  ");
        	
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
