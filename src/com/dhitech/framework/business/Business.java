package com.dhitech.framework.business;

import java.sql.Connection;
import java.util.Collection;

import com.dhitech.framework.exception.AppException;
import com.dhitech.framework.tray.Tray;

/**
 * 공통 비즈니스 인터페이스
 *
 * 경고: 메소드 호출 시 이 비즈니스 인터페이스를 전달하지 말 것.
 * 파라미터와 리턴 타입으로 항상 빈의 리모트 인터페이스를 사용해야 한다.
 *
 * @author <b>박찬우</b>
 * @version 1.0, 2004/01/08
 */

public interface Business {

    /**
     * 이 메소드는 데이터 소스에서 다건의 데이터를 검색하여 리턴한다.
     *
     * @param   reqTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
     * @return   Tray: ResultSetTray 객체 - primary key가 아닌 다른 조건으로 찾게 되는 결과셋
     *
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    public Tray find(Tray reqTray) throws AppException;

    /**
     * 이 메소드는 데이터 소스에서 검색한 여러 결과셋을 리턴한다.
     *
     * @param   reqTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
     * @return   Collection: ResultSetTray 객체를 모든 컬렉션
     *
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    public Collection findAll(Tray reqTray) throws AppException;
	
    /**
     * 이 메소드는 데이터 소스에서 하나의 레코드를 검색하는 일을 수행한다.
     *
     * @param   reqTrayPrimaryKey: 데이터 소스에서 레코드를 구별하는 유일한 ID 값
     * @return   Tray: 데이터 소스에서 검색된 데이터를 Map 기반으로 포장한 래퍼 클래스
     *
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    public Tray findByPrimaryKey(Tray reqTrayPrimaryKey) throws AppException;

    /**
     * 이 메소드는 데이터 소스에 하나의 레코드를 삽입하는 일을 수행한다.
     *
     * @param   reqTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
     * @return  Tray: 데이터 소스에서 검색된 데이터를 Map 기반으로 포장한 래퍼 클래스
     *
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    public boolean insert(Tray reqTray) throws AppException;

    /**
     * 이 메소드는 데이터 소스에 하나의 레코드를 수정하는 일을 수행한다.
     *
     * @param   reqTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
     * @return  Tray: 데이터 소스에서 검색된 데이터를 Map 기반으로 포장한 래퍼 클래스
     *
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    public boolean update(Tray reqTray) throws AppException;

    /**
     * 이 메소드는 데이터 소스에 하나의 레코드를 삭제하는 일을 수행한다.
     *
     * @param   reqTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
     * @return  Tray: 데이터 소스에서 검색된 데이터를 Map 기반으로 포장한 래퍼 클래스
     *
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    public boolean delete(Tray reqTray) throws AppException;

    /**
     * 이 메소드는 빈 Tray를 생성하여 insert(pTray)를 호출한다.
     *
     * @return  Tray: 텅 빈 Map 기반의 래퍼 클래스
     *
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    public Tray createTray() throws AppException;
    
    /**
     * 이 메소드는 데이터 소스에서 다건의 데이터를 검색하여 리턴한다.
     *
     * @param   reqTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
     * @return   Tray: ResultSetTray 객체 - primary key가 아닌 다른 조건으로 찾게 되는 결과셋
     *
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    public Tray findByUserId(Tray reqTray) throws AppException;
}
