package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class Activity {

	private Long id;
	private String name=null;
	private String type =null;
	private String descTxt =null;
	
	private Date startDate = null;
	private String startDateOrig = null;
	private Date stopDate = null;
	private String stopDateOrig = null;
	private Long durationValue=null;
	private String durationValueOrg=null;
	private String durationUOMCode =null;
	private String plannedInd =null;
	private String reasonCode =  null;
	private String ageAtVisit = null;
	private String ageAtVisitUnits = null;
	private String plannedDuration =null;
	private String plannedDurDesc=null;
	private String subTypeCode=null;
	private Long studyParticipantAssignId;
	private Long securityKey;
	private String source=null;
	private Date sourceExtractDate=null;
	private Date ctomInsertDate=null;
	private Date ctomUpdateDate=null;
	
	private Observation observation=null;
	private Procedure procedure=null;
	private StudyTimePoint studyTimePoint=null;
	
	/**
	 * @return the ageAtVisit
	 */
	public String getAgeAtVisit() {
		return ageAtVisit;
	}
	/**
	 * @param ageAtVisit the ageAtVisit to set
	 */
	public void setAgeAtVisit(String ageAtVisit) {
		this.ageAtVisit = ageAtVisit;
	}
	/**
	 * @return the ageAtVisitUnits
	 */
	public String getAgeAtVisitUnits() {
		return ageAtVisitUnits;
	}
	/**
	 * @param ageAtVisitUnits the ageAtVisitUnits to set
	 */
	public void setAgeAtVisitUnits(String ageAtVisitUnits) {
		this.ageAtVisitUnits = ageAtVisitUnits;
	}
	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}
	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the startDateOrig
	 */
	public String getStartDateOrig() {
		return startDateOrig;
	}
	/**
	 * @param startDateOrig the startDateOrig to set
	 */
	public void setStartDateOrig(String startDateOrig) {
		this.startDateOrig = startDateOrig;
	}
	/**
	 * @return the stopDate
	 */
	public Date getStopDate() {
		return stopDate;
	}
	/**
	 * @param stopDate the stopDate to set
	 */
	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}
	/**
	 * @return the stopDateOrig
	 */
	public String getStopDateOrig() {
		return stopDateOrig;
	}
	/**
	 * @param stopDateOrig the stopDateOrig to set
	 */
	public void setStopDateOrig(String stopDateOrig) {
		this.stopDateOrig = stopDateOrig;
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
	 * @return the observation
	 */
	public Observation getObservation() {
		return observation;
	}
	/**
	 * @param observation the observation to set
	 */
	public void setObservation(Observation observation) {
		this.observation = observation;
	}
	/**
	 * @return the procedure
	 */
	public Procedure getProcedure() {
		return procedure;
	}
	/**
	 * @param procedure the procedure to set
	 */
	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}
	/**
	 * @return the studyTimePoint
	 */
	public StudyTimePoint getStudyTimePoint() {
		return studyTimePoint;
	}
	/**
	 * @param studyTimePoint the studyTimePoint to set
	 */
	public void setStudyTimePoint(StudyTimePoint studyTimePoint) {
		this.studyTimePoint = studyTimePoint;
	}
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the durationValue
	 */
	public Long getDurationValue() {
		return durationValue;
	}
	/**
	 * @param durationValue the durationValue to set
	 */
	public void setDurationValue(Long durationValue) {
		this.durationValue = durationValue;
	}
	/**
	 * @return the durationValueOrg
	 */
	public String getDurationValueOrg() {
		return durationValueOrg;
	}
	/**
	 * @param durationValueOrg the durationValueOrg to set
	 */
	public void setDurationValueOrg(String durationValueOrg) {
		this.durationValueOrg = durationValueOrg;
	}
	/**
	 * @return the durationUOMCode
	 */
	public String getDurationUOMCode() {
		return durationUOMCode;
	}
	/**
	 * @param durationUOMCode the durationUOMCode to set
	 */
	public void setDurationUOMCode(String durationUOMCode) {
		this.durationUOMCode = durationUOMCode;
	}
	/**
	 * @return the plannedInd
	 */
	public String getPlannedInd() {
		return plannedInd;
	}
	/**
	 * @param plannedInd the plannedInd to set
	 */
	public void setPlannedInd(String plannedInd) {
		this.plannedInd = plannedInd;
	}
	/**
	 * @return the plannedDuration
	 */
	public String getPlannedDuration() {
		return plannedDuration;
	}
	/**
	 * @param plannedDuration the plannedDuration to set
	 */
	public void setPlannedDuration(String plannedDuration) {
		this.plannedDuration = plannedDuration;
	}
	/**
	 * @return the plannedDurDesc
	 */
	public String getPlannedDurDesc() {
		return plannedDurDesc;
	}
	/**
	 * @param plannedDurDesc the plannedDurDesc to set
	 */
	public void setPlannedDurDesc(String plannedDurDesc) {
		this.plannedDurDesc = plannedDurDesc;
	}
	/**
	 * @return the subTypeCode
	 */
	public String getSubTypeCode() {
		return subTypeCode;
	}
	/**
	 * @param subTypeCode the subTypeCode to set
	 */
	public void setSubTypeCode(String subTypeCode) {
		this.subTypeCode = subTypeCode;
	}
	/**
	 * @return the studyParticipantAssignId
	 */
	public Long getStudyParticipantAssignId() {
		return studyParticipantAssignId;
	}
	/**
	 * @param studyParticipantAssignId the studyParticipantAssignId to set
	 */
	public void setStudyParticipantAssignId(Long studyParticipantAssignId) {
		this.studyParticipantAssignId = studyParticipantAssignId;
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

	
}
