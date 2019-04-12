package com.eng.framework.exception;

import java.rmi.RemoteException;

/**
 * Delegate의 복잡한 catch 코드를 단순화하기 위해 도입한 클래스.
 * 다음과 같은 코드를 이용하여 사용한다.
 *
 * <pre>
 * try {
 *
 * catch (특별처리가 필요한 exception) {
 * 	특별처리; // 가령, 객체의 반환, 변수값 세팅...
 * } catch (Exception ex) {
 * 	throw TubisExceptionManager.handleException("로그에 남길 디버그메시지(함수호출위치를 보여주는등)", ex);
 * } finally {
 * 	// 필요하다면
 * }
 * </pre>
 *
 * @author 진헌규
 * @version 1.1, 2004-03-08 return type이 void가 아닌 method에서 try block 내에서 return하는 경우
 * 발생하는 컴파일 에러를 해결하기 위해 handleException()의 return type을 {@link AppException}으로 변경.
 *
 * @version 1.0, 2004-03-03
 */
public class ExceptionManager {
    /**
     * 일반적인 예외를 처리한다.
     * <ul>
     * <li>AppException은 그대로 throw</li>
     * <li>그외의 Exception은 AppException으로 만들어 throw</li>
     * <li>RemoteException은 getCause()로 원인을 꺼내어 위의 룰대로 처리</li>
     * </ul>
     *
     * @param msg		로그에 남길 디버그 메시지
     * @param t			catch하고자 하는 Exception 객체
     * @return			Exception을 감싸고있는 {@link AppException} 객체
     */
    public static AppException handleException(String msg, Throwable t) throws AppException {
        if (t instanceof AppException) {
            throw (AppException) t;
        } else if (t instanceof RemoteException) {
            Throwable cause = ((RemoteException) t).getCause();
            if (cause instanceof AppException) {
                throw (AppException) cause;
            } else {
                throw new AppException(msg, cause);
            }
        } else {
            throw new AppException(msg, t);
        }
    }
}
