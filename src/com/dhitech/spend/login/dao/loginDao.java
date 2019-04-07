package com.dhitech.spend.login.dao;

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

        	userQuery.append("\n	WITH COMBINE_DEPT(DEPT_ID, PAR_DEPT_ID, DEPT_LEVEL, DEPT_NAMES, BUMUN, DEPT_NM_KR) AS ( ");
        	userQuery.append("\n		SELECT A.DEPT_ID, A.PAR_DEPT_ID, A.DEPT_LEVEL, CONVERT(varchar(255), A.DEPT_NM_KR) DEPT_NAMES, ");
        	userQuery.append("\n			CASE WHEN A.DEPT_LEVEL = 2 THEN CONVERT(varchar(255), A.DEPT_NM_KR) WHEN A.DEPT_LEVEL <> 2 THEN CONVERT(varchar(255), '') END BUMUN, A.DEPT_NM_KR ");
        	userQuery.append("\n		FROM [BX].[TCMG_DEPT] A	WHERE PAR_DEPT_ID = 0");
        	userQuery.append("\n		UNION ALL");
        	userQuery.append("\n		SELECT B.DEPT_ID, B.PAR_DEPT_ID,B.DEPT_LEVEL, CONVERT(varchar(255), C.DEPT_NAMES + ' > ' + B.DEPT_NM_KR) DEPT_NAMES, ");
        	userQuery.append("\n			CASE WHEN B.DEPT_LEVEL = 2 THEN CONVERT(varchar(255), B.DEPT_NM_KR) WHEN B.DEPT_LEVEL <> 2 THEN CONVERT(varchar(255), C.BUMUN) END BUMUN, B.DEPT_NM_KR ");
        	userQuery.append("\n		FROM [BX].[TCMG_DEPT] B, COMBINE_DEPT C ");
        	userQuery.append("\n		WHERE B.PAR_DEPT_ID = C.DEPT_ID ");
        	userQuery.append("\n	) ");
        	userQuery.append("\n	SELECT TOP 1 T3.DEPT_ID as dept_id,T3.DEPT_CD as dept_cd,T5.BUMUN as department,T5.DEPT_NM_KR as team,T4.LOGON_CD as userid,T4.LOGON_PW as userpw, ");
        	userQuery.append("\n		T4.USER_NM_KR as user_name,T6.GRADE_NM as user_grade,T6.DUTY_NM as user_duty FROM BX.TCMG_USERDEPT T1 ");
        	userQuery.append("\n	INNER JOIN BX.TCMG_CO T2 ON T1.CO_ID = T2.CO_ID ");
        	userQuery.append("\n	INNER JOIN BX.TCMG_DEPT T3 ON T1.DEPT_ID = T3.DEPT_ID ");
        	userQuery.append("\n	INNER JOIN BX.TCMG_USER T4 ON T1.USER_ID = T4.USER_ID AND T4.LOGON_CD = :user_id AND T4.logon_pw = :user_pw ");
        	userQuery.append("\n	INNER JOIN COMBINE_DEPT T5 ON T3.DEPT_ID = T5.DEPT_ID ");
        	userQuery.append("\n	INNER JOIN BX.TCMH_PERAPPOINTMENT T6 ON T1.USER_ID = T6.USER_ID ");
        	userQuery.append("\n	ORDER BY T6.created_dt DESC ");
        	
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
	
	public Tray findEx(Connection conn, Tray reqTray) throws AppException{
        try{
        	Tray rsTray = null;

        	StringBuffer userQuery = new StringBuffer();

        	userQuery.append("\n	WITH COMBINE_DEPT(DEPT_ID, PAR_DEPT_ID, DEPT_LEVEL, DEPT_NAMES, BUMUN, DEPT_NM_KR) AS ( ");
        	userQuery.append("\n		SELECT A.DEPT_ID, A.PAR_DEPT_ID, A.DEPT_LEVEL, CONVERT(varchar(255), A.DEPT_NM_KR) DEPT_NAMES, ");
        	userQuery.append("\n			CASE WHEN A.DEPT_LEVEL = 2 THEN CONVERT(varchar(255), A.DEPT_NM_KR) WHEN A.DEPT_LEVEL <> 2 THEN CONVERT(varchar(255), '') END BUMUN, A.DEPT_NM_KR ");
        	userQuery.append("\n		FROM [BX].[TCMG_DEPT] A	WHERE PAR_DEPT_ID = 0");
        	userQuery.append("\n		UNION ALL");
        	userQuery.append("\n		SELECT B.DEPT_ID, B.PAR_DEPT_ID,B.DEPT_LEVEL, CONVERT(varchar(255), C.DEPT_NAMES + ' > ' + B.DEPT_NM_KR) DEPT_NAMES, ");
        	userQuery.append("\n			CASE WHEN B.DEPT_LEVEL = 2 THEN CONVERT(varchar(255), B.DEPT_NM_KR) WHEN B.DEPT_LEVEL <> 2 THEN CONVERT(varchar(255), C.BUMUN) END BUMUN, B.DEPT_NM_KR ");
        	userQuery.append("\n		FROM [BX].[TCMG_DEPT] B, COMBINE_DEPT C ");
        	userQuery.append("\n		WHERE B.PAR_DEPT_ID = C.DEPT_ID ");
        	userQuery.append("\n	) ");
        	userQuery.append("\n	SELECT TOP 1 T3.DEPT_ID as dept_id,T3.DEPT_CD as dept_cd,T5.BUMUN as department,T5.DEPT_NM_KR as team,T4.LOGON_CD as userid,T4.LOGON_PW as userpw, ");
        	userQuery.append("\n		T4.USER_NM_KR as user_name,T6.GRADE_NM as user_grade,T6.DUTY_NM as user_duty FROM BX.TCMG_USERDEPT T1 ");
        	userQuery.append("\n	INNER JOIN BX.TCMG_CO T2 ON T1.CO_ID = T2.CO_ID ");
        	userQuery.append("\n	INNER JOIN BX.TCMG_DEPT T3 ON T1.DEPT_ID = T3.DEPT_ID ");
        	userQuery.append("\n	INNER JOIN BX.TCMG_USER T4 ON T1.USER_ID = T4.USER_ID AND T4.LOGON_CD = :user_id ");
        	userQuery.append("\n	INNER JOIN COMBINE_DEPT T5 ON T3.DEPT_ID = T5.DEPT_ID ");
        	userQuery.append("\n	INNER JOIN BX.TCMH_PERAPPOINTMENT T6 ON T1.USER_ID = T6.USER_ID ");
        	userQuery.append("\n	ORDER BY T6.created_dt DESC ");
        	
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
	
	public boolean insert(Connection conn, Tray reqTray) throws AppException{
		boolean result = true;
        try{      
        	int registUserCnt = 0;
        	int registMonthDateCnt = 0;
        	Tray tmpTray = null;
        	
        	StringBuffer SELECT_SQL = new StringBuffer();
        	SELECT_SQL.append("\n	select * from dh_user where userid = '"+reqTray.getString("user_id")+"' ");
        	QueryRunner runner = new QueryRunner(SELECT_SQL.toString());
        	Log.debug("", this, "SELECT_SQL : \n" + runner.toString());  
        	tmpTray = (Tray)runner.query(conn);
        	
        	if (tmpTray.getRowCount() == 0){
	        	StringBuffer INSERT_SQL = new StringBuffer();
	        	String roll = "사용자";
	        	//dh_user
	        	if (("BA02").equals(reqTray.getString("dept_cd"))){
	        		roll = "관리자";
	        	}	        	
	        	INSERT_SQL.append("\n	INSERT INTO dh_user(userid, username, roll, regdate) ");
	        	INSERT_SQL.append("\n		VALUES ('"+reqTray.getString("user_id")+"', '"+reqTray.getString("user_name")+"', '"+roll+"', now()); ");
	        	runner = new QueryRunner(INSERT_SQL.toString());        	 
	        	Log.debug("", this, "INSERT_SQL : \n" + runner.toString());  
	        	registUserCnt = runner.update(conn);
	        	
	        	//dh_traffic_item
	        	INSERT_SQL.setLength(0);
	        	INSERT_SQL.append("\n	INSERT INTO dh_spend_month (userid, year, month, company_card, credit_card, cash, create_date, update_date, finish_status, department)");
	        	INSERT_SQL.append("\n	VALUES ('"+reqTray.getString("user_id")+"', to_char( (now() - INTERVAL '1 MONTH') , 'YYYY'), to_char( (now() - INTERVAL '1 MONTH') , 'MM'),'0','0','0',now(),null,'w','"+reqTray.getString("departmemt")+"')");
				
				runner = new QueryRunner(INSERT_SQL.toString());        	 
	        	Log.debug("", this, "INSERT_SQL : \n" + runner.toString());  
	        	registMonthDateCnt = runner.update(conn);
        	}

        	if(registUserCnt < 1 || registMonthDateCnt < 1){
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
