/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.auth;

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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * LabViewerAuthorizationHelper will check if a user is authorized for lab viewer.
 * <P>
 *
 * @author Lisa Kelley
 */
public class LabViewerAuthorizationHelper {
	private static final Logger LOG = Logger.getLogger(LabViewerAuthorizationHelper.class);
	private AuthorizationManager authorizationManager;
	private SuiteRoleMembershipLoader authorizationHelper;

	private synchronized AuthorizationManager getAuthorizationManager()	{
		if (authorizationManager == null) {
            authorizationManager = (AuthorizationManagerImpl) ObjectFactory.getObject("csmUserProvisioningManager");
		}
		return authorizationManager;
	}

	private synchronized SuiteRoleMembershipLoader getAuthorizationHelper()	{
        if (authorizationHelper == null) {
            authorizationHelper = new SuiteRoleMembershipLoader();
            authorizationHelper.setAuthorizationManager(getAuthorizationManager());
        }
        return authorizationHelper;
    }

	private long getUserId(String username)  {
		long userId = 0;
		User user = getAuthorizationManager().getUser(username);
		if (user == null) {
			throw new SuiteAuthorizationAccessException("Username %s is not a CSM user", username);
		} else {
			userId = user.getUserId().longValue();
		}
		return userId;
	}

	/**
	 *
	 * @param username user name
	 * @return map of roles
	 */
	public Map<SuiteRole, SuiteRoleMembership> getUserRoleMemberships(String username) {
		long userId = getUserId(username);
		Map<SuiteRole, SuiteRoleMembership> userRoleMemberships =
		    getAuthorizationHelper().getRoleMemberships(userId);
	    if (userRoleMemberships.isEmpty()) {
	        throw new SuiteAuthorizationAccessException("Username %s has no associated CSM roles", username);
        }
	    return userRoleMemberships;
	}

	/**
	 *
	 * @param username user name
	 * @return map of roles
	 */
	public Set<SuiteRole> getUserRoles(String username)	{
		return getUserRoleMemberships(username).keySet();
	}

	/**
	 *
	 * @param username username
	 * @param role role
	 * @return  SuiteRoleMembership
	 */
	public SuiteRoleMembership getUserRoleMembership(String username, SuiteRole role)
	{
	    Map<SuiteRole, SuiteRoleMembership> userRoleMemberships = getUserRoleMemberships(username);
	    SuiteRoleMembership userRoleMembership = userRoleMemberships.get(role);
	    if (userRoleMembership == null) {
	        throw new SuiteAuthorizationAccessException(
	           "Username %s has no CSM protection groups associated with role %s", username, role.getDisplayName());
	        }
		return userRoleMembership;
	}
	/**
	 * return true, if the user has access to to all Studies, else return false.
	 * @param username username
	 * @return boolean
	 */
	public boolean isAllStudies(String username) {
	    return getUserRoleMembership(username , SuiteRole.LAB_DATA_USER).isAllStudies();
	}
    /**
     * return true, if the user has access to to all Sites, else return false.
     * @param userName username
     * @return boolean
     */
    public boolean isAllSites(String userName) {
        return getUserRoleMembership(userName , SuiteRole.LAB_DATA_USER).isAllSites();
    }

    /**
     * this method return the authorized studies for a given user.
     * @param userName userName
     * @return list of studies
     */
    public List<String> getAuthStudies(String userName) {
        return getUserRoleMembership(userName , SuiteRole.LAB_DATA_USER).getStudyIdentifiers();
    }
    /**
     * this method return the authorized stites for a given user.
     * @param userName userName
     * @return list of sites
     */
    public List<String> getAuthSites(String userName) {
        return getUserRoleMembership(userName , SuiteRole.LAB_DATA_USER).getSiteIdentifiers();
    }

	// eventually remove ////////
	/**
	 * @param username user name
	 * @return boolean
	 */
	public boolean isAuthorized(String username) {
		boolean authorized = false;
		try {
			AuthorizationManager authManager =
			    SecurityServiceProvider.getAuthorizationManager(CsmHelper.SUITE_APPLICATION_NAME);
			if (authManager != null) {
				User user = authorizationManager.getUser(username);
				if (user == null) {
					LOG.error("Username: " + username + " is not a CSM user");
				} else {
					authorized = true;
				}
			}
		} catch (CSConfigurationException e) {
			LOG.error("Error during authorization check", e);
		} catch (CSException e)	{
			LOG.error("Error during authorization check", e);
		}

		return authorized;
	}
}
