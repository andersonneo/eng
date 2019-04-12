package com.eng.login.cmd;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eng.framework.tray.Tray;
import com.eng.login.gate.LoginGate;

public class LoginCmd{
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private Tray reqTray = null;

	public LoginCmd() {
    }

	public LoginCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doExecute();
    }
    protected void doExecute() {
    	LoginGate gate = new LoginGate();
        Tray rsTray = gate.find(reqTray);
        request.setAttribute("result", rsTray);
    }
}