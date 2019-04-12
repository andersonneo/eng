/**
 * 파일명: com.kyeryong.framework.tray.AbstractTray.java
 * 파일개요: 클라이언트 요청 정보를 캡슐화한 클래스
 * 저작권: Copyright (c) 2003 by SK C&C. All rights reserved.
 * 작성자: 박찬우 (nucha@dreamwiz.com)
 */

package com.eng.framework.tray;

//Java API
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.eng.framework.exception.AppException;

/**
 * HTTP request 정보를 Tray 개체에 담기 위한 기본 구현을 제공하는 추상 클래스
 *
 * <p>
 * 사용법
 *
 * @author <b>박찬우</b>
 * @version 1.0, 2004/02/01
 */

public abstract class AbstractTray implements Tray {
    //Map에 List를 저장하며, List에는 String을 저장한다.
    private static String EMPTY_STRING = "";
    private static String[] EMPTY_STRING_ARRAY = new String[0];
    protected Map map = new TreeMap();

    public AbstractTray() {
    }

    /**
     * 몇 가지 종류의 데이터들이 저장되었는지를 리턴한다.
     * @return - 사용하고 있는 키의 갯수
     */
    public int size() {
        return map.size();
    }

    /**
     * 특정 key에 해당하는 데이터의 갯수를 리턴한다.
     * @param key - 관심있는 키
     * @return - 키에 관련된 값들의 갯수
     */
    public int size(String key) {
        Object obj = map.get(key);

        int iSize = 0;
        if (obj != null) {
            List list = (List) obj;
            iSize = list.size();
        }

        return iSize;
    }

    /**
     * 요청정보가 비어있는지를 검사한다.
     * @return - 비어있으면 true, 그렇지 않으면 false
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 정보가 가득차있는지를 검사한다.
     * @return - 디폴트 리턴값은 false
     */
    public boolean isFull() {
        return false;
    }

    /**
     * 저장된 데이터에 대한 열거를 제공한다.
     * @return - Enumeration
     * @throws AppException - 기본적인 예외 발생
     */
    public Enumeration getEnumeration() throws AppException {
        throw new AppException("기본 구현을 제공하지 않습니다.");
    }

    /**
     * 저장된 정보 제거
     */
    public void clear() {
        //this.clearList();
        map.clear();
    }

    /**
     * 저장된 특정 정보 제거
     * @param key - 특정 정보에 대한 키
     */
    public void clear(String key) {
        map.remove(key);
    }

    /**
     * 요청 정보들에 대한 키를 리턴한다.
     * @return - 키들의 배열
     */
    public String[] getKeys() {
        int iSize = map.size();
        //System.out.println("iSize=" + iSize);
        String[] keys = new String[iSize];
        Set set = map.keySet();
        Iterator it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            keys[i++] = (String) it.next();
        }

        return keys;
    }

    /**
     * index 번째 key 값을 얻어낸다.
     * @param key - 사용할 키
     * @param index - 원하는 index
     * @return - index 번째 key 값, 의미가 없다면 null을 리턴한다.
     */
    public String get(String key, int index) {
        List list = (List) map.get(key);
        if (list == null) {
            return (String)null;
        }

        try {
            String value = (String) list.get(index);
            return value;
        } catch (IndexOutOfBoundsException ex) {
            throw new AppException("key = " + key + ", index = " + index, ex);
        }
    }

    /**
     * 0번째 key에 대한 값을 얻어낸다.
     * @param key - 사용할 키
     * @return - 0번째 key에 대응하는 값
     */
    public String get(String key) {
        return get(key, 0);
    }

    /**
     * 0번째 key에 대한 값을 얻어낸다.
     * @param key - 사용할 키
     * @return - 0번째 key에 대응하는 값
     */
    public String getString(String key) {
        return getString(key, 0);
    }

    public String getDateString(String key) {
        return getDateString(key, 0);
    }

    /**
     * key에 대한 모든 값을 문자열 배열로 얻어낸다.
     * @param key - 사용할 키
     * @return - key에 대응하는 문자열 배열 값
     */
    public String[] getAllString(String key) {
        List list = (List) map.get(key);
        if (list == null) {
            return EMPTY_STRING_ARRAY;
        }

        String[] values = (String[]) list.toArray(EMPTY_STRING_ARRAY);
        return values;
    }

    /**
     * index 번째 key 값을 얻어낸다.
     * @param key - 사용할 키
     * @param index - 원하는 index
     * @return - index 번째 key 값
     */
    public String getString(String key, int index) {
        String value = get(key, index);
        if (value == null) {
            return "";
        }

        return value;
    }

    /**
     * 임시 -- 최진석 20040223 - 날짜포맷(21자) 중 날짜(yyyy-mm-dd)만 리턴한다.
     *
     * index 번째 key 값을 얻어낸다.
     * @param key - 사용할 키
     * @param index - 원하는 index
     * @return - index 번째 key 값
     */
    public String getDateString(String key, int index) {
        String value = get(key, index);
        if (value == null || value.length() != 21) {
            return "";
        }

        return value.substring(0, 10);
    }

    /**
     * 임시 -- 최진석 20040223 - 날짜포맷(21자) 중 시간(hh:mm)만 리턴한다.
     *
     * index 번째 key 값을 얻어낸다.
     * @param key - 사용할 키
     * @param index - 원하는 index
     * @return - index 번째 key 값
     */
    public String getTimeString(String key, int index) {
        String value = get(key, index);
        if (value == null || value.length() != 21) {
            return "";
        }

        return value.substring(11, 16);
    }

    /**
     * key의 0번째 값을 BigDecimal로 변환하여 리턴한다.
     * @param key - 사용할 키
     * @return - key의 0번째 값에 대한 BigDecimal
     */
    public BigDecimal getBigDecimal(String key) {
        return getBigDecimal(key, 0);
    }

    /**
     * key의 index번째 값을 BigDecimal로 변환하여 리턴한다.
     * @param key - 사용할 키
     * @param index - index번째 요소
     * @return - key의 index번째 값에 대한 BigDecimal
     */
    public BigDecimal getBigDecimal(String key, int index) {
        String value = get(key, index);
        if (value == null) {
            return new BigDecimal("0");
        }

        if (value.equals("")) {
            return new BigDecimal("0");
        }

        return new BigDecimal(value);
    }

    /**
     * key의 0번째 값을 int로 변환하여 리턴한다.
     * @param key - 사용할 키
     * @return - key의 0번째 값에 대한 BigDecimal
     */
    public int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * key의 index번째 값을 int로 변환하여 리턴한다.
     * @param key - 사용할 키
     * @param index - index번째 요소
     * @return - key의 index번째 값에 대한 int
     */
    public int getInt(String key, int index) {
        String value = get(key, index);
        if (value == null) {
            return 0;
        }

        if (value.equals("")) {
            return 0;
        }

        int iValue = 0;
        try {
            iValue = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
        }

        return iValue;
    }

    /**
     * key의 0번째 값을 double로 변환하여 리턴한다.
     * @param key - 사용할 키
     * @return - key의 0번째 값에 대한 double
     */
    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, int index) {
        String value = get(key, index);
        if (value == null) {
            return 0L;
        }

        if (value.equals("")) {
            return 0L;
        }

        double dValue = 0.0D;
        try {
            dValue = Double.parseDouble(value);
        } catch (NumberFormatException ex) {
        }

        return dValue;
    }

    private void clearList() {
        String[] keys = this.getKeys();

        int size = keys.length;
        for (int i = 0; i < size; i++) {
            List list = (List) map.get(keys[i]);
            list.clear();
        }
    }

    /**
     * 사용하려는 index가 유효한 지를 조사한다.
     * @param index - 조사하려는 index
     * @return - 유효하면 true, 그렇지 않으면 false
     */
    private boolean isValidIndex(int index) {
        int iSize = size();
        return (index >= 0 && index < iSize);
    }

    /**
     * 비어있는 List를 생성하여 반환한다.
     * @return - Empty List
     */
    private List newList() {
        return new java.util.ArrayList();
    }

    /**
     * key-value 쌍으로 값을 세팅한다.
     * @param key - 사용하려는 키
     * @param value - 키에 대응하는 값
     */
    public void set(String key, String value) {
        String[] values = new String[] {value};
        set(key, values);
    }

    /**
     * key-values[] 쌍으로 값을 세팅한다.
     * @param key - 사용하려는 키
     * @param values - 키에 대응하는 배열값
     */
    public void set(String key, String[] values) {
        if (key == null || values == null) {
            throw new java.lang.IllegalArgumentException("key나 value에 문제가 있습니다.");
        }

        List list = this.newList();
        int size = values.length;
        for (int i = 0; i < size; i++) {
            list.add(values[i]);
        }

        map.put(key, list);
    }

    /**
     * 기존 key에 value를 추가한다.
     * @param key - 사용하려는 키
     * @param value - 키에 대응하는 값
     */
    public void add(String key, String value) {
        if (map.get(key) == null) {
            set(key, value);
            return;
        }

        List list = (List) map.get(key);
        list.add(value);
    }

    public void setString(String key, String value) {
        set(key, value);
    }

    public void setString(String key, String[] values) {
        this.set(key, values);
    }

    /**
     * Tray가 저장하고 있는 내용을 보여주는 Collection View이다.
     * @return - Tray의 내용
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        String[] keys = this.getKeys();
        int size = keys.length;
        for (int i = 0; i < size; i++) {
            //System.out.println("key: " + keys[i]);
            buffer.append(keys[i]).append(": \n");
            buffer.append(toString(keys[i]));
        }

        return buffer.toString();
    }

    /**
     * key에 대한 값들에 대한 문자열
     * @param key - 관심 key
     * @return - key와 관련된 값들의 문자열
     */
    private String toString(String key) {
        StringBuffer buffer = new StringBuffer();
        Object obj = getObject(key);
        List list = (List) obj;
        int listSize = list.size();
        for (int j = 0; j < listSize; j++) {
            Object value = list.get(j);
            String svalue = value != null ? value.toString() : "{null}";
            buffer.append("\t").append(svalue).append("<br>").append("\n");
        }

        return buffer.toString();
    }

    /**
     * key-List 쌍을 저장하는 메소드
     * @param key - 대상 키
     * @param list - 키에 관련된 대상 List
     */
    public void set(String key, List list) {
        map.put(key, list);
    }
    
    /**
     * key-List 쌍을 저장하는 메소드
     * @param key - 대상 키
     * @param list - 키에 관련된 대상 List
     */
    public List getList(String key) {
    	return (List)map.get(key);
    }
    

    //-------------파일 업로드 관련 메소드들-------------------------------------//


    /**
     * 파일명을 리턴한다.
     * @param key - 관심 키
     * @return - 디폴트로 빈 문자열 리턴
     */
    public String getFileName(String key) {
        return getFileName(key, 0);
    }

    /**
     * key의 index번째 파일명을 리턴한다.
     * @param key - 관심 키
     * @param index
     * @return - 디폴트로 빈 문자열 리턴
     */
    public String getFileName(String key, int index) {
        return EMPTY_STRING;
    }

    /**
     * 파일 타입을 리턴한다.
     * @param key - 관심 키
     * @return - 빈 문자열 리턴
     */
    public String getFileType(String key) {
        return getFileType(key, 0);
    }

    /**
     * key의 index번째 파일 타입을 리턴한다.
     * @param key - 관심 키
     * @param index
     * @return - 빈 문자열 리턴
     */
    public String getFileType(String key, int index) {
        return EMPTY_STRING;
    }

    /**
     * key의 0번째 파일이 저장된 위치를 리턴한다.
     * @param key - 관심 키
     * @return - 빈 문자열 리턴
     */
    public String getSaveDir(String key) {
        return getSaveDir(key, 0);
    }

    /**
     * key의 index번째 파일이 저장된 위치를 리턴한다.
     * @param key - 관심 키
     * @param index
     * @return - 빈 문자열 리턴
     */
    public String getSaveDir(String key, int index) {
        return EMPTY_STRING;
    }

    /**
     * key의 0번째 파일 크기를 리턴한다.
     * @param key - 관심 키
     * @return - 디폴트로 -1을 리턴
     */
    public long getFileLength(String key) {
        return getFileLength(key, 0);
    }

    /**
     * key의 index번째 파일 크기를 리턴한다.
     * @param key - 관심 키
     * @param index -
     * @return - 디폴트로 -1을 리턴한다.
     */
    public long getFileLength(String key, int index) {
        return -1;
    }

    /**
     * key에 관련된 객체를 리턴한다.
     * @param key - 관심 키
     * @return - Object (List 객체)
     */
    protected Object getObject(String key) {
        return map.get(key);
    }

    /**
     * ResultSetTray의 경우에만 유효한 method로, tray가 담고 있는 row의
     * 개수를 리턴한다. AbstractTray에서는 -1을 리턴함.
     *
     * @return	-1
     */
    public int getRowCount() {
        return -1;
    }

    //===============================================================================
    //  Request 데이터를 가진 tray의 내용을
    //  파일에 기록하거나 삭제하는 기능 구현. BaseCommand에서 사용될 수 있다.
    //===============================================================================

    //Tray에 있는 내용을 탭을 구분자로 key1 value1 key2 value2 형식으로 파일에 저장한다.
    private String myroot = "/";
    private String myDir = "tubis/domains/tubisDomain/logs/";
    public String writeToFile(String fileName) {
        String root = myroot + myDir;
        String myfile = "";

        int lastPointIndex = fileName.lastIndexOf(".");
        String packageName = fileName.substring(0, lastPointIndex);
        String cmdName = fileName.substring(lastPointIndex + 1);
        String myLocate = makeDir(packageName);

        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");

        String timestamp = df.format(new java.util.Date());

        long writeTime = System.currentTimeMillis();
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            myfile = myroot + myLocate + cmdName + "_" + timestamp + ".txt";
            fw = new FileWriter(myfile);
            bw = new BufferedWriter(fw);

            Set keySet = map.keySet();
            Iterator keyIterator = keySet.iterator();
            StringBuffer buffer = new StringBuffer();
            while (keyIterator.hasNext()) {
                String key = (String) keyIterator.next();
                List eachList = (List) map.get(key);
                for (int i = 0; i < eachList.size(); i++) {
                    buffer.append(key).append("\t").append(eachList.get(i)).append("\n");
                }
            }

            bw.write(buffer.toString());
            bw.flush();

            //System.out.println(buffer.toString());

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ignore) {
                }
            }

            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ignore) {
                }
            }
        }

        return myfile;
    }

    public void removeFile(String fqfn) {
        if (fqfn == null || fqfn.equals("")) {
            return;
        }

        try {
            File f = new File(fqfn);
            if (f.exists()) {
                f.delete();
            }
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    private String makeDir(String packageName) {
        String myLocate = myDir + convertPackageToDir(packageName);

        java.util.StringTokenizer st = new java.util.StringTokenizer(myLocate, "/");

        String path = "/";
        while (st.hasMoreTokens()) {
            String dirName = st.nextToken();
            path += dirName + "/";
            File f = new File(path);
            if (!f.exists()) {
                boolean isMade = f.mkdir();
            }
        }

        return myLocate;
    }

    /**
     * tubis.ccs.a.a10 -> tubis/ccs/a/a10/ 으로 변환
     * @param packageName
     * @return
     */
    String convertPackageToDir(String packageName) {
        java.util.StringTokenizer st = new java.util.StringTokenizer(packageName, ".");

        String path = "";
        while (st.hasMoreTokens()) {
            String dirName = st.nextToken();
            path += dirName + "/";
        }

        return path;
    }

}
