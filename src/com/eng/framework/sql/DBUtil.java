/**
 * 파일명: DBUtil.java
 * 작성일: 2004. 2. 11.
 * 작성자: 진헌규(hkjin@daou.co.kr)
 */
package com.eng.framework.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * DB 개발과정에서 자주 사용되는 기능을 담고있는 유틸리티 클래스.
 *
 * @author 진헌규
 * @version 2004. 2. 11.
 */
public class DBUtil {
    /**
     * <code>java.sql.ResultSet</code>을 받아 메타정보를 출력해준다.
     * 개발과정에서 디버깅 용도로 사용.
     *
     * @param rs
     * @throws SQLException
     */
    public static void dumpResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int cnt = rsmd.getColumnCount();
        for (int i = 1; i <= cnt; i++) {
            System.out.println("Column #" + i + ": " + rsmd.getColumnName(i));
        }
    }

    /**
     * 주어진 DB connection을 close한다.
     *
     * @param conn
     */
    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception ex) {
        }
    }
}
