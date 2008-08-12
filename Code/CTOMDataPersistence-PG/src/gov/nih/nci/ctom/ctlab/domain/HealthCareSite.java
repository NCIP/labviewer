package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class HealthCareSite {
	
	private Long id;
	private String name;
	private String descpTxt;
	private String statusCode;
	private Date statusDt;
	private String statusDtOrg;
	private String streetAddr;
	private String city;
	private String stateCode;
	private String postalCode;
	private String countryCode;
	private String telecomAddr;
	private String nciInstituteCd;
    private String source;
    private Date srcExtractDt;
    private Date ctomInsertDt;
    private Date ctomUpdateDt;
	private StudyParticipantAssignment studyParticipantAssignment=null; 

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the descpTxt
	 */
	public String getDescpTxt() {
		return descpTxt;
	}

	/**
	 * @param descpTxt the descpTxt to set
	 */
	public void setDescpTxt(String descpTxt) {
		this.descpTxt = descpTxt;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the statusDt
	 */
	public Date getStatusDt() {
		return statusDt;
	}

	/**
	 * @param statusDt the statusDt to set
	 */
	public void setStatusDt(Date statusDt) {
		this.statusDt = statusDt;
	}

	/**
	 * @return the statusDtOrg
	 */
	public String getStatusDtOrg() {
		return statusDtOrg;
	}

	/**
	 * @param statusDtOrg the statusDtOrg to set
	 */
	public void setStatusDtOrg(String statusDtOrg) {
		this.statusDtOrg = statusDtOrg;
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
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
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
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long hId) {
		this.id = hId;
	}

	/**
	 * @return the studyParticipantAssignment
	 */
	public StudyParticipantAssignment getStudyParticipantAssignment() {
		return studyParticipantAssignment;
	}

	/**
	 * @param studyParticipantAssignment the studyParticipantAssignment to set
	 */
	public void setStudyParticipantAssignment(
			StudyParticipantAssignment studyParticipantAssignment) {
		this.studyParticipantAssignment = studyParticipantAssignment;
	}

	/**
	 * @return the nciInstituteCd
	 */
	public String getNciInstituteCd() {
		return nciInstituteCd;
	}

	/**
	 * @param nciInstituteCd the nciInstituteCd to set
	 */
	public void setNciInstituteCd(String nciInstituteCd) {
		this.nciInstituteCd = nciInstituteCd;
	}
}
