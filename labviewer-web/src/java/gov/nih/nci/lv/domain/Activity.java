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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Lab Hub Activity.
 * @author Naveen Amiruddin
 *
 */
@Entity
@Table(name = "LAB_HUB_ACTIVITY")
@Inheritance(strategy = InheritanceType.JOINED)
public class Activity extends AbstractId {
    
    private Date actualEndDateTime;
    private Date actualStartDateTime;
    private StudyParticipantAssignment studyParticipantAssignment;
    /**
     * 
     * @return actualEndDateTime
     */
    @Column(name = "ACTUAL_END_DATE_TIME")
    public Date getActualEndDateTime() {
        return actualEndDateTime;
    }
    /**
     * 
     * @param actualEndDateTime actualEndDateTime
     */
    public void setActualEndDateTime(Date actualEndDateTime) {
        this.actualEndDateTime = actualEndDateTime;
    }
    /**
     * 
     * @return actualStartDateTime
     */
    @Column(name = "ACTUAL_START_DATE_TIME")
    public Date getActualStartDateTime() {
        return actualStartDateTime;
    }
    /**
     * 
     * @param actualStartDateTime actualStartDateTime
     */
    public void setActualStartDateTime(Date actualStartDateTime) {
        this.actualStartDateTime = actualStartDateTime;
    }
    /**
     * 
     * @return studyParticipantAssignment
     */
    @ManyToOne
    @JoinColumn(name = "SUBJECT_ASSIGNMENT_ID")    
    public StudyParticipantAssignment getStudyParticipantAssignment() {
        return studyParticipantAssignment;
    }
    /**
     * 
     * @param studyParticipantAssignment studyParticipantAssignment
     */
    public void setStudyParticipantAssignment(
            StudyParticipantAssignment studyParticipantAssignment) {
        this.studyParticipantAssignment = studyParticipantAssignment;
    }
    
    
    

}
