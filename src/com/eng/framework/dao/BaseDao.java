package com.eng.framework.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * <pre>
 * Title: 리소스 해제
 * Description: Resources를 Close한다.
 * Copyright: Copyright (c) 2006
 * Company: www.UbiwareLab.com
 * @author yunkidon@hotmail.com
 * @version 1.0
 * </pre>
 */
public class BaseDao {

    /**
     * ResultSet close
     * @param rs ResultSet
     */
    protected void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
    }
    /**
     * Statement close
     * @param stmt Statement
     */
    protected void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
            }
        }
    }
    /**
     * Statement ,ResultSet close
     * @param stmt Statement
     * @param rs ResultSet
     */
    protected void close(Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
    }
    /**
     * ResultSet,stmt close
     * @param rs ResultSet
     * @param stmt Statement
     */
    protected void close(ResultSet rs, Statement stmt) {
        close(stmt, rs);
    }
}
