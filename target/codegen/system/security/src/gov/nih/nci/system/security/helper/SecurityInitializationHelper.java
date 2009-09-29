package gov.nih.nci.system.security.helper;

import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.authorization.attributeLevel.UserClassAttributeMapCache;
import gov.nih.nci.security.authorization.instancelevel.InstanceLevelSecurityHelper;
import gov.nih.nci.system.security.acegi.providers.GroupNameAuthenticationToken;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SecurityInitializationHelper 
{
	static final Logger log = Logger.getLogger(SecurityInitializationHelper.class.getName());
	private AuthorizationManager authorizationManager ;
	private boolean instanceLevelSecurityEnabled;
	private boolean attributeLevelSecurityEnabled;
	private boolean securityEnabled;

	public void addFilters(Configuration cfg) {
		if(securityEnabled && instanceLevelSecurityEnabled && authorizationManager!=null)
		{
				InstanceLevelSecurityHelper.addFiltersForGroups(authorizationManager, cfg);
				InstanceLevelSecurityHelper.addFilters(authorizationManager, cfg);
		}
	}

	public void initializeFilters(Session session) {
		if(securityEnabled && instanceLevelSecurityEnabled && authorizationManager!=null)
		{
			if(SecurityContextHolder.getContext()!=null)
			{
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if(auth !=null && auth instanceof GroupNameAuthenticationToken)
				{
					GroupNameAuthenticationToken groupAuth = (GroupNameAuthenticationToken)auth;
					
					String[] groups = new String[groupAuth.getGroups().size()];
					int i = 0;
					for (String group: groupAuth.getGroups()){
						log.debug("groupAuth group: " + group);
						groups[i++] = group;
					}
					
					log.debug("groups.length(): " + groups.length);
					

					InstanceLevelSecurityHelper.initializeFiltersForGroups(groups,session,authorizationManager);					
				}
				else if (auth!=null)
				{
					InstanceLevelSecurityHelper.initializeFilters(auth.getName(), session, authorizationManager);					
				}
			}
		}
	}
	
	public void enableAttributeLevelSecurity(SessionFactory sessionFactory) 
	{
		if(securityEnabled && attributeLevelSecurityEnabled && authorizationManager != null)
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth !=null && auth instanceof GroupNameAuthenticationToken)
			{
				GroupNameAuthenticationToken groupAuth = (GroupNameAuthenticationToken)auth;		
				UserClassAttributeMapCache.setAttributeMapForGroup((String[])(groupAuth.getGroups().toArray()), sessionFactory, authorizationManager);	
			}
			else if (auth!=null)
			{
				UserClassAttributeMapCache.setAttributeMap(auth.getName(),sessionFactory, authorizationManager);
			}
		}
	}
	
	public AuthorizationManager getAuthorizationManager() {
		return authorizationManager;
	}

	public void setAuthorizationManager(AuthorizationManager authorizationManager) {
		this.authorizationManager = authorizationManager;
	}

	public boolean isInstanceLevelSecurityEnabled() {
		return instanceLevelSecurityEnabled;
	}

	public void setInstanceLevelSecurityEnabled(boolean instanceLevelSecurityEnabled) {
		this.instanceLevelSecurityEnabled = instanceLevelSecurityEnabled;
	}

	public boolean isAttributeLevelSecurityEnabled() {
		return attributeLevelSecurityEnabled;
	}

	public void setAttributeLevelSecurityEnabled(boolean attributeLevelSecurityEnabled) {
		this.attributeLevelSecurityEnabled = attributeLevelSecurityEnabled;
	}

	public boolean isSecurityEnabled() {
		return securityEnabled;
	}

	public void setSecurityEnabled(boolean securityEnabled) {
		this.securityEnabled = securityEnabled;
	}

}