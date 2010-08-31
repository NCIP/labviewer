package gov.nih.nci.cagrid.labviewer.service;

import gov.nih.nci.cabig.ctms.suite.authorization.SuiteAuthorizationAccessException;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;
import gov.nih.nci.cagrid.labviewer.service.globus.LabLoaderAuthorization;
import gov.nih.nci.cagrid.labviewer.xml.HL7v3CtLabUnMarshaller;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;
import gov.nih.nci.ctom.ctlab.handler.ProtocolHandler;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Map;

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
			log.error("Error saving lab: no user credentials provided");
			throw new RemoteException("No user credentials provided");
		}

		log.debug("Service called by: " + callerId);		
		int beginIndex = callerId.lastIndexOf("=") + 1;
		int endIndex = callerId.length();
		String username = callerId.substring(beginIndex, endIndex);
		log.debug("Username = " + username);
		
		try
		{
		    Map<SuiteRole, SuiteRoleMembership> userRoleMemberships = getAuthorizationHelper().getUserRoleMemberships(username);
		    SuiteRole labLoaderRole = SuiteRole.LAB_DATA_USER;
		    if (userRoleMemberships.containsKey(labLoaderRole))
		    {
		    	log.debug("User role memberships contains role: " + labLoaderRole.toString());
		    	if (labLoaderRole.isScoped())
		    	{
		    		log.debug("Role is scoped: " + labLoaderRole.toString());
		    		SuiteRoleMembership userRoleMembership = userRoleMemberships.get(labLoaderRole);

		    		if (labLoaderRole.isStudyScoped())
		    		{
		    			log.debug("Role is study scoped: " + labLoaderRole.toString());
		    			HL7v3CtLabUnMarshaller unMarshaller = new HL7v3CtLabUnMarshaller();
		    			String studyId = unMarshaller.getStudyId(xml);
		    			if (studyId == null)
		    			{
		    				throw new SuiteAuthorizationAccessException("Role %s is study scoped - study identifier is null", labLoaderRole.getDisplayName());
		    			}
		    			log.debug("StudyId = " + studyId);
		    			
		    			// if the user has permission to access specific studies (not all studies), then verify the study
		    			if (!userRoleMembership.isAllStudies() && !userRoleMembership.getStudyIdentifiers().contains(studyId))
		    		    {
		    				throw new SuiteAuthorizationAccessException("Username %s is not authorized for study %s", username, studyId);
		    		    }
		    			log.debug("User is authorized for study");
		    		}

		    		if (labLoaderRole.isSiteScoped())
		    		{
		    			log.debug("Role is site scoped: " + labLoaderRole.toString());
		    			HL7v3CtLabUnMarshaller unMarshaller = new HL7v3CtLabUnMarshaller();
		    			String siteNciInstituteCode = unMarshaller.getSiteNciInstituteCode(xml);
		    			if (siteNciInstituteCode == null)
		    			{
		    				throw new SuiteAuthorizationAccessException("Role %s is site scoped - site NCI institute code is null", labLoaderRole.getDisplayName());
		    			}
		    			log.debug("Site NCI institute code = " + siteNciInstituteCode);
		    			
		    			// if the user has permission to access specific sites (not all sites), then verify the sites
		    			if (!userRoleMembership.isAllSites() && !userRoleMembership.getSiteIdentifiers().contains(siteNciInstituteCode))
		    		    {
		    				throw new SuiteAuthorizationAccessException("Username %s is not authorized for site %s", username, siteNciInstituteCode);
		    		    }
		    			log.debug("User is authorized for site");
		    		}
		    	}
		    }
		    else
		    {
		    	throw new SuiteAuthorizationAccessException("Username %s is not authorized for role %s", username, labLoaderRole.getDisplayName());
		    }
		}
		catch (SuiteAuthorizationAccessException e)
		{
			log.error("Error saving lab: ", e);
			throw new RemoteException(e.getMessage());
		}
		catch (Exception e)
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
