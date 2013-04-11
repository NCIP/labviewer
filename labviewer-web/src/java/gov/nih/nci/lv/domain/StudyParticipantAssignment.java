/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.lv.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Study Participant Assignment domain.
 * @author Naveen Amiruddin
 *
 */
@Entity
@Table(name = "STUDY_PARTICIPANT_ASSIGNMENT")
public class StudyParticipantAssignment extends AbstractDate {

    private StudySite studySite;
    private Participant participant;
    private List<Activity> activities = new ArrayList<Activity>();
    private List<Identifier> studySubjectIdentifiers = new ArrayList<Identifier>();
    /**
     *
     * @return studySite
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_SITE_ID", updatable = false)
    public StudySite getStudySite() {
        return studySite;
    }
    /**
     *
     * @param studySite studySite
     */
    public void setStudySite(StudySite studySite) {
        this.studySite = studySite;
    }
    /**
     *
     * @return participant
     */
    @ManyToOne
    @JoinColumn(name = "PARTICIPANT_ID", updatable = false)
    public Participant getParticipant() {
        return participant;
    }
    /**
     *
     * @param participant participant
     */
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
    /**
     *
     * @return activities
     */
    @OneToMany(mappedBy = "studyParticipantAssignment")
    public List<Activity> getActivities() {
        return activities;
    }
    /**
     *
     * @param activities activities
     */
    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
    /**
     *
     * @return studySubjectIdentifiers
     */
    @OneToMany(mappedBy = "studyParticipantAssignment")
    public List<Identifier> getStudySubjectIdentifiers() {
        return studySubjectIdentifiers;
    }
    /**
     *
     * @param studySubjectIdentifiers studySubjectIdentifiers
     */
    public void setStudySubjectIdentifiers(List<Identifier> studySubjectIdentifiers) {
        this.studySubjectIdentifiers = studySubjectIdentifiers;
    }



}
