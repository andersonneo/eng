package com.eng.framework.customtag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.eng.framework.log.Log;

/**
 * <pre>
 * Title: Footer를 디스플레이한다..
 * Description: Identity Manager
 * Copyright: Copyright (c) 1996-2004 ubiware Inc. All Rights Reserved.
 * Company: www.ubi-ware.com</p>
 * </pre>
 * @author yunkidon@hotmail.com
 * @version 1.0
 */

public class Footer extends Customtag {

    public int doStartTag() throws JspException {
        try {
            out.print(getFooter()); //Footer 호출
        } catch (IOException e) {
            Log.error("ERROR", this, "태그 에러 발생 : " + e);
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    /**
     * Footer 생성
     * @return StringBuffer
     */
    private StringBuffer getFooter() {
        StringBuffer main = new StringBuffer();

        //main.append("<br><strong>Copyright (c) 2000-2007 유비웨어랩 Corporation. All Rights Reserved. </strong>");
        main.append("<br><strong>Copyright(c) 2009 LGTelecom All rights reserved. </strong>");
        return main;
    }

    public void release() {
        super.release();
    }

    public void doCatch(Throwable t) {
        super.doCatch(t);
        release();
    }
}
