package com.dhitech.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.dhitech.framework.log.Log;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class EncodingFilter implements Filter {

    private String encoding = null;
    protected FilterConfig filterConfig = null;

    /* (비Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {

        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");

    }

    /* (비Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Log.debug("", this, "request encoding => " + encoding);
        if (request.getCharacterEncoding() == null) {
            if (encoding != null) {
                request.setCharacterEncoding(encoding);
                Log.debug("", this, "request encoding => " + encoding);
            }
        }
        chain.doFilter(request, response);
    }

    /* (비Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {

        this.encoding = null;
        this.filterConfig = null;

    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig cfg) {
        this.filterConfig = cfg;
    }

}
