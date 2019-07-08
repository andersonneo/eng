package com.eng.adminBoard.cmd;

import java.io.File;
import java.util.Collection;
import java.util.Date;

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
    public void iCTLCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) throws Exception {
      this.request = request;
      this.response = response;
      this.reqTray = reqTray;
      
      
      
      
      
      
      if(reqTray.getString("profileFile")!="") {
	      int i = -1;
	      String fileExt ="";//RENAME 후 파일 확장자를 위한 변수
	      String reFileName = "";
	      
	      String saveDir = com.eng.framework.util.SpmUtil.getProperty("file.upload.path"); //저장경로
	      Date nowDate = new Date();
	      String strDate = com.eng.framework.util.SpmUtil.dateCastStringType(nowDate, "yyyyMMddHHmmss");
	
	      //파일을 가져온다
	      String fileName = com.eng.framework.util.SpmUtil.getProperty("file.upload.path")+reqTray.getString("profileFile");
	      //확장자 체크, 확장자 분리
	      if(( i = reqTray.getString("profileFile").lastIndexOf(".")) > -1){
	          fileExt = reqTray.getString("profileFile").substring(i);
	      }
	      reFileName = strDate+fileExt;
	
	      //파일복사
	      File f = new File(fileName);
	      f.renameTo(new File(com.eng.framework.util.SpmUtil.getProperty("file.upload.path")+reFileName));
	
	      reqTray.setString("fileName",reFileName);
      }else {
    	  reqTray.setString("fileName","");
      }
      
      
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



