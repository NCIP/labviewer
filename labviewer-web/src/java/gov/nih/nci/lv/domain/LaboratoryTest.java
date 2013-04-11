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
 *
 * @author Naveen Amiruddin
 *
 */
@Entity
@Table(name = "LAB_HUB_LAB_TEST")
public class LaboratoryTest extends AbstractId {

    private Cd laboratoryTestCode;
    private Specimen specimen;
    private LaboratoryResult laboratoryResult;

    /**
     * Retreives the value of laboratoryTestCode attribue.
     * @return laboratoryTestCode
     **/

    @ManyToOne
    @JoinColumn(name = "LAB_TEST_CONCEPT_DESCRIPTOR_ID")
    public Cd getLaboratoryTestCode() {
         return laboratoryTestCode;
    }
    /**
    * Sets the value of laboratoryTestCode attribue.
    * @param laboratoryTestCode laboratoryTestCode
    **/
    public void setLaboratoryTestCode(Cd laboratoryTestCode) {
        this.laboratoryTestCode = laboratoryTestCode;
    }

    /**
     * Retreives the value of specimen attribue.
     * @return specimen
     **/
    @ManyToOne
    @JoinColumn(name = "SPECIMEN_ID")
    public Specimen getSpecimen() {
         return specimen;
    }
    /**
     * Sets the value of specimen attribue.
     * @param specimen specimen
     **/
    public void setSpecimen(Specimen specimen) {
       this.specimen = specimen;
    }

    /**
    * Retreives the value of laboratoryResult attribue.
    * @return laboratoryResult
    **/
    @ManyToOne
    @JoinColumn(name = "LAB_RESULT_ID")
    public LaboratoryResult getLaboratoryResult() {
        return laboratoryResult;
    }
    /**
    * Sets the value of laboratoryResult attribue.
    * @param laboratoryResult laboratoryResult
    **/
    public void setLaboratoryResult(LaboratoryResult laboratoryResult) {
       this.laboratoryResult = laboratoryResult;
    }

}
