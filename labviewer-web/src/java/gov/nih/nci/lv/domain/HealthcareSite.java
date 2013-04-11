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
 * HealthcareSite domain.
 * @author Naveen Amiruddin
 *
 */
@Entity
@Table(name = "HEALTHCARE_SITE")
public class HealthcareSite extends AbstractDate {

    private String nciInstituteCode;
    private String name;
    private String streetAddress;
    private String city;
    private String stateCode;
    private String postalCode;
    private String countryCode;
    private String telecomAddress;
    private List<StudySite> studySites = new ArrayList<StudySite>();
    /**
     *
     * @return nciInstituteCode
     */
    @Column(name = "NCI_INSTITUTE_CODE")
    public String getNciInstituteCode() {
        return nciInstituteCode;
    }
    /**
     *
     * @param nciInstituteCode nciInstituteCode
     */
    public void setNciInstituteCode(String nciInstituteCode) {
        this.nciInstituteCode = nciInstituteCode;
    }
    /**
     *
     * @return name
     */

    public String getName() {
        return name;
    }
    /**
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     *
     * @return streetAddress
     */
    @Column(name = "STREET_ADDRESS")
    public String getStreetAddress() {
        return streetAddress;
    }
    /**
     *
     * @param streetAddress streetAddress
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    /**
     *
     * @return city
     */
    public String getCity() {
        return city;
    }
    /**
     *
     * @param city city
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     *
     * @return stateCode
     */
    @Column(name = "STATE_CODE")
    public String getStateCode() {
        return stateCode;
    }
    /**
     *
     * @param stateCode stateCode
     */
     public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
    /**
     *
     * @return postalCode
     */
    @Column(name = "POSTAL_CODE")
    public String getPostalCode() {
        return postalCode;
    }
    /**
     *
     * @param postalCode postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     *
     * @return countryCode
     */
    @Column(name = "COUNTRY_CODE")
    public String getCountryCode() {
        return countryCode;
    }
    /**
     *
     * @param countryCode countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     *
     * @return telecomAddress
     */
    @Column(name = "TELECOM_ADDRESS")
    public String getTelecomAddress() {
        return telecomAddress;
    }
    /**
     *
     * @param telecomAddress telecomAddress
     */
    public void setTelecomAddress(String telecomAddress) {
        this.telecomAddress = telecomAddress;
    }
    /**
     *
     * @return studySites studySites
     */
    @OneToMany(mappedBy = "healthcareSite")
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


}
