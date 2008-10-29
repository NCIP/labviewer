package gov.nih.nci.cagrid.labviewer.service;

import gov.nih.nci.cagrid.labviewer.service.globus.LabLoaderAuthorization;
import gov.nih.nci.cagrid.labviewer.xml.HL7v3CtLabUnMarshaller;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;
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
	
  public void loadLab(java.lang.String string) throws RemoteException {
	  logger.info("LabLoader loadLab method called.");
	  
	  // Authorization code : note No Specific exception for LabLoader
		String username = LabLoaderAuthorization.getCallerIdentity();
		String user = "";
		if (username == null)
		{
			logger.error("No user credentials provided");
			RemoteException re = new RemoteException("No user credentials provided");
			throw re;
		}
		else
		{
			logger.info("User who called was " + username);
			
			LabViewerAuthorizationHelper lvaHelper = new LabViewerAuthorizationHelper();
			if(username!=null){
				int beginIndex = username.lastIndexOf("=");
				int endIndex = username.length();
				user = username.substring(beginIndex+1, endIndex);
				}
			boolean authorized = lvaHelper.isAuthorized(user);
			
			if (!authorized)
			{
				logger.error("User not authorized for this operation");
				
			}else
			{
				  // Now unmarshall the HL7v3 message
				  HL7v3CtLabUnMarshaller unMarshaller = new HL7v3CtLabUnMarshaller();
				  Object obj = null;
				  Connection con = null;				  
				  try
				  {
					  obj = unMarshaller.parseXmlToObject(string);
				
					  // Now save the lab
					  CTLabDAO dao = new CTLabDAO();
				 	  con = dao.getConnection();
					  con.setAutoCommit(false);
					  
					  if (obj != null)
					  {
						  dao.saveProtocol(con, (gov.nih.nci.ctom.ctlab.domain.Protocol)obj);
					  }
					  con.commit();
					  logger.debug("Message succussfully saved to the CTODS Database");
				  }//end of try
				  catch (Exception ex)
				  {
					  logger.debug(ex.getMessage());
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
				  }//end of finally
   }//end of else
   }//end of else
 
}

  public void rollback(java.lang.String string) throws RemoteException {
	  logger.info("LabLoader rollback method called.");
  }

}

