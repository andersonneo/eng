package com.dhitech.framework.tray;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.dhitech.framework.config.ConfigFactory;
import com.dhitech.framework.log.Log;

/**
 * 참조.
 * // Copyright (C) 1998 by Jason Hunter <jhunter@acm.org>.  All rights reserved.
 * // Use of this class is limited.  Please see the LICENSE for more information.
 *
 * //com.oreilly.servlet.MultipartRequest;
 *
 * 버그 1: parseMultipartRequest() 메소드 내에서 컨텐트 타입으로 얻어낸 boundary와
 *      첫 라인을 읽어들여 얻어낸 boundary 사이에 -가 두개 차이가 나고 있다.
 *      임시변통으로 startsWith 대신 indexOf를 사용한다.
 * 버그 2: MultipartRequest일 경우에는 한글 문제 발생하고 있음. (컨테이너 혹은 개발자 책임?)
 * 버그 3: Mac Binary File에 대한 저장은 고려되지 않았음.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Nucha Network Systems</p>
 * @author NUCHA
 * @version 1.0
 */
public class DhitechMultipartRequestTrayFactory extends RequestTrayFactory {
    /**
     * Properties 객체
     */
    private static ConfigFactory cf = ConfigFactory.getInstance();
    private static String dir = null;
    private static String uploadName= null;
    
    //private static String dir = ConfigurationManager.getInstance().getConfiguration("base").get("file.upload.path");

    private int maxSize = 10 * 1024 * 1024; //10 Mega
    private static final String FILE_UNKNOWN = "unknown";
    private static final String DEFAULT_CONTENT_TYPE =
        "application/octet-stream";

    protected Tray createTray(HttpServletRequest request) {
        try{
           dir = cf.getConfiguration("base.properties").getString("file.upload.path");
           uploadName = cf.getConfiguration("base.properties").getString("file.upload.name");
        }catch(Exception ex){}
        Log.debug("\n file.upload.path : " + dir);
        MultipartRequestTray multipartRequestTray = new MultipartRequestTray();
        File f = new File(dir);
        if (!f.isDirectory()) {
            Log.debug("..... it's not a directroy");
            throw new java.lang.IllegalStateException(dir + "은 디렉토리가 아닙니다.");
        }

        if (!f.canWrite()) {
            Log.debug("..... can't write");
            throw new java.lang.IllegalStateException(dir +
                "은 쓰기가 불가능한 디렉토리입니다.");
        }
        try {
            multipartRequestTray = saveFile(request);
        } catch (Exception ex) {
            Log.debug(" at MultipartRequest createTray() " + ex);
        }

        return multipartRequestTray;
    }

    private MultipartRequestTray saveFile(HttpServletRequest request) throws
        Exception {
        MultipartRequestTray multipartRequestTray = new MultipartRequestTray();
        MultipartRequest multi = new MultipartRequest(request, dir, maxSize);
        Enumeration files = multi.getFileNames();

        String fname = ""; //파라메타명
        String filename = ""; //파라메타명의 파일명
        String maskname = "";

        long filesize = 0; //file size

        while (files.hasMoreElements()) {
            maskname = "" + System.currentTimeMillis();
            fname = (String) files.nextElement();
            filename = multi.getFilesystemName(fname);
            System.out.println(fname+":"+filename);
            File f = multi.getFile(fname);
            if (f != null) {
                // Rename file
                filesize = f.length();
                if (filesize == 0) {
                    filename = "";
                    maskname = "";
                    continue;
                }

                File up1 = new File(dir + "/" + filename);
                multipartRequestTray.setString(fname, filename);
                multipartRequestTray.setString(fname + "_size", "" + f.length());

                File up2 = new File(dir + "/" + filename);
                multipartRequestTray.setString(fname + "_maskname", maskname);

                while (true) { //파일명이 중복 않되게 딜레이 시킴
                    if (!maskname.equals(System.currentTimeMillis() + "")) {
                        break;
                    }
                }

                if (up1.exists()) {
                    boolean rslt = up1.renameTo(up2);
                }
            } else {
                filename = "";
                maskname = "";
            } // end if
        } //end while

        Enumeration namesEnum = multi.getParameterNames();
        while (namesEnum.hasMoreElements()) {
            String name = (String) namesEnum.nextElement();
            String[] values = multi.getParameterValues(name);

            multipartRequestTray.set(name, values);
        }
        //Log.debug(multipartRequestTray.toString());
        return multipartRequestTray;
    }
}
