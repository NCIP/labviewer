/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

/**
 * HL7V3MessageHandlerFactory creates the handlers for persisting different
 * objects in HL7V3 message
 * 
 * @author asharma
 */
public class HL7V3MessageHandlerFactory
{

	private static final HL7V3MessageHandlerFactory hl7v3factory =
			new HL7V3MessageHandlerFactory();

	/**
	 * Singleton private constructor
	 */
	private HL7V3MessageHandlerFactory()
	{

	}

	/**
	 * Returns the instance of HL7V3MessageHandlerFactory 
	 * @return hl7v3factory
	 */
	public static HL7V3MessageHandlerFactory getInstance()
	{
		return hl7v3factory;
	}

	/**
	 * Returns handlers for persisting different objects in HL7V3 message
	 * based on the string type passed in as parameter. 
	 * @param type 
	 * @return HL7V3MessageHandlerInterface
	 */
	public HL7V3MessageHandlerInterface getHandler(String type)
	{
		switch (HandlerConstantsEnum.handleName(type))
		{
		case ACTIVITY:
			return new ActivityHandler();
		case CENTRAL_LABORATORY:
			return new CentralLaboratoryHandler();
		case CLINICAL_RESULT:
			return new ClinicalResultHandler();
		case HEALTH_CARE_SITE:
			return new HealthCareSiteHandler();
		case HEALTH_CARE_SITE_PARTICIPANT:
			return new HealthCareSiteParticipantHandler();
		case PROTOCOL_IDENTIFIER:
			return new ProtocolIdentifierHandler();
		case PARTICIPANT_IDENTIFIER:
			return new ParticipantIdentifierHandler();
		case SPA_IDENTIFIER:
			return new SPAIdentifierHandler();
		case INVESTIGATOR:
			return new InvestigatorHandler();
		case OBSERVATION:
			return new ObservationHandler();
		case PARTICIPANT:
			return new ParticipantHandler();
		case PERFORMING_LABORATORY:
			return new PerformingLaboratoryHandler();
		case PROCEDURE:
			return new ProcedureHandler();
		case PROTOCOL:
			return new ProtocolHandler();
		case PROTOCOL_STATUS:
			return new ProtocolStatusHandler();
		case SPECIMEN_COLLECTION:
			return new SpecimenCollectionHandler();
		case SPECIMEN:
			return new SpecimenHandler();
		case STUDY_PARTICIPANT_ASSIGNMENT:
			return new StudyParticipantAssignmentHandler();
		case STUDY_TIME_POINT:
			return new StudyTimePointHandler();
		default:
			return null;
		}
	}

}
