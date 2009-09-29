package gov.nih.nci.system.applicationservice;

import java.util.List;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.query.SDKQuery;
import gov.nih.nci.system.query.SDKQueryResult;

public interface WritableApplicationService extends ApplicationService
{
	public SDKQueryResult executeQuery(SDKQuery query) throws ApplicationException;
	
	public List<SDKQueryResult> executeBatchQuery(List<SDKQuery> batchQuery) throws ApplicationException;
}