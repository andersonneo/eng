package com.dhitech.spend.getduzon.gate;

import java.sql.Connection;

import com.dhitech.framework.business.BaseBean;
import com.dhitech.framework.exception.AppException;
import com.dhitech.framework.exception.ExceptionManager;
import com.dhitech.framework.log.Log;
import com.dhitech.framework.tray.Tray;
import com.dhitech.spend.getduzon.dao.GetDuzonDao;

public final class GetDuzonGate extends BaseBean {
	private GetDuzonDao dao = null;

	public GetDuzonGate() {
		init();
	}

	private void init() {
		try {
			dao = new GetDuzonDao();
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}
	
	public Tray find(Connection conn, Tray reqTray) throws AppException {
		try {
			return dao.find(conn);
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ExceptionManager.handleException(this.getClass().getName(), ex);
		}
	}	
}
