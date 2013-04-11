/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Naveen Amiruddin
 *
 */
@Entity
@Table(name = "LAB_VIEWER_STATUS")
public class LoadLabStatus {


    private String aeIndicator;
    private Date aeSentDate;
    private String cdmsIndicator;
    private Date cdmsDate;
    private Long clinicalResultId;
    private Long id;
    /**
     * set id.
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the id of the object.
     * @return the id
     */
    @Id
    @SequenceGenerator(name = "labviewer_seq", sequenceName = "LAB_VIEWER_STATUS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "labviewer_seq")
    public Long getId() {
        return this.id;
    }

    /**
     *
     * @return aeIndicator
     */
    @Column(name = "ADVERSE_EVENT_INDICATOR")
    public String getAeIndicator() {
        return aeIndicator;
    }
    /**
     *
     * @param aeIndicator aeIndicator
     */
    public void setAeIndicator(String aeIndicator) {
        this.aeIndicator = aeIndicator;
    }
    /**
     *
     * @return aeSentDate
     */
    @Column(name = "ADVERSE_EVENT_SENT_DATE")
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
     * @return cdmsIndicator
     */
    @Column(name = "CDMS_INDICATOR")
    public String getCdmsIndicator() {
        return cdmsIndicator;
    }
    /**
     *
     * @param cdmsIndicator cdmsIndicator
     */
    public void setCdmsIndicator(String cdmsIndicator) {
        this.cdmsIndicator = cdmsIndicator;
    }
    /**
     *
     * @return cdmsDate
     */
    @Column(name = "CDMS_SENT_DATE")
    public Date getCdmsDate() {
        return cdmsDate;
    }
    /**
     *
     * @param cdmsDate cdmsDate
     */
    public void setCdmsDate(Date cdmsDate) {
        this.cdmsDate = cdmsDate;
    }
    /**
     *
     * @return cdmsDate
     */
    @Column(name = "CLINICAL_RESULT_ID")
    public Long getClinicalResultId() {
        return clinicalResultId;
    }
    /**
     *
     * @param clinicalResultId clinicalResultId
     */
    public void setClinicalResultId(Long clinicalResultId) {
        this.clinicalResultId = clinicalResultId;
    }
}
