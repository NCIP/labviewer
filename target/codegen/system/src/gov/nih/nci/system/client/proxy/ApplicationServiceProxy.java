package gov.nih.nci.system.client.proxy;

import gov.nih.nci.system.applicationservice.ApplicationService;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class ApplicationServiceProxy implements MethodInterceptor
{
	private ProxyHelper proxyHelper;
	
	private ApplicationService as;
	
	private Authentication auth;
	
	public void setProxyFactory(ProxyHelper proxyHelper)
	{
		this.proxyHelper = proxyHelper;
	}
	
	public void setApplicationService (ApplicationService as)
	{
		this.as = as;
	}
	
	public void setAuthentication(Authentication auth) {
		this.auth=auth;
	}
	
	public Object invoke(MethodInvocation invocation) throws Throwable {		
		if (as == null)
			return invocation.proceed();
		
		//Save Authentication object
		Authentication tempAuth = SecurityContextHolder.getContext().getAuthentication();

		SecurityContextHolder.getContext().setAuthentication(auth);

		Object[] proxyobjects = invocation.getArguments();
		int i = 0;
		for (Object proxyObject : proxyobjects) {
			invocation.getArguments()[i] = proxyHelper.convertToObject(proxyObject);
			i++;
		}
		Object value = invocation.proceed();
		
		//Restore saved Authentication object
		SecurityContextHolder.getContext().setAuthentication(tempAuth);
		
		value = proxyHelper.convertToProxy(as, value);
		return value;
	}
}