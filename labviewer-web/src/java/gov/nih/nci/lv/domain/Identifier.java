/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.lv.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Identifier domain.
 * @author Naveen Amiruddin
 *
 */
@Entity
@Table(name = "IDENTIFIER")
public class Identifier extends AbstractDate {

    private String extension;
    private Protocol protocol;
    private String root;
    private Participant participant;
    private StudyParticipantAssignment studyParticipantAssignment;


    /**
     *
     * @return root
     */
    public String getRoot() {
        return root;
    }
    /**
     *
     * @param root root
     */
    public void setRoot(String root) {
        this.root = root;
    }

    /**
     *
     * @return extension
     */
    public String getExtension() {
        return extension;
    }
    /**
     *
     * @param extension extension
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }


    /**
     *
     * @return protocol
     */
    @OneToOne
    @JoinColumn(name = "PROTOCOL_ID", updatable = false)
    public Protocol getProtocol() {
        return protocol;
    }
    /**
     *
     * @param protocol protocol
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
    /**
     *
     * @return participant
     */
    @OneToOne
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
     * @return studyParticipantAssignment
     */
    @OneToOne
    @JoinColumn(name = "STUDY_PARTICIPANT_ASSIGNMNT_ID", updatable = false)
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
