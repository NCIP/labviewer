package gov.nih.nci.ctom.ctlab.domain;
public class Protocol {		
	String prNCIIdentifier = null;
	String prIdentifierAsgnAuth = null;
	String prLongTitle = null;
	HealthCareSite healthCareSite;
	Investigator investigator;
	
	/**
	 * @return the prIdentifierAsgnAuth
	 */
	public String getPrIdentifierAsgnAuth() {
		return prIdentifierAsgnAuth;
	}
	/**
	 * @param prIdentifierAsgnAuth the prIdentifierAsgnAuth to set
	 */
	public void setPrIdentifierAsgnAuth(String prIdentifierAsgnAuth) {
		this.prIdentifierAsgnAuth = prIdentifierAsgnAuth;
	}
	/**
	 * @return the prLongTitle
	 */
	public String getPrLongTitle() {
		return prLongTitle;
	}
	/**
	 * @param prLongTitle the prLongTitle to set
	 */
	public void setPrLongTitle(String prLongTitle) {
		this.prLongTitle = prLongTitle;
	}
	/**
	 * @return the prNCIIdentifier
	 */
	public String getPrNCIIdentifier() {
		return prNCIIdentifier;
	}
	/**
	 * @param prNCIIdentifier the prNCIIdentifier to set
	 */
	public void setPrNCIIdentifier(String prNCIIdentifier) {
		this.prNCIIdentifier = prNCIIdentifier;
	}
	/**
	 * @return the healthCareSite
	 */
	public HealthCareSite getHealthCareSite() {
		return healthCareSite;
	}
	/**
	 * @param healthCareSite the healthCareSite to set
	 */
	public void setHealthCareSite(HealthCareSite healthCareSite) {
		this.healthCareSite = healthCareSite;
	}
	/**
	 * @return the investigator
	 */
	public Investigator getInvestigator() {
		return investigator;
	}
	/**
	 * @param investigator the investigator to set
	 */
	public void setInvestigator(Investigator investigator) {
		this.investigator = investigator;
	}
	
}
