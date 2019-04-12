package com.eng.framework.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.eng.framework.tray.DhitechResultSetTrayFactory;
import com.eng.framework.tray.ResultSetTrayFactory;

/**
 * <code>java.sql.ResultSet</code>을 다른 형태의 클래스로 변환하여 돌려주는 인터페이스.
 *
 * @author 진헌규(hkjin@daou.co.kr)
 */
public interface ResultConverter {

    /**
     * <code>ResultSet</code>의 모든 레코드를 배열의 List로 변환해주는 인스턴스
     */
    public static final ResultConverter ListOfArray = new ResultConverter() {
        public Object fromResultSet(ResultSet rs, Object arg) throws SQLException {
            ArrayList v = new ArrayList(10);
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                String[] array = new String[rsmd.getColumnCount()];
                for (int i = 0; i < array.length; i++) {
                    array[i] = rs.getString(i + 1);
                }
                v.add(array);
            }

            return v.size() > 0 ? v : null;
        }
    };

    /**
     * <code>ResultSet</code>의 모든 레코드를 Map의 List로 변환해주는 인스턴스
     */
    public static final ResultConverter ListOfMap = new ResultConverter() {
        public Object fromResultSet(ResultSet rs, Object arg) throws SQLException {
            ArrayList v = new ArrayList(10);
            ResultSetMetaData rsmd = rs.getMetaData();

            String[] colNames = new String[rsmd.getColumnCount()];
            for (int i = 0; i < colNames.length; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }

            while (rs.next()) {
                HashMap map = new HashMap();
                for (int i = 0; i < colNames.length; i++) {
                    map.put(colNames[i], rs.getString(i + 1));
                }
                v.add(map);
            }

            return v.size() > 0 ? v : null;
        }
    };

    /**
     * <code>ResultSet</code>의 첫번째 레코드를 Map으로 변환해주는 인스턴스
     */
    public static final ResultConverter Map = new ResultConverter() {
        public Object fromResultSet(ResultSet rs, Object arg) throws SQLException {
            if (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                HashMap map = new HashMap();

                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    map.put(rsmd.getColumnName(i + 1), rs.getString(i + 1));
                }

                return map;
            } else {
                return null;
            }
        }
    };

    /**
     * <code>ResultSet</code>의 첫번째 레코드의 첫번째 필드를 문자열값으로 변환해주는 인스턴스.
     * count(*)나 EXISTS 등의 조건에서 사용
     */
    public static final ResultConverter Scalar = new ResultConverter() {
        public Object fromResultSet(ResultSet rs, Object arg) throws SQLException {
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }
        }
    };

    /**
     * <code>ResultSet</code>의 모든 레코드를 Tray로 변환해주는 인스턴스.
     */
    public static final ResultConverter ResultSetTray = new ResultConverter() {
       
        public Object fromResultSet(ResultSet rs, Object arg) throws SQLException {
        	ResultSetTrayFactory factory = new DhitechResultSetTrayFactory();	
            return factory.getTray(rs);
        }
    };

    /**
     * <code>java.sql.ResultSet</code> 객체를 받아 다른 클래스의 객체로 변환하는 method.
     * <code>Object</code>를 돌려주므로 받는 쪽에서 적절한 캐스팅이 필요하다.
     *
     * @param rs	쿼리결과를 담고 있는 <code>java.sql.ResultSet</code>
     * @param arg	변환과정에 필요한 매개변수
     * @return		rs가 변환된 다른 객체.
     * @throws SQLException
     */
    abstract Object fromResultSet(ResultSet rs, Object arg) throws SQLException;
}
