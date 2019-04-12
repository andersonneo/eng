package com.eng.framework.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eng.framework.exception.AppException;
import com.eng.framework.tray.Tray;

public interface Command {

      /**
       *
       * @param reqTray - 데몬에서 자동으로 호출되며 시스템정보와 사용자 또는 조직정보가 담겨있다.
       * @return - 성공여부
       */
     // boolean execute(Tray reqTray) throws AppException;
      String execute(Tray reqTray, HttpServletRequest request, HttpServletResponse response);
}
