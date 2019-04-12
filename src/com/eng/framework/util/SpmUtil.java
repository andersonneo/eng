package com.eng.framework.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import oracle.sql.CLOB;

import com.eng.framework.config.Config;
import com.eng.framework.config.ConfigFactory;
import com.eng.framework.connection.DBManager;
import com.eng.framework.log.Log;
import com.eng.framework.sql.QueryRunner;
import com.eng.framework.tray.NewTray;
import com.eng.framework.tray.Tray;

public class SpmUtil {
	
	/**
	 * @param conn
	 * @param reqTray
	 * @return
	 * @throws SQLException
	 */
	public boolean CorporationAdminCheck(Connection conn, Tray reqTray) throws SQLException{
		boolean chk = false;
		try{
			StringBuffer SELECT_PC_SERVER_MANAGER_SQL = new StringBuffer();
			SELECT_PC_SERVER_MANAGER_SQL.append("\n	SELECT * FROM pc_server_manager	");
			SELECT_PC_SERVER_MANAGER_SQL.append("\n		WHERE user_key = :user_key	");
			QueryRunner runner = new QueryRunner(SELECT_PC_SERVER_MANAGER_SQL.toString());
			runner.setParams(reqTray);
			Log.debug("", this, "SELECT_PC_SERVER_MANAGER_SQL : " + runner.toString());
			Tray rsTray = (Tray)runner.query(conn);
			
			if(rsTray.getRowCount() == 0){
				chk = true;
			}
		}catch (Exception e) {
			// TODO: handle exception
			Log.error(e);
		}finally{

		}
		return chk;
	}
	
	public static String RemoveXss(String str) {
		//String str_low = "";
		
		// HTML tag를 모두 제거
		str = str.replaceAll("<","&lt;");
		str = str.replaceAll(">","&gt;");
		
		// 특수 문자 제거
		//str = str.replaceAll("\"","&gt;");
		//str = str.replaceAll("&", "&amp;");
		//str = str.replaceAll("%00", null);
		str = str.replaceAll("\"", "&#34;");
		str = str.replaceAll("\'", "&#39;");
		//str = str.replaceAll("%", "&#37;");
		//str = str.replaceAll("../", "");
		//str = str.replaceAll("..\\\\", "");
		//str = str.replaceAll("./", "");
		//str = str.replaceAll("%2F", "");
		
		// 허용할 HTML tag만 변경
		//str = str.replaceAll("&lt;p&gt;", "<p>");
		//str = str.replaceAll("&lt;P&gt;", "<P>");
		//str = str.replaceAll("&lt;br&gt;", "<br>");
		//str = str.replaceAll("&lt;BR&gt;", "<BR>");
		return str;
	}
	
	/*
	 * Tray 諛곗뿴�먯꽌 �섎굹��Tray濡�留뚮벉 
	 * 
	 * for(int i=0; i < poscoApproveTray.getRowCount();i++){
	 *		Tray tmpTray = new NewTray();
	 *		for (int j=0; j<poscoApproveTray.getKeys().length; j++){
	 *			tmpTray.setString(poscoApproveTray.getKeys()[j], poscoApproveTray.getString(poscoApproveTray.getKeys()[j], i));
	 *		}
	 *	}
	 * @param inputTray
	 * @return
	 * @throws Exception
	 */
	public static Tray trayToTray(Tray inputTray, int i) {
		Tray tempTray = new NewTray();
		try{
			for (int j=0; j<inputTray.getKeys().length; j++){
				tempTray.setString(inputTray.getKeys()[j], inputTray.getString(inputTray.getKeys()[j], i));
			}
		}catch (Exception e) {
			// TODO: handle exception
			Log.error(e);
		}
		
		return tempTray;
	}
	
	public Tray trayToTray2(Tray inputTray, int i) {
		Tray tempTray = new NewTray();
		try{
			for (int j=0; j<inputTray.getKeys().length; j++){
				tempTray.setString(inputTray.getKeys()[j], inputTray.getString(inputTray.getKeys()[j], i));
			}
		}catch (Exception e) {
			// TODO: handle exception
			Log.error(e);
		}
		
		return tempTray;
	}
	
	/* �섏씠吏��뚯슦湲�
	 * @param subQuery
	 * @param stratRow
	 * @param endRow
	 * @return
	 * @throws Exception
	 */
	public static String PageingQuery(String subQuery, int stratRow, int endRow) throws Exception{
		
		String query = "";
//      query = "\n	SELECT * "
//              +"\n	FROM (SELECT ROWNUM rn, c.* "
//              +"\n	FROM (SELECT ROWNUM, b.* "
//              +"\n	FROM (";

      query += subQuery;

//      query += "\n	) b) c "
//              +"\n	WHERE ROWNUM <= "+endRow+") "
//              +"\n	WHERE rn > "+stratRow;
      
      query += " limit " + stratRow + ", " + endRow;

      return query;
    }
	
	/* �꾨줈�쇳떚瑜�諛곗뿴濡�媛��怨��ㅼ옄
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static String[] getProperties(String name) throws Exception{
		String[] value = null;
		Config conf = ConfigFactory.getInstance().getConfiguration("base.properties");
		if(conf.getString(name) == null){
			value = new String[0];
		}else{
			value = conf.getStringArray(name);
		}
		return value;
	}
	
	/* �꾨줈�쇳떚瑜�臾몄옄濡�媛��怨��ㅼ옄
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static String getProperty(String name) throws Exception{
		String value = null;
		Config conf = ConfigFactory.getInstance().getConfiguration("base.properties");

		value = conf.getString(name);
		
		return value;
	}
	
	/*
	 * �좎쭨蹂�솚
	 */
	public static String dateCastStringType(Date date, String pattern) throws Exception{
		String reDate = "";
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			reDate = sdf.format(date); 
		}catch (Exception e) {
			// TODO: handle exception
		}
		return reDate;
	}
	
	
	@SuppressWarnings("deprecation")
	public static void saveClob(String str, CLOB clob) throws Exception{
        Writer writer = clob.getCharacterOutputStream();
        Reader src = new CharArrayReader(str.toCharArray());
        char[] buffer = new char[1024];
        int read = 0;
        while ( (read = src.read(buffer)) != -1) {
            writer.write(buffer, 0, read); // write clob.
        }
        src.close();
        writer.close();
    }
	
	
	
	public static String readClobData(Reader reader) throws IOException {
        StringBuffer data = new StringBuffer();
        try{
        	char[] buf = new char[1024];
        	int cnt = 0;
        	if (null != reader) {
        		while((cnt=reader.read(buf))!= -1){
        			data.append(buf, 0, cnt);
        		}
        	}
        }catch (Exception e) {
			// TODO: handle exception
        	//System.out.println(e);
        	Log.error(e);
		}finally{
			reader.close();
		}
        return data.toString();
    }
	
	
	/* 1. CLOB��STRING�쇰줈 �쎌뼱�⑤떎 
	 * 2. 媛믪씠 �덉쑝硫�value return
	 * 3. 媛믪씠 �놁쑝硫��ㅼ쓬 移쇰읆��泥댄겕 �쒕떎
	 * 
	 * @param ColumnNames
	 * @param values
	 * @param index
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String selectClobData(String[] ColumnNames, int index, java.sql.ResultSet rs) throws Exception{
		String value = "";
		
		value = readClobData(rs.getCharacterStream(index+1)); //�곗꽑 read�쒕떎
		
		if(value.equals("")){
			if((index+1) <= ColumnNames.length){ //NEXT CloumnName Check
				if(ColumnNames[index+1].equals("save_clob")){ //NEXT CloumnName Name Check
					saveClob(rs.getString(index+2), (CLOB)rs.getObject(index+1));
				}
			}
		}
		return value;
	}
	
	/*
	 * �섎굹���몃젅���ㅺ컪��諛곗뿴濡�蹂�솚
	 */
	public static String[] TraytoStringAll(Tray reqTray, String key){
		String[] stringAll = new String[reqTray.getRowCount()];
		
		for(int i=0; i < reqTray.getRowCount(); i++){
			stringAll[i] = reqTray.getString(key);
		}
		
		return stringAll;
	}
	
	
	/* URL 泥댄겕 留덉�留�.jsp��泥댄겕 �덊븳��..
	 * @param reqUrl
	 * @param userTray
	 * @return
	 */
	public static boolean checkUrl(String reqUrl, Tray menuTray){
		boolean result = false;
		if(reqUrl.indexOf("login") > -1 || reqUrl.indexOf("logout") > -1 || reqUrl.indexOf("index.jsp") > -1 || reqUrl.indexOf("/include/") > -1){
			return true;
		}
		if(reqUrl.indexOf("/") > -1){
			if(reqUrl.split("/").length > 3){
				reqUrl = reqUrl.substring(0, reqUrl.lastIndexOf("/"));
				for(int i=0; i<menuTray.getRowCount(); i++){
					if(menuTray.getString("url_address",i).indexOf(reqUrl) > -1){
						result =true;
						break;
					}
				}
			}
		}
		return result;
	}
	
	/* �ㅼ젙�쒓컙怨��뚯씪�ъ씠利덈� 泥댄겕�섏뿬 server Tray��媛�蹂�꼍. 
	 * @param server
	 * @throws Exception
	 */
	public static void checkValue(Tray server) throws Exception{
		//�ㅼ젙�쒓컙 泥댄겕
		if(server.getString("mcmd_limit_time").equals("") || server.getInt("mcmd_limit_time") == 0){
			server.clear("mcmd_limit_time");
			server.setString("mcmd_limit_time",getProperty("mcmd.limitTime"));
		}
		
		if(server.getString("mcmd_file_size").equals("") || server.getInt("mcmd_file_size") == 0){
			server.clear("mcmd_file_size");
			server.setString("mcmd_file_size",getProperty("mcmd.filesize"));
		}
	}
	
	public static String calcDay(int day, String regex){
		String date = "";
		
		FormatCalendar yesterday = new FormatCalendar();
        yesterday.add(FormatCalendar.DAY_OF_YEAR, day);
        date = yesterday.format(regex);
		
		return date;
	}
	
}
