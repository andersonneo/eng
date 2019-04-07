package com.dhitech.spend.infosearchdetail.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhitech.framework.tray.Tray;
import com.dhitech.spend.infosearchdetail.gate.infosearchDetailGate;

public class infosearchDetailCmd {
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private Tray reqTray = null;
	
	public infosearchDetailCmd(){
	}
	
	//전월 데이터 복사
	public void copyCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doCopy();
    }
	private void doCopy() {
		infosearchDetailGate gate = new infosearchDetailGate();
		boolean success = gate.insert(reqTray);
        request.setAttribute("result", success + "");
	}
	
	//지출항목 리스트
	public void searchCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doSearch();
    }
	private void doSearch() {
		infosearchDetailGate gate = new infosearchDetailGate();
		Tray collection = gate.find(reqTray);
        request.setAttribute("result", collection);
	}
	//교통비 리스트
	public void searchTCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doTSearch();
    }
	private void doTSearch() {
		infosearchDetailGate gate = new infosearchDetailGate();
		Tray collection = gate.findEx(reqTray);
        request.setAttribute("result", collection);
	}
		
	//지출항목,교통비 입력
	public void iCTLCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doInsert();
    }
	private void doInsert() {
		infosearchDetailGate gate = new infosearchDetailGate();
		Tray collection = gate.findStatic(reqTray);
        request.setAttribute("result", collection);
	}
	
	//지출항목,교통비 수정
	public void uCTLCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doUpdate();
    }
	private void doUpdate() {
		infosearchDetailGate gate = new infosearchDetailGate();
		boolean success = gate.update(reqTray);
        request.setAttribute("result", success + "");
	}
	
	//지출항목,교통비 삭제
	public void dCTLCmd(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.reqTray = reqTray;
        doDelete();
    }
	private void doDelete() {
		infosearchDetailGate gate = new infosearchDetailGate();
		boolean success = gate.delete(reqTray);
        request.setAttribute("result", success + "");
	}
}