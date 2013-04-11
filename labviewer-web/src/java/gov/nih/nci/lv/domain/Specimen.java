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

/**
 * Specimen.
 * @author Naveen Amiruddin
 *
 */
@Entity
public class Specimen extends AbstractDate {

    private SpecimenCollection specimenCollection;
    private List<LaboratoryTest> laboratoryTests = new ArrayList<LaboratoryTest>();

    /**
     *
     * @return laboratoryTests
     */
    @OneToMany(mappedBy = "specimen")
    public List<LaboratoryTest> getLaboratoryTests() {
        return laboratoryTests;
    }

    /**
     *
     * @param laboratoryTests laboratoryTests
     */
    public void setLaboratoryTests(List<LaboratoryTest> laboratoryTests) {
        this.laboratoryTests = laboratoryTests;
    }

    /**
     *
     * @return specimenCollection
     */
    @ManyToOne
    @JoinColumn(name = "SPECIMEN_COLLECTION_ID")
    public SpecimenCollection getSpecimenCollection() {
        return specimenCollection;
    }

    /**
     *
     * @param specimenCollection specimenCollection
     */
    public void setSpecimenCollection(SpecimenCollection specimenCollection) {
        this.specimenCollection = specimenCollection;
    }





}
