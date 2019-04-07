package com.dhitech.spend.accountsubject.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhitech.framework.tray.Tray;
import com.dhitech.spend.accountsubject.gate.accountSubjectGate;

public class accountSubjectCmd {
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private Tray reqTray = null;
	
	public accountSubjectCmd(){
	}
	
	/*
	public void iCTLCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doInsert();
    }
	private void doInsert() {
		infosearchGate gate = new infosearchGate();
		boolean success = gate.insert(reqTray);
        request.setAttribute("result", success + "");
	}
	*/
	
	public void searchCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doSearch();
    }
	private void doSearch() {
		accountSubjectGate gate = new accountSubjectGate();
		Tray collection = gate.find(reqTray);
        request.setAttribute("result", collection);
	}
}
