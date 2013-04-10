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
 *
 * @author NAmiruddin
 *
 */
@Entity
@Table(name = "Lab_Hub_Specimen_Collection")
public class SpecimenCollection extends Activity {

    private List<Specimen> speciments = new ArrayList<Specimen>();
    private CentralLaboratory centralLaboratory = null;
    /**
     *
     * @return speciments
     */
    @OneToMany(mappedBy = "specimenCollection")
    public List<Specimen> getSpeciments() {
        return speciments;
    }

    /**
     *
     * @param speciments speciments
     */
    public void setSpeciments(List<Specimen> speciments) {
        this.speciments = speciments;
    }

    /**
     *
     * @return centralLaboratory
     */
    @ManyToOne
    @JoinColumn(name = "CENTRAL_LABORATORY_ID")
    public CentralLaboratory getCentralLaboratory() {
        return centralLaboratory;
    }

    /**
     *
     * @param centralLaboratory centralLaboratory
     */
    public void setCentralLaboratory(CentralLaboratory centralLaboratory) {
        this.centralLaboratory = centralLaboratory;
    }


}
