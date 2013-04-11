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
 * PerformingLaboratory.
 */
@Entity
@Table(name = "PERFORMING_LABORATORY")
public class PerformingLaboratory extends Organization {

    private List<LaboratoryResult> labResults = new ArrayList<LaboratoryResult>();

    /**
     *
     * @return labResults
     */
    @OneToMany(mappedBy = "performingLaboratory")
    public List<LaboratoryResult> getLabResults() {
        return labResults;
    }

    /**
     *
     * @param labResults labResults
     */
    public void setLabResults(List<LaboratoryResult> labResults) {
        this.labResults = labResults;
    }




}
