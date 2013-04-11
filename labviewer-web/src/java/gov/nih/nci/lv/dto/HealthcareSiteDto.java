/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.dto;

import gov.nih.nci.lv.domain.HealthcareSite;


/**
 * dto for healthcaresite.
 * @author NAmiruddin
 *
 */
public class HealthcareSiteDto extends AbstractDto<HealthcareSiteDto> {

    private String nciInstituteCode;
    private String name;
    private String streetAddress;
    private String city;
    private String stateCode;
    private String postalCode;
    private String countryCode;
    private String telecomAddress;

    /**
     *
     */
    public HealthcareSiteDto() {
    }


    /**
     *
     * @param healthcareSite healthcareSite
     */
    public HealthcareSiteDto(HealthcareSite healthcareSite) {
        super();
        this.setId(healthcareSite.getId());
        this.setName(healthcareSite.getName());
        this.setCity(healthcareSite.getCity());
        this.setCountryCode(healthcareSite.getCountryCode());
        this.setNciInstituteCode(healthcareSite.getNciInstituteCode());
        this.setPostalCode(healthcareSite.getPostalCode());
        this.setStreetAddress(healthcareSite.getStreetAddress());
        this.setTelecomAddress(healthcareSite.getTelecomAddress());
        this.setCtomUpdateDate(healthcareSite.getCtomUpdateDate());
    }

    /**
     *
     * @return nciInstituteCode
     */
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
