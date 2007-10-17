package gov.nih.nci.ctom.ctlab.domain;

public class SpecimenCollection {
	private CentralLaboratory centralLaboratory;
	private Specimen specimen;
	private Long procedureActivityId;
	
	/**
	 * @return the centralLaboratory
	 */
	public CentralLaboratory getCentralLaboratory() {
		return centralLaboratory;
	}
	/**
	 * @param centralLaboratory the centralLaboratory to set
	 */
	public void setCentralLaboratory(CentralLaboratory centralLaboratory) {
		this.centralLaboratory = centralLaboratory;
	}
	/**
	 * @return the specimen
	 */
	public Specimen getSpecimen() {
		return specimen;
	}
	/**
	 * @param specimen the specimen to set
	 */
	public void setSpecimen(Specimen specimen) {
		this.specimen = specimen;
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

}
