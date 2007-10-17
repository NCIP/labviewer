package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class Observation {

	Long id;
	String statusCode = null;
	Date reportingDate = null;
	String reportingDateOrig = null;
	String confidentialityCode = null;
	String comments = null;
	Long activityId;
	ClinicalResult clinicalResult;
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
