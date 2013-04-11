/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Study Investigator.
 * @author NAmiruddin
 *
 */
@Entity
@Table(name = "STUDY_INVESTIGATOR")
public class StudyInvestigator extends StudyProtocolId {

    private Investigator investigator;

    /**
     *
     * @return investigator
     */
    @ManyToOne
    @JoinColumn(name = "INVESTIGATOR_ID")
    public Investigator getInvestigator() {
        return investigator;
    }

    /**
     *
     * @param investigator investigator
     */
    public void setInvestigator(Investigator investigator) {
        this.investigator = investigator;
    }





}
