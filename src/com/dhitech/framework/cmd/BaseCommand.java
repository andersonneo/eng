/**
 * 파일명: com.ubiware.framework.cmd.BaseCommand.java
 * 파일개요: 클라이언트 Action을 캡슐화하기 위한 추상 클래스
 * 저작권: Copyright (c) 2003 by SK C&C. All rights reserved.
 * 작성자: 박찬우 (nucha@dreamwiz.com)
 */

package com.dhitech.framework.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dhitech.framework.exception.AppException;
import com.dhitech.framework.log.Log;
import com.dhitech.framework.tray.ResultSetTray;
import com.dhitech.framework.tray.Tray;

/**
 * Actor의 Action을 처리하기 위한 추상 클래스
 * Actor가 보낸 전송 데이터의 유효성 체크를 한 후, 서비스 호출을 실행하는 흐름을 제어한다.
 * @author <b>박찬우</b>
 * @version 1.0, 2004/01/29
 */

public abstract class BaseCommand implements Command {
    //초기 메인 화면의 각 컴포넌트를 디폴트값으로 채울 때 사용하기 위한 텅 빈 결과셋
    private static Tray EMPTY_RESULT_SET_TRAY = new ResultSetTray();

    //포워딩될 다음 페이지
    private String nextPage = null;
    private HttpServletRequest req = null;

    /**
     * 기본 생성자
     */
    public BaseCommand() {}

    /**
     * 클라이언트의 요청을 처리하기 위해 서비스를 호출하고 그 결과를 request에 저장하며 포워딩 될 다음 페이지를 리턴한다.
     * 이 메소드는 하위 클래스의 흐름을 제어하는 템플릿 메소드이다.
     *
     * @param reqTray - 클라이언트가 보낸 폼 데이터를 key-value 기반으로 저장한 객체
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     * @return - 포워딩될 다음 화면(JSP)
     */
    public final String execute(Tray reqTray, HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        this.req = request;
        try {
            boolean isCommonValidate = doCommonValidate(reqTray, request);
            if (!isCommonValidate) {
                return getNextPage();
            }

            doExecute(reqTray, request, response);
        } catch (AppException ex) {
            Log.warn("", this, "BaseCommand.execute(): 최종적으로 exception catch. exception = " + ex.toString());
            request.setAttribute("exception", ex);
        } catch (Throwable t) {
            request.setAttribute("exception", new AppException("Command에서 일반 예외 발생", t));
        } finally {
            long endTime = System.currentTimeMillis();
        }

        //포워딩될 다음 페이지 리턴
        return getNextPage();
    }

    /**
     * 사용자의 요청을 처리해줄 서비스를 호출하고 그 결과를 request에 저장한다.
     *
     * @param tray
     * @param request
     * @param response
     * @return
     */
    protected abstract void doExecute(Tray reqTray, HttpServletRequest request, HttpServletResponse response);

    /**
     * 포워딩될 다음 페이지를 지정하는 메소드
     * @param nextPage - 포워딩될 다음 페이지
     */
    protected void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * 초기 화면을 디폴트 값으로 채워 보여줄 때 필요한 텅 빈 결과셋을 반환하는 메소드
     * @return
     */
    protected Tray getEmptyResultSetTray() {
        return EMPTY_RESULT_SET_TRAY;
    }

    /**
     * 포워딩 될 다음 페이지를 반환하는 메소드
     * @return - 서비스 호출한 결과를 다룰 페이지(JSP)
     * @throws com.dhitech.framework.exception.paos.framework.exception.AppException - 다음 페이지를 지정 안하면 예외 발생
     */
    private String getNextPage() throws AppException {
        if (nextPage == null) {
            throw new AppException("하위 Command에서 next page를 지정하지 않았습니다.");
        }
        return nextPage;
    }

    /**
     * 공통적인 유효성 체크를 한다.
     * @param reqTray -
     * @param request - HttpServletRequest
     * @return
     */
    private boolean doCommonValidate(Tray reqTray, HttpServletRequest request) {

        return true;
    }

    private Tray getSessionTray(HttpServletRequest request, String sessionKey) {
        HttpSession httpSession = request.getSession(false);

        if (httpSession == null) {
            throw new AppException("HttpSession이 null");
        }

        return (Tray) httpSession.getAttribute(sessionKey);
    }
}
