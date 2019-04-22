package com.eng.framework.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class XSSDetectFilter implements Filter {

    protected FilterConfig filterConfig = null;

    private String[] badChars = null;
    private String[] exceptURL = null;
    private String redirectUrl = null;
    private String encoding = null;
    

    public void destroy() {
        this.filterConfig = null;
        this.encoding = null;
        this.redirectUrl = null;
        this.badChars = null;
        this.exceptURL = null;
        
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        boolean isBad = false;
        boolean isIgnore = false;

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        request.setCharacterEncoding(encoding);

        String uri = req.getRequestURI();
        String ext = "";
        if(uri.length() > 3){
        	ext = uri.substring(uri.length()-3, uri.length());
        }
        if(!ext.equals("jsp")){ 
            isIgnore = true;
        }
        
        //09.02.12 by jin add
        if( uri != null){
            for(int i=0; i<exceptURL.length; i++){
                if(uri.indexOf(exceptURL[i])>=0){
                	isIgnore = true;
                    break;
                }
            }
        }

        if(!isIgnore){
            Enumeration enum1 = request.getParameterNames();
            while (enum1.hasMoreElements()) {
            	String paramName = (String)enum1.nextElement();
                String value = request.getParameter(paramName);
                for(int i=0; i<badChars.length; i++){              	
                    if(value.indexOf(badChars[i])>=0){
                        isBad = true;
                        break;
                    }
                }
                if(isBad){
                    break;
                }
            }
            
            if(isBad){
                res.sendRedirect(redirectUrl);
            }else{
                chain.doFilter(request, response);
            }
            
        }else{
           chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        badChars = (filterConfig.getInitParameter("BadChars")+"<,>").split(","); //09.02.12 comment jin
        redirectUrl = filterConfig.getInitParameter("RedirectUrl");
        encoding = filterConfig.getInitParameter("Encoding");
        exceptURL = (filterConfig.getInitParameter("ExceptURL")).split(","); //09.02.12 add jin 
    }
}
