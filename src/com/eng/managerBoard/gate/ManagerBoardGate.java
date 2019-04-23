package com.eng.managerBoard.gate;

import java.sql.Connection;
import java.util.Collection;

import com.eng.adminBoard.dao.AdminBoardDao;
import com.eng.framework.business.BaseBean;
import com.eng.framework.exception.AppException;
import com.eng.framework.exception.ExceptionManager;
import com.eng.framework.log.Log;
import com.eng.framework.tray.Tray;

public class ManagerBoardGate extends BaseBean {

	private AdminBoardDao dao = null;

	public ManagerBoardGate() {
		init();
	}

	private void init() {
		try {
			dao = new AdminBoardDao();
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	} 


	public Collection findAll(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.findAll(conn, reqTray);
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
	
	public boolean delete(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.delete(conn, reqTray);
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
	
}
