package gov.nih.nci.cagrid.labviewer.service;

import gov.nih.nci.cabig.ctms.suite.authorization.ScopeType;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cagrid.labviewer.service.globus.LabLoaderAuthorization;
import gov.nih.nci.cagrid.labviewer.xml.HL7v3CtLabUnMarshaller;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;
import gov.nih.nci.ctom.ctlab.handler.ProtocolHandler;

import java.rmi.RemoteException;
import java.sql.Connection;
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
	private final static String HEALTHCARE_SITE = "HealthcareSite";
	private final static String STUDY = "Study";

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
		
		LabViewerAuthorizationHelper authHelper = new LabViewerAuthorizationHelper();
		List<String> protectionStudies = authHelper.getProtectionStudies(username, SuiteRole.LAB_DATA_USER);
		List<String> protectionSites = authHelper.getProtectionSites(username, SuiteRole.LAB_DATA_USER);
		if (protectionStudies.isEmpty() || protectionSites.isEmpty())
		{
			log.error("Error saving lab - user " + username + " not authorized for this operation");
			throw new RemoteException("User " + username + " not authorized for this operation");
		}
		
		// if the user has permission to access specific studies (not all studies), then verify the study in the lab message
		if (!protectionStudies.contains(ScopeType.STUDY.getAllScopeCsmName()))
		{
			HL7v3CtLabUnMarshaller unMarshaller = new HL7v3CtLabUnMarshaller();
			String studyId = unMarshaller.getStudyId(xml);
			if (studyId != null && !protectionStudies.contains(studyId))
		    {
		    	log.error("Error saving lab - user " + username + " does not have permission for this study");
				throw new RemoteException("User " + username + " does not have permission for this study");
		    }
	    }
		
		// if the user has permission to access specific sites (not all sites), then verify the sites in the lab message
		if (!protectionSites.contains(ScopeType.SITE.getAllScopeCsmName()))
		{
			HL7v3CtLabUnMarshaller unMarshaller = new HL7v3CtLabUnMarshaller();
			String siteNciInstituteCode = unMarshaller.getSiteNciInstituteCode(xml);
			if (siteNciInstituteCode != null && !protectionSites.contains(siteNciInstituteCode))
			{
		    	log.error("Error saving lab - user " + username + " does not have permission for this healthcare site");
				throw new RemoteException("User " + username + " does not have permission for this healthcare site");
			}
	    }
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
