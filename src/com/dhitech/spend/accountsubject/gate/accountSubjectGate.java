package com.dhitech.spend.accountsubject.gate;

import java.sql.Connection;

import com.dhitech.framework.business.BaseBean;
import com.dhitech.framework.exception.AppException;
import com.dhitech.framework.exception.ExceptionManager;
import com.dhitech.framework.log.Log;
import com.dhitech.framework.tray.Tray;
import com.dhitech.spend.accountsubject.dao.accountSubjectDao;

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
