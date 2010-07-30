package gov.nih.nci.cagrid.labviewer.grid;
import gov.nih.nci.cabig.ccts.domain.OrganizationAssignedIdentifierType;
import gov.nih.nci.cabig.ccts.domain.ParticipantType;
import gov.nih.nci.cabig.ccts.domain.Registration;
import gov.nih.nci.cabig.ccts.domain.Study;
import gov.nih.nci.cagrid.labviewer.grid.client.StudyServiceClient;

import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;

public class StudyServiceTest {

    String serviceUrl =
        "https://localhost:21443/ctom-wsrf/services/cagrid/StudyService";

    String sampleFile = "D:/CCTS/FILES/XML/REGISTER_SUBJECT.xml";
    String proxyFile = "D:/CCTS/FILES/PROXY/proxy";
    private Logger log = Logger.getLogger(getClass());

    public static void main(String[] args)
    {
        StudyServiceTest studyService = new StudyServiceTest();
        studyService.testStudyService();
    }

    /**
     * test method performs the test by creating the registration object and
     * calling the service
     */
    public void testStudyService()
    {
        try
        {
            System.out.println("Calling study service at " + serviceUrl);
            log.debug("Calling study service at " + serviceUrl);
            // Setup the credentials
            GlobusCredential gb = new GlobusCredential(proxyFile);
            // Create the client
            StudyServiceClient client =  new StudyServiceClient(this.serviceUrl, gb);
            // Call the service
            Registration registration = new Registration();
            ParticipantType participant = new ParticipantType();
            OrganizationAssignedIdentifierType identifier = 
                new OrganizationAssignedIdentifierType();
            participant.setGridId("111");
            identifier.setValue ("00-00-00-0");
            OrganizationAssignedIdentifierType[] identifiers = { identifier };
            participant.setIdentifier(identifiers);
            registration.setParticipant(participant);

            Study study = client.getStudy(registration);
            System.out.println("Returned from calling service"+study.getLongTitleText());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
}
