package com.dhitech.framework.connection;  

import java.sql.*;
import java.util.*;

import com.dhitech.framework.util.*;
import com.dhitech.framework.consts.*;

public final class ConnectionPool 
{
	private static final boolean debug = true;		// Print debug information to System.err.

	private Vector<Connection> free;							// Storage for the unused connections.
	private Vector<Connection> used;

	// Connection information.
	private String jdbcDriver;
	private String url;
	private String user;
	private String password ;
	private Properties info;
	private int initialCons = 0;					// Initial Connections
	private int maxCons = 0;						// Maximum number of concurrent connections allowed.
	private int numCons = 0;						// The number of connection that have been created.
	private boolean block;							// Whether to block until a connection is free when maxCons are in use.
	private long timeout;							// Timeout waiting for a connection to be released when blocking.
	private boolean reuseCons = true;				// Whether we should re-use connections or not
	

	public ConnectionPool(String jdbcDriver, String url, String user, String password, int initialCons, int maxCons, boolean block, long timeout) 
	  throws SQLException 
	{
		this.jdbcDriver = jdbcDriver;
		this.url = url;
		this.user = user;
		this.password = password;
		this.initialCons = initialCons;
		this.maxCons = maxCons;
		this.block = block;
		this.timeout = timeout;
		
		try {
			Class.forName(jdbcDriver);
			
			// maxCons has precedence over initialCons
			if (maxCons > 0 && maxCons < initialCons) {
				initialCons = maxCons;
			}
			
			// Create vectors large enough to store all the connections we make now.
			free = new Vector<Connection>(initialCons);
			used = new Vector<Connection>(initialCons);
	
			//System.out.println("free.size ["+ free.size() +"]");
			//System.out.println("used.size ["+ used.size() +"]");
			// Create some connections.
			while (numCons < initialCons){
				addConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} // end try
		
		
	}

	/**
	 * Closes all unallocated <code>connections</code>, allocated
	 * <code>connections</code> are marked for closing when they are released.
	 *
	 * @see	  #releaseConnection
	 * @see	  java.sql.Connection#close
	 **/
	public synchronized void closeAll() {
		// Close unallocated connections
		Enumeration cons = ((Vector)free.clone()).elements();
		while (cons.hasMoreElements()) {
			Connection con = (Connection)cons.nextElement();
			free.removeElement(con);
			numCons--;
			try {
				con.close();
			} catch (SQLException e) {
				// The Connection appears to be broken anyway, so we will ignore it
			} // end try
		} // end while
		// Move allocated connections to a list of connections that are closed
		// when they are released.
		cons = ((Vector)used.clone()).elements();
		while (cons.hasMoreElements()) {
			Connection con = (Connection)cons.nextElement();
			used.removeElement(con);
		} // end while
	}

	/**
	 * Gets the <code>block</code> property for the pool.
	 * The block values specifies whether a <code>getConnection()</code> request
	 * should wait for a <code>connection</code> to be release if the maximum
	 * allowed are all in use.
	 *
	 * @return	  The block property.
	 * @see	  #setBlock
	 * @see	  #getConnection
	 **/
	public boolean getBlock() {
		return block;
	}


	/**
	 * Gets a <code>Connection</code> from the pool.
	 *
	 * @return	  A connection
	 * @exception ConnectionPoolException if the maximum number of allowed
	 *		      connections are all in use, and, the "pool" is not
	 *		      blocking or the timeout expired when waiting.
	 * @exception SQLException if all existing connections are in use and a new
	 *		      one could not be created, this is the exception thrown
	 *		      by DriverManger when attempting to get a new connection.
	 * @see	  java.sql.DriverManager
	 **/
	public Connection getConnection() throws SQLException {
		return getConnection(this.block, timeout);
	}

	/**
	 * Gets a <code>Connection</code> from the pool.
	 *
	 * @param	  block		If a request for a connection should block until
	 *				a connection is released when none are available
	 *				and maxCons has been reached, overrides the
	 *  				value specified at construction.
	 * @param	  timeout	Maximum time to wait for a connection to be
	 *				released when maxCons are in use, overrides the
	 *				values specified at construction.
	 * @return	  A connection
	 * @exception ConnectionPoolException if the maximum number of allowed
	 *		      connections are all in use, and, the "pool" is not
	 *		      blocking or the timeout expired when waiting.
	 * @exception SQLException if all existing connections are in use and a new
	 *		      one could not be created, this is the exception thrown
	 *		      by DriverManger when attempting to get a new connection.
	 * @see	  java.sql.DriverManager
	 **/
	public synchronized Connection getConnection(boolean block, long timeout) throws SQLException {
	
		if(free.isEmpty()) {
	
			// None left, do we create more?
			if(maxCons <= 0 || numCons < maxCons) {
				addConnection();
			} else if (block) {
				try {
					long start = System.currentTimeMillis();
					do {
						wait(timeout);
						if (timeout > 0) {
							timeout -= System.currentTimeMillis() - start;
							if (timeout == 0) {
								timeout -= 1;
							}
						}
					} while (timeout >= 0 && free.isEmpty() && maxCons > 0 && numCons >= maxCons);
				} catch (InterruptedException e) {
				} // end try
		
				// Did anyone release a connection while we were waiting?
				if (free.isEmpty()) {
					/*
				 	* OK, nothing on the free list, but someone may have
				 	* released a connection that they closed, so the free list
				 	* is empty but numCons is now < maxCons and we can create a
				 	* new connection.
				 	*/
					if (maxCons <= 0 || numCons < maxCons) {
						addConnection();
					} else {
			  			throw new SQLException("Timeout waiting for a connection to be released");
					} // end if
				} // end if
			} else {
				// No connections left and we don't wait for more.
				throw new SQLException("Maximum number of allowed connections reached");
			}
		}  // end if
	
		// If we get this far at least one connection is available.
		Connection con;
		synchronized (used) {
			con = (Connection)free.lastElement();
			// Move this connection off the free list
			free.removeElement(con);
			used.addElement(con);
		}
		//System.out.println("GetConnection     : "  + con + "  FREE : " + free.size());
		return con;
	}
	
	/**
	 * Gets the <code>maxCons</code> property for the pool.
	 * The maxCons values specifies the maximum number of
	 * <code>Connections</code> that can be allocated from the pool at any one
	 * time.
	 *
	 * @return	  The maxCons property.
	 * @see	  #getConnection
	 **/
	public int getMaxCons() {
		return maxCons;
	}


	/**
	 * Gets the reuseConnections property for the pool.
	 *
	 * @see	  #setReuseConnections
	 **/
	public boolean getReuseConnections() {
		return reuseCons;
	}


	/**
	 * Gets the <code>timeout</code> property for the pool.
	 * The timeout values specifies how long to wait for a
	 * <code>Connection</code> to be release if the maximum allowed are all in
	 * use when <code>getConnection()</code> is called and <code>block</code> is
	 * true.
	 *
	 * @return	  The timeout property.
	 * @see	  #setTimeout
	 **/
	public long getTimeout() {
		return timeout;
	}



	/**
	 * Gets the <code>url</code> property for the pool.
	 * This property is the url for <code>Connections</code> opened by this
	 * pool.
	 *
	 * @return	  The url property.
	 **/
	public String getUrl() {
		return url;
	}

	/**
	 * Release a connection back to the pool.
	 * Apps should take care not to use a <code>Connection</code> after it has
	 * been released as it may well be in use somewhere else, in which case the
	 * effects are undefined.<p>
	 * If an app has cause to close a <code>Connection</code> then it should
	 * still be released so that the count of active transactions is correct and
	 * a new <code>Connection</code> can be created to take its place.<p>
	 * A rollback is performed on the <code>Connection</code> so if autoCommit
	 * is false any changes made that have not been committed will be lost.
	 *
	 * @param	  con	The Connection to put back in the pool.
	 * @exception UnownedConnectionException if the Connection did not come from
	 *		      this pool.
	 * @see	  #getConnection
	**/
	public synchronized void releaseConnection(Connection con)
			throws SQLException {

		boolean reuseThisCon = reuseCons;
		if(used.contains(con)) {
			// Move this connection from the used list to the free list
			used.removeElement(con);
			numCons--;
		} else {
			throw new SQLException("Connection " + con +
			" did not come from this ConnectionPool");
		} // end if
		try {
			if(reuseThisCon) {
				free.addElement(con);
				numCons++;
			} else {
				con.close();
			}
	   
			// Wake up a thread waiting for a connection
			notify();
		} catch (SQLException se) {
			/*
			* The Connection seems to be broken, but it's off the used list
			* and the connection count has been decremented.
			*/
			// Just to be sure
			try {
				con.close();
			} catch(Exception e) {
				// we're expecting an SQLException here
			} // end try
			notify();
		} // end try
		//System.out.println("ReleaseConnection : "  + con + "  FREE : " + free.size());
		//System.out.println( "DB_POOL ReleaseConnection : "  + con + "  FREE : " + free.size());
		
	}

	/**
	 * Sets the <code>block</code> property for the pool.
	 * The block values specifies whether a <code>getConnection()</code> request
	 * should wait for a <code>Connection</code> to be release if the maximum
	 * allowed are all in use.<p>
	 * Setting <code>block</code> to false will have no effect on any connection
	 * requests that have already begin to wait for a connection.
	 *
	 * @param	  block		The block property.
	 * @see	  #getBlock
	 **/
	public void setBlock(boolean block) {
		this.block = block;
	}

	/**
	 * Sets the reuseConnections property on the pool.
	 * If this property is false then whenever a <code>Connection</code> is
	 * released it will be closed.
	 *
	 * @see	  #getReuseConnections
	 * @see	  #releaseConnection
	 * @see	  java.sql.Connection
	 * @see	  java.sql.Connection#close
	 **/
	public synchronized void setReuseConnections(boolean reuseCons) {
		this.reuseCons = reuseCons;
	}


	/**
	 * Sets the <code>timeout</code> property for the pool.
	 * The timeout values specifies how long to wait for a
	 * <code>Connection</code> to be release if the maximum allowed are all in
	 * use when <code>getConnection()</code> is called and <code>block</code> is
	 * true.<p>
	 * Setting <code>timeout</code> will have no effect on any
	 * <code>Connection</code> requests that have already begin to wait for a
	 * <code>Connection</code>.
	 *
	 * @return  The timeout property.
	 * @see	  #getTimeout
	**/
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}


	/**
	 * Adds a new <code>Connection</code> to the pool.
	 *
	 * @exception SQLException if a connection could not be established.
	 **/
	private void addConnection() throws SQLException {
		free.addElement(getNewConnection());
	}


	/**
	 * Gets a new <code>Connection</code>.
	 *
	 * @exception SQLException if a connection could not be established.
	 **/
	private Connection getNewConnection() throws SQLException {
		Connection con = null;
		//System.out.println("About to connect to " + url);
 		try {
			con = DriverManager.getConnection(url, user, password);
		} catch(Exception e) {
			e.printStackTrace();
		} // end try
		++numCons;
		return con;
	}
	protected void finalize() {
		System.out.println("ConnectionPool Closed");
		for(int i=0;i<free.size();i++) {
			Connection conn = (Connection)free.elementAt(i);
			System.out.println("Close Connection : " + conn);
			if(conn != null) try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
		}
		
	}
}
