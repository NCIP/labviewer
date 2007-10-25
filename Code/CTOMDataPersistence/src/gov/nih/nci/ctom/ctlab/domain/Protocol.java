package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class Protocol {	
	private Long id;
	private String nciIdentifier = null;
	private Long amendmentId;
	private String amendmentIdOrg;
	private Date amendmentDt;
	private String amendmentDtOrg;
	private String navyNCIId;
	private String longTxtTitle;
	private String shortTxtTitle;
	private String precisTxt;
	private String descTxt;
	private String diseaseCode;
	private String intentCode;
	private String monitorCode;
	private String phaseCode;
	private String sponsorCode;
	private String blindedId;
	private String multiInstId;
	private String randomId;
	private Long targetAccNum;
	private String targetAccNumOrg;
	private String idAssigningAuth;
	private String docURI;
	private String sponsorMontior;
	private int securityKey;
	private String source;
	private Date srcExtractDt;
	private Date ctomInsertDt;
	private Date ctomUpdateDt;
	private String ctomInsertUser;
	private String ctomUpdateUser;
	
	HealthCareSite healthCareSite;
	Investigator investigator;
	
	/**
	 * @return the healthCareSite
	 */
	public HealthCareSite getHealthCareSite() {
		return healthCareSite;
	}
	/**
	 * @param healthCareSite the healthCareSite to set
	 */
	public void setHealthCareSite(HealthCareSite healthCareSite) {
		this.healthCareSite = healthCareSite;
	}
	/**
	 * @return the investigator
	 */
	public Investigator getInvestigator() {
		return investigator;
	}
	/**
	 * @param investigator the investigator to set
	 */
	public void setInvestigator(Investigator investigator) {
		this.investigator = investigator;
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
	 * @return the nciIdentifier
	 */
	public String getNciIdentifier() {
		return nciIdentifier;
	}
	/**
	 * @param nciIdentifier the nciIdentifier to set
	 */
	public void setNciIdentifier(String nciIdentifier) {
		this.nciIdentifier = nciIdentifier;
	}
	/**
	 * @return the amendmentId
	 */
	public Long getAmendmentId() {
		return amendmentId;
	}
	/**
	 * @param amendmentId the amendmentId to set
	 */
	public void setAmendmentId(Long amendmentId) {
		this.amendmentId = amendmentId;
	}
	/**
	 * @return the amendmentIdOrg
	 */
	public String getAmendmentIdOrg() {
		return amendmentIdOrg;
	}
	/**
	 * @param amendmentIdOrg the amendmentIdOrg to set
	 */
	public void setAmendmentIdOrg(String amendmentIdOrg) {
		this.amendmentIdOrg = amendmentIdOrg;
	}
	/**
	 * @return the amendmentDt
	 */
	public Date getAmendmentDt() {
		return amendmentDt;
	}
	/**
	 * @param amendmentDt the amendmentDt to set
	 */
	public void setAmendmentDt(Date amendmentDt) {
		this.amendmentDt = amendmentDt;
	}
	/**
	 * @return the amendmentDtOrg
	 */
	public String getAmendmentDtOrg() {
		return amendmentDtOrg;
	}
	/**
	 * @param amendmentDtOrg the amendmentDtOrg to set
	 */
	public void setAmendmentDtOrg(String amendmentDtOrg) {
		this.amendmentDtOrg = amendmentDtOrg;
	}
	/**
	 * @return the navyNCIId
	 */
	public String getNavyNCIId() {
		return navyNCIId;
	}
	/**
	 * @param navyNCIId the navyNCIId to set
	 */
	public void setNavyNCIId(String navyNCIId) {
		this.navyNCIId = navyNCIId;
	}
	/**
	 * @return the longTxtTitle
	 */
	public String getLongTxtTitle() {
		return longTxtTitle;
	}
	/**
	 * @param longTxtTitle the longTxtTitle to set
	 */
	public void setLongTxtTitle(String longTxtTitle) {
		this.longTxtTitle = longTxtTitle;
	}
	/**
	 * @return the shortTxtTitle
	 */
	public String getShortTxtTitle() {
		return shortTxtTitle;
	}
	/**
	 * @param shortTxtTitle the shortTxtTitle to set
	 */
	public void setShortTxtTitle(String shortTxtTitle) {
		this.shortTxtTitle = shortTxtTitle;
	}
	/**
	 * @return the precisTxt
	 */
	public String getPrecisTxt() {
		return precisTxt;
	}
	/**
	 * @param precisTxt the precisTxt to set
	 */
	public void setPrecisTxt(String precisTxt) {
		this.precisTxt = precisTxt;
	}
	/**
	 * @return the descTxt
	 */
	public String getDescTxt() {
		return descTxt;
	}
	/**
	 * @param descTxt the descTxt to set
	 */
	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}
	/**
	 * @return the diseaseCode
	 */
	public String getDiseaseCode() {
		return diseaseCode;
	}
	/**
	 * @param diseaseCode the diseaseCode to set
	 */
	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}
	/**
	 * @return the intentCode
	 */
	public String getIntentCode() {
		return intentCode;
	}
	/**
	 * @param intentCode the intentCode to set
	 */
	public void setIntentCode(String intentCode) {
		this.intentCode = intentCode;
	}
	/**
	 * @return the monitorCode
	 */
	public String getMonitorCode() {
		return monitorCode;
	}
	/**
	 * @param monitorCode the monitorCode to set
	 */
	public void setMonitorCode(String monitorCode) {
		this.monitorCode = monitorCode;
	}
	/**
	 * @return the phaseCode
	 */
	public String getPhaseCode() {
		return phaseCode;
	}
	/**
	 * @param phaseCode the phaseCode to set
	 */
	public void setPhaseCode(String phaseCode) {
		this.phaseCode = phaseCode;
	}
	/**
	 * @return the sponsorCode
	 */
	public String getSponsorCode() {
		return sponsorCode;
	}
	/**
	 * @param sponsorCode the sponsorCode to set
	 */
	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}
	/**
	 * @return the blindedId
	 */
	public String getBlindedId() {
		return blindedId;
	}
	/**
	 * @param blindedId the blindedId to set
	 */
	public void setBlindedId(String blindedId) {
		this.blindedId = blindedId;
	}
	/**
	 * @return the multiInstId
	 */
	public String getMultiInstId() {
		return multiInstId;
	}
	/**
	 * @param multiInstId the multiInstId to set
	 */
	public void setMultiInstId(String multiInstId) {
		this.multiInstId = multiInstId;
	}
	/**
	 * @return the randomId
	 */
	public String getRandomId() {
		return randomId;
	}
	/**
	 * @param randomId the randomId to set
	 */
	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}
	/**
	 * @return the targetAccNum
	 */
	public Long getTargetAccNum() {
		return targetAccNum;
	}
	/**
	 * @param targetAccNum the targetAccNum to set
	 */
	public void setTargetAccNum(Long targetAccNum) {
		this.targetAccNum = targetAccNum;
	}
	/**
	 * @return the targetAccNumOrg
	 */
	public String getTargetAccNumOrg() {
		return targetAccNumOrg;
	}
	/**
	 * @param targetAccNumOrg the targetAccNumOrg to set
	 */
	public void setTargetAccNumOrg(String targetAccNumOrg) {
		this.targetAccNumOrg = targetAccNumOrg;
	}
	/**
	 * @return the idAssigningAuth
	 */
	public String getIdAssigningAuth() {
		return idAssigningAuth;
	}
	/**
	 * @param idAssigningAuth the idAssigningAuth to set
	 */
	public void setIdAssigningAuth(String idAssigningAuth) {
		this.idAssigningAuth = idAssigningAuth;
	}
	/**
	 * @return the docURI
	 */
	public String getDocURI() {
		return docURI;
	}
	/**
	 * @param docURI the docURI to set
	 */
	public void setDocURI(String docURI) {
		this.docURI = docURI;
	}
	/**
	 * @return the sponsorMontior
	 */
	public String getSponsorMontior() {
		return sponsorMontior;
	}
	/**
	 * @param sponsorMontior the sponsorMontior to set
	 */
	public void setSponsorMontior(String sponsorMontior) {
		this.sponsorMontior = sponsorMontior;
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
	/**
	 * @return the ctomInsertUser
	 */
	public String getCtomInsertUser() {
		return ctomInsertUser;
	}
	/**
	 * @param ctomInsertUser the ctomInsertUser to set
	 */
	public void setCtomInsertUser(String ctomInsertUser) {
		this.ctomInsertUser = ctomInsertUser;
	}
	/**
	 * @return the ctomUpdateUser
	 */
	public String getCtomUpdateUser() {
		return ctomUpdateUser;
	}
	/**
	 * @param ctomUpdateUser the ctomUpdateUser to set
	 */
	public void setCtomUpdateUser(String ctomUpdateUser) {
		this.ctomUpdateUser = ctomUpdateUser;
	}
	
}
