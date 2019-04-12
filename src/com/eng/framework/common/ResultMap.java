package com.eng.framework.common;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class ResultMap {
        /**
         * SQL 쿼리에 관한 결과셋을 포함한 ArrayList
         */
        private ArrayList results = null;

        /**
         * SQL 쿼리에 관한 컬럼 이름을 포함한 ArrayList
         */
        public ArrayList columnNames = null;

        /**
         * 컬럼 개수
         */
        private int columnCount = 0;

        /**
         * toString()에 의해 결정되는 각 필드의 너비
         */
        private int toStringFormatWidth = 12;

        /**
         * 현재 커서 위치
         */
        private int cursor = -1;

        /**
         * 결과셋 배열리스트와 컬럼카운트를 설정하는 생성자
         *
         * @param columnCount
         */
        public ResultMap(int columnCount) {
                results = new ArrayList();
                columnNames = new ArrayList();
                this.columnCount = columnCount;
        }

        /**
         * 커서를 다음위치로 이동
         *
         * @return 데이터가 없으면 false 리턴
         */
        public boolean next() {
                cursor++;
                return this.getRowCount() > cursor;
        }

        /**
         * 커서를 이전위치로 이동
         * @return 커서의 위치가 before first 이거나 0일때
         */
        public boolean previous() {

                if (cursor <= 0) return false;
                cursor--;
                return this.getRowCount() > cursor;
        }

        /**
         * 커서를 첫번째 위치로 이동
         */
        public void reset() {
                cursor = 0;
        }

        /** 
         * 커서가 처음 위치에 있는지를 체크
         *
         * @return
         */
        public boolean isFirst() {
                return cursor == 0;
        }

        /**
         * 커서를 처음 이전상태(-1)로 이동
         */
        public void beforeFirst() {
                cursor = -1;
        }

        /**
         * 현재 커서 위치를 리턴
         *
         * @return
         */
        public int getCurrentCursorPosition() {
                return this.cursor;
        }

        /**
         * SQL결과셋을 List 형태로 반환
         *
         * @return List
         */
        public List toList() {
                return (results);
        }

        /**
         * 스트링 포맷 너비 Setter
         *
         * @param toStringFormatWidth
         */
        public void setToStringFormatWidth(int toStringFormatWidth) {
                this.toStringFormatWidth = toStringFormatWidth;
        }

        /**
         * 객체 추가 (a field value from a SELECT) to the sql results container)
         *
         * @param o
         */
        public void add(Object o) {
                results.add(o);
        }

        /**
         * 쿼리에 의해 반환되는 로우 갯수
         *
         * @return number of rows
         */
        public int getRowCount() {
                int rowCnt = results.size() / columnCount;
                return (rowCnt);
        }

        /**
         * 로우와 컬럼 인덱스를 통해서 객체를 리턴 받음
         *
         * @param row
         * @param col
         * @return object
         */
        public Object getObject(int row, int col) {
                int index = (row * columnCount) + col;
                return (results.get(index));
        }

        /**
         * 현재 커서와 컬럼 인덱스를 통해서 객체를 리턴 받음
         *
         * @param col
         * @return object
         */
        public Object getObject(int col) {
                return getObject(cursor, col);
        }

        /**
         * 컬럼이름 리스트에 컬럼이름을 추가
         *
         * @param columnName columnName to add
         */
        public void addColumnName(String columnName) {
                columnNames.add(columnName);
        }

        /**
         * 컬럼이름으로 컬럼인덱스를 반환받음
         *
         * @param columnName
         * @return columnName corresponding to index
         */
        private int getColumnIndex(String columnName) throws Exception {
                int colIndex = -1;
                for (int i = 0; i < columnNames.size(); i++) {
                        String thisColumnName = (String) columnNames.get(i);
                        if (thisColumnName.equalsIgnoreCase(columnName)) {
                                colIndex = i;
                                break;
                        }
                }

                if (colIndex < 0)
                        throw new Exception("Invalid column name: " + columnName);
                return (colIndex);
        }

        /**
         * 해당로우와 컬럼이름으로 int 값을 반환
         *
         * @param row
         * @param columnName
         * @return int value of field
         */
        public int getInt(int row, String columnName) throws Exception {
                return (getInt(row, getColumnIndex(columnName)));
        }

        /**
         * 커서와 컬럼이름으로 int 값을 반환
         *
         * @param columnName
         * @return
         * @throws Exception
         */
        public int getInt(String columnName) throws Exception {
                return getInt(cursor, columnName);
        }

        /**
         * 해당로우와 컬럼 인덱스로 int 값을 반환
         *
         * @param row
         * @param col
         * @return int value of field
         */
        public int getInt(int row, int col) {
                if (isNull(row, col))
                        return (0);

                Object o = getObject(row, col);
                if (o instanceof Integer)
                        return (((Integer) o).intValue());
                else
                        return (((BigDecimal) o).intValue());
        }

        /**
         * 커서와 컬럼 인덱스로 int 값을 반환
         *
         * @param col
         * @return
         */
        public int getInt(int col) {
                return getInt(cursor, col);
        }

        /**
         * 해당로우와 컬럼이름으로 long 값을 반환
         *
         * @param row        int
         * @param columnName String
         * @return long value of field
         */
        public long getLong(int row, String columnName) throws Exception {
                return (getLong(row, getColumnIndex(columnName)));
        }

        /**
         * 커서와 컬럼이름으로 long 값을 반환
         *
         * @param columnName
         * @return
         * @throws Exception
         */
        public long getLong(String columnName) throws Exception {
                return getLong(cursor, columnName);
        }

        /**
         * 해당로우와 컬럼 인덱스로 long 값을 반환
         *
         * @param row
         * @param col
         * @return long
         */
        public long getLong(int row, int col) {
                if (isNull(row, col))
                        return (0);

                Object o = getObject(row, col);
                if (o instanceof Long)
                        return (((Long) o).longValue());
                else if (o instanceof Integer)
                        return (((Integer) o).longValue());
                else
                        return (((BigDecimal) o).longValue());
        }

        /**
         * 커서와 컬럼 인덱스로 long 값을 반환
         *
         * @param col
         * @return
         */
        public long getLong(int col) {
                return getLong(cursor, col);
        }

        /**
         * 해당로우와 컬럼 인덱스로 String 값을 반환
         *
         * @param row int
         * @param col int
         * @return String value of field
         */
        public String getString(int row, int col) {
                Object o = getObject(row, col);
                if (o == null) {
                        return "";
                } else if (o instanceof Clob) {
                        Clob lob = (Clob)o;
                        try {
                                return lob.getSubString(1, (int) lob.length());
                        } catch (SQLException e) {
                                return "Clob Error!";
                        }
                } else if (o instanceof BigDecimal) {
                        BigDecimal b = (BigDecimal) o;
                        return b.toString();
                } else if (o instanceof Integer)
                        return ("" + getInt(row, col));
                else if ((o instanceof Date) || (o instanceof Timestamp))
                        return ("" + getDate(row, col));
                else {
                        String s = (String) o;
                        return (TextUtil.replaceS(s));
                }
        }

        /**
         * 커서와 컬럼 인덱스로 String 값을 반환
         *
         * @param col
         * @return
         */
        public String getString(int col) {
                return getString(cursor, col);

        }

        /**
         * 해당로우와 컬럼이름으로 String 값을 반환
         *
         * @param row        int
         * @param columnName String
         * @return String value of field
         */
        public String getString(int row, String columnName) throws Exception {
                return (getString(row, getColumnIndex(columnName)));
        }

        /**
         * 커서와 컬럼이름으로 String 값을 반환
         *
         * @param columnName
         * @return
         * @throws Exception
         */
        public String getString(String columnName) throws Exception {
                return getString(cursor, columnName);
        }

        /**
         * 해당로우와 컬럼 인덱스로 boolean 값을 반환
         *
         * @param row int
         * @param col int
         * @return boolean value of field
         */
        public boolean getBoolean(int row, int col) {
                if (isNull(row, col))
                        return (false);

                Object o = getObject(row, col);
                Boolean b = (Boolean) o;
                return (b.booleanValue());
        }

        /**
         * 커서와 컬럼 인덱스로 boolean 값을 반환
         *
         * @param col
         * @return
         */
        public boolean getBoolean(int col) {
                return getBoolean(cursor, col);
        }

        /**
         * 해당로우와 컬럼이름으로 boolean 값을 반환
         *
         * @param row        int
         * @param columnName String
         * @return boolean value of field
         */
        public boolean getBoolean(int row, String columnName) throws Exception {
                return (getBoolean(row, getColumnIndex(columnName)));
        }

        /**
         * 커서와 컬럼이름으로 boolean 값을 반환
         *
         * @param columnName
         * @return
         * @throws Exception
         */
        public boolean getBoolean(String columnName) throws Exception {
                return getBoolean(cursor, columnName);
        }

        /**
         * 해당로우와 컬럼 인덱스로 java.sql.Date 값을 반환
         *
         * @param row int
         * @param col int
         * @return Date value of field
         */
        public Date getDate(int row, int col) {
                if (isNull(row, col))
                        return (null);

                Object o = getObject(row, col);
                if (o instanceof Timestamp) {
                        Timestamp t = (Timestamp) o;
                        Date d = new Date(t.getTime());
                        return (d);
                } else {
                        Date d = (Date) o;
                        return (d);
                }
        }

        /**
         * 커서와 컬럼 인덱스로 java.sql.Date 값을 반환
         *
         * @param col
         * @return
         */
        public Date getDate(int col) {
                return getDate(cursor, col);
        }

        /**
         * 해당로우와 컬럼이름으로 java.sql.Date 값을 반환
         *
         * @param row        int
         * @param columnName String
         * @return Date value of field
         */
        public Date getDate(int row, String columnName) throws Exception {
                return (getDate(row, getColumnIndex(columnName)));
        }

        /**
         * 커서와 컬럼이름으로 java.sql.Date 값을 반환
         *
         * @param columnName
         * @return
         * @throws Exception
         */
        public Date getDate(String columnName) throws Exception {
                return getDate(cursor, columnName);
        }

        /**
         * 해당로우와 컬럼 인덱스로 java.sql.Time값을 반환
         *
         * @param row int
         * @param col int
         * @return Time value of field
         */
        public Time getTime(int row, int col) {
                if (isNull(row, col))
                        return (null);

                Object o = getObject(row, col);
                Time t = (Time) o;
                return (t);
        }

        /**
         * 커서와 컬럼 인덱스로 java.sql.Time값을 반환
         *
         * @param col
         * @return
         */
        public Time getTime(int col) {
                return getTime(cursor, col);
        }

        /**
         * 해당로우와 컬럼이름으로 java.sql.Time값을 반환
         *
         * @param row        int
         * @param columnName String
         * @return Time value of field
         */
        public Time getTime(int row, String columnName) throws Exception {
                return (getTime(row, getColumnIndex(columnName)));
        }

        /**
         * 커서와 컬럼이름으로 java.sql.Time값을 반환
         *
         * @param columnName
         * @return
         * @throws Exception
         */
        public Time getTime(String columnName) throws Exception {
                return getTime(cursor, columnName);
        }

        /**
         * 해당로우와 컬럼 인덱스로 java.sql.Timestamp값을 반환
         *
         * @param row int
         * @param col int
         * @return Timestamp value of field
         */
        public Timestamp getTimestamp(int row, int col) {
                if (isNull(row, col))
                        return (null);

                Object o = getObject(row, col);
                if (o instanceof Date) {
                        Date d = (Date) o;
                        Timestamp t = new Timestamp(d.getTime());
                        return (t);
                } else {
                        Timestamp t = (Timestamp) o;
                        return (t);
                }
        }

        /**
         * 커서와 컬럼 인덱스로 java.sql.Timestamp값을 반환
         *
         * @param col
         * @return
         */
        public Timestamp getTimestamp(int col) {
                return getTimestamp(cursor, col);
        }

        /**
         * 해당로우와 컬럼이름으로 java.sql.Timestamp값을 반환
         *
         * @param row        int
         * @param columnName String
         * @return Timestamp value of field
         */
        public Timestamp getTimestamp(int row, String columnName) throws Exception {
                return (getTimestamp(row, getColumnIndex(columnName)));
        }

        /**
         * 커서와 컬럼이름으로 java.sql.Timestamp값을 반환
         *
         * @param columnName
         * @return
         * @throws Exception
         */
        public Timestamp getTimestamp(String columnName) throws Exception {
                return getTimestamp(cursor, columnName);
        }

        /**
         * 해당로우와 컬럼 인덱스로 double값을 반환
         *
         * @param row int
         * @param col int
         * @return double value of field
         */
        public double getDouble(int row, int col) {
                if (isNull(row, col))
                        return (0);

                Object o = getObject(row, col);
                if (o instanceof BigDecimal) {
                        BigDecimal b = (BigDecimal) o;
                        return (b.doubleValue());
                } else {
                        Double d = (Double) o;
                        return (d.doubleValue());
                }
        }

        /**
         * 커서와 컬럼 인덱스로 double값을 반환
         *
         * @param col
         * @return
         */
        public double getDouble(int col) {
                return getDouble(cursor, col);
        }

        /**
         * 해당로우와 컬럼이름으로 double값을 반환
         *
         * @param row        int
         * @param columnName String
         * @return double value of field
         */
        public double getDouble(int row, String columnName) throws Exception {
                return (getDouble(row, getColumnIndex(columnName)));
        }

        /**
         * 커서와 컬럼이름으로 double값을 반환
         *
         * @param columnName
         * @return
         * @throws Exception
         */
        public double getDouble(String columnName) throws Exception {
                return getDouble(cursor, columnName);
        }

        /**
         * 해당로우와 컬럼 인덱스로 float값을 반환
         *
         * @param row int
         * @param col int
         * @return float value of field
         */
        public double getFloat(int row, int col) {
                if (isNull(row, col))
                        return (0);

                Object o = getObject(row, col);
                if (o instanceof BigDecimal) {
                        BigDecimal b = (BigDecimal) o;
                        return (b.floatValue());
                } else {
                        Float f = (Float) o;
                        return (f.floatValue());
                }
        }

        /**
         * 커서와 컬럼 인덱스로 float값을 반환
         *
         * @param col
         * @return
         */
        public double getFloat(int col) {
                return getFloat(cursor, col);
        }

        /**
         * 해당로우와 컬럼이름으로 float값을 반환
         *
         * @param row        int
         * @param columnName String
         * @return float value of field
         */
        public double getFloat(int row, String columnName) throws Exception {
                return (getFloat(row, getColumnIndex(columnName)));
        }

        /**
         * 커서와 컬럼이름으로 float값을 반환
         *
         * @param columnName
         * @return
         * @throws Exception
         */
        public double getFloat(String columnName) throws Exception {
                return getFloat(cursor, columnName);
        }

        /**
         * 결과셋을 텍스트 테이블 형태로 보여줌
         *
         * @return String
         */
        public String toString() {
                if (columnCount < 1)
                        return (null);

                StringBuffer out = new StringBuffer("");
                for (int col = 0; col < columnCount; col++) {
                        String formattedColName =
                              formatWithSpaces((String) columnNames.get(col));
                        out.append(formattedColName);
                }

                out.deleteCharAt(out.length() - 2);
                int len = out.length();
                out.append("\n");
                for (int i = 0; i < len - 1; i++)
                        out.append("-");
                out.append("\n");

                for (int row = 0; row < getRowCount(); row++) {
                        for (int col = 0; col < columnCount; col++) {
                                String formattedColName = null;
                                if (isNull(row, col))
                                        formattedColName = formatWithSpaces("NULL");
                                else
                                        formattedColName = formatWithSpaces(getString(row, col));
                                out.append(formattedColName);
                        }
                        out.deleteCharAt(out.length() - 2);
                        out.append("\n");
                }

                return (out.toString());
        }

        /**
         * 필드명을 특정 너비에 맞춘다.(자르거나 공백을 덧붙임)
         *
         * @param s String
         * @return String
         */
        private String formatWithSpaces(String s) {
                StringBuffer sb = new StringBuffer(s);
                if (s.length() < toStringFormatWidth) {
                        for (int i = 0; i < toStringFormatWidth - s.length(); i++)
                                sb.append(" ");
                        return (sb.toString());
                } else {
                        return (sb.substring(0, toStringFormatWidth));
                }
        }

        /**
         * 특정필드(row,col) 가 널인지 검사
         *
         * @param row
         * @param col
         * @return true if null
         */
        public boolean isNull(int row, int col) {
                Object o = getObject(row, col);
                return (o == null);
        }

        /**
         * 특정 필드(row, columnName) 가 널인지 검사
         *
         * @param row
         * @param columnName
         * @return true if null
         */
        public boolean isNull(int row, String columnName) throws Exception {
                return (isNull(row, getColumnIndex(columnName)));
        }

        /**
         * 전체 레코드개수 getter
         *
         * @return 전체 레코드 개수
         */
        public int getTotalRows() throws Exception {
                return getTotalRows("total_rows");
        }

        /**
         * 전체 레코드 개수 반환
         *
         * @param name 토탈 로우의 이름
         * @return
         */
        public int getTotalRows(String name) throws Exception {
                if (this.getRowCount() == 0) return 0;

                String tr = this.getString(0, name);

                if (tr != null)
                        return (int) Double.parseDouble(tr);
                else
                        return 0;
        }
        public HashMap getSelectRow(String[] cols,int rownum) throws Exception {
            HashMap row=null;
            if(getRowCount()>rownum)
                row=new HashMap();
            for(int i=0;i<cols.length;i++)
            {
                String temp=null;
                try
                {
                	String tmp=getString(rownum, cols[i]);
                	temp=tmp;
                }
                catch(Exception ex)
                {
                }
                 row.put(cols[i], temp);
            }
            return row;
        }
        public ArrayList getcolumnNames()
        {
        	return this.columnNames; 
        }
        public String[] getKeys()
        {        	
        	return (String[])getcolumnNames().toArray();
        }

}

