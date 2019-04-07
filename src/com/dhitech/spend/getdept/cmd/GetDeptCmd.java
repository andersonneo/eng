package com.dhitech.spend.getdept.cmd;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhitech.framework.tray.Tray;
import com.dhitech.spend.getdept.gate.GetDeptGate;

public class GetDeptCmd {
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private Tray reqTray = null;
	
	public GetDeptCmd(){
	}
	
	public void searchCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doSearch();
    }
	private void doSearch() {
		GetDeptGate gate = new GetDeptGate();
		Connection conn = null;
        Tray rsTray = gate.find(conn, reqTray);
        request.setAttribute("result", rsTray);
	}
}
