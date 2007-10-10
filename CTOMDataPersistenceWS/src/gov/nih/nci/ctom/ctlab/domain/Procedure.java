package gov.nih.nci.ctom.ctlab.domain;

/**
 * @author griffinch
 *
 */
public class Procedure {
	private Long id;
	private String fastingStatus;
	private SpecimenCollection specimenCollection;

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
	 * @return the specimenCollection
	 */
	public SpecimenCollection getSpecimenCollection() {
		return specimenCollection;
	}

	/**
	 * @param specimenCollection the specimenCollection to set
	 */
	public void setSpecimenCollection(SpecimenCollection specimenCollection) {
		this.specimenCollection = specimenCollection;
	}

	/**
	 * @return the fastingStatus
	 */
	public String getFastingStatus() {
		return fastingStatus;
	}

	/**
	 * @param fastingStatus the fastingStatus to set
	 */
	public void setFastingStatus(String fastingStatus) {
		this.fastingStatus = fastingStatus;
	}
}
