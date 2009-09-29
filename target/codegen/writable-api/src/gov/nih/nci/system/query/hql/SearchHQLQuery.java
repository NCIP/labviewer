package gov.nih.nci.system.query.hql;

import gov.nih.nci.system.query.SDKQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.List;

public class SearchHQLQuery extends HQLCriteria implements HQLSearchQuery,SDKQuery
{
	private static final long serialVersionUID = 1L;

	public SearchHQLQuery(String hql, List<Object> parameters) {
		super(hql, parameters);
	}
}