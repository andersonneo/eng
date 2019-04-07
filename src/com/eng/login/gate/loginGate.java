package com.dhitech.spend.login.gate;

import java.sql.Connection;

import com.dhitech.framework.business.BaseBean;
import com.dhitech.framework.exception.AppException;
import com.dhitech.framework.exception.ExceptionManager;
import com.dhitech.framework.log.Log;
import com.dhitech.framework.tray.Tray;
import com.dhitech.spend.login.dao.loginDao;

public final class loginGate extends BaseBean {

	private loginDao dao = null;


	public loginGate() {
		init();
	}

	private void init() {
		try {
			dao = new loginDao();
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}

	public Tray find(Connection conn, Tray reqTray) throws AppException {
		try {
			conn = this.getConnection("dhli");
			Tray tray = dao.find(conn, reqTray);
			conn.close();
			return tray;
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
	
	public Tray findEx(Connection conn, Tray reqTray) throws AppException {
		try {
			conn = this.getConnection("dhli");
			Tray tray = dao.findEx(conn, reqTray);
			conn.close();
			return tray;
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
	
	public boolean insert(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.insert(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
