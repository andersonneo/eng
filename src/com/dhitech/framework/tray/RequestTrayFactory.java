/**
 * 파일명: com.kyeryong.framework.tray.RequestTrayFactory.java
 * 파일개요: 클라이언트 요청 정보를 캡슐화한 클래스
 * 저작권: Copyright (c) 2003 by SK C&C. All rights reserved.
 * 작성자: 박찬우 (nucha@dreamwiz.com)
 */

package com.dhitech.framework.tray;

//Java API
import javax.servlet.http.HttpServletRequest;

/**
 * RequestTrayFactory 클래스는 Tray 객체를 생성하는 팩토리 클래스의 상위 추상클래스이며
 * 실제 생성은 상속받은 팩토리에 위임한다.
 * <p>
 *
 * @author <b>박찬우</b>
 * @version 1.0, 2004/02/02
 */

public abstract class RequestTrayFactory {
    /**
     * Tray 객체 생성
     * @param request - HttpServletRequest
     * @return - request 객체의 내용을 가진 Tray 객체
     */
    protected abstract Tray createTray(HttpServletRequest request);

    /**
     * 생성된 Tray를 가져가는 메소드
     * @param request - HttpServletRequest
     * @return - request 객체의 내용을 가진 Tray 객체
     */
    public Tray getTray(HttpServletRequest request) {
        return createTray(request);
    }
}
