package gov.nih.nci.caxchange.ctom.viewer.util;

import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElementPrivilegeContext;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;

import java.util.Set;

import org.apache.log4j.Logger;

/**
 * LabViewerAuthorizationHelper will check if a user is authorized for lab viewer
 * <P>
 * @author mholck
 */
public class LabViewerAuthorizationHelper
{
	private static final Logger logger = Logger.getLogger(LabViewerAuthorizationHelper.class);
	private final String APPLICATION_CONTEXT = "labviewer";
	
	public boolean isAuthorized(String username)
	{
		boolean authorized = false;
		
		AuthorizationManager authorizationManager = null;
		UserProvisioningManager userProvisioningManager = null;
		Set<ProtectionElementPrivilegeContext> protectionElementPrivilegeContextSet = null;
		
		try
		{
			authorizationManager = SecurityServiceProvider.getAuthorizationManager(APPLICATION_CONTEXT);
			userProvisioningManager = SecurityServiceProvider.getUserProvisioningManager(APPLICATION_CONTEXT);
			User user = null;
			
			if (authorizationManager != null)
				user = authorizationManager.getUser(username);
			
			if (user != null && userProvisioningManager != null)
			{
				protectionElementPrivilegeContextSet =
					userProvisioningManager.getProtectionElementPrivilegeContextForUser(user.getUserId().toString());
			}
		}
		catch (CSConfigurationException e)
		{
			logger.error("Error during authorization check", e);
			
		}
		catch (CSObjectNotFoundException e)
		{
			logger.error("Error during authorization check", e);
		}
		catch (CSException e)
		{
			logger.error("Error during authorization check", e);
		}
		
		if(protectionElementPrivilegeContextSet != null)
		{
			for(ProtectionElementPrivilegeContext pepc: protectionElementPrivilegeContextSet)
			{
				ProtectionElement pe = pepc.getProtectionElement();
				logger.debug("Protection Element: " + pe.getProtectionElementName());
				if (pe.getProtectionElementName().equalsIgnoreCase(APPLICATION_CONTEXT))
					authorized = true;
			}
		}
		
		return authorized;
	}
}
