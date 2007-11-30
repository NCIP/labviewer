/*
 * Created on Jul 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.ctom.ctlab.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author griffinch
 */
public abstract class BaseJDBCDAO
{
	
	public Connection getConnection()
	{
	    Connection result = null;
	    
		String fDriverName="oracle.jdbc.driver.OracleDriver";
		//String fDbName="jdbc:oracle:thin:@cbiodb30.nci.nih.gov:1521:cccqa";
		String fDbName="jdbc:oracle:thin:@localhost:1521:xe";
		String fUserName="caxchange";
		String fPassword="caxchange";

	    try
	    {
	       Class.forName(fDriverName).newInstance();
	    }
	    catch (Exception ex)
	    {
	        System.err.println("Check classpath. Cannot load db driver: " + fDriverName);
	    }

	    try
	    {
	        result = DriverManager.getConnection(fDbName, fUserName, fPassword);
	    }
	    catch (SQLException e)
	    {
	        System.err.println( "Driver loaded, but cannot connect to db: " + fDbName);
	    }
	    
	    return result;
	}
	
}
