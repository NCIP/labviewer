package gov.nih.nci.ctom.ctlab.domain;

public class StudyTimePoint {
	
	private Long id;
	private String visitName = null;
	private Long activityId;
	
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
