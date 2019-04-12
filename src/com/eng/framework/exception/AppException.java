package com.eng.framework.exception;

import java.net.URLEncoder;

import com.eng.framework.log.Log;
import com.eng.framework.util.AhnLabUtil;

/**
 * tUBIS 애플리케이션을 구동하는 과정에서 발생하는 모든 종류의 예외를 담는 예외객체.
 * java.lang.RuntimeException을 상속받고 있으며, 시스템/프로그램 오류에 의한 예외
 * (non-business exception)와 비즈니스 로직을 어겨 발생한 예외(business exception)을
 * 모두 포괄한다.
 *
 * <p>시스템 혹은 프로그램 오류에 의한 예외의 경우, 속성값은 다음과 같이 세팅된다.</p>
 * <ul>
 * <li>에러코드는 디폴트값인 {@link #SYSTEM_ERR_CODE}</li>
 * <li>에러메시지는 디폴트값인 {@link #SYSTEM_DISPLAY_MSG}</li>
 * <li>예외의 원인객체는 해당 Throwable 인스턴스</li>
 * <ul>
 *
 * <p>비즈니스 로직을 어겨 발생한 예외의 경우, 속성값은 다음과 같이 세팅된다.</p>
 * <ul>
 * <li>에러코드는 해당 예외의 코드(ERR_MESSAGE 테이블에 존재)</li>
 * <li>에러메시지는 해당 예외의 메시지(ERR_MESSAGE 테이블에 존재)</li>
 * <li>예외의 원인객체는 대개의 경우 null</li>
 * </ul>
 *
 * 이밖에, 개별 프로그램에서 할당하는 상세에러메시지도 지정 가능하다.
 *
 * @author NUCHA
 * @version 1.0
 * @version 1.1, 2004-03-09 생성자의 인수로 전달한 문자열이 에러코드 도메인에 속할 경우
 * 해당 에러메시지 등 상세정보를 DB에서 가져오도록 수정.
 */

public class AppException extends RuntimeException {
    /**
     * tUBIS에서 발생한 에러코드임을 나타내는 식별자
     */
    public static final char TUBIS = 'T';

    /**
     * '시스템에러'의 에러코드.
     */
    public static final String SYSTEM_ERR_CODE = "X0001";

    /**
     * '시스템에러'의 에러메시지.
     */
    protected static final String SYSTEM_ERR_MSG = "시스템 에러가 발생하였습니다.";

    /**
     *
     */
    private char companyCode = TUBIS;

    /**
     * 에러코드.
     * workflow 에러의 경우에는 해당 에러코드를 가지며, system 에러의 경우에는
     * 기본으로 '시스템에러'의 에러코드를 갖는다.
     */
    private String errorCode = SYSTEM_ERR_CODE;

    /**
     * 화면에 출력될 에러메시지.
     * log에 출력될 메시지와 구별하기 위해 필요.
     */
    private String errorMessage = SYSTEM_ERR_MSG;

    /**
     * 이 예외를 발생시킨 원인이 되는 예외객체.
     * (SQLException, NullPointerException, NumberFormatException... 등)
     */
    private Throwable cause = null;

    /**
     * 화면에 출력될 에러상세메시지.
     *
     * @param errorCode
     * @return
     */
    private String errorDetail = null;
    ;

    /**
     * 주어진 에러코드와 상세내역으로부터 예외 객체를 생성하여 돌려준다.
     *
     * @deprecated			AppException의 생성자로 통합.
     * @param errorCode		에러코드
     * @param errorDetail	에러상세내역
     * @return
     */
    public static AppException getFromErrorCode(String errorCode, String errorDetail) {
        return new AppException(TUBIS, errorCode, errorDetail);
    }

    /**
     * 주어진 에러코드와 상세내역으로부터 예외 객체를 생성하여 돌려준다.
     *
     * @deprecated			AppException의 생성자로 통합.
     * @param errorCode		에러코드
     * @return
     */
    public static AppException getFromErrorCode(String errorCode) {
        return new AppException(TUBIS, errorCode);
    }

    /**
     * 주어진 문자열이 확실히 에러코드가 아닌지 판단한다.
     * (현재의 판단조건: 한글이나 white space가 들어있을 경우)
     *
     * @param msg
     * @return
     */
    private static boolean isNotErrorCode(String msg) {
        return (AhnLabUtil.containsHangul(msg) || AhnLabUtil.containsWhiteSpace(msg));
    }

    /**
     * 에러상세메시지 혹은 에러코드를 전달하여 AppException을 생성한다.
     *
     * @param msg 에러상세메시지 혹은 에러코드
     */
    public AppException(char companyCode, String msg) {
        this.companyCode = companyCode;
        initMessage(msg);
    }

    /**
     * 에러상세메시지 혹은 에러코드를 전달하여 AppException을 생성한다.
     *
     * @param msg 에러상세메시지 혹은 에러코드
     */
    public AppException(String msg) {
        this(TUBIS, msg);
    }

    /**
     * 에러상세메시지 혹은 에러코드를 전달하여 AppException을 생성한다.
     * 에러의 원인이 된 Throwable 객체를 함께 전달한다.
     *
     * @param msg 에러상세메시지 혹은 에러코드
     * @param cause	에러의 원인이 되는 Throwable 객체
     */
    public AppException(char companyCode, String msg, Throwable cause) {
        this(companyCode, msg);
        this.cause = cause;
        if (this.cause != null) {
            Log.error("ERROR", this, "AppException.cause = " + cause);
        }
    }

    /**
     * 에러상세메시지 혹은 에러코드를 전달하여 AppException을 생성한다.
     * 에러의 원인이 된 Throwable 객체를 함께 전달한다.
     *
     * @param msg 에러상세메시지 혹은 에러코드
     * @param cause	에러의 원인이 되는 Throwable 객체
     */
    public AppException(String msg, Throwable cause) {
        this(TUBIS, msg, cause);
    }

    /**
     * 에러코드 및 에러상세 메시지를 전달하여 AppException을 생성한다.
     * 에러코드임이 확실하므로 곧장 DB에서 로드한다.
     *
     * @param companyCode	회사구분코드
     * @param errorCode		에러코드
     * @param errorDetail	에러상세메시지
     */
    public AppException(char companyCode, String errorCode, String errorDetail) {
        this.companyCode = companyCode;
        this.errorCode = errorCode;
        loadMessageFromDB(errorCode);
        this.errorDetail = errorDetail;
    }

    /**
     * 에러코드 및 에러상세 메시지를 전달하여 AppException을 생성한다.
     * 에러코드임이 확실하므로 곧장 DB에서 로드한다.
     *
     * @param errorCode		에러코드
     * @param errorDetail	에러상세메시지
     */
    public AppException(String errorCode, String errorDetail) {
        this(TUBIS, errorCode, errorDetail);
    }

    /**
     * 에러코드 및 에러상세 메시지를 전달하여 AppException을 생성한다.
     * 에러코드임이 확실하므로 곧장 DB에서 로드한다.
     * 에러의 원인이 된 Throwable 객체를 함께 전달한다.
     *
     * @param errorCode		에러코드
     * @param errorDetail	에러상세메시지
     */
    public AppException(char companyCode, String errorCode, String errorDetail, Throwable t) {
        this(companyCode, errorCode, t);
        this.errorDetail = errorDetail;
    }

    /**
     * 에러코드 및 에러상세 메시지를 전달하여 AppException을 생성한다.
     * 에러코드임이 확실하므로 곧장 DB에서 로드한다.
     * 에러의 원인이 된 Throwable 객체를 함께 전달한다.
     *
     * @param errorCode		에러코드
     * @param errorDetail	에러상세메시지
     */
    public AppException(String errorCode, String errorDetail, Throwable t) {
        this(TUBIS, errorCode, errorDetail, t);
    }

    /**
     * 아무런 정보도 없는 생성자.
     * 아무런 부가정보도 주어지지 않으므로 deprecate되었음.
     *
     * @deprecated
     */
    public AppException() {
    }

    /**
     * 에러상세메시지 혹은 에러코드를 받아 메시지를 초기화한다.
     * 에러코드로 추정되는 경우에는 DB에서 메시지를 가져오고,
     * 확실히 에러코드가 아닐 경우에는 시스템 에러로 처리한다.
     *
     * @param msg 에러상세메시지 혹은 에러코드
     */
    private void initMessage(String msg) {
        if (msg == null) {
            return;
        }

        // 에러코드로 추정될 경우 이 값을 key로 에러메시지테이블을 조회
        if (!isNotErrorCode(msg)) {
            loadMessageFromDB(msg);
            // 에러코드가 아니면 이 값을 에러의 상세메시지로 할당
        } else {
            errorDetail = msg;
        }

        Log.error("ERROR", this, "AppException 발생: ");
    }

    /**
     * 주어진 에러코드에 해당하는 에러정보를 세팅한다.
     * 에러코드에 해당하는 레코드가 없을 경우, 혹은 에러정보를 가져오는 과정에서
     * 에러가 발생할 경우 시스템 에러로 처리.
     *
     * @param errorCode		에러코드
     */
    private void loadMessageFromDB(String errorCode) {

    }

    private void initAsSystemException() {
        this.companyCode = TUBIS;
        this.errorCode = SYSTEM_ERR_CODE;
        this.errorMessage = SYSTEM_ERR_MSG;
    }

    public void printStackTrace() {
        if (cause != null) {
            Log.error("", this, "예외 발생 이유: " + getMessage());
//            cause.printStackTrace();
        }
        super.printStackTrace();
    }

    /**
     * 이 예외를 발생시킨 원인이 되는 예외객체를 돌려준다.
     * (주로 try-catch 구문에서 catch된 예외객체)
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * 에러메시지 테이블상의 에러코드를 돌려준다.
     *
     * @return
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 에러메시지 테이블상의 에러메시지를 돌려준다.
     *
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 개별 프로그램에서 지정한 상세 에러메시지를 돌려준다.
     *
     * @return
     */
    public String getErrorDetail() {
        return errorDetail;
    }

    /**
     * 로그파일 출력용으로 쓰이는 문자열을 생성.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer(100);
        buf.append("에러코드=").append(errorCode).append(',');
        buf.append("에러메시지=").append(errorMessage).append(',');
        if (errorDetail != null) {
            buf.append("에러상세=").append(errorDetail).append(',');
        }
        if (cause != null) {
            buf.append("에러원인=").append(cause.getMessage());
        }
        return buf.toString();
    }

    /**
     * errorPopup.jsp로 전달할 수 있도록 HTTP parameter 형식의 문자열을 생성.
     *
     * @return
     */
    public String toHTTPParameter() {
        StringBuffer buf = new StringBuffer(100);
        buf.append("errorCode=").append(errorCode).append('&');
        buf.append("errorMessage=").append(URLEncoder.encode(errorMessage)).append('&');
        if (errorDetail != null) {
            buf.append("errorDetail=").append(URLEncoder.encode(errorDetail)).append('&');
        }
        if (cause != null) {
            buf.append("cause=").append(URLEncoder.encode(cause.toString()));
        }

        return buf.toString();
    }
}
