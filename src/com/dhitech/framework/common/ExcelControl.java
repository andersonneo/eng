package com.dhitech.framework.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.JXLException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.RowsExceededException;

public class ExcelControl {
	private String sheetName;
	private ResultMap resut=null; 
	private ResultMap wirtexls=null;
	public ExcelControl()
	{}
    public ExcelControl(String filename, int columnCnt) throws BiffException, IOException
    {
    	this.resut= readExcelfile(filename, columnCnt);
    }
    public void setWirtexls(ResultMap wirtexls)
    {
    	this.wirtexls=wirtexls;
    }
    private ResultMap readExcelfile(String filename, int columnCnt) throws BiffException, IOException
    {
    	ResultMap results = null;
        Workbook workbook = Workbook.getWorkbook(new File(filename));
        Sheet sheet = workbook.getSheet(0);
        this.sheetName = sheet.getName();
        int columnCount =sheet.getColumns();
        int blankColumn = columnCnt-columnCount;
        results = new ResultMap(columnCnt);
        for(int i=0;i<sheet.getRows();i++)
        {
        	if(i==0)
        	{
	        	for(int j=0;j<columnCount;j++)
	        	{
	        		Cell cell = null;
	        		cell = sheet.getCell(j, i);
	        		results.addColumnName(cell.getContents());
	        	}
	        	if (blankColumn > 0){
	        		for(int k=0;k<blankColumn;k++)
		        	{
		        		results.addColumnName(" ");
		        	}
	        	}
        	}
        	else
        	{
	        	for(int j=0;j<columnCount;j++)
	        	{
	        		Cell cell = null;
	        		cell = sheet.getCell(j, i);
	        		results.add(cell.getContents());
	        	}
	        	if (blankColumn > 0){
	        		for(int k=0;k<blankColumn;k++)
		        	{
		        		results.add(" ");
		        	}
	        	}
        	}
        }
        return results;
    }
    public ResultMap getResult()
    {
    	return resut;
    }
    public void setResult(ResultMap rm)
    {
    	this.wirtexls=rm;
    }
    /**
     * writeExcelfile(저장할 파일명,결과 셋)
     * */
    public boolean writeExcelfile(String file,ResultMap rm) throws IOException, RowsExceededException, JXLException
    {
    	boolean bret=false;
    	this.wirtexls=rm;
    	bret=writeExcelfile(file);
    	return bret;
    }
    public boolean writeExcelfile(String file) throws IOException, RowsExceededException, JXLException
    {
    	boolean bret=false;
    	WritableWorkbook workbook = Workbook.createWorkbook(new File(file)); 
    	WritableSheet sheet = workbook.createSheet("write", 0);
    	ArrayList columnNames=wirtexls.columnNames;
        if (columnNames.size() < 1)
            return (bret);

	    for (int col = 0; col < wirtexls.columnNames.size(); col++) {
	    	Label label = new Label(col,0 , (String)columnNames.get(col));
	    	sheet.addCell(label);
	    }
	
	    for (int row = 0; row < wirtexls.getRowCount(); row++) {
	            for (int col = 0; col < columnNames.size(); col++) {
	            	Label label = new Label(col, row+1, (String)wirtexls.getString(row, col));
	            	sheet.addCell(label); 
	            }
	    }
    	workbook.write(); 
    	workbook.close(); 

    	return bret;    	
    }
    public static void main(String args[])
    {
    	ExcelControl ex=new ExcelControl();
    	try {
			ex.writeExcelfile("aa");
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JXLException e) {
			e.printStackTrace();
		}
    }
    
	/**
	 * @return the sheetName
	 */
	public String getSheetName() {
		return sheetName;
	}
	/**
	 * @param sheetName the sheetName to set
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
}

