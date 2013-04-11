/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.dto;

import gov.nih.nci.lv.domain.Investigator;

/**
 * dto for InvestigatorDto.
 * @author NAmiruddin
 *
 */
public class InvestigatorDto extends AbstractDto<InvestigatorDto> {
    private String nciIdentifier;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String city;
    private String stateCode;
    private String postalCode;
    private String countryCode;
    private String telecomAddress;

    /**
     *
     */
    public InvestigatorDto() {
    }


    /**
     *
     * @param investigator investigator
     */

    public InvestigatorDto(Investigator investigator) {
        super();
        this.setId(investigator.getId());
        this.setFirstName(investigator.getFirstName());
        this.setLastName(investigator.getLastName());
        this.setCity(investigator.getCity());
        this.setCountryCode(investigator.getCountryCode());
        this.setNciIdentifier(investigator.getNciIdentifier());
        this.setPostalCode(investigator.getPostalCode());
        this.setStreetAddress(investigator.getStreetAddress());
        this.setTelecomAddress(investigator.getTelecomAddress());
        this.setCtomUpdateDate(investigator.getCtomUpdateDate());
    }




    /**
     *
     * @return nciIdentifier
     */
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
     * @return firstName firstName
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     *
     * @param firstName firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return LastName
     */
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




}
