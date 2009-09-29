package gov.nih.nci.system.dao.orm.translator;
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import gov.nih.nci.system.query.nestedcriteria.NestedCriteria;
import gov.nih.nci.system.util.SystemConstant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

public class NestedCriteria2HQL
{

	private NestedCriteria criteria;
	private Configuration cfg;
	private boolean caseSensitive;	
	private HQLCriteria hqlCriteria;

	private static Logger log = Logger.getLogger(NestedCriteria2HQL.class);

	private List paramList = new ArrayList();

	public NestedCriteria2HQL(NestedCriteria crit, Configuration cfg,boolean caseSensitive)
	{
		this.criteria = crit;
		this.cfg = cfg;
		this.caseSensitive = caseSensitive;
	}
	
	public HQLCriteria translate() throws Exception
	{
		StringBuffer hql = new StringBuffer();

		processNestedCriteria( hql, criteria);
		
		hqlCriteria = prepareQuery(hql);
		log.debug("HQL Query :"+hqlCriteria.getHqlString());
		return hqlCriteria;
	}

	private void processNestedCriteria(StringBuffer hql, NestedCriteria criteria) throws Exception {
		if (condition1(criteria)) {
			log.debug("Processing Scenario1:  src=dest and internal nested criteria=null");
			solveScenario1(hql, criteria);
		} else if (condition2(criteria)) {
			log.debug("Processing Scenario2:  src!=dest and internal nested criteria=null");
			solveScenario2(hql, criteria);
		} else if (condition3(criteria)){
			log.debug("Processing Scenario3:  nested criteria!=null");
			solveScenario3(hql, criteria);			
		} else {
			//should never happen
			log.error("Unexpected NestedCriteria condition found for criteria: " + criteria);
			throw new Exception("Unexpected NestedCriteria condition found for criteria: " + criteria);
		}
	}
	
	private boolean condition1(NestedCriteria criteria){
		
		if (criteria.getSourceName().equals(criteria.getTargetObjectName()) &&
		criteria.getInternalNestedCriteria() == null)
			return true;
		return false;
	}
	
	private boolean condition2(NestedCriteria criteria){
		
		if (!criteria.getSourceName().equals(criteria.getTargetObjectName()) &&
		criteria.getInternalNestedCriteria() == null)
			return true;
		return false;
	}	
	
	private boolean condition3(NestedCriteria criteria){
		
		if (criteria.getInternalNestedCriteria() != null)
			return true;
		return false;
	}
	
	
	//Single Object with query by example
	private void solveScenario1(StringBuffer hql, NestedCriteria criteria) throws Exception {
		
		Collection sourceObjectList = criteria.getSourceObjectList();
		
		if (sourceObjectList == null){
			log.error("Scenario1: Source object list is unexpectedly null");
			throw new Exception("Source Object List is unexpectedly null");
		}
		
		String targetObjectName = criteria.getTargetObjectName();		
		String destAlias = getAlias(targetObjectName,1);

		if (sourceObjectList.size() == 1 ){
			log.debug("Scenario1: Processing single object in source object list");
			String select = getObjectCriterion(sourceObjectList.iterator().next(), cfg, false);
			hql.append(select);
			log.debug("Scenario1: Single object HQL sub-select: " + select);
		} else {
			log.debug("Scenario1: Processing multiple objects in source object list");
			
			hql.append("select " + destAlias + " from " + targetObjectName + " " + destAlias + " where ");
			for (Iterator i = sourceObjectList.iterator(); i.hasNext();)
			{
				Object obj = i.next();
				hql.append(destAlias + " in (" + getObjectCriterion(obj, cfg, false)+" )");
				if (i.hasNext())
					hql.append(" or ");
			}
		}
	}
	
	//Getting association for the query by example
	private void solveScenario2(StringBuffer hql, NestedCriteria criteria) throws Exception {
		
		Collection sourceObjectList = criteria.getSourceObjectList();
		
		if (sourceObjectList == null){
			log.error("Scenario2: Source object list is unexpectedly null");
			throw new Exception("Scenario2: Source Object List is unexpectedly null");
		}
		
		String targetObjectName = criteria.getTargetObjectName();
		String sourceObjectName = criteria.getSourceName();
		String srcAlias = getAlias(criteria.getSourceName(),1);
		String destAlias = getAlias(targetObjectName,1);
		
		log.debug("Scenario2: targetObjectName: " + targetObjectName);
		log.debug("Scenario2: sourceObjectName: " + sourceObjectName);		
		log.debug("Scenario2: srcAlias: " + srcAlias);
		log.debug("Scenario2: destAlias: " + destAlias);
	

		if (sourceObjectList.size() == 1 ){
			log.debug("Scenario2: Processing single object in source object list");
			
			StringBuffer selectBuffer = new StringBuffer();
			String roleName = criteria.getRoleName();
			if(roleName==null)
			{
				selectBuffer.append("select ").append(destAlias).append(" from ")
						.append(targetObjectName).append(" ").append(destAlias)
						.append(", ").append(sourceObjectName).append(" ")
						.append(srcAlias).append(" where ");
				
				log.debug("Scenario2: roleName: " + roleName);
				selectBuffer.append(destAlias).append("=").append(srcAlias);

				if(isObjectEmpty(sourceObjectList.iterator().next()))
				{
					selectBuffer.append(" and ").append(srcAlias).append(".id is not null ");
				}
				else if(isObjectAssociationEmpty(sourceObjectList.iterator().next()))
				{
					selectBuffer.append(" and ").append(getObjectCriterion(sourceObjectList.iterator().next(), cfg, true));
				}
				else
				{
					selectBuffer.append(" and ").append(srcAlias).append(" in (")
						.append(getObjectCriterion(sourceObjectList.iterator().next(), cfg, false)).append(")");
				}
			}
			else
			{
					if (criteria.isTargetCollection())
					{
						//No attributes or associations populated
						if(isObjectEmpty(sourceObjectList.iterator().next()))
						{
							if(criteria.getSourceRoleName() != null && !criteria.isSourceCollection())
							{
								selectBuffer.append("select ").append(destAlias).append(" from ")
								.append(targetObjectName).append(" ").append(destAlias).append(" where ");
								selectBuffer.append(destAlias).append(".").append(criteria.getSourceRoleName()).append(".id is not null ");
							}
							else
							{
								selectBuffer.append("select ").append(destAlias).append(" from ").append(sourceObjectName).append(" ").append(srcAlias)
								.append(" inner join ").append(srcAlias).append(".").append(roleName).append(" ").append(destAlias).append(" where ")
								.append(srcAlias).append(".id is not null ");
							}
						}
						//Only some of the attributes populated
						else if(isObjectAssociationEmpty(sourceObjectList.iterator().next()))
						{
							if(criteria.getSourceRoleName() != null && !criteria.isSourceCollection())
							{
								selectBuffer.append("select ").append(destAlias).append(" from ").append(targetObjectName).append(" ").append(destAlias)
								.append(", ").append(sourceObjectName).append(" ").append(srcAlias).append(" where ")
								.append(destAlias).append(".").append(criteria.getSourceRoleName()).append(".id").append("=").append(srcAlias).append(".id")
								.append(" and ")
								.append(getObjectCriterion(sourceObjectList.iterator().next(), cfg, true));
							}
							else
							{
								selectBuffer.append("select ").append(destAlias).append(" from ").append(sourceObjectName).append(" ").append(srcAlias)
								.append(" inner join ").append(srcAlias).append(".").append(roleName).append(" ").append(destAlias).append(" where ")
								.append(getObjectCriterion(sourceObjectList.iterator().next(), cfg, true));
							}
						}
						//Attributes and associtions populated
						else
						{
							selectBuffer.append("select ").append(destAlias).append(" from ").append(sourceObjectName).append(" ").append(srcAlias)
							.append(" inner join ").append(srcAlias).append(".").append(roleName).append(" ").append(destAlias).append(" where ")
							.append(srcAlias).append(" in (")
							.append(getObjectCriterion(sourceObjectList.iterator().next(), cfg, false)).append(")");
							
						}
					}
					else //Target is not collection
					{
						//No attributes or associations populated
						if(isObjectEmpty(sourceObjectList.iterator().next()))
						{
							selectBuffer.append("select ").append(destAlias).append(" from ").append(sourceObjectName).append(" ").append(srcAlias)
							.append(" inner join ").append(srcAlias).append(".").append(roleName).append(" ").append(destAlias).append(" where ")
							.append(srcAlias).append(".id is not null ");
						}
						//Only some of the attributes populated
						else if(isObjectAssociationEmpty(sourceObjectList.iterator().next()))
						{
							selectBuffer.append("select ").append(destAlias).append(" from ").append(targetObjectName).append(" ").append(destAlias)
							.append(", ").append(sourceObjectName).append(" ").append(srcAlias).append(" where ")
							.append(srcAlias).append(".").append(roleName).append(".id").append("=").append(destAlias).append(".id")
							.append(" and ")
							.append(getObjectCriterion(sourceObjectList.iterator().next(), cfg, true));
						}
						//Attributes and associtions populated
						else
						{
							selectBuffer.append("select ").append(destAlias).append(" from ")
							.append(targetObjectName).append(" ").append(destAlias)
							.append(", ").append(sourceObjectName).append(" ")
							.append(srcAlias).append(" where ")
							.append(destAlias).append(".id").append("=").append(srcAlias).append(".").append(roleName).append(".id")
							.append(" and ").append(srcAlias).append(" in (")
							.append(getObjectCriterion(sourceObjectList.iterator().next(), cfg,false)).append(")");
						}
					}
				}
			
			log.debug("Scenario2: single object HQL sub-select: " + selectBuffer.toString());	
			
			hql.append(selectBuffer.toString());

		} 
		else //More than one example objects present
		{
			log.debug("Scenario2: Processing multiple objects in source object list");
			String roleName = criteria.getRoleName();
			if(roleName==null)
			{
				hql.append("select ").append(destAlias).append(" from ")
				.append(targetObjectName).append(" ").append(destAlias)
				.append(", ").append(sourceObjectName).append(" ")
				.append(srcAlias).append(" where ");
				hql.append(destAlias).append(".id").append("=").append(srcAlias).append(".id");
				hql.append(" and ");
			}
			else
			{
					if (criteria.isTargetCollection())
					{
						if(criteria.getSourceRoleName() != null && !criteria.isSourceCollection())
						{
							hql.append("select ").append(destAlias).append(" from ").append(targetObjectName).append(" ").append(destAlias)
							.append(", ").append(sourceObjectName).append(" ").append(srcAlias).append(" where ");
							hql.append(destAlias).append(".").append(criteria.getSourceRoleName()).append(".id").append("=").append(srcAlias).append(".id");
							hql.append(" and ");
						}
						else
						{
							hql.append("select ").append(destAlias).append(" from ").append(sourceObjectName).append(" ").append(srcAlias)
							.append(" inner join ").append(srcAlias).append(".").append(roleName).append(" ").append(destAlias).append(" where ")
							.append("1=1 and ");
						}
					}
					else
					{
						hql.append("select ").append(destAlias).append(" from ").append(targetObjectName).append(" ").append(destAlias)
						.append(", ").append(sourceObjectName).append(" ").append(srcAlias).append(" where ");
						hql.append(destAlias).append(".id").append("=").append(srcAlias).append(".").append(roleName).append(".id");
						hql.append(" and ");
					}
				}
			
			for (Iterator i = sourceObjectList.iterator(); i.hasNext();)
			{
				Object obj = i.next();
				hql.append(srcAlias + " in (" + getObjectCriterion(obj, cfg,false)).append(")");
				if (i.hasNext())
					hql.append(" or ");
			}
		}
	}
	
	//Traversing the path of the nested search criteria
	private void solveScenario3(StringBuffer hql, NestedCriteria criteria) throws Exception {
		
		String targetObjectName = criteria.getTargetObjectName();
		String sourceObjectName = criteria.getSourceName();
		String srcAlias = getAlias(criteria.getSourceName(),2);
		String destAlias = getAlias(targetObjectName,1);
		
		log.debug("Scenario3: targetObjectName: " + targetObjectName);
		log.debug("Scenario3: sourceObjectName: " + sourceObjectName);		
		log.debug("Scenario3: srcAlias: " + srcAlias);
		log.debug("Scenario3: destAlias: " + destAlias);
		
		
		String roleName = criteria.getRoleName();
		log.debug("Scenario2: roleName: " + roleName);
				
		if (roleName == null){
			hql.append("select ").append(destAlias).append(" from ")
			.append(targetObjectName).append(" ").append(destAlias)
			.append(", ").append(sourceObjectName).append(" ")
			.append(srcAlias).append(" where ");
			hql.append(destAlias).append(".id").append("=").append(srcAlias).append(".id");
			StringBuffer internalNestedCriteriaBuffer = new StringBuffer();
			processNestedCriteria(internalNestedCriteriaBuffer, criteria.getInternalNestedCriteria());
			
			hql.append(" and ").append(srcAlias).append(" in (")
				.append(internalNestedCriteriaBuffer).append(")");
		} 
		else 
		{
			if (criteria.isTargetCollection())
			{
				//hql.append("select ").append(destAlias).append(" from ")
				//.append(sourceObjectName).append(" ").append(srcAlias)
				//.append(" inner join ").append(srcAlias).append(".").append(criteria.getRoleName()).append(" ").append(destAlias)
				//.append(" where ");
				if(criteria.getSourceRoleName()!=null && !criteria.isSourceCollection())
				{
					hql.append("select ").append(destAlias).append(" from ")
					.append(targetObjectName).append(" ").append(destAlias)
					.append(", ").append(sourceObjectName).append(" ")
					.append(srcAlias).append(" where ");
					hql.append(destAlias).append(".").append(criteria.getSourceRoleName()).append(".id").append("=").append(srcAlias).append(".id");
					hql.append(" and ");
				}
				else
				{
				hql.append("select ").append(destAlias).append(" from ")
				.append(targetObjectName).append(" ").append(destAlias)
				.append(", ").append(sourceObjectName).append(" ")
				.append(srcAlias).append(" where ");
				hql.append(destAlias).append(" in elements(").append(srcAlias).append(".").append(criteria.getRoleName()).append(")");
				hql.append(" and ");
				}
			}
			else
			{
				hql.append("select ").append(destAlias).append(" from ")
				.append(targetObjectName).append(" ").append(destAlias)
				.append(", ").append(sourceObjectName).append(" ")
				.append(srcAlias).append(" where ");
				hql.append(destAlias).append(".id").append("=").append(srcAlias).append(".").append(roleName).append(".id");
				hql.append(" and ");
			}
				
			StringBuffer internalNestedCriteriaBuffer = new StringBuffer();
			processNestedCriteria(internalNestedCriteriaBuffer, criteria.getInternalNestedCriteria());
			
			hql.append(srcAlias).append(" in (")
				.append(internalNestedCriteriaBuffer).append(")");
		}
			
		
		log.debug("Scenario3: HQL select: " + hql.toString());
	}

	public static String getCountQuery(String hql)
	{
		String upperHQL = hql.toUpperCase();
		String modifiedHQL = "";
		
		int firstSelectIndex = upperHQL.indexOf("SELECT");
		int firstFromIndex = upperHQL.indexOf("FROM");
		
        if((firstSelectIndex >= 0) && (firstSelectIndex<firstFromIndex))
        {
            String projections = hql.substring(firstSelectIndex+"SELECT".length(),firstFromIndex);
            String[] tokens = projections.split(",");
            modifiedHQL = hql.substring(0, firstSelectIndex+"SELECT".length())+" count("+tokens[0].trim()+") " + hql.substring( firstFromIndex);
        }
        else
        {
              modifiedHQL = hql.substring(0, firstFromIndex)+" select count(*) " + hql.substring(firstFromIndex);
        }
		
		return modifiedHQL;
	}
	
	private boolean distinctRequired()
	{
		boolean distinct = true;
		boolean containsCLOB =checkClobAttribute(criteria.getTargetObjectName());
		boolean condition1 = condition1(criteria);
		
		if(condition1 || containsCLOB)
			distinct = false;
		
		return distinct;
	}
	
	private boolean inRequired()
	{
		boolean condition2 = condition2(criteria);
		boolean condition3 = condition3(criteria);
		
		boolean condition1 = false;
		
		// Verify if it is condition1() and it contains one object in the list with no associations 
		if(condition1(criteria))
		{
			condition1 = true;
			
			List objects = criteria.getSourceObjectList();
			
			if(objects == null)
			{
				condition1 = false;
			}
			else if(objects.size() == 1)
			{
				try
				{
					HashMap map = getObjAssocCriterion(objects.get(0),cfg);
					if(map == null || map.size() == 0)
						condition1=false;
				}
				catch(Exception e)
				{
					//Ignore exception
				}
			}
		}
		if(condition2)
		{
			try {
				if(criteria.isTargetCollection() && !criteria.isSourceCollection() && criteria.getSourceRoleName()!=null && criteria.getSourceObjectList().size()==1 && isObjectAssociationEmpty(criteria.getSourceObjectList().iterator().next()))
					condition2=false;
			} catch (Exception e) {
				//do nothing. In will be added
			}
		}
		return condition1 || condition2 || condition3;
	}
	
	private HQLCriteria prepareQuery(StringBuffer hql)
	{
		//Check if the target contains any CLOB. If yes then do a subselect with distinct else do plain distinct
		String destalias = getAlias(criteria.getTargetObjectName(), 1);
		boolean containsCLOB = true;//checkClobAttribute(criteria.getTargetObjectName());
		String countQ="";
		String normalQ="";
		String originalQ = hql.toString();
		boolean distinctRequired = false; //distinctRequired();
		if(!distinctRequired)
		{
			if(inRequired())
			{
				String modifiedQ = originalQ.replaceFirst(destalias,destalias+".id" );
				String suffix = "from "+criteria.getTargetObjectName()+" as "+destalias+" where "+destalias+".id in ("+modifiedQ+")";
				normalQ="select "+destalias+" "+suffix;
				countQ="select count(*) "+suffix;
			}
			else
			{
				normalQ=originalQ;
				countQ=getCountQuery(originalQ);
			}
		}
		else
		{
			normalQ = originalQ.replaceFirst("select "+destalias, "select distinct ("+destalias+") ");
			countQ = originalQ.replaceFirst("select "+destalias, "select count(distinct "+destalias+".id) ");
		}
		
		log.debug("****** NormalQ: " + normalQ);
		log.debug("****** CountQ: " + countQ);
		
		HQLCriteria hCriteria = new HQLCriteria(normalQ, countQ, paramList);
		return hCriteria;
	}

	private boolean checkClobAttribute(String objClassName)
	{
		PersistentClass pclass = cfg.getClassMapping(objClassName);

		Iterator properties = pclass.getPropertyIterator();
		while (properties.hasNext())
		{
			Property prop = (Property) properties.next();
			if (!prop.getType().isAssociationType())
				if (prop.getType().getName().equals("text"))
					return true;
		}
		
		return false;
	}



	private String getObjectAttributeCriterion(String sourceAlias, Object obj, Configuration cfg) throws Exception 
	{
		StringBuffer whereClause = new StringBuffer();
		HashMap criterionMap = getObjAttrCriterion(obj, cfg);
		if (criterionMap != null)
		{
			Iterator keys = criterionMap.keySet().iterator();
			while (keys.hasNext())
			{
				String key = (String) keys.next();
				Object value = criterionMap.get(key);
				if (!key.equals("id") && (value instanceof String))
				{
					if (isCaseSensitive())
					{
						whereClause.append(sourceAlias + SystemConstant.DOT + key + getOperator(value) + "? ");
						paramList.add(((String) value).replaceAll("\\*", "\\%"));
					} else
					{
						whereClause.append("lower(" + sourceAlias + SystemConstant.DOT + key + ") " + getOperator(value) + "? ");
						paramList.add(((String) value).toLowerCase().replaceAll("\\*", "\\%"));
					}
				} else
				{
					whereClause.append(sourceAlias).append(SystemConstant.DOT).append(key).append(getOperator(value)).append("? ");
					paramList.add(value);
				}
				if (keys.hasNext())
					whereClause.append(" and ");
			}
		}
		return whereClause.toString();
	}

	private HashMap getObjAttrCriterion(Object obj, Configuration cfg) throws Exception
	{
		HashMap criterions = new HashMap();
		String objClassName = obj.getClass().getName();
		PersistentClass pclass = getPersistentClass(objClassName);
		
		if (pclass != null){
			setAttrCriterion(obj, pclass, criterions);

			while (pclass.getSuperclass() != null)
			{
				pclass = pclass.getSuperclass();
				if(pclass != null){
					setAttrCriterion(obj, pclass, criterions);
				}
			}

			String identifier = pclass.getIdentifierProperty().getName();
			Field idField = getDeclaredField(pclass.getMappedClass(), identifier);
			idField.setAccessible(true);
			try {
				if (idField.get(obj) != null)
					criterions.put(identifier, idField.get(obj));
			} catch (Exception e) {
				// Do nothing - when dealing with implicit queries, pclass would be the concrete subclass of obj, 
				// and the identifier field might be in a subclass of obj				
			}
		}

		return criterions;
	}

	private void setAttrCriterion(Object obj, PersistentClass pclass, HashMap criterions) throws Exception
	{
		Iterator properties = pclass.getPropertyIterator();
		while (properties.hasNext())
		{
			Property prop = (Property) properties.next();
			if (!prop.getType().isAssociationType())
			{
				String fieldName = prop.getName();
				Field field = getDeclaredField(pclass.getMappedClass(), fieldName);
				field.setAccessible(true);

				try {
					if (field.get(obj) != null)
					{
						if (prop.getType().getName().equals("text"))
							criterions.put(fieldName, field.get(obj) + "*");
						else
							criterions.put(fieldName, field.get(obj));
					}
				} catch (Exception e) {
					// Do nothing - when dealing with implicit queries, pclass would be the concrete subclass of obj, and thus contain fields that are not in obj
				}
			}
		}
	}

	private String getObjectCriterion(Object obj, Configuration cfg, boolean skipAssociations) throws Exception
	{
		String srcAlias = getAlias(obj.getClass().getName(),1);
		
		StringBuffer hql = new StringBuffer();
		HashMap associationCritMap = null;
		if(!skipAssociations)
		{
		associationCritMap = getObjAssocCriterion(obj, cfg);

		hql.append("select ");
		hql.append(srcAlias);
		hql.append(" from ").append(obj.getClass().getName()).append(" ").append(srcAlias);

		// get association value
		if (associationCritMap != null && associationCritMap.size() > 0)
		{
			Iterator associationKeys = associationCritMap.keySet().iterator();
			int counter = 0;
			while (associationKeys.hasNext())
			{
				String roleName = (String) associationKeys.next();
				Object roleValue = associationCritMap.get(roleName);

				if (roleValue instanceof Collection)
				{
					Object[] objs = ((Collection) roleValue).toArray();
					for (int i = 0; i < objs.length; i++)
					{
						String alias = getAlias(objs[i].getClass().getName(),counter++);
						hql.append(",").append(objs[i].getClass().getName()).append(" ").append(alias);
					}
				} else
				{
					String alias = getAlias(roleValue.getClass().getName(),counter++);
					hql.append(",").append(roleValue.getClass().getName()).append(" ").append(alias);
				}
			}
			hql.append(" where ");
			associationKeys = associationCritMap.keySet().iterator();
			counter = 0;
			while (associationKeys.hasNext())
			{
				String roleName = (String) associationKeys.next();
				Object roleValue = associationCritMap.get(roleName);

				if (roleValue instanceof Collection)
				{
					Object[] objs = ((Collection) roleValue).toArray();
					for (int i = 0; i < objs.length; i++)
					{
						String alias = getAlias(objs[i].getClass().getName(),counter++);
						hql.append(alias).append(" in elements(").append(srcAlias).append(".").append(roleName).append(")");
						hql.append(" and ");
						hql.append(alias).append(" in (").append(getObjectCriterion(objs[i], cfg, false)).append(") ");
						if (i < objs.length-1)
							hql.append(" and ");
					}
				} else
				{
					String alias = getAlias(roleValue.getClass().getName(),counter++);
					hql.append(alias).append(".id").append("=").append(srcAlias).append(".").append(roleName).append(".id");
					hql.append(" and ");
					hql.append(alias).append(" in (").append(getObjectCriterion(roleValue, cfg, false)).append(") ");
				}
				hql.append(" ");
				if (associationKeys.hasNext())
					hql.append(" and ");
			}
		}
		}
		String attributeCriteria = getObjectAttributeCriterion(srcAlias, obj, cfg);
		if ((associationCritMap == null || associationCritMap.size() == 0 && attributeCriteria != null && attributeCriteria.trim().length() > 0) && !skipAssociations)
			hql.append(" where ");
		if (associationCritMap != null && associationCritMap.size() > 0 && attributeCriteria != null && attributeCriteria.trim().length() > 0)
			hql.append(" ").append(" and ");
		hql.append(attributeCriteria);

		return hql.toString();
	}

	private void setAssocCriterion(Object obj, PersistentClass pclass, HashMap criterions) throws Exception
	{
		Iterator properties = pclass.getPropertyIterator();
		while (properties.hasNext())
		{
			Property prop = (Property) properties.next();
			if (prop.getType().isAssociationType())
			{
				String fieldName = prop.getName();
				Field field = getDeclaredField(pclass.getMappedClass(), fieldName);
				field.setAccessible(true);
				try {
					Object value = field.get(obj);
					if (value != null)
						if ((value instanceof Collection && ((Collection) value).size() > 0) || !(value instanceof Collection))
							criterions.put(fieldName, field.get(obj));
				} catch (Exception e) {
					// Do nothing - when dealing with implicit queries, pclass would be the concrete subclass of obj, and thus contain fields that are not in obj
				}
			}
		}
	}

	private HashMap getObjAssocCriterion(Object obj, Configuration cfg) throws Exception
	{
		HashMap criterions = new HashMap();
		String objClassName = obj.getClass().getName();

		PersistentClass pclass = getPersistentClass(objClassName);
		if (pclass != null){
			setAssocCriterion(obj, pclass, criterions);
			
			pclass = pclass.getSuperclass();
			while (pclass != null)
			{
				setAssocCriterion(obj, pclass, criterions);
				pclass = pclass.getSuperclass();
			}
		}
		return criterions;
	}

	private String getAlias(String sourceName, int count)
	{
		String alias = sourceName.substring(sourceName.lastIndexOf(SystemConstant.DOT) + 1);
		alias = alias.substring(0, 1).toLowerCase() + alias.substring(1);
		return alias+"_"+count;
	}

	private String getOperator(Object valueObj)
	{
		if (valueObj instanceof java.lang.String)
		{
			String value = (String) valueObj;
			if (value.indexOf('*') >= 0)
			{
				return " like ";
			}
		}
		return "=";
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
	
	
	private Field getDeclaredField(Class klass, String fieldName) throws NoSuchFieldException {
		
		Field field = null;
		
		do {
			
			try {
				field = klass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				field = null;
				klass = klass.getSuperclass();
			}
		} while (!klass.getName().equals("java.lang.Object" ) && field == null);
		
		if (field == null)
			throw new NoSuchFieldException("fieldName: " + fieldName);
		
		return field;
	}
	
	private PersistentClass getPersistentClass(String objClassName){
		PersistentClass pclass = cfg.getClassMapping(objClassName);
		
		if (pclass == null) {//might be dealing with an implicit class.  Check to see if we have a persistent subclass mapping we can use instead
			
			Iterator iter = cfg.getClassMappings();
			Class objClass = null;
			try {
				objClass = Class.forName(objClassName);
			} catch (ClassNotFoundException e1) {
				log.error("Class not found for " + objClassName);
				return null;
			}
			
			while (iter.hasNext()){
				pclass = (PersistentClass)iter.next();
				
				try { 
					(pclass.getMappedClass()).asSubclass(objClass);
					log.debug("Searching for persistent subclass of " + objClassName +"; found " + pclass.getClassName());
					return pclass;
				} catch (Exception e) {
					//do nothing
				}

			}// while
		}
		
		return pclass;
	}
	
	private boolean isObjectEmpty(Object obj)
	{
		Class klass = obj.getClass();
		while (!klass.getName().equals("java.lang.Object" ))
		{
			for(Field field:klass.getDeclaredFields())
			{
				int modifier = field.getModifiers();
				if(!Modifier.isStatic(modifier))
				{
					try {
						field.setAccessible(true);
						Object value = field.get(obj);
						if(value!=null && value instanceof Collection && ((Collection)value).size()>0)
							return false;
						if(value!=null)
							return false;
					} catch (IllegalArgumentException e) {
						//No action
					} catch (IllegalAccessException e) {
						//No action
					}
				}
			}
			klass = klass.getSuperclass();
		}
		return true;
	}
	
	private boolean isObjectAssociationEmpty(Object obj) throws Exception
	{
		Class klass = obj.getClass();
		while (!klass.getName().equals("java.lang.Object" ))
		{
			for(Field field:klass.getDeclaredFields())
			{
				int modifier = field.getModifiers();
				if(!Modifier.isStatic(modifier))
				{
					try {
						field.setAccessible(true);
						Object value = field.get(obj);
						if(value!=null)
						{
							if(value instanceof Collection && ((Collection)value).size()>0)
								return false;
					    	if(!(value instanceof Integer || value instanceof Float || value instanceof Double
					    			|| value instanceof Character || value instanceof Long || value instanceof Boolean
					    			|| value instanceof Byte ||  value instanceof Short  
					    			|| value instanceof String || value instanceof Date))
							return false;
						}
					} catch (IllegalArgumentException e) {
						//No action
					} catch (IllegalAccessException e) {
						//No action
					}
				}
			}
			klass = klass.getSuperclass();
		}
		return true;
	}	
}