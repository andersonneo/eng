/**
 * 파일명: com.kyeryong.framework.tray.TubisRequestTrayFactory.java
 * 파일개요: 클라이언트 요청 정보를 캡슐화한 RequestTray를 생성하는 팩토리 클래스
 * 저작권: Copyright (c) 2003 by SK C&C. All rights reserved.
 * 작성자: 박찬우 (nucha@dreamwiz.com)
 */

package com.eng.framework.tray;

//Java API
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * TubisRequestTrayFactory 클래스는 클라이언트가 보낸 정보를 Tray 객체에 저장하고 리턴한다.
 * <p>
 *
 * @author <b>박찬우</b>
 * @version 1.0, 2004/02/02
 */

public class DhitechRequestTrayFactory extends RequestTrayFactory {

    /**
     * request 객체에서 정보를 꺼내 Tray 객체에 담아 리턴한다.
     * @param request - HttpServletRequest
     * @return
     */
    protected Tray createTray(HttpServletRequest request) {
        Tray tray = new RequestTray();
        Enumeration namesEnum = request.getParameterNames();
        while (namesEnum.hasMoreElements()) {
            String name = (String) namesEnum.nextElement();
            String[] values = request.getParameterValues(name);
//      if(values!=null){
//        for(int i=0;i<values.length;i++){
//         //values[i]= StringUtil.convertToKR(values[i]);
//         Log.debug("\n"+values[i]+"==>"+StringUtil.convertToKR(values[i])+"<======>"+StringUtil.convertFromKR(values[i]));
//        }
//      }
            tray.set(name, values);
        }

        return tray;
    }
}
