package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class AdverseEvent {

	
	
	private String ctcTermTypeCd = null;
	private String ctcTermTypeCdSystem = null;
	private String ctcGradeCd = null;
	private String ctcGradeCdSystem = null;
	
	private Long id;
	private Date onsetDt;
	private String onsetDtOrg;
	private Date resolvedDt;
	private String resolvedDtOrg;
	private String ctcCatCode;
	private String ctcCatCodeSyst; 
	private String ctcAttCode;
	private String ctcAttCodeSyst;
	private String ctcCode;
	private String ctcCodeSyst;
	private String seriousReasonCode;
	private String outcomeCode;
	private String actionTakenCode;
	private String condPatternCode;
	private String doseLimitToxInd;
	private String doseLimitToxDescTxt;
	private String filedInd;
	private String source;
	private Date srcExtractDt;
	private Date ctomInsertDt;
	private Date ctomUpdateDt;
	

	/**
	 * @return the ctcGradeCd
	 */
	public String getCtcGradeCd() {
		return ctcGradeCd;
	}
	/**
	 * @param ctcGradeCd the ctcGradeCd to set
	 */
	public void setCtcGradeCd(String ctcGradeCd) {
		this.ctcGradeCd = ctcGradeCd;
	}
	/**
	 * @return the ctcGradeCdSystem
	 */
	public String getCtcGradeCdSystem() {
		return ctcGradeCdSystem;
	}
	/**
	 * @param ctcGradeCdSystem the ctcGradeCdSystem to set
	 */
	public void setCtcGradeCdSystem(String ctcGradeCdSystem) {
		this.ctcGradeCdSystem = ctcGradeCdSystem;
	}
	/**
	 * @return the ctcTermTypeCd
	 */
	public String getCtcTermTypeCd() {
		return ctcTermTypeCd;
	}
	/**
	 * @param ctcTermTypeCd the ctcTermTypeCd to set
	 */
	public void setCtcTermTypeCd(String ctcTermTypeCd) {
		this.ctcTermTypeCd = ctcTermTypeCd;
	}
	/**
	 * @return the ctcTermTypeCdSystem
	 */
	public String getCtcTermTypeCdSystem() {
		return ctcTermTypeCdSystem;
	}
	/**
	 * @param ctcTermTypeCdSystem the ctcTermTypeCdSystem to set
	 */
	public void setCtcTermTypeCdSystem(String ctcTermTypeCdSystem) {
		this.ctcTermTypeCdSystem = ctcTermTypeCdSystem;
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
	 * @return the onsetDt
	 */
	public Date getOnsetDt() {
		return onsetDt;
	}
	/**
	 * @param onsetDt the onsetDt to set
	 */
	public void setOnsetDt(Date onsetDt) {
		this.onsetDt = onsetDt;
	}
	/**
	 * @return the onsetDtOrg
	 */
	public String getOnsetDtOrg() {
		return onsetDtOrg;
	}
	/**
	 * @param onsetDtOrg the onsetDtOrg to set
	 */
	public void setOnsetDtOrg(String onsetDtOrg) {
		this.onsetDtOrg = onsetDtOrg;
	}
	/**
	 * @return the resolvedDt
	 */
	public Date getResolvedDt() {
		return resolvedDt;
	}
	/**
	 * @param resolvedDt the resolvedDt to set
	 */
	public void setResolvedDt(Date resolvedDt) {
		this.resolvedDt = resolvedDt;
	}
	/**
	 * @return the resolvedDtOrg
	 */
	public String getResolvedDtOrg() {
		return resolvedDtOrg;
	}
	/**
	 * @param resolvedDtOrg the resolvedDtOrg to set
	 */
	public void setResolvedDtOrg(String resolvedDtOrg) {
		this.resolvedDtOrg = resolvedDtOrg;
	}
	/**
	 * @return the ctcCatCode
	 */
	public String getCtcCatCode() {
		return ctcCatCode;
	}
	/**
	 * @param ctcCatCode the ctcCatCode to set
	 */
	public void setCtcCatCode(String ctcCatCode) {
		this.ctcCatCode = ctcCatCode;
	}
	/**
	 * @return the ctcCatCodeSyst
	 */
	public String getCtcCatCodeSyst() {
		return ctcCatCodeSyst;
	}
	/**
	 * @param ctcCatCodeSyst the ctcCatCodeSyst to set
	 */
	public void setCtcCatCodeSyst(String ctcCatCodeSyst) {
		this.ctcCatCodeSyst = ctcCatCodeSyst;
	}
	/**
	 * @return the ctcAttCode
	 */
	public String getCtcAttCode() {
		return ctcAttCode;
	}
	/**
	 * @param ctcAttCode the ctcAttCode to set
	 */
	public void setCtcAttCode(String ctcAttCode) {
		this.ctcAttCode = ctcAttCode;
	}
	/**
	 * @return the ctcAttCodeSyst
	 */
	public String getCtcAttCodeSyst() {
		return ctcAttCodeSyst;
	}
	/**
	 * @param ctcAttCodeSyst the ctcAttCodeSyst to set
	 */
	public void setCtcAttCodeSyst(String ctcAttCodeSyst) {
		this.ctcAttCodeSyst = ctcAttCodeSyst;
	}
	/**
	 * @return the ctcCode
	 */
	public String getCtcCode() {
		return ctcCode;
	}
	/**
	 * @param ctcCode the ctcCode to set
	 */
	public void setCtcCode(String ctcCode) {
		this.ctcCode = ctcCode;
	}
	/**
	 * @return the ctcCodeSyst
	 */
	public String getCtcCodeSyst() {
		return ctcCodeSyst;
	}
	/**
	 * @param ctcCodeSyst the ctcCodeSyst to set
	 */
	public void setCtcCodeSyst(String ctcCodeSyst) {
		this.ctcCodeSyst = ctcCodeSyst;
	}
	/**
	 * @return the seriousReasonCode
	 */
	public String getSeriousReasonCode() {
		return seriousReasonCode;
	}
	/**
	 * @param seriousReasonCode the seriousReasonCode to set
	 */
	public void setSeriousReasonCode(String seriousReasonCode) {
		this.seriousReasonCode = seriousReasonCode;
	}
	/**
	 * @return the outcomeCode
	 */
	public String getOutcomeCode() {
		return outcomeCode;
	}
	/**
	 * @param outcomeCode the outcomeCode to set
	 */
	public void setOutcomeCode(String outcomeCode) {
		this.outcomeCode = outcomeCode;
	}
	/**
	 * @return the actionTakenCode
	 */
	public String getActionTakenCode() {
		return actionTakenCode;
	}
	/**
	 * @param actionTakenCode the actionTakenCode to set
	 */
	public void setActionTakenCode(String actionTakenCode) {
		this.actionTakenCode = actionTakenCode;
	}
	/**
	 * @return the condPatternCode
	 */
	public String getCondPatternCode() {
		return condPatternCode;
	}
	/**
	 * @param condPatternCode the condPatternCode to set
	 */
	public void setCondPatternCode(String condPatternCode) {
		this.condPatternCode = condPatternCode;
	}
	/**
	 * @return the doseLimitToxInd
	 */
	public String getDoseLimitToxInd() {
		return doseLimitToxInd;
	}
	/**
	 * @param doseLimitToxInd the doseLimitToxInd to set
	 */
	public void setDoseLimitToxInd(String doseLimitToxInd) {
		this.doseLimitToxInd = doseLimitToxInd;
	}
	/**
	 * @return the doseLimitToxDescTxt
	 */
	public String getDoseLimitToxDescTxt() {
		return doseLimitToxDescTxt;
	}
	/**
	 * @param doseLimitToxDescTxt the doseLimitToxDescTxt to set
	 */
	public void setDoseLimitToxDescTxt(String doseLimitToxDescTxt) {
		this.doseLimitToxDescTxt = doseLimitToxDescTxt;
	}
	/**
	 * @return the filedInd
	 */
	public String getFiledInd() {
		return filedInd;
	}
	/**
	 * @param filedInd the filedInd to set
	 */
	public void setFiledInd(String filedInd) {
		this.filedInd = filedInd;
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
