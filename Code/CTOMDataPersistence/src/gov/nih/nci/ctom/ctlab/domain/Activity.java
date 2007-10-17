package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class Activity {

	private Long id;
	private String reasonCode =  null;
	private Date startDate = null;
	private String startDateOrig = null;
	private Date stopDate = null;
	private String stopDateOrig = null;
	private String ageAtVisit = null;
	private String ageAtVisitUnits = null;
	private Observation observation;
	private Procedure procedure;
	private StudyTimePoint studyTimePoint;
	
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

	
}
