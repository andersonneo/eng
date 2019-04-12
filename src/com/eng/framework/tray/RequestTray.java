/**
 * 파일명: com.kyeryong.framework.tray.RequestTray.java
 * 파일개요: 클라이언트 요청 정보를 캡슐화한 클래스
 * 저작권: Copyright (c) 2003 by SK C&C. All rights reserved.
 * 작성자: 박찬우 (nucha@dreamwiz.com)
 */

package com.eng.framework.tray;

//Java API
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

/**
 * RequestTray 클래스는 HttpServletRequest 객체 정보를 저장하기 위한 Tray이다.
 * <p>
 * @author <b>박찬우</b>
 * @version 1.0, 2004/02/01
 */

public class RequestTray extends AbstractTray implements java.io.Serializable {

    public RequestTray() {
    }

    /**
     * 저장된 데이터에 대한 열거 제공.
     * @return - Enumeration (List로 타입 캐스팅해서 사용하면 됨)
     */
    public Enumeration getEnumeration() {
        return new Enumeration() {
            Set set = map.keySet();
            Iterator it = set.iterator();

            int count = size();

            public boolean hasMoreElements() {
                return size() != 0;
            }

            public Object nextElement() {
                String key = (String) it.next();
                return map.get(key);
            }
        };
    }

    public String toString() {
        return super.toString();
    }

    public void setRowCount(int row_count){
    }

    public void setColumnNames(String[] columnNames){
    }
}
