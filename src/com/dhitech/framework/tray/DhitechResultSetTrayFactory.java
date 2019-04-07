/**
 * 파일명: com.kyeryong.framework.tray.TubisResultSetTrayFactory.java
 * 파일개요: ResultSet에 대한 Tray를 생성하는 팩토리 클래스
 * 저작권: Copyright (c) 2003 by SK C&C. All rights reserved.
 * 작성자: 박찬우 (nucha@dreamwiz.com)
 */

package com.dhitech.framework.tray;

//Java API
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.sql.CLOB;

import com.dhitech.framework.exception.AppException;

/**
 * TubisResultSetTrayFactory 클래스는 java.sql.ResultSet 객체에 대한
 * 내용을 감싸는 Tray 객체를 생성하는 팩토리이다.
 * <p>
 * 사용법
 * <blockquote><pre>
 * ResultSet rs = pstmt.executeQuery();
 * ResultSetTrayFactory factory = new TubisResultSetTrayFactory();
 * Tray rsTray = factory.getTray(rs);
 * for (int i = 0; i < rsTray.getRowCount(); i++) {
 *     String name = rsTray.getString("name", i);
 *     int age = rsTray.getInt("age", i);
 * }
 * </pre></blockquote>
 * @author <b>박찬우</b>
 * @version 1.0, 2004/02/01
 */

public class DhitechResultSetTrayFactory extends ResultSetTrayFactory {
    //결과셋의 컬럼명 정보
    private String[] columnNames = null;

    //결과셋의 각 컬럼의 타입
    private String[] columnTypeNames = null;

    //테이블의 컬럼 명과 그 컬럼에 해당하는 실제 값들을 저장하는 맵
    //컬럼명을 키(String)로, 컬럼값들을 값(List)으로  매핑시켜 저장한다.
    //Map map = new HashMap();


    /**
     * 전달받은 ResultSet에서 필요한 내용을 꺼내어 ResultSetTray 객체를 생성한 후 리턴.
     * @param rs - java.sql.ResultSet
     * @return - ResultSetTray
     */
    protected Tray createTray(ResultSet rs) {
        ResultSetTray tray = new ResultSetTray();

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            setColumnNames(rsmd, tray);
            setColumnTypeNames(rsmd, tray);
            setColumnValues(rs, tray);
        } catch (SQLException ex) {
            throw new AppException("TubisResultSetTrayFactory.createTray()", ex);
        }

        return tray;
    }

    /**
     * ResultSetMeta에서 컬럼명을 얻어 ResultSetTray에 저장한다.
     * @param rsmd
     * @param tray
     * @throws java.sql.SQLException
     */
    private void setColumnNames(ResultSetMetaData rsmd, ResultSetTray tray) throws java.sql.SQLException {
        int columnCount = rsmd.getColumnCount();
        this.columnNames = new String[columnCount];

        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = rsmd.getColumnName(i + 1).toLowerCase();
        }

        tray.setColumnNames(columnNames);
    }

    /**
     * ResultSetMeta에서 컬럼타입을 얻어 ResultSetTray에 저장한다.
     * @param rsmd
     * @param tray
     * @throws java.sql.SQLException
     */
    private void setColumnTypeNames(ResultSetMetaData rsmd, ResultSetTray tray) throws java.sql.SQLException {
        int columnCount = rsmd.getColumnCount();
        String[] columnTypeNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnTypeNames[i] = rsmd.getColumnTypeName(i + 1);
        }

        tray.setColumnTypeNames(columnTypeNames);
    }

    /**
     * 컬럼값들을 저장할 List 배열을 생성한다.
     * @param rsmd
     * @param tray
     * @throws java.sql.SQLException
     */
    private List[] getEmptyList(int columnCount) {
        List[] columnValues = new List[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnValues[i] = new ArrayList();
        }
        return columnValues;
    }

    /**
     * 컬럼값들을 ResultSetTray에 저장한다.
     * @param rs
     * @param tray
     * @throws java.sql.SQLException
     */
    private void setColumnValues(java.sql.ResultSet rs, ResultSetTray tray) throws java.sql.SQLException {
        int columnCount = tray.getColumnNames().length;
        List[] columnValues = null;

        Object obj = null;
        int row_cnt = 0;
        while (rs.next()) {
            if (columnValues == null) {
                columnValues = getEmptyList(columnCount);
            }

            for (int i = 0; i < columnCount; i++) {
                obj = rs.getObject(i + 1);
                if (obj == null) {
                    columnValues[i].add("");
                    continue;
                }

                if(obj.getClass().toString().indexOf("oracle.sql.CLOB") > -1){
             	   try {
             		   columnValues[i].add(com.dhitech.framework.util.SpmUtil.readClobData(rs.getCharacterStream(i+1)));
             	   } catch (Exception e) {
 					// TODO Auto-generated catch block
             		   e.printStackTrace();
             	   }
                } else if(obj instanceof byte[]){
              	   columnValues[i].add(new String((byte[])obj));
                } else{
             	   columnValues[i].add(obj.toString());
                }
            }

            row_cnt++;
        }

        if (columnValues != null) {
            for (int i = 0; i < columnCount; i++) {
                tray.set(tray.getColumnNames()[i], columnValues[i]);
            }
        }

        tray.setRowCount(row_cnt);
    }
}
