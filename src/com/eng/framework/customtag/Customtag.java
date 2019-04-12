package com.eng.framework.customtag;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import com.eng.framework.exception.AppException;

public class Customtag extends TagSupport implements TryCatchFinally {
    protected PageContext pageContext;
    protected ServletRequest req;
    protected HttpServletRequest hreq;
    protected Tag parent;
    protected HttpSession hs;
    protected JspWriter out;
    protected StringBuffer sb = new StringBuffer();
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void release() {
        super.release();
        sb.delete(0, sb.length());
    }

    public void setPageContext(PageContext pg) {
        pageContext = pg;
        out = pageContext.getOut();
        req = pageContext.getRequest();
        hreq = (HttpServletRequest)pageContext.getRequest();
        hs = pageContext.getSession();
    }

    public void setParent(Tag p) {
        parent = p;
    }

    public Tag getParent() {
        return parent;
    }

    public void doCatch(Throwable t) {
        try {
            AppException ae = new AppException("CustomTag Exception", t);
            sb.append("<SCRIPT language=\"JavaScript\" src=\"/eam/js/common.js\"></SCRIPT>");
            sb.append("<script>showErrorPopup('" + ae.toHTTPParameter() + "');</script>");
            out.print(sb);
        } catch (Exception e) {
            System.out.print("태그 에러 발생 : " + e);
        }
    }

    public void doFinally() {
    }
}
