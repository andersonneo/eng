package com.eng.framework.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * log4J를 구현한 클래스
 * <p>Title: </p>
 * <p>Description: </p>
 * Copyright: Copyright (c) 2006
 * Company: www.UbiwareLab.com
 * @author yunkidon@hotmail.com
 * @version 1.0
 * <pre>Usage
 *  ConfigFactory cf = ConfigFactory.getInstance();
    Config config = cf.getConfiguration("base.properties");
    Log.debug("","",config.getString("ldap.server.primary.host")); -->base.log
    Log.debug("TRACE","",config.getString("ldap.server.primary.host"));-->trace.log
    Log.debug("ERROR","",config.getString("ldap.server.primary.host"));-->error.log
 * </pre>
 */



public class Log {

    private static Logger logger = null;

    static {
        try {
            PropertyConfigurator.configureAndWatch("log4j.properties");
            System.out.println("Log System configured!");
        } catch (Exception ex) {
            System.err.print("Log System not configured!");
        }

    }

    public Log() {}

    
    /**
     * 디버깅용 로그
     * @param Object 출력 스트링
     */
    public static void debug(Object pStr) {
        logger = Logger.getRootLogger();
        logger.debug(pStr);
    }

    /**
     * 디버깅용 로그
     * @param String  로그파일명
     * @param Object 출력요청 클래스
     * @param Object 출력 스트링
     */
    public static void debug(String pCat, Class pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.debug(pCaller.getName() + " - " + pStr);
    }

    /**
     * 경고성 로그
     * @param String  로그파일명
     * @param Object 출력요청 객체
     * @param Object 출력 스트링
     */
    public static void debug(String pCat, Object pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.debug(pCaller.getClass().getName() + " - " + pStr);
    }

    /**
     * 에러 로그
     * @param Object 출력 스트링
     */
    public static void error(Object pStr) {
        logger = Logger.getRootLogger();
        logger.error(pStr);
    }

    /**
     * 에러 로그
     * @param String  로그파일명
     * @param Object 출력요청 클래스
     * @param Object 출력 스트링
     */
    public static void error(String pCat, Class pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.error(pCaller.getName() + " - " + pStr);
    }

    /**
     * 에러 로그
     * @param String  로그파일명
     * @param Object 출력 스트링
     */
    public static void error(String pCat, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.error(pStr);
    }

    /**
     * 에러 로그
     * @param String  로그파일명
     * @param Object 출력요청 객체
     * @param Object 출력 스트링
     */
    public static void error(String pCat, Object pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.error(pCaller.getClass().getName() + " - " + pStr);
    }

    /**
     * Fatal 로그
     * @param Object 출력 스트링
     */
    public static void fatal(Object pStr) {
        logger = Logger.getRootLogger();
        logger.fatal(pStr);
    }

    /**
     * Fatal 로그
     * @param String  로그파일명
     * @param Object 출력요청 클래스
     * @param Object 출력 스트링
     */
    public static void fatal(String pCat, Class pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.fatal(pCaller.getName() + " - " + pStr);
    }

    /**
     * Fatal 로그
     * @param String  로그파일명
     * @param Object 출력 스트링
     */
    public static void fatal(String pCat, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.fatal(pStr);
    }

    /**
     * Fatal 로그
     * @param String  로그파일명
     * @param Object 출력요청 객체
     * @param Object 출력 스트링
     */
    public static void fatal(String pCat, Object pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.fatal(pCaller.getClass().getName() + " - " + pStr);
    }

    /**
     * 정보성 로그
     * @param Object 출력 스트링
     */
    public static void info(Object pStr) {
        logger = Logger.getRootLogger();
        logger.info(pStr);
    }

    /**
     * 정보성 로그
     * @param String  로그파일명
     * @param Object 출력요청 클래스
     * @param Object 출력 스트링
     */
    public static void info(String pCat, Class pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.info(pCaller.getName() + " - " + pStr);
    }

    /**
     * 정보성 로그
     * @param String  로그파일명
     * @param Object 출력요청 객체
     * @param Object 출력 스트링
     */
    public static void info(String pCat, Object pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.info(pCaller.getClass().getName() + " - " + pStr);
    }

    /**
     * 경고성 로그
     * @param String  로그파일명
     * @param Object 출력요청 객체
     * @param Object 출력 스트링
     */
    public static void warn(Object pStr) {
        logger = Logger.getRootLogger();
        logger.warn(pStr);
    }

    /**
     * 경고성 로그
     * @param String  로그파일명
     * @param Object 출력요청 객체
     * @param Object 출력 스트링
     */
    public static void warn(String pCat, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.warn(pStr);
    }

    /**
     * 경고성 로그
     * @param String  로그파일명
     * @param Object 출력요청 객체
     * @param Object 출력 스트링
     */
    public static void warn(String pCat, Class pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.warn(pCaller.getName() + " - " + pStr);
    }

    /**
     * 경고성 로그
     * @param String  로그파일명
     * @param Object 출력요청 객체
     * @param Object 출력 스트링
     */
    public static void warn(String pCat, Object pCaller, Object pStr) {
        logger = Logger.getLogger(pCat);
        logger.warn(pCaller.getClass().getName() + " - " + pStr);
    }
}
