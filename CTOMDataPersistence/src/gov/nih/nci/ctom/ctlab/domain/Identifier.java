package gov.nih.nci.ctom.ctlab.domain;

import java.util.Date;

public class Identifier
{
	private Long id;
	private String root;
	private String extension;
	private String assigningAuthorityName;
	private String displayableIndicator;
	private Long protocolId;
	private Long participantId;
	private Long studyParticipantAssignmentId;
	private String source;
	private Date ctomInsertDate=null;
	private Date ctomUpdateDate=null;
	private HealthCareSite healthCareSite=null;
	
	public String getAssigningAuthorityName() {
		return assigningAuthorityName;
	}
	public void setAssigningAuthorityName(String assigningAuthorityName) {
		this.assigningAuthorityName = assigningAuthorityName;
	}
	public String getDisplayableIndicator() {
		return displayableIndicator;
	}
	public void setDisplayableIndicator(String displayableIndicator) {
		this.displayableIndicator = displayableIndicator;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParticipantId() {
		return participantId;
	}
	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}
	public Long getProtocolId() {
		return protocolId;
	}
	public void setProtocolId(Long protocolId) {
		this.protocolId = protocolId;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Long getStudyParticipantAssignmentId() {
		return studyParticipantAssignmentId;
	}
	public void setStudyParticipantAssignmentId(Long studyParticipantAssignmentId) {
		this.studyParticipantAssignmentId = studyParticipantAssignmentId;
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
	 * @return the ctomUpdateDAte
	 */
	public Date getCtomUpdateDate() {
		return ctomUpdateDate;
	}
	/**
	 * @param ctomUpdateDAte the ctomUpdateDAte to set
	 */
	public void setCtomUpdateDate(Date ctomUpdateDate) {
		this.ctomUpdateDate = ctomUpdateDate;
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
}
