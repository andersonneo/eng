/**
 * 파일명: com.kyeryong.framework.tray.Tray.java
 * 파일개요: Data Transfer Object에 대한 공통 인터페이스
 * 저작권: Copyright (c) 2003 by SK C&C. All rights reserved.
 * 작성자: 박찬우 (nucha@dreamwiz.com)
 */

package com.eng.framework.tray;

//Java API
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.List;

/**
 * Tray 인터페이스는 Data Transfer Object에 대한 공통 인터페이스이다.
 * Map에 key-List 쌍을 저장하는 구조이며 기본적으로 문자열을 List에 저장한다.
 *
 * 저장된 문자열을 원하는 타입으로 리턴하는 메소드가 제공되며
 * 메소드 인자로 사용된 (key, index)는 key와 연결된 List의 index번째 문자열에 대응된다.
 * 메소드 인자로 사용된 (key)는 (key, index)=(key, 0)에 대응된다.
 * <p>
 * 사용법
 *
 * @author <b>박찬우</b>
 * @version 1.0, 2004/02/01
 */

public interface Tray extends java.io.Serializable {
    /**
     * 요청 정보의 갯수를 리턴한다.
     * @return - 요청 정보의 갯수
     */
    int size();

    /**
     * 요청정보가 비어있는지를 검사한다.
     */
    boolean isEmpty();

    /**
     * 정보가 가득차있는지를 검사한다.
     */
    boolean isFull();

    /**
     * 담겨있는 정보 제거
     */
    void clear();

    /**
     * 데이터에 접근할 수 있는 인터페이스를 제공한다.
     * @return - Enumeration
     */
    Enumeration getEnumeration();

    /**
     * 요청 속의 특정 정보의 갯수
     * @param key - 갯수를 알고 싶은 특정 정보에 대한 키
     * @return - 특정 정보의 갯수
     */
    int size(String key);

    //key-value 기반 Tray일 경우를 위한 인터페이스

    /**
     * key에 대응하는 List를 제거한다.
     * @param key - 관심 키
     */
    void clear(String key);

    /**
     * 모든 키를 문자열 배열로 리턴한다.
     * @return - 키들의 문자열 배열
     */
    String[] getKeys();

    String get(String key, int index);

    String get(String key);

    String getString(String key);

    String getDateString(String key);

    String[] getAllString(String key);

    String getString(String key, int index);

    String getDateString(String key, int index);

    String getTimeString(String key, int index);

    BigDecimal getBigDecimal(String key);

    BigDecimal getBigDecimal(String key, int index);

    int getInt(String key);

    int getInt(String key, int index);

    /*    char getChar(String key);
        char getChar(String key, int index);


        byte getByte(String key);
        byte getByte(String key, int index);


        long getLong(String key);
        long getLong(String key, int index);


        float getFloat(String key);
        float getFloat(String key, int index);
     */

    double getDouble(String key);

    double getDouble(String key, int index);

    //set methods
    void set(String key, String value); //존재하면 업데이트 된다.

    void set(String key, String[] values);

    void setString(String key, String value);

    void setString(String key, String[] values);

    void set(String key, List list);

    void setRowCount(int row_count);

    void setColumnNames(String[] columnNames);

    //add methods
    void add(String key, String value); //존재하면 마지막 원소로 업데이트 된다.

    //파일 업로드 관련 인터페이스: 폼 데이타의 일부 중 파일업로드 컴포넌트가 있을 경우 유효한 메소드들
    public String getFileName(String name);

    public String getFileName(String name, int index);

    public String getFileType(String name);

    public String getFileType(String name, int index);

    public String getSaveDir(String name);

    public String getSaveDir(String name, int index);

    public long getFileLength(String name);

    public long getFileLength(String name, int index);
    
    public List getList(String key);

    /**
     * 파일에 Tray의 내용을 기록한다.
     * @param - fileName 주로 웹 화면에서 보내는 cmd 값이다.
     * @return - 저장된 파일의 전체경로-작업이 실패할 경우 삭제하는데 사용된다.
     */
    public String writeToFile(String fileName);

    //
    /**
     * Tray의 내용을 기록한 전체경로 파일명을 받아서 삭제한다.
     *  트랜잭션이 실패하면 2차저장소에 기록했던 파일을 삭제하는데 사용한다.
     * @param - fqfn 파일저장 위치를 fully qualified file name으로 입력받는다.
     * @return - 없음.
     */
    public void removeFile(String fqfn);

    /**
     * {@link ResultSetTray}처럼 하나의 key에 대해 모두 같은 개수의 value만 존재하는
     * tray의 경우에 구현해야 하는 method.
     * key마다 value의 갯수가 달라지는 Tray의 경우는 -1을 리턴하도록 구현해야 한다.
     *
     * @return
     */
    public int getRowCount();

    /*
        void setInt(String key);
        void setInt(String key, int index);

        void setChar(String key);
        void setChar(String key, int index);

        void setByte(String key);
        void setByte(String key, int index);

        void setLong(String key);
        void setLong(String key, int index);

        void setFloat(String key);
        void setFloat(String key, int index);

        void setDouble(String key);
        void setDouble(String key, int index);
     */
}
