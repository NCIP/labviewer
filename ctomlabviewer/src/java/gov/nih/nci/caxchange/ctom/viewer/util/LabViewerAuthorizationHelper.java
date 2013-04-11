/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.util;

import gov.nih.nci.cabig.ctms.suite.authorization.CsmHelper;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteAuthorizationAccessException;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembershipLoader;
import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.provisioning.AuthorizationManagerImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * LabViewerAuthorizationHelper will check if a user is authorized for lab
 * viewer
 * <P>
 * 
 * @author Lisa Kelley
 */
public class LabViewerAuthorizationHelper
{
	private static final Logger log = Logger.getLogger(LabViewerAuthorizationHelper.class);
	private AuthorizationManager authorizationManager;
	private SuiteRoleMembershipLoader authorizationHelper;
	
	private synchronized AuthorizationManager getAuthorizationManager()
	{
		if (authorizationManager == null)
		{
		    authorizationManager = (AuthorizationManagerImpl)ObjectFactory.getObject("csmUserProvisioningManager");
		}
		
		return authorizationManager;
	}
	
	private synchronized SuiteRoleMembershipLoader getAuthorizationHelper()
	{
        if (authorizationHelper == null)
        {
            authorizationHelper = new SuiteRoleMembershipLoader();
            authorizationHelper.setAuthorizationManager(getAuthorizationManager());
        }
        
        return authorizationHelper;
    }
	
	private long getUserId(String username) throws SuiteAuthorizationAccessException
	{
		long userId = 0;

		User user = getAuthorizationManager().getUser(username);
		if (user == null)
		{
			throw new SuiteAuthorizationAccessException("Username %s is not a CSM user", username);
		}
		else
		{
			userId = user.getUserId().longValue();
			log.debug("UserId = " + userId);
		}

		return userId;
	}
	
	public Map<SuiteRole, SuiteRoleMembership> getUserRoleMemberships(String username) throws SuiteAuthorizationAccessException
	{
		long userId = getUserId(username);
		Map<SuiteRole, SuiteRoleMembership> userRoleMemberships = getAuthorizationHelper().getRoleMemberships(userId);
	    if (userRoleMemberships.isEmpty())
        {
	        throw new SuiteAuthorizationAccessException("Username %s has no associated CSM roles", username);
        }
	    
	    return userRoleMemberships;
	}

	public Set<SuiteRole> getUserRoles(String username) throws SuiteAuthorizationAccessException
	{	
		Map<SuiteRole, SuiteRoleMembership> userRoleMemberships = getUserRoleMemberships(username);
		
		return userRoleMemberships.keySet();
	}
	
	public SuiteRoleMembership getUserRoleMembership(String username, SuiteRole role) throws SuiteAuthorizationAccessException
	{
		Map<SuiteRole, SuiteRoleMembership> userRoleMemberships = getUserRoleMemberships(username);
	    
	    SuiteRoleMembership userRoleMembership = userRoleMemberships.get(role);
	    if (userRoleMembership == null)
        {
	        throw new SuiteAuthorizationAccessException("Username %s has no CSM protection groups associated with role %s", username, role.getDisplayName());
        }

		return userRoleMembership;
	}
	
	// eventually remove ///////////////////////////////////////////////////////////////////////////////////////////////
	public boolean isAuthorized(String username)
	{
		boolean authorized = false;

		try
		{
			AuthorizationManager authorizationManager = SecurityServiceProvider.getAuthorizationManager(CsmHelper.SUITE_APPLICATION_NAME);
			if (authorizationManager != null)
			{
				User user = authorizationManager.getUser(username);
				if (user == null)
				{
					log.error("Username: " + username + " is not a CSM user");
				}
				else
				{
					authorized = true;
				}
			}
		}
		catch (CSConfigurationException e)
		{
			log.error("Error during authorization check", e);
		}
		catch (CSException e)
		{
			log.error("Error during authorization check", e);
		}

		return authorized;
	}
	
}
