/**
 * 파일명: com.kyeryong.framework.tray.ResultSetTray.java
 * 파일개요: java.sql.ResultSet 정보를 캡슐화한 클래스
 * 저작권: Copyright (c) 2003 by SK C&C. All rights reserved.
 * 작성자: 박찬우 (nucha@dreamwiz.com)
 *
 * @version 1.1 2004-05-12 toHtml() method 작성 - 박찬우
 * @version 1.1 2004-05-03 toString() method 재작성
 */

package com.dhitech.framework.tray;

//Java API
import java.util.List;

public class ResultSetTray extends AbstractTray implements java.io.Serializable {
    //결과셋의 컬럼명 정보
    private String[] columnNames = null;

    //결과셋의 각 컬럼의 타입
    private String[] columnTypeNames = null;

    private int rowCount = 0;

    public ResultSetTray() {
    }

    public String toHtml() {
        if (getRowCount() <= 0) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("<table width='910' cellspacing='1' cellpadding='2' border='0' class='tab'>");
            buffer.append("<tr>");
            buffer.append("<td class='td_ce'>").append("조회된 내용이 없습니다.").append("</td>");
            buffer.append("</tr>");
            buffer.append("</table>");
            return buffer.toString();
        }

        int columnCount = this.getColumnNames().length;

        StringBuffer buffer = new StringBuffer();

        buffer.append("<table width='100%' cellspacing='1' cellpadding='2' border='0' class='tab'>");
        buffer.append("<tr>");

        //제목표시
        buffer.append("<td class='td_g_ce'>");
        buffer.append("NO");
        buffer.append("</td>");

        for (int i = 0; i < columnCount; i++) {
            String eachColumnName = columnNames[i];

            buffer.append("<td class='td_g_ce'>");
            buffer.append(eachColumnName);
            buffer.append("</td>");
        }
        buffer.append("</tr>");

        //내용표시
        List list = null;
        int recordCount = getRowCount();
        for (int i = 0; i < recordCount; i++) {
            buffer.append("<tr class='cursor' id='tr_" + i + "' bgcolor='#FFFFFF' onClick=\"chg_back(" + i + ",'#F1F8F4');location.href='#none';\">");
            buffer.append("<td class='td_ce'>").append(String.valueOf(i + 1)).append("</td>");
            for (int j = 0; j < columnCount; j++) {
                String eachColumnName = columnNames[j];
                list = (List) map.get(eachColumnName);

                buffer.append("<td class='td_ce'>").append(list.get(i).toString()).append("</td>");
            }
            buffer.append("</tr>");
        }

        buffer.append("</table>");

        return buffer.toString();
    }

    /**
     * 저장하고 있는 키와 데이터에 대한 요약을 문자열로 리턴한다.
     * @return - 저장된 키-값 쌍의 내용
     */
    public String toString() {
        int col_count = columnNames == null ? 0 : columnNames.length;
        int row_count = getRowCount();
        int row_max = 0;

        if (col_count == 0 || getRowCount() == 0) {
            return "조회된 내용이 없습니다.";
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append("조회건수: ").append(getRowCount()).append('\n');

        for (int i = 0; i < col_count; i++) {
            String col_name = columnNames[i];
            buffer.append(col_name).append('\t');
            row_max = Math.max(row_max, size(col_name));
        }
        buffer.append('\n');

        for (int i = 0; i < row_max; i++) {
            for (int j = 0; j < col_count; j++) {
                String col_name = columnNames[j];
                buffer.append(i > size(col_name) ? "{null}" : get(col_name, i));
                buffer.append("\t");
            }
            buffer.append('\n');
        }

        return buffer.toString();
    }

    /**
     * select 쿼리문에서 사용한 column명을 세팅한다.
     * @param columnNames - 컬럼명 배열
     */
    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    /**
     * select 쿼리문에서 사용한 column의 타입을 세팅한다.
     * @param columnTypeNames - 컬럼타입명 배열
     */
    public void setColumnTypeNames(String[] columnTypeNames) {
        this.columnTypeNames = columnTypeNames;
    }

    /**
     * 컬럼명 배열을 리턴한다.
     * @return - 컬럼명 배열
     */
    public String[] getColumnNames() {
        return columnNames;
    }

    /**
     * 컬럼타입명 배열을 리턴한다.
     * @return - 컬럼타입명 배열
     */
    public String[] getColumnTypeNames() {
        return columnTypeNames;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}
