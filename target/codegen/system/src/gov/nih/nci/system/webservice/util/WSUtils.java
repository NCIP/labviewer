package gov.nih.nci.system.webservice.util;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.proxy.BeanProxy;
import gov.nih.nci.system.client.proxy.ProxyHelperImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import org.aopalliance.intercept.MethodInvocation;

public class WSUtils extends ProxyHelperImpl 
{
	
	protected Object convertObjectToProxy(ApplicationService as, Object obj) 
	{
		if(null == obj) return null;
    	if(obj instanceof Integer || obj instanceof Float || obj instanceof Double
    			|| obj instanceof Character || obj instanceof Long || obj instanceof Boolean
    			|| obj instanceof Byte ||  obj instanceof Short
    			|| obj instanceof String || obj instanceof Date || obj instanceof BeanProxy)
    		return obj;

    	setAssociationsToNull(obj);
/*    	org.springframework.aop.framework.ProxyFactory pf = new org.springframework.aop.framework.ProxyFactory();
        pf.addAdvice(new BeanProxy(as, this));
        pf.setTarget(obj);
	    
		return pf.getProxy();		
*/	
    	return obj;
    }
	
	private void setAssociationsToNull(Object obj) {
		Method methods[] = obj.getClass().getMethods();
		for(Method method:methods)
		{
	    	if(method.getName().startsWith("set") && (method.getParameterTypes().length == 1))
	    	{
	    		Class type = method.getParameterTypes()[0];
	        	if(!(type.getName().equals(Integer.class.getName()) 
	        			||type.getName().equals(Float.class.getName())
	        			||type.getName().equals(Double.class.getName())
	        			||type.getName().equals(Character.class.getName())
	        			||type.getName().equals(Long.class.getName())
	        			||type.getName().equals(Boolean.class.getName())
	        			||type.getName().equals(Byte.class.getName())
	        			||type.getName().equals(Short.class.getName())
	        			||type.getName().equals(String.class.getName())
	        			||type.getName().equals(Date.class.getName())))
	        	{
		    		try
		    		{
		    			method.invoke(obj, new Object[]{null});
		    		}
		    		catch(Exception e)
		    		{
		    			
		    		}
	        	}
		    }
		}
	}

	public Object lazyLoad(ApplicationService as, MethodInvocation invocation) throws Throwable{
	    Object bean = invocation.getThis();
	    Method method = invocation.getMethod();
	    String methodName = method.getName();
	    Object args[] = invocation.getArguments();
    	if(methodName.startsWith("get") && (args == null || args.length == 0))
    	{
        	String fieldName = methodName.substring(3);
    		fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
    		Object value = null;
    		
    		Field field = getField(bean, fieldName); 

    		Class[] params =  new Class[]{field.getType()};
	    	Method setter = getMethod(bean,"set"+method.getName().substring(3), params);
	    	if(setter != null && params!=null && params.length==1)
	    		setter.invoke(bean, new Object[]{value});

	    	return value;
	    }
    	return null;
	}
}