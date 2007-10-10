package gov.nih.nci.ctom.ctlab.domain;

public class ConceptDescriptor {

	String code = null;
	String codeSystem = null;
	String codeSystemName = null;
	String codeSystemVersion = null;
	String displayText = null;
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the codeSystem
	 */
	public String getCodeSystem() {
		return codeSystem;
	}
	/**
	 * @param codeSystem the codeSystem to set
	 */
	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}
	/**
	 * @return the codeSystemName
	 */
	public String getCodeSystemName() {
		return codeSystemName;
	}
	/**
	 * @param codeSystemName the codeSystemName to set
	 */
	public void setCodeSystemName(String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}
	/**
	 * @return the codeSystemVersion
	 */
	public String getCodeSystemVersion() {
		return codeSystemVersion;
	}
	/**
	 * @param codeSystemVersion the codeSystemVersion to set
	 */
	public void setCodeSystemVersion(String codeSystemVersion) {
		this.codeSystemVersion = codeSystemVersion;
	}
	/**
	 * @return the displayText
	 */
	public String getDisplayText() {
		return displayText;
	}
	/**
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	
}
