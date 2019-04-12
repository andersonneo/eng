package com.eng.adminBoard.gate;

import java.sql.Connection;
import java.util.Collection;

import com.eng.adminBoard.dao.AdminBoardDao;
import com.eng.framework.business.BaseBean;
import com.eng.framework.exception.AppException;
import com.eng.framework.exception.ExceptionManager;
import com.eng.framework.log.Log;
import com.eng.framework.tray.Tray;

public class AdminBoardGate extends BaseBean {

	private AdminBoardDao dao = null;

	public AdminBoardGate() {
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
	
}
