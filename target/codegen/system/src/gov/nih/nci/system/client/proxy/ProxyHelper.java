package gov.nih.nci.system.client.proxy;

import gov.nih.nci.system.applicationservice.ApplicationService;

import org.aopalliance.intercept.MethodInvocation;

public interface ProxyHelper {
	public Object convertToProxy(ApplicationService as, Object obj);

	public Object convertToObject(Object proxyObject) throws Throwable;

	public boolean isInitialized(MethodInvocation invocation) throws Throwable;

	public Object lazyLoad(ApplicationService as, MethodInvocation invocation)throws Throwable;

}