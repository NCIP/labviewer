/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.dto;

import gov.nih.nci.lv.domain.Identifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Naveen Amiruddin
 *
 */
public class LabSearchDto extends AbstractDto<LabSearchDto> {

    private String labIds;
    private Long studyParticipantId;
    private Date actualStartDate;
    private String labTestCode;
    private Float numericResult;
    private String textResult;
    private String uom;
    private Integer referenceLowRange;
    private Integer referenceHighRange;
    private String range;
    private boolean isWithinRange;
    private Date aeSentDate;
    private Date cdmsSentDate;
    private List<String> studySubjectIdentifiers;

    /**
     * @return labIds
     */
    public String getLabIds() {
        return labIds;
    }
    /**
     *
     * @param labIds labIds
     */
    public void setLabIds(String labIds) {
        this.labIds = labIds;
    }
    /**
     *
     * @return studyParticipantId
     */
    public Long getStudyParticipantId() {
        return studyParticipantId;
    }
    /**
     *
     * @param studyParticipantId studyParticipantId
     */
    public void setStudyParticipantId(Long studyParticipantId) {
        this.studyParticipantId = studyParticipantId;
    }
    /**
     *
     * @return actualStartDate
     */
    public Date getActualStartDate() {
        return actualStartDate;
    }
    /**
     *
     * @param actualStartDate actualStartDate
     */
    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }
    /**
     *
     * @return labTestCode
     */
    public String getLabTestCode() {
        return labTestCode;
    }
    /**
     *
     * @param labTestCode labTestCode
     */
    public void setLabTestCode(String labTestCode) {
        this.labTestCode = labTestCode;
    }
    /**
     *
     * @return numericResult
     */
    public Float getNumericResult() {
        return numericResult;
    }
    /**
     *
     * @param numericResult numericResult
     */
    public void setNumericResult(Float numericResult) {
        this.numericResult = numericResult;
    }
    /**
     *
     * @return textResult
     */
    public String getTextResult() {
        return textResult;
    }
    /**
     *
     * @param textResult textResult
     */
    public void setTextResult(String textResult) {
        this.textResult = textResult;
    }
    /**
     *
     * @return uom
     */
    public String getUom() {
        return uom;
    }
    /**
     *
     * @param uom uom
     */
    public void setUom(String uom) {
        this.uom = uom;
    }
    /**
     *
     * @return referenceLowRange
     */
    public Integer getReferenceLowRange() {
        return referenceLowRange;
    }
    /**
     *
     * @param referenceLowRange referenceLowRange
     */
    public void setReferenceLowRange(Integer referenceLowRange) {
        this.referenceLowRange = referenceLowRange;
    }
    /**
     *
     * @return referenceHighRange
     */
    public Integer getReferenceHighRange() {
        return referenceHighRange;
    }
    /**
     *
     * @param referenceHighRange referenceHighRange
     */
    public void setReferenceHighRange(Integer referenceHighRange) {
        this.referenceHighRange = referenceHighRange;
    }
    /**
     *
     * @return range
     */
    public String getRange() {
        return range;
    }
    /**
     *
     * @param range range
     */
    public void setRange(String range) {
        this.range = range;
    }
    /**
     *
     * @return isWithinRange
     */
    public boolean getIsWithinRange() {
        return isWithinRange;
    }
    /**
     *
     * @param isWithinRange isWithinRange
     */
    public void setIsWithinRange(boolean isWithinRange) {
        this.isWithinRange = isWithinRange;
    }
    /**
     *
     * @return studySubjectIdentifiers
     */
    public List<String> getStudySubjectIdentifiers() {
        return studySubjectIdentifiers;
    }
    /**
     *
     * @param studySubjectIdentifiers studySubjectIdentifiers
     */
    public void setStudySubjectIdentifiers(List<String> studySubjectIdentifiers) {
        this.studySubjectIdentifiers = studySubjectIdentifiers;
    }
    /**
     *
     * @param identifiers identifier
     */
    public void setStudySubjectId(List<Identifier> identifiers) {
        List<String> ids = new ArrayList<String>();
        if (identifiers != null) {
            for (Identifier identifier : identifiers) {
                ids.add(identifier.getExtension());
            }
        }
        this.studySubjectIdentifiers = ids;
    }
    /**
     *
     * @return aeSentDate
     */
    public Date getAeSentDate() {
        return aeSentDate;
    }
    /**
     *
     * @param aeSentDate aeSentDate
     */
    public void setAeSentDate(Date aeSentDate) {
        this.aeSentDate = aeSentDate;
    }
    /**
     *
     * @return cdmsSentDate cdmsSentDate
     */
    public Date getCdmsSentDate() {
        return cdmsSentDate;
    }
    /**
     *
     * @param cdmsSentDate caersSentDate
     */
    public void setCdmsSentDate(Date cdmsSentDate) {
        this.cdmsSentDate = cdmsSentDate;
    }
}
