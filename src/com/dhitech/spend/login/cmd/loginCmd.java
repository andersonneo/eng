package com.dhitech.spend.login.cmd;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhitech.framework.tray.Tray;
import com.dhitech.spend.login.gate.loginGate;

public class loginCmd{
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private Tray reqTray = null;

	public loginCmd() {
    }

	public loginCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doExecute();
    }
    protected void doExecute() {
    	loginGate gate = new loginGate();
    	Connection conn = null;
        Tray rsTray = gate.find(conn, reqTray);
        request.setAttribute("result", rsTray);
    }
    
    public void loginWithoutPwCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doExecuteEx();
    }
    protected void doExecuteEx() {
    	loginGate gate = new loginGate();
    	Connection conn = null;
        Tray rsTray = gate.findEx(conn, reqTray);
        request.setAttribute("result", rsTray);
    }
    
    public void dataCheckCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doCheck();
    }
    protected void doCheck() {
    	loginGate gate = new loginGate();
    	boolean success = gate.insert(reqTray);
        request.setAttribute("result", success + "");
    }
    
}