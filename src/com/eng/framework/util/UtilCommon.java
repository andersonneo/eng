package com.eng.framework.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
  
public class UtilCommon
{
    public 	static final String FILLER        = " ";
    public 	static final int    FILLER_OPTION = 0;
    public 	static final String ZERP          = "0";
    public 	static final int    ZERP_OPTION   = 1;
    private static final int    millisPerHour = 60 * 60 * 1000;

  	public static long doCvrtStrToLong(String sSrc)
  	{
		long rV = 0L;

    	try
    	{
			if( sSrc.trim().length() == 0 )
				rV = 0L;
			else
				rV = Long.parseLong(sSrc.trim());
    	}
    	catch(Exception e)
    	{
    	  	e.printStackTrace();
    	}
    	return rV;
  	}




  	public static String strAppendZero(String getAmount, int intLen)
  	{
    	String   imsiString  = "";
    	int      intLength   = 0;
    	try
    	{
    		imsiString = getAmount;
    	   	if (getAmount.length() < intLen)
    	   	{
    	      	intLength = intLen - getAmount.length();
    	      	for (int i = 0 ; i < intLength ; i++)
    	      	{
    	         	imsiString = "0" + imsiString ;
    	      	}
    	   	}
    	}
    	catch(Exception e)
    	{
    	  	e.printStackTrace();
    	}
    	return imsiString;
  	}


    public static String getNullToBlank(String src)
    {
        if ( src == null )
            return " ";
        else
            return src;
    }

    public static String getNullToStr(String src, String str)
    {
        if ( src == null )
            return str;
        else
            return src;
    }

    public static String getFillStr(String src, String fillStr, int size, int opt)
    {
        if ( src.length() >= size )
            return src;

        int cnt = size - src.length();
        for ( int i = 0 ; i < cnt ; i++ )
        {
            if ( opt == 1 )
                src = fillStr + src;
            else
                src = src + fillStr;
        }

        return src;
    }

    // �־��� ���̸�ŭ ' '�� ä�� �����ϴ� �޼ҵ�
    public static String fillSpace(int count)
    {
        String sReturn = "";

        for(int i=0; i < count; i++)
            sReturn += " ";

        return sReturn;
    }

    // �־��� ���� ��ŭ '0'�� ä�� ���ڸ� �����ϴ� �޼ҵ�
    public static String fillZeros( int count)
    {
        String sReturn = "";

        for(int i=0; i < count; i++)
            sReturn += "0";

        return sReturn;
    }

    // �־��� ���̿��� ������ ���̸� �� ����ŭ '0'�� �տ� ä�� ���ڸ� �����ϴ� �޼ҵ�
    public static String fillZeros( int tLen, String source)
    {
        if (source.equals("null") || (source == null) )
            source = "";

        int mLen = source.trim().length();

        for ( int i=0 ; i < ( tLen - mLen ) ; i++ )
            source = "0" + source;

        return source;
    }

    // ���� 0 �����ϰ� �����ϴ� �޼ҵ�
    // ��) 00000 ==> 0 , 00001 ==> 1 , 00100 ==> 100
    public static String deleteZero(String amt)
    {
        if(amt.equals("000000000000") || amt.equals(""))
        {
            amt = "0";
        }
        else
        {
            int count = 0;
            while(amt.substring(0,1).equals("0"))
            {
                count = amt.length();
                amt = amt.substring(1,count);
            }
        }

        return amt;
    }

    // String ==> char[] �� ä�� �־� char[]�� �����ϴ� �޼ҵ�
    public static char[] getCharArray(String sSource, char[] cDestination)
    {
        sSource.getChars(0, sSource.length(), cDestination, 0);
        return cDestination;
    }

    // char[] ==> byte[] �� ä�� �־� byte[]�� �����ϴ� �޼ҵ�
    public static byte[] cstobs(char cs[])
    {
        byte ret[] = new byte[cs.length];
        for(int i = 0; i < cs.length; i++)
            ret[i] = (byte)cs[i];

        return ret;
    }

    //byte[] ==> String ���� ��ȯ�Ͽ� �����ϴ� �޼ҵ�
    public static String bstostr(byte bs[], int position, int length)
    {
        byte[] bReturn = new byte[length];
        System.arraycopy(bs, position, bReturn, 0, length);

        return new String(bReturn);
    }

    //�߸� ǥ���� ��Ʈ���� �����ڵ� �ѱ� �ڵ尪�� ��Ʈ������ ��ȯ���ִ� �޼ҵ�
    public static String Uni2Ksc(String str) throws UnsupportedEncodingException
    {
        if(str == null)
            return null;

        return new String(str.getBytes("ISO-8859-1"),"euc-kr");
    }

    //�����ڵ� �ѱ� �ڵ尪���� ǥ���� ��Ʈ���� �߸� ǥ���� ��Ʈ������ ��ȯ���ִ� �޼ҵ�
    public static String Ksc2Uni(String str) throws UnsupportedEncodingException
    {
        if(str == null)
            return null;

        return new String(str.getBytes("euc-kr"),"ISO-8859-1");
    }

    // ����Ǵ� �ý��ۿ� �� �̽������� ����(���ο� ��)�� ��� �޼ҵ�
    public static String getEsc()
    {
        String strOsName = System.getProperty("os.name").toLowerCase();
        String m_strLF   = "";

        if( strOsName.indexOf("window") != -1 )
            m_strLF = "\r\n";
        else
            m_strLF = "\n";

		return m_strLF;
    }

    // �ý����� ���� ��¥�� ��� �޼ҵ�(yyyyMMdd)
    public static String getDate()
    {
        SimpleDateFormat sdf     = new SimpleDateFormat("yyyyMMdd");
        sdf.setTimeZone(new SimpleTimeZone( 9 * millisPerHour, "KST"));

        return sdf.format(new Date());
    }

    //�ý����� ���� �ð��� ��� �޼ҵ�(HH:mm:ss:SSS)
    public static String getTime()
    {
        SimpleDateFormat sdf     = new SimpleDateFormat("HH:mm:ss:SSS");
        sdf.setTimeZone(new SimpleTimeZone( 9 * millisPerHour, "KST"));

        return sdf.format(new Date());
    }

    //�ý����� ���� �ð��� ��� �޼ҵ�(HH:mm:ss:SSS)
    public static String getTimeHH_MM_SS()
    {
        SimpleDateFormat sdf     = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(new SimpleTimeZone( 9 * millisPerHour, "KST"));

        return sdf.format(new Date());
    }
    
    //�ý����� ���� �ð��� ��� �޼ҵ�(HH:mm:ss:SSS)
    public static String getTimeS()
    {
        SimpleDateFormat sdf     = new SimpleDateFormat("HHmmssSSS");
        sdf.setTimeZone(new SimpleTimeZone( 9 * millisPerHour, "KST"));

        return sdf.format(new Date());
    }


    //�ý����� ����ð��� ��� �޼ҵ�(HHmmss)
    public static String getTime_HHmmss()
    {
        SimpleDateFormat sdf     = new SimpleDateFormat("HHmmss");
        sdf.setTimeZone(new SimpleTimeZone( 9 * millisPerHour, "KST"));

        return sdf.format(new Date());
    }

    //�ý����� ����ð��� ��� �޼ҵ�(HHmmss)
    public static String getHHmmss()
    {
        SimpleDateFormat sdf     = new SimpleDateFormat("HHmmss");
        sdf.setTimeZone(new SimpleTimeZone( 9 * millisPerHour, "KST"));

        return sdf.format(new Date());
    }
    
    //�־��� ���信 ��� �ý����� ���糯¥/�ð��� ��� �޼ҵ�
    public static String getDateFormat(String format)
    {
        SimpleDateFormat sdf     = new SimpleDateFormat(format);
        sdf.setTimeZone(new SimpleTimeZone( 9 * millisPerHour, "KST"));

        return sdf.format(new Date());
    }


    public static void assignString(String in, byte out[]) {

   	    byte tin[] = in.getBytes();
        int tlen = tin.length;

        if(tlen <= out.length) {
 	        System.arraycopy(tin, 0, out, 0, tlen);
        	for(int i = tlen; i < out.length; i++)
        	    out[i] = 32;
    	}
    	else {
            System.arraycopy(tin, 0, out, 0, out.length);
        }
    }

    public static void assignBytes(byte[] in, byte out[]) {

        int len = in.length;

        if(len <= out.length) {
 	        System.arraycopy(in, 0, out, 0, len);
        	for(int i = len; i < out.length; i++)
        	    out[i] = 32;
    	}
    	else {
            System.arraycopy(in, 0, out, 0, out.length);
        }
    }


    // DB ���� Sequence ���ؼ� Number fetching.....
    public static String getHostSerialNumFromDB() {

        String strRetVal = null;

        try {

        } catch(Exception e) {

        } finally {

        }
        return strRetVal;
    }
    
      /*
     * edited by shpark 2008.8.05
     * byte�� 4byte int������ ��ȯ
     */
    public static int byte4ToInt(byte[] buff)
    {
        int num = 0;
        num = ((buff[0] & 0xff) << 24) 
        +((buff[1] & 0xff) << 16) 
        +((buff[2] & 0xff) << 8) 
        +(buff[3] & 0xff)  ;
        return num;
    }
    
}