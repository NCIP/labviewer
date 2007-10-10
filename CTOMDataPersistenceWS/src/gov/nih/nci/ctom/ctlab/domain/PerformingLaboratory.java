package gov.nih.nci.ctom.ctlab.domain;

public class PerformingLaboratory {
	
	Long organizationId;
	String plIdentifier = null;
	String plName = null;
	/**
	 * @return the plIdentifier
	 */
	public String getPlIdentifier() {
		return plIdentifier;
	}
	/**
	 * @param plIdentifier the plIdentifier to set
	 */
	public void setPlIdentifier(String plIdentifier) {
		this.plIdentifier = plIdentifier;
	}
	/**
	 * @return the plName
	 */
	public String getPlName() {
		return plName;
	}
	/**
	 * @param plName the plName to set
	 */
	public void setPlName(String plName) {
		this.plName = plName;
	}
	/**
	 * @return the organizationId
	 */
	public Long getOrganizationId() {
		return organizationId;
	}
	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	
}
