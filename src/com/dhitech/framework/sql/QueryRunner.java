package com.dhitech.framework.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dhitech.framework.exception.AppException;
import com.dhitech.framework.log.Log;
import com.dhitech.framework.tray.Tray;

/**
 * 한개의 SQL문에 대해 생성되고, 그 SQL문의 실행을 책임지며, 로그기록을 돕는 클래스.
 * 패러미터 세팅에 드는 공수를 최소화하기 위해
 * <pre>SELECT empno, empname FROM emp WHERE empno = :empno</pre>와 같이
 * 콜론 표기법으로 SQL문을 표기하고, {@link com.dhitech.framework.tray.kyeryong.framework.tray.Tray}를 인수로 받아
 * 바인드변수값을 한번에 대체한다.
 *
 * 쿼리 실행시에는 {@link #query} 메쏘드(SELECT의 경우)와 {@link #update}(그외의 경우)를
 * 사용할 수 있으며, {@link #query} 메쏘드 사용시 {@link ResultConverter} 인스턴스를 함께
 * 전달하여 <code>java.sql.ResultSet</code>의 내용을 담는 다른 클래스를 생성할 수 있다.
 *
 * @author 진헌규(hkjin@daou.co.kr)
 * @version 1.1	문자열 중간의 ':'도 바인드변수로 인식하는 버그 해결
 * @version 1.0 2003.12.20
 */
public class QueryRunner {

    /**
     * <code>java.sql.Statement</code>를 닫는데 사용되는 내부 메쏘드
     * @param stmt 닫고자 하는 Statement 인스턴스
     */
    private static void closeStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
        }
    }

    /**
     * <code>java.sql.ResultSet</code>를 닫는데 사용되는 내부 메쏘드
     * @param rs	닫고자 하는 ResultSet 인스턴스
     */
    private static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
        }

    }

    /**
     * 이 클래스의 인스턴스가 관리하는 SQL 문자열
     */
    private String sql;

    /**
     * SQL 문자열에 포함된 바인드 변수의 갯수
     */
    private int paramCount;

    /**
     * colon notation(ex. :emp_id)을 사용할 경우, 바인드 변수의 이름을 저장하는 리스트
     */
    private List paramNames;

    /**
     * 바인드 변수에 할당될 값을 저장하는 배열. 값은 문자열과 DATE 형태만 지원한다.
     */
    private Object[] paramValues;

    /**
     * 기본 fetch 사이즈.
     */
    protected static final int DEFAULT_FETCH_SIZE = 30;

    /**
     * 주어진 SQL문자열로 객체를 생성한다.
     * SQL 문자열은 '?'를 포함하거나 바인딩변수(':name')를 포함할 수 있다.
     *
     * @param sql	실행시킬 SQL 문자열
     */
    public QueryRunner(String sql) {
        StringBuffer buf = new StringBuffer(sql.length());
        StringBuffer paramName = new StringBuffer();

        int length = sql.length();
        boolean partOfParamName = false;
        boolean partOfString = false;
        for (int i = 0; i < length; i++) {
            char c = sql.charAt(i);

            // :empname 형태의 바인드변수 처리
            if (partOfParamName) {
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_') {
                    // 변수명이 계속되고 있는 경우: 변수명 버퍼에 추가.
                    paramName.append(c);
                } else {
                    // 변수명이 끝난 경우: 완성된 변수명을 변수명 배열에 추가.
                    paramNames.add(paramName.toString());
                    paramName.delete(0, paramName.length());
                    buf.append(c);
                    partOfParamName = false;
                }
            } else {
                switch (c) {
                // 바인드변수의 시작: '?'로 치환
                case ':':

                    // 문자열 중간의 ':'라면 무시
                    if (partOfString) {
                        break;
                    }
                    if (paramNames == null) {
                        paramNames = new ArrayList();
                    }
                    paramCount++;
                    partOfParamName = true;
                    c = '?';
                    break;

                    // 문자열의 시작/종료
                case '\'':
                    partOfString = !partOfString;
                    break;

                    // 바인드변수(JDBC 표준): 문자열의 일부가 아니라면 변수로 간주
                case '?':
                    if (!partOfString) {
                        paramCount++;
                    }
                    break;
                }
                buf.append(c);
            }
        }

        // 변수명이 완결되지 않은 상태에서 SQL이 종료한 경우: 변수명을 배열에 추가.
        if (partOfParamName) {
            paramNames.add(paramName.toString());
        }

        // 멤버 변수 할당
        this.sql = buf.toString();
        paramValues = new Object[paramCount];
    }

    /**
     * 주어진 SQL문자열과 Tray로 객체를 생성한다.
     *
     * @param sql		실행시킬 SQL 문자열
     * @param paramTray	SQL문에 전달될 패러미터값을 담고 있는 Tray
     */
    public QueryRunner(String sql, Tray paramTray) {
        this(sql);
        setParams(paramTray);
    }

    /**
     * SQL문에 전달될 인수값을 설정한다.
     *
     * @param paramValues
     * @deprecated '?' 표기법의 SQL을 사용할 경우 지원되는 메쏘드로, 가급적 Tray 사용을 권장
     */
    public void setParams(String[] paramValues) {
        System.arraycopy(paramValues, 0, this.paramValues, 0, paramValues.length);
    }

    /**
     * SQL문에 전달될 인수값을 설정한다.
     * Tray 내에 들어있는 key-value 매핑을 따라 Tray가 담고 있는 모든 값을 세팅.
     *
     * @param paramTray	SQL문에 전달될 패러미터값을 담고 있는 {@link com.dhitech.framework.tray.kyeryong.framework.tray.Tray}
     */
    public void setParams(Tray paramTray) {
        for (int i = 0; i < paramCount; i++) {
            paramValues[i] = paramTray.getString((String) paramNames.get(i));
        }
    }

    /**
     * SQL문에 전달될 인수값을 설정한다.
     *
     * @param paramName
     * @param paramValue
     */
    public void setParam(String paramName, Object paramValue) {
        int index = paramNames.indexOf(paramName);
        for (int i = 0; i < paramValues.length; i++) {
            if (paramNames.get(i).equals(paramName)) {
                paramValues[i] = paramValue;
            }
        }
    }

    /**
     * 인스턴스 내에 설정된 모든 바인드변수의 값을 null로 초기화한다.
     *
     */
    public void clearParams() {
        for (int i = 0; i < paramValues.length; i++) {
            paramValues[i] = null;
        }
    }

    /**
     * SQL문에 포함된 바인드변수의 갯수를 돌려준다.
     *
     * @return	SQL문에 포함된 바인드변수의 갯수
     */
    public int getParamCount() {
        return paramCount;
    }

    /**
     * SQL문에 포함된 바인드변수의 이름 배열을 돌려준다.
     *
     * @return SQL문에 포함된 바인드변수의 이름 배열
     */
    public String[] getParamNames() {
        return (String[]) paramNames.toArray(new String[0]);
    }

    /**
     * SQL문에 포함된 바인드변수의 이름 배열을 돌려준다.
     *
     * @return SQL문에 포함된 바인드변수의 이름 배열
     */
    public Object[] getParamValues() {
        return paramValues;
    }

    /**
     * SELECT 쿼리문을 실행하고, 그 결과를 {@link ResultConverter}를 이용하여
     * 변환된 클래스의 형태로 리턴한다.
     *
     * @param conn	<code>java.sql.Connection</code>
     * @return
     * @throws AppException
     */
    public Object query(Connection conn) throws AppException {
        return query(conn, ResultConverter.ResultSetTray);
    }

    /**
     * SELECT 쿼리문을 실행하고, 그 결과를 <code>ResultConverter</code>를 이용하여
     * 변환된 클래스의 형태로 리턴한다.
     *
     * @param conn	<code>java.sql.Connection</code>
     * @param converter <code>ResultConverter</code>
     * @return
     * @throws AppException
     */
    public Object query(Connection conn, ResultConverter converter) throws AppException {
        return query(conn, converter, null);
    }

    /**
     * SELECT 쿼리문을 실행하고, 그 결과를 <code>ResultConverter</code>를 이용하여
     * 변환된 클래스의 형태로 리턴한다.
     *
     * @param conn	<code>java.sql.Connection</code>
     * @param converter <code>ResultConverter</code>
     * @param arg
     * @return
     * @throws AppException
     */
    public Object query(Connection conn, ResultConverter converter, Object arg) throws AppException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Object result = null;

        try {
            //Log.info("DB",this,sql);
            pstmt = conn.prepareStatement(sql);
            /*
               if (arg instanceof QueryFactor) {
                            pstmt.setFetchSize(((QueryFactor) arg).fetchSize);
                        } else {
                            pstmt.setFetchSize(DEFAULT_FETCH_SIZE);
               }
             */
            //pstmt.setFetchSize(paramCount);

            for (int i = 0; i < paramCount; i++) {
                pstmt.setObject(i + 1, (String) paramValues[i]);
            }

            rs = pstmt.executeQuery();
            result = converter.fromResultSet(rs, arg);
        } catch (SQLException ex) {
            wrapSQLException(ex);
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
        }

        return result;
    }

    /**
     * DML문을 실행하고 변경된 레코드의 갯수를 리턴한다.
     *
     * @return
     * @throws AppException
     */
    public int update(Connection conn) throws AppException {
        PreparedStatement pstmt = null;
        int rows = 0;

        try {
            pstmt = conn.prepareStatement(sql);
            //pstmt.setFetchSize(paramCount);

            for (int i = 0; i < paramCount; i++) {
                pstmt.setObject(i + 1, paramValues[i]);
            }
            rows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            wrapSQLException(ex);
        } finally {
            closeStatement(pstmt);
        }

        return rows;
    }

    public String getQuery() {
        return sql;
    }

    /**
     * 로그를 남기기 위해 이 객체가 갖고있는 SQL문을 문자열 형태로 남긴다.
     * 이때, 바인드변수에 할당된 값은 '['과 ']'으로 둘러싸여진다.
     *
     * @return
     */
    public String toString() {
        StringBuffer buf = new StringBuffer(sql.length());
        int len = sql.length();
        int param_index = 0;

        boolean partOfString = false;
        char c;
        for (int i = 0; i < len; i++) {
            c = sql.charAt(i);
            switch (c) {
            case '?':
                if (!partOfString && param_index < paramCount && paramValues[param_index] != null) {
                    buf.append("'");
                    buf.append(paramValues[param_index++]).append("'");
                } else {
                    buf.append(c);
                }
                break;
            case '\'':
                partOfString = !partOfString;
            default:
                buf.append(c);
            }
        }

        return buf.toString();
    }

    /**
     * SQL오류가 발생한 경우, 발생한 오류를 처리하고,
     * <code>AppException</code>로 감싸서 다시 throw한다.
     *
     * @param ex
     * @throws SQLException
     */
    private void wrapSQLException(SQLException ex) throws AppException {
        Log.debug("QueryRunner SQL에러. sql = " + ex.toString());
        throw new AppException("QueryRunner에서 예외 발생: SQL코드 = " + ex.getErrorCode(), ex);
    }
}
