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
	
	public AdminBoardCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doExecute();
    }
	 protected void doExecute() {
		AdminBoardGate gate = new AdminBoardGate();
		Collection collection = gate.findAll(reqTray);
		request.setAttribute("result", collection);
	  }
	    
    
    
    //등록
    public void iCTLCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
      this.request = request;
      this.response = response;
      this.reqTray = reqTray;
      
      
      if(reqTray.getString("gubun").equals("insert")) {
    	  doInsert();
      }else if(reqTray.getString("gubun").equals("update")) {
    	  doUpdate();
      }else if(reqTray.getString("gubun").equals("delete")) {
    	  doDelete();
      }
    }
    
    
    
    
    
	private void doInsert() {
		AdminBoardGate gate = new AdminBoardGate();
		boolean success = gate.insert(reqTray);
		request.setAttribute("result", success + "");
	}
	
	private void doUpdate() {
		AdminBoardGate gate = new AdminBoardGate();
		boolean success = gate.update(reqTray);
		request.setAttribute("result", success + "");
	}
	
	private void doDelete() {
		AdminBoardGate gate = new AdminBoardGate();
		boolean success = gate.delete(reqTray);
		request.setAttribute("result", success + "");
	}
		
		
		
	}



