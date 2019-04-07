package com.dhitech.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import com.dhitech.framework.log.Log;

/**
 * 파일입출력 클래스를 사용하기 쉽게 재구현 하였습니다.
 *
 * @version 2003-01-10
 * @author 김종국
 */

/**
 * @author Administrator
 *
 */
public class FileIO {

    protected static final int BLKSIZE = 1024;
    
	private static String [] original = { "\\", "/", ":", "*", "?", "\"", "<", ">", "|" };
	
	private static String [] escaped = { "_", "_", "_", "_", "_", "_", "_", "_", "_" };
	
	private static int length;
	
	static {
		length = original.length;
	}

	public static String escape(String input) {
		for (int i = 0; i < length; i++)
			input = StringUtility.replaceAll(input, original[i], escaped[i]);

		return input;
	}

    private FileIO() {
    }

    /**
     * 파일이름으로 파일을 복사합니다.
     *
     * @param inName 원본 파일명입니다.
     * @param outName 복사될 파일명 입니다.
     */
    public static void copyFile(File inName, File outName)
        throws FileNotFoundException, IOException {
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(inName));
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outName));
        copyFile(is, os, true);
    }

    /**
     * 파일이름으로 파일을 복사합니다.
     *
     * @param inName 원본 파일명입니다.
     * @param outName 복사될 파일명 입니다.
     */
    public static void copyFile(String inName, String outName)
        throws FileNotFoundException, IOException {
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(inName));
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outName));
        copyFile(is, os, true);
    }

    /**
     * 열려있는 스트림으로부터 출력스트림으로 복사를 합니다.
     * close 를 통해 OutputStream 을 닫을 지 결정합니다.
     *
     * @param is InputStream 입니다.
     * @param os OutputSream 입니다.
     * @param close 메소드 호출 후 OutStream 폐쇄여부를 결정합니다.
     */
    public static void copyFile(InputStream is, OutputStream os, boolean close)
        throws IOException {
        int count = 0;
        byte b[] = new byte[BLKSIZE];
        while ((count = is.read(b)) != -1) {
            os.write(b, 0, count);
        }
        is.close();
        os.close();
    }

    /**
     * 열려져 있는 Reader 로 부터 열려져 Writer 로 복사를 합니다.
     * close 를 통해 Writer 을 닫을 지 결정합니다.
     *
     * @param is Reader 입니다.
     * @param os Writer 입니다.
     * @param close 메소드 호출 후 Write 폐쇄여부를 결정합니다.
     */
    public static void copyFile(Reader is, Writer os, boolean close) throws IOException {
        int b; // the byte read from the file
        while ((b = is.read()) != -1) {
            os.write(b);
        }
        is.close();
        if (close)
            os.close();
    }

    /**
     * 파일이름으로 부터 PrintWriter 방향으로 파일을 복사합니다.
     * close 를 통해 OutputStream 을 닫을 지 결정합니다.
     *
     * @param inName 파일이름을 나타냅니다.
     * @param pw 출력위치를 나태냅니다.
     * @param close 메소드 호출 후 출력스트림 폐쇄여부를 결정합니다.
     */
    public static void copyFile(String inName, PrintWriter pw, boolean close)
        throws FileNotFoundException, IOException {
        BufferedReader is = new BufferedReader(new FileReader(inName));
        copyFile(is, pw, close);
    }

    /**
     * 파일을 열고 첫번째 줄을 읽어 들입니다.
     *
     * @param inName 읽어들일 파일을 나타냅니다.
     * @return 첫번째 줄을 String 으로 반환합니다.
     */
    public static String readLine(String inName) throws FileNotFoundException, IOException {
        BufferedReader is = new BufferedReader(new FileReader(inName));
        String line = null;
        line = is.readLine();
        is.close();
        return line;
    }

    /**
     * Reader 를 읽어들여 문자열로 반환해 줍니다.
     * <p>
     * <pre>
     * exmaple:
     *
     *     String tmp = readerToString(new FileReader("D:/test.java"));
     * </pre>
     *
     * @param is 읽어들일 Reader 를 나타냅니다.
     * @return 읽어들인 Reader 내용을 문자열로 반환합니다.
     */
    public static String readerToString(Reader is) throws IOException {
        StringBuffer sb = new StringBuffer();
        char[] b = new char[BLKSIZE];
        int n;

        while ((n = is.read(b)) > 0)
            sb.append(b, 0, n);

        is.close();
        
        return sb.toString();
    }
    
    /**
     * InputStream 에서 문자열로 반환합니다.
     * <p>
     * <pre>
     * exmaple:
     *
     *     String tmp = inputStreamToString(new FileInputStream("d:/Test.java"));
     * </pre>
     *
     * @param is 읽어들일 InputStream 를 나타냅니다.
     * @return 읽어들인 InputStream 내용을 문자열로 반환합니다.
     */
    public static String inputStreamToString(InputStream is) throws IOException {
        return readerToString(new InputStreamReader(is));
    }
    
    public static byte [] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[BLKSIZE];
        int n;
        
        while ((n = is.read(buffer)) > 0)
            baos.write(buffer, 0, n);
        
        baos.flush();
        is.close();
        baos.close();
        
        return baos.toByteArray();        
    }
    
    /**
     * InputStream 에서 enc 형식에 맞게 인코딩한 문자열로 반환합니다.
     *
     * @param is 읽어들일 InputStream 를 나타냅니다.
     * @param enc 인코딩 형식을 나타냅니다.
     * @return 읽어들인 InputStream 내용을 문자열로 반환합니다.
     */
    public static String inputStreamToString(InputStream is, String enc) throws IOException {
        return readerToString(new InputStreamReader(is, enc));
    }
    
    public static byte [] fileToByte(File file) throws IOException {
        return inputStreamToByte(new FileInputStream(file));
    }
    
    public static byte [] fileToByte(String file) throws IOException {
        return inputStreamToByte(new FileInputStream(file));
    }
    
    /**
     * 파일을 읽어 들여 문자열로 반환합니다.
     *
     * @param file 읽어들일 파일 입니다.
     * @return 파일을 읽어들인 결과를 반환합니다.
     */
    public static String fileToString(File file) throws IOException {
        return inputStreamToString(new FileInputStream(file));
    }
    
    /**
     * 파일을 읽어 들여 문자열로 반환합니다.
     *
     * @param file 읽어들일 추상패스명입니다. 입니다.
     * @return 파일을 읽어들인 결과를 반환합니다.
     */
    public static String fileToString(String file) throws IOException {
        return inputStreamToString(new FileInputStream(file));
    }

    /**
     * 파일을 읽어 들여 문자열로 반환합니다.
     *
     * @param file 읽어들일 추상패스명입니다. 입니다.
     * @param enc 인코딩 형식을 나타냅니다.
     * @return 파일을 읽어들인 결과를 반환합니다.
     */
    public static String fileToString(String file, String enc) throws IOException {
        return inputStreamToString(new FileInputStream(file), enc);
    }

    /**
     * 문자열을 읽어 들여 파일에 저장합니다.
     *
     * @param text 저장 할 문자열입니다..
     * @param fileName 문자열을 저장 할 파일명입니다..
     */
    public static void stringToFile(String text, String fileName) throws IOException {
        BufferedWriter os = new BufferedWriter(new FileWriter(fileName));
        os.write(text);
        os.flush();
        os.close();
    }
    
    public static void stringToFile(String text, File fileName) throws IOException {
        BufferedWriter os = new BufferedWriter(new FileWriter(fileName));
        os.write(text);
        os.flush();
        os.close();
    }

    /**
     * 파일을 읽어서 BufferedReader 로 반환합니다.
     *
     * @param fileName 읽어들일 파일명을 나타냅니다.
     * @return 읽어들인 파일 내용을 반환합니다.
     */
    public static BufferedReader openFile(String fileName) throws IOException {
        return new BufferedReader(new FileReader(fileName));
    }


    /**
     * 이 추상 패스명이 나타내는 디렉토리를 생성합니다.
     * 이미 존재하는 패스명일 경우 true 값을 반환합니다.
     * 상위 폴더가 존재하지 않을경우는 모든 상위 폴더를 생성한 후 추상 패스명을 생성합니다.
     *
     * @param folderName지정된 파일의 추상 패스명
     * @return 추상패스명을 생성할 경우 성공할 경우는 true, 그렇지 않은 경우는 false 를 반환합니다.
     * @throws IOException 폴더를 생성하지 못했을 경우
     */
    public static boolean makeFolder(String folderName) throws IOException {
        File f = new File(folderName);
        if (f.exists()) return true;
        else return f.mkdirs();
    }


    /**
     * 이 추상 패스명이 나타내는 디렉토리를 생성합니다.
     * 이미 존재하는 패스명일 경우 true 값을 반환합니다.
     * 상위 폴더가 존재하지 않을경우는 모든 상위 폴더를 생성한 후 추상 패스명을 생성합니다.
     *
     * @param folderName지정된 파일의 추상 패스명
     * @return 추상패스명을 생성할 경우 성공할 경우는 true, 그렇지 않은 경우는 false 를 반환합니다.
     * @throws IOException 폴더를 생성하지 못했을 경우
     */
    public static boolean makeFolder(File folderName) throws IOException {
        return makeFolder(folderName);
    }
    
    
    /* 파일 삭제!!
     * 한번에 안지워질때가 있다..... 한번 더 확인 후 삭제
     * @param fileName
     * @return
     * @throws IOException
     */
    public static boolean deleteFile(String fileName) throws IOException{
    	File file = new File(fileName);
		try{
			if(!file.delete()){
				if(file.isFile()){
					if(file.delete()){
						Log.info("fileName : " + file.isFile());
					}
				}
			}
		}catch(Exception e){
			Log.info("DELETE FAIL : " + fileName);
		}
		
		return file.isFile();
    }
    
    public static boolean fileNameChange(String oriFileName, String newFileName) throws IOException{
    	File oriFile = new File(oriFileName);
    	File newFile = new File(newFileName);
    	
    	return oriFile.renameTo(newFile);  
    }


    public static void main(String[] args) {
        try {
            String st = fileToString("d:/Test.java", "euc-kr");
            System.out.println(st);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}