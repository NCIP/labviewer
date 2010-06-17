package gov.nih.nci.cagrid.labviewer.service;

import gov.nih.nci.cabig.ctms.suite.authorization.SuiteAuthorizationAccessException;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cagrid.labviewer.service.globus.LabLoaderAuthorization;
import gov.nih.nci.cagrid.labviewer.xml.HL7v3CtLabUnMarshaller;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;
import gov.nih.nci.ctom.ctlab.handler.ProtocolHandler;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * LabLoaderImpl is the implementation of the Lab Loader grid service to accept
 * HL7v3 messages and persist them to the CTODS database.
 * 
 * @author Michael Holck
 */
public class LabLoaderImpl extends LabLoaderImplBase
{
	Logger log = Logger.getLogger(getClass());
	private LabViewerAuthorizationHelper authorizationHelper;

	public LabLoaderImpl() throws RemoteException
	{
		super();
	}

	/**
	 * loadLab method unmarshalls Lab message and calls into the 
	 * DAO to the persist the Lab message data
	 * @param string
	 * @throws RemoteException
	 */
	public void loadLab(java.lang.String string) throws RemoteException
	{
		log.info("LabLoader loadLab method called.");
		checkAuthorization(LabLoaderAuthorization.getCallerIdentity(), string);

       // Now unmarshall the HL7v3 message
		HL7v3CtLabUnMarshaller unMarshaller = new HL7v3CtLabUnMarshaller();
		Object obj = null;
		Connection con = null;
		try
		{
			obj = unMarshaller.parseXmlToObject(string);

			// Now save the lab
			ProtocolHandler dao = new ProtocolHandler();
			// obtain the connection
			con = dao.getConnection();
			con.setAutoCommit(false);

			if (obj != null)
			{
				// Call into the DAO save Protocol method.
				dao.persist(con,
						(gov.nih.nci.ctom.ctlab.domain.Protocol) obj);
			}
			// call connection commit
			con.commit();
			log
					.debug("Message succussfully saved to the CTODS Database");
		}// end of try
		catch (Exception ex)
		{
			log.debug(ex.getMessage());
			try
			{
				// issue rollback in case of exception
				con.rollback();
			}
			catch (Exception e)
			{
				// throw the remote exception
				RemoteException re1 = new RemoteException(e.getMessage());
				throw re1;
			}
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception ex)
			{
				log.error("Error closing connection",ex);
			}
		}
	}
	
	/**
	 * @param callerId
	 * @throws RemoteException
	 */
	private void checkAuthorization(String callerId, String xml) throws RemoteException
	{
		if (callerId == null)
		{
			log.error("Error saving lab - no user credentials provided");
			throw new RemoteException("No user credentials provided");
		}

		log.debug("Service called by: " + callerId);
		
		int beginIndex = callerId.lastIndexOf("=") + 1;
		int endIndex = callerId.length();
		String username = callerId.substring(beginIndex, endIndex);
		
		HL7v3CtLabUnMarshaller unMarshaller = new HL7v3CtLabUnMarshaller();
		String studyId = unMarshaller.getStudyId(xml);
		
		List<String> siteNciInstituteCodes = new ArrayList<String>();
		siteNciInstituteCodes.add(unMarshaller.getSiteNciInstituteCode(xml));
		
		try
		{
		    getAuthorizationHelper().checkAuthorization(username, SuiteRole.LAB_DATA_USER, studyId, siteNciInstituteCodes);
		}
		catch (SuiteAuthorizationAccessException e)
		{
			log.error("Error saving lab: ", e);
			throw new RemoteException(e.getMessage());
		}
	}
	
	private synchronized LabViewerAuthorizationHelper getAuthorizationHelper()
	{
        if (authorizationHelper == null)
        {
            authorizationHelper = new LabViewerAuthorizationHelper();
        }
        
        return authorizationHelper;
    }

	/**
	 * @param string
	 * @throws RemoteException
	 */
	public void rollback(java.lang.String string) throws RemoteException
	{
		log.info("LabLoader rollback method called: Not Implemented");
	}

}
