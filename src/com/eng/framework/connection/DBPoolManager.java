package com.eng.framework.connection;

import java.sql.*;
import java.util.Iterator;

import com.eng.framework.config.Config;
import com.eng.framework.config.ConfigFactory;
import com.eng.framework.exception.AppException;
import com.eng.framework.exception.PropNotFoundException;
import com.eng.framework.log.Log;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DataSourceConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 *
 * <pre>Title: 데이타베이스 커넥션 관리
 * Description: db.properties에 있는 설정을 자동으로 읽어들여 풀을 생성한다.
 * Copyright: gw.ubi-ware.com Copyright (c) 2006
 * Company: 안랩유비웨어</pre>
 * @author  jyna@ubi-ware.com
 * @version 1.1  reversioning---> yunkidon@hotmail.com 1.2
 */
public class DBPoolManager {

    /**
     * Properties 객체
     */
    private static Config conf = null;

    static {
        try {
            conf = ConfigFactory.getInstance().getConfiguration("db.properties");
        } catch (PropNotFoundException pe) {
            System.err.println("Can't read the properties file. Make sure db.properties is in the CLASSPATH");
        } catch (Exception e) {
            System.err.println("find db.properties. but other problems are here.");
        }
    }

    /**
     * use DBManager.getInstance()
     */
    private static DBPoolManager instance = null;


    /**
     * use getInstance();
     */
    private DBPoolManager() {
        loadDrivers((Iterator) conf.getKeys());
    }


    /**
     * Double Check Lock 구현
     * @return PoolManager
     */
    public static DBPoolManager getInstance() {
        if (instance == null) {
            synchronized (DBPoolManager.class) {
                if (instance == null) {
                    instance = new DBPoolManager();
                }
            }
        }
        return instance;
    }

    /**
     * db.properties에 설정되어있는 oracledb.xxx의 앞 단어를 입력한다. ---> oracledb
     * @param connName String
     * @return Connection
     * @throws SQLException
     * @see db.properties의 설정참조
     */
    public Connection getConnection(String connName) throws SQLException {
    	Log.debug("ConnectionPoolManager----------> "+connName);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:" + connName);
            if (conn == null) {
                loadDrivers((Iterator) conf.getKeys());
                conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:" + connName);
            }
        } catch (Exception ex) {
            loadDrivers((Iterator) conf.getKeys());
            Log.error("ERROR", this, ex);
            throw new AppException("msg.scheduler.main.001", ex);
        }
        return conn;
    }

    /**
     * DB 풀을 호출하지 않을경우 디폴트 커넥션을 넘겨준다.
     * @return Connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:oracledb");

            if (conn == null) {
                loadDrivers((Iterator) conf.getKeys());
                conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:oracledb");
            }
        } catch (Exception ex) {
            loadDrivers((Iterator) conf.getKeys());
            Log.error("ERROR", this, ex);
            throw new AppException("msg.scheduler.main.001", ex);
        }
        return conn;
    }

    /**
     * 프로퍼티 파일을 읽어서 BasicDataSource를 생성한다.
     * @param keys Iterator
     */
    private void loadDrivers(Iterator keys) {

        BasicDataSource bds = null;
        String name = null;
        String pool = null;
        String driver = null;
        String url = null;
        String user = null;
        String password = null;
        int maxActive = 10;
        int maxIdle = 30;
        int maxWait = 10000;
        boolean defaultAutoCommit = false;
        boolean defaultReadOnly = false;

        while (keys.hasNext()) {
            name = (String) keys.next();
            if (name.endsWith(".driver")) {
                try {
                    pool = name.substring(0, name.lastIndexOf("."));
                    driver = conf.getString(pool + ".driver");
                    url = conf.getString(pool + ".url");
                    user = conf.getString(pool + ".user");
                    password = conf.getString(pool + ".password");
                    maxActive = Integer.parseInt(conf.getString(pool + ".maxActive"));
                    maxIdle = Integer.parseInt(conf.getString(pool + ".maxIdle"));
                    maxWait = Integer.parseInt(conf.getString(pool + ".maxWait"));
                    defaultAutoCommit = conf.getString(pool + ".defaultAutoCommit").equals("true");
                    defaultReadOnly = conf.getString(pool + ".defaultReadOnly").equals("true");

                    Log.debug("", this, "프로퍼티 정보 pool:" + pool + " driver:" + driver + " url:" + url + " password:" + password + " maxIdle:" + maxIdle + " maxActive:" + maxActive + " maxWait:" + maxWait);

                    if (url == null) {
                        Log.error("ERROR", this, "Can't initialize pool : confirm url in db.properties.");
                        continue;
                    }

                    bds = new BasicDataSource();
                    bds.setDriverClassName(driver);
                    bds.setUrl(url);
                    bds.setUsername(user);
                    bds.setPassword(password);
                    bds.setMaxActive(maxActive);
                    bds.setMaxIdle(maxIdle);
                    bds.setMaxWait(maxWait);
                    bds.setDefaultAutoCommit(defaultAutoCommit);
                    bds.setDefaultReadOnly(defaultReadOnly);

                    createPools(pool, bds);
                    Log.debug("", this, "Initialized pool : " + pool);
                } catch (Exception e) {
                    Log.error("ERROR", this, "Can't initialize loadDrivers : " + pool + "\n" + e);
                }
            }
        }
    }


    private void createPools(String pool, BasicDataSource bds) throws Exception {
        try {
            //ObjectPool connectionPool = new GenericObjectPool(null);
            GenericObjectPool connectionPool = new GenericObjectPool();

            ConnectionFactory connectionFactory = new DataSourceConnectionFactory(bds);

            PoolableConnectionFactory poolableConnectionFactory;
            try {
                poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);
            } catch (Exception e) {
                return;
            }

            PoolingDriver driver = new PoolingDriver();
            driver.registerPool(pool, connectionPool);
            printDriverStats(pool);
        } catch (Exception e) {
            Log.error("ERROR", this, "Can't initialize DBCP : " + pool + "\n" + e);
            throw e;
        }
    }

    public void freeConnection(Connection conn) {
    	Log.debug("con.close()!");
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception ex) {}
    }

    /**
     * 풀의 정보를 출력한다.
     * @param pool String
     * @throws Exception
     */
    public void printDriverStats(String pool) throws Exception {
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        ObjectPool connectionPool = driver.getConnectionPool(pool);
        Log.info("", this, "풀 생성 pool name: " + pool);
        Log.info("", this, "NumActive: " + connectionPool.getNumActive());
        Log.info("", this, "NumIdle: " + connectionPool.getNumIdle());
    }
}
