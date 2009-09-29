package gov.nih.nci.system.client.proxy;

import gov.nih.nci.system.applicationservice.ApplicationService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.aop.framework.Advised;

public class ProxyHelperImpl implements ProxyHelper 
{
	private static Logger log = Logger.getLogger(ProxyHelperImpl.class.getName());
	
	public Object convertToProxy(ApplicationService as, Object obj) 
	{
		if(obj == null) return null;
		
		if(obj instanceof ListProxy)
			return convertListProxyToProxy(as,(ListProxy)obj);
		if(obj instanceof java.util.Collection)
			return convertCollectionToProxy(as,(Collection)obj);
		else if(obj instanceof Object[])
			return convertArrayToProxy(as,(Object[])obj);
		else
		   	return convertObjectToProxy(as,obj);
	}
	
	@SuppressWarnings("unchecked")
	public Object convertToObject(Object proxyObject) throws Throwable {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		if (proxyObject instanceof Collection) {
			Collection<Object> unwrapedProxyObjects = new ArrayList<Object>();
			Collection<Object> batchQueries = (Collection) proxyObject;
			for (Object tempProxyObject : batchQueries) {
				Object unwrapedProxyObject = convertToObject(map,tempProxyObject);
				unwrapedProxyObjects.add(unwrapedProxyObject);
			}
			return unwrapedProxyObjects;
		} else {
			Object unwrapedProxyObject = convertToObject(map, proxyObject);
			return unwrapedProxyObject;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Object convertToObject(Map<Object,Object> map,Object proxyObject)throws Exception {
		if (isPrimitiveObject(proxyObject) || proxyObject instanceof Class || proxyObject instanceof DetachedCriteria) {
			return proxyObject;
		}

		Object plainObject = convertProxyToObject(proxyObject);
		Object mapPlainObject = map.get(plainObject);
		if (mapPlainObject != null) return mapPlainObject;

		map.put(plainObject, plainObject);
		Method[] methods = plainObject.getClass().getMethods(); 
		for (Method method : methods) {
			if (method.getName().startsWith("get") && method.getParameterTypes().length==0) {
				Object childObject = method.invoke(plainObject);
				if (!(childObject == null || isPrimitiveObject(childObject) || childObject instanceof Class)
						&& Hibernate.isInitialized(childObject)) {
					
					if (childObject instanceof ListProxy) {
						ListProxy objectProxy = (ListProxy) childObject;
						int associationSize = objectProxy.size();
						if (associationSize != objectProxy.getListChunk().size()) {
							String associationName=null;
							if (objectProxy.getListChunk().size() > 0) {
								String cglibClassName = objectProxy.getListChunk().get(0).getClass().getName();
								int startindex = cglibClassName.indexOf("$$EnhancerByCGLIB");
								associationName = cglibClassName.substring(0,startindex);
							}
							String className = objectProxy.getTargetClassName();
							throw new Exception("update or delete elements for the association "+associationName+" is not allowed.association "+associationName+" for Class "+className+" is not fully initialized. Total size of assocation in database "+associationSize+" retrieved size is "+objectProxy.getListChunk().size()+".");
						}
					}				
				    log.debug("invoking " + method.getName() + " on class "+ plainObject.getClass());
					String setterMethodName = "set"+ method.getName().substring(3);	
					if (childObject instanceof List && !(childObject instanceof Set)) {
						Object plainObjectCollection = convertProxyToObject(childObject);
						Collection<Object> objects = (Collection<Object>) plainObjectCollection;
						Collection<Object> tempObjects = new ArrayList<Object>();
						for (Object object : objects) {
							Object child = convertToObject(map, object);
							tempObjects.add(child);
						}
						Method setterMethod = plainObject.getClass().getMethod(setterMethodName,new Class[] { method.getReturnType() });
						setterMethod.invoke(plainObject, tempObjects);
					}else if (childObject instanceof Collection) {
						Object plainObjectCollection = convertProxyToObject(childObject);
						Collection<Object> objects = (Collection<Object>) plainObjectCollection;
						Collection<Object> tempObjects = new HashSet<Object>();
						for (Object object : objects) {
							Object child = convertToObject(map, object);
							tempObjects.add(child);
						}
						Method setterMethod = plainObject.getClass().getMethod(setterMethodName,new Class[] { method.getReturnType() });
						setterMethod.invoke(plainObject, tempObjects);
					} else if (childObject instanceof Object) {
						try{
						Method setterMethod = plainObject.getClass().getMethod(setterMethodName,new Class[] { method.getReturnType() });
						Object child = convertToObject(map, childObject);
						setterMethod.invoke(plainObject, child);
						}catch(Exception e)
						{}
					}
				}
			}
		}
		return plainObject;
	}
	
	protected Object convertListProxyToProxy(ApplicationService as, ListProxy proxy) {
		proxy.setAppService(as);
		List chunk = proxy.getListChunk();
		
		List modifiedChunk = new ArrayList((Collection)convertToProxy(as,chunk));
		proxy.setListChunk(modifiedChunk);
		
		return proxy;
	}

	protected Object convertObjectToProxy(ApplicationService as, Object obj) 
	{
		if(null == obj) return null;
    	if(obj instanceof Integer || obj instanceof Float || obj instanceof Double
    			|| obj instanceof Character || obj instanceof Long || obj instanceof Boolean
    			|| obj instanceof Byte ||  obj instanceof Short  
    			|| obj instanceof String || obj instanceof Date || obj instanceof BeanProxy)
    		return obj;

    	org.springframework.aop.framework.ProxyFactory pf = new org.springframework.aop.framework.ProxyFactory();
        pf.addAdvice(new BeanProxy(as, this));
        pf.setTarget(obj);
	    
		return pf.getProxy();		
	}

	protected Object convertCollectionToProxy(ApplicationService as, Collection collection) {
		if(null == collection) return null;
		Collection<Object> modifiedCollection =  new ArrayList<Object>();
		for(Object obj:collection)
			modifiedCollection.add(convertToProxy(as, obj));
		return modifiedCollection;
	}

	protected Object convertArrayToProxy(ApplicationService as, Object[] objects) {
		if(null == objects) return null;
		if(objects.length==0) return objects;
		Object[] modifiedCollection =  objects.clone();
		for(int i=0;i<objects.length;i++)
			modifiedCollection[i] = convertToProxy(as, objects[i]);
		return modifiedCollection;
	}
	
	public boolean isInitialized(MethodInvocation invocation) throws Throwable {
	    Object retVal = invocation.proceed();
		return Hibernate.isInitialized(retVal);
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
    		
    		Field field = getField(bean, fieldName); 
    		
    		if (field == null){ //Fix for [#8200] Query generator assumes lowercase association names
    			fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    			field = getField(bean, fieldName);
    		}
    		
    		Object obj = as.getAssociation(createClone(bean),fieldName);
    		Object value = obj;


    		if(obj instanceof ListProxy)
    			((ListProxy)obj).setAppService(as);
    			
    		if(!field.getType().getName().equalsIgnoreCase("java.util.Collection"))
    		{
    			Class<?> x = field.getType();
    			Collection results = (Collection)obj;
    			if(results.size() == 1)
    				value = results.iterator().next();
    			else if(results.size() == 0)
    				value = null;
    			else
    				throw new Exception("Invalid data obtained from the database for the "+fieldName+" attribute of the "+bean.getClass().getName());
    		}

    		Class[] params =  new Class[]{field.getType()};
	    	Method setter = getMethod(bean,"set"+method.getName().substring(3), params);
	    	if(setter != null && params!=null && params.length==1)
	    		setter.invoke(bean, new Object[]{value});

	    	return value;
	    }
    	
    	return null;
	}
	
	protected Method getMethod(Object bean, String methodName, Class[] params) {
		Method method = null;
		if(bean == null) return null;
		
		Class klass = bean.getClass();
		while(klass!=null && klass!= Object.class)
		{
			try {
				method = klass.getDeclaredMethod(methodName,params);
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
			if(method!=null) 
				break;;
			klass = klass.getSuperclass();
		}
		if(method==null) {
			log.debug("Method not found for methodName: " + methodName);
		}
		return method;
	}

	protected Field getField(Object bean, String fieldName) {
		Field field = null;
		if(bean == null) return null;
		
		Class klass = bean.getClass();
		while(klass!=null && klass!= Object.class)
		{
			try {
				field = klass.getDeclaredField(fieldName);
			} catch (SecurityException e) {
			} catch (NoSuchFieldException e) {
			}
			if(field!=null) 
				break;;
			klass = klass.getSuperclass();
		}
		if(field==null) {
			log.debug("Field not found for fieldName: " + fieldName);
		}
		return field;
	}

	protected Object createClone(Object source)
	{
		try 
		{
			Object target = source.getClass().newInstance();
			List<Field> fieldList = new ArrayList<Field>();
			getAllFields(source.getClass(), fieldList);
			for(Field field:fieldList)
			{
				Object obj = field.get(source);
			    if(obj instanceof Integer || obj instanceof Float || obj instanceof Double
			    		|| obj instanceof Character || obj instanceof Long || obj instanceof Boolean
			    		|| obj instanceof String )
			    {
			    	if(!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()))
				    {
				    	field.setAccessible(true);
				    	field.set(target, obj);
			    	}
			    }
			}
			return target;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void getAllFields(Class klass, List<Field> fieldList){

		if ( klass == null || 
				klass.getName().equalsIgnoreCase("java.lang.Object") ||
				klass.isInterface() ||
				klass.isPrimitive()) {
			return; // end of processing
		} 

		getAllFields(klass.getSuperclass(), fieldList);

		Field[] fields = klass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			String fieldName = fields[i].getName();
			if(fieldName.indexOf('$')==-1)
				fieldList.add(fields[i]);
		}	
	}
	
	private Object convertProxyToObject(Object obj) {
		if (obj == null)
			return null;
		while (obj!=null && obj instanceof Advised) {
			Advised proxy = (org.springframework.aop.framework.Advised) obj;
			try {
				obj = proxy.getTargetSource().getTarget();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	private boolean isPrimitiveObject(Object obj) {
		if (obj == null || obj instanceof Integer || obj instanceof Float
				|| obj instanceof Double || obj instanceof Character
				|| obj instanceof Long || obj instanceof Boolean
				|| obj instanceof Byte || obj instanceof Short
				|| obj instanceof String || obj instanceof Date) {
			return true;
		} else
			return false;
	}

	private Method[] getMethods(Class c)
	{
		List<Method> methods = new ArrayList<Method>();
		Class temp=c;
		while(!"java.lang.Object".equals(temp.getName()))
		{
			for(Method method: temp.getMethods())
				methods.add(method);
			temp = temp.getSuperclass();
		}
		return (Method[])methods.toArray();
	}
}