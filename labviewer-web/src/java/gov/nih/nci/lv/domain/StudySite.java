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
 * Study Site class.
 * @author Naveen Amiruddin
 *
 */
@Entity
@Table(name = "STUDY_SITE")
public class StudySite extends StudyProtocolId {

    /** The healthcareSite. */
    private HealthcareSite healthcareSite;
    /**
     *
     * @return healthcareSite
     */
    @ManyToOne
    @JoinColumn(name = "HEALTHCARE_SITE_ID", updatable = false)
    public HealthcareSite getHealthcareSite() {
        return healthcareSite;
    }

    /**
     *
     * @param healthcareSite healthcareSite
     */
    public void setHealthcareSite(HealthcareSite healthcareSite) {
        this.healthcareSite = healthcareSite;
    }





}
