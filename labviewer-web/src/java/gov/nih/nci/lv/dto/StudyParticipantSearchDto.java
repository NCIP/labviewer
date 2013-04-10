/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.dto;

import gov.nih.nci.lv.domain.HealthcareSite;
import gov.nih.nci.lv.domain.Identifier;
import gov.nih.nci.lv.domain.Participant;

/**
 * Study Participant Dto.
 * @author NAmiruddin
 *
 */
public class StudyParticipantSearchDto extends AbstractDto<StudyParticipantSearchDto> {

    private String identifier; // mrn
    private String firstName;
    private String lastName;
    private String gridId;
    private HealthcareSiteDto healthcareSiteDto;
    /**
     * no arg cons.
     */
    public StudyParticipantSearchDto() {

    }
    /**
     * contructs with a protocol id.
     * @param studyProtocolId studyProtocolId
     */
    public StudyParticipantSearchDto(long studyProtocolId) {
        super();
        setStudyProtocolId(studyProtocolId);
    }
    /**
     *
     * @param studyProtocolId studyProtocolId
     * @param studyParticipantIdentifier studyParticipantIdentifier
     */
    public StudyParticipantSearchDto(long studyProtocolId , long studyParticipantIdentifier) {
        super();
        setStudyProtocolId(studyProtocolId);
        setId(studyParticipantIdentifier);
    }
    /**
     *
     * @param studyProtocolId studyProtocolId
     * @param studyParticipantIdentifier studyParticipantIdentifier
     * @param userName userName
     */
    public StudyParticipantSearchDto(long studyProtocolId , long studyParticipantIdentifier, String userName) {
        super();
        setStudyProtocolId(studyProtocolId);
        setId(studyParticipantIdentifier);
        setUserName(userName);
    }
    /**
     *
     * @param identifier identifier
     * @param participant participant
     * @param healthcareSite healthcareSite
     */
    public StudyParticipantSearchDto(Identifier identifier, Participant participant, HealthcareSite healthcareSite) {
        super();
        this.setId(participant.getId());
        this.firstName = participant.getFirstName();
        this.lastName = participant.getLastName();
        this.identifier  = identifier.getExtension();
        this.gridId = identifier.getRoot();
        this.healthcareSiteDto = new HealthcareSiteDto(healthcareSite);
    }
    /**
     *
     * @return identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    /**
     *
     * @param identifier identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    /**
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     *
     * @param firstName firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     *
     * @return  lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     *
     * @param lastName lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     *
     * @return healthcareSiteDto
     */
    public HealthcareSiteDto getHealthcareSiteDto() {
        return healthcareSiteDto;
    }
    /**
     *
     * @param healthcareSiteDto healthcareSiteDto
     */
    public void setHealthcareSiteDto(HealthcareSiteDto healthcareSiteDto) {
        this.healthcareSiteDto = healthcareSiteDto;
    }
    /**
     *
     * @return grid id
     */
    public String getGridId() {
        return gridId;
    }
    /**
     *
     * @param gridId grid id
     */
    public void setGridId(String gridId) {
        this.gridId = gridId;
    }



}
