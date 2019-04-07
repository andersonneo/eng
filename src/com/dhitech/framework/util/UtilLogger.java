package com.dhitech.framework.util;
  
import java.io.File;
import java.io.FileWriter;
import com.dhitech.framework.consts.*; 

public class UtilLogger {
  	
	/*
	 * ����͸��� ���� �� ���� �ϴ� �α׵鸸 ȣ���Ͽ� ����� ��
	 * Run Time/Debug ���� ������ �α� ���� 
	 */
    public static void DBaylog(String dir, String msg)
    {
        FileWriter outfile = null;

        //DEBUG_MODE�� ��츸 �α׸� �����.
        if(Constant.DEBUG_MODE == false) return;
        
        try
        {
            File file = new File(dir);

            if(!file.exists())
                file.mkdir();

            outfile = new FileWriter( dir + "log." + UtilCommon.getDate(), true );
            outfile.write( UtilCommon.getTime() + " -- " + msg + UtilCommon.getEsc());
            outfile.flush();
        }
        catch( Exception e )
        {
        }
        finally
        {
            try
            {
                outfile.close();
            }
            catch(Exception e)
            {
            }
        }
    }    

	/*
	 * Debug ��忡���� �α׸� ����� �Լ�
	 */
	public static void Debuglog(String dir, String msg)
    {
        FileWriter outfile = null;

        //DEBUG_MODE�� ��츸 �α׸� �����.
        if(Constant.DEBUG_MODE == false) return;
        
        try
        {
            File file = new File(dir);

            if(!file.exists())
                file.mkdir();

            outfile = new FileWriter( dir + "log." + UtilCommon.getDate(), true );
            outfile.write( UtilCommon.getTime() + " -- " + msg + UtilCommon.getEsc());
            outfile.flush();
        }
        catch( Exception e )
        {
        }
        finally
        {
            try
            {
                outfile.close();
            }
            catch(Exception e)
            {
            }
        }
    }  
	
	/*
	 * ����͸��� ���� �� ���� �ϴ� �α׵鸸 ȣ���Ͽ� ����� ��
	 * Run Time/Debug ���� ������ �α� ���� 
	 */
    public static void DBaylog(String dir, String strThreadName, String msg)
    {
        FileWriter outfile = null;

        //DEBUG_MODE�� ��츸 �α׸� �����.
        if(Constant.DEBUG_MODE == false) return;
        
        try
        {
            File file = new File(dir);

            if(!file.exists())
                file.mkdir();

            outfile = new FileWriter( dir + strThreadName + "." + UtilCommon.getDate(), true );
            outfile.write( UtilCommon.getTime() + " -- " + msg + UtilCommon.getEsc());
            outfile.flush();
        }
        catch( Exception e )
        {
        }
        finally
        {
            try
            {
                outfile.close();
            }
            catch(Exception e)
            {
            }
        }
    } 

	/*
	 * Debug ��忡���� �α׸� ����� �Լ�
	 */
	public static void Debuglog(String dir, String strThreadName, String msg)
    {
        FileWriter outfile = null;

        //DEBUG_MODE�� ��츸 �α׸� �����.
        if(Constant.DEBUG_MODE == false) return;
        
        try
        {
            File file = new File(dir);

            if(!file.exists())
                file.mkdir();

            outfile = new FileWriter( dir + strThreadName + "."+ UtilCommon.getDate(), true );
            outfile.write( UtilCommon.getTime() + " -- " + msg + UtilCommon.getEsc());
            outfile.flush();
        }
        catch( Exception e )
        {
        }
        finally
        {
            try
            {
                outfile.close();
            }
            catch(Exception e)
            {
            }
        }
    }  	
}
