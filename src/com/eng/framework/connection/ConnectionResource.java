package com.eng.framework.connection; 

import java.sql.*;


public class ConnectionResource {

	private Connection conn = null;
	private ConnectionPoolManager cpm = null;
    public  String     mstrServerName = null;
	
	public ConnectionResource(String strServerName) throws Exception {
	    this.mstrServerName = strServerName;
		cpm = ConnectionPoolManager.getInstance(this.mstrServerName);
		conn = cpm.getConnection();
	}
	
	public Connection getConnection() throws Exception {
		if(conn == null) throw new Exception("Connection is NOT available!!");
		return conn;
	}
	
	public void release() {
		if(conn != null) {
			cpm.releaseConnection(conn);
			conn = null;
		}
	}
	
	  public void release(Statement stmt)
	  {
	        if(stmt != null) {
	        	try{
	        		stmt.close();
	        	}catch(Exception e) {}
				
			}
	        if(conn != null) {
				cpm.releaseConnection(conn);
				conn = null;
			}
	  }	

	  public void release(PreparedStatement pstmt)
	  {
	        if(pstmt != null) {
	        	try{
	        		pstmt.close();
	        	}catch(Exception e) {}
				
			}
	        if(conn != null) {
				cpm.releaseConnection(conn);
				conn = null;
			}
	  }	

	  public void release(Statement stmt, ResultSet rs)
	  {
	        if(rs != null) {
	        	try{
	        		rs.close();
	        	}catch(Exception e) {}
				
			}
	        if(stmt != null) {
	        	try{
	        		stmt.close();
	        	}catch(Exception e) {}
				
			}
	        if(conn != null) {
				cpm.releaseConnection(conn);
				conn = null;
			}
	  }	

	  public void release(PreparedStatement pstmt, ResultSet rs)
	  {
	        if(rs != null) {
	        	try{
	        		rs.close();
	        	}catch(Exception e) {}
				
			}
	        if(pstmt != null) {
	        	try{
	        		pstmt.close();
	        	}catch(Exception e) {}
				
			}
	        if(conn != null) {
				cpm.releaseConnection(conn);
				conn = null;
			}
	  }	

}
