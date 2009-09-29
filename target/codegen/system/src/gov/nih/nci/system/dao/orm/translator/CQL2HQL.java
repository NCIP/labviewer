package gov.nih.nci.system.dao.orm.translator;


import gov.nih.nci.system.dao.QueryException;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLGroup;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import gov.nih.nci.system.util.ClassCache;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** 
 *  CQL2HQL
 *  
 *  Translates a CQL query to Hibernate HQL
 * 
 * @author Satish Patel
 * 
 */
public class CQL2HQL {
	public  final String TARGET_ALIAS = "xxTargetAliasxx";
	public  final String SOURCE_ASSOC_ALIAS = "parent";
	public  final String TARGET_ASSOC_ALIAS = "child";
	private  Map predicateValues;

	private ClassCache classCache;
	
	public CQL2HQL(ClassCache classCache)
	{
		this.classCache = classCache;
	}

	/**
	 * Translates a CQL query into an HQL string.  This translation process assumes the
	 * CQL Query has passed validation.  Processing of invalid CQL may or may not procede
	 * with undefined results.
	 * 
	 * @param query
	 * 		The CQL Query to translate into HQL
	 * @param eliminateSubclasses
	 * 		A flag indicating that the query should be formulated to avoid
	 * 		returning subclass instances of the targeted class.
	 * @return
	 * @throws QueryException
	 */
	public  HQLCriteria translate(CQLQuery query, boolean eliminateSubclasses, boolean caseSensitive) throws QueryException {
		StringBuffer hql = new StringBuffer();
		List parameters = new ArrayList();
		processTarget(hql,parameters, query.getTarget(), eliminateSubclasses,caseSensitive);
		return new HQLCriteria(hql.toString(),parameters);
	}
	
	private  void processTarget(StringBuffer hql, List params, CQLObject target, boolean eliminateSubclasses, boolean caseSensitive) 
		throws QueryException {
		String objName = target.getName();
		//hql.append("select distinct ").append(TARGET_ALIAS).append(" ");
		hql.append(" From ").append(objName);
		hql.append(" as ").append(TARGET_ALIAS);
		
		if(eliminateSubclasses==false && target.getAttribute() == null && target.getAssociation() == null && target.getGroup() == null)
			return;
		
		boolean andFlag = false;
		hql.append(" where ");
		if (eliminateSubclasses) {
			hql.append(TARGET_ALIAS).append(".class = ").append(objName);
			andFlag = true;
		}

		if (target.getAttribute() != null) {
			if (andFlag) 
				hql.append(" and ");
			processAttribute(hql, params,target.getName(), target.getAttribute(), true,caseSensitive);
			andFlag = true;
		}
		if (target.getAssociation() != null) {
			if (andFlag) 
				hql.append(" and ");
			processAssociation(hql, params, objName, target.getAssociation(), true,caseSensitive);
			andFlag = true;
		}
		if (target.getGroup() != null) {
			if (andFlag) 
				hql.append(" and ");
			hql.append(" (");
			processGroup(hql, params, objName, target.getGroup(), true,caseSensitive);
			hql.append(") ");
		}
	}
	
	
	/**
	 * Processes an Object of a CQL Query.
	 * 
	 * @param hql
	 * 		The existing HQL query fragment
	 * @param obj
	 * 		The object to process into HQL
	 * @throws QueryException
	 */
	private  void processObject(StringBuffer hql, List params, CQLObject obj, boolean caseSensitive) throws QueryException {
		String objName = obj.getName();
		boolean andFlag = false;
		hql.append("from ").append(objName);

		if( obj.getAttribute() == null && obj.getAssociation() == null && obj.getGroup() == null)
			return;
		
		hql.append(" where ");

		if (obj.getAttribute() != null) {
			processAttribute(hql,params, obj.getName() ,obj.getAttribute(), false,caseSensitive);
			andFlag = true;
		}
		if (obj.getAssociation() != null) {
			if (andFlag) 
				hql.append(" and ");
			processAssociation(hql, params, objName, obj.getAssociation(), false,caseSensitive);
			andFlag = true;
		}
		if (obj.getGroup() != null) {
			if (andFlag) 
				hql.append(" and ");
			hql.append(" (");
			processGroup(hql,params, objName, obj.getGroup(), false,caseSensitive);
			hql.append(") ");
		}
	}
	
	
	/**
	 * Proceses an Attribute of a CQL Query.
	 * 
	 * @param hql
	 * 		The existing HQL query fragment
	 * @param parentName 
	 * @param objAlias
	 * 		The alias of the object to which this attribute belongs
	 * @param attrib
	 * 		The attribute to process into HQL
	 * @throws QueryException
	 */
	private  void processAttribute(StringBuffer hql,  List params, String parentName, CQLAttribute attrib, boolean useAlias, boolean caseSensitive) throws QueryException {
		String dataType = getDataType(parentName,attrib.getName());
		CQLPredicate predicate = attrib.getPredicate();
		String attribName = useAlias?TARGET_ALIAS+"."+attrib.getName():attrib.getName();
	
		if (predicate.equals(CQLPredicate.IS_NULL)) 
			hql.append(attribName).append(" is null");
		else if (predicate.equals(CQLPredicate.IS_NOT_NULL)) 
			hql.append(attribName).append(" is not null");
		else
		{
			String predValue = convertPredicate(predicate);
			if ("java.lang.String".equals(dataType))
			{
				if(caseSensitive)
					hql.append(" ").append(attribName).append(" ");
				else
					hql.append(" lower(").append(attribName).append(")");
				
				hql.append(" ").append(predValue);
				if(caseSensitive)
					hql.append("?");
				else
					hql.append(" lower(?)");
				params.add(attrib.getValue());
			}
			else if ("java.util.Date".equals(dataType) || "java.lang.Boolean".equals(dataType)
						|| "java.lang.Long".equals(dataType) || "java.lang.Double".equals(dataType)
						|| "java.lang.Float".equals(dataType) || "java.lang.Integer".equals(dataType))
			{
					hql.append(attribName).append(" ").append(predValue).append("?");
				if ("java.util.Date".equals(dataType))
					params.add(new java.util.Date(attrib.getValue()));
				else if ("java.lang.Boolean".equals(dataType))
					params.add(new Boolean(attrib.getValue()));
				else if ("java.lang.Long".equals(dataType))
					params.add(new Long(attrib.getValue()));
				else if ("java.lang.Double".equals(dataType))
					params.add(new Double(attrib.getValue()));
				else if ("java.lang.Float".equals(dataType))
					params.add(new Float(attrib.getValue()));
				else if ("java.lang.Integer".equals(dataType))
					params.add(new Integer(attrib.getValue()));
			}
			else
				hql.append(attribName).append(" ").append(predValue).append(" '").append(attrib.getValue()).append("' ");
		}
	}
	
	
	/**
	 * Processes an Association of a CQL Query.
	 * 
	 * @param hql
	 * 		The existing HQL query fragment
	 * @param parentAlias
	 * 		The alias of the parent object
	 * @param parentName
	 * 		The class name of the parent object
	 * @param assoc
	 * 		The association to process into HQL
	 * @throws QueryException
	 */
	private  void processAssociation(StringBuffer hql,  List params, String parentName, CQLAssociation assoc, boolean useAlias, boolean caseSensitive) throws QueryException {
		// get the role name of the association
		String roleName = getRoleName(parentName, assoc);
		String sourceRoleName = assoc.getSourceRoleName();
		if (roleName == null && sourceRoleName ==null ) {
			// still null?? no association to the object!
			if(!existInheritance(parentName,assoc.getName()))
				throw new QueryException("Association from type " + parentName + 
						" to type " + assoc.getName() + " does not exist.  Use only direct associations");
		}
		if(roleName!=null)
		{
			if(classCache.isCollection(parentName,roleName))
			{
				if (useAlias) 
					hql.append(TARGET_ALIAS).append(".");
				hql.append("id in ( select ").append(TARGET_ASSOC_ALIAS).append(".id from ");
				hql.append(parentName).append(" as ").append(TARGET_ASSOC_ALIAS).append(",");
				hql.append(assoc.getName()).append(" as ").append(SOURCE_ASSOC_ALIAS);
				hql.append(" where ").append(SOURCE_ASSOC_ALIAS).append(" in elements(").append(TARGET_ASSOC_ALIAS).append(".").append(roleName).append(")");
				hql.append(" and ").append(SOURCE_ASSOC_ALIAS).append(".id in (select id ");
				processObject(hql,params, assoc,caseSensitive);
				hql.append("))");
			}
			else
			{
				if (useAlias) 
					hql.append(TARGET_ALIAS).append(".");
				hql.append(roleName).append(".id in ( select id ");
				processObject(hql,params, assoc,caseSensitive);
				hql.append(")");
			}
		}
		else if (sourceRoleName!=null)
		{
			if(classCache.isCollection(assoc.getName(),sourceRoleName))
			{
				if (useAlias) 
					hql.append(TARGET_ALIAS).append(".");
				hql.append("id in ( select ").append(TARGET_ASSOC_ALIAS).append(".id from ");
				hql.append(parentName).append(" as ").append(TARGET_ASSOC_ALIAS).append(",");
				hql.append(assoc.getName()).append(" as ").append(SOURCE_ASSOC_ALIAS);
				hql.append(" where ").append(TARGET_ASSOC_ALIAS).append(" in elements(").append(SOURCE_ASSOC_ALIAS).append(".").append(sourceRoleName).append(")");
				hql.append(" and ").append(SOURCE_ASSOC_ALIAS).append(".id in (select id ");
				processObject(hql,params, assoc,caseSensitive);
				hql.append("))");
			}
			else
			{
				if (useAlias) 
					hql.append(TARGET_ALIAS).append(".");
				hql.append("id in ( select ").append(sourceRoleName).append(".id ");
				processObject(hql,params, assoc,caseSensitive);
				hql.append(")");
			}
		}
		else
		{
			if (useAlias) 
				hql.append(TARGET_ALIAS).append(".");
			hql.append("id in ( select ").append("id ");
			processObject(hql,params, assoc,caseSensitive);
			hql.append(")");
		}
	}
	
	
	/**
	 * Processes a Group of a CQL Query.
	 * 
	 * @param hql
	 * 		The existing HQL query fragment
	 * @param parentName
	 * 		The type name of the parent object
	 * @param group
	 * 		The group to process into HQL
	 * @throws QueryException
	 */
	private  void processGroup(StringBuffer hql,  List params, String parentName, CQLGroup group, boolean useAlias, boolean caseSensitive) throws QueryException {
		String logic = group.getLogicOperator().getValue();
		
		// flag indicating a logic clause is needed before adding further query parts
		boolean logicClauseNeeded = false;
		
		// attributes
		if (group.getAttributeCollection()!= null) {
			Iterator iterator = group.getAttributeCollection().iterator();
			for (int i = 0; iterator.hasNext(); i++) {
				logicClauseNeeded = true;
				processAttribute(hql,params, parentName,(CQLAttribute)iterator.next(), useAlias,caseSensitive);
				if (iterator.hasNext()) {
					hql.append(" ").append(logic).append(" ");
				}
			}
		}
		
		// associations
		if (group.getAssociationCollection() != null) {
			if (logicClauseNeeded) {
				hql.append(" ").append(logic).append(" ");
			}
			Iterator iterator = group.getAssociationCollection().iterator();
			for (int i = 0; iterator.hasNext(); i++) {
				logicClauseNeeded = true;
				processAssociation(hql,params, parentName, (CQLAssociation) iterator.next(), useAlias,caseSensitive);
				if (iterator.hasNext()) {
					hql.append(" ").append(logic).append(" ");
				}
			}
		}
		
		// subgroups
		if (group.getGroupCollection() != null) {
			if (logicClauseNeeded) {
				hql.append(" ").append(logic).append(" ");
			}
			Iterator iterator = group.getGroupCollection().iterator();
			for (int i = 0; iterator.hasNext(); i++) {
				hql.append("( ");
				processGroup(hql,params, parentName, (CQLGroup) iterator.next(), useAlias,caseSensitive);
				hql.append(" )");
				if (iterator.hasNext()) {
					hql.append(" ").append(logic).append(" ");
				}
			}
		}
	}
	
	
	/**
	 * Gets the role name of an association relative to its parent class.
	 * 
	 * @param parentName
	 * 		The class name of the parent of the association
	 * @param assoc
	 * 		The associated object restriction
	 * @return
	 * 		The role name of the associated object
	 * @throws QueryException
	 */
	private  String getRoleName(String parentName, CQLAssociation assoc) throws QueryException {
		String roleName = assoc.getTargetRoleName();
		if (roleName == null) {
			// determine role based on object's type
			Class parentClass = null;
			try {
				parentClass = classCache.getClassFromCache(parentName);
			} catch (Exception ex) {
				throw new QueryException("Could not load class: " + ex.getMessage(), ex);
			}
			String associationTypeName = assoc.getName();
			
			// search the fields of the right type
			List<String> typedFields = classCache.getFieldsOfTypeFromCache(parentClass, associationTypeName);
			if (typedFields.size() == 1) {
				// found one and only one field
				roleName = (String)typedFields.get(0); //.getName();
				//log.debug("roleName: " + roleName);
			} else if (typedFields.size() > 1) {
				// more than one association found
				throw new QueryException("Association from " + parentClass.getName() + 
					" to " + associationTypeName + " is ambiguous: Specify a role name");
			}
			
			if (roleName == null) {
				// search for a setter method
				Method[] setters = classCache.getSettersForTypeFromCache(parentClass, associationTypeName);
				if (setters.length == 1) {
					String temp = setters[0].getName().substring(3);
					if (temp.length() == 1) {
						roleName = String.valueOf(Character.toLowerCase(temp.charAt(0)));
					} else {
						roleName = String.valueOf(Character.toLowerCase(temp.charAt(0))) 
							+ temp.substring(1);
					}
				} else if (setters.length > 1) {
					// more than one association found
					throw new QueryException("Association from " + parentClass.getName() + 
						" to " + associationTypeName + " is ambiguous: Specify a role name");
				}
			}
		}
		return roleName;
	}
	
	
	
	/**
	 * Converts a predicate to its HQL string equivalent.
	 * 
	 * @param p
	 * @return
	 */
	private  String convertPredicate(CQLPredicate p) {
		if (predicateValues == null) {
			predicateValues = new HashMap();
			predicateValues.put(CQLPredicate.EQUAL_TO, "=");
			predicateValues.put(CQLPredicate.GREATER_THAN, ">");
			predicateValues.put(CQLPredicate.GREATER_THAN_EQUAL_TO, ">=");
			predicateValues.put(CQLPredicate.LESS_THAN, "<");
			predicateValues.put(CQLPredicate.LESS_THAN_EQUAL_TO, "<=");
			predicateValues.put(CQLPredicate.LIKE, "LIKE");
			predicateValues.put(CQLPredicate.NOT_EQUAL_TO, "!=");
		}
		return (String) predicateValues.get(p);
	}

	private  boolean existInheritance(String parent, String child) throws QueryException
	{
		try {
			Class parentClass= classCache.getClassFromCache(parent);
			Class childClass= classCache.getClassFromCache(child);
			if (childClass.getSuperclass().getName().equals(parent)
					|| parentClass.getSuperclass().getName().equals(child))
				return true;
			return false;
		} catch (Exception ex) {
			throw new QueryException("Could not load class: " + ex.getMessage(), ex);
		}
	}
	
	
	/**
	 * Gets the data type of a particular field of the class
	 * @param className
	 * @param attribName
	 * @return
	 * @throws ClassNotFoundException 
	 */
	private  String getDataType(String className, String attribName) throws QueryException
	{
		Field[] classFields;
		try
		{
			classFields = classCache.getFields(classCache.getClassFromCache(className));
			for (int i=0; i<classFields.length;i++)
			{
				if(classFields[i].getName().equals(attribName))
					return classFields[i].getType().getName();
			}
			return "";
		} 
		catch (ClassNotFoundException e)
		{
			throw new QueryException("Could not determine type of attribute "+attribName+" in class "+className,e);
		}
	}
}