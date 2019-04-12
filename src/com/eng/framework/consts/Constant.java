package com.eng.framework.consts;

import com.eng.framework.config.Config;
import com.eng.framework.config.ConfigFactory;
import com.eng.framework.exception.PropNotFoundException;

public class Constant {
	
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
	
    final static public String  CSVPath             = conf.getString("xls.dir");    
    final static public String  CSVName             = conf.getString("xls.filename");    
    final static public String  CSVFFName           = Constant.CSVPath + Constant.CSVName;    
    final static public String  Deli                = ",";
    
    final static public boolean DEBUG_MODE          = true;
    
    final static public String  SELECT_ALL          = "0";
    final static public String  SELECT_NEW          = "1";
    final static public String  SELECT_CHG          = "2";
    
    final static public String  mstrDataTypeByte    = "BYTE";
    final static public String  mstrDataTypeInt     = "INT";


   
    final static public int initialCons             = 1;           // DB POOL �ʽ� �� Ŀ�ؼǼ� 
    final static public int maxCons                 = 100;         // DB POOL MAX �� Ŀ�ؼǼ�
    final static public long timeout                = 1000L;        
    final static public boolean block               = true;         // Ŀ�ؼ��� Ǯ�� ���� ������ ���� Ŀ�ؼƼ��� ��涧���� wait()����
}
