package gov.nih.nci.cabig.labviewer.grid;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gov.nih.nci.ccts.grid.ParticipantType;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.common.RegistrationConsumer;
import gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException;
import gov.nih.nci.ccts.grid.stubs.types.RegistrationConsumptionException;

public class LabViewerRegistrationConsumer implements RegistrationConsumer
{
	private static final Log logger = LogFactory.getLog(LabViewerRegistrationConsumer.class);
	
	public Registration register(Registration registration)
		throws RemoteException, InvalidRegistrationException, RegistrationConsumptionException
	{
		logger.info("Registration message received");
		System.out.println("LabViewerRegistrationConsumer.register called");
		ParticipantType participant = registration.getParticipant();
		System.out.println("Participant first name is " + participant.getFirstName());
		System.out.println("Participant last name is " + participant.getLastName());
		
		return registration;
	}
	
}