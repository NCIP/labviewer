package gov.nih.nci.cabig.labviewer.grid;

import gov.nih.nci.ccts.grid.Study;
import gov.nih.nci.ccts.grid.common.StudyConsumerI;
import gov.nih.nci.ccts.grid.stubs.types.InvalidStudyException;
import gov.nih.nci.ccts.grid.stubs.types.StudyCreationException;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

public class LabViewerStudyConsumer implements StudyConsumerI
{
	Logger logger = Logger.getLogger(getClass());
	
	public void commit(Study study) throws RemoteException, InvalidStudyException
	{
		// TODO Auto-generated method stub
		
	}

	public void createStudy(Study study) throws RemoteException, InvalidStudyException, StudyCreationException
	{
		// TODO Auto-generated method stub
		
	}

	public void rollback(Study study) throws RemoteException, InvalidStudyException
	{
		// TODO Auto-generated method stub
		
	}
}
