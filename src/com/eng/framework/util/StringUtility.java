package com.eng.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtility {

    public StringUtility() {
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
     * 문자열을 URLEncoding 합니다.
     *
     * @param old 인코딩 될 문자열
     * @return URLEncoding 된 결과를 반환합니다.
     */
    public static String encodeExceptSlash(String old)
            throws UnsupportedEncodingException {
        int index = -1;
        StringBuffer sb = new StringBuffer();

        while ((index = old.indexOf("/")) != -1) {
            String toBeEncoded = old.substring(0, index);
            sb.append(URLEncoder.encode(toBeEncoded, "euc-kr"));
            sb.append("/");
            old = old.substring(index + 1);
        }

        sb.append(URLEncoder.encode(old, "euc-kr"));

        return sb.toString();
    }

    /**
     * 문자열 배열을 구분 문자열로 연결시킨 문자열을 생성합니다.
     *
     * @param tokens 문자열 배열.
     * @param delim 구분자.
     * @return 문자열 배열을 구분자로 연결 후 조합된 문자열을 반환합니다.
     */
    public static String join(String tokens[], String delim) {
        int len = tokens.length;
        StringBuffer sb = new StringBuffer();

        if (len > 0) {
            sb.append(tokens[0]);

            for (int i = 1; i < len; i++)
                sb.append(delim).append(tokens[i]);
        }

        return sb.toString();
    }
    
    /**
     * 문자열 배열을 구분 문자열로 연결시킨 문자열을 생성합니다.
     * @param tokens 문자열 리스트.
     * @param delim 구분자.
     * @return 문자열 배열을 구분자로 연결 후 조합된 문자열을 반환합니다.
     */
    public static String join2(List tokens, String delim) {
        int len = tokens.size();
        StringBuffer sb = new StringBuffer();

        if (len > 0) {
            sb.append(tokens.get(0));

            for (int i = 1; i < len; i++)
                sb.append(delim).append(tokens.get(i));
        }

        return sb.toString();
    }
    
    /**
     * 구분자로 리스트에 넣자 
     */
    public static List splitStr(String str, String delim){
    	List splitStrList = new ArrayList();
    	
    	String[] splitArrayStr = new String[str.split(delim).length];
    	splitArrayStr = str.split(delim);
    	
    	for(int i=0; i < splitArrayStr.length; i++) {
    		splitStrList.add((String) splitArrayStr[i]);
    	}
    	
    	return splitStrList;
    }
    
    /**
     * 리스트를 배열로 만들자
     * @param tokenList
     * @return
     */
    public static String[] listToArray(List tokenList){
    	String[] arrayString = null;
    	if(tokenList != null){
    		arrayString = new String[tokenList.size()];
    		for(int i=0; i < tokenList.size(); i++){
    			arrayString[i] = (String)tokenList.get(i);
    		}
    	}
    	return arrayString;
    }

    /**
     * 문자열 배열 중 검색대상 문자열과 일치(대소문자 무시))하는 문자열 위치 반환합니다.
     *
     * @param tokens 검색대상 문자열 배열.
     * @param token 검색 문자열.
     * @return 검색된 문자열 배열 위치.
     */
    public static int lookupIgnoreCase(String tokens[], String token) {
        for (int i = tokens.length - 1; i >= 0; i--) {
            if (token.equalsIgnoreCase(tokens[i]))
                return i;
        }

        return -1;
    }

    /**
     * 문자열 검색 후 만족하는 문자열을 삭제합니다.
     *
     * @param source 검색대상 문자열.
     * @param validChars 검색문자열.
     * @return 검색대상 문자열 중 삭제한 문자열을 반환합니다.
     */
    public static String removeInvalid(String source, String validChars) {
        StringBuffer valid = new StringBuffer();

        for (int i = 0; i < source.length(); i++) {
            if (validChars.indexOf(source.charAt(i)) > -1)
                valid.append(source.charAt(i));
        }

        return valid.toString();
    }

    /**
     * 문자열 검색 후 해당 문자열로 치환합니다.
     *
     * @param source 검색대상 문자열.
     * @param oldPattern 치환 될 문자열.
     * @param newPattern 치환 되어질 문자열.
     * @return 치환 할 문자열.
     */
    public static String replaceAll(String source, String oldPattern,String newPattern) {
        int length = oldPattern.length();
        int fromIndex = 0;
        int toIndex = -1;
        StringBuffer sb = new StringBuffer();

        while ((toIndex = source.indexOf(oldPattern, fromIndex)) != -1) {
            sb.append(source.substring(fromIndex, toIndex));
            sb.append(newPattern);
            fromIndex = toIndex + length;
        }

        sb.append(source.substring(fromIndex));

        return sb.toString();
    }

    private static String split2(String temp, String newPattern){
        StringBuffer sb = new StringBuffer();
        if(temp.length() > 50){
            sb.append(temp.substring(0,50));
            sb.append(newPattern);
            //sb.append(temp.substring(60));
        }else{
            sb.append(temp);
        }
        return sb.toString();
    }

    public static String replaceAll2(String source, String oldPattern,
        String newPattern) {
    int count = 1;
    int stsize = 0;
    StringBuffer sb = new StringBuffer();
    java.util.StringTokenizer st = new java.util.StringTokenizer(source, oldPattern);
    stsize = st.countTokens();
    while(st.hasMoreTokens()){
        String temp = (String)st.nextToken("\n");
        int len = temp.length();
        while(len > 50){
            sb.append(split2(temp,newPattern));
            temp = temp.substring(50);
            len -= 50;
        }
        if(len > 0) sb.append(temp);

        if(count != stsize) sb.append(newPattern);
        count++;
    }

    return sb.toString();
}


    /**
     * 문자열을 쉼표 값을 기준으로 문자열 배열을 생성합니다.
     *
     * @param str 문자열 배열 생성을 위한 문자열.
     * @return 생성된 문자열 배열 반환
     */
    public static String[] split(String str) {
        return split(str, " ,");
    }

    /**
     * 문자열에서 구분자를 기준으로 문자열 배열을 생성합니다.
     * 구분자 사이가 비어있으면 배열에 추가되지 않음
     *
     * @param str 문자열 배열 생성을 위한 문자열.
     * @param delim 문자열 배열 생성 기준 문자열.
     * @return 생성된 문자열 배열
     */
    public static String[] split(String str, String delim) {
        return split(str, delim, false);
    }

    /**
     * 문자열에서 구분자를 기준으로 문자열 배열을 생성합니다.
     * 구분자 사이가 비어있으면 배열에 추가할지 여부는 allowEmptry 활용
     * @param str
     * @param delim
     * @param allowEmpty
     * @return
     */
    public static String[] split(String str, String delim, boolean allowEmpty) {
        StringTokenizer tokens = new StringTokenizer(str, delim, allowEmpty);
        Vector vector = new Vector();
        String token = "";
        String preToken = "";

        while  (tokens.hasMoreTokens()) {
            token = tokens.nextToken();
            if (allowEmpty) {
                if (preToken.equals("") && token.equals(delim)) vector.add("");
                if (preToken.equals(delim) && token.equals(delim)) vector.add("");
            }

            if (!token.equals(delim)) vector.add(token);
            preToken = token;
        }
        if (allowEmpty && preToken.equals(delim)) vector.add("");

        return (String[])vector.toArray(new String[0]);
    }

    /**
     * StringTokenizer 값을 문자열 배열로 바꿔줍니다.
     *
     * @param tokenizer 치환 될 StringTokenizer.
     * @return StringTokenizer 를 문자열 배열로 치환 후 반환.
     */
    public static String[] tokenStringArray(StringTokenizer tokenizer) {
        String returnArray[] = new String[tokenizer.countTokens()];

        for (int counter = 0; tokenizer.hasMoreTokens(); counter++) {
            String id = tokenizer.nextToken();
            returnArray[counter] = id.trim();
        }

        return returnArray;
    }

    /**
     * StringTokenizer 값을 long 형 배열로 바꿔줍니다.
     *
     * @param tokenizer 치환 될 StringTokenizer.
     * @return StringTokenizer 를 long 형 배열로 치환 후 반환.
     */
    public static long[] tokenlongArray(StringTokenizer tokenizer) {
        long returnArray[] = new long[tokenizer.countTokens()];

        for (int counter = 0; tokenizer.hasMoreTokens(); counter++) {
            String value = tokenizer.nextToken();
            long key = Long.parseLong(value);
            returnArray[counter] = key;
        }

        return returnArray;
    }

    public static String fileLength(int length) {
        if (length > 1024 * 1024) {
            java.text.DecimalFormat df = new java.text.DecimalFormat("###.##");
            return (df.format(length / 1024.0 / 1024) + "MB");
        } else if (length > 1024) {
            return (Math.round(length / 1024) + "KB");
        } else {
            length = (int) (length);
            return (length + "B");
        }
    }

    /**
     * Vector에서 ToString으로 만들어진 문자열("[....]"형태)을 Vector 객체로 변환해준다.
     * @param str
     * @return
     */
    public static Vector vectorStrToVector(String str) {
        Vector vector = new Vector();
        if (!str.equals("[]")) {
            str = StringUtility.replaceAll(str, "[", "");
            str = StringUtility.replaceAll(str, "]", "");

            StringTokenizer tokens = new StringTokenizer(str, ",");
            int len = tokens.countTokens();
            for (int i = 0; i < len; i++)
                vector.add(tokens.nextToken());
        }

        return vector;
    }

    public static String fileLength(String length) {
        return fileLength(Integer.parseInt(length));
    }
    
    /**
     * 090901 KJW NULL값 리턴
     * @param str
     * @return
     */
    public static String nullToBlank(String str){
    	if(str == null) str = "";
		return str;
    }
    
    public static boolean checkNumber(String str){
    	boolean chk = true;
    	for(int i=0;i<str.length();i++){
    		char c=str.charAt(i);
    		if(!(0x30 <= c && c <= 0x39)){
    			chk = false;
    		}
    	}
    	return chk;
    }
    
    /**
     * 091022 KJW 형변환 문자에서 정수로
     * @param str
     * @return
     */
    public static int strChangeInteger(String str){
    	int integer = -1;

    	if(str != null && !(str.trim().equals(""))){
    		if(checkInteger(str)){
    			integer = Integer.parseInt(str);
    		}
    	}
    	return integer;
    }
    
    /**
     * 091022 KJW 숫자인지 판별
     * @param str
     * @return
     */
    public static boolean checkInteger(String str){
    	Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(str);
    	return m.find();
    }
}
