/*
 * Created on Jul 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.ctom.ctlab.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author griffinch
 */
public abstract class BaseJDBCDAO
{
	private static final String CONFIG_FILE = "/dataConnection.properties";
	public Connection getConnection()
	{
	    Connection result = null;
	    
		//String fDriverName="oracle.jdbc.driver.OracleDriver";
		//String fDbName="jdbc:oracle:thin:@cbiodb30.nci.nih.gov:1521:cccqa";
		//String fDbName="jdbc:oracle:thin:@localhost:1521:xe";
		//String fUserName="caxchange";
		//String fPassword="caxchange";
	    
	    Properties props    =   new Properties();
	    //Get the file input stream
	    try {
	    	InputStream stream = getClass().getResourceAsStream(CONFIG_FILE);
			props.load(stream);
		} 
	    catch (FileNotFoundException e1) 
		{
			System.err.println("The config file not found: " + CONFIG_FILE);
		} 
		catch (IOException e1) 
		{
			System.err.println("Error reading the config file: " + CONFIG_FILE);
		}
	    //Read the properties from the properties file
		String fDriverName = (String)props.getProperty("driver");
	    String fDbName = (String)props.getProperty("url");
        String fUserName = (String)props.getProperty("user");
        String fPassword =(String) props.getProperty("passwd");
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
