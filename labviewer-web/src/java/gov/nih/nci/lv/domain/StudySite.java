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
public class StudySite extends AbstractId {
    
    /** The study protocol. */
    private Protocol protocol;
    /** The study protocol. */
    private HealthcareSite healthcareSite;
    /**
     * 
     * @return protocol
     */
    @ManyToOne
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
