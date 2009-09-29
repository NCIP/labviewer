package gov.nih.nci.system.security.acegi.authorization;

import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.vote.AccessDecisionVoter;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.system.security.acegi.authentication.CSMUserDetailsService;
import gov.nih.nci.system.security.acegi.providers.GroupNameAuthenticationToken;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * CSM Implementation of AcessDecisionVoter.
 * 
 * <p>Votes if any {@link ConfigAttribute#getAttribute()} starts with a prefix indicating that it is a role. 
 * RolePrefix isnt used which means that essentially any attribute will be voted on. 
 * 
 */
public class CSMRoleVoter implements AccessDecisionVoter {
	static final Logger log = Logger.getLogger(CSMRoleVoter.class.getName());
	/**
     * NOTE: RolePrefix isnt used in CSMRoleVoter. 
     * It is desired to delegate this to SecurityHelper implemented by SDK. 
     * This avoids a security breach in case the User has access to CSM API's.
     * SDK SecurityHelper Impl can provide the RolePrefix in the Configuration file.
     */
    private String rolePrefix = "";
    
    private CSMUserDetailsService userDetailsService;
    private Class applicationServiceMethodHelperClass;

    
    public boolean supports(ConfigAttribute attribute) {
        if (attribute.getAttribute() != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This implementation supports any type of class, because it does not query the presented secure object.
     *
     * @param clazz the secure object
     *
     * @return always <code>true</code>
     */
    public boolean supports(Class clazz) {
        return true;
    }

    public int vote(Authentication authentication, Object object, ConfigAttributeDefinition config) {        
		Boolean isProtectionElementsCached = userDetailsService.getCacheProtectionElementsFlag();
    	final MethodInvocation methodInvocation = (MethodInvocation)object;
    	Map<String,Collection<String>> privilegeMap=getDomainObjectNameAndPrivileges(methodInvocation);

		Iterator<String> iterator = privilegeMap.keySet().iterator();
		int result = ACCESS_ABSTAIN;
		while (iterator.hasNext()) {			
			String protectionElementName = (String) iterator.next();
			Collection<String> privileges = privilegeMap.get(protectionElementName);
			
			for (String privilege : privileges) {
				if ("*".equals(protectionElementName)) {
					result = ACCESS_GRANTED;
				} else if (authentication instanceof GroupNameAuthenticationToken) {
					result = authorizeByGroupName(authentication,protectionElementName, privilege);
					if(result==ACCESS_DENIED) log.info("user dont have permisson to "+privilege+" on protectionElement "+protectionElementName);
				} else if (isProtectionElementsCached) {
					result = authorizeCachedProtectionElements(authentication,protectionElementName, privilege);
					if(result==ACCESS_DENIED) log.info("user dont have permisson to "+privilege+" on protectionElement "+protectionElementName);
				} else {
					result = authorizeNonCachedProtectionElements(authentication, protectionElementName, privilege);
					if(result==ACCESS_DENIED) log.info("user dont have permisson to "+privilege+" on protectionElement "+protectionElementName);
				}
				if(result==ACCESS_DENIED) return result;
			}			
		}
		return result;
	}

	private int authorizeNonCachedProtectionElements(Authentication authentication,String protectionElementName,String privilege) {
		int result = ACCESS_DENIED;
		try {
			final AuthorizationManager authorizationManager = userDetailsService.getAuthorizationManager();
			boolean accessAllowed = authorizationManager.checkPermission(authentication.getName(),protectionElementName, privilege);
			if(accessAllowed){
				result=ACCESS_GRANTED;
			}
			log.info("accessAllowed "+accessAllowed+" for user "+authentication.getName()+ "  protectedElementName  " + protectionElementName+ "  privilge " + privilege);
		} catch (CSException e) {
			throw new RuntimeException("CSException occured ",e);
		}
		return result;
	}

	private int authorizeCachedProtectionElements(Authentication authentication,String protectedElementName,String privilege) {
		int result = ACCESS_DENIED;
		GrantedAuthority[] grantedAuthorities = authentication.getAuthorities();
		String authorizeInputAttr=protectedElementName+"_"+privilege;		
		// Attempt to find a matching granted authority
		for (int i = 0; i < grantedAuthorities.length; i++) {
			if ((authorizeInputAttr.equals(grantedAuthorities[i].getAuthority()))) {
				result = ACCESS_GRANTED;
			}
		}
		return result;
	}

	private int authorizeByGroupName(Authentication authentication,String protectionElementName,String privilege) {
		int result=ACCESS_DENIED;
		GroupNameAuthenticationToken authenticationToken=(GroupNameAuthenticationToken)authentication;
		final AuthorizationManager authorizationManager = userDetailsService.getAuthorizationManager();
		Iterator<String> itr=authenticationToken.getGroups().iterator();
		  while (itr.hasNext()) {
			String groupName = (String) itr.next();
			try {
				boolean accessAllowed = authorizationManager.checkPermissionForGroup(groupName, protectionElementName, privilege);
				if(accessAllowed){
					result=ACCESS_GRANTED;
				}
				log.info("accessAllowed "+accessAllowed+" for user "+authentication.getName()+ "  protectedElementName  " + protectionElementName+ "  privilge " + privilege);
			} catch (CSException e) {
				throw new RuntimeException("CSException occured ",e);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Collection<String>> getDomainObjectNameAndPrivileges(MethodInvocation invocation) {
		Map<String, Collection<String>> requiredPrivilageMap = new HashMap<String, Collection<String>>();
		Method m = getApplicationServiceHelperMethod();
		try {
			if (m != null)
				requiredPrivilageMap = (Map) m.invoke(applicationServiceMethodHelperClass.newInstance(),invocation);
		} catch (Exception e) {
			throw new RuntimeException("error in getDomainObjectName reflection invocation ", e);
		}
		return requiredPrivilageMap;
	}

	@SuppressWarnings("unchecked")
	private Method getApplicationServiceHelperMethod() {
		Method applicationServiceHelperMethod=null;
		try {
			Class[] params = new Class[] { MethodInvocation.class };
			applicationServiceHelperMethod = applicationServiceMethodHelperClass.getMethod("getDomainObjectName", params);			
		} catch (Exception e) {
			new RuntimeException("error occured",e);
		}
		return applicationServiceHelperMethod;
	}


    public void setUserDetailsService(CSMUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public CSMUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
	
	public void setApplicationServiceMethodHelperClass(Class applicationServiceMethodHelperClass) {
		this.applicationServiceMethodHelperClass = applicationServiceMethodHelperClass;
	}
	
	public Class getApplicationServiceMethodHelperClass() {
		return applicationServiceMethodHelperClass;
	}	
}
