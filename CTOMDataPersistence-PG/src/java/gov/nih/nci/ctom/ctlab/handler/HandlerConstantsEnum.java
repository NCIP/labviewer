/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

/**
 * HandlerConstants Enum defines all the handlers for persisting a HL7V3message
 * @author asharma
 *
 */
public enum HandlerConstantsEnum
{
	ACTIVITY,
	CENTRAL_LABORATORY,
	CLINICAL_RESULT,
	HEALTH_CARE_SITE, 
	HEALTH_CARE_SITE_PARTICIPANT,
	PROTOCOL_IDENTIFIER,
	PARTICIPANT_IDENTIFIER,
	SPA_IDENTIFIER,
	INVESTIGATOR,
	OBSERVATION,
	PARTICIPANT,
	PERFORMING_LABORATORY,
	PROCEDURE,
	PROTOCOL,
	PROTOCOL_STATUS,
	SPECIMEN_COLLECTION,
	SPECIMEN,
	STUDY_PARTICIPANT_ASSIGNMENT,
	STUDY_TIME_POINT,
	NOVALUE;
	
	/**
	 * @param str
	 * @return
	 */
	public static HandlerConstantsEnum handleName(String str)
    {
        try {
            return valueOf(str);
        } 
        catch (Exception ex) {
            return NOVALUE;
        }
    }   

	
	
}
