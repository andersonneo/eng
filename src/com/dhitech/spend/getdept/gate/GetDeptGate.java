package com.dhitech.spend.getdept.gate;

import java.sql.Connection;

import com.dhitech.framework.business.BaseBean;
import com.dhitech.framework.exception.AppException;
import com.dhitech.framework.exception.ExceptionManager;
import com.dhitech.framework.log.Log;
import com.dhitech.framework.tray.Tray;
import com.dhitech.spend.getdept.dao.GetDeptDao;

public final class GetDeptGate extends BaseBean {
	private GetDeptDao dao = null;

	public GetDeptGate() {
		init();
	}

	private void init() {
		try {
			dao = new GetDeptDao();
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
}
