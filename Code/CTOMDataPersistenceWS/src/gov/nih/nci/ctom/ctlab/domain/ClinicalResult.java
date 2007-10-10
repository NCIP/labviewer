package gov.nih.nci.ctom.ctlab.domain;

public class ClinicalResult {

	String panelName = null;
	String crCode;
	String crCodeSystem;
	String crCodeSystemName;
	Double crCodeSystemVersion;
	String crCodeDisplayText;
	String value = null;
	String valueUnitOfMeasureCd = null;
	String valueUnitOfMeasureCdSystem = null;
	String valueUnitOfMeasureCdSystemName = null;
	String refFlag = null;
	String labTechCd = null;
	Long observationId;
	PerformingLaboratory performingLaboratory;
	/**
	 * @return the labTechCd
	 */
	public String getLabTechCd() {
		return labTechCd;
	}
	/**
	 * @param labTechCd the labTechCd to set
	 */
	public void setLabTechCd(String labTechCd) {
		this.labTechCd = labTechCd;
	}
	/**
	 * @return the panelName
	 */
	public String getPanelName() {
		return panelName;
	}
	/**
	 * @param panelName the panelName to set
	 */
	public void setPanelName(String panelName) {
		this.panelName = panelName;
	}
	/**
	 * @return the refFlag
	 */
	public String getRefFlag() {
		return refFlag;
	}
	/**
	 * @param refFlag the refFlag to set
	 */
	public void setRefFlag(String refFlag) {
		this.refFlag = refFlag;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the valueUnitOfMeasureCd
	 */
	public String getValueUnitOfMeasureCd() {
		return valueUnitOfMeasureCd;
	}
	/**
	 * @param valueUnitOfMeasureCd the valueUnitOfMeasureCd to set
	 */
	public void setValueUnitOfMeasureCd(String valueUnitOfMeasureCd) {
		this.valueUnitOfMeasureCd = valueUnitOfMeasureCd;
	}
	/**
	 * @return the performingLaboratory
	 */
	public PerformingLaboratory getPerformingLaboratory() {
		return performingLaboratory;
	}
	/**
	 * @param performingLaboratory the performingLaboratory to set
	 */
	public void setPerformingLaboratory(PerformingLaboratory performingLaboratory) {
		this.performingLaboratory = performingLaboratory;
	}
	/**
	 * @return the observationId
	 */
	public Long getObservationId() {
		return observationId;
	}
	/**
	 * @param observationId the observationId to set
	 */
	public void setObservationId(Long observationId) {
		this.observationId = observationId;
	}
	/**
	 * @return the valueUnitOfMeasureCdSystem
	 */
	public String getValueUnitOfMeasureCdSystem() {
		return valueUnitOfMeasureCdSystem;
	}
	/**
	 * @param valueUnitOfMeasureCdSystem the valueUnitOfMeasureCdSystem to set
	 */
	public void setValueUnitOfMeasureCdSystem(String valueUnitOfMeasureCdSystem) {
		this.valueUnitOfMeasureCdSystem = valueUnitOfMeasureCdSystem;
	}
	/**
	 * @return the valueUnitOfMeasureCdSystemName
	 */
	public String getValueUnitOfMeasureCdSystemName() {
		return valueUnitOfMeasureCdSystemName;
	}
	/**
	 * @param valueUnitOfMeasureCdSystemName the valueUnitOfMeasureCdSystemName to set
	 */
	public void setValueUnitOfMeasureCdSystemName(
			String valueUnitOfMeasureCdSystemName) {
		this.valueUnitOfMeasureCdSystemName = valueUnitOfMeasureCdSystemName;
	}
	/**
	 * @return the crCode
	 */
	public String getCrCode() {
		return crCode;
	}
	/**
	 * @param crCode the crCode to set
	 */
	public void setCrCode(String crCode) {
		this.crCode = crCode;
	}
	/**
	 * @return the crCodeSystem
	 */
	public String getCrCodeSystem() {
		return crCodeSystem;
	}
	/**
	 * @param crCodeSystem the crCodeSystem to set
	 */
	public void setCrCodeSystem(String crCodeSystem) {
		this.crCodeSystem = crCodeSystem;
	}
	/**
	 * @return the crCodeSystemName
	 */
	public String getCrCodeSystemName() {
		return crCodeSystemName;
	}
	/**
	 * @param crCodeSystemName the crCodeSystemName to set
	 */
	public void setCrCodeSystemName(String crCodeSystemName) {
		this.crCodeSystemName = crCodeSystemName;
	}
	/**
	 * @return the crCodeDisplayText
	 */
	public String getCrCodeDisplayText() {
		return crCodeDisplayText;
	}
	/**
	 * @param crCodeDisplayText the crCodeDisplayText to set
	 */
	public void setCrCodeDisplayText(String crCodeDisplayText) {
		this.crCodeDisplayText = crCodeDisplayText;
	}
	/**
	 * @return the crCodeSystemVersion
	 */
	public Double getCrCodeSystemVersion() {
		return crCodeSystemVersion;
	}
	/**
	 * @param crCodeSystemVersion the crCodeSystemVersion to set
	 */
	public void setCrCodeSystemVersion(Double crCodeSystemVersion) {
		this.crCodeSystemVersion = crCodeSystemVersion;
	}

	
}
