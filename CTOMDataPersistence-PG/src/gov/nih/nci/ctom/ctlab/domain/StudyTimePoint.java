package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class StudyTimePoint {
	
	private Long id;
	private String visitName = null;
	private Long activityId;
	private Long courseNum;
	private String courseNumOrg;
	private String epochName;
	private Date courseStartDt;
	private String courseStartDtOrg;
	private Date courseStopDt;
	private String courseStopDtOrg;
	private int securityKey;
	private String source;
	private Date srcExtractDt;
	private Date ctomInsertDt;
	private Date ctomUpdateDt;
	/**
	 * @return the courseNum
	 */
	public Long getCourseNum() {
		return courseNum;
	}

	/**
	 * @param courseNum the courseNum to set
	 */
	public void setCourseNum(Long courseNum) {
		this.courseNum = courseNum;
	}

	/**
	 * @return the courseNumOrg
	 */
	public String getCourseNumOrg() {
		return courseNumOrg;
	}

	/**
	 * @param courseNumOrg the courseNumOrg to set
	 */
	public void setCourseNumOrg(String courseNumOrg) {
		this.courseNumOrg = courseNumOrg;
	}

	/**
	 * @return the epochName
	 */
	public String getEpochName() {
		return epochName;
	}

	/**
	 * @param epochName the epochName to set
	 */
	public void setEpochName(String epochName) {
		this.epochName = epochName;
	}

	/**
	 * @return the courseStartDt
	 */
	public Date getCourseStartDt() {
		return courseStartDt;
	}

	/**
	 * @param courseStartDt the courseStartDt to set
	 */
	public void setCourseStartDt(Date courseStartDt) {
		this.courseStartDt = courseStartDt;
	}

	/**
	 * @return the courseStartDtOrg
	 */
	public String getCourseStartDtOrg() {
		return courseStartDtOrg;
	}

	/**
	 * @param courseStartDtOrg the courseStartDtOrg to set
	 */
	public void setCourseStartDtOrg(String courseStartDtOrg) {
		this.courseStartDtOrg = courseStartDtOrg;
	}

	/**
	 * @return the courseStopDt
	 */
	public Date getCourseStopDt() {
		return courseStopDt;
	}

	/**
	 * @param courseStopDt the courseStopDt to set
	 */
	public void setCourseStopDt(Date courseStopDt) {
		this.courseStopDt = courseStopDt;
	}

	/**
	 * @return the courseStopDtOrg
	 */
	public String getCourseStopDtOrg() {
		return courseStopDtOrg;
	}

	/**
	 * @param courseStopDtOrg the courseStopDtOrg to set
	 */
	public void setCourseStopDtOrg(String courseStopDtOrg) {
		this.courseStopDtOrg = courseStopDtOrg;
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
	 * @return the visitName
	 */
	public String getVisitName() {
		return visitName;
	}

	/**
	 * @param visitName the visitName to set
	 */
	public void setVisitName(String visitName) {
		this.visitName = visitName;
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
}
