/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.ctom.ctlab.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author griffinch
 */
public abstract class BaseJDBCDAO
{

	private static final String CONFIG_FILE = "/dataConnection.properties";

	// Logging File
	private static Logger log = Logger.getLogger("client");

	/**
	 * Gets the database Connection
	 * 
	 * @return Connection
	 */
	public Connection getConnection()
	{

		Connection result = null;
		Properties props = new Properties();
		// Get the file input stream
		try
		{
			InputStream stream = getClass().getResourceAsStream(CONFIG_FILE);
			props.load(stream);
			stream.close();
		}
		catch (FileNotFoundException e1)
		{
			log.error("The config file not found: " + CONFIG_FILE);
		}
		catch (IOException e1)
		{
			log.error("Error reading the config file: " + CONFIG_FILE);
		}
		// Read the properties from the properties file
		String fDriverName = (String) props.getProperty("driver");
		String fDbName = (String) props.getProperty("url");
		String fUserName = (String) props.getProperty("user");
		String fPassword = (String) props.getProperty("passwd");
		try
		{
			Class.forName(fDriverName).newInstance();
			log.debug("Check classpath. loaded db driver: " + fDriverName);
		}
		catch (Exception ex)
		{
			log.error("Check classpath. Cannot load db driver: " + fDriverName);
		}

		try
		{ // get the Connection
			result = DriverManager.getConnection(fDbName, fUserName, fPassword);
			if (result != null)
			{
				log.info("Connection to db obtained");

			}
			else
			{
				log.error("Unable to obtain connection to the db");
				throw (new NullPointerException("Null Connection object"));
			}

		}
		catch (SQLException e)
		{
			log.error("Driver loaded, but cannot connect to db: " + fDbName);
			log.error("Driver loaded, but cannot connect to db: " + e.getLocalizedMessage());
		}
		
		return result;
	}

	/**
	 * Retrieves the next Id value from the database. Executes the query
	 * depending on the database.
	 * 
	 * @param con
	 *            Connection
	 * @param seq
	 *            Sequence used to retrieve the next Id
	 * @return Id
	 */
	public final Long getNextVal(Connection con, String seq) throws SQLException
	{

		Long nextValue = null;
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		try
		{

			DatabaseMetaData metaData = con.getMetaData();
			String databaseName = metaData.getDatabaseProductName();
			//retrieve the next id to insert into database table  for Oracle
			if (databaseName.indexOf("Oracle") >= 0)
			{

				rs = stmt.executeQuery("select " + seq + ".nextval from dual");
				rs.next();
				nextValue = rs.getLong(1);
			}
			//retrieve the next id to insert into database table  for Postgres
			else
			{
				rs = stmt.executeQuery("select nextval('" + seq + "')");
				rs.next();
				nextValue = rs.getLong(1);

			}
		}
		finally
		{
			rs = SQLHelper.closeResultSet(rs);
			stmt = SQLHelper.closeStatement(stmt);
		}
		return nextValue;
	}

}
