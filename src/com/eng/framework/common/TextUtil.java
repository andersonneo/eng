package com.eng.framework.common;

public class TextUtil {
    /**
     * 닫는 태그가 필요없는 HTML 태그 배열
     */
    public final static String[] SINGLE_TAGS = {"br", "p", "hr"};

    //~ Methods ////////////////////////////////////////////////////////////////


    /**
     * 최신 replace 알고리듬이 적용된 replace 함수
     *
     * @param subject 변경될 대상 문자열
     * @param find    찾는 문자
     * @param replace 변경할 문자
     */
    public static String replace(String subject, String find, String replace) {
            if (TextUtil.noNull(subject).trim().equals("")) return "";

            StringBuffer buf = new StringBuffer();
            int l = find.length();
            int s = 0;
            int i = subject.indexOf(find);
            while (i != -1) {
                    buf.append(subject.substring(s, i));
                    buf.append(replace);
                    s = i + l;
                    i = subject.indexOf(find, s);
            }
            buf.append(subject.substring(s));
            return buf.toString();
    }

    /**
     * XSS 방지를 위한 TAG문자 치환함수 20050811 CJH
     *
     * @param subject 변경될 대상 문자열
     */
    public static String replaceS(String subject) {
            return replace(replace(subject,"<","&#60"),">","&#62");
    }

    /**
     * Return <code>string</code>, or <code>defaultString</code> if
     * <code>string</code> 이 <code>null</code> or <code>""</code>이면
     * <code>string</code>, 또는 <code>defaultString</code>를 출력한다..
     * 절대 <code>null</code>을 리턴하지 않는다(NullPointerException 방지용).
     * <p/>
     * <p>Examples:</p>
     * <pre>
     * // "hello" 출력
     * String s=null;
     * System.out.println(TextUtils.noNull(s,"hello");
     * <p/>
     * // "hello" 출력
     * s="";
     * System.out.println(TextUtils.noNull(s,"hello");
     * <p/>
     * // "world" 출력
     * s="world";
     * System.out.println(TextUtils.noNull(s, "hello");
     * </pre>
     *
     * @param string        대상 스트링
     * @param defaultString 스트링이 <code>null</code> 이거나 <code>""</code>이면 리턴되는 문자열
     * @return 예제 참조
     * @see #stringSet(java.lang.String)
     */
    public final static String noNull(String string, String defaultString) {
            return (stringSet(string)) ? string : defaultString;
    }

    /**
     * 스트링이 <code>null</code>이면 <code>""</code>를 리턴
     * 절대 <code>null</code>을 리턴하지 않는다(NullPointerException 방지용).
     * <p/>
     * <p>Examples:</p>
     * <pre>
     * // 0 출력
     * String s=null;
     * System.out.println(TextUtils.noNull(s).length());
     * <p/>
     * // 1 출력
     * s="a";
     * System.out.println(TextUtils.noNull(s).length());
     * </pre>
     *
     * @param string 대상 스트링
     * @return null 이아닌 스트링 리턴
     */
    public final static String noNull(String string) {
            return noNull(string, "");
    }

    public final static boolean stringSet(String string) {
            return (string != null) && !"".equals(string);
    }
    public final static String SapKor(String str) throws Exception
    {
    	String chstr=null;
    	if(str!=null)
    		chstr=new String(str.getBytes("ISO-8859-1"), "euc-kr");
    	return chstr;
    }

}
