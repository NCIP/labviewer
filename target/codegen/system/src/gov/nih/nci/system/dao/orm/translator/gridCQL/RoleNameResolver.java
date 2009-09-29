package gov.nih.nci.system.dao.orm.translator.gridCQL;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.cagrid.cqlquery.Association;
import gov.nih.nci.cagrid.data.QueryProcessingException;
import gov.nih.nci.system.util.ClassCache;


public class RoleNameResolver extends gov.nih.nci.cagrid.sdkquery4.processor.RoleNameResolver
{

	ClassCache cache;
	
	public RoleNameResolver(ClassCache cache)
	{
		super(null);
		this.cache = cache;
	}
	
	public String getRoleName(String parentName, Association assoc) throws QueryProcessingException 
	{
		try
		{
			if(assoc.getRoleName()!=null) return assoc.getRoleName();
			Field[] fields = cache.getAllFields(cache.getClassFromCache(parentName));
			List<String> roleNames = new ArrayList<String>();
			for(Field field:fields)
			{
				if(field.getType().isPrimitive()) continue;
				String temp = null;
				if(null != (temp = cache.getReturnType(parentName, field.getName())))
				{
					if(temp.startsWith("class "))
						temp = temp.substring("class ".length());
					if(temp.startsWith("java.lang.")) continue;
					if(temp.equals(assoc.getName()))
						roleNames.add(field.getName());
				}
			}
			if(roleNames.size() == 0)
				throw new QueryProcessingException("Association from " + parentName 
                        + " to " + assoc.getName() + " was not found in the domain model");
			else if (roleNames.size()>1)
				throw new QueryProcessingException("Association from " + parentName 
                + " to " + assoc.getName() + " is ambiguous without role name specified (" 
                + roleNames.size() + " associations found)");
				
			return roleNames.get(0);
		} 
		catch (Exception e)
		{
			throw new QueryProcessingException(e);
		}
	}
}