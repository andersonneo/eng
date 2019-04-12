package com.eng.adminBoard.cmd;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eng.adminBoard.gate.AdminBoardGate;
import com.eng.framework.tray.Tray;

public class AdminBoardCmd {
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private Tray reqTray = null;
	
	public AdminBoardCmd(){
	}
	
	public void AdminBoardCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doExecute();
    }
	 protected void doExecute() {
		 AdminBoardGate gate = new AdminBoardGate();
    	Collection collection = gate.findAll(reqTray);
        request.setAttribute("count_result", collection);
	  }
	    
	    protected void doExecute(Tray reqTray, HttpServletRequest request, HttpServletResponse response){}
	}
