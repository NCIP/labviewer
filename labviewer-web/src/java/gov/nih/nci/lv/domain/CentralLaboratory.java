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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CentralLaboratory.
 * @author Naveen Amiruddin
 *
 */
@Entity
@Table(name = "CENTRAL_LABORATORY")
public class CentralLaboratory extends Organization {

    private List<SpecimenCollection> specimenCollections = new ArrayList<SpecimenCollection>();

    /**
     *
     * @return specimenCollections
     */
    @OneToMany(mappedBy = "centralLaboratory")
    public List<SpecimenCollection> getSpecimenCollections() {
        return specimenCollections;
    }

    /**
     *
     * @param specimenCollections specimenCollections
     */
    public void setSpecimenCollections(List<SpecimenCollection> specimenCollections) {
        this.specimenCollections = specimenCollections;
    }




}
