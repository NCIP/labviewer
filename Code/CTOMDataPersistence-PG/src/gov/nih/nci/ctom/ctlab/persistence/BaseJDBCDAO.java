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

import org.apache.log4j.Logger;

/**
 * @author griffinch
 */
public abstract class BaseJDBCDAO
{
	private static final String CONFIG_FILE = "/dataConnection.properties";
	private static final Logger log = Logger.getLogger(BaseJDBCDAO.class);
	public Connection getConnection()
	{
	    Connection result = null;
	    Properties props    =   new Properties();
	    //Get the file input stream
	    try {
	    	InputStream stream = getClass().getResourceAsStream(CONFIG_FILE);
			props.load(stream);
		} 
	    catch (FileNotFoundException e1) 
		{
	    	log.error("The config file not found: " + CONFIG_FILE);
	    			} 
		catch (IOException e1) 
		{
			log.error("Error reading the config file: " + CONFIG_FILE);
		}
	    //Read the properties from the properties file
		String fDriverName = (String)props.getProperty("driver");
	    String fDbName = (String)props.getProperty("url");
        String fUserName = (String)props.getProperty("user");
        String fPassword =(String) props.getProperty("passwd");
	    try
	    {
	       Class.forName(fDriverName).newInstance();
	       System.out.println("Check classpath. loaded db driver: " + fDriverName);
	    }
	    catch (Exception ex)
	    {
	    	log.error("Check classpath. Cannot load db driver: " + fDriverName);
	     }

	    try
	    {
	    	result = DriverManager.getConnection(fDbName, fUserName, fPassword);
	    	if(result!=null)
	    	{
	    		log.info("Connection to db obtained");
	    		System.out.println("Connection to db obtained");
	    	}else
	    	{
	    		log.error("Unable to obtain connection to the db");
	    		System.out.println("Unable to obtain connection to the db");
	    		throw(new NullPointerException("Null Connection object"));
	    	}
	    	
	    }
	    catch (SQLException e)
	    {
	    	log.error( "Driver loaded, but cannot connect to db: " + fDbName);
	    	log.error( "Driver loaded, but cannot connect to db: " + e.getLocalizedMessage());
	    }
	    return result;
	}
	
}
