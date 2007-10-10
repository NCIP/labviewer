package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class Participant {

	private Long id;
	private String initials = null;
	private String lastName = null;
	private String firstName = null;
	private String adminGenderCode = null;
	private String adminGenderCodeSystem = null;
	private String adminGenderCodeSystemName = null;
	private String ethnicGroupCode;
	private Date birthDate = null;
	private String birthDateOrig = null;
	private String raceCode;
	private String raceCodeSystem;
	private String raceCodeSystemName;
	private String postalCode;
	private String countryCode;
	private String maritalStatusCode;
	
	/**
	 * @return the adminGenderCode
	 */
	public String getAdminGenderCode() {
		return adminGenderCode;
	}
	/**
	 * @param adminGenderCode the adminGenderCode to set
	 */
	public void setAdminGenderCode(String adminGenderCode) {
		this.adminGenderCode = adminGenderCode;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the initials
	 */
	public String getInitials() {
		return initials;
	}
	/**
	 * @param initials the initials to set
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}
	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	/**
	 * @return the birthDateOrig
	 */
	public String getBirthDateOrig() {
		return birthDateOrig;
	}
	/**
	 * @param birthDateOrig the birthDateOrig to set
	 */
	public void setBirthDateOrig(String birthDateOrig) {
		this.birthDateOrig = birthDateOrig;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the raceCode
	 */
	public String getRaceCode() {
		return raceCode;
	}
	/**
	 * @param raceCode the raceCode to set
	 */
	public void setRaceCode(String raceCode) {
		this.raceCode = raceCode;
	}
	/**
	 * @return the adminGenderCodeSystem
	 */
	public String getAdminGenderCodeSystem() {
		return adminGenderCodeSystem;
	}
	/**
	 * @param adminGenderCodeSystem the adminGenderCodeSystem to set
	 */
	public void setAdminGenderCodeSystem(String adminGenderCodeSystem) {
		this.adminGenderCodeSystem = adminGenderCodeSystem;
	}
	/**
	 * @return the adminGenderCodeSystemName
	 */
	public String getAdminGenderCodeSystemName() {
		return adminGenderCodeSystemName;
	}
	/**
	 * @param adminGenderCodeSystemName the adminGenderCodeSystemName to set
	 */
	public void setAdminGenderCodeSystemName(String adminGenderCodeSystemName) {
		this.adminGenderCodeSystemName = adminGenderCodeSystemName;
	}
	/**
	 * @return the raceCodeSystem
	 */
	public String getRaceCodeSystem() {
		return raceCodeSystem;
	}
	/**
	 * @param raceCodeSystem the raceCodeSystem to set
	 */
	public void setRaceCodeSystem(String raceCodeSystem) {
		this.raceCodeSystem = raceCodeSystem;
	}
	/**
	 * @return the raceCodeSystemName
	 */
	public String getRaceCodeSystemName() {
		return raceCodeSystemName;
	}
	/**
	 * @param raceCodeSystemName the raceCodeSystemName to set
	 */
	public void setRaceCodeSystemName(String raceCodeSystemName) {
		this.raceCodeSystemName = raceCodeSystemName;
	}
	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @return the maritalStatusCode
	 */
	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}
	/**
	 * @param maritalStatusCode the maritalStatusCode to set
	 */
	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}
	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String zipCode) {
		this.postalCode = zipCode;
	}
	/**
	 * @return the ethnicGroupCode
	 */
	public String getEthnicGroupCode() {
		return ethnicGroupCode;
	}
	/**
	 * @param ethnicGroupCode the ethnicGroupCode to set
	 */
	public void setEthnicGroupCode(String ethnicGroupCode) {
		this.ethnicGroupCode = ethnicGroupCode;
	}

}
