package gov.nih.nci.system.query.hql;

import java.util.List;

import gov.nih.nci.system.query.SDKQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

public class UpdateHQLQuery extends HQLCriteria implements HQLManipulationQuery,SDKQuery
{
	private static final long serialVersionUID = 1L;

	public UpdateHQLQuery(String hql, List<Object> parameters) {
		super(hql, parameters);
	}
	public UpdateHQLQuery(String hql) {
		super(hql);
	}
}