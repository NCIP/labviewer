package gov.nih.nci.system.dao.orm.translator.gridCQL;

import gov.nih.nci.cagrid.sdkquery4.processor.RoleNameResolver;
import gov.nih.nci.system.util.ClassCache;

import java.io.IOException;
import java.lang.reflect.Field;

public class CQL2ParameterizedHQL extends gov.nih.nci.cagrid.sdkquery4.processor.CQL2ParameterizedHQL
{

	ClassCache cache;
	
	public CQL2ParameterizedHQL(ClassCache cache, RoleNameResolver roleNameResolver, boolean caseInsensitive) throws IOException, ClassNotFoundException
	{
		super(null, roleNameResolver, caseInsensitive);
		this.cache = cache;
		replaceFields();
	}

	private void replaceFields()
	{
		try
		{
			Class klass = gov.nih.nci.cagrid.sdkquery4.processor.CQL2ParameterizedHQL.class;
			//Field[] fields = klass.getDeclaredFields();
			//for(Field field: fields)
			//	System.out.println("field-->"+field.getName());
			Field field = klass. getDeclaredField("typesInfoUtil");
			field.setAccessible(true);
			field.set(this, new DomainTypesInformationUtil(cache));
		}
		catch (Exception e)
		{
			//Do nothing
			e.printStackTrace();
		}
		
	}
}