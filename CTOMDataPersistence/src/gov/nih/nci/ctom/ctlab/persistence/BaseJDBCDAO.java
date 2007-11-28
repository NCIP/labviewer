/*
 * Created on Jul 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.ctom.ctlab.persistence;

import java.sql.Connection;

import org.hibernate.Session;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * BaseJDBCDAO is just used to get the connection to the database. It uses spring to
 * get the connection from the hibernate connection pool.
 * <P>
 * @author mholck
 */
public abstract class BaseJDBCDAO
{	
	public Connection getConnection()
	{
		XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("DAOConfig.xml"));
		LabViewerDAOSupport dao = (LabViewerDAOSupport)factory.getBean("DAO");
		Session session = dao.getSessionFactory().openSession();

		Connection result = session.connection();
	    
	    return result;
	}
	
}
