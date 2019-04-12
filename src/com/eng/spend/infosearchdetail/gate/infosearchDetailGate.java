package com.eng.spend.infosearchdetail.gate;

import java.sql.Connection;

import com.eng.framework.business.BaseBean;
import com.eng.framework.exception.AppException;
import com.eng.framework.exception.ExceptionManager;
import com.eng.framework.log.Log;
import com.eng.framework.tray.Tray;
import com.eng.spend.infosearchdetail.dao.infosearchDetailDao;

public final class infosearchDetailGate extends BaseBean {
	private infosearchDetailDao dao = null;

	public infosearchDetailGate() {
		init();
	}

	private void init() {
		try {
			dao = new infosearchDetailDao();
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
	
	public Tray findStatic(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.findStatic(conn, reqTray);
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
	
	public boolean delete(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.delete(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
}