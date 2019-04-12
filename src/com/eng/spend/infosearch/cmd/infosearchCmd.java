package com.eng.spend.infosearch.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eng.framework.tray.Tray;
import com.eng.spend.infosearch.gate.infosearchGate;

public class infosearchCmd {
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private Tray reqTray = null;
	
	public infosearchCmd(){
	}
	
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
	
	public void uCTLCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doUpdate();
    }
	private void doUpdate() {
		infosearchGate gate = new infosearchGate();
		boolean success = gate.update(reqTray);
        request.setAttribute("result", success + "");
	}
	
	public void uExCTLCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doUpdateEx();
    }
	private void doUpdateEx() {
		infosearchGate gate = new infosearchGate();
		boolean success = gate.updateEx(reqTray);
        request.setAttribute("result", success + "");
	}
		
	public void searchCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doSearch();
    }
	private void doSearch() {
		infosearchGate gate = new infosearchGate();
		Tray collection = gate.find(reqTray);
        request.setAttribute("result", collection);
	}
}
