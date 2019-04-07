package com.dhitech.framework.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.Vector;
import java.math.BigDecimal;

import com.dhitech.framework.exception.AppException;
import com.dhitech.framework.log.Log;

/**
 * <p>Title: String Utility</p>
 * <p>Description: </p>

 * @author
 * @version 1.0
 */

public final class StringUtil {

    private static final String LT = "&lt;";
    private static final String GT = "&gt;";
    private static final String QUOT = "&quot;";
    private static final String AMP = "&amp;";
    public static final String HTML_SPACE = "&nbsp;";
    public static final String NULL = "";
    public static final String SPACE = " ";
    public static final String BR = "<br>";

    /**
	 * Don't let anyone instantiate this class
	 */
	private StringUtil() {

	}
	
	/**
    * 문자열을 바이트 크기에 맞게 수정 후 줄임말 표시(...)를 붙입니다.
    *
    * @param str 수정 될 문자열.
    * @param i 크기 인덱스
    * @return 수정 된 문자열
    */
   public static String cropByte(String str, int i) {
       if (str == null)
           return "";

       int slen = 0, blen = 0;
       char c;

       if (str.getBytes().length > i) {
           while (blen + 1 < i) {
               c = str.charAt(slen);
               blen++;
               slen++;
               if (c > 127)
                   blen++; //2-byte character..
           }
           str = str.substring(0, slen) + "...";
       }
       return str;
   }

	   /**
     * <pre>
     * 문자열이 null인지 체크한다.
     * </pre>
     * @param   str         문자열
     * @return  null이면 "", 아니면 그대로 리턴
     */
    public static String nchk(String str) {
        return nchk(str, "");
    }   
    
    /**
     * <pre>
     * 문자열이 null이면 대치할 문자열을 리턴한다.
     * </pre>
     * @param   str         문자열
     * @param   replaceStr  대치할 문자열
     * @return  null이면 replaceStr, 아니면 그대로 리턴
     */
    public static String nchk(String str, String defaultStr) {
        return (str==null)?defaultStr:str;
    }
    
    static public final String funcText2Entity(String ostr)	{
        StringBuffer tmp = new StringBuffer();
        int olen = ostr.length();
        if(ostr==null) ostr=NULL;

        for(int i=0; i<olen;i++) {
            if(ostr.charAt(i) == '<') tmp.append(LT);
            else if(ostr.charAt(i) == '>') tmp.append(GT);
            else if(ostr.charAt(i) == '"') tmp.append(QUOT);
              else if(ostr.charAt(i) == '&') tmp.append(AMP);
            else if(ostr.charAt(i) == ' ') {
                    if(i+1<olen && ostr.charAt(i+1) == ' ')
                            tmp.append(HTML_SPACE);
                    else
                            tmp.append(ostr.charAt(i));
            }
            else if(ostr.charAt(i) == '\n') tmp.append(BR);
            else if(ostr.charAt(i) == '\r') tmp.append(SPACE);
            else tmp.append(ostr.charAt(i));
        }

        return tmp.toString();
    }

    public static String checkTag(String ostr) {
        StringBuffer tmp = new StringBuffer();
        int olen = ostr.length();
        if(ostr==null) ostr=NULL;

        for(int i=0; i<olen;i++) {

            if(ostr.charAt(i) == '<') tmp.append(LT);
            else if(ostr.charAt(i) == '>') tmp.append(GT);
            else tmp.append(ostr.charAt(i));

        }
        return tmp.toString();
    }

    /**
     * 8859_1 --> KSC5601
     * @param english 변환할 문자열
     * @return KSC5601로 변환된 문자열
     */
	public static synchronized String E2K(String english) {
		String korean = null;
		if (english == null ) return null;
		try {
			korean = new String(new String(english.getBytes("8859_1"), "KSC5601"));
		} catch( UnsupportedEncodingException e ) {
			korean = new String(english);
		}
		return korean;
	}

	/**
	 * KSC5601 --> 8859_1.
	 */
	public static synchronized String K2E( String korean )
	{
		String english = null;

		if (korean == null ) return null;
		//if (korean == null ) return "";

		english = new String(korean);
		try {
			english = new String(new String(korean.getBytes("KSC5601"), "8859_1"));
		}
		catch( UnsupportedEncodingException e ){
			english = new String(korean);
		}
		return english;
	}


    /**
     *Entity Class의 string field character conversion
     *K2E KSC5601-->8859-1
     */
    public static void fixK2E(Object o) {
        if (o==null) return;

        Class c = o.getClass();
        if(c.isPrimitive()) return;

        Field[] fields = c.getFields();
        for(int i=0;i<fields.length;i++) {
            try {
                Object f  = fields[i].get(o);
                Class  fc = fields[i].getType();
                if(fc.getName().equals("java.lang.String")) {
                    fields[i].set(o,StringUtil.K2E((String)f));
                }
            }
            catch(Exception e) {
            }
        }//end for
    }

    /**
     *Entity Class의 string field character conversion
     *E2K 8859-1-->KSC5601
     */
    public static void fixE2K(Object o) {
        if (o==null) return;

        Class c = o.getClass();
        if(c.isPrimitive()) return;

        Field[] fields = c.getFields();
        for(int i=0;i<fields.length;i++) {
            try {
                Object f  = fields[i].get(o);
                Class  fc = fields[i].getType();
                if(fc.getName().equals("java.lang.String")) {
                    fields[i].set(o,StringUtil.E2K((String)f));
                }
            }
            catch(Exception e) {
            }
        }//end for
    }



    /**
     *Entity Class의 null string field 초기화
     <p>
     Entity Class 내에 있는 java.lang.String형의 field변수 중
     값이 null인 필드를 길이가 0인 blank string으로 초기화 시켜준다.
     <p>
     */
    public static void fixNull(Object o) {
         if (o==null) return;

         Class c = o.getClass();
         if(c.isPrimitive()) return;

         Field[] fields = c.getFields();
         for(int i=0;i<fields.length;i++) {
             try {
                 Object f = fields[i].get(o);
                 Class fc = fields[i].getType();

                 if (fc.getName().equals("java.lang.String")) {
                     if (f==null) fields[i].set(o,"");
                     else fields[i].set(o,f);
                 }
             }
             catch(Exception e) {

             }
         }
    }


    /**
     *Entity Class의 null string field 초기화
     <p>
     Entity Class 내에 있는 java.lang.String형의 field변수 중
     값이 null인 필드를 길이가 0인 blank string으로 초기화 시켜주고 insert/update sql문을 위해
     "'" --> "''"로 변환시켜 준다..
     <p>
     */
    public static void fixNullAndSql(Object o) {

         if (o==null) return;

         Class c = o.getClass();
         if(c.isPrimitive()) return;

         Field[] fields = c.getFields();
         for(int i=0;i<fields.length;i++) {
             try {
                 Object f = fields[i].get(o);
                 Class fc = fields[i].getType();

                 if (fc.getName().equals("java.lang.String")) {
                     if (f==null) fields[i].set(o,"");
                     else fields[i].set(o,replace((String)f,"'","''"));
                 }
             }
             catch(Exception e) {

             }
         }
    }

    /**
     *스트링이 null이면 "" return 하며 null이 아니면 ' --> ''로 변환
     */
    public static String fixNullAndSql(String s) {
        if (s==null) return "";
        else return replace(s,"'","''");
    }

    /**
     *스트링이 null이면 "" return
     */
    public static String fixNull(String s) {
        if (s==null) return "";
        else return s;
    }

    /**
     *스트링이 null이면 "" return
     */
    public static String fixNull(String s, String s2) {
        if (s==null) return s2;
        else return s;
    }


    /**
     *
     */
    public static void fixNullAndTrim(Object o) {
        if (o==null) return;

        Class c = o.getClass();
        if (c.isPrimitive()) return;

        Field[] fields = c.getFields();

        for(int i=0;i<fields.length;i++) {
            try {
                Object f = fields[i].get(o);
                Class fc = fields[i].getType();
                if(fc.getName().equals("java.lang.String")) {
                    if (f==null) fields[i].set(o,"");
                    else {
                        String item = StringUtil.trim((String)f);
                        fields[i].set(o,item);
                    }
                }
            }
            catch(Exception e) {}
        }//end for
    }//end fixNullAndTrim


	/**
	 * Entity Class의 재귀적인 null string field 초기화.
	 * <p>
	 * fixNull() 과 유사한 기능을 하는데, java.lang.String field 뿐만 아니라
	 * Member 변수 중 Array, Object 가 있으면 재귀적으로 \uFFFDi아 가서 String형을
	 * blank string("")으로 만들어 준다.<br>
	 * 정상적인 String인 경우 trim()을 시켜준다.<br>
	 * 만약 Array나, Vector가 null일 경우 Instance화는 하지 않는다.
	 *
	 * <p>
	 * 재귀적으로 추적되는 만큼, 부모와 자식간에 서로 양방향 reference를 갖고 있으면
	 * 절대 안된다. Stack Overflow를 내며 JVM을 내릴 것이다.
	 *
	 *
	 * @param java.lang.Object Object내의 public String 형뿐만 아니라, Object[], Vector 등과
	 *        같은 public Object형 Member Variable에 영향을 준다.
	 *
	 * @see fixNull(java.lang.Object)
	 * @see fixNullAndTrim(java.lang.Object)
	 * @see fixNullAndTrimAll(java.lang.Object)
	 *
	 */
	public static void fixNullAll(Object o)
	{
		if ( o == null ) return;

		Class c = o.getClass();
		if ( c.isPrimitive() ) return;

		if( c.isArray() ) {
			int length = Array.getLength(o);
			for(int i=0; i<length ;i++){
				Object element = Array.get(o, i);
				StringUtil.fixNullAll(element);
			}
		}
		else {
			Field[] fields = c.getFields();
			for (int i=0 ; i<fields.length; i++) {
				try {
					Object f = fields[i].get(o);
					Class fc = fields[i].getType();
					if ( fc.isPrimitive() ) continue;
					if ( fc.getName().equals("java.lang.String") ) {
						if ( f == null ) fields[i].set(o, "");
						else	fields[i].set(o, f);
					}
					else if ( f != null ) {
						StringUtil.fixNullAll(f);
					}
					else {} // Some Object, but it's null.
				}
				catch(Exception e) {
				}
			}
		}
	}




    public static String trim(String s) {
        int st = 0;
        char[] val = s.toCharArray();
        int count = val.length;
        int len = count;

        while ((st<len) && ((val[st]<=' ') || (val[st] == ' '))) st++;
        while ((st<len) && ((val[len-1]<=' ') || (val[len-1]==' '))) len--;

        return ((st>0 || (len<count)) ? s.substring(st,len) : s);
    }


	/**
	 *
	 * @param e java.lang.Throwable
	 */
	public static String getStackTrace(Throwable e) {
		java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
		java.io.PrintWriter writer = new java.io.PrintWriter(bos);
		e.printStackTrace(writer);
		writer.flush();
		return bos.toString();
	}


	/**
	 * 특정문자열을 변환하는 함수
	 * 사용예: replace("abcdefgabc","abc","111") 결과:"111def111"
	 */
    public  static String replace(String src, String from, String to ) {
        if ( src == null ) return null;
        if ( from == null ) return src;
        if ( to == null ) to = "";
        StringBuffer buf = new StringBuffer();
        for(int pos; (pos = src.indexOf(from)) >= 0; ) {
          buf.append(src.substring(0,pos));
          buf.append(to);
          src = src.substring(pos+from.length());
        }
        buf.append(src);
        return buf.toString();
    }


    /**
     *실시간 입찰(사용자,관리자) 실시간 data update 부분에
     *javaScript 의 " "안에 들어가면 안될 문자를 바꿔준다.
     */
    public static String toInnerHtml(String src) {

        if (src==null)
            return "";

        String result = src.trim();

        //\를 \\로 변환
        result = replace(result,"\\","\\\\");
        //"를 \"로 변환
        result = replace(result,"\"","\\\"");

        return result;

    }

    /**
     *len 길이만큼의 문자를 왼쪽부터 채운다.<br>
     *getFilledString("123","0",5) --> "00123"
     *
     */
    public static String getFilledString(String src, String value, int len) {

        StringBuffer result = new StringBuffer();

        if (len<=src.length()) return src;

        for (int i=0;i<len-src.length();i++) {
            result.append(value);
        }
        result.append(src);

        return result.toString();

    }

    /**
     *beLeft : true : 왼쪽부터 채운다. false : 오른쪽부터 채운다.
     *
     */
    public static String getFilledString(String src, String value, int len, boolean beLeft) {

        if (beLeft) return getFilledString(src,value,len);

        StringBuffer result = new StringBuffer();

        if (len<=src.length()) return src;

        result.append(src);
        for (int i=0;i<len-src.length();i++) {
            result.append(value);
        }

        return result.toString();

    }


    /**
     *숫자로 변경가능하면 숫자를 reutrn 하며 변경 불가능 한경우에는 "0"을 return 한다.
     */
    public static long toLong(String value) {
        try {
            return Long.parseLong(value);
        }
        catch (Exception e) {
            return 0;
        }
    }

    /**
     *금액format return ex) 5000000 --> 5,000,000
     */
    public static String toAmount(String  amount) {

        if (amount==null) return "";
        if (amount.length()==0) return "";

        String tmpAmt = new Long(amount.trim()).toString();


        StringBuffer buf = new StringBuffer();
        StringBuffer result = new StringBuffer();
        if (tmpAmt.length() == 0) return "0";

        for(int i=1;i<=tmpAmt.length();i++) {
            buf.append(tmpAmt.charAt(tmpAmt.length()-i));
            if ((i % 3)==0 && i<tmpAmt.length()) buf.append(",");
        }

        for(int i=1;i<=buf.toString().length();i++) {
            result.append(buf.toString().charAt(buf.toString().length()-i));
        }

        return result.toString();
    }


    public static String toAmount(int amount) {

        return toAmount(new Integer(amount).toString());

    }

    public static String toAmount(long amount) {

        return toAmount(new Long(amount).toString());

    }

    /**
     *금액을 한글형으로 만들어 RETURN 시켜준다.
     */
    public static String toKorAmt(String amt) {


        String price  = "";
        String danwee = "";
        char flag = 'N';

        for(int i=1;i<=amt.length();i++) {

            int j = Integer.parseInt(String.valueOf(amt.charAt(amt.length()-i)));

            switch(j) {
                case 1: danwee="일";break;
                case 2: danwee="이";break;
                case 3: danwee="삼";break;
                case 4: danwee="사";break;
                case 5: danwee="오";break;
                case 6: danwee="육";break;
                case 7: danwee="칠";break;
                case 8: danwee="팔";break;
                case 9: danwee="구";break;
                case 0: continue;
            }

            if (i==1) {
                price = danwee;
                continue;
            }


            switch((i-1)%4) {
                case 1:
                    danwee += "십";
                    break;
                case 2:
                    danwee += "백";
                    break;
                case 3:
                    danwee += "천";
                    break;
                case 0:
                    //continue;
            }

            if (i>4 && i<9 && flag!='M') {
                danwee += "만";
                flag = 'M';
            }

            if (i>8 && flag!='U') {
                danwee += "억";
                flag = 'U';
            }

            price = danwee + price ;
        }

        return price + "원";

    }//end toKorAmt



   /**
    * 화면 하단에 표시할 페이지 표시를 문자열로 되돌린다.
    *
    * @param   iTotalRow		총 Data 건수
	* @param   iCurrPageNo		현재 페이지
	* @param   iRowPerPage		한페이지당 출력되는 Row수
	* @param   iPageSplitCnt	페이지 표시될 페이지번호 수
	* @param   sURL				link에 표시될 문자열
    * @return  String			페이지 표시 문자열
    */
	public static String getPageSplitList(int iTotalRow, int iCurrPageNo, int iRowPerPage, int iPageSplitCnt, String sURL) {

		// 총 페이지 수
		int iPageTotalNo = 0;

		// 시작 페이지
		int iStartCnt = 0;

		// 끝 페이지
		int iEndCnt = 0;

		// 리턴값 저장
		StringBuffer retVal = new StringBuffer();

		if (iTotalRow < 0) iTotalRow = 0;

		iPageTotalNo = (iTotalRow % iRowPerPage != 0 ? iTotalRow / iRowPerPage + 1 : iTotalRow / iRowPerPage);

		iStartCnt = ( (iCurrPageNo % iPageSplitCnt) == 0 ? ( (iCurrPageNo / iPageSplitCnt) - 1 ) * iPageSplitCnt + 1: (iCurrPageNo / iPageSplitCnt) * iPageSplitCnt + 1);

		iEndCnt = iStartCnt + iPageSplitCnt;

		if (iEndCnt>iPageTotalNo) iEndCnt = iPageTotalNo + 1;

		if ((iStartCnt != 1) && (iCurrPageNo <= iPageTotalNo)) {
			retVal.append("<A HREF='" + sURL + (iStartCnt - 1) + "'>◀</A>&nbsp;");
		}

		for (int j=iStartCnt;j<iEndCnt;j++)	{

			if ( j == iCurrPageNo) {
				retVal.append("&nbsp;[<b>" + j + "</b>]&nbsp;");
			} else {
				retVal.append("<A HREF='" + sURL + j + "'>&nbsp;[" + j + "]&nbsp;</A>");
			}
		}

		if (iPageTotalNo > (iEndCnt - 1)) {
			retVal.append("<A HREF='" + sURL + iEndCnt + "'>▶</A>");
		}

		return retVal.toString();
	}


	/**
    * 화면 하단에 표시할 페이지 표시를 문자열로 되돌린다.
    * Get방식이 아닌 Post 방식으로 넘긴다.
    *
    * @param   totalRow		    총 Data 건수
	* @param   currPage		    현재 페이지
	* @param   listPerPage		한페이지당 출력되는 Row수
	* @param   sURL				link에 표시될 문자열(넘길 주소)
	* @param   formObj		    form name
	* @param   pageObj   		page 정보가 들어있는 element
    * @return  String			페이지 표시 문자열
    */
    public static String getPageSplitList(int iTotalRow, int iCurrPageNo, int iRowPerPage, int iPageSplitCnt, String formObj, String pageObj) {

        if (iTotalRow == 0)
            return "";

        StringBuffer buffer = new StringBuffer();

        buffer.append("<SCRIPT LANGUAGE=JAVASCRIPT>\n");
        buffer.append("function goPage(pageNum) {\n");
        buffer.append("     var form = ").append(formObj).append(";\n");
        //buffer.append("	alert(pageNum);");
        //buffer.append("	alert(form);");
        //buffer.append("	alert(form.FCODE.value);");
        buffer.append("     var pageElm = form.").append(pageObj).append(";\n");
        buffer.append(" 	pageElm.value = pageNum; \n");
        buffer.append("     form.submit() ; \n");
        buffer.append("}\n");
        buffer.append("</SCRIPT>\n\n");


		// 총 페이지 수
		int iPageTotalNo = 0;

		// 시작 페이지
		int iStartCnt = 0;

		// 끝 페이지
		int iEndCnt = 0;


		if (iTotalRow < 0) iTotalRow = 0;

		iPageTotalNo = (iTotalRow % iRowPerPage != 0 ? iTotalRow / iRowPerPage + 1 : iTotalRow / iRowPerPage);

		iStartCnt = ( (iCurrPageNo % iPageSplitCnt) == 0 ? ( (iCurrPageNo / iPageSplitCnt) - 1 ) * iPageSplitCnt + 1: (iCurrPageNo / iPageSplitCnt) * iPageSplitCnt + 1);

		iEndCnt = iStartCnt + iPageSplitCnt;

		if (iEndCnt>iPageTotalNo) iEndCnt = iPageTotalNo + 1;

		if ((iStartCnt != 1) && (iCurrPageNo <= iPageTotalNo)) {
			buffer.append("<A HREF='JavaScript:goPage(" + (iStartCnt - 1) + ");'>◀</A>&nbsp;");
		}

		for (int j=iStartCnt;j<iEndCnt;j++)	{

			if ( j == iCurrPageNo) {
				buffer.append("&nbsp;[<b>" + j + "</b>]&nbsp;");
			} else {
				buffer.append("<A HREF='JavaScript:goPage(" + j + ");'>&nbsp;[" + j + "]&nbsp;</A>");
			}
		}

		if (iPageTotalNo > (iEndCnt - 1)) {
			buffer.append("<A HREF='JavaScript:goPage(" + iEndCnt + ");'>▶</A>");
		}


		return buffer.toString();
    }


    /**
    * 입력문자열을 구분자기준으로 잘라서 배열로 되돌린다.<BR>
    *
    * @param   strIn        입력문자열
    * @param   chrSeprator  구분문자자
    * @return  retArrary    구분자로 분리된 배열
    */
	public static String[] cutStringToArray(String inStr, char chrSeprator){

		int curIndex = 0;
        int endIndex = 0;

		String[] retArrary = null;

		if(inStr==null || inStr.length()<1){
		     retArrary=new String[0];
		     return retArrary;
		}

		int arrCount=countCharCode(inStr,chrSeprator);

        if(arrCount<1){
		     retArrary=new String[0];
		     return retArrary;
		}

		retArrary=new String[arrCount];

		for(int i=0;i<arrCount;i++){

            curIndex=inStr.indexOf(chrSeprator,endIndex);

			if(curIndex==-1){
				retArrary[i]=inStr.substring(endIndex);
			} else {
				retArrary[i]=inStr.substring(endIndex,curIndex);
			}

			endIndex=curIndex+1;
		}

		return retArrary;
	}

    /*
    * 입력문자열을 구분자기준으로 배열화하기위해 배열수를 Count하여 되돌린다.
    *
    * @param   strIn        입력문자열
    * @param   chrSeprator  구분문자자
    * @return  retCnt       구분자로 분리된 갯수
    */
	public static int countCharCode(String inStr,char chrSeperator){

		int retCnt=0;

        if(inStr.trim().equals("")){
			return 0;
		}

		for(int i=0;i<inStr.length();i++){

            if(inStr.charAt(i)==chrSeperator){
				retCnt++;
			}
		}

		return ++retCnt;
	}


	/**
	 * '문자를 "문자로 변환,<br>
	 * \문자를 문자로 변환,<br>
	 * 문자를 \\문자로 변환한다.<br>
	 *
	 * @param	chr	변경하고자 하는 원본 문자열
	 * @return	변경후 문자열
	 */
	static public String chkStr(String chr)
	{
		while (chr.indexOf("'")!=-1){
			String tmp = chr;
			chr = tmp.substring(0,tmp.indexOf("'"));
			chr += "\"";
			chr += tmp.substring(tmp.indexOf("'")+1,tmp.length());
		}

		while (chr.indexOf("\\")!=-1){
			String tmp2 = chr;
			chr = tmp2.substring(0,tmp2.indexOf("\\"));
			chr += "";
			chr += tmp2.substring(tmp2.indexOf("\\")+1,tmp2.length());
		}

		while (chr.indexOf("")!=-1){
			String tmp2 = chr;
			chr = tmp2.substring(0,tmp2.indexOf(""));
			chr += "\\\\";
			chr += tmp2.substring(tmp2.indexOf("")+1,tmp2.length());
		}

		return chr;
	}


    /**
     * Vector 의 String 요소를 sql의 in 구문에 맞게 만들어 준다.
     * ex) 'aaa','bbb'
     */
    public static String getInSql(Vector items) {
        if (items.size()==0)
            return "";

        String sql = "";

        for(int i=0;i<items.size();i++) {
            sql += "'"  + (String)items.elementAt(i) + "',";
        }

        return sql.substring(0,sql.length()-1);
    }


    /**
     * 7109201105714 ==> '71' , '9', '20' 로 변환하여 return
     */
    public static String[] splitBirthDay(String personal_id) {

        String[] returnVal = {"","",""};

        if(personal_id.length() != 13)
            return returnVal;


        try {

            String chkval = personal_id.substring(6,7);

            if(chkval.equals("1") || chkval.equals("2"))
                returnVal[0] = "19" + personal_id.substring(0,2);
            else
                returnVal[0] = "20" + personal_id.substring(0,2);

            returnVal[1] = String.valueOf(Integer.parseInt(personal_id.substring(2,4)));
            returnVal[2] = String.valueOf(Integer.parseInt(personal_id.substring(4,6)));

        } catch (Exception e) {

        }

        return returnVal;

    }

    public static boolean isMan(String personal_id) {

        if(personal_id.length() != 13)
            return false;

        String chkval = personal_id.substring(6,7);

        if(chkval.equals("1") || chkval.equals("3"))
            return true;
        else
            return false;

    }


	public static String getFileExt(String fileName)
	{
		String fileExt = "";
		int dot = fileName.lastIndexOf(".");

		if(dot>-1) {
			fileExt  = fileName.substring(dot+1,fileName.length());
		}

		return fileExt;
	}


    /**
     *스트링내의 특수문자를 HTML형태에 맞게 변환시켜 준다.
     */
    public static String toHtml(String s) {
        if (s==null) return null;

        StringBuffer buf = new StringBuffer();
        char[] c = s.toCharArray();
        int len = c.length;

        for(int i=0;i<len;i++) {
            if      (c[i] == '&' ) buf.append("&amp;");
            else if (c[i] == '<' ) buf.append("&lt;");
            else if (c[i] == '>' ) buf.append("&gt;");
            else if (c[i] == '"' ) buf.append("&quot;");
            else if (c[i] == '\\' ) buf.append("&#039;");
            else buf.append(c[i]);
        }
        //String resultStr = replace(buf.toString(),"\n","<br>");
        return buf.toString();
    }


    /**
	 * 제목에서 일정크기(size) 이상은 ...으로 처리한다.
	 * @return java.lang.String
	 * @param s java.lang.String
	 */
	public static String head(String s, int size) {

		if ( s == null ) return "";
        if ( s.length()==0) return "";

        StringBuffer value = new StringBuffer();

        int cnt = 0;

        for(int i=0;i<s.length();i++) {

            if (cnt+1<=size)
                value.append(s.charAt(i));

     	    if (new Character(s.charAt(i)).hashCode()>4400){
                cnt=cnt+2;
            }
            else{
                cnt=cnt+1;
            }

        }

        if (cnt>size)
            value.append("...");


		return value.toString();
	}

	/**
	 *
	 * @return java.lang.String
	 * @param s java.lang.String
	 */
	public static String tail(String s, int size) {
		if ( s == null ) return "";
		String value = null;
		if ( s.length() > size ) value =  "..." + s.substring(s.length()-size);
		else value = s;
		return value;
	}


        /**
          * Space, CR, LF를 HTML태그로 바꾸어준다.
          * @param String strVal 바꿀 문자열
          * @return String
          */
         public static String replaceHTML(String strVal) {
             String strReturn = "";
             String strTemp = "";

             int size = strVal.length();

             for (int i = 0;i < size;i++) {
                 if (strVal.charAt(i) == '\n') {
                     strTemp += " <br>\n";
                     continue;
                 }
                 if (strVal.charAt(i) == '\r') {
                     continue;
                 }
                 if (i < size - 1) {
                     if (strVal.substring(i, i + 2).equals("  ")) {
                         strTemp += "&nbsp";
                         continue;
                     }
                 }

                 strTemp += String.valueOf(strVal.charAt(i));
             }

             int pos = 0;

             strReturn = strTemp;

             return strReturn;
         }

         /**
          * seed라는 숫자와 앞에 0을 붙여 총 길이 totalLen 인 문자열을 만든다.
          * @param seed
          * @param totalLen
          * @return
          */
         public static String makeSequenceNo(int seed, int totalLen) {
             String sSeed = String.valueOf(seed);
             int len = sSeed.length();
             int no = totalLen - len;

             return makeZero(no) + sSeed;
         }

         public static String makeZero(int len) {
             StringBuffer buffer = new StringBuffer();
             for (int i = 0;i < len;i++) {
                 buffer.append("0");
             }

             return buffer.toString();
         }

         /**
          * 인자로 전달된 BigDecimal의 기존 값에 eachAmount를 더한 후 리턴
          * @param bigDecimal
          * @param eachAmount
          * @return
          */
         public static BigDecimal sum(BigDecimal bigDecimal, String eachAmount) {
             return bigDecimal.add(new BigDecimal(eachAmount));
         }

         public static boolean isEMail(String email) {
             if (email == null) {
                 return false;
             }
             if (email.trim().equals("")) {
                 return false;
             }
             if (email.indexOf("@") == -1) {
                 return false;
             }
             if (email.indexOf(".") == -1) {
                 return false;
             }
             if (email.indexOf(" ") != -1) {
                 return false;
             }
             if (email.indexOf("@") > email.indexOf(".")) {
                 return false;
             }

             return true;
         }

         /**
          * 인자로 전달된 문자열을 구분자 delim으로 자른 후, 배열에 담아 리턴한다.
          * @param str
          * @param delim
          * @return
          */
         public static String[] getArrString(String str, String delim) {
             java.util.StringTokenizer st = new java.util.StringTokenizer(str, delim);
             java.util.ArrayList list = new java.util.ArrayList();
             while (st.hasMoreTokens()) {
                 list.add(st.nextToken());
             }

             String[] result = (String[]) list.toArray(new String[0]);
             return result;
         }

         /**
          * yyyymmddhh24miss->yyyy-mm-dd hh24:mi:ss 형식으로 변환하여 리턴한다.
          * @param sysDate
          * @return
          */
         public static String convertSysDate2TubisDate(String sysDate) {
             StringBuffer buffer = new StringBuffer();
             buffer.append(sysDate.substring(0, 4)).append("-").append(sysDate.substring(4, 6)).append("-").append(sysDate.substring(6, 8)).append(" ").append(sysDate.substring(8, 10)).
                     append(":").append(sysDate.substring(10, 12)).append(":").append(sysDate.substring(12, 14));

             return buffer.toString();
         }

         /**
          * yyyy-mm-dd hh24:mi:ss->yyyymmddhh24miss 형식으로 변환하여 리턴한다.
          * @param tubisDate
          * @return
          */
         public static String convertTubisDate2SysDate(String tubisDate) {
             StringBuffer buffer = new StringBuffer();
             buffer.append(tubisDate.substring(0, 4)).append(tubisDate.substring(5, 7)).append(tubisDate.substring(8, 10)).append(tubisDate.substring(11,
                     13)).append(tubisDate.substring(14, 16)).append(tubisDate.substring(17, 19));

             return buffer.toString();
         }

         /**
          * YYYY-MM-DD HH24:MI:SS -> YYYY-MM-DD로 변환하여 리턴한다.
          * @param tubisDate - YYYY-MM-DD HH24:MI:SS 형식의 문자열
          * @return - YYYY-MM-DD 형식의 문자열
          */
         public static String convertTubisDate2YYYY_MM_DD(String tubisDate) {
             return tubisDate.substring(0, 10);
         }

         /**
          * NULL을 "0"로 변환하여 리턴한다.
          * @param data
          * @return - data의 문자형
          */
         public static String nullToZero(Object data) {
             if (data == null) {
                 return "0";
             } else {
                 return data.toString();
             }
         }

         /**
          * 데이타를 fromIndex에서 toIndex까지 Asterisk으로 치환
          * @param data
          * @return - data의 문자형
          */
         public static String replaceAsterisk(String data, int fromIndex, int toIndex) {
             if (data == null || fromIndex > toIndex) {
                 return data;
             }

             StringBuffer newValue = new StringBuffer();
             char[] dataChars = data.toCharArray();
             int dataLen = dataChars.length;

             for (int i = 0;i < dataLen;i++) {
                 if (i >= fromIndex && i <= toIndex) {
                     newValue.append('*');
                 } else {
                     newValue.append(dataChars[i]);
                 }
             }
             return newValue.toString();
         }

         /**
          * 스트링을 fromIndex에서 끝까지 Asterisk으로 치환(
          * @param data
          * @return - data의 문자형
          */
         public static String replaceAsterisk(String data, int fromIndex) {
             if (data == null) {
                 return data;
             } else {
                 return replaceAsterisk(data, fromIndex, data.length() - 1);
             }
         }

         /**
          * 스트링을 Asterisk으로 치환
          * @param data
          * @return - data의 문자형
          */
         public static String replaceAsterisk(String data) {
             if (data == null) {
                 return data;
             } else {
                 return replaceAsterisk(data, 0, data.length() - 1);
             }
         }

         /**
          * 입력한 데이타를 해당 타입에 맞게 Asterisk으로 변환하는 메소드
          * @param indataTray - '*'으로 변환할 데이타
          * @param sessionTray -세션정보
          */
         public String DataToAsterisk(String inData, String inType) {
             Log.warn("start StringUtil.DataToAsterisk");
             try {
                 String newData = "";
                 int sizeinData = inData.length();

                 if (inType.equals("")) { //타입없음.
                     newData = StringUtil.replaceAsterisk(inData);
                 }

                 if (inType.equals("ADDR")) { //주소타입
                     int indexOfTemp = inData.indexOf(" ", 2);
                     if (indexOfTemp > 30) {
                         newData = StringUtil.replaceAsterisk(inData);
                     } else {
                         newData = StringUtil.replaceAsterisk(inData, indexOfTemp);
                     }
                 } else if (inType.equals("SSN")) { //주민번호 타입:뒷번호 모두 '*'
                     newData = StringUtil.replaceAsterisk(inData, 7);
                 } else if (inType.equals("bill_acnt_no")) { //청구계정번호
                     newData = StringUtil.replaceAsterisk(inData, 4);
                 } else if (inType.equals("cust_no")) { //고객식별번호
                     newData = StringUtil.replaceAsterisk(inData, 6);
                 } else if (inType.equals("cont_no")) { //가입계약번호
                     newData = StringUtil.replaceAsterisk(inData, 6);
                 }

                 return newData;
             } catch (AppException ex) {
                 Log.error(">>> StringUtil.DataToAsterisk(String inData, String inType) 에서 AppException 에러발생", ex);
                 ex.printStackTrace();
                 throw ex;
             } catch (Exception ex) {
                 Log.error(">>> StringUtil.DataToAsterisk(String inData, String inType) 에서 Exception 에러발생", ex);
                 ex.printStackTrace();
                 throw new AppException(">>> StringUtil.DataToAsterisk(String inData, String inType) 에서 Exception 에러발생", ex);
             } finally {

             }
         }

         public static String convertToKR(String str, String type) {
             try {
                 if (type == null) {
                     return new String(str.getBytes("8859_1"), "KSC5601");
                 } else {
                     return new String(str.getBytes(type), "KSC5601");
                 }
             } catch (Exception e) {
                 return null;
             }
         }

         public static String convertToKR(String str) {
             return convertToKR(str, null);
         }

         public static String convertFromKR(String str, String type) {
             try {
                 if (type == null) {
                     return new String(str.getBytes("KSC5601"), "8859_1");
                 } else {
                     return new String(str.getBytes("KSC5601"), type);
                 }
             } catch (Exception e) {
                 return null;
             }
         }

         public static String convertFromKR(String str) {
             return convertFromKR(str, null);
    }

     	public static String getRandomString()
    	{
     	   String random = "";
    	   Random ran = new Random();
    	   	for (int a=0;a<10;a++){
    	   		if((a%3)==0){
    	   			char ch = (char) ((Math.random() * 26) + 65);
    	    	    random += ch;
    			}else if((a%3)==1){
    	   				char ch = (char) ((Math.random() * 26) + 97);
    	   				random += ch;
    	   		}else if((a%3)==2){
    	   		    	int ch = ran.nextInt(10) + 1;
    	    	    	random += ch;
    	    	}
    	   	}
    		return random;
    	}         
     	
        /**
         * ca ldap date format convert to general date format
         * @param date - ca ldap의 날짜형   
         * @param return - data의 문자형
         */
        public static String cadate(String date){
    		String retVar = date.substring(5, 10);
    		String sYear = "20" + retVar.substring(0, 2);
    		int Year  = Integer.parseInt(sYear); //00001 08 099
    		
    		int dayVal = Integer.parseInt(retVar.substring(2, 5));
    		int intArray [];
    		String monthVal [] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    		if (((Year % 4 == 0) && (Year % 100 != 0)) || (Year % 400 == 0)) {			//윤달인 경우
    			int dArray[] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    			intArray = dArray;
    		}
    		else {
    			int dArray[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    			intArray = dArray;
    		}

    		String day = "";
    		int j = 0;

    		for (int i=0; i < intArray.length; i++){
    			if (dayVal - intArray[i] >0){
    				dayVal = dayVal - intArray[i];
    				j++;
    			}

    			if (dayVal < 0)
    				break;
    		}

    		return Year +"-" +  monthVal[j] +"-"+ (dayVal<10?  ("0"+Integer.toString(dayVal)) : Integer.toString(dayVal));
    	}
        
        public static String getStr(String str, int intstr) throws Exception{
            int len = 0;
            String reStr = "";
            if(str != null){
            	String[] tempStr = str.split("[\n]");
            	if(tempStr.length > 0){
            		if (tempStr.length < 3) {
            			len = tempStr.length;
            		} else {
            			len = 3;
            		}

            		for (int i = 0; i < len; i++) {
            			reStr += getTile(tempStr[i], intstr) + "<br>";
            		}
            		if (tempStr.length > 3) {
            			reStr += "...................";
            		}
            	}else{
            		if(str.getBytes().length > intstr){
            			reStr = getTile(str, intstr)+".........";
            		}else{
            			reStr = str;
            		}
            	}
            }
            return reStr;
        }
        
        public static String getTile(String str, int intStr) throws  Exception{
            String reStr ="";
            int sublen = 0;
            StringBuffer sbuf = new StringBuffer();
            for(int j=0;(j<str.length()&&sublen<intStr);j++){
                if(Character.getType(str.charAt(j)) == Character.OTHER_LETTER) sublen=sublen+2;//한글
                else sublen++;//기타 영문,특수문자,공백
                sbuf.append(str.charAt(j));
            }
            reStr = sbuf.toString();
            return reStr;
        }
        
        
        /*
         * 100728 긴 글자 자르기  
         * @param str
         * @param intStr
         * @return
         * @throws Exception
         */
        public static String insertBR(String str, int intStr) throws  Exception{
            String reStr ="";
            int sublen = 0;
            StringBuffer sbuf = new StringBuffer();
                        
            //글자수 만큼 돌린다
            for(int j=0;j<str.length();j++){
                if(Character.getType(str.charAt(j)) == Character.OTHER_LETTER) sublen=sublen+2;//한글
                else sublen++;//기타 영문,특수문자,공백
                
                //제한글자수를 넘으면
                if(intStr < sublen){
                	sbuf.append(str.charAt(j) + "<BR>");
                	sublen = 0;
                }else{
                	sbuf.append(str.charAt(j));
                }
            }
                        
            reStr = sbuf.toString();
            return reStr;
        }
        
        public static String commaBR(String str, int intcomma) throws Exception{
        	String reStr = "";
        	
        	String[] arrStr = str.split(",");
        	
        	if(arrStr.length < intcomma){
        		reStr = str;
        	}else{
        		for(int i=0; i < arrStr.length; i++){
        			if(i%5==0){
        				if(i==0){
        					reStr += arrStr[i]+",";
        				}else{
        					reStr += "<br>"+arrStr[i]+",";
        				}
        			}else{
        				reStr += arrStr[i]+",";
        			}
        		}
        		reStr = reStr.substring(0, reStr.lastIndexOf(","));
        	}
        	
        	return reStr;
        }
        
        /**
         * 이메일 형식 체크
         * @param mail
         * @return
         */
        public static boolean checkFormatMail(String mail) {
    		 
        	String[] temp = mail.split("@");	
    	 
        	if(temp.length == 2){
        		int count = 0;
          		 
            	for(int i = 0; i < temp[0].length(); i++){
           		 	if(temp[0].charAt(i) <= 'z' && temp[0].charAt(i) >= 'a') continue;		
           		 	else if(temp[0].charAt(i) <= 'Z' && temp[0].charAt(i) >= 'A') continue;	
           		 	else if(temp[0].charAt(i) <= '9' && temp[0].charAt(i) >= '0') continue;	
           		 	else if(temp[0].charAt(i) == '-' && temp[0].charAt(i) == '_') continue;	
           		 	else return false;
           	 	}	
           		 		 		 
           	 	for(int i = 0; i < temp[1].length(); i++){	
           	 		if(temp[1].charAt(i) <= 'z' && temp[1].charAt(i) >= 'a') continue;
           	 		else if(temp[1].charAt(i) == '.'){ count++;  continue;}
           	 		else return false;
           	 	}		
           		 
           	 	if(count > 0 && count <=2) return true;
           	 	else  return false;
        	} 
        	else return false;
        }               
        
}//end class
