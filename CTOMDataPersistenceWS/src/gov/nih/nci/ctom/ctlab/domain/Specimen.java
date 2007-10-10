package gov.nih.nci.ctom.ctlab.domain;

public class Specimen {

	private String accessionNumber = null;
	private String sampleIdentifierOrig = null;
	private String commentsFromLaboratory = null;
	private String commentsFromInvestigator = null;
	private String condition = null;
	private String sampleTypeCd = null;
	private String sampleTypeCdSystem = null;
	private String sampleTypeCdSystemName = null;
	private Long procedureActivityId;
	private Long id;
	/**
	 * @return the accessionNumber
	 */
	public String getAccessionNumber() {
		return accessionNumber;
	}
	/**
	 * @param accessionNumber the accessionNumber to set
	 */
	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}
	/**
	 * @return the commentsFromInvestigator
	 */
	public String getCommentsFromInvestigator() {
		return commentsFromInvestigator;
	}
	/**
	 * @param commentsFromInvestigator the commentsFromInvestigator to set
	 */
	public void setCommentsFromInvestigator(String commentsFromInvestigator) {
		this.commentsFromInvestigator = commentsFromInvestigator;
	}
	/**
	 * @return the commentsFromLaboratory
	 */
	public String getCommentsFromLaboratory() {
		return commentsFromLaboratory;
	}
	/**
	 * @param commentsFromLaboratory the commentsFromLaboratory to set
	 */
	public void setCommentsFromLaboratory(String commentsFromLaboratory) {
		this.commentsFromLaboratory = commentsFromLaboratory;
	}
	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	/**
	 * @return the sampleIdentifierOrig
	 */
	public String getSampleIdentifierOrig() {
		return sampleIdentifierOrig;
	}
	/**
	 * @param sampleIdentifierOrig the sampleIdentifierOrig to set
	 */
	public void setSampleIdentifierOrig(String sampleIdentifierOrig) {
		this.sampleIdentifierOrig = sampleIdentifierOrig;
	}
	/**
	 * @return the sampleTypeCd
	 */
	public String getSampleTypeCd() {
		return sampleTypeCd;
	}
	/**
	 * @param sampleTypeCd the sampleTypeCd to set
	 */
	public void setSampleTypeCd(String sampleTypeCd) {
		this.sampleTypeCd = sampleTypeCd;
	}
	/**
	 * @return the procedureActivityId
	 */
	public Long getProcedureActivityId() {
		return procedureActivityId;
	}
	/**
	 * @param procedureActivityId the procedureActivityId to set
	 */
	public void setProcedureActivityId(Long procedureActivityId) {
		this.procedureActivityId = procedureActivityId;
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
	 * @return the sampleTypeCdSystem
	 */
	public String getSampleTypeCdSystem() {
		return sampleTypeCdSystem;
	}
	/**
	 * @param sampleTypeCdSystem the sampleTypeCdSystem to set
	 */
	public void setSampleTypeCdSystem(String sampleTypeCdSystem) {
		this.sampleTypeCdSystem = sampleTypeCdSystem;
	}
	/**
	 * @return the sampleTypeCdSystemName
	 */
	public String getSampleTypeCdSystemName() {
		return sampleTypeCdSystemName;
	}
	/**
	 * @param sampleTypeCdSystemName the sampleTypeCdSystemName to set
	 */
	public void setSampleTypeCdSystemName(String sampleTypeCdSystemName) {
		this.sampleTypeCdSystemName = sampleTypeCdSystemName;
	}
	

	
}
