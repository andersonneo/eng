package com.eng.framework.exception;

/**
 * <p>Title: 프로퍼티를 읽지 못한경우 발생</p>
 * <p>Description: 기간시스템과 LDAP 연동 API</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: www.UbiwareLab.com</p>
 * @author yunkidon@hotmail.com
 * @version 1.0
 */
public class PropNotFoundException extends Exception {

    public PropNotFoundException() {
    }

    public PropNotFoundException(String msg) {
        super(msg);
    }
}
