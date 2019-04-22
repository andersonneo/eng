package com.eng.framework.business;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.commons.configuration.PropertiesConfiguration;

import com.eng.framework.config.ConfigFactory;
import com.eng.framework.connection.DBManager;
import com.eng.framework.exception.AppException;
import com.eng.framework.exception.PropNotFoundException;
import com.eng.framework.log.Log;
import com.eng.framework.tray.Tray;

/**
 * 1. 표준 세션 EJBean 구현에서 사용할 SessionBean 인터페이스를 상속한다.
 * 2. SessionBean 인터페이스의 기본적인 기능을 구현한다.
 * 3. Database Connection을 얻고 반납하는 과정을 자동으로 처리하며
 *    비즈니스 로직의 구현은 각각의 하위 SessionBean에 맡긴다.
 * 
 * 트랜잭션 관리는 컨테이너가 하기로 한다.
 * 배치디스크립터에서 <transaction-type>Container</transaction-type>을 지정한다.
 *
 * 참조: J2EE Design Pattern Applied, Wrox, 정보문화사
 *
 * @author <b>박찬우</b>
 * @version 1.1, 2004-03-31 수정자 진헌규(hkjin@daou.co.kr), Logger.setLogID() 부분 추가
 * @version 1.0, 2004/01/05
 */

public abstract class BaseBean implements Business {
	
    public Tray find(Tray reqTray) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;
        Connection conn = null;
        Tray rsTray;
        try {
            Log.debug("", this, "BaseBean 에서 find 메소드 호출 시작");
            conn = getConnection("eng");
            Log.debug("", this, "connection 객체 하나를 얻어 온다. : " + conn);
            rsTray = find(conn, reqTray);
            return rsTray;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.find(Tray reqTray)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }
    
    public Tray findEx(Tray reqTray) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;
        Connection conn = null;
        Tray rsTray;
        try {
            Log.debug("", this, "BaseBean 에서 findEx 메소드 호출 시작");
            conn = this.getConnection();
            Log.debug("", this, "connection 객체 하나를 얻어 온다. : " + conn);
            rsTray = findEx(conn, reqTray);
            return rsTray;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.findEx(Tray reqTray)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }

    public Collection findAll(Tray reqTray) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;
        Connection conn = null;
        Collection trayCollection;
        try {
            conn = getConnection();
            trayCollection = findAll(conn, reqTray);
            return trayCollection;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.findAll(Tray reqTray)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }
    
    public Collection findAllEx(Tray reqTray) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;
        Connection conn = null;
        Collection trayCollection;
        try {
            conn = getConnection();
            trayCollection = findAllEx(conn, reqTray);
            return trayCollection;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.findAllEx(Tray reqTray)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }
	
    public Tray findByPrimaryKey(Tray reqTrayPrimaryKey) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;

        Connection conn = null;
        Tray tray = null;
        try {
            conn = getConnection();
            Log.debug("", this, "connection 객체 하나를 얻어 온다. : " + conn);
            tray = findByPrimaryKey(conn, reqTrayPrimaryKey);
            return tray;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.findByPrimaryKey(Object pPrimaryKey)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }
    
    public Tray findByPrimaryKeyEx(Tray reqTrayPrimaryKey) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;

        Connection conn = null;
        Tray tray = null;
        try {
            conn = getConnection();
            Log.debug("", this, "connection 객체 하나를 얻어 온다. : " + conn);
            tray = findByPrimaryKeyEx(conn, reqTrayPrimaryKey);
            return tray;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.findByPrimaryKeyEx(Object pPrimaryKey)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }

    public boolean insert(Tray reqTray) throws AppException {

        long start = System.currentTimeMillis();
        long end = 0;

        Connection conn = null;
        Tray tray = null;
        try {
            conn = getConnection();
            return insert(conn, reqTray);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.insert(Tray reqTray)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }

    public boolean insertEx(Tray reqTray) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;

        Connection conn = null;
        Tray tray = null;
        try {
            conn = getConnection();
            return insertEx(conn, reqTray);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.insertEx(Tray reqTray)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }
    
    public boolean update(Tray reqTray) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;

        Connection conn = null;
        Tray tray = null;
        try {
            conn = getConnection();
            return update(conn, reqTray);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.update(Tray reqTray)에서 일반 예외 발생", ex);
        } catch (Throwable t) {
            throw new AppException("BaseBean.update(Tray reqTray)에서 throwable 발생", t);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }
    
    public boolean updateEx(Tray reqTray) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;

        Connection conn = null;
        Tray tray = null;
        try {
            conn = getConnection();
            return updateEx(conn, reqTray);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.updateEx(Tray reqTray)에서 일반 예외 발생", ex);
        } catch (Throwable t) {
            throw new AppException("BaseBean.updateEx(Tray reqTray)에서 throwable 발생", t);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }

    public Tray staticUpdate(Tray reqTray) throws AppException {

        long start = System.currentTimeMillis();
        long end = 0;

        Connection conn = null;
        Tray tray = null;
        try {
            conn = getConnection();
            return staticUpdate(conn, reqTray);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.staticUpdate(Tray reqTray)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }

    public boolean delete(Tray reqTray) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;

        Connection conn = null;
        Tray tray = null;
        try {
            conn = getConnection();
            return delete(conn, reqTray);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.delete(Tray reqTray)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }
    
    public boolean deleteEx(Tray reqTray) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;

        Connection conn = null;
        Tray tray = null;
        try {
            conn = getConnection();
            return deleteEx(conn, reqTray);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.deleteEx(Tray reqTray)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }

    public Tray createTray() throws AppException {
        Connection conn = null;
        Tray tray = null;
        try {
            conn = getConnection();
            tray = createTray(conn);
            return tray;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.createTray()에서 일반 예외 발생", ex);
        } finally {
            returnConnection(conn);
        }
    }
    
    public Tray findByUserId(Tray reqTray) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;
        Connection conn = null;
        Tray rsTray;
        try {
            Log.debug("", this, "BaseBean 에서 find 메소드 호출 시작");
            conn = this.getConnection();
            Log.debug("", this, "connection 객체 하나를 얻어 온다. : " + conn);
            rsTray = findByUserId(conn, reqTray);
            return rsTray;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.find(Tray reqTray)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }

    public Tray findStatic(Tray reqTray) throws AppException {
        long start = System.currentTimeMillis();
        long end = 0;
        Connection conn = null;
        Tray rsTray;
        try {
            Log.debug("", this, "BaseBean 에서 find 메소드 호출 시작");
            conn = this.getConnection();
            Log.debug("", this, "connection 객체 하나를 얻어 온다. : " + conn);
            rsTray = findStatic(conn, reqTray);
            return rsTray;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("BaseBean.findStatic(Tray reqTray)에서 일반 예외 발생", ex);
        } finally {
            end = System.currentTimeMillis();
            Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
            returnConnection(conn);
        }
    }

    /**
     * 기본적인 DB 드라이버를 로딩하고 connection 객체를 반환한다.
     * @return Connection
     */
    protected Connection getConnection() throws Exception {
        return DBManager.getInstance().getConnection();
    }
    
    /*
     * 추가 DB 사용시
     * @return Connection
     */
    protected Connection getConnection(String conName) throws Exception {
        return DBManager.getInstance().getConnection(conName);
    }    

    /**
     * 이 메소드는 데이터 소스에서 다건의 데이터를 검색하여 리턴한다.
     *
     * @param   conn: Database Connection
     * @param   reqTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
     * @return   Tray: ResultSetTray 객체 - primary key가 아닌 다른 조건으로 찾게 되는 결과셋
     *
     * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    protected Tray find(Connection conn, Tray reqTray) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }
    
    protected Tray findEx(Connection conn, Tray reqTray) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }
    
    protected Tray findByUserId(Connection conn, Tray reqTray) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }

    protected Tray findStatic(Connection conn, Tray reqTray) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }
    
    /**
     * 이 메소드는 데이터 소스에서 검색한 여러 결과셋을 리턴한다.
     *
     * @param   conn: Database Connection
     * @param   reqTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
     * @return   Collection: ResultSetTray 객체의 모음
     *
     * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    protected Collection findAll(Connection conn, Tray reqTray) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }
    
    protected Collection findAllEx(Connection conn, Tray reqTray) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }
    
    /**
     * 이 메소드는 데이터 소스에서 레코드를 검색하는 일을 수행한다.
     *
     * @param   conn: Database Connection
     * @param   pPrimaryKey: 데이터 소스에서 레코드를 구별하는 유일한 ID 값
     * @return   Tray: 데이터 소스에서 검색된 데이터를 Map 기반으로 포장한 래퍼 클래스
     *
     * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    protected Tray findByPrimaryKey(Connection conn, Tray reqTrayPrimaryKey) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }
    
    protected Tray findByPrimaryKeyEx(Connection conn, Tray reqTrayPrimaryKey) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }

    /**
     * 이 메소드는 데이터 소스에 레코드를 삽입하는 일을 수행한다.
     *
     * @param   conn: Database Connection
     * @param   pTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
     * @return  Tray: 데이터 소스에서 검색된 데이터를 Map 기반으로 포장한 래퍼 클래스
     *
     * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    protected boolean insert(Connection conn, Tray pTray) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }
    
    protected boolean insertEx(Connection conn, Tray pTray) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }

    /**
     * 이 메소드는 데이터 소스에 레코드를 수정하는 일을 수행한다.
     *
     * @param   conn: Database Connection
     * @param   pTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
     * @return  Tray: 데이터 소스에서 검색된 데이터를 Map 기반으로 포장한 래퍼 클래스
     *
     * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    protected boolean update(Connection conn, Tray pTray) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }
    
    protected boolean updateEx(Connection conn, Tray pTray) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }
    
    protected Tray staticUpdate(Connection conn, Tray reqTrayPrimaryKey) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }
    
    /**
     * 이 메소드는 데이터 소스에 레코드를 삭제하는 일을 수행한다.
     *
     * @param   conn: Database Connection
     * @param   pTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
     * @return  Tray: 데이터 소스에서 검색된 데이터를 Map 기반으로 포장한 래퍼 클래스
     *
     * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    protected boolean delete(Connection conn, Tray pTray) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }
    
    protected boolean deleteEx(Connection conn, Tray pTray) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }

    /**
     * 이 메소드는 빈 Tray를 생성하여 insert(pTray)를 호출한다.
     *
     * @param   conn: Database Connection
     * @return  Tray: 텅 빈 Map 기반의 래퍼 클래스
     *
     * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
     * @exception <code>com.ubiware.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
     */
    protected Tray createTray(Connection conn) throws AppException {
        throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
    }


    protected void returnConnection(Connection conn) throws AppException {
    	 if(conn != null) {
             try {
                 if(!conn.isClosed()){
                     Log.info("", this, "at BaseBeen close connection!");
                     conn.close();
                 }
             } catch (SQLException e) {
                 throw new AppException("BaseBean.returnConnection(Connection conn)에서 Connection.close() 예외 발생", e);
             }
         }
    }

    /**
     * 각 비지니스별 Trace한다.
     * @param start long
     * @param end long
     * @return long
     */
    protected long currentTimeMillis(long start, long end) {
        //return (end - start) / 24 / 60;
    	return (end - start);
    }
}
