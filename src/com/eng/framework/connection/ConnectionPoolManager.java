package com.eng.framework.connection;  

import java.sql.*;
import java.io.*;
import java.util.*;

import com.eng.framework.config.Config;
import com.eng.framework.config.ConfigFactory;
import com.eng.framework.consts.*;
import com.eng.framework.exception.PropNotFoundException;
import com.eng.framework.util.*;

public class ConnectionPoolManager {
    
    private static ConnectionPoolManager cpm;
    private ConnectionPool cp;
    public  String strFile = null;
    
    private static Config conf = null;
	static {
        try {
            conf = ConfigFactory.getInstance().getConfiguration("db.properties");
        } catch (PropNotFoundException pe) {
            System.err.println("Can't read the properties file. Make sure db.properties is in the CLASSPATH");
        } catch (Exception e) {
            System.err.println("find db.properties. but other problems are here.");
        }
    }
	
    private ConnectionPoolManager(String strServerName) {
        try {
            String driverName   = "oracle.jdbc.driver.OracleDriver";
            String url          = "";
            String user         = "";
            String password     = "";
    
            System.out.print("\n strServerName:" + strServerName);
            if (strServerName.equals(conf.getString("eng.servername"))) 
            {
//                url         = Constant.TYPEA_DBURL;
//                user        = Constant.TYPEA_ID;
//                password    = Constant.TYPEA_PASS;
            	url         = conf.getString("eng.url");
            	user        = conf.getString("eng.user");
            	password    = conf.getString("eng.password");
            } else {
                System.out.println("Not Server Type!!! Exception is occured : ");
            }

            int initialCons     = Constant.initialCons; // �ʱ� �� Ŀ�ؼǼ�
            int maxCons         = Constant.maxCons;     // �ƽ� �� Ŀ�ؼǼ�
            long timeout        = Constant.timeout;     
            boolean block       = Constant.block;       //�ƽ��� ���� wait����

            System.out.print("\n url:" + url);
            System.out.print("\n user:" + user);
            System.out.print("\n password:" + password);
            
            cp = new ConnectionPool(driverName, url, user, password, initialCons, maxCons, block, timeout);
            
            System.out.println("*** ConnectionPool is created...");
        } catch(Exception e) {
            System.out.println("!!! Exception is occured : " + e);
        }
    }
    
    public static ConnectionPoolManager getInstance(String strServerName) {
        if(cpm == null) {
            cpm = new ConnectionPoolManager(strServerName);
        }
        return cpm;
    }

    public Connection getConnection() throws Exception {
        return cp.getConnection();
    }

    public void releaseConnection(Connection conn) {
        try {
            cp.releaseConnection(conn);
        } catch(SQLException se) {
        }
    }
    
    protected void finalize() {
    }
}
