package com.eng.spend.accountsubject.gate;

import java.sql.Connection;

import com.eng.framework.business.BaseBean;
import com.eng.framework.exception.AppException;
import com.eng.framework.exception.ExceptionManager;
import com.eng.framework.log.Log;
import com.eng.framework.tray.Tray;
import com.eng.spend.accountsubject.dao.accountSubjectDao;

public final class accountSubjectGate extends BaseBean {
	private accountSubjectDao dao = null;

	public accountSubjectGate() {
		init();
	}

	private void init() {
		try {
			dao = new accountSubjectDao();
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}

	/*
	public boolean insert(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.insert(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
	*/
	
	public Tray find(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.find(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}	
}
