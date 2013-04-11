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
import javax.persistence.Table;

/**
 *
 * @author Naveen Amiruddin
 *
 */
@Entity
@Table(name = "PROTOCOL")
public class Protocol extends AbstractUser {

    private String nciIdentifier;
    private String longTitleText;
    private String shortTitleText;
    private String phaseCode;
    private List<StudySite> studySites = new ArrayList<StudySite>();
    private List<ProtocolStatus> protocolStatuses = new ArrayList<ProtocolStatus>();
    /**
     *
     * @return nciIdentifier
     */
    @Column(name = "NCI_IDENTIFIER")
    public String getNciIdentifier() {
        return nciIdentifier;
    }
    /**
     *
     * @param nciIdentifier nciIdentifier
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }
    /**
     *
     * @return longTitleText
     */
    @Column(name = "LONG_TITLE_TEXT")
    public String getLongTitleText() {
        return longTitleText;
    }
    /**
     *
     * @param longTitleText longTitleText
     */
    public void setLongTitleText(String longTitleText) {
        this.longTitleText = longTitleText;
    }
    /**
     *
     * @return shortTitleText
     */
    @Column(name = "SHORT_TITLE_TEXT")
    public String getShortTitleText() {
        return shortTitleText;
    }
    /**
     *
     * @param shortTitleText shortTitleText
     */
    public void setShortTitleText(String shortTitleText) {
        this.shortTitleText = shortTitleText;
    }

    /**
     *
     * @return phaseCode
     */
    @Column(name = "PHASE_CODE")
    public String getPhaseCode() {
        return phaseCode;
    }
    /**
     *
     * @param phaseCode phaseCode
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }
    /**
     *
     * @return studySites
     */
    @OneToMany(mappedBy = "protocol")
    public List<StudySite> getStudySites() {
        return studySites;
    }
    /**
     *
     * @param studySites studySites
     */
    public void setStudySites(List<StudySite> studySites) {
        this.studySites = studySites;
    }
    /**
     *
     * @return protocolStatuses
     */
    @OneToMany(mappedBy = "protocol")
    public List<ProtocolStatus> getProtocolStatuses() {
        return protocolStatuses;
    }
    /**
     *
     * @param protocolStatuses protocolStatuses
     */
    @OneToMany(mappedBy = "protocol")
    public void setProtocolStatuses(List<ProtocolStatus> protocolStatuses) {
        this.protocolStatuses = protocolStatuses;
    }
}
