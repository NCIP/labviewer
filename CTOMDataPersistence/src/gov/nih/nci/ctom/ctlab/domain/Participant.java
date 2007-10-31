package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class Participant {

	private Long id;
	private String initials = null;
	private String lastName = null;
	private String firstName = null;
	private String middleName = null;
	
	private Date birthDate = null;
	private String birthDateOrig = null;
	private String adminGenderCode = null;
	
	private String telecomAddress=null;
	private String streetAddress = null;
	private String city = null;
	private String state = null;
	private String zipCode = null;
	private String countryCode;
	private String phone = null;
	
	private String educationLevelCode =null;
	
	private String ethnicGroupCode=null;
	private String householdIncomeCode=null;
	private String maritalStatusCode;
	private String raceCode;
	private String employmentStatusCode = null;
	private String employmentStatusOtherTxt = null;
	
	private String paymentMethodCode=null;
	private String confidentialityInd=null;
	
	private Long admGndrConceptDescId=null;
	private Long raceConceptDescId=null;
	
	private Long securityKey=null;
	private String source=null;
	private Date sourceExtractDate =null;
	private Date ctomInsertDate=null;
	private Date ctomUpdateDate=null;
	
	private Identifier identifier;
	
	public Participant(){
		identifier = new Identifier();
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
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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
	 * @return the telecomAddress
	 */
	public String getTelecomAddress() {
		return telecomAddress;
	}
	/**
	 * @param telecomAddress the telecomAddress to set
	 */
	public void setTelecomAddress(String telecomAddress) {
		this.telecomAddress = telecomAddress;
	}
	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}
	/**
	 * @param streetAddress the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the educationLevelCode
	 */
	public String getEducationLevelCode() {
		return educationLevelCode;
	}
	/**
	 * @param educationLevelCode the educationLevelCode to set
	 */
	public void setEducationLevelCode(String educationLevelCode) {
		this.educationLevelCode = educationLevelCode;
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
	/**
	 * @return the householdIncomeCode
	 */
	public String getHouseholdIncomeCode() {
		return householdIncomeCode;
	}
	/**
	 * @param householdIncomeCode the householdIncomeCode to set
	 */
	public void setHouseholdIncomeCode(String householdIncomeCode) {
		this.householdIncomeCode = householdIncomeCode;
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
	 * @return the employmentStatusCode
	 */
	public String getEmploymentStatusCode() {
		return employmentStatusCode;
	}
	/**
	 * @param employmentStatusCode the employmentStatusCode to set
	 */
	public void setEmploymentStatusCode(String employmentStatusCode) {
		this.employmentStatusCode = employmentStatusCode;
	}
	/**
	 * @return the employmentStatusOtherTxt
	 */
	public String getEmploymentStatusOtherTxt() {
		return employmentStatusOtherTxt;
	}
	/**
	 * @param employmentStatusOtherTxt the employmentStatusOtherTxt to set
	 */
	public void setEmploymentStatusOtherTxt(String employmentStatusOtherTxt) {
		this.employmentStatusOtherTxt = employmentStatusOtherTxt;
	}
	/**
	 * @return the paymentMethodCode
	 */
	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}
	/**
	 * @param paymentMethodCode the paymentMethodCode to set
	 */
	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}
	/**
	 * @return the confidentialityInd
	 */
	public String getConfidentialityInd() {
		return confidentialityInd;
	}
	/**
	 * @param confidentialityInd the confidentialityInd to set
	 */
	public void setConfidentialityInd(String confidentialityInd) {
		this.confidentialityInd = confidentialityInd;
	}
	/**
	 * @return the admGndrConceptDescId
	 */
	public Long getAdmGndrConceptDescId() {
		return admGndrConceptDescId;
	}
	/**
	 * @param admGndrConceptDescId the admGndrConceptDescId to set
	 */
	public void setAdmGndrConceptDescId(Long admGndrConceptDescId) {
		this.admGndrConceptDescId = admGndrConceptDescId;
	}
	/**
	 * @return the raceConceptDescId
	 */
	public Long getRaceConceptDescId() {
		return raceConceptDescId;
	}
	/**
	 * @param raceConceptDescId the raceConceptDescId to set
	 */
	public void setRaceConceptDescId(Long raceConceptDescId) {
		this.raceConceptDescId = raceConceptDescId;
	}
	/**
	 * @return the securityKey
	 */
	public Long getSecurityKey() {
		return securityKey;
	}
	/**
	 * @param securityKey the securityKey to set
	 */
	public void setSecurityKey(Long securityKey) {
		this.securityKey = securityKey;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the sourceExtractDate
	 */
	public Date getSourceExtractDate() {
		return sourceExtractDate;
	}
	/**
	 * @param sourceExtractDate the sourceExtractDate to set
	 */
	public void setSourceExtractDate(Date sourceExtractDate) {
		this.sourceExtractDate = sourceExtractDate;
	}
	/**
	 * @return the ctomInsertDate
	 */
	public Date getCtomInsertDate() {
		return ctomInsertDate;
	}
	/**
	 * @param ctomInsertDate the ctomInsertDate to set
	 */
	public void setCtomInsertDate(Date ctomInsertDate) {
		this.ctomInsertDate = ctomInsertDate;
	}
	/**
	 * @return the ctomUpdateDate
	 */
	public Date getCtomUpdateDate() {
		return ctomUpdateDate;
	}
	/**
	 * @param ctomUpdateDate the ctomUpdateDate to set
	 */
	public void setCtomUpdateDate(Date ctomUpdateDate) {
		this.ctomUpdateDate = ctomUpdateDate;
	}
	/**
	 * @return the identifier
	 */
	public Identifier getIdentifier() {
		return identifier;
	}
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}
	
	
	
}
