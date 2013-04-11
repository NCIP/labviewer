/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.lv.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Participant class.
 */
@Entity
public class Participant extends AbstractDate {

    private String firstName;
    private String lastName;
    private List<StudyParticipantAssignment> spassignments = new ArrayList<StudyParticipantAssignment>();
    /**
     *
     * @return firstName
     */
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }
    /**
     *
     * @param firstName  firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     *
     * @return lastName
     */
    @Column(name = "LAST_NAME")
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
     * @return spassignments
     */
    @OneToMany(mappedBy = "participant")
    public List<StudyParticipantAssignment> getSpassignments() {
        return spassignments;
    }
    /**
     *
     * @param spassignments spassignments
     */
    public void setSpassignments(List<StudyParticipantAssignment> spassignments) {
        this.spassignments = spassignments;
    }





}
