/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.dto;

import gov.nih.nci.lv.domain.Identifier;
import gov.nih.nci.lv.domain.Protocol;
import gov.nih.nci.lv.domain.ProtocolStatus;

/**
 * Study Search class.
 * @author NAmiruddin
 *
 */
public class StudySearchDto extends AbstractDto<StudySearchDto> {

    private String nciIdentifier;
    private String shortTitle;
    private String phaseCode;
    private String statusCode;

    /**
     * no arg constructor.
     */
    public StudySearchDto() {

    }
    /**
     * no arg constructor.
     * @param id protocol id
     */
    public StudySearchDto(Long id) {
        super();
        this.setId(id);
    }
    /**
     * no arg constructor.
     * @param id protocol id
     * @param userName userName
     */
    public StudySearchDto(Long id, String userName) {
        super();
        this.setId(id);
        this.setUserName(userName);
    }
    /**
     *
     * @param protocol protocol
     * @param identifier identifier
     * @param protocolStatus protocolStatus
     */
    public StudySearchDto(Protocol protocol , Identifier identifier , ProtocolStatus protocolStatus) {
        super();
        this.setId(protocol.getId());
        this.nciIdentifier =  identifier.getExtension();
        this.shortTitle = protocol.getShortTitleText();
        this.statusCode = protocolStatus.getStatusCode();
    }


    /**
     *
     * @return nciIdentifier
     */
    public String getNciIdentifier() {
        return nciIdentifier;
    }
    /**
     *
     * @param nciIdentifier nciIdentifier
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }
    /**
     *
     * @return shortTitle
     */
    public String getShortTitle() {
        return shortTitle;
    }
    /**
     *
     * @param shortTitle shortTitle
     */
    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }
    /**
     *
     * @return phaseCode
     */
    public String getPhaseCode() {
        return phaseCode;
    }
    /**
     *
     * @param phaseCode phaseCode
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }
    /**
     *
     * @return statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }
    /**
     *
     * @param statusCode statusCode
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


}
