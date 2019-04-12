package com.eng.spend.getduzon.gate;

import java.sql.Connection;

import com.eng.framework.business.BaseBean;
import com.eng.framework.exception.AppException;
import com.eng.framework.exception.ExceptionManager;
import com.eng.framework.log.Log;
import com.eng.framework.tray.Tray;
import com.eng.spend.getduzon.dao.GetDuzonDao;

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
