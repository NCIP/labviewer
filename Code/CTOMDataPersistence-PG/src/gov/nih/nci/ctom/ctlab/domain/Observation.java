package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class Observation {

	private Long id;
	private String statusCode = null;
	private Date reportingDate = null;
	private String reportingDateOrig = null;
	private String confidentialityCode = null;
	private String comments = null;
	private Long activityId;
	private ClinicalResult clinicalResult= null;
	private String uncertainityCode=null;
	private String subtypeCode=null;
	private int securityKey;
	private String source;
	private Date srcExtractDt=null;
	private Date ctomInsertDt=null;
	private Date ctomUpdateDt=null;
	/**
	 * @return the uncertainityCode
	 */
	public String getUncertainityCode() {
		return uncertainityCode;
	}
	/**
	 * @param uncertainityCode the uncertainityCode to set
	 */
	public void setUncertainityCode(String uncertainityCode) {
		this.uncertainityCode = uncertainityCode;
	}
	/**
	 * @return the subtypeCode
	 */
	public String getSubtypeCode() {
		return subtypeCode;
	}
	/**
	 * @param subtypeCode the subtypeCode to set
	 */
	public void setSubtypeCode(String subtypeCode) {
		this.subtypeCode = subtypeCode;
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
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the confidentialityCode
	 */
	public String getConfidentialityCode() {
		return confidentialityCode;
	}
	/**
	 * @param confidentialityCode the confidentialityCode to set
	 */
	public void setConfidentialityCode(String confidentialityCode) {
		this.confidentialityCode = confidentialityCode;
	}
	/**
	 * @return the reportingDate
	 */
	public Date getReportingDate() {
		return reportingDate;
	}
	/**
	 * @param reportingDate the reportingDate to set
	 */
	public void setReportingDate(Date reportingDate) {
		this.reportingDate = reportingDate;
	}
	/**
	 * @return the reportingDateOrig
	 */
	public String getReportingDateOrig() {
		return reportingDateOrig;
	}
	/**
	 * @param reportingDateOrig the reportingDateOrig to set
	 */
	public void setReportingDateOrig(String reportingDateOrig) {
		this.reportingDateOrig = reportingDateOrig;
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
	 * @return the activityId
	 */
	public Long getActivityId() {
		return activityId;
	}
	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
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
	 * @return the clinicalResult
	 */
	public ClinicalResult getClinicalResult() {
		return clinicalResult;
	}
	/**
	 * @param clinicalResult the clinicalResult to set
	 */
	public void setClinicalResult(ClinicalResult clinicalResult) {
		this.clinicalResult = clinicalResult;
	}

	
}
