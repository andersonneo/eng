package com.dhitech.framework.util;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import oracle.sql.CLOB;

public class oracleUtil {
	
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
        	System.out.println(e);
		}finally{
			reader.close();
		}
        return data.toString();
    }

}
