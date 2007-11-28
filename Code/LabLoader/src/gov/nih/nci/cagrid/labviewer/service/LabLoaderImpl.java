package gov.nih.nci.cagrid.labviewer.service;

import gov.nih.nci.cagrid.labviewer.xml.HL7v3CtLabUnMarshaller;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;

import java.rmi.RemoteException;
import java.sql.Connection;

import org.apache.log4j.Logger;

/** 
 * LabLoaderImpl is the implementation of the Lab Loader grid service to accept
 * HL7v3 messages and persist them to the CTODS database.
 * 
 * @author Michael Holck
 */
public class LabLoaderImpl extends LabLoaderImplBase
{
	Logger logger = Logger.getLogger(getClass());
	
	public LabLoaderImpl() throws RemoteException
	{
		super(); 
	}
	
  public void loadLab(java.lang.String string) throws RemoteException
  {
	  logger.info("LabLoader loadLab method called.");
	  
	  // Now unmarshall the HL7v3 message
	  HL7v3CtLabUnMarshaller unMarshaller = new HL7v3CtLabUnMarshaller();
	  Object obj = null;
	  
	  try
	  {
		  obj = unMarshaller.parseXmlToObject(string);
	  }
	  catch (Exception e)
	  {
		  RemoteException re = new RemoteException(e.getMessage());
		  throw re;
	  }
	  
	  // Now save it
	  CTLabDAO dao = new CTLabDAO();
	  Connection con = null;
	  try
	  {
		  con = dao.getConnection();
		  con.setAutoCommit(false);
		  
		  if (obj != null)
		  {
			  dao.saveProtocol(con, (gov.nih.nci.ctom.ctlab.domain.Protocol)obj);
		  }
			
		  con.commit();
		  logger.debug("Message succussfully saved to the CTODS Database");
	  }
	  catch (Exception ex)
	  {
		  try
		  {
			  con.rollback();
		  }
		  catch (Exception e)
		  {
			  RemoteException re = new RemoteException(e.getMessage());
			  throw re;
		  }
	  }
	  finally
	  {
		  try
		  {
			  con.close();
		  }
		  catch(Exception ex){}
	  }
  }

  public void rollback(java.lang.String string) throws RemoteException
  {
	  System.out.println("LabLoader rollback method called.");
  }

}

