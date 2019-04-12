package com.eng.login.gate;

import java.sql.Connection;

import com.eng.framework.business.BaseBean;
import com.eng.framework.exception.AppException;
import com.eng.framework.exception.ExceptionManager;
import com.eng.framework.log.Log;
import com.eng.framework.tray.Tray;
import com.eng.login.dao.LoginDao;

public final class LoginGate extends BaseBean {

	private LoginDao dao = null;


	public LoginGate() {
		init();
	}

	private void init() {
		try {
			dao = new LoginDao();
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}

	public Tray find(Connection conn, Tray reqTray) throws AppException {
		try {
			Tray tray = dao.find(conn, reqTray);
			conn.close();
			return tray;
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
