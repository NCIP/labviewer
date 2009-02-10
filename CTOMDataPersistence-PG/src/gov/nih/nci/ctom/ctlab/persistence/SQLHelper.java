/**
 * 
 */
package gov.nih.nci.ctom.ctlab.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * @author asharma
 *
 */
public class SQLHelper {
	 public static final Logger  logger  = Logger.getLogger(SQLHelper.class.getName());
		   
    /**
     * @param ps
     */
    public static PreparedStatement closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
              
            } catch (SQLException e1) {
            	logger.error("Failed to close PreparedStatement", e1);
            }
            ps = null;
        }
        return ps;
       
    }
  
    /**
     * @param stmt
     */
    public static Statement closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
            	stmt.close();
            	
            } catch (SQLException e1) {
            	logger.error("Failed to close Statement", e1);
            }
            stmt = null;
        }
        return stmt;
    }
    /**
     * @param rs
     */
    public static ResultSet closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
               
            } catch (SQLException e1) {
            	logger.error("Failed to close ResultSet", e1);
            }
            rs = null;
        }
        return rs;
       
    }
    
    /**
     * @param cstmt
     */
    public static CallableStatement closeCallableStatement(CallableStatement cstmt) {
        if (cstmt != null) {
            try {
            	cstmt.close();
           
            } catch (SQLException e1) {
            	logger.error("Failed to close CallableStatement", e1);
            }
         	cstmt = null;
        }
        return cstmt;
    }  
        
    /**
     * @param con
     */
    public static Connection closeConnection(Connection con){
            try
            {
              if (con != null && !con.isClosed())
                    con.close();
            }catch (SQLException e1) {
            	logger.error("Failed to close Connection", e1);
            }catch (Exception e){
                logger.fatal("Error closing the connection ", e);
            }
            
            con = null;
            return con;
        }
  
 }

