package gov.nih.nci.system.util;

import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.query.SDKQuery;
import gov.nih.nci.system.query.example.DeleteExampleQuery;
import gov.nih.nci.system.query.example.InsertExampleQuery;
import gov.nih.nci.system.query.example.SearchExampleQuery;
import gov.nih.nci.system.query.example.UpdateExampleQuery;
import gov.nih.nci.system.query.hql.DeleteHQLQuery;
import gov.nih.nci.system.query.hql.InsertHQLQuery;
import gov.nih.nci.system.query.hql.SearchHQLQuery;
import gov.nih.nci.system.query.hql.UpdateHQLQuery;
import gov.nih.nci.system.security.SecurityConstants;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

public class WritableApiApplicationServiceMethodHelper extends ApplicationServiceMethodHelper{
	
	@SuppressWarnings("unchecked")
	public Map<String, Collection<String>> getDomainObjectName(MethodInvocation invocation) throws ApplicationException {
		
		Method method = invocation.getMethod();
		Object[] arguments = invocation.getArguments();
		Map<String, Collection<String>> securityMap = new HashMap<String, Collection<String>>();
		
		if ("executeQuery".equals(method.getName()) && (arguments[0] instanceof SDKQuery)) {
			SDKQuery sdkQuery = (SDKQuery) arguments[0];
			Map<String, String> tempSecurityMap = validateAndGetSecurityMap(sdkQuery);
			
			Collection<String> privileges = new ArrayList<String>();
			String protectedElementName = tempSecurityMap.keySet().iterator().next();
			privileges.add(tempSecurityMap.get(protectedElementName));
			securityMap.put(protectedElementName, privileges);
		} else if ("executeBatchQuery".equals(method.getName()) && (arguments[0] instanceof List)) {
			List<SDKQuery> batchQueries = (List<SDKQuery>) arguments[0];

			for (SDKQuery sdkQuery : batchQueries) {
				Collection<String> privileges = new ArrayList<String>();

				Map<String, String> tempSecurityMap = validateAndGetSecurityMap(sdkQuery);
				String protectedElementName = tempSecurityMap.keySet().iterator().next();
				if (securityMap.get(protectedElementName) == null) {
					privileges.add(tempSecurityMap.get(protectedElementName));
					securityMap.put(protectedElementName, privileges);
				} else {
					privileges = securityMap.get(protectedElementName);
					privileges.add(tempSecurityMap.get(protectedElementName));
					securityMap.put(protectedElementName, privileges);
				}
			}
		} else {
			securityMap = super.getDomainObjectName(invocation);
		}
		return securityMap;	
	}

	private Map<String, String> validateAndGetSecurityMap(SDKQuery sdkQuery) throws ApplicationException {
		Map<String, String> securityMap=new HashMap<String, String>();
		String privilege;
		if (sdkQuery instanceof InsertExampleQuery) {
			privilege=SecurityConstants.CREATE;
			InsertExampleQuery insertExampleQuery = (InsertExampleQuery) sdkQuery;
			securityMap.put(insertExampleQuery.getExample().getClass().getName(), privilege);
		} else if (sdkQuery instanceof DeleteExampleQuery) {
			privilege=SecurityConstants.DELETE;
			DeleteExampleQuery deleteExampleQuery = (DeleteExampleQuery) sdkQuery;
			securityMap.put(deleteExampleQuery.getExample().getClass().getName(), privilege);
		} else if (sdkQuery instanceof UpdateExampleQuery) {
			privilege=SecurityConstants.UPDATE;
			UpdateExampleQuery updateExampleQuery = (UpdateExampleQuery) sdkQuery;
			securityMap.put(updateExampleQuery.getExample().getClass().getName(), privilege);
		} else if (sdkQuery instanceof SearchExampleQuery) {
			privilege=SecurityConstants.READ;
			SearchExampleQuery searchExampleQuery = (SearchExampleQuery) sdkQuery;
			securityMap.put(searchExampleQuery.getExample().getClass().getName(), privilege);
		} else if (sdkQuery instanceof InsertHQLQuery) {
			privilege=SecurityConstants.CREATE;
			InsertHQLQuery insertHQLQuery = (InsertHQLQuery) sdkQuery;
			String hql=insertHQLQuery.getHqlString();
			String domainClassName=getDomainClassName(hql, privilege);
			securityMap.put(domainClassName, privilege);
		} else if (sdkQuery instanceof UpdateHQLQuery) {
			privilege=SecurityConstants.UPDATE;
			UpdateHQLQuery updateHQLQuery = (UpdateHQLQuery) sdkQuery;
			String hql=updateHQLQuery.getHqlString();
			String domainClassName=getDomainClassName(hql, privilege);
			securityMap.put(domainClassName, privilege);
		} else if (sdkQuery instanceof DeleteHQLQuery) {
			privilege=SecurityConstants.DELETE;
			DeleteHQLQuery deleteHQLQuery = (DeleteHQLQuery) sdkQuery;
			String hql=deleteHQLQuery.getHqlString();
			String domainClassName=getDomainClassName(hql, privilege);
			securityMap.put(domainClassName, privilege);
		} else if (sdkQuery instanceof SearchHQLQuery) {
			privilege = SecurityConstants.READ;
			SearchHQLQuery searchHQLQuery = (SearchHQLQuery) sdkQuery;
			String hql = searchHQLQuery.getHqlString();
			String domainClassName = getDomainClassName(hql,privilege);
			securityMap.put(domainClassName, privilege);
		}
		return securityMap;
	}
	
	private String getDomainClassName(String hql, String privilege) throws ApplicationException {

		String insert = "INSERT";
		String update = "UPDATE";
		String delete = "DELETE";
		String upperHQL = hql.toUpperCase();
		if (privilege.equals(SecurityConstants.READ)) {
			int index1 = upperHQL.indexOf(insert);
			int index2 = upperHQL.indexOf(update);
			int index3 = upperHQL.indexOf(delete);
			if (!(index1 == -1 && index2 == -1 && index3 == -1)) {
				String incorrectPrivilege = null;
				if (index1 != -1) incorrectPrivilege = insert;
				else if (index2 != -1) incorrectPrivilege = update;
				else if (index3 != -1) incorrectPrivilege = delete;
				throw new ApplicationException("Invalid Operation "+incorrectPrivilege+"  in HQL "+hql+" .Expected "+privilege+" in HQL ");
			}
			String domainObjectName = getDomainObjectForDeleteAndSelectHql(hql);
			return domainObjectName;
		} else if (privilege.equals(SecurityConstants.CREATE)) {
			int index1 = upperHQL.indexOf(update);
			int index2 = upperHQL.indexOf(delete);
			if (!(index1 == -1 && index2 == -1)) {
				String incorrectPrivilege=index1!=-1?update:delete;
				throw new ApplicationException("Invalid Operation "+incorrectPrivilege+"  in HQL "+hql+" .Expected "+privilege+" in HQL ");
			}
			String domainObjectName = getDomainObjectForInsertHql(hql);
			return domainObjectName;
		} else if (privilege.equals(SecurityConstants.DELETE)) {
			int index1 = upperHQL.indexOf(insert);
			int index2 = upperHQL.indexOf(update);
			if (!(index1 == -1 && index2 == -1)) {
				String incorrectPrivilege = index1 != -1 ? insert : update;
				throw new ApplicationException("Invalid Operation "+ incorrectPrivilege + "  in HQL " + hql+" .Expected "+privilege+" in HQL ");
			}
			String domainObjectName = getDomainObjectForDeleteAndSelectHql(hql);
			return domainObjectName;
		} else if (privilege.equals(SecurityConstants.UPDATE)) {
			int index1 = upperHQL.indexOf(insert);
			int index2 = upperHQL.indexOf(delete);
			if (!(index1 == -1 && index2 == -1)) {
				String incorrectPrivilege = index1 != -1 ? insert : delete;
				throw new ApplicationException("Invalid Operation "+ incorrectPrivilege + "  in HQL " + hql+" .Expected "+privilege+" in HQL ");
			}
			String domainObjectName = getDomainObjectForUpdateHql(hql);
			return domainObjectName;
		}
		return "*";
	}
	
	private String getDomainObjectForInsertHql(String hql){
		String upperHQL = hql.toUpperCase();
		int index = upperHQL.indexOf(" INTO ");
		hql = hql.substring(index + " INTO ".length()).trim() + " ";
		String domainObjectName = hql.substring(0, hql.indexOf(' ')).trim();
		return domainObjectName;
	}
	
	private String getDomainObjectForDeleteAndSelectHql(String hql) {
		String upperHQL = hql.toUpperCase();
		int index = upperHQL.indexOf(" FROM ");
		hql = hql.substring(index + " FROM ".length()).trim() + " ";
		String domainObjectName = hql.substring(0, hql.indexOf(' ')).trim();
		return domainObjectName;
	}
	
	
	private String getDomainObjectForUpdateHql(String hql) {
		String upperHQL = hql.toUpperCase();
		int index = upperHQL.indexOf("UPDATE");
		hql = hql.substring(index + "UPDATE ".length()).trim() + " ";
		String domainObjectName = hql.substring(0, hql.indexOf(' ')).trim();
		return domainObjectName;
	}
}