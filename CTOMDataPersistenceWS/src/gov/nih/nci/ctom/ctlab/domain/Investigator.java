package gov.nih.nci.ctom.ctlab.domain;

public class Investigator {

	String  invNCIId = null;
	String  invLastName = null;
	/**
	 * @return the invLastName
	 */
	public String getInvLastName() {
		return invLastName;
	}
	/**
	 * @param invLastName the invLastName to set
	 */
	public void setInvLastName(String invLastName) {
		this.invLastName = invLastName;
	}
	/**
	 * @return the invNCIId
	 */
	public String getInvNCIId() {
		return invNCIId;
	}
	/**
	 * @param invNCIId the invNCIId to set
	 */
	public void setInvNCIId(String invNCIId) {
		this.invNCIId = invNCIId;
	}

}
