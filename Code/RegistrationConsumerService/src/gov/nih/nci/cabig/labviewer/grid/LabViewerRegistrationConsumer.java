package gov.nih.nci.cabig.labviewer.grid;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gov.nih.nci.ccts.grid.IdentifierType;
import gov.nih.nci.ccts.grid.ParticipantType;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.common.RegistrationConsumer;
import gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException;
import gov.nih.nci.ccts.grid.stubs.types.RegistrationConsumptionException;
import gov.nih.nci.ctom.ctlab.domain.Identifier;
import gov.nih.nci.ctom.ctlab.domain.Participant;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;

public class LabViewerRegistrationConsumer implements RegistrationConsumer
{
	private static final Log logger = LogFactory.getLog(LabViewerRegistrationConsumer.class);
	
	public Registration register(Registration registration)
		throws RemoteException, InvalidRegistrationException, RegistrationConsumptionException
	{
		logger.info("Registration message received");
		System.out.println("LabViewerRegistrationConsumer.register called");
		ParticipantType participant = registration.getParticipant();
		
		Participant part = new Participant();
		part.setFirstName(participant.getFirstName());
		part.setLastName(participant.getLastName());
		part.setAdminGenderCode(participant.getAdministrativeGenderCode());
		part.setBirthDate(participant.getBirthDate());
		part.setEthnicGroupCode(participant.getEthnicGroupCode());
		part.setRaceCode(participant.getRaceCode());
		
		// Assume that only one identifier was sent and use that
		IdentifierType id = participant.getIdentifier(0);
		if (id != null)
		{
			Identifier ident = new Identifier();
			ident.setExtension(id.getValue());
			ident.setSource(id.getSource());
			part.setIdentifier(ident);
		}
		else
		{
			throw new InvalidRegistrationException();
		}
		
		System.out.println("Saving participant");
		
		CTLabDAO dao = new CTLabDAO();
		Connection con = dao.getConnection();
		
		try
		{
			dao.saveParticipant(con, part);
		}
		catch (SQLException e)
		{
			logger.error("Error saving participant", e);
			throw new RegistrationConsumptionException();
		}
		
		return registration;
	}
	
}