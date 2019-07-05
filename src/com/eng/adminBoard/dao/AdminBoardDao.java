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
        	SELECT_BOARD_SQL.append("\n	SELECT *					");
        	SELECT_BOARD_SQL.append("\n	from eng_admin_board				");
        	Log.debug("SELECT_BOARD_SQL : " + runner.toString());
            runner = new QueryRunner(SELECT_BOARD_SQL.toString());
            rsTray = (Tray)runner.query(conn);
            arrayList.add(rsTray);
            
            StringBuffer SELECT_IDX_BOARD_SQL = new StringBuffer();
            SELECT_IDX_BOARD_SQL.append("\n	SELECT *					");
            SELECT_IDX_BOARD_SQL.append("\n	from eng_admin_board				");
            
            System.out.println("=============="+reqTray.getString("idx"));
            
            if(reqTray.getString("idx") == null||reqTray.getString("idx").equals("")){
            }else {
            	SELECT_IDX_BOARD_SQL.append("\n	WHERE idx = "+Integer.parseInt(reqTray.getString("idx"))					);
            }
        	Log.debug("SELECT_IDX_BOARD_SQL : " + runner.toString());
            runner = new QueryRunner(SELECT_IDX_BOARD_SQL.toString());
            if(reqTray.getString("idx") == null||reqTray.getString("idx").equals("")){
            }else {
            	
            	runner.setParams(reqTray);    
            }
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
        	        	
        	INSERT_SQL.append("\n	INSERT INTO eng_admin_board (");
        	INSERT_SQL.append("\n	idx,");
        	INSERT_SQL.append("\n	first_name, ");
        	INSERT_SQL.append("\n	last_name,");
        	INSERT_SQL.append("\n	gender, ");
        	INSERT_SQL.append("\n	email, ");
        	INSERT_SQL.append("\n	birth_day, ");
        	INSERT_SQL.append("\n	age, ");
        	INSERT_SQL.append("\n	height, ");
        	INSERT_SQL.append("\n	weight, ");
        	INSERT_SQL.append("\n	civil_status, ");
        	INSERT_SQL.append("\n	country_of_birth, ");
        	INSERT_SQL.append("\n	nationality, ");
        	INSERT_SQL.append("\n	occupation, ");
        	INSERT_SQL.append("\n	address_abroad_no_street, ");
        	INSERT_SQL.append("\n	address_abroad_city, ");
        	INSERT_SQL.append("\n	address_abroad_country_zip, ");
        	INSERT_SQL.append("\n	contact_no, ");
        	INSERT_SQL.append("\n	emergency_contact_no, ");
        	INSERT_SQL.append("\n	relationship_contact, ");
        	INSERT_SQL.append("\n	passport_no, ");
        	INSERT_SQL.append("\n	date_of_passport_expiration, ");
        	INSERT_SQL.append("\n	place_of_issue, ");
        	INSERT_SQL.append("\n	date_of_arrival, ");
        	INSERT_SQL.append("\n	flight_no_to_php, ");
        	INSERT_SQL.append("\n	date_of_visa, ");
        	INSERT_SQL.append("\n	profile_file ");
        	INSERT_SQL.append("\n	)");
        	INSERT_SQL.append("\n	VALUES (");
        	INSERT_SQL.append("\n	nextval('engseq'),");
        	INSERT_SQL.append("\n	:firstName, ");
        	INSERT_SQL.append("\n	:lastName, ");
        	INSERT_SQL.append("\n	:gender, ");
        	INSERT_SQL.append("\n	:eMail, ");
        	INSERT_SQL.append("\n	:birthDay, ");
        	INSERT_SQL.append("\n	:age, ");
        	INSERT_SQL.append("\n	:height, ");
        	INSERT_SQL.append("\n	:weight, ");
        	INSERT_SQL.append("\n	:civilStatus, ");
        	INSERT_SQL.append("\n	:countryBirth, ");
        	INSERT_SQL.append("\n	:nationality, ");
        	INSERT_SQL.append("\n	:occupation, ");
        	INSERT_SQL.append("\n	:street, ");
        	INSERT_SQL.append("\n	:prefecture, ");
        	INSERT_SQL.append("\n	:zipCode, ");
        	INSERT_SQL.append("\n	:contactNoCountry, ");
        	INSERT_SQL.append("\n	:emergencyContactNo, ");
        	INSERT_SQL.append("\n	:relationshipContact, ");
        	INSERT_SQL.append("\n	:passportNo, ");
        	INSERT_SQL.append("\n	:datePassportExpiration, ");
        	INSERT_SQL.append("\n	:placeOfIssue, ");
        	INSERT_SQL.append("\n	:dateArrival, ");
        	INSERT_SQL.append("\n	:flightNoPhilippines, ");
        	INSERT_SQL.append("\n	:visaExpiration, ");
        	INSERT_SQL.append("\n	:fileName ");
        	INSERT_SQL.append("\n	)");
 	
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
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      first_name       = :firstName,                                                                                  ");          
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      last_name               		= :lastName  ,                                                                                ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      gender               		= :gender ,                                                                                 ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      email               		= :eMail ,                                                                                 ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      birth_day               		= :birthDay    ,                                                                              ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      age               		= :age       ,                                                                           ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      height               		= :height   ,                                                                               ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      weight               		= :weight      ,                                                                            ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      civil_status               		= :civilStatus     ,                                                                             ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      country_of_birth               		= :countryBirth   ,                                                                               ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      nationality               		= :nationality      ,                                                                            ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      occupation               		= :occupation      ,                                                                            ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      address_abroad_no_street               		= :street     ,                                                                             ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      address_abroad_city               		= :prefecture    ,                                                                              ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      address_abroad_country_zip               		= :zipCode   ,                                                                               ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      contact_no               		= :contactNoCountry           ,                                                                       ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      emergency_contact_no               		= :emergencyContactNo     ,                                                                             ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      relationship_contact               		= :relationshipContact     ,                                                                             ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      passport_no               		= :passportNo                           ,                                                       ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      date_of_passport_expiration               		= :datePassportExpiration   ,                                                                               ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      place_of_issue               		= :placeOfIssue            ,                                                                      ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      date_of_arrival               		= :dateArrival              ,                                                                    ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      flight_no_to_php               		= :flightNoPhilippines         ,                                                                         ");      
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      date_of_visa                		= :visaExpiration          ,                                                                         ");        
    	    	UPDATE_ADMIN_BOARD_SQL.append("\n      profile_file                		= :fileName                                                                                   ");      
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
        	
        	System.out.println("===================="+Integer.parseInt(reqTray.getString("idx")));
        	
        	StringBuffer DELETE_ADMIN_BOARD_SQL = new StringBuffer();
        	DELETE_ADMIN_BOARD_SQL.append("\n	DELETE FROM eng_admin_board			");                            
        	DELETE_ADMIN_BOARD_SQL.append("\n	WHERE idx ="+Integer.parseInt(reqTray.getString("idx"))				);
        	
        	QueryRunner runner = new QueryRunner(DELETE_ADMIN_BOARD_SQL.toString());
        	
        	runner.setParams(reqTray);
        	
        	System.out.println(runner.toString());
        	
        	
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
