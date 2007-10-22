package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class ConceptDescriptor {

	private Long id;
	private String code = null;
	private String codeSystem = null;
	private String codeSystemName = null;
	private String codeSystemVersion = null;
	private String displayText = null;
	private String source = null;
	private Date sourceExtractDate =null;
	private Date ctomInsertDate =null;
	private Date ctomUpdateDate =null;
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
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the sourceExtractDate
	 */
	public Date getSourceExtractDate() {
		return sourceExtractDate;
	}
	/**
	 * @param sourceExtractDate the sourceExtractDate to set
	 */
	public void setSourceExtractDate(Date sourceExtractDate) {
		this.sourceExtractDate = sourceExtractDate;
	}
	/**
	 * @return the ctomInsertDate
	 */
	public Date getCtomInsertDate() {
		return ctomInsertDate;
	}
	/**
	 * @param ctomInsertDate the ctomInsertDate to set
	 */
	public void setCtomInsertDate(Date ctomInsertDate) {
		this.ctomInsertDate = ctomInsertDate;
	}
	/**
	 * @return the ctomUpdateDate
	 */
	public Date getCtomUpdateDate() {
		return ctomUpdateDate;
	}
	/**
	 * @param ctomUpdateDate the ctomUpdateDate to set
	 */
	public void setCtomUpdateDate(Date ctomUpdateDate) {
		this.ctomUpdateDate = ctomUpdateDate;
	}

	
}
