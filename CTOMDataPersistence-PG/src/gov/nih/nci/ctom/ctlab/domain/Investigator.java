package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class Investigator {

	private Long id;
	private String lastName;
	private String firstName;
	private String middleNAle;
	private Date birthDt;
	private String birthDtOrg;
	private String telecomAddr;
	private String admGenderCode;
	private String streetAddr;
	private String city;
	private String state;
	private String zipCode;
	private String countryCode;
	private String phone;
	private String eduLevelCode;
	private String ethnicGrpCode;
	private String houseHldIncCode;
	private String maritalStatCode;
	private String raceCode;
	private String empStatCode;
	private String empStatCodeOthTxt;
	private String nciId;
	private String initials;
	private Long admGndrConceptDescId; 
	private Long raceConceptDescpId; 
	private int securityKey;
	private String source; 
	private Date srcExtractDt; 
	private Date ctomInsertDt; 
	private Date ctomUpdateDt;
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
	 * @return the middleNAle
	 */
	public String getMiddleNAle() {
		return middleNAle;
	}
	/**
	 * @param middleNAle the middleNAle to set
	 */
	public void setMiddleNAle(String middleNAle) {
		this.middleNAle = middleNAle;
	}
	/**
	 * @return the birthDt
	 */
	public Date getBirthDt() {
		return birthDt;
	}
	/**
	 * @param birthDt the birthDt to set
	 */
	public void setBirthDt(Date birthDt) {
		this.birthDt = birthDt;
	}
	/**
	 * @return the birthDtOrg
	 */
	public String getBirthDtOrg() {
		return birthDtOrg;
	}
	/**
	 * @param birthDtOrg the birthDtOrg to set
	 */
	public void setBirthDtOrg(String birthDtOrg) {
		this.birthDtOrg = birthDtOrg;
	}
	/**
	 * @return the telecomAddr
	 */
	public String getTelecomAddr() {
		return telecomAddr;
	}
	/**
	 * @param telecomAddr the telecomAddr to set
	 */
	public void setTelecomAddr(String telecomAddr) {
		this.telecomAddr = telecomAddr;
	}
	/**
	 * @return the admGenderCode
	 */
	public String getAdmGenderCode() {
		return admGenderCode;
	}
	/**
	 * @param admGenderCode the admGenderCode to set
	 */
	public void setAdmGenderCode(String admGenderCode) {
		this.admGenderCode = admGenderCode;
	}
	/**
	 * @return the streetAddr
	 */
	public String getStreetAddr() {
		return streetAddr;
	}
	/**
	 * @param streetAddr the streetAddr to set
	 */
	public void setStreetAddr(String streetAddr) {
		this.streetAddr = streetAddr;
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
	 * @return the eduLevelCode
	 */
	public String getEduLevelCode() {
		return eduLevelCode;
	}
	/**
	 * @param eduLevelCode the eduLevelCode to set
	 */
	public void setEduLevelCode(String eduLevelCode) {
		this.eduLevelCode = eduLevelCode;
	}
	/**
	 * @return the ethnicGrpCode
	 */
	public String getEthnicGrpCode() {
		return ethnicGrpCode;
	}
	/**
	 * @param ethnicGrpCode the ethnicGrpCode to set
	 */
	public void setEthnicGrpCode(String ethnicGrpCode) {
		this.ethnicGrpCode = ethnicGrpCode;
	}
	/**
	 * @return the houseHldIncCode
	 */
	public String getHouseHldIncCode() {
		return houseHldIncCode;
	}
	/**
	 * @param houseHldIncCode the houseHldIncCode to set
	 */
	public void setHouseHldIncCode(String houseHldIncCode) {
		this.houseHldIncCode = houseHldIncCode;
	}
	/**
	 * @return the maritalStatCode
	 */
	public String getMaritalStatCode() {
		return maritalStatCode;
	}
	/**
	 * @param maritalStatCode the maritalStatCode to set
	 */
	public void setMaritalStatCode(String maritalStatCode) {
		this.maritalStatCode = maritalStatCode;
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
	 * @return the empStatCode
	 */
	public String getEmpStatCode() {
		return empStatCode;
	}
	/**
	 * @param empStatCode the empStatCode to set
	 */
	public void setEmpStatCode(String empStatCode) {
		this.empStatCode = empStatCode;
	}
	/**
	 * @return the empStatCodeOthTxt
	 */
	public String getEmpStatCodeOthTxt() {
		return empStatCodeOthTxt;
	}
	/**
	 * @param empStatCodeOthTxt the empStatCodeOthTxt to set
	 */
	public void setEmpStatCodeOthTxt(String empStatCodeOthTxt) {
		this.empStatCodeOthTxt = empStatCodeOthTxt;
	}
	/**
	 * @return the nciId
	 */
	public String getNciId() {
		return nciId;
	}
	/**
	 * @param nciId the nciId to set
	 */
	public void setNciId(String nciId) {
		this.nciId = nciId;
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
	 * @return the raceConceptDescpId
	 */
	public Long getRaceConceptDescpId() {
		return raceConceptDescpId;
	}
	/**
	 * @param raceConceptDescpId the raceConceptDescpId to set
	 */
	public void setRaceConceptDescpId(Long raceConceptDescpId) {
		this.raceConceptDescpId = raceConceptDescpId;
	}
	/**
	 * @return the securityKey
	 */
	public int getSecurityKey() {
		return securityKey;
	}
	/**
	 * @param securityKey the securityKey to set
	 */
	public void setSecurityKey(int securityKey) {
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
	 * @return the srcExtractDt
	 */
	public Date getSrcExtractDt() {
		return srcExtractDt;
	}
	/**
	 * @param srcExtractDt the srcExtractDt to set
	 */
	public void setSrcExtractDt(Date srcExtractDt) {
		this.srcExtractDt = srcExtractDt;
	}
	/**
	 * @return the ctomInsertDt
	 */
	public Date getCtomInsertDt() {
		return ctomInsertDt;
	}
	/**
	 * @param ctomInsertDt the ctomInsertDt to set
	 */
	public void setCtomInsertDt(Date ctomInsertDt) {
		this.ctomInsertDt = ctomInsertDt;
	}
	/**
	 * @return the ctomUpdateDt
	 */
	public Date getCtomUpdateDt() {
		return ctomUpdateDt;
	}
	/**
	 * @param ctomUpdateDt the ctomUpdateDt to set
	 */
	public void setCtomUpdateDt(Date ctomUpdateDt) {
		this.ctomUpdateDt = ctomUpdateDt;
	}

}
