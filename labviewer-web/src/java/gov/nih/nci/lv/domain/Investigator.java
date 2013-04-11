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

/**
 * Investigator class.
 * @author NAmiruddin
 *
 */
@Entity
public class Investigator extends AbstractDate {

    private String nciIdentifier;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String city;
    private String stateCode;
    private String postalCode;
    private String countryCode;
    private String telecomAddress;
    private List<StudyInvestigator> studyInvestigators = new ArrayList<StudyInvestigator>();

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
     * @return firstName
     */
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }
    /**
     *
     * @param firstName  firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     *
     * @return lastName
     */
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }
    /**
     *
     * @param lastName lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
    @Column(name = "STATE")
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
    @Column(name = "ZIP_CODE")
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
     * @return studyInvestigators
     */
    @OneToMany(mappedBy = "investigator")
    public List<StudyInvestigator> getStudyInvestigators() {
        return studyInvestigators;
    }
    /**
     *
     * @param studyInvestigators studyInvestigators
     */
    public void setStudyInvestigators(List<StudyInvestigator> studyInvestigators) {
        this.studyInvestigators = studyInvestigators;
    }

}
