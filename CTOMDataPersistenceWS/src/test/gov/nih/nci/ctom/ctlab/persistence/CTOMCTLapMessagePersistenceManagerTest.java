package test.gov.nih.nci.ctom.ctlab.persistence;

import gov.nih.nci.ctom.applicationservice.CTOMCTLabMessagePersistenceManager;
import junit.framework.TestCase;

public class CTOMCTLapMessagePersistenceManagerTest extends TestCase {

	public void testSaveData() {
		CTOMCTLabMessagePersistenceManager mgr = new CTOMCTLabMessagePersistenceManager();
		try {
			mgr.saveData(null);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
