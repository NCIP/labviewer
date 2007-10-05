package gov.nih.nci.ctom.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceLocator {

	public static String CTOM_DATASOURCE="java:/ctomDB";
	
	public static DataSource getDefaultCTOMDataSource() throws NamingException{
		Context ctx = new InitialContext();  
    	return  (DataSource)ctx.lookup("java:/ctomDB");		
	}
	
	


	public static DataSource getDataSource(String name) throws NamingException{
		Context ctx = new InitialContext();  
    	return  (DataSource)ctx.lookup(name);		
	}

}
