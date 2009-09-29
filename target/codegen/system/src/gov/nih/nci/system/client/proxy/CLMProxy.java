package gov.nih.nci.system.client.proxy;

import gov.nih.nci.logging.api.user.UserInfoHelper;

import org.acegisecurity.context.SecurityContextHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

public class CLMProxy implements MethodInterceptor
{

	private static Logger log = Logger.getLogger(CLMProxy.class.getName());
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		String userName = null;
		try {
			userName = SecurityContextHolder.getContext().getAuthentication().getName();
		} catch(NullPointerException e){
			log.error("Error:  Unable to retrieve userName from SecurityContext; setting userName to 'dummy'");
			userName = "dummy";
		}
		
		log.debug("userName has been set to: " + userName);

		UserInfoHelper.setUserName(userName);
		Object value = invocation.proceed();
		UserInfoHelper.setUserName(null);
		
		return value;
	}
}