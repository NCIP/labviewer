/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.viewobjects;



public class StudySearchResult
{
	private Integer id;
	private String studyId = "";
	private String longTitle = "";
	private String sponsorCode = "";
	private String phaseCode = "";
	private String status = "";
	private String gridId = "";
	private String details="";
	
	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	public String getStudyId()
	{
		return studyId;
	}

	public void setStudyId(String studyId)
	{
		this.studyId = studyId;
	}

	/**
	 * @return the longTitle
	 */
	public String getLongTitle() {
		return longTitle;
	}

	/**
	 * @param longTitle the longTitle to set
	 */
	public void setLongTitle(String longTitle) {
		this.longTitle = longTitle;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the gridId
	 */
	public String getGridId() {
		return gridId;
	}

	/**
	 * @param gridId the gridId to set
	 */
	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	
		
}
