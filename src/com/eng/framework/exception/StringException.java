package com.eng.framework.exception;

import org.apache.taglibs.bsf.expression;

public class StringException extends expression {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public static String getStackTrace(Throwable t) {
		StringBuilder buf = new StringBuilder(t.toString()).append("\n");
		StackTraceElement[] ele = t.getStackTrace();
		for (int i = 0x00; i < ele.length; i++)
			buf.append(ele[i].toString()).append("\n");
		return buf.toString();
	}
}
