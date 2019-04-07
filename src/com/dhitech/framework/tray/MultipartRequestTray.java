/**
 * 파일명: com.kyeryong.framework.tray.MultipartRequestTray.java
 * 파일개요: 클라이언트 요청 정보를 캡슐화한 클래스
 * 저작권: Copyright (c) 2003 by SK C&C. All rights reserved.
 * 작성자: 박찬우 (nucha@dreamwiz.com)
 */

package com.dhitech.framework.tray;

//Java API
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * MultipartRequestTray 클래스는 multipart/form-data를 저장하기 위한 Tray이다.
 * <p>
 * @author <b>박찬우</b>
 * @version 1.0, 2004/02/01
 */

public class MultipartRequestTray extends AbstractTray implements java.io.Serializable {
    private Map files;
    private String boundary;

    public MultipartRequestTray() {
        files = new HashMap();
    }

    /**
     * multipart/form-data의 boundary를 저장한다.
     * @param boundary
     */
    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    /**
     * multipart/form-data의 boundary를 리턴한다.
     * @return
     */
    public String getBoundary() {
        return this.boundary;
    }

    /**
     * Tray가 저장하고 있는 내용물에 대한 열거를 제공한다.
     * @return - Enumeration
     */
    public Enumeration getEnumeration() {
        return null;
    }

    /**
     * 업로드한 파일 정보를 저장한다.
     * @param key - 폼 구성요소의 이름
     * @param saveDir - 파일 저장 디렉토리
     * @param fileName - 업로드한 파일명
     * @param type - 업로드한 파일의 타입
     */
    public void addFiles(String key, String saveDir, String fileName, String type) {
        Object obj = files.get(key);
        ArrayList fileList = null;
        if (obj == null) {
            fileList = new ArrayList();
            files.put(key, fileList);
        } else {
            fileList = (ArrayList) obj;
        }

        UploadFile uploadFile = new UploadFile(saveDir, fileName, type);
        fileList.add(uploadFile);
    }

    /**
     * 업로드한 파일명을 리턴한다.
     * @param key - 폼 구성요소의 이름
     * @return - 파일명
     */
    public String getFileName(String key) {
        return getFileName(key, 0);
    }

    /**
     * 파일명을 리턴한다.
     * @param key - 폼의 구성요소 이름
     * @param index - index번째 요소
     * @return - 파일명
     */
    public String getFileName(String key, int index) {
        UploadFile uploadFile = getUploadFile(key, index);
        return uploadFile.getFileName();
    }

    /**
     * 파일 타입을 리턴한다.
     * @param key - 폼 구성요소 이름
     * @return - 파일타입
     */
    public String getFileType(String key) {
        return getFileType(key, 0);
    }

    /**
     * index번째의 파일 타입을 리턴한다.
     * @param key - 폼 구성요소 이름
     * @param index - index번째 요소
     * @return - 파일 타입
     */
    public String getFileType(String key, int index) {
        UploadFile uploadFile = getUploadFile(key, index);
        return uploadFile.getType();
    }

    /**
     * 저장 디렉토리명을 리턴한다.
     * @param key - 폼 구성요소 이름
     * @return - 저장디렉토리명
     */
    public String getSaveDir(String key) {
        return getSaveDir(key, 0);
    }

    /**
     * 저장 디렉토리명을 리턴한다.
     * @param key - 폼 구성요소 이름
     * @param index - index번째 요소
     * @return - 저장디렉토리명
     */
    public String getSaveDir(String key, int index) {
        UploadFile uploadFile = getUploadFile(key, index);
        return uploadFile.getDir();
    }

    /**
     * 파일크기를 리턴한다.
     * @param key - 폼 구성요소 이름
     * @return - 파일 크기
     */
    public long getFileLength(String key) {
        return getFileLength(key, 0);
    }

    /**
     * 파일크기를 리턴한다.
     * @param key - 폼 구성요소 이름
     * @param index - index번째 요소
     * @return - 파일 크기
     */
    public long getFileLength(String key, int index) {
        UploadFile uploadFile = getUploadFile(key, index);
        return uploadFile.getFileLength();
    }

    private UploadFile getUploadFile(String key) {
        return getUploadFile(key, 0);
    }

    private UploadFile getUploadFile(String key, int index) {
        ArrayList fileList = (ArrayList) files.get(key);
        return (UploadFile) fileList.get(index);
    }

    /**
     * Tray 내부의 Map에 저장된 키들의 갯수를 리턴한다.
     * @return - 키의 갯수
     */
    public int size() {
        int elementSize = super.size();
        int fileElementSize = files.size();
        return elementSize + fileElementSize;
    }

    /**
     * [일반 폼 구성요소 정보/파일업로드 구성요소/추가정보]들의 정보를 문자열로 리턴
     * @return - Tray의 내용에 대한 문자열 정보
     */
    public String toString() {
        String requestInfo = super.toString();

        StringBuffer fileInfo = new StringBuffer();
        String key = null;
        Set set = files.keySet();
        Iterator it = set.iterator();

        while (it.hasNext()) {
            key = (String) it.next();
            List list = (List) files.get(key);
            for (int i = 0; i < list.size(); i++) {
                UploadFile eachUploadFile = (UploadFile) list.get(i);
                fileInfo.append(key).append(": \n").append("    ").append(eachUploadFile.toString());
            }
        }

        return requestInfo + "\n\n" + fileInfo;
    }

    class UploadFile implements java.io.Serializable {
        private String saveDir;
        private String fileName;
        private String type;

        UploadFile(String dir, String fileName, String type) {
            this.saveDir = dir;
            this.fileName = fileName;
            this.type = type;
        }

        public String getDir() {
            if (saveDir == null) {
                return "";
            }
            return saveDir;
        }

        public String getType() {
            if (type == null) {
                return "";
            }
            return type;
        }

        public String getFileName() {
            if (fileName == null) {
                return "";
            }
            return fileName;
        }

        public long getFileLength() {
            if (saveDir == null || fileName == null) {
                return 0;
            } else {
                return new File(saveDir + File.separator + fileName).length();
            }
        }

        public String toString() {
            String s = "    dir: " + getDir() + "\n" + "    fileName: " + getFileName() + "\n" + "    type: " + getType() + "\n" + "    length: " + getFileLength();
            return s;
        }
    }

    public void setRowCount(int row_count){
    }

    public void setColumnNames(String[] columnNames){
    }
}
