package com.dhitech.spend.infosearch.gate;

import java.sql.Connection;

import com.dhitech.framework.business.BaseBean;
import com.dhitech.framework.exception.AppException;
import com.dhitech.framework.exception.ExceptionManager;
import com.dhitech.framework.log.Log;
import com.dhitech.framework.tray.Tray;
import com.dhitech.spend.infosearch.dao.infosearchDao;

public final class infosearchGate extends BaseBean {
	private infosearchDao dao = null;

	public infosearchGate() {
		init();
	}

	private void init() {
		try {
			dao = new infosearchDao();
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
	
	public boolean update(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.update(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
	
	public boolean updateEx(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.updateEx(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
		
	public Tray find(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.find(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
	
	public Tray findEx(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.findEx(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}
