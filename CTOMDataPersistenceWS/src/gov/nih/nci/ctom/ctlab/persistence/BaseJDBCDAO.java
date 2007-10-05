/*
 * Created on Jul 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.ctom.ctlab.persistence;




import gov.nih.nci.ctom.util.ServiceLocator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * @author griffinch
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class BaseJDBCDAO {

	
	
	
	
	public Connection getConnection() throws  SQLException, NamingException {
		

		
	    Connection result = null;
	    
		String fDriverName="oracle.jdbc.driver.OracleDriver";
		String fDbName="jdbc:oracle:thin:@cbiodb2-d.nci.nih.gov:1521:cccdev";
		String fUserName="caxchange";
		String fPassword="d3v!xchng";
		

	    try {
	       Class.forName(fDriverName).newInstance();
	     }
	     catch (Exception ex){
	        System.err.println("Check classpath. Cannot load db driver: " + fDriverName);
	     }

	     try {
	       result = DriverManager.getConnection(fDbName, fUserName, fPassword);
	     }
	     catch (SQLException e){
	        System.err.println( "Driver loaded, but cannot connect to db: " + fDbName);
	     }
	     return result;

		/*
		
		DataSource ds = null;
		ds = ServiceLocator.getDataSource(ServiceLocator.CTOM_DATASOURCE);
		Connection cn = ds.getConnection();
		return cn; 
		*/
	}
	
}
