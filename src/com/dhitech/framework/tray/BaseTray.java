package com.dhitech.framework.tray;

import java.util.Enumeration;

import com.dhitech.framework.exception.AppException;

/**
 * {@link AbstractTray}의 추상 method를 최대한 단순하게 구현한 Tray.
 * ("구현되지 않은 method"라는 예외 발생시킴)
 * javax.servlet.http.HttpRequest나 java.sql.ResultSet에서 생성되지 않고
 * 사용자에 의해 직접 생성되어 delegate나 EJB의 인수로 전달되는 용도로 사용함.
 *
 * @author 진헌규
 * @version 1.0, 2004-03-10
 */
public class BaseTray extends AbstractTray {
    public Enumeration getEnumeration() {
        throw new AppException("구현되지 않은 method입니다");
    }

    public void setRowCount(int row_count){
    }

    public void setColumnNames(String[] columnNames){
    }

}
